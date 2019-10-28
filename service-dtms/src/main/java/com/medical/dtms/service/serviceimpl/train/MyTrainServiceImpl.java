package com.medical.dtms.service.serviceimpl.train;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.medical.dtms.common.eception.BizException;
import com.medical.dtms.common.enumeration.ErrorCodeEnum;
import com.medical.dtms.common.enumeration.train.TrainStateEnum;
import com.medical.dtms.common.model.exam.ExamDetailModel;
import com.medical.dtms.common.model.exam.ExamQuestionsTypeModel;
import com.medical.dtms.common.model.exam.query.ExamSubmintQuestionQuery;
import com.medical.dtms.common.model.item.SimpleQMSItemDetailsModel;
import com.medical.dtms.common.model.question.DtmsQuestionsModel;
import com.medical.dtms.common.model.train.MyTrainBeginModel;
import com.medical.dtms.common.model.train.MyTrainPageModel;
import com.medical.dtms.common.model.train.MyTrainSubmitModel;
import com.medical.dtms.common.model.train.query.MyTrainPageQuery;
import com.medical.dtms.common.model.train.query.TrainSubmitAnswerQuery;
import com.medical.dtms.common.util.BeanConvertUtils;
import com.medical.dtms.common.util.DateUtils;
import com.medical.dtms.common.util.IdGenerator;
import com.medical.dtms.dto.question.DtmsQuestionsDTO;
import com.medical.dtms.dto.question.QuestionQuery;
import com.medical.dtms.dto.train.TrainConfigDTO;
import com.medical.dtms.dto.train.TrainQuestionProcessDTO;
import com.medical.dtms.dto.train.TrainUserDTO;
import com.medical.dtms.feignservice.train.MyTrainService;
import com.medical.dtms.service.manager.exam.ExamModelManager;
import com.medical.dtms.service.manager.exam.ExamQuestionsTypeManager;
import com.medical.dtms.service.manager.item.QMSItemDetailsManager;
import com.medical.dtms.service.manager.question.DtmsQuestionManager;
import com.medical.dtms.service.manager.train.TrainConfigManager;
import com.medical.dtms.service.manager.train.TrainQuestionProcessManager;
import com.medical.dtms.service.manager.train.TrainUserManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @version： MyTrainServiceImpl.java v 1.0, 2019年10月11日 14:28 wuxuelin Exp$
 * @Description 我的培训
 **/
@RestController
@Slf4j
public class MyTrainServiceImpl implements MyTrainService {

    @Autowired
    private TrainUserManager trainUserManager;
    @Autowired
    private QMSItemDetailsManager itemDetailsManager;
    @Autowired
    private TrainConfigManager trainConfigManager;
    @Autowired
    private DtmsQuestionManager questionManager;
    @Autowired
    private ExamModelManager examModelManager;
    @Autowired
    private ExamQuestionsTypeManager examQuestionsTypeManager;
    @Autowired
    private IdGenerator idGenerator;
    @Autowired
    private TrainQuestionProcessManager processManager;

    /***
     *
     *   确定交卷 的时候注意：如果用户提前完成考试（即 用户完成考试时间小于考试设定的结束时间），则将tb_dtms_train_user 表中的 考试结束时间设置为 用户完成考试的时间
     *  考试完成的前提是用户所有题目都答了，即用户所有题目答案都不为空
     * */

    /**
     * 培训管理 - 我的培训 - 分页展示
     *
     * @param query
     * @return com.github.pagehelper.PageInfo<com.medical.dtms.common.model.train.MyTrainPageModel>
     **/
    @Override
    public PageInfo<MyTrainPageModel> pageListMyTrain(@RequestBody MyTrainPageQuery query) {
        PageHelper.startPage(query.getPageNo(), query.getPageSize());
        List<MyTrainPageModel> dtos = trainUserManager.pageListMyTrain(query);
        if (CollectionUtils.isEmpty(dtos)) {
            return new PageInfo<>(new ArrayList<>());
        }
        // 查询培训类别名称
        List<Long> trainTypeIds = dtos.stream().map(MyTrainPageModel::getTrainTypeId).distinct().collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(trainTypeIds)) {
            List<SimpleQMSItemDetailsModel> detailsModelList = itemDetailsManager.getQMSItemDetailsByIds(trainTypeIds);
            if (CollectionUtils.isNotEmpty(detailsModelList)) {
                for (MyTrainPageModel dto : dtos) {
                    List<SimpleQMSItemDetailsModel> collect = detailsModelList.stream().filter(simpleQMSItemDetailsModel -> dto.getTrainTypeId().equals(simpleQMSItemDetailsModel.getBizId())).collect(Collectors.toList());
                    dto.setTrainTypeName(CollectionUtils.isEmpty(collect) == true ? null : StringUtils.isBlank(collect.get(0).getItemName()) == true ? null : collect.get(0).getItemName());
                }
            }
        }

        for (MyTrainPageModel dto : dtos) {
            // 比较用户完成考试时间与考试规定结束时间，来确定是否完成考试
            if (null == dto.getFinishTime()) {
                dto.setIsFinishQuestions(false);
            }
            if (null != dto.getFinishTime() && dto.getFinishTime().before(dto.getEndDate()) && !dto.getIsFinishQuestions()) {
                dto.setEndDate(dto.getFinishTime());
            }

            // 比较培训设置分数与用户得分，确定用户是否及格（即 培训结论）
            if (StringUtils.isNotBlank(dto.getPointStr()) && null != dto.getPassPoint()) {
                if (Integer.valueOf(dto.getPointStr()) < dto.getPassPoint()) {
                    dto.setPass(false);
                    dto.setPassStr("不合格");
                } else {
                    dto.setPass(true);
                    dto.setPassStr("合格");
                }
            }

            // 比较用户开始考试时间、结束考试的时间与考试设置的结束时间，确定培训状态
            if (null != dto.getState()) {
                dto.setStateStr(TrainStateEnum.getNameByState(dto.getState()));
            }
        }
        return new PageInfo<>(dtos);
    }

    /**
     * 培训管理 - 我的培训 - 开始考试
     *
     * @param processDTO
     * @return
     **/
    @Override
    @Transactional
    public MyTrainBeginModel beginTrainExam(@RequestBody TrainQuestionProcessDTO processDTO) {
        MyTrainBeginModel beginModel = new MyTrainBeginModel();
        beginModel.setUserId(processDTO.getCreatorId());
        beginModel.setDspName(processDTO.getCreator());

        // 查询培训对应的考试id、及格分数等信息
        TrainConfigDTO configDTO = trainConfigManager.selectByPrimaryKey(processDTO.getTrainId());
        if (null == configDTO) {
            log.error("无此培训数据：id:" + processDTO.getTrainId());
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "无此培训数据");
        }
        if (configDTO.getEndDate().before(new Date())) {
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "考试已经结束！现在时间为：" +
                    DateUtils.format(new Date(), DateUtils.YYYY_MM_DD_HH_mm_ss) + ",结束时间为：" + DateUtils.format(configDTO.getEndDate(), DateUtils.YYYY_MM_DD_HH_mm_ss));
        }

        // 查询用户考试信息
        TrainSubmitAnswerQuery submitAnswerQuery = new TrainSubmitAnswerQuery();
        submitAnswerQuery.setTrainId(processDTO.getTrainId());
        submitAnswerQuery.setTrainUserId(Long.valueOf(processDTO.getCreatorId()));
        TrainUserDTO trainUserInfo = trainUserManager.getTrainUserByPrimaryKey(submitAnswerQuery);
        if (null == trainUserInfo) {
            log.error("无培训信息，id" + processDTO.getTrainId());
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "服务器内部错误，请稍后重试！");
        }

        // 查询试卷名称等信息
        ExamDetailModel examDetailModel = examModelManager.getExamByExamId(configDTO.getExamId());
        if (null == examDetailModel) {
            log.error("无试卷信息，id" + configDTO.getExamId());
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "服务器内部错误，请稍后重试！");
        }
        beginModel.setExamName(examDetailModel.getExamName());
        beginModel.setExamDuration(examDetailModel.getExamDuration());
        beginModel.setTotalPoints(configDTO.getTotalPoint());
        beginModel.setPassPoint(configDTO.getPassPoint());
        beginModel.setExamId(configDTO.getExamId());

        // 查询试题类型
        List<ExamQuestionsTypeModel> typeModels = examQuestionsTypeManager.listQuestionTypeByExamId(configDTO.getExamId());
        if (CollectionUtils.isNotEmpty(typeModels)) {
            //查询试题
            getQuestionsInfo(typeModels);
            beginModel.setExamQuestionTypes(typeModels);

            // 未开始考试，本地点击为 第一次考试，此时初始化试卷信息，同时将开始考试时间设置为当前时间
            if (null == trainUserInfo.getBeginTime()) {
                List<TrainQuestionProcessDTO> processDTOList = new ArrayList<>();
                TrainQuestionProcessDTO questionProcessDTO;
                for (int length = typeModels.size(), i = 0; i < length; i++) {
                    ExamQuestionsTypeModel typeModel = typeModels.get(i);
                    for (DtmsQuestionsModel questionsModel : typeModel.getQuestionForPreview()) {
                        questionProcessDTO = new TrainQuestionProcessDTO();
                        questionProcessDTO.setBizId(idGenerator.nextId());
                        questionProcessDTO.setCreator(processDTO.getCreator());
                        questionProcessDTO.setCreatorId(processDTO.getCreatorId());
                        questionProcessDTO.setTrainId(processDTO.getTrainId());
                        questionProcessDTO.setTrainUserId(Long.parseLong(processDTO.getCreatorId()));
                        questionProcessDTO.setUserId(Long.parseLong(processDTO.getCreatorId()));
                        questionProcessDTO.setExamId(configDTO.getExamId());
                        questionProcessDTO.setQuestionsId(questionsModel.getBizId());
                        processDTOList.add(questionProcessDTO);
                    }
                }

                try {
                    processManager.batchInsertInfo(processDTOList);
                } catch (Exception e) {
                    log.error("批量更新培训过程表失败", e);
                    throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "服务器内部错误，请稍后重试");
                }
                // 更新开始时间为当前时间等
                trainUserInfo.setBeginTime(new Date());
                trainUserInfo.setState(0);
                trainUserInfo.setIsFinishQuestions(false);
                trainUserInfo.setPassPoint(configDTO.getPassPoint());
                trainUserInfo.setModifier(processDTO.getCreator());
                trainUserInfo.setModifierId(processDTO.getCreatorId());
                try {
                    trainUserManager.updateUserInfo(trainUserInfo);
                } catch (Exception e) {
                    log.error("更新trainUser表出错", e);
                    throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "服务器内部错误，请稍后重试");
                }
            }
        }
        return beginModel;
    }

    /**
     * 培训管理 - 我的培训 - 提交试卷
     *
     * @param processDTO
     * @return
     **/
    @Override
    @Transactional
    public Boolean submitTrainAnswer(@RequestBody MyTrainSubmitModel model) {
        // 获取该套试卷的题信息
        List<DtmsQuestionsModel> questionsModels = questionManager.listQuestionsByExamId(model.getExamId());
        if (CollectionUtils.isEmpty(questionsModels)) {
            log.error("试卷id:" + model.getExamId());
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "无此试卷试题数据");
        }

        TrainSubmitAnswerQuery submitAnswerQuery = new TrainSubmitAnswerQuery();
        submitAnswerQuery.setTrainId(model.getTrainId());
        submitAnswerQuery.setTrainUserId(Long.valueOf(model.getModifierId()));
        TrainUserDTO trainUserInfo = trainUserManager.getTrainUserByPrimaryKey(submitAnswerQuery);

        // 查询试题类型
        List<ExamQuestionsTypeModel> typeModels = examQuestionsTypeManager.listQuestionTypeByExamId(model.getExamId());

        if (model.getQuestions().size() == questionsModels.size()) {
            // 全部完成，此时结束考试
            trainUserInfo.setFinishTime(new Date());
            trainUserInfo.setIsFinish(true);
        }

        // 算分
        Integer score = 0;
        for (ExamSubmintQuestionQuery question : model.getQuestions()) {
            List<DtmsQuestionsModel> collect = questionsModels.stream().filter(dtmsQuestionsModel -> dtmsQuestionsModel.getBizId().equals(question.getQuestionsId())).collect(Collectors.toList());
            collect.removeAll(Collections.singleton(null));
            if (CollectionUtils.isEmpty(collect)) {
                log.error("无此题数据，id：" + question.getQuestionsId());
                throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "服务器内部错误，请稍后重试");
            }
            if (collect.get(0).getAnswer().equals(question.getAnswer())) {
                List<ExamQuestionsTypeModel> modelList = typeModels.stream().filter(examQuestionsTypeModel -> examQuestionsTypeModel.getQuestionsTypeId().equals(collect.get(0).getQuestionsTypeId())).collect(Collectors.toList());
                modelList.removeAll(Collections.singleton(null));
                if (CollectionUtils.isEmpty(modelList)) {
                    log.error("无试题类型数据，typeId：" + collect.get(0).getQuestionsTypeId());
                    throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "服务器内部错误，请稍后重试");
                }
                question.setQuestionsPoints(modelList.get(0).getQuestionsPoints());
                score += modelList.get(0).getQuestionsPoints();
            } else {
                question.setQuestionsPoints(0);
            }
        }

        // 更新答题过程表
        try {
            processManager.batchUpdateTrainQuestionProcess(model);
        } catch (Exception e) {
            log.error(" 更新答题过程表失败", e);
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "服务器内部错误，请稍后重试");
        }

        // 更新trainUser表
        trainUserInfo.setUserExamTotal(score);
        trainUserInfo.setPointStr(String.valueOf(score));
        trainUserInfo.setModifierId(model.getModifierId());
        trainUserInfo.setModifier(model.getModifier());
        try {
            trainUserManager.updateUserInfo(trainUserInfo);
        } catch (Exception e) {
            log.error("培训提交试卷时，更新trainUser表出错", e);
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "服务器内部错误，请稍后重试");
        }
        return true;
    }


    private void getQuestionsInfo(List<ExamQuestionsTypeModel> typeModels) {
        QuestionQuery query = new QuestionQuery();
        for (ExamQuestionsTypeModel typeModel : typeModels) {
            if (StringUtils.isNotBlank(typeModel.getExamQuestionString())) {
                query.setQuestionIds(Arrays.asList(typeModel.getExamQuestionString().split(",")));
                //查询试题
                List<DtmsQuestionsDTO> listQuestionsForPreview = questionManager
                        .listQuestionsForPreview(query);
                if (CollectionUtils.isEmpty(listQuestionsForPreview)) {
                    log.error("试题信息为空，type:" + typeModel.getExamQuestionTypeId());
                    throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "试题信息为空");
                }
                typeModel.setQuestionForPreview(CollectionUtils.isEmpty(listQuestionsForPreview) == true ? new ArrayList<>() : BeanConvertUtils
                        .convertList(listQuestionsForPreview, DtmsQuestionsModel.class));
            }
        }
    }

}
