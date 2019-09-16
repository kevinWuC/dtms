package com.medical.dtms.web.controller.syslog;

import com.github.pagehelper.PageInfo;
import com.medical.dtms.common.enumeration.ErrorCodeEnum;
import com.medical.dtms.common.resp.Result;
import com.medical.dtms.dto.log.QMSSysLoginLogDTO;
import com.medical.dtms.dto.log.query.QMSSysLoginLogQuery;
import com.medical.dtms.feignservice.syslogs.SysLoginLogService;
import com.medical.dtms.common.model.syslog.SysLoginLogModel;
import com.medical.dtms.common.login.OperatorInfo;
import com.medical.dtms.common.login.SessionTools;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @version： SysLoginLogController.java v 1.0, 2019年08月21日 10:11 huangshuaiquan Exp$
 * @Description 登录日志 - 控制类
 **/
@RestController
public class SysLoginLogController {

    @Autowired
    private SysLoginLogService loginLogService;

    /**
    * @description 登录日志 - 添加功能
    * @param  [loginLogDTO]
    * @return  com.medical.dtms.common.resp.Result<java.lang.Boolean>
    **/
    @RequestMapping(value = "/loginLogs/addLoginLog", method = RequestMethod.POST)
    public Result<Boolean> addLoginLog(@RequestBody QMSSysLoginLogDTO loginLogDTO) {

        if (null == loginLogDTO){
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(),"所有内容均为必填项，请填写完整");
        }
        if (null == loginLogDTO.getCreateDate()){
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "发生时间为空");
        }

        if (StringUtils.isBlank(loginLogDTO.getAccount())){
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "登陆账户为空");
        }
        if (null == loginLogDTO.getLoginLogStatus()){
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "登录状态为空");
        }
        if (StringUtils.isBlank(loginLogDTO.getIpAddress())){
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "IP地址为空");
        }
        if (StringUtils.isBlank(loginLogDTO.getIpAddressName())){
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "IP地址所在地为空");
        }
        OperatorInfo info = SessionTools.getOperator();
        loginLogDTO.setCreator(info.getDspName());
        loginLogDTO.setCreatorId(info.getBizId());
        loginLogService.addLoginLog(loginLogDTO);
        return Result.buildSuccess(true);
    }


    /**
    * @description 登录日志 - 列表查询功能
    * @param  [query]
    * @return  com.medical.dtms.common.resp.Result<com.github.pagehelper.PageInfo<com.medical.dtms.model.syslog.SysLoginLogModel>>
    **/
    @RequestMapping(value = "/loginLogs/listLoginLogs", method = RequestMethod.POST)
    public Result<PageInfo<SysLoginLogModel>> pageListLoginLogs(@RequestBody QMSSysLoginLogQuery query){
        if (null != query && null != query.getBeginTime()&& null != query.getEndTime() && query.getBeginTime().after(query.getEndTime())){
            return Result.buildFailed(ErrorCodeEnum.PARAM_ERROR.getErrorCode(),"开始时间不能大于结束时间");
        }
        checkParams(query);
        PageInfo<SysLoginLogModel> pageInfo = loginLogService.pageListLoginLogs(query);
        return Result.buildSuccess(pageInfo);
    }

    /**
     * 分页参数校验
     * @param query
     */
    private void checkParams(QMSSysLoginLogQuery query) {
        if (null == query) {
            query = new QMSSysLoginLogQuery();
        }
        if (null == query.getPageNo()) {
            query.setPageNo(1);
        }
        if (null == query.getPageSize()) {
            query.setPageSize(100);
        }
    }
}
