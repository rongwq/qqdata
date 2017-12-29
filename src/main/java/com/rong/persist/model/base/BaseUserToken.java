package com.rong.persist.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseUserToken<M extends BaseUserToken<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Long id) {
		set("id", id);
	}

	public java.lang.Long getId() {
		return getLong("id");
	}

	public void setUserId(java.lang.Long userId) {
		set("user_id", userId);
	}

	public java.lang.Long getUserId() {
		return getLong("user_id");
	}

	public void setMobile(java.lang.String mobile) {
		set("mobile", mobile);
	}

	public java.lang.String getMobile() {
		return getStr("mobile");
	}

	public void setToken(java.lang.String token) {
		set("token", token);
	}

	public java.lang.String getToken() {
		return getStr("token");
	}

	public void setIsExpir(java.lang.Boolean isExpir) {
		set("is_expir", isExpir);
	}

	public java.lang.Boolean getIsExpir() {
		return get("is_expir");
	}

	public void setExpirTime(java.util.Date expirTime) {
		set("expir_time", expirTime);
	}

	public java.util.Date getExpirTime() {
		return get("expir_time");
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

}