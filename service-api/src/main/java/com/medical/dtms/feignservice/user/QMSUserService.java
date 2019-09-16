package com.medical.dtms.feignservice.user;

import com.github.pagehelper.PageInfo;
import com.medical.dtms.common.eception.BizException;
import com.medical.dtms.dto.user.QMSUserDTO;
import com.medical.dtms.dto.user.QMSUserInDeptDTO;
import com.medical.dtms.dto.user.QMSUserInJobsDTO;
import com.medical.dtms.dto.user.QMSUserInRoleDTO;
import com.medical.dtms.dto.user.query.BaseUserQuery;
import com.medical.dtms.common.model.menu.QMSMenuModel;
import com.medical.dtms.common.model.user.QMSUserModel;
import com.medical.dtms.common.model.user.UserLoginModel;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @version： QMSUserService.java v 1.0, 2019年08月20日 17:39 wuxuelin Exp$
 * @Description
 **/
@FeignClient("service-dtms")
public interface QMSUserService {

    @RequestMapping(value = "/user/addUser", method = RequestMethod.POST)
    Boolean addUser(@RequestBody QMSUserDTO dto);

    @RequestMapping(value = "/user/updateUser", method = RequestMethod.POST)
    Boolean updateUser(@RequestBody QMSUserDTO dto);

    @RequestMapping(value = "/user/deleteUser", method = RequestMethod.POST)
    Boolean deleteUser(@RequestBody QMSUserDTO dto);

    @RequestMapping(value = "/user/pageListUser", method = RequestMethod.POST)
    PageInfo<QMSUserModel> pageListUser(@RequestBody BaseUserQuery query);

    @RequestMapping(value = "/user/addUserInRole", method = RequestMethod.POST)
    Boolean addUserInRole(@RequestBody QMSUserInRoleDTO roleDTO);

    @RequestMapping(value = "/user/addUserInJob", method = RequestMethod.POST)
    Boolean addUserInJob(@RequestBody QMSUserInJobsDTO jobsDTO);

    @RequestMapping(value = "/user/addUserInDept", method = RequestMethod.POST)
    Boolean addUserInDept(@RequestBody QMSUserInDeptDTO deptDTO);

    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    UserLoginModel login(@RequestBody QMSUserDTO dto);

    @RequestMapping(value = "/user/listMenusByUserId", method = RequestMethod.POST)
    List<QMSMenuModel> listMenusByUserId(@RequestBody BaseUserQuery query);
}
