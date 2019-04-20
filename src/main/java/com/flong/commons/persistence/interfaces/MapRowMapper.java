package com.flong.commons.persistence.interfaces;

import java.util.Map;

import com.flong.commons.lang.exception.DaoAccessException;

/**
 * Map行数据映射回调接口
 *
 * 创建日期：2012-9-26
 * @author wangk
 */
public interface MapRowMapper<R> {
	
	/**
	 * Map行数据映射回调方法
	 *
	 * @param map    行数据的map对象，key为列名，value为列对应的数据
	 * @param index  记录序号
	 * @return R R指定类型的行数据
	 * 创建日期：2012-9-26
	 * 修改说明：
	 * @author wangk
	 */
	public R mapRow(Map<String, Object> map, int index) throws DaoAccessException;

}
