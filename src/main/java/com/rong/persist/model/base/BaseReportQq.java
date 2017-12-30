package com.rong.persist.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseReportQq<M extends BaseReportQq<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Long id) {
		set("id", id);
	}

	public java.lang.Long getId() {
		return getLong("id");
	}

	public void setTeamName(java.lang.String teamName) {
		set("team_name", teamName);
	}

	public java.lang.String getTeamName() {
		return getStr("team_name");
	}

	public void setQqCount(java.lang.Integer qqCount) {
		set("qq_count", qqCount);
	}

	public java.lang.Integer getQqCount() {
		return getInt("qq_count");
	}

	public void setQqCountLived(java.lang.Integer qqCountLived) {
		set("qq_count_lived", qqCountLived);
	}

	public java.lang.Integer getQqCountLived() {
		return getInt("qq_count_lived");
	}

	public void setCostPrice(java.lang.Double costPrice) {
		set("cost_price", costPrice);
	}

	public java.lang.Double getCostPrice() {
		return getDouble("cost_price");
	}

	public void setQqCountType1(java.lang.Integer qqCountType1) {
		set("qq_count_type_1", qqCountType1);
	}

	public java.lang.Integer getQqCountType1() {
		return getInt("qq_count_type_1");
	}

	public void setQqCountType2(java.lang.Integer qqCountType2) {
		set("qq_count_type_2", qqCountType2);
	}

	public java.lang.Integer getQqCountType2() {
		return getInt("qq_count_type_2");
	}

	public void setQqCountType3(java.lang.Integer qqCountType3) {
		set("qq_count_type_3", qqCountType3);
	}

	public java.lang.Integer getQqCountType3() {
		return getInt("qq_count_type_3");
	}

	public void setQqCountType4(java.lang.Integer qqCountType4) {
		set("qq_count_type_4", qqCountType4);
	}

	public java.lang.Integer getQqCountType4() {
		return getInt("qq_count_type_4");
	}

	public void setQqState0(java.lang.Integer qqState0) {
		set("qq_state_0", qqState0);
	}

	public java.lang.Integer getQqState0() {
		return getInt("qq_state_0");
	}

}
