package com.medical.dtms.web.controller.syslog;

import com.github.pagehelper.PageInfo;
import com.medical.dtms.common.enumeration.ErrorCodeEnum;
import com.medical.dtms.common.model.syslog.QMSSysLogDetailsModel;
import com.medical.dtms.common.resp.Result;
import com.medical.dtms.dto.log.query.QMSSysLogsQuery;
import com.medical.dtms.feignservice.syslogs.SysLogsService;
import com.medical.dtms.common.model.syslog.QMSSysLogsModel;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @version： SysLogsController.java v 1.0, 2019年08月19日 11:33 wuxuelin Exp$
 * @Description
 **/
@RestController
public class SysLogsController {

    @Autowired
    private SysLogsService logsService;

    /**
     * 系统日志 - 操作日志列表分页查询
     *
     * @param [query]
     * @return
     **/
    @RequestMapping(value = "/syslog/pageListSysLogs", method = RequestMethod.POST)
    public Result<PageInfo<QMSSysLogsModel>> pageListSysLogs(@RequestBody QMSSysLogsQuery query) {
        checkParams(query);
        PageInfo<QMSSysLogsModel> pageInfo = logsService.pageListSysLogs(query);
        return Result.buildSuccess(pageInfo);
    }

    /**
     * 根据 系统日志 id 查询 系统操作日志明细
     *
     * @param query
     * @return
     **/
    @RequestMapping(value = "/syslog/listQMSSysLogDetails", method = RequestMethod.POST)
    public Result<List<QMSSysLogDetailsModel>> listQMSSysLogDetails(@RequestBody QMSSysLogsQuery query) {
        if (null == query || null == query.getLogId()) {
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), ErrorCodeEnum.PARAM_IS_EMPTY.getErrorMessage());
        }
        List<QMSSysLogDetailsModel> list = logsService.listQMSSysLogDetails(query);
        return Result.buildSuccess(list);
    }

    @RequestMapping(value = "/syslog/addSysLog")
    public String addSysLog(@RequestParam("logJsonString") String logJsonString) {
        logsService.addSysLog(logJsonString);
        return "success";
    }

    /**
     * 分页参数校验
     *
     * @param query
     */
    private void checkParams(QMSSysLogsQuery query) {
        if (null == query) {
            query = new QMSSysLogsQuery();
        }
        if (null == query.getPageNo()) {
            query.setPageNo(1);
        }
        if (null == query.getPageSize()) {
            query.setPageSize(10);
        }
        query.setBusinessName(
                StringUtils.isBlank(query.getBusinessName()) == true ? null : query.getBusinessName().replaceAll("%", "\\\\%"));
        query.setOperationUser(
                StringUtils.isBlank(query.getOperationUser()) == true ? null : query.getOperationUser().replaceAll("%", "\\\\%"));
    }
}
