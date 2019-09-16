/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.medical.dtms.service.manager.question;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medical.dtms.common.model.question.DtmsQuestionListModel;
import com.medical.dtms.common.util.BeanConvertUtils;
import com.medical.dtms.dto.question.DtmsQuestionsDTO;
import com.medical.dtms.dto.question.QuestionQuery;
import com.medical.dtms.service.dataobject.question.DtmsQuestionsDO;
import com.medical.dtms.service.mapper.question.DtmsQuestionsMapper;

/**
 * 
 * @author shenqifeng
 * @version $Id: DtmsQuestionManager.java, v 0.1 2019年8月27日 下午9:33:16 shenqifeng Exp $
 */
@Service
public class DtmsQuestionManager {
    @Autowired
    private DtmsQuestionsMapper questionMapper;

    /**
     * 新增试题
     * 
     * @param questionsDO
     * @return
     */
    public Boolean saveQuestion(DtmsQuestionsDTO questionsDTO) {
        DtmsQuestionsDO questionsDO = BeanConvertUtils.convert(questionsDTO, DtmsQuestionsDO.class);
        Integer num = questionMapper.saveQuestion(questionsDO);
        return num > 0 ? true : false;
    }

    /**
     * 批量新增试题
     * 
     * @param questionsDO
     * @return
     */
    public Boolean saveBatchQuestion(List<DtmsQuestionsDTO> questionsDTOs) {
        List<DtmsQuestionsDO> questionsDOs = BeanConvertUtils.convertList(questionsDTOs,
            DtmsQuestionsDO.class);
        Integer num = questionMapper.saveBatchQuestion(questionsDOs);
        return num > 0 ? true : false;
    }

    /**
     * 修改试题
     * 
     * @param questionsDO
     * @return
     */
    public Boolean updateQuestion(DtmsQuestionsDTO questionsDTO) {
        DtmsQuestionsDO questionsDO = BeanConvertUtils.convert(questionsDTO, DtmsQuestionsDO.class);
        Integer num = questionMapper.updateQuestion(questionsDO);
        return num > 0 ? true : false;
    }

    /**
     * 查询试题详情
     * 
     * @param bizId
     * @return
     */
    public DtmsQuestionsDTO getQuestionById(Long bizId) {
        DtmsQuestionsDTO questionsDTO = new DtmsQuestionsDTO();
        DtmsQuestionsDO questionsDO = questionMapper.getQuestionById(bizId);
        if (questionsDO != null) {
            questionsDTO = BeanConvertUtils.convert(questionsDO, DtmsQuestionsDTO.class);
        }
        return questionsDTO;

    }

    /**
     * 列表查询试题
     * 
     * @param query
     * @return
     */
    public List<DtmsQuestionListModel> listQuestionsByQuery(QuestionQuery query) {
        return questionMapper.listQuestionsByQuery(query);
    }

    /**
     * 查询试卷试题
     * 
     * @param query
     * @return
     */
    public List<DtmsQuestionListModel> listQuestionByQueryForExam(QuestionQuery query) {
        return questionMapper.listQuestionByQueryForExam(query);
    }

    /**
     * 查询试卷试题
     * 
     * @param query
     * @return
     */
    public List<DtmsQuestionListModel> listQuestionByQueryForExamNotChose(QuestionQuery query) {
        return questionMapper.listQuestionByQueryForExamNotChose(query);
    }

    /**
     * 查询预览试题
     * 
     * @param query
     * @return
     */
    public List<DtmsQuestionsDTO> listQuestionsForPreview(QuestionQuery query) {
        return BeanConvertUtils.convertList(questionMapper.listQuestionsForPreview(query),
            DtmsQuestionsDTO.class);
    }
}
