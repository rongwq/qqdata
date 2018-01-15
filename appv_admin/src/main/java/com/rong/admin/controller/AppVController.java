package com.rong.admin.controller;

import java.util.Date;

import org.apache.log4j.Logger;

import com.rong.common.bean.BaseRenderJson;
import com.rong.persist.model.App;
import com.rong.persist.model.Version;
import com.rong.system.service.AppService;
import com.rong.system.service.AppServiceImpl;
import com.rong.system.service.VersionService;
import com.rong.system.service.VersionServiceImpl;

public class AppVController extends BaseController{
	private final Logger logger = Logger.getLogger(this.getClass());
	private final AppService appService=new AppServiceImpl();
	private final VersionService versionService=new VersionServiceImpl();
	
	/**
	 * APP版本分页列表
	 */
	public void versionList(){
		Integer appId = getParaToInt("appId");
		int page = getParaToInt("page", 1);
		int pageSize = getParaToInt("pageSize", BaseController.pageSize);
		keepPara();
		App app = appService.findById(appId);
		setAttr("app", app);
		setAttr("page", versionService.list(app.getAppCode(),page,pageSize));
		render("/views/appv/versionList.jsp");
	}
	
	/**
	 * 保存APP版本
	 */
	public void versionSave() {
		Long appId = getParaToLong("appId");
		String appName = getPara("appName");
		String appCode = getPara("appCode");
		String versionName = getPara("versionName");
		String appVersion = getPara("appVersion");
		Integer systemType = getParaToInt("systemType");
		String downloadUrl = getPara("downloadUrl");
		String iconUrl = getPara("iconUrl");
		Long fileSize = getParaToLong("fileSize");
		String remark = getPara("remark");
		Boolean autoDownload = getParaToBoolean("autoDownload");
		Boolean isFile = getParaToBoolean("isFile");
		Version model = new Version();
		model.setCreateTime(new Date());
		model.setAppId(appId);
		model.setAppName(appName);
		model.setAppCode(appCode);
		model.setVersionName(versionName);
		model.setAppVersion(appVersion);
		model.setSystemType(systemType);
		model.setDownloadUrl(downloadUrl);
		model.setIconUrl(iconUrl);
		model.setFileSize(fileSize);
		model.setRemark(remark);
		model.setAutoDownload(autoDownload);
		model.setIsFile(isFile);
		model.setIsPublish(false);
		model.save();
		BaseRenderJson.returnAddObj(this, true);
		logger.info("[操作日志]新增版本信息成功：" + model.toJson());
	}
	
	/**
	 * 去更新APP版本
	 */
	public void toVersionUpdate(){
		Long id = getParaToLong("id");
		setAttr("model", versionService.findById(id));
		render("/views/appv/versionUpdate.jsp");
	}
	
	/**
	 * 更新APP版本
	 */
	public void versionUpdate(){
		Long id = getParaToLong("id");
		String versionName = getPara("versionName");
		String appVersion = getPara("appVersion");
		Integer systemType = getParaToInt("systemType");
		String downloadUrl = getPara("downloadUrl");
		String iconUrl = getPara("img");
		Long fileSize = getParaToLong("fileSize");
		String remark = getPara("remark");
		Boolean autoDownload = getParaToBoolean("autoDownload");
		Boolean isFile = getParaToBoolean("isFile");
		Version model = versionService.findById(id);
		model.setVersionName(versionName);
		model.setAppVersion(appVersion);
		model.setSystemType(systemType);
		model.setDownloadUrl(downloadUrl);
		model.setIconUrl(iconUrl);
		model.setFileSize(fileSize);
		model.setAutoDownload(autoDownload);
		model.setIsFile(isFile);
		model.setUpdateTime(new Date());
		model.setRemark(remark);
		model.update();
		BaseRenderJson.returnUpdateObj(this, true);
		logger.info("[操作日志]更新APP版本成功："+model.toJson());
	}
	
	/**
	 * 启用APP版本
	 */
	public void versionEnable(){
		Long id = getParaToLong("id");
		Version model = versionService.findById(id);
		model.setIsPublish(true);
		model.update();
		BaseRenderJson.returnUpdateObj(this, true);
		logger.info("[操作日志]启用APP版本成功："+model.toJson());
	}
	
	/**
	 * 禁用APP版本
	 */
	public void versionDisable(){
		Long id = getParaToLong("id");
		Version model = versionService.findById(id);
		model.setIsPublish(false);
		model.update();
		BaseRenderJson.returnUpdateObj(this, true);
		logger.info("[操作日志]禁用APP版本成功："+model.toJson());
	}
	
	/**
	 * 删除APP
	 */
	public void versionDelete() {
		Long id = getParaToLong("id");
		if(versionService.deleteById(id)){
			BaseRenderJson.returnDelObj(this, true);
			logger.info("[操作日志]"+getUser().getUserName()+ "删除APP版本成功:id:" + id);
		}else{
			BaseRenderJson.returnDelObj(this, false);
			logger.info("[操作日志]"+getUser().getUserName()+ "删除APP版本失败:id:" + id);
		}
	}
	
	/**
	 * APP分页列表
	 */
	public void appList(){
		int page = getParaToInt("page", 1);
		int pageSize = getParaToInt("pageSize", BaseController.pageSize);
		keepPara();
		setAttr("page", appService.list(page,pageSize));
		render("/views/appv/appList.jsp");
	}
	
	/**
	 * 保存APP
	 */
	public void appSave(){
		String appName = getPara("appName");
		String appCode = getPara("appCode");
		String remark = getPara("remark");
		App model = new App();
		model.setAppName(appName);
		model.setAppCode(appCode);
		model.setCreateTime(new Date());
		model.setRemark(remark);
		model.save();
		BaseRenderJson.returnAddObj(this, true);
		logger.info("[操作日志]添加APP成功："+model.toJson());
	}
	
	/**
	 * 更新APP
	 */
	public void appUpdate(){
		Long id = getParaToLong("id");
		String appName = getPara("appName");
		String appCode = getPara("appCode");
		String remark = getPara("remark");
		App model = appService.findById(id);
		model.setAppName(appName);
		model.setAppCode(appCode);
		model.setUpdateTime(new Date());
		model.setRemark(remark);
		model.update();
		BaseRenderJson.returnUpdateObj(this, true);
		logger.info("[操作日志]更新APP成功："+model.toJson());
	}
	
	/**
	 * 去更新APP
	 */
	public void toAppUpdate(){
		Long id = getParaToLong("id");
		setAttr("model", appService.findById(id));
		render("/views/appv/appUpdate.jsp");
	}
	
	/**
	 * 删除APP
	 */
	public void appDelete() {
		Long id = getParaToLong("id");
		if(appService.deleteById(id)){
			BaseRenderJson.returnDelObj(this, true);
			logger.info("[操作日志]"+getUser().getUserName()+ "删除APP成功:id:" + id);
		}else{
			BaseRenderJson.returnDelObj(this, false);
			logger.info("[操作日志]"+getUser().getUserName()+ "删除APP失败:id:" + id);
		}
	}
}
