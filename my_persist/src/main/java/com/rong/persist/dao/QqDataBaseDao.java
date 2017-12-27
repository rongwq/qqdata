package com.rong.persist.dao;

import com.rong.persist.base.BaseDao;
import com.rong.persist.model.QqDataBase;

/**
 * qq数据dao
 * 
 * @author Wenqiang-Rong
 *
 */
public class QqDataBaseDao extends BaseDao<QqDataBase> {

	public static final QqDataBase dao = QqDataBase.dao;

	public static final String FILEDS = "id,create_time,update_time,qq,pwd,question1,question1_answer,question2,question2_answer,question3,question3_answer,mobile,token";

	public QqDataBase findByQq(String qq){
		String sql = "select " + FILEDS + " from " + QqDataBase.TABLE + " where qq = ?";
		return dao.findFirst(sql, qq);
	}
}
