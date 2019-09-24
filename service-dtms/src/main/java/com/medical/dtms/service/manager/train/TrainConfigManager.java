package com.medical.dtms.service.manager.train;

import com.medical.dtms.common.enumeration.log.OperationTypeEnum;
import com.medical.dtms.common.model.train.TrainConfigModel;
import com.medical.dtms.common.model.train.TrainConfigQueryModel;
import com.medical.dtms.common.util.BeanConvertUtils;
import com.medical.dtms.common.util.DateUtils;
import com.medical.dtms.common.util.IdGenerator;
import com.medical.dtms.dto.train.TrainConfigDTO;
import com.medical.dtms.dto.train.query.TrainConfigQuery;
import com.medical.dtms.service.dataobject.log.QMSSysLogDetailsDO;
import com.medical.dtms.service.dataobject.log.QMSSysLogsDO;
import com.medical.dtms.service.dataobject.train.TrainConfigDO;
import com.medical.dtms.service.manager.syslogs.SysLoginLogManager;
import com.medical.dtms.service.manager.table.OperateManager;
import com.medical.dtms.service.mapper.qms.QMSSysLogDetailsMapper;
import com.medical.dtms.service.mapper.qms.QMSSysLogsMapper;
import com.medical.dtms.service.mapper.train.TrainConfigMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @version： TrainConfigManager.java v 1.0, 2019年08月29日 13:53 huangshuaiquan Exp$
 * @Description
 **/
@Service
public class TrainConfigManager {

    @Autowired
    private TrainConfigMapper trainConfigMapper;
    @Autowired
    private QMSSysLogsMapper qmsSysLogsMapper;
    @Autowired
    private IdGenerator idGenerator;
    @Autowired
    private OperateManager operateManager;
    @Autowired
    private SysLoginLogManager loginLogManager;
    @Autowired
    private QMSSysLogDetailsMapper qmsSysLogDetailsMapper;

    /**
     * 培训配置 - 新增
     **/
    public Integer insert(TrainConfigDTO trainConfigDTO) {
        TrainConfigDO aDo = BeanConvertUtils.convert(trainConfigDTO, TrainConfigDO.class);
        int num = trainConfigMapper.insert(aDo);
        if (num == 1){
            //新增日志记录
            QMSSysLogsDO qmsSysLogsDO = new QMSSysLogsDO();
            qmsSysLogsDO.setBizId(idGenerator.nextId());
            qmsSysLogsDO.setOperationType(OperationTypeEnum.OPERATION_TYPE_INSERT.getType());
            qmsSysLogsDO.setTableName(operateManager.getTableName(aDo.getClass()));
            qmsSysLogsDO.setBusinessName(operateManager.getTableComment(aDo.getClass()));
            qmsSysLogsDO.setObjectId(String.valueOf(aDo.getBizId()));
            qmsSysLogsDO.setOperationIp(loginLogManager.getIpByUserId(aDo.getCreatorId()));
            qmsSysLogsDO.setGmtCreated(new Date());
            qmsSysLogsDO.setCreator(aDo.getCreator());
            qmsSysLogsDO.setCreatorId(aDo.getCreatorId());
            qmsSysLogsDO.setIsDeleted(false);
            int insert = qmsSysLogsMapper.insert(qmsSysLogsDO);
        }
        return num;
    }

    /**
     * 主键查询培训是否存在
     **/
    public TrainConfigDTO selectByPrimaryKey(Long bizId) {
        TrainConfigDO aDo = trainConfigMapper.selectByPrimaryKey(bizId);
        if (null == aDo) {
            return null;
        }
        return BeanConvertUtils.convert(aDo, TrainConfigDTO.class);
    }

    /**
     * 主键查询培训信息
     **/
    public TrainConfigQueryModel selectTrainConfig(Long bizId) {
        return trainConfigMapper.selectTrainConfig(bizId);
    }

    /**
     * 根据培训名称确定唯一性
     **/
    public TrainConfigDTO getTrainConfigByTrainName(TrainConfigQuery query) {
        TrainConfigDO aDo = trainConfigMapper.getTrainConfigByTrainName(query);
        if (null == aDo) {
            return null;
        }
        return BeanConvertUtils.convert(aDo, TrainConfigDTO.class);
    }

    /**
     * 培训配置 - 更新
     **/
    public Integer updateByPrimaryKeySelective(TrainConfigDTO dto) {
        TrainConfigDO aDo = BeanConvertUtils.convert(dto, TrainConfigDO.class);
        //查询没有修改之前的数据
        TrainConfigDO trainConfigDO = trainConfigMapper.selectByPrimaryKey(dto.getBizId());
        int num = trainConfigMapper.updateByPrimaryKeySelective(aDo);
        if (num == 1){
            //新增修改日志记录
            QMSSysLogsDO qmsSysLogsDO = new QMSSysLogsDO();
            qmsSysLogsDO.setBizId(idGenerator.nextId());
            qmsSysLogsDO.setOperationType(OperationTypeEnum.OPERATION_TYPE_UPDATE.getType());
            qmsSysLogsDO.setTableName(operateManager.getTableName(aDo.getClass()));
            qmsSysLogsDO.setBusinessName(operateManager.getTableComment(aDo.getClass()));
            qmsSysLogsDO.setObjectId(String.valueOf(aDo.getBizId()));
            qmsSysLogsDO.setOperationIp(loginLogManager.getIpByUserId(aDo.getModifierId()));
            qmsSysLogsDO.setGmtModified(new Date());
            qmsSysLogsDO.setModifier(aDo.getModifier());
            qmsSysLogsDO.setModifierId(aDo.getModifierId());
            int insert = qmsSysLogsMapper.insert(qmsSysLogsDO);
            addSysLogDetails(trainConfigDO,aDo);
        }
        return num;
    }


    /**
     * 添加日志详情
     * @param trainConfigDO
     * @param aDo
     * @return
     */
    private boolean addSysLogDetails(TrainConfigDO trainConfigDO,TrainConfigDO aDo){
        boolean isSuccess = false;
        if (!StringUtils.equals(trainConfigDO.getTrainName(),aDo.getTrainName())) {
            QMSSysLogDetailsDO qmsSysLogDetailsDO = new QMSSysLogDetailsDO();
            qmsSysLogDetailsDO.setBizId(idGenerator.nextId());
            qmsSysLogDetailsDO.setFieldName("TrainName");
            /*qmsSysLogDetailsDO1.setFieldText();*/
            qmsSysLogDetailsDO.setNewValue(aDo.getTrainName());
            qmsSysLogDetailsDO.setOldValue(trainConfigDO.getTrainName());
            qmsSysLogDetailsDO.setIsDeleted(false);
            qmsSysLogDetailsDO.setCreator(aDo.getModifier());
            qmsSysLogDetailsDO.setCreatorId(String.valueOf(aDo.getModifierId()));
            qmsSysLogDetailsDO.setModifier(aDo.getModifier());
            qmsSysLogDetailsDO.setModifierId(String.valueOf(aDo.getModifierId()));
            qmsSysLogDetailsDO.setGmtModified(new Date());
            qmsSysLogDetailsDO.setGmtCreated(new Date());
            int num = qmsSysLogDetailsMapper.insert(qmsSysLogDetailsDO);
            isSuccess = num == 1 ? true : false;
        }

        if (!StringUtils.equals(trainConfigDO.getTrainDescription(),aDo.getTrainDescription())) {
            QMSSysLogDetailsDO qmsSysLogDetailsDO = new QMSSysLogDetailsDO();
            qmsSysLogDetailsDO.setBizId(idGenerator.nextId());
            qmsSysLogDetailsDO.setFieldName("TrainDescription");
            /*qmsSysLogDetailsDO1.setFieldText();*/
            qmsSysLogDetailsDO.setNewValue(aDo.getTrainDescription());
            qmsSysLogDetailsDO.setOldValue(trainConfigDO.getTrainDescription());
            qmsSysLogDetailsDO.setIsDeleted(false);
            qmsSysLogDetailsDO.setCreator(aDo.getModifier());
            qmsSysLogDetailsDO.setCreatorId(String.valueOf(aDo.getModifierId()));
            qmsSysLogDetailsDO.setModifier(aDo.getModifier());
            qmsSysLogDetailsDO.setModifierId(String.valueOf(aDo.getModifierId()));
            qmsSysLogDetailsDO.setGmtModified(new Date());
            qmsSysLogDetailsDO.setGmtCreated(new Date());
            int num = qmsSysLogDetailsMapper.insert(qmsSysLogDetailsDO);
            isSuccess = num == 1 ? true : false;
        }

        if ((trainConfigDO.getStartDate()).getTime() != (aDo.getStartDate()).getTime()) {
            QMSSysLogDetailsDO qmsSysLogDetailsDO = new QMSSysLogDetailsDO();
            qmsSysLogDetailsDO.setBizId(idGenerator.nextId());
            qmsSysLogDetailsDO.setFieldName("StartDate");
            /*qmsSysLogDetailsDO1.setFieldText();*/
            qmsSysLogDetailsDO.setNewValue(DateUtils.format(aDo.getStartDate(),DateUtils.YYYYMMDDHHMMSS));
            qmsSysLogDetailsDO.setOldValue(DateUtils.format(trainConfigDO.getStartDate(),DateUtils.YYYYMMDDHHMMSS));
            qmsSysLogDetailsDO.setIsDeleted(false);
            qmsSysLogDetailsDO.setCreator(aDo.getModifier());
            qmsSysLogDetailsDO.setCreatorId(String.valueOf(aDo.getModifierId()));
            qmsSysLogDetailsDO.setModifier(aDo.getModifier());
            qmsSysLogDetailsDO.setModifierId(String.valueOf(aDo.getModifierId()));
            qmsSysLogDetailsDO.setGmtModified(new Date());
            qmsSysLogDetailsDO.setGmtCreated(new Date());
            int num = qmsSysLogDetailsMapper.insert(qmsSysLogDetailsDO);
            isSuccess = num == 1 ? true : false;
        }

        if ((trainConfigDO.getEndDate()).getTime() != (aDo.getEndDate()).getTime()) {
            QMSSysLogDetailsDO qmsSysLogDetailsDO = new QMSSysLogDetailsDO();
            qmsSysLogDetailsDO.setBizId(idGenerator.nextId());
            qmsSysLogDetailsDO.setFieldName("EndDate");
            /*qmsSysLogDetailsDO1.setFieldText();*/
            qmsSysLogDetailsDO.setNewValue(DateUtils.format(aDo.getEndDate(),DateUtils.YYYYMMDDHHMMSS));
            qmsSysLogDetailsDO.setOldValue(DateUtils.format(trainConfigDO.getEndDate(),DateUtils.YYYYMMDDHHMMSS));
            qmsSysLogDetailsDO.setIsDeleted(false);
            qmsSysLogDetailsDO.setCreator(aDo.getModifier());
            qmsSysLogDetailsDO.setCreatorId(String.valueOf(aDo.getModifierId()));
            qmsSysLogDetailsDO.setModifier(aDo.getModifier());
            qmsSysLogDetailsDO.setModifierId(String.valueOf(aDo.getModifierId()));
            qmsSysLogDetailsDO.setGmtModified(new Date());
            qmsSysLogDetailsDO.setGmtCreated(new Date());
            int num = qmsSysLogDetailsMapper.insert(qmsSysLogDetailsDO);
            isSuccess = num == 1 ? true : false;
        }

        if (trainConfigDO.getIsStart() != aDo.getIsStart()) {
            QMSSysLogDetailsDO qmsSysLogDetailsDO = new QMSSysLogDetailsDO();
            qmsSysLogDetailsDO.setBizId(idGenerator.nextId());
            qmsSysLogDetailsDO.setFieldName("IsStart");
            /*qmsSysLogDetailsDO1.setFieldText();*/
            qmsSysLogDetailsDO.setNewValue(String.valueOf(aDo.getIsStart()));
            qmsSysLogDetailsDO.setOldValue(String.valueOf(trainConfigDO.getIsStart()));
            qmsSysLogDetailsDO.setIsDeleted(false);
            qmsSysLogDetailsDO.setCreator(aDo.getModifier());
            qmsSysLogDetailsDO.setCreatorId(String.valueOf(aDo.getModifierId()));
            qmsSysLogDetailsDO.setModifier(aDo.getModifier());
            qmsSysLogDetailsDO.setModifierId(String.valueOf(aDo.getModifierId()));
            qmsSysLogDetailsDO.setGmtModified(new Date());
            qmsSysLogDetailsDO.setGmtCreated(new Date());
            int num = qmsSysLogDetailsMapper.insert(qmsSysLogDetailsDO);
            isSuccess = num == 1 ? true : false;
        }
        if (!StringUtils.equals(trainConfigDO.getAnswer(),aDo.getAnswer())) {
            QMSSysLogDetailsDO qmsSysLogDetailsDO = new QMSSysLogDetailsDO();
            qmsSysLogDetailsDO.setBizId(idGenerator.nextId());
            qmsSysLogDetailsDO.setFieldName("Answer");
            /*qmsSysLogDetailsDO1.setFieldText();*/
            qmsSysLogDetailsDO.setNewValue(aDo.getAnswer());
            qmsSysLogDetailsDO.setOldValue(trainConfigDO.getAnswer());
            qmsSysLogDetailsDO.setIsDeleted(false);
            qmsSysLogDetailsDO.setCreator(aDo.getModifier());
            qmsSysLogDetailsDO.setCreatorId(String.valueOf(aDo.getModifierId()));
            qmsSysLogDetailsDO.setModifier(aDo.getModifier());
            qmsSysLogDetailsDO.setModifierId(String.valueOf(aDo.getModifierId()));
            qmsSysLogDetailsDO.setGmtModified(new Date());
            qmsSysLogDetailsDO.setGmtCreated(new Date());
            int num = qmsSysLogDetailsMapper.insert(qmsSysLogDetailsDO);
            isSuccess = num == 1 ? true : false;
        }

        if (!StringUtils.equals(trainConfigDO.getTrainInfo(),aDo.getTrainInfo())) {
            QMSSysLogDetailsDO qmsSysLogDetailsDO = new QMSSysLogDetailsDO();
            qmsSysLogDetailsDO.setBizId(idGenerator.nextId());
            qmsSysLogDetailsDO.setFieldName("TrainInfo");
            /*qmsSysLogDetailsDO1.setFieldText();*/
            qmsSysLogDetailsDO.setNewValue(aDo.getTrainInfo());
            qmsSysLogDetailsDO.setOldValue(trainConfigDO.getTrainInfo());
            qmsSysLogDetailsDO.setIsDeleted(false);
            qmsSysLogDetailsDO.setCreator(aDo.getModifier());
            qmsSysLogDetailsDO.setCreatorId(String.valueOf(aDo.getModifierId()));
            qmsSysLogDetailsDO.setModifier(aDo.getModifier());
            qmsSysLogDetailsDO.setModifierId(String.valueOf(aDo.getModifierId()));
            qmsSysLogDetailsDO.setGmtModified(new Date());
            qmsSysLogDetailsDO.setGmtCreated(new Date());
            int num = qmsSysLogDetailsMapper.insert(qmsSysLogDetailsDO);
            isSuccess = num == 1 ? true : false;
        }
        if (!StringUtils.equals(trainConfigDO.getExamName(),aDo.getExamName())) {
            QMSSysLogDetailsDO qmsSysLogDetailsDO = new QMSSysLogDetailsDO();
            qmsSysLogDetailsDO.setBizId(idGenerator.nextId());
            qmsSysLogDetailsDO.setFieldName("ExamName");
            /*qmsSysLogDetailsDO1.setFieldText();*/
            qmsSysLogDetailsDO.setNewValue(aDo.getExamName());
            qmsSysLogDetailsDO.setOldValue(trainConfigDO.getExamName());
            qmsSysLogDetailsDO.setIsDeleted(false);
            qmsSysLogDetailsDO.setCreator(aDo.getModifier());
            qmsSysLogDetailsDO.setCreatorId(String.valueOf(aDo.getModifierId()));
            qmsSysLogDetailsDO.setModifier(aDo.getModifier());
            qmsSysLogDetailsDO.setModifierId(String.valueOf(aDo.getModifierId()));
            qmsSysLogDetailsDO.setGmtModified(new Date());
            qmsSysLogDetailsDO.setGmtCreated(new Date());
            int num = qmsSysLogDetailsMapper.insert(qmsSysLogDetailsDO);
            isSuccess = num == 1 ? true : false;
        }
        if (trainConfigDO.getPassPoint() != aDo.getPassPoint()) {
            QMSSysLogDetailsDO qmsSysLogDetailsDO = new QMSSysLogDetailsDO();
            qmsSysLogDetailsDO.setBizId(idGenerator.nextId());
            qmsSysLogDetailsDO.setFieldName("PassPoint");
            /*qmsSysLogDetailsDO1.setFieldText();*/
            qmsSysLogDetailsDO.setNewValue(String.valueOf(aDo.getPassPoint()));
            qmsSysLogDetailsDO.setOldValue(String.valueOf(trainConfigDO.getPassPoint()));
            qmsSysLogDetailsDO.setIsDeleted(false);
            qmsSysLogDetailsDO.setCreator(aDo.getModifier());
            qmsSysLogDetailsDO.setCreatorId(String.valueOf(aDo.getModifierId()));
            qmsSysLogDetailsDO.setModifier(aDo.getModifier());
            qmsSysLogDetailsDO.setModifierId(String.valueOf(aDo.getModifierId()));
            qmsSysLogDetailsDO.setGmtModified(new Date());
            qmsSysLogDetailsDO.setGmtCreated(new Date());
            int num = qmsSysLogDetailsMapper.insert(qmsSysLogDetailsDO);
            isSuccess = num == 1 ? true : false;
        }
        if (trainConfigDO.getTotalPoint() != aDo.getTotalPoint()) {
            QMSSysLogDetailsDO qmsSysLogDetailsDO = new QMSSysLogDetailsDO();
            qmsSysLogDetailsDO.setBizId(idGenerator.nextId());
            qmsSysLogDetailsDO.setFieldName("TotalPoint");
            /*qmsSysLogDetailsDO1.setFieldText();*/
            qmsSysLogDetailsDO.setNewValue(String.valueOf(aDo.getTotalPoint()));
            qmsSysLogDetailsDO.setOldValue(String.valueOf(trainConfigDO.getTotalPoint()));
            qmsSysLogDetailsDO.setIsDeleted(false);
            qmsSysLogDetailsDO.setCreator(aDo.getModifier());
            qmsSysLogDetailsDO.setCreatorId(String.valueOf(aDo.getModifierId()));
            qmsSysLogDetailsDO.setModifier(aDo.getModifier());
            qmsSysLogDetailsDO.setModifierId(String.valueOf(aDo.getModifierId()));
            qmsSysLogDetailsDO.setGmtModified(new Date());
            qmsSysLogDetailsDO.setGmtCreated(new Date());
            int num = qmsSysLogDetailsMapper.insert(qmsSysLogDetailsDO);
            isSuccess = num == 1 ? true : false;
        }
        if (trainConfigDO.getReadFen() != aDo.getReadFen()) {
            QMSSysLogDetailsDO qmsSysLogDetailsDO = new QMSSysLogDetailsDO();
            qmsSysLogDetailsDO.setBizId(idGenerator.nextId());
            qmsSysLogDetailsDO.setFieldName("ReadFen");
            /*qmsSysLogDetailsDO1.setFieldText();*/
            qmsSysLogDetailsDO.setNewValue(String.valueOf(aDo.getReadFen()));
            qmsSysLogDetailsDO.setOldValue(String.valueOf(trainConfigDO.getReadFen()));
            qmsSysLogDetailsDO.setIsDeleted(false);
            qmsSysLogDetailsDO.setCreator(aDo.getModifier());
            qmsSysLogDetailsDO.setCreatorId(String.valueOf(aDo.getModifierId()));
            qmsSysLogDetailsDO.setModifier(aDo.getModifier());
            qmsSysLogDetailsDO.setModifierId(String.valueOf(aDo.getModifierId()));
            qmsSysLogDetailsDO.setGmtModified(new Date());
            qmsSysLogDetailsDO.setGmtCreated(new Date());
            int num = qmsSysLogDetailsMapper.insert(qmsSysLogDetailsDO);
            isSuccess = num == 1 ? true : false;
        }
        return isSuccess;

    }

    /**
     * 培训配置 - 列表查询
     **/
    public List<TrainConfigModel> listTrainConfigs(TrainConfigQuery query) {
        return trainConfigMapper.listTrainConfigs(query);
    }

    /**
     * 培训配置 - 删除
     **/
    public Integer deleteByPrimaryKey(Long bizId) {
        TrainConfigDO aDo = trainConfigMapper.selectByPrimaryKey(bizId);
        int num = trainConfigMapper.deleteByPrimaryKey(bizId);
        if (num == 1){
            //新增修改日志记录
            QMSSysLogsDO qmsSysLogsDO = new QMSSysLogsDO();
            qmsSysLogsDO.setBizId(idGenerator.nextId());
            qmsSysLogsDO.setOperationType(OperationTypeEnum.OPERATION_TYPE_DELETE.getType());
            qmsSysLogsDO.setTableName(operateManager.getTableName(aDo.getClass()));
            qmsSysLogsDO.setBusinessName(operateManager.getTableComment(aDo.getClass()));
            qmsSysLogsDO.setObjectId(String.valueOf(aDo.getBizId()));
            qmsSysLogsDO.setOperationIp(loginLogManager.getIpByUserId(aDo.getModifierId()));
            qmsSysLogsDO.setGmtModified(new Date());
            qmsSysLogsDO.setModifier(aDo.getModifier());
            qmsSysLogsDO.setModifierId(aDo.getModifierId());
            int insert = qmsSysLogsMapper.insert(qmsSysLogsDO);

        }
        return num;
    }


}
