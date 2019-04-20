package com.flong.modules.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.flong.commons.lang.StringUtils;
import com.flong.commons.persistence.bean.SimplePage;
import com.flong.commons.persistence.dao.impl.EntityDaoSupport;
import com.flong.modules.dao.TableDao;
import com.flong.modules.pojo.TableIdentifiers;
import com.flong.modules.pojo.UserTables;

@Repository
@SuppressWarnings("all")
public class TableDaoImpl extends EntityDaoSupport<UserTables> implements TableDao {

	@Override
	public List<UserTables> queryTables(SimplePage page, UserTables object) {
		String sql= getUserTablesOracleSQL()+ " from user_tables where 1=1 ";
		if(!StringUtils.isEmpty(object.getTable_name())){
			sql +=" and table_name like '%'|| upper('"+object.getTable_name()+"') ||'%'";
		}
		List<UserTables> query = iSQLQuery.query(sql, UserTables.class, page);
		return query;
	}
	
	@Override
	public List<UserTables> queryTableByName(String tableName) {
		String sql= getUserTablesOracleSQL()+ " from user_tables where 1=1 ";
		if(!StringUtils.isEmpty(tableName)){
			sql +=" and upper(table_name)=upper('"+tableName+"')";
		}
		SimplePage page = new SimplePage ();
		page.setShowCount(1);//只需要一个值即可
		List<UserTables> query = iSQLQuery.query(sql, UserTables.class, page);
		return query;
	}
	
	

	
	/**
	 * @Description 获取Oracle的UserTables的查询sql
	 * @Author		liangjilong
	 * @Date		2017年3月1日 上午9:59:44
	 * @return 		参数
	 * @return 		String 返回类型
	 */
	private static String getUserTablesOracleSQL(){
		StringBuffer buffer = new StringBuffer();
		buffer.append("select table_name, tablespace_name, cluster_name, iot_name, status, pct_free, pct_used, ini_trans, max_trans, initial_extent, next_extent, min_extents,");
		buffer.append("max_extents, pct_increase, freelists, freelist_groups, logging, backed_up, num_rows, blocks, empty_blocks, avg_space, chain_cnt, avg_row_len,");
		buffer.append("avg_space_freelist_blocks, num_freelist_blocks, degree, instances, cache, table_lock, sample_size, last_analyzed, partitioned, iot_type, temporary,");
		buffer.append("secondary, nested, buffer_pool, flash_cache, cell_flash_cache, row_movement, global_stats, user_stats, duration, skip_corrupt, monitoring, ");
		buffer.append("cluster_owner, dependencies, compression, compress_for, dropped, read_only, segment_created, result_cache");
		return buffer.toString();
	}

	@Override
	public String createTableSQLScript(String tableName) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
