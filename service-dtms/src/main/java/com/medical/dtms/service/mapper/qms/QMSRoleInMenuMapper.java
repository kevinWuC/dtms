package com.medical.dtms.service.mapper.qms;


import com.medical.dtms.dto.role.query.QMSRoleInMenuQuery;
import com.medical.dtms.common.model.menu.QMSMenuModel;
import com.medical.dtms.service.dataobject.role.QMSRoleInMenuDO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QMSRoleInMenuMapper {
    int deleteByPrimaryKey(String id);

    int insert(QMSRoleInMenuDO record);

    QMSRoleInMenuDO selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(QMSRoleInMenuDO record);

    int updateByPrimaryKey(QMSRoleInMenuDO record);

    List<QMSRoleInMenuDO> listRoleInMenu(QMSRoleInMenuQuery query);

    Integer deleteByCondition(QMSRoleInMenuDO roleInMenuDO);

    Integer addQMSRoleInMenu(List<QMSRoleInMenuDO> dtos);

    List<QMSMenuModel> listMenuByRole(List<Long> roleIds);
}