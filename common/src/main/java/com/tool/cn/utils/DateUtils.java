package com.tool.cn.utils;

import android.support.annotation.NonNull;
import android.support.annotation.StringDef;
import android.text.TextUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 *  2017/2/27  9:56.
 *
 *
 * @version 1.0.0
 * @class DateUtils
 * @describe 日期时间处理工具类
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class DateUtils {

    private static final String TAG = "DateUtils";

    /**
     * 默认格式化规则
     */
    public static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_SHORT_PATTERN = "yyyy/MM/dd";
    public static final String DEFAULT_PATTERN_NEW = "MM/dd HH:mm";
    /**
     * 默认地区
     */
    private static final Locale DEFAULT_LOCALE = Locale.CANADA;
    /**
     * 1秒钟的毫秒数
     */
    public static final long MILLISECOND_IN_SECOND = 1000;
    /**
     * 1分钟的毫秒数
     */
    public static final long MILLISECOND_IN_MINUTE = 60 * MILLISECOND_IN_SECOND;
    /**
     * 1小时的毫秒数
     */
    public static final long MILLISECOND_IN_HOUR = 60 * MILLISECOND_IN_MINUTE;
    /**
     * 1天的毫秒数
     */
    public static final long MILLISECOND_IN_DAY = 24 * MILLISECOND_IN_HOUR;

    /**
     * 时间单位
     */
    @StringDef({TIME_UNIT_SECOND, TIME_UNIT_MINUTE, TIME_UNIT_HOUR, TIME_UNIT_DAY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface UNIT {

    }

    public static final String TIME_UNIT_SECOND = "s";//时间单位——秒
    public static final String TIME_UNIT_MINUTE = "m";//时间单位——分钟
    public static final String TIME_UNIT_HOUR = "h";//时间单位——小时
    public static final String TIME_UNIT_DAY = "d";//时间单位——天

    /**
     * 获取系统当前时间
     *
     * @param pattern 时间格式
     * @return
     */
    public static String getCurrentTime(String pattern) {
        return millisecond2Str(System.currentTimeMillis(), pattern);
    }


    /**
     * 将Date时间根据pattern格式化为String类型
     *
     * @param date 需要格式化的Date类型时间
     * @return 根据DEFAULT_PATTERN格式化后的String类型时间字符串
     * 注：pattern默认为 DEFAULT_PATTERN
     * 注：区域默认为 DEFAULT_LOCALE
     */
    public static String date2Str(@NonNull Date date) {
        return date2Str(date, null);
    }

    /**
     * 转换时间格式，默认将yyyy-MM-dd HH:mm:ss格式转换为yyyy-MM-dd格式
     *
     * @param original 需要转化的时间
     * @return 转换后的时间
     */
    public static String date3Str(String original) {
        return str2Str(original, DEFAULT_PATTERN, DEFAULT_SHORT_PATTERN);
    }

    public static String date3Str(Long time) {
        if (time == null || time == 0) {
            return "";
        }
        return str2Str(millisecond2Str(time), DEFAULT_PATTERN, DEFAULT_PATTERN_NEW);
    }

    /**
     * 将originalPattern格式的String类型时间转换为targetPattern格式的String类型时间
     *
     * @param original        原始originalPattern格式的时间
     * @param originalPattern 原始时间格式
     * @param targetPattern   目标时间格式
     * @return 格式化后的时间
     */
    public static String str2Str(String original, String originalPattern, String targetPattern) {
        Date date = str2Date(original, originalPattern);
        return date2Str(date, targetPattern);
    }

    /**
     * 将Date时间根据pattern格式化为String类型
     *
     * @param date    需要格式化的Date类型时间
     * @param pattern 格式化模板
     * @return 根据pattern格式化后的String类型时间字符串
     * 注：区域默认为 DEFAULT_LOCALE
     */
    public static String date2Str(@NonNull Date date, String pattern) {
        return date2Str(date, pattern, null);
    }

    /**
     * 将Date时间根据pattern格式化为String类型
     *
     * @param date    需要格式化的Date类型时间
     * @param pattern 格式化模板
     * @param locale  区域
     * @return 根据pattern格式化后的String类型时间字符串
     */
    @SuppressWarnings("ConstantConditions")
    public static String date2Str(@NonNull Date date, String pattern, Locale locale) {
        if (null == date) {
            throw new IllegalArgumentException("The date must not be null");
        }
        try {
            SimpleDateFormat sdf = getSimpleDateFormat(pattern, locale);
            return sdf.format(date);
        } catch (Exception e) {
            LogUtils.e(TAG, "formatDate failed" + e.getMessage());
            return "";
        }
    }

    /**
     * 将String类时间字符串格式化为Date时间
     *
     * @param strDate 需要格式化的String类型时间
     * @return 根据pattern格式化后的Date类型时间
     * 注：pattern默认为 DEFAULT_PATTERN
     * 注：区域默认为 DEFAULT_LOCALE
     */
    public static Date str2Date(String strDate) {
        return str2Date(strDate, null, null);
    }

    /**
     * 将String类时间字符串格式化为Date时间
     *
     * @param strDate 需要格式化的String类型时间
     * @param pattern 格式化模板
     * @return 根据pattern格式化后的Date类型时间
     * 注：区域默认为 DEFAULT_LOCALE
     */
    public static Date str2Date(String strDate, String pattern) {
        return str2Date(strDate, pattern, null);
    }

    /**
     * 将String类时间字符串格式化为Date时间
     *
     * @param strDate 需要格式化的String类型时间
     * @param pattern 格式化模板
     * @param locale  区域
     * @return 根据pattern格式化后的Date类型时间
     */
    public static Date str2Date(String strDate, String pattern, Locale locale) {
        if (TextUtils.isEmpty(strDate)) {
            return new Date();
        }
        SimpleDateFormat sdf = getSimpleDateFormat(pattern, locale);
        try {
            return sdf.parse(strDate);
        } catch (ParseException e) {
            LogUtils.e(TAG, "strParseDate failed" + e.getMessage());
            return new Date();
        }
    }

    /**
     * 将毫秒值类时间字符串格式化为String
     *
     * @param millisecond 需要格式化的毫秒值
     * @return 根据pattern格式化后的String时间
     * 注：pattern默认为 DEFAULT_PATTERN
     * 注：区域默认为 DEFAULT_LOCALE
     */
    public static String millisecond2Str(long millisecond) {
        Date date = new Date(millisecond);
        return date2Str(date);
    }

    /**
     * 将毫秒值类时间字符串格式化为String
     *
     * @param millisecond 需要格式化的毫秒值
     * @param pattern     格式化规则
     * @return 根据pattern格式化后的String时间
     */
    public static String millisecond2Str(long millisecond, String pattern) {
        Date date = new Date(millisecond);
        return date2Str(date, pattern);
    }

    /**
     * 将String类时间字符串格式化为毫秒值
     *
     * @param strDate 需要格式化的String类型时间
     * @return 根据pattern格式化后的毫秒值时间
     * 注：pattern默认为 DEFAULT_PATTERN
     * 注：区域默认为 DEFAULT_LOCALE
     */
    public static long str2Millisecond(String strDate) {
        return str2Millisecond(strDate, null);
    }

    /**
     * 将String类时间字符串格式化为毫秒值
     *
     * @param strDate 需要格式化的String类型时间
     * @param pattern 格式化模板
     * @return 根据pattern格式化后的毫秒值时间
     * 注：区域默认为 DEFAULT_LOCALE
     */
    public static long str2Millisecond(String strDate, String pattern) {
        return str2Millisecond(strDate, pattern, null);
    }

    /**
     * 将String类时间字符串格式化为毫秒值
     *
     * @param strDate 需要格式化的String类型时间
     * @param pattern 格式化模板
     * @param locale  区域
     * @return 根据pattern格式化后的毫秒值时间
     */
    public static long str2Millisecond(String strDate, String pattern, Locale locale) {
        Date date = str2Date(strDate, pattern, locale);
        return date.getTime();
    }

    /**
     * 将Date对象转换为Calendar对象
     *
     * @param date 需要转换的Date对象
     * @return 转换后的Calendar对象
     */
    public static Calendar date2Calendar(@NonNull Date date) {
        return date2Calendar(date, null);
    }

    /**
     * 将Date对象转换为Calendar对象
     *
     * @param date     需要转换的Date对象
     * @param timeZone 时区
     * @return 转换后的Calendar对象
     */
    public static Calendar date2Calendar(@NonNull Date date, TimeZone timeZone) {
        Calendar calendar;
        if (null == timeZone) {
            calendar = Calendar.getInstance();
        } else {
            calendar = Calendar.getInstance(timeZone);
        }
        calendar.setTime(date);
        return calendar;
    }

    /**
     * 将Calendar对象转换为Date对象
     *
     * @param calendar 需要转换的Calendar对象
     * @return 转换后的Date对象
     */
    public static Date calendar2Date(@NonNull Calendar calendar) {
        return calendar.getTime();
    }

    /**
     * 根据Date对象获取对应的描述：凌晨、早上、中午、下午、晚上
     *
     * @param date 用来判断的Date对象
     * @return 描述：凌晨、早上、中午、下午、晚上,失败时返回""
     */
    public static String date2CN(@NonNull Date date) {
        String strDate = date2Str(date, "HH").trim();
        int hour;
        try {
            hour = Integer.parseInt(strDate);
        } catch (NumberFormatException e) {
            LogUtils.e(TAG, e.getMessage());
            return "";
        }
        if (hour < 6) {
            return "凌晨";
        } else if (hour >= 6 && hour < 12) {
            return "早上";
        } else if (hour == 12) {
            return "中午";
        } else if (hour > 12 && hour < 18) {
            return "下午";
        } else {
            return "晚上";
        }
    }

    /**
     * 判断srcDate时间是否在startDate和endDate之间
     *
     * @param srcDate   需要判断的时间
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return true：srcDate时间在startDate和endDate之间，false：srcDate时间不在startDate和endDate之间
     */
    public static boolean between(@NonNull Date srcDate, @NonNull Date startDate, @NonNull Date endDate) {
        if (startDate.after(srcDate)) {
            return false;
        } else if (endDate.before(srcDate)) {
            return false;
        }
        return true;
    }

    /**
     * 检查两个Date对象是否是同一天
     *
     * @param date1 first Date
     * @param date2 second Date
     * @return true：同一天，false不是同一天
     */
    @SuppressWarnings("ConstantConditions")
    public static boolean isSameDay(@NonNull Date date1, @NonNull Date date2) {
        if (null == date1 || null == date2) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar calendar1 = getCalendar();
        calendar1.setTime(date1);

        Calendar calendar2 = getCalendar();
        calendar2.setTime(date2);

        return isSameDay(calendar1, calendar2);
    }

    /**
     * 检查两个Calendar对象是否是同一天，忽略时间
     *
     * @param calendar1 first Calendar
     * @param calendar2 second Calendar
     * @return true：同一天，false不是同一天
     */
    @SuppressWarnings("ConstantConditions")
    public static boolean isSameDay(@NonNull Calendar calendar1, @NonNull Calendar calendar2) {
        if (null == calendar1 || null == calendar2) {
            throw new IllegalArgumentException("The date must not be null");
        }
        return calendar1.get(Calendar.ERA) == calendar2.get(Calendar.ERA) &&
                calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) &&
                calendar1.get(Calendar.DAY_OF_YEAR) == calendar2.get(Calendar.DAY_OF_YEAR);
    }

    /**
     * 检查两个Date对象是否是同一瞬时值,这种方法比较的是两个对象的毫秒值
     *
     * @param date1 first Date
     * @param date2 second Date
     * @return true：同一瞬时值，false不是同一瞬时值
     */
    @SuppressWarnings("ConstantConditions")
    public static boolean isSameInstant(@NonNull Date date1, @NonNull Date date2) {
        if (null == date1 || null == date2) {
            throw new IllegalArgumentException("The date must not be null");
        }
        return date1.getTime() == date2.getTime();
    }

    /**
     * 检查两个Calendar对象是否是同一瞬时值,这种方法比较的是两个对象的毫秒值
     *
     * @param calendar1 first Calendar
     * @param calendar2 second Calendar
     * @return true：同一瞬时值，false不是同一瞬时值
     */
    @SuppressWarnings("ConstantConditions")
    public static boolean isSameInstant(@NonNull Calendar calendar1, @NonNull Calendar calendar2) {
        if (null == calendar1 || null == calendar2) {
            throw new IllegalArgumentException("The date must not be null");
        }
        return calendar1.getTime().getTime() == calendar2.getTime().getTime();
    }

    /**
     * 计算时间差
     *
     * @param time 需要计算的时间
     * @return 时间差
     * 注：不足unit单位时返回0
     */
    public static String howLong(@NonNull String time) {

        if (TextUtils.isEmpty(time)) {
            throw new IllegalArgumentException("time can not empty");
        }
        long mTime = System.currentTimeMillis() - str2Millisecond(time);
        String mData;
        if (mTime >= 10 * MILLISECOND_IN_DAY) {
            mData = millisecond2Str(str2Millisecond(time), "M月d日");
        } else if (mTime >= MILLISECOND_IN_DAY) {
            mData = mTime / MILLISECOND_IN_DAY + "天前";
        } else if (mTime >= MILLISECOND_IN_HOUR) {
            mData = mTime / MILLISECOND_IN_HOUR + "小时前";
        } else if (mTime >= MILLISECOND_IN_MINUTE) {
            mData = mTime / MILLISECOND_IN_MINUTE + "分钟前";
        } else if (mTime >= 5 * MILLISECOND_IN_SECOND) {
            mData = mTime / MILLISECOND_IN_SECOND + "h秒前";
        } else {
            mData = "刚刚";
        }
        return mData;
    }

    /**
     * 计算时间差
     *
     * @param time 需要计算的时间
     * @return 时间差
     * 注：不足unit单位时返回0
     */
    public static String howLong(@NonNull String time, String format) {

        if (TextUtils.isEmpty(time)) {
            throw new IllegalArgumentException("time can not empty");
        }
        long mTime = System.currentTimeMillis() - str2Millisecond(time, format);
        String mData;
        if (mTime >= 10 * MILLISECOND_IN_DAY) {
            mData = millisecond2Str(str2Millisecond(time), "M月d日");
        } else if (mTime >= MILLISECOND_IN_DAY) {
            mData = mTime / MILLISECOND_IN_DAY + "天前";
        } else if (mTime >= MILLISECOND_IN_HOUR) {
            mData = mTime / MILLISECOND_IN_HOUR + "小时前";
        } else if (mTime >= MILLISECOND_IN_MINUTE) {
            mData = mTime / MILLISECOND_IN_MINUTE + "分钟前";
        } else if (mTime >= 5 * MILLISECOND_IN_SECOND) {
            mData = mTime / MILLISECOND_IN_SECOND + "h秒前";
        } else {
            mData = "刚刚";
        }
        return mData;
    }

    /**
     * 计算时长
     *
     * @param duration 时长毫秒值
     * @return 时长
     */
    public static String computerDuration(long duration) {
        if (0 > duration) {
            return "";
        }
        duration /= 1000;
        long day = duration / (24 * 60 * 60);
        long hour = duration / (60 * 60) % 24;
        long minute = duration / 60 % 60;
        long second = duration % 60;
        StringBuilder sb = new StringBuilder();
        if (day >= 1) {
            sb.append(day).append("天");
        }
        if (hour >= 1) {
            sb.append(hour).append("小时");
        }
        if (minute >= 1) {
            sb.append(minute).append("分钟");
        }
        if (second >= 1) {
            sb.append(second).append("秒");
        }
        if (sb.length() > 0) {
            return sb.toString();
        } else {
            return "";
        }
    }

    /**
     * 计算时间差
     *
     * @param unit  返回的时间单位
     * @param time1 需要计算的时间
     * @param time2 需要计算的时间
     * @return 时间差
     * 注：不足unit单位时返回0
     */
    public static long howLong(@NonNull @UNIT String unit, @NonNull String time1, @NonNull String time2) {
        if (TextUtils.isEmpty(unit)) {
            throw new IllegalArgumentException("unit can not empty");
        }
        if (TextUtils.isEmpty(time1) || TextUtils.isEmpty(time2)) {
            throw new IllegalArgumentException("time can not empty");
        }
        Date date1 = str2Date(time1);
        Date date2 = str2Date(time2);
        long time = Math.abs(date1.getTime() - date2.getTime());
        if (TIME_UNIT_SECOND.equals(unit)) {//返回秒
            return time / MILLISECOND_IN_SECOND;
        } else if (TIME_UNIT_MINUTE.equals(unit)) {//返回分钟
            return time / MILLISECOND_IN_MINUTE;
        } else if (TIME_UNIT_HOUR.equals(unit)) {//返回小时
            return time / MILLISECOND_IN_HOUR;
        } else if (TIME_UNIT_DAY.equals(unit)) {//返回天数
            return time / MILLISECOND_IN_DAY;
        } else {
            return 0;
        }
    }

    /**
     * 计算两个日期的差， 返回如"1年2月3天11.5小时"的字符串
     *
     * @param date1 需要计算时间差的一个日期
     * @param date2 需要计算时间差的一个日期
     * @return 时间差, eg:"1年2月3天11.5小时"的字符串
     */
    public static String getIntervalString(Date date1, Date date2) {
        StringBuilder sb = new StringBuilder();

        Calendar calendar1 = getCalendar();
        calendar1.setTime(date1);

        Calendar calendar2 = getCalendar();
        calendar2.setTime(date2);

        float hour;
        int day;
        int month = 0;
        int year = 0;

        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMaximumFractionDigits(1);//保留1位小数

        //计算小时
        hour = (Math.abs(calendar1.getTimeInMillis() - calendar2.getTimeInMillis()) % MILLISECOND_IN_DAY / (float) MILLISECOND_IN_HOUR);
        //计算天
        day = calendar1.get(Calendar.DAY_OF_MONTH) - calendar2.get(Calendar.DAY_OF_MONTH);
        if (day < 0) {
            day += getDaysOfMonth(calendar1.get(Calendar.YEAR), calendar1.get(Calendar.MONTH));
            month--;
        }
        //计算月
        month += calendar1.get(Calendar.MONTH) - calendar2.get(Calendar.MONTH);
        if (month < 0) {
            month += 12;
            year--;
        }
        //计算年
        year += calendar1.get(Calendar.YEAR) - calendar2.get(Calendar.YEAR);

        if (year > 0) {
            sb.append(year).append("年");
        }
        if (month > 0) {
            sb.append(month).append("月");
        }
        if (day > 0) {
            sb.append(day).append("天");
        }
        if (hour > 0) {
            sb.append(hour).append("小时");
        }
        return sb.toString().trim();
    }

    /**
     * 根据year和month获取某年某月的天数
     *
     * @param year  年
     * @param month 月
     * @return year年month月的天数
     */
    public static int getDaysOfMonth(int year, int month) {
        int day = 0;
        if (1 == month || 3 == month || 5 == month || 7 == month || 8 == month || 10 == month || 12 == month) {
            return 31;
        } else if (4 == month || 6 == month || 9 == month || 11 == month) {
            return 30;
        } else if (2 == month) {
            if (0 == year % 400 || (0 == year % 4 && 0 != year % 100)) {
                return 29;
            } else {
                return 28;
            }
        } else {
            return 0;
        }
    }

    /**
     * 在date时间基础上增加amount年,原date不变
     *
     * @param date   需要增加amount年的date时间
     * @param amount 增加amount年
     * @return 增加amount年后的date时间
     */
    public static Date addYears(@NonNull Date date, int amount) {
        return add(date, Calendar.YEAR, amount);
    }

    /**
     * 在date时间基础上增加amount月,原date不变
     *
     * @param date   需要增加amount月的date时间
     * @param amount 增加amount月
     * @return 增加amount月后的date时间
     */
    public static Date addMonths(@NonNull Date date, int amount) {
        return add(date, Calendar.MONTH, amount);
    }

    /**
     * 在date时间基础上增加amount周,原date不变
     *
     * @param date   需要增加amount周的date时间
     * @param amount 增加amount周
     * @return 增加amount周后的date时间
     */
    public static Date addWeeks(@NonNull Date date, int amount) {
        return add(date, Calendar.WEEK_OF_YEAR, amount);
    }

    /**
     * 在date时间基础上增加amount天,原date不变
     *
     * @param date   需要增加amount天的date时间
     * @param amount 增加amount天
     * @return 增加amount天后的date时间
     */
    public static Date addDays(@NonNull Date date, int amount) {
        return add(date, Calendar.DAY_OF_MONTH, amount);
    }

    /**
     * 在date时间基础上增加amount小时,原date不变
     *
     * @param date   需要增加amount小时的date时间
     * @param amount 增加amount小时
     * @return 增加amount小时后的date时间
     */
    public static Date addHours(@NonNull Date date, int amount) {
        return add(date, Calendar.HOUR_OF_DAY, amount);
    }

    /**
     * 在date时间基础上增加amount分钟,原date不变
     *
     * @param date   需要增加amount分钟的date时间
     * @param amount 增加amount分钟
     * @return 增加amount分钟后的date时间
     */
    public static Date addMinutes(@NonNull Date date, int amount) {
        return add(date, Calendar.MINUTE, amount);
    }

    /**
     * 在date时间基础上增加amount秒,原date不变
     *
     * @param date   需要增加amount秒的date时间
     * @param amount 增加amount秒
     * @return 增加amount秒后的date时间
     */
    public static Date addSeconds(@NonNull Date date, int amount) {
        return add(date, Calendar.SECOND, amount);
    }

    /**
     * 在date时间基础上增加amount毫秒,原date不变
     *
     * @param date   需要增加amount毫秒的date时间
     * @param amount 增加amount毫秒
     * @return 增加amount毫秒后的date时间
     */
    public static Date addMilliseconds(@NonNull Date date, int amount) {
        return add(date, Calendar.MILLISECOND, amount);
    }

    /**
     * 增加date，返回一个新的date对象，原date不变
     *
     * @param date          需要增加的date
     * @param calendarField Calendar字段
     * @param amount        增加的总数，可以是负的
     * @return 增加后的新的date对象
     */
    @SuppressWarnings("ConstantConditions")
    private static Date add(@NonNull Date date, int calendarField, int amount) {
        if (null == date) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar calendar = getCalendar();
        calendar.setTime(date);
        calendar.add(calendarField, amount);
        return calendar.getTime();
    }

    /**
     * 对date对象的年份字段设置value值，返回一个新的date对象，原date不变
     *
     * @param date  需要设置的date
     * @param value 需要设置的年份
     * @return 设置年份字段后返回的新的Date对象
     */
    private static Date setYears(@NonNull Date date, int value) {
        return set(date, Calendar.YEAR, value);
    }

    /**
     * 对date对象的月字段设置value值，返回一个新的date对象，原date不变
     *
     * @param date  需要设置的date
     * @param value 需要设置的月
     * @return 设置月字段后返回的新的Date对象
     */
    private static Date setMonths(@NonNull Date date, int value) {
        return set(date, Calendar.MONTH, value);
    }

    /**
     * 对date对象的天字段设置value值，返回一个新的date对象，原date不变
     *
     * @param date  需要设置的date
     * @param value 需要设置的天
     * @return 设置天字段后返回的新的Date对象
     */
    private static Date setDays(@NonNull Date date, int value) {
        return set(date, Calendar.DAY_OF_MONTH, value);
    }

    /**
     * 对date对象的小时字段设置value值，返回一个新的date对象，原date不变
     *
     * @param date  需要设置的date
     * @param value 需要设置的小时
     * @return 设置小时字段后返回的新的Date对象
     */
    private static Date setHours(@NonNull Date date, int value) {
        return set(date, Calendar.HOUR_OF_DAY, value);
    }

    /**
     * 对date对象的分钟字段设置value值，返回一个新的date对象，原date不变
     *
     * @param date  需要设置的date
     * @param value 需要设置的分钟
     * @return 设置分钟字段后返回的新的Date对象
     */
    private static Date setMinutes(@NonNull Date date, int value) {
        return set(date, Calendar.MINUTE, value);
    }

    /**
     * 对date对象的秒字段设置value值，返回一个新的date对象，原date不变
     *
     * @param date  需要设置的date
     * @param value 需要设置的秒值
     * @return 设置秒字段后返回的新的Date对象
     */
    private static Date setSeconds(@NonNull Date date, int value) {
        return set(date, Calendar.SECOND, value);
    }

    /**
     * 对date对象的毫秒值字段设置value值，返回一个新的date对象，原date不变
     *
     * @param date  需要设置的date
     * @param value 需要设置的毫秒值
     * @return 设置毫秒值字段后返回的新的Date对象
     */
    private static Date setMilliseconds(@NonNull Date date, int value) {
        return set(date, Calendar.MILLISECOND, value);
    }

    /**
     * 对date对象的calendarField字段设置value值，返回一个新的date对象，原date不变
     *
     * @param date          需要设置的date
     * @param calendarField Calendar字段
     * @param value         calendarField字段需要设置的值
     * @return 设置calendarField字段后返回的新的Date对象
     */
    @SuppressWarnings("ConstantConditions")
    private static Date set(@NonNull Date date, int calendarField, int value) {
        if (null == date) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar calendar = getCalendar();
        calendar.setTime(date);
        calendar.set(calendarField, value);
        return calendar.getTime();
    }

    /**
     * 获取SimpleDateFormat实例
     *
     * @param pattern 格式化模板,默认为 DEFAULT_PATTERN
     * @param locale  区域,默认为 DEFAULT_LOCALE
     * @return SimpleDateFormat实例
     */
    private static SimpleDateFormat getSimpleDateFormat(String pattern, Locale locale) {
        if (TextUtils.isEmpty(pattern)) {
            pattern = DEFAULT_PATTERN;
        }
        if (null == locale) {
            locale = DEFAULT_LOCALE;
        }
        return new SimpleDateFormat(pattern, locale);
    }

    public static Calendar getCalendar() {
        return Calendar.getInstance();
    }

    public static Calendar getCalendar(final TimeZone timeZone, final Locale locale) {
        return Calendar.getInstance(timeZone, locale);
    }

}
