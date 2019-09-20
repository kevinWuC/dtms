package com.medical.dtms.service.manager.syslogs;

import com.medical.dtms.common.model.syslog.QMSSysLogDetailsModel;
import com.medical.dtms.dto.log.query.QMSSysLogsQuery;
import com.medical.dtms.service.mapper.qms.QMSSysLogDetailsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version： QMSSysLogDetailsManager.java v 1.0, 2019年09月19日 21:49 wuxuelin Exp$
 * @Description
 **/
@Service
public class QMSSysLogDetailsManager {

    @Autowired
    private QMSSysLogDetailsMapper detailsMapper;

    /**
     * 根据 系统日志 id 查询 系统操作日志明细
     */
    public List<QMSSysLogDetailsModel> listQMSSysLogDetails(QMSSysLogsQuery query) {
        return detailsMapper.listQMSSysLogDetails(query);
    }
}
