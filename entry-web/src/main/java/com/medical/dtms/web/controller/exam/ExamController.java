/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.medical.dtms.web.controller.exam;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.medical.dtms.common.eception.BizException;
import com.medical.dtms.common.enumeration.ErrorCodeEnum;
import com.medical.dtms.common.login.OperatorInfo;
import com.medical.dtms.common.login.SessionTools;
import com.medical.dtms.common.model.exam.ExamDetailModel;
import com.medical.dtms.common.model.exam.ExamPlanModel;
import com.medical.dtms.common.model.exam.ExamQuestionsTypeModel;
import com.medical.dtms.common.model.question.DtmsQuestionListModel;
import com.medical.dtms.common.resp.Result;
import com.medical.dtms.common.util.DateUtils;
import com.medical.dtms.dto.exam.ExamModelDTO;
import com.medical.dtms.dto.exam.query.ExamQuery;
import com.medical.dtms.dto.question.QuestionQuery;
import com.medical.dtms.feignservice.exam.ExamService;
import com.medical.dtms.feignservice.question.DtmsQuestionService;

import lombok.extern.slf4j.Slf4j;

/**
 * 试卷管理
 * @author shenqifeng
 * @version $Id: ExamController.java, v 0.1 2019年9月7日 下午8:52:47 shenqifeng Exp $
 */
@Slf4j
@RestController
public class ExamController {
    @Autowired
    private ExamService         examService;
    @Autowired
    private DtmsQuestionService dtmsQuestionService;

    /**
     * 新增
     * 
     * @param examModelDTO
     * @return
     */
    @RequestMapping(value = "/exam/insertExam", method = RequestMethod.POST)
    public Result<Boolean> insertExam(@RequestBody ExamModelDTO examModelDTO) {
        if (null == examModelDTO || null == examModelDTO.getExamDuration()
            || null == examModelDTO.getTotalPoints()
            || CollectionUtils.isEmpty(examModelDTO.getExamQuestionsTypes())) {
            log.error("缺少参数");
            throw new BizException(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "缺少参数");
        }
        //获取用户信息
        OperatorInfo operatorInfo = SessionTools.getOperator();
        examModelDTO.setCreateUserId(operatorInfo.getBizId());
        examModelDTO.setCreateUserName(operatorInfo.getDspName());
        examModelDTO.setModifyUserId(operatorInfo.getBizId());
        examModelDTO.setModifyUserName(operatorInfo.getDspName());
        examService.insertExam(examModelDTO);

        return new Result<Boolean>(ErrorCodeEnum.SUCCESS.getErrorCode(), true, "新增成功", true);
    }

    /**
     * 修改
     * 
     * @param examModelDTO
     * @return
     */
    @RequestMapping(value = "/exam/updateExam", method = RequestMethod.POST)
    public Result<Boolean> updateExam(@RequestBody ExamModelDTO examModelDTO) {
        if (null == examModelDTO || null == examModelDTO.getExamId()
            || null == examModelDTO.getExamDuration() || null == examModelDTO.getTotalPoints()
            || CollectionUtils.isEmpty(examModelDTO.getExamQuestionsTypes())) {
            log.error("缺少参数");
            throw new BizException(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "缺少参数");
        }
        //获取用户信息
        OperatorInfo operatorInfo = SessionTools.getOperator();

        examModelDTO.setModifyUserId(operatorInfo.getBizId());
        examModelDTO.setModifyUserName(operatorInfo.getDspName());
        examService.updateExam(examModelDTO);

        return new Result<Boolean>(ErrorCodeEnum.SUCCESS.getErrorCode(), true, "修改成功", true);
    }

    /**
     * 删除
     * 
     * @param examModelDTO
     * @return
     */
    @RequestMapping(value = "/exam/deleteExam", method = RequestMethod.POST)
    public Result<Boolean> deleteExam(@RequestBody ExamModelDTO examModelDTO) {
        if (null == examModelDTO || null == examModelDTO.getExamId()) {
            log.error("缺少参数");
            throw new BizException(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "缺少参数");
        }

        OperatorInfo operatorInfo = SessionTools.getOperator();

        examModelDTO.setModifyUserId(operatorInfo.getBizId());
        examModelDTO.setModifyUserName(operatorInfo.getDspName());
        examService.deleteExam(examModelDTO);

        return new Result<Boolean>(ErrorCodeEnum.SUCCESS.getErrorCode(), true, "删除成功", true);
    }

    /**
     * 预览
     * 
     * @param examModelDTO
     * @return
     */
    @RequestMapping(value = "/exam/listExamQuestionForPreview", method = RequestMethod.POST)
    public Result<List<ExamQuestionsTypeModel>> listExamQuestionForPreview(@RequestBody ExamModelDTO examModelDTO) {
        if (null == examModelDTO || null == examModelDTO.getExamId()) {
            log.error("缺少参数");
            throw new BizException(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "缺少参数");
        }

        List<ExamQuestionsTypeModel> list = examService
            .listExamQuestionForPreview(examModelDTO.getExamId());

        return new Result<List<ExamQuestionsTypeModel>>(ErrorCodeEnum.SUCCESS.getErrorCode(), true,
            "查询成功", list);
    }

    /**
     * 详情查询
     * 
     * @param query
     * @return
     */
    @RequestMapping(value = "/exam/getExamByExamId", method = RequestMethod.POST)
    public Result<ExamDetailModel> getExamByExamId(@RequestBody ExamQuery query) {
        if (null == query || null == query.getExamId()) {
            log.error("缺少参数");
            throw new BizException(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "缺少参数");
        }

        ExamDetailModel detailModel = examService.getExamByExamId(query.getExamId());

        return new Result<ExamDetailModel>(ErrorCodeEnum.SUCCESS.getErrorCode(), true, "查询成功",
            detailModel);
    }

    /**
     * 分页查询
     * 
     * @param query
     * @return
     */
    @RequestMapping(value = "/exam/listExamByQuery", method = RequestMethod.POST)
    public Result<PageInfo<ExamModelDTO>> listExamByQuery(@RequestBody ExamQuery query) {
        if (null == query) {
            query = new ExamQuery();
            query.setPageNo(1);
            query.setPageSize(10);
        }
        if (null != query.getStartTime()) {
            query.setStartTime(DateUtils.getStartOfDay(query.getStartTime()));
        }
        if (null != query.getEndTime()) {
            query.setEndTime(DateUtils.getEndOfDay(query.getEndTime()));
        }

        PageInfo<ExamModelDTO> pageInfo = examService.listExamByQuery(query);

        return new Result<PageInfo<ExamModelDTO>>(ErrorCodeEnum.SUCCESS.getErrorCode(), true,
            "查询成功", pageInfo);
    }

    /**
     * 查询未选择试题
     * 
     * @param query
     * @return
     */
    @RequestMapping(value = "/exam/listQuestionByQueryForExamNotChose", method = RequestMethod.POST)
    public Result<List<DtmsQuestionListModel>> listQuestionByQueryForExamNotChose(@RequestBody QuestionQuery query) {
        if (null == query || null == query.getQuestionsTypeId()) {
            log.error("试题类型为空");
            throw new BizException(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "试题类型为空");
        }

        List<DtmsQuestionListModel> listQuestionByQueryForExamNotChose = dtmsQuestionService
            .listQuestionByQueryForExamNotChose(query);

        return new Result<List<DtmsQuestionListModel>>(ErrorCodeEnum.SUCCESS.getErrorCode(), true,
            "查询成功", listQuestionByQueryForExamNotChose);
    }

    /**
     * 查询已选择试题
     * 
     * @param query
     * @return
     */
    @RequestMapping(value = "/exam/listQuestionByQueryForExam", method = RequestMethod.POST)
    public Result<List<DtmsQuestionListModel>> listQuestionByQueryForExam(@RequestBody QuestionQuery query) {
        if (null == query || CollectionUtils.isEmpty(query.getQuestionIds())) {
            log.error("试题id为空");
            throw new BizException(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "试题id为空");
        }

        List<DtmsQuestionListModel> list = dtmsQuestionService.listQuestionByQueryForExam(query);

        return new Result<List<DtmsQuestionListModel>>(ErrorCodeEnum.SUCCESS.getErrorCode(), true,
            "查询成功", list);
    }

    /**
     * 查询已选择试题
     * 
     * @param query
     * @return
     */
    @RequestMapping(value = "/exam/listExamForPlan", method = RequestMethod.POST)
    public Result<List<ExamPlanModel>> listExamForPlan() {

        List<ExamPlanModel> list = examService.listExamForPlan();
        return new Result<List<ExamPlanModel>>(ErrorCodeEnum.SUCCESS.getErrorCode(), true, "查询成功",
            list);
    }

}
