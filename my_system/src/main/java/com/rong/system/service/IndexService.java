package com.rong.system.service;

import java.util.List;

import com.jfinal.plugin.activerecord.Record;

/**
 * 首页业务接口类
 * @author Wenqiang-Rong
 * @date 2017年12月21日
 */
public interface IndexService {
	Record getIndexUserValue();
	/**
	 * qq类型统计
	 * @return
	 */
	List<Record> qqTypeStatis();
}

