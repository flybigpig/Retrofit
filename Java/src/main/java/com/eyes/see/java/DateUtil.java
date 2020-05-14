package com.eyes.see.java;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author
 * @date 2020/5/8.
 * GitHub：
 * email：
 * description：
 */
public class DateUtil {

    public static String getAMorPMFormatTime(String time, String format) {
        SimpleDateFormat formatTime = new SimpleDateFormat(format + " aa", Locale.ENGLISH);
        try {
            return formatTime.format(new SimpleDateFormat(format).parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }


    public static String getTimeByAMorPMFormat(String time, String format) {
        SimpleDateFormat formatTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss aa", Locale.ENGLISH);
        SimpleDateFormat formatTime1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss aa", Locale.ENGLISH);
        String str = "";
        try {
            str = formatTime1.format(formatTime.parse(time));
        } catch (Exception e) {
            System.out.println("传入日期错误");
        }
        return str;
    }

    public static String getTimeByAMorPMFormat(long time) {
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

    public static void main(String[] args) {
//        System.out.println(getAMorPMFormatTime("2019-03-03 19:10:10", "yyyy-MM-dd hh:mm:ss"));
//        System.out.println(getTimeByAMorPMFormat("2019-03-03 07:10:10 PM", "yyyy-MM-dd hh:mm:ss"));

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date start = calendar.getTime();

        String ss = DateTransUtils.stampToDate(start.getTime());
        System.out.println(ss);

        System.out.println(getTimeByAMorPMFormat(0));

    }
}
