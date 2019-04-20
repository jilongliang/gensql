package com.flong.modules.pojo;

import java.io.Serializable;

import com.flong.commons.persistence.Entity;
import com.flong.commons.persistence.annotation.Column;
import com.flong.commons.persistence.annotation.Id;
import com.flong.commons.persistence.annotation.Relation;
import com.flong.commons.utils.Table;
/**
 * @Description	TODO
 * @ClassName	UserColComments
 * @Date		2017年3月1日 下午2:53:02
 * @Author		liangjilong
 * @Copyright (c) All Right Reserved , 2017.
 */
@SuppressWarnings("all")
@Relation(UserColComments.TABLE)
public class UserColComments extends Entity implements Serializable{
	public static final String TABLE = Table.USER_COL_COMMENTS;
	
	
	@Id    
    @Column("table_name")
	public String table_name;//表名或视图名
	private String  column_name;//列名
	public String comments;//表的注释内容
	public String getColumn_name() {
		return column_name;
	}

	public void setColumn_name(String column_name) {
		this.column_name = column_name;
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
