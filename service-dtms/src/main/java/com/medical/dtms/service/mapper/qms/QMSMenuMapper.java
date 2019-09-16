package com.medical.dtms.service.mapper.qms;

import com.medical.dtms.dto.menu.query.QMSMenuQuery;
import com.medical.dtms.common.model.menu.QMSMenuModel;
import com.medical.dtms.service.dataobject.menu.QMSMenuDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QMSMenuMapper {

    int deleteByPrimaryKey(@Param("bizId") Long bizId);

    int deleteByPatentId(@Param("bizId") Long bizId);

    int insert(QMSMenuDO record);

    int updateByPrimaryKeySelective(QMSMenuDO record);

    QMSMenuDO getQMSMenuByCondition(QMSMenuQuery menuQuery);

    List<QMSMenuDO> listQMSMenus(QMSMenuQuery query);

    List<QMSMenuModel> listParentInfos(List<Long> parentIds);
}