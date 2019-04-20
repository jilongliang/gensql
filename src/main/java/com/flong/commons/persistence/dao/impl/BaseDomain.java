package com.flong.commons.persistence.dao.impl;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.flong.commons.utils.ObjectUtil;

/**
 * 数据模型基础类
 *
 * 创建日期：2012-11-13
 * @author wangk
 */
@SuppressWarnings("all")
public abstract class BaseDomain implements Cloneable, Serializable {
	private static final long serialVersionUID = -3707046914855595598L;

	/**
	 * @see java.lang.Object#toString()
	 * 创建日期：2012-11-13
	 * 修改说明：
	 * @author wangk
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	/**
	 * 浅层复制(如果属性为引用类型则只复制属性的引用值)当前对象
	 *
	 * @param <T>
	 * @return
	 * 创建日期：2013-1-25
	 * 修改说明：
	 * @author wangk
	 */
	public <T> T simpleClone() {
		try {
			T ret = (T)clone();
			return ret;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 获取指定属性的值集合
	 * @description
	 * @param fieldNames
	 * @return
	 * 创建日期：2013-1-19
	 * 修改说明：
	 * @author wangk
	 */
	public List<Object> getFieldValues(List<String> fieldNames) {
		List<Object> list = new ArrayList<Object>();
		if(CollectionUtils.isNotEmpty(fieldNames)){
			for (String fieldName : fieldNames) {
				try {
					list.add(new PropertyDescriptor(fieldName, getClass()).getReadMethod().invoke(this));
				} catch (Exception e) {
					throw new RuntimeException(e);
				} 
			}
		}
		return list;
	}

	/**
	 * 将当前对象转换成属性和值的映射
	 *
	 * @return
	 * 创建日期：2013-1-29
	 * 修改说明：
	 * @author wangk
	 */
	public Map<String, Object> toFieldMapping() {
		Map<String, Object> entrys = new HashMap<String, Object>();
		Map<String, Method> readMethodMapping = ObjectUtil.getReadMethodMapping(getClass());
		for (String field : readMethodMapping.keySet()) {
			try {
				entrys.put(field, readMethodMapping.get(field).invoke(this));
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return entrys;
	}

}
