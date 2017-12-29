package com.rong.persist.dao;

import com.jfinal.plugin.activerecord.Db;
import com.rong.persist.base.BaseDao;
import com.rong.persist.model.UserPwderrorStatis;


/**
 * 会员用户密码错误次数统计dao
 * @author Wenqiang-Rong
 * @date 2017年12月29日
 */
public class UserPwderrorStatisDao extends BaseDao<UserPwderrorStatis> {
	public UserPwderrorStatis getByUserId(long userId){
		return findFirst("select * from "+UserPwderrorStatis.TABLE+" where userId=?", userId);
	}
	public void delete(){
		String sql = "truncate table "+UserPwderrorStatis.TABLE;
		Db.update(sql);
	}
	
}
