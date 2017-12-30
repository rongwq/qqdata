package com.rong.admin.controller;

import java.io.File;
import java.util.Date;
import java.util.List;

import com.jfinal.kit.Kv;
import com.jfinal.kit.PathKit;
import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Page;
import com.rong.common.bean.BaseRenderJson;
import com.rong.common.bean.MyErrorCodeConfig;
import com.rong.common.util.CommonUtil;
import com.rong.common.util.DateTimeUtil;
import com.rong.common.util.TxtExportUtil;
import com.rong.common.util.ZipUtil;
import com.rong.persist.enums.QqDataTypeEnum;
import com.rong.persist.model.QqData;
import com.rong.persist.model.QqDataBaseHistory;
import com.rong.persist.model.QqUpdatePwdWait;
import com.rong.user.service.QqDataBaseHistoryService;
import com.rong.user.service.QqDataBaseHistoryServiceImpl;
import com.rong.user.service.QqDataService;
import com.rong.user.service.QqDataServiceImpl;
import com.rong.user.service.QqTeamService;
import com.rong.user.service.QqTeamServiceImpl;
import com.rong.user.service.QqUpdatePwdWaitService;
import com.rong.user.service.QqUpdatePwdWaitServiceImpl;

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
	private QqUpdatePwdWaitService qqUpdatePwdWaitService = new QqUpdatePwdWaitServiceImpl();

	/**
	 * QQ列表
	 */
	public void list() {
		Page<QqData> list = pageList(pageSize);
		keepPara();
		setAttr("page", list);
		setAttr("nowDate", new Date());
		render("/views/qq/list.jsp");
	}

	private Page<QqData> pageList(int pagaSize) {
		int pageNumber = getParaToInt("page", 1);
		String qq = getPara("qq");
		Integer qqType = getParaToInt("qqType");
		Integer state = getParaToInt("state");
		Integer storageState = getParaToInt("storageState");
		String teamName = getPara("teamName");
		Integer isHaveTag = getParaToInt("isHaveTag");
		String tags = getPara("tags");
		Integer qAgeMin = getParaToInt("qAgeMin");
		Integer qAgeMax = getParaToInt("qAgeMax");
		Integer qqLength = getParaToInt("qqLength");
		Kv param = Kv.by("qq", qq).set("qqType", qqType)
				.set("storageState", storageState)
				.set("state", state)
				.set("teamName", teamName)
				.set("isHaveTag", isHaveTag)
				.set("tags", tags)
				.set("qAgeMin", qAgeMin)
				.set("qAgeMax", qAgeMax)
				.set("qqLength", qqLength);
		Page<QqData> list = qqDataService.list(pageNumber, pagaSize, param);
		return list;
	}
	
	/**
	 * 导出QQ列表
	 */
	public void exportTxt() {
		List<QqData> list = pageList(pageSize).getList();
		StringBuffer write = new StringBuffer();
		String tab = "  ";
		String enter = "\r\n";
		write.append("编号" + tab).append("QQ" + tab).append("PWD" + tab).append("所属分类" + tab).append("编组" + tab);
		write.append("标签" + tab).append("入库时间" + tab).append("Q龄" + tab).append("登录次数" + tab).append("仓库状态" + tab);
		write.append("使用状态" + tab).append("出库时间" + tab).append("剩余天数" + tab);
		write.append(enter);
		for (QqData qqData : list) {
			write.append(qqData.getId() + tab).append(qqData.getQq() + tab).append(qqData.getQqPwd() + tab)
			.append(QqDataTypeEnum.getName(qqData.getQqType()) + tab).append(qqData.getTeamName() + tab);
			
			Date outStorageTime = qqData.getOutStorageTime();
			write.append(qqData.getTags() + tab).append(DateTimeUtil.formatDateTime(qqData.getInStorageTime(),"yyyy-MM-dd HH:mm")+ tab).append(qqData.getQqAge() + tab)
			.append(qqData.getLoginCount() + tab).append((outStorageTime==null?"未出库":"出库") + tab);
			
			String haveDaysStr = "-";
			if(outStorageTime!=null){
				int haveDays = DateTimeUtil.getBetweenDay(outStorageTime, new Date());
				haveDaysStr = String.valueOf(qqData.getOutStorageDays() - haveDays);
			}
			write.append((qqData.getState()?"可用":"已冻结") + tab).append(outStorageTime==null?"-":DateTimeUtil.formatDateTime(outStorageTime,"yyyy-MM-dd HH:mm") + tab).append(haveDaysStr + tab);
			
			write.append(enter);
		}
		String webPath = PathKit.getWebRootPath();
		String pathname = webPath + File.separator + "qq导出数据.txt";
		File file = new File(pathname);
		try {
			TxtExportUtil.createFile(file);
			TxtExportUtil.writeTxtFile(write.toString(), file);
			ZipUtil.zip(pathname, webPath, "qq导出数据.zip");
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderFile(file);
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
		String qqData = getPara("qqData");
		String teamName = getPara("teamName");
		String tags = getPara("tags");
		String costPrice = getPara("costPrice");
		// 校验qqData格式是否正确
		boolean validSuccess = CommonUtil.validQqData(qqData);
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
			int qqType = 1;
			if(vals.length==2){
				qqType = 1;
			}else if(vals.length==8){
				qqType = 2;
			}else if(vals.length==9){
				qqType = 3;
			}else if(vals.length==10){
				qqType = 4;
			}else{
				qqType = 1;
			}
			// 1.保存qqData
			qqDataService.saveQqData(qq, qqPwd, qqType, tags, teamId, teamName);
			// 2.保存qqDataBase
			qqDataService.saveQqDataBase(qqType, vals);
			// 3.保存qqDataBaseHistory
			qqDataService.saveQqDataBaseHistory(qqType, vals);
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
		boolean validSuccess = CommonUtil.validQqData(qqData);
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
	 * 修改QQ标签
	 */
	public void updateTag() {
		String qqData = getPara("qqData");
		String tags = getPara("tags");
		String qqDataStrs[] = qqData.split("\n");
		for (int i = 0; i < qqDataStrs.length; i++) {
			String qq = qqDataStrs[i];
			QqData qqDataModel = qqDataService.findByQq(qq);
			qqDataModel.setTags(tags + "、"+ (qqDataModel.getTags()==null?"":qqDataModel.getTags()));
			qqDataModel.update();
		}
		BaseRenderJson.returnUpdateObj(this, true);
		logger.info("[操作日志]更新标签成功："+qqData+"，新增标签："+tags);
	}
	
	/**
	 * 出库
	 */
	public void outStorage() {
		String qqData [] = getParaValues("qq");
		String tags = getPara("tags");
		Integer outStorageDays = getParaToInt("outStorageDays");
		for (int i = 0; i < qqData.length; i++) {
			String qq = qqData[i];
			QqData qqDataModel = qqDataService.findByQq(qq);
			qqDataModel.setTags(tags + "、"+ qqDataModel.getTags());
			qqDataModel.setOutStorageTime(new Date());
			qqDataModel.setOutStorageDays(outStorageDays);
			qqDataModel.update();
		}
		BaseRenderJson.returnUpdateObj(this, true);
		logger.info("[操作日志]出库成功："+qqData);
	}
	
	/**
	 * 新增待修改密码的QQ
	 */
	public void addUpdatePwdWait() {
		String qqData = getPara("qqData");
		String qqDataStrs[] = qqData.split("\n");
		Date now = new Date();
		for (int i = 0; i < qqDataStrs.length; i++) {
			String vals[] = qqDataStrs[i].split("----");
			QqUpdatePwdWait waitPwd = new QqUpdatePwdWait();
			waitPwd.setCreateTime(now);
			waitPwd.setQq(vals[0]);
			waitPwd.setTokenCode(vals[1]);
			waitPwd.save();
		}
		BaseRenderJson.returnAddObj(this, true);
		logger.info("[操作日志]新增待修改密码的QQ成功："+qqData);
	}
	
	/**
	 * 待修改密码的QQ列表
	 */
	public void qqUpdatePwdWaitList() {
		int pageNumber = getParaToInt("page", 1);
		Page<QqUpdatePwdWait> list = qqUpdatePwdWaitService.list(pageNumber, pageSize);
		keepPara();
		setAttr("page", list);
		setAttr("nowDate", new Date());
		render("/views/qq/listQqUpdatePwdWait.jsp");
	}
	
	/**
	 * 删除待修改密码的QQ
	 */
	public void deleteUpdatePwdWait() {
		Long id = getParaToLong("id");
		if (qqUpdatePwdWaitService.deleteById(id)) {
			BaseRenderJson.returnDelObj(this, true);
			logger.info("[操作日志]删除待修改密码的QQid:" + id + "成功");
		} else {
			BaseRenderJson.returnDelObj(this, false);
			logger.error("[操作日志]删除待修改密码的QQid:" + id + "失败");
		}
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
