package com.qbase.locationability;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 此工具类主要处理 时间，日期格式化输出，还有关于时间判断、应用的一些方法。 使用详情可以参考 例子 main()
 */
public class TimeUtil {
    // 常用日期输出格式
    public final static String YYYY_MM_DDTHH_MM_SS_XXX = "yyyy-MM-dd'T'HH:mm:ss.SSS";
    public final static String YYYY_MM_DDTHH_MM_SS = "yyyy-MM-dd'T'HH:mm:ss";
    public final static String YYYYMMDD = "yyyyMMdd";
    public final static String YYYY_MM_DD = "yyyy-MM-dd";
    public final static String YYYY_MM = "yyyy-MM";
    public final static String YYYYMMDDHHMM = "yyyyMMddHHmm";
    public final static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public final static String YYYY_MM_DD_BARS = "yyyy-MM-dd";
    public final static String YYYY_MM_DD_POINT = "yyyy.MM.dd";
    public final static String YYYY_MM_DD_SLASH = "yyyy/MM/dd";
    public final static String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public final static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public final static String YYYY_MM_DD_HH_MM_SS2 = "yyyy/MM/dd HH:mm:ss";
    public final static String YY_MM_DD_UNIT = "yyyy年MM月dd日";
    public final static String MM_DD_SLASH = "MM/dd";
    public final static String MM_DD_BARS = "MM-dd";
    public final static String MM_DD_HH_MM = "MM-dd HH:mm";
    public final static String HHMMSS = "HHmmss";
    public final static String HH_MM_SS = "HH:mm:ss";
    public final static String HH_MM = "HH:mm";
    public final static String YYYY = "yyyy";

    private final static int BASE_DAY = 80 * 365;
    private final static int ONE_DAY_TIME = 1000 * 24 * 60 * 60;

    /**
     * 给时间 格式转换
     *
     * @param newFormat time
     * @return
     */
    public static String timeFormat(String time, String oldFromat, String newFormat) {
        String formatTime = time;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(oldFromat);
            Date date = sdf.parse(time);
            SimpleDateFormat sdf2 = new SimpleDateFormat(newFormat);
            formatTime = sdf2.format(date);
        } catch (Exception e) {
        }
        return formatTime;
    }

    /**
     * 获取当前时间的秒数
     *
     * @return
     */
    public static int getCurrentSeconds() {
        long seconds = System.currentTimeMillis() / 1000;
        return Integer.parseInt(String.valueOf(seconds));
    }

    /**
     * 获取当前时间的秒数
     *
     * @return
     */
    public static long getCurrentSecond() {
        long seconds = System.currentTimeMillis() / 1000;
        return seconds;
    }

    /**
     * 获取当前时间的秒数
     *
     * @return 2016-02-29 09:43:29
     */
    public static String getCurrentTime() {
        return getCurrentTime(YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 获取当前时间的秒数
     *
     * @return 2016-02-29 09:43
     */
    public static String getCurrentFen() {
        return getCurrentTime(YYYY_MM_DD_HH_MM);
    }

    /**
     * 获取当前时间的秒数
     *
     * @return 2016-02-29 09:43:29
     */
    public static String getCurrentTime(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        long longTime = System.currentTimeMillis();
        Date date = new Date(longTime);
        return sdf.format(date);
    }

    /**
     * 获取固定格式的时间
     *
     * @param time 以秒数来定
     * @return
     */
    public static String intToFromatTime(int time) {
        return intToFromatTime(time, YYYY_MM_DD_SLASH);
    }

    /**
     * 获取固定格式的时间
     *
     * @param time 以秒数来定
     * @return
     */
    public static String intToFromatTime(int time, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        long longTime = (long) time * 1000;
        Date date = new Date(longTime);
        return sdf.format(date);
    }

    /**
     * 获取固定格式的时间
     *
     * @param time 以秒数来定
     * @return
     */
    public static long intToFromatTime(String time, String format) {
        long longTime = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Date date = sdf.parse(time);
            longTime = date.getTime() / 1000;
        } catch (Exception e) {

        }

        return longTime;
    }

    /**
     * 获取固定格式的时间
     *
     * @param time 以秒数来定
     * @return
     */
    public static String intToFromatTime(long time, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        long longTime = (long) time * 1000;
        Date date = new Date(longTime);
        return sdf.format(date);
    }

    /**
     * 获取固定格式的时间
     *
     * @param millisTime 以毫秒数来定
     * @return
     */
    public static String intMillsTimeToFromatTime(int millisTime) {
        return intMillsTimeToFromatTime(millisTime, YYYY_MM_DD_SLASH);
    }

    /**
     * 获取固定格式的时间
     *
     * @param millisTime，format 以毫秒数来定
     * @return
     */
    public static String intMillsTimeToFromatTime(int millisTime, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = new Date(millisTime);
        return sdf.format(date);
    }

    /**
     * @param startTime 01：14
     * @param endTime   12 ：24
     * @return endTime > startTime ture
     */
    public static boolean equslTime(String startTime, String endTime) {
        int sTime = Integer.parseInt(startTime.replace(":", "").trim());
        int eTime = Integer.parseInt(startTime.replace(":", "").trim());
        if (eTime > sTime) {
            return true;
        }
        return false;
    }

    /**
     * 获取剩余时间：剩余天数：剩余小时：剩余分钟
     *
     * @param seconds
     * @return 剩余：1天12小时15分钟
     */
    public static String getRemindTime(int seconds) {
        int day = 0;//天数
        int hour = 0;//小时
        int min = 0;//分钟

        String time = "剩余：";
        //天数
        day = seconds / (24 * 60 * 60);
        if (day != 0) {
            time = time + day + "天";
            return time;
        }

        hour = seconds / (60 * 60);
        if (hour != 0) {
            time = time + hour + "小时";
            return time;
        }

        min = seconds / (60 * 60);
        if (min != 0) {
            time = time + min + "分钟";
            return time;
        }

        return time;
    }

    /**
     * 按照格式输出时间
     *
     * @param time
     * @param timeFormat
     * @param outFormat
     * @return
     */
    public static String outPutFormatData(String time, String timeFormat, String outFormat) {
        String outTime = time;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(timeFormat);
            Date ds = sdf.parse(time);
            SimpleDateFormat sdf2 = new SimpleDateFormat(outFormat);
            outTime = sdf2.format(ds);
        } catch (Exception e) {
            return outTime;
        }
        return outTime;
    }

    /**
     * 按照格式输出时间
     *
     * @param time
     * @param outFormat
     * @return
     */
    public static String outStandTime(String time, String outFormat) {
        String outTime = time;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
            Date ds = sdf.parse(time);
            SimpleDateFormat sdf2 = new SimpleDateFormat(outFormat);
            outTime = sdf2.format(ds);
        } catch (Exception e) {
            return outTime;
        }
        return outTime;
    }

    /**
     * 将天数转换成秒数
     *
     * @param day
     * @return
     */
    public static int dayToSeconds(int day) {
        return day * 24 * 60 * 60;
    }

    /**
     * 获取固定格式的时间
     *
     * @return
     */
    public static String fromatStringTime(String time, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Date d = sdf.parse(time);
            return sdf.format(d);
        } catch (Exception e) {
            return time;
        }
    }

    /**
     * 获取固定格式的时间
     *
     * @return false 可编辑
     */
    public static boolean realBirthday(String time) {
        try {
            if (TextUtils.isEmpty(time)) {
                return false;
            }
            SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD_BARS);
            Date date = sdf.parse(time);
            long apart = System.currentTimeMillis() - date.getTime();
            //相隔天数  如果超过60年 则生日失效
            long apart_day = apart / ONE_DAY_TIME;
            if (apart_day >= BASE_DAY) {
                //超过
                return false;
            } else {
                return true;
            }

        } catch (Exception e) {
            return false;
        }

    }


    /**
     * 获取固定格式的时间
     * 将字符串时间转换为固定格式字符串时间
     * format：格式
     * time：时间
     *
     * @param time 2019-06-06 00:00:00
     * @return
     */
    public static String intFromatTime(String time, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Date d = sdf.parse(time);
            return sdf.format(d);
        } catch (Exception e) {
            return time;
        }
    }

    /**
     * 获取固定格式的时间
     *
     * @param time 2016-09-06T14:44:04.473
     * @return 2019-06-06 14:44:04
     */
    public static String initStandardTimeS(String time) {
        try {
            if (TextUtils.isEmpty(time)) {
                return time;
            }
            time = time.replace("Z", " UTC");
            try {

                String time_str = time.substring(0, time.indexOf("."));
                return initStandardTime(time_str);
            } catch (Exception e) {
                //注意是空格+UTC
                SimpleDateFormat sdf2 = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS, Locale.getDefault());
                //注意格式化的表达式
                SimpleDateFormat format = new SimpleDateFormat(YYYY_MM_DDTHH_MM_SS_XXX, Locale.getDefault());
                Date d = format.parse(time);
                return sdf2.format(d);
            }

        } catch (Exception e) {
            return initStandardTime(time);
        }
    }

    /**
     * 获取间隔时间
     *
     * @return 2019-06-06 14:44:04
     */

    public static String getIntervalTime(String time1, String time2) {
        try {
            if (TextUtils.isEmpty(time1)) {
                return "结束";
            }
            long endtime = intToFromatTime(time1, YYYY_MM_DD_HH_MM_SS);
            long cretime = intToFromatTime(time2, YYYY_MM_DD_HH_MM_SS);
            long chtime = endtime - cretime;

            String time = getTime(chtime);
            return time;
        } catch (Exception e) {
            return "结束";
        }
    }

    /**
     * 计时时间区间
     * time format :2019-06-06 14:44:04
     */
    public static Long initRectTime(String currenttime, String endtime) {
        try {
            if (TextUtils.isEmpty(currenttime) || TextUtils.isEmpty(endtime)) {
                return Long.valueOf(0);
            }

            long c_time = intToFromatTime(currenttime, YYYY_MM_DD_HH_MM_SS);
            long e_time = intToFromatTime(endtime, YYYY_MM_DD_HH_MM_SS);
            return e_time - c_time;
        } catch (Exception e) {
            return Long.valueOf(0);
        }
    }

    /**
     * 获取时分秒
     */
    public static String getTime(long time) {
        String str = "";

        int s = (int) (time % 60);
        int m = (int) (time / 60 % 60);
        int h = (int) (time / 3600);
        if (h != 0) {
            str = h + "小时";
        }

        if (m != 0) {
            str = str + m + "分";

        }
        if (s != 0) {
            str = str + s + "秒";
        }

        return str;
    }

    /**
     * 获取时分秒
     * "08:00:00" ==> 28800
     */
    public static long getLongTime(String time) {
        try {
            String[] my = time.split(":");
            int hour = Integer.parseInt(my[0]);
            int min = Integer.parseInt(my[1]);
            //int sec = Integer.parseInt(my[2]);
            return hour * 3600 + min * 60;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取固定格式的时间
     *
     * @param time 2016-09-06T14:44:04
     * @return 2019-06-06 14:44:04
     */
    public static String initStandardTime(String time) {
        try {
            if (TextUtils.isEmpty(time)) {
                return time;
            }
            //注意是空格+UTC
            time = time.replace("Z", " UTC");
            SimpleDateFormat sdf2 = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
            //注意格式化的表达式
            SimpleDateFormat format = new SimpleDateFormat(YYYY_MM_DDTHH_MM_SS);
            Date d = format.parse(time);
            return sdf2.format(d);
        } catch (Exception e) {
            return time;
        }
    }

    /**
     * 获取固定格式的时间
     * 将字符串时间转换为固定格式字符串时间
     * format：格式
     * time：时间
     *
     * @param
     * @return
     */
    public static String toString(String str, String Uformat) {
        SimpleDateFormat format = new SimpleDateFormat(Uformat);

        try {
            Date date = format.parse(str);
            return format.format(date);
        } catch (ParseException e) {
            return str;
        }
    }

    public static String formatUTC(long time) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS, Locale.CHINA);
            sdf.applyPattern(YYYY_MM_DD_HH_MM_SS);
            return sdf.format(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.valueOf(time);
    }
}
