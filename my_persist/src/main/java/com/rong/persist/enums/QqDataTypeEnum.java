package com.rong.persist.enums;

/**
 * qq分类枚举
 * 
 * @author Wenqiang-Rong
 * @date 2017年12月27日
 */
public enum QqDataTypeEnum {
	WHITE("白号", 1), THREE_QUESTION("三问号", 2), MOBILE("绑机号", 3), TOKEN("令牌号", 4), MOBILE_NOQUESTION("手机令牌号-无密保", 5), TOKEN_NOQUESTION("手机号-无密保", 6);
	// 成员变量
	private String name;
	private int index;

	// 构造方法
	private QqDataTypeEnum(String name, int index) {
		this.name = name;
		this.index = index;
	}

	// 普通方法
	public static String getName(int index) {
		for (QqDataTypeEnum c : QqDataTypeEnum.values()) {
			if (c.getIndex() == index) {
				return c.name;
			}
		}
		return null;
	}

	// get set 方法
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

}
