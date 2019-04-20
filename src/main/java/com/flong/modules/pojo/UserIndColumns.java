package com.flong.modules.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.flong.commons.persistence.Entity;
import com.flong.commons.persistence.annotation.Column;
import com.flong.commons.persistence.annotation.Id;
import com.flong.commons.persistence.annotation.Relation;
import com.flong.commons.utils.Table;
/**
 * @Description	索引实体
 * @ClassName	UserIndColumns
 * @Date		2017年3月1日 上午9:49:57
 * @Author		liangjilong
 * @Copyright (c) All Right Reserved , 2017.
 */

@SuppressWarnings("all")
@Relation(UserIndColumns.TABLE)
public class UserIndColumns extends Entity implements Serializable {
	public static final String TABLE = Table.USER_IND_COLUMNS;
	@Id   
	@Column("index_name")
	private String index_name; //索引名称
	private String table_name; //表名
	private String column_name; //列明
	private BigDecimal column_position; //列状态
	private BigDecimal column_length; 
	private BigDecimal char_length; 
	private String descend;
	
	
	public String getIndex_name() {
		return index_name;
	}
	public void setIndex_name(String index_name) {
		this.index_name = index_name;
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
	public BigDecimal getColumn_position() {
		return column_position;
	}
	public void setColumn_position(BigDecimal column_position) {
		this.column_position = column_position;
	}
	public BigDecimal getColumn_length() {
		return column_length;
	}
	public void setColumn_length(BigDecimal column_length) {
		this.column_length = column_length;
	}
	public BigDecimal getChar_length() {
		return char_length;
	}
	public void setChar_length(BigDecimal char_length) {
		this.char_length = char_length;
	}
	public String getDescend() {
		return descend;
	}
	public void setDescend(String descend) {
		this.descend = descend;
	}
}
