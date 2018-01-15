package com.rong.user.service;

import com.jfinal.plugin.activerecord.Page;
import com.rong.persist.base.BaseService;
import com.rong.persist.model.IpTemp;

/**
 * 临时IP库业务接口层
 * @author Wenqiang-Rong
 * @date 2017年12月29日
 */
public interface IpTempService extends BaseService<IpTemp>{
	/**
	 * 分页查询所有
	 * @param pageNumber
	 * @param pageSize
	 * @return Page<IpTemp>
	 */
	Page<IpTemp> list(int pageNumber,int pageSize);
	/**
	 * 根据名称查询
	 * @param ip
	 * @return IpTemp
	 */
	IpTemp findByIp(String ip);
	/**
	 * 保存
	 * @param ip ip
	 * @param address 归属地
	 * @return Boolean
	 */
	boolean save(String ip,String address);
	
	boolean clean();
}

