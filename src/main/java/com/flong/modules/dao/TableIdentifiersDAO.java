/**
 * 
 */
package com.flong.modules.dao;

import java.io.Serializable;
import java.util.List;

import com.flong.commons.lang.exception.BizLogicException;
import com.flong.commons.persistence.bean.SimplePage;
import com.flong.commons.persistence.dao.EntityDao;
import com.flong.modules.pojo.TableIdentifiers;

/**
 * @author WangY
 *
 */
public interface TableIdentifiersDAO extends EntityDao<TableIdentifiers> {
	/**
	 * 获得业务单的唯一编码，编码格式为XX-ZZYYYYMMDDNNNN（其中XX为商家简称，ZZ为业务缩写，YYYYMMDD为当前日期，NNNN为流水号）
	 * @param s 业务单对应的实体类实例
	 * @return 字符串，业务单当前流水的唯一编码
	 * @throws BizLogicException 当实体类无法对应相应的表时，抛出异常
	 */
	String getBizIdentifier(Serializable s) throws BizLogicException;
	
	/**
	 * 获得从表主键的唯一编码，编码格式为"主表唯一编码-"+NN（NN为流水号）
	 * @param s 从表对应的实体类实例
	 * @param column 从表与主表关联的数据库表字段名称
	 * @param value 从表与主表关联的主表唯一编码值
	 * @return 字符串，从表当前流水的唯一编码
	 * @throws BizLogicException 当实体类无法对应相应的表时，抛出异常
	 */
	String getSubIdentifier(String tableName, String column, String value) throws BizLogicException;
	
	/**
	 * 获得商品表主键的唯一编码，编码格式为XX-GAAABBBNNNN（XX为商家简称，G为商品表标识，AAA为一类商品类目，BBB为二类商品类目，NNNN为流水号
	 * @param s 商品表对应的实体类实例
	 * @param classCode 商品类目编码，即AAABBB
	 * @return 字符串，商品当前流水的唯一编码
	 * @throws BizLogicException 当实体类无法对应相应的表时，抛出异常
	 */
	String getGoodsIdentifier(Serializable s, String classCode) throws BizLogicException;
	
	String getPackageIdentifier(Serializable s, String goodsCode) throws BizLogicException;
	
	/**
	 * 获得非业务单非商品的表主键唯一编码，编码格式为XX-ZNNNNNNNN(XX为商家简称，Z为类型标识，NNNNNNNN为流水号)
	 * @param s 非业务单非商品的表对应的实体类实例
	 * @return 字符串，主键的唯一编码
	 * @throws BizLogicException 当实体类无法对应相应的表时，抛出异常
	 */
	String getNormalIdentifier(Serializable s) throws BizLogicException;
	
	String[] getBizIdentifiers(Serializable s, int n) throws BizLogicException;
	
	String[] getSubIdentifiers(String tableName, String column, String value, int n)
		throws BizLogicException;
	
	String[] getGoodsIdentifiers(Serializable s, String[] classCode, int n)
		throws BizLogicException;
	
	String[] getNormalIdentifiers(Serializable s, int n) throws BizLogicException;

	String getBizIdentifier(Serializable s, boolean bool) throws BizLogicException;
	
	List<String> getBizIdentifierBatch(Class<?> clazz, int size) throws BizLogicException;

	List<TableIdentifiers> findList(SimplePage page, TableIdentifiers object);
}
