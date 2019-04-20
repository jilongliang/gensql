package com.flong.modules.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.flong.commons.lang.StringUtils;
import com.flong.commons.persistence.bean.SimplePage;
import com.flong.commons.persistence.dao.impl.EntityDaoSupport;
import com.flong.modules.dao.IndexDao;
import com.flong.modules.pojo.UserIndColumns;
import com.flong.modules.pojo.Views;

@Repository
@SuppressWarnings("all")
public class IndexDaoImpl extends EntityDaoSupport<UserIndColumns> implements IndexDao {

	private static String SELECT_USER_INDEX_COLUMNS_ORACLE_SQL = "select index_name,table_name,column_name,column_position,column_length ,char_length,descend from user_ind_columns ";
	
	@Override
	public List<UserIndColumns> queryIndexColumnList(SimplePage page, UserIndColumns object) {
		String sql = SELECT_USER_INDEX_COLUMNS_ORACLE_SQL+" where 1=1 ";//
		if(!StringUtils.isEmpty(object.getIndex_name())){
			sql +=" and index_name like '%"+object.getIndex_name()+"%'";
		}
		if(!StringUtils.isEmpty(object.getColumn_name())){
			sql +=" and column_name like '%"+object.getColumn_name()+"%'";
		}
		if(!StringUtils.isEmpty(object.getTable_name())){
			sql +=" and table_name like '%"+object.getTable_name()+"%'";
		}
		List<UserIndColumns> query = iSQLQuery.query(sql, UserIndColumns.class, page);
		return query;
	}

	@Override
	public  List<UserIndColumns> queryIndexColumnList(String table_name, String index_name) {
		String sql = SELECT_USER_INDEX_COLUMNS_ORACLE_SQL+" where upper(table_name)=upper('"+table_name+"')  and upper(index_name)=upper('"+index_name+"')  ";
		SimplePage page =new SimplePage();
		page.setShowCount(Integer.MAX_VALUE);
		List<UserIndColumns> query = iSQLQuery.query(sql, UserIndColumns.class, page);
		return query;
	}
	

	 
	
}
