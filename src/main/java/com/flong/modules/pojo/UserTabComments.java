package com.flong.modules.pojo;

import java.io.Serializable;

import com.flong.commons.persistence.Entity;
import com.flong.commons.persistence.annotation.Column;
import com.flong.commons.persistence.annotation.Id;
import com.flong.commons.persistence.annotation.Relation;
import com.flong.commons.utils.Table;

/**
 * @Description	用户表的注释
 * @ClassName	UserTabComments
 * @Date		2017年3月1日 下午2:43:52
 * @Author		liangjilong
 * @Copyright (c) All Right Reserved , 2017.
 */
@SuppressWarnings("all")
@Relation(UserTabComments.TABLE)
public class UserTabComments extends Entity implements Serializable{
	public static final String TABLE = Table.USER_TAB_COMMENTS;
	
	
	@Id    
    @Column("table_name")
	public String table_name;//表名或视图名
	private String table_type; //View or Table
	public String comments;//表的注释内容
	public String getTable_type() {
		return table_type;
	}
	public void setTable_type(String table_type) {
		this.table_type = table_type;
	}
	public String getTable_name() {
		return table_name;
	}
	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
 
}
