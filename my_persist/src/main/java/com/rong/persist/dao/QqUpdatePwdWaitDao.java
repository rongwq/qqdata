package com.rong.persist.dao;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Page;
import com.rong.persist.base.BaseDao;
import com.rong.persist.model.QqUpdatePwdWait;

/**
 * 等待修改密码dao
 * @author Wenqiang-Rong
 * @date 2017年12月29日
 */
public class QqUpdatePwdWaitDao extends BaseDao<QqUpdatePwdWait> {

	public static final QqUpdatePwdWait dao = QqUpdatePwdWait.dao;

	public static final String FILEDS = "id,qq,token_code,create_time,update_time";

	public Page<QqUpdatePwdWait> page(int pageNumber, int pageSize, Kv param) {
		String select = "select " + FILEDS;
		String sqlExceptSelect = "from " + QqUpdatePwdWait.TABLE;
		StringBuffer where = new StringBuffer(" where 1=1");
		String orderBy = " order by create_time desc";
		sqlExceptSelect = sqlExceptSelect + where + orderBy;
		return dao.paginate(pageNumber, pageSize, select, sqlExceptSelect);
	}
}
