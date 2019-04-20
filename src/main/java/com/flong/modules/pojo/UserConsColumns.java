package com.flong.modules.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.flong.commons.persistence.Entity;
import com.flong.commons.persistence.annotation.Column;
import com.flong.commons.persistence.annotation.Id;
import com.flong.commons.persistence.annotation.Relation;
import com.flong.commons.utils.Table;
/**
 * @Description	系统约束主键表
 * @ClassName	UserConsColumns
 * @Date		2017年3月2日 下午1:38:39
 * @Author		liangjilong
 * @Copyright (c) All Right Reserved , 2017.
 */
@SuppressWarnings("all")
@Relation(UserConsColumns.TABLE)
public class UserConsColumns extends Entity implements Serializable {
	public static final String TABLE = Table.USER_CONS_COLUMNS;
	@Id    
    @Column("table_name")
	private String table_name; //约束表的名称
	private String owner; //约束定义的所有者
	private String constraint_name; //用户约束表的字段的主键名称
	private String column_name;//用户约束表的字段名称
	private BigDecimal position;//字段定位/位置
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
	public String getTable_name() {
		return table_name;
	}
	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}
	public String getColumn_name() {
		return column_name;
	}
	public void setColumn_name(String column_name) {
		this.column_name = column_name;
	}
	public BigDecimal getPosition() {
		return position;
	}
	public void setPosition(BigDecimal position) {
		this.position = position;
	}
	

}
