package com.rong.admin.controller;

import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;
import com.rong.common.bean.BaseRenderJson;
import com.rong.common.bean.MyErrorCodeConfig;
import com.rong.system.service.IndexService;
import com.rong.system.service.IndexServiceImpl;
/**
 * 首页控制类
 * @author Wenqiang-Rong
 *
 */
public class IndexController extends Controller {
	IndexService service = new IndexServiceImpl();
	
	public void index() {
		render("/views/login.jsp");
	}

	public void qqTypeStatis(){
		List<Record> list = service.qqTypeStatis();
		BaseRenderJson.returnObjectTmplate(this, MyErrorCodeConfig.REQUEST_SUCCESS, list, "获取qq统计数据成功");
	}
}
