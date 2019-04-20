package com.flong.commons.web.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.flong.commons.persistence.dao.impl.BaseDomain;
import com.flong.commons.utils.JsonUtil;
import com.flong.commons.utils.ObjectUtil;
import com.flong.commons.utils.converter.StringToDateConverter;

/**
 * 请求数据模型，用于封装JSON格式的字符串数据
 *
 * 创建日期：2012-9-29
 * @author wangk
 */
public class RequestData extends BaseDomain {
	private static final long serialVersionUID = -8674229932786448445L;

	/** JSON字符串格式的请求数据 */
	private String requestData;
	/** 请求数据的Map对象，对象创建后requestMap不能为null */
	private Map<String, Object> requestMap;

	/**
	 * 构造方法
	 * 创建日期：2012-9-29
	 * 修改说明：
	 * @author wangk
	 */
	public RequestData() {
		this((String)null);
	}

	/**
	 * 构造方法
	 * @param requestData 请求数据，只能是JSON数组或JSON对象的字符串格式
	 * 创建日期：2012-9-29
	 * 修改说明：
	 * @author wangk
	 */
	public RequestData(String requestData) {
		setRequestData(requestData);
		setRequestMap(parseRequestMap(requestData));
	}

	/**
	 * 构造方法， 指定requestMap，如果requestMap为null则创建空的Map对象
	 * 
	 * @param requestMap  请求数据Map对象
	 * 					  requestMap的value值类型只能是基本数据类型、String、List、Map
	 * 					    且List的元素值、Map的value值类型取值范围同上。
	 * 创建日期：2012-12-27
	 * 修改说明：
	 * @author wangk
	 */
	public RequestData(Map<String, Object> requestMap) {
		if(requestMap == null) {
			requestMap = new HashMap<String, Object>();
		}
		setRequestMap(requestMap);
	}

	/**
	 * @description 将数据转换成Map对象
	 * @author wangk
	 * @return Map<String,Object>
	 * @create 2012-7-4 下午05:20:43
	 */
	public Map<String, Object> toMap() {
		return requestMap;
	}
	
	/**
	 * 将数据转换成List对象
	 *
	 * @return
	 * 创建日期：2012-10-15
	 * 修改说明：
	 * @author wangk
	 */
	public List<Object> toList() {
		if(isList()) {
			return JsonUtil.parseList(requestData);
		}
		List<Object> list = new ArrayList<Object>();
		list.add(toMap());
		return list;
	}

	/**
	 * 将数据转换成clazz指定元素类型的List对象
	 *
	 * @param <T>
	 * @param clazz
	 * @return
	 * 创建日期：2012-10-15
	 * 修改说明：
	 * @author wangk
	 */
	public <T> List<T> toList(Class<T> clazz) {
		List<Object> value = toList();
		List<T> list = new ArrayList<T>();
		for (Object item : value) {
			T t = (T) ObjectUtil.convert(item, clazz);
			list.add(t);
		}
		return list;
	}
	
	/**
	 * @description 将数据转换成Clazz指定的类型对象
	 * @author wangk
	 * @param clazz
	 * @return Object
	 * @create 2012-7-4 下午05:21:06
	 */
	public <T> T toBean(Class<T> clazz) {
		return ObjectUtil.parse(requestMap, clazz);
	}

	/**
	 * 将数据转换成Clazz指定的枚举对象
	 *
	 * @param <T>
	 * @param clazz
	 * @return
	 * 创建日期：2012-10-17
	 * 修改说明：
	 * @author wangk
	 */
	public <T extends Enum<T>> T toEnum(Class<T> clazz) {
		Object obj = requestMap.get("name");
		if(obj == null) {
			return null;
		}
		return Enum.valueOf(clazz, obj.toString().replaceAll("^\\w*\\.", ""));
	}

	/**
	 * 获得请求数据的所有参数名，如果没有则返回空集合
	 *
	 * @return
	 * 创建日期：2012-12-27
	 * 修改说明：
	 * @author wangk
	 */
	public List<String> getNames() {
		List<String> names = new ArrayList<String>();
		for (String name : requestMap.keySet()) {
			names.add(name);
		}
		return names;
	}
	
	/**
	 * 获得Object类型的参数值
	 *
	 * @param name  参数名称
	 * @return
	 * 创建日期：2012-10-9
	 * 修改说明：
	 * @author wangk
	 */
	public Object get(String name) {
		return requestMap.get(name);
	}
	
	/**
	 * 获得由T指定类型的参数值
	 *
	 * @param <T>    返回类型参数 
	 * @param name   参数名称
	 * @param clazz  返回类型
	 * @return T     T对象
	 * 创建日期：2012-10-9
	 * 修改说明：
	 * @author wangk
	 */
	public <T> T get(String name, Class<T> clazz) {
		return ObjectUtil.parse(getMap(name), clazz);
	}
	
	public <T extends Enum<T>> T getEnum(String name, Class<T> clazz) {
		Object value = getMap(name).get("name");
		if(value == null) {
			return null;
		}
		return Enum.valueOf(clazz, value.toString().replaceAll("^\\w*\\.", ""));
	}
	
	public byte getB(String name) {
		Byte value = getByte(name);
		return value == null ? 0 : value;
	}
	
	public Byte getByte(String name) {
		String value = getString(name);
		if(value == null) {
			return null;
		}
		return Byte.valueOf(value);
	}
	
	public short getS(String name) {
		Short value = getShort(name);
		return value == null ? 0 : value;
	}
	
	public Short getShort(String name) {
		String value = getString(name);
		if(value == null) {
			return null;
		}
		return Short.valueOf(value);
	}
	
	public int getInt(String name) {
		Integer value = getInteger(name);
		return value == null ? 0 : value;
	}
	
	public Integer getInteger(String name) {
		String value = getString(name);
		if(value == null) {
			return null;
		}
		return Integer.valueOf(value);
	}
	
	public long getL(String name) {
		Long value = getLong(name);
		return value == null ? 0 : value;
	}
	
	public Long getLong(String name) {
		String value = getString(name);
		if(value == null || "".equals(value)) {
			return null;
		}
		return Long.valueOf(value);
	}
	
	public float getF(String name) {
		Float value = getFloat(name);
		return value == null ? 0 : value;
	}
	
	public Float getFloat(String name) {
		String value = getString(name);
		if(value == null) {
			return null;
		}
		return Float.valueOf(value);
	}
	
	public double getD(String name) {
		Double value = getDouble(name);
		return value == null ? 0 : value;
	}
	
	public Double getDouble(String name) {
		String value = getString(name);
		if(value == null) {
			return null;
		}
		return Double.valueOf(value);
	}
	
	public boolean getBool(String name) {
		Boolean value = getBoolean(name);
		return value == null ? false : value;
	}
	
	public Boolean getBoolean(String name) {
		String value = getString(name);
		if(value == null) {
			return null;
		}
		return Boolean.valueOf(value);
	}
	
	public char getChar(String name) {
		Character value = getCharacter(name);
		return value == null ? 0 : value;
	}
	
	public Character getCharacter(String name) {
		String value = getString(name);
		if(value ==null || value.length()==0) {
			return null;
		}
		return value.charAt(0);
	}
	
	public String getString(String name) {
		Object value = get(name);
		if(value == null) {
			return null;
		}
		return value.toString();
	}

	/**
	 * 获得日期类型的参数值，可以解析$.toJSON(date)的字符串和date.getTime()得到的毫秒数
	 *
	 * @param name  参数名称
	 * @return Date 日期对象
	 * 创建日期：2012-10-9
	 * 修改说明：
	 * @author wangk
	 */
	public Date getDate(String name) {
		String value = getString(name);
		if(value == null) {
			return null;
		}
		return new StringToDateConverter().convert(value);
	}
	
	/**
	 * 获得BigDecimal类型的参数值，非数值参数返回null
	 *
	 * @param name
	 * @return
	 * 创建日期：2012-12-27
	 * 修改说明：
	 * @author wangk
	 */
	public BigDecimal getBigDecimal(String name) {
		if(!isNumber(name)) {
			return null;
		}
		return new BigDecimal(getString(name));
	}

	/**
	 * 获得Map类型的参数值
	 *
	 * @param name  参数名称
	 * @return Map<String, Object>
	 * 创建日期：2012-10-9
	 * 修改说明：
	 * @author wangk
	 */
	public Map<String, Object> getMap(String name) {
		Object value = get(name);
		if(!(value instanceof Map)) {
			return new HashMap<String, Object>();
		}
		@SuppressWarnings("unchecked")
		Map<String, Object> map = (Map<String, Object>)value;
		return map;
	}
	
	/**
	 * 获得List类型的参数值
	 *
	 * @param name  参数名称
	 * @return List<Object>
	 * 创建日期：2012-10-9
	 * 修改说明：
	 * @author wangk
	 */
	@SuppressWarnings("unchecked")
	public List<Object> getList(String name) {
		Object value = get(name);
		if(value instanceof List) {
			return (List<Object>)value;
		}
		List<Object> list = new ArrayList<Object>();
		list.add(value);
		return list;
	}

	/**
	 * 获得clazz指定元素类型的List类型的参数值
	 *
	 * @param <T>
	 * @param name
	 * @param clazz
	 * @return
	 * 创建日期：2012-10-15
	 * 修改说明：
	 * @author wangk
	 */
	public <T> List<T> getList(String name, Class<T> clazz) {
		List<Object> value = getList(name);
		if(value == null) {
			return null;
		}
		List<T> list = new ArrayList<T>();
		for (Object item : value) {
			@SuppressWarnings("unchecked")
			T t = (T) ObjectUtil.convert(item, clazz);
			list.add(t);
		}
		return list;
	}

	/**
	 * 获得name参数指定的请求数据部分
	 *
	 * @param name         参数名称
	 * @return RequestData 请求数据对象
	 * 创建日期：2012-12-27
	 * 修改说明：
	 * @author wangk
	 */
	public RequestData getComponent(String name) {
		if(isList(name)) {
			return new RequestData(JsonUtil.obj2json(get(name)));
		}
		return new RequestData(getMap(name));
	}
	
	/**
	 * 判断请求参数是否为数值类型
	 *
	 * @param name  参数名称
	 * @return boolean
	 * 创建日期：2012-10-9
	 * 修改说明：
	 * @author wangk
	 */
	public boolean isNumber(String name) {
		String value = getString(name);
		if(value == null) {
			return false;
		}
		if(value.matches("^[\\+\\-]?\\d+(\\.\\d*)?([Ee][\\+\\-]?\\d+)?$")) {
			return true;
		}
		return false;
	}
	
	/**
	 * 判断请求参数是否为数组
	 *
	 * @param name  参数名称
	 * @return
	 * 创建日期：2012-12-29
	 * 修改说明：
	 * @author wangk
	 */
	public boolean isList(String name) {
		return JsonUtil.isJsonArrayString(getString(name));
	}
	
	/**
	 * 判断请求数据是否为数组
	 *
	 * @return
	 * 创建日期：2012-12-27
	 * 修改说明：
	 * @author wangk
	 */
	public boolean isList() {
		return JsonUtil.isJsonArrayString(requestData);
	}

	/**
	 * 判断是否有请求数据
	 * 1、当请求数据为数组时返回true；
	 * 2、当请求数据为Map时，Map不为null返回true，否则返回false
	 * 3、其他情况返回false
	 *
	 * @return boolean
	 * 创建日期：2012-10-9
	 * 修改说明：
	 * @author wangk
	 */
	public boolean hasData() {
		return !requestMap.isEmpty() || isList();
	}

	/**
	 * @see com.flong.commons.BaseDomain#toString()
	 * 
	 * 创建日期：2012-12-27
	 * 修改说明：
	 * @author wangk
	 */
	@Override
	public String toString() {
		return isList()?requestData:JsonUtil.obj2json(requestMap);
	}

	private void setRequestData(String requestData) {
		this.requestData = requestData;
	}

	private void setRequestMap(Map<String, Object> requestMap) {
		this.requestMap = requestMap;
	}

	/**
	 * 将JSON字符串格式的数据解析成requestMap
	 *
	 * @param requestData
	 * @return
	 * 创建日期：2012-12-27
	 * 修改说明：
	 * @author wangk
	 */
	private Map<String, Object> parseRequestMap(String requestData) {
		if(!JsonUtil.isJsonObjectString(requestData)) {
			return new HashMap<String, Object>();
		}
		return JsonUtil.parseMap(requestData);
	}

}
