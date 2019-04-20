package com.flong.commons.lang.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;

import org.apache.log4j.Logger;


/**
 *@Author:liangjilong
 *@Date:2015年10月1日-上午9:03:51
 *@Email:jilongliang@sina.aom
 *@Version:1.0
 *@Desct:对象转换Bean
 */
@SuppressWarnings("all")
public class ObjectBeans {
	final Logger logger = Logger.getLogger(ObjectBeans.class);
	
	private static Map conver = new HashMap();

	/***
	 * 
	 * @param values
	 * @param clazz
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static <T> Object getBean(Map values, Class<T> clazz)
			throws InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		Object dest = clazz.newInstance();

		Method[] method = clazz.getMethods();
		Map convers = (Map) conver.get(clazz);

		if (convers == null) {
			convers = new HashMap();
			conver.put(clazz, convers);
			for (int i = 0; i < method.length; i++) {
				Method meth = method[i];
				if (meth.getName().startsWith("get")) {
					boolean isColumn = meth.isAnnotationPresent(Column.class);
					Type type = meth.getGenericReturnType();
					FieldUtil f = new FieldUtil();
					f.type = type;
					f.name = meth.getName().substring(3);
					f.dbName = f.name.toUpperCase();
					if (isColumn) {
						Column column = (Column) meth.getAnnotation(Column.class);
						String name = column.name().toUpperCase();
						f.dbName = name;
					}

					convers.put(("set" + meth.getName().substring(3)).toUpperCase(),f);
				}

			}
		}

		for (int i = 0; i < method.length; i++) {
			Method meth = method[i];

			if (!meth.getName().startsWith("set")) {
				continue;
			}
			
			String name = meth.getName().toUpperCase();
			//System.out.println(name);//获取setxx值.
			FieldUtil f = (FieldUtil) convers.get(name);
			if (f != null) {
				if (f.dbName != null)
					name = f.dbName;

				Object param = f.conver(values.get(name));
				if (param != null)
					meth.invoke(dest, new Object[] { param });
			}
		}
		return dest;
	}
}