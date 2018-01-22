package com.rong.user.service;

import java.util.Date;

import com.jfinal.plugin.activerecord.Page;
import com.rong.persist.base.BaseServiceImpl;
import com.rong.persist.dao.QqGroupDao;
import com.rong.persist.model.QqGroup;

/**
 * qq群组业务服务实现层
 * @author Wenqiang-Rong
 * @date 2018年1月22日
 */
public class QqGroupServiceImpl extends BaseServiceImpl<QqGroup> implements QqGroupService{
	private QqGroupDao dao = new QqGroupDao();

	@Override
	public Page<QqGroup> list(int pageNumber, int pageSize) {
		return dao.page(pageNumber, pageSize, null);
	}

	@Override
	public QqGroup findByGroupNo(String groupNo) {
		return dao.findByGroupNo(groupNo);
	}

	@Override
	public boolean save(String groupNo) {
		QqGroup qqGroup = new QqGroup();
		qqGroup.setGroupNo(groupNo);
		qqGroup.setCreateTime(new Date());
		return dao.save(qqGroup);
	}
}
