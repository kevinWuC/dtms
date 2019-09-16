package com.medical.dtms.service.manager.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medical.dtms.common.util.BeanConvertUtils;
import com.medical.dtms.service.dataobject.demo.LeaveDO;
import com.medical.dtms.service.mapper.DemoMapper;

import com.medical.dtms.dto.demo.LeaveDTO;

/**
 * $Id：DemoManager.java v 1.0, 2019/8/8 9:44 wuxuelin Exp$
 *
 * @Description service
 **/
@Service
public class DemoManager {

    @Autowired
    private DemoMapper demoMapper;

    /**
     * 保存审批数据
     */
    public void startApply(LeaveDTO leaveDTO) {
        LeaveDO leaveDO = BeanConvertUtils.convert(leaveDTO, LeaveDO.class);
        demoMapper.startApply(leaveDO);
    }
}
