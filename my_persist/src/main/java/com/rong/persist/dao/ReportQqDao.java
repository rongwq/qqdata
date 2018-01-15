package com.rong.persist.dao;

import java.util.List;

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
			where.append(" and to_days(create_time) = to_days('"+time+"')");
		}else {
			where.append(" and to_days(now()) - to_days(create_time) <=10");
		}
		String orderBy = " order by create_time desc";
		sqlExceptSelect = sqlExceptSelect + where + orderBy;
		return dao.paginate(pageNumber, pageSize, select, sqlExceptSelect);
	}
	
	/**
	 * 统计产量明细-当天及时统计，不保存
	 * 日期  编组 QQ数量 存活率 活号成本 白号   三问号  绑机号  令牌号 冻结号
	 * @return
	 */
	public List<ReportQq> qqStatisNow(){
		String sql = "SELECT now() create_time,q.team_name team_name,"+
		"count(*) qq_count,"+
		"count(IF(q.state = 1, TRUE, NULL)) qq_count_lived,"+
		"count(IF(q.state = 0, TRUE, NULL)) qq_state_0,"+
		"count(IF(q.qq_type = 1, TRUE, NULL)) qq_count_type_1,"+
		"count(IF(q.qq_type = 2, TRUE, NULL)) qq_count_type_2,"+
		"count(IF(q.qq_type = 3, TRUE, NULL)) qq_count_type_3,"+
		"count(IF(q.qq_type = 4, TRUE, NULL)) qq_count_type_4,"+
		"t.cost_price cost_price "+
		"FROM qq_data q,qq_team t "+
		"WHERE q.team_id = t.id AND ("+
		"DATEDIFF(now(), q.create_time) = 0 "+
		"OR DATEDIFF(now(), q.update_time) = 0) "+
		"GROUP BY q.team_name";
		return dao.find(sql);
	}
}
