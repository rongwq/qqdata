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
	/**
	 * 根据qq好查询
	 * @param qq
	 * @return
	 */
	QqUpdatePwdWait findByQq(String qq);
	/**
	 * 获取1条数据，按创建时间先后排序，先到先拿
	 * @return
	 */
	QqUpdatePwdWait getFisrt();
}

