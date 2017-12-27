package com.rong.persist.dao;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Page;
import com.rong.persist.base.BaseDao;
import com.rong.persist.model.QqData;
import com.rong.persist.model.QqTeam;

/**
 * 编组dao
 * 
 * @author Wenqiang-Rong
 *
 */
public class QqTeamDao extends BaseDao<QqTeam> {

	public static final QqTeam dao = QqTeam.dao;

	public static final String FILEDS = "id,name,cost_price,qq_count,qq_count_lived,create_time,update_time,qq,qq_pwd,qq_type,team_id,team_name,tags,login_count,state,out_storage_time,in_storage_time,out_storage_days,login_time";

	public Page<QqTeam> page(int pageNumber, int pageSize, Kv param) {
		String select = "select " + FILEDS;
		String sqlExceptSelect = "from " + QqData.TABLE;
		StringBuffer where = new StringBuffer(" where 1=1");
		String orderBy = " order by create_time desc";
		sqlExceptSelect = sqlExceptSelect + where + orderBy;
		return dao.paginate(pageNumber, pageSize, select, sqlExceptSelect);
	}
}
