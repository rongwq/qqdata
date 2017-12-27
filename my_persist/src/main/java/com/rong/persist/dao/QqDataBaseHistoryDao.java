package com.rong.persist.dao;

import com.jfinal.plugin.activerecord.Page;
import com.rong.persist.base.BaseDao;
import com.rong.persist.model.QqDataBaseHistory;

/**
 * qq数据dao
 * 
 * @author Wenqiang-Rong
 *
 */
public class QqDataBaseHistoryDao extends BaseDao<QqDataBaseHistory> {

	public static final QqDataBaseHistory dao = QqDataBaseHistory.dao;

	public static final String FILEDS = "id,create_time,update_time,qq,pwd,question1,question1_answer,question2,question2_answer,question3,question3_answer,mobile,token";

	public Page<QqDataBaseHistory> page(int pageNumber,int pageSize,String qq){
		String select = "select " + FILEDS;
		String sqlExceptSelect = "from " + QqDataBaseHistory.TABLE;
		StringBuffer where = new StringBuffer(" where qq = ?");
		String orderBy = " order by create_time desc";
		sqlExceptSelect = sqlExceptSelect + where + orderBy;
		return dao.paginate(pageNumber, pageSize, select, sqlExceptSelect,qq);
	}
}
