package com.medical.dtms.service.manager.train;

import com.medical.dtms.common.enumeration.log.OperationTypeEnum;
import com.medical.dtms.common.model.train.TrainConfigModel;
import com.medical.dtms.common.model.train.TrainConfigQueryModel;
import com.medical.dtms.common.util.BeanConvertUtils;
import com.medical.dtms.common.util.DateUtils;
import com.medical.dtms.common.util.IdGenerator;
import com.medical.dtms.dto.train.TrainConfigDTO;
import com.medical.dtms.dto.train.query.TrainConfigQuery;
import com.medical.dtms.logclient.service.LogClient;
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
    private LogClient logClient;
    @Autowired
    private QMSSysLogDetailsMapper qmsSysLogDetailsMapper;

    /**
     * 培训配置 - 新增
     **/
    public Integer insert(TrainConfigDTO trainConfigDTO) {
        TrainConfigDO aDo = BeanConvertUtils.convert(trainConfigDTO, TrainConfigDO.class);
        int num = trainConfigMapper.insert(aDo);
        if (num == 1) {
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
        TrainConfigDO newDo = BeanConvertUtils.convert(dto, TrainConfigDO.class);
        //查询没有修改之前的数据
        TrainConfigDO oldDo = trainConfigMapper.selectByPrimaryKey(dto.getBizId());

        // 记录日志
        logClient.logObject(
                // 对象主键
                String.valueOf(oldDo.getBizId()),
                // 操作人
                dto.getModifier(),
                // 操作类型
                dto.getIsDeleted() == null ? OperationTypeEnum.OPERATION_TYPE_UPDATE.getType() : dto.getIsDeleted() == true ? OperationTypeEnum.OPERATION_TYPE_DELETE.getType() : OperationTypeEnum.OPERATION_TYPE_UPDATE.getType(),
                // 本次操作的别名，这里是操作的表名
                operateManager.getTableName(newDo.getClass()),
                // 本次操作的额外描述，这里记录为操作人的ip
                loginLogManager.getIpByUserId(dto.getModifierId()),
                // 备注，这里是操作模块名
                "文件管理",
                // 旧值
                oldDo,
                // 新值
                newDo
        );

        return trainConfigMapper.updateByPrimaryKeySelective(newDo);
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
    public Integer deleteByPrimaryKey(TrainConfigDTO dto) {
        TrainConfigDO oldDo = trainConfigMapper.selectByPrimaryKey(dto.getBizId());

        TrainConfigDO newDo = BeanConvertUtils.convert(dto, TrainConfigDO.class);
        // 记录日志
        logClient.logObject(
                // 对象主键
                String.valueOf(dto.getBizId()),
                // 操作人
                dto.getModifier(),
                // 操作类型
                dto.getIsDeleted() == null ? OperationTypeEnum.OPERATION_TYPE_UPDATE.getType() : dto.getIsDeleted() == true ? OperationTypeEnum.OPERATION_TYPE_DELETE.getType() : OperationTypeEnum.OPERATION_TYPE_UPDATE.getType(),
                // 本次操作的别名，这里是操作的表名
                operateManager.getTableName(newDo.getClass()),
                // 本次操作的额外描述，这里记录为操作人的ip
                loginLogManager.getIpByUserId(dto.getModifierId()),
                // 备注，这里是操作模块名
                "文件管理",
                // 旧值
                oldDo,
                // 新值
                newDo
        );
        return trainConfigMapper.deleteByPrimaryKey(dto.getBizId());
    }
}
