package com.flong.modules.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.flong.commons.persistence.bean.SimplePage;
import com.flong.commons.persistence.dao.impl.EntityDaoSupport;
import com.flong.modules.dao.UserTabCommentsDao;
import com.flong.modules.pojo.UserTabColumns;
import com.flong.modules.pojo.UserTabComments;

@Repository
@SuppressWarnings("all")
public class UserTabCommentsDaoImpl extends EntityDaoSupport<UserTabComments> implements UserTabCommentsDao {
	private static String SELECT_USER_TAB_COMMENTS_SQL = "select table_name, table_type, comments from user_tab_comments ";
	
	@Override
	public List<UserTabComments> getUserTabCommentsByName(String tableName) {
		String sql = SELECT_USER_TAB_COMMENTS_SQL+ " where upper(table_name)=upper('"+tableName+"') ";//
		
		SimplePage page = new SimplePage ();
		page.setShowCount(Integer.MAX_VALUE);
		return iSQLQuery.query(sql, UserTabComments.class, page);
	}
	
}
