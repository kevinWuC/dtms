/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.medical.dtms.feignservice.exam;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.medical.dtms.dto.exam.ExamPlanModelDTO;
import com.medical.dtms.dto.exam.query.ExamPlanModelQuery;

/**
 * 考试安排
 * 
 * @author shenqifeng
 * @version $Id: ExamPlanModelService.java, v 0.1 2019年9月9日 下午10:31:30 shenqifeng Exp $
 */
@FeignClient("service-dtms")
public interface ExamPlanModelService {

    /**
     * 新增
     * 
     * @param examPlanModelDO
     * @return
     */
    @RequestMapping(value = "/examPlan/insertExamPlanModel", method = RequestMethod.POST)
    Boolean insertExamPlanModel(@RequestBody ExamPlanModelDTO examPlanModelDTO);

    /**
     * 修改
     * 
     * @param examPlanModelDO
     * @return
     */
    @RequestMapping(value = "/examPlan/updateExamPlanModel", method = RequestMethod.POST)
    Boolean updateExamPlanModel(@RequestBody ExamPlanModelDTO examPlanModelDTO);

    /**
     * 查询详情
     * 
     * @param examPlanModelId
     * @return
     */
    @RequestMapping(value = "/examPlan/getExamPlanModelById", method = RequestMethod.POST)
    ExamPlanModelDTO getExamPlanModelById(@RequestParam("examPlanModelId") Long examPlanModelId);

    /**
     * 分页查询
     * 
     * @param query
     * @return
     */
    @RequestMapping(value = "/examPlan/listExamPlanModelByQuery", method = RequestMethod.POST)
    PageInfo<ExamPlanModelDTO> listExamPlanModelByQuery(@RequestBody ExamPlanModelQuery query);

    /**
     * 人员配置
     * 
     * @param examPlanModelDTO
     * @return
     */
    @RequestMapping(value = "/examPlan/staffConfig", method = RequestMethod.POST)
    Boolean staffConfig(@RequestBody ExamPlanModelDTO examPlanModelDTO);

}
