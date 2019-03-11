package com.rong.common.bean;

import com.rong.common.util.PropertiesUtils;

/**
 * 系统参数配置、常量配置
 * @author Wenqiang-Rong
 *
 */
public class MyConst {
	/** ~~~~~~~~~~~~~~~~~~~~系统参数配置~~~~~~~~~~~~~~~~~~~~ */
	public static final String SESSION_KEY = "session_login";
	// 极光推送配置
	public static String jpush_appkey;
	public static String jpush_masterSecret;
	public static boolean apnsProduction = false;// 极光推送是否开启生产环境

	// ftp上传
	public static String ftp_host;
	public static Integer ftp_port;
	public static String ftp_username;
	public static String ftp_pwd;
	public static String ftp_uploads;
	public static String ftp_files;
	public static String ftp_ueditor;

	public static String version = PropertiesUtils.get("version", "1.0.0.20160620_beta");
	public static String imgUrlHead;
	public static String upload; // FTP 上传目录
	public static String ueditorHead;
	public static boolean devMode = false;
	public static int RUNNING_MODE = Integer.parseInt(PropertiesUtils.get("RUNNING_MODE", "1")); // 当前模式

	/** ~~~~~~~~~~~~~~~~~~~~常量配置~~~~~~~~~~~~~~~~~~~~ */
	// 用户管理
	public static final int USERSTATUS_DISABLE = 0;// 禁用
	public static final int USERSTATUS_ENABLE = 1;// 正常

	// 系统配置
	public static final int RUNNING_MODE_DEV_SERVER = 1; // 开发服务器
	public static final int RUNNING_MODE_TEST_SERVER = 2; // 测试服务器
	public static final int RUNNING_MODE_ONLINE_SERVER = 3; // 正式环境服务器
	public static final long TOKEN_EXPIR_TIME = 2592000; // 超时时间 单位秒
	public static final int thum_width = 400;
	public static final int loginPwderrorCount = 5;
	public static final int payPwderrorCount = 5;

	// 对密码格式的限制
	public static final String REGEX = "(?!^\\d+$)(?!^[a-zA-Z]+$)[0-9a-zA-Z]{6,12}";
	public static final String DEFAULT_PASSWORD = "123456";
	
	// 用户管理
	public static final int QQSTATE_DISABLE = 0;// 禁用
	public static final int QQSTATE_ENABLE = 1;// 正常
	public static final int QQSTATE_DISABLE_FOREVER = 2;// 永久冻结

}
