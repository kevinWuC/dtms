package com.medical.dtms.feignservice.syslogs;

import com.github.pagehelper.PageInfo;
import com.medical.dtms.common.model.syslog.QMSSysLogDetailsModel;
import com.medical.dtms.dto.log.query.QMSSysLogsQuery;
import com.medical.dtms.common.model.syslog.QMSSysLogsModel;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @version： SysLogsService.java v 1.0, 2019年08月19日 11:30 wuxuelin Exp$
 * @Description
 **/
@FeignClient("service-dtms")
public interface SysLogsService {

    @RequestMapping(value = "/syslog/pageListSysLogs", method = RequestMethod.POST)
    PageInfo<QMSSysLogsModel> pageListSysLogs(@RequestBody QMSSysLogsQuery query);

    @RequestMapping(value = "/syslog/listQMSSysLogDetails", method = RequestMethod.POST)
    List<QMSSysLogDetailsModel> listQMSSysLogDetails(@RequestBody QMSSysLogsQuery query);
}
