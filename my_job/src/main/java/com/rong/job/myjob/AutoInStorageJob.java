package com.rong.job.myjob;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.jfinal.kit.Kv;
import com.rong.common.util.DateTimeUtil;
import com.rong.persist.dao.QqDataBaseDao;
import com.rong.persist.dao.QqDataDao;
import com.rong.persist.model.QqData;
import com.rong.persist.model.QqUpdatePwdWait;

/**
 * 出库的qq自动入库
 * @author Wenqiang-Rong
 * @date 2017年12月28日
 */
public class AutoInStorageJob implements Job{
	private static final Logger logger = Logger.getLogger(AutoInStorageJob.class);
	QqDataDao qqDataDao = new QqDataDao();
	QqDataBaseDao qqDataBaseDao = new QqDataBaseDao();
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		try{
			Kv param = Kv.by("storageState", 2);
			List<QqData> qqDataList = qqDataDao.page(1, 9999, param).getList();
			int expirCount = 0;
			for (QqData qqData : qqDataList) {
				//1.判定qq出库是否到期
				Date now = new Date();
				Date outStorageTime = qqData.getOutStorageTime();
				int outStorageDays = qqData.getOutStorageDays();
				int day = DateTimeUtil.getBetweenDay(outStorageTime, now);
				if(day > outStorageDays){//过期
					expirCount++;
					//2.自动入库
					qqData.setOutStorageDays(null);
					qqData.setOutStorageTime(null);
					qqData.setUpdateTime(now);
					boolean qqDataUpdateResult = false;
					try {
						//异常捕获，避免单次错误影响循环
						qqDataUpdateResult = qqData.update();
					} catch (Exception e) {
						e.printStackTrace();
					}
					if(qqDataUpdateResult){
						//3.入库后，自动提交到修改密码表
						QqUpdatePwdWait qqUpdatePwdWait = new QqUpdatePwdWait();
						qqUpdatePwdWait.setCreateTime(now);
						qqUpdatePwdWait.setQq(qqData.getQq());
						//获取令牌
						String tokenCode = qqDataBaseDao.findByQq(qqData.getQq()).getTokenCode();
						qqUpdatePwdWait.setTokenCode(tokenCode);
						try {
							//异常捕获，避免单次错误影响整体循环
							qqUpdatePwdWait.save();
						} catch (Exception e) {
							e.printStackTrace();
							logger.error("qq:"+qqData.getQq()+",自动提交到修改密码表错误:"+e);
						}
					}
				}
			}
			logger.info("执行出库的qq自动入库数据成功，流程结束,执行结果成功数："+expirCount);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("执行出库的qq自动入库出现异常");
		}		
	}
}
