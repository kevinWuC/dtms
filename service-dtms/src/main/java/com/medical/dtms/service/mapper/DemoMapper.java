package com.medical.dtms.service.mapper;

import org.springframework.stereotype.Repository;

import com.medical.dtms.service.dataobject.demo.LeaveDO;

/**
 * $Id：DemoMapper.java v 1.0, 2019/8/8 9:44 wuxuelin Exp$
 *
 * @Description mapper
 **/
@Repository
public interface DemoMapper {

    /**
     * 保存审批数据
     */
    void startApply(LeaveDO leave);
}
