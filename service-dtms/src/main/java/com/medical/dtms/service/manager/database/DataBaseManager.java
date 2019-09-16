package com.medical.dtms.service.manager.database;

import com.medical.dtms.service.mapper.qms.DataBaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version： DataBaseManager.java v 1.0, 2019年08月22日 9:43 wuxuelin Exp$
 * @Description
 **/
@Service
public class DataBaseManager {

    @Autowired
    private DataBaseMapper dataBaseMapper;

    /**
     * 根据数据库查询表
     */
    public List<String> showTables(String dataBaseName) {
        return dataBaseMapper.showTables(dataBaseName);
    }
}
