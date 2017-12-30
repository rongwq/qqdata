package com.rong.persist.dao;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Page;
import com.rong.common.util.StringUtils;
import com.rong.persist.base.BaseDao;
import com.rong.persist.model.ReportQq;

/**
 * 产量明细报表dao
 * @author Wenqiang-Rong
 * @date 2017年12月30日
 */
public class ReportQqDao extends BaseDao<ReportQq> {

	public static final ReportQq dao = ReportQq.dao;

	public static final String FILEDS = "id,team_name,cost_price,qq_count,qq_count_lived,create_time,qq_count_type_1,qq_count_type_2,qq_count_type_3,qq_count_type_4,qq_state_0";

	public Page<ReportQq> page(int pageNumber, int pageSize, Kv param) {
		String select = "select " + FILEDS;
		String sqlExceptSelect = "from " + ReportQq.TABLE;
		StringBuffer where = new StringBuffer(" where 1=1");
		// 日期查询
		String time = param.getStr("time");
		if (!StringUtils.isNullOrEmpty(time)) {
			where.append(" and to_days(create_time) = to_days("+time+")");
		}else {
			where.append(" and to_days(create_time) = to_days(now())");
		}
		String orderBy = " order by create_time desc";
		sqlExceptSelect = sqlExceptSelect + where + orderBy;
		return dao.paginate(pageNumber, pageSize, select, sqlExceptSelect);
	}
}
