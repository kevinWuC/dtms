package com.medical.dtms.feignservice.file;

import com.github.pagehelper.PageInfo;
import com.medical.dtms.common.model.file.FileApprovalModel;
import com.medical.dtms.common.model.file.FileAttachmentModel;
import com.medical.dtms.common.model.file.FileReceiveModel;
import com.medical.dtms.dto.file.FileAttachmentsDTO;
import com.medical.dtms.dto.file.FileModelDTO;
import com.medical.dtms.dto.file.query.FileModelQuery;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @version： FileApprovalService.java v 1.0, 2019年08月29日 16:03 wuxuelin Exp$
 * @Description
 **/
@FeignClient("service-dtms")
public interface FileApprovalService {

    @RequestMapping(value = "/approval/pageListFilesInApproval", method = RequestMethod.POST)
    PageInfo<FileApprovalModel> pageListFilesInApproval(@RequestBody FileModelQuery query);

    @RequestMapping(value = "/approval/startFileApproval", method = RequestMethod.POST)
    Boolean startFileApproval(@RequestBody FileModelDTO dto);

    @RequestMapping(value = "/receive/pageListForReceive", method = RequestMethod.POST)
    PageInfo<FileReceiveModel> pageListForReceive(@RequestBody FileModelQuery query);

    @RequestMapping(value = "/receive/confirmReceive", method = RequestMethod.POST)
    Boolean confirmReceive(@RequestBody FileModelDTO dto);

    @RequestMapping(value = "/approval/pageListFileAttach", method = RequestMethod.POST)
    List<FileAttachmentModel> pageListFileAttach(@RequestBody FileModelQuery query);
}
