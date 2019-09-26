package com.medical.dtms.service.manager.job;

import com.medical.dtms.common.enumeration.log.OperationTypeEnum;
import com.medical.dtms.common.model.job.QMSJobsModel;
import com.medical.dtms.common.util.BeanConvertUtils;
import com.medical.dtms.dto.job.QMSJobsDTO;
import com.medical.dtms.dto.job.query.QMSJobsQuery;
import com.medical.dtms.logclient.service.LogClient;
import com.medical.dtms.service.dataobject.job.QMSJobsDO;
import com.medical.dtms.service.manager.syslogs.SysLoginLogManager;
import com.medical.dtms.service.manager.table.OperateManager;
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
    @Autowired
    private OperateManager operateManager;
    @Autowired
    private SysLoginLogManager loginLogManager;
    @Autowired
    private LogClient logClient;


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

        QMSJobsQuery query = new QMSJobsQuery(jobsDTO.getBizId());
        QMSJobsDO oldJob = jobsMapper.getQMSJobByCondition(query);

        QMSJobsDO newJob = BeanConvertUtils.convert(jobsDTO, QMSJobsDO.class);

        // 记录日志
        logClient.logObject(
                // 对象主键
                oldJob.getBizId(),
                // 操作人
                oldJob.getModifier(),
                // 操作类型
                jobsDTO.getIsDeleted() == null ? OperationTypeEnum.OPERATION_TYPE_UPDATE.getType() : jobsDTO.getIsDeleted() == true ? OperationTypeEnum.OPERATION_TYPE_DELETE.getType() : OperationTypeEnum.OPERATION_TYPE_UPDATE.getType(),
                // 本次操作的别名，这里是操作的表名
                operateManager.getTableName(newJob.getClass()),
                // 本次操作的额外描述，这里记录为操作人的ip
                loginLogManager.getIpByUserId(jobsDTO.getModifierId()),
                // 备注，这里是操作模块名
                "菜单管理",
                // 旧值
                oldJob,
                // 新值
                newJob
        );

        return jobsMapper.updateByPrimaryKeySelective(newJob);
    }

    /**
     * 岗位设置 - 列表查询
     */
    public List<QMSJobsModel> listJobs(QMSJobsQuery query) {
        return jobsMapper.listJobs(query);
    }

    /**
     * 根据部门 id  查询 职位 信息
     */
    public List<QMSJobsModel> listJobsByDeptIds(List<String> lastIds) {
        return jobsMapper.listJobsByDeptIds(lastIds);
    }


}
