package com.flong.modules.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.flong.commons.persistence.bean.SimplePage;
import com.flong.commons.persistence.dao.impl.EntityDaoSupport;
import com.flong.modules.dao.UserConsColumnsDao;
import com.flong.modules.pojo.UserConsColumns;

@Repository
@SuppressWarnings("all")
public class UserConsColumnsDaoImpl extends EntityDaoSupport<UserConsColumns> implements UserConsColumnsDao {

	@Override
	public List<UserConsColumns> getUserConsColumnList(SimplePage page, UserConsColumns object) {
		
		return null;
	}

	@Override
	public UserConsColumns getUserConsColumnsByName(String tableName) {
		String sql = " select A.* from user_cons_columns A, user_constraints B  where A.constraint_name = B.constraint_name "
				   + " and B.constraint_type = 'P' AND upper(A.table_name) = upper('"+tableName+"')";
		SimplePage simplePage = new SimplePage();
		simplePage.setShowCount(Integer.MAX_VALUE);
		List<UserConsColumns> query = iSQLQuery.query(sql, UserConsColumns.class, simplePage);
		if(query!=null && query.size()>0){
			return query.get(0);
		}
		return null;
	}

 
		

	 
	
}
