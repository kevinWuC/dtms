package com.medical.dtms.service.serviceimpl.syslogs;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.medical.dtms.common.eception.BizException;
import com.medical.dtms.common.enumeration.ErrorCodeEnum;
import com.medical.dtms.common.util.BeanConvertUtils;
import com.medical.dtms.common.util.IdGenerator;
import com.medical.dtms.dto.log.QMSSysLoginLogDTO;
import com.medical.dtms.dto.log.query.QMSSysLoginLogQuery;
import com.medical.dtms.feignservice.syslogs.SysLoginLogService;
import com.medical.dtms.common.model.syslog.SysLoginLogModel;
import com.medical.dtms.service.dataobject.log.QMSSysLoginLogDO;
import com.medical.dtms.service.manager.syslogs.SysLoginLogManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @version： SysLoginLogServiceImpl.java v 1.0, 2019年08月20日 16:56 wuxuelin Exp$
 * @Description
 **/
@RestController
@Slf4j
public class SysLoginLogServiceImpl implements SysLoginLogService {

    @Autowired
    private SysLoginLogManager loginLogManager;
    @Autowired
    private IdGenerator idGenerator;

    /**
    * @description 登录日志 - 添加功能
    * @param [loginDTO]
    * @return java.lang.Boolean
    **/
    @Override
    public Boolean addLoginLog(@RequestBody QMSSysLoginLogDTO loginLogDTO) {

        try {
            loginLogDTO.setBizId(idGenerator.nextId());
            loginLogManager.insert(loginLogDTO);
        } catch (Exception e) {
           log.error("新增登陆日志失败",e);
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
        }

        return true;
    }

    /**
    * @description 登录日志 - 列表查询功能
    * @param  [query]
    * @return com.github.pagehelper.PageInfo<com.medical.dtms.model.syslog.SysLoginLogModel>
    **/
    @Override
    public PageInfo<SysLoginLogModel> pageListLoginLogs(@RequestBody QMSSysLoginLogQuery query) {
        PageHelper.startPage(query.getPageNo(), query.getPageSize());
        List<SysLoginLogModel> models = loginLogManager.listLoginLogs(query);
        if (CollectionUtils.isEmpty(models)) {
            return new PageInfo<>(new ArrayList<>());
        }
        return new PageInfo<>(models);
    }
}
