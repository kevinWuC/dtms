package com.medical.dtms.service.serviceimpl.train;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.medical.dtms.common.constants.Constants;
import com.medical.dtms.common.eception.BizException;
import com.medical.dtms.common.enumeration.ErrorCodeEnum;
import com.medical.dtms.common.model.dept.QMSUserInDeptModel;
import com.medical.dtms.common.model.exam.ExamExcelModel;
import com.medical.dtms.common.model.exam.ExamQuestionsTypeModel;
import com.medical.dtms.common.model.question.DtmsQuestionsModel;
import com.medical.dtms.common.model.question.QuestionItemModel;
import com.medical.dtms.common.model.train.*;
import com.medical.dtms.common.util.BeanConvertUtils;
import com.medical.dtms.common.util.DateUtils;
import com.medical.dtms.common.util.IdGenerator;
import com.medical.dtms.dto.exam.ExamUserAnswerModelDTO;
import com.medical.dtms.dto.exam.ExamUserPlanModelDTO;
import com.medical.dtms.dto.exam.query.ExamUserAnswerModelQuery;
import com.medical.dtms.dto.question.DtmsQuestionsDTO;
import com.medical.dtms.dto.question.QuestionQuery;
import com.medical.dtms.dto.train.TrainConfigDTO;
import com.medical.dtms.dto.train.TrainUserDTO;
import com.medical.dtms.dto.train.query.TrainUserQuery;
import com.medical.dtms.dto.user.query.QMSUserInDeptQuery;
import com.medical.dtms.feignservice.exam.ExamService;
import com.medical.dtms.feignservice.train.TrainUserService;
import com.medical.dtms.service.manager.exam.ExamQuestionsTypeManager;
import com.medical.dtms.service.manager.exam.ExamUserAnswerModelManager;
import com.medical.dtms.service.manager.exam.ExamUserPlanModelManager;
import com.medical.dtms.service.manager.item.QMSItemDetailsManager;
import com.medical.dtms.service.manager.question.DtmsQuestionManager;
import com.medical.dtms.service.manager.train.TrainConfigManager;
import com.medical.dtms.service.manager.train.TrainFilesManager;
import com.medical.dtms.service.manager.train.TrainUserManager;
import com.medical.dtms.service.manager.user.QMSUserInDeptManager;
import lombok.extern.slf4j.Slf4j;
import netscape.security.Privilege;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    private ExamService examService;
    @Autowired
    private ExamUserAnswerModelManager answerModelManager;


    /**
     * @param [query]
     * @return com.github.pagehelper.PageInfo<com.medical.dtms.model.train.TrainUserModel>
     * @description 培训统计 - 分页展示用户列表
     **/
    @Override
    public PageInfo<TrainUserModel> pageListTrainUser(@RequestBody TrainUserQuery query) {
        // 处理部门
        List<QMSUserInDeptModel> deptModels = new ArrayList<>();
        if (null != query.getDepId()) {
            // 根据人员id 查询部门名称
            QMSUserInDeptQuery deptQuery = new QMSUserInDeptQuery();
            deptQuery.setDeptId(query.getDepId());
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
    public PageInfo<TrainUserModel> viewMyTrain(@RequestBody TrainUserQuery query) {
        PageHelper.startPage(query.getPageNo(), query.getPageSize());
        List<TrainUserModel> dos = trainUserManager.viewMyTrain(query);
        if (CollectionUtils.isEmpty(dos)) {
            return new PageInfo<>(new ArrayList<>());
        }

        // 处理 试题类型 id
        // 获取所有的试题类型 id
        List<Long> questionsTypeIds = dos.stream().map(TrainUserModel::getQuestionsTypeId).distinct().collect(Collectors.toList());


        //根据试题类型id得到试题类型name
        query.setQuestionsTypeIds(questionsTypeIds);
        List<QuestionItemModel> ados = detailsManager.queryDetailsList(questionsTypeIds);
        if (CollectionUtils.isEmpty(ados)) {
            return new PageInfo<>(new ArrayList<>());
        }


        return new PageInfo<>(dos);
    }


    /**
     * @param []beginTrainExam
     * @return java.util.List<com.medical.dtms.model.train.TrainUserModel>
     * @description 我的培训-开始考试
     **/
    @Override
    public MyTrainTestModel beginTrainExam(@RequestBody TrainUserDTO trainUserDTO) {
        TrainUserDTO userDTO = trainUserManager.getTrainUserByPrimaryKey(trainUserDTO.getBizId());
        if (null == userDTO) {
            log.error("本场考试不存在");
            throw new BizException(ErrorCodeEnum.NO_DATA.getErrorCode(), "本场考试不存在");
        }

        if (null != userDTO.getIsFinishQuestions() && userDTO.getIsFinishQuestions()) {
            log.error("考试已结束,不能重复考试");
            throw new BizException(ErrorCodeEnum.NO_DATA.getErrorCode(), "考试已结束,不能重复考试");
        }

        // 获取 exam_id
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
        // 查询培训配置信息和所用考卷
//        model = configManager.queryTrainInfo(userDTO.getTrainId());
//
//        model.setUserId(trainUserDTO.getUserId());
//        model.setDspName(trainUserDTO.getDspName());
//        model.setExamId(trainUserDTO.getExamId());
//        model.setExamDuration(trainUserDTO.getExamDuration());
//        model.setPassPoint(trainUserDTO.getPassPoint());
//        model.setTotalPoints(trainUserDTO.getTotalPoints());
//        //2.查询试题类型
//        ExamUserPlanModelDTO examUserPlanModelDTO = new ExamUserPlanModelDTO();
//        List<ExamQuestionsTypeModel> typeModels = examQuestionsTypeManager.listQuestionTypeByExamId(trainUserDTO.getExamId());
//        if (CollectionUtils.isNotEmpty(typeModels)) {
//            //查询试题试题
//            QuestionQuery query = new QuestionQuery();
//            for (ExamQuestionsTypeModel typeModel : typeModels) {
//                if (StringUtils.isNotBlank(typeModel.getExamQuestionString())) {
//                    query.setQuestionIds(Arrays.asList(typeModel.getExamQuestionString().split(",")));
//                    //查询试题
//                    List<DtmsQuestionsDTO> listQuestionsForPreview = dtmsQuestionManager
//                            .listQuestionsForPreview(query);
//                    typeModel.setQuestionForPreview(BeanConvertUtils
//                            .convertList(listQuestionsForPreview, DtmsQuestionsModel.class));
//                }
//            }
//            model.setQuestionsTypeId(trainUserDTO.getQuestionsTypeId());
//            //新增提交的试卷....
//            ExamUserAnswerModelDTO userAnswerModelDTO = new ExamUserAnswerModelDTO();
//            userAnswerModelDTO.setExamId(examUserPlanModelDTO.getExamId());
//            userAnswerModelDTO.setExamPlanId(examUserPlanModelDTO.getExamPlanId());
//            userAnswerModelDTO.setExamUserPlanId(examUserPlanModelDTO.getExamUserPlanModelId());
//            userAnswerModelDTO.setUserId(examUserPlanModelDTO.getUserId());
//            //添加试题
//            List<ExamUserAnswerModelDTO> dtos = new ArrayList<>();
//            for (ExamQuestionsTypeModel typeModel : typeModels) {
//                if (StringUtils.isNotBlank(typeModel.getExamQuestionString())) {
//                    query.setQuestionIds(Arrays.asList(typeModel.getExamQuestionString().split(",")));
//                    //查询试题
//                    List<DtmsQuestionsDTO> listQuestionsForPreview = dtmsQuestionManager
//                            .listQuestionsForPreview(query);
//                    ExamUserAnswerModelDTO dto;
//                    for (DtmsQuestionsDTO question : listQuestionsForPreview) {
//                        dto = userAnswerModelDTO;
//                        dto.setBizId(idGenerator.nextId());
//                        dto.setQuestionsId(question.getBizId());
//                        dto.setAnswer(question.getAnswer());
//                        dtos.add(dto);
//                    }
//                }
//            }
//            userAnswerModelManager.insertBatchExamUserAnswerModel(dtos);
//        }
//        //更新考试开始时间
//        ExamUserPlanModelDTO startExamUserPlan = new ExamUserPlanModelDTO();
//        startExamUserPlan.setExamUserPlanModelId(examUserPlanModelDTO.getExamUserPlanModelId());
//        startExamUserPlan.setModifyUserName(examUserPlanModelDTO.getModifyUserName());
//        startExamUserPlan.setModifyUserId(examUserPlanModelDTO.getModifyUserId());
//        startExamUserPlan.setStartDate(new Date());
//        examUserPlanModelManager.updateExamUserPlanModel(startExamUserPlan);
//        return model;
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
     * 确认交卷
     *
     * @param processDTO
     * @return
     */
    @Override
    public Boolean addTrainUser(@RequestBody TrainUserDTO dto) {

        try {
            dto.setBizId(idGenerator.nextId());
            trainUserManager.addTrainUser(dto);
        } catch (Exception e) {
            log.error("确认交卷失败", e);
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
        }

        return true;
    }


    /**
     * @param [query]
     * @return com.github.pagehelper.PageInfo<com.medical.dtms.model.train.TrainUserModel>
     * @description 考试统计 - 查询查看
     **/
    @Override
    public PageInfo<TrainUserModel> pageListExamTotal(@RequestBody TrainUserQuery query) {
        // 处理部门
        List<QMSUserInDeptModel> deptModels = new ArrayList<>();
        if (null != query.getDepId()) {
            // 根据人员id 查询部门名称
            QMSUserInDeptQuery deptQuery = new QMSUserInDeptQuery();
            deptQuery.setDeptId(query.getDepId());
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
        List<TrainUserModel> list = trainUserManager.pageListExamTotal(query);
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
     * @description 考试统计 - 导出
     **/
    @Override
    public List<ExamExcelModel> exportExam(@RequestBody TrainUserQuery query) {
        // 不分页，但是需要根据条件查询，然后将结果导出
        List<TrainUserModel> models = trainUserManager.exportExam(query);
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
