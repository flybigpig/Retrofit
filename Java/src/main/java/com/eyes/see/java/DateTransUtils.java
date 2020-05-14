package com.eyes.see.java;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author
 * @date 2020/5/7.
 * GitHub：
 * email：
 * description：
 */
public class DateTransUtils {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static final long DAY_IN_MILLIS = 24 * 60 * 60 * 1000;

    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate(String stamp) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(stamp);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    public static String stampToDate(long stamp) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date(stamp);
        res = simpleDateFormat.format(date);
        return res;
    }

    //获取今日某时间的时间戳
    public static long getTodayStartStamp(int hour, int minute, int second) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, second);
        long todayStamp = cal.getTimeInMillis();

//        Log.i("Wingbu", " DateTransUtils-getTodayStartStamp()  获取当日" + hour + ":" + minute + ":" + second + "的时间戳 :" + todayStamp);

        return todayStamp;
    }

    //获取当日00:00:00的时间戳,东八区则为早上八点
    public static long getZeroClockTimestamp(long time) {
        long currentStamp = time;
        currentStamp -= currentStamp % DAY_IN_MILLIS;
//        Log.i("Wingbu", " DateTransUtils-getZeroClockTimestamp()  获取当日00:00:00的时间戳,东八区则为早上八点 :" + currentStamp);
        return currentStamp;
    }

    //获取最近7天的日期,用于查询这7天的系统数据
    public static ArrayList<String> getSearchDays() {
        ArrayList<String> dayList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            dayList.add(getDateString(i));
        }
        return dayList;
    }

    //获取dayNumber天前，当天的日期字符串
    public static String getDateString(int dayNumber) {
        long time = System.currentTimeMillis() - dayNumber * DAY_IN_MILLIS;
//        Log.i("getDateString", " DateTransUtils-getDateString()  获取查询的日期 :" + dateFormat.format(time));
        return dateFormat.format(time);
    }

    public static String getDayDate(long time) {
        return dateFormat.format(time);
    }

    public static String formatDuring(long mss) {
        long timer = mss / 1000; //seconds
        String sTime = String.format("%02d:%02d:%02d", timer / 3600, timer % 3600 / 60, timer % 60);//转为标准格式
        return sTime;
    }

    public static String getAMorPMFormatTime(String time, String format) {
        SimpleDateFormat formatTime = new SimpleDateFormat(format + " aa", Locale.ENGLISH);
        try {
            return formatTime.format(new SimpleDateFormat(format).parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }


    public static String getTimeByAMorPMFormat(long time) {
        if (time == 0) {
            return "00:00:00";
        }
        SimpleDateFormat formatTime1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss aa", Locale.ENGLISH);
        SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
        String str = "";
        try {
            Date date = new Date(time);
            str = formatTime.format(date);
        } catch (Exception e) {
            System.out.println("传入日期错误");
        }
        return str;
    }

    public static String getTimeByAMorPM(long time) {
        if (time == 0) {
            return "AM";
        }
        SimpleDateFormat formatTime = new SimpleDateFormat("aa", Locale.ENGLISH);
        String str = "";
        try {
            Date date = new Date(time);
            str = formatTime.format(date);
        } catch (Exception e) {
            System.out.println("传入日期错误");
        }
        return str;
    }
}