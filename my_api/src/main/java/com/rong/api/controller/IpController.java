package com.rong.api.controller;

import java.util.HashMap;
import java.util.Map;

import com.jfinal.core.Controller;
import com.jfinal.log.Log;
import com.rong.common.bean.BaseRenderJson;
import com.rong.common.bean.MyErrorCodeConfig;
import com.rong.common.validator.CommonValidatorUtils;
import com.rong.persist.model.IpTemp;
import com.rong.user.service.IpTempService;
import com.rong.user.service.IpTempServiceImpl;

/**
 * 临时IP库接口
 * @author Wenqiang-Rong
 * @date 2017年12月29日
 */
public class IpController extends Controller{
	private final Log logger = Log.getLog(this.getClass());
	private IpTempService ipTempService = new IpTempServiceImpl();
	/**
	 * 新增IP
	 */
	public void save(){
		String ip = getPara("ip");
		String address = getPara("address");
		Map<String , Object> paramMap = new HashMap<>();
		paramMap.put("ip", ip);
		paramMap.put("address", address);
		if(CommonValidatorUtils.requiredValidate(paramMap, this)){
			return;
		}
		IpTemp ipTemp = ipTempService.findByIp(ip);
		if (ipTemp != null) {
			BaseRenderJson.apiReturnJson(this, MyErrorCodeConfig.ERROR_BAD_REQUEST, ip+"已存在");
			return;
		}
		ipTempService.save(ip, address);
		logger.info("[api]新增IP成功："+ip+",归属地："+address);
		BaseRenderJson.apiReturnJson(this, MyErrorCodeConfig.REQUEST_SUCCESS, "保存成功");
	}
}
