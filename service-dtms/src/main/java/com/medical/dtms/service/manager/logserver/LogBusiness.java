package com.medical.dtms.service.manager.logserver;

import com.medical.dtms.logclient.model.AttributeModel;
import com.medical.dtms.logclient.model.OperationModel;
import com.medical.dtms.logclient.service.LogServer;
import com.medical.dtms.service.constant.RespConstant;
import com.medical.dtms.service.form.OperationForm;
import com.medical.dtms.service.mapper.logserver.AttributeMapper;
import com.medical.dtms.service.mapper.logserver.OperationMapper;
import com.medical.dtms.service.util.RespUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LogBusiness {
    private static final Log LOG = LogFactory.getLog(LogBusiness.class);

    @Autowired
    private LogServer logServer;
    @Autowired
    private OperationMapper operationMapper;
    @Autowired
    private AttributeMapper attributeMapper;

    public Map<String, Object> add(String logJsonString) {
        try {
            OperationModel operationModel = logServer.resolveOperationModel(logJsonString);
            operationMapper.add(operationModel);
            Long operationId = operationModel.getId();

            List<AttributeModel> attributeModelList = operationModel.getAttributeModelList();
            for (AttributeModel attributeModel : attributeModelList) {
                attributeModel.setId(null);
                attributeModel.setOperationId(operationId);
            }

            if (!CollectionUtils.isEmpty(attributeModelList)) {
                attributeMapper.addBatch(attributeModelList);
            }

            return RespUtil.getSuccessMap();
        } catch (Exception ex) {
            LOG.error("ObjectLogger ERROR : add log error,", ex);
            return RespUtil.getCommonErrorMap(RespConstant.INSERT_EXCEPTION);
        }
    }

    public Map<String, Object> query(OperationForm operationForm) {
        try {
            OperationModel operationFilterModel = new OperationModel();
            if (operationForm.getId() != null) {
                operationFilterModel.setId(operationForm.getId());
            }
            operationFilterModel.setAppName(operationForm.getAppName());
            operationFilterModel.setObjectName(operationForm.getObjectName());
            operationFilterModel.setObjectId(operationForm.getObjectId());
            operationFilterModel.setOperator(operationForm.getOperator());
            operationFilterModel.setOperationName(operationForm.getOperationName());
            operationFilterModel.setOperationAlias(operationForm.getOperationAlias());
            List<OperationModel> operationModelList = operationMapper.queryByFilter(operationFilterModel);

            if (!CollectionUtils.isEmpty(operationModelList)) {
                List<Long> operationIdList = operationModelList.stream().map(OperationModel::getId).collect(Collectors.toList());
                List<AttributeModel> attributeModelList = attributeMapper.queryByOperationIdList(operationIdList);
                if (!CollectionUtils.isEmpty(attributeModelList)) {
                    Map<Long, List<AttributeModel>> attributeModelMap = new HashMap<>();
                    for (AttributeModel attributeModel : attributeModelList) {
                        attributeModelMap.putIfAbsent(attributeModel.getOperationId(), new ArrayList<>());
                        attributeModelMap.get(attributeModel.getOperationId()).add(attributeModel);
                    }

                    for (OperationModel operationModel : operationModelList) {
                        if (attributeModelMap.containsKey(operationModel.getId())) {
                            operationModel.getAttributeModelList().addAll(attributeModelMap.get(operationModel.getId()));
                        }
                    }
                }
            }

            return RespUtil.getSuccessMap(operationModelList);
        } catch (Exception ex) {
            LOG.error("ObjectLogger ERROR : query log error,", ex);
            return RespUtil.getCommonErrorMap(RespConstant.QUERY_EXCEPTION);
        }
    }
}
