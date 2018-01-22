package com.rong.common.bean;
/**
 * 错误码
 * @author rongwq
 *
 */
public class MyErrorCodeConfig {
	// 错误代码
	public static final String ERROR_TOKEN_MISS = "301"; // token不正确
	public static final String ERROR_TOKEN_EXPIRE = "302"; // token过期
	public static final String ERROR_BAD_REQUEST = "400"; // 参数 不正确
	public static final String ERROR_FAIL = "500"; // 请求异常
	public static final String REQUEST_SUCCESS = "1"; // 请求成功
	public static final String REQUEST_FAIL = "0"; // 请求失败
	
	public static final String USER_NOT_EXIST = "USER_NOT_EXIST"; // 用户不存在
	public static final String ACCOUNT_SMS_NOT_RIGHT = "SMS_NOT_RIGHT"; // 验证码不正确
	public static final String ACCOUNT_SMS_CODE_EXPIRED = "SMS_CODE_EXPIRED"; // 验证码过期

}
