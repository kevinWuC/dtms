package com.medical.dtms.service.mapper.qms;

import com.medical.dtms.common.model.role.QMSRoleModel;
import com.medical.dtms.dto.role.query.QMSRoleQuery;
import com.medical.dtms.service.dataobject.role.QMSRoleDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QMSRoleMapper {
    int deleteByPrimaryKey(@Param("bizId") Long bizId);

    int insert(QMSRoleDO record);

    int updateByPrimaryKeySelective(QMSRoleDO record);

    QMSRoleDO getRoleByCondition(QMSRoleQuery query);

    List<QMSRoleModel> listQMSRoles(QMSRoleQuery query);
}