package com.medical.dtms.service.mapper.datasource;

import com.medical.dtms.common.model.datasource.BackUpInfoModel;
import com.medical.dtms.dto.datasource.query.QMSBackUpQuery;
import com.medical.dtms.service.dataobject.datasource.QMSBackUpDO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QMSBackUpMapper {

    int insert(QMSBackUpDO record);

    QMSBackUpDO selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(QMSBackUpDO record);

    List<BackUpInfoModel> ListBackUpInfo(QMSBackUpQuery query);
}