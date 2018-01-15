package com.rong.admin.controller;

import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Page;
import com.rong.common.bean.BaseRenderJson;
import com.rong.persist.model.QqTeam;
import com.rong.user.service.QqTeamService;
import com.rong.user.service.QqTeamServiceImpl;

/**
 * qq编组管理
 * @author Wenqiang-Rong
 * @date 2017年12月27日
 */
public class QqTeamController extends BaseController {
	private final Log logger = Log.getLog(this.getClass());
	private QqTeamService qqTeamService = new QqTeamServiceImpl();

	/**
	 * 编组列表
	 */
	public void list() {
		int pageNumber = getParaToInt("page", 1);
		Page<QqTeam> list = qqTeamService.list(pageNumber, pageSize);
		keepPara();
		setAttr("page", list);
		render("/views/qq/team.jsp");
	}
	
	/**
	 * 修改编组名称
	 */
	public void updateName() {
		Long id = getParaToLong("id");
		String teamName = getPara("teamName");
		QqTeam qqTeam = qqTeamService.find(teamName);
		if(qqTeam!=null){
			BaseRenderJson.returnJsonS(this,0,"编组名称:'"+teamName+"'已经存在,请重新输入");
			return;
		}
		boolean result = qqTeamService.updateName(id, teamName);
		if (result) {
			BaseRenderJson.returnUpdateObj(this, true);
			logger.info("[操作日志]修改编组名称成功");
		} else {
			BaseRenderJson.returnUpdateObj(this, false);
			logger.error("[操作日志]修改编组名称失败");
		}
	}

	/**
	 * 删除
	 */
	public void delete() {
		Long id = getParaToLong("id");
		if (qqTeamService.deleteById(id)) {
			BaseRenderJson.returnDelObj(this, true);
			logger.info("[操作日志]删除编组id:" + id + "成功");
		} else {
			BaseRenderJson.returnDelObj(this, false);
			logger.error("[操作日志]删除编组id:" + id + "失败");
		}

	}
}
