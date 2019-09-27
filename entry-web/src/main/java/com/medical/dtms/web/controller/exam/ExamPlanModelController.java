/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.medical.dtms.web.controller.exam;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.github.pagehelper.PageInfo;
import com.medical.dtms.common.eception.BizException;
import com.medical.dtms.common.enumeration.ErrorCodeEnum;
import com.medical.dtms.common.login.OperatorInfo;
import com.medical.dtms.common.login.SessionTools;
import com.medical.dtms.common.resp.Result;
import com.medical.dtms.dto.exam.ExamPlanModelDTO;
import com.medical.dtms.dto.exam.query.ExamPlanModelQuery;
import com.medical.dtms.feignservice.exam.ExamPlanModelService;

import lombok.extern.slf4j.Slf4j;

/**
 * 考试安排
 * @author shenqifeng
 * @version $Id: ExamPlanModelController.java, v 0.1 2019年9月9日 下午10:34:23 shenqifeng Exp $
 */
@Slf4j
@RestController
public class ExamPlanModelController {
    @Autowired
    private ExamPlanModelService examPlanModelService;


    /**
     * 删除
     *
     * @param examPlanModelDTO
     * @return
     */
    @RequestMapping(value = "/examPlan/deleteExamPlanModel", method = RequestMethod.POST)
    public Result<Boolean> deleteExamPlanModel(@RequestBody ExamPlanModelDTO examPlanModelDTO){
        if (null == examPlanModelDTO || null == examPlanModelDTO.getExamPlanModelId()) {
            log.error("缺少参数");
            throw new BizException(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "缺少参数");
        }
        OperatorInfo operatorInfo = SessionTools.getOperator();
        examPlanModelDTO.setModifyUserId(operatorInfo.getBizId());
        examPlanModelDTO.setModifyUserName(operatorInfo.getDspName());
        examPlanModelService.deleteExamPlanModel(examPlanModelDTO);
        return new Result<Boolean>(ErrorCodeEnum.SUCCESS.getErrorCode(), true, "删除成功", true);
    }

    /**
     * 新增
     * 
     * @param examPlanModelDO
     * @return
     */
    @RequestMapping(value = "/examPlan/insertExamPlanModel", method = RequestMethod.POST)
    public Result<Boolean> insertExamPlanModel(@RequestBody ExamPlanModelDTO examPlanModelDTO) {
        if (null == examPlanModelDTO || null == examPlanModelDTO.getExamId()
            || null == examPlanModelDTO.getExamDuration()
            || StringUtils.isBlank(examPlanModelDTO.getExamPlanName())
            || null == examPlanModelDTO.getUseDeptId() || null == examPlanModelDTO.getTotalPoints()
            || null == examPlanModelDTO.getPassPoints() || null == examPlanModelDTO.getStartDate()
            || null == examPlanModelDTO.getEndDate()
            || StringUtils.isBlank(examPlanModelDTO.getExamInfo())
            || StringUtils.isBlank(examPlanModelDTO.getExamContent())) {
            log.error("缺少参数");
            throw new BizException(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "缺少参数");
        }
        //获取用户信息
        OperatorInfo operatorInfo = SessionTools.getOperator();
        examPlanModelDTO.setCreateUserId(operatorInfo.getBizId());
        examPlanModelDTO.setCreateUserName(operatorInfo.getDspName());
        examPlanModelDTO.setModifyUserId(operatorInfo.getBizId());
        examPlanModelDTO.setModifyUserName(operatorInfo.getDspName());

        examPlanModelService.insertExamPlanModel(examPlanModelDTO);

        return new Result<Boolean>(ErrorCodeEnum.SUCCESS.getErrorCode(), true, "新增成功", true);
    }

    /**
     * 修改
     * 
     * @param examPlanModelDO
     * @return
     */
    @RequestMapping(value = "/examPlan/updateExamPlanModel", method = RequestMethod.POST)
    public Result<Boolean> updateExamPlanModel(@RequestBody ExamPlanModelDTO examPlanModelDTO) {
        if (null == examPlanModelDTO || null == examPlanModelDTO.getExamPlanModelId()
            || null == examPlanModelDTO.getExamId() || null == examPlanModelDTO.getExamDuration()
            || StringUtils.isBlank(examPlanModelDTO.getExamPlanName())
            || null == examPlanModelDTO.getUseDeptId() || null == examPlanModelDTO.getTotalPoints()
            || null == examPlanModelDTO.getPassPoints() || null == examPlanModelDTO.getStartDate()
            || null == examPlanModelDTO.getEndDate()
            || StringUtils.isBlank(examPlanModelDTO.getExamInfo())
            || StringUtils.isBlank(examPlanModelDTO.getExamContent())) {
            log.error("缺少参数");
            throw new BizException(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "缺少参数");
        }
        //获取用户信息
        OperatorInfo operatorInfo = SessionTools.getOperator();
        examPlanModelDTO.setModifyUserId(operatorInfo.getBizId());
        examPlanModelDTO.setModifyUserName(operatorInfo.getDspName());
        examPlanModelService.updateExamPlanModel(examPlanModelDTO);

        return new Result<Boolean>(ErrorCodeEnum.SUCCESS.getErrorCode(), true, "修改成功", true);
    }

    /**
     * 启动
     * 
     * @param examPlanModelDO
     * @return
     */
    @RequestMapping(value = "/examPlan/startExamPlanModel", method = RequestMethod.POST)
    public Result<Boolean> startExamPlanModel(@RequestBody ExamPlanModelDTO examPlanModelDTO) {
        if (null == examPlanModelDTO || null == examPlanModelDTO.getExamPlanModelId()) {
            log.error("缺少参数");
            throw new BizException(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "缺少参数");
        }
        //获取用户信息
        OperatorInfo operatorInfo = SessionTools.getOperator();
        examPlanModelDTO.setModifyUserId(operatorInfo.getBizId());
        examPlanModelDTO.setModifyUserName(operatorInfo.getDspName());
        examPlanModelDTO.setIsStart(true);
        examPlanModelService.updateExamPlanModel(examPlanModelDTO);

        return new Result<Boolean>(ErrorCodeEnum.SUCCESS.getErrorCode(), true, "启动成功", true);
    }

    /**
     * 查询详情
     * 
     * @param examPlanModelId
     * @return
     */
    @RequestMapping(value = "/examPlan/getExamPlanModelById", method = RequestMethod.POST)
    public Result<ExamPlanModelDTO> getExamPlanModelById(@RequestBody ExamPlanModelDTO examPlanModelDTO) {
        if (null == examPlanModelDTO || null == examPlanModelDTO.getExamPlanModelId()) {
            log.error("缺少参数");
            throw new BizException(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "缺少参数");
        }
        ExamPlanModelDTO examPlan = examPlanModelService
            .getExamPlanModelById(examPlanModelDTO.getExamPlanModelId());
        return new Result<ExamPlanModelDTO>(ErrorCodeEnum.SUCCESS.getErrorCode(), Boolean.TRUE,
            "查询成功", examPlan);
    }

    /**
     * 分页查询
     * 
     * @param query
     * @return
     */
    @RequestMapping(value = "/examPlan/listExamPlanModelByQuery", method = RequestMethod.POST)
    public Result<PageInfo<ExamPlanModelDTO>> listExamPlanModelByQuery(@RequestBody ExamPlanModelQuery query) {
        if (null == query) {
            query = new ExamPlanModelQuery();
        }
        if (null == query.getPageNo()) {
            query.setPageNo(1);
        }
        if (null == query.getPageSize()) {
            query.setPageSize(10);
        }

        PageInfo<ExamPlanModelDTO> examPlans = examPlanModelService.listExamPlanModelByQuery(query);
        return new Result<PageInfo<ExamPlanModelDTO>>(ErrorCodeEnum.SUCCESS.getErrorCode(),
            Boolean.TRUE, "查询成功", examPlans);
    }

    /**
     * 人员配置
     * 
     * @param examPlanModelDO
     * @return
     */
    @RequestMapping(value = "/examPlan/staffConfig", method = RequestMethod.POST)
    public Result<Boolean> staffConfig(@RequestBody ExamPlanModelDTO examPlanModelDTO) {
        if (null == examPlanModelDTO || null == examPlanModelDTO.getExamPlanModelId()
            || CollectionUtils.isEmpty(examPlanModelDTO.getUserIds())) {
            log.error("缺少参数");
            throw new BizException(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "缺少参数");
        }
        //获取用户信息
        OperatorInfo operatorInfo = SessionTools.getOperator();
        examPlanModelDTO.setModifyUserId(operatorInfo.getBizId());
        examPlanModelDTO.setModifyUserName(operatorInfo.getDspName());

        examPlanModelService.staffConfig(examPlanModelDTO);

        return new Result<Boolean>(ErrorCodeEnum.SUCCESS.getErrorCode(), true, "配置成功", true);
    }

}
