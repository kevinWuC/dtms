package com.medical.dtms.service.manager.item;

import com.medical.dtms.common.util.BeanConvertUtils;
import com.medical.dtms.common.model.item.QMSItemsModel;
import com.medical.dtms.service.dataobject.item.QMSItemsDO;
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
    public Integer insert(QMSItemsDTO itemsDTO) {
        QMSItemsDO aDo = BeanConvertUtils.convert(itemsDTO, QMSItemsDO.class);
        return qmsItemsMapper.insert(aDo);
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
        QMSItemsDO aDo = BeanConvertUtils.convert(dto, QMSItemsDO.class);
        return qmsItemsMapper.updateByPrimaryKeySelective(aDo);
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
