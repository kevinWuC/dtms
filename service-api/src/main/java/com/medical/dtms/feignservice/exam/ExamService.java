/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.medical.dtms.feignservice.exam;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.medical.dtms.common.model.exam.ExamDetailModel;
import com.medical.dtms.common.model.exam.ExamPlanModel;
import com.medical.dtms.common.model.exam.ExamQuestionsTypeModel;
import com.medical.dtms.dto.exam.ExamModelDTO;
import com.medical.dtms.dto.exam.query.ExamQuery;

/**
 * 试题管理
 * @author shenqifeng
 * @version $Id: DtmsQuestionService.java, v 0.1 2019年8月27日 下午9:40:41 shenqifeng Exp $
 */
@FeignClient("service-dtms")
public interface ExamService {
    /**
     * 新增试卷
     * 
     * @param questionsDTO
     * @return
     */
    @RequestMapping(value = "/exam/insertExam", method = RequestMethod.POST)
    Boolean insertExam(@RequestBody ExamModelDTO examModelDTO);

    /**
     * 修改试卷
     * 
     * @param examModelDTO
     * @return
     */
    @RequestMapping(value = "/exam/updateExam", method = RequestMethod.POST)
    Boolean updateExam(@RequestBody ExamModelDTO examModelDTO);

    /**
     * 删除试卷
     * 
     * @param examId
     * @return
     */
    @RequestMapping(value = "/exam/deleteExam", method = RequestMethod.POST)
    Boolean deleteExam(@RequestBody ExamModelDTO detailModel);

    /**
     * 分页查询
     * 
     * @param query
     * @return
     */
    @RequestMapping(value = "/exam/listExamByQuery", method = RequestMethod.POST)
    PageInfo<ExamModelDTO> listExamByQuery(@RequestBody ExamQuery query);

    /**
     * 详情查询
     * 
     * @param examId
     * @return
     */
    @RequestMapping(value = "/exam/getExamByExamId", method = RequestMethod.POST)
    ExamDetailModel getExamByExamId(@RequestParam("examId") Long examId);

    /**
     * 预览
     * 
     * @param examId
     * @return
     */
    @RequestMapping(value = "/exam/listExamQuestionForPreview", method = RequestMethod.POST)
    List<ExamQuestionsTypeModel> listExamQuestionForPreview(@RequestParam("examId") Long examId);

    /**
     * 查询所有试卷
     * 
     * @return
     */
    @RequestMapping(value = "/exam/listExamForPlan", method = RequestMethod.POST)
    List<ExamPlanModel> listExamForPlan();

}
