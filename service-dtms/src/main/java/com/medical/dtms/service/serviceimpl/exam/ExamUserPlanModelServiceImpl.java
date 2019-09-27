/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.medical.dtms.service.serviceimpl.exam;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.medical.dtms.common.eception.BizException;
import com.medical.dtms.common.enumeration.ErrorCodeEnum;
import com.medical.dtms.common.enumeration.exam.QuestionTypeNameEnum;
import com.medical.dtms.common.model.exam.ExamDetailModel;
import com.medical.dtms.common.model.exam.ExamQuestionsTypeModel;
import com.medical.dtms.common.model.exam.ExamStartModel;
import com.medical.dtms.common.model.exam.query.ExamSubmintQuestionQuery;
import com.medical.dtms.common.model.exam.query.ExamSubmitAnswerQuery;
import com.medical.dtms.dto.exam.query.ExamUserAfreshPlanQuery;
import com.medical.dtms.common.model.question.DtmsQuestionsModel;
import com.medical.dtms.common.util.BeanConvertUtils;
import com.medical.dtms.common.util.IdGenerator;
import com.medical.dtms.dto.exam.ExamPlanModelDTO;
import com.medical.dtms.dto.exam.ExamUserAnswerModelDTO;
import com.medical.dtms.dto.exam.ExamUserPlanModelDTO;
import com.medical.dtms.dto.exam.query.ExamPlanModelQuery;
import com.medical.dtms.dto.item.QMSItemDetailsDTO;
import com.medical.dtms.dto.item.query.QMSItemDetailsQuery;
import com.medical.dtms.dto.question.DtmsQuestionsDTO;
import com.medical.dtms.dto.question.QuestionQuery;
import com.medical.dtms.dto.user.QMSUserDTO;
import com.medical.dtms.dto.user.query.BaseUserQuery;
import com.medical.dtms.feignservice.exam.ExamUserPlanModelService;
import com.medical.dtms.service.dataobject.exam.ExamUserAnswerModelDo;
import com.medical.dtms.service.manager.exam.*;
import com.medical.dtms.service.manager.item.QMSItemDetailsManager;
import com.medical.dtms.service.manager.question.DtmsQuestionManager;
import com.medical.dtms.service.manager.user.QMSUserManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author shenqifeng
 * @version $Id: ExamPlanModelServiceImpl.java, v 0.1 2019年9月9日 下午10:52:07 shenqifeng Exp $
 */
@Slf4j
@RestController
public class ExamUserPlanModelServiceImpl implements ExamUserPlanModelService {

    @Autowired
    private ExamUserPlanModelManager examUserPlanModelManager;
    @Autowired
    private QMSUserManager userManager;
    @Autowired
    private ExamModelManager examModelManager;
    @Autowired
    private ExamQuestionsTypeManager examQuestionsTypeManager;
    @Autowired
    private DtmsQuestionManager dtmsQuestionManager;
    @Autowired
    private ExamUserAnswerModelManager userAnswerModelManager;
    @Autowired
    private IdGenerator idGenerator;
    @Autowired
    private DtmsQuestionManager questionManager;
    @Autowired
    private QMSItemDetailsManager qmsItemDetailsManager;
    @Autowired
    private ExamPlanModelManager examPlanModelManager;

    @Override
    public PageInfo<ExamUserPlanModelDTO> listExamUserPlanByQuery(@RequestBody ExamPlanModelQuery query) {
        PageHelper.startPage(query.getPageNo(), query.getPageSize());
        List<ExamUserPlanModelDTO> list = examUserPlanModelManager.listExamUserPlanByQuery(query);
        PageInfo<ExamUserPlanModelDTO> paginfo = new PageInfo<>(list);
        return paginfo;
    }

    @Override
    public PageInfo<ExamUserPlanModelDTO> listExamUserPlanByQueryForMark(@RequestBody ExamPlanModelQuery query) {
        PageHelper.startPage(query.getPageNo(), query.getPageSize());
        List<ExamUserPlanModelDTO> list = examUserPlanModelManager.listExamUserPlanByQueryForMark(query);
        PageInfo<ExamUserPlanModelDTO> paginfo = new PageInfo<>(list);
        return paginfo;
    }


    /**
     * 开始考试
     *
     * @see com.medical.dtms.feignservice.exam.ExamUserPlanModelService#startExam(com.medical.dtms.dto.exam.ExamUserPlanModelDTO)
     */
    @Override
    @Transactional
    public ExamStartModel startExam(@RequestBody ExamUserPlanModelDTO userPlanModelDTO) {
        ExamStartModel model = new ExamStartModel();
        //获取用户考试信息
        ExamUserPlanModelDTO examUserPlanModelDTO = examUserPlanModelManager.selectExamUserPlanModelByBizId(userPlanModelDTO.getExamUserPlanModelId());
        if (null == examUserPlanModelDTO) {
            log.error("本场考试不存在");
            throw new BizException(ErrorCodeEnum.NO_DATA.getErrorCode(), "本场考试不存在");
        }
        if (null != examUserPlanModelDTO.getIsFinish() && examUserPlanModelDTO.getIsFinish()) {
            log.error("考试已结束,不能重复考试");
            throw new BizException(ErrorCodeEnum.NO_DATA.getErrorCode(), "考试已结束,不能重复考试");
        }
        BaseUserQuery userQuery = new BaseUserQuery();
        userQuery.setUserId(examUserPlanModelDTO.getUserId());
        QMSUserDTO user = userManager.getUserByCondition(userQuery);
        ExamDetailModel exam = examModelManager.getExamByExamId(examUserPlanModelDTO.getExamId());
        model.setUserId(examUserPlanModelDTO.getUserId());
        model.setDspName(user.getDspName());
        model.setExamId(examUserPlanModelDTO.getExamId());
        model.setExamPlanId(examUserPlanModelDTO.getExamPlanId());
        model.setExamUserPlanModelId(examUserPlanModelDTO.getExamUserPlanModelId());
        model.setExamName(exam.getExamName());
        model.setExamDuration(examUserPlanModelDTO.getExamDuration());
        model.setExamPlanName(examUserPlanModelDTO.getExamPlanName());
        model.setPassPoints(examUserPlanModelDTO.getPassPoints());
        model.setTotalPoints(examUserPlanModelDTO.getTotalPoints());
        //2.查询试题类型
        List<ExamQuestionsTypeModel> typeModels = examQuestionsTypeManager.listQuestionTypeByExamId(examUserPlanModelDTO.getExamId());
        if (CollectionUtils.isNotEmpty(typeModels)) {
            //查询试题试题
            QuestionQuery query = new QuestionQuery();
            for (ExamQuestionsTypeModel typeModel : typeModels) {
                if (StringUtils.isNotBlank(typeModel.getExamQuestionString())) {
                    query.setQuestionIds(Arrays.asList(typeModel.getExamQuestionString().split(",")));
                    //查询试题
                    List<DtmsQuestionsDTO> listQuestionsForPreview = dtmsQuestionManager
                            .listQuestionsForPreview(query);
                    typeModel.setQuestionForPreview(BeanConvertUtils
                            .convertList(listQuestionsForPreview, DtmsQuestionsModel.class));
                }
            }
            model.setExamQuestionTypes(typeModels);

            //通过是否存在开始考试时间判断是否存在已经提交的试卷
            if (null != examUserPlanModelDTO.getIsBegin() && !examUserPlanModelDTO.getIsBegin()) {
                //新增提交的试卷....
                ExamUserAnswerModelDTO userAnswerModelDTO = new ExamUserAnswerModelDTO();
                userAnswerModelDTO.setExamId(examUserPlanModelDTO.getExamId());
                userAnswerModelDTO.setExamPlanId(examUserPlanModelDTO.getExamPlanId());
                userAnswerModelDTO.setExamUserPlanId(examUserPlanModelDTO.getExamUserPlanModelId());
                userAnswerModelDTO.setUserId(examUserPlanModelDTO.getUserId());
                userAnswerModelDTO.setCreator(userPlanModelDTO.getCreateUserName());
                userAnswerModelDTO.setCreatorId(userPlanModelDTO.getCreateUserId().toString());
                //添加试题
                if (CollectionUtils.isNotEmpty(typeModels)) {
                    List<ExamUserAnswerModelDTO> dtos = new ArrayList<>();
                    for (int lenType = typeModels.size(), i = 0; i < lenType; i++) {
                        ExamQuestionsTypeModel typeModel = typeModels.get(i);
                        ExamUserAnswerModelDTO dto;
                        for (int lenQ = typeModel.getQuestionForPreview().size(), j = 0; j < lenQ; j++) {
                            dto = new ExamUserAnswerModelDTO();
                            dto.setExamId(userAnswerModelDTO.getExamId());
                            dto.setExamPlanId(userAnswerModelDTO.getExamPlanId());
                            dto.setExamUserPlanId(userAnswerModelDTO.getExamUserPlanId());
                            dto.setUserId(userAnswerModelDTO.getUserId());
                            dto.setCreator(userAnswerModelDTO.getCreator());
                            dto.setCreatorId(userAnswerModelDTO.getCreatorId());
                            dto.setExamUserAnswerModeId(idGenerator.nextId());
                            dto.setQuestionsId(typeModel.getQuestionForPreview().get(j).getBizId());
                            dtos.add(dto);
                        }
                    }
                    userAnswerModelManager.insertBatchExamUserAnswerModel(dtos);
                }

            }
            //开始考试
            ExamUserPlanModelDTO startExamUserPlan = new ExamUserPlanModelDTO();
            startExamUserPlan.setExamUserPlanModelId(examUserPlanModelDTO.getExamUserPlanModelId());
            startExamUserPlan.setModifyUserName(examUserPlanModelDTO.getModifyUserName());
            startExamUserPlan.setModifyUserId(examUserPlanModelDTO.getModifyUserId());
            startExamUserPlan.setIsBegin(true);
            examUserPlanModelManager.updateExamUserPlanModel(startExamUserPlan);
        }


        return model;
    }

    /**
     * 提交试卷
     *
     * @see com.medical.dtms.feignservice.exam.ExamUserPlanModelService#submitExamAnswer(com.medical.dtms.common.model.exam.query.ExamSubmitAnswerQuery)
     */
    @Override
    @Transactional
    public Boolean submitExamAnswer(@RequestBody ExamSubmitAnswerQuery query) {
        ExamUserPlanModelDTO examUserPlanlDTO = examUserPlanModelManager.selectExamUserPlanModelByBizId(query.getExamUserPlanId());
        if (null == examUserPlanlDTO) {
            log.error("本场考试不存在");
            throw new BizException(ErrorCodeEnum.NO_DATA.getErrorCode(), "本场考试不存在");
        }
        if (null != examUserPlanlDTO.getIsFinish() && examUserPlanlDTO.getIsFinish()) {
            log.error("该试卷已提交,勿重复提交");
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "该试卷已提交,勿重复提交");
        }
        //创建要修改的试卷内容(更新考试结束时间,考试得分,考试结束时间)
        ExamUserPlanModelDTO examUserPlanModelDTO = new ExamUserPlanModelDTO();
        examUserPlanModelDTO.setModifyUserId(Long.parseLong(query.getModifierId()));
        examUserPlanModelDTO.setModifyUserName(query.getModifier());
        examUserPlanModelDTO.setExamUserPlanModelId(query.getExamUserPlanId());
        //准备提交更新的题目信息
        List<ExamUserAnswerModelDTO> dtos = new ArrayList<>();
        List<ExamSubmintQuestionQuery> submitQuestions = query.getQuestions();
        int total_points = 0;
        if (CollectionUtils.isNotEmpty(submitQuestions)) {
            ExamUserAnswerModelDTO dto;
            DtmsQuestionsDTO dtmsQuestionsDTO;
            for (ExamSubmintQuestionQuery submitQuestion : submitQuestions) {
                dto = new ExamUserAnswerModelDTO();
                dto.setModifier(query.getModifier());
                dto.setModifierId(query.getModifierId());
                dto.setUserId(query.getUserId());
                dto.setExamId(query.getExamId());
                dto.setExamPlanId(query.getExamPlanId());
                dto.setExamUserPlanId(query.getExamUserPlanId());
                String submitAnswer = submitQuestion.getAnswer();
                if (null != submitAnswer && !"".equals(submitAnswer)) {
                    dtmsQuestionsDTO = questionManager.getQuestionById(submitQuestion.getQuestionsId());
                    String answer = dtmsQuestionsDTO.getAnswer();
                    if (answer.equals(submitAnswer)) {
                        Integer questionsPoints = submitQuestion.getQuestionsPoints();
                        dto.setAnswerPoints(questionsPoints);
                        total_points += questionsPoints;
                    }
                    dto.setAnswer(submitAnswer);
                }
                dtos.add(dto);
            }
            if (CollectionUtils.isNotEmpty(dtos)) {
                userAnswerModelManager.updateBatchExamUserAnswerModel(dtos);
            }
        }
        examUserPlanModelDTO.setBaseTotalPoints(total_points);
        examUserPlanModelDTO.setIsFinish(true);
        examUserPlanModelManager.updateExamUserPlanModel(examUserPlanModelDTO);
        return true;
    }

    /**
     * 开始考试
     *
     * @see com.medical.dtms.feignservice.exam.ExamUserPlanModelService#startExam(com.medical.dtms.dto.exam.ExamUserPlanModelDTO)
     */
    @Override
    public ExamStartModel lookExam(@RequestBody ExamUserPlanModelDTO userPlanModelDTO) {
        ExamStartModel model = new ExamStartModel();
        //获取用户考试信息
        ExamUserPlanModelDTO examUserPlanModelDTO = examUserPlanModelManager.selectExamUserPlanModelByBizId(userPlanModelDTO.getExamUserPlanModelId());
        if (null == examUserPlanModelDTO) {
            log.error("本场考试不存在");
            throw new BizException(ErrorCodeEnum.NO_DATA.getErrorCode(), "本场考试不存在");
        }
        if (null != examUserPlanModelDTO.getIsBegin() && !examUserPlanModelDTO.getIsBegin()) {
            log.error("请先考试,再查看");
            throw new BizException(ErrorCodeEnum.NO_DATA.getErrorCode(), "请先考试,再查看");
        }
        BaseUserQuery userQuery = new BaseUserQuery();
        userQuery.setUserId(examUserPlanModelDTO.getUserId());
        QMSUserDTO user = userManager.getUserByCondition(userQuery);
        ExamDetailModel exam = examModelManager.getExamByExamId(examUserPlanModelDTO.getExamId());
        model.setUserId(examUserPlanModelDTO.getUserId());
        model.setDspName(user.getDspName());
        model.setExamId(examUserPlanModelDTO.getExamId());
        model.setExamPlanId(examUserPlanModelDTO.getExamPlanId());
        model.setExamUserPlanModelId(examUserPlanModelDTO.getExamUserPlanModelId());
        model.setExamName(exam.getExamName());
        model.setExamDuration(examUserPlanModelDTO.getExamDuration());
        model.setExamPlanName(examUserPlanModelDTO.getExamPlanName());
        model.setPassPoints(examUserPlanModelDTO.getPassPoints());
        model.setTotalPoints(examUserPlanModelDTO.getTotalPoints());
        model.setUserPoints(examUserPlanModelDTO.getBaseTotalPoints());
        //2.查询试题类型
        List<ExamQuestionsTypeModel> typeModels = examQuestionsTypeManager.listQuestionTypeByExamId(examUserPlanModelDTO.getExamId());
        if (CollectionUtils.isNotEmpty(typeModels)) {
            //查询试题试题
            QuestionQuery query = new QuestionQuery();
            ExamUserAnswerModelDo answerModelDo;
            ExamUserAnswerModelDTO userAnswer;
            for (ExamQuestionsTypeModel typeModel : typeModels) {
                if (StringUtils.isNotBlank(typeModel.getExamQuestionString())) {
                    query.setQuestionIds(Arrays.asList(typeModel.getExamQuestionString().split(",")));
                    //查询试题
                    List<DtmsQuestionsDTO> listQuestionsForPreview = dtmsQuestionManager
                            .listQuestionsForPreview(query);
                    typeModel.setQuestionForPreview(BeanConvertUtils
                            .convertList(listQuestionsForPreview, DtmsQuestionsModel.class));
                    for (DtmsQuestionsModel questionsModel : typeModel.getQuestionForPreview()) {
                        answerModelDo = new ExamUserAnswerModelDo();
                        answerModelDo.setQuestionsId(questionsModel.getBizId());
                        answerModelDo.setExamId(examUserPlanModelDTO.getExamId());
                        answerModelDo.setExamPlanId(examUserPlanModelDTO.getExamPlanId());
                        answerModelDo.setExamUserPlanId(examUserPlanModelDTO.getExamUserPlanModelId());
                        userAnswer = userAnswerModelManager.getUserAnswerByCondition(answerModelDo);
                        if (null != userAnswer) {
                            questionsModel.setUserAnswer(userAnswer.getAnswer());
                            questionsModel.setUserPoints(userAnswer.getAnswerPoints());
                        }
                        userAnswer = null;
                    }
                }
            }
            model.setExamQuestionTypes(typeModels);
        }
        return model;
    }


    /**
     * 提交阅卷结果
     *
     * @see com.medical.dtms.feignservice.exam.ExamUserPlanModelService#submitExamAnswer(com.medical.dtms.common.model.exam.query.ExamSubmitAnswerQuery)
     */
    @Override
    @Transactional
    public Boolean submitExamMarkResult(@RequestBody ExamSubmitAnswerQuery query) {
        ExamUserPlanModelDTO examUserPlanlDTO = examUserPlanModelManager.selectExamUserPlanModelByBizId(query.getExamUserPlanId());
        if (null == examUserPlanlDTO) {
            log.error("本场考试不存在");
            throw new BizException(ErrorCodeEnum.NO_DATA.getErrorCode(), "本场考试不存在");
        }
        //创建要修改的试卷内容(更新修改者信息)
        examUserPlanlDTO.setModifyUserId(Long.parseLong(query.getModifierId()));
        examUserPlanlDTO.setModifyUserName(query.getModifier());
        //准备提交更新的题目信息
        List<ExamUserAnswerModelDTO> dtos = new ArrayList<>();
        List<ExamSubmintQuestionQuery> submitQuestions = query.getQuestions();
        int totalPoints = examUserPlanlDTO.getTotalPoints();
        if (CollectionUtils.isNotEmpty(submitQuestions)) {
            ExamUserAnswerModelDTO dto;
            DtmsQuestionsDTO questionDTO;
            QMSItemDetailsQuery qmsItemDetailsQuery;
            QMSItemDetailsDTO qmsItemDetailsDTO;
            Long questionsTypeId;
            for (ExamSubmintQuestionQuery submitQuestion : submitQuestions) {
                questionDTO = questionManager.getQuestionById(submitQuestion.getQuestionsId());
                qmsItemDetailsQuery = new QMSItemDetailsQuery();
                qmsItemDetailsQuery.setBizId(Long.parseLong(questionDTO.getQuestionsTypeId()));
                qmsItemDetailsDTO = qmsItemDetailsManager.getQMSItemDetailsByCondition(qmsItemDetailsQuery);
                questionsTypeId = Long.parseLong(questionDTO.getQuestionsTypeId());
                if (null != questionDTO && null != questionsTypeId
                        && !QuestionTypeNameEnum.TYPE_SINGLE_CHOICE_QUESTION.getName().equals(qmsItemDetailsDTO.getItemName())
                        && !QuestionTypeNameEnum.TYPE_MULTIPLE_CHOICES_QUESTION.getName().equals(qmsItemDetailsDTO.getItemName())
                        && !QuestionTypeNameEnum.TYPE_JUDGE_QUESTION.getName().equals(qmsItemDetailsDTO.getItemName())) {
                    dto = new ExamUserAnswerModelDTO();
                    dto.setModifier(query.getModifier());
                    dto.setModifierId(query.getModifierId());
                    dto.setUserId(query.getUserId());
                    dto.setExamId(query.getExamId());
                    dto.setExamPlanId(query.getExamPlanId());
                    dto.setExamUserPlanId(query.getExamUserPlanId());
                    dto.setAnswerPoints(submitQuestion.getQuestionsPoints());
                    totalPoints += submitQuestion.getQuestionsPoints();
                    dtos.add(dto);
                }
            }
            if (CollectionUtils.isNotEmpty(dtos)) {
                userAnswerModelManager.updateBatchExamUserAnswerModel(dtos);
            }
        }
        examUserPlanlDTO.setBaseTotalPoints(totalPoints);
        examUserPlanlDTO.setIsMark(true);
        examUserPlanModelManager.updateExamUserPlanModel(examUserPlanlDTO);
        return true;
    }

    /**
     * 重新发起考试
     */
    @Override
    public Boolean afreshExamPlan(@RequestBody ExamUserAfreshPlanQuery query) {
        List<ExamUserPlanModelDTO> list = query.getAfreshExamUserList();
        if (CollectionUtils.isEmpty(list)) {
            log.error("暂无补考人员");
            throw new BizException(ErrorCodeEnum.NO_DATA.getErrorCode(), "暂无补考人员");
        }
        //删除补考人员上次考试信息
        for (ExamUserPlanModelDTO dto : list) {
            dto.setModifyUserId(query.getModifyUserId());
            dto.setModifyUserName(query.getModifyUserName());
        }
        examUserPlanModelManager.deleteBatchByCondition(list);

        //重新发起补考
        ExamPlanModelDTO planModelDTO;
        for (ExamUserPlanModelDTO dto : list) {
            planModelDTO = examPlanModelManager.getExamPlanModelById(dto.getExamPlanId());
            dto.setExamUserPlanModelId(idGenerator.nextId());
            dto.setCreateUserId(query.getModifyUserId());
            dto.setCreateUserName(query.getModifyUserName());
            dto.setEndDate(planModelDTO.getEndDate());
            dto.setExamDuration(planModelDTO.getExamDuration());
            dto.setExamId(planModelDTO.getExamId());
            dto.setExamPlanId(planModelDTO.getExamPlanModelId());
            dto.setModifyUserId(query.getModifyUserId());
            dto.setModifyUserName(query.getModifyUserName());
            dto.setPassPoints(planModelDTO.getPassPoints());
            dto.setStartDate(planModelDTO.getStartDate());
            dto.setTotalPoints(planModelDTO.getTotalPoints());
        }
        examUserPlanModelManager.insertBatchExamUserPlanModelForAfresh(list);

        return true;
    }


}
