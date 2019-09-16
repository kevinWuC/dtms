package com.medical.dtms.service.manager.role;

import com.medical.dtms.common.util.BeanConvertUtils;
import com.medical.dtms.dto.role.QMSRoleDTO;
import com.medical.dtms.dto.role.query.QMSRoleQuery;
import com.medical.dtms.common.model.role.QMSRoleModel;
import com.medical.dtms.service.dataobject.role.QMSRoleDO;
import com.medical.dtms.service.mapper.qms.QMSRoleMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @version： QMSRoleManager.java v 1.0, 2019年08月20日 14:17 wuxuelin Exp$
 * @Description
 **/
@Service
public class QMSRoleManager {

    @Autowired
    private QMSRoleMapper roleMapper;

    /**
     * 角色管理 - 条件查询角色数据
     */
    public QMSRoleDTO getRoleByCondition(QMSRoleQuery query) {
        QMSRoleDO roleDO = roleMapper.getRoleByCondition(query);
        if (null == roleDO) {
            return null;
        }
        return BeanConvertUtils.convert(roleDO, QMSRoleDTO.class);
    }

    /**
     * 角色管理 - 新增
     */
    public Integer insert(QMSRoleDTO dto) {
        QMSRoleDO aDo = BeanConvertUtils.convert(dto, QMSRoleDO.class);
        return roleMapper.insert(aDo);
    }

    /**
     * 角色管理 - 编辑
     */
    public Integer updateRole(QMSRoleDTO dto) {
        QMSRoleDO aDo = BeanConvertUtils.convert(dto, QMSRoleDO.class);
        return roleMapper.updateByPrimaryKeySelective(aDo);
    }

    /**
     * 角色管理 - 删除
     */
    public Integer deleteRole(Long bizId) {
        return roleMapper.deleteByPrimaryKey(bizId);
    }

    /**
     * 角色管理 - 分页展示列表
     */
    public List<QMSRoleModel> listQMSRoles(QMSRoleQuery query) {
        List<QMSRoleModel> roleDOS = roleMapper.listQMSRoles(query);
        if (CollectionUtils.isEmpty(roleDOS)){
            return new ArrayList<>();
        }
        return roleDOS;
    }
}
