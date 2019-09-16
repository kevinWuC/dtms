package com.medical.dtms.web.controller.role;

import com.github.pagehelper.PageInfo;
import com.medical.dtms.common.enumeration.ErrorCodeEnum;
import com.medical.dtms.common.resp.Result;
import com.medical.dtms.dto.role.QMSRoleDTO;
import com.medical.dtms.dto.role.query.QMSRoleQuery;
import com.medical.dtms.feignservice.role.QMSRoleService;
import com.medical.dtms.common.model.role.QMSRoleModel;
import com.medical.dtms.common.login.OperatorInfo;
import com.medical.dtms.common.login.SessionTools;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * @version： QMSRoleController.java v 1.0, 2019年08月20日 12:02 wuxuelin Exp$
 * @Description 角色控制类
 **/
@RestController
public class QMSRoleController {

    @Autowired
    private QMSRoleService roleService;

    /**
     * @Description 角色管理 - 新增
     * @Param [roleDTO]
     * @Return com.acit.common.resp.Result<java.lang.Boolean>
     */
    @RequestMapping(value = "/role/addRole", method = RequestMethod.POST)
    public Result<Boolean> addRole(@RequestBody QMSRoleDTO roleDTO) {
        if (null == roleDTO || StringUtils.isBlank(roleDTO.getRoleName())) {
            return new Result<>(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "角色名为空");
        }
        OperatorInfo info = SessionTools.getOperator();
        roleDTO.setCreator(info.getDspName());
        roleDTO.setCreatorId(info.getUserId());
        roleService.addRole(roleDTO);
        return Result.buildSuccess(true);
    }

    /**
     * @param [roleDTO]
     * @return com.medical.dtms.common.resp.Result<java.lang.Boolean>
     * @description 角色管理 - 更新
     **/
    @RequestMapping(value = "/role/updateRole", method = RequestMethod.POST)
    public Result<Boolean> updateRole(@RequestBody QMSRoleDTO roleDTO) {
        if (null == roleDTO.getBizId()) {
            return new Result<>(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "id 为空");
        }
        OperatorInfo info = SessionTools.getOperator();
        roleDTO.setModifier(info.getDspName());
        roleDTO.setModifierId(info.getUserId());
        roleService.updateRole(roleDTO);
        return Result.buildSuccess(true);
    }

    /**
     * @param [roleDTO]
     * @return com.medical.dtms.common.resp.Result<java.lang.Boolean>
     * @description 角色管理 - 删除
     **/
    @RequestMapping(value = "/role/deleteRole", method = RequestMethod.POST)
    public Result<Boolean> deleteRole(@RequestBody QMSRoleDTO roleDTO) {
        if (null == roleDTO.getBizId()) {
            return new Result<>(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "id 为空");
        }
        OperatorInfo info = SessionTools.getOperator();
        roleDTO.setModifier(info.getDspName());
        roleDTO.setModifierId(info.getUserId());
        roleService.deleteRole(roleDTO);
        return Result.buildSuccess(true);
    }

    /**
     * @param [query]
     * @return com.medical.dtms.common.resp.Result<java.util.List<com.medical.dtms.model.role.QMSRoleModel>>
     * @description 角色管理 - 列表展示
     **/
    @RequestMapping(value = "/role/pageListQMSRoles", method = RequestMethod.POST)
    public Result<PageInfo<QMSRoleModel>> pageListQMSRoles(@RequestBody QMSRoleQuery query) {
        checkParams(query);
        PageInfo<QMSRoleModel> list = roleService.pageListQMSRoles(query);
        return Result.buildSuccess(list);
    }

    /**
     * 分页参数校验
     *
     * @param query
     */
    private void checkParams(QMSRoleQuery query) {
        if (null == query) {
            query = new QMSRoleQuery();
        }
        if (null == query.getPageNo()) {
            query.setPageNo(1);
        }
        if (null == query.getPageSize()) {
            query.setPageSize(10);
        }
    }
}
