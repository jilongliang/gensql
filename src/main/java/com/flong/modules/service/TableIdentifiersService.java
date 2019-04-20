/**
 * 
 */
package com.flong.modules.service;

import java.io.Serializable;
import java.util.List;

import com.flong.commons.lang.exception.BizLogicException;
import com.flong.commons.persistence.bean.SimplePage;
import com.flong.commons.persistence.dao.EntityDao;
import com.flong.modules.pojo.TableIdentifiers;

/**
 * 表主键编码生成公共接口<br>
 * @author WangY
 * <p>
 * 提供获得RBSS所有表主键编码的统一接口，注入方式调用方法：
 * <pre> @Autowired TableIdentifiersService tableIdentifiersService;</pre>
 * 
 * 获取业务单据编码举例，以获取订单编码为例：
 * <pre> RssaleOrderbase order = new RssaleOrderbase();
 * String nid = tableIdentifiersService.nextBizId(order);
 * order.setConsumeCode(nid);</pre>
 * 
 * 获取商品编码举例：
 * <pre> String classCode = level1Code + level2Code;
 * rpGoodsbase good = new rpGoodsbase();
 * String nid = tableIdentifiersService.nextGoodsId(good, classCode);
 * good.setGoodsCode(nid);</pre>
 * 
 * 获取从表主键编码举例，以入库单商品明细为例：
 * <pre> String inCode = inOrder.getStorageInCode();
 * RwstorageinOrdergoods inGood = new RwstorageinOrdergoods();
 * String nid = tableIdentifiersService.nextSubId(inGood, inCode);
 * inGood.setStrageingoodsCode(nid);</pre>
 * 
 * 获取非业务单非商品表主键编码，以通用包件为例：
 * <pre> RpPackagebase package = new RpPackagebase();
 * String nid =  tableIdentifiersService.nextNormalId(package);
 * package.setPackageCode(nid);</pre>
 * 
 * 如需批量获得主键编码，请使用相应的批量方法 nextBizIds, nextGoodsIds, nextSubIds, nextNormalIds
 * 
 * </p>
 */
public interface TableIdentifiersService extends  EntityDao<TableIdentifiers>  {
	/**
	 * 获得业务单的唯一编码，编码格式为XX-ZZYYYYMMDDNNNN（其中XX为商家简称，ZZ为业务缩写，YYYYMMDD为当前日期，NNNN为流水号）
	 * @param s 业务单对应的实体类实例
	 * @return 字符串，业务单当前流水的唯一编码
	 * @throws BizLogicException 当实体类无法对应相应的表时，抛出异常
	 */
	String nextBizId(Serializable s) throws BizLogicException;
	/**
	 * 无商家简称
	 * @param s
	 * @param bool  false(无商家简称)
	 * @return
	 * @throws BizLogicException
	 */
	String nextBizId(Serializable s,boolean bool) throws BizLogicException;
	
	/**
	 * 获得商品表主键的唯一编码，编码格式为XX-GAAABBBNNNN（XX为商家简称，G为商品表标识，AAA为一类商品类目，BBB为二类商品类目，NNNN为流水号
	 * @param s 商品表对应的实体类实例
	 * @param classCode 商品类目编码，即AAABBB
	 * @return 字符串，商品当前流水的唯一编码
	 * @throws BizLogicException 当实体类无法对应相应的表时，抛出异常
	 */
	String nextGoodsId(Serializable s, String classCode) throws BizLogicException;
	
	String nextPackageId(Serializable s, String goodsCode) throws BizLogicException;
	
	/**
	 * 获得非业务单非商品的表主键唯一编码，编码格式为XX-ZNNNNNNNN(XX为商家简称，Z为类型标识，NNNNNNNN为流水号)
	 * @param s 非业务单非商品的表对应的实体类实例
	 * @return 字符串，主键的唯一编码
	 * @throws BizLogicException 当实体类无法对应相应的表时，抛出异常
	 */
	String nextNormalId(Serializable s) throws BizLogicException;
	
	/**
	 * 获得从表主键的唯一编码，编码格式为"主表唯一编码-"+NN（NN为流水号）
	 * @param tableName 从表表名
	 * @param column 从表与主表关联的数据库表字段名称
	 * @param value 从表与主表关联的主表唯一编码值
	 * @return 字符串，从表当前流水的唯一编码
	 * @throws BizLogicException 当实体类无法对应相应的表时，抛出异常
	 */
	String nextSubId(String tableName, String column, String value) throws BizLogicException;
	
	/**
	 * 批量获得业务单的唯一编码，共可获得参数n个业务单编码。编码格式为XX-ZZYYYYMMDDNNNN（其中XX为商家简称，ZZ为业务缩写，YYYYMMDD为当前日期，NNNN为流水号）
	 * @param s 业务单对应的实体类实例
	 * @param n 业务单数量
	 * @return 字符串数组，对应n个业务单流水的唯一编码
	 * @throws BizLogicException 当实体类无法对应相应的表时，抛出异常；当n<=0时，抛出异常
	 */
	String[] nextBizIds(Serializable s, int n) throws BizLogicException;
	
	/**
	 * 批量获得商品表主键的唯一编码，共可获得参数n个商品编码。编码格式为XX-GAAABBBNNNN（XX为商家简称，G为商品表标识，AAA为一类商品类目，BBB为二类商品类目，NNNN为流水号
	 * @param s 商品表对应的实体类实例
	 * @param classCode 商品类目编码数组，即AAABBB类目编码组成的数组
	 * @param n 商品数量
	 * @return 字符串数组，对应n个商品当前流水的唯一编码
	 * @throws BizLogicException 当实体类无法对应相应的表时，抛出异常；当n<=0或n!=classCode.length时，抛出异常
	 */
	String[] nextGoodsIds(Serializable s, String[] classCode, int n) throws BizLogicException;
	
	/**
	 * 批量获得从表主键的唯一编码，共可获得相应的n个从表主键编码。编码格式为"主表唯一编码-"+NN（NN为流水号）
	 * @param tableName 从表表名
	 * @param column 从表与主表关联的数据库表字段名称
	 * @param value 从表与主表关联的主表唯一编码值
	 * @param n 从表主键编码数量
	 * @return 字符串数组，从表当前流水的唯一编码
	 * @throws BizLogicException 当实体类无法对应相应的表时，抛出异常；当n<=0时，抛出异常
	 */
	String[] nextSubIds(String tableName, String column, String value, int n) throws BizLogicException;
	
	/**
	 * 批量获得非业务单非商品的表主键唯一编码，共可获得相应的n个主键编码。编码格式为XX-ZNNNNNNNN(XX为商家简称，Z为类型标识，NNNNNNNN为流水号)
	 * @param s 非业务单非商品的表对应的实体类实例
	 * @param n 需获得的主键编码数量
	 * @return 字符串，主键的唯一编码
	 * @throws BizLogicException 当实体类无法对应相应的表时，抛出异常；当n<=0时，抛出异常
	 */
	String[] nextNormalIds(Serializable s, int n) throws BizLogicException;
	
	/**
	 * 查询所有信息
	 * @param page
	 * @param object
	 * @return
	 */
	public List<TableIdentifiers> findList(SimplePage page,TableIdentifiers object);
	
	
	//public void saveOrUpdate(TableIdentifiers obj);


	//public TableIdentifiers findById(String id);


	//public boolean existEmail(TableIdentifiers email);
}
