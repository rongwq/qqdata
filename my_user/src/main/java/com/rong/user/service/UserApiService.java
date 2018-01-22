package com.rong.user.service;

import com.rong.persist.base.BaseService;
import com.rong.persist.model.User;

/**
 * 用户服务接口层
 * @author Wenqiang-Rong
 * @date 2017年12月21日
 */
public interface UserApiService extends BaseService<User>{
	public User getUserByMobile(String mobile);
	public User getUserById(long userId);
	public User getUserByOpenid(String openid);
	public int userInfoSave(User user);
	public User getByMobileAndPassword(String mobile, String password) ;
	public boolean setUserPayPassword(String mobile, String payPassword);
	public boolean updateUserHead(long userId,String userHead) ;
	public boolean updateMobile(long userId,String updateMobile) ;
	public boolean updateUserName(String mobile, String userName);
	public User getUserByCode(String code);
	public boolean deleteUserById(long userId);

}

