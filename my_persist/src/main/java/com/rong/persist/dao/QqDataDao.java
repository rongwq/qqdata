package com.rong.persist.dao;

import java.util.List;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.rong.common.util.StringUtils;
import com.rong.persist.base.BaseDao;
import com.rong.persist.model.QqData;

/**
 * qq数据dao
 * @author Wenqiang-Rong
 * @date 2017年12月29日
 */
public class QqDataDao extends BaseDao<QqData> {

	public static final QqData dao = QqData.dao;

	public static final String FILEDS = "id,create_time,update_time,qq,qq_pwd,qq_type,qq_age,team_id,team_name,tags,login_count,state,out_storage_time,in_storage_time,out_storage_days,login_time";

	public Page<QqData> page(int pageNumber, int pageSize, Kv param) {
		String select = "select " + FILEDS;
		String sqlExceptSelect = "from " + QqData.TABLE;
		if(param==null){
			String orderBy = " order by create_time desc";
			sqlExceptSelect = sqlExceptSelect + orderBy;
			return dao.paginate(pageNumber, pageSize, select, sqlExceptSelect);
		}
		StringBuffer where = new StringBuffer(" where 1=1");
		// qq
		String qq = param.getStr("qq");
		if (!StringUtils.isNullOrEmpty(qq)) {
			where.append(" and qq = '" + qq + "'");
		}
		// qq类型 1-白号,2-三问号,3-绑机号,4-令牌号
		Integer qqType = param.getInt("qqType");
		if (qqType != null) {
			where.append(" and qq_type = " + qqType + "");
		}
		// qq状态 1-可用，0已冻结,2永久冻结
		Integer state = param.getInt("state");
		if (state != null) {
			where.append(" and state = " + state + "");
		}
		// qq库存状态 1未出仓，2出仓
		Integer storageState = param.getInt("storageState");
		if(storageState!=null){
			if (storageState == 1) {
				where.append(" and out_storage_time is null");
			}else if (storageState == 2) {
				where.append(" and out_storage_time is not null");
			}
		}
		// qq编组
		String teamName = param.getStr("teamName");
		if (!StringUtils.isNullOrEmpty(teamName)) {
			where.append(" and team_name = '" + teamName + "'");
		}
		// 标签是否包含 1包含，2不包含
		Integer isHaveTag = param.getInt("isHaveTag");
		String tags = param.getStr("tags");
		if(!StringUtils.isNullOrEmpty(tags)){
			if(isHaveTag!=null){
				String tagsArr [] = tags.split("、");
				if (isHaveTag == 1) {
					for (int i = 0; i < tagsArr.length; i++) {
						if(i==0){
							where.append(" and( tags like '%"+tagsArr[i]+"%'");
						}else{
							where.append(" or tags like '%"+tagsArr[i]+"%'");
						}
					}
					where.append(")");
				}else if (isHaveTag == 2) {
					for (int i = 0; i < tagsArr.length; i++) {
						where.append(" and tags not like '%"+tagsArr[i]+"%'");
					}
				}
			}
		}
		// q龄查询 小于qAgeMin
		Integer qAgeMin = param.getInt("qAgeMin");
		if (qAgeMin != null && qAgeMin > 0) {
			where.append(" and qq_age <= "+qAgeMin);
		}
		// q龄查询 大于qAgeMax
		Integer qAgeMax = param.getInt("qAgeMax");
		if (qAgeMax != null && qAgeMax > 0) {
			where.append(" and qq_age >= " + qAgeMax);
		}
		// qq长度
		Integer qqLength = param.getInt("qqLength");
		if (qqLength != null && qqLength > 0) {
			where.append(" and length(qq) = " + qqLength);
		}
		// 当天未登录qq 
		Boolean notLoginToday = param.getBoolean("notLoginToday");
		if (notLoginToday!=null && notLoginToday) {
			where.append(" and (to_days(login_time) != to_days(now()) or login_time is null)");
		}
		String orderBy = " order by create_time desc";
		sqlExceptSelect = sqlExceptSelect + where + orderBy;
		return dao.paginate(pageNumber, pageSize, select, sqlExceptSelect);
	}
	
	public QqData findByQq(String qq){
		String sql = "select " + FILEDS + " from " + QqData.TABLE + " where qq = ?";
		return dao.findFirst(sql, qq);
	}
	
	@Override
	public boolean deleteById(long id){
		QqData qqData = findById(id);
		return qqData.delete();
	}
	
	public List<Record> qqTypeStatis(){
		String sql = "SELECT qq_type qqType,"
				+ "count(IF(state = 0, TRUE, NULL)) state0,"
				+ "count(IF(state = 2, TRUE, NULL)) state2,"
				+ "count(*) allCount,"
				+ "count(IF(state = 1 and out_storage_time IS NULL, TRUE, NULL)) storageCount,"
				+ "count(IF (out_storage_time IS NOT NULL,TRUE,NULL)) outStorageCount,"
				+ "count(if(LENGTH(qq)=9 and out_storage_time IS NULL,true,null)) qqlen9Count,"
				+ "count(if(LENGTH(qq)=10 and out_storage_time IS NULL,true,null)) qqlen10Count "
				+ "FROM "
				+ "qq_data GROUP BY qq_type";
		return Db.find(sql);
	}
	
	/**
	 * 统计产量明细-定时器使用
	 * 日期  编组 QQ数量 存活率 活号成本 白号   三问号  绑机号  令牌号 冻结号
	 * @return
	 */
	public List<Record> qqStatis(){
		String sql = "SELECT q.team_name teamName,"+
		"count(*) allCount,"+
		"count(IF(q.state = 1, TRUE, NULL)) aliveCount,"+
		"count(IF(q.state = 0, TRUE, NULL)) unaliveCount,"+
		"count(IF(q.qq_type = 1, TRUE, NULL)) qqCountType1,"+
		"count(IF(q.qq_type = 2, TRUE, NULL)) qqCountType2,"+
		"count(IF(q.qq_type = 3, TRUE, NULL)) qqCountType3,"+
		"count(IF(q.qq_type = 4, TRUE, NULL)) qqCountType4,"+
		"t.cost_price costPrice "+
		"FROM qq_data q,qq_team t "+
		"WHERE q.team_id = t.id AND ("+
		"DATEDIFF(now(), q.create_time) = 1 "+
		"OR DATEDIFF(now(), q.update_time) = 1) "+
		"GROUP BY q.team_name";
		return Db.find(sql);
	}
}
