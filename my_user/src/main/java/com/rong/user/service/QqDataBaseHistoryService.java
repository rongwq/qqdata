package com.rong.user.service;

import com.jfinal.plugin.activerecord.Page;
import com.rong.persist.base.BaseService;
import com.rong.persist.model.QqDataBaseHistory;

/**
 * QQ修改历史数据业务接口层
 * @author Wenqiang-Rong
 * @date 2017年12月21日
 */
public interface QqDataBaseHistoryService extends BaseService<QqDataBaseHistory>{
	Page<QqDataBaseHistory> list(int pageNumber,int pageSize,String qq);
}

