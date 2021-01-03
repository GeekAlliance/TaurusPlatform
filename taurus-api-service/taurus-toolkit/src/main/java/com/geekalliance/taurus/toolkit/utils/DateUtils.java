package com.geekalliance.taurus.toolkit.utils;


import com.geekalliance.taurus.toolkit.StringPool;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Slf4j
public class DateUtils implements Serializable {
    private static final long serialVersionUID = 2467154164296626748L;

    private static DateFormat dateFormat;


    /**
     * 日期格式化 年份 例如:2018
     */
    public static final String DATE_FORMAT_YYYY = "yyyy";

    /**
     * 日期格式化 年份和月份 例如：201808
     */
    public static final String DATE_FORMAT_YYYYMM = "yyyyMM";

    /**
     * 日期格式化 年份和月份 例如：2018-08
     */
    public static final String DATE_FORMAT_YYYY_MM = "yyyy-MM";

    /**
     * 日期格式化 年月日 例如：20180808
     */
    public static final String DATE_FORMAT_YYYYMMDD = "yyyyMMdd";

    /**
     * 日期格式化 年月日 例如：2018-08-08
     */
    public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";

    /**
     * 日期格式化 年月日小时分钟 例如：2018-08-08 15:30
     */
    public static final String DATE_FORMAT_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";

    /**
     * 日期格式化 年月日小时分钟秒 例如：2018-08-08 15:30:20
     */
    public static final String DATE_FORMAT_YYYY_MM_DD_HH_SS = "yyyy-MM-dd HH:mm:ss";

    /**
     * 日期格式化 年月日小时分钟秒毫秒 例如：20180808153020234
     */
    public static final String DATE_TIME_FORMAT_YYYYMMDDHHMISSSSS = "yyyyMMddHHmmssSSS";

    /**
     * 导出文件日期格式
     */
    public static final String EXPORT_DATE_FORMAT = "yyyy-MM-dd_HH-mm-ss";


    /**
     * @param date
     * @return
     */
    public static LocalDateTime dateConverterLocalDateTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }

    public static Date localDateTimeConverterDate(LocalDateTime localDateTime) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDateTime.atZone(zoneId);
        return Date.from(zdt.toInstant());
    }

    /**
     * 获取某个日期年份
     *
     * @param date
     */
    public static Integer getYear(Date date) {
        LocalDateTime localDateTime = dateConverterLocalDateTime(date);
        return localDateTime.getYear();
    }

    /**
     * 获取某个日期月份
     *
     * @param date
     */
    public static Integer getMonth(Date date) {
        LocalDateTime localDateTime = dateConverterLocalDateTime(date);
        return localDateTime.getMonthValue();
    }

    /**
     * @param date
     */
    public static Integer getDayOfMonth(Date date) {
        LocalDateTime localDateTime = dateConverterLocalDateTime(date);
        return localDateTime.getDayOfMonth();
    }

    /**
     * 获取某个日期日数
     *
     * @param date
     */
    public static Integer getDayOfYear(Date date) {
        LocalDateTime localDateTime = dateConverterLocalDateTime(date);
        return localDateTime.getDayOfYear();
    }

    /**
     * 获取某天最大时间
     *
     * @param date
     */
    public static Date getEndOfDay(Date date) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
        LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
        return Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 获取当前日期月份的最后一天
     *
     * @param date
     * @return
     */
    public static Date getLastDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, lastDay);
        return calendar.getTime();
    }

    /**
     * 获取日期偏移天数后的日期
     *
     * @param date
     * @param offset
     * @return
     */
    public static Date getOffsetDay(Date date, int offset) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, offset);
        return calendar.getTime();
    }


    /**
     * 格式化Date日期
     *
     * @param date   Date类型时间
     * @param format String类型格式
     * @return 格式化后的字符串
     */
    public static String parseDateToString(Date date, String format) {
        try {
            if (date == null) {
                return null;
            }
            dateFormat = new SimpleDateFormat(format);
            return dateFormat.format(date);
        } catch (Exception ex) {
            log.error("parseDateToString error: " + ex.getMessage() + "转换时间：" + date);
            return StringPool.EMPTY;
        }
    }

    /**
     * 格式化Timestamp时间
     *
     * @param timestamp Timestamp时间类型
     * @param format    String类型格式
     * @return 格式化后的字符串
     */
    public static String parseTimestampToString(Timestamp timestamp, String format) {
        dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(timestamp);
    }

    /**
     * 格式化String时间
     *
     * @param date   string类型日期
     * @param format String类型格式
     * @return 格式化后的Date日期
     */
    public static Date parseStringToDate(String date, String format) {
        if (StringUtils.isBlank(date)) {
            return null;
        }
        try {
            dateFormat = new SimpleDateFormat(format);
            return dateFormat.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    public static List<Date> getBetweenDates(Date start, Date end) {
        List<Date> result = new ArrayList<Date>();
        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(start);
        tempStart.add(Calendar.DAY_OF_YEAR, 1);
        Calendar tempEnd = Calendar.getInstance();
        tempEnd.setTime(end);
        while (tempStart.before(tempEnd)) {
            result.add(tempStart.getTime());
            tempStart.add(Calendar.DAY_OF_YEAR, 1);
        }
        return result;
    }
}
