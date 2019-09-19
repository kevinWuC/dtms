package com.medical.dtms.service.mapper.qms;

import com.medical.dtms.common.model.job.QMSJobsModel;
import com.medical.dtms.dto.job.query.QMSJobsQuery;
import com.medical.dtms.service.dataobject.job.QMSJobsDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QMSJobsMapper {

    List<QMSJobsModel> listJobs(QMSJobsQuery query);

    int deleteByPrimaryKey(@Param("bizId") Long bizId);

    QMSJobsDO getQMSJobByCondition(QMSJobsQuery query);

    int insert(QMSJobsDO record);

    int insertSelective(QMSJobsDO record);

    QMSJobsDO selectByPrimaryKey(@Param("bizId") Long bizId);

    int updateByPrimaryKeySelective(QMSJobsDO record);

    int updateByPrimaryKey(QMSJobsDO record);

    List<QMSJobsModel> listJobsByDeptIds(List<String> lastIds);

}