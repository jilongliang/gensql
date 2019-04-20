package com.flong.modules.pojo;

import java.io.Serializable;

/**
 * @Description	表的外键实体
 * @ClassName	UserConstraints
 * @Date		2017年3月2日 下午1:46:51
 * @Author		liangjilong
 * @Copyright (c) All Right Reserved ; 2017.
 */
@SuppressWarnings("all")
public class UserConstraints implements Serializable {

	private String owner;//约束定义的所有者
	private String constraint_name; //约束定义名称
	private String constraint_type; //约束定义的类型
	private String table_name; //约束定义的表名称
	private String search_condition; //搜索条件
	private String r_owner; //约束定义的所有者
	private String r_constraint_name; //
	private String delete_rule; //
	private String status; //
	private String deferrable; //
	private String deferred; //
	private String validated; //
	private String generated; //
	private String bad; //
	private String rely; //
	private String last_change; //
	private String index_owner; //
	private String index_name; //
	private String invalid; //
	private String view_related; //
	
	
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getConstraint_name() {
		return constraint_name;
	}
	public void setConstraint_name(String constraint_name) {
		this.constraint_name = constraint_name;
	}
	public String getConstraint_type() {
		return constraint_type;
	}
	public void setConstraint_type(String constraint_type) {
		this.constraint_type = constraint_type;
	}
	public String getTable_name() {
		return table_name;
	}
	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}
	public String getSearch_condition() {
		return search_condition;
	}
	public void setSearch_condition(String search_condition) {
		this.search_condition = search_condition;
	}
	public String getR_owner() {
		return r_owner;
	}
	public void setR_owner(String r_owner) {
		this.r_owner = r_owner;
	}
	public String getR_constraint_name() {
		return r_constraint_name;
	}
	public void setR_constraint_name(String r_constraint_name) {
		this.r_constraint_name = r_constraint_name;
	}
	public String getDelete_rule() {
		return delete_rule;
	}
	public void setDelete_rule(String delete_rule) {
		this.delete_rule = delete_rule;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDeferrable() {
		return deferrable;
	}
	public void setDeferrable(String deferrable) {
		this.deferrable = deferrable;
	}
	public String getDeferred() {
		return deferred;
	}
	public void setDeferred(String deferred) {
		this.deferred = deferred;
	}
	public String getValidated() {
		return validated;
	}
	public void setValidated(String validated) {
		this.validated = validated;
	}
	public String getGenerated() {
		return generated;
	}
	public void setGenerated(String generated) {
		this.generated = generated;
	}
	public String getBad() {
		return bad;
	}
	public void setBad(String bad) {
		this.bad = bad;
	}
	public String getRely() {
		return rely;
	}
	public void setRely(String rely) {
		this.rely = rely;
	}
	public String getLast_change() {
		return last_change;
	}
	public void setLast_change(String last_change) {
		this.last_change = last_change;
	}
	public String getIndex_owner() {
		return index_owner;
	}
	public void setIndex_owner(String index_owner) {
		this.index_owner = index_owner;
	}
	public String getIndex_name() {
		return index_name;
	}
	public void setIndex_name(String index_name) {
		this.index_name = index_name;
	}
	public String getInvalid() {
		return invalid;
	}
	public void setInvalid(String invalid) {
		this.invalid = invalid;
	}
	public String getView_related() {
		return view_related;
	}
	public void setView_related(String view_related) {
		this.view_related = view_related;
	}
}
