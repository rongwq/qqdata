package com.rong.user.service;

import java.util.Date;

import com.jfinal.plugin.activerecord.Page;
import com.rong.common.exception.CommonException;
import com.rong.persist.base.BaseServiceImpl;
import com.rong.persist.dao.IpTempDao;
import com.rong.persist.model.IpTemp;

/**
 * QQ编组服务实现层
 * @author Wenqiang-Rong
 * @date 2017年12月26日
 */
public class IpTempServiceImpl extends BaseServiceImpl<IpTemp> implements IpTempService{
	private IpTempDao dao = new IpTempDao();

	@Override
	public Page<IpTemp> list(int pageNumber, int pageSize) {
		return dao.page(pageNumber, pageSize, null);
	}

	@Override
	public IpTemp findByIp(String ip) {
		return dao.findByIp(ip);
	}

	@Override
	public boolean save(String ip, String address) throws CommonException{
		IpTemp ipTemp = findByIp(ip);
		if(ipTemp==null){
			ipTemp = new IpTemp();
			ipTemp.setIp(ip);
			ipTemp.setAddress(address);
			ipTemp.setCreateTime(new Date());
		}
		return dao.save(ipTemp);
	}

	@Override
	public boolean clean() {
		return dao.clean();
	}
}
