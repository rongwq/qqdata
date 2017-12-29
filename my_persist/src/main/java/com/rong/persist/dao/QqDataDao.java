package com.rong.persist.dao;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Page;
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
		// qq状态 1-可用，0已冻结
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
}
