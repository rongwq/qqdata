package com.rong.user.service;

import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Page;
import com.rong.persist.base.BaseServiceImpl;
import com.rong.persist.dao.QqDataBaseDao;
import com.rong.persist.dao.QqDataBaseHistoryDao;
import com.rong.persist.dao.QqDataDao;
import com.rong.persist.model.QqData;
import com.rong.persist.model.QqDataBase;
import com.rong.persist.model.QqDataBaseHistory;

/**
 * QQ数据服务实现层
 * @author Wenqiang-Rong
 * @date 2017年12月26日
 */
public class QqDataServiceImpl extends BaseServiceImpl<QqData> implements QqDataService{
	private QqDataDao dao = new QqDataDao();
	private QqDataBaseDao qqDataBaseDao = new QqDataBaseDao();
	private QqDataBaseHistoryDao qqDataBaseHistoryDao = new QqDataBaseHistoryDao();

	@Override
	public Page<QqData> list(int pageNumber, int pageSize, Kv param) {
		return dao.page(pageNumber, pageSize, param);
	}

	@Override
	public QqData findByQq(String qq) {
		return dao.findByQq(qq);
	}

	/**
	 * 修改密码
	 * QQ密码发生改变，需要删除该QQ的token
	 * 修改密码的类型发生改，自动匹配
	 */
	@Override
	public boolean updatePwd(QqData qqData,String vals []) {
		//1.更新前，先记录更新前的数据
		QqDataBase qqDataBase = qqDataBaseDao.findByQq(qqData.getQq());
		int qqType = qqData.getQqType();
		qqDataBase.setToken(null);
		qqDataBase.setPwd(qqData.getQqPwd());
		//2.如果qq类型不一致，并且修改类型高于qq类型大于
		int len = QqData.getQqTypeByValLength(vals.length).getIndex();
		if(len>=qqType){
			qqData.setQqType(len);
			switch (len) {
			case 2:
				qqDataBase.setQuestion1(vals[2]);
				qqDataBase.setQuestion1Answer(vals[3]);
				qqDataBase.setQuestion2(vals[4]);
				qqDataBase.setQuestion2Answer(vals[5]);
				qqDataBase.setQuestion3(vals[6]);
				qqDataBase.setQuestion3Answer(vals[7]);
				break;
			case 3:
				qqDataBase.setQuestion1(vals[2]);
				qqDataBase.setQuestion1Answer(vals[3]);
				qqDataBase.setQuestion2(vals[4]);
				qqDataBase.setQuestion2Answer(vals[5]);
				qqDataBase.setQuestion3(vals[6]);
				qqDataBase.setQuestion3Answer(vals[7]);
				qqDataBase.setMobile(vals[8]);
				break;
			case 4:
				qqDataBase.setQuestion1(vals[2]);
				qqDataBase.setQuestion1Answer(vals[3]);
				qqDataBase.setQuestion2(vals[4]);
				qqDataBase.setQuestion2Answer(vals[5]);
				qqDataBase.setQuestion3(vals[6]);
				qqDataBase.setQuestion3Answer(vals[7]);
				qqDataBase.setMobile(vals[8]);
				qqDataBase.setToken(vals[9]);
				break;
			default:
				break;
			}
		}
		qqDataBaseDao.update(qqDataBase);
		QqDataBaseHistory qqDataBaseHistory = new QqDataBaseHistory();
		try {
			BeanUtils.copyProperties(qqDataBaseHistory,qqDataBase);
		} catch (Exception e) {
			e.printStackTrace();
		}
		qqDataBaseHistory.setId(null);
		qqDataBaseHistory.setCreateTime(new Date());
		qqDataBaseHistoryDao.save(qqDataBaseHistory);
		dao.update(qqData);
		return true;
	}
}
