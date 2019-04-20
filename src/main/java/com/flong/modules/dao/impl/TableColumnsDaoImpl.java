package com.flong.modules.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.flong.commons.persistence.bean.SimplePage;
import com.flong.commons.persistence.dao.impl.EntityDaoSupport;
import com.flong.modules.dao.TableColumnsDao;
import com.flong.modules.pojo.UserTabColumns;
import com.flong.modules.pojo.UserTables;

@Repository
@SuppressWarnings("all")
public class TableColumnsDaoImpl extends EntityDaoSupport<UserTabColumns> implements TableColumnsDao {

	public List<UserTabColumns> getUserTabColumnsByName(String tableName) {
		String sql =  getUserTabColumnsOracleSQL()+" from user_tab_columns where upper(table_name)=upper('"+tableName+"') ";
		SimplePage page = new SimplePage ();
		page.setShowCount(Integer.MAX_VALUE);
		List<UserTabColumns> query = iSQLQuery.query(sql, UserTabColumns.class, page);
		return query;
	}
	
	
 
	
	
	/**
	 * @Description 获取Oracle的user_tab_column查询SQL
	 * @Author		liangjilong
	 * @Date		2017年3月1日 上午9:32:32
	 * @return 		参数
	 * @return 		String 返回类型
	 */
	private static String getUserTabColumnsOracleSQL(){
		StringBuffer sqlBuffer = new StringBuffer(); 
		sqlBuffer.append("select table_name, column_name, data_type, data_type_mod, data_type_owner, data_length,");
		sqlBuffer.append("data_precision, data_scale, nullable, column_id,default_length, data_default, num_distinct,");
		sqlBuffer.append("low_value, high_value,character_set_name, char_col_decl_length,global_stats, user_stats, ");
		sqlBuffer.append("avg_col_len, char_length, char_used,v80_fmt_image, data_upgraded, histogram ").append(" ");
		return sqlBuffer.toString();
	}
	
}
