package com.rong.user.service;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Page;
import com.rong.persist.base.BaseService;
import com.rong.persist.model.QqData;

/**
 * QQ数据业务接口层
 * @author Wenqiang-Rong
 * @date 2017年12月21日
 */
public interface QqDataService extends BaseService<QqData>{
	Page<QqData> list(int pageNumber,int pageSize,Kv param);
	QqData findByQq(String qq);
	boolean updatePwd(QqData qqData,String vals []);
	boolean saveQqData(String qq,String qqPwd,int qqType,String tags,long teamId,String teamName);
	boolean saveQqDataBase(int qqType,String vals []);
	boolean saveQqDataBaseHistory(int qqType,String vals []);
}

