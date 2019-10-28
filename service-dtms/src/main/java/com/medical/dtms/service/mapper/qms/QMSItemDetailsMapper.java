package com.medical.dtms.service.mapper.qms;

import com.medical.dtms.common.model.dropdown.DropDownDetailsModel;
import com.medical.dtms.common.model.dropdown.DropDownModel;
import com.medical.dtms.common.model.dropdown.query.DropDownQuery;
import com.medical.dtms.common.model.item.QMSItemDetailsModel;
import com.medical.dtms.common.model.item.SimpleQMSItemDetailsModel;
import com.medical.dtms.common.model.question.QuestionItemModel;
import com.medical.dtms.dto.item.query.QMSItemDetailsQuery;
import com.medical.dtms.service.dataobject.item.QMSItemDetailsDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface QMSItemDetailsMapper {
    int deleteByPrimaryKey(@Param("bizId") Long bizId);

    int insert(QMSItemDetailsDO record);

    QMSItemDetailsDO selectByPrimaryKey(String itemdetailsid);

    int updateByPrimaryKeySelective(QMSItemDetailsDO record);

    List<QMSItemDetailsModel> listDetailsByItemsId(QMSItemDetailsQuery query);

    QMSItemDetailsDO queryQMSItemDetails(String itemsName);

    Integer deleteByItemId(@Param("itemsId") Long itemsId);

    QMSItemDetailsDO getQMSItemDetailsByCondition(QMSItemDetailsQuery query);

    List<DropDownModel> listAllFileTypes(DropDownQuery query);

    List<QuestionItemModel> listDetailsByItemsIdForQuestion(@Param("itemsId") Long itemsId);

//    List<QuestionItemModel> queryDetailsList(List<Long> questionsTypeIds);

    List<DropDownDetailsModel> listTypeDetailsName(@Param("itemsId") Long itemsId);

    List<SimpleQMSItemDetailsModel> getQMSItemDetailsByIds(List<Long> trainTypeIds);

}