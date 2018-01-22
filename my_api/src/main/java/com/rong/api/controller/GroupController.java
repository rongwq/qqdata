package com.rong.api.controller;

import java.util.HashMap;
import java.util.Map;

import com.jfinal.core.Controller;
import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Page;
import com.rong.common.bean.BaseRenderJson;
import com.rong.common.bean.MyErrorCodeConfig;
import com.rong.common.validator.CommonValidatorUtils;
import com.rong.persist.model.QqGroup;
import com.rong.user.service.QqGroupService;
import com.rong.user.service.QqGroupServiceImpl;

/**
 * QQ群接口
 * @author Wenqiang-Rong
 * @date 2018年1月22日
 */
public class GroupController extends Controller{
	private final Log logger = Log.getLog(this.getClass());
	private QqGroupService groupService = new QqGroupServiceImpl();
	/**
	 * 新增
	 */
	public void save(){
		String groupNo = getPara("groupNo");
		Map<String , Object> paramMap = new HashMap<>();
		paramMap.put("groupNo", groupNo);
		if(CommonValidatorUtils.requiredValidate(paramMap, this)){
			return;
		}
		QqGroup group = groupService.findByGroupNo(groupNo);
		if (group != null) {
			BaseRenderJson.apiReturnJson(this, MyErrorCodeConfig.ERROR_BAD_REQUEST, groupNo+"已存在");
			return;
		}
		groupService.save(groupNo);
		logger.info("[api]新增群成功："+groupNo);
		BaseRenderJson.apiReturnJson(this, MyErrorCodeConfig.REQUEST_SUCCESS, "保存成功");
	}
	
	/**
	 * QQ群列表
	 */
	public void list() {
		Page<QqGroup> list = pageList();
		BaseRenderJson.returnObjectTmplate(this, MyErrorCodeConfig.REQUEST_SUCCESS, list, "获取成功");
	}
	
	private Page<QqGroup> pageList() {
		int pageNumber = getParaToInt("page", 1);
		int pageSize = getParaToInt("pageSize", 10);
		Page<QqGroup> list = groupService.list(pageNumber, pageSize);
		return list;
	}
}
