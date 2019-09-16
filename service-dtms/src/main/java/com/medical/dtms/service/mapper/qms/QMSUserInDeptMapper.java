package com.medical.dtms.service.mapper.qms;

import com.medical.dtms.common.model.dept.QMSUserInDeptModel;
import com.medical.dtms.common.model.syslog.BaseSimpleUserModel;
import com.medical.dtms.dto.user.query.QMSUserInDeptQuery;
import com.medical.dtms.service.dataobject.user.QMSUserInDeptDO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QMSUserInDeptMapper {

    int deleteByPrimaryKey(String id);

    int insert(QMSUserInDeptDO record);

    QMSUserInDeptDO selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(QMSUserInDeptDO record);

    int updateByPrimaryKey(QMSUserInDeptDO record);

    List<QMSUserInDeptDO> listUserInDept(QMSUserInDeptQuery query);

    Integer deleteByCondition(QMSUserInDeptDO UserInDeptDO);

    Integer addQMSUserInDept(List<QMSUserInDeptDO> dtos);

    List<BaseSimpleUserModel> listDeptIdsByUserIds(List<Long> userIds);

    List<QMSUserInDeptModel> listDeptByUserIdsAndDept(QMSUserInDeptQuery query);
}