package com.rong.admin.controller;

import java.util.Date;

import com.jfinal.kit.Kv;
import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Page;
import com.rong.common.bean.BaseRenderJson;
import com.rong.common.bean.MyErrorCodeConfig;
import com.rong.persist.model.QqData;
import com.rong.persist.model.QqDataBase;
import com.rong.persist.model.QqDataBaseHistory;
import com.rong.user.service.QqDataBaseHistoryService;
import com.rong.user.service.QqDataBaseHistoryServiceImpl;
import com.rong.user.service.QqDataService;
import com.rong.user.service.QqDataServiceImpl;
import com.rong.user.service.QqTeamService;
import com.rong.user.service.QqTeamServiceImpl;

/**
 * qq数据管理
 * 
 * @author Wenqiang-Rong
 * @date 2017年12月26日
 */
public class QqDataController extends BaseController {
	private final Log logger = Log.getLog(this.getClass());
	private QqDataService qqDataService = new QqDataServiceImpl();
	private QqTeamService qqTeamService = new QqTeamServiceImpl();
	private QqDataBaseHistoryService qqDataBaseHistoryService = new QqDataBaseHistoryServiceImpl();

	/**
	 * QQ列表
	 */
	public void list() {
		int pageNumber = getParaToInt("page", 1);
		String qq = getPara("qq");
		Integer qqType = getParaToInt("qqType");
		Kv param = Kv.by("qq", qq).set("qqType", qqType);
		Page<QqData> list = qqDataService.list(pageNumber, pageSize, param);
		keepPara();
		setAttr("page", list);
		setAttr("nowDate", new Date());
		render("/views/qq/list.jsp");
	}
	
	/**
	 * QQ修改历史记录
	 */
	public void history() {
		int pageNumber = getParaToInt("page", 1);
		String qq = getPara("qq");
		Page<QqDataBaseHistory> list = qqDataBaseHistoryService.list(pageNumber, pageSize, qq);
		keepPara();
		setAttr("page", list);
		render("/views/qq/history.jsp");
	}

	/**
	 * 新增QQ
	 */
	public void add() {
		Integer qqType = getParaToInt("qqType");
		String qqData = getPara("qqData");
		String teamName = getPara("teamName");
		String tags = getPara("tags");
		String costPrice = getPara("costPrice");
		// 校验qqData格式是否正确
		boolean validSuccess = validQqData(qqType, qqData);
		if (!validSuccess) {// 校验失败
			BaseRenderJson.returnBaseTemplateObj(this, MyErrorCodeConfig.ERROR_BAD_REQUEST, "格式错误");
			return;
		}
		//保存编组
		long teamId = qqTeamService.save(teamName, Double.parseDouble(costPrice));
		String qqDataStrs[] = qqData.split("\n");
		for (int i = 0; i < qqDataStrs.length; i++) {
			String vals[] = qqDataStrs[i].split("----");
			String qq = vals[0];
			String qqPwd = vals[1];
			// 1.保存qqData
			saveQqData(qq, qqPwd, qqType, tags, teamId, teamName);
			// 2.保存qqDataBase
			saveQqDataBase(qqType, vals);
			// 3.保存qqDataBaseHistory
			saveQqDataBaseHistory(qqType, vals);
		}
		BaseRenderJson.returnAddObj(this, true);
		logger.info("[操作日志]入库成功");
	}
	
	/**
	 * 修改QQ密码
	 */
	public void updatePwd() {
		String qqData = getPara("qqData");
		// 校验qqData格式是否正确
		boolean validSuccess = validQqData(qqData);
		if (!validSuccess) {// 校验失败
			BaseRenderJson.returnBaseTemplateObj(this, MyErrorCodeConfig.ERROR_BAD_REQUEST, "格式错误");
			return;
		}
		String qqDataStrs[] = qqData.split("\n");
		for (int i = 0; i < qqDataStrs.length; i++) {
			String vals[] = qqDataStrs[i].split("----");
			String qq = vals[0];
			String qqPwd = vals[1];
			QqData qqDataModel = qqDataService.findByQq(qq);
			qqDataModel.setQqPwd(qqPwd);
			qqDataService.updatePwd(qqDataModel, vals);
		}
		BaseRenderJson.returnUpdateObj(this, true);
		logger.info("[操作日志]更新密码成功："+qqData);
	}
	
	/**
	 * 保存qqData
	 * @param qq
	 * @param qqPwd
	 * @param qqType
	 * @param tags
	 * @param teamId
	 * @param teamName
	 * @return
	 */
	private boolean saveQqData(String qq,String qqPwd,int qqType,String tags,long teamId,String teamName){
		Date now = new Date();
		QqData model = new QqData();
		model.setQq(qq);
		model.setQqPwd(qqPwd);
		model.setInStorageTime(now);
		model.setCreateTime(now);
		model.setLoginCount(0);
		model.setQqType(qqType);
		model.setState(true);
		model.setTags(tags);
		model.setTeamId(teamId);
		model.setTeamName(teamName);
		return model.save();
	}
	
	/**
	 * 保存QqDataBase
	 * @param qqType
	 * @param vals
	 * @return
	 */
	private boolean saveQqDataBase(int qqType,String vals []){
		QqDataBase qqDataBase = null;
		String qq = vals[0];
		String qqPwd = vals[1];
		String question1,question1_answer,question2,question2_answer,question3,question3_answer,mobile,token;
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
			token = vals[9];
			qqDataBase = new QqDataBase(qq, qqPwd, question1, question1_answer, question2, question2_answer, question3, question3_answer,mobile,token);
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
	private boolean saveQqDataBaseHistory(int qqType,String vals []){
		String qq = vals[0];
		String qqPwd = vals[1];
		String question1,question1_answer,question2,question2_answer,question3,question3_answer,mobile,token;
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
			token = vals[9];
			qqDataBaseHistory = new QqDataBaseHistory(qq, qqPwd, question1, question1_answer, question2, question2_answer, question3, question3_answer,mobile,token);
			break;
		default:
			return false;
		}
		return qqDataBaseHistory.save();
	}

	/**
	 * 校验入库qq格式
	 * 
	 * @param qqType
	 * @param qqData
	 * 格式如下：
	 * 白号 2383088706----xzzqt11480 
	 * 三问号 2383088706----xzzqt11480----您母亲的姓名是？----uesxvm----您高中班主任的名字是？----unojku----您父亲的姓名是？----uyzkox 
	 * 绑机号 2383088706----xzzqt11480----您母亲的姓名是？----uesxvm----您高中班主任的名字是？----unojku----您父亲的姓名是？----uyzkox----15243834134 
	 * 令牌号 2383088706----xzzqt11480----您母亲的姓名是？----uesxvm----您高中班主任的名字是？----unojku----您父亲的姓名是？----uyzkox----15243834134----token
	 * @return
	 */
	private boolean validQqData(int qqType, String qqData) {
		boolean valid = true;
		switch (qqType) {
		case 1:
			valid = validQqData(qqType, qqData, 2);
			break;
		case 2:
			valid = validQqData(qqType, qqData, 8);
			break;
		case 3:
			valid = validQqData(qqType, qqData, 9);
			break;
		case 4:
			valid = validQqData(qqType, qqData, 10);
			break;
		default:
			break;
		}
		logger.info("validQqData：" + qqData + "校验结果：" + valid);
		return valid;
	}
	
	private boolean validQqData(String qqData) {
		String qqDataStrs[] = qqData.split("\n");
		for (int i = 0; i < qqDataStrs.length; i++) {
			String vals[] = qqDataStrs[i].split("----");
			if (!(vals.length ==2 || vals.length==8 || vals.length==9 || vals.length==10)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 校验格式
	 * @param qqType
	 * @param qqData
	 * @param length
	 * @return
	 */
	private boolean validQqData(int qqType, String qqData, int length) {
		String qqDataStrs[] = qqData.split("\n");
		for (int i = 0; i < qqDataStrs.length; i++) {
			String vals[] = qqDataStrs[i].split("----");
			if (vals.length != length) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 删除
	 */
	public void delete() {
		Long id = getParaToLong("id");
		if (qqDataService.deleteById(id)) {
			BaseRenderJson.returnDelObj(this, true);
			logger.info("[操作日志]删除QQ数据id:" + id + "成功");
		} else {
			BaseRenderJson.returnDelObj(this, false);
			logger.error("[操作日志]删除QQ数据id:" + id + "失败");
		}

	}
}
