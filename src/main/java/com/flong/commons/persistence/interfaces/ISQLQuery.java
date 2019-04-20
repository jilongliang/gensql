package com.flong.commons.persistence.interfaces;

import java.util.List;
import java.util.Map;

import com.flong.commons.persistence.bean.SimplePage;
 
@SuppressWarnings("all")
public abstract interface ISQLQuery {
	
	/**
	 * 不支持参数，返回总条数的查询
	 * @param sql
	 * @return
	 */
	public abstract int getCount(String sql);

	/**
	 * 支持sql并传参数的查询，返回总条数
	 * @param sql
	 * @param params
	 * @return
	 */
	public abstract int getCount(String sql, Object[] params);

	/***
	 * 查询所有的数据以Map<String,Object>的方式返回
	 * @param sql
	 * @return
	 */
	public abstract List query(String sql);
	/**
	 * 查询所有的数据以Map<String,Object>的方式返回.
	 * @param sql
	 * @return
	 */
	public abstract List query(String sql, Object[] params);

	/**
	 * 带分页的sql查询，不支持JavaBean的转换
	 * 分页查询的数据以Map<String,Object>的方式返回
	 * @param sql
	 * @param simplePage
	 * @return
	 */
	public abstract List query(String sql, SimplePage simplePage);
	/**
	 * 带分页的sql查询，不支持JavaBean的转换，支持传参数的查询.
	 * @param sql
	 * @param simplePage
	 * @return
	 */
	public abstract List query(String sql, Object[] params, SimplePage simplePage);

	/***
	 * 支持多表查询的转换的JavaBean对象的分页查询.
	 * 分页查询的数据以Map<String,Object>的方式返回
	 * @param sql
	 * @param paramClass转换的JavaBean对象
	 * @param simplePage分页对象
	 * @return
	 */
	public abstract <T> List query(String sql, Class<T> paramClass, SimplePage simplePage);

	/****
	 * 处理分页的查询..
	 * @param sql
	 * @param params
	 * @param paramClass
	 * @param simplePage
	 * @return
	 */
	public abstract <T> List query(String sql,Object[] params, Class<T> paramClass, SimplePage simplePage);
	
	/***
	 * 获取名称
	 * @param sql
	 * @return
	 */
	public List<String> getName(String sql) ;

	/**
	 * 获取字段的名称和类型
	 * @param sql
	 * @return
	 */
	public List<String[]> getNameAndType(String sql) ;
	
	/**
	 * 获取一个List的Map集合
	 * @param sql
	 * @return
	 */
	public List<Map<String, Object>> getListBySql(String sql);

	/**
	 * 获取一个多为数组的List集合
	 * @param sql
	 * @return
	 */
	public <T> List<T> queryForList(String sql,Class<T> elementType);

}