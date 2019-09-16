/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.medical.dtms.web.controller.exam;

import com.medical.dtms.common.eception.BizException;
import com.medical.dtms.common.model.exam.ExamStartModel;
import com.medical.dtms.common.model.exam.query.ExamSubmitAnswerQuery;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.github.pagehelper.PageInfo;
import com.medical.dtms.common.enumeration.ErrorCodeEnum;
import com.medical.dtms.common.login.OperatorInfo;
import com.medical.dtms.common.login.SessionTools;
import com.medical.dtms.common.resp.Result;
import com.medical.dtms.dto.exam.ExamUserPlanModelDTO;
import com.medical.dtms.dto.exam.query.ExamPlanModelQuery;
import com.medical.dtms.feignservice.exam.ExamUserPlanModelService;

import lombok.extern.slf4j.Slf4j;

/**
 * 考试安排
 * @author shenqifeng
 * @version $Id: ExamPlanModelController.java, v 0.1 2019年9月9日 下午10:34:23 shenqifeng Exp $
 */
@Slf4j
@RestController
public class ExamUserPlanModelController {
    @Autowired
    private ExamUserPlanModelService examUserPlanModelService;

    /**
     * 分页查询
     * 
     * @param query
     * @return
     */
    @RequestMapping(value = "/examUserPlan/listExamUserPlanByQuery", method = RequestMethod.POST)
    public Result<PageInfo<ExamUserPlanModelDTO>> listExamUserPlanByQuery(@RequestBody ExamPlanModelQuery query) {
        if (null == query) {
            query = new ExamPlanModelQuery();
        }
        if (null == query.getPageNo()) {
            query.setPageNo(1);
        }
        if (null == query.getPageSize()) {
            query.setPageSize(10);
        }
        //获取用户信息
        OperatorInfo operatorInfo = SessionTools.getOperator();
        query.setUserId(operatorInfo.getBizId());

        PageInfo<ExamUserPlanModelDTO> examPlans = examUserPlanModelService
            .listExamUserPlanByQuery(query);
        return new Result<PageInfo<ExamUserPlanModelDTO>>(ErrorCodeEnum.SUCCESS.getErrorCode(),
            Boolean.TRUE, "查询成功", examPlans);
    }

    /**
     * 开始考试
     *
     * @param userPlanModelDTO
     * @return
     */
    @RequestMapping(value = "/examUserPlan/startExam", method = RequestMethod.POST)
    public Result<ExamStartModel> startExam(@RequestBody ExamUserPlanModelDTO userPlanModelDTO){
        if (null == userPlanModelDTO || null == userPlanModelDTO.getExamUserPlanModelId()){
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "主键为空");
        }

        OperatorInfo operatorInfo = SessionTools.getOperator();
        userPlanModelDTO.setCreateUserId(operatorInfo.getBizId());
        userPlanModelDTO.setCreateUserName(operatorInfo.getDspName());

        ExamStartModel exam = examUserPlanModelService.startExam(userPlanModelDTO);

        return Result.buildSuccess(exam);
    }


    /**
     * 提交考试答案
     *
     * @param query
     * @return
     */
    @RequestMapping(value = "/examUserPlan/submitExamAnswer", method = RequestMethod.POST)
    public Result<Boolean> submitExamAnswer(@RequestBody ExamSubmitAnswerQuery query){
        if (null == query || null == query.getExamId()
                || null == query.getExamPlanId()
                || null == query.getExamUserPlanId()
                || null == query.getUserId()
                || CollectionUtils.isEmpty(query.getQuestions())
               ) {
            log.error("缺少参数");
            throw new BizException(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "缺少参数");
        }
        //获取用户信息
        OperatorInfo info = SessionTools.getOperator();
        query.setModifier(info.getDspName());
        query.setModifierId(info.getUserId());
        examUserPlanModelService.submitExamAnswer(query);

        return new Result<Boolean>(ErrorCodeEnum.SUCCESS.getErrorCode(), true, "提交成功", true);
    }



}
