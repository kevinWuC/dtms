package com.medical.dtms.service.manager.role;

import com.medical.dtms.common.util.BeanConvertUtils;
import com.medical.dtms.dto.role.QMSRoleInMenuDTO;
import com.medical.dtms.dto.role.query.QMSRoleInMenuQuery;
import com.medical.dtms.common.model.menu.QMSMenuModel;
import com.medical.dtms.service.dataobject.role.QMSRoleInMenuDO;
import com.medical.dtms.service.mapper.qms.QMSRoleInMenuMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * @version： QMSRoleInMenuManager.java v 1.0, 2019年08月20日 16:02 wuxuelin Exp$
 * @Description
 **/
@Service
public class QMSRoleInMenuManager {

    @Autowired
    private QMSRoleInMenuMapper roleInMenuMapper;

    /**
     * 查询角色关联的菜单
     */
    public List<QMSRoleInMenuDTO> listRoleInMenu(QMSRoleInMenuQuery query) {
        List<QMSRoleInMenuDO> dtos = roleInMenuMapper.listRoleInMenu(query);
        if (CollectionUtils.isEmpty(dtos)) {
            return new ArrayList<>();
        }
        return BeanConvertUtils.convertList(dtos, QMSRoleInMenuDTO.class);
    }

    /**
     * 根据 角色id 删除数据
     */
    public Integer deleteByRoleId(Long roleId) {
        QMSRoleInMenuDO roleInMenuDO = new QMSRoleInMenuDO();
        roleInMenuDO.setRoleId(roleId);
        return roleInMenuMapper.deleteByCondition(roleInMenuDO);
    }

    /**
     * 新增角色 - 菜单关联/给角色授权
     */
    public Integer addQMSRoleInMenu(List<QMSRoleInMenuDTO> dtos) {
        List<QMSRoleInMenuDO> aDos = BeanConvertUtils.convertList(dtos, QMSRoleInMenuDO.class);
        return roleInMenuMapper.addQMSRoleInMenu(aDos);
    }

    /**
     * 根据 菜单id 删除数据
     */
    public Integer deleteByMenuId(Long menuId) {
        QMSRoleInMenuDO roleInMenuDO = new QMSRoleInMenuDO();
        roleInMenuDO.setMenuId(menuId);
        return roleInMenuMapper.deleteByCondition(roleInMenuDO);
    }

    /**
     * 根据角色ids 查询 菜单数据
     */
    public List<QMSMenuModel> listMenuByRole(List<Long> roleIds) {
        List<QMSMenuModel> dtos = roleInMenuMapper.listMenuByRole(roleIds);
        if (CollectionUtils.isEmpty(dtos)) {
            return new ArrayList<>();
        }
        return dtos;
    }
}
