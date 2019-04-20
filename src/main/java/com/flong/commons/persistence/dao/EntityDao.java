package com.flong.commons.persistence.dao;

import java.util.List;
import java.util.Map;

import com.flong.commons.lang.exception.DaoAccessException;
import com.flong.commons.persistence.Entity;
import com.flong.commons.persistence.bean.DataStore;
import com.flong.commons.persistence.bean.PagingParameter;
import com.flong.commons.persistence.condition.Condition;

/**
 * 实体类DAO接口
 *
 * 创建日期：2012-9-24
 * @author wangk
 */
@SuppressWarnings("all")
public interface EntityDao<E extends Entity> extends BaseDao {
	
	/**
	 * 根据ID值获得实体对象，可选参数associationLink，如果不指定只查询单个实体对象
	 * 否则使用左连接查询当前实体(左连接查询的主表)和associationLink指定的实体对象
	 *
	 * @param id               ID值
	 * @param associationLink  <p>关联实体链，从当前实体的父表开始依次指定，必须按从子表到父表的顺序且不能间断
	 * 						       否则将抛出AnnotationNotFoundException
	 * 						       如果有多个分支，则各分支可以依次指定，各个分支之间没有顺序
	 * 						   </p>
	 * @return E               实体对象，记录不存在时返回null
	 * 创建日期：2012-9-25
	 * 修改说明：
	 * @author wangk
	 */
	public <K extends Object> E get(K id, Class<?>... associationLink) throws DaoAccessException;
	
	/**
	 * 根据SQL语句获得实体对象，
	 *
	 * @param sql     SQL语句
	 * @param params  查询参数
	 * @return E 没有记录返回null, 仅有一条记录返回实体对象，否则抛出DuplicatedRecordException
	 * 			  如果SQL语句指定了查询列，则返回的实体对象只有相应的属性有值，其他为null
	 * 创建日期：2012-9-26
	 * 修改说明：
	 * @author wangk
	 */
	public E get(String sql, Object... params) throws DaoAccessException;
	public E get(String sql, List<Object> params) throws DaoAccessException;
	public E get(String sql, Map<String, Object> params) throws DaoAccessException;
	
	/**
	 * 根据Condition对象获得实体对象
	 *
	 * @param condition       <p>条件对象，条件出现的列如果没有指定所属表则默认为当前实体对应的表
	 * 						  associationLink中的实体的列必须带所属表前缀，其格式为：表名.列名
	 * 						    如果associationLink中有相同的实体（包括和当前实体相同）
	 * 						    则相同的实体必须在表名后加上实体序号后缀（第一个实体除外），其格式为：表名_序号.列名
	 * 						    当前实体的序号为0，associationLink中的第一个实体的序号为1，依次类推
	 * 						    如果associationLink中的实体对应的表名以‘_数字’结尾则指定列的所属表时必须加实体序号后缀
	 * 						  </p>
	 * @param associationLink 关联实体链，参数说明请参考get(id, associationLink)
	 * @return E
	 * 创建日期：2012-10-8
	 * 修改说明：
	 * @author wangk
	 */
	public E get(Condition condition, Class<?>... associationLink) throws DaoAccessException;
	
	/**
	 * 通过ID值删除记录
	 *
	 * @param id
	 * 创建日期：2012-9-25
	 * 修改说明：
	 * @author wangk
	 */
	public <K extends Number> void delete(K id) throws DaoAccessException;

	/**
	 * 新增记录（不指定ID值，ID值由数据库生成）
	 *
	 * @param <K>     ID属性的类型参数
	 * @param entity  新增记录的实体对象，其ID属性值必须为空，否则将抛出DuplicateRecordException
	 * @return K      数据库生成的ID值，接收返回值的变量类型必须和ID属性的类型相同，否则将抛出ClassCastException
	 * 创建日期：2012-9-25
	 * 修改说明：
	 * @author wangk
	 */
	public <K extends Number> K save(E entity) throws DaoAccessException;
	
	/**
	 * 新增记录（指定ID值）
	 *
	 * @param <K>    ID属性的类型参数
	 * @param entity 新增记录的实体对象，其ID属性值必须为空，否则将抛出DuplicateRecordException
	 * @param id     ID值，如果指定的ID值为null，则ID值由数据库生成
	 * 创建日期：2012-12-7
	 * 修改说明：
	 * @author wangk
	 */
	public <K extends Number> void save(E entity, K id) throws DaoAccessException;

	/**
	 * 更新记录
	 *
	 * @param entity
	 * 创建日期：2012-9-25
	 * 修改说明：
	 * @author wangk
	 */
	public void update(E entity) throws DaoAccessException;

	/**
	 * 新增或更新记录，ID值为空做新增操作，ID值非空做更新操作
	 *
	 * @param entity
	 * 创建日期：2012-9-25
	 * 修改说明：
	 * @author wangk
	 */
	public void saveOrUpdate(E entity) throws DaoAccessException;

	/**
	 * 根据SQL语句查询记录
	 *
	 * @param sql       SQL语句，可以只是WHERE子句，为null或空字符串时表时查询全部记录
	 * @param params    SQL参数
	 * @return List<E>  实体集合，若SQL语句指定了查询列，则实体对象中没有被指定列对应的属性为空值
	 * 创建日期：2012-9-25
	 * 修改说明：
	 * @author wangk
	 */
	public List<E> query(String sql, Object... params) throws DaoAccessException;
	public List<E> query(String sql, List<Object> params) throws DaoAccessException;
	public List<E> query(String sql, Map<String, Object> params) throws DaoAccessException;
	
	/**
	 * 根据Condition对象查询记录
	 *
	 * @param condition        参数说明请参考get(condition, associationLink)
	 * @param associationLink
	 * @return
	 * 创建日期：2012-10-8
	 * 修改说明：
	 * @author wangk
	 */
	public List<E> query(Condition condition, Class<?>... associationLink) throws DaoAccessException;
	public List<E> query(Condition condition, String orders, Class<?>... associationLink) throws DaoAccessException;
	
	/**
	 * 根据SQL语句和分页参数查询记录
	 *
	 * @param sql           参数说明请参考query(sql, params)
	 * @param paging
	 * @param params
	 * @return DataStore<E> 分页数据
	 * 创建日期：2012-9-26
	 * 修改说明：
	 * @author wangk
	 */
	public DataStore<E> query(String sql, PagingParameter paging, Object... params) throws DaoAccessException;
	public DataStore<E> query(String sql, PagingParameter paging, List<Object> params) throws DaoAccessException;
	public DataStore<E> query(String sql, PagingParameter paging, Map<String, Object> params) throws DaoAccessException;
	
	/**
	 * 根据Condition对象和分页参数查询记录
	 *
	 * @param condition   参数说明请参考get(condition, associationLink)
	 * @param paging
	 * @return
	 * 创建日期：2012-10-8
	 * 修改说明：
	 * @author wangk
	 */
	public DataStore<E> query(Condition condition, PagingParameter paging, Class<?>... associationLink) throws DaoAccessException;
	public DataStore<E> query(Condition condition, String orders, PagingParameter paging, Class<?>... associationLink) throws DaoAccessException;
	
	/**
	 * 查询表里的所有记录
	 *
	 * @param associationLink  参数说明请参考get(condition, associationLink)
	 * @return
	 * 创建日期：2012-10-20
	 * 修改说明：
	 * @author wangk
	 */
	public List<E> queryAll(Class<?>... associationLink) throws DaoAccessException;
	public List<E> queryAll(String orders, Class<?>... associationLink) throws DaoAccessException;
	
	/**
	 * 分页查询表里的所有记录
	 *
	 * @param paging
	 * @return
	 * 创建日期：2012-10-20
	 * 修改说明：
	 * @author wangk
	 */
	public DataStore<E> queryAll(PagingParameter paging, Class<?>... associationLink) throws DaoAccessException;
	public DataStore<E> queryAll(String orders, PagingParameter paging, Class<?>... associationLink) throws DaoAccessException;
	
	/**
	 * 计算表里的记录数
	 *
	 * @return
	 * 创建日期：2012-10-20
	 * 修改说明：
	 * @author wangk
	 */
	public int count() throws DaoAccessException;
	public int count(Condition condition) throws DaoAccessException;
	
	/**
	 * 批量删除
	 *
	 * @param ids
	 * 创建日期：2012-9-25
	 * 修改说明：
	 * @author wangk
	 */
	public <K extends Number> void deletes(List<K> ids) throws DaoAccessException;
	
	/**
	 * 批量新增
	 *
	 * @param <K>      ID属性类型参数
	 * @param entitys  实体集合，各个对象的ID值必须为空，否则将抛出DuplicateRecordException
	 * @param ids      指定ID集合，entitys的前ids.length个对象通过指定的ID值新增，其他的对象ID值由数据库生成
	 * @return
	 * 创建日期：2012-9-25
	 * 修改说明：
	 * @author wangk
	 */
	public <K extends Number> void saves(List<E> entitys, K... ids) throws DaoAccessException;

	/**
	 * 批量更新
	 *
	 * @param entitys
	 * 创建日期：2012-9-25
	 * 修改说明：
	 * @author wangk
	 */
	public void updates(List<E> entitys) throws DaoAccessException;

	/**
	 * 批量新增或更新
	 *
	 * @param entitys
	 * 创建日期：2012-9-25
	 * 修改说明：
	 * @author wangk
	 */
	public void saveOrUpdates(List<E> entitys) throws DaoAccessException;

	/**
	 * 获得实体对象的被引用对象，被引用对象类型和E的类型相同
	 *	例：获得某一地区的上一级地区
	 *
	 * @param entity     实体对象
	 * @return E         被引用对象
	 * 创建日期：2012-10-8
	 * 修改说明：
	 * @author wangk
	 */
	public E getReferenced(E entity) throws DaoAccessException;
	public E getReferenced(Object referenceValue) throws DaoAccessException;
	
	/**
	 * 获得实体对象的被引用对象
	 *
	 * @param <R>              被引用对象参数
	 * @param entity           实体对象
	 * @param referencedClass  被引用对象类型
	 * @return R               被引用对象
	 * 创建日期：2012-10-8
	 * 修改说明：
	 * @author wangk
	 */
	public <R extends Entity> R getReferenced(E entity, Class<R> referencedClass) throws DaoAccessException;
	public <R extends Entity> R getReferenced(Object referenceValue, Class<R> referencedClass) throws DaoAccessException;
	 
	/**
	 * 查询被引用对象的所有引用对象，引用对象类型为E
	 * (R可以和E相同，例：查询某一地区的所有下一级地区)
	 *
	 * @param <R>         被引用对象参数
	 * @param referenced  被引用对象
	 * @return List<E>    引用对象集合
	 * 创建日期：2012-10-8
	 * 修改说明：
	 * @author wangk
	 */
	public <R extends Entity> List<E> queryReferences(R referenced) throws DaoAccessException;
	public <R extends Entity> List<E> queryReferences(Class<R> referencedClass, Object referencedValue) throws DaoAccessException;

	/**
	 * 查询被引用对象的所有引用对象，引用对象类型由referenceClass指定
	 * (应用于关联表的DAO中)
	 *
	 * @param <R>            被引用对象参数
	 * @param <S>            引用对象参数
	 * @param referenced     被引用对象
	 * @param referenceClass 引用对象类型
	 * @return List<S>       引用对象集合
	 * 创建日期：2012-10-8
	 * 修改说明：
	 * @author wangk
	 */
	public <R extends Entity, S extends Entity> List<S> queryReferences(R referenced, Class<S> referenceClass) throws DaoAccessException;
	public <R extends Entity, S extends Entity> List<S> queryReferences(Class<R> referencedClass, Object referencedValue, Class<S> referenceClass) throws DaoAccessException;
	
	/**
	 * 查询被引用对象的引用对象分页数据，引用对象类型为E
	 *
	 * @param <R>           被引用对象参数
	 * @param referenced    被引用对象
	 * @param paging        分页参数
	 * @return DataStore<E> 引用对象分页数据
	 * 创建日期：2012-10-8
	 * 修改说明：
	 * @author wangk
	 */
	public <R extends Entity> DataStore<E> queryReferences(R referenced, PagingParameter paging) throws DaoAccessException;
	public <R extends Entity> DataStore<E> queryReferences(Class<R> referencedClass, Object referencedValue, PagingParameter paging) throws DaoAccessException;
	
	/**
	 * 查询被引用对象的引用对象分页数据，引用对象类型由referenceClass指定
	 * (应用于关联表的DAO中)
	 * 
	 * @param <R>             被引用对象参数
	 * @param <S>             引用对象参数
	 * @param referenced      被引用对象
	 * @param referenceClass  引用对象类型
	 * @param paging          分页参数
	 * @return DataStore<S>   引用对象分页数据
	 * 创建日期：2012-10-8
	 * 修改说明：
	 * @author wangk
	 */
	public <R extends Entity, S extends Entity> DataStore<S> queryReferences(R referenced, Class<S> referenceClass, PagingParameter paging) throws DaoAccessException;
	public <R extends Entity, S extends Entity> DataStore<S> queryReferences(Class<R> referencedClass, Object referencedValue, Class<S> referenceClass, PagingParameter paging) throws DaoAccessException;

}
