package com.rong.persist.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseQqGroup<M extends BaseQqGroup<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Long id) {
		set("id", id);
	}

	public java.lang.Long getId() {
		return getLong("id");
	}

	public void setGroupNo(java.lang.String groupNo) {
		set("group_no", groupNo);
	}

	public java.lang.String getGroupNo() {
		return getStr("group_no");
	}

	public void setCreateTime(java.util.Date createTime) {
		set("create_time", createTime);
	}

	public java.util.Date getCreateTime() {
		return get("create_time");
	}

	public void setUpdateTime(java.util.Date updateTime) {
		set("update_time", updateTime);
	}

	public java.util.Date getUpdateTime() {
		return get("update_time");
	}

	public void setGroupName(java.lang.String groupName) {
		set("group_name", groupName);
	}

	public java.lang.String getGroupName() {
		return getStr("group_name");
	}

	public void setState(java.lang.Boolean state) {
		set("state", state);
	}

	public java.lang.Boolean getState() {
		return get("state");
	}

}
