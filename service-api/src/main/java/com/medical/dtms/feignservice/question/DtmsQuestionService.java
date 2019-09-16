/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.medical.dtms.feignservice.question;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.medical.dtms.common.model.question.DtmsQuestionListModel;
import com.medical.dtms.common.model.question.QuestionItemModel;
import com.medical.dtms.dto.question.DtmsQuestionsDTO;
import com.medical.dtms.dto.question.QuestionQuery;

/**
 * 试题管理
 * @author shenqifeng
 * @version $Id: DtmsQuestionService.java, v 0.1 2019年8月27日 下午9:40:41 shenqifeng Exp $
 */
@FeignClient("service-dtms")
public interface DtmsQuestionService {
    /**
     * 新增试题
     * 
     * @param questionsDTO
     * @return
     */
    @RequestMapping(value = "/question/saveQuestion", method = RequestMethod.POST)
    Boolean saveQuestion(@RequestBody DtmsQuestionsDTO questionsDTO);

    /**
     * 新增试题
     * 
     * @param questionsDTO
     * @return
     */
    @RequestMapping(value = "/question/saveBatchQuestion", method = RequestMethod.POST)
    Boolean saveBatchQuestion(@RequestBody List<DtmsQuestionsDTO> questionsDTOs);

    /**
     * 修改试题
     * 
     * @param questionsDTO
     * @return
     */
    @RequestMapping(value = "/question/updateQuestion", method = RequestMethod.POST)
    Boolean updateQuestion(@RequestBody DtmsQuestionsDTO questionsDTO);

    /**
     * 查询试题详情
     * 
     * @param bizId
     * @return
     */
    @RequestMapping(value = "/question/getQuestionById", method = RequestMethod.POST)
    DtmsQuestionsDTO getQuestionById(@RequestParam("bizId") Long bizId);

    /**
     * 列表查询试题
     * 
     * @param query
     * @return
     */
    @RequestMapping(value = "/question/listQuestionsByQuery", method = RequestMethod.POST)
    PageInfo<DtmsQuestionListModel> listQuestionsByQuery(@RequestBody QuestionQuery query);

    /**
     * 试卷查询已选题目
     * 
     * @param query
     * @return
     */
    @RequestMapping(value = "/question/listQuestionByQueryForExam", method = RequestMethod.POST)
    List<DtmsQuestionListModel> listQuestionByQueryForExam(@RequestBody QuestionQuery query);

    /**
     * 试卷查询未选题目
     * 
     * @param query
     * @return
     */
    @RequestMapping(value = "/question/listQuestionByQueryForExamNotChose", method = RequestMethod.POST)
    List<DtmsQuestionListModel> listQuestionByQueryForExamNotChose(@RequestBody QuestionQuery query);

    /**
     * 试题类型  题库类型  培训类别
     * 
     * @param string
     * @return
     */
    @RequestMapping(value = "/question/listDetailsByItemsIdForQuestion", method = RequestMethod.POST)
    List<QuestionItemModel> listDetailsByItemsIdForQuestion(@RequestParam("type") String type);

}
