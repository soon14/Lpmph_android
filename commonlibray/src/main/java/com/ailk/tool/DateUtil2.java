package com.ailk.tool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Project : XHS
 * Created by 王可 on 2016/10/19.
 */

public class DateUtil2 {

    private static final long ONE_HOUR = 1000*60*60;
    private static final long ONE_DAY_TIME=1000*60*60*24;
    private static final int BASE_YEAR=1900;

    private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    private final static ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };

    public static Date strDate(long time) {
        Timestamp ts = new Timestamp(time);
        Date date = new Date();
        try {
            date = ts;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 将字符串转位日期类型
     *
     * @param sdate
     * @return
     */
    public static Date toDate(String sdate) {
        return toDate(sdate, dateFormater.get());
    }

    public static Date toDate(String sdate, SimpleDateFormat dateFormater) {
        try {
            return dateFormater.parse(sdate);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String getDateString(Date date) {
        return dateFormater.get().format(date);
    }

    public static String getDateString2(Date date) {
        return dateFormater2.get().format(date);
    }

    /**
     * 以友好的方式显示时间
     *
     * @param sdate
     * @return
     */
    public static String friendly_time(String sdate) {
        Date time = null;

        if (isInEasternEightZones())
            time = toDate(sdate);
        else
            time = transformTime(toDate(sdate),
                    TimeZone.getTimeZone("GMT+08"), TimeZone.getDefault());

        if (time == null) {
            return "Unknown";
        }
        String ftime = "";
        Calendar cal = Calendar.getInstance();

        // 判断是否是同一天
        String curDate = dateFormater.get().format(cal.getTime());
        String paramDate = dateFormater.get().format(time);
        if (curDate.equals(paramDate)) {
            long hour = ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0)
                if ((cal.getTimeInMillis() - time.getTime()) < 0) {
                    ftime = Math.max(
                            (time.getTime() - cal.getTimeInMillis()) / 60000, 1)
                            + "分钟后";
                } else {
                    ftime = Math.max(
                            (cal.getTimeInMillis() - time.getTime()) / 60000, 1)
                            + "分钟前";
                }
            else
            if (hour < 0) {
                ftime = (int) ((time.getTime() - cal.getTimeInMillis()) / 3600000) + "小时后";
            } else {
                ftime = hour + "小时前";
            }
            return ftime;
        }

        long lt = time.getTime() / 86400000;
        long ct = cal.getTimeInMillis() / 86400000;
        long days = ct - lt;
        if (days == 0) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0)
                if ((cal.getTimeInMillis() - time.getTime()) < 0) {
                    ftime = Math.max(
                            (time.getTime() - cal.getTimeInMillis()) / 60000, 1)
                            + "分钟后";
                } else {
                    ftime = Math.max(
                            (cal.getTimeInMillis() - time.getTime()) / 60000, 1)
                            + "分钟前";
                }
            else
            if (hour < 0) {
                ftime = (int) ((time.getTime() - cal.getTimeInMillis()) / 3600000) + "小时后";
            } else {
                ftime = hour + "小时前";
            }
        } else if (days == 1) {
            ftime = "1天前";
        } else if (days == 2) {
            ftime = "2天前";
        } else if (days > 2 && days < 31) {
            ftime = days + "天前";
        } else if (days >= 31 && days <= 2 * 31) {
            ftime = "1个月前";
        } else if (days > 2 * 31 && days <= 3 * 31) {
            ftime = "2个月前";
        } else if (days > 3 * 31 && days <= 4 * 31) {
            ftime = "3个月前";
        } else if (days > 4 * 31 && days <= 5 * 31) {
            ftime = "4个月前";
        } else if (days > 5 * 31 && days <= 6 * 31) {
            ftime = "5个月前";
        } else if (days > 6 * 31 && days <= 7 * 31) {
            ftime = "6个月前";
        } else if (days > 7 * 31 && days <= 8 * 31) {
            ftime = "7个月前";
        } else if (days > 8 * 31 && days <= 9 * 31) {
            ftime = "8个月前";
        } else if (days > 9 * 31 && days <= 10 * 31) {
            ftime = "9个月前";
        } else if (days > 10 * 31 && days <= 11 * 31) {
            ftime = "10个月前";
        } else if (days > 11 * 31 && days <= 12 * 31) {
            ftime = "11个月前";
        } else if (days > 12 * 31){
            ftime = days % 365 + "年前";
        } else if (days < 0) {
            if (days == -1) {
                ftime = "1天后";
            } else if (days == -2) {
                ftime = "2天后";
            } else if (days < -2 && days > -31) {
                ftime = (lt - ct) + "天后";
            } else if (days <= -31 && days >= 2 * -31) {
                ftime = "1个月后";
            } else if (days < 2 * -31 && days >= 3 * -31) {
                ftime = "2个月后";
            } else if (days < 3 * -31 && days >= 4 * -31) {
                ftime = "3个月后";
            } else if (days < 4 * -31 && days >= 5 * -31) {
                ftime = "4个月后";
            } else if (days < 5 * -31 && days >= 6 * -31) {
                ftime = "5个月后";
            } else if (days < 6 * -31 && days >= 7 * -31) {
                ftime = "6个月后";
            } else if (days < 7 * -31 && days >= 8 * -31) {
                ftime = "7个月后";
            } else if (days < 8 * -31 && days >= 9 * -31) {
                ftime = "8个月后";
            } else if (days < 9 * -31 && days >= 10 * -31) {
                ftime = "9个月后";
            } else if (days < 10 * -31 && days >= 11 * -31) {
                ftime = "10个月后";
            } else if (days < 11 * -31 && days >= 12 * -31) {
                ftime = "11个月后";
            } else if (days < 12 * -31){
                ftime = (lt - ct) % 365 + "年后";
            }
        }
        return ftime;
    }

    public static String friendly_time2(long time) {
        long currentTime = System.currentTimeMillis();
        long gapTime = currentTime - time;
        String curDate = dateFormater2.get().format(Calendar.getInstance().getTime());
        String paramDate = dateFormater2.get().format(time);
        String mTime = "";
        Date date = new Date(time);
        SimpleDateFormat sdf;
        if (gapTime < ONE_HOUR) {
            mTime = Math.max(gapTime / 60000, 1) + "分钟前";
        }
        else if (gapTime < ONE_DAY_TIME && curDate.equals(paramDate)) {
            //相关时间与当前时间差小于一天只显示小时分钟
            sdf = new SimpleDateFormat("HH:mm", Locale.CHINA);
            mTime = "今天" + sdf.format(date);
        }
        else {
            int year = Calendar.getInstance().get(Calendar.YEAR);
            if (date.getYear()+BASE_YEAR==year) {
                //相关时间与当前时间差过长显示月日
                sdf = new SimpleDateFormat("MM-dd", Locale.CHINA);
            } else {
                //不在同年显示年份
                sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
            }
            mTime = sdf.format(date);
        }
        return mTime;
    }


    /**
     * 智能格式化
     */
    public static String friendly_time3(String sdate) {
        String res = "";
        if (isEmpty(sdate))
            return "";

        Date date = DateUtil2.toDate(sdate);
        if (date == null)
            return sdate;

        SimpleDateFormat format = dateFormater2.get();

        if (isToday(date.getTime())) {
            format.applyPattern(isMorning(date.getTime()) ? "上午 HH:mm" : "下午 HH:mm");
            res = format.format(date);
        }
//        else if (isYesterday(date.getTime())) {
//            format.applyPattern(isMorning(date.getTime()) ? "昨天 上午 hh:mm" : "昨天 下午 hh:mm");
//            res = format.format(date);
//        }
//        else if (isCurrentYear(date.getTime())) {
//            format.applyPattern(isMorning(date.getTime()) ? "MM-dd 上午 hh:mm" : "MM-dd 下午 hh:mm");
//            res = format.format(date);
//        }
        else {
            format.applyPattern("MM-dd HH:MM");
            res = format.format(date);
        }
        return res;
    }

    public static String friendlyTime(long time) {
        long currentTime = System.currentTimeMillis();
        long gapTime = currentTime - time;
        long gapTime2 = time - currentTime;
        long day = gapTime / ONE_DAY_TIME;
        long day2 = gapTime2 / ONE_DAY_TIME;
        String ftime = "";
        Calendar cal = Calendar.getInstance();

        if (gapTime > 0) {
            if (gapTime < ONE_HOUR) {
                ftime = Math.max(gapTime / 60000, 1) + "分钟前";
            }
            else if (ONE_HOUR < gapTime && gapTime < ONE_DAY_TIME) {
                ftime = (int) ((cal.getTimeInMillis() -time) / 3600000) + "小时前";
            }
            else if (day > 0 && day < 31) {
                ftime = day + "天前";
            }
            else if (day >= 31 && day <= 2 * 31) {
                ftime = "1个月前";
            }
            else if (day > 2 * 31 && day <= 3 * 31) {
                ftime = "2个月前";
            }
            else if (day > 3 * 31 && day <= 4 * 31) {
                ftime = "3个月前";
            }
            else if (day > 4 * 31 && day <= 5 * 31) {
                ftime = "4个月前";
            }
            else if (day > 5 * 31 && day <= 6 * 31) {
                ftime = "5个月前";
            }
            else if (day > 6 * 31 && day <= 7 * 31) {
                ftime = "6个月前";
            }
            else if (day > 7 * 31 && day <= 8 * 31) {
                ftime = "7个月前";
            }
            else if (day > 8 * 31 && day <= 9 * 31) {
                ftime = "8个月前";
            }
            else if (day > 9 * 31 && day <= 10 * 31) {
                ftime = "9个月前";
            }
            else if (day > 10 * 31 && day <= 11 * 31) {
                ftime = "10个月前";
            }
            else if (day > 11 * 31 && day <= 12 * 31) {
                ftime = "11个月前";
            }
            else if (day > 12 * 31){
                ftime = day % 365 + "年前";
            }
        } else {
            if (gapTime2 < ONE_HOUR) {
                ftime = Math.max(gapTime2 / 60000, 1) + "分钟后";
            }
            else if (ONE_HOUR < gapTime2 && gapTime2< ONE_DAY_TIME) {
                ftime = (int) ((cal.getTimeInMillis() -time) / 3600000) + "小时后";
            }
            else if (day2 > 0 && day2 < 31){
                ftime = day2 + "天后";
            }
            else if (day2 >= 31 && day2 <= 2 * 31) {
                ftime = "1个月后";
            }
            else if (day2 > 2 * 31 && day2 <= 3 * 31) {
                ftime = "2个月后";
            }
            else if (day2 > 3 * 31 && day2 <= 4 * 31) {
                ftime = "3个月后";
            }
            else if (day2 > 4 * 31 && day2 <= 5 * 31) {
                ftime = "4个月后";
            }
            else if (day2 > 5 * 31 && day2 <= 6 * 31) {
                ftime = "5个月后";
            }
            else if (day2 > 6 * 31 && day2 <= 7 * 31) {
                ftime = "6个月后";
            }
            else if (day2 > 7 * 31 && day2 <= 8 * 31) {
                ftime = "7个月后";
            }
            else if (day2 > 8 * 31 && day2 <= 9 * 31) {
                ftime = "8个月后";
            }
            else if (day2 > 9 * 31 && day2 <= 10 * 31) {
                ftime = "9个月后";
            }
            else if (day2 > 10 * 31 && day2 <= 11 * 31) {
                ftime = "10个月后";
            }
            else if (day2 > 11 * 31 && day2 <= 12 * 31) {
                ftime = "11个月后";
            }
            else if (day2 > 12 * 31){
                ftime = day2 % 365 + "年后";
            }
        }
        return ftime;
    }

    /**
     * @return 判断一个时间是不是上午
     */
    public static boolean isMorning(long when) {
        android.text.format.Time time = new android.text.format.Time();
        time.set(when);

        int hour = time.hour;
        return (hour >= 0) && (hour < 12);
    }

    /**
     * @return 判断一个时间是不是今天
     */
    public static boolean isToday(long when) {
        android.text.format.Time time = new android.text.format.Time();
        time.set(when);

        int thenYear = time.year;
        int thenMonth = time.month;
        int thenMonthDay = time.monthDay;

        time.set(System.currentTimeMillis());
        return (thenYear == time.year)
                && (thenMonth == time.month)
                && (thenMonthDay == time.monthDay);
    }

    /**
     * @return 判断一个时间是不是昨天
     */
    public static boolean isYesterday(long when) {
        android.text.format.Time time = new android.text.format.Time();
        time.set(when);

        int thenYear = time.year;
        int thenMonth = time.month;
        int thenMonthDay = time.monthDay;

        time.set(System.currentTimeMillis());
        return (thenYear == time.year)
                && (thenMonth == time.month)
                && (time.monthDay - thenMonthDay == 1);
    }

    /**
     * @return 判断一个时间是不是今年
     */
    public static boolean isCurrentYear(long when) {
        android.text.format.Time time = new android.text.format.Time();
        time.set(when);

        int thenYear = time.year;

        time.set(System.currentTimeMillis());
        return (thenYear == time.year);
    }

    /**
     * 获取当前日期是星期几<br>
     *
     * @param dt
     * @return 当前日期是星期几
     */
    public static int getWeekOfDate(Date dt) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return w;
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
     * 返回long类型的今天的日期
     *
     * @return
     */
    public static long getToday() {
        Calendar cal = Calendar.getInstance();
        String curDate = dateFormater2.get().format(cal.getTime());
        curDate = curDate.replace("-", "");
        return Long.parseLong(curDate);
    }

    public static String getCurTimeStr() {
        Calendar cal = Calendar.getInstance();
        String curDate = dateFormater.get().format(cal.getTime());
        return curDate;
    }

    /***
     * 计算两个时间差，返回的是的秒s
     *
     * @author 火蚁 2015-2-9 下午4:50:06
     *
     * @return long
     * @param dete1
     * @param date2
     * @return
     */
    public static long calDateDifferent(String dete1, String date2) {

        long diff = 0;

        Date d1 = null;
        Date d2 = null;

        try {
            d1 = dateFormater.get().parse(dete1);
            d2 = dateFormater.get().parse(date2);

            // 毫秒ms
            diff = d2.getTime() - d1.getTime();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return diff / 1000;
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
     * 字符串转整数
     *
     * @param str
     * @param defValue
     * @return
     */
    public static int toInt(String str, int defValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
        }
        return defValue;
    }

    /**
     * 对象转整数
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static int toInt(Object obj) {
        if (obj == null)
            return 0;
        return toInt(obj.toString(), 0);
    }

    /**
     * 对象转整数
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static long toLong(String obj) {
        try {
            return Long.parseLong(obj);
        } catch (Exception e) {
        }
        return 0;
    }

    /**
     * 字符串转布尔值
     *
     * @param b
     * @return 转换异常返回 false
     */
    public static boolean toBool(String b) {
        try {
            return Boolean.parseBoolean(b);
        } catch (Exception e) {
        }
        return false;
    }

    public static String getString(String s) {
        return s == null ? "" : s;
    }

    /**
     * 将一个InputStream流转换成字符串
     *
     * @param is
     * @return
     */
    public static String toConvertString(InputStream is) {
        StringBuffer res = new StringBuffer();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader read = new BufferedReader(isr);
        try {
            String line;
            line = read.readLine();
            while (line != null) {
                res.append(line + "<br>");
                line = read.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != isr) {
                    isr.close();
                    isr.close();
                }
                if (null != read) {
                    read.close();
                    read = null;
                }
                if (null != is) {
                    is.close();
                    is = null;
                }
            } catch (IOException e) {
            }
        }
        return res.toString();
    }

    /***
     * 截取字符串
     *
     * @param start
     *            从那里开始，0算起
     * @param num
     *            截取多少个
     * @param str
     *            截取的字符串
     * @return
     */
    public static String getSubString(int start, int num, String str) {
        if (str == null) {
            return "";
        }
        int leng = str.length();
        if (start < 0) {
            start = 0;
        }
        if (start > leng) {
            start = leng;
        }
        if (num < 0) {
            num = 1;
        }
        int end = start + num;
        if (end > leng) {
            end = leng;
        }
        return str.substring(start, end);
    }

    /**
     * 获取当前时间为每年第几周
     *
     * @return
     */
    public static int getWeekOfYear() {
        return getWeekOfYear(new Date());
    }

    /**
     * 获取当前时间为每年第几周
     *
     * @param date
     * @return
     */
    public static int getWeekOfYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        int week = c.get(Calendar.WEEK_OF_YEAR) - 1;
        week = week == 0 ? 52 : week;
        return week > 0 ? week : 1;
    }

    public static int[] getCurrentDate() {
        int[] dateBundle = new int[3];
        String[] temp = getDataTime("yyyy-MM-dd").split("-");

        for (int i = 0; i < 3; i++) {
            try {
                dateBundle[i] = Integer.parseInt(temp[i]);
            } catch (Exception e) {
                dateBundle[i] = 0;
            }
        }
        return dateBundle;
    }

    /**
     * 返回当前系统时间
     */
    public static String getDataTime(String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(new Date());
    }

    /**
     * 判断用户的设备时区是否为东八区（中国） 2014年7月31日
     * @return
     */
    public static boolean isInEasternEightZones() {
        boolean defaultVaule = true;
        if (TimeZone.getDefault() == TimeZone.getTimeZone("GMT+08"))
            defaultVaule = true;
        else
            defaultVaule = false;
        return defaultVaule;
    }

    /**
     * 根据不同时区，转换时间 2014年7月31日
     * @param
     * @return
     */
    public static Date transformTime(Date date, TimeZone oldZone, TimeZone newZone) {
        Date finalDate = null;
        if (date != null) {
            int timeOffset = oldZone.getOffset(date.getTime())
                    - newZone.getOffset(date.getTime());
            finalDate = new Date(date.getTime() - timeOffset);
        }
        return finalDate;
    }

    public static Timestamp getCurrentTimeStamp() {
        Date date = new Date(System.currentTimeMillis());
        return new Timestamp(date.getTime());
    }

}
