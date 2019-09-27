/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.medical.dtms.service.serviceimpl.exam;

import java.util.ArrayList;
import java.util.List;

import com.medical.dtms.service.manager.exam.ExamUserAnswerModelManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.medical.dtms.common.eception.BizException;
import com.medical.dtms.common.enumeration.ErrorCodeEnum;
import com.medical.dtms.common.util.IdGenerator;
import com.medical.dtms.dto.exam.ExamPlanModelDTO;
import com.medical.dtms.dto.exam.ExamUserPlanModelDTO;
import com.medical.dtms.dto.exam.query.ExamPlanModelQuery;
import com.medical.dtms.feignservice.exam.ExamPlanModelService;
import com.medical.dtms.service.manager.exam.ExamPlanModelManager;
import com.medical.dtms.service.manager.exam.ExamUserPlanModelManager;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author shenqifeng
 * @version $Id: ExamPlanModelServiceImpl.java, v 0.1 2019年9月9日 下午10:52:07 shenqifeng Exp $
 */
@Slf4j
@RestController
public class ExamPlanModelServiceImpl implements ExamPlanModelService {
    @Autowired
    private ExamPlanModelManager     examPlanModelManager;
    @Autowired
    private IdGenerator              idGenerator;
    @Autowired
    private ExamUserPlanModelManager examUserPlanModelManager;
    @Autowired
    private ExamUserAnswerModelManager examUserAnswerModelManager;


    @Override
    @Transactional
    public Boolean deleteExamPlanModel(@RequestBody ExamPlanModelDTO dto) {
        //校验是否被删除
        ExamPlanModelDTO examPlanModelDTO = examPlanModelManager.getExamPlanModelById(dto.getExamPlanModelId());
        if (null == examPlanModelDTO){
            log.error("考试安排不存在或已被删除");
            throw new BizException(ErrorCodeEnum.NO_DATA.getErrorCode(), "考试安排不存在或已被删除");
        }
        // 未被删除
        //删除与用户的关联
        try {
            examUserPlanModelManager.deleteByExamPlanId(dto.getExamPlanModelId());
        } catch (Exception e) {
            log.error("删除考试安排时,删除考试安排与用户的关联失败", e);
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
        }
        //删除与用户答案的关联
        try {
            examUserAnswerModelManager.deleteByExamPlanId(dto.getExamPlanModelId());
        } catch (Exception e) {
            log.error("删除考试安排时,删除考试安排与用户答案的关联失败", e);
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
        }
        //删除考试安排
        try {
            examPlanModelManager.deleteExamPlanModelByPlanModeID(dto);
        } catch (Exception e) {
            log.error("删除考试安排失败", e);
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
        }

        return true;
    }

    @Override
    public Boolean insertExamPlanModel(@RequestBody ExamPlanModelDTO examPlanModelDTO) {
        examPlanModelDTO.setExamPlanModelId(idGenerator.nextId());
        return examPlanModelManager.insertExamPlanModel(examPlanModelDTO);
    }

    @Override
    @Transactional
    public Boolean updateExamPlanModel(@RequestBody ExamPlanModelDTO examPlanModelDTO) {
        //1.修改考试安排
        examPlanModelManager.updateExamPlanModel(examPlanModelDTO);
        //2.修改用户考试开启
        examUserPlanModelManager.updateStart(examPlanModelDTO.getExamPlanModelId());
        return true;
    }

    @Override
    public ExamPlanModelDTO getExamPlanModelById(@RequestParam("examPlanModelId") Long examPlanModelId) {
        return examPlanModelManager.getExamPlanModelById(examPlanModelId);
    }

    @Override
    public PageInfo<ExamPlanModelDTO> listExamPlanModelByQuery(@RequestBody ExamPlanModelQuery query) {
        PageHelper.startPage(query.getPageNo(), query.getPageSize());
        List<ExamPlanModelDTO> list = examPlanModelManager.listExamPlanModelByQuery(query);
        PageInfo<ExamPlanModelDTO> paginfo = new PageInfo<>(list);
        return paginfo;
    }

    /**
     * 人员配置
     * @see com.medical.dtms.feignservice.exam.ExamPlanModelService#staffConfig(com.medical.dtms.dto.exam.ExamPlanModelDTO)
     */
    @Override
    @Transactional
    public Boolean staffConfig(@RequestBody ExamPlanModelDTO examPlanModelDTO) {
        //1.获取详情
        ExamPlanModelDTO examPlanModelById = examPlanModelManager
            .getExamPlanModelById(examPlanModelDTO.getExamPlanModelId());
        if (null == examPlanModelById) {
            log.error("考试安排不存在");
            throw new BizException(ErrorCodeEnum.NO_DATA.getErrorCode(), "考试安排不存在");
        }
        if (null != examPlanModelById.getIsStart() && examPlanModelById.getIsStart()) {
            log.error("考试安排已经启动");
            throw new BizException(ErrorCodeEnum.NO_DATA.getErrorCode(), "考试安排已经启动，不能进行配置");
        }
        //2.删除原先的人员
        examUserPlanModelManager.deleteByExamPlanId(examPlanModelDTO.getExamPlanModelId());
        //3.新增
        List<Long> userIds = examPlanModelDTO.getUserIds();
        List<ExamUserPlanModelDTO> list = new ArrayList<ExamUserPlanModelDTO>();
        ExamUserPlanModelDTO examUserPlanModelDTO = null;
        for (Long userId : userIds) {
            examUserPlanModelDTO = new ExamUserPlanModelDTO();
            examUserPlanModelDTO.setExamUserPlanModelId(idGenerator.nextId());
            examUserPlanModelDTO.setCreateUserId(examPlanModelDTO.getModifyUserId());
            examUserPlanModelDTO.setCreateUserName(examPlanModelDTO.getModifyUserName());
            examUserPlanModelDTO.setEndDate(examPlanModelById.getEndDate());
            examUserPlanModelDTO.setExamDuration(examPlanModelById.getExamDuration());
            examUserPlanModelDTO.setExamId(examPlanModelById.getExamId());
            examUserPlanModelDTO.setExamPlanId(examPlanModelById.getExamPlanModelId());
            examUserPlanModelDTO.setModifyUserId(examPlanModelDTO.getModifyUserId());
            examUserPlanModelDTO.setModifyUserName(examPlanModelDTO.getModifyUserName());
            examUserPlanModelDTO.setPassPoints(examPlanModelById.getPassPoints());
            examUserPlanModelDTO.setStartDate(examPlanModelById.getStartDate());
            examUserPlanModelDTO.setTotalPoints(examPlanModelById.getTotalPoints());
            examUserPlanModelDTO.setUserId(userId);
            list.add(examUserPlanModelDTO);
        }
        examUserPlanModelManager.insertBatchExamUserPlanModel(list);
        return true;
    }
}
