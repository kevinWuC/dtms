/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.medical.dtms.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.compress.utils.Lists;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.medical.dtms.common.config.ExcelField;

/**
 * 
 * @author shenqifeng
 * @version $Id: ExcelImportUtils.java, v 0.1 2019年8月31日 下午3:18:22 shenqifeng Exp $
 */
public class ExcelImportUtils {

    private static Logger           logger = LoggerFactory.getLogger(ExcelImportUtils.class);
    private static SimpleDateFormat sdf    = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static <E> List<E> readFile(File file, Class<E> clzz) {
        return readFile(file, clzz, ExcelField.class, "name");
    }

    /**
    * 从文件读取数据
    * @param file：Excel文件，第一行为列标题
    * @param clzz：映射生成的实体类
    * @param annotationClass：注解类
    * @param methodName：注解类中对列应名的方法
    * @return List
    */
    public static <E, T extends Annotation> List<E> readFile(File file, Class<E> clzz,
                                                             Class<T> annotationClass,
                                                             String methodName) {
        Workbook wb = null;
        try {
            if (file == null || !validateExcel(file.getName())) {
                logger.error("文件为空或者不是Excel类型的文件");
                return Lists.newArrayList();
            }
            InputStream is;
            is = new FileInputStream(file);
            //创建工作表
            if (isExcel2003(file.getName())) {
                wb = new HSSFWorkbook(is);
            } else {
                wb = new XSSFWorkbook(is);
            }
            return readExcel(clzz, wb, annotationClass, methodName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return Lists.newArrayList();
        } catch (IOException e) {
            e.printStackTrace();
            return Lists.newArrayList();
        } finally {
            if (wb != null) {
                try {
                    wb.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 读取Excel内容，默认第一行为标题行
     * @param wb
     * @param file
     * @param map
     * @return
     */
    private static <E, T extends Annotation> List<E> readExcel(Class<E> clzz, Workbook wb,
                                                               Class<T> annotationClass,
                                                               String methodName) {
        //获取实体类的所有属性
        Field[] fields = clzz.getDeclaredFields();
        Map<String, String> map = getFieldMap(fields, annotationClass, methodName);
        Sheet sheet = wb.getSheetAt(0);
        Row title = sheet.getRow(0);
        int totalColumns = title.getPhysicalNumberOfCells();
        int totalRows = sheet.getPhysicalNumberOfRows();
        //获取Excel所有的列标题
        String[] titles = getColumnTitle(title, totalColumns);
        List<E> list = new ArrayList<>();
        Cell cell;
        Row row;
        E e;
        //从第二行开始读取数据
        for (int i = 1; i < totalRows; i++) {
            row = sheet.getRow(i);
            e = getNewInstance(clzz);
            for (int j = 0; j < totalColumns; j++) {
                cell = row.getCell(j);
                readCellContent(map.get(titles[j]), fields, cell, e);
            }
            list.add(e);
        }
        return list;

    }

    /**
     * 读取单元格内容，并将内容添加到实体类E中
     * @param fieldName 当前单元格对应的Bean字段
     * @param fields 属性数组
     * @param cell 单元格
     * @param e 实体类
     */
    private static <E> void readCellContent(String fieldName, Field[] fields, Cell cell, E e) {
        Object obj = getCellValue(cell);
        if (obj == null) {
            return;
        }
        mappingValueToBean(fieldName, fields, obj, e);
    }

    /**
     * 映射值到实体类
     * @param fieldName
     * @param fields
     * @param obj
     * @param e
     */
    private static <E> void mappingValueToBean(String fieldName, Field[] fields, Object obj, E e) {
        try {
            for (Field field : fields) {
                if (!fieldName.equals(field.getName())) {
                    continue;
                }
                //设置私有属性可以访问
                field.setAccessible(true);
                field.set(e, getValue(field, obj));
                break;
            }
        } catch (IllegalArgumentException e1) {
            e1.printStackTrace();
        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * 将obj的值转化为该属性类型的值
     * @param field
     * @param obj
     * @return
     */
    private static Object getValue(Field field, Object obj) {
        if (field.getType().equals(obj.getClass())) {
            return obj;
        }
        Object obj2 = null;
        try {
            if (Date.class.equals(field.getType())) {
                obj2 = sdf.parse(obj.toString());
            } else if (String.class.equals(field.getType())) {
                obj2 = String.valueOf(obj);
            } else if (Long.class.equals(field.getType())) {
                obj2 = Long.valueOf(obj.toString());
            } else if (Integer.class.equals(field.getType())) {
                obj2 = Integer.valueOf(obj.toString());
            } else if (BigDecimal.class.equals(field.getType())) {
                obj2 = new BigDecimal(obj.toString());
            } else if (Boolean.class.equals(field.getType())) {
                obj2 = Boolean.valueOf(obj.toString());
            } else if (Float.class.equals(field.getType())) {
                obj2 = Float.valueOf(obj.toString());
            } else if (Double.class.equals(field.getType())) {
                obj2 = Double.valueOf(obj.toString());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return obj2;
    }

    /**
     * 获取单元格的值
     * @param cell
     * @return Object
     */
    private static Object getCellValue(Cell cell) {
        Object obj;
        // 以下是判断数据的类型
        switch (cell.getCellType()) {
            case NUMERIC: // 数字
                obj = cell.getNumericCellValue();
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    obj = HSSFDateUtil.getJavaDate((double) obj);
                }
                break;
            case STRING: // 字符串
                obj = cell.getStringCellValue();
                break;
            case BOOLEAN: // Boolean
                obj = cell.getBooleanCellValue();
                break;
            case FORMULA: // 公式
                obj = cell.getCellFormula();
                break;
            case BLANK: // 空值
                obj = null;
                break;
            case ERROR: // 故障
                obj = "非法字符";
                break;
            default:
                obj = "未知类型";
                break;
        }
        return obj;
    }

    /**
     * 通过反射获取T类的新实例
     * @param clzz
     * @return T
     */
    private static <T> T getNewInstance(Class<T> clzz) {
        T t = null;
        try {
            t = clzz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * 获取列标题
     * @param title 列标题所在行
     * @param totalColumns 总列数
     * @return String[]
     */
    private static String[] getColumnTitle(Row title, int totalColumns) {
        String[] titles = new String[totalColumns];
        for (int j = 0; j < totalColumns; j++) {
            titles[j] = title.getCell(j).getStringCellValue();
        }
        return titles;
    }

    /**
     * 获取属性和注解对应的集合
     * @param clzz
     * @param annotationClass
     * @param methodName
     * @return Map : key为属性上的注解值，value为属性名
     */
    private static <T extends Annotation> Map<String, String> getFieldMap(Field[] fields,
                                                                          Class<T> annotationClass,
                                                                          String methodName) {
        Map<String, String> map = new HashMap<>();
        T t;
        for (Field field : fields) {
            //获取属性上T类型的注解
            if (field.isAnnotationPresent(annotationClass)) {
                t = field.getAnnotation(annotationClass);
                map.put(String.valueOf(getMethodReturnValue(t, methodName)), field.getName());
            }
        }
        return map;
    }

    /**
     * 获取方法的返回值
     * @param T 实体类 
     * @param methodName 方法名
     * @return Object
     */
    private static <T> Object getMethodReturnValue(T t, String methodName) {
        Object obj = null;
        try {
            obj = t.getClass().getMethod(methodName).invoke(t);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return obj;
    }

    /**
     * 验证是否是Excel格式的文件
     * @param fileName：文件名
     * @return boolean : true表示是Excel格式的文件，false表示不是
     */
    private static boolean validateExcel(String fileName) {
        if (fileName == null || !(isExcel2003(fileName) || isExcel2007(fileName))) {
            return false;
        }
        return true;
    }

    /**
     * 判断是不是2003格式的Excel
     * @param fileName
     * @return boolean : true表示是2003格式的Excel，false表示不是
     */
    private static boolean isExcel2003(String fileName) {
        return fileName.matches("^.+\\.(?i)(xls)$");
    }

    /**
     * 判断是不是2007格式的Excel
     * @param fileName
     * @return boolean : true表示是2007格式的Excel，false表示不是
     */
    private static boolean isExcel2007(String fileName) {
        return fileName.matches("^.+\\.(?i)(xlsx)$");
    }

}
