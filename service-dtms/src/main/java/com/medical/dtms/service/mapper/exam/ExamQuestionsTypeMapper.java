package com.medical.dtms.service.mapper.exam;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.medical.dtms.common.model.exam.ExamQuestionsTypeModel;
import com.medical.dtms.service.dataobject.exam.ExamQuestionsTypeDO;

/**
 * 试卷与尸体类别关联
 * 
 * @author shenqifeng
 * @version $Id: ExamQuestionSTypeMapper.java, v 0.1 2019年9月7日 下午8:18:43 shenqifeng Exp $
 */
@Repository
public interface ExamQuestionsTypeMapper {
    /**
     * 新增
     * 
     * @param examQuestionSTypeDO
     * @return
     */
    Integer insertExamQuestionType(ExamQuestionsTypeDO examQuestionsTypeDO);

    /**
     * 新增
     * 
     * @param examQuestionSTypeDO
     * @return
     */
    Integer insertBatchExamQuestionType(List<ExamQuestionsTypeDO> examQuestionsTypeDOs);

    /**
     * 查询试题类型
     * 
     * @param examId
     * @return
     */
    List<ExamQuestionsTypeModel> listQuestionTypeByExamId(@Param("examId") Long examId);

    /**
     * 删除
     * 
     * @param examId
     * @return
     */
    Integer deleteByExamId(@Param("examId") Long examId);
}