package com.medical.dtms.service.serviceimpl.job;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.medical.dtms.common.eception.BizException;
import com.medical.dtms.common.enumeration.ErrorCodeEnum;
import com.medical.dtms.common.model.job.QMSJobsModel;
import com.medical.dtms.common.util.IdGenerator;
import com.medical.dtms.dto.job.QMSJobsDTO;
import com.medical.dtms.dto.job.query.QMSJobsQuery;
import com.medical.dtms.dto.user.QMSUserInJobsDTO;
import com.medical.dtms.dto.user.query.BaseUserQuery;
import com.medical.dtms.feignservice.job.QMSJobsService;
import com.medical.dtms.service.manager.job.QMSJobsManager;
import com.medical.dtms.service.manager.user.QMSUserInJobsManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @version： QMSJobsServiceImpl.java v 1.0, 2019年08月23日 13:35 huangshuaiquan Exp$
 * @Description 岗位管理
 **/
@RestController
@Slf4j
public class QMSJobsServiceImpl implements QMSJobsService {

    @Autowired
    private QMSJobsManager jobsManager;
    @Autowired
    private IdGenerator idGenerator;
    @Autowired
    private QMSUserInJobsManager userInJobsManager;


    /**
     * @param [jobsDTO]
     * @return java.lang.Boolean
     * @description 岗位管理 - 添加功能
     **/
    @Override
    public Boolean addQMSJob(@RequestBody QMSJobsDTO jobsDTO) {
        // 根据职位名称 做唯一性校验
        QMSJobsQuery query = new QMSJobsQuery(jobsDTO.getJobName());
        QMSJobsDTO dto = jobsManager.getQMSJobByCondition(query);
        if (null != dto) {
            // 存在,提示重复
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "职位已存在，请重新填写");
        }
        try {
            //不存在 就新增
            jobsDTO.setBizId(idGenerator.nextId());
            jobsManager.insert(jobsDTO);
        } catch (Exception e) {
            log.error("新增职位失败", e);
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
        }
        return true;
    }

    /**
     * @param [jobsDTO]
     * @return java.lang.Boolean
     * @description 岗位管理 - 删除功能(逻辑删除)
     **/
    @Override
    @Transactional(rollbackFor = {BizException.class})
    public Boolean deleteQMSJob(@RequestBody QMSJobsDTO jobsDTO) {

        // 校验是否被删除
        QMSJobsQuery query = new QMSJobsQuery(jobsDTO.getBizId());
        QMSJobsDTO dto = jobsManager.getQMSJobByCondition(query);
        if (null == dto) {
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "该职位被删除，请勿重复操作");
        }

        // 未被删除
        // 校验能否被删除
        if (null != dto.getAllowDelete() && !dto.getAllowDelete()) {
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "该职位不允许被删除");
        }

        // 删除与用户的关联
        BaseUserQuery jobsQuery = new BaseUserQuery();
        jobsQuery.setJobId(jobsDTO.getBizId());
        List<QMSUserInJobsDTO> jobsDTOS = userInJobsManager.listQMSUserInJob(jobsQuery);
        if (CollectionUtils.isNotEmpty(jobsDTOS)) {
            try {
                userInJobsManager.deleteQMSUserInJobByJobId(jobsDTO.getBizId());
            } catch (Exception e) {
                log.error("删除岗位时，删除用户关联关系失败", e);
                throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
            }
        }


        try {
            jobsDTO.setIsDeleted(true);
            jobsManager.updateQMSJob(jobsDTO);
        } catch (Exception e) {
            log.error("岗位删除失败", e);
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
        }


        return true;
    }

    /**
     * @param [jobsDTO]
     * @return java.lang.Boolean
     * @description 岗位管理 - 编辑功能
     **/
    @Override
    public Boolean updateQMSJob(@RequestBody QMSJobsDTO jobsDTO) {

        // 校验是否被删除
        QMSJobsQuery query = new QMSJobsQuery(jobsDTO.getBizId());
        QMSJobsDTO dto = jobsManager.getQMSJobByCondition(query);
        if (null == dto) {
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "该职位被删除，无法操作");
        }

        // 未被删除
        // 校验能否被编辑
        if (null != dto.getAllowEdit() && !dto.getAllowEdit()) {
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "该职位不允许被编辑");
        }

        try {
            jobsManager.updateQMSJob(jobsDTO);
        } catch (Exception e) {
            log.error("更新职位失败", e);
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
        }

        return true;
    }

    /**
     * @param [query]
     * @return com.github.pagehelper.PageInfo<com.medical.dtms.model.job.QMSJobsModel>
     * @description 岗位管理 - 列表查询功能
     **/
    @Override
    public PageInfo<QMSJobsModel> getPageListJobs(@RequestBody QMSJobsQuery query) {
        PageHelper.startPage(query.getPageNo(), query.getPageSize());
        List<QMSJobsModel> models = jobsManager.listJobs(query);
        if (CollectionUtils.isEmpty(models)) {
            return new PageInfo<>(new ArrayList<>());
        }
        return new PageInfo<>(models);
    }
}
