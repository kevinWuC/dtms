package com.medical.dtms.service.mapper.qms;

import com.medical.dtms.common.model.syslog.SimpleLogInLogModel;
import com.medical.dtms.common.model.syslog.SysLoginLogModel;
import com.medical.dtms.dto.log.query.QMSSysLoginLogQuery;
import com.medical.dtms.service.dataobject.log.QMSSysLoginLogDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QMSSysLoginLogMapper {

    int deleteByPrimaryKey(@Param("loginLogId") Long loginLogId);

    int insert(QMSSysLoginLogDO record);

    int insertSelective(QMSSysLoginLogDO record);

    QMSSysLoginLogDO selectByPrimaryKey(@Param("bizId") Long bizId);

    int updateByPrimaryKeySelective(QMSSysLoginLogDO record);

    int updateByPrimaryKey(QMSSysLoginLogDO record);

    List<SysLoginLogModel> listLoginLogs(QMSSysLoginLogQuery Query);

    List<SimpleLogInLogModel> listUserLastVisitAndVisitTime(List<Long> userIds);

    SysLoginLogModel getIpByUserId(@Param("userId") Long userId);
}