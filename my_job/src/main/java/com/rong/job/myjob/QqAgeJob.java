package com.rong.job.myjob;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.jfinal.plugin.activerecord.Db;

/**
 * 计算q龄
 * @author Wenqiang-Rong
 * @date 2017年12月30日
 */
public class QqAgeJob implements Job{
	private static final Logger logger = Logger.getLogger(QqAgeJob.class);
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		try{
			int count = Db.update("update qq_data set qq_age = qq_age+1 where DATEDIFF(now(),login_time) = 1");
			logger.info("计算q龄成功,昨日登录qq数："+count);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("计算q龄出现异常");
		}		
	}
}
