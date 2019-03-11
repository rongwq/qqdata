package com.rong.user.service;

import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Page;
import com.rong.common.bean.MyConst;
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
	
	/**
	 * 保存qqData，再次保存QqDataBase，保存saveQqDataBaseHistory
	 * @param qq
	 * @param qqPwd
	 * @param qqType
	 * @param tags
	 * @param teamId
	 * @param teamName
	 * @return
	 */
	public boolean saveQqData(String vals [],String qq,String qqPwd,int qqType,String tags,long teamId,String teamName){
		Date now = new Date();
		QqData model = new QqData();
		model.setQq(qq);
		model.setQqPwd(qqPwd);
		model.setInStorageTime(now);
		model.setCreateTime(now);
		model.setLoginCount(0);
		//2019-1-14 数据类型合并
		if(qqType==5){
			model.setQqType(3);
		}else if(qqType==6){
			model.setQqType(4);
		}else{
			model.setQqType(qqType);
		}
		model.setState(MyConst.QQSTATE_ENABLE);
		model.setTags(tags);
		model.setTeamId(teamId);
		model.setTeamName(teamName);
		QqData item = findByQq(qq);
		if(item==null){
			model.save();
		}else{
			model.setId(item.getId());
			model.update();
		}
		saveQqDataBase(qqType, vals);
		saveQqDataBaseHistory(qqType, vals);
		return true;
	}
	
	/**
	 * 保存QqDataBase
	 * @param qqType
	 * @param vals
	 * @return
	 */
	public boolean saveQqDataBase(int qqType,String vals []){
		QqDataBase qqDataBase = null;
		String qq = vals[0];
		String qqPwd = vals[1];
		String question1,question1_answer,question2,question2_answer,question3,question3_answer,mobile,tokenCode;
		switch (qqType) {
		case 1:// 白号
			qqDataBase = new QqDataBase(qq, qqPwd);
			break;
		case 2:// 三问号
			question1 = vals[2];
			question1_answer = vals[3];
			question2 = vals[4];
			question2_answer = vals[5];
			question3 = vals[6];
			question3_answer = vals[7];
			qqDataBase = new QqDataBase(qq, qqPwd, question1, question1_answer, question2, question2_answer, question3, question3_answer);
			break;
		case 3:// 绑机号
			question1 = vals[2];
			question1_answer = vals[3];
			question2 = vals[4];
			question2_answer = vals[5];
			question3 = vals[6];
			question3_answer = vals[7];
			mobile = vals[8];
			qqDataBase = new QqDataBase(qq, qqPwd, question1, question1_answer, question2, question2_answer, question3, question3_answer,mobile);
			break;
		case 4:// 令牌号
			question1 = vals[2];
			question1_answer = vals[3];
			question2 = vals[4];
			question2_answer = vals[5];
			question3 = vals[6];
			question3_answer = vals[7];
			mobile = vals[8];
			tokenCode = vals[9];
			qqDataBase = new QqDataBase(qq, qqPwd, question1, question1_answer, question2, question2_answer, question3, question3_answer,mobile,tokenCode);
			break;
		case 5:// 2018-12-3：新增绑定手机号
			mobile = vals[2];
			qqDataBase = new QqDataBase(qq, qqPwd,mobile);
			break;
		case 6:// 2018-12-3：新增绑定手机号+token
			mobile = vals[2];
			tokenCode = vals[3];
			qqDataBase = new QqDataBase(qq, qqPwd,mobile,tokenCode);
			break;
		default:
			return false;
		}
		return qqDataBase.save();
	}
	
	/**
	 * 保存QqDataBaseHistory
	 * @param qqType
	 * @param vals
	 * @return
	 */
	public boolean saveQqDataBaseHistory(int qqType,String vals []){
		String qq = vals[0];
		String qqPwd = vals[1];
		String question1,question1_answer,question2,question2_answer,question3,question3_answer,mobile,tokenCode;
		QqDataBaseHistory qqDataBaseHistory = null;
		switch (qqType) {
		case 1:// 白号
			qqDataBaseHistory = new QqDataBaseHistory(qq, qqPwd);
			break;
		case 2:// 三问号
			question1 = vals[2];
			question1_answer = vals[3];
			question2 = vals[4];
			question2_answer = vals[5];
			question3 = vals[6];
			question3_answer = vals[7];
			qqDataBaseHistory = new QqDataBaseHistory(qq, qqPwd, question1, question1_answer, question2, question2_answer, question3, question3_answer);
			break;
		case 3:// 绑机号
			question1 = vals[2];
			question1_answer = vals[3];
			question2 = vals[4];
			question2_answer = vals[5];
			question3 = vals[6];
			question3_answer = vals[7];
			mobile = vals[8];
			qqDataBaseHistory = new QqDataBaseHistory(qq, qqPwd, question1, question1_answer, question2, question2_answer, question3, question3_answer,mobile);
			break;
		case 4:// 令牌号
			question1 = vals[2];
			question1_answer = vals[3];
			question2 = vals[4];
			question2_answer = vals[5];
			question3 = vals[6];
			question3_answer = vals[7];
			mobile = vals[8];
			tokenCode = vals[9];
			qqDataBaseHistory = new QqDataBaseHistory(qq, qqPwd, question1, question1_answer, question2, question2_answer, question3, question3_answer,mobile,tokenCode);
			break;
		case 5:// 2018-12-3：新增绑定手机号
			mobile = vals[2];
			qqDataBaseHistory = new QqDataBaseHistory(qq, qqPwd,mobile);
			break;
		case 6:// 2018-12-3：新增绑定手机号+token
			mobile = vals[2];
			tokenCode = vals[3];
			qqDataBaseHistory = new QqDataBaseHistory(qq, qqPwd,mobile,tokenCode);
			break;
		default:
			return false;
		}
		return qqDataBaseHistory.save();
	}
}
