package com.medical.dtms.service.serviceimpl.train;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.medical.dtms.common.constants.Constants;
import com.medical.dtms.common.eception.BizException;
import com.medical.dtms.common.enumeration.ErrorCodeEnum;
import com.medical.dtms.common.model.dept.QMSUserInDeptModel;
import com.medical.dtms.common.model.exam.ExamExcelModel;
import com.medical.dtms.common.model.exam.ExamTotalModel;
import com.medical.dtms.common.model.exam.query.ExamSubmintQuestionQuery;
import com.medical.dtms.common.model.exam.query.ExamSubmitAnswerQuery;
import com.medical.dtms.common.model.question.QuestionItemModel;
import com.medical.dtms.common.model.train.*;
import com.medical.dtms.common.model.train.query.TrainSubmitAnswerQuery;
import com.medical.dtms.common.util.BeanConvertUtils;
import com.medical.dtms.common.util.DateUtils;
import com.medical.dtms.common.util.IdGenerator;
import com.medical.dtms.dto.exam.ExamUserAnswerModelDTO;
import com.medical.dtms.dto.exam.ExamUserPlanModelDTO;
import com.medical.dtms.dto.exam.query.ExamUserAnswerModelQuery;
import com.medical.dtms.dto.question.DtmsQuestionsDTO;
import com.medical.dtms.dto.train.TrainConfigDTO;
import com.medical.dtms.dto.train.TrainQuestionProcessDTO;
import com.medical.dtms.dto.train.TrainUserDTO;
import com.medical.dtms.dto.train.query.TrainUserQuery;
import com.medical.dtms.dto.user.query.QMSUserInDeptQuery;
import com.medical.dtms.feignservice.exam.ExamService;
import com.medical.dtms.feignservice.train.TrainUserService;
import com.medical.dtms.service.manager.exam.ExamUserAnswerModelManager;
import com.medical.dtms.service.manager.item.QMSItemDetailsManager;
import com.medical.dtms.service.manager.question.DtmsQuestionManager;
import com.medical.dtms.service.manager.train.TrainConfigManager;
import com.medical.dtms.service.manager.train.TrainFilesManager;
import com.medical.dtms.service.manager.train.TrainQuestionProcessManager;
import com.medical.dtms.service.manager.train.TrainUserManager;
import com.medical.dtms.service.manager.user.QMSUserInDeptManager;
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
 * @version： TrainUserServiceImpl.java v 1.0, 2019年08月20日 17:40  Exp$
 * @Description 培训用户管理
 **/
@RestController
@Slf4j
public class TrainUserServiceImpl implements TrainUserService {

    @Autowired
    private TrainUserManager trainUserManager;
    @Autowired
    private IdGenerator idGenerator;
    @Autowired
    private QMSUserInDeptManager userInDeptManager;
    @Autowired
    private TrainFilesManager trainFilesManager;
    @Autowired
    private QMSItemDetailsManager detailsManager;
    @Autowired
    private ExamUserAnswerModelManager answerModelManager;
    @Autowired
    private DtmsQuestionManager questionManager;
    @Autowired
    private TrainQuestionProcessManager trainQuestionProcessManager;
    @Autowired
    private TrainConfigManager configManager;

    /**
     * @param [query]
     * @return com.github.pagehelper.PageInfo<com.medical.dtms.model.train.TrainUserModel>
     * @description 培训统计 - 分页展示用户列表
     **/
    @Override
    public PageInfo<TrainUserModel> pageListTrainUser(@RequestBody TrainUserQuery query) {
        // 处理部门
        List<QMSUserInDeptModel> deptModels = new ArrayList<>();
        if (null != query.getDeptId()) {
            // 根据人员id 查询部门名称
            QMSUserInDeptQuery deptQuery = new QMSUserInDeptQuery();
            deptQuery.setDeptId(query.getDeptId());
            deptQuery.setUserOrDept(Constants.USER);
            deptModels = userInDeptManager.listDeptByUserIdsAndDept(deptQuery);
            if (CollectionUtils.isEmpty(deptModels)) {
                return new PageInfo<>(new ArrayList<>());
            }
            // 获取人员id
            List<Long> userIds = deptModels.stream().map(QMSUserInDeptModel::getUserId).distinct().collect(Collectors.toList());
            query.setUserIds(userIds);
        }

        PageHelper.startPage(query.getPageNo(), query.getPageSize());
        List<TrainUserModel> list = trainUserManager.pageListTrainUser(query);
        if (CollectionUtils.isEmpty(list)) {
            return new PageInfo<>(new ArrayList<>());
        }

        // 判断是否及格
        for (TrainUserModel model : list) {
            if (StringUtils.isBlank(model.getPointStr())) {
                model.setPointStr(String.valueOf(0));
            }
            if (null == model.getPassPoint()) {
                model.setPassPoint(0);
            }
            if (Integer.parseInt(model.getPointStr()) >= model.getPassPoint()) {
                model.setPass(true);
            } else {
                model.setPass(false);
            }

            // 部门名称赋值
            if (CollectionUtils.isEmpty(deptModels)) {
                List<QMSUserInDeptModel> modelList = deptModels.stream().filter(qmsUserInDeptModel -> qmsUserInDeptModel.getUserId().equals(model.getUserId())).collect(Collectors.toList());
                model.setDeptName(CollectionUtils.isEmpty(modelList) == true ? null : StringUtils.isBlank(modelList.get(0).getDeptName()) == true ? null : modelList.get(0).getDeptName());
            }
        }

        return new PageInfo<>(list);
    }

    /**
     * @param [query]
     * @return java.util.List<com.medical.dtms.common.model.file.ExportModel>
     * @description 培训统计 - 导出
     **/
    @Override
    public List<TrainExcelModel> exportTrain(@RequestBody TrainUserQuery query) {
        // 不分页，但是需要根据条件查询，然后将结果导出
        List<TrainUserModel> models = trainUserManager.exportTrain(query);
        if (CollectionUtils.isEmpty(models)) {
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "无满足条件数据,请更换查询条件");
        }

        // 获取用户 id 集合
        List<Long> userIds = models.stream().map(TrainUserModel::getUserId).distinct().collect(Collectors.toList());
        QMSUserInDeptQuery deptQuery = new QMSUserInDeptQuery();
        deptQuery.setUserIds(userIds);
        List<QMSUserInDeptModel> deptModels = userInDeptManager.listDeptByUserIdsAndDept(deptQuery);
        // 获取培训id 集合
        List<Long> trainIds = models.stream().map(TrainUserModel::getTrainId).distinct().collect(Collectors.toList());
        // 查询培训关联的文件id、名称
        List<SimpleTrainFileModel> modelList = trainFilesManager.listFileInfoByTrainIds(trainIds);

        if (CollectionUtils.isNotEmpty(modelList)) {
            Map<Long, List<SimpleTrainFileModel>> map = modelList.stream().collect(Collectors.groupingBy(SimpleTrainFileModel::getTrainId));
            Map<Long, List<QMSUserInDeptModel>> deptMap = deptModels.stream().collect(Collectors.groupingBy(QMSUserInDeptModel::getUserId));

            for (TrainUserModel aDo : models) {
                aDo.setFileName(CollectionUtils.isEmpty(map.get(aDo.getTrainId())) == true ? null : map.get(aDo.getTrainId()).get(0) == null ? null : StringUtils.isBlank(map.get(aDo.getTrainId()).get(0).getFileName()) == true ? null : map.get(aDo.getTrainId()).get(0).getFileName());
                if (StringUtils.isBlank(aDo.getPointStr())) {
                    aDo.setPointStr(String.valueOf(0));
                }
                if (null == aDo.getTotalPoints() || 0 == aDo.getTotalPoints()) {
                    aDo.setTotalPoints(0);
                }

                aDo.setPoint(aDo.getPointStr() + "/" + aDo.getTotalPoints());
                aDo.setFinishTimeStr(DateUtils.format(aDo.getFinishTime(), DateUtils.YYYY_MM_DD_HH_mm_ss));
                if (aDo.getTotalPoints() < Integer.valueOf(aDo.getPointStr())) {
                    aDo.setPassStr("不合格");
                }
                if (aDo.getTotalPoints() >= Integer.valueOf(aDo.getPointStr())) {
                    aDo.setPassStr("合格");
                }

                // 部门名称
                aDo.setDeptName(CollectionUtils.isEmpty(deptMap.get(aDo.getUserId())) == true ? null : deptMap.get(aDo.getUserId()).get(0) == null ? null : StringUtils.isBlank(deptMap.get(aDo.getUserId()).get(0).getDeptName()) == true ? null : deptMap.get(aDo.getUserId()).get(0).getDeptName());
            }
        }

        return BeanConvertUtils.convertList(models, TrainExcelModel.class);
    }


    /**
     * @param [query]
     * @return com.github.pagehelper.PageInfo<com.medical.dtms.model.train.TrainUserModel>
     * @description 培训及时率 - 列表查询功能
     **/
    @Override
    public PageInfo<TrainUserModel> listTrainTimely(@RequestBody TrainUserQuery query) {
        // 1.  获取部信息（部门id、name）、用户id
        QMSUserInDeptQuery deptQuery = new QMSUserInDeptQuery();
        deptQuery.setUserOrDept(Constants.DEPT);
        List<QMSUserInDeptModel> deptModel = userInDeptManager.listDeptByUserIdsAndDept(deptQuery);
        if (CollectionUtils.isEmpty(deptModel)) {
            return new PageInfo<>(new ArrayList<>());
        }


        List<Long> userId = deptModel.stream().map(QMSUserInDeptModel::getUserId).collect(Collectors.toList());
        // 2. 根据用户id 去查询 tb_dtms_train_user（培训人员）表 查询这些用户的培训信息
        query.setUserIds(userId);
        List<TrainUserModel> dos = trainUserManager.listTrainTimely(query);
        if (CollectionUtils.isEmpty(dos)) {
            return new PageInfo<>(new ArrayList<>());
        }

        // 根据部门分组
        Map<Long, List<QMSUserInDeptModel>> map = deptModel.stream().collect(Collectors.groupingBy(QMSUserInDeptModel::getDeptId));
        for (TrainUserModel user : dos) {
            for (Long deptId : map.keySet()) {
                // 获取本部门下的人员数据
                List<QMSUserInDeptModel> models = map.get(deptId);
                if (CollectionUtils.isNotEmpty(models)) {
                    user.setDeptName(StringUtils.isBlank(models.get(0).getDeptName()) == true ? null : models.get(0).getDeptName());
                    // 本部门参与培训的人集合
                    List<QMSUserInDeptModel> deptModels = models.stream().filter(qmsUserInDeptModel -> qmsUserInDeptModel.getUserId().equals(user.getUserId())).distinct().collect(Collectors.toList());
                    user.setTrainTotal(deptModels.size());
                    if (user.getFinishTime().before(user.getEndDate())) {
                        // 统计及时人数
                        List<QMSUserInDeptModel> models1 = models.stream().filter(qmsUserInDeptModel -> qmsUserInDeptModel.getUserId().equals(user.getUserId())).distinct().collect(Collectors.toList());
                        user.setTimelyTotal(models1.size());
                    }
                }
            }
        }

        return new PageInfo<>(dos);
    }

    /**
     * @param []
     * @return java.util.List<com.medical.dtms.model.train.TrainUserModel>
     * @description 我的培训-查看
     **/
    @Override
    public MyTrainTestModel viewMyTrain(@RequestBody TrainUserQuery query) {
//        PageHelper.startPage(query.getPageNo(), query.getPageSize());
        List<TrainUserModel> dos = trainUserManager.viewMyTrain(query);
        if (CollectionUtils.isEmpty(dos)) {
            return null;
        }

        // 处理 试题类型 id
        // 获取所有的试题类型 id
        List<Long> questionsTypeIds = dos.stream().map(TrainUserModel::getQuestionsTypeId).distinct().collect(Collectors.toList());


        //根据试题类型id得到试题类型name
        query.setQuestionsTypeIds(questionsTypeIds);
        List<QuestionItemModel> ados = detailsManager.queryDetailsList(questionsTypeIds);
        if (CollectionUtils.isEmpty(ados)) {
            return null;
        }

        return null;
    }


    /**
     * @param []beginTrainExam
     * @return java.util.List<com.medical.dtms.model.train.TrainUserModel>
     * @description 我的培训-开始考试
     **/

    /**
     *  思路：
     *  1. 需求：一次考试，只要还在规定的时间内（即 当前时间小于 考试结束时间），用户都可以点进来，然后提交
     *  2. 实现：
     *  （1） 首先确定用户是否是第一次参加本次考试：根据 用户 id、考试id 查询 用户培训答题过程表，如果没有记录，则说明是第一次参加考试，如果有，则说明不是第一次参加考试
     *  （2） 用户在第一次点击开始考试时，向用户培训答题过程表（tb_dtms_train_question_process） 添加一条记录
     *  （3） 第二次及以后点进来，回显用户已经提交的答案等信息
     * */
    @Override
    public MyTrainTestModel beginTrainExam(@RequestBody TrainUserDTO trainUserDTO) {
        // 查询 用户 与 培训是否关联，即 该用户是否有该次培训任务
        TrainSubmitAnswerQuery submitAnswerQuery = new TrainSubmitAnswerQuery();
        submitAnswerQuery.setTrainId(trainUserDTO.getTrainId());
        submitAnswerQuery.setTrainUserId(trainUserDTO.getTrainUserId());
        TrainUserDTO userDTO = trainUserManager.getTrainUserByPrimaryKey(submitAnswerQuery);
        if (null == trainUserDTO) {
            log.error("无本次培训");
            throw new BizException(ErrorCodeEnum.NO_DATA.getErrorCode(), "无本次培训");
        }

        // 校验本次考试是否存在
        TrainConfigDTO trainConfigDTO = configManager.selectByPrimaryKey(trainUserDTO.getTrainId());
        if (null == trainConfigDTO){
            log.error("本场考试不存在");
            throw new BizException(ErrorCodeEnum.NO_DATA.getErrorCode(), "本场考试不存在");
        }

        if (null != userDTO.getIsFinishQuestions() && userDTO.getIsFinishQuestions()) {
            log.error("考试已结束,不能重复考试");
            throw new BizException(ErrorCodeEnum.NO_DATA.getErrorCode(), "考试已结束,不能重复考试");
        }

        // 校验用户是否第一次考试 TODO
//        TrainQuestionProcessDTO processDTO = trainQuestionProcessManager.checkFirstExamOrNot(submitAnswerQuery);
//        if (null == processDTO){
//            // 说明用户是第一次考试，此时将考试、试卷信息添加到 用户培训答题过程 表
//
//        }

        // 查询考试信息  TODO
        MyTrainTestModel testModel = trainUserManager.listExamIds(userDTO.getBizId());
        if (null == testModel) {
            return null;
        }

        // 查询每一题得分和用户答案
        ExamUserAnswerModelQuery modelQuery = new ExamUserAnswerModelQuery();
        modelQuery.setExamId(testModel.getExamId());
        modelQuery.setUserId(trainUserDTO.getUserId());
        List<UserExamInfoModel> dtos = answerModelManager.listExamInfo(modelQuery);
        if (CollectionUtils.isNotEmpty(dtos)) {
            List<String> points = dtos.stream().map(UserExamInfoModel::getAnswerPoints).collect(Collectors.toList());
            Integer point = 0;
            for (String s : points) {
                point += Integer.valueOf(s);
            }
            testModel.setTotalPoints(point);
            testModel.setModels(dtos);
        } else {
            testModel.setModels(new ArrayList<>());
        }

        return testModel;
    }


    /**
     * 提交试卷
     *
     * com.medical.dtms.common.model.train.query.TrainSubmitAnswerQuery
     */

    /**
     *  思路：
     *   1.需求：规定时间内参加考试都可以提交答案
     *   2.实现：
     *   （1）找到可以提交的试卷
     *   （2）修改答案
     *   （3）保存至当前表
     *
     *
     *
     *
     *
     * */
    @Override
    @Transactional
    public Boolean submitTrainAnswer(@RequestBody TrainSubmitAnswerQuery query) {
        // 查询 用户 与 培训是否关联，即 该用户是否有该次培训任务
        TrainUserDTO trainUserDTO = trainUserManager.getTrainUserByPrimaryKey(query);
        if (null == trainUserDTO) {
            log.error("无本次培训");
            throw new BizException(ErrorCodeEnum.NO_DATA.getErrorCode(), "无本次培训");
        }
        // 校验本次考试是否存在
        TrainConfigDTO trainConfigDTO = configManager.selectByPrimaryKey(query.getTrainId());
        if (null == trainConfigDTO){
            log.error("本场考试不存在");
            throw new BizException(ErrorCodeEnum.NO_DATA.getErrorCode(), "本场考试不存在");
        }

        if (null != trainUserDTO.getIsFinish() && trainUserDTO.getIsFinish()) {
            log.error("该试卷已提交,勿重复提交");
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "该试卷已提交,勿重复提交");
        }

        //创建要修改的试卷内容(更新考试结束时间,考试得分,考试结束时间)
        TrainUserDTO trainUsersDTO = new TrainUserDTO();
        trainUsersDTO.setModifierId(query.getModifierId());
        trainUsersDTO.setModifier(query.getModifier());
        trainUsersDTO.setTrainUserId(query.getTrainUserId());
        //准备提交更新的题目信息
        List<TrainQuestionProcessDTO> dtos = new ArrayList<>();
        List<ExamSubmintQuestionQuery> submitQuestions = query.getQuestions();
        int total_points = 0;
        if (CollectionUtils.isNotEmpty(submitQuestions)) {
            TrainQuestionProcessDTO dto;
            DtmsQuestionsDTO dtmsQuestionsDTO;
            for (ExamSubmintQuestionQuery submitQuestion : submitQuestions) {
                dto = new TrainQuestionProcessDTO();
                dto.setModifier(query.getModifier());
                dto.setModifierId(query.getModifierId());
                dto.setUserId(query.getUserId());
                dto.setExamId(query.getExamId());
                dto.setTrainId(query.getTrainId());
                dto.setTrainUserId(query.getTrainUserId());
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
                trainQuestionProcessManager.updateBatchTrainQuestionProcess(dtos);
            }
        }
        trainUserDTO.setBaseTotalPoints(total_points);
        trainUserDTO.setIsFinish(true);
        trainUserManager.addTrainUser(trainUserDTO);
        return true;
    }

    /**
     * @param []
     * @return java.util.List<com.medical.dtms.model.train.TrainUserQueryModel>
     * @description 我的培训-查询培训试卷
     **/
    @Override
    public PageInfo<TrainUserQueryModel> listTrainExam(@RequestBody TrainUserQuery query) {
        PageHelper.startPage(query.getPageNo(), query.getPageSize());
        List<TrainUserQueryModel> dos = trainUserManager.listTrainExam(query);
        if (CollectionUtils.isEmpty(dos)) {
            return new PageInfo<>(new ArrayList<>());
        }
        return new PageInfo<>(dos);
    }

    /**
     * @param [query]
     * @return com.github.pagehelper.PageInfo<com.medical.dtms.model.train.TrainUserModel>
     * @description 考试统计 - 查询查看
     **/
    @Override
    public PageInfo<ExamTotalModel> pageListExamTotal(@RequestBody TrainUserQuery query) {
        // 处理部门
        List<QMSUserInDeptModel> deptModels = new ArrayList<>();
        if (null != query.getDeptId()) {
            // 根据人员id 查询部门名称
            QMSUserInDeptQuery deptQuery = new QMSUserInDeptQuery();
            deptQuery.setDeptId(query.getDeptId());
            deptQuery.setUserOrDept(Constants.USER);
            deptModels = userInDeptManager.listDeptByUserIdsAndDept(deptQuery);
            if (CollectionUtils.isEmpty(deptModels)) {
                return new PageInfo<>(new ArrayList<>());
            }
            // 获取人员id
            List<Long> userIds = deptModels.stream().map(QMSUserInDeptModel::getUserId).distinct().collect(Collectors.toList());
            query.setUserIds(userIds);
        }

        PageHelper.startPage(query.getPageNo(), query.getPageSize());
        List<ExamTotalModel> list = trainUserManager.pageListExamTotal(query);
        if (CollectionUtils.isEmpty(list)) {
            return new PageInfo<>(new ArrayList<>());
        }

        // 判断是否及格
        for (ExamTotalModel model : list) {
            if (StringUtils.isBlank(model.getPointStr())) {
                model.setPointStr(String.valueOf(0));
            }
            if (null == model.getPassPoint()) {
                model.setPassPoint(0);
            }
            if (Integer.parseInt(model.getPointStr()) >= model.getPassPoint()) {
                model.setPass(true);
            } else {
                model.setPass(false);
            }

            // 部门名称赋值
            if (CollectionUtils.isEmpty(deptModels)) {
                List<QMSUserInDeptModel> modelList = deptModels.stream().filter(qmsUserInDeptModel -> qmsUserInDeptModel.getUserId().equals(model.getUserId())).collect(Collectors.toList());
                model.setDeptName(CollectionUtils.isEmpty(modelList) == true ? null : StringUtils.isBlank(modelList.get(0).getDeptName()) == true ? null : modelList.get(0).getDeptName());
            }
        }

        return new PageInfo<>(list);
    }


    /**
     * @param [query]
     * @return java.util.List<com.medical.dtms.common.model.file.ExportModel>
     * @description 考试统计 - 导出
     **/
    @Override
    public List<ExamExcelModel> exportExam(@RequestBody TrainUserQuery query) {
        // 不分页，但是需要根据条件查询，然后将结果导出
        List<ExamTotalModel> models = trainUserManager.exportExam(query);
        if (CollectionUtils.isEmpty(models)) {
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "无满足条件数据,请更换查询条件");
        }

        // 获取用户 id 集合
        List<Long> userIds = models.stream().map(ExamTotalModel::getUserId).distinct().collect(Collectors.toList());
        QMSUserInDeptQuery deptQuery = new QMSUserInDeptQuery();
        deptQuery.setUserIds(userIds);
        List<QMSUserInDeptModel> deptModels = userInDeptManager.listDeptByUserIdsAndDept(deptQuery);
        // 获取培训id 集合
        List<Long> trainIds = models.stream().map(ExamTotalModel::getTrainId).distinct().collect(Collectors.toList());
        // 查询培训关联的文件id、名称
        List<SimpleTrainFileModel> modelList = trainFilesManager.listFileInfoByTrainIds(trainIds);

        if (CollectionUtils.isNotEmpty(modelList)) {
            Map<Long, List<SimpleTrainFileModel>> map = modelList.stream().collect(Collectors.groupingBy(SimpleTrainFileModel::getTrainId));
            Map<Long, List<QMSUserInDeptModel>> deptMap = deptModels.stream().collect(Collectors.groupingBy(QMSUserInDeptModel::getUserId));
            for (ExamTotalModel aDo : models) {

                if (StringUtils.isBlank(aDo.getPointStr())) {
                    aDo.setPointStr(String.valueOf(0));
                }
                if (null == aDo.getTotalPoints() || 0 == aDo.getTotalPoints()) {
                    aDo.setTotalPoints(0);
                }
                if (null == aDo.getPassPoint() || 0 == aDo.getPassPoint()) {
                    aDo.setPassPoint(0);
                }
                aDo.setPointStr(aDo.getPointStr());
                aDo.setPassTotal(aDo.getPassPoint() + "/" + aDo.getTotalPoints());
                aDo.setFinishTimeStr(DateUtils.format(aDo.getFinishTime(), DateUtils.YYYY_MM_DD_HH_mm_ss));
                if (aDo.getTotalPoints() < Integer.valueOf(aDo.getPointStr())) {
                    aDo.setPassStr("不合格");
                }
                if (aDo.getTotalPoints() >= Integer.valueOf(aDo.getPointStr())) {
                    aDo.setPassStr("合格");
                }

                // 部门名称
                aDo.setDeptName(CollectionUtils.isEmpty(deptMap.get(aDo.getUserId())) == true ? null : deptMap.get(aDo.getUserId()).get(0) == null ? null : StringUtils.isBlank(deptMap.get(aDo.getUserId()).get(0).getDeptName()) == true ? null : deptMap.get(aDo.getUserId()).get(0).getDeptName());
            }
        }
        return BeanConvertUtils.convertList(models, ExamExcelModel.class);
    }
}
