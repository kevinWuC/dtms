package com.medical.dtms.service.manager.user;

import com.medical.dtms.common.model.syslog.BaseSimpleUserModel;
import com.medical.dtms.common.util.BeanConvertUtils;
import com.medical.dtms.dto.user.QMSUserInJobsDTO;
import com.medical.dtms.dto.user.query.BaseUserQuery;
import com.medical.dtms.service.dataobject.user.QMSUserInJobsDO;
import com.medical.dtms.service.mapper.qms.QMSUserInJobsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version： QMSUserInJobsManager.java v 1.0, 2019年08月20日 20:46 wuxuelin Exp$
 * @Description
 **/
@Service
public class QMSUserInJobsManager {

    @Autowired
    private QMSUserInJobsMapper userInJobsMapper;

    /**
     * 查询用户是否关联职位
     */
    public List<QMSUserInJobsDTO> listQMSUserInJob(BaseUserQuery query) {
        List<QMSUserInJobsDO> list = userInJobsMapper.listQMSUserInJob(query);
        return BeanConvertUtils.convertList(list, QMSUserInJobsDTO.class);
    }

    /**
     * 根据用户 id 删除数据
     */
    public Integer deleteQMSUserInJobByUserId(Long userId) {
        QMSUserInJobsDO jobsDO = new QMSUserInJobsDO();
        jobsDO.setUserId(userId);
        return userInJobsMapper.deleteQMSUserInJobByCondition(jobsDO);
    }

    /**
     * 根据岗位 id 删除数据
     */
    public Integer deleteQMSUserInJobByJobId(Long jobId) {
        QMSUserInJobsDO jobsDO = new QMSUserInJobsDO();
        jobsDO.setUserId(jobId);
        return userInJobsMapper.deleteQMSUserInJobByCondition(jobsDO);
    }

    /**
     * 新增 用户与岗位关联
     */
    public Integer insert(List<QMSUserInJobsDTO> dtoList) {
        List<QMSUserInJobsDO> dos = BeanConvertUtils.convertList(dtoList, QMSUserInJobsDO.class);
        return userInJobsMapper.insert(dos);
    }

    /** 根据用户ids 查询 岗位 id*/
    public List<BaseSimpleUserModel> listJobIdsByUserIds(List<Long> userIds){
        return userInJobsMapper.listJobIdsByUserIds(userIds);
    }
}
