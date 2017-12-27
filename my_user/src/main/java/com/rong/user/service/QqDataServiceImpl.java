package com.rong.user.service;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Page;
import com.rong.persist.base.BaseServiceImpl;
import com.rong.persist.dao.QqDataDao;
import com.rong.persist.model.QqData;

/**
 * QQ数据服务实现层
 * @author Wenqiang-Rong
 * @date 2017年12月26日
 */
public class QqDataServiceImpl extends BaseServiceImpl<QqData> implements QqDataService{
	private QqDataDao dao = new QqDataDao();

	@Override
	public Page<QqData> list(int pageNumber, int pageSize, Kv param) {
		return dao.page(pageNumber, pageSize, param);
	}
}
