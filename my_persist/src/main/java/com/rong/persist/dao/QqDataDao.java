package com.rong.persist.dao;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Page;
import com.rong.common.util.StringUtils;
import com.rong.persist.base.BaseDao;
import com.rong.persist.model.QqData;

/**
 * qq数据dao
 * 
 * @author Wenqiang-Rong
 *
 */
public class QqDataDao extends BaseDao<QqData> {

	public static final QqData dao = QqData.dao;

	public static final String FILEDS = "id,create_time,update_time,qq,qq_pwd,qq_type,team_id,team_name,tags,login_count,state,out_storage_time,in_storage_time,out_storage_days,login_time";

	public Page<QqData> page(int pageNumber, int pageSize, Kv param) {
		String select = "select " + FILEDS;
		String sqlExceptSelect = "from " + QqData.TABLE;
		StringBuffer where = new StringBuffer(" where 1=1");
		String qq = param.getStr("qq");
		if (!StringUtils.isNullOrEmpty(param.getStr("qq"))) {
			where.append(" and qq = '" + qq + "'");
		}
		Integer qqType = param.getInt("qqType");
		if (qqType != null && qqType > 0) {
			where.append(" and qq_type = " + qqType + "");
		}
		String orderBy = " order by create_time desc";
		sqlExceptSelect = sqlExceptSelect + where + orderBy;
		return dao.paginate(pageNumber, pageSize, select, sqlExceptSelect);
	}
	
	public QqData findByQq(String qq){
		String sql = "select " + FILEDS + " from " + QqData.TABLE + " where qq = ?";
		return dao.findFirst(sql, qq);
	}
}
