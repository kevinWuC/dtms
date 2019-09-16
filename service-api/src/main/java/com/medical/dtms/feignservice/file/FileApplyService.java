package com.medical.dtms.feignservice.file;

import com.github.pagehelper.PageInfo;
import com.medical.dtms.common.model.file.ApplyCheckModel;
import com.medical.dtms.common.model.file.FileApplyDetailModel;
import com.medical.dtms.common.model.file.FileApplyModel;
import com.medical.dtms.dto.file.FileApplyDTO;
import com.medical.dtms.dto.file.query.FileModelQuery;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @version： FileApplyService.java v 1.0, 2019年08月30日 15:58 wuxuelin Exp$
 * @Description
 **/
@FeignClient("service-dtms")
public interface FileApplyService {

    @RequestMapping(value = "/apply/addFileApply", method = RequestMethod.POST)
    Boolean addFileApply(@RequestBody FileApplyDTO dto);

    @RequestMapping(value = "/apply/pageListApply", method = RequestMethod.POST)
    PageInfo<FileApplyModel> pageListApply(@RequestBody FileModelQuery query);

    @RequestMapping(value = "/apply/agreeOrDisThisApply", method = RequestMethod.POST)
    Boolean agreeOrDisThisApply(@RequestBody FileApplyDTO dto);

    @RequestMapping(value = "/apply/checkOptionLegalOrNot", method = RequestMethod.POST)
    Boolean checkOptionLegalOrNot(@RequestBody FileApplyDTO dto);

    @RequestMapping(value = "/apply/pageListApplyCheck", method = RequestMethod.POST)
    PageInfo<ApplyCheckModel> pageListApplyCheck(@RequestBody FileModelQuery query);

    @RequestMapping(value = "/apply/selectByPrimaryKey", method = RequestMethod.POST)
    FileApplyDetailModel selectByPrimaryKey(@RequestBody FileModelQuery query);
}
