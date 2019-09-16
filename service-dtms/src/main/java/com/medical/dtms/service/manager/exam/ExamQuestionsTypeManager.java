package com.medical.dtms.service.manager.exam;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.medical.dtms.common.model.exam.ExamQuestionsTypeModel;
import com.medical.dtms.common.util.BeanConvertUtils;
import com.medical.dtms.dto.exam.ExamQuestionsTypeDTO;
import com.medical.dtms.service.dataobject.exam.ExamQuestionsTypeDO;
import com.medical.dtms.service.mapper.exam.ExamQuestionsTypeMapper;

/**
 * 试卷与尸体类别关联
 * 
 * @author shenqifeng
 * @version $Id: ExamQuestionSTypeMapper.java, v 0.1 2019年9月7日 下午8:18:43 shenqifeng Exp $
 */
@Repository
public class ExamQuestionsTypeManager {
    @Autowired
    private ExamQuestionsTypeMapper examQuestionsTypeMapper;

    /**
     * 新增
     * 
     * @param examQuestionSTypeDO
     * @return
     */
    public Boolean insertExamQuestionType(ExamQuestionsTypeDTO examQuestionsTypeDTO) {
        ExamQuestionsTypeDO examQuestionsTypeDO = BeanConvertUtils.convert(examQuestionsTypeDTO,
            ExamQuestionsTypeDO.class);
        Integer num = examQuestionsTypeMapper.insertExamQuestionType(examQuestionsTypeDO);
        return num > 0 ? true : false;
    }

    /**
     * 新增
     * 
     * @param examQuestionSTypeDO
     * @return
     */
    public Boolean insertBatchExamQuestionType(List<ExamQuestionsTypeDTO> examQuestionsTypeDTOs) {
        List<ExamQuestionsTypeDO> examQuestionsTypeDOs = BeanConvertUtils
            .convertList(examQuestionsTypeDTOs, ExamQuestionsTypeDO.class);
        Integer num = examQuestionsTypeMapper.insertBatchExamQuestionType(examQuestionsTypeDOs);
        return num > 0 ? true : false;
    }

    /**
     * 查询试题类型
     * 
     * @param examId
     * @return
     */
    public List<ExamQuestionsTypeModel> listQuestionTypeByExamId(Long examId) {
        return examQuestionsTypeMapper.listQuestionTypeByExamId(examId);
    }

    /**
     * 删除
     * 
     * @param examId
     * @return
     */
    public Boolean deleteByExamId(Long examId) {
        Integer num = examQuestionsTypeMapper.deleteByExamId(examId);
        return num > 0 ? true : false;
    }
}