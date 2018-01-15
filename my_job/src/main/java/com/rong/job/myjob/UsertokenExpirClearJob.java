package com.rong.job.myjob;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.jfinal.plugin.activerecord.Db;
import com.rong.persist.model.UserToken;

public class UsertokenExpirClearJob implements Job{
	private final Logger logger = Logger.getLogger(this.getClass());

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("开始清理用户过期token数据流程");
		try{
			String sql = "delete from "+UserToken.TABLE +" where expirTime < ? ";
			long newTime  = System.currentTimeMillis();
			Db.update(sql, newTime);
			logger.info("定时清理用户过期token数据成功，流程结束");
		}catch(Exception e){
			e.printStackTrace();
			logger.error("定时清理用户过期token数据出现异常");
		}		
	}
}
