package com.rong.user.service;

import com.jfinal.plugin.activerecord.Page;
import com.rong.persist.base.BaseService;
import com.rong.persist.model.QqUpdatePwdWait;

/**
 * 等待修改密码QQ业务接口层
 * @author Wenqiang-Rong
 * @date 2017年12月21日
 */
public interface QqUpdatePwdWaitService extends BaseService<QqUpdatePwdWait>{
	/**
	 * 分页查询所有
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<QqUpdatePwdWait>
	 */
	Page<QqUpdatePwdWait> list(int pageNumber,int pageSize);
}

