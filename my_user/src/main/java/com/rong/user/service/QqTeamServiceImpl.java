package com.rong.user.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.rong.persist.base.BaseServiceImpl;
import com.rong.persist.dao.QqDataDao;
import com.rong.persist.dao.QqTeamDao;
import com.rong.persist.model.QqData;
import com.rong.persist.model.QqTeam;

/**
 * QQ编组服务实现层
 * @author Wenqiang-Rong
 * @date 2017年12月26日
 */
public class QqTeamServiceImpl extends BaseServiceImpl<QqTeam> implements QqTeamService{
	private QqTeamDao dao = new QqTeamDao();
	private QqDataDao qqDataDao = new QqDataDao();

	@Override
	public Page<QqTeam> list(int pageNumber, int pageSize) {
		return dao.page(pageNumber, pageSize, null);
	}

	@Override
	public QqTeam find(String name) {
		Map<String, Object> paraMap = new HashMap<>();
		paraMap.put("team_name", name);
		return dao.findFirst(paraMap, "", false);
	}

	@Override
	public Long save(String name, double costPrice) {
		QqTeam qqTeam = find(name);
		if(qqTeam==null){
			qqTeam = new QqTeam();
			qqTeam.setCostPrice(costPrice);
			qqTeam.setTeamName(name);
			qqTeam.setCreateTime(new Date());
			dao.save(qqTeam);
		}
		return qqTeam.getId();
	}

	@Override
	public boolean updateName(long id, String name) {
		QqTeam qqTeam = findById(id);
		qqTeam.setTeamName(name);
		boolean result = qqTeam.update();
		if(result==true){
			//更新qqData中的teamName
			String sql = "select "+QqDataDao.FILEDS+" from " + QqData.TABLE +" where team_id = ?";
			List<QqData> qqDataList = qqDataDao.find(sql,id);
			for (QqData qqData : qqDataList) {
				qqData.setTeamName(name);
			}
			Db.batchUpdate(qqDataList, 100);
		}
		return result;
	}
	
	@Override
	public boolean deleteById(long id){
		boolean result = super.deleteById(id);
		if(result){
			//删除所有编组下面的qq
			String sql = "select "+QqDataDao.FILEDS+" from " + QqData.TABLE +" where team_id = ?";
			List<QqData> qqDataList = qqDataDao.find(sql,id);
			for (QqData qqData : qqDataList) {
				qqData.delete();
			}
		}
		return result;
	}
}
