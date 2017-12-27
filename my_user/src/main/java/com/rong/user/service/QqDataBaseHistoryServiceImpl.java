package com.rong.user.service;

import com.jfinal.plugin.activerecord.Page;
import com.rong.persist.base.BaseServiceImpl;
import com.rong.persist.dao.QqDataBaseHistoryDao;
import com.rong.persist.model.QqDataBaseHistory;

/**
 * QQ修改历史数据服务实现层
 * @author Wenqiang-Rong
 * @date 2017年12月26日
 */
public class QqDataBaseHistoryServiceImpl extends BaseServiceImpl<QqDataBaseHistory> implements QqDataBaseHistoryService{
	private QqDataBaseHistoryDao qqDataBaseHistoryDao = new QqDataBaseHistoryDao();

	@Override
	public Page<QqDataBaseHistory> list(int pageNumber, int pageSize, String qq) {
		return qqDataBaseHistoryDao.page(pageNumber, pageSize, qq);
	}
}
