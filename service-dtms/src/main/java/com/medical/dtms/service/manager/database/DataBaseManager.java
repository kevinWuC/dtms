package com.medical.dtms.service.manager.database;

import com.medical.dtms.common.enumeration.log.OperationTypeEnum;
import com.medical.dtms.common.model.datasource.BackUpInfoModel;
import com.medical.dtms.service.dataobject.datasource.BackUpModel;
import com.medical.dtms.common.util.BeanConvertUtils;
import com.medical.dtms.dto.datasource.QMSBackUpDTO;
import com.medical.dtms.dto.datasource.query.QMSBackUpQuery;
import com.medical.dtms.logclient.service.LogClient;
import com.medical.dtms.service.dataobject.datasource.QMSBackUpDO;
import com.medical.dtms.service.manager.syslogs.SysLoginLogManager;
import com.medical.dtms.service.manager.table.OperateManager;
import com.medical.dtms.service.mapper.datasource.QMSBackUpMapper;
import com.medical.dtms.service.mapper.qms.DataBaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version： DataBaseManager.java v 1.0, 2019年08月22日 9:43 wuxuelin Exp$
 * @Description
 **/
@Service
public class DataBaseManager {

    @Autowired
    private DataBaseMapper dataBaseMapper;
    @Autowired
    private QMSBackUpMapper upDOMapper;
    @Autowired
    private OperateManager operateManager;
    @Autowired
    private SysLoginLogManager loginLogManager;
    @Autowired
    private LogClient logClient;

    /**
     * 根据数据库查询表
     */
    public List<String> showTables(String dataBaseName) {
        return dataBaseMapper.showTables(dataBaseName);
    }

    /**
     * 保存导出的sql 文件信息到数据库
     */
    public Integer insertSqlInfo(QMSBackUpDTO dto) {
        QMSBackUpDO aDo = BeanConvertUtils.convert(dto, QMSBackUpDO.class);
        BackUpModel oldModel = new BackUpModel();

        BackUpModel newModel = new BackUpModel();
        newModel.setComment("数据库备份成功，文件名：" + dto.getSqlFileName());
        // 记录日志
        logClient.logObject(
                // 对象主键
                dto.getBizId(),
                // 操作人
                dto.getCreator(),
                // 操作类型
                OperationTypeEnum.OPERATION_TYPE_OTHER.getType(),
                // 本次操作的别名，这里是操作的表名
                operateManager.getTableName(aDo.getClass()),
                // 本次操作的额外描述，这里记录为操作人的ip
                loginLogManager.getIpByUserId(dto.getCreatorId()),
                // 备注，这里是操作模块名
                "备份数据库",
                // 旧值
                oldModel,
                // 新值
                newModel
        );
        return upDOMapper.insert(aDo);
    }

    /**
     * 主键查询是否存在
     */
    public QMSBackUpDTO selectByPrimaryKey(String bizId) {
        QMSBackUpDO upDO = upDOMapper.selectByPrimaryKey(bizId);
        return BeanConvertUtils.convert(upDO, QMSBackUpDTO.class);
    }

    /**
     * 更新
     */
    public Integer updateByPrimaryKeySelective(QMSBackUpDTO dto) {
        QMSBackUpDO newDo = BeanConvertUtils.convert(dto, QMSBackUpDO.class);

        QMSBackUpDO oldDo = upDOMapper.selectByPrimaryKey(dto.getBizId());

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
                "岗位管理",
                // 旧值
                oldDo,
                // 新值
                newDo
        );

        return upDOMapper.updateByPrimaryKeySelective(newDo);
    }

    /**
     * 查询备份列表
     */
    public List<BackUpInfoModel> ListBackUpInfo(QMSBackUpQuery query) {
        return upDOMapper.ListBackUpInfo(query);
    }

}
