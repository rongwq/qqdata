package com.rong.system.service;

import com.jfinal.plugin.activerecord.Page;
import com.rong.persist.base.BaseService;
import com.rong.persist.model.Version;

/**
 * app版本接口
 * @author Wenqiang-Rong
 * @date 2018年1月12日
 */
public interface VersionService extends BaseService<Version>{
	Page<Version> list(String code,int pageNo,int pageSize);
	/**
	 * 根据code和type查询最新版本
	 * @param code
	 * @param type 1-Android 2-iOS
	 * @return
	 */
	Version getForApp(String code,Integer type);
}
