package com.medical.dtms.service.manager.table;

import com.medical.dtms.common.constants.Constants;
import com.medical.dtms.common.eception.BizException;
import com.medical.dtms.common.enumeration.ErrorCodeEnum;
import com.medical.dtms.common.model.datasource.BackUpInfoModel;
import com.medical.dtms.common.model.datasource.UsageOfTablesModel;
import com.medical.dtms.common.model.table.TableDetailModel;
import com.medical.dtms.dto.datasource.QMSBackUpDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.persistence.Table;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version： OperateManager.java v 1.0, 2019年08月19日 12:26 wuxuelin Exp$
 * @Description
 **/
@Service
@Slf4j
public class OperateManager {

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String userName;
    @Value("${spring.datasource.password}")
    private String password;

    @Value("${sqlbackup.path}")
    private String sqlPath;
    @Value("${sqlbackup.staticPath}")
    private String sqlStaticPath;

    /**
     * 获取实体类对应的表名，由于实体类命名与表名未完全对应，所以 实体类上需要加上 @Table 注解
     */
    public static String getTableName(Class<?> object) {
        return object.getAnnotation(Table.class).name();
    }

    /**
     * 获取数据库连接
     */
    public Connection getConnection() {
        Connection connection;
        try {
            connection = DriverManager.getConnection(url, userName, password);
        } catch (SQLException e) {
            log.error("获取数据库连接失败", e);
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
        }
        return connection;
    }

    /**
     * 获取表备注名
     */
    public String getTableComment(Class<?> object) {
        // 获取数据库名
        String dataBaseName = getDataBaseName();
        // 获取表名
        String tableName = getTableName(object);

        String tableComment = null;
        Connection connection = getConnection();
        try {
            Statement smt = connection.createStatement();
            ResultSet rs = smt.executeQuery("SELECT TABLE_NAME,TABLE_COMMENT FROM information_schema.TABLES WHERE table_schema='dtms'");
            while (rs.next()) {
                String name = rs.getString("TABLE_NAME");
                System.out.println(name);
                if (StringUtils.isNotBlank(name) && StringUtils.equals(tableName, name)) {
                    tableComment = rs.getString("TABLE_COMMENT");
                    System.out.println(tableComment);
                }
            }
        } catch (SQLException e) {
            log.error("获取表备注名", e);
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
        }
        return tableComment;
    }

    /**
     * 获取数据库名
     */
    public String getDataBaseName() {
        return url.substring(url.indexOf("3306"), url.indexOf("?")).replaceAll("/", "").replaceAll("3306", "");
    }

    /**
     * 获取数据库 表列表
     */
    public List<String> listTableNames(String dataBaseName) {
        Connection connection = getConnection();
        List<String> tableNameList = new ArrayList<>();
        try {
            Statement smt = connection.createStatement();
            ResultSet rs = smt.executeQuery("SELECT TABLE_NAME FROM information_schema.TABLES WHERE table_schema='dtms'");
            while (rs.next()) {
                String table = rs.getString("table_name");
                tableNameList.add(table);
            }
        } catch (SQLException e) {
            log.error("查询失败", e);
        }
        return tableNameList;
    }

    /**
     * 获取表字段信息
     */
    public List<TableDetailModel> listTableInfo(String tableName) {
        List<TableDetailModel> list = new ArrayList<>();
        Connection connection = getConnection();
        try {
            Statement smt = connection.createStatement();
            ResultSet rs = smt.executeQuery("select * from" + "\t" + tableName);
            ResultSetMetaData data = rs.getMetaData();

            TableDetailModel model;
            for (int i = 1; i <= data.getColumnCount(); i++) {
                model = new TableDetailModel();
                // 获取列名
                String columnName = data.getColumnName(i);
                model.setColumnName(columnName);

                // 获取字段类型
                String typeName = data.getColumnTypeName(i);
                model.setColumnType(typeName.toLowerCase());

                // 获取字段长度
                int size = data.getColumnDisplaySize(i);
                model.setColumnLength(size);

                // 是否自增
                boolean autoIncrement = data.isAutoIncrement(i);
                if (autoIncrement) {
                    model.setPrimaryKey(true);
                } else {
                    model.setPrimaryKey(false);
                }

                // 是否允许空
                int dataNullable = data.isNullable(i);
                if (Constants.columnNoNulls.equals(dataNullable) || Constants.columnNullableUnknown.equals(dataNullable)) {
                    model.setAllowEmpty(false);
                }
                if (Constants.columnNullable.equals(dataNullable)) {
                    model.setAllowEmpty(true);
                }

                // 获取默认值
                ResultSet set = smt.executeQuery("select default(" + columnName + ") AS def from" + "\t" + tableName + "\t");
                while (set.next()) {
                    String def = set.getString("def");
                    model.setDefaultValue(def);
                    break;
                }

                list.add(model);
            }

            // 处理说明/备注
            ResultSet resultSet = smt.executeQuery("show full columns from" + "\t" + tableName);
            while (resultSet.next()) {
                String field = resultSet.getString("Field");
                String comment = resultSet.getString("Comment");

                for (TableDetailModel detailModel : list) {
                    if (StringUtils.equals(detailModel.getColumnName(), field)) {
                        detailModel.setRemark(comment);
                    }
                }
            }
        } catch (SQLException e) {
            log.error("表字段信息查询失败", e);
            return new ArrayList<>();
        }
        return list;
    }

    /**
     * 执行 sql
     */
    public void executeQuerySql(String tableName) {
        Connection connection = getConnection();
        try {
            Statement smt = connection.createStatement();
            ResultSet rs = smt.executeQuery("select * from" + "\t" + tableName);
            ResultSetMetaData data = rs.getMetaData();
            int count = data.getColumnCount();
            for (int i = 1; i < count; i++) {
                Object o = rs.getObject(i);

                System.out.println("obj:" + o);

            }
            while (rs.next()) {
                String field = rs.getString("Field");

            }

        } catch (SQLException e) {
            log.error("表数据查询失败", e);
        }
    }

    /**
     * 数据库备份 - 表空间使用情况
     */
    public List<UsageOfTablesModel> showUsageOfTables() {
        List<UsageOfTablesModel> list = new ArrayList<>();
        try {
            Connection connection = getConnection();
            UsageOfTablesModel model;
            Statement smt = connection.createStatement();
            ResultSet rs = smt.executeQuery("SELECT TABLE_NAME,TABLE_ROWS,TABLE_COMMENT,concat(truncate(data_length/1024/1024,2),' MB') as DATA_SIZE,concat(truncate(index_length/1024/1024,2),' MB') as INDEX_SIZE FROM information_schema.TABLES WHERE table_schema='dtms'");
            while (rs.next()) {
                model = new UsageOfTablesModel();
                // 表名
                String name = rs.getString("TABLE_NAME");
                model.setTableName(name);
                // 表总记录数
                Integer tableRows = rs.getInt("TABLE_ROWS");
                model.setCountNo(tableRows);
                // 表说明
                String tableContent = rs.getString("TABLE_COMMENT");
                model.setTableContent(tableContent);
                // 索引所占大小
                String indexSize = rs.getString("INDEX_SIZE");
                model.setIndexUsageSum(indexSize);
                // 表所占大小
                String dataSize = rs.getString("DATA_SIZE");
                model.setUsageSum(dataSize);
                list.add(model);
            }
        } catch (SQLException e) {
            log.error("sql 语法错误");
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
        } catch (Exception e) {
            log.error("查询表空间使用情况失败", e);
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
        }
        return list;
    }

    /**
     * 导出 sql 并返回相关信息
     */
    public QMSBackUpDTO exportSql(QMSBackUpDTO dto) {
        // 指定导出的 sql 存放的文件夹
        File saveFile = new File(sqlPath);
        if (!saveFile.exists()) {
            saveFile.mkdirs();
        }

        StringBuilder sb = new StringBuilder();
        String host = getHost();
        String dataBaseName = getDataBaseName();
        String fileName = System.currentTimeMillis() + "_" + "dtms.sql";

        sb.append("mysqldump").append(" --opt ").append(" -h ").append(host).append(" --user=").append(userName).append(" --password=").append(password).append(" --databases ").append(dataBaseName);
        sb.append(" --result-file=").append(sqlPath + fileName).append(" --default-character-set=utf8 ");
        String sqlUrl = "";
        try {
            Process exec = Runtime.getRuntime().exec(sb.toString());
            if (exec.waitFor() == 0) {
                log.error("数据库备份成功，保存路径：" + sqlPath);
                sqlUrl = dto.getUrlPath() + sqlStaticPath.replaceAll("\\*", "") + fileName;

                dto.setBackUpDate(new Date());
                dto.setSqlFileName(fileName);
                dto.setSqlUrl(sqlUrl);

                File sqlFile = new File(sqlPath, fileName);
                Double size = Double.valueOf(sqlFile.length() / 1024 / 1024);
                dto.setSqlFileSize(String.format("%.2f", size) + "MB");
            } else {
                System.out.println("process.waitFor()=" + exec.waitFor());
            }
        } catch (IOException e) {
            log.error("备份 数据库 出现 IO异常 ", e);
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
        } catch (InterruptedException e) {
            log.error("备份 数据库 出现 线程中断异常 ", e);
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
        } catch (Exception e) {
            log.error("备份 数据库 出现 其他异常 ", e);
            throw new BizException(ErrorCodeEnum.FAILED.getErrorCode(), ErrorCodeEnum.FAILED.getErrorMessage());
        }
        return dto;
    }

    /**
     * 获取主机地址
     */
    private String getHost() {
        return url.substring(url.indexOf("mysql"), url.indexOf("3306")).replace(":", "").replace("//", "").replace("mysql", "");
    }


}
