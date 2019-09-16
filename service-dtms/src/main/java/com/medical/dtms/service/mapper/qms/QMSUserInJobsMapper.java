package com.medical.dtms.service.mapper.qms;

import com.medical.dtms.common.model.syslog.BaseSimpleUserModel;
import com.medical.dtms.dto.user.query.BaseUserQuery;
import com.medical.dtms.service.dataobject.user.QMSUserInJobsDO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QMSUserInJobsMapper {

    Integer deleteQMSUserInJobByCondition(QMSUserInJobsDO jobsDO );

    int insert(List<QMSUserInJobsDO> dos);

    List<QMSUserInJobsDO> listQMSUserInJob(BaseUserQuery query);

    List<BaseSimpleUserModel> listJobIdsByUserIds(List<Long> userIds);
}