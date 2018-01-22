package com.rong.user.service;

import java.sql.Timestamp;
import java.util.Date;

import com.rong.common.util.CommonUtil;
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
