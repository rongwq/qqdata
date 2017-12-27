package com.rong.persist.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseQqDataBase<M extends BaseQqDataBase<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Long id) {
		set("id", id);
	}

	public java.lang.Long getId() {
		return getLong("id");
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

	public void setQq(java.lang.String qq) {
		set("qq", qq);
	}

	public java.lang.String getQq() {
		return getStr("qq");
	}

	public void setPwd(java.lang.String pwd) {
		set("pwd", pwd);
	}

	public java.lang.String getPwd() {
		return getStr("pwd");
	}

	public void setQuestion1(java.lang.String question1) {
		set("question1", question1);
	}

	public java.lang.String getQuestion1() {
		return getStr("question1");
	}

	public void setQuestion1Answer(java.lang.String question1Answer) {
		set("question1_answer", question1Answer);
	}

	public java.lang.String getQuestion1Answer() {
		return getStr("question1_answer");
	}

	public void setQuestion2(java.lang.String question2) {
		set("question2", question2);
	}

	public java.lang.String getQuestion2() {
		return getStr("question2");
	}

	public void setQuestion2Answer(java.lang.String question2Answer) {
		set("question2_answer", question2Answer);
	}

	public java.lang.String getQuestion2Answer() {
		return getStr("question2_answer");
	}

	public void setQuestion3(java.lang.String question3) {
		set("question3", question3);
	}

	public java.lang.String getQuestion3() {
		return getStr("question3");
	}

	public void setQuestion3Answer(java.lang.String question3Answer) {
		set("question3_answer", question3Answer);
	}

	public java.lang.String getQuestion3Answer() {
		return getStr("question3_answer");
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

}
