package com.medical.dtms.service.mapper.qms;

import com.medical.dtms.common.model.syslog.QMSSysLogDetailsModel;
import com.medical.dtms.dto.log.query.QMSSysLogsQuery;
import com.medical.dtms.service.dataobject.log.QMSSysLogDetailsDO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QMSSysLogDetailsMapper {
    int deleteByPrimaryKey(String syslogdetailsid);

    int insert(QMSSysLogDetailsDO record);

    QMSSysLogDetailsDO selectByPrimaryKey(String syslogdetailsid);

    int updateByPrimaryKeySelective(QMSSysLogDetailsDO record);

    List<QMSSysLogDetailsModel> listQMSSysLogDetails(QMSSysLogsQuery query);
}