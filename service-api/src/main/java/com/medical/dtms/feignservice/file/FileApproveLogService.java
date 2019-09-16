package com.medical.dtms.feignservice.file;

import com.github.pagehelper.PageInfo;
import com.medical.dtms.common.model.file.FileApproveLogModel;
import com.medical.dtms.dto.file.query.FileApproveLogQuery;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @version： FileApproveLogService.java v 1.0, 2019年08月28日 9:57 wuxuelin Exp$
 * @Description
 **/
@FeignClient("service-dtms")
public interface FileApproveLogService {

    @RequestMapping(value = "/log/pageListLogInfo", method = RequestMethod.POST)
    PageInfo<FileApproveLogModel> pageListLogInfo(@RequestBody FileApproveLogQuery query);

    @RequestMapping(value = "/log/pageListInsertLog", method = RequestMethod.POST)
    PageInfo<FileApproveLogModel> pageListInsertLog(@RequestBody FileApproveLogQuery query);

    @RequestMapping(value = "/log/pageListUpdateLog", method = RequestMethod.POST)
    PageInfo<FileApproveLogModel> pageListUpdateLog(@RequestBody FileApproveLogQuery query);
}
