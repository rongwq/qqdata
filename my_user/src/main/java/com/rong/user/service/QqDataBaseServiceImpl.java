package com.rong.user.service;

import java.util.List;

import com.rong.persist.base.BaseServiceImpl;
import com.rong.persist.dao.QqDataBaseDao;
import com.rong.persist.model.QqDataBase;

/**
 * QQ修改历史数据服务实现层
 * @author Wenqiang-Rong
 * @date 2017年12月26日
 */
public class QqDataBaseServiceImpl extends BaseServiceImpl<QqDataBase> implements QqDataBaseService{
	private QqDataBaseDao qqDataBaseDao = new QqDataBaseDao();

	@Override
	public QqDataBase findByQq(String qq) {
		return qqDataBaseDao.findByQq(qq);
	}

	@Override
	public List<QqDataBase> findByQqs(String qqs) {
		return qqDataBaseDao.findByQqs(qqs);
	}
}
