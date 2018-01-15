package com.rong.user.service;

import com.jfinal.plugin.activerecord.Page;
import com.rong.common.bean.MyConst;
import com.rong.persist.base.BaseServiceImpl;
import com.rong.persist.dao.UserDao;
import com.rong.persist.model.User;

/**
 * 会员用户业务实现类
 * @author Wenqiang-Rong
 * @date 2017年12月21日
 */
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService{
	private UserDao dao = new UserDao();
	@Override
	public Page<User> getUserList(int page,int pagesize,String userName,String mobile) {
		return dao.getUserList(page,pagesize,userName,mobile);
	}
	
	public boolean setState(long id, int userstate){
		return dao.updateField(id, "state", userstate);
	}
	
	
	public boolean setEnable(long id, boolean isEnable){
	
		return setState(id,  isEnable ? MyConst.USERSTATUS_ENABLE : MyConst.USERSTATUS_DISABLE);
	}
	
	@Override
	public boolean resetPassword(long id, String md5) {
		return dao.updateField(id, "user_password", md5);
	}
}
