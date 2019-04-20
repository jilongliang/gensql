/**
 * 
 */
package com.flong.commons.web.chart.core;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.map.ListOrderedMap;

/**
 * 通用JSON配置元素
 * <p>
 * 因为JavaBean会限制属性的命名规则(如头字母会被转成小写)，因此这里使用Map
 * </p>
 * 
 * @author LiuLongbiao
 * @create 2012-11-29
 * 
 */
public class ChartElement implements Map<String, Object> {

	private ListOrderedMap store;

	public ChartElement() {
		store = new ListOrderedMap();
	}

	/**
	 * 设置属性值
	 * <p>
	 * 流式API，返回自身以供链式调用；子类应重载该方法，以返回自身类型的实例
	 * </p>
	 * 
	 * @author LiuLongbiao
	 * @create 2012-11-29
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @return
	 */
	public ChartElement attr(String key, Object value) {
		put(key, value);
		return this;
	}

	/**
	 * 获取属性值
	 * <p>
	 * 客户端代码需确保类型正确性
	 * </p>
	 * 
	 * @author LiuLongbiao
	 * @create 2012-12-5
	 * @param key
	 *            键
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T attr(String key) {
		return (T) get(key);
	}

	@Override
	public int size() {
		return this.store.size();
	}

	@Override
	public boolean isEmpty() {
		return this.store.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return this.store.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return this.store.containsValue(value);
	}

	@Override
	public Object get(Object key) {
		return this.store.get(key);
	}

	@Override
	public Object put(String key, Object value) {
		return this.store.put(key, value);
	}

	@Override
	public Object remove(Object key) {
		return this.store.remove(key);
	}

	@Override
	public void putAll(Map<? extends String, ? extends Object> m) {
		this.store.putAll(m);
	}

	@Override
	public void clear() {
		this.store.clear();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<String> keySet() {
		return this.store.keySet();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Object> values() {
		return this.store.values();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<java.util.Map.Entry<String, Object>> entrySet() {
		return this.store.entrySet();
	}

}
