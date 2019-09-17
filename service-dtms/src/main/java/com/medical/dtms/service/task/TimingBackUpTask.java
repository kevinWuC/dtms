package com.medical.dtms.service.task;

import com.medical.dtms.dto.datasource.QMSBackUpDTO;
import com.medical.dtms.service.manager.database.DataBaseManager;
import com.medical.dtms.service.manager.table.OperateManager;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import java.util.UUID;

/**
 * @version： TimingBackUpDataBaseTask.java v 1.0, 2019年09月17日 11:02 wuxuelin Exp$
 * @Description
 **/
@Data
@Slf4j
public class TimingBackUpTask extends TimingBackUpAbstractTask {

    public TimingBackUpTask(Long taskId, String taskName, String cron, String creator, String creatorId, String urlPath) {
        super(taskId, taskName, cron, creator, creatorId, urlPath);
    }

    @Override
    public void run(TimingBackUpAbstractTask task) {
        try {
            QMSBackUpDTO dto = new QMSBackUpDTO();
            dto.setBizId(UUID.randomUUID().toString().replaceAll("-", ""));
            dto.setCreator(task.getCreator());
            dto.setCreatorId(task.getCreatorId());
            dto.setUrlPath(task.getUrlPath());
            OperateManager manager = (OperateManager) ApplicationContextUtils.getBean("operateManager");
            manager.exportSql(dto);
            DataBaseManager dataBaseManager = (DataBaseManager) ApplicationContextUtils.getBean("dataBaseManager");
            dataBaseManager.insertSqlInfo(dto);
        } catch (Exception e) {
            log.error("定时备份数据库失败", e);
        }
    }
}
