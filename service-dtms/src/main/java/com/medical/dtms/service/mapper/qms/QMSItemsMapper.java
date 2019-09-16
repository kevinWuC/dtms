package com.medical.dtms.service.mapper.qms;

import com.medical.dtms.common.model.item.QMSItemsModel;
import com.medical.dtms.service.dataobject.item.QMSItemsDO;
import com.medical.dtms.dto.item.query.QMSItemsQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QMSItemsMapper {

    int deleteByPrimaryKey(@Param("itemsId") Long itemsId);

    int insert(QMSItemsDO record);

    QMSItemsDO selectByPrimaryKey(@Param("bizId") Long bizId);

    int updateByPrimaryKeySelective(QMSItemsDO record);

    QMSItemsDO getQMSItemByCode(QMSItemsQuery query);

    List<QMSItemsModel> listQMSItems(QMSItemsQuery query);
}