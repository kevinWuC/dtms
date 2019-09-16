package com.medical.dtms.feignservice.role;

import com.medical.dtms.dto.role.QMSRoleInMenuDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @version： QMSRoleInMenuService.java v 1.0, 2019年08月20日 15:59 wuxuelin Exp$
 * @Description
 **/
@FeignClient("service-dtms")
public interface QMSRoleInMenuService {

    @RequestMapping(value = "/roleInMenu/addQMSRoleInMenu", method = RequestMethod.POST)
    Boolean addQMSRoleInMenu(@RequestBody QMSRoleInMenuDTO dto);
}
