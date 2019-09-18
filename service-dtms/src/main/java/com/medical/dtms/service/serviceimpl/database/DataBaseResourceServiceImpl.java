package com.medical.dtms.service.serviceimpl.database;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.medical.dtms.common.eception.BizException;
import com.medical.dtms.common.enumeration.ErrorCodeEnum;
import com.medical.dtms.common.model.datasource.BackUpInfoModel;
import com.medical.dtms.common.model.datasource.UsageOfTablesModel;
import com.medical.dtms.common.util.IdGenerator;
import com.medical.dtms.common.util.Paginator;
import com.medical.dtms.dto.datasource.QMSBackUpDTO;
import com.medical.dtms.dto.datasource.QMSTaskDTO;
import com.medical.dtms.dto.datasource.query.QMSBackUpQuery;
import com.medical.dtms.dto.datasource.query.QMSTaskQuery;
import com.medical.dtms.feignservice.databaseresource.DataBaseResourceService;
import com.medical.dtms.common.model.table.DataBaseTableModel;
import com.medical.dtms.common.model.table.TableDetailModel;
import com.medical.dtms.common.model.table.query.DataBaseTableQuery;
import com.medical.dtms.service.manager.database.DataBaseManager;
import com.medical.dtms.service.manager.database.QMSTaskManager;
import com.medical.dtms.service.manager.table.OperateManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    @Autowired
    private IdGenerator idGenerator;
    @Autowired
    private QMSTaskManager taskManager;

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
     *
     * @return
     */
    @Override
    public Paginator<UsageOfTablesModel> showUsageOfTables(@RequestBody DataBaseTableQuery query) {
        List<UsageOfTablesModel> list = operateManager.showUsageOfTables();
        List<UsageOfTablesModel> newList = new ArrayList<>();
        Long totalCount = 0L;
        if (CollectionUtils.isEmpty(list)) {
            return new Paginator<>();
        }
        for (UsageOfTablesModel model : list) {
            if (StringUtils.isBlank(model.getIndexUsageSum()) && StringUtils.isNotBlank(model.getUsageSum())) {
                model.setEmptySum(Double.valueOf(model.getUsageSum().replace("MB", "")) + "MB");
            }
            if (StringUtils.isBlank(model.getUsageSum())) {
                model.setEmptySum(0.00 + "MB");
            }
            if (StringUtils.isNotBlank(model.getIndexUsageSum()) && StringUtils.isNotBlank(model.getUsageSum())) {
                Double used = Double.valueOf(model.getIndexUsageSum().replace("MB", ""));
                Double sum = Double.valueOf(model.getUsageSum().replace("MB", ""));
                if (sum.compareTo(used) == -1 || sum.compareTo(used) == 0) {
                    model.setEmptySum(0.00 + "MB");
                } else {
                    double v = sum - used;
                    model.setEmptySum(v + "MB");
                }
            }
        }
        // 手动分页
        int offset = query.getPageNo() > 1 ? (query.getPageNo() - 1) * query.getPageSize() : 0;
        for (int i = 0; i < query.getPageSize() && i < list.size() - offset; i++) {
            UsageOfTablesModel model = list.get(offset + i);
            newList.add(model);
        }
        totalCount = Long.valueOf(newList.size());

        return new Paginator<>(query.getPageNo(), query.getPageSize(), totalCount,newList);
    }

    /**
     * @param [urlPath]
     * @return java.lang.String
     * @description 数据库备份 - 备份
     **/
    @Override
    public Boolean exportSql(@RequestBody QMSBackUpDTO dto) {
        try {
            operateManager.exportSql(dto);
            dto.setBizId(UUID.randomUUID().toString().replaceAll("-", ""));
            dataBaseManager.insertSqlInfo(dto);
        } catch (Exception e) {
            log.error("数据库备份失败", e);
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
        }
        return true;
    }

    /***
     *
     * @description 删除记录
     * @param  [dto]
     * @return java.lang.Boolean
     **/
    @Override
    public Boolean deleteSqlBackUp(@RequestBody QMSBackUpDTO dto) {
        QMSBackUpDTO backUpDTO = dataBaseManager.selectByPrimaryKey(dto.getBizId());
        if (null == backUpDTO) {
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), "该条记录不存在，无法操作！");
        }
        backUpDTO.setIsDeleted(true);
        try {
            dataBaseManager.updateByPrimaryKeySelective(backUpDTO);
        } catch (Exception e) {
            log.error("删除失败", e);
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
        }
        return true;
    }

    /**
     * @param [query]
     * @return com.github.pagehelper.PageInfo<com.medical.dtms.common.model.datasource.BackUpInfoModel>
     * @description 分页展示记录
     **/
    @Override
    public PageInfo<BackUpInfoModel> pageListBackUpInfo(@RequestBody QMSBackUpQuery query) {
        PageHelper.startPage(query.getPageNo(), query.getPageSize());
        List<BackUpInfoModel> models = dataBaseManager.ListBackUpInfo(query);
        if (CollectionUtils.isEmpty(models)) {
            return new PageInfo<>(new ArrayList<>());
        }
        return new PageInfo<>(models);
    }

    /**
     * @param [dto]
     * @return java.lang.Boolean
     * @description 定时自动备份数据库
     **/
    @Override
    public Boolean timingBackUpDataBase(@RequestBody QMSTaskDTO dto) {

        try {
            // 校验该种定时任务是否存在，如果存在，先删除再重新添加
            QMSTaskQuery query = new QMSTaskQuery();
            query.setExcDate(dto.getExcDate());
            query.setEffective(dto.getEffective());
            QMSTaskDTO taskDTO = taskManager.queryTaskExistOrNot(query);
            if (null != taskDTO) {
                taskDTO.setIsDeleted(true);
                taskManager.deleteTask(taskDTO);
            }
            dto.setTaskName("定时备份任务");
            dto.setBizId(idGenerator.nextId());
            taskManager.insert(dto);
            return true;
        } catch (Exception e) {
            log.error("定时任务添加失败", e);
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
        }
    }
}
