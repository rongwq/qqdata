package com.rong.admin.config;

import org.apache.log4j.MDC;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.rong.common.bean.BaseRenderJson;
import com.rong.common.bean.MyConst;
import com.rong.common.bean.MyErrorCodeConfig;
import com.rong.common.util.RequestUtils;
import com.rong.persist.model.SystemAdmin;

/**
 * 公共拦截器
 * @author Wenqiang-Rong
 * @date 2017年12月21日
 */
public class CommonInterceptor implements Interceptor {

	@Override
	public void intercept(Invocation ai) {
		try {
			myIntercept(ai);
		} catch (Exception e) {
			e.printStackTrace();
			BaseRenderJson.returnBaseTemplateObj(ai.getController(), MyErrorCodeConfig.REQUEST_FAIL, "系统异常");
		}
	}

	private void myIntercept(Invocation ai) {
		Controller controller = ai.getController();
		String url = controller.getRequest().getRequestURL().toString();
		String[] temp = url.split("/");
		//验证码部分不需要做判断
		if ("captcha".equals(temp[temp.length - 1]) || "login".equals(temp[temp.length - 1]) || temp.length == 4) {
			ai.invoke();
			return;
		}
		//判断登陆后权限
		SystemAdmin u = controller.getSessionAttr(MyConst.SESSION_KEY);
		if(u!=null){
			MDC.put("userId", u.getUserName());//设置log4j的用户id-不同线程都有自己的MDC的key
			MDC.put("ip",RequestUtils.getRequestIpAddress(controller.getRequest()));
			ai.invoke();
		} else {
			controller.redirect("/");
		}
		return;
	}

}
