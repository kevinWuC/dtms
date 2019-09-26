package com.medical.dtms.service.manager.database;

import com.medical.dtms.common.enumeration.log.OperationTypeEnum;
import com.medical.dtms.common.util.BeanConvertUtils;
import com.medical.dtms.dto.datasource.QMSTaskDTO;
import com.medical.dtms.dto.datasource.query.QMSTaskQuery;
import com.medical.dtms.logclient.service.LogClient;
import com.medical.dtms.service.dataobject.datasource.BackUpModel;
import com.medical.dtms.service.dataobject.datasource.QMSTaskDO;
import com.medical.dtms.service.manager.syslogs.SysLoginLogManager;
import com.medical.dtms.service.manager.table.OperateManager;
import com.medical.dtms.service.mapper.datasource.QMSTaskMapper;
import com.medical.dtms.service.task.TimingBackUpTask;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @version： QMSTaskManager.java v 1.0, 2019年09月17日 16:14 wuxuelin Exp$
 * @Description
 **/
@Service
public class QMSTaskManager {

    @Autowired
    private QMSTaskMapper taskMapper;
    @Autowired
    private OperateManager operateManager;
    @Autowired
    private SysLoginLogManager loginLogManager;
    @Autowired
    private LogClient logClient;

    /**
     * 添加定时任务信息
     */
    public Integer insert(QMSTaskDTO dto) {
        QMSTaskDO aDo = BeanConvertUtils.convert(dto, QMSTaskDO.class);

        BackUpModel oldModel = new BackUpModel();

        BackUpModel newModel = new BackUpModel();
        newModel.setComment("设置定时备份数据库成功，自动启动时间：" + dto.getExcDate() + ":00:00");


        // 记录日志
        logClient.logObject(
                // 对象主键
                String.valueOf(dto.getBizId()),
                // 操作人
                dto.getCreator(),
                // 操作类型
                OperationTypeEnum.OPERATION_TYPE_OTHER.getType(),
                // 本次操作的别名，这里是操作的表名
                operateManager.getTableName(aDo.getClass()),
                // 本次操作的额外描述，这里记录为操作人的ip
                loginLogManager.getIpByUserId(dto.getCreatorId()),
                // 备注，这里是操作模块名
                "设置定时备份数据库",
                // 旧值
                oldModel,
                // 新值
                newModel
        );


        return taskMapper.insert(aDo);
    }

    /**
     * 校验任务是否已存在
     */
    public QMSTaskDTO queryTaskExistOrNot(QMSTaskQuery query) {
        QMSTaskDO taskDO = taskMapper.queryTaskExistOrNot(query);
        return BeanConvertUtils.convert(taskDO, QMSTaskDTO.class);
    }

    /**
     * 删除(逻辑删除)
     */
    public Integer deleteTask(QMSTaskDTO dto) {
        QMSTaskDO aDo = BeanConvertUtils.convert(dto, QMSTaskDO.class);
        return taskMapper.updateByPrimaryKeySelective(aDo);
    }

    /**
     * 定时任务列表
     */
    public List<TimingBackUpTask> listTasks() {
        List<QMSTaskDO> dtos = taskMapper.listTasks();
        List<TimingBackUpTask> tasks = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(dtos)) {
            TimingBackUpTask task;
            for (QMSTaskDO dto : dtos) {
                StringBuilder sb = new StringBuilder();
                sb.append(0).append(" ").append(0).append(" ").append(dto.getExcDate()).append(" ").append("*").append(" ").append("*").append(" ").append("?");
                String cron = sb.toString();
                task = new TimingBackUpTask(dto.getBizId(), dto.getTaskName(), cron, dto.getCreator(), dto.getCreatorId(), dto.getUrlPath());
                tasks.add(task);
            }
        }
        return tasks;
    }
}
