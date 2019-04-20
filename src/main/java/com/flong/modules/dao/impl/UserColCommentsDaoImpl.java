package com.flong.modules.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.flong.commons.persistence.bean.SimplePage;
import com.flong.commons.persistence.dao.impl.EntityDaoSupport;
import com.flong.modules.dao.UserColCommentsDao;
import com.flong.modules.pojo.UserColComments;
import com.flong.modules.pojo.UserTabComments;

@Repository
@SuppressWarnings("all")
public class UserColCommentsDaoImpl extends EntityDaoSupport<UserColComments> implements UserColCommentsDao {
	private static String SELECT_USER_COL_COMMENTS_SQL = "select table_name, column_name, comments from user_col_comments ";
	
	@Override
	public List<UserColComments> getUserColCommentsByName(String tableName) {
		String sql = SELECT_USER_COL_COMMENTS_SQL+"  where upper(table_name)=upper('"+tableName+"') ";//
		
		SimplePage page = new SimplePage ();
		page.setShowCount(Integer.MAX_VALUE);
		return iSQLQuery.query(sql, UserColComments.class, page);
	}
	
}
