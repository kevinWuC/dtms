package com.medical.dtms.web.controller.role;

import com.medical.dtms.common.enumeration.ErrorCodeEnum;
import com.medical.dtms.common.resp.Result;
import com.medical.dtms.dto.role.QMSRoleInMenuDTO;
import com.medical.dtms.feignservice.role.QMSRoleInMenuService;
import com.medical.dtms.common.login.OperatorInfo;
import com.medical.dtms.common.login.SessionTools;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @version： QMSRoleInMenuController.java v 1.0, 2019年08月20日 15:24 wuxuelin Exp$
 * @Description 角色 - 菜单 关联 控制类
 **/
@RestController
public class QMSRoleInMenuController {

    @Autowired
    private QMSRoleInMenuService menuService;

    /**
     * @param [dto]
     * @return com.medical.dtms.common.resp.Result<java.lang.Boolean>
     * @description 新增 角色 - 菜单关联
     **/
    @RequestMapping(value = "/roleInMenu/addQMSRoleInMenu", method = RequestMethod.POST)
    public Result<Boolean> addQMSRoleInMenu(@RequestBody QMSRoleInMenuDTO dto) {
        if (null == dto || null == dto.getRoleId() || CollectionUtils.isEmpty(dto.getMenuIdList())) {
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), ErrorCodeEnum.PARAM_IS_EMPTY.getErrorMessage());
        }
        OperatorInfo info = SessionTools.getOperator();
        dto.setCreator(info.getDspName());
        dto.setCreatorId(info.getUserId());
        menuService.addQMSRoleInMenu(dto);
        return Result.buildSuccess(true);
    }
}
