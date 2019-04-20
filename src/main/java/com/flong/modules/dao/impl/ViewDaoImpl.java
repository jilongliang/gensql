package com.flong.modules.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.flong.commons.lang.StringUtils;
import com.flong.commons.persistence.bean.SimplePage;
import com.flong.commons.persistence.dao.impl.EntityDaoSupport;
import com.flong.modules.dao.ViewDao;
import com.flong.modules.pojo.Views;

@Repository
@SuppressWarnings("all")
public class ViewDaoImpl extends EntityDaoSupport<Views> implements ViewDao {

	private static String  VIEWS_FIELD_SQL = "owner, view_name, text_length, text, type_text_length, type_text, oid_text_length, oid_text, view_type_owner, view_type, superview_name, editioning_view, read_only";
	@Override
	public List<Views> queryViews(SimplePage page, Views object) {
		String sql = " select "+VIEWS_FIELD_SQL+" from all_views where 1=1 ";//
		if(!StringUtils.isEmpty(object.getView_name())){
			sql +=" and view_name like '%"+object.getView_name()+"%'";
		}
		List<Views> query = iSQLQuery.query(sql, Views.class, page);
		return query;
	}
	
	
	public List<Views> queryViews(String view_name) {
		String sql = " select "+VIEWS_FIELD_SQL+" from all_views where upper(view_name)=upper('"+view_name+"')";//
		SimplePage simplePage = new SimplePage();
		simplePage.setShowCount(Integer.MAX_VALUE);
		List<Views> query = iSQLQuery.query(sql, Views.class, simplePage);
		return query;
		
	}
	 
	
}
