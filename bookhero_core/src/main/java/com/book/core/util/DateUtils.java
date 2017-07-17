package com.book.core.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
     * 
     * @author bjpengpeng
     * @date 2015年10月23日
     * @description 获取给光大发送扣款时间的上限
     * @param st
     * @return
     */
    public static Timestamp getGDDebitUpperTime(Timestamp st) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(st);
        int weekday = cal.get(Calendar.DAY_OF_WEEK);
        cal.set(Calendar.HOUR_OF_DAY, 13);
        cal.set(Calendar.MINUTE, 30);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        switch (weekday) {
            case Calendar.MONDAY:
                return new Timestamp(cal.getTime().getTime());
            case Calendar.TUESDAY:
                return new Timestamp(cal.getTime().getTime());
            case Calendar.WEDNESDAY:
                return new Timestamp(cal.getTime().getTime());
            case Calendar.THURSDAY:
                return new Timestamp(cal.getTime().getTime());
            case Calendar.FRIDAY:
                return new Timestamp(cal.getTime().getTime());
            case Calendar.SATURDAY:/*周六不发送*/
                return null;
            case Calendar.SUNDAY:/*周日不发送*/
                return null;
        }
        return null;
    }

    /**
     * 
     * @author bjpengpeng
     * @date 2015年10月23日
     * @description 获取给光大发送扣款文件的时间 上限
     * @return
     */
    public static Timestamp getGDDebitLowerTime(Timestamp upperTime) {
        if (null == upperTime) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(upperTime);
        int weekday = cal.get(Calendar.DAY_OF_WEEK);
        cal.set(Calendar.HOUR_OF_DAY, 13);
        cal.set(Calendar.MINUTE, 30);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        switch (weekday) {
            case Calendar.MONDAY:
                cal.add(Calendar.DAY_OF_MONTH, -2);//周五
                return new Timestamp(cal.getTime().getTime());
            case Calendar.TUESDAY:
                return new Timestamp(cal.getTime().getTime());
            case Calendar.WEDNESDAY:
                return new Timestamp(cal.getTime().getTime());
            case Calendar.THURSDAY:
                return new Timestamp(cal.getTime().getTime());
            case Calendar.FRIDAY:
                return new Timestamp(cal.getTime().getTime());
            case Calendar.SATURDAY:/*周六不发送*/
                return null;
            case Calendar.SUNDAY:/*周日不发送*/
                return null;
        }
        return null;
    }

    public static Date getGDConfirmTimeDesc(Timestamp orderTime) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(orderTime);
        int weekday = cal.get(Calendar.DAY_OF_WEEK) - 1;
        Calendar divisionTime = Calendar.getInstance();
        divisionTime.set(Calendar.HOUR_OF_DAY, 13);
        divisionTime.set(Calendar.MINUTE, 30);
        divisionTime.set(Calendar.SECOND, 0);
        switch (weekday) {
            case 0:
                cal.add(Calendar.DAY_OF_MONTH, 1);//下周一
                cal.set(Calendar.HOUR_OF_DAY, 15);
                cal.set(Calendar.MINUTE, 0);
                return cal.getTime();
            case 1:
                if (orderTime.compareTo(divisionTime.getTime()) < 0) {
                    cal.set(Calendar.HOUR_OF_DAY, 15);
                    cal.set(Calendar.MINUTE, 0);
                    return cal.getTime();
                }
                else {
                    cal.add(Calendar.DAY_OF_MONTH, 1);//周二
                    cal.set(Calendar.HOUR_OF_DAY, 15);
                    cal.set(Calendar.MINUTE, 0);
                    return cal.getTime();
                }
            case 2:
                if (orderTime.compareTo(divisionTime.getTime()) < 0) {
                    cal.set(Calendar.HOUR_OF_DAY, 15);
                    cal.set(Calendar.MINUTE, 0);
                    return cal.getTime();
                }
                else {
                    cal.add(Calendar.DAY_OF_MONTH, 1);//周三
                    cal.set(Calendar.HOUR_OF_DAY, 15);
                    cal.set(Calendar.MINUTE, 0);
                    return cal.getTime();
                }
            case 3:
                if (orderTime.compareTo(divisionTime.getTime()) < 0) {
                    cal.set(Calendar.HOUR_OF_DAY, 15);
                    cal.set(Calendar.MINUTE, 0);
                    return cal.getTime();
                }
                else {
                    cal.add(Calendar.DAY_OF_MONTH, 1);//周四
                    cal.set(Calendar.HOUR_OF_DAY, 15);
                    cal.set(Calendar.MINUTE, 0);
                    return cal.getTime();
                }
            case 4:
                if (orderTime.compareTo(divisionTime.getTime()) < 0) {
                    cal.set(Calendar.HOUR_OF_DAY, 15);
                    cal.set(Calendar.MINUTE, 0);
                    return cal.getTime();
                }
                else {
                    cal.add(Calendar.DAY_OF_MONTH, 1);//周二
                    cal.set(Calendar.HOUR_OF_DAY, 15);
                    cal.set(Calendar.MINUTE, 0);
                    return cal.getTime();
                }
            case 5:
                if (orderTime.compareTo(divisionTime.getTime()) < 0) {
                    cal.set(Calendar.HOUR_OF_DAY, 15);//周五扣款
                    cal.set(Calendar.MINUTE, 0);
                    return cal.getTime();
                }
                else {
                    cal.add(Calendar.DAY_OF_MONTH, 3);//下周一
                    cal.set(Calendar.HOUR_OF_DAY, 15);
                    cal.set(Calendar.MINUTE, 0);
                    return cal.getTime();
                }
            case 6:
                cal.add(Calendar.DAY_OF_MONTH, 2);//下周一
                cal.set(Calendar.HOUR_OF_DAY, 15);
                cal.set(Calendar.MINUTE, 0);
                return cal.getTime();
        }
        return cal.getTime();
    }

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
            default:

        }
        return calendar.getTime();
    }

    /**
     * 
     *  判断是否是同一天
     *
     *   @param today
     *   @param orderDay
     *   @return
     *
     *   2015年9月28日/下午5:58:46
     *   mailto:"cuixiang"<cuixiang@corp.netease.com>
     */
    public static boolean equalDay(Date today, Date orderDay) {
        if (null == today || null == orderDay) {
            return false;
        }
        else {
            return getDiffDays(getDateStart(today), getDateStart(orderDay)) == 0;
        }
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
     * @author bjwuguang
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
