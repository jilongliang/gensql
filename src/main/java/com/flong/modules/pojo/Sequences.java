package com.flong.modules.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.flong.commons.persistence.Entity;
import com.flong.commons.persistence.annotation.Column;
import com.flong.commons.persistence.annotation.Id;
import com.flong.commons.persistence.annotation.Relation;
import com.flong.commons.utils.Table;
/**
 * @Description	序列实体
 * @ClassName	Sequences
 * @Date		2017年3月1日 上午9:50:46
 * @Author		liangjilong
 * @Copyright (c) All Right Reserved , 2017.
 */
@SuppressWarnings("all")
@Relation(Sequences.TABLE)
public class Sequences extends Entity  implements Serializable{
	
	public static final String TABLE = Table.USER_SEQUENCES;
	@Id   
	@Column("index_name")
	private String sequence_name; //序列名称
	private BigDecimal min_value; //最小值
	private BigDecimal max_value; //最大值
	private BigDecimal increment_by; //递增
	private String cycle_flag; //循环标志
	private String order_flag; //order标志
	private BigDecimal cache_size; //缓存大小
	private BigDecimal last_number;//最后一个数字
	
	public String getSequence_name() {
		return sequence_name;
	}
	public void setSequence_name(String sequence_name) {
		this.sequence_name = sequence_name;
	}
	public BigDecimal getMin_value() {
		return min_value;
	}
	public void setMin_value(BigDecimal min_value) {
		this.min_value = min_value;
	}
	public BigDecimal getMax_value() {
		return max_value;
	}
	public void setMax_value(BigDecimal max_value) {
		this.max_value = max_value;
	}
	public BigDecimal getIncrement_by() {
		return increment_by;
	}
	public void setIncrement_by(BigDecimal increment_by) {
		this.increment_by = increment_by;
	}
	public String getCycle_flag() {
		return cycle_flag;
	}
	public void setCycle_flag(String cycle_flag) {
		this.cycle_flag = cycle_flag;
	}
	public String getOrder_flag() {
		return order_flag;
	}
	public void setOrder_flag(String order_flag) {
		this.order_flag = order_flag;
	}
	public BigDecimal getCache_size() {
		return cache_size;
	}
	public void setCache_size(BigDecimal cache_size) {
		this.cache_size = cache_size;
	}
	public BigDecimal getLast_number() {
		return last_number;
	}
	public void setLast_number(BigDecimal last_number) {
		this.last_number = last_number;
	}
	
	 
}
