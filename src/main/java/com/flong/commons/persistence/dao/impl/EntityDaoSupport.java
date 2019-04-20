package com.flong.commons.persistence.dao.impl;

import java.beans.PropertyDescriptor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.flong.commons.lang.exception.DaoAccessException;
import com.flong.commons.persistence.Entity;
import com.flong.commons.persistence.annotation.Association;
import com.flong.commons.persistence.annotation.Reference;
import com.flong.commons.persistence.bean.DataStore;
import com.flong.commons.persistence.bean.PagingParameter;
import com.flong.commons.persistence.builder.SimpleSqlBuilder;
import com.flong.commons.persistence.condition.Condition;
import com.flong.commons.persistence.dao.EntityDao;
import com.flong.commons.persistence.exception.AnnotationNotFoundException;
import com.flong.commons.persistence.exception.DuplicateRecordException;
import com.flong.commons.persistence.exception.ErrorCode;
import com.flong.commons.persistence.exception.FieldColumnMappingException;
import com.flong.commons.persistence.exception.IllegalRecordException;
import com.flong.commons.utils.ObjectUtil;

/**
 * 实体Dao支持类
 *
 * 创建日期：2012-9-24
 * @author wangk
 */
@SuppressWarnings("all")
public abstract class EntityDaoSupport<E extends Entity> extends BaseDaoSupport implements EntityDao<E> {
	////redirect和RedirectView一样
	protected static final String ENTER = "\n";
	protected static final String TAB = "    ";
	protected static String COMMA=",";
	/** 日志对象 */
	private static final Logger logger = Logger.getLogger(BaseDaoSupport.class);
	/** 条件关键字集合 */
	private static final List<String> CONDITION_KEYWORDS = new ArrayList<String>();
	/** 条件运算符（正则表达式形式）集合 */
	private static final List<String> CONDITION_OPERATORS = new ArrayList<String>();

	static {
		/** 初始化条件关键字集合 */
		CONDITION_KEYWORDS.add("IS");
		CONDITION_KEYWORDS.add("NOT");
		CONDITION_KEYWORDS.add("NULL");
		CONDITION_KEYWORDS.add("LIKE");
		CONDITION_KEYWORDS.add("BETWEEN");
		CONDITION_KEYWORDS.add("AND");
		CONDITION_KEYWORDS.add("OR");
		CONDITION_KEYWORDS.add("IN");
		CONDITION_KEYWORDS.add("ASC");
		CONDITION_KEYWORDS.add("DESC");
		/** 初始化条件运算符集合（= < > + - * / % ( ) , ?），组合符号（<> <= >=）需要特殊处理 */
		CONDITION_OPERATORS.add("=");
		CONDITION_OPERATORS.add("<");
		CONDITION_OPERATORS.add(">");
		CONDITION_OPERATORS.add("\\+");
		CONDITION_OPERATORS.add("\\-");
		CONDITION_OPERATORS.add("\\*");
		CONDITION_OPERATORS.add("/");
		CONDITION_OPERATORS.add("%");
		CONDITION_OPERATORS.add("\\(");
		CONDITION_OPERATORS.add("\\)");
		CONDITION_OPERATORS.add("\\,");
		CONDITION_OPERATORS.add("\\?");
	}

	/** SQL语句构建对象 */
	protected SimpleSqlBuilder<E> simpleSqlBuilder;
	/** 实体类类型 */
	private Class<E> entityClass;

	/**
	 * 构造方法  通过反射初始化entityClass
	 * 创建日期：2012-10-8
	 * 修改说明：
	 * @author wangk
	 */
	public EntityDaoSupport() {
		//获得EntityDaoSupport包含泛型实参的类型
		ParameterizedType parameterizedType = 
				(ParameterizedType)getClass().getGenericSuperclass();
		Type[] params = parameterizedType.getActualTypeArguments();
		
		Class<E> entityClass = (Class<E>)params[0];
		init(entityClass);
	}

	/**
	 * 构造方法
	 * @param entityClass 实体类类型
	 * 创建日期：2012-9-25
	 * 修改说明：
	 * @author wangk
	 */
	public EntityDaoSupport(Class<E> entityClass) {
		init(entityClass);
	}

	/**
	 * 初始化方法
	 *
	 * @param entityClass  实体类类型
	 * 创建日期：2012-10-11
	 * 修改说明：
	 * @author wangk
	 */
	private void init(Class<E> entityClass) {
		this.entityClass = entityClass;
		//初始化simpleSqlBuilder
		simpleSqlBuilder = new SimpleSqlBuilder<E>(entityClass);
		//检查实体所有的引用属性列是否存在和引用属性类型和被引用属性类型是否相同
		simpleSqlBuilder.checkReferenceFields();
		//检查关联属性是否存在非自身循环关联并记录debug级别日志
		for (Class<?> association : simpleSqlBuilder.getAssociationLink()) {
			logger.debug(association.getName());
		}
	}
	
	/**
	 * 获得SQL语句构建对象
	 *
	 * @return
	 * 创建日期：2012-9-25
	 * 修改说明：
	 * @author wangk
	 */
	public SimpleSqlBuilder<E> getSimpleSqlBuilder() {
		return simpleSqlBuilder;
	}

	@Override
	public <K extends Object> E get(K id, Class<?>... associationLink) throws DaoAccessException {
		if(associationLink != null && associationLink.length == 0) {
			try {
				return jdbcTemplate.queryForObject(simpleSqlBuilder.getQuerySimpleSql(), 
						simpleSqlBuilder.getRowMapper(), id);
			} catch (EmptyResultDataAccessException e) {
				//只处理空结果异常，表示没有对应的记录，返回null
				logger.warn(e);
				return null;
			} catch (Exception e) {
				throw new DaoAccessException(e);
			}
		}
		return get(Condition.eq(simpleSqlBuilder.getFieldColumnMapping().get(
				simpleSqlBuilder.getIdField()), id), associationLink);
	}
	
	@Override
	public E get(String sql, Object... params) throws DaoAccessException {
		return getUniqueEntity(query(sql, params));
	}
	@Override
	public E get(String sql, List<Object> params) throws DaoAccessException {
		return get(sql, params.toArray());
	}
	@Override
	public E get(String sql, Map<String, Object> params) throws DaoAccessException {
		return getUniqueEntity(query(sql, params));
	}
	
	@Override
	public E get(Condition condition, Class<?>... associationLink) throws DaoAccessException {
		return getUniqueEntity(query(condition, associationLink));
	}
	
	@Override
	public <K extends Number> K save(E entity) throws DaoAccessException {
		return saveWithId(entity, null);
	}

	@Override
	public <K extends Number> void save(E entity, K id) throws DaoAccessException {
		saveWithId(entity, id);
	}

	@Override
	public void update(E entity) throws DaoAccessException {
		try {
			if(entity.isTransient()) {
				throw new IllegalRecordException(ErrorCode.ILLEGAL_RECORD_AS_UPDATE_TRANSIENT,
						"The record " + entity + " is transient!");
			}
			Map<String, Object> params = simpleSqlBuilder.getSqlParameters(entity);
			namedParameterJdbcTemplate.update(simpleSqlBuilder.getUpdateSql(params.keySet()), params);
		} catch (Exception e) {
			throw new DaoAccessException(e);
		}
	}

	@Override
	public void saveOrUpdate(E entity) throws DaoAccessException {
		if(entity.isTransient()) {
			save(entity);
		} else {
			update(entity);
		}
	}

	@Override
	public <K extends Number> void delete(K id) throws DaoAccessException {
		try {
			jdbcTemplate.update(simpleSqlBuilder.getDeleteSql(), id);
		} catch (Exception e) {
			throw new DaoAccessException(e);
		}
	}

	@Override
	public List<E> query(String sql, Object... params) throws DaoAccessException {
		return query(sql, null, params).getDatas();
	}
	@Override
	public List<E> query(String sql, List<Object> params) throws DaoAccessException {
		return query(sql, params.toArray());
	}
	@Override
	public List<E> query(String sql, Map<String, Object> params) throws DaoAccessException {
		return query(sql, null, params).getDatas();
	}
	
	@Override
	public List<E> query(Condition condition, Class<?>... associationLink) throws DaoAccessException {
		String orders = null;
		return query(condition, orders, associationLink);
	}
	
	@Override
	public List<E> query(Condition condition, String orders, Class<?>... associationLink) throws DaoAccessException {
		return query(condition, orders, null, associationLink).getDatas();
	}

	@Override
	public DataStore<E> query(String sql, PagingParameter paging, Object... params) throws DaoAccessException {
		sql = handleSimpleSql(sql);
		return queryDataStore(sql, params, getRowMapperBySql(sql), paging);
	}
	@Override
	public DataStore<E> query(String sql, PagingParameter paging,
			List<Object> params) throws DaoAccessException {
		return query(sql, paging, params.toArray());
	}
	@Override
	public DataStore<E> query(String sql, PagingParameter paging,
			Map<String, Object> params) throws DaoAccessException {
		sql = handleSimpleSql(sql);
		return queryDataStore(sql, params, getRowMapperBySql(sql), paging);
	}

	@Override
	public DataStore<E> query(Condition condition, PagingParameter paging, Class<?>... associationLink) throws DaoAccessException {
		return query(condition, null, paging, associationLink);
	}
	
	@Override
	public DataStore<E> query(Condition condition, String orders, PagingParameter paging, Class<?>... associationLink) throws DaoAccessException {
		Object[] params = new Object[0];
		if(condition != null) {
			params = condition.getParameters();
		}
		if(associationLink != null && associationLink.length == 0) {
			String sql = condition==null?"":condition.toSqlString();
			if(orders == null) {
				orders = simpleSqlBuilder.getFieldColumnMapping().get(simpleSqlBuilder.getIdField());
			}
			if(!sql.equals("")) {
				sql += " ";
			}
 			sql += "ORDER BY " + orders;
			return query(sql, paging, params);
		}
		return queryDataStore(buildAssociationSql(condition, orders, associationLink), params, 
				simpleSqlBuilder.getRowMapper(associationLink), paging);
	}
	
	@Override
	public List<E> queryAll(Class<?>... associationLink) throws DaoAccessException {
		String orders = null;
		return queryAll(orders, associationLink);
	}

	@Override
	public List<E> queryAll(String orders, Class<?>... associationLink) throws DaoAccessException {
		return query(null, orders, associationLink);
	}

	@Override
	public DataStore<E> queryAll(PagingParameter paging, Class<?>... associationLink) throws DaoAccessException {
		return queryAll(null, paging, associationLink);
	}

	@Override
	public DataStore<E> queryAll(String orders, PagingParameter paging, Class<?>... associationLink) throws DaoAccessException {
		return query(null, orders, paging, associationLink);
	}

	@Override
	public int count() throws DaoAccessException {
		return count(null);
	}

	@Override
	public int count(Condition condition) throws DaoAccessException {
		try {
			String sql = simpleSqlBuilder.getQueryCountSql();
			if(condition == null) {
				return jdbcTemplate.queryForInt(sql);
			}
			sql += " WHERE " + condition.toSqlString();
			logger.debug(sql);
			return jdbcTemplate.queryForInt(sql, condition.getParameters());
		} catch (Exception e) {
			throw new DaoAccessException(e);
		}
	}

	@Override
	public <K extends Number> void deletes(List<K> ids) throws DaoAccessException {
		if(CollectionUtils.isEmpty(ids)) {
			return;
		}
		try {
			List<Object[]> batchArgs = new ArrayList<Object[]>();
			for (K id : ids) {
				batchArgs.add(new Object[]{id});
			}
			jdbcTemplate.batchUpdate(simpleSqlBuilder.getDeleteSql(), batchArgs);
		} catch (Exception e) {
			throw new DaoAccessException(e);
		}
	}

	@Override
	public <K extends Number> void saves(List<E> entitys, K... ids) throws DaoAccessException {
		if(CollectionUtils.isEmpty(entitys)) {
			return;
		}
		try {
			int entityCount = entitys.size();
			int idCount = ids.length;
			if(idCount > entityCount) {
				idCount = entityCount;
			}
			List<E> withIdEntitys = entitys.subList(0, idCount);
			List<E> withoutIdEntitys = entitys.subList(idCount, entityCount);
			if(CollectionUtils.isNotEmpty(withIdEntitys)) {
				
				Map<String, Object>[] batchArgs = new Map[idCount];
				for (int i = 0; i < idCount; i++) {
					E entity = withIdEntitys.get(i);
					if(!entity.isTransient()) {
						throw new DuplicateRecordException(ErrorCode.DUPLICATE_RECORDE_AS_SAVE_ENTITY, 
								"The record that whoes id equals " + entity.identityString() + " is already exist!");
					}
					batchArgs[i] = simpleSqlBuilder.getAllSqlParameter(entity);
					batchArgs[i].put(simpleSqlBuilder.getIdField(), ids[i]);
				}
				namedParameterJdbcTemplate.batchUpdate(simpleSqlBuilder.getIncludeIdFieldInsertSql(), batchArgs);
			}
			if(CollectionUtils.isNotEmpty(withoutIdEntitys)) {
				
				Map<String, Object>[] batchArgs = new Map[withoutIdEntitys.size()];
				for (int i = 0; i < batchArgs.length; i++) {
					E entity = withoutIdEntitys.get(i);
					if(!entity.isTransient()) {
						throw new DuplicateRecordException(ErrorCode.DUPLICATE_RECORDE_AS_SAVE_ENTITY, 
								"The record that whoes id equals " + entity.identityString() + " is already exist!");
					}
					batchArgs[i] = simpleSqlBuilder.getAllSqlParameter(entity);
				}
				namedParameterJdbcTemplate.batchUpdate(simpleSqlBuilder.getInsertSql(), batchArgs);
			}
		} catch (Exception e) {
			throw new DaoAccessException(e);
		}
	}

	@Override
	public void updates(List<E> entitys) throws DaoAccessException {
		if(CollectionUtils.isEmpty(entitys)) {
			return;
		}
		try {
			
			Map<String, Object>[] batchArgs = new Map[entitys.size()];
			for (int i = 0; i < batchArgs.length; i++) {
				E entity = entitys.get(i);
				if(entity.isTransient()) {
					throw new IllegalRecordException(ErrorCode.ILLEGAL_RECORD_AS_UPDATE_TRANSIENT,
							"The record " + entity + " is transient!");
				}
				batchArgs[i] = simpleSqlBuilder.getAllSqlParameter(entity);
			}
			namedParameterJdbcTemplate.batchUpdate(simpleSqlBuilder.getUpdateSql(), batchArgs);
		} catch (Exception e) {
			throw new DaoAccessException(e);
		}
	}

	@Override
	public void saveOrUpdates(List<E> entitys) throws DaoAccessException {
		if(CollectionUtils.isEmpty(entitys)) {
			return;
		}
		List<E> saves = new ArrayList<E>();
		List<E> updates = new ArrayList<E>();
		for (E entity : entitys) {
			if(entity.isTransient()) {
				saves.add(entity);
			} else {
				updates.add(entity);
			}
		}
		saves(saves);
		updates(updates);
	}

	@Override
	public E getReferenced(E entity) throws DaoAccessException {
		return getReferenced(entity, entityClass);
	}

	@Override
	public E getReferenced(Object referenceValue) throws DaoAccessException {
		return getReferenced(referenceValue, entityClass);
	}
	
	@Override
	public <R extends Entity> R getReferenced(E entity, Class<R> referencedClass) throws DaoAccessException {
		Object value = null;
		try {
			String field = simpleSqlBuilder.getReferenceField(referencedClass);
			if(field == null) {
				throw new AnnotationNotFoundException(ErrorCode.ANNOTATION_NOT_FOUND_AS_REFERENCE, 
						"Annotation " + Reference.class.getName() + " not found for referenced " +
						referencedClass.getName() + " of " + entityClass.getName());
			}
			value = new PropertyDescriptor(field, entityClass).getReadMethod().invoke(entity);
		} catch (Exception e) {
			throw new DaoAccessException(e);
		}
		if(value == null) {
			return null;
		}
		return getReferenced(value, referencedClass);
	}

	@Override
	public <R extends Entity> R getReferenced(Object referenceValue,
			Class<R> referencedClass) throws DaoAccessException {
		try {
			String referencedColumn = simpleSqlBuilder.getReferencedColumn(referencedClass);
			if(referencedColumn == null) {
				throw new AnnotationNotFoundException(ErrorCode.ANNOTATION_NOT_FOUND_AS_REFERENCE, 
						"Annotation " + Reference.class.getName() + " not found for referenced " +
						referencedClass.getName() + " of " + entityClass.getName());
			}
			SimpleSqlBuilder<R> referencedSqlBuilder = new SimpleSqlBuilder<R>(referencedClass);
			String sql = referencedSqlBuilder.getQueryAllSql() + " WHERE " + referencedColumn + " = ?";
			logger.debug(sql);
			return jdbcTemplate.queryForObject(sql, referencedSqlBuilder.getRowMapper(), referenceValue);
		} catch (Exception e) {
			throw new DaoAccessException(e);
		}
	}
	
	@Override
	public <R extends Entity> List<E> queryReferences(R referenced) throws DaoAccessException {
		PagingParameter paging = null;
		return queryReferences(referenced, paging).getDatas();
	}

	@Override
	public <R extends Entity> List<E> queryReferences(Class<R> referencedClass, Object referencedValue) throws DaoAccessException {
		PagingParameter paging = null;
		return queryReferences(referencedClass, referencedValue, paging).getDatas();
	}
	
	@Override
	public <R extends Entity, S extends Entity> List<S> queryReferences(
			R referenced, Class<S> referenceClass) throws DaoAccessException {
		return queryReferences(referenced, referenceClass, null).getDatas();
	}

	@Override
	public <R extends Entity, S extends Entity> List<S> queryReferences(
			Class<R> referencedClass, Object referencedValue,
			Class<S> referenceClass) throws DaoAccessException {
		return queryReferences(referencedClass, referencedValue, referenceClass, null).getDatas();
	}
	
	@Override
	public <R extends Entity> DataStore<E> queryReferences(R referenced,
			PagingParameter paging) throws DaoAccessException {
		Object value = getReferencedValue(referenced);
		if(value == null) {
			return null;
		}
		return queryReferences(referenced.getClass(), value, paging);
	}

	@Override
	public <R extends Entity> DataStore<E> queryReferences(
			Class<R> referencedClass, Object referencedValue,
			PagingParameter paging) throws DaoAccessException {
		String sql = null;
		try {
			String field = simpleSqlBuilder.getReferenceField(referencedClass);
			if(field == null) {
				throw new AnnotationNotFoundException(ErrorCode.ANNOTATION_NOT_FOUND_AS_REFERENCE, 
						"Annotation " + Reference.class.getName() + " not found for referenced " +
						referencedClass.getName() + " of " + entityClass.getName());
			}
			sql = simpleSqlBuilder.getQueryAllSql() + " WHERE " + 
			simpleSqlBuilder.getFieldColumnMapping().get(field) + " = ? ORDER BY " +
			simpleSqlBuilder.getFieldColumnMapping().get(simpleSqlBuilder.getIdField());
		} catch (Exception e) {
			throw new DaoAccessException(e);
		}
		return queryDataStore(sql, new Object[]{referencedValue}, simpleSqlBuilder.getRowMapper(), paging);
	}
	
	@Override
	public <R extends Entity, S extends Entity> DataStore<S> queryReferences(
			R referenced, Class<S> referenceClass, PagingParameter paging) throws DaoAccessException {
		Object value = getReferencedValue(referenced);
		if(value == null) {
			return null;
		}
		return queryReferences(referenced.getClass(), value, referenceClass, paging);
	}

	@Override
	public <R extends Entity, S extends Entity> DataStore<S> queryReferences(
			Class<R> referencedClass, Object referencedValue,
			Class<S> referenceClass, PagingParameter paging) throws DaoAccessException {
		String sql = null;
		SimpleSqlBuilder<S> referenceSqlBuilder = null;
		try {
			String referencedColumn = simpleSqlBuilder.getReferencedColumn(referenceClass);
			if(referencedColumn == null) {
				throw new AnnotationNotFoundException(ErrorCode.ANNOTATION_NOT_FOUND_AS_REFERENCE, 
						"Annotation " + Reference.class.getName() + " not found for referenced " +
						referenceClass.getName() + " of " + entityClass.getName());
			}
			String field = simpleSqlBuilder.getReferenceField(referencedClass);
			if(field == null) {
				throw new AnnotationNotFoundException(ErrorCode.ANNOTATION_NOT_FOUND_AS_REFERENCE, 
						"Annotation " + Reference.class.getName() + " not found for referenced " +
						referencedClass.getName() + " of " + entityClass.getName());
			}
			referenceSqlBuilder = new SimpleSqlBuilder<S>(referenceClass);
			sql = referenceSqlBuilder.getQueryAllSql() + " WHERE " + referencedColumn + " IN (SELECT " + 
					simpleSqlBuilder.getFieldColumnMapping().get(simpleSqlBuilder.getReferenceField(referenceClass)) + 
					" FROM " + simpleSqlBuilder.getTableName() + " WHERE " + 
					simpleSqlBuilder.getFieldColumnMapping().get(field) + " = ?) ORDER BY " + 
					referenceSqlBuilder.getFieldColumnMapping().get(referenceSqlBuilder.getIdField());
		} catch (Exception e) {
			throw new DaoAccessException(e);
		}
		return queryDataStore(sql, new Object[]{referencedValue}, referenceSqlBuilder.getRowMapper(), paging);
	}

	/**
	 * 根据被引用对象获得外键属性值
	 *
	 * @param <R>
	 * @param referenced
	 * @return
	 * 创建日期：2012-10-8
	 * 修改说明：
	 * @author wangk
	 */
	private <R extends Entity> Object getReferencedValue(R referenced) throws DaoAccessException {
		try {
			
			Class<R> referencedClass = (Class<R>)referenced.getClass();
			String referencedColumn = simpleSqlBuilder.getReferencedColumn(referencedClass);
			if(referencedColumn == null) {
				throw new AnnotationNotFoundException(ErrorCode.ANNOTATION_NOT_FOUND_AS_REFERENCE, 
						"Annotation " + Reference.class.getName() + " not found for referenced " +
						referencedClass.getName() + " of " + entityClass.getName());
			}
			SimpleSqlBuilder<R> referencedSqlBuilder = new SimpleSqlBuilder<R>(referencedClass);
			String columnField = referencedSqlBuilder.getColumnField(referencedColumn);
			if(columnField == null) {
				throw new FieldColumnMappingException(ErrorCode.FIELD_COLUMN_MAPPING_AS_NOT_EXIST_COLUMN, 
						"ColumnName " + referencedColumn + " not exist for " + referencedClass.getName());
			}
			return new PropertyDescriptor(columnField, referencedClass).getReadMethod().invoke(referenced);
		} catch (Exception e) {
			throw new DaoAccessException(e);
		}
	}

	/**
	 * 构建关联查询SQL语句
	 *
	 * @param condition       查询条件
	 * @param orders          排序对象
	 * @param associationLink 关联链
	 * @return String         关联查询SQL语句
	 * 创建日期：2012-12-5
	 * 修改说明：
	 * @author wangk
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String buildAssociationSql(Condition condition, String orders, Class<?>[] associationLink) throws DaoAccessException {
		try {
			if(associationLink == null) {
				associationLink = simpleSqlBuilder.getAssociationLink();
			}
			StringBuilder sb1 = new StringBuilder();
			StringBuilder sb2 = new StringBuilder();
			String tableName = simpleSqlBuilder.getTableName() + "_0";
			Map<String, String> fieldColumnMapping = simpleSqlBuilder.getFieldColumnMapping();
			for (String field : fieldColumnMapping.keySet()) {
				sb1.append(tableName +  "." + fieldColumnMapping.get(field) + " AS " + 
						tableName +  "_" + fieldColumnMapping.get(field) + ", ");
			}
			sb2.append(simpleSqlBuilder.getTableName()).append(" AS ").append(tableName);
			//关联类型三标志(被关联类型下标、被关联类型、关联类型)集合
			List<List<Object>> associationFlags = new ArrayList<List<Object>>();
			for (int i = 0;i < associationLink.length;i++) {
				Class<?> tempClass = associationLink[i];
				if(!ObjectUtil.isExtends(tempClass, Entity.class)) {
					throw new FieldColumnMappingException(ErrorCode.FIELD_COLUMN_MAPPING_AS_ASSOCIATION_TYPE_ERROR,
							"Association " + tempClass.getName() + " type error for " +
							entityClass.getName() + ", the type must extends Entity!");
				}
				Class<? extends Entity> associationClass = (Class<? extends Entity>)tempClass;
				SimpleSqlBuilder<? extends Entity> associationSqlBuilder = new SimpleSqlBuilder(associationClass);
				Class<? extends Entity> clazz = null;
				SimpleSqlBuilder<? extends Entity> sqlBuilder = null;
				int j = -1;
				for (; j < i; j++) {
					if(j >= 0) {
						clazz = (Class<? extends Entity>)associationLink[j];
					} else {
						clazz = entityClass;
					}
					sqlBuilder = new SimpleSqlBuilder(clazz);
					if(sqlBuilder.getAssociationField(associationClass) != null) {
						List<Object> associationFlag = new ArrayList<Object>();
						associationFlag.add(j + 1);
						associationFlag.add(clazz);
						associationFlag.add(associationClass);
						if(!associationFlags.contains(associationFlag)) {
							//检查指定的关联类型对应的引用属性是否存在
							sqlBuilder.checkExistReferenceField(associationClass);
							associationFlags.add(associationFlag);
							break;
						}
					}
				}
				if(j == i) {
					throw new AnnotationNotFoundException(ErrorCode.ANNOTATION_NOT_FOUND_AS_ASSOCIATION,
							"Annotation " + Association.class.getName() + " not found for association " +
							associationClass.getName() + " of " + entityClass.getName());
				}
				String tableName2 = associationSqlBuilder.getTableName() + "_" + (i + 1);
				Map<String, String> fieldColumnMapping2 = associationSqlBuilder.getFieldColumnMapping();
				for (String field : fieldColumnMapping2.keySet()) {
					sb1.append(tableName2 +  "." + fieldColumnMapping2.get(field) + " AS " + 
							tableName2 +  "_" + fieldColumnMapping2.get(field) + ", ");
				}
				String tableName1 = sqlBuilder.getTableName() + "_" + (j + 1);
				Map<String, String> fieldColumnMapping1 = sqlBuilder.getFieldColumnMapping();
				sb2.append(" LEFT JOIN " + associationSqlBuilder.getTableName() + " AS " + tableName2 + " ON " + tableName1 + "." + 
						fieldColumnMapping1.get(sqlBuilder.getReferenceField(associationClass)) + 
						" = " + tableName2 + "." + sqlBuilder.getReferencedColumn(associationClass));
			}
			sb1.delete(sb1.length() - 2, sb1.length());
			String sql = "SELECT " + sb1 + " FROM " + sb2;
			if(condition != null) {
				sql += " WHERE " + handleSqlColumnPrefix(condition.toSqlString(), associationLink);
			}
			if(orders == null) {
				orders = simpleSqlBuilder.getFieldColumnMapping().get(simpleSqlBuilder.getIdField());
			}
			sql += " ORDER BY " + handleSqlColumnPrefix(orders, associationLink);
			logger.debug(sql);
			return sql;
		} catch (Exception e) {
			throw new DaoAccessException(e);
		}
	}

	/**
	 * 处理SQL子句的列名前缀，以区分来自不同的表
	 * 	1. 没有前缀则加上：entityClass#tableName_0.
	 *  2. 有前缀但没有下标则加上下标，如entityClass#tableName.处理成entityClass#tableName_0.
	 *     (关联链中有两个相同的类型时如果没有下标则加上第一次出现的下标，要指定后面类型的列必须带下标)
	 *  3. entityClass下标为0，associationLink的第一个元素下标为1，依次类批(即数组下标加一)
	 *  4. 出现的表名不能以'_数字'结尾
	 *
	 * @param sql              条件SQL语句
	 * @param associationLink  关联链
	 * @return
	 * 创建日期：2012-12-5
	 * 修改说明：
	 * @author wangk
	 */
	private String handleSqlColumnPrefix(String sql, Class<?>[] associationLink) {
		if(sql == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		int start = 0;
		int end = 0;
		for (int i = 0; i < sql.length(); i++) {
			char ch = sql.charAt(i);
			if(ch == '\'') {
				end = i;
				sb.append(handleSubSqlColumnPrefix(sql.substring(start, end), associationLink));
				start = end;
				for (i++; i < sql.length(); i++) {
					if(sql.charAt(i) == ch) {
						end = i + 1;
						sb.append(sql.substring(start, end));
						start = end;
						break;
					}
				}
			} else if(ch == '(' && sql.substring(i+1).trim().toUpperCase().startsWith("SELECT")) {
				end = i;
				sb.append(handleSubSqlColumnPrefix(sql.substring(start, end), associationLink));
				start = end;
				Stack<Character> stack = new Stack<Character>();
				stack.push(ch);
				for (i++; i < sql.length(); i++) {
					char ch2 = sql.charAt(i);
					if(ch2 == ch) {
						stack.push(ch);
					} else if(ch2 == ')') {
						stack.pop();
					}
					if(stack.isEmpty()) {
						end = i + 1;
						sb.append(sql.substring(start, end));
						start = end;
						break;
					}
				}
			}
		}
		end = sql.length();
		sb.append(handleSubSqlColumnPrefix(sql.substring(start, end), associationLink));
		return sb.toString().trim();
	}

	/**
	 * 处理一段SQL子句的列名前缀，不包含子查询和字符串数据
	 *
	 * @param subSql          一段SQL子句
	 * @param associationLink 关联链
	 * @return String         处理结果，结尾带空格
	 * 创建日期：2012-12-6
	 * 修改说明：
	 * @author wangk
	 */
	private String handleSubSqlColumnPrefix(String subSql, Class<?>[] associationLink) {
		//处理组合符号（= -> #, < -> @, > -> &）
		subSql = subSql.replaceAll("\\s*<>\\s*", " @& ").replaceAll("\\s*<=\\s*", " @# ").replaceAll("\\s*>=\\s*", " &# ");
		for (String operator : CONDITION_OPERATORS) {
			//处理单个符号
			subSql = subSql.replaceAll("\\s*" + operator + "\\s*", " " + operator + " ");
		}
		subSql = subSql.replaceAll("@&", "<>").replaceAll("@#", "<=").replaceAll("&#", ">=");
		String[] strings = subSql.split("\\s+");
		StringBuilder sb = new StringBuilder();
		for (String string : strings) {
			if(string.matches("^[A-Za-z_][\\w]*(\\.\\w+)?$") && !CONDITION_KEYWORDS.contains(string.toUpperCase())) {
				sb.append(handleColumnPrefix(string, associationLink));
			} else {
				sb.append(string);
			}
			sb.append(" ");
		}
		return sb.toString();
	}
	
	/**
	 * 处理列名前缀
	 *
	 * @param column           列名
	 * @param associationLink  关联链
	 * @return String          带前缀的列名
	 * 创建日期：2012-12-5
	 * 修改说明：
	 * @author wangk
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String handleColumnPrefix(String column, Class<?>[] associationLink) {
		if(column.indexOf(".") < 0) {
			return simpleSqlBuilder.getTableName() + "_0." + column;
		}
		String[] strings = column.split("\\.");
		String tableName = strings[0];
		if(!tableName.matches("^.*_\\d+$")) {
			int index = 0;
			if(!tableName.equalsIgnoreCase(simpleSqlBuilder.getTableName())) {
				int i = 0;
				for (; i < associationLink.length; i++) {
					if(tableName.equalsIgnoreCase(new SimpleSqlBuilder(associationLink[i]).getTableName())) {
						break;
					}
				}
				index = i + 1;
			}
			return tableName + "_" + index + "." + strings[1];
		}
		return column;
	}

	/**
	 * 获得指定entitys的唯一实体对象，没有返回null，两个及以上抛出DuplicateRecordException
	 *
	 * @param entitys    实体集合
	 * @return
	 * 创建日期：2012-12-6
	 * 修改说明：
	 * @author wangk
	 */
	private E getUniqueEntity(List<E> entitys) {
		if(CollectionUtils.isEmpty(entitys)) {
			return null;
		}
		if(entitys.size() == 1) {
			return entitys.get(0);
		}
		throw new DuplicateRecordException(ErrorCode.DUPLICATE_RECORDE_AS_GET_ENTITY);
	}

	/**
	 * 保存实体对象，ID值由参数指定，如果ID指定为null则由数据库生成
	 *
	 * @param <K>     ID类型参数
	 * @param entity  实体对象
	 * @param id      ID值
	 * @return
	 * 创建日期：2012-12-3
	 * 修改说明：
	 * @author wangk
	 */
	private <K extends Number> K saveWithId(E entity, K id) throws DaoAccessException {
		try {
			if(!entity.isTransient()) {
				throw new DuplicateRecordException(ErrorCode.DUPLICATE_RECORDE_AS_SAVE_ENTITY,
						"The record " + entity + " is exist!");
			}
			
			Class<K> idFieldType = (Class<K>)new PropertyDescriptor(simpleSqlBuilder.getIdField(), entityClass).getPropertyType();
			Map<String, Object> params = simpleSqlBuilder.getSqlParameters(entity);
			if(id == null) {
				String sql = simpleSqlBuilder.getInsertSql(params.keySet());
				KeyHolder keyHolder = new GeneratedKeyHolder();
				namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(params), keyHolder);
				id = idFieldType.getConstructor(String.class).newInstance(keyHolder.getKey().toString());
			} else {
				params.put(simpleSqlBuilder.getIdField(), id);
				String sql = simpleSqlBuilder.getIncludeIdFieldInsertSql(params.keySet());
				namedParameterJdbcTemplate.update(sql, params);
			}
			//设置entity的ID值
			new PropertyDescriptor(simpleSqlBuilder.getIdField(), entityClass).getWriteMethod().invoke(entity, id);
			return id;
		} catch (Exception e) {
			throw new DaoAccessException(e);
		}
	}
	
	/**
	 * 处理单表查询的SQL语句，SQL为null或空字符串或只指定WHERE子句时处理成查询全部记录
	 *
	 * @param sql       SQL语句
	 * @return String   处理后的SQL语句
	 * 创建日期：2012-12-6
	 * 修改说明：
	 * @author wangk
	 */
	private String handleSimpleSql(String sql) {
		if(StringUtils.isBlank(sql)) {
			sql = simpleSqlBuilder.getQueryAllSql();
		} else if(!sql.trim().toUpperCase().startsWith("SELECT")) {
			if(!sql.trim().toUpperCase().startsWith("WHERE") && !sql.trim().toUpperCase().startsWith("ORDER")) {
				sql = "WHERE " + sql;
			}
			sql = simpleSqlBuilder.getQueryAllSql() + " " + sql;
		}
		logger.debug(sql);
		return sql;
	}
	
	/**
	 * 根据SQL语句获取不同的RowMapper，如果指定了投影列则只映射指定列对应的属性，其他属性为null，否则映射全部属性
	 *
	 * @param sql           SQL语句
	 * @return RowMapper<E> 记录映射对象
	 * 创建日期：2012-12-6
	 * 修改说明：
	 * @author wangk
	 */
	private RowMapper<E> getRowMapperBySql(String sql) {
		sql = sql.trim().toUpperCase();
		String projection = sql.substring(sql.indexOf("SELECT")+6, sql.indexOf("FROM")).trim();
		if(projection.indexOf("*") >=0) {
			return simpleSqlBuilder.getRowMapper();
		}
		Set<String> columns = new HashSet<String>();
		for (String column : projection.split(",")) {
			columns.add(column.trim().replaceAll("\\s.*$", ""));
		}
		return simpleSqlBuilder.getRowMapper(columns);
	}

	/**
	 * 查询分页数据，如果指定的paging为null或参数不正确则查询出全部的数据
	 *
	 * @param <T>           SQL参数类型，限制只能使用Object[]或Map<String, Object>
	 * @param sql           SQL语句
	 * @param params        SQL参数
	 * @param rowMapper     记录映射对象
	 * @param paging        分页参数
	 * @return DataStore<E> 分页数据
	 * @throws DaoAccessException DAO访问异常
	 * 创建日期：2012-12-6
	 * 修改说明：
	 * @author wangk
	 */
	private <P, S extends Entity> DataStore<S> queryDataStore(
			String sql, P params, RowMapper<S> rowMapper, PagingParameter paging) throws DaoAccessException {
		try {
			if(paging == null || paging.isInvalid()) {
				if(params instanceof Map) {
					return new DataStore<S>(paging, namedParameterJdbcTemplate.query(sql, new MapSqlParameterSource((Map<String, Object>)params), rowMapper));
				}
				return new DataStore<S>(paging, jdbcTemplate.query(sql, rowMapper, (Object[])params));
			}
			int records = 0;
			if(params instanceof Map) {
				records = namedParameterJdbcTemplate.queryForInt(pagingSqlBuilder.getCountSql(sql), (Map<String, Object>)params);
			} else {
				records = jdbcTemplate.queryForInt(pagingSqlBuilder.getCountSql(sql), (Object[])params);
			}
			if(records < 0) {
				return null;
			}
			if(records == 0) {
				return new DataStore<S>(records, new ArrayList<S>());
			}
			if(params instanceof Map) {
				return new DataStore<S>(records, namedParameterJdbcTemplate.query(
						pagingSqlBuilder.getPagingSql(sql, paging), new MapSqlParameterSource((Map<String, Object>)params), rowMapper));
			}
			return new DataStore<S>(records, jdbcTemplate.query(pagingSqlBuilder.getPagingSql(sql, paging), rowMapper, (Object[])params));
		} catch (Exception e) {
			throw new DaoAccessException(e);
		}
	}

}
