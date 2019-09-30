package com.medical.dtms.service.manager.role;

import com.medical.dtms.common.enumeration.log.OperationTypeEnum;
import com.medical.dtms.common.util.BeanConvertUtils;
import com.medical.dtms.dto.role.QMSRoleDTO;
import com.medical.dtms.dto.role.query.QMSRoleQuery;
import com.medical.dtms.common.model.role.QMSRoleModel;
import com.medical.dtms.logclient.service.LogClient;
import com.medical.dtms.service.dataobject.role.QMSRoleDO;
import com.medical.dtms.service.manager.syslogs.SysLoginLogManager;
import com.medical.dtms.service.manager.table.OperateManager;
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
    @Autowired
    private OperateManager operateManager;
    @Autowired
    private SysLoginLogManager loginLogManager;
    @Autowired
    private LogClient logClient;

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
        QMSRoleDO newDo = BeanConvertUtils.convert(dto, QMSRoleDO.class);

        QMSRoleDO oldDo = new QMSRoleDO();
        // 记录日志
        logClient.logObject(
                // 对象主键
                String.valueOf(oldDo.getBizId()),
                // 操作人
                dto.getCreator(),
                // 操作类型
                OperationTypeEnum.OPERATION_TYPE_INSERT.getType(),
                // 本次操作的别名，这里是操作的表名
                operateManager.getTableName(newDo.getClass()),
                // 本次操作的额外描述，这里记录为操作人的ip
                loginLogManager.getIpByUserId(dto.getCreatorId()),
                // 备注，这里是操作模块名
                "角色管理",
                // 旧值
                oldDo,
                // 新值
                newDo
        );

        return roleMapper.insert(newDo);
    }

    /**
     * 角色管理 - 编辑
     */
    public Integer updateRole(QMSRoleDTO dto) {

        QMSRoleQuery query = new QMSRoleQuery();
        query.setBizId(dto.getBizId());
        QMSRoleDO oldRole = roleMapper.getRoleByCondition(query);

        QMSRoleDO newRole = BeanConvertUtils.convert(dto, QMSRoleDO.class);

        // 记录日志
        logClient.logObject(
                // 对象主键
                String.valueOf(oldRole.getBizId()),
                // 操作人
                dto.getModifier(),
                // 操作类型
                dto.getIsDeleted() == null ? OperationTypeEnum.OPERATION_TYPE_UPDATE.getType() : dto.getIsDeleted() == true ? OperationTypeEnum.OPERATION_TYPE_DELETE.getType() : OperationTypeEnum.OPERATION_TYPE_UPDATE.getType(),
                // 本次操作的别名，这里是操作的表名
                operateManager.getTableName(newRole.getClass()),
                // 本次操作的额外描述，这里记录为操作人的ip
                loginLogManager.getIpByUserId(dto.getModifierId()),
                // 备注，这里是操作模块名
                "角色管理",
                // 旧值
                oldRole,
                // 新值
                newRole
        );
        return roleMapper.updateByPrimaryKeySelective(newRole);
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
        if (CollectionUtils.isEmpty(roleDOS)) {
            return new ArrayList<>();
        }
        return roleDOS;
    }

    /**
     * 根据 角色 id 查询数据
     */
    public List<QMSRoleDTO> showRoleInfoByRoleIds(List<Long> roleIds) {
        List<QMSRoleDO> roleDOS = roleMapper.showRoleInfoByRoleIds(roleIds);
        if (CollectionUtils.isEmpty(roleDOS)) {
            return new ArrayList<>();
        }
        return BeanConvertUtils.convertList(roleDOS, QMSRoleDTO.class);
    }
}
