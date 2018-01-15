package com.rong.user.service;

import com.jfinal.plugin.activerecord.Page;
import com.rong.persist.base.BaseService;
import com.rong.persist.model.QqTeam;

/**
 * QQ编组业务接口层
 * @author Wenqiang-Rong
 * @date 2017年12月21日
 */
public interface QqTeamService extends BaseService<QqTeam>{
	/**
	 * 分页查询所有
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<QqTeam>
	 */
	Page<QqTeam> list(int pageNumber,int pageSize);
	/**
	 * 根据名称查询
	 * @param name
	 * @return QqTeam
	 */
	QqTeam find(String name);
	/**
	 * 保存如果存在，则不再次保存
	 * @param name 名称
	 * @param costPrice 成本价
	 * @return Long 编组id
	 */
	Long save(String name,double costPrice);
	
	/**
	 * 修改编组名称，并将对应的qqData编组名称也修改
	 * @param id
	 * @param name
	 * @return
	 */
	boolean updateName(long id,String name);
}

