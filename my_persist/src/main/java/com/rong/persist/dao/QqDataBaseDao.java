package com.rong.persist.dao;

import java.util.List;

import com.rong.persist.base.BaseDao;
import com.rong.persist.model.QqDataBase;

/**
 * qq数据dao
 * @author Wenqiang-Rong
 * @date 2017年12月29日
 */
public class QqDataBaseDao extends BaseDao<QqDataBase> {

	public static final QqDataBase dao = QqDataBase.dao;

	public static final String FILEDS = "id,create_time,update_time,qq,pwd,question1,question1_answer,question2,question2_answer,question3,question3_answer,mobile,token,token_code";

	public QqDataBase findByQq(String qq){
		String sql = "select " + FILEDS + " from " + QqDataBase.TABLE + " where qq = ?";
		return dao.findFirst(sql, qq);
	}
	
	public List<QqDataBase> findByQqs(String qqs){
		String sql = "select " + FILEDS + " from " + QqDataBase.TABLE + " where qq in ("+qqs+") group by qq order by create_time desc";
		return dao.find(sql);
	}
}
