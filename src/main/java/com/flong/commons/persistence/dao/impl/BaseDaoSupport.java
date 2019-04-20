package com.flong.commons.persistence.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.flong.commons.lang.exception.DaoAccessException;
import com.flong.commons.persistence.Entity;
import com.flong.commons.persistence.bean.DataStore;
import com.flong.commons.persistence.bean.PagingParameter;
import com.flong.commons.persistence.builder.PagingSqlBuilder;
import com.flong.commons.persistence.builder.SimpleSqlBuilder;
import com.flong.commons.persistence.condition.Condition;
import com.flong.commons.persistence.dao.BaseDao;
import com.flong.commons.persistence.interfaces.ISQLQuery;
import com.flong.commons.persistence.interfaces.MapRowMapper;
import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 数据查询DAO支持类
 *
 * 创建日期：2012-9-26
 * @author wangk
 */
public abstract class BaseDaoSupport implements BaseDao, InitializingBean {
	public static java.sql.ResultSet rs = null;
	public static java.sql.PreparedStatement pst = null;
	public static java.sql.Connection conn = null;
	/** 日志对象 */
	private static final Logger logger = Logger.getLogger(BaseDaoSupport.class);
	/** 实现类日志对象 */
	protected final Logger log = Logger.getLogger(getClass());
	/**加入ISQLQuery注解....*/
	@Autowired protected ISQLQuery iSQLQuery;
	/** JDBC模版对象 */
	@Autowired protected JdbcTemplate jdbcTemplate;
	/** SQL语句参数带名称的JDBC模版对象 */
	protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	/** 分页SQL语句创建对象 */
	protected PagingSqlBuilder pagingSqlBuilder;
	
	/**
	 * 获得JDBC模版对象
	 *
	 * @return
	 * 创建日期：2012-9-25
	 * 修改说明：
	 * @author wangk
	 */
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	/**
	 * 获得SQL语句参数带名称的JDBC模版对象
	 *
	 * @return
	 * 创建日期：2012-12-19
	 * 修改说明：
	 * @author wangk
	 */
	public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
		return namedParameterJdbcTemplate;
	}

	/**
	 * 获得分页SQL语句创建对象
	 *
	 * @return
	 * 创建日期：2012-10-8
	 * 修改说明：
	 * @author wangk
	 */
	public PagingSqlBuilder getPagingSqlBuilder() {
		return pagingSqlBuilder;
	}

	/**
	 * 初始化非注入的属性
	 * 
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 * 创建日期：2012-12-19
	 * 修改说明：
	 * @author wangk
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		//初始化namedParameterJdbcTemplate
		namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
		//初始化pagingSqlBuilder
		pagingSqlBuilder = new PagingSqlBuilder(((ComboPooledDataSource)
				jdbcTemplate.getDataSource()).getJdbcUrl().replaceAll("://.*$", ""));
	}
	
	@Override
	public List<Map<String, Object>> search(String sql, Object... params) throws DaoAccessException {
		try {
			logger.debug(sql);
			return jdbcTemplate.queryForList(sql, params);
		} catch (Exception e) {
			throw new DaoAccessException(e);
		}
	}

	@Override
	public List<Map<String, Object>> search(String sql, List<Object> params) throws DaoAccessException {
		return search(sql, params.toArray());
	}

	@Override
	public List<Map<String, Object>> search(String sql, Map<String, Object> params) throws DaoAccessException {
		try {
			logger.debug(sql);
			return namedParameterJdbcTemplate.queryForList(sql, params);
		} catch (Exception e) {
			throw new DaoAccessException(e);
		}
	}
	
	
	@Override
	public <R> List<R> search(String sql, MapRowMapper<R> mapRowMapper, Object... params) throws DaoAccessException {
		List<Map<String, Object>> list = search(sql, params);
		if(list == null) {
			return null;
		}
		List<R> ret = new ArrayList<R>();
		for (int i = 0; i < list.size(); i++) {
			ret.add(mapRowMapper.mapRow(list.get(i), i));
		}
		return ret;
	}

	@Override
	public <R> List<R> search(String sql, MapRowMapper<R> mapRowMapper,
			List<Object> params) throws DaoAccessException {
		return search(sql, mapRowMapper, params.toArray());
	}

	@Override
	public <R> List<R> search(String sql, MapRowMapper<R> mapRowMapper,
			Map<String, Object> params) throws DaoAccessException {
		List<Map<String, Object>> list = search(sql, params);
		if(list == null) {
			return null;
		}
		List<R> ret = new ArrayList<R>();
		for (int i = 0; i < list.size(); i++) {
			ret.add(mapRowMapper.mapRow(list.get(i), i));
		}
		return ret;
	}
	
	
	@Override
	public DataStore<Map<String, Object>> search(String sql, PagingParameter paging,
			Object... params) throws DaoAccessException {
		try {
			PagingSqlBuilder pagingSqlBuilder = getPagingSqlBuilder();
			int records = jdbcTemplate.queryForInt(pagingSqlBuilder.getCountSql(sql), params);
			if(records < 0) {
				return null;
			}
			if(records == 0) {
				return new DataStore<Map<String, Object>>(records, new ArrayList<Map<String, Object>>());
			}
			return new DataStore<Map<String, Object>>(records, search(pagingSqlBuilder.getPagingSql(sql, paging), params));
		} catch (Exception e) {
			throw new DaoAccessException(e);
		}
		
	}

	@Override
	public DataStore<Map<String, Object>> search(String sql,
			PagingParameter paging, List<Object> params) throws DaoAccessException {
		return search(sql, paging, params.toArray());
	}

	@Override
	public DataStore<Map<String, Object>> search(String sql,
			PagingParameter paging, Map<String, Object> params) throws DaoAccessException {
		try {
			PagingSqlBuilder pagingSqlBuilder = getPagingSqlBuilder();
			int records = namedParameterJdbcTemplate.queryForInt(pagingSqlBuilder.getCountSql(sql), params);
			if(records < 0) {
				return null;
			}
			if(records == 0) {
				return new DataStore<Map<String, Object>>(records, new ArrayList<Map<String, Object>>());
			}
			return new DataStore<Map<String, Object>>(records, search(pagingSqlBuilder.getPagingSql(sql, paging), params));
		} catch (Exception e) {
			throw new DaoAccessException(e);
		}
	}
	

	@Override
	public <R> DataStore<R> search(String sql, MapRowMapper<R> mapRowMapper, PagingParameter paging, Object... params) throws DaoAccessException {
		DataStore<Map<String, Object>> dataStore = search(sql, paging, params);
		if(dataStore == null) {
			return null;
		}
		if(dataStore.getDatas() == null) {
			return new DataStore<R>(dataStore.getRecords(), null);
		}
		List<R> list = new ArrayList<R>();
		for (int i = 0; i < dataStore.getDatas().size(); i++) {
			list.add(mapRowMapper.mapRow(dataStore.getDatas().get(i), i));
		}
		return new DataStore<R>(dataStore.getRecords(), list);
	}

	@Override
	public <R> DataStore<R> search(String sql, MapRowMapper<R> mapRowMapper,
			PagingParameter paging, List<Object> params) throws DaoAccessException {
		return search(sql, mapRowMapper, paging, params.toArray());
	}

	@Override
	public <R> DataStore<R> search(String sql, MapRowMapper<R> mapRowMapper,
			PagingParameter paging, Map<String, Object> params) throws DaoAccessException {
		DataStore<Map<String, Object>> dataStore = search(sql, paging, params);
		if(dataStore == null) {
			return null;
		}
		if(dataStore.getDatas() == null) {
			return new DataStore<R>(dataStore.getRecords(), null);
		}
		List<R> list = new ArrayList<R>();
		for (int i = 0; i < dataStore.getDatas().size(); i++) {
			list.add(mapRowMapper.mapRow(dataStore.getDatas().get(i), i));
		}
		return new DataStore<R>(dataStore.getRecords(), list);
	}
	
	
	@Override
	public List<Map<String, Object>> join(Condition condition,
			Class<?>... classLink) throws DaoAccessException {
		String orders = null;
		return join(condition, orders, classLink);
	}

	@Override
	public List<Map<String, Object>> join(Condition condition, String orders,
			Class<?>... classLink) throws DaoAccessException {
		String sql = buildJoinSql(condition, orders, classLink);
		List<Map<String, Object>> result = null;
		if(condition == null) {
			result = search(sql);
		} else {
			result = search(sql, condition.getParameters());
		}
		convertJoinResult(result, classLink);
		return result;
	}

	@Override
	public <R> List<R> join(Condition condition, MapRowMapper<R> mapRowMapper,
			Class<?>... classLink) throws DaoAccessException {
		return join(condition, null, mapRowMapper, classLink);
	}

	@Override
	public <R> List<R> join(Condition condition, String orders,
			MapRowMapper<R> mapRowMapper, Class<?>... classLink) throws DaoAccessException {
		List<Map<String, Object>> list = join(condition, orders, classLink);
		if(list == null) {
			return null;
		}
		List<R> ret = new ArrayList<R>();
		for (int i = 0; i < list.size(); i++) {
			ret.add(mapRowMapper.mapRow(list.get(i), i));
		}
		return ret;
	}

	@Override
	public DataStore<Map<String, Object>> join(Condition condition,
			PagingParameter paging, Class<?>... classLink) throws DaoAccessException {
		String orders = null;
		return join(condition, orders , paging, classLink);
	}

	@Override
	public DataStore<Map<String, Object>> join(Condition condition,
			String orders, PagingParameter paging,
			Class<?>... classLink) throws DaoAccessException {
		PagingSqlBuilder pagingSqlBuilder = getPagingSqlBuilder();
		String sql = buildJoinSql(condition, orders, classLink);
		Object[] params = new Object[0];
		if(condition != null) {
			params = condition.getParameters();
		}
		int records = 0;
		try {
			records = jdbcTemplate.queryForInt(pagingSqlBuilder.getCountSql(sql), params);
		} catch (Exception e) {
			throw new DaoAccessException(e);
		}
		if(records < 0) {
			return null;
		}
		if(records == 0) {
			return new DataStore<Map<String, Object>>(records, new ArrayList<Map<String, Object>>());
		}
		List<Map<String, Object>> datas = null;
		datas = search(pagingSqlBuilder.getPagingSql(sql, paging), params);
		convertJoinResult(datas, classLink);
		return new DataStore<Map<String, Object>>(records, datas);
	}

	@Override
	public <R> DataStore<R> join(Condition condition,
			MapRowMapper<R> mapRowMapper, PagingParameter paging,
			Class<?>... classLink) throws DaoAccessException {
		return join(condition, null, mapRowMapper, paging, classLink);
	}

	@Override
	public <R> DataStore<R> join(Condition condition, String orders,
			MapRowMapper<R> mapRowMapper, PagingParameter paging,
			Class<?>... classLink) throws DaoAccessException {
		DataStore<Map<String, Object>> dataStore = join(condition, orders, paging, classLink);
		if(dataStore == null) {
			return null;
		}
		if(dataStore.getDatas() == null) {
			return new DataStore<R>(dataStore.getRecords(), null);
		}
		List<R> list = new ArrayList<R>();
		for (int i = 0; i < dataStore.getDatas().size(); i++) {
			list.add(mapRowMapper.mapRow(dataStore.getDatas().get(i), i));
		}
		return new DataStore<R>(dataStore.getRecords(), list);
	}

	/**
	 * 构建(内)连接SQL语句
	 *
	 * @param condition
	 * @param orders
	 * @param classLink
	 * @return
	 * 创建日期：2012-10-19
	 * 修改说明：
	 * @author wangk
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String buildJoinSql(Condition condition, String orders,
			Class<?>... classLink) throws DaoAccessException {
		try {
			StringBuilder sb1 = new StringBuilder();
			StringBuilder sb2 = new StringBuilder();
			for (int i = 0;i < classLink.length;i++) {
				Class<? extends Entity> clazz = (Class<? extends Entity>)classLink[i];
				SimpleSqlBuilder<? extends Entity> sqlBuilder1 = new SimpleSqlBuilder(clazz);
				String tableName1 = sqlBuilder1.getTableName();
				Map<String, String> fieldColumnMapping1 = sqlBuilder1.getFieldColumnMapping();
				for (String field : sqlBuilder1.getFieldColumnMapping().keySet()) {
					sb1.append(tableName1 +  "." + fieldColumnMapping1.get(field) + " AS " + 
							tableName1 +  "_" + fieldColumnMapping1.get(field) + ", ");
				}
				if(i == classLink.length - 1) {
					break;
				}
				Class<? extends Entity> rClass = (Class<? extends Entity>)classLink[i+1];			
				SimpleSqlBuilder<? extends Entity> sqlBuilder2 = new SimpleSqlBuilder(rClass);
				String tableName2 = sqlBuilder2.getTableName();
				if(i == 0) {
					sb2.append(tableName1);
				}
				sb2.append(" JOIN " + tableName2 + " ON " + tableName1 + "." + 
						fieldColumnMapping1.get(sqlBuilder1.getReferenceField(rClass)) + 
						" = " + tableName2 + "." + sqlBuilder1.getReferencedColumn(rClass));
			}
			sb1.delete(sb1.length() - 2, sb1.length());
			String sql = "SELECT " + sb1 + " FROM " + sb2;
			if(condition != null) {
				sql += " WHERE " + condition.toSqlString();
			}
			if(orders != null) {
				sql += " ORDER BY " + orders;
			}
			logger.debug(sql);
			return sql;
		} catch (Exception e) {
			throw new DaoAccessException(e);
		}
	}
	
	/**
	 * 转换连接查询结果，Map对象的key值为：对象名.属性名
	 *
	 * @param result
	 * @param classLink
	 * 创建日期：2012-10-19
	 * 修改说明：
	 * @author wangk
	 */
	private void convertJoinResult(List<Map<String, Object>> result, Class<?>... classLink) throws DaoAccessException {
		if(CollectionUtils.isEmpty(result)) {
			return;
		}
		for (Map<String, Object> map : result) {
			try {
				for (Class<?> clazz : classLink) {
					@SuppressWarnings({ "rawtypes", "unchecked" })
					SimpleSqlBuilder<? extends Entity> sqlBuilder = new SimpleSqlBuilder(clazz);
					String tableName = sqlBuilder.getTableName();
					Map<String, String> fieldColumnMapping = sqlBuilder.getFieldColumnMapping();
					String className = clazz.getSimpleName();
					String variableName = className.substring(0, 1).toLowerCase() + className.substring(1);
					for (String field : fieldColumnMapping.keySet()) {
						map.put(variableName + "." + field, map.remove(tableName + "_" + fieldColumnMapping.get(field)));
					}
				}
			} catch (Exception e) {
				throw new DaoAccessException(e);
			}
		}
	}

}
