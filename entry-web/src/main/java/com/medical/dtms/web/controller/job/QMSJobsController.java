package com.medical.dtms.web.controller.job;

import com.github.pagehelper.PageInfo;
import com.medical.dtms.common.enumeration.ErrorCodeEnum;
import com.medical.dtms.common.resp.Result;
import com.medical.dtms.dto.job.QMSJobsDTO;
import com.medical.dtms.dto.job.query.QMSJobsQuery;
import com.medical.dtms.feignservice.job.QMSJobsService;
import com.medical.dtms.common.model.job.QMSJobsModel;
import com.medical.dtms.common.login.OperatorInfo;
import com.medical.dtms.common.login.SessionTools;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @version： QMSJobsController.java v 1.0, 2019年08月23日 15:49 huangshuaiquan Exp$
 * @Description 岗位管理
 **/
@RestController
public class QMSJobsController {

    @Autowired
    private QMSJobsService jobsService;

    /**
     * @param [jobsDTO]
     * @return com.medical.dtms.common.resp.Result<java.lang.Boolean>
     * @description 岗位管理 - 添加功能
     **/
    @RequestMapping(value = "/jobs/addQMSJob", method = RequestMethod.POST)
    public Result<Boolean> addQMSJob(@RequestBody QMSJobsDTO jobsDTO) {

        if (null == jobsDTO) {
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "请填写完整");
        }

        if (StringUtils.isBlank(jobsDTO.getJobName())) {
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "职位名称为空");
        }

        /*if (null == jobsDTO.getSortCode()){
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "排序码为空");
        }*/

        if (StringUtils.isBlank(jobsDTO.getDeptId())) {
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "部门id为空");
        }

        if (StringUtils.isBlank(jobsDTO.getDeptName())) {
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "部门名称为空");
        }

        if (null == jobsDTO.getAllowQuery() || null == jobsDTO.getAllowRead() || null == jobsDTO.getAllowDelete() || null == jobsDTO.getAllowEdit() || null == jobsDTO.getAllowEnable()) {
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "选项为空");
        }

        OperatorInfo info = SessionTools.getOperator();
        jobsDTO.setCreator(info.getDspName());
        jobsDTO.setCreatorId(info.getUserId());
        jobsService.addQMSJob(jobsDTO);

        return Result.buildSuccess(true);
    }

    /**
     * @param [jobsDTO]
     * @return com.medical.dtms.common.resp.Result<java.lang.Boolean>
     * @description 岗位管理 - 删除功能
     **/
    @RequestMapping(value = "/jobs/deleteQMSJob", method = RequestMethod.POST)
    public Result<Boolean> deleteQMSJob(@RequestBody QMSJobsDTO jobsDTO) {
        if (null == jobsDTO || null == jobsDTO.getBizId()) {
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), ErrorCodeEnum.PARAM_IS_EMPTY.getErrorMessage());
        }
        OperatorInfo info = SessionTools.getOperator();
        jobsDTO.setModifier(info.getDspName());
        jobsDTO.setModifierId(info.getUserId());
        jobsService.deleteQMSJob(jobsDTO);
        return Result.buildSuccess(true);
    }

    /**
     * @param [jobsDTO]
     * @return com.medical.dtms.common.resp.Result<java.lang.Boolean>
     * @description 岗位管理 - 编辑功能
     **/
    @RequestMapping(value = "/jobs/updateQMSJob", method = RequestMethod.POST)
    public Result<Boolean> updateQMSJob(@RequestBody QMSJobsDTO jobsDTO) {

        if (null == jobsDTO || null == jobsDTO.getBizId()) {
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), ErrorCodeEnum.PARAM_IS_EMPTY.getErrorMessage());
        }

        OperatorInfo info = SessionTools.getOperator();
        jobsDTO.setModifier(info.getDspName());
        jobsDTO.setModifierId(info.getUserId());
        jobsService.updateQMSJob(jobsDTO);
        return Result.buildSuccess(true);
    }

    /**
     * @param [query]
     * @return com.medical.dtms.common.resp.Result<com.github.pagehelper.PageInfo < com.medical.dtms.model.job.QMSJobsModel>>
     * @description 岗位管理 - 列表查询功能
     **/
    @RequestMapping(value = "/jobs/getPageListJobs", method = RequestMethod.POST)
    public Result<PageInfo<QMSJobsModel>> getPageListJobs(@RequestBody QMSJobsQuery query) {
        checkParams(query);
        PageInfo<QMSJobsModel> pageListJobs = jobsService.getPageListJobs(query);
        return Result.buildSuccess(pageListJobs);
    }

    /**
     * 分页参数校验
     *
     * @param query
     */
    private void checkParams(QMSJobsQuery query) {
        if (null == query) {
            query = new QMSJobsQuery();
        }
        if (null == query.getPageNo()) {
            query.setPageNo(1);
        }
        if (null == query.getPageSize()) {
            query.setPageSize(10);
        }
    }
}
