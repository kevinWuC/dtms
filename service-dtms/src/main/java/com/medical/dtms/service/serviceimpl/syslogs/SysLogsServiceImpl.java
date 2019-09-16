package com.medical.dtms.service.serviceimpl.syslogs;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.medical.dtms.common.util.IdGenerator;
import com.medical.dtms.dto.log.query.QMSSysLogsQuery;
import com.medical.dtms.feignservice.syslogs.SysLogsService;
import com.medical.dtms.common.model.syslog.QMSSysLogsModel;
import com.medical.dtms.service.manager.syslogs.SysLogsManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * @version： SysLogsServiceImpl.java v 1.0, 2019年08月19日 9:50 wuxuelin Exp$
 * @Description 系统日志
 **/
@RestController
@Slf4j
public class SysLogsServiceImpl implements SysLogsService {

    @Autowired
    private SysLogsManager logsManager;
    @Autowired
    private IdGenerator idGenerator;

    /**
     * @param [query]
     * @return com.github.pagehelper.PageInfo<com.medical.dtms.model.syslog.QMSSysLogsModel>
     * @description 系统日志 - 操作日志列表分页查询  TODO
     **/
    @Override
    public PageInfo<QMSSysLogsModel> pageListSysLogs(@RequestBody QMSSysLogsQuery query) {
        PageHelper.startPage(query.getPageNo(), query.getPageSize());
        logsManager.getLogsTable(query);

        return null;
    }


}
