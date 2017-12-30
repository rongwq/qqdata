package com.rong.admin.controller;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Page;
import com.rong.persist.dao.ReportQqDao;
import com.rong.persist.model.ReportQq;

/**
 * 报表管理
 * @author Wenqiang-Rong
 * @date 2017年12月30日
 */
public class ReportController extends BaseController {
	private ReportQqDao qqDao = new ReportQqDao();

	/**
	 * 产量明细报表
	 */
	public void qqStatis() {
		int pageNumber = getParaToInt("page", 1);
		String time = getPara("time");
		Kv param = Kv.by("time", time);
		Page<ReportQq> list = qqDao.page(pageNumber, pageSize, param);
		keepPara();
		setAttr("page", list);
		render("/views/report/qqStatis.jsp");
	}
}
