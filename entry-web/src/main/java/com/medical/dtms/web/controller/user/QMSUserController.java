package com.medical.dtms.web.controller.user;

import com.github.pagehelper.PageInfo;
import com.medical.dtms.common.eception.BizException;
import com.medical.dtms.dto.log.QMSSysLoginLogDTO;
import com.medical.dtms.feignservice.syslogs.SysLoginLogService;
import com.medical.dtms.common.model.menu.QMSMenuModel;
import com.medical.dtms.common.enumeration.ErrorCodeEnum;
import com.medical.dtms.common.resp.Result;
import com.medical.dtms.dto.user.QMSUserDTO;
import com.medical.dtms.dto.user.QMSUserInDeptDTO;
import com.medical.dtms.dto.user.QMSUserInJobsDTO;
import com.medical.dtms.dto.user.QMSUserInRoleDTO;
import com.medical.dtms.dto.user.query.BaseUserQuery;
import com.medical.dtms.feignservice.user.QMSUserService;
import com.medical.dtms.common.model.user.QMSUserModel;
import com.medical.dtms.common.model.user.UserLoginModel;
import com.medical.dtms.common.login.OperatorInfo;
import com.medical.dtms.common.login.SessionTools;
import com.medical.dtms.web.ipaddress.IpAddressManager;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * @version： QMSUserController.java v 1.0, 2019年08月19日 16:31 wuxuelin Exp$
 * @Description 用户控制类
 **/
@RestController
public class QMSUserController {

    @Autowired
    private QMSUserService userService;
    @Autowired
    private SysLoginLogService logService;
    @Autowired
    private IpAddressManager ipAddressManager;

    /**
     * @param [dto]
     * @return com.medical.dtms.common.resp.Result<com.medical.dtms.model.user.UserLoginModel>
     * @description 用户登录
     **/
    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public Result<UserLoginModel> login(@RequestBody QMSUserDTO dto, HttpServletRequest request) {
        if (null == dto || StringUtils.isBlank(dto.getAccount()) || StringUtils.isBlank(dto.getPassWord())) {
            return new Result<>(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "用户或密码为空");
        }

        UserLoginModel model = userService.login(dto);
        model.setKeepLogin(dto.getKeepLogin() == null ? false : dto.getKeepLogin());
        SessionTools.saveUserInfo(model, request);

        // 保存登录信息到 登录日志表
        String address = ipAddressManager.getCityAddress(request);

        QMSSysLoginLogDTO logDTO = new QMSSysLoginLogDTO();
        logDTO.setCreateDate(new Date());
        logDTO.setAccount(model.getAccount());
        logDTO.setLoginLogStatus(true);
        logDTO.setIpAddress(request.getRemoteAddr());
        logDTO.setCreatorId(model.getBizId());
        logDTO.setCreator(model.getDspName());
        logDTO.setIpAddressName(StringUtils.isBlank(address) == true ? null : address);
        logService.addLoginLog(logDTO);

        return Result.buildSuccess(ErrorCodeEnum.SUCCESS.getErrorCode(), true, "登录成功", model);
    }

    /**
     * @param [request]
     * @return com.medical.dtms.common.resp.Result<java.lang.Boolean>
     * @description 退出登录
     **/
    @RequestMapping(value = "/user/logout", method = RequestMethod.POST)
    public Result<Boolean> logout(HttpServletRequest request) {
        SessionTools.cleanUserInfo(request);
        return Result.buildSuccess(ErrorCodeEnum.SUCCESS.getErrorCode(), true, "退出成功");
    }


    /**
     * @param [dto]
     * @return com.medical.dtms.common.resp.Result<java.lang.Boolean>
     * @description 用户管理 - 新增用户
     **/
    @RequestMapping(value = "/user/addUser", method = RequestMethod.POST)
    public Result<Boolean> addUser(@RequestBody QMSUserDTO dto) {
        if (null == dto || StringUtils.isBlank(dto.getAccount()) || StringUtils.isBlank(dto.getDspName())) {
            return new Result<>(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode());
        }
        if (StringUtils.isBlank(dto.getEmail()) || null == dto.getDeptId() || null == dto.getRoleId()) {
            return new Result<>(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "邮箱/默认部门/角色未填写，请填写完整");
        }
        OperatorInfo info = SessionTools.getOperator();
        dto.setCreator(info.getDspName());
        dto.setCreatorId(info.getUserId());
        userService.addUser(dto);

        return Result.buildSuccess(true);
    }

    /**
     * @param [dto]
     * @return com.medical.dtms.common.resp.Result<java.lang.Boolean>
     * @description 用户管理 - 编辑用户
     **/
    @RequestMapping(value = "/user/updateUser", method = RequestMethod.POST)
    public Result<Boolean> updateUser(@RequestBody QMSUserDTO dto) {
        if (null == dto || null == dto.getBizId()) {
            return new Result<>(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode());
        }
        OperatorInfo info = SessionTools.getOperator();
        dto.setModifier(info.getDspName());
        dto.setModifierId(info.getUserId());
        userService.updateUser(dto);
        return Result.buildSuccess(true);
    }

    /**
     * @param [dto]
     * @return com.medical.dtms.common.resp.Result<java.lang.Boolean>
     * @description 用户管理 - 删除用户
     **/
    @RequestMapping(value = "/user/deleteUser", method = RequestMethod.POST)
    public Result<Boolean> deleteUser(@RequestBody QMSUserDTO dto) {
        if (null == dto || null == dto.getBizId()) {
            return new Result<>(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode());
        }
        OperatorInfo info = SessionTools.getOperator();
        dto.setModifier(info.getDspName());
        dto.setModifierId(info.getUserId());
        userService.deleteUser(dto);
        return Result.buildSuccess(true);
    }


    /**
     * @param [query]
     * @return com.medical.dtms.common.resp.Result<java.lang.Boolean>
     * @description 用户管理 - 用户列表查询（分页展示）
     **/
    @RequestMapping(value = "/user/pageListUser", method = RequestMethod.POST)
    public Result<QMSUserModel> pageListUser(@RequestBody BaseUserQuery query) {
        checkParams(query);
        PageInfo<QMSUserModel> info = userService.pageListUser(query);
        return Result.buildSuccess(info);
    }

    /**
     * @param [dto]
     * @return com.medical.dtms.common.resp.Result<java.lang.Boolean>
     * @description 用户管理 - 新增用户与角色关联
     **/
    @RequestMapping(value = "/user/addUserInRole", method = RequestMethod.POST)
    public Result<Boolean> addUserInRole(@RequestBody QMSUserInRoleDTO roleDTO) {
        if (null == roleDTO || null == roleDTO.getUserId() || CollectionUtils.isEmpty(roleDTO.getRoleIdList())) {
            return new Result<>(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode());
        }
        OperatorInfo info = SessionTools.getOperator();
        roleDTO.setCreator(info.getDspName());
        roleDTO.setCreatorId(info.getUserId());
        userService.addUserInRole(roleDTO);
        return Result.buildSuccess(true);
    }

    /**
     * @param [dto]
     * @return com.medical.dtms.common.resp.Result<java.lang.Boolean>
     * @description 用户管理 - 新增用户与职位关联
     **/
    @RequestMapping(value = "/user/addUserInJob", method = RequestMethod.POST)
    public Result<Boolean> addUserInJob(@RequestBody QMSUserInJobsDTO jobsDTO) {
        if (null == jobsDTO || null == jobsDTO.getUserId() || CollectionUtils.isEmpty(jobsDTO.getJobIdList())) {
            return new Result<>(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode());
        }
        OperatorInfo info = SessionTools.getOperator();
        jobsDTO.setCreator(info.getDspName());
        jobsDTO.setCreatorId(info.getUserId());
        userService.addUserInJob(jobsDTO);
        return Result.buildSuccess(true);
    }

    /**
     * @param [deptDTO]
     * @return com.medical.dtms.common.resp.Result<java.lang.Boolean>
     * @description 用户管理 - 新增用户与部门关联
     **/
    @RequestMapping(value = "/user/addUserInDept", method = RequestMethod.POST)
    public Result<Boolean> addUserInDept(@RequestBody QMSUserInDeptDTO deptDTO) {
        if (null == deptDTO || null == deptDTO.getUserId() || CollectionUtils.isEmpty(deptDTO.getDeptIdList())) {
            return new Result<>(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode());
        }
        OperatorInfo info = SessionTools.getOperator();
        deptDTO.setCreator(info.getDspName());
        deptDTO.setCreatorId(info.getUserId());
        userService.addUserInDept(deptDTO);
        return Result.buildSuccess(true);
    }

    /**
     * @param [query]
     * @return com.medical.dtms.common.resp.Result<java.util.List < com.medical.dtms.model.menu.QMSMenuModel>>
     * @description 查询用户所拥有的菜单（均为末级菜单）
     **/
    @RequestMapping(value = "/user/listMenusByUserId", method = RequestMethod.POST)
    public Result<List<QMSMenuModel>> listMenusByUserId(@RequestBody BaseUserQuery query) {
        if (null == query) {
            query = new BaseUserQuery();
        }
        if (null == query.getUserId()) {
            OperatorInfo info = SessionTools.getOperator();
            query.setUserId(info.getBizId());
        }
        List<QMSMenuModel> list = userService.listMenusByUserId(query);
        return Result.buildSuccess(list);
    }

    /**
     * @param query
     * @return 所有人员列表
     **/
    @RequestMapping(value = "/user/listUsersInfo", method = RequestMethod.POST)
    public Result<PageInfo<QMSUserModel>> listUsersInfo(@RequestBody BaseUserQuery query) {
        checkPageParams(query);
        PageInfo<QMSUserModel> userModels = userService.listUsersInfo(query);
        return Result.buildSuccess(userModels);
    }

    /**
     * 分页参数校验
     *
     * @param query
     */
    private void checkParams(BaseUserQuery query) {
        if (null == query) {
            query = new BaseUserQuery();
        }
        if (null == query.getPageNo()) {
            query.setPageNo(1);
        }
        if (null == query.getPageSize()) {
            query.setPageSize(10);
        }
        query.setDspName(StringUtils.isBlank(query.getDspName()) == true ? null : query.getDspName().replace("%", "\\\\%"));
        query.setAccount(StringUtils.isBlank(query.getAccount()) == true ? null : query.getAccount().replace("%", "\\\\%"));
    }

    /**
     * 分页参数校验
     *
     * @param query
     */
    private void checkPageParams(BaseUserQuery query) {
        if (null == query) {
            query = new BaseUserQuery();
        }
        if (null == query.getPageNo()) {
            query.setPageNo(1);
        }
        if (null == query.getPageSize()) {
            query.setPageSize(50);
        }
    }
}
