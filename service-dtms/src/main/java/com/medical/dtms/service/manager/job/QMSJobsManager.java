package com.medical.dtms.service.manager.job;

import com.medical.dtms.common.model.job.QMSJobsModel;
import com.medical.dtms.common.util.BeanConvertUtils;
import com.medical.dtms.dto.job.QMSJobsDTO;
import com.medical.dtms.dto.job.query.QMSJobsQuery;
import com.medical.dtms.service.dataobject.job.QMSJobsDO;
import com.medical.dtms.service.mapper.qms.QMSJobsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version： QMSJobManager.java v 1.0, 2019年08月23日 9:40 huangshuaiquan Exp$
 * @Description
 **/
@Service
public class QMSJobsManager {

    @Autowired
    private QMSJobsMapper jobsMapper;


    /**
     * 岗位设置 - 新增
     **/
    public Integer insert(QMSJobsDTO jobsDTO) {
        QMSJobsDO aDo = BeanConvertUtils.convert(jobsDTO, QMSJobsDO.class);
        return jobsMapper.insert(aDo);
    }

    /**
     * 岗位设置 - 根据职位名称做唯一性校验
     **/
    public QMSJobsDTO getQMSJobByCondition(QMSJobsQuery query) {
        QMSJobsDO aDo = jobsMapper.getQMSJobByCondition(query);
        if (null == aDo) {
            return null;
        }
        return BeanConvertUtils.convert(aDo, QMSJobsDTO.class);
    }

    /**
     * 岗位设置 - 删除功能
     */
    public Integer deleteQMSJobDetails(Long bizId) {
        return jobsMapper.deleteByPrimaryKey(bizId);
    }

    /**
     * 岗位设置 - 编辑功能
     */
    public Integer updateQMSJob(QMSJobsDTO jobsDTO) {
        QMSJobsDO aDo = BeanConvertUtils.convert(jobsDTO, QMSJobsDO.class);
        return jobsMapper.updateByPrimaryKeySelective(aDo);
    }

    /**
     * 岗位设置 - 列表查询
     */
    public List<QMSJobsModel> listJobs(QMSJobsQuery query){
        return jobsMapper.listJobs(query);
    }

}
