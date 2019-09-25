package com.medical.dtms.service.mapper.logserver;

import com.medical.dtms.common.model.syslog.QMSSysLogDetailsModel;
import com.medical.dtms.dto.log.query.QMSSysLogsQuery;
import com.medical.dtms.logclient.model.AttributeModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttributeMapper {

    List<AttributeModel> queryByOperationIdList(List<Long> operationIdList);

    List<AttributeModel> queryByFilter(AttributeModel attributeModel);

    Integer addBatch(@Param("attributeModelList") List<AttributeModel> attributeModelList);

    List<QMSSysLogDetailsModel> listQMSSysLogDetails(QMSSysLogsQuery query);
}
