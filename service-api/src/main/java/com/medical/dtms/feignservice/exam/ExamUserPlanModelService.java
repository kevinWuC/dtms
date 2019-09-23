/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.medical.dtms.feignservice.exam;

import com.github.pagehelper.PageInfo;
import com.medical.dtms.common.model.exam.ExamStartModel;
import com.medical.dtms.common.model.exam.query.ExamSubmitAnswerQuery;
import com.medical.dtms.dto.exam.query.ExamUserAfreshPlanQuery;
import com.medical.dtms.dto.exam.ExamUserPlanModelDTO;
import com.medical.dtms.dto.exam.query.ExamPlanModelQuery;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 考试安排
 * 
 * @author shenqifeng
 * @version $Id: ExamPlanModelService.java, v 0.1 2019年9月9日 下午10:31:30 shenqifeng Exp $
 */
@FeignClient("service-dtms")
public interface ExamUserPlanModelService {

    /**
     * 分页查询
     * 
     * @param query
     * @return
     */
    @RequestMapping(value = "/examUserPlan/listExamUserPlanByQuery", method = RequestMethod.POST)
    PageInfo<ExamUserPlanModelDTO> listExamUserPlanByQuery(@RequestBody ExamPlanModelQuery query);

    /**
     * 开始考试
     *
     * @param userPlanModelDTO
     * @return
     */
    @RequestMapping(value = "/examUserPlan/startExam", method = RequestMethod.POST)
    ExamStartModel startExam(@RequestBody ExamUserPlanModelDTO userPlanModelDTO);

    /**
     * 提交考试答案
     *
     * @param query
     * @return
     */
    @RequestMapping(value = "/examUserPlan/submitExamAnswer", method = RequestMethod.POST)
    Boolean submitExamAnswer(@RequestBody ExamSubmitAnswerQuery query);

    /**
     * 试卷查看
     *
     * @param userPlanModelDTO
     * @return
     */
    @RequestMapping(value = "/examUserPlan/lookExam", method = RequestMethod.POST)
    ExamStartModel lookExam(@RequestBody ExamUserPlanModelDTO userPlanModelDTO);

    /**
     * 分页查询（批卷用）
     *
     * @param query
     * @return
     */
    @RequestMapping(value = "/examUserPlan/listExamUserPlanByQueryForMark", method = RequestMethod.POST)
    PageInfo<ExamUserPlanModelDTO> listExamUserPlanByQueryForMark(@RequestBody ExamPlanModelQuery query);

    /**
     * 提交阅卷结果
     *
     * @param query
     * @return
     */
    @RequestMapping(value = "/examUserPlan/submitExamMarkResult", method = RequestMethod.POST)
    Boolean submitExamMarkResult(@RequestBody ExamSubmitAnswerQuery query);

    /**
     * 重新发起考试
     *
     * @param query
     * @return
     */
    @RequestMapping(value = "/examUserPlan/afreshExamPlan", method = RequestMethod.POST)
    Boolean afreshExamPlan(@RequestBody ExamUserAfreshPlanQuery query);

}
