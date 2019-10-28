package com.medical.dtms.web.controller.database;

import com.github.pagehelper.PageInfo;
import com.medical.dtms.common.constants.Constants;
import com.medical.dtms.common.enumeration.ErrorCodeEnum;
import com.medical.dtms.common.login.OperatorInfo;
import com.medical.dtms.common.login.SessionTools;
import com.medical.dtms.common.model.datasource.BackUpInfoModel;
import com.medical.dtms.common.model.datasource.UsageOfTablesModel;
import com.medical.dtms.common.resp.Result;
import com.medical.dtms.common.util.Paginator;
import com.medical.dtms.dto.datasource.QMSBackUpDTO;
import com.medical.dtms.dto.datasource.QMSTaskDTO;
import com.medical.dtms.dto.datasource.query.QMSBackUpQuery;
import com.medical.dtms.feignservice.databaseresource.DataBaseResourceService;
import com.medical.dtms.common.model.table.DataBaseTableModel;
import com.medical.dtms.common.model.table.TableDetailModel;
import com.medical.dtms.common.model.table.query.DataBaseTableQuery;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
     * @description 数据库资源 - 执行 sql
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
    public Result<Paginator<UsageOfTablesModel>> showUsageOfTables(@RequestBody DataBaseTableQuery query) {
        checkQueryParams(query);
        Paginator<UsageOfTablesModel> pageInfo = dataBaseResourceService.showUsageOfTables(query);
        return Result.buildSuccess(pageInfo);
    }

    /**
     * @param [request]
     * @return com.medical.dtms.common.resp.Result<java.lang.String>
     * @description 数据库备份 - 备份
     **/
    @RequestMapping(value = "/table/exportSql", method = RequestMethod.POST)
    public Result<Boolean> exportSql(HttpServletRequest request) {
        OperatorInfo info = SessionTools.getOperator();

        QMSBackUpDTO dto = new QMSBackUpDTO();
        dto.setCreator(info.getDspName());
        dto.setCreatorId(info.getUserId());

        String urlPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        dto.setUrlPath(urlPath);

        Boolean b = dataBaseResourceService.exportSql(dto);
        return Result.buildSuccess(b);
    }

    /**
     * @param [dto]
     * @return com.medical.dtms.common.resp.Result<java.lang.Boolean>
     * @description 删除备份记录
     **/
    @RequestMapping(value = "/table/deleteSqlBackUp", method = RequestMethod.POST)
    public Result<Boolean> deleteSqlBackUp(@RequestBody QMSBackUpDTO dto) {
        if (null == dto || null == dto.getBizId()) {
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), ErrorCodeEnum.PARAM_IS_EMPTY.getErrorMessage());
        }
        OperatorInfo info = SessionTools.getOperator();
        dto.setModifier(info.getDspName());
        dto.setModifierId(info.getUserId());
        dataBaseResourceService.deleteSqlBackUp(dto);
        return Result.buildSuccess(true);
    }

    /**
     * @param [query]
     * @return com.medical.dtms.common.resp.Result<com.github.pagehelper.PageInfo < com.medical.dtms.common.model.datasource.BackUpInfoModel>>
     * @description 分页查询 备份数据
     **/
    @RequestMapping(value = "/table/pageListBackUpInfo", method = RequestMethod.POST)
    public Result<PageInfo<BackUpInfoModel>> pageListBackUpInfo(@RequestBody QMSBackUpQuery query) {
        checkParams(query);
        PageInfo<BackUpInfoModel> pageInfo = dataBaseResourceService.pageListBackUpInfo(query);
        return Result.buildSuccess(pageInfo);
    }

    /**
     * @param [dto]
     * @return com.medical.dtms.common.resp.Result<java.lang.Boolean>
     * @description 定时自动备份数据库
     **/
    @RequestMapping(value = "/table/timingBackUpDataBase", method = RequestMethod.POST)
    public Result<Boolean> timingBackUpDataBase(@RequestBody QMSTaskDTO dto, HttpServletRequest request) {
        if (null == dto || null == dto.getExcDate() || null == dto.getEffective()) {
            return Result.buildFailed(ErrorCodeEnum.PARAM_IS_EMPTY.getErrorCode(), ErrorCodeEnum.PARAM_IS_EMPTY.getErrorMessage());
        }
        OperatorInfo info = SessionTools.getOperator();

        dto.setCreator(info.getDspName());
        dto.setCreatorId(info.getUserId());

        String urlPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        dto.setUrlPath(urlPath);

        dataBaseResourceService.timingBackUpDataBase(dto);
        return Result.buildSuccess(true);
    }

    /**
     * 分页参数校验
     *
     * @param query
     */
    private void checkParams(QMSBackUpQuery query) {
        if (null == query) {
            query = new QMSBackUpQuery();
        }
        if (null == query.getPageNo()) {
            query.setPageNo(1);
        }
        if (null == query.getPageSize()) {
            query.setPageSize(10);
        }
    }

    /**
     * 分页参数校验
     *
     * @param query
     */
    private void checkQueryParams(DataBaseTableQuery query) {
        if (null == query) {
            query = new DataBaseTableQuery();
        }
        if (null == query.getPageNo()) {
            query.setPageNo(1);
        }
        if (null == query.getPageSize()) {
            query.setPageSize(10);
        }
    }
}
