package com.rong.persist.dao;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.rong.persist.base.BaseDao;
import com.rong.persist.model.IpTemp;

/**
 * 临时IP库dao
 * @author Wenqiang-Rong
 * @date 2017年12月29日
 */
public class IpTempDao extends BaseDao<IpTemp> {

	public static final IpTemp dao = IpTemp.dao;

	public static final String FILEDS = "id,ip,address,create_time,update_time";

	public Page<IpTemp> page(int pageNumber, int pageSize, Kv param) {
		String select = "select " + FILEDS;
		String sqlExceptSelect = "from " + IpTemp.TABLE;
		StringBuffer where = new StringBuffer(" where 1=1");
		String orderBy = " order by create_time desc";
		sqlExceptSelect = sqlExceptSelect + where + orderBy;
		return dao.paginate(pageNumber, pageSize, select, sqlExceptSelect);
	}
	
	public IpTemp findByIp(String ip){
		String sql = "select " + FILEDS + " from " + IpTemp.TABLE + " where ip = ?";
		return dao.findFirst(sql, ip);
	}
	
	public boolean clean(){
		String sql = "delete from " + IpTemp.TABLE + " where 1=1";
		return Db.update(sql)>0;
	}
	
	public int cleanExpir(){
		String sql = "delete from " + IpTemp.TABLE + " where TIMESTAMPDIFF(HOUR,create_time,now()) >= 24";
		return Db.update(sql);
	}
}
