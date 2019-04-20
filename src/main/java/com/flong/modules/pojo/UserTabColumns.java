package com.flong.modules.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.flong.commons.persistence.Entity;
import com.flong.commons.persistence.annotation.Column;
import com.flong.commons.persistence.annotation.Id;
import com.flong.commons.persistence.annotation.Relation;
import com.flong.commons.utils.Table;
/**
 * @Description	用户表列实体
 * @ClassName	UserTabColumns
 * @Date		2017年3月1日 上午9:50:25
 * @Author		liangjilong
 * @Copyright (c) All Right Reserved , 2017.
 */
@SuppressWarnings("all")
@Relation(UserTabColumns.TABLE)
public class UserTabColumns extends Entity implements Serializable{
	public static final String TABLE = Table.USER_TAB_COLUMNS;
	
	
	@Id    
    @Column("table_name")
	private String table_name;//表或视图名称
	private String column_name; //列名称
	private String data_type;//数据类型(VRACHR2,NUMBER,DATE等等)
	private String data_type_mod; //数据类型模型
	private String data_type_owner;//数据类型拥有者
	private BigDecimal data_length;//表字段的长度
	private BigDecimal data_precision;//数据双精度：如NUMBER(16,4),16代表data_precision
	private BigDecimal data_scale; //数据比例：如NUMBER(16,4),4代表data_scale
	private String nullable; //是否允许为空(Y允许为空,N不允许为空)
	private BigDecimal column_id;//列的ID
	private BigDecimal default_length; //默认长度
	private String data_default; //默认数据
	private BigDecimal num_distinct; //
	private Object low_value; //最低值
	private Object high_value;//最高值
	private String density; //密度
	private String num_nulls; //字段为空数量
	private String num_buckets; //列的柱状图中的桶数
	private String last_analyzed; //分析此列的最后一个日期
	private BigDecimal sample_size;//用于分析此柱的样品量
	private String character_set_name; //名称字节码
	private BigDecimal char_col_decl_length;//字符类型列的长度
	private String global_stats; //是否在不合并基本分区的情况下计算统计信息?
	private String user_stats; //是否直接由用户输入统计数据？
	private BigDecimal avg_col_len; //列的平均长度（以字节为单位）
	private BigDecimal char_length; // 列的最大长度（以字符为单位）
	private String char_used;//C是以字符为单位的最大长度，B（以字节为单位）
	private String v80_fmt_image; //列数据是否为8.0图像格式？
	private String data_upgraded;//列数据是否已升级到最新类型版本格式？
	private String histogram;
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
	public String getData_type() {
		return data_type;
	}
	public void setData_type(String data_type) {
		this.data_type = data_type;
	}
	public String getData_type_mod() {
		return data_type_mod;
	}
	public void setData_type_mod(String data_type_mod) {
		this.data_type_mod = data_type_mod;
	}
	public String getData_type_owner() {
		return data_type_owner;
	}
	public void setData_type_owner(String data_type_owner) {
		this.data_type_owner = data_type_owner;
	}
	public BigDecimal getData_length() {
		return data_length;
	}
	public void setData_length(BigDecimal data_length) {
		this.data_length = data_length;
	}
	public BigDecimal getData_precision() {
		return data_precision;
	}
	public void setData_precision(BigDecimal data_precision) {
		this.data_precision = data_precision;
	}
	public BigDecimal getData_scale() {
		return data_scale;
	}
	public void setData_scale(BigDecimal data_scale) {
		this.data_scale = data_scale;
	}
	public String getNullable() {
		return nullable;
	}
	public void setNullable(String nullable) {
		this.nullable = nullable;
	}
	public BigDecimal getColumn_id() {
		return column_id;
	}
	public void setColumn_id(BigDecimal column_id) {
		this.column_id = column_id;
	}
	public BigDecimal getDefault_length() {
		return default_length;
	}
	public void setDefault_length(BigDecimal default_length) {
		this.default_length = default_length;
	}
	public String getData_default() {
		return data_default;
	}
	public void setData_default(String data_default) {
		this.data_default = data_default;
	}
	public BigDecimal getNum_distinct() {
		return num_distinct;
	}
	public void setNum_distinct(BigDecimal num_distinct) {
		this.num_distinct = num_distinct;
	}
	public Object getLow_value() {
		return low_value;
	}
	public void setLow_value(Object low_value) {
		this.low_value = low_value;
	}
	public Object getHigh_value() {
		return high_value;
	}
	public void setHigh_value(Object high_value) {
		this.high_value = high_value;
	}
	public String getDensity() {
		return density;
	}
	public void setDensity(String density) {
		this.density = density;
	}
	public String getNum_nulls() {
		return num_nulls;
	}
	public void setNum_nulls(String num_nulls) {
		this.num_nulls = num_nulls;
	}
	public String getNum_buckets() {
		return num_buckets;
	}
	public void setNum_buckets(String num_buckets) {
		this.num_buckets = num_buckets;
	}
	public String getLast_analyzed() {
		return last_analyzed;
	}
	public void setLast_analyzed(String last_analyzed) {
		this.last_analyzed = last_analyzed;
	}
	public BigDecimal getSample_size() {
		return sample_size;
	}
	public void setSample_size(BigDecimal sample_size) {
		this.sample_size = sample_size;
	}
	public String getCharacter_set_name() {
		return character_set_name;
	}
	public void setCharacter_set_name(String character_set_name) {
		this.character_set_name = character_set_name;
	}
	public BigDecimal getChar_col_decl_length() {
		return char_col_decl_length;
	}
	public void setChar_col_decl_length(BigDecimal char_col_decl_length) {
		this.char_col_decl_length = char_col_decl_length;
	}
	public String getGlobal_stats() {
		return global_stats;
	}
	public void setGlobal_stats(String global_stats) {
		this.global_stats = global_stats;
	}
	public String getUser_stats() {
		return user_stats;
	}
	public void setUser_stats(String user_stats) {
		this.user_stats = user_stats;
	}
	public BigDecimal getAvg_col_len() {
		return avg_col_len;
	}
	public void setAvg_col_len(BigDecimal avg_col_len) {
		this.avg_col_len = avg_col_len;
	}
	public BigDecimal getChar_length() {
		return char_length;
	}
	public void setChar_length(BigDecimal char_length) {
		this.char_length = char_length;
	}
	public String getChar_used() {
		return char_used;
	}
	public void setChar_used(String char_used) {
		this.char_used = char_used;
	}
	public String getV80_fmt_image() {
		return v80_fmt_image;
	}
	public void setV80_fmt_image(String v80_fmt_image) {
		this.v80_fmt_image = v80_fmt_image;
	}
	public String getData_upgraded() {
		return data_upgraded;
	}
	public void setData_upgraded(String data_upgraded) {
		this.data_upgraded = data_upgraded;
	}
	public String getHistogram() {
		return histogram;
	}
	public void setHistogram(String histogram) {
		this.histogram = histogram;
	}
	
	 
	
	
	
}
