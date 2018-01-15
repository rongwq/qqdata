package com.rong.admin.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

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
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public void qqStatis() throws IllegalAccessException, InvocationTargetException {
		int pageNumber = getParaToInt("page", 1);
		String time = getPara("time");
		Kv param = Kv.by("time", time);
		Page<ReportQq> list = reportQqDao.page(pageNumber, pageSize, param);
		Page<ReportQq> returnList = list;
		boolean isnow = DateTimeUtil.formatDateTime(new Date(),DateTimeUtil.DEFAULT_FORMAT_DAY).equals(time);
		if(time==null || isnow){
			List<ReportQq> listNowStatis = reportQqDao.qqStatisNow();
			listNowStatis.addAll(list.getList());
			returnList = new Page<>(listNowStatis, pageNumber, pageSize, list.getTotalPage(), list.getTotalRow());
		}
		setAttr("page", returnList);
		setAttr("time", time);
		render("/views/report/qqStatis.jsp");
	}
}
