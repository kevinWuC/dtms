package com.medical.dtms.service.mapper.qms;

import com.medical.dtms.service.dataobject.log.QMSSysLogDetailsDO;
import org.springframework.stereotype.Repository;

@Repository
public interface QMSSysLogDetailsMapper {
    int deleteByPrimaryKey(String syslogdetailsid);

    int insert(QMSSysLogDetailsDO record);

    QMSSysLogDetailsDO selectByPrimaryKey(String syslogdetailsid);

    int updateByPrimaryKeySelective(QMSSysLogDetailsDO record);
}