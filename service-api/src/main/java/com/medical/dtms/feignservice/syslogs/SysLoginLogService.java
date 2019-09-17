package com.medical.dtms.feignservice.syslogs;

import com.github.pagehelper.PageInfo;
import com.medical.dtms.dto.log.QMSSysLoginLogDTO;
import com.medical.dtms.dto.log.query.QMSSysLoginLogQuery;
import com.medical.dtms.common.model.syslog.SysLoginLogModel;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @version： SysLoginLogService.java v 1.0, 2019年08月20日 17:09 huangshuaiquan Exp$
 * @Description 登录日志
 **/
@FeignClient("service-dtms")
public interface SysLoginLogService {

    @RequestMapping(value = "/loginLogs/addLoginLog", method = RequestMethod.POST)
    Boolean addLoginLog(@RequestBody QMSSysLoginLogDTO loginLogDTO);

    @RequestMapping(value = "/loginLogs/listLoginLogs", method = RequestMethod.POST)
    PageInfo<SysLoginLogModel> pageListLoginLogs(@RequestBody QMSSysLoginLogQuery query);
}
