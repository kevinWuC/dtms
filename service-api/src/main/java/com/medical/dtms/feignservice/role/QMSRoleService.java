package com.medical.dtms.feignservice.role;

import com.github.pagehelper.PageInfo;
import com.medical.dtms.dto.role.QMSRoleDTO;
import com.medical.dtms.dto.role.query.QMSRoleQuery;
import com.medical.dtms.common.model.role.QMSRoleModel;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * @version： QMSRoleService.java v 1.0, 2019年08月20日 12:04 wuxuelin Exp$
 * @Description
 **/
@FeignClient("service-dtms")
public interface QMSRoleService {

    @RequestMapping(value = "/role/addRole", method = RequestMethod.POST)
    Boolean addRole(@RequestBody QMSRoleDTO roleDTO);

    @RequestMapping(value = "/role/updateRole", method = RequestMethod.POST)
    Boolean updateRole(@RequestBody QMSRoleDTO roleDTO);

    @RequestMapping(value = "/role/deleteRole", method = RequestMethod.POST)
    Boolean deleteRole(@RequestBody QMSRoleDTO roleDTO);

    @RequestMapping(value = "/role/pageListQMSRoles", method = RequestMethod.POST)
    PageInfo<QMSRoleModel> pageListQMSRoles(@RequestBody QMSRoleQuery query);
}
