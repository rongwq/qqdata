package com.rong.user.service;
import com.jfinal.plugin.activerecord.Page;
import com.rong.persist.base.BaseService;
import com.rong.persist.model.User;

/**
 * 会员用户业务接口
 * @author Wenqiang-Rong
 * @date 2017年12月21日
 */
public interface UserService extends BaseService<User>{
	//管理后台方法
	Page<User> getUserList(int page,int pageSize,String userName,String mobile);
	boolean resetPassword(long id, String md5);
	boolean setEnable(long id, boolean isEnable);

}