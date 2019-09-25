package com.medical.dtms.service.manager.syslogs;

import com.medical.dtms.common.model.syslog.QMSSysLogDetailsModel;
import com.medical.dtms.common.model.syslog.QMSSysLogsModel;
import com.medical.dtms.common.util.BeanConvertUtils;
import com.medical.dtms.dto.log.QMSSysLogsDTO;
import com.medical.dtms.dto.log.query.QMSSysLogsQuery;
import com.medical.dtms.logclient.model.AttributeModel;
import com.medical.dtms.logclient.model.OperationModel;
import com.medical.dtms.service.dataobject.log.QMSSysLogsDO;
import com.medical.dtms.service.mapper.logserver.AttributeMapper;
import com.medical.dtms.service.mapper.logserver.OperationMapper;
import com.medical.dtms.service.mapper.qms.QMSSysLogsMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version： SysLogsManager.java v 1.0, 2019年08月19日 11:21 wuxuelin Exp$
 * @Description 系统日志
 **/
@Service
@Slf4j
public class SysLogsManager {

    @Autowired
    private QMSSysLogsMapper qmsSysLogsMapper;
    @Autowired
    private OperationMapper operationMapper;
    @Autowired
    private AttributeMapper attributeMapper;

    /**
     * 系统日志--新增日志
     *
     * @param dto
     * @return
     */
    public Integer insert(QMSSysLogsDTO dto) {
        QMSSysLogsDO aDo = BeanConvertUtils.convert(dto, QMSSysLogsDO.class);
        return qmsSysLogsMapper.insert(aDo);
    }

    /**
     * 分页查询系统日志
     */
    public List<QMSSysLogsModel> pageListSysLogs(QMSSysLogsQuery query) {
        return operationMapper.pageListSysLogs(query);
    }

    /**
     * 根据 系统日志 id 查询 系统操作日志明细
     */
    public List<QMSSysLogDetailsModel> listQMSSysLogDetails(QMSSysLogsQuery query) {
        return attributeMapper.listQMSSysLogDetails(query);
    }

    /**
     * 添加操作记录
     */
    public Integer addOperationModel(OperationModel model) {
        return operationMapper.add(model);
    }

    /**
     * 添加操作记录明细
     */
    public Integer addBatchAttribute(List<AttributeModel> attributeModelList) {
        return attributeMapper.addBatch(attributeModelList);
    }

}
