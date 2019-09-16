package com.medical.dtms.feignservice.file;

import com.github.pagehelper.PageInfo;
import com.medical.dtms.common.model.file.FileYearModel;
import com.medical.dtms.common.model.file.FileYearRecordExportModel;
import com.medical.dtms.common.model.file.FileYearRecordModel;
import com.medical.dtms.dto.file.FileYearDTO;
import com.medical.dtms.dto.file.query.FileModelQuery;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @version： FileYearService.java v 1.0, 2019年08月28日 18:29 wuxuelin Exp$
 * @Description
 **/
@FeignClient("service-dtms")
public interface FileYearService {

    @RequestMapping(value = "/year/insertFileYear",method = RequestMethod.POST)
    Boolean insertFileYear(@RequestBody FileYearDTO dto);

    @RequestMapping(value = "/year/pageListFileYearRecord", method = RequestMethod.POST)
    PageInfo<FileYearRecordModel> pageListFileYearRecord(@RequestBody FileModelQuery query);

    @RequestMapping(value = "/year/pageListFileYear", method = RequestMethod.POST)
    PageInfo<FileYearModel> pageListFileYear(@RequestBody FileModelQuery query);

    @RequestMapping(value = "/file/exportFileYearRecord", method = RequestMethod.POST)
    List<FileYearRecordExportModel>  exportFileYearRecord(@RequestBody FileModelQuery query);
}
