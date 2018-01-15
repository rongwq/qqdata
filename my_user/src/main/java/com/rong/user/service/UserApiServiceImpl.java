package com.rong.user.service;

import java.sql.Timestamp;
import java.util.Date;

import com.jfinal.plugin.redis.Redis;
import com.rong.common.bean.MyConst;
import com.rong.common.bean.MyErrorCodeConfig;
import com.rong.common.bean.RedisKeyConst;
import com.rong.common.util.CommonUtil;
import com.rong.common.util.StringUtils;
import com.rong.persist.base.BaseServiceImpl;
import com.rong.persist.dao.UserDao;
import com.rong.persist.dao.UserTokenDao;
import com.rong.persist.model.User;

/**
 * 用户服务实现层
 * @author Wenqiang-Rong
 * @date 2017年12月21日
 */
public class UserApiServiceImpl extends BaseServiceImpl<User> implements UserApiService{
	private UserDao dao = new UserDao();
	private UserTokenDao userTokenDao = new UserTokenDao();
	private UserPwderrorStatisService pwderrorCountSevice = new UserPwderrorStatisServiceImpl();
	@Override
	public User getUserByMobile(String mobile) {
		User user=null;
		user=dao.getUserByMobile(mobile);
		return user;
	}

	@Override
	public User getUserByOpenid(String openid) {
		User user = dao.getUserByOpenid(openid);
		return user;
	}

	@Override
	public int userInfoSave(User user) {
		int id = 0;
		try {
			final String plainPwd = user.getStr("userPassword");
			if(null != plainPwd && !"".equals(plainPwd)){
				user.set("userPassword", CommonUtil.getMD5(plainPwd));
			}
			Timestamp timestamp = new Timestamp(new Date().getTime());
			user.set("registerDate", timestamp);
			boolean s1 = user.save();
			if (s1) {
				id = user.get("id");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}
	
	@Override
	public User getByMobileAndPassword(String mobile, String password) {
		User user=null;
		try {
			String decrptyPwd = "";
			if (password.length() < 16) {// 加密
				decrptyPwd = CommonUtil.getMD5(password);
			}
			user = dao.getByMobileAndPassword(mobile, decrptyPwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	/**
	 * 设置支付密码
	 * @param mobile
	* @param payPassword
	* @return
	 */
	@Override
	public boolean setUserPayPassword(String mobile,String payPassword){
		User user = this.getUserByMobile(mobile);
		user.setPayPassword(User.getMD5(payPassword.getBytes()));
		return user.update();
	}

	@Override
	public boolean updateUserHead(long userId, String userHead) {
		User user = getUserById(userId);
		user.setUserHead(userHead);
		return user.update();
	}

	@Override
	public boolean updateMobile(long userId, String updateMobile) {
		userTokenDao.updateMobileByUserId(userId, updateMobile);
		return dao.updateField(userId, "mobile", updateMobile);
	}

	@Override
	public User getUserById(long userId) {
		User user=dao.getUserById(userId);
		return user;
	}
	
	

	@Override
	public boolean updateUserName(String mobile, String userName) {
		User user = this.getUserByMobile(mobile);
		user.setNickName(userName);
		return user.update();
	}

	/**
	 * 检验支付密码
	 * 
	 * @param userId      用户ID
	 * @param payPassword 支付密码 传值进来是未MD5加密的
	 */
	@Override
	public String checkPayPassword(long userId, String payPassword) {
		//1、判断支付密码是否为空
		if (StringUtils.isNullOrEmpty(payPassword)) {
			return MyErrorCodeConfig.ERROR_PAYPASSWORD;//支付密码错误
		}
		User us = UserDao.dao.findById(userId);
		//是否设置了支付密码
		if (null == us.getPayPassword()) {
			return MyErrorCodeConfig.ERROR_NOTSETPAYPASSWORD;//没有设置支付密码
		} else {
			//3、判断错误情况
			//判断支付密码是否正确
			if (!CommonUtil.getMD5(payPassword).equals(us.getPayPassword())) {
				//判断是否达到最大的错误次数
				Boolean errorCountResult = pwderrorCountSevice.isMaxPayPwdError(userId);
				if (errorCountResult) {
					return MyErrorCodeConfig.ERROR_PAYPASSWORD_LOCKED;//支付密码已被锁定
				}
				//保存错误次数信息
				int errorCount = pwderrorCountSevice.savePayPwdError(userId);
				//获取最大错误次数
				int max = Integer.parseInt(String.valueOf(Redis.use().hget(RedisKeyConst.SYSCONFMAP, MyConst.payPwderrorCount)));
				if (max == errorCount) {
					return MyErrorCodeConfig.ERROR_PAYPASSWORD_LOCKED;//已经被锁定
				}
				return MyErrorCodeConfig.ERROR_PAYPASSWORD;//支付密码错误
			}
		}
		//4、支付密码正确时则清理之前的错误记录
		pwderrorCountSevice.clearPayPwdError(userId);
		return MyErrorCodeConfig.REQUEST_SUCCESS;
	}

	@Override
	public User getUserByCode(String code) {
		User user=dao.getUserByCode(code);
		return user;
	}

	@Override
	public boolean deleteUserById(long userId) {
		return dao.deleteUserById(userId);
	}

}
