package com.rong.user.service;

import com.jfinal.plugin.activerecord.Page;
import com.rong.persist.base.BaseServiceImpl;
import com.rong.persist.dao.QqUpdatePwdWaitDao;
import com.rong.persist.model.QqUpdatePwdWait;

/**
 * 等待修改密码QQ服务实现层
 * @author Wenqiang-Rong
 * @date 2017年12月26日
 */
public class QqUpdatePwdWaitServiceImpl extends BaseServiceImpl<QqUpdatePwdWait> implements QqUpdatePwdWaitService{
	private QqUpdatePwdWaitDao dao = new QqUpdatePwdWaitDao();

	@Override
	public Page<QqUpdatePwdWait> list(int pageNumber, int pageSize) {
		return dao.page(pageNumber, pageSize, null);
	}
}
