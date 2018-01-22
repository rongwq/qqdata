package com.rong.user.service;

import com.jfinal.plugin.activerecord.Page;
import com.rong.persist.base.BaseService;
import com.rong.persist.model.QqGroup;

/**
 * qq群组业务接口层
 * @author Wenqiang-Rong
 * @date 2018年1月22日
 */
public interface QqGroupService extends BaseService<QqGroup>{
	/**
	 * 分页查询所有
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<QqGroup>
	 */
	Page<QqGroup> list(int pageNumber,int pageSize);
	/**
	 * 根据号码查询
	 * @param groupNo
	 * @return QqGroup
	 */
	QqGroup findByGroupNo(String groupNo);
	/**
	 * 保存
	 * @param groupNo 群号
	 * @return Boolean
	 */
	boolean save(String groupNo);
}

