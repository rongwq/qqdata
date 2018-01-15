package com.rong.persist.dao;

import java.util.Date;

import com.rong.common.bean.MyConst;
import com.rong.common.util.GenerateSequenceUtil;
import com.rong.persist.base.BaseDao;
import com.rong.persist.model.User;
import com.rong.persist.model.UserToken;

/**
 * 用户token管理
 * 
 * @author Wenqiang-Rong
 * @date 2017年12月20日
 */
public class UserTokenDao extends BaseDao<UserToken> {
	public static final UserToken dao = UserToken.dao;
	
	public static final String FILEDS = "id,user_id,mobile,token,is_expir,expir_time,create_time,update_time";

	/**
	 * 获取最新一条token,有缓存
	 * 
	 * @return
	 */
	public UserToken getByUserId(long userId) {
		String sql = "SELECT * FROM " + UserToken.TABLE + " WHERE user_id = ? AND is_expir = 0 ORDER BY id DESC";
		return dao.findFirst(sql, userId);
	}

	public String saveToken(User user) {
		String token = GenerateSequenceUtil.generateSequenceNo();
		UserToken userToken = new UserToken();
		userToken.setUserId(user.getId());
		userToken.setMobile(user.getMobile());
		userToken.setToken(token);
		userToken.setCreateTime(new Date());
		userToken.setExpirTime(new Date(System.currentTimeMillis() + MyConst.TOKEN_EXPIR_TIME * 1000));
		userToken.setIsExpir(false);
		userToken.save();
		return token;
	}

	/**
	 * 根据UserId删除token
	 */
	public boolean delAllByUserId(Integer userId) {
		UserToken userToken = getByUserId(userId);
		return update(userToken);
	}

	/**
	 * 判断是否有效
	 * 
	 * @param userId
	 * @param token
	 * @return
	 */
	public boolean checkIsLoginCache(Integer userId, String token) {
		UserToken userToken = getByUserId(userId);
		if (null == userToken)
			return false;
		boolean isNotExpir = System.currentTimeMillis() < userToken.getExpirTime().getTime()
				&& (!userToken.getIsExpir());
		if (isNotExpir && token.equals(userToken.getToken())) {
			return true;
		} else {
			return false;
		}
	}

	public boolean updateMobileByUserId(long userId, String mobile) {
		UserToken userToken = getByUserId(userId);
		userToken.setMobile(mobile);
		return update(userToken);
	}
}
