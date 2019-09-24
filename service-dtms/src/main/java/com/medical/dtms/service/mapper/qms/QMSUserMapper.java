package com.medical.dtms.service.mapper.qms;


import com.medical.dtms.common.model.dropdown.DropDownModel;
import com.medical.dtms.common.model.dropdown.query.DropDownQuery;
import com.medical.dtms.common.model.user.SimpleUserModel;
import com.medical.dtms.dto.user.query.BaseUserQuery;
import com.medical.dtms.common.model.user.QMSUserModel;
import com.medical.dtms.service.dataobject.user.QMSUserDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QMSUserMapper {

    int deleteByPrimaryKey(@Param("bizId") Long bizId);

    int insert(QMSUserDO record);

    int updateByPrimaryKeySelective(QMSUserDO record);

    QMSUserDO getUserByCondition(BaseUserQuery query);

    List<QMSUserModel> pageListUser(BaseUserQuery query);

    List<DropDownModel> listUsers(DropDownQuery query);

    List<QMSUserModel> listUsersInfo(BaseUserQuery query);

    List<SimpleUserModel> listUserInfoByIds(List<Long> userIds);
}