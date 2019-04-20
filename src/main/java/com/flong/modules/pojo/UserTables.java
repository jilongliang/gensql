package com.flong.modules.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.flong.commons.persistence.Entity;
import com.flong.commons.persistence.annotation.Column;
import com.flong.commons.persistence.annotation.Id;
import com.flong.commons.persistence.annotation.Relation;
import com.flong.commons.utils.Table;

/**
 * 
 * @Description	系统用户表
 * @ClassName	UserTables
 * @Date		2017年2月28日 下午5:54:26
 * @Author		liangjilong
 * @Copyright (c) All Right Reserved ; 2017.
 */
@SuppressWarnings("all")
@Relation(TableIdentifiers.TABLE)
public class UserTables extends Entity implements Serializable{
   /** 表名常量 */
    public static final String TABLE = Table.USER_TABLES;
  //ID和@Column必须配置一个jdbcTemplate底层代码控制，如果需要配置所有字段jpa可以用jdbcTemplate封装的
   //saveOrUpdate,具体代码请阅读TableIdentifiersDAOImpl
    @Id  
    @Column("table_name")
	private String table_name;
	private String tablespace_name;
	private String cluster_name;
	private String iot_name;
	private String status;
	private BigDecimal pct_free;
	private BigDecimal pct_used;
	private BigDecimal ini_trans;
	private BigDecimal max_trans;
	private BigDecimal initial_extent;
	private BigDecimal next_extent;
	private BigDecimal min_extents;
	private BigDecimal max_extents;
	private String pct_increase;
	private BigDecimal freelists;
	private BigDecimal freelist_groups;
	private String logging;
	private String backed_up;
	private BigDecimal num_rows;
	private BigDecimal blocks;
	private BigDecimal empty_blocks;
	private BigDecimal avg_space;
	private BigDecimal chain_cnt;
	private BigDecimal avg_row_len;
	private BigDecimal avg_space_freelist_blocks;
	private BigDecimal num_freelist_blocks;
	private String degree;
	private String instances;
	private String cache;
	private String table_lock;
	private BigDecimal sample_size;
	private Date last_analyzed;
	private String partitioned;
	private String iot_type;
	private String temporary;
	private String secondary;
	private String nested;
	private String buffer_pool;
	private String flash_cache;
	private String cell_flash_cache;
	private String row_movement;
	private String global_stats;
	private String user_stats;
	private String duration;
	private String skip_corrupt;
	private String monitoring;
	private String cluster_owner;
	private String dependencies;
	private String compression;
	private String compress_for;
	private String dropped;
	private String read_only;
	private String segment_created;
	private String result_cache;
	
	
	public String getTable_name() {
		return table_name;
	}
	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}
	public String getTablespace_name() {
		return tablespace_name;
	}
	public void setTablespace_name(String tablespace_name) {
		this.tablespace_name = tablespace_name;
	}
	public String getCluster_name() {
		return cluster_name;
	}
	public void setCluster_name(String cluster_name) {
		this.cluster_name = cluster_name;
	}
	public String getIot_name() {
		return iot_name;
	}
	public void setIot_name(String iot_name) {
		this.iot_name = iot_name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public BigDecimal getPct_free() {
		return pct_free;
	}
	public void setPct_free(BigDecimal pct_free) {
		this.pct_free = pct_free;
	}
	public BigDecimal getPct_used() {
		return pct_used;
	}
	public void setPct_used(BigDecimal pct_used) {
		this.pct_used = pct_used;
	}
	public BigDecimal getIni_trans() {
		return ini_trans;
	}
	public void setIni_trans(BigDecimal ini_trans) {
		this.ini_trans = ini_trans;
	}
	public BigDecimal getMax_trans() {
		return max_trans;
	}
	public void setMax_trans(BigDecimal max_trans) {
		this.max_trans = max_trans;
	}
	public BigDecimal getInitial_extent() {
		return initial_extent;
	}
	public void setInitial_extent(BigDecimal initial_extent) {
		this.initial_extent = initial_extent;
	}
 
	public String getPct_increase() {
		return pct_increase;
	}
	public void setPct_increase(String pct_increase) {
		this.pct_increase = pct_increase;
	}
	public BigDecimal getFreelists() {
		return freelists;
	}
	public void setFreelists(BigDecimal freelists) {
		this.freelists = freelists;
	}
	public BigDecimal getFreelist_groups() {
		return freelist_groups;
	}
	public void setFreelist_groups(BigDecimal freelist_groups) {
		this.freelist_groups = freelist_groups;
	}
	public String getLogging() {
		return logging;
	}
	public void setLogging(String logging) {
		this.logging = logging;
	}
	public String getBacked_up() {
		return backed_up;
	}
	public void setBacked_up(String backed_up) {
		this.backed_up = backed_up;
	}
 
	public BigDecimal getBlocks() {
		return blocks;
	}
	public void setBlocks(BigDecimal blocks) {
		this.blocks = blocks;
	}
	public BigDecimal getEmpty_blocks() {
		return empty_blocks;
	}
	public void setEmpty_blocks(BigDecimal empty_blocks) {
		this.empty_blocks = empty_blocks;
	}
	public BigDecimal getAvg_space() {
		return avg_space;
	}
	public void setAvg_space(BigDecimal avg_space) {
		this.avg_space = avg_space;
	}
	public BigDecimal getChain_cnt() {
		return chain_cnt;
	}
	public void setChain_cnt(BigDecimal chain_cnt) {
		this.chain_cnt = chain_cnt;
	}
	public BigDecimal getAvg_row_len() {
		return avg_row_len;
	}
	public void setAvg_row_len(BigDecimal avg_row_len) {
		this.avg_row_len = avg_row_len;
	}
	public BigDecimal getAvg_space_freelist_blocks() {
		return avg_space_freelist_blocks;
	}
	public void setAvg_space_freelist_blocks(BigDecimal avg_space_freelist_blocks) {
		this.avg_space_freelist_blocks = avg_space_freelist_blocks;
	}
	public BigDecimal getNum_freelist_blocks() {
		return num_freelist_blocks;
	}
	public void setNum_freelist_blocks(BigDecimal num_freelist_blocks) {
		this.num_freelist_blocks = num_freelist_blocks;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public String getInstances() {
		return instances;
	}
	public void setInstances(String instances) {
		this.instances = instances;
	}
	public String getCache() {
		return cache;
	}
	public void setCache(String cache) {
		this.cache = cache;
	}
	public String getTable_lock() {
		return table_lock;
	}
	public void setTable_lock(String table_lock) {
		this.table_lock = table_lock;
	}
	public BigDecimal getSample_size() {
		return sample_size;
	}
	public void setSample_size(BigDecimal sample_size) {
		this.sample_size = sample_size;
	}
	public Date getLast_analyzed() {
		return last_analyzed;
	}
	public void setLast_analyzed(Date last_analyzed) {
		this.last_analyzed = last_analyzed;
	}
	public String getPartitioned() {
		return partitioned;
	}
	public void setPartitioned(String partitioned) {
		this.partitioned = partitioned;
	}
	public String getIot_type() {
		return iot_type;
	}
	public void setIot_type(String iot_type) {
		this.iot_type = iot_type;
	}
	public String getTemporary() {
		return temporary;
	}
	public void setTemporary(String temporary) {
		this.temporary = temporary;
	}
	public String getSecondary() {
		return secondary;
	}
	public void setSecondary(String secondary) {
		this.secondary = secondary;
	}
	public String getNested() {
		return nested;
	}
	public void setNested(String nested) {
		this.nested = nested;
	}
	public String getBuffer_pool() {
		return buffer_pool;
	}
	public void setBuffer_pool(String buffer_pool) {
		this.buffer_pool = buffer_pool;
	}
	public String getFlash_cache() {
		return flash_cache;
	}
	public void setFlash_cache(String flash_cache) {
		this.flash_cache = flash_cache;
	}
	public String getCell_flash_cache() {
		return cell_flash_cache;
	}
	public void setCell_flash_cache(String cell_flash_cache) {
		this.cell_flash_cache = cell_flash_cache;
	}
	public String getRow_movement() {
		return row_movement;
	}
	public void setRow_movement(String row_movement) {
		this.row_movement = row_movement;
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
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getSkip_corrupt() {
		return skip_corrupt;
	}
	public void setSkip_corrupt(String skip_corrupt) {
		this.skip_corrupt = skip_corrupt;
	}
	public String getMonitoring() {
		return monitoring;
	}
	public void setMonitoring(String monitoring) {
		this.monitoring = monitoring;
	}
	public String getCluster_owner() {
		return cluster_owner;
	}
	public void setCluster_owner(String cluster_owner) {
		this.cluster_owner = cluster_owner;
	}
	public String getDependencies() {
		return dependencies;
	}
	public void setDependencies(String dependencies) {
		this.dependencies = dependencies;
	}
	public String getCompression() {
		return compression;
	}
	public void setCompression(String compression) {
		this.compression = compression;
	}
	public String getCompress_for() {
		return compress_for;
	}
	public void setCompress_for(String compress_for) {
		this.compress_for = compress_for;
	}
	public String getDropped() {
		return dropped;
	}
	public void setDropped(String dropped) {
		this.dropped = dropped;
	}
	public String getRead_only() {
		return read_only;
	}
	public void setRead_only(String read_only) {
		this.read_only = read_only;
	}
	public String getSegment_created() {
		return segment_created;
	}
	public void setSegment_created(String segment_created) {
		this.segment_created = segment_created;
	}
	public String getResult_cache() {
		return result_cache;
	}
	public void setResult_cache(String result_cache) {
		this.result_cache = result_cache;
	}
	public BigDecimal getNext_extent() {
		return next_extent;
	}
	public void setNext_extent(BigDecimal next_extent) {
		this.next_extent = next_extent;
	}
	public BigDecimal getMin_extents() {
		return min_extents;
	}
	public void setMin_extents(BigDecimal min_extents) {
		this.min_extents = min_extents;
	}
	public BigDecimal getMax_extents() {
		return max_extents;
	}
	public void setMax_extents(BigDecimal max_extents) {
		this.max_extents = max_extents;
	}
	public BigDecimal getNum_rows() {
		return num_rows;
	}
	public void setNum_rows(BigDecimal num_rows) {
		this.num_rows = num_rows;
	}
	
	
}
