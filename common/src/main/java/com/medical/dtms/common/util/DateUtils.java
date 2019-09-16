/**
 * BEYONDSOFT.COM INC
 */
package com.medical.dtms.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

/**
 * 时间转换工具类
 *
 * @author yulijun
 * @version $Id: DateUtils.java, v 0.1 2017/6/5 18:15 yulijun Exp $$
 */
public class DateUtils {

    public static final String YYYYMMDD = "yyyy-MM-dd";

    public static final String YYYYMMDDHH = "yyyy-MM-dd HH";

    public static final String YYYYMMDDHHMM = "yyyy-MM-dd HH:mm";

    public static final String YYYYMMDD000000 = "yyyy-MM-dd 00:00:00";

    public static final String YYYYMMDDHH0000 = "yyyy-MM-dd HH:00:00";

    public static final String YYYYMMDDHHMM00 = "yyyy-MM-dd HH:mm:00";

    public static final String YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";

    public static final String CHN_YMD = "yyyy年MM月dd日";

    public static final String CHN_YMD_HH = "yyyy-MM-dd HH时";

    public static final String CHN_YMD_HHMM = "yyyy年MM月dd日HH:mm";

    public static final String CHN_MDHHMM = "MM月dd日HH时mm分";

    public static final String CHN_MD = "MM月dd日";

    public static final String HHMM = "HH:mm";

    public static final String YYYYMMD235959 = "yyyy-MM-dd 23:59:59";

    public static final String YYYY_MM_DD_HH_mm_ss = "yyyy/m/dd HH:mm:ss";

    /**
     * date转换成string
     *
     * @param date    Date对象
     * @param pattern 转换格式
     * @return
     */
    public static String format(Date date, String pattern) {
        if (date == null || StringUtils.isBlank(pattern)) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.SIMPLIFIED_CHINESE);
        return sdf.format(date);
    }

    /**
     * Calendar转换成string
     *
     * @param calendar Calendar对象
     * @param pattern  转换格式
     * @return
     */
    public static String format(Calendar calendar, String pattern) {
        if (StringUtils.isBlank(pattern)) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(calendar.getTime());
    }

    /**
     * string转换Date
     *
     * @param datestring 日期格式字符串
     * @param pattern    转换格式
     * @return Date对象
     */
    public static Date parse2Date(String datestring, String pattern) {
        if (StringUtils.isBlank(pattern) || StringUtils.isBlank(datestring)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.parse(datestring);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * string转换Calender
     *
     * @param datestring 日期格式字符串
     * @param pattern    转换格式
     * @return Calendar对象
     */
    public static Calendar parse2Calendar(String datestring, String pattern) {
        if (StringUtils.isBlank(pattern) || StringUtils.isBlank(datestring)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(sdf.parse(datestring));
            return calendar;
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 获取传入时间所属月的首日
     *
     * @param date
     * @return
     */
    public static Date getFirstDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date firstDayOfMonth = calendar.getTime();
        return firstDayOfMonth;
    }

    /**
     * 获取传入时间所属月的末尾日
     *
     * @param date
     * @return
     */
    public static Date getLastDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date lastDayOfMonth = calendar.getTime();
        return lastDayOfMonth;
    }

    /**
     * 往后倒退年份
     *
     * @return
     */
    public static Date getBackYear(Integer num) {
        Calendar calendar = Calendar.getInstance();
        Integer year = calendar.get(Calendar.YEAR);
        Integer month = calendar.get(Calendar.MONTH);
        calendar.set(year - num, month, 1);
        return calendar.getTime();
    }

    /**
     * 往后倒退年份到12月31日
     *
     * @return
     */
    public static Date getBackEndYear(Integer num) {
        Calendar calendar = Calendar.getInstance();
        Integer year = calendar.get(Calendar.YEAR);
        calendar.set(year - num, Calendar.DECEMBER, 31);
        return calendar.getTime();
    }

    /**
     * 往后倒退年份到1月1日
     *
     * @return
     */
    public static Date getBackStartYear(Integer num) {
        Calendar calendar = Calendar.getInstance();
        Integer year = calendar.get(Calendar.YEAR);
        calendar.set(year - num, Calendar.JANUARY, 1);
        return calendar.getTime();
    }

    /**
     * 往前倒退num天
     *
     * @return
     */
    public static Date getBackDay(Integer num) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -num);
        return calendar.getTime();
    }

    /**
     * 将日期时间归至零点
     *
     * @param date
     * @return
     */
    public static Date dateToBeginOfDay(Date date) {
        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            return calendar.getTime();
        }
        return null;
    }

    /**
     * 获得某天最大时间 2017-10-15 23:59:59
     * 
     * @param date
     * @return
     */
    public static Date getEndOfDay(Date date) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()),
            ZoneId.systemDefault());
        ;
        LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
        return Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 获得某天最小时间 2017-10-15 00:00:00
     * 
     * @param date
     * @return
     */
    public static Date getStartOfDay(Date date) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()),
            ZoneId.systemDefault());
        LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
        return Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 秒转成天数
     *
     * @param mm
     * @return
     */
    public static Long convert2Day(Long mm) {
        if (null == mm) {
            return 0L;
        }
        Long day = mm / (60 * 60 * 24);
        return day;
    }

    /**
     * 天数转成秒数
     *
     * @param day
     * @return
     */
    public static Long convert2mill(Long day) {
        if (null == day) {
            return 0L;
        }
        return day * 24 * 60 * 60;
    }

}
