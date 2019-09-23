package com.medical.dtms.service.mapper.logserver;

import com.medical.dtms.logclient.model.OperationModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperationMapper {

    List<OperationModel> queryByFilter(OperationModel operationModel);

    Integer add(OperationModel logOperationModel);
}
