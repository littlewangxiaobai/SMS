package com.whx.sms.util;

import android.text.format.Time;
import android.util.Log;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/**
 * 时间格式换算工具类
 * Created by zcy on 2017/9/22.
 */
public class TimeFormatUtils {

    public static final String DATE_FORMAT_TYPE_1 = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_TYPE_2 = "yyyy-MM-dd";
    public static final String DATE_FORMAT_TYPE_3 = "yyyy.MM.dd";
    public static final String DATE_FORMAT_TYPE_4 = "MM-dd HH:mm";
    public static final String DATE_FORMAT_TYPE_5 = "MM-dd";
    public static final String DATE_FORMAT_TYPE_6 = "yyyy年MM月";
    public static final String DATE_FORMAT_TYPE_9 = "HH:mm:ss";

    /**
     * lmn
     **/
    public static final String DATE_FORMAT_TYPE_7 = "MM月dd日";
    public static final String DATE_FORMAT_TYPE_8 = "HH:mm";
    //---------------20180209---------------//


    //数字格式化
    public static final String NUMBER_FORMAT_TYPE_0 = "0.";//取一位整数  
    public static final String NUMBER_FORMAT_TYPE_1 = "0.0";//保留一位小数
    public static final String NUMBER_FORMAT_TYPE_2 = "0.00";//取两位小数  
    public static final String NUMBER_FORMAT_TYPE_3 = "00.000";//取两位整数和三位小数，整数不足部分以0填补。  
    public static final String NUMBER_FORMAT_TYPE_4 = "#";//取所有整数部分  
    public static final String NUMBER_FORMAT_TYPE_5 = "#.##%";//以百分比方式计数，并取两位小数  
    public static final String NUMBER_FORMAT_TYPE_6 = "#.#####E0";//显示为科学计数法，并取五位小数  
    public static final String NUMBER_FORMAT_TYPE_7 = "00.####E0";//显示为两位整数的科学计数法，并取四位小数  
    public static final String NUMBER_FORMAT_TYPE_8 = ",###";//每三位以逗号进行分隔。 
    public static final String NUMBER_FORMAT_TYPE_9 = "光速大小为每秒,###米。";//将格式嵌入文本 


    public static String getCurrentTime() {
        Time time = new Time("GMT+8");

        time.setToNow();
        String year = "" + time.year;
        int imonth = time.month + 1;
        String month = imonth > 9 ? "" + imonth : "0" + imonth;
        String day = time.monthDay > 9 ? "" + time.monthDay : "0"
                + time.monthDay;
//        String hour = (time.hour + 8) > 9 ? "" + (time.hour + 8) : "0"
//                + (time.hour + 8);
//        String minute = time.minute > 9 ? "" + time.minute : "0" + time.minute;
//        String sec = time.second > 9 ? "" + time.second : "0" + time.second;
        String currentTime = year + month + day ;
        return currentTime;
    }


    public static String getYesterdayMonthDayTime(){
        Time time = new Time("GMT+8");
        time.set(getYesterdayTime());
        int imonth = time.month + 1;
        String currentTime =  imonth + "月"+time.monthDay+"日" ;
        return currentTime;
    }


    /**
     * 分钟转换成小时
     *
     * @param minTime 分钟数
     * @param type    保留小数的类型
     * @return 得到的结果
     */
    public static String formatM2H(int minTime, String type) {
        double h = ((double) minTime) / 60;
        return new DecimalFormat(type).format(h);
    }


//    /**
//     * 十位的数字转换成时间格式(秒为单位的数字)
//     * @param type 时间格式
//     * @param longTime 用数字表示的时间
//     * @return 转换成字符串格式的时间
//     */
//    public static String formatTime(String type ,String longTime){
//        long l = Long.parseLong(longTime)*1000;
//        SimpleDateFormat format = new SimpleDateFormat(type);
//        return format.format(l);
//    }

    /**
     * 十位的数字转换成时间格式(秒为单位的数字)
     *
     * @param type     时间格式
     * @param longTime 用数字表示的时间
     * @return 转换成字符串格式的时间
     */
    public static String formatTime(String type, String longTime) {
        return null == longTime ? "" : formatTime(type, Long.parseLong(longTime));
    }

    public static String formatTime(String type, long time) {
        return time == 0 ? "" : new SimpleDateFormat(type).format(time * 1000);
    }

    public static String formatTime3(String type, String longTime) {
        return null == longTime ? "" : formatTime2(type, Long.parseLong(longTime));
    }

    public static String formatTime3(String type, long longTime) {
        String time = String.valueOf(longTime);
        return formatTime3(type, time);
    }

    public static String formatTime2(String type, long time) {
        return time == 0 ? "" : new SimpleDateFormat(type).format(time * 1);
    }

    public static String formatTime(long endTime) {
        long s = endTime - System.currentTimeMillis() / 1000;
        return s < 0 ? null : s < 60 ? "即将完成" : mFormatTime(s);
    }

    private static String mFormatTime(long s) {
        return s / 60 < 60 ? s / 60 + "分钟" : hFormatTime(s / 60);
    }

    private static String hFormatTime(long m) {
        return m / 60 < 24 ? m / 60 + "小时" + m % 60 + "分钟" : dFormatTime(m / 60);
    }

    private static String dFormatTime(long h) {
        return h / 24 + "天" + h % 24 + "小时";
    }

    public static String getLeftTime(long time) {
        long l = time / 1000;
        long day = l / (24 * 60 * 60);
        long hour = (l % (24 * 60 * 60)) / (60 * 60);
        long min = (l % (60 * 60)) / 60;
        long s = l % 60;
        return ("" + day + "天" + hour + "小时" + min + "分" + s + "秒");
    }

    public static boolean isToday(String time) {
        return time.equals(formatTime(DATE_FORMAT_TYPE_5, getCurrentTime()));
    }


    /**
     * 输入时间戳变星期
     * 20180209
     *
     * @param time
     * @return
     * @author lmn
     */

    public static String changeWeek(long time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
        String times = sdr.format(new Date(time));
        Date date = null;
        int mydate = 0;
        String week = null;
        try {
            date = sdr.parse(times);
            Calendar cd = Calendar.getInstance();
            cd.setTime(date);
            mydate = cd.get(Calendar.DAY_OF_WEEK);
            // 获取指定日期转换成星期几
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (mydate == 1) {
            week = "星期日";
        } else if (mydate == 2) {
            week = "星期一";
        } else if (mydate == 3) {
            week = "星期二";
        } else if (mydate == 4) {
            week = "星期三";
        } else if (mydate == 5) {
            week = "星期四";
        } else if (mydate == 6) {
            week = "星期五";
        } else if (mydate == 7) {
            week = "星期六";
        }
        return week;

    }


    /**
     * 根据时间戳判断是否为上午、中午、下午 、晚上
     * 20180209
     *
     * @param time
     * @return
     * @author lmn
     */
    public static String getDuringDay(long time) {

        Date date = new Date(time);
        int hour = date.getHours();

        if (hour >= 6 && hour < 9) {
            return "早上好";
        }
        if (hour >= 9 && hour <= 12) {
            return "上午好";
        }
        if (hour >= 12 && hour <= 13) {
            return "中午好";
        } else if (hour >= 13 && hour <= 18) {
            return "下午好";
        } else {
            return "晚上好";
        }

    }


    /**
     * 根据毫秒转换成"yyyy-MM-dd   HH:mm:ss"格式的时间
     *
     * @param _ms
     * @return
     */
    public static String ms2DateOnlyDay1(long _ms) {

        if(_ms < 15371717400l){
            _ms = _ms * 1000;
        }

        Date date = new Date(_ms);
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT_TYPE_1, Locale.getDefault());
        return format.format(date);
    }

    /**
     * 根据毫秒转换成"HH:mm:ss"格式的时间
     *
     * @param _ms
     * @return
     */
    public static String ms2Time(long _ms) {
        long days = _ms / (1000 * 60 * 60 * 24);
        long hours = (_ms % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (_ms % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (_ms % (1000 * 60)) / 1000;

        StringBuilder stringBuilder = new StringBuilder();

        if (days > 0) {
            stringBuilder.append(days);
            stringBuilder.append("天 ");
        }

        if (days > 0 || hours > 0) {
            if (hours < 10) {
                stringBuilder.append("0");
            }
            stringBuilder.append(hours);
            stringBuilder.append("：");
        }

        if (minutes < 10) {
            stringBuilder.append("0");
        }
        stringBuilder.append(minutes);
        stringBuilder.append("：");


        if (seconds < 10) {
            stringBuilder.append("0");
        }
        stringBuilder.append(seconds);
        return stringBuilder.toString();
    }

    /**
     * 根据毫秒转换成"yyyy-MM-dd"格式的时间
     *
     * @param _ms
     * @return
     */
    public static String ms2DateOnlyDay(long _ms) {
        Date date = new Date(_ms);
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT_TYPE_2, Locale.getDefault());
        return format.format(date);
    }

    /**
     * 根据毫秒转换成"yyyy-MM-dd"格式的时间
     *
     * @param _ms
     * @return
     */
    public static String ms2DateOnlyDay2(long _ms) {
        Date date = new Date(_ms);
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT_TYPE_3, Locale.getDefault());
        return format.format(date);
    }
    /**
     * 根据毫秒转换成"yyyy-MM-dd"格式的时间
     *
     * @param _ms
     * @return
     */
    public static String ms2DateOnlyDay(String _ms) {
        long ms = Long.parseLong(String.valueOf(_ms));
        Date date = new Date(ms);


        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT_TYPE_2, Locale.getDefault());
        return format.format(date);
    }

    /**
     * 根据毫秒转换成"今天 09:35"格式的时间
     *
     * @param _ms
     * @return
     */
    public static String ms3DateOnlyDay(String _ms) {
        Date dateCurrent = new Date();
        long ms = Long.parseLong(String.valueOf(_ms));
        long msCurrent = dateCurrent.getTime();

        String dateStr = formatTime2(DATE_FORMAT_TYPE_2, msCurrent);
        String dateCurrentStr = formatTime2(DATE_FORMAT_TYPE_2, ms);

        if (dateStr.equals(dateCurrentStr)) {
            dateStr = "今天 " + formatTime2(DATE_FORMAT_TYPE_8, ms);
        } else {
            dateStr = formatTime3(DATE_FORMAT_TYPE_4, _ms);
        }
        return dateStr;
    }

    public static String ms3Date(String _ms) {
        Date dateCurrent = new Date();
        long ms = Long.parseLong(String.valueOf(_ms));
        long msCurrent = dateCurrent.getTime();

        String dateStr = formatTime2(DATE_FORMAT_TYPE_6, msCurrent);
        String dateCurrentStr = formatTime2(DATE_FORMAT_TYPE_6, ms);

        if (dateStr.equals(dateCurrentStr)) {
            dateStr = "本月";
        } else {
            dateStr = formatTime3(DATE_FORMAT_TYPE_6, _ms);
        }
        return dateStr;
    }

    /**
     * 根据毫秒转换成"今天 09:35"格式的时间
     *
     * @return
     */
    public static String ms3DateOnlyDay(long ms) {
        Date dateCurrent = new Date();
        long msCurrent = dateCurrent.getTime();

        String dateStr = formatTime2(DATE_FORMAT_TYPE_2, msCurrent);
        String dateCurrentStr = formatTime2(DATE_FORMAT_TYPE_2, ms);

        if (dateStr.equals(dateCurrentStr)) {
            dateStr = "今天 " + formatTime2(DATE_FORMAT_TYPE_8, ms);
        } else {
            dateStr = formatTime3(DATE_FORMAT_TYPE_4, ms);
        }
        return dateStr;
    }

    /**
     * 获得系统时间 年、月、日、小时、分钟
     *
     * @return HashMap
     */
    public static HashMap<String, Object> getTimeNew() {
        HashMap<String, Object> map = new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        map.put("year", calendar.get(Calendar.YEAR));
        map.put("month", calendar.get(Calendar.MONTH));
        map.put("day", calendar.get(Calendar.DAY_OF_MONTH));
        map.put("hour", calendar.get(Calendar.HOUR_OF_DAY));
        map.put("minute", calendar.get(Calendar.MINUTE));
        return map;
    }

    /**
     * 根据date获取月份
     *
     * @return int
     */
    public static int getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 根据date获取月份
     *
     * @return int
     */
    public static int getMonth(String _ms) {
        long ms = Long.parseLong(String.valueOf(_ms));
        Date date = new Date(ms);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 根据date获取月份
     *
     * @return int
     */
    public static boolean isCurrentMonth(String _ms) {
        long ms = Long.parseLong(String.valueOf(_ms));
        Date date = new Date(ms);
        Calendar calendarCurrent = Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendarCurrent.get(Calendar.MONTH) == calendar.get(Calendar.MONTH);
    }

    /**
     * 根据信用卡period判断是否过期 格式 032018
     *
     * @param time
     * @return
     */
    public static boolean isExpired(String time) {
        String formatTime = time.substring(2) + time.substring(0, 2);
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
        Log.i("mydata", "isExpired: " + Integer.valueOf(formatTime));
        return Integer.valueOf(format.format(date)) > Integer.valueOf(formatTime);
    }

    public static boolean isExpired(Date time) {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
        return Integer.valueOf(format.format(date)) > Integer.valueOf(format.format(time));
    }

    public static long getCurrentTimeStamp() {
        Date date = new Date(System.currentTimeMillis());
//        Timestamp timestamp=new Timestamp(date.getTime());
        return date.getTime();
    }




    public static String formatString(long hours) {
        if(hours < 10){
            return "0"+hours;
        }else{
            return String.valueOf(hours);
        }
    }

    public static long getYesterdayTime(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date start = calendar.getTime();
        return start.getTime()-24*60*60*1000L;
    }

    public static long getTodayTime(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date start = calendar.getTime();
        return start.getTime();
    }
}
