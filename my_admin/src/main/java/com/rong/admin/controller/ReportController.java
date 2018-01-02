package com.rong.admin.controller;

import java.util.Date;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Page;
import com.rong.common.util.DateTimeUtil;
import com.rong.persist.dao.ReportQqDao;
import com.rong.persist.model.ReportQq;

/**
 * 报表管理
 * @author Wenqiang-Rong
 * @date 2017年12月30日
 */
public class ReportController extends BaseController {
	private ReportQqDao reportQqDao = new ReportQqDao();

	/**
	 * 产量明细报表
	 */
	public void qqStatis() {
		int pageNumber = getParaToInt("page", 1);
		String time = getPara("time",DateTimeUtil.formatDateTime(new Date(),DateTimeUtil.DEFAULT_FORMAT_DAY));
		Kv param = Kv.by("time", time);
		Page<ReportQq> list = reportQqDao.page(pageNumber, pageSize, param);
		setAttr("page", list);
		setAttr("time", time);
		render("/views/report/qqStatis.jsp");
	}
}
