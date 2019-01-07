package com.qishouqing.frame.utils;

import android.text.TextUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/***
 * 包名： cn.com.dareway.nurse.utils
 * 类名： DateUtil
 * 时间： 2018/8/18 16:55
 * 作者： ljm
 * 功能简述：日期处理类
 * 修改历史：
 */

public class DateUtil {

    /**
     * 时间日期格式化到年月日时分秒.
     */
    public static final String dateFormatYMDHMS = "yyyy-MM-dd HH:mm:ss";
    public static final String dateFormatYMDHMS1 = "yyyyMMddHHmmss";
    public static final String dateFormatYYMMdd = "yy-MM-dd";

    /**
     * 时间日期格式化到年月日.
     */
    public static final String dateFormatYMD = "yyyy-MM-dd";

    /**
     * 时间日期格式化到年月日.
     */
    public static final String dateFormatYYMMDD = "yyyyMMdd";

    /**
     * 时间日期格式化到年月.
     */
    public static final String dateFormatYM = "yyyy-MM";

    /**
     * 时间日期格式化到年月日时分.
     */
    public static final String dateFormatYMDHM = "yyyy-MM-dd HH:mm";

    /**
     * 时间日期格式化到月日.
     */
    public static final String dateFormatMD = "MM/dd";

    /**
     * 时分秒.
     */
    public static final String dateFormatHMS = "HH:mm:ss";

    /**
     * 时分.
     */
    public static final String dateFormatHM = "HH:mm";

    /**
     * 上午.
     */
    public static final String AM = "AM";

    /**
     * 下午.
     */
    public static final String PM = "PM";


    /**
     * 描述：String类型的日期时间转化为Date类型.
     *
     * @param strDate String形式的日期时间
     * @param format  格式化字符串，如："yyyy-MM-dd HH:mm:ss"
     * @return Date Date类型日期时间
     */
    public static Date getDateByFormat(String strDate, String format) {
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = mSimpleDateFormat.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 描述：获取偏移之后的Date.
     *
     * @param date          日期时间
     * @param calendarField Calendar属性，对应offset的值， 如(Calendar.DATE,表示+offset天,Calendar.HOUR_OF_DAY,表示＋offset小时)
     * @param offset        偏移(值大于0,表示+,值小于0,表示－)
     * @return Date 偏移之后的日期时间
     */
    public static Date getDateByOffset(Date date, int calendarField, int offset) {
        Calendar c = new GregorianCalendar();
        try {
            c.setTime(date);
            c.add(calendarField, offset);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c.getTime();
    }

    /**
     * 描述：获取指定日期时间的字符串(可偏移).
     *
     * @param strDate       String形式的日期时间
     * @param format        格式化字符串，如："yyyy-MM-dd HH:mm:ss"
     * @param calendarField Calendar属性，对应offset的值， 如(Calendar.DATE,表示+offset天,Calendar.HOUR_OF_DAY,表示＋offset小时)
     * @param offset        偏移(值大于0,表示+,值小于0,表示－)
     * @return String String类型的日期时间
     */
    public static String getStringByOffset(String strDate, String format, int calendarField, int offset) {
        String mDateTime = null;
        try {
            Calendar c = new GregorianCalendar();
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
            c.setTime(mSimpleDateFormat.parse(strDate));
            c.add(calendarField, offset);
            mDateTime = mSimpleDateFormat.format(c.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return mDateTime;
    }

    /**
     * 描述：Date类型转化为String类型(可偏移).
     *
     * @param date          the date
     * @param format        the format
     * @param calendarField the calendar field
     * @param offset        the offset
     * @return String String类型日期时间
     */
    public static String getStringByOffset(Date date, String format, int calendarField, int offset) {
        String strDate = null;
        try {
            Calendar c = new GregorianCalendar();
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
            c.setTime(date);
            c.add(calendarField, offset);
            strDate = mSimpleDateFormat.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strDate;
    }


    /**
     * 描述：Date类型转化为String类型.
     *
     * @param date   the date
     * @param format the format
     * @return String String类型日期时间
     */
    public static String getStringByFormat(Date date, String format) {
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
        String strDate = null;
        try {
            strDate = mSimpleDateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strDate;
    }

    /**
     * 描述：获取指定日期时间的字符串,用于导出想要的格式.
     *
     * @param strDate String形式的日期时间，必须为yyyy-MM-dd HH:mm:ss格式
     * @param format  输出格式化字符串，如："yyyy-MM-dd HH:mm:ss"
     * @return String 转换后的String类型的日期时间
     */
    public static String getStringByFormat(String strDate, String format) {
        String mDateTime = null;
        try {
            Calendar c = new GregorianCalendar();
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(dateFormatYMDHMS);
            c.setTime(mSimpleDateFormat.parse(strDate));
            SimpleDateFormat mSimpleDateFormat2 = new SimpleDateFormat(format);
            mDateTime = mSimpleDateFormat2.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mDateTime;
    }


    /**
     *
     *
     * @param strDate String形式的日期时间，必须为yyyy-MM-dd HH:mm:ss格式
     * @param format  输出格式化字符串，如："yyyy-MM-dd HH:mm:ss"
     * @return String 转换后的String类型的日期时间
     */
    /**
     * 描述：获取指定日期时间的字符串,用于导出想要的格式.
     *
     * @param strDate       String形式的日期时间
     * @param format        String形式的日期时间转化格式
     * @param resoureFormat 原日期String的格式
     * @return
     */
    public static String getStringByFormat(String strDate, String format, String resoureFormat) {
        String mDateTime = null;
        try {
            Calendar c = new GregorianCalendar();
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(resoureFormat);
            c.setTime(mSimpleDateFormat.parse(strDate));
            SimpleDateFormat mSimpleDateFormat2 = new SimpleDateFormat(format);
            mDateTime = mSimpleDateFormat2.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mDateTime;
    }

    /**
     * String形式的日期时间，必须为yyyyMMddHHmmss格式
     *
     * @param strDate tring形式的日期时间，必须为yyyyMMddHHmmss格式
     * @param format
     * @return
     */
    public static String getStringByYmdhmsFormat(String strDate, String format) {
        String mDateTime = null;
        try {
            Calendar c = new GregorianCalendar();
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(dateFormatYMDHMS1);
            c.setTime(mSimpleDateFormat.parse(strDate));
            SimpleDateFormat mSimpleDateFormat2 = new SimpleDateFormat(format);
            mDateTime = mSimpleDateFormat2.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mDateTime;
    }

    /**
     * 将 yyyyMMdd格式的日期，转化为yyyy-MM-dd格式
     *
     * @param strDate
     * @return
     */
    public static String praseymd2splitFormat(String strDate) {
        Date date = getDateByFormat(strDate, dateFormatYYMMDD);
     /*   String dateStr = DateUtil.getStringByFormat(date, dateFormatYYMMDD);*/
        return DateUtil.getStringByFormat(date, dateFormatYMD);
    }


    /**
     * 获取年月日，时分
     *
     * @param time
     * @return
     */
    public static String getYMDHS(String time) {
        String resTime = DateUtil.getStringByFormat(time, DateUtil.dateFormatYMDHM);
        if (TextUtils.isEmpty(resTime)) {
            return time;
        } else {
            return resTime;
        }
    }


    /**
     * 描述：获取milliseconds表示的日期时间的字符串.
     *
     * @param milliseconds the milliseconds
     * @param format       格式化字符串，如："yyyy-MM-dd HH:mm:ss"
     * @return String 日期时间字符串
     */
    public static String getStringByFormat(long milliseconds, String format) {
        String thisDateTime = null;
        try {
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
            thisDateTime = mSimpleDateFormat.format(milliseconds);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return thisDateTime;
    }

    /**
     * 描述：获取表示当前日期时间的字符串.
     *
     * @param format 格式化字符串，如："yyyy-MM-dd HH:mm:ss"
     * @return String String类型的当前日期时间
     */
    public static String getCurrentDate(String format) {
//		AbLogUtil.d(AbDateUtil.class, "getCurrentDate:"+format);
        String curDateTime = null;
        try {
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
            Calendar c = new GregorianCalendar();
            curDateTime = mSimpleDateFormat.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return curDateTime;

    }

    /**
     * 描述：获取表示当前日期时间的字符串(可偏移).
     *
     * @param format        格式化字符串，如："yyyy-MM-dd HH:mm:ss"
     * @param calendarField Calendar属性，对应offset的值， 如(Calendar.DATE,表示+offset天,Calendar.HOUR_OF_DAY,表示＋offset小时)
     * @param offset        偏移(值大于0,表示+,值小于0,表示－)
     * @return String String类型的日期时间
     */
    public static String getCurrentDateByOffset(String format, int calendarField, int offset) {
        String mDateTime = null;
        try {
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
            Calendar c = new GregorianCalendar();
            c.add(calendarField, offset);
            mDateTime = mSimpleDateFormat.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mDateTime;

    }


    public static String getLastYearDate(String dateFormat) {
        Calendar c = Calendar.getInstance();
        //过去一年
        c.setTime(new Date());
        c.add(Calendar.YEAR, -1);
        Date y = c.getTime();
        return getStringByFormat(y, dateFormat);

    }

    /**
     * 描述：计算两个日期所差的天数.
     *
     * @param milliseconds1 the milliseconds1
     * @param milliseconds2 the milliseconds2
     * @return int 所差的天数
     */
    public static int getOffectDay(long milliseconds1, long milliseconds2) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTimeInMillis(milliseconds1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis(milliseconds2);
        //先判断是否同年
        int y1 = calendar1.get(Calendar.YEAR);
        int y2 = calendar2.get(Calendar.YEAR);
        int d1 = calendar1.get(Calendar.DAY_OF_YEAR);
        int d2 = calendar2.get(Calendar.DAY_OF_YEAR);
        int maxDays = 0;
        int day = 0;
        if (y1 - y2 > 0) {
            maxDays = calendar2.getActualMaximum(Calendar.DAY_OF_YEAR);
            day = d1 - d2 + maxDays;
        } else if (y1 - y2 < 0) {
            maxDays = calendar1.getActualMaximum(Calendar.DAY_OF_YEAR);
            day = d1 - d2 - maxDays;
        } else {
            day = d1 - d2;
        }
        return day;
    }

    /**
     * 描述：计算两个日期所差的小时数.
     *
     * @param date1 第一个时间的毫秒表示
     * @param date2 第二个时间的毫秒表示
     * @return int 所差的小时数
     */
    public static int getOffectHour(long date1, long date2) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTimeInMillis(date1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis(date2);
        int h1 = calendar1.get(Calendar.HOUR_OF_DAY);
        int h2 = calendar2.get(Calendar.HOUR_OF_DAY);
        int h = 0;
        int day = getOffectDay(date1, date2);
        h = h1 - h2 + day * 24;
        return h;
    }

    /**
     * 描述：计算两个日期所差的分钟数.
     *
     * @param date1 第一个时间的毫秒表示
     * @param date2 第二个时间的毫秒表示
     * @return int 所差的分钟数
     */
    public static int getOffectMinutes(long date1, long date2) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTimeInMillis(date1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis(date2);
        int m1 = calendar1.get(Calendar.MINUTE);
        int m2 = calendar2.get(Calendar.MINUTE);
        int h = getOffectHour(date1, date2);
        int m = 0;
        m = m1 - m2 + h * 60;
        return m;
    }

    /**
     * 描述：获取本周一.
     *
     * @param format the format
     * @return String String类型日期时间
     */
    public static String getFirstDayOfWeek(String format) {
        return getDayOfWeek(format, Calendar.MONDAY);
    }

    /**
     * 描述：获取本周日.
     *
     * @param format the format
     * @return String String类型日期时间
     */
    public static String getLastDayOfWeek(String format) {
        return getDayOfWeek(format, Calendar.SUNDAY);
    }

    /**
     * 描述：获取本周的某一天.
     *
     * @param format        the format
     * @param calendarField the calendar field
     * @return String String类型日期时间
     */
    private static String getDayOfWeek(String format, int calendarField) {
        String strDate = null;
        try {
            Calendar c = new GregorianCalendar();
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
            int week = c.get(Calendar.DAY_OF_WEEK);
            if (week == calendarField) {
                strDate = mSimpleDateFormat.format(c.getTime());
            } else {
                int offectDay = calendarField - week;
                if (calendarField == Calendar.SUNDAY) {
                    offectDay = 7 - Math.abs(offectDay);
                }
                c.add(Calendar.DATE, offectDay);
                strDate = mSimpleDateFormat.format(c.getTime());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strDate;
    }

    /**
     * 描述：获取本月第一天.
     *
     * @param format the format
     * @return String String类型日期时间
     */
    public static String getFirstDayOfMonth(String format) {
        String strDate = null;
        try {
            Calendar c = new GregorianCalendar();
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
            //当前月的第一天
            c.set(GregorianCalendar.DAY_OF_MONTH, 1);
            strDate = mSimpleDateFormat.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strDate;

    }

    /**
     * 描述：获取本月最后一天.
     *
     * @param format the format
     * @return String String类型日期时间
     */
    public static String getLastDayOfMonth(String format) {
        String strDate = null;
        try {
            Calendar c = new GregorianCalendar();
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
            // 当前月的最后一天
            c.set(Calendar.DATE, 1);
            c.roll(Calendar.DATE, -1);
            strDate = mSimpleDateFormat.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strDate;
    }


    /**
     * 描述：获取表示当前日期的0点时间毫秒数.
     *
     * @return the first time of day
     */
    public static long getFirstTimeOfDay() {
        Date date = null;
        try {
            String currentDate = getCurrentDate(dateFormatYMD);
            date = getDateByFormat(currentDate + " 00:00:00", dateFormatYMDHMS);
            return date.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 描述：获取表示当前日期24点时间毫秒数.
     *
     * @return the last time of day
     */
    public static long getLastTimeOfDay() {
        Date date = null;
        try {
            String currentDate = getCurrentDate(dateFormatYMD);
            date = getDateByFormat(currentDate + " 24:00:00", dateFormatYMDHMS);
            return date.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 描述：判断是否是闰年()
     * <p>(year能被4整除 并且 不能被100整除) 或者 year能被400整除,则该年为闰年.
     *
     * @param year 年代（如2012）
     * @return boolean 是否为闰年
     */
    public static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 400 != 0) || year % 400 == 0;
    }

    /**
     * 描述：根据时间返回格式化后的时间的描述.
     * 小于1小时显示多少分钟前  大于1小时显示今天＋实际日期，大于今天全部显示实际时间
     *
     * @param strDate   the str date
     * @param outFormat the out format
     * @return the string
     */
    public static String formatDateStr2Desc(String strDate, String outFormat) {

        DateFormat df = new SimpleDateFormat(dateFormatYMDHMS);
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        try {
            c2.setTime(df.parse(strDate));
            c1.setTime(new Date());
            int d = getOffectDay(c1.getTimeInMillis(), c2.getTimeInMillis());
            if (d == 0) {
                int h = getOffectHour(c1.getTimeInMillis(), c2.getTimeInMillis());
                if (h > 0) {
                    return "今天" + getStringByFormat(strDate, dateFormatHM);
                    //return h + "小时前";
                } else if (h < 0) {
                    //return Math.abs(h) + "小时后";
                } else if (h == 0) {
                    int m = getOffectMinutes(c1.getTimeInMillis(), c2.getTimeInMillis());
                    if (m > 0) {
                        return m + "分钟前";
                    } else if (m < 0) {
                        //return Math.abs(m) + "分钟后";
                    } else {
                        return "刚刚";
                    }
                }

            } else if (d > 0) {
                if (d == 1) {
                    //return "昨天"+getStringByFormat(strDate,outFormat);
                } else if (d == 2) {
                    //return "前天"+getStringByFormat(strDate,outFormat);
                }
            } else if (d < 0) {
                if (d == -1) {
                    //return "明天"+getStringByFormat(strDate,outFormat);
                } else if (d == -2) {
                    //return "后天"+getStringByFormat(strDate,outFormat);
                } else {
                    //return Math.abs(d) + "天后"+getStringByFormat(strDate,outFormat);
                }
            }

            String out = getStringByFormat(strDate, outFormat);
            if (!TextUtils.isEmpty(out)) {
                return out;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return strDate;
    }


    /**
     * 取指定日期为星期几.
     *
     * @param strDate  指定日期
     * @param inFormat 指定日期格式
     * @return String   星期几
     */
    public static String getWeekNumber(String strDate, String inFormat) {
        String week = "星期日";
        Calendar calendar = new GregorianCalendar();
        DateFormat df = new SimpleDateFormat(inFormat);
        try {
            calendar.setTime(df.parse(strDate));
        } catch (Exception e) {
            return "错误";
        }
        int intTemp = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        switch (intTemp) {
            case 0:
                week = "星期日";
                break;
            case 1:
                week = "星期一";
                break;
            case 2:
                week = "星期二";
                break;
            case 3:
                week = "星期三";
                break;
            case 4:
                week = "星期四";
                break;
            case 5:
                week = "星期五";
                break;
            case 6:
                week = "星期六";
                break;
        }
        return week;
    }


    public static String getShortWeekStr(String strDate, String inFormat) {
        String week = "周日";
        Calendar calendar = new GregorianCalendar();
        DateFormat df = new SimpleDateFormat(inFormat);
        try {
            calendar.setTime(df.parse(strDate));
        } catch (Exception e) {
            return "错误";
        }
        int intTemp = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        switch (intTemp) {
            case 0:
                week = "周日";
                break;
            case 1:
                week = "周一";
                break;
            case 2:
                week = "周二";
                break;
            case 3:
                week = "周三";
                break;
            case 4:
                week = "周四";
                break;
            case 5:
                week = "周五";
                break;
            case 6:
                week = "周六";
                break;
        }
        return week;
    }

    /**
     * 根据给定的日期判断是否为上下午.
     *
     * @param strDate the str date
     * @param format  the format
     * @return the time quantum
     */
    public static String getTimeQuantum(String strDate, String format) {
        Date mDate = getDateByFormat(strDate, format);
        int hour = mDate.getHours();
        if (hour >= 12)
            return "PM";
        else
            return "AM";
    }

    /**
     * 根据当前时间判断是否为上下午.
     *
     * @return the time quantum
     */
    public static String getNowTimeQuantum() {
        Date mDate = new Date();
        int hour = mDate.getHours();
        if (hour >= 12)
            return "PM";
        else
            return "AM";
    }

    /**
     * 根据给定的毫秒数算得时间的描述.
     *
     * @param milliseconds the milliseconds
     * @return the time description
     */
    public static String getTimeDescription(long milliseconds) {
        if (milliseconds > 1000) {
            //大于一分
            if (milliseconds / 1000 / 60 > 1) {
                long minute = milliseconds / 1000 / 60;
                long second = milliseconds / 1000 % 60;
                return minute + "分" + second + "秒";
            } else {
                //显示秒
                return milliseconds / 1000 + "秒";
            }
        } else {
            return milliseconds + "毫秒";
        }
    }


    /* /**
     * 判断传入的字符串日期是否在今天之后
     *
     * @param ymd 字符串日期，格式是：yyyy-MM-dd
     * @return
     *//*
    public static boolean isAfterToday(String ymd) {
        Date dateToCompare = getDateByFormat(ymd, dateFormatYMD);
        Date dateToday = new Date();
        if (dateToCompare.after(dateToday)) {
            return true;
        }
        return false;

    }*/

    /***
     * 判断给定的两个字符串日期字符串是不是同一天,注意两个字符串的格式要一致
     *
     * @param dateStr1
     * @param dateStr2
     * @param dateFormat
     * @return
     */
    public static boolean isSameDay(String dateStr1, String dateStr2, String dateFormat) {
        if (TextUtils.isEmpty(dateStr1) || TextUtils.isEmpty(dateStr2)) {
            LogUtils.D("AbDateUtil_isSameDay", "所传时间字符串有空");
            return false;
        }
        Date date1 = getDateByFormat(dateStr1, dateFormat);
        Date date2 = getDateByFormat(dateStr2, dateFormat);
        int offsetDay = date1.compareTo(date2);
        return offsetDay == 0;
    }

    /***
     * 判断给定的两个字符串日期字符，第二个日期是否在第一个日期之前
     *
     * @param dateStr1
     * @param dateStr2
     * @param dateFormat
     * @return
     */
    public static boolean isAfterDay(String dateStr1, String dateStr2, String dateFormat) {
        if (TextUtils.isEmpty(dateStr1) || TextUtils.isEmpty(dateStr2)) {
            LogUtils.D("AbDateUtil_isSameDay", "所传时间字符串有空");
            return false;
        }
        Date date1 = getDateByFormat(dateStr1, dateFormat);
        Date date2 = getDateByFormat(dateStr2, dateFormat);
        return date1.after(date2);
    }

    /***
     * 判断给定的两个字符串日期字符，第二个日期是否在第一个日期之前
     *
     * @param dateStr1
     * @param dateStr2
     * @param dateFormat
     * @return
     */
    public static boolean isAfterTime(String dateStr1, String dateStr2, String dateFormat) {
        if (TextUtils.isEmpty(dateStr1) || TextUtils.isEmpty(dateStr2)) {
            //  Log.e("AbDateUtil_isSameDay", "所传时间字符串有空");
            return false;
        }
        Date date1 = getDateByFormat(dateStr1, dateFormat);
        Date date2 = getDateByFormat(dateStr2, dateFormat);
        return date1.after(date2);

    }


    /***
     * 判断给定的日期字符串是不是当天
     *
     * @param lDate 被比较的日期 格式是毫秒数
     * @return
     */
    public static boolean isNowDays(long lDate) {
        Calendar lCalendar = Calendar.getInstance();
        lCalendar.setTimeInMillis(lDate);
        Calendar nCalendar = Calendar.getInstance();
        nCalendar.setTimeInMillis(System.currentTimeMillis());
        int lYear = lCalendar.get(Calendar.YEAR);
        int nYear = nCalendar.get(Calendar.YEAR);
        if (lYear != nYear) {
            return false;
        }
        int lMonth = lCalendar.get(Calendar.MONTH);
        int nMonth = nCalendar.get(Calendar.MONTH);
        if (lMonth != nMonth) {
            return false;
        }
        int lDay = lCalendar.get(Calendar.DAY_OF_MONTH);
        int nDay = nCalendar.get(Calendar.DAY_OF_MONTH);
        return lDay == nDay;
    }

    public static String getMiddleTime(String startTime, String endTime, String dateFormat) {
        if (TextUtils.isEmpty(startTime) || TextUtils.isEmpty(endTime) || TextUtils.isEmpty(dateFormat)) {
            return null;
        }
        long sTime = DateUtil.getDateByFormat(startTime, dateFormat).getTime();
        long eTime = DateUtil.getDateByFormat(endTime, dateFormat).getTime();
        long mTime = sTime + ((eTime - sTime) / 2);
        return DateUtil.getStringByFormat(mTime, dateFormat);
    }

    /***
     * 获取提醒放学时间和当前时间相差的分钟数
     *
     * @param reminderTime 提醒时间
     */
    public static int getDifferenceTime(String reminderTime) {
        // 获取当前日期
        String ymd = DateUtil.getCurrentDate(DateUtil.dateFormatYMD);// 2015-10-27
        // 返回当前时间和提醒相差的分钟数
        return Math.abs(DateUtil.getOffectMinutes(System.currentTimeMillis(), DateUtil.getDateByFormat(ymd + " " + reminderTime, DateUtil.dateFormatYMDHM).getTime()));
    }


    /**
     * 判断时间是不是今天
     *
     * @param ymd
     * @return 是返回true，不是返回false
     */
    public static boolean isNow(String ymd) {
        //当前时间
        String today = getCurrentDate(DateUtil.dateFormatYMD);
        return today.equals(ymd);
    }

    /**
     * 判断时间是不是今天
     *
     * @param ymd
     * @return 是返回true，不是返回false
     */
    public static boolean isYesterDay(String ymd) {
        //当前时间
        String todayStr = getCurrentDate(DateUtil.dateFormatYMD);
        Date today = getDateByFormat(todayStr, DateUtil.dateFormatYMD);
        // 转化比较日期
        Date comDay = getDateByFormat(ymd, DateUtil.dateFormatYMD);
        if (comDay == null) {
            return false;
        }
        //昨天 86400000=24*60*60*1000 一天
        if ((today.getTime() - comDay.getTime()) > 0 && (today.getTime() - comDay.getTime()) <= 86400000) {
            return true;
        }
        return false;
    }


    /***
     * 根据秒数，换算成时间
     *
     * @param seconds

     * @return
     */
    public static String parse2Hms(int seconds) {
        String time = "";
        if (seconds > 30 * 60) {
            return time;
        } else {
            time += "00:";
            int minute = seconds / 60;
            if (minute == 0) {
                time += "00:";
            } else {
                time += strFormat2(minute + "") + ":";
            }
            int second = seconds % 60;
            time += strFormat2(second + "");
        }
        return time;
    }

    public static String timeLeft(int seconds) {
        LogUtils.D("intervalaaaa", seconds + "bbb");
        String time = "";
        time += "00:";
        int minute = seconds / 60;
        if (minute == 0) {
            time += "00:";
        } else {
            time += strFormat2(minute + "") + ":";
        }
        int second = seconds % 60;
        LogUtils.D("intervalaaaa", seconds + "ccc");
        time += strFormat2(second + "");
        return time;
    }

    /**
     * 统一时间格式显示
     *
     * @param dateFormatYMDHMS 年月日时分秒
     * @return
     */
    public static String getCommonTime(String dateFormatYMDHMS) {
        String showTime = dateFormatYMDHMS;
        if (!TextUtils.isEmpty(dateFormatYMDHMS)) {
            String ymd = DateUtil.getStringByFormat(dateFormatYMDHMS, DateUtil.dateFormatYMD);
            if (DateUtil.isNow(ymd)) {
                showTime = "今天 " + DateUtil.getStringByFormat(dateFormatYMDHMS, DateUtil.dateFormatHM);
            }/* else if (DateUtil.isYesterDay(ymd)) {
                showTime = "昨天 " + DateUtil.getStringByFormat(dateFormatYMDHMS, DateUtil.dateFormatHM);
            }*/ else {
                showTime = DateUtil.getStringByFormat(dateFormatYMDHMS, DateUtil.dateFormatYMDHM);
            }
        }
        return showTime;
    }

    /**
     * 描述：不足2个字符的在前面补“0”.
     *
     * @param str 指定的字符串
     * @return 至少2个字符的字符串
     */
    public static String strFormat2(String str) {
        try {
            if (str.length() <= 1) {
                str = "0" + str;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 获取几天前
     *
     * @param date
     * @return
     */
    public static Date getPreDate(Date date, int dayCount) {
        long addTime = 1;        //以1为乘以的基数
        addTime *= 1 * dayCount;            //1天以后，如果是30天以后则这里是30
        addTime *= 24;            //1天24小时
        addTime *= 60;            //1天60分钟
        addTime *= 60;            //1天60秒钟
        addTime *= 1000;        //1秒=1000毫秒
        //用毫秒数构造新的日期
        Date nextDate = new Date(date.getTime() - addTime);
        return nextDate;
    }

    /**
     * 获取几天前
     *
     * @param date
     * @return
     */
    public static String getPreDateFormat(Date date, int dayCount, String format) {
        Date nextDate = getPreDate(date, dayCount);
        try {
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
            return mSimpleDateFormat.format(nextDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 获取昨天日期
     *
     * @return
     */
    public static String getPreDateFormat(String format) {
        return getPreDateFormat(new Date(), 1, format);
    }

    /**
     * 由出生日期获得年龄 ,年月日
     *
     * @param ymd 格式 yyyyMMdd
     * @return
     * @throws Exception
     */
    public static int getAge(String ymd) {
        Date birthDay = getDateByFormat(ymd, dateFormatYYMMDD);
        Calendar cal = Calendar.getInstance();
        if (cal.before(birthDay)) {
            return -1;
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);

        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;
            } else {
                age--;
            }
        }
        return age;
    }

    /**
     * 判断当前时间是否在时间段内
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static boolean isBetweenDuration(String startTime, String endTime) {
        if (TextUtils.isEmpty(startTime) || TextUtils.isEmpty(endTime)) {
            return false;
        }
        Date startDate = getDateByFormat(getCurrentDate(dateFormatYMD) + " " + startTime, dateFormatYMDHMS);
        Date endDate = getDateByFormat(getCurrentDate(dateFormatYMD) + " " + endTime, dateFormatYMDHMS);
        Date nowDate = new Date();
        return nowDate.after(startDate) && nowDate.before(endDate);
    }


    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
        /* System.out.println(isSameDay("2015-2-3", "2015-02-03", dateFormatYMD));*/
        /*System.out.println(getMiddleTime("07:00:00", "09:30:00", dateFormatHMS));*/
        /* System.out.println(parse2Hms(1201));*/
    }

}
