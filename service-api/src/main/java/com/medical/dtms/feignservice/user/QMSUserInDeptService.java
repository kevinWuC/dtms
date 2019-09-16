package com.medical.dtms.feignservice.user;

import com.medical.dtms.dto.user.QMSUserInDeptDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @version： QMSUserInDeptService.java v 1.0, 2019年08月20日 15:59  Exp$
 * @Description
 **/
@FeignClient("service-dtms")
public interface QMSUserInDeptService {

    @RequestMapping(value = "/userInDept/addQMSUserInDept", method = RequestMethod.POST)
    Boolean addQMSUserInDept(@RequestBody QMSUserInDeptDTO dto);

}
