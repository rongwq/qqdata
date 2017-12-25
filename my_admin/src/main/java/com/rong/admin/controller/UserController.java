package com.rong.admin.controller;

import com.jfinal.aop.Duang;
import com.jfinal.plugin.activerecord.Page;
import com.rong.common.bean.BaseRenderJson;
import com.rong.common.bean.MyConst;
import com.rong.common.bean.MyErrorCodeConfig;
import com.rong.common.exception.CommonException;
import com.rong.common.util.CommonUtil;
import com.rong.persist.model.User;
import com.rong.user.service.UserApiService;
import com.rong.user.service.UserApiServiceImpl;
import com.rong.user.service.UserService;
import com.rong.user.service.UserServiceImpl;


public class UserController extends BaseController {
	private UserService userService = new UserServiceImpl();
	private UserApiService userApiService = Duang.duang(UserApiServiceImpl.class);
	
	/**
	 * 用户列表
	 */
	public void getUserList(){
		int page = getParaToInt("page", 1);
		String userName = getPara("userName");
		String mobile = getPara("mobile");
		Page<User> list = userService.getUserList(page, pageSize,userName,mobile);
		keepPara();
		setAttr("page", list);
		render("/views/user/list.jsp");
	}
	
	/**
	 *启用停用 
	 */
	public void setEnable() {
		Long id = getParaToLong("id",0L);
		Boolean isEnable = getParaToBoolean("isEnable");
		try {
			BaseRenderJson.returnOpareObj(this, userService.setEnable(id, isEnable));
		} catch (CommonException e) {
			BaseRenderJson.returnBaseTemplateObj(this, "0", "更新失败，" + e.getMessage());
		}
	}
	
	/**
	 * 用户列表重置密码
	 */
	public void resetPassword() {
		String password = getPara("password");
		long id = getParaToLong("id",0L);
		User user = userApiService.getUserById(id);
		if (!password.matches(MyConst.REGEX)) {
    		BaseRenderJson.returnBaseTemplateObj(this, MyErrorCodeConfig.REQUEST_FAIL, "请输入6-12位由字母和数字组合成的登录密码");
    		return;
    	}
		if(user == null){
			BaseRenderJson.returnBaseTemplateObj(this, MyErrorCodeConfig.REQUEST_FAIL, "此用户不存在");
			return;
		}
		 String md5 = CommonUtil.getMD5(password);
		try {
			BaseRenderJson.returnOpareObj(this, userService.resetPassword(id, md5));
		} catch (Exception e) {
			BaseRenderJson.returnBaseTemplateObj(this, "0", "操作失败，" + e.getMessage());
		}
	}
}
