package com.rong.persist.model;

import java.util.Date;

import com.rong.persist.model.base.BaseQqDataBase;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class QqDataBase extends BaseQqDataBase<QqDataBase> {
	public static final QqDataBase dao = new QqDataBase().dao();
	public static final String TABLE = "qq_data_base";
	
	public QqDataBase(){}
	
	// 白号格式
	public QqDataBase(String qq,String qqPwd){
		Date now = new Date();
		this.setCreateTime(now);
		this.setPwd(qqPwd);
		this.setQq(qq);
	}
	
	// 三问号格式
	public QqDataBase(String qq,String qqPwd,String question1,String question1_answer,String question2,String question2_answer,String question3,String question3_answer){
		Date now = new Date();
		this.setCreateTime(now);
		this.setPwd(qqPwd);
		this.setQq(qq);
		this.setQuestion1(question1);
		this.setQuestion1Answer(question1_answer);
		this.setQuestion2(question2);
		this.setQuestion2Answer(question2_answer);
		this.setQuestion3(question3);
		this.setQuestion3Answer(question3_answer);
	}
	
	// 绑机号格式
	public QqDataBase(String qq,String qqPwd,String question1,String question1_answer,String question2,String question2_answer,String question3,String question3_answer,String mobile){
		Date now = new Date();
		this.setCreateTime(now);
		this.setPwd(qqPwd);
		this.setQq(qq);
		this.setQuestion1(question1);
		this.setQuestion1Answer(question1_answer);
		this.setQuestion2(question2);
		this.setQuestion2Answer(question2_answer);
		this.setQuestion3(question3);
		this.setQuestion3Answer(question3_answer);
		this.setMobile(mobile);
	}
	
	// 令牌号格式
	public QqDataBase(String qq,String qqPwd,String question1,String question1_answer,String question2,String question2_answer,String question3,String question3_answer,String mobile,String tokenCode){
		Date now = new Date();
		this.setCreateTime(now);
		this.setPwd(qqPwd);
		this.setQq(qq);
		this.setQuestion1(question1);
		this.setQuestion1Answer(question1_answer);
		this.setQuestion2(question2);
		this.setQuestion2Answer(question2_answer);
		this.setQuestion3(question3);
		this.setQuestion3Answer(question3_answer);
		this.setMobile(mobile);
		this.setTokenCode(tokenCode);
	}
}
