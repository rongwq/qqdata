package com.rong.system.service;

import com.jfinal.plugin.activerecord.Page;
import com.rong.persist.base.BaseServiceImpl;
import com.rong.persist.dao.AppDao;
import com.rong.persist.model.App;

/**
 * app管理实现类
 * @author Wenqiang-Rong
 * @date 2018年1月12日
 */
public class AppServiceImpl extends BaseServiceImpl<App> implements AppService {
	private final AppDao dao = new AppDao(); 
	
	@Override
	public Page<App> list(int pageNo, int pageSize) {
		return dao.list(pageNo, pageSize);
	}

}

