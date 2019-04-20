package com.flong.commons.lang.config;


import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
/***
 *@Author:liangjilong
 *@Date:2015年12月5日下午12:25:12
 *@Email:jilongliang@sina.com
 *@Version:1.0
 *@CopyRight(c)jilongliang@sina.com
 *@Description：读取文properties
 */
public class PropertiesHelper {

	private static final Map<String, String> properties = new HashMap<String, String>();
	static {
		try {
			Properties pps = new Properties();
			pps.load(PropertiesHelper.class.getClassLoader().getResourceAsStream("prop/DBSource.properties"));
			//处理重复的值.
			for (Entry<Object, Object> entry : pps.entrySet()) {
				properties.put(entry.getKey().toString().trim(), entry.getValue().toString().trim());
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 *通过key值去获取值.
	 */
	public static String getValueByKey(String name) {
		return properties.get(name);
	}

}
