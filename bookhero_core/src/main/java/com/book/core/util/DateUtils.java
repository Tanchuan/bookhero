package com.book.core.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 日期常用工具包
 * @author bjtanchuan
 * @since 2017/07/15
 */
public final class DateUtils {

    /**
     * 对日期进行格式化
     * @param date
     * @param split
     * @return
     */
    public static Date formatDateBySpec(String date, String split) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy" + split + "MM" + split + "dd");
            return sdf.parse(date);
        }
        catch (Exception ex) {
            return null;
        }
    }

    /**
     * 求两日期之间的毫秒差
     * @param begin
     * @param end
     * @return
     */
    public static double secondsBetweenDate(Date begin, Date end) {
        return Math.abs((begin.getTime() - end.getTime()) / 1000D);
    }

    /**
     * 根据偏移值和得到与当前相关的日期
     * @param offset
     * @param sep
     * @return
     */
    public static String getDate(int offset, char sep) {
        String yesterday = null;
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
        cal.add(Calendar.DAY_OF_MONTH, offset);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int date = cal.get(Calendar.DATE);
        String m = String.valueOf(month);
        String d = String.valueOf(date);
        if (month < 10) {
            m = "0" + m;
        }
        if (date < 10) {
            d = "0" + d;
        }
        yesterday = String.valueOf(year) + sep + m + sep + d;
        return yesterday;
    }

    /**
     * 返回传入日期相对偏移量的日期
     * @param start	传入日期
     * @param format	传入日期格式
     * @param offset	偏移量
     * @param seq	返回日期分隔符
     * @return
     */
    public static String getDate(String start, String format, int offset, char seq) {
        String retDay = null;
        Calendar cal = Calendar.getInstance();
        Date dateStart = null;
        DateFormat df = new SimpleDateFormat(format);
        try {
            dateStart = df.parse(start);
        }
        catch (Exception ex) {
            return start;
        }

        cal.setTime(dateStart);
        cal.add(Calendar.DAY_OF_MONTH, offset);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int date = cal.get(Calendar.DATE);
        String m = String.valueOf(month);
        String d = String.valueOf(date);
        if (month < 10) {
            m = "0" + m;
        }
        if (date < 10) {
            d = "0" + d;
        }
        retDay = String.valueOf(year) + seq + m + seq + d;
        return retDay;
    }

    public static Calendar getCalendar(int year, int month, int day, int hour, int minute, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        return calendar;
    }

    /**
     * 返回该天的零点时刻
     * @param date
     * @return
     */
    public static Date getDateStart(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date getDateStart(Date date, int type) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        switch (type) {
            case Calendar.YEAR:
                calendar.set(Calendar.MONTH, 0);
            case Calendar.MONTH:
                calendar.set(Calendar.DAY_OF_MONTH, 0);
            case Calendar.DAY_OF_MONTH:
                calendar.set(Calendar.HOUR_OF_DAY, 0);
            case Calendar.HOUR:
            case Calendar.HOUR_OF_DAY:
                calendar.set(Calendar.MINUTE, 0);
            case Calendar.MINUTE:
                calendar.set(Calendar.SECOND, 0);
            case Calendar.SECOND:
                calendar.set(Calendar.MILLISECOND, 0);
            case Calendar.WEEK_OF_MONTH:
//                英文从周六开始，TODO
                calendar.set(Calendar.DAY_OF_WEEK, 0);
            default:

        }
        return calendar.getTime();
    }

    public static int weeksNumFromNow(Date date) {
        return getWeeksNum(date) - getWeeksNum(new Date());
    }
    /**
     * 
     *  判断是否是同一天
     *
     *   @param today
     *   @param orderDay
     *   @return
     *
     */
    public static boolean equalDay(Date today, Date orderDay) {
        if (null == today || null == orderDay) {
            return false;
        }
        else {
            return getDiffDays(getDateStart(today), getDateStart(orderDay)) == 0;
        }
    }

    public static boolean equalWeek(Date date, Date otherDate) {
        return getWeeksNum(date) == getWeeksNum(otherDate);
    }

    public static int getWeeksNum (Date date) {
        int weekMSecs = 7 * 24 * 3600 * 1000;
        return (int)Math.floor(date.getTime()/ weekMSecs);
    }
    public static int getDiffDays(Date begin, Date end) {
        return (int)(getDiffMinutes(begin, end) / 1440L);
    }

    public static long getDiffMinutes(Date begin, Date end) {
        return getDiffMsecs(begin, end) / 60000L;
    }

    public static long getDiffMsecs(Date begin, Date end) {
        return end.getTime() - begin.getTime();
    }

    /**
     * 获取星期几描述
     * @param date
     * @return	周几，如：周一
     */
    public static String getWeekDesc(Date date) {
        String weekDesc = "";
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int weekday = cal.get(Calendar.DAY_OF_WEEK);
        switch (weekday) {
            case Calendar.SUNDAY:
                weekDesc = "周日";
                break;
            case Calendar.MONDAY:
                weekDesc = "周一";
                break;
            case Calendar.TUESDAY:
                weekDesc = "周二";
                break;
            case Calendar.WEDNESDAY:
                weekDesc = "周三";
                break;
            case Calendar.THURSDAY:
                weekDesc = "周四";
                break;
            case Calendar.FRIDAY:
                weekDesc = "周五";
                break;
            case Calendar.SATURDAY:
                weekDesc = "周六";
                break;
        }

        return weekDesc;
    }

    /**
     * 加、减分钟。
     * 
     * @param date
     *         基准日期。
     * @param minutes
     *         如果>0，则增加分钟数；否则，会减分钟数。
     * @return
     *         计算后的日期。
     */
    public static Date addMinutes(Date date, int minutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, minutes);
        return cal.getTime();
    }


    public static Date addDays(Date date, int addedDays) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, addedDays);
        return cal.getTime();
    }

    /**
     * 为yyyyMMdd格式日期添加分隔符
     */
    public static String addSplit(String strDate, String split) {
        String result = null;
        try {
            Date date = new SimpleDateFormat("yyyyMMdd").parse(strDate);
            String format = "yyyy" + split + "MM" + split + "dd";
            result = new SimpleDateFormat(format).format(date);
        }
        catch (Exception ex) {
            return null;
        }
        return result;
    }

    public static int getWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }


    public static Date getDate(String date, String format) throws ParseException {
        SimpleDateFormat sorceFmt = new SimpleDateFormat(format);
        return new Date(sorceFmt.parse(date).getTime());
    }

    public static Timestamp getTimestamp(String time, String format) throws ParseException {
        SimpleDateFormat sorceFmt = new SimpleDateFormat(format);
        return new Timestamp(sorceFmt.parse(time).getTime());
    }


    public static Date formatToDate(String date, String format) {
        try {
            if(date != null && !"".equalsIgnoreCase(date)) {
                SimpleDateFormat e = new SimpleDateFormat(format);
                return new Date(e.parse(date).getTime());
            } else {
                return null;
            }
        } catch (ParseException e) {
            return null;
        }
    }

    public static Timestamp formatToTimestamp(String dateStr, String format) {
        try {
            SimpleDateFormat e = new SimpleDateFormat(format);
            return new Timestamp(e.parse(dateStr).getTime());
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 周日当做一周的最后一天
     * */
    public static Date getLastDayOfWeekCn(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        calendar.add(Calendar.DATE, 1);
        return calendar.getTime();
    }

}
