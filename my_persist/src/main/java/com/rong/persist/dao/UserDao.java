package com.rong.persist.dao;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.rong.common.util.StringUtils;
import com.rong.persist.base.BaseDao;
import com.rong.persist.model.User;

/**
 * 会员用户dao
 * @author Wenqiang-Rong
 * @date 2017年12月29日
 */
public class UserDao  extends BaseDao<User> {

	public static final User dao = User.dao;

	public static final String FILEDS = "id,nick_name,user_head,mobile,user_password,pay_password,"
			+"user_type,code,sex,state,create_time,update_time";
	
	public User getUserByMobile(String mobile){
		String sql = "select "+FILEDS+" from " + User.TABLE+ " where mobile =? ";
		return  dao.findFirst(sql, mobile);
	}

	public User getUserById(long userId){
		String sql = "select "+FILEDS+" from " + User.TABLE+ " where id =? ";
		return  dao.findFirst(sql, userId);
	}

	public User getUserByOpenid(String openid){
		String sql = "select "+FILEDS+" from " + User.TABLE+ " where openid =? ";
		return  dao.findFirst(sql, openid);
	}
	

	/**
	 * @date 2017-7-7
	 * @return user
	 * @param mobile
	 * @param decrptyPwd
	 * @dec 获取用户信息
	 * */
	public User getByMobileAndPassword(String mobile, String decrptyPwd){
		String sql = "select "+FILEDS+" from " + User.TABLE+ " where mobile=? and user_password=?";
		return dao.findFirst(sql, mobile,decrptyPwd);
	}

	/**
	 * 
	 * @param page
	 * @param pagesize
	 * @param userName 用户名
	 * @param mobile 手机号 条件搜索
	 * 查询用户端 用户列表信息 20170712 yong.pei
	 */
	public Page<User> getUserList(int page,int pagesize,String userName,String mobile) {
		StringBuffer where = new StringBuffer(" where 1=1");
		if(!StringUtils.isNullOrEmpty(userName)){
			where.append(" and nick_name like '%"+userName+"%'  ");
		}
		if(!StringUtils.isNullOrEmpty(mobile)){
			where.append(" and mobile like '%"+mobile+"%' ");
		}
		String select = "select "+FILEDS;
		String sqlExceptSelect = "from "+User.TABLE;
		return dao.paginate(page, pagesize, select, sqlExceptSelect+where.toString()+" AND user_type = 1 "+" order by create_time desc");
	}
	
	public boolean updateField(long id, String fieldName, Object value){
		return Db.update(String.format("UPDATE %s SET %s = ? WHERE id = ?", User.TABLE, fieldName), value, id)>0;
	}

	public User getUserByCode(String code) {
		String sql = "select "+FILEDS+" from " + User.TABLE+ " where code =? ";
		return  dao.findFirst(sql, code);
	}

	public boolean deleteUserById(long userId){
		return dao.deleteById(userId);
	}
}
