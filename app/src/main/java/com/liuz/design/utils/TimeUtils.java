package com.liuz.design.utils;

import android.text.TextUtils;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;


/**
 * date: 2018/7/26 14:41
 * author liuzhao
 */
public class TimeUtils {
    public static final String TIME_FORMAT_STYLE_DIANDIAN = "yyyy.MM.dd";
    public static final String TIME_FORMAT_STYLE_YMD = "yyyy/MM/dd HH:mm";
    public static final String Time_FORMAT_YEAD = "yyyy年MM月dd日 HH:mm";
    public static final String Time_POSITION = "yyyy/MM/dd";
    public static final String TIME_FORMAT_STYLE_MD = "MM月dd日 HH:mm";
    private final static Pattern emailer = Pattern
            .compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
    // private final static SimpleDateFormat dateFormater = new
    // SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    // private final static SimpleDateFormat dateFormater2 = new
    // SimpleDateFormat("yyyy-MM-dd");

    private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        }
    };

    private final static ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };

    private final static ThreadLocal<SimpleDateFormat> dateFormater3 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM");
        }
    };

    private final static ThreadLocal<SimpleDateFormat> dateFormater4 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };

    /**
     * 将字符串转为日期类型
     *
     * @param sdate
     * @return
     */
    public static Date toDate(String sdate) {
        Date d = new Date();
        d.setTime(Long.parseLong(sdate) * 1000);
        return d;
    }

    /**
     * 友好方式显示日期
     *
     * @param sdate
     * @return
     */
    public static String friendly_time(String sdate) {
        Date time = toDate(sdate);
        Log.i("toDate", time.toString());
        return friendly_time1(time);
    }

    public static String friednlyTime(long time) {
        Date d = new Date();
        d.setTime(time);
        return friendly_time1(d);
    }

    /**
     * 以友好的方式显示时间
     *
     * @param time
     * @return
     */
    public static String friendly_time1(Date time) {

        if (time == null) {
            return "Unknown";
        }

        Date serviceTime = new Date();
        if (serviceTime == null) {
            return "Unknown1";
        }
        String ftime = "";

        // 判断是否是同一天
        String curDate = dateFormater2.get().format(serviceTime.getTime());
        String paramDate = dateFormater2.get().format(time);
        if (curDate.equals(paramDate)) {
            int hour = (int) ((serviceTime.getTime() - time.getTime()) / 3600000);
            if (hour == 0)
                ftime = Math.max(
                        (serviceTime.getTime() - time.getTime()) / 60000, 1)
                        + "分钟前";
            else
                ftime = hour + "小时前";
            return ftime;
        }

        long lt = time.getTime() / 86400000;
        long ct = serviceTime.getTime() / 86400000;
        int days = (int) (ct - lt);
        if (days == 0) {
            int hour = (int) ((serviceTime.getTime() - time.getTime()) / 3600000);
            if (hour == 0)
                ftime = Math.max(
                        (serviceTime.getTime() - time.getTime()) / 60000, 1)
                        + "分钟前";
            else
                ftime = hour + "小时前";
        } else if (days == 1) {
            ftime = "昨天";
        } else if (days == 2) {
            ftime = "前天";
        } else if (days > 2 && days <= 10) {
            ftime = (days - 1) + "天前";
        } else if (days > 10) {
            ftime = dateFormater2.get().format(time);
        }
        return ftime;
    }

    /**
     * 判断给定字符串时间是否为今日
     *
     * @param sdate
     * @return boolean
     */
    public static boolean isToday(String sdate) {
        boolean b = false;
        Date time = toDate(sdate);
        Date today = new Date();
        if (time != null) {
            String nowDate = dateFormater2.get().format(today);
            String timeDate = dateFormater2.get().format(time);
            if (nowDate.equals(timeDate)) {
                b = true;
            }
        }
        return b;
    }

    /**
     * 判断给定字符串时间是否为今日
     *
     * @param sdate
     * @return boolean
     */
    public static boolean isThisMonth(String sdate) {
        boolean b = false;
        Date time = toDate(sdate);
        Date today = new Date();
        if (time != null) {
            String nowDate = dateFormater3.get().format(today);
            String timeDate = dateFormater3.get().format(time);
            if (nowDate.equals(timeDate)) {
                b = true;
            }
        }
        return b;
    }

    /**
     * 判断是否为今年，必须固定格式如下 ：2014-01-01
     *
     * @param time 如：2014-01-01
     * @return
     */
    public static boolean isThisYear(String time) {
        if (time.length() != 10) {
            return false;
        } else {
            Date today = new Date();
            String nowDate = dateFormater2.get().format(today);
            if (nowDate.substring(0, 4).equals(time.substring(0, 4))) {
                return true;
            } else {
                return false;
            }
        }

    }

    /**
     * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
     *
     * @param input
     * @return boolean
     */
    public static boolean isEmpty(String input) {
        if (input == null || "".equals(input))
            return true;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }


    /**
     * 格式化显示时间
     * @param dateStr  yyyy-MM-dd HH:mm:ss
     * @return
     */
//    public static String formatDate(String dateStr) {
//
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//        Date date;
//        try {
//            date = sdf.parse(dateStr);
//        } catch (ParseException e) {
//            date = new Date();
//            e.printStackTrace();
//        }
//        String needStr = sdf.format(date);
//        return needStr;
//    }

    /**
     * @param dateStr
     * @param format
     * @return
     */
    public static Date parseDate(String dateStr, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            date = new Date();
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 格式化时间
     *
     * @param time
     * @param format
     * @return
     */
    public static String formatTime(long time, String format) {
        if (time == 0 || TextUtils.isEmpty(format)) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format.toString());
        Date date = new Date(time);
        String dateStr = sdf.format(date);
        return dateStr;
    }

    /**
     * 格式化时间
     *
     * @return
     */
    public static String formatTime(String strTime) {
        long time = Long.parseLong(strTime);
        if (time == 0 || TextUtils.isEmpty(TIME_FORMAT_STYLE_YMD)) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT_STYLE_YMD.toString());
        Date date = new Date(time * 1000);
        String dateStr = sdf.format(date);
        return dateStr;
    }

    /**
     * 格式化时间
     *
     * @return
     */
    public static String formatTime(String strTime, String format) {
        long time = Long.parseLong(strTime);
        if (time == 0 || TextUtils.isEmpty(format)) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format.toString());
        Date date = new Date(time * 1000);
        String dateStr = sdf.format(date);
        return dateStr;
    }


    /*时间戳转换成字符窜*/
    static SimpleDateFormat sf;

    public static String getDateToString(long time) {
        Date d = new Date(time);
        sf = new SimpleDateFormat("yyyy年MM月dd日");
        return sf.format(d);
    }

    public static String formatTimeMonth(String strTime) {
        long time = Long.parseLong(strTime);
        if (time == 0 || TextUtils.isEmpty(TIME_FORMAT_STYLE_MD)) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT_STYLE_MD.toString());
        Date date = new Date(time * 1000);
        String dateStr = sdf.format(date);
        return dateStr;
    }

    /**
     * 根据年 月 获取对应的月份 天数
     */
    public static int getDaysByYearMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DATE, 1);
        cal.roll(Calendar.DATE, -1);
        int maxDate = cal.get(Calendar.DATE);
        return maxDate;
    }

    /**
     * 将字符串转为日期类型
     *
     * @param sdate
     * @return
     */
    public static Date toDate2(String sdate) {

        try {
            if (sdate.contains("/")) {
                return dateFormater2.get().parse(sdate);
            } else {
                return dateFormater4.get().parse(sdate);
            }
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 获取当前年月日
     *
     * @return
     */
    public static String getCurrentTime() {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateNowStr = sdf.format(d);
        return dateNowStr;
    }

    /**
     * 获取当前年月日
     *
     * @return
     */
    public static long getCurrentLongTime() {
        return System.currentTimeMillis() / 1000;
    }


}
