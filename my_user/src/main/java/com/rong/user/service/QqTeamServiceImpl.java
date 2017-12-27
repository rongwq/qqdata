package com.rong.user.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.jfinal.plugin.activerecord.Page;
import com.rong.persist.base.BaseServiceImpl;
import com.rong.persist.dao.QqTeamDao;
import com.rong.persist.model.QqTeam;

/**
 * QQ编组服务实现层
 * @author Wenqiang-Rong
 * @date 2017年12月26日
 */
public class QqTeamServiceImpl extends BaseServiceImpl<QqTeam> implements QqTeamService{
	private QqTeamDao dao = new QqTeamDao();

	@Override
	public Page<QqTeam> list(int pageNumber, int pageSize) {
		return dao.page(pageNumber, pageSize, null);
	}

	@Override
	public QqTeam find(String name) {
		Map<String, Object> paraMap = new HashMap<>();
		paraMap.put("name", name);
		return dao.findFirst(paraMap, "", false);
	}

	@Override
	public Long save(String name, double costPrice) {
		QqTeam qqTeam = find(name);
		if(qqTeam==null){
			qqTeam = new QqTeam();
			qqTeam.setCostPrice(costPrice);
			qqTeam.setName(name);
			qqTeam.setCreateTime(new Date());
			dao.save(qqTeam);
		}
		return qqTeam.getId();
	}
}
