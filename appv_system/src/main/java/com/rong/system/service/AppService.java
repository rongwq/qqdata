package com.rong.system.service;

import com.jfinal.plugin.activerecord.Page;
import com.rong.persist.base.BaseService;
import com.rong.persist.model.App;

/**
 * app管理业务层接口
 * @author Wenqiang-Rong
 * @date 2018年1月12日
 */
public interface AppService extends BaseService<App>{
	Page<App> list(int pageNo,int pageSize);
}
