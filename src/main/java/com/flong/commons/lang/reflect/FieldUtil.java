package com.flong.commons.lang.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.flong.commons.lang.DateUtils;

/***
 * Field帮助类
 * @author liangjilong
 *
 */
@SuppressWarnings("all")
public class FieldUtil {
	
	public Type type;
	public String dbName;
	public String name;

	/**
	 * 获取 类C 中的属性， 会递归查找父。
	 */
	public static Field getFiled(String name, Class c) {
		if (name == null || c == null)
			return null;
		Field field = null;

		String[] names = name.split("\\.");
		String name0 = stripIndex(names[0]).getPureName();
		try {
			field = c.getDeclaredField(name0);
			if (names.length == 1)
				return field;

			String lastName = name.substring(names[0].length() + 1, name
					.length());
			return getFiled(lastName, field.getType());

		} catch (Exception e) {
			Class superClass = c.getSuperclass();
			if ("java.lang.Object".equals(superClass.getName()))
				return null;
			else
				return getFiled(name, c.getSuperclass());
		}
	}

	/**
	 * 获取obj中name属性的值
	 */
	public static Object getFieldValue(String name, Object obj) {
		if (obj == null || StringUtils.isBlank(name))
			return null;

		String[] names = name.split("\\.");
		FieldInfo fieldInfo = stripIndex(names[0]);
		String name0 = fieldInfo.getPureName();

		Field field = getFiled(name0, obj.getClass());

		if (names.length == 1) {
			if (field == null)
				return null;
			try {
				boolean old = field.isAccessible();
				field.setAccessible(true);
				Object value = field.get(obj);
				field.setAccessible(old);
				if(fieldInfo.getIndex()>=0) {
					if (value instanceof List) {
						List list = (List) value;
						return list.get(fieldInfo.getIndex());
					} else if (value instanceof Object[]) {
						Object[] objs = (Object[]) value;
						return objs[fieldInfo.getIndex()];
					}
				}
				return value;
			} catch (Exception e) {
				return null;
			}
		} else {
			Object fieldObj = FieldUtil.getFieldValue(name0, obj);
			if (fieldObj == null)
				return null;

			String lastName = name.substring(names[0].length() + 1, name
					.length());
			return getFieldValue(lastName, fieldObj);
		}
	}

	/**
	 * 对带"[]"的格式，如orderItemList[0].orderAttrList[1].attrValue分离出对象的字段表达和数组（带"[]"）表达的字段信息
	 * 
	 * @param fieldName
	 * @return
	 */
	public static FieldInfo stripIndex(String fieldName) {
		FieldInfo fieldInfo = new FieldInfo();
		if (fieldName.contains("[") && fieldName.contains("]")) {
			fieldInfo.setPureName(fieldName
					.substring(0, fieldName.indexOf('[')));
			fieldInfo.setIndex(Integer.valueOf(fieldName.substring(fieldName
					.indexOf('[') + 1, fieldName.indexOf(']'))));
			return fieldInfo;
		}
		fieldInfo.setPureName(fieldName);
		fieldInfo.setIndex(-1);	
		return fieldInfo;
	}

	/** 根据字段类型转换。 */
	private static Object changeType(Field field, Object value) {
		Object className = field.getType().getName();
		if ("java.lang.String".equals(className))
			return value;
		if ("java.lang.BigDecimal".equals(className))
			return value;
		if ("java.lang.Object".equals(className))
			return value;
		if ("long".equals(className) || "java.lang.Long".equals(className))
			return Long.parseLong(value.toString());
		if ("int".equals(className) || "java.lang.Integer".equals(className))
			return Integer.parseInt(value.toString());
		if ("float".equals(className) || "java.lang.Float".equals(className))
			return Float.parseFloat(value.toString());
		if ("double".equals(className) || "java.lang.Double".equals(className))
			return Double.parseDouble(value.toString());
		if ("java.util.Date".equals(className))
			return DateUtils.formatDate(value.toString(), DateUtils.YYYYMMDDHHMMSS);//
		try {
			Class cl = field.getType();
			return Enum.valueOf(cl, value.toString());
		} catch (Exception e) {
			return value;
		}

	}

	/**
	 * 设置obj中name属性的值为value,含List类型结合类
	 */
	public static void setFieldValue(String name, Object obj, Object value) {
		if (obj == null || StringUtils.isBlank(name))
			return;

		String[] names = name.split("\\.");
		String name0 = stripIndex(names[0]).getPureName();
		int objInArrayIndex = stripIndex(names[0]).getIndex();

		Field field = getFiled(name0, obj.getClass());
		if (field == null)
			return;

		if (names.length == 1) {
			try {
				boolean old = field.isAccessible();
				field.setAccessible(true);
				field.set(obj, changeType(field, value));
				field.setAccessible(old);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				Object fieldObj = FieldUtil.getFieldValue(name0, obj);
				Class clazz = field.getType();

				String lastName = name.substring(names[0].length() + 1, name
						.length());
				if (fieldObj == null) {
					if (clazz.getName().equals("java.util.List")
							|| clazz.getName().equals("java.util.ArrayList")) {

						String setObjMethodName = "set"
								+ name0.substring(0, 1).toUpperCase()
								+ name0.substring(1, name0.length());
						Method setObjMethod = obj.getClass().getMethod(
								setObjMethodName, clazz);
						Type[] genericType = setObjMethod
								.getGenericParameterTypes();
						String typeString = genericType[0].toString();

						String collectionClassName = typeString.substring(
								typeString.indexOf('<') + 1, typeString
										.indexOf('>'));
						Class collectionClazz = Class
								.forName(collectionClassName);
						Object collectionObj = collectionClazz.newInstance();
						ArrayList fieldObjOfCollection = new ArrayList();
						fieldObjOfCollection.add(collectionObj);
						// 设置对象(obj)的集合类字段(arrayListObj)
						FieldUtil.setFieldValue(name0, obj,
								fieldObjOfCollection);
						// 对obj的集合类字段设置对象(collectionObj)的字段值
						setFieldValue(lastName, collectionObj, value);
					} else {
						fieldObj = clazz.newInstance();
						// 设置对象(obj)的普通类字段(arrayListObj)
						FieldUtil.setFieldValue(name0, obj, fieldObj);
						// 对obj的普通类字段(fieldObj)设置字段值
						setFieldValue(lastName, fieldObj, value);
					}
				} else {
					if (field.getType().getName().equals("java.util.List")
							|| clazz.getName().equals("java.util.ArrayList")) {
						// 添加obj的集合类字段的对象(collectionObj)
						Object collectionObj = null;
						if (objInArrayIndex > ((List) fieldObj).size() - 1) {
							String setObjMethodName = "set"
									+ name0.substring(0, 1).toUpperCase()
									+ name0.substring(1, name0.length());
							Method setObjMethod = obj.getClass().getMethod(
									setObjMethodName, clazz);
							Type[] genericType = setObjMethod
									.getGenericParameterTypes();
							String typeString = genericType[0].toString();
							String collectionClassName = typeString.substring(
									typeString.indexOf('<') + 1, typeString
											.indexOf('>'));
							Class collectionClazz = Class
									.forName(collectionClassName);
							collectionObj = collectionClazz.newInstance();
							((List) fieldObj).add(((List) fieldObj).size(),
									collectionObj);
						} else {
							collectionObj = ((List) fieldObj)
									.get(objInArrayIndex);
							((List) fieldObj).set(objInArrayIndex,
									collectionObj);
						}

						// 对obj的集合类字段设置对象(collectionObj)的字段值
						setFieldValue(lastName, collectionObj, value);
					} else {
						// 对obj的普通类字段(fieldObj)设置字段值
						setFieldValue(lastName, fieldObj, value);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	

  public Object conver(Object o) {
		if (o == null)
			return null;
		Object dest = null;

		if ((Object.class.equals(this.type))){
			if ((o instanceof Object))
				dest = o;
			else
				dest = o.toString();
		} else if (this.type == String.class) {
			if ((o instanceof String))
				dest = o;
			else
				dest = o.toString();
		} else if ((Integer.class.equals(this.type)) || (Integer.TYPE.equals(this.type))){
			dest = parseInt(o);
		}else if ((BigDecimal.class.equals(this.type))){
			if ((o instanceof BigDecimal))
				dest = o;
			else
				dest = o.toString();
		}else if ((Double.class.equals(this.type)) || (Double.TYPE.equals(this.type))){
			dest = parseDouble(o);
		}else if ((Short.class.equals(this.type)) || (Short.TYPE.equals(this.type))){
			dest = parseShort(o);
		}else if ((Float.class.equals(this.type)) || (Float.TYPE.equals(this.type))){
			dest = parseFloat(o);
		}else if (Date.class.equals(this.type)){
			dest = parseDate(o);
		}else if ((Boolean.TYPE.equals(this.type)) || (Boolean.class.equals(this.type))) {
			dest = parseBoolean(o);
		}
		return dest;
	}

	private Object parseInt(Object o) {
		if (o == null)
			return null;
		if ((o instanceof Integer))
			return o;
		try {
			double dv = Double.parseDouble(o.toString().trim());
			return Integer.valueOf((int) dv);
		} catch (Exception e) {
		}
		return null;
	}

	private Object parseDouble(Object o) {
		if (o == null)
			return null;
		if ((o instanceof Double))
			return o;
		try {
			return Double.valueOf(Double.parseDouble(o.toString().trim()));
		} catch (Exception e) {
		}
		return null;
	}

	private Object parseShort(Object o) {
		if (o == null)
			return null;
		if ((o instanceof Short))
			return o;
		try {
			return Short.valueOf((short) (int) Double.parseDouble(o.toString()
					.trim()));
		} catch (Exception e) {
		}
		return null;
	}

	private Object parseLong(Object o) {
		if (o == null)
			return null;
		if ((o instanceof Long))
			return o;
		try {
			return Long.valueOf((long) Double.parseDouble(o.toString().trim()));
		} catch (Exception e) {
		}
		return null;
	}

	private Object parseBoolean(Object o) {
		if (o == null)
			return null;
		if ((o instanceof Boolean))
			return o;
		try {
			return Boolean
					.valueOf(Double.parseDouble(o.toString().trim()) > 0.0D);
		} catch (Exception e) {
		}
		return null;
	}

	private Object parseFloat(Object o) {
		if (o == null)
			return null;
		if ((o instanceof Float))
			return o;
		try {
			return Float.valueOf((float) Double
					.parseDouble(o.toString().trim()));
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * 时间转换
	 * @param o
	 * @return
	 */
	private Object parseDate(Object o) {
		if (o == null)
			return null;
		if ((o instanceof Date)) {
			return o;
		}
		return DateUtils.converToDate(o.toString());
	}
}

class FieldInfo {
	
	/** 字段名称 */
	private String pureName;
	/** 字段描述的所指的索引位置 */
	private int index;

	public String getPureName() {
		return pureName;
	}
	public void setPureName(String pureName) {
		this.pureName = pureName;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
}
