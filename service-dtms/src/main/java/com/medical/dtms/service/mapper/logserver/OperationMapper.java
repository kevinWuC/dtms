package com.medical.dtms.service.mapper.logserver;

import com.medical.dtms.common.model.syslog.QMSSysLogsModel;
import com.medical.dtms.dto.log.query.QMSSysLogsQuery;
import com.medical.dtms.logclient.model.OperationModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperationMapper {

    List<OperationModel> queryByFilter(OperationModel operationModel);

    Integer add(OperationModel logOperationModel);

    List<QMSSysLogsModel> pageListSysLogs(QMSSysLogsQuery query);
}
