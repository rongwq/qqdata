package com.rong.user.service;

import java.util.Date;

import com.jfinal.plugin.redis.Redis;
import com.rong.common.bean.MyConst;
import com.rong.common.bean.RedisKeyConst;
import com.rong.persist.base.BaseServiceImpl;
import com.rong.persist.dao.UserPwderrorStatisDao;
import com.rong.persist.model.UserPwderrorStatis;

public class UserPwderrorStatisServiceImpl extends BaseServiceImpl<UserPwderrorStatis> implements UserPwderrorStatisService {
	
	private UserPwderrorStatisDao dao=new UserPwderrorStatisDao();
	
	@Override
	public boolean clearLoginPwdError(long userId) {
		UserPwderrorStatis  pwderrorCount = dao.getByUserId(userId);
		if(pwderrorCount==null){
			return true;
		}
		if(pwderrorCount.getLoginPwderrorCount()==0){
			return true;
		}
		pwderrorCount.setLoginPwderrorCount(0);
		return pwderrorCount.update();
	}
	
	@Override
	public int saveLoginPwdError(long userId) {
		UserPwderrorStatis  pwderrorCount = dao.getByUserId(userId);
		if(pwderrorCount==null){
			pwderrorCount = new UserPwderrorStatis();
			pwderrorCount.setUserId(userId);
			pwderrorCount.setLoginPwderrorCount(1);
			pwderrorCount.setPayPwderrorCount(0);
			pwderrorCount.setCreateTime(new Date());
			pwderrorCount.save();
		}else{
			pwderrorCount.setLoginPwderrorCount(pwderrorCount.getLoginPwderrorCount()+1);
			pwderrorCount.setUpdateTime(new Date());
			pwderrorCount.update();
		}
		return pwderrorCount.getLoginPwderrorCount();
	}
	
	@Override
	public boolean isMaxLoginPwdError(long userId) {
		int loginPwdErrorCount = getLoginPwderrorCount(userId);
		int max = Integer.parseInt(Redis.use().hget(RedisKeyConst.SYSCONFMAP, MyConst.loginPwderrorCount).toString());
		if(loginPwdErrorCount<max){
			return false;
		}
		return true;
	}
	
	public int getLoginPwderrorCount(long userId) {
		UserPwderrorStatis  pwderrorCount = dao.getByUserId(userId);
		if(pwderrorCount==null){
			return 0;
		}
		return pwderrorCount.getLoginPwderrorCount();
	}
	
	/**
	 * 判断支付密码是否错误次数达到最大值
	 * @param userId
	 * @return
	 */
	@Override
	public boolean isMaxPayPwdError(long userId) {
		UserPwderrorStatis userPwderrorStatis = dao.getByUserId(userId);
		if (userPwderrorStatis == null) {
			return false;
		}
		Integer payPwdErrorCount = userPwderrorStatis.getPayPwderrorCount();
		Integer max = Integer.parseInt(Redis.use().hget(RedisKeyConst.SYSCONFMAP, MyConst.payPwderrorCount).toString());
		if (payPwdErrorCount < max) {
			return false;
		}
		return true;
	}

	/**
	 *  保存用户支付密码错误信息
	 */
	@Override
	public Integer savePayPwdError(long userId) {
		UserPwderrorStatis userPwderrorStatis = dao.getByUserId(userId);
		if (userPwderrorStatis == null) {
			userPwderrorStatis = new UserPwderrorStatis();
			userPwderrorStatis.setUserId(userId);
			userPwderrorStatis.setPayPwderrorCount(1);
			userPwderrorStatis.setLoginPwderrorCount(0);
			userPwderrorStatis.setCreateTime(new Date());
			userPwderrorStatis.save();
		} else {
			userPwderrorStatis.setPayPwderrorCount(userPwderrorStatis.getPayPwderrorCount() + 1);
			userPwderrorStatis.setUpdateTime(new Date());
			userPwderrorStatis.update();
		}
		return userPwderrorStatis.getPayPwderrorCount();
	}

	/**
	 * 清除支付密码错误记录
	 */
	@Override
	public void clearPayPwdError(long userId) {
		UserPwderrorStatis userPwderrorStatis = dao.getByUserId(userId);
		if (userPwderrorStatis != null && userPwderrorStatis.getPayPwderrorCount() != 0) {
			userPwderrorStatis.setPayPwderrorCount(0);
			userPwderrorStatis.update();
		}
	}
	/**
	 * 0点清除全部错误登录信息
	 */
	@Override
	public void delete(){
		dao.delete();
	}
}
