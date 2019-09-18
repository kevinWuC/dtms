package com.medical.dtms.feignservice.databaseresource;

import com.github.pagehelper.PageInfo;
import com.medical.dtms.common.model.datasource.BackUpInfoModel;
import com.medical.dtms.common.model.datasource.UsageOfTablesModel;
import com.medical.dtms.common.model.table.DataBaseTableModel;
import com.medical.dtms.common.model.table.TableDetailModel;
import com.medical.dtms.common.model.table.query.DataBaseTableQuery;
import com.medical.dtms.dto.datasource.QMSBackUpDTO;
import com.medical.dtms.dto.datasource.QMSTaskDTO;
import com.medical.dtms.dto.datasource.query.QMSBackUpQuery;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @version： DataBaseService.java v 1.0, 2019年08月21日 17:44 wuxuelin Exp$
 * @Description
 **/
@FeignClient("service-dtms")
public interface DataBaseResourceService {

    @RequestMapping(value = "/table/listAllTables", method = RequestMethod.POST)
    DataBaseTableModel listAllTables(@RequestBody DataBaseTableQuery query);

    @RequestMapping(value = "/table/listTableInfo", method = RequestMethod.POST)
    List<TableDetailModel> listTableInfo(@RequestBody DataBaseTableQuery query);

    @RequestMapping(value = "/table/executeSql", method = RequestMethod.POST)
    Boolean executeSql(@RequestBody DataBaseTableQuery query);

    @RequestMapping(value = "/table/executeQuerySql", method = RequestMethod.POST)
    Class<?> executeQuerySql(@RequestBody DataBaseTableQuery query);

    @RequestMapping(value = "/table/showUsageOfTables", method = RequestMethod.POST)
    PageInfo<UsageOfTablesModel> showUsageOfTables(@RequestBody DataBaseTableQuery query);

    @RequestMapping(value = "/table/exportSql", method = RequestMethod.POST)
    Boolean exportSql(@RequestBody QMSBackUpDTO dto);

    @RequestMapping(value = "/table/deleteSqlBackUp", method = RequestMethod.POST)
    Boolean deleteSqlBackUp(@RequestBody QMSBackUpDTO dto);

    @RequestMapping(value = "/table/pageListBackUpInfo", method = RequestMethod.POST)
    PageInfo<BackUpInfoModel> pageListBackUpInfo(@RequestBody QMSBackUpQuery query);

    @RequestMapping(value = "/table/timingBackUpDataBase", method = RequestMethod.POST)
    Boolean timingBackUpDataBase(@RequestBody QMSTaskDTO dto);
}
