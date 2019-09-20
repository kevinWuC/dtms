package com.medical.dtms.service.mapper.qms;

import com.medical.dtms.service.dataobject.log.QMSSysLogsDO;
import org.springframework.stereotype.Repository;

@Repository
public interface QMSSysLogsMapper {
    int deleteByPrimaryKey(String syslogsid);

    int insert(QMSSysLogsDO record);

    int insertSelective(QMSSysLogsDO record);

    QMSSysLogsDO selectByPrimaryKey(String syslogsid);

    int updateByPrimaryKeySelective(QMSSysLogsDO record);

    int updateByPrimaryKey(QMSSysLogsDO record);
}