package com.rong.persist.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseUserPwderrorStatis<M extends BaseUserPwderrorStatis<M>> extends Model<M> implements IBean {

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

	public void setPayPwderrorCount(java.lang.Integer payPwderrorCount) {
		set("pay_pwderror_count", payPwderrorCount);
	}

	public java.lang.Integer getPayPwderrorCount() {
		return getInt("pay_pwderror_count");
	}

	public void setLoginPwderrorCount(java.lang.Integer loginPwderrorCount) {
		set("login_pwderror_count", loginPwderrorCount);
	}

	public java.lang.Integer getLoginPwderrorCount() {
		return getInt("login_pwderror_count");
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
