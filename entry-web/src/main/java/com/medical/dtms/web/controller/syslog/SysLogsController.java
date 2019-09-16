package com.medical.dtms.web.controller.syslog;

import com.github.pagehelper.PageInfo;
import com.medical.dtms.common.resp.Result;
import com.medical.dtms.dto.log.query.QMSSysLogsQuery;
import com.medical.dtms.feignservice.syslogs.SysLogsService;
import com.medical.dtms.common.model.syslog.QMSSysLogsModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @version： SysLogsController.java v 1.0, 2019年08月19日 11:33 wuxuelin Exp$
 * @Description
 **/
@RestController
public class SysLogsController {

    @Autowired
    private SysLogsService logsService;

    /**
     * @param [query]
     * @return com.medical.dtms.common.resp.Result<com.github.pagehelper.PageInfo < com.medical.dtms.model.syslog.QMSSysLogsModel>>
     * @description 系统日志 - 操作日志列表分页查询
     **/
    @RequestMapping(value = "/syslog/pageListSysLogs", method = RequestMethod.POST)
    public Result<PageInfo<QMSSysLogsModel>> pageListSysLogs(@RequestBody QMSSysLogsQuery query) {
        checkParams(query);
        PageInfo<QMSSysLogsModel> pageInfo = logsService.pageListSysLogs(query);
        return Result.buildSuccess(pageInfo);
    }


    /**
     * 获取客户端 ip
     */
    @RequestMapping(value = "/syslog/getRequestIP", method = RequestMethod.POST)
    public Result<String> getRequestIP(HttpServletRequest request) throws Exception {
//        String ip = request.getHeader("X-forwarded-for");
        String ip = request.getRemoteAddr();
        System.out.println("ip:" + ip);





        return Result.buildSuccess(ip);
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
    }
}
