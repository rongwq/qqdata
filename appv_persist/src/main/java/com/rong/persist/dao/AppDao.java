package com.rong.persist.dao;

import com.jfinal.plugin.activerecord.Page;
import com.rong.persist.base.BaseDao;
import com.rong.persist.model.App;

/**
 * APP的dao类
 * @author Wenqiang-Rong
 * @date 2018年1月12日
 */
public class AppDao extends BaseDao<App>{
	private App dao = App.dao;
	public static final String FILEDS = "id,app_name,app_code,remark,create_time,update_time";
	
	public Page<App> list(int pageNo,int pageSize) {
		String select = "select " + FILEDS;
		String sqlExceptSelect = "from "+App.TABLE ;
		String orderBy = " order by id desc";
		sqlExceptSelect += orderBy;
		return dao.paginate(pageNo, pageSize, select, sqlExceptSelect);
	}
}
