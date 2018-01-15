package com.rong.persist.dao;

import com.jfinal.plugin.activerecord.Page;
import com.rong.common.util.StringUtils;
import com.rong.persist.base.BaseDao;
import com.rong.persist.model.Version;

/**
 * APP版本的dao类
 * @author Wenqiang-Rong
 * @date 2018年1月12日
 */
public class VersionDao extends BaseDao<Version>{
	private Version dao = Version.dao;
	public static final String FILEDS = "id,app_id,app_name,app_code,version_name,system_type,download_url,icon_url,file_size,app_version,is_publish,auto_download,is_file,remark,create_time,update_time";
	
	public Page<Version> list(String appCode,int page,int pagesize) {
		String select = "select " + FILEDS;
		String sqlExceptSelect = "from "+Version.TABLE ;
		StringBuffer sqlWhere = new StringBuffer(" where 1=1");
		if(!StringUtils.isNullOrEmpty(appCode)){
			sqlWhere.append(" and app_code = '"+appCode+"'");
		}
		String orderBy = " order by id desc";
		sqlExceptSelect +=  (sqlWhere.toString() + orderBy);
		return dao.paginate(page, pagesize, select, sqlExceptSelect);
	}
	
	public Version getForApp(String code ,Integer type) {
		String sql = "select " + FILEDS + " from " + Version.TABLE + " where system_type = ? and is_publish = 1 and app_code = ? order by create_time desc";
		return dao.findFirst(sql, type, code);
	}
}
