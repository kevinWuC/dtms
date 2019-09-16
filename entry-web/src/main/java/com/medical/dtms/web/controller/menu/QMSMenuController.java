package com.medical.dtms.web.controller.menu;

import com.medical.dtms.common.enumeration.ErrorCodeEnum;
import com.medical.dtms.common.resp.Result;
import com.medical.dtms.dto.menu.QMSMenuDTO;
import com.medical.dtms.dto.menu.query.QMSMenuQuery;
import com.medical.dtms.feignservice.menu.QMSMenuService;
import com.medical.dtms.common.model.menu.QMSMenuModel;
import com.medical.dtms.common.login.OperatorInfo;
import com.medical.dtms.common.login.SessionTools;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @version： MenuController.java v 1.0, 2019年08月19日 16:36 wuxuelin Exp$
 * @Description 菜单管理 控制类
 **/
@RestController
public class QMSMenuController {

    @Autowired
    private QMSMenuService menuService;

    /**
     * @Description 菜单管理 - 新增
     * @Param [menuDTO]
     * @Return com.acit.common.resp.Result<java.lang.Boolean>
     */
    @RequestMapping(value = "/menu/addMenu", method = RequestMethod.POST)
    public Result<Boolean> addMenu(@RequestBody QMSMenuDTO menuDTO) {
        if (null == menuDTO || StringUtils.isBlank(menuDTO.getFullName())) {
            return new Result<>(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "菜单名为空");
        }
        if (null == menuDTO.getParentId()) {
            return new Result<>(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "父级 id 为空");
        }
        OperatorInfo info = SessionTools.getOperator();
        menuDTO.setCreator(info.getDspName());
        menuDTO.setCreatorId(info.getUserId());
        menuService.addMenu(menuDTO);
        return Result.buildSuccess(true);
    }

    /**
     * @param [menuDTO]
     * @return com.medical.dtms.common.resp.Result<java.lang.Boolean>
     * @description 菜单管理 - 更新
     **/
    @RequestMapping(value = "/menu/updateMenu", method = RequestMethod.POST)
    public Result<Boolean> updateMenu(@RequestBody QMSMenuDTO menuDTO) {
        if (null == menuDTO.getBizId()) {
            return new Result<>(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "id 为空");
        }
        OperatorInfo info = SessionTools.getOperator();
        menuDTO.setModifier(info.getDspName());
        menuDTO.setModifierId(info.getUserId());
        menuService.updateMenu(menuDTO);
        return Result.buildSuccess(true);
    }

    /**
     * @param [menuDTO]
     * @return com.medical.dtms.common.resp.Result<java.lang.Boolean>
     * @description 菜单管理 - 删除
     **/
    @RequestMapping(value = "/menu/deleteMenu", method = RequestMethod.POST)
    public Result<Boolean> deleteMenu(@RequestBody QMSMenuDTO menuDTO) {
        if (null == menuDTO.getBizId()) {
            return new Result<>(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "id 为空");
        }
        OperatorInfo info = SessionTools.getOperator();
        menuDTO.setModifier(info.getDspName());
        menuDTO.setModifierId(info.getUserId());
        menuService.deleteMenu(menuDTO);
        return Result.buildSuccess(true);
    }

    /**
     * @param [query]
     * @return com.medical.dtms.common.resp.Result<java.util.List < com.medical.dtms.model.menu.QMSMenuModel>>
     * @description 菜单管理 - 列表展示（子父级结构，默认返回一二级，三级需要通过点击二级来展开）
     **/
    @RequestMapping(value = "/menu/listQMSMenus", method = RequestMethod.POST)
    public Result<List<QMSMenuModel>> listQMSMenus(@RequestBody QMSMenuQuery query) {
        List<QMSMenuModel> list = menuService.listQMSMenus(query);
        return Result.buildSuccess(list);
    }
}
