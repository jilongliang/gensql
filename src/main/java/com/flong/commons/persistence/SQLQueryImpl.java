package com.flong.commons.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.flong.commons.persistence.bean.PageList;
import com.flong.commons.persistence.bean.SimplePage;
import com.flong.commons.persistence.interfaces.IPagination;
import com.flong.commons.persistence.interfaces.ISQLQuery;
/**
 *@Author:liangjilong
 *@Date:2015年10月1日-上午9:03:51
 *@Email:jilongliang@sina.com
 *@Version:1.0
 *@Desct:SQL查询帮助类，这里的封装类完全可以封装到BaseDao，此时在Dao层就减少了接口的编写
 *但是为了规范，给Dao层分担点任务，还是分开了,毕竟Dao层一般情况都是编写SQL的处理.
 */
@SuppressWarnings("all")
public class SQLQueryImpl implements ISQLQuery {
	private final Logger loger = Logger.getLogger(SQLQueryImpl.class);
	protected boolean showSql;//是否显示SQL
	
	protected JdbcTemplate jdbcTemplate;//spring的 jdbctemplate
	private IPagination pagination;//自定义的分页接口

	
	protected <T> RowMapper<T> getSingleColumnRowMapper(Class<T> requiredType) {
		return new BeanRowMapper(requiredType);
	}

	protected RowMapper<Map<String, Object>> getColumnMapRowMapper() {
		return new ColumnMapRowMapper();
	}

	public List query(String sql) {
		if (this.showSql)
			this.loger.info(sql);
		return this.jdbcTemplate.queryForList(sql);
	}

	public List query(String sql, Object[] param) {
		if (this.showSql)
			this.loger.info(sql);
		return this.jdbcTemplate.queryForList(sql, param);
	}

	public List query(String sql, SimplePage page) {
		return query(sql, new Object[0], page);
	}

	/**
	 * 通过分页查询出所有信息
	 */
	public List query(String sql, Object[] param, SimplePage page) {
		IPagination pagination = getPagination();
		pagination.setRowMapper(getColumnMapRowMapper());
		pagination.setPageConfig(page);
		if (this.showSql)
			this.loger.info(pagination.fixSQl(sql));
		this.jdbcTemplate.query(pagination.fixSQl(sql), param, pagination);
		int recordCount = getCount(pagination.fixCountSQL(sql), param);
		page.setRowCount(recordCount);

		List list = pagination.list();//查询所有列表信息
		PageList resultList = new PageList(list);
		resultList.setTotal(recordCount);//设置记录总条数
		resultList.setPageNum(page.getPageNum());//
		resultList.setPageSize(page.getShowCount());
		resultList.setPageCount(recordCount / page.getShowCount()+ (recordCount % page.getShowCount() > 0 ? 1 : 0));
		return resultList;
	}

	
	/**
	 * 通过分页查询出所有信息
	 */
	public <T> List query(String sql, Class<T> clazz, SimplePage page) {
		return query(sql, new Object[0], clazz, page);
	}
	/**
	 * 通过分页和实体查询出所有信息
	 */
	public <T> List query(String sql, Object[] param, Class<T> clazz, SimplePage page) {
		IPagination pagination = getPagination();
		pagination.setRowMapper(getSingleColumnRowMapper(clazz));
		pagination.setPageConfig(page);
		if (this.showSql)
			this.loger.info(pagination.fixSQl(sql));
		this.jdbcTemplate.query(pagination.fixSQl(sql), param, pagination);
		int recordCount = getCount(pagination.fixCountSQL(sql), param);
		page.setRowCount(recordCount);
		List list = pagination.list();
		PageList resultList = new PageList(list);
		resultList.setTotal(recordCount);
		resultList.setPageNum(page.getPageNum());
		resultList.setPageSize(page.getShowCount());
		resultList.setPageCount(recordCount / page.getShowCount()+ (recordCount % page.getShowCount() > 0 ? 1 : 0));
		return resultList;
	}

	/**
	 * 在spring-jdbc-3.2.2以后queryForInt都去掉这个方法，都用queryForObject
	 */
	public int getCount(String sql) {
		Integer number=this.jdbcTemplate.queryForObject(sql, null, Integer.class);  
		
		 return (number != null ? number.intValue() : 0);  
	}

	public int getCount(String sql, Object[] params) {
		Integer number = jdbcTemplate.queryForObject(sql, params, Integer.class);  
	    return (number != null ? number.intValue() : 0);  
		//return this.jdbcTemplate.queryForInt(sql, params);
	}
	
	
	/**
	 * 获取一个List集合
	 * @param sql
	 * @return
	 */
	public List<Map<String, Object>> getListBySql(String sql) {
		if(showSql){
			loger.info("This SQL-----="+sql);
		}
		return this.jdbcTemplate.queryForList(sql);
	}

	/**
	 * 获取一个List集合
	 * @param sql
	 * @return
	 */
	public <T> List<T> queryForList(String sql,Class<T> elementType) {
		if(showSql){
			loger.info("This SQL-----="+sql);
		}
		return this.jdbcTemplate.queryForList(sql,elementType);
	}

	/***
	 * 获取名称
	 * @param sql
	 * @return
	 */
	public List<String> getName(String sql) {
		if(showSql){
			loger.info("This SQL-----="+sql);
		}
		List list = new ArrayList();
		SqlRowSet srs = this.jdbcTemplate.queryForRowSet(sql);
		while (srs.next()) {
			list.add(srs.getString(1));
		}
		return list;
	}

	/**
	 * 获取字段的名称和类型
	 * @param sql
	 * @return
	 */
	public List<String[]> getNameAndType(String sql) {
		if(showSql){
			loger.info("This SQL-----="+sql);
		}
		List list = new ArrayList();
		SqlRowSet srs = this.jdbcTemplate.queryForRowSet(sql);
		while (srs.next()) {
			String[] value = new String[2];
			value[0] = srs.getString(1);
			value[1] = srs.getString(2);
			list.add(value);
		}
		return list;
	}
	
	/*************************注入get/set*************************************/
	
	public boolean isShowSql() {
		return this.showSql;
	}

	public void setShowSql(boolean showSql) {
		this.showSql = showSql;
	}
	public JdbcTemplate getJdbcTemplate() {
		return this.jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public IPagination getPagination() {
		if (this.pagination == null)
			this.pagination = new DefaultPagination();
		try {
			IPagination p = (IPagination) this.pagination.getClass().newInstance();
			return p;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return this.pagination;
	}

	public void setPagination(IPagination pagination) {
		this.pagination = pagination;
	}
}