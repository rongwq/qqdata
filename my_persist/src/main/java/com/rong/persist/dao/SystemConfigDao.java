package com.rong.persist.dao;

import java.util.List;

import com.jfinal.plugin.activerecord.Page;
import com.rong.common.util.StringUtils;
import com.rong.persist.base.BaseDao;
import com.rong.persist.model.SystemConfig;

public class SystemConfigDao  extends BaseDao<SystemConfig>{
	private SystemConfig dao = SystemConfig.dao;
	
	public Page<SystemConfig> list(int pageNo, int pageSize, Integer id, String key, String value) {
		String sqlSelect = "select * ";
		StringBuffer sqlWhere = new StringBuffer(" where 1=1");
		if(id!=null){
			sqlWhere.append(" and id= "+id+" ");
		}
		if(!StringUtils.isNullOrEmpty(key)){
			sqlWhere.append(" and `key` = '"+key+"'");
		}
		if(!StringUtils.isNullOrEmpty(value)){
			sqlWhere.append(" and value like '%"+value+"%'");
		}
		String sqlExceptSelect = "from "+SystemConfig.TABLE+sqlWhere.toString() +" order by createTime desc";
		return dao.paginate(pageNo, pageSize, sqlSelect, sqlExceptSelect);
	}
	
	public List<SystemConfig> getAll() {
		String sql = "select * from "+SystemConfig.TABLE+" order by createTime desc";
		return dao.find(sql);
	}
	
	public List<SystemConfig> getAppConf() {
		String sql = "select * from "+SystemConfig.TABLE+" where type='app' order by createTime desc";
		return dao.find(sql);
	}
	
	public SystemConfig getByKey(String key) {
		String sql="select * from "+SystemConfig.TABLE+" where `key` = ?";
		return dao.findFirst(sql,key);
	}
	
}
