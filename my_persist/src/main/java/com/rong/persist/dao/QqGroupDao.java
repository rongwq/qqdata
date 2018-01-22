package com.rong.persist.dao;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Page;
import com.rong.persist.base.BaseDao;
import com.rong.persist.model.QqGroup;

/**
 * qq群dao层
 * @author Wenqiang-Rong
 * @date 2018年1月22日
 */
public class QqGroupDao extends BaseDao<QqGroup> {

	public static final QqGroup dao = QqGroup.dao;

	public static final String FILEDS = "id,group_no,group_name,state,create_time,update_time";

	public Page<QqGroup> page(int pageNumber, int pageSize, Kv param) {
		String select = "select " + FILEDS;
		String sqlExceptSelect = "from " + QqGroup.TABLE;
		StringBuffer where = new StringBuffer(" where 1=1");
		String orderBy = " order by create_time desc";
		sqlExceptSelect = sqlExceptSelect + where + orderBy;
		return dao.paginate(pageNumber, pageSize, select, sqlExceptSelect);
	}
	
	public QqGroup findByGroupNo(String groupNo){
		String sql = "select " + FILEDS + " from " + QqGroup.TABLE + " where group_no = ?";
		return dao.findFirst(sql, groupNo);
	}
}
