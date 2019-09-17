package com.medical.dtms.service.manager.syslogs;

import com.medical.dtms.common.util.BeanConvertUtils;
import com.medical.dtms.dto.log.QMSSysLogsDTO;
import com.medical.dtms.dto.log.query.QMSSysLogsQuery;
import com.medical.dtms.service.dataobject.log.QMSSysLogsDO;
import com.medical.dtms.service.dataobject.user.QMSUserDO;
import com.medical.dtms.service.manager.table.OperateManager;
import com.medical.dtms.service.mapper.qms.QMSSysLogsMapper;
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
    private QMSSysLogsMapper qmsSysLogsMapper;

    /**
     * 系统日志--新增日志
     * @param dto
     * @return
     */
    public Integer insert(QMSSysLogsDTO dto) {
        QMSSysLogsDO aDo = BeanConvertUtils.convert(dto, QMSSysLogsDO.class);
        return qmsSysLogsMapper.insert(aDo);
    }


}
