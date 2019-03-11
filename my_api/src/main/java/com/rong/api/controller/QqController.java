package com.rong.api.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.kit.Kv;
import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.rong.common.bean.BaseRenderJson;
import com.rong.common.bean.MyConst;
import com.rong.common.bean.MyErrorCodeConfig;
import com.rong.common.util.CommonUtil;
import com.rong.common.util.DateTimeUtil;
import com.rong.common.validator.CommonValidatorUtils;
import com.rong.persist.enums.QqDataTypeEnum;
import com.rong.persist.model.QqData;
import com.rong.persist.model.QqDataBase;
import com.rong.persist.model.QqUpdatePwdWait;
import com.rong.user.service.QqDataBaseService;
import com.rong.user.service.QqDataBaseServiceImpl;
import com.rong.user.service.QqDataService;
import com.rong.user.service.QqDataServiceImpl;
import com.rong.user.service.QqTeamService;
import com.rong.user.service.QqTeamServiceImpl;
import com.rong.user.service.QqUpdatePwdWaitService;
import com.rong.user.service.QqUpdatePwdWaitServiceImpl;

/**
 * 临时IP库接口
 * @author Wenqiang-Rong
 * @date 2017年12月29日
 */
public class QqController extends Controller{
	private final Log logger = Log.getLog(this.getClass());
	private QqDataService qqDataService = new QqDataServiceImpl();
	private QqDataBaseService qqDataBaseService = new QqDataBaseServiceImpl();
	private QqTeamService qqTeamService = new QqTeamServiceImpl();
	private QqUpdatePwdWaitService qqUpdatePwdWaitService = new QqUpdatePwdWaitServiceImpl();
	
	/**
	 * 贴标签-支持批量操作，格式：{'qqs':'qq1;qq2;qq3','tags':'tag1、tag2'}
	 */
	@Before(Tx.class)
	public void updateTags(){
		String qqs = getPara("qqs");
		String tags = getPara("tags");
		Map<String , Object> paramMap = new HashMap<>();
		paramMap.put("qqs", qqs);
		paramMap.put("tags", tags);
		if(CommonValidatorUtils.requiredValidate(paramMap, this)){
			return;
		}
		String qqDataStrs[] = qqs.split(";");
		for (int i = 0; i < qqDataStrs.length; i++) {
			String qq = qqDataStrs[i];
			QqData qqDataModel = qqDataService.findByQq(qq);
			if(qqDataModel!=null){
				qqDataModel.setTags(tags + "、"+ (qqDataModel.getTags()==null?"":qqDataModel.getTags()));
				qqDataModel.update();
			}
		}
		logger.info("[api]更新标签成功："+qqs+"，新增标签："+tags);
		BaseRenderJson.apiReturnJson(this, MyErrorCodeConfig.REQUEST_SUCCESS, "贴标签成功");
	}
	
	/**
	 * 新增QQ
	 */
	@Before(Tx.class)
	public void add() {
		String qqs = getPara("qqs");
		String costPrice = getPara("costPrice","1");
		String defaultTeamName = DateTimeUtil.formatDateTime(new Date(),"yyyyMMdd");
		String teamName = getPara("teamName",defaultTeamName);
		// 校验qqData格式是否正确
		boolean validSuccess = CommonUtil.validQqData4Api(qqs);
		if (!validSuccess) {// 校验失败
			BaseRenderJson.returnBaseTemplateObj(this, MyErrorCodeConfig.ERROR_BAD_REQUEST, "格式错误");
			return;
		}
		//保存编组
		long teamId = qqTeamService.save(teamName, Double.parseDouble(costPrice));
		String qqDataStrs[] = qqs.split(";");
		for (int i = 0; i < qqDataStrs.length; i++) {
			String vals[] = qqDataStrs[i].split("-");
			String qq = vals[0];
			String qqPwd = vals[1];
			int qqType = 1;
			if(vals.length==2){
				qqType = 1;
			}else if(vals.length==8){
				qqType = 2;
			}else if(vals.length==9){
				qqType = 3;
			}else if(vals.length==10){
				qqType = 4;
			}else if(vals.length==3){
				qqType = 5;
			}else if(vals.length==4){
				qqType = 6;
			}else{
				qqType = 1;
			}
			// 1.保存qqData
			qqDataService.saveQqData(vals,qq, qqPwd, qqType, null, teamId, teamName);
		}
		BaseRenderJson.apiReturnJson(this, MyErrorCodeConfig.REQUEST_SUCCESS, "qq入库成功");
		logger.info("[api]qq入库成功");
	}
	
	/**
	 * 修改QQ密码
	 */
	@Before(Tx.class)
	public void updatePwd() {
		String qq = getPara("qq");
		String pwd = getPara("pwd");
		Map<String , Object> paramMap = new HashMap<>();
		paramMap.put("qq", qq);
		paramMap.put("pwd", pwd);
		if(CommonValidatorUtils.requiredValidate(paramMap, this)){
			return;
		}
		QqData qqDataModel = qqDataService.findByQq(qq);
		if(qqDataModel==null){
			BaseRenderJson.returnBaseTemplateObj(this, MyErrorCodeConfig.ERROR_BAD_REQUEST, "qq不存在");
			return;
		}
		qqDataModel.setQqPwd(pwd);
		boolean result = qqDataModel.update();
		if(result){
			//修改密码成功，删除待改密qq表记录
			QqUpdatePwdWait waitUpdatePwdQq = qqUpdatePwdWaitService.findByQq(qq);
			if(waitUpdatePwdQq!=null){
				waitUpdatePwdQq.delete();
				logger.info("[api]更新密码成功-删除待修改qq密码表数据："+qq+",pwd:"+pwd);
			}
		}
		BaseRenderJson.apiReturnJson(this, MyErrorCodeConfig.REQUEST_SUCCESS, "更新密码成功");
		logger.info("[api]更新密码成功："+qq+",pwd:"+pwd);
	}
	
	/**
	 * 获取1个待修改密码的qq
	 */
	public void getWaitUpdatePwdQq() {
		QqUpdatePwdWait waitUpdatePwdQq = qqUpdatePwdWaitService.getFisrt();
		Record record = new Record();
		record.set("qq", waitUpdatePwdQq.getQq());
		record.set("tokenCode", waitUpdatePwdQq.getTokenCode());
		BaseRenderJson.returnObjectTmplate(this, MyErrorCodeConfig.REQUEST_SUCCESS, record, "查询成功");
	}
	
	/**
	 * 登录
	 * 成功，修改QQ状态,登录次数和token
	 * 冻结，则把类型是令牌号的QQ自动提交等待修改密码表
	 */
	@Before(Tx.class)
	public void qqLogin() {
		String qq = getPara("qq");
		Boolean state = getParaToBoolean("state");//1-可用，0已冻结
		String token = getPara("qqToken");
		Map<String , Object> paramMap = new HashMap<>();
		paramMap.put("qq", qq);
		paramMap.put("state", state);
		if(CommonValidatorUtils.requiredValidate(paramMap, this)){
			return;
		}
		QqData qqDataModel = qqDataService.findByQq(qq);
		if(qqDataModel==null){
			BaseRenderJson.returnBaseTemplateObj(this, MyErrorCodeConfig.ERROR_BAD_REQUEST, "qq不存在");
			return;
		}
		if(state){//可用
			qqDataModel.setState(MyConst.QQSTATE_ENABLE);
			qqDataModel.setLoginTime(new Date());
			qqDataModel.setLoginCount(qqDataModel.getLoginCount()+1);
			boolean result = qqDataModel.update();
			if(result){
				//修改成功，修改token
				QqDataBase qqDataBase = qqDataBaseService.findByQq(qq);
				if(qqDataBase!=null){
					qqDataBase.setToken(token);
					qqDataBase.update();
					logger.info("[api]登录-更新token成功："+qq+",state:"+state+",token:"+token);
				}
			}
		}else{//冻结，则把类型是令牌号的QQ自动提交等待修改密码表
			if(qqDataModel.getQqType()==QqDataTypeEnum.TOKEN.getIndex()){
				QqUpdatePwdWait qqUpdatePwdWait = new QqUpdatePwdWait();
				qqUpdatePwdWait.setQq(qq);
				qqUpdatePwdWait.setCreateTime(new Date());
				QqDataBase qqDataBase = qqDataBaseService.findByQq(qq);
				qqUpdatePwdWait.setTokenCode(qqDataBase.getTokenCode());
				if(qqUpdatePwdWaitService.findByQq(qq)==null){
					qqUpdatePwdWait.save();
				}
			}
		}
		BaseRenderJson.apiReturnJson(this, MyErrorCodeConfig.REQUEST_SUCCESS, "登录成功");
		logger.info("[api]登录成功："+qq+",state:"+state);
	}
	
	/**
	 * QQ列表,按编组查询
	 */
	public void list() {
		Page<QqData> list = pageList();
		List<Record> returnList = new ArrayList<>();
		for (QqData qqData : list.getList()) {
			Record record = new Record();
			record.set("qq", qqData.getQq());
			record.set("pwd", qqData.getQqPwd());
			//后续需优化，关联查询
			QqDataBase qqDataBase = qqDataBaseService.findByQq(qqData.getQq());
			if(qqDataBase!=null){
				record.set("token", qqDataBase.getToken());
			}
			returnList.add(record);
		}
		Page<Record> returnPage = new Page<>(returnList, list.getPageNumber(), list.getPageSize(), list.getTotalPage(), list.getTotalRow());
		BaseRenderJson.returnObjectTmplate(this, MyErrorCodeConfig.REQUEST_SUCCESS, returnPage, "获取qq列表成功");
	}

	private Page<QqData> pageList() {
		int pageNumber = getParaToInt("page", 1);
		int pageSize = getParaToInt("pageSize", 10);
		String teamName = getPara("teamName");
		Kv param = Kv.by("teamName", teamName);
		Page<QqData> list = qqDataService.list(pageNumber, pageSize, param);
		return list;
	}
	
	/**
	 * 当天未登录QQ列表
	 */
	public void notLoginList() {
		int pageNumber = getParaToInt("page", 1);
		int pageSize = getParaToInt("pageSize", 10);
		Kv param = Kv.by("notLoginToday",true);
		Page<QqData> list = qqDataService.list(pageNumber, pageSize, param);
		List<Record> returnList = new ArrayList<>();
		for (QqData qqData : list.getList()) {
			Record record = new Record();
			record.set("qq", qqData.getQq());
			record.set("pwd", qqData.getQqPwd());
			//后续需优化，关联查询
			QqDataBase qqDataBase = qqDataBaseService.findByQq(qqData.getQq());
			if(qqDataBase!=null){
				record.set("token", qqDataBase.getToken());
			}else{
				record.set("token", null);
			}
			returnList.add(record);
		}
		Page<Record> returnPage = new Page<>(returnList, list.getPageNumber(), list.getPageSize(), list.getTotalPage(), list.getTotalRow());
		BaseRenderJson.returnObjectTmplate(this, MyErrorCodeConfig.REQUEST_SUCCESS, returnPage, "获取当天未登录QQ列表成功");
	}
	
	/**
	 * QQ列表，返回参数只包括qq号码
	 */
	public void qqList() {
		int pageNumber = getParaToInt("page", 1);
		int pageSize = getParaToInt("pageSize", 10);
		Page<QqData> list = qqDataService.list(pageNumber, pageSize, null);
		List<String> returnList = new ArrayList<>();
		for (QqData qqData : list.getList()) {
			returnList.add(qqData.getQq());
		}
		Page<String> returnPage = new Page<>(returnList, list.getPageNumber(), list.getPageSize(), list.getTotalPage(), list.getTotalRow());
		BaseRenderJson.returnObjectTmplate(this, MyErrorCodeConfig.REQUEST_SUCCESS, returnPage, "获取QQ列表成功");
	}
}
