package com.rong.user.service;

import java.util.List;

import com.rong.persist.base.BaseService;
import com.rong.persist.model.QqDataBase;

/**
 * QQ密保token数据业务接口层
 * @author Wenqiang-Rong
 * @date 2017年12月30日
 */
public interface QqDataBaseService extends BaseService<QqDataBase>{
	QqDataBase findByQq(String qq);
	List<QqDataBase> findByQqs(String qqs);
}

