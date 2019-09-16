//package com.medical.dtms.service.config;
//
//import com.medical.dtms.common.enumeration.DBType;
//
///**
// * @author : hejinsheng
// * @date Date : 2018年12月03日 15:45
// * @Description: 数据源帮助类
// */
//public class DynamicDataSourceHolder {
//
//    private static ThreadLocal<DBType> holder = new ThreadLocal<>();
//
//    /**
//     * 设置当前线程使用的数据源
//     */
//    public static void markDataSource(DBType type){
//        holder.set(type);
//    }
//
//    /**
//     * 获取数据源
//     */
//    public static DBType getDataSource(){
//        return holder.get();
//    }
//}
