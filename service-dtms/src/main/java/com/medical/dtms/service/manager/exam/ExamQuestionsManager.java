package com.medical.dtms.service.manager.exam;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.medical.dtms.common.util.BeanConvertUtils;
import com.medical.dtms.dto.exam.ExamQuestionsDTO;
import com.medical.dtms.service.dataobject.exam.ExamQuestionsDO;
import com.medical.dtms.service.mapper.exam.ExamQuestionsMapper;

/**
 * 试卷与试题关联
 * 
 * @author shenqifeng
 * @version $Id: ExamQuestionsMapper.java, v 0.1 2019年9月7日 下午8:18:13 shenqifeng Exp $
 */
@Repository
public class ExamQuestionsManager {
    @Autowired
    private ExamQuestionsMapper examQuestionsMapper;

    /**
     * 新增
     * 
     * @param examQuestionsDO
     * @return
     */
    public Boolean insertExamQuestion(ExamQuestionsDTO examQuestionsDTO) {
        ExamQuestionsDO examQuestionsDO = BeanConvertUtils.convert(examQuestionsDTO,
            ExamQuestionsDO.class);
        Integer num = examQuestionsMapper.insertExamQuestion(examQuestionsDO);
        return num > 0 ? true : false;
    }

    /**
     * 新增
     * 
     * @param examQuestionsDO
     * @return
     */
    public Boolean insertBatchExamQuestion(List<ExamQuestionsDTO> examQuestionsDTOs) {
        List<ExamQuestionsDO> examQuestionsDOs = BeanConvertUtils.convertList(examQuestionsDTOs,
            ExamQuestionsDO.class);
        Integer num = examQuestionsMapper.insertBatchExamQuestion(examQuestionsDOs);
        return num > 0 ? true : false;
    }

    /**
     * 删除
     * 
     * @param examId
     * @return
     */
    public Boolean deleteByExamId(Long examId) {
        Integer num = examQuestionsMapper.deleteByExamId(examId);
        return num > 0 ? true : false;
    }

    public List<Long> queryByExamId(Long examId){
        return examQuestionsMapper.queryByExamId(examId);
    }
}