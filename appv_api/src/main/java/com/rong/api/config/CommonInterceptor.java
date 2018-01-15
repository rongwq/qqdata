package com.rong.api.config;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.rong.common.bean.BaseRenderJson;
import com.rong.common.bean.MyErrorCodeConfig;

public class CommonInterceptor implements Interceptor {

	/**
	 * 控制器操作主逻辑 加入事务操作
	 * 
	 * @param ai
	 * @return
	 */
	private boolean doMain(Invocation ai) {
		try {
			ai.invoke();// 然后调用
		} catch (Exception e) {
			e.printStackTrace();
			BaseRenderJson.returnBaseTemplateObj(ai.getController(), MyErrorCodeConfig.REQUEST_FAIL, e.getMessage());
		}
		return true;
	}

	@Override
	public void intercept(Invocation ai){
		doMain(ai);
	}
}
