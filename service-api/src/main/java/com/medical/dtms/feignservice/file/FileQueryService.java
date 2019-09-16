package com.medical.dtms.feignservice.file;

import com.github.pagehelper.PageInfo;
import com.medical.dtms.common.model.file.ArchiveOrInvalidModel;
import com.medical.dtms.common.model.file.ExportModel;
import com.medical.dtms.common.model.file.FileQueryModel;
import com.medical.dtms.dto.file.FileQueryDTO;
import com.medical.dtms.dto.file.query.FileModelQuery;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @version： FileInDeptRoleService.java v 1.0, 2019年08月28日 11:27 wuxuelin Exp$
 * @Description
 **/
@FeignClient("service-dtms")
public interface FileQueryService {

    @RequestMapping(value = "/query/addFileInDeptRole", method = RequestMethod.POST)
    Boolean addFileInDeptRole(@RequestBody FileQueryDTO dto);

    @RequestMapping(value = "/query/deleteFileInFileQuery", method = RequestMethod.POST)
    Boolean deleteFileInFileQuery(@RequestBody FileQueryDTO dto);

    @RequestMapping(value = "/query/exportFiles", method = RequestMethod.POST)
    List<ExportModel> exportFiles(@RequestBody FileModelQuery query);

    @RequestMapping(value = "/query/pageListQueryFiles", method = RequestMethod.POST)
    PageInfo<FileQueryModel> pageListQueryFiles(@RequestBody FileModelQuery query);

    @RequestMapping(value = "/query/pageListMyFiles", method = RequestMethod.POST)
    PageInfo<FileQueryModel> pageListMyFiles(@RequestBody FileModelQuery query);
}
