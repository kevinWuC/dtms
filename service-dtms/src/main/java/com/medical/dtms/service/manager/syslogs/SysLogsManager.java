package com.medical.dtms.service.manager.syslogs;

import com.medical.dtms.dto.log.query.QMSSysLogsQuery;
import com.medical.dtms.service.dataobject.log.QMSSysLogsDO;
import com.medical.dtms.service.manager.table.OperateManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version： SysLogsManager.java v 1.0, 2019年08月19日 11:21 wuxuelin Exp$
 * @Description
 **/
@Service
@Slf4j
public class SysLogsManager {

    @Autowired
    private OperateManager operateManager;

    public List<QMSSysLogsDO> getLogsTable(QMSSysLogsQuery query) {
//        String comment = tableManager.getTableComment(aDo.getClass());


        return null;
    }


}
