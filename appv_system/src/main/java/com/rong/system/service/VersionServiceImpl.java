package com.rong.system.service;

import com.jfinal.plugin.activerecord.Page;
import com.rong.persist.base.BaseServiceImpl;
import com.rong.persist.dao.VersionDao;
import com.rong.persist.model.Version;

/**
 * app版本管理实现类
 * @author Wenqiang-Rong
 * @date 2018年1月12日
 */
public class VersionServiceImpl extends BaseServiceImpl<Version> implements VersionService {
	private final VersionDao dao = new VersionDao(); 
	
	@Override
	public Page<Version> list(String code,int pageNo, int pageSize) {
		return dao.list(code,pageNo, pageSize);
	}

	@Override
	public Version getForApp(String code,Integer type) {
		return dao.getForApp(code, type);
	}
}

