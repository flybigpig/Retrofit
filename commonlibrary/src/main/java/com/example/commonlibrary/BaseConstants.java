package com.example.commonlibrary;


/**
 *  2017/3/9  15:04.
 *
 *
 * @version 1.0.0
 * @class BaseConstants
 * @describe 公共的静态常量类
 */
public class BaseConstants {

    /**
     * 文件夹路径
     */
    public static String IMAGE_PATH;//图片文件夹
    public static String VIDEO_PATH;//视频文件夹
    public static String DOWNLOAD_PATH;//下载文件夹
    public static String CRASH_PATH;//日志文件夹

    /**
     * 请求码
     */
    public static final int REQUEST_CODE = 0x01;   //扫描？
    public static final int CAMERE_REQUEST_CODE = 0x02; //相机
    public static final int PHOTO_REQUEST_CODE = 0x03; //相册
    public static final int PCROP_REQUEST_CODE = 0x04; //裁剪
    public static final int CURRENCY_REQUEST_CODE = 0x05; //币种
    public static final int ADDRESS_REQUEST_CODE = 0x06; //地址
    public static final int TRANSCATION_PERMISSION_REQUEST=0x07 ; //交易请求

    /**
     * 返回码
     */
    public static final int RESULT_CODE = 0x01;

    /**
     * 账号,SharedPreferences存储Key
     */
    public static final String ACCOUNT = "account";
    /**
     * 密码,SharedPreferences存储Key
     */
    public static final String PASSWORD = "password";
    /**
     * 不是第一次加载引导页,SharedPreferences存储Key
     */
    public static final String LEAD_KEY = "lead_key";

    /**
     * 轮播图间隔时间
     */
    public static final int BANNER_DELAY_TIME = 5000;

}
