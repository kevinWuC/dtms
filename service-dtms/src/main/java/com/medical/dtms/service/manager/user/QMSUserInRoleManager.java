package com.medical.dtms.service.manager.user;

import com.medical.dtms.common.model.syslog.BaseSimpleUserModel;
import com.medical.dtms.common.util.BeanConvertUtils;
import com.medical.dtms.dto.user.QMSUserInRoleDTO;
import com.medical.dtms.dto.user.query.BaseUserQuery;
import com.medical.dtms.common.model.user.QMSUserInRoleModel;
import com.medical.dtms.service.dataobject.user.QMSUserInRoleDO;
import com.medical.dtms.service.mapper.qms.QMSUserInRoleMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @version： QMSUserInRoleManager.java v 1.0, 2019年08月20日 20:45 wuxuelin Exp$
 * @Description
 **/
@Service
public class QMSUserInRoleManager {

    @Autowired
    private QMSUserInRoleMapper userInRoleMapper;

    /**
     * 查询用户是否关联角色
     */
    public List<QMSUserInRoleDTO> listQMSUserInRole(BaseUserQuery query) {
        List<QMSUserInRoleDO> list = userInRoleMapper.listQMSUserInRole(query);
        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList<>();
        }
        return BeanConvertUtils.convertList(list, QMSUserInRoleDTO.class);
    }

    /**
     * 根据用户 id 删除数据
     */
    public Integer deleteQMSUserInRoleByUserId(Long userId) {
        QMSUserInRoleDO userInRoleDO = new QMSUserInRoleDO();
        userInRoleDO.setUserId(userId);
        return userInRoleMapper.deleteQMSUserInRoleByCondition(userInRoleDO);
    }

    /**
     * 新增 用户 - 角色关联
     */
    public Integer insert(List<QMSUserInRoleDTO> dtoList) {
        List<QMSUserInRoleDO> dos = BeanConvertUtils.convertList(dtoList, QMSUserInRoleDO.class);
        return userInRoleMapper.insert(dos);
    }

    /**
     * 根据角色 id 删除数据
     */
    public Integer deleteQMSUserInRoleByRoleId(Long roleId) {
        QMSUserInRoleDO userInRoleDO = new QMSUserInRoleDO();
        userInRoleDO.setRoleId(roleId);
        return userInRoleMapper.deleteQMSUserInRoleByCondition(userInRoleDO);
    }

    /**
     * 根据 用户id 查询角色
     */
    public  List<QMSUserInRoleModel> listRoleInfoByUserId(Long userId) {
        return userInRoleMapper.listRoleInfoByUserId(userId);
    }

    /** 根据用户ids 查询 角色 id*/
    public List<BaseSimpleUserModel> listRoleIdsByUserIds(List<Long> userIds){
        return userInRoleMapper.listRoleIdsByUserIds(userIds);
    }
}
