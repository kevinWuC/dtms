package com.medical.dtms.service.mapper.datasource;


import com.medical.dtms.dto.datasource.query.QMSTaskQuery;
import com.medical.dtms.service.dataobject.datasource.QMSTaskDO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QMSTaskMapper {
    int insert(QMSTaskDO record);

    QMSTaskDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(QMSTaskDO record);

    QMSTaskDO queryTaskExistOrNot(QMSTaskQuery query);

    List<QMSTaskDO> listTasks();
}