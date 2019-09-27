package com.medical.dtms.service.manager.item;

import com.medical.dtms.common.enumeration.log.OperationTypeEnum;
import com.medical.dtms.common.util.BeanConvertUtils;
import com.medical.dtms.common.model.item.QMSItemsModel;
import com.medical.dtms.logclient.service.LogClient;
import com.medical.dtms.service.dataobject.item.QMSItemsDO;
import com.medical.dtms.service.manager.syslogs.SysLoginLogManager;
import com.medical.dtms.service.manager.table.OperateManager;
import com.medical.dtms.service.mapper.qms.QMSItemsMapper;
import com.medical.dtms.dto.item.QMSItemsDTO;
import com.medical.dtms.dto.item.query.QMSItemsQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version： QMSItemsManager.java v 1.0, 2019年08月14日 14:17 wuxuelin Exp$
 * @Description
 **/
@Service
public class QMSItemsManager {

    @Autowired
    private QMSItemsMapper qmsItemsMapper;
    @Autowired
    private OperateManager operateManager;
    @Autowired
    private SysLoginLogManager loginLogManager;
    @Autowired
    private LogClient logClient;

    /**
     * 字典类别 - 根据编号做唯一性校验
     */
    public QMSItemsDTO getQMSItemByCode(QMSItemsQuery query) {
        QMSItemsDO itemsDO = qmsItemsMapper.getQMSItemByCode(query);
        if (null == itemsDO) {
            return null;
        }
        return BeanConvertUtils.convert(itemsDO, QMSItemsDTO.class);
    }

    /**
     * 字典类别 - 新增
     */
    public Integer insert(QMSItemsDTO dto) {
        QMSItemsDO newDo = BeanConvertUtils.convert(dto, QMSItemsDO.class);

        QMSItemsDO oldDo = new QMSItemsDO();
        // 记录日志
        logClient.logObject(
                // 对象主键
                String.valueOf(oldDo.getBizId()),
                // 操作人
                dto.getCreator(),
                // 操作类型
                OperationTypeEnum.OPERATION_TYPE_INSERT.getType(),
                // 本次操作的别名，这里是操作的表名
                operateManager.getTableName(newDo.getClass()),
                // 本次操作的额外描述，这里记录为操作人的ip
                loginLogManager.getIpByUserId(dto.getCreatorId()),
                // 备注，这里是操作模块名
                "字典类别管理",
                // 旧值
                oldDo,
                // 新值
                newDo
        );

        return qmsItemsMapper.insert(newDo);
    }

    /**
     * 字典类别 - 主键查询是否存在
     */
    public QMSItemsDTO selectByPrimaryKey(Long bizId) {
        QMSItemsDO aDo = qmsItemsMapper.selectByPrimaryKey(bizId);
        if (null == aDo) {
            return null;
        }
        return BeanConvertUtils.convert(aDo, QMSItemsDTO.class);
    }

    /**
     * 字典类别 - 更新
     */
    public Integer updateByPrimaryKeySelective(QMSItemsDTO dto) {

        QMSItemsQuery query = new QMSItemsQuery();
        query.setBizId(dto.getBizId());
        QMSItemsDO oldItem = qmsItemsMapper.getQMSItemByCode(query);

        QMSItemsDO newItem = BeanConvertUtils.convert(dto, QMSItemsDO.class);

        // 记录日志
        logClient.logObject(
                // 对象主键
                String.valueOf(oldItem.getBizId()),
                // 操作人
                dto.getModifier(),
                // 操作类型
                dto.getIsDeleted() == null ? OperationTypeEnum.OPERATION_TYPE_UPDATE.getType() : dto.getIsDeleted() == true ? OperationTypeEnum.OPERATION_TYPE_DELETE.getType() : OperationTypeEnum.OPERATION_TYPE_UPDATE.getType(),
                // 本次操作的别名，这里是操作的表名
                operateManager.getTableName(newItem.getClass()),
                // 本次操作的额外描述，这里记录为操作人的ip
                loginLogManager.getIpByUserId(dto.getModifierId()),
                // 备注，这里是操作模块名
                "字典类别管理",
                // 旧值
                oldItem,
                // 新值
                newItem
        );

        return qmsItemsMapper.updateByPrimaryKeySelective(newItem);
    }

    /**
     * 字典类别 - 删除（物理删除）
     */
    public Integer deleteByPrimaryKey(Long itemsId) {
        return qmsItemsMapper.deleteByPrimaryKey(itemsId);
    }

    /**
     * 字典类别 - 列表
     */
    public List<QMSItemsModel> listQMSItems(QMSItemsQuery query) {
        return qmsItemsMapper.listQMSItems(query);
    }
}
