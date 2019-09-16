package com.medical.dtms.service.serviceimpl.exam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.medical.dtms.common.eception.BizException;
import com.medical.dtms.common.enumeration.ErrorCodeEnum;
import com.medical.dtms.common.model.exam.ExamDetailModel;
import com.medical.dtms.common.model.exam.ExamPlanModel;
import com.medical.dtms.common.model.exam.ExamQuestionsTypeModel;
import com.medical.dtms.common.model.question.DtmsQuestionsModel;
import com.medical.dtms.common.util.BeanConvertUtils;
import com.medical.dtms.common.util.IdGenerator;
import com.medical.dtms.dto.exam.ExamModelDTO;
import com.medical.dtms.dto.exam.ExamQuestionsDTO;
import com.medical.dtms.dto.exam.ExamQuestionsTypeDTO;
import com.medical.dtms.dto.exam.query.ExamQuery;
import com.medical.dtms.dto.question.DtmsQuestionsDTO;
import com.medical.dtms.dto.question.QuestionQuery;
import com.medical.dtms.feignservice.exam.ExamService;
import com.medical.dtms.service.manager.exam.ExamModelManager;
import com.medical.dtms.service.manager.exam.ExamQuestionsManager;
import com.medical.dtms.service.manager.exam.ExamQuestionsTypeManager;
import com.medical.dtms.service.manager.question.DtmsQuestionManager;

import lombok.extern.slf4j.Slf4j;

/**
 * 试卷管理
 * 
 * @author shenqifeng
 * @version $Id: ExamServiceImpl.java, v 0.1 2019年9月7日 下午9:03:56 shenqifeng Exp $
 */
@RestController
@Slf4j
public class ExamServiceImpl implements ExamService {
    @Autowired
    private ExamModelManager         examModelManager;
    @Autowired
    private ExamQuestionsManager     examQuestionsManager;
    @Autowired
    private ExamQuestionsTypeManager examQuestionsTypeManager;
    @Autowired
    private IdGenerator              idGenerator;

    @Autowired
    private DtmsQuestionManager dtmsQuestionManager;

    /**
     * 新增试卷
     * @see com.medical.dtms.feignservice.exam.ExamService#insertExam(com.medical.dtms.dto.exam.ExamModelDTO)
     */
    @Override
    @Transactional
    public Boolean insertExam(@RequestBody ExamModelDTO examModelDTO) {
        //1.新增试卷
        Long examId = idGenerator.nextId();
        examModelDTO.setExamId(examId);
        examModelManager.insertExam(examModelDTO);
        //2.新增试卷与试题类型关联表
        List<ExamQuestionsTypeDTO> examQuestionsTypes = examModelDTO.getExamQuestionsTypes();
        if (CollectionUtils.isEmpty(examQuestionsTypes)) {
            log.error("缺少试题");
            throw new BizException(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "缺少试题");
        }
        //试卷与试题关联
        List<ExamQuestionsDTO> examQuestionDTOs = new ArrayList<ExamQuestionsDTO>();
        ExamQuestionsDTO questionsDTO = null;
        Long examQuestionTypeId = null;
        for (ExamQuestionsTypeDTO examQuestionsTypeDTO : examQuestionsTypes) {
            examQuestionTypeId = idGenerator.nextId();
            examQuestionsTypeDTO.setExamId(examId);
            examQuestionsTypeDTO.setExamQuestionTypeId(examQuestionTypeId);
            examQuestionsTypeDTO.setCreateUserId(examModelDTO.getCreateUserId());
            examQuestionsTypeDTO.setCreateUserName(examModelDTO.getCreateUserName());

            //获取试卷与试题关联
            List<Long> examQuestions = examQuestionsTypeDTO.getExamQuestions();
            if (CollectionUtils.isEmpty(examQuestions)) {
                log.error("缺少试题");
                throw new BizException(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "缺少试题");
            }
            for (Long questionId : examQuestions) {
                questionsDTO = new ExamQuestionsDTO();
                questionsDTO.setCreateUserId(examModelDTO.getCreateUserId());
                questionsDTO.setCreateUserName(examModelDTO.getCreateUserName());
                questionsDTO.setExamId(examId);
                questionsDTO.setExamQuestionTypeId(examQuestionTypeId);
                questionsDTO.setExamQuestionId(idGenerator.nextId());
                questionsDTO.setQuestionsId(questionId);
                examQuestionDTOs.add(questionsDTO);
            }

        }
        examQuestionsTypeManager.insertBatchExamQuestionType(examQuestionsTypes);
        //3.新增试卷与试题关联表
        examQuestionsManager.insertBatchExamQuestion(examQuestionDTOs);
        return true;
    }

    /**
     * 修改试卷
     * @see com.medical.dtms.feignservice.exam.ExamService#insertExam(com.medical.dtms.dto.exam.ExamModelDTO)
     */
    @Override
    @Transactional
    public Boolean updateExam(@RequestBody ExamModelDTO examModelDTO) {
        //1.校验试卷是否使用中
        ExamDetailModel model = examModelManager.getExamByExamId(examModelDTO.getExamId());
        if (null == model) {
            log.error("试卷不存在  examId:{}", examModelDTO.getExamId());
            throw new BizException(ErrorCodeEnum.NO_DATA.getErrorCode(), "试卷不存在");
        }
        if (model.getIsUse()) {
            log.error("试卷使用中");
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "试卷使用中不可修改");
        }

        //1.修改试卷
        examModelManager.updateExam(examModelDTO);

        //2.先删除，再新增试卷与试题类型关联表
        List<ExamQuestionsTypeDTO> examQuestionsTypes = examModelDTO.getExamQuestionsTypes();
        if (CollectionUtils.isEmpty(examQuestionsTypes)) {
            log.error("缺少试题");
            throw new BizException(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "缺少试题");
        }
        //试卷与试题关联
        List<ExamQuestionsDTO> examQuestionDTOs = new ArrayList<ExamQuestionsDTO>();
        ExamQuestionsDTO questionsDTO = null;
        Long examQuestionTypeId = null;
        for (ExamQuestionsTypeDTO examQuestionsTypeDTO : examQuestionsTypes) {
            examQuestionTypeId = idGenerator.nextId();
            examQuestionsTypeDTO.setExamId(examModelDTO.getExamId());
            examQuestionsTypeDTO.setExamQuestionTypeId(examQuestionTypeId);
            examQuestionsTypeDTO.setCreateUserId(examModelDTO.getCreateUserId());
            examQuestionsTypeDTO.setCreateUserName(examModelDTO.getCreateUserName());

            //获取试卷与试题关联
            List<Long> examQuestions = examQuestionsTypeDTO.getExamQuestions();
            if (CollectionUtils.isEmpty(examQuestions)) {
                log.error("缺少试题");
                throw new BizException(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "缺少试题");
            }
            for (Long questionId : examQuestions) {
                questionsDTO = new ExamQuestionsDTO();
                questionsDTO.setCreateUserId(examModelDTO.getCreateUserId());
                questionsDTO.setCreateUserName(examModelDTO.getCreateUserName());
                questionsDTO.setExamId(examModelDTO.getExamId());
                questionsDTO.setExamQuestionTypeId(examQuestionTypeId);
                questionsDTO.setExamQuestionId(idGenerator.nextId());
                questionsDTO.setQuestionsId(questionId);
                examQuestionDTOs.add(questionsDTO);
            }

        }
        examQuestionsTypeManager.deleteByExamId(examModelDTO.getExamId());
        examQuestionsTypeManager.insertBatchExamQuestionType(examQuestionsTypes);
        //3.先删除   再新增试卷与试题关联表
        examQuestionsManager.deleteByExamId(examModelDTO.getExamId());
        examQuestionsManager.insertBatchExamQuestion(examQuestionDTOs);
        return true;
    }

    /**
     * 删除
     * @see com.medical.dtms.feignservice.exam.ExamService#deleteExam(java.lang.Long)
     */
    @Override
    @Transactional
    public Boolean deleteExam(@RequestParam("examId") Long examId) {
        //1.校验试卷是否使用中
        ExamDetailModel model = examModelManager.getExamByExamId(examId);
        if (null == model) {
            log.error("试卷不存在  examId:{}", examId);
            throw new BizException(ErrorCodeEnum.NO_DATA.getErrorCode(), "试卷不存在");
        }
        if (model.getIsUse()) {
            log.error("试卷使用中");
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "试卷使用中不可删除");
        }

        //1.删除试卷
        examModelManager.deleteByExamId(examId);
        //2.删除试题类型
        examQuestionsTypeManager.deleteByExamId(examId);
        //3.先删除  试题a
        examQuestionsManager.deleteByExamId(examId);
        return true;
    }

    /**
     * 分页查询
     * @see com.medical.dtms.feignservice.exam.ExamService#listExamByQuery(com.medical.dtms.dto.exam.query.ExamQuery)
     */
    @Override
    public PageInfo<ExamModelDTO> listExamByQuery(@RequestBody ExamQuery query) {
        PageHelper.startPage(query.getPageNo(), query.getPageSize());
        List<ExamModelDTO> list = examModelManager.listExamByQuery(query);
        PageInfo<ExamModelDTO> pageinfo = new PageInfo<>(list);
        return pageinfo;
    }

    /**
     * 详情查询
     * @see com.medical.dtms.feignservice.exam.ExamService#getExamByExamId(java.lang.Long)
     */
    @Override
    public ExamDetailModel getExamByExamId(@RequestParam("examId") Long examId) {
        //1.查询试卷详情
        ExamDetailModel model = examModelManager.getExamByExamId(examId);
        if (null == model) {
            log.error("试卷不存在  examId:{}", examId);
            throw new BizException(ErrorCodeEnum.NO_DATA.getErrorCode(), "试卷不存在");
        }

        //2.查询试题类型
        List<ExamQuestionsTypeModel> typeModels = examQuestionsTypeManager
            .listQuestionTypeByExamId(examId);
        if (CollectionUtils.isEmpty(typeModels)) {
            return model;
        }
        //查询试题试题
        QuestionQuery query = new QuestionQuery();
        for (ExamQuestionsTypeModel typeModel : typeModels) {
            if (StringUtils.isNotBlank(typeModel.getExamQuestionString())) {
                query.setQuestionIds(Arrays.asList(typeModel.getExamQuestionString().split(",")));
                //查询试题
                typeModel.setExamQuestions(dtmsQuestionManager.listQuestionByQueryForExam(query));
            }
        }
        model.setExamQuestionTypes(typeModels);
        return model;
    }

    /**
     * 预览
     * @see com.medical.dtms.feignservice.exam.ExamService#listExamQuestionForPreview(java.lang.Long)
     */
    @Override
    public List<ExamQuestionsTypeModel> listExamQuestionForPreview(@RequestParam("examId") Long examId) {
        //2.查询试题类型
        List<ExamQuestionsTypeModel> typeModels = examQuestionsTypeManager
            .listQuestionTypeByExamId(examId);
        if (CollectionUtils.isEmpty(typeModels)) {
            return typeModels;
        }
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
        return typeModels;
    }

    /**
    * 查询所有试卷
    * 
    * @return
    */
    @Override
    public List<ExamPlanModel> listExamForPlan() {
        return examModelManager.listExamForPlan();
    }

}
