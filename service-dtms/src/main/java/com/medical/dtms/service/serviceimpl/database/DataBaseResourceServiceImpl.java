package com.medical.dtms.service.serviceimpl.database;

import com.github.pagehelper.PageInfo;
import com.medical.dtms.common.model.datasource.UsageOfTablesModel;
import com.medical.dtms.feignservice.databaseresource.DataBaseResourceService;
import com.medical.dtms.common.model.table.DataBaseTableModel;
import com.medical.dtms.common.model.table.TableDetailModel;
import com.medical.dtms.common.model.table.query.DataBaseTableQuery;
import com.medical.dtms.service.manager.database.DataBaseManager;
import com.medical.dtms.service.manager.table.OperateManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @version： DataBaseServiceImpl.java v 1.0, 2019年08月21日 17:47 wuxuelin Exp$
 * @Description
 **/
@RestController
@Slf4j
public class DataBaseResourceServiceImpl implements DataBaseResourceService {

    @Autowired
    private OperateManager operateManager;

    @Autowired
    private DataBaseManager dataBaseManager;

    /**
     * @param [query]
     * @return com.medical.dtms.model.table.DataBaseTableModel
     * @description 数据库资源 - 对象资源管理器
     **/
    @Override
    public DataBaseTableModel listAllTables(@RequestBody DataBaseTableQuery query) {
        DataBaseTableModel model = new DataBaseTableModel();
        if (null == query || StringUtils.isBlank(query.getDataBaseName())) {
            // 查询数据库
            String baseName = operateManager.getDataBaseName();
            query.setDataBaseName(baseName);
        }

        // 如果有数据库，则查询库里的表
        List<String> tables = dataBaseManager.showTables(query.getDataBaseName());
        model.setDataBaseName(query.getDataBaseName());
        model.setTableNameList(tables);
        return model;
    }

    /**
     * @param [query]
     * @return java.util.List<com.medical.dtms.model.table.TableDetailModel>
     * @description 查询表字段具体信息
     **/
    @Override
    public List<TableDetailModel> listTableInfo(@RequestBody DataBaseTableQuery query) {
        return operateManager.listTableInfo(query.getTableName());
    }

    /**
     * @param [query]
     * @return java.lang.Boolean
     * @description 执行 修改与删除操作
     **/
    @Override
    public Boolean executeSql(@RequestBody DataBaseTableQuery query) {
        // 切库
        String baseName = operateManager.getDataBaseName();
        query.setDataBaseName(baseName);
        return true;
    }

    /**
     * @param [query]
     * @return java.lang.Class<?>
     * @description 执行 查询操作 TODO
     **/
    @Override
    public Class<?> executeQuerySql(@RequestBody DataBaseTableQuery query) {
        // 切库
        String baseName = operateManager.getDataBaseName();
        query.setDataBaseName(baseName);

        String table = "";
        if (StringUtils.isBlank(query.getSql())) {
            List<String> tables = dataBaseManager.showTables(query.getDataBaseName());
            if (CollectionUtils.isEmpty(tables)) {
                return null;
            }
            table = tables.get(0);
            table = "tb_dtms_qms_menu";
            query.setTableName(table);
        }
        operateManager.executeQuerySql(table);
        return null;
    }

    /**
     * 数据库备份 - 表空间使用情况
     * @return
     */
    @Override
    public PageInfo<UsageOfTablesModel> showUsageOfTables(@RequestBody DataBaseTableQuery query){
        List<UsageOfTablesModel> list = operateManager.showUsageOfTables();
        if (CollectionUtils.isEmpty(list)){
            return new PageInfo<>(new ArrayList<>());
        }
        return new PageInfo<>(list);
    }

}
