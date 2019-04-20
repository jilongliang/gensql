package com.flong.commons.persistence.dao;

import java.util.List;
import java.util.Map;

import com.flong.commons.lang.exception.DaoAccessException;
import com.flong.commons.persistence.bean.DataStore;
import com.flong.commons.persistence.bean.PagingParameter;
import com.flong.commons.persistence.condition.Condition;
import com.flong.commons.persistence.interfaces.MapRowMapper;

/**
 * 数据查询DAO接口
 *
 * 创建日期：2012-9-26
 * @author wangk
 */
public interface BaseDao {
	
	/**
	 * 查询SQL语句
	 *
	 * @param sql                        SQL语句
	 * @param params                     SQL参数
	 * @return List<Map<String, Object>> 查询结果
	 * 创建日期：2012-9-26
	 * 修改说明：
	 * @author wangk
	 */
	public List<Map<String, Object>> search(String sql, Object... params) throws DaoAccessException;
	public List<Map<String, Object>> search(String sql, List<Object> params) throws DaoAccessException;
	public List<Map<String, Object>> search(String sql, Map<String, Object> params) throws DaoAccessException;
	
	/**
	 * 查询SQL语句
	 *
	 * @param <R>           行记录类型
	 * @param sql           SQL语句
	 * @param mapRowMapper  Map行数据映射对象
	 * @param params        SQL参数 
	 * @return List<R>      查询结果
	 * 创建日期：2012-9-26
	 * 修改说明：
	 * @author wangk
	 */
	public <R> List<R> search(String sql, MapRowMapper<R> mapRowMapper, Object... params) throws DaoAccessException;
	public <R> List<R> search(String sql, MapRowMapper<R> mapRowMapper, List<Object> params) throws DaoAccessException;
	public <R> List<R> search(String sql, MapRowMapper<R> mapRowMapper, Map<String, Object> params) throws DaoAccessException;
	
	/**
	 * 分页查询数据
	 *
	 * @param sql                             SQL语句
	 * @param paging                          分页参数
	 * @param params                          SQL参数
	 * @return DataStore<Map<String, Object>> 分页数据
	 * 创建日期：2012-9-26
	 * 修改说明：
	 * @author wangk
	 */
	public DataStore<Map<String, Object>> search(String sql, PagingParameter paging, Object... params) throws DaoAccessException;
	public DataStore<Map<String, Object>> search(String sql, PagingParameter paging, List<Object> params) throws DaoAccessException;
	public DataStore<Map<String, Object>> search(String sql, PagingParameter paging, Map<String, Object> params) throws DaoAccessException;
	
	/**
	 * 分页查询数据
	 *
	 * @param <R>           行记录类型
	 * @param sql           SQL语句
	 * @param mapRowMapper  Map行数据映射对象
	 * @param paging        分页参数
	 * @param params        SQL参数
	 * @return DataStore<R> 分页数据
	 * 创建日期：2012-9-26
	 * 修改说明：
	 * @author wangk
	 */
	public <R> DataStore<R> search(String sql, MapRowMapper<R> mapRowMapper, PagingParameter paging, Object... params) throws DaoAccessException;
	public <R> DataStore<R> search(String sql, MapRowMapper<R> mapRowMapper, PagingParameter paging, List<Object> params) throws DaoAccessException;
	public <R> DataStore<R> search(String sql, MapRowMapper<R> mapRowMapper, PagingParameter paging, Map<String, Object> params) throws DaoAccessException;

	/**
	 * 连接查询
	 *
	 * @param condition                  查询条件，指定列格式：表名.列名
	 * @param classLink                  连接实体类类型链
	 * @return List<Map<String, Object>> 查询结果，Map.key: 对象名(Class指定的类名的首字母小写形式).属性名
	 * 创建日期：2012-10-19
	 * 修改说明：
	 * @author wangk
	 */
	public List<Map<String, Object>> join(Condition condition, Class<?>... classLink) throws DaoAccessException;
	
	/**
	 * 连接查询
	 *
	 * @param condition 查询条件
	 * @param orders    排序对象
	 * @param classLink 连接实体类类型链
	 * @return          查询结果
	 * 创建日期：2012-10-19
	 * 修改说明：
	 * @author wangk
	 */
	public List<Map<String, Object>> join(Condition condition, String orders, Class<?>... classLink) throws DaoAccessException;
	
	/**
	 * 连接查询（内连接）
	 *
	 * @param <R>          映射类型参数
	 * @param condition    查询条件
	 * @param mapRowMapper 行匹配对象
	 * @param classLink    连接实体类型链（从子表到父表的顺序）
	 * @return
	 * 创建日期：2012-10-19
	 * 修改说明：
	 * @author wangk
	 */
	public <R> List<R> join(Condition condition, MapRowMapper<R> mapRowMapper, Class<?>... classLink) throws DaoAccessException;	
	
	/**
	 * 连接查询
	 *
	 * @param <R>          映射类型参数
	 * @param condition    查询条件
	 * @param orders       排序对象
	 * @param mapRowMapper 行匹配对象
	 * @param classLink    连接实体类型链
	 * @return
	 * 创建日期：2012-10-19
	 * 修改说明：
	 * @author wangk
	 */
	public <R> List<R> join(Condition condition, String orders, MapRowMapper<R> mapRowMapper, Class<?>... classLink) throws DaoAccessException;	
	
	/**
	 * 连接查询
	 *
	 * @param condition   查询条件
	 * @param paging      分页参数
	 * @param classLink   连接实体类型链
	 * @return
	 * 创建日期：2012-10-19
	 * 修改说明：
	 * @author wangk
	 */
	public DataStore<Map<String, Object>> join(Condition condition, PagingParameter paging, Class<?>... classLink) throws DaoAccessException;
	
	/**
	 * 连接查询
	 *
	 * @param condition  查询条件
	 * @param orders     排序对象
	 * @param paging     分页参数
	 * @param classLink  连接实体类型链
	 * @return
	 * 创建日期：2012-10-19
	 * 修改说明：
	 * @author wangk
	 */
	public DataStore<Map<String, Object>> join(Condition condition, String orders, PagingParameter paging, Class<?>... classLink) throws DaoAccessException;
	
	/**
	 * 连接查询
	 *
	 * @param <R>          映射类型参数
	 * @param condition    查询条件
	 * @param mapRowMapper 行匹配对象
	 * @param paging       分页参数
	 * @param classLink    连接实体类型链
	 * @return
	 * 创建日期：2012-10-19
	 * 修改说明：
	 * @author wangk
	 */
	public <R> DataStore<R> join(Condition condition, MapRowMapper<R> mapRowMapper, PagingParameter paging, Class<?>... classLink) throws DaoAccessException;
	
	/**
	 * 连接查询
	 *
	 * @param <R>          映射类型参数
	 * @param condition    查询条件
	 * @param orders       排序对象
	 * @param mapRowMapper 行匹配对象
	 * @param paging       分页参数
	 * @param classLink    连接实体类型链
	 * @return
	 * 创建日期：2012-10-19
	 * 修改说明：
	 * @author wangk
	 */
	public <R> DataStore<R> join(Condition condition, String orders, MapRowMapper<R> mapRowMapper, PagingParameter paging, Class<?>... classLink) throws DaoAccessException;

}
