package com.rong.user.service;

import com.rong.persist.base.BaseService;
import com.rong.persist.model.UserPwderrorStatis;

/**
 * 用户登录密码和支付密码错误次数统计
 * @author Wenqiang-Rong
 * @date 2017年12月21日
 */
public interface UserPwderrorStatisService extends BaseService<UserPwderrorStatis>{
	public boolean clearLoginPwdError(long userId);
	public int saveLoginPwdError(long userId);
	public boolean isMaxLoginPwdError(long userId);
	public boolean isMaxPayPwdError(long userId);
	public Integer savePayPwdError(long userId);
	public void clearPayPwdError(long userId);
	public void delete();
}
