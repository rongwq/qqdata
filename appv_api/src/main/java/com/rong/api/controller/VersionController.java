package com.rong.api.controller;

import com.jfinal.core.Controller;
import com.rong.common.bean.BaseRenderJson;
import com.rong.common.bean.MyErrorCodeConfig;
import com.rong.persist.model.Version;
import com.rong.system.service.VersionService;
import com.rong.system.service.VersionServiceImpl;

public class VersionController extends Controller{
	private final VersionService versionService=new VersionServiceImpl();
	
	/**
	 * 版本查询
	 * appcode-项目code
	 * system-系统类型：1-Android，2-iOS
	 */
	public void version() {
		String app = getPara("app");
		Integer type  = getParaToInt("type",Version.SYSTEM_ANDROID);
		Version version = versionService.getForApp(app,type);
		version.remove("app_id").remove("is_file").remove("update_time").remove("is_publish").remove("system_type").remove("id");
		BaseRenderJson.returnObjectTmplate(this, MyErrorCodeConfig.REQUEST_SUCCESS, version, "查询成功");
	}
	
	
}
