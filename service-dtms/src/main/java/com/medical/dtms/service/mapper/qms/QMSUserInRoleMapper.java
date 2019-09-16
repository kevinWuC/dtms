package com.medical.dtms.service.mapper.qms;

import com.medical.dtms.common.model.syslog.BaseSimpleUserModel;
import com.medical.dtms.dto.user.query.BaseUserQuery;
import com.medical.dtms.common.model.user.QMSUserInRoleModel;
import com.medical.dtms.service.dataobject.user.QMSUserInRoleDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QMSUserInRoleMapper {

    int insert(List<QMSUserInRoleDO> dos);

    List<QMSUserInRoleDO> listQMSUserInRole(BaseUserQuery query);

    Integer deleteQMSUserInRoleByCondition(QMSUserInRoleDO userInRoleDO);

    List<QMSUserInRoleModel> listRoleInfoByUserId(@Param("userId") Long userId);

    List<BaseSimpleUserModel> listRoleIdsByUserIds(List<Long> userIds);
}