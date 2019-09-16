//package com.medical.dtms.service.config;
//
//import com.alibaba.druid.pool.DruidDataSource;
//import com.medical.dtms.common.enumeration.DBType;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.core.env.Environment;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//
//import javax.sql.DataSource;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * @author : hejinsheng
// * @date Date : 2018年12月03日 11:52
// * @Description: 多数据源配置
// */
//@Configuration
//@MapperScan("com.medical.dtms.service.mapper")
//public class DataSourceConfiguration {
//
//    /**
//     * 默认数据源
//     */
//    @Value("${datasource.default.url}")
//    private String defaultUrl;
//    @Value("${datasource.default.username}")
//    private String defaultUserName;
//    @Value("${datasource.default.password}")
//    private String defaultPassword;
//
//    /**
//     * 数据源2
//     */
//    @Value("${datasource.datasource2.url}")
//    private String datasource2Url;
//    @Value("${datasource.datasource2.username}")
//    private String datasource2userName;
//    @Value("${datasource.datasource2.password}")
//    private String datasource2Password;
//
//    /**
//     * 初始连接数
//     */
//    private Integer initSize = 1;
//    /**
//     * 最小空闲连接数
//     */
//    private Integer minIdle = 10;
//    /**
//     * 最大活跃连接数
//     */
//    private Integer maxActive = 100;
//    /**
//     * 超时时间
//     */
//    private Long maxWait = 60000L;
//
//    @Autowired
//    private Environment environment;
//
//    /**
//     * 初始化默认数据库 读写
//     * @return
//     */
//    @Bean(name = "defaultDataSource")
//    public DataSource writeDataSource(){
//        return createDruidDataSource(defaultUrl,defaultUserName,defaultUrl,initSize,minIdle,maxActive);
//    }
//
//    /**
//     * 只读数据库
//     * @return
//     */
//    @Bean(name = "readDataSource")
//    public DataSource readDataSource(){
//        return createDruidDataSource(datasource2Url,datasource2userName,datasource2Password,initSize,minIdle,maxActive);
//    }
//
//    /**
//     * @param [url, userName, pass, initSize, minIdle, maxActive]
//     * @return com.alibaba.druid.pool.DruidDataSource
//     * @description 创建 DruidDataSource 数据库连接池
//     **/
//    private DruidDataSource createDruidDataSource(String url, String userName, String pass, Integer initSize, Integer minIdle, Integer maxActive) {
//        DruidDataSource dataSource = new DruidDataSource();
//        dataSource.setUrl(url);
//        dataSource.setUsername(userName);
//        dataSource.setPassword(pass);
//        dataSource.setMaxActive(maxActive);
//        dataSource.setMinIdle(minIdle);
//        dataSource.setInitialSize(initSize);
//        dataSource.setMaxWait(maxWait);
//
//        dataSource.setTestOnBorrow(false);
//        dataSource.setTestOnReturn(false);
//        dataSource.setTestWhileIdle(true);
//        dataSource.setRemoveAbandoned(true);
//
//        List<String> sqls = new ArrayList<>();
//        sqls.add("set names utf8mb4");
//        dataSource.setConnectionInitSqls(sqls);
//        return dataSource;
//    }
//
//    /**
//     * 数据源
//     * @param defaultDataSource
//     * @param readDataSource
//     * @return
//     */
//    @Bean("dataSource")
//    @Primary
//    public DataSource dataSource(@Qualifier("defaultDataSource") DataSource defaultDataSource, @Qualifier("readDataSource") DataSource readDataSource){
//        DynamicDataSource dynamicDataSource = new DynamicDataSource();
//        Map<Object,Object> map = new HashMap<>();
//        map.put(DBType.DATABASE_WRITE,defaultDataSource);
//        map.put(DBType.DATABASE_READ,readDataSource);
//        dynamicDataSource.setTargetDataSources(map);   //数据源池
//        // 默认的数据库
//        dynamicDataSource.setDefaultTargetDataSource(defaultDataSource);
//        dynamicDataSource.afterPropertiesSet();
//        return dynamicDataSource;
//    }
//
//    @Bean
//    public SqlSessionFactoryBean sqlSessionFactory(@Qualifier("defaultDataSource") DataSource defaultDataSource, @Qualifier("readDataSource") DataSource readDataSource) throws Exception {
//        SqlSessionFactoryBean fb = new SqlSessionFactoryBean();
//        fb.setDataSource(this.dataSource(defaultDataSource, readDataSource));
//        //实体类所在目录
//        fb.setTypeAliasesPackage(environment.getProperty("mybatis.type-aliases-package"));
//        //mapping所在目录
//        fb.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(environment.getProperty("mybatis.mapper-locations")));
//        return fb;
//    }
//
//}
