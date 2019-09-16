package com.medical.dtms.web.controller.database;

import com.github.pagehelper.PageInfo;
import com.medical.dtms.common.constants.Constants;
import com.medical.dtms.common.enumeration.ErrorCodeEnum;
import com.medical.dtms.common.model.datasource.UsageOfTablesModel;
import com.medical.dtms.common.resp.Result;
import com.medical.dtms.feignservice.databaseresource.DataBaseResourceService;
import com.medical.dtms.common.model.table.DataBaseTableModel;
import com.medical.dtms.common.model.table.TableDetailModel;
import com.medical.dtms.common.model.table.query.DataBaseTableQuery;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @version： DataBaseController.java v 1.0, 2019年08月21日 17:30 wuxuelin Exp$
 * @Description 数据库资源控制类
 **/
@RestController
public class DataBaseResourceController {

    @Autowired
    private DataBaseResourceService dataBaseResourceService;

    /**
     * @param [query]
     * @return com.medical.dtms.common.resp.Result<com.medical.dtms.model.table.DataBaseTableModel>
     * @description 数据库资源 - 对象资源管理器 - 列表查询
     **/
    @RequestMapping(value = "/table/listAllTables", method = RequestMethod.POST)
    public Result<DataBaseTableModel> listAllTables(@RequestBody DataBaseTableQuery query) {
        DataBaseTableModel model = dataBaseResourceService.listAllTables(query);
        return Result.buildSuccess(model);
    }

    /**
     * @param [query]
     * @return com.medical.dtms.common.resp.Result<java.util.List < com.medical.dtms.model.table.TableDetailModel>>
     * @description 数据库资源 - 查询表字段具体信息
     **/
    @RequestMapping(value = "/table/listTableInfo", method = RequestMethod.POST)
    public Result<List<TableDetailModel>> listTableInfo(@RequestBody DataBaseTableQuery query) {
        if (null == query || StringUtils.isBlank(query.getTableName())) {
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), "表名为空");
        }
        List<TableDetailModel> models = dataBaseResourceService.listTableInfo(query);
        return Result.buildSuccess(models);
    }

    /**
     * @param [query]
     * @return com.medical.dtms.common.resp.Result<?>
     * @description 数据库资源 - 执行 sql TODO
     **/
    @RequestMapping(value = "/table/executeSql", method = RequestMethod.POST)
    public Result<?> executeSql(@RequestBody DataBaseTableQuery query) {
        //  如果是 增删改，返回值为 true
        if (null != query && Constants.operateType_update_or_delete.equals(query.getOperateType())) {
            dataBaseResourceService.executeSql(query);
            return Result.buildSuccess(true);
        }

        // 如果是 查询，则为对应表数据
        dataBaseResourceService.executeQuerySql(query);
        return Result.buildSuccess();
    }


    /**
     * 数据库备份 - 表空间使用情况
     *
     * @param query
     * @return
     */
    @RequestMapping(value = "/table/showUsageOfTables", method = RequestMethod.POST)
    public Result<PageInfo<UsageOfTablesModel>> showUsageOfTables(@RequestBody DataBaseTableQuery query) {
        PageInfo<UsageOfTablesModel> pageInfo = dataBaseResourceService.showUsageOfTables(query);
        return Result.buildSuccess(pageInfo);
    }
}
