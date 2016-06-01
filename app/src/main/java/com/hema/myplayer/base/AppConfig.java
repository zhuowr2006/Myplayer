package com.hema.myplayer.base;

import android.os.Environment;

import java.io.File;

/**
 * @ClassName: AppConfig
 * @Description: 应用程序的根配置，比如版本，目录配置等等。
 * @date 2014-3-18 下午2:18:59
 */
public class AppConfig {
	private final static String APP_CONFIG = "config";

	// APP内部版本号
	public final static int APP_VERSION_CODE = 5;// 这个要重新写
	// APP版本名称
	public final static String APP_VERSION_NAME = "2.2";
	// APP名称
	public static final String APP_NAME_DESC = "深圳社保";
	// APP名称
	public static final String APP_NAME = "fortunes-szsb-client";

	public final static String DB_NAME = "_fortunesszsb.db";// 数据库名字
	public final static int DB_VERSION = 1;// 数据库版本号

	// 是否打印Log信息 true打印 false不打印
	public final static boolean isDebug = true;
	// 验证成功后跳转到验证记录的提醒重新刷新
	public static boolean JUMP_AND_REMIND_REFRESH = false;
	// 验证成功后跳转到验证记录的提醒重新刷新
	public static boolean JUMP_AND_REMIND_REFRESH2 = false;
	// 清除请求日期
	public static boolean isCleanDataFlag = false;
	// 清除活动ID
	public static boolean isCleanActivityFlag = true;
	public static String ISNEEDREFRASH = "isNeedRefrash";
	/**
	 * PreferencesUtils用到的
	 */
	// PreferencesUtils
	public final static String APP_PREFERENCE_NAME = "fortunes-szsb-client";
	// 用户名
	public final static String USER_NAME = "username";
	// 用户的个人电脑号或身份证号
	public final static String USER_ID = "userid";
	// 用户验证码
	public final static String USER_CODE = "usercode";

	// 验证码
	public final static String CODE = "123456";

	// 完成支付页面标记
	public static String PAY_WHERE = "where";

	// // 是否记住登录状态
	// public final static String USER_CHECKBOX = "userCheckbox";

	/***
	 * SDCard路径
	 */
	public static final String SDCARD_Path = Environment
			.getExternalStorageDirectory().getAbsolutePath();

	/**
	 * 程序目录路径
	 */
	public static final String YOUJIAO_PATH = SDCARD_Path + File.separator
			+ "fortunes";
	public static final String YOUXING_PATH = YOUJIAO_PATH + File.separator
			+ "szsb";
	/**
	 * 图片目录路径
	 */
	public static final String YOUJIAOPIC_PATH = YOUXING_PATH + File.separator
			+ "pic";

}
