/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.medical.dtms.service.serviceimpl.question;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.medical.dtms.common.enumeration.QuestionTypeEnum;
import com.medical.dtms.common.model.question.DtmsQuestionListModel;
import com.medical.dtms.common.model.question.QuestionItemModel;
import com.medical.dtms.common.util.IdGenerator;
import com.medical.dtms.dto.question.DtmsQuestionsDTO;
import com.medical.dtms.dto.question.QuestionQuery;
import com.medical.dtms.feignservice.question.DtmsQuestionService;
import com.medical.dtms.service.manager.item.QMSItemDetailsManager;
import com.medical.dtms.service.manager.question.DtmsQuestionManager;

/**
 * 试题管理
 * @author shenqifeng
 * @version $Id: DtmsQuestionServiceImpl.java, v 0.1 2019年8月27日 下午9:39:41 shenqifeng Exp $
 */
@RestController
public class DtmsQuestionServiceImpl implements DtmsQuestionService {
    @Autowired
    private DtmsQuestionManager   questionManager;
    @Autowired
    private IdGenerator           idGenerator;
    @Autowired
    private QMSItemDetailsManager itemDetailsManager;

    @Override
    public Boolean saveQuestion(@RequestBody DtmsQuestionsDTO questionsDTO) {
        questionsDTO.setBizId(idGenerator.nextId());
        return questionManager.saveQuestion(questionsDTO);
    }

    @Override
    public Boolean saveBatchQuestion(@RequestBody List<DtmsQuestionsDTO> questionsDTOs) {
        for (DtmsQuestionsDTO questionsDTO : questionsDTOs) {
            questionsDTO.setBizId(idGenerator.nextId());
        }
        return questionManager.saveBatchQuestion(questionsDTOs);
    }

    @Override
    public Boolean updateQuestion(@RequestBody DtmsQuestionsDTO questionsDTO) {
        return questionManager.updateQuestion(questionsDTO);
    }

    @Override
    public DtmsQuestionsDTO getQuestionById(@RequestParam("bizId") Long bizId) {
        return questionManager.getQuestionById(bizId);
    }

    @Override
    public PageInfo<DtmsQuestionListModel> listQuestionsByQuery(@RequestBody QuestionQuery query) {
        PageHelper.startPage(query.getPageNo(), query.getPageSize());
        List<DtmsQuestionListModel> listModel = questionManager.listQuestionsByQuery(query);
        PageInfo pageInfo = new PageInfo<>(listModel);
        return pageInfo;
    }

    /**
     * 查询已选择试题
     * @see com.medical.dtms.feignservice.question.DtmsQuestionService#listQuestionByQueryForExam(com.medical.dtms.dto.question.QuestionQuery)
     */
    @Override
    public List<DtmsQuestionListModel> listQuestionByQueryForExam(@RequestBody QuestionQuery query) {
        return questionManager.listQuestionByQueryForExam(query);
    }

    /**
     * 查询未选择试题
     * @see com.medical.dtms.feignservice.question.DtmsQuestionService#listQuestionByQueryForExamNotChose(com.medical.dtms.dto.question.QuestionQuery)
     */
    @Override
    public List<DtmsQuestionListModel> listQuestionByQueryForExamNotChose(@RequestBody QuestionQuery query) {
        return questionManager.listQuestionByQueryForExamNotChose(query);
    }

    /**
     * 
     * @see com.medical.dtms.feignservice.question.DtmsQuestionService#listItemsByItemId(java.lang.Long)
     */
    @Override
    public List<QuestionItemModel> listDetailsByItemsIdForQuestion(@RequestParam("type") String type) {
        return itemDetailsManager
            .listDetailsByItemsIdForQuestion((long) QuestionTypeEnum.getTypeByName(type));
    }

}
