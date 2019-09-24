package com.medical.dtms.feignservice.dropdown;

import com.github.pagehelper.PageInfo;
import com.medical.dtms.common.model.dropdown.DropDownDetailsModel;
import com.medical.dtms.common.model.dropdown.DropDownModel;
import com.medical.dtms.common.model.dropdown.query.DropDownQuery;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @version： DropDownService.java v 1.0, 2019年09月04日 11:20 wuxuelin Exp$
 * @Description
 **/
@FeignClient("service-dtms")
public interface DropDownService {

    @RequestMapping(value = "/dropdown/listFileTypes")
    PageInfo<DropDownModel> listFileTypes(@RequestBody DropDownQuery query);

    @RequestMapping(value = "/dropdown/listApplyTypesInFileModel")
    PageInfo<DropDownModel> listApplyTypesInFileModel(@RequestBody DropDownQuery query);

    @RequestMapping(value = "/dropdown/listReceiveDept")
    PageInfo<DropDownModel> listReceiveDept(@RequestBody DropDownQuery query);

    @RequestMapping(value = "/dropdown/listApplyDeptInFileModel")
    PageInfo<DropDownModel> listApplyDeptInFileModel(@RequestBody DropDownQuery query);

    @RequestMapping(value = "/dropdown/listUsers")
    PageInfo<DropDownModel> listUsers(@RequestBody DropDownQuery query);

    @RequestMapping(value = "/dropdown/listYears")
    PageInfo<DropDownModel> listYears(@RequestBody DropDownQuery query);

    @RequestMapping(value = "/dropdown/listRolesInFileModel")
    List<DropDownModel> listRolesInFileModel(@RequestBody DropDownQuery query);

    @RequestMapping(value = "/dropdown/listAllFileTypes")
    PageInfo<DropDownModel> listAllFileTypes(@RequestBody DropDownQuery query);

    @RequestMapping(value = "/dropdown/listAllApplyReasons")
    PageInfo<DropDownModel> listAllApplyReasons(@RequestBody DropDownQuery query);

    @RequestMapping(value = "/dropdown/listAllApplyTypes")
    PageInfo<DropDownModel> listAllApplyTypes(@RequestBody DropDownQuery query);

    @RequestMapping(value = "/dropdown/listTypeDetails")
    PageInfo<DropDownDetailsModel> listTypeDetailsName(@RequestBody DropDownQuery query);
}
