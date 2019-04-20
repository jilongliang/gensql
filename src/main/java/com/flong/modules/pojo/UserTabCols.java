package com.flong.modules.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
/**
 * 
 * @ClassName	UserTabCols和UserTabColumns同等使用
 * @Date		2017年3月2日 下午5:05:22
 * @Author		liangjilong
 * @Copyright (c) All Right Reserved ; 2017.
 */
@SuppressWarnings("all")
public class UserTabCols implements Serializable{
	private String table_name;
	private String column_name;
	private String data_type;
	private String data_type_mod;
	private String data_type_owner;
	private String data_length;
	private String data_precision;
	private String data_scale;
	private String nullable;
	private String column_id;
	private BigDecimal default_length;
	private String data_default;
	private String num_distinct;
	private BigDecimal low_value;
	private BigDecimal high_value;
	private String density;
	private String num_nulls;
	private String num_buckets;
	private String last_analyzed;
	private String sample_size;
	private String character_set_name;
	private BigDecimal char_col_decl_length;
	private String global_stats;
	private String user_stats;
	private BigDecimal avg_col_len;
	private BigDecimal char_length;
	private String char_used;
	private String v80_fmt_image;
	private String data_upgraded;
	private String hidden_column;
	private String virtual_column;
	private String segment_column_id;
	private String internal_column_id;
	private String histogram;
	private String qualified_col_name;
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
	public String getData_length() {
		return data_length;
	}
	public void setData_length(String data_length) {
		this.data_length = data_length;
	}
	public String getData_precision() {
		return data_precision;
	}
	public void setData_precision(String data_precision) {
		this.data_precision = data_precision;
	}
	public String getData_scale() {
		return data_scale;
	}
	public void setData_scale(String data_scale) {
		this.data_scale = data_scale;
	}
	public String getNullable() {
		return nullable;
	}
	public void setNullable(String nullable) {
		this.nullable = nullable;
	}
	public String getColumn_id() {
		return column_id;
	}
	public void setColumn_id(String column_id) {
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
	public String getNum_distinct() {
		return num_distinct;
	}
	public void setNum_distinct(String num_distinct) {
		this.num_distinct = num_distinct;
	}
	public BigDecimal getLow_value() {
		return low_value;
	}
	public void setLow_value(BigDecimal low_value) {
		this.low_value = low_value;
	}
	public BigDecimal getHigh_value() {
		return high_value;
	}
	public void setHigh_value(BigDecimal high_value) {
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
	public String getSample_size() {
		return sample_size;
	}
	public void setSample_size(String sample_size) {
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
	public String getHidden_column() {
		return hidden_column;
	}
	public void setHidden_column(String hidden_column) {
		this.hidden_column = hidden_column;
	}
	public String getVirtual_column() {
		return virtual_column;
	}
	public void setVirtual_column(String virtual_column) {
		this.virtual_column = virtual_column;
	}
	public String getSegment_column_id() {
		return segment_column_id;
	}
	public void setSegment_column_id(String segment_column_id) {
		this.segment_column_id = segment_column_id;
	}
	public String getInternal_column_id() {
		return internal_column_id;
	}
	public void setInternal_column_id(String internal_column_id) {
		this.internal_column_id = internal_column_id;
	}
	public String getHistogram() {
		return histogram;
	}
	public void setHistogram(String histogram) {
		this.histogram = histogram;
	}
	public String getQualified_col_name() {
		return qualified_col_name;
	}
	public void setQualified_col_name(String qualified_col_name) {
		this.qualified_col_name = qualified_col_name;
	}
	

}
