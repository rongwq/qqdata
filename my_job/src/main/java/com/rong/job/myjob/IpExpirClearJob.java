package com.rong.job.myjob;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.rong.persist.dao.IpTempDao;

/**
 * 清理过期IP
 * @author Wenqiang-Rong
 * @date 2017年12月29日
 */
public class IpExpirClearJob implements Job{
	private final Logger logger = Logger.getLogger(this.getClass());
	IpTempDao dao = new IpTempDao();

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		try{
			int count = dao.cleanExpir();
			logger.info("清理过期IP数据成功，清理IP数量："+count);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("清理过期IP数据出现异常"+e);
		}		
	}
}
