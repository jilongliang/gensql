package com.flong.modules.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.flong.commons.persistence.Entity;
import com.flong.commons.persistence.annotation.Column;
import com.flong.commons.persistence.annotation.Id;
import com.flong.commons.persistence.annotation.Relation;
import com.flong.commons.utils.Table;
/**
 * @Description	系统视图实体
 * @ClassName	Views
 * @Date		2017年3月1日 上午9:51:00
 * @Author		liangjilong
 * @Copyright (c) All Right Reserved , 2017.
 */
@SuppressWarnings("all")
@Relation(Views.TABLE)
public class Views extends Entity implements Serializable {
	 /** 表名常量 */
    public static final String TABLE = Table.ALL_VIEWS;
	
	private String owner; //拥有者
	@Id   
	@Column("index_name")
	private String view_name; //视图名称
	private BigDecimal text_length; //已经建好的视图脚本长度
	private String text; //已经建好的视图脚本
	private BigDecimal type_text_length;//属于视图的长度
	private String type_text; 
	private BigDecimal oid_text_length; 
	private String oid_text;
	private String view_type_owner; 
	private String view_type; 
	private String superview_name;
	private String editioning_view; 
	private String read_only;
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getView_name() {
		return view_name;
	}
	public void setView_name(String view_name) {
		this.view_name = view_name;
	}
	public BigDecimal getText_length() {
		return text_length;
	}
	public void setText_length(BigDecimal text_length) {
		this.text_length = text_length;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public BigDecimal getType_text_length() {
		return type_text_length;
	}
	public void setType_text_length(BigDecimal type_text_length) {
		this.type_text_length = type_text_length;
	}
	public String getType_text() {
		return type_text;
	}
	public void setType_text(String type_text) {
		this.type_text = type_text;
	}
	public BigDecimal getOid_text_length() {
		return oid_text_length;
	}
	public void setOid_text_length(BigDecimal oid_text_length) {
		this.oid_text_length = oid_text_length;
	}
	public String getOid_text() {
		return oid_text;
	}
	public void setOid_text(String oid_text) {
		this.oid_text = oid_text;
	}
	public String getView_type_owner() {
		return view_type_owner;
	}
	public void setView_type_owner(String view_type_owner) {
		this.view_type_owner = view_type_owner;
	}
	public String getView_type() {
		return view_type;
	}
	public void setView_type(String view_type) {
		this.view_type = view_type;
	}
	public String getSuperview_name() {
		return superview_name;
	}
	public void setSuperview_name(String superview_name) {
		this.superview_name = superview_name;
	}
	public String getEditioning_view() {
		return editioning_view;
	}
	public void setEditioning_view(String editioning_view) {
		this.editioning_view = editioning_view;
	}
	public String getRead_only() {
		return read_only;
	}
	public void setRead_only(String read_only) {
		this.read_only = read_only;
	}
	 
	 
	
}
