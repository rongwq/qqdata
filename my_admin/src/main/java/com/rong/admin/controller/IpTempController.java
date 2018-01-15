package com.rong.admin.controller;

import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Page;
import com.rong.common.bean.BaseRenderJson;
import com.rong.persist.model.IpTemp;
import com.rong.user.service.IpTempService;
import com.rong.user.service.IpTempServiceImpl;

/**
 * 临时IP库管理
 * @author Wenqiang-Rong
 * @date 2017年12月29日
 */
public class IpTempController extends BaseController {
	private final Log logger = Log.getLog(this.getClass());
	private IpTempService ipTempService = new IpTempServiceImpl();

	/**
	 * 列表
	 */
	public void list() {
		int pageNumber = getParaToInt("page", 1);
		Page<IpTemp> list = ipTempService.list(pageNumber, pageSize);
		keepPara();
		setAttr("page", list);
		render("/views/ip/list.jsp");
	}

	/**
	 * 删除
	 */
	public void delete() {
		Long id = getParaToLong("id");
		if (ipTempService.deleteById(id)) {
			BaseRenderJson.returnDelObj(this, true);
			logger.info("[操作日志]ip删除id:" + id + "成功");
		} else {
			BaseRenderJson.returnDelObj(this, false);
			logger.error("[操作日志]ip删除id:" + id + "失败");
		}

	}
	
	/**
	 * 一键清理
	 */
	public void clean() {
		if (ipTempService.clean()) {
			BaseRenderJson.returnDelObj(this, true);
			logger.info("[操作日志]临时IP库一键清理成功");
		} else {
			BaseRenderJson.returnDelObj(this, false);
			logger.error("[操作日志]临时IP库一键清理失败");
		}

	}
}
