package com.medical.dtms.service.serviceimpl.syslogs;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.medical.dtms.common.enumeration.log.OperationTypeEnum;
import com.medical.dtms.common.model.syslog.QMSSysLogDetailsModel;
import com.medical.dtms.common.util.IdGenerator;
import com.medical.dtms.dto.log.query.QMSSysLogsQuery;
import com.medical.dtms.feignservice.syslogs.SysLogsService;
import com.medical.dtms.common.model.syslog.QMSSysLogsModel;
import com.medical.dtms.service.manager.syslogs.QMSSysLogDetailsManager;
import com.medical.dtms.service.manager.syslogs.SysLogsManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


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
    private QMSSysLogDetailsManager detailsManager;
    @Autowired
    private IdGenerator idGenerator;

    /**
     * 系统日志 - 操作日志列表分页查询
     *
     * @param [query]
     * @return
     **/
    @Override
    public PageInfo<QMSSysLogsModel> pageListSysLogs(@RequestBody QMSSysLogsQuery query) {
        PageHelper.startPage(query.getPageNo(), query.getPageSize());
        List<QMSSysLogsModel> models = logsManager.pageListSysLogs(query);
        if (CollectionUtils.isEmpty(models)) {
            return new PageInfo<>(models);
        }

        for (QMSSysLogsModel model : models) {
            model.setOperationName(model.getOperationType() == null ? null : OperationTypeEnum.getNameByType(model.getOperationType()));
        }
        return new PageInfo<>(models);
    }

    /**
     * 根据 系统日志 id 查询 系统操作日志明细
     *
     * @param query
     * @return
     **/
    @Override
    public List<QMSSysLogDetailsModel> listQMSSysLogDetails(@RequestBody QMSSysLogsQuery query) {
        List<QMSSysLogDetailsModel> list = detailsManager.listQMSSysLogDetails(query);
        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList<>();
        }
        return list;
    }


}
