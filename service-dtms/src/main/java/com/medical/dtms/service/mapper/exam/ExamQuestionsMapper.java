package com.medical.dtms.service.mapper.exam;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.medical.dtms.service.dataobject.exam.ExamQuestionsDO;

/**
 * 试卷与试题关联
 * 
 * @author shenqifeng
 * @version $Id: ExamQuestionsMapper.java, v 0.1 2019年9月7日 下午8:18:13 shenqifeng Exp $
 */
@Repository
public interface ExamQuestionsMapper {
    /**
     * 新增
     * 
     * @param examQuestionsDO
     * @return
     */
    Integer insertExamQuestion(ExamQuestionsDO examQuestionsDO);

    /**
     * 新增
     * 
     * @param examQuestionsDO
     * @return
     */
    Integer insertBatchExamQuestion(List<ExamQuestionsDO> examQuestionsDOs);

    /**
     * 删除
     * 
     * @param examId
     * @return
     */
    Integer deleteByExamId(@Param("examId") Long examId);
}