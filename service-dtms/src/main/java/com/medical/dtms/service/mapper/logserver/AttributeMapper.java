package com.medical.dtms.service.mapper.logserver;

import com.medical.dtms.logclient.model.AttributeModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttributeMapper {

    List<AttributeModel> queryByOperationIdList(List<Integer> operationIdList);

    List<AttributeModel> queryByFilter(AttributeModel attributeModel);

    Integer addBatch(@Param("attributeModelList") List<AttributeModel> attributeModelList);
}
