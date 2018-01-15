package com.rong.job.myjob;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.rong.common.util.DateTimeUtil;
import com.rong.persist.dao.QqDataDao;
import com.rong.persist.model.ReportQq;

/**
 * 产量明细报表
 * @author Wenqiang-Rong
 * @date 2017年12月30日
 */
public class QqReportJob implements Job{
	private static final Logger logger = Logger.getLogger(QqReportJob.class);
	QqDataDao qqDataDao = new QqDataDao();
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		try{
			List<Record>  list = qqDataDao.qqStatis();
			List<ReportQq>  reportQqList = new ArrayList<>();
			for (Record record : list) {
				ReportQq item = new ReportQq();
				item.setCostPrice(record.getDouble("costPrice"));
				item.setTeamName(record.getStr("teamName"));
				item.setQqCount(record.getInt("allCount"));
				item.setQqCountLived(record.getInt("aliveCount"));
				item.setQqCountType1(record.getInt("qqCountType1"));
				item.setQqCountType2(record.getInt("qqCountType2"));
				item.setQqCountType3(record.getInt("qqCountType3"));
				item.setQqCountType4(record.getInt("qqCountType4"));
				item.setQqState0(record.getInt("unaliveCount"));
				item.setCreateTime(DateTimeUtil.parseDateTime(DateTimeUtil.lastSecondInYesterday(),DateTimeUtil.DEFAULT_FORMAT_HOUR));
				reportQqList.add(item);
			}
			Db.batchSave(reportQqList, 100);
			logger.info("生成产量明细报表成功,统计qq数："+list.size());
		}catch(Exception e){
			e.printStackTrace();
			logger.error("生成产量明细报表异常");
		}		
	}
}
