package com.medical.dtms.feignservice.file;

import com.github.pagehelper.PageInfo;
import com.medical.dtms.common.model.file.ArchiveOrInvalidModel;
import com.medical.dtms.common.model.file.BackFileModel;
import com.medical.dtms.common.model.file.FileDetailModel;
import com.medical.dtms.common.model.file.FileModel;
import com.medical.dtms.dto.file.FileModelDTO;
import com.medical.dtms.dto.file.query.FileModelQuery;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @version： FileService.java v 1.0, 2019年08月27日 11:07 wuxuelin Exp$
 * @Description
 **/
@FeignClient("service-dtms")
public interface FileService {

    @RequestMapping(value = "/file/addFile", method = RequestMethod.POST)
    Boolean addFile(@RequestBody FileModelDTO dto);

    @RequestMapping(value = "/file/updateFile", method = RequestMethod.POST)
    Boolean updateFile(@RequestBody FileModelDTO dto);

    @RequestMapping(value = "/file/deleteFile", method = RequestMethod.POST)
    Boolean deleteFile(@RequestBody FileModelDTO dto);

    @RequestMapping(value = "/file/pageListFile", method = RequestMethod.POST)
    PageInfo<FileModel> pageListFile(FileModelQuery query);

    @RequestMapping(value = "/file/pageListArchiveOrInvalidFile", method = RequestMethod.POST)
    PageInfo<ArchiveOrInvalidModel> pageListArchiveOrInvalidFile(@RequestBody FileModelQuery query);

    @RequestMapping(value = "/file/pageListBackFile", method = RequestMethod.POST)
    PageInfo<BackFileModel> pageListBackFile(@RequestBody FileModelQuery query);

    @RequestMapping(value = "/file/selectByPrimaryKey", method = RequestMethod.POST)
    FileDetailModel selectByPrimaryKey(@RequestBody FileModelQuery query);

    @RequestMapping(value = "/file/exportArchiveFile", method = RequestMethod.POST)
    List<ArchiveOrInvalidModel> exportArchiveOrInvalidFile(@RequestBody FileModelQuery query);
}
