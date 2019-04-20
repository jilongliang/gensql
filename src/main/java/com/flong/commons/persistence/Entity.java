package com.flong.commons.persistence;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.flong.commons.persistence.annotation.Column;
import com.flong.commons.persistence.annotation.Id;
import com.flong.commons.persistence.dao.impl.BaseDomain;
import com.flong.commons.persistence.exception.AnnotationNotFoundException;
import com.flong.commons.persistence.exception.ErrorCode;
import com.flong.commons.persistence.exception.FieldColumnMappingException;
import com.flong.commons.utils.ObjectUtil;

/**
 * 实体抽象类
 *
 * 创建日期：2012-9-24
 * @author wangk
 */
@SuppressWarnings("all")
public abstract class Entity extends BaseDomain implements Comparable<Entity> {
	/** 序列化版本标识 */
	private static final long serialVersionUID = 4224390531787078169L;
	
	/** 列访问操作符 */
	public static final String COLUMN_ACCESS_OPERATOR = ".";
	/** 表后缀连接符 */
	public static final String TABLE_SUFFIX_CONNECTOR = "_";
	/** 降序排序常量 */
	public static final String ORDER_DESCENDING = "DESC";
	/** 关联链(全部的关联类型)常量 */
	public static final Class<?>[] ASSOCIATION_LINK_ALL = null;
	
	/** 注解表名默认值 */
	public static final String TABLE_NAME_DEFAULT = "";
	/** 注解列名默认值 */
	public static final String COLUMN_NAME_DEFAULT = "";
	/** 注解列类型默认值 */
	public static final int COLUMN_TYPE_DEFAULT = 0;
	/** 注解列长度默认值 */
	public static final int COLUMN_SIZE_DEFAULT = 0;
	/** 注解外键引用实体类类型默认值 */
	public static final Class<? extends Entity> REFERENCE_CLASS_DEFAULT = Entity.class;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		String idstr = identityString();
		Long id = idstr == null ? 0 : Long.valueOf(idstr);
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		Entity other = (Entity) obj;
		String idstr = identityString();
		String oidstr = other.identityString();
		if(idstr == oidstr) {
			return true;
		}
		if(idstr == null) {
			return false;
		}
		return idstr.equals(oidstr);
	}

	@Override
	public int compareTo(Entity other) {
		String idstr = identityString();
		String oidstr = other.identityString();
		if(idstr == null && oidstr == null) {
			return 0;
		}
		if(idstr == null) {
			return 1;
		}
		if(oidstr == null) {
			return -1;
		}
		return new BigDecimal(idstr).compareTo(new BigDecimal(oidstr));
	}

	/**
	 * 获得实体对象的ID值的字符串形式
	 *
	 * @return
	 * 创建日期：2012-9-24
	 * 修改说明：
	 * @author wangk
	 */
	public String identityString() {
		Class<? extends Entity> clazz = getClass();
		for (Field field : ObjectUtil.getAllField(clazz)) {
			Id id = field.getAnnotation(Id.class);
			if(id != null) {
				try {
					Object value = new PropertyDescriptor(
							field.getName(), clazz).getReadMethod().invoke(this);
					return value == null ? null : value.toString();
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		}
		throw new AnnotationNotFoundException(ErrorCode.ANNOTATION_NOT_FOUND_AS_ID, 
				"Annotation " + Id.class.getName() + " not found for " + clazz.getName());
	}

	/**
	 * 获得当前对象和other对象属性值不同的列集合，当other的类型和当前对象所属类型不同时将抛出RuntimeException
	 *
	 * @param other         比较的对象
	 * @return List<String> 属性值不同的列集合      
	 * 创建日期：2012-11-21
	 * 修改说明：
	 * @author wangk
	 */
	public List<String> getDifferentColumns(Entity other) {
		List<String> differentColumns = new ArrayList<String>();
		Class<? extends Entity> clazz = getClass();
		for (Field field  : ObjectUtil.getAllField(clazz)) {
			Column column = field.getAnnotation(Column.class);
			if(column == null) {
				continue;
			}
			String name = column.value();
			if(name.equals(COLUMN_NAME_DEFAULT)) {
				name = field.getName().toUpperCase();
			}
			Object value = null;
			Object otherValue = null;
			try {
				Method method = new PropertyDescriptor(field.getName(), clazz).getReadMethod();
				value = method.invoke(this);
				otherValue = method.invoke(other);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			if(value==null && otherValue!=null || value!=null && !value.equals(otherValue)) {
				differentColumns.add(name);
			}
		}
		return differentColumns;
	}

	/**
	 * 获得指定列名的属性值
	 *
	 * @param <T>         属性类型参数，必须用属性的实际类型及其父类型接收返回值，否则将抛出类型转换异常
	 * 						  指定的列为空或不存在时将抛出RuntimeException
	 * @param columnName  列名
	 * @return T          属性值
	 * 创建日期：2012-11-21
	 * 修改说明：
	 * @author wangk
	 */
	public <T> T getFieldValue(String columnName) {
		Class<? extends Entity> clazz = getClass();
		for (Field field  : ObjectUtil.getAllField(clazz)) {
			Column column = field.getAnnotation(Column.class);
			if(column == null) {
				continue;
			}
			String name = column.value();
			if(name.equals(COLUMN_NAME_DEFAULT)) {
				name = field.getName().toUpperCase();
			}
			if(name.equalsIgnoreCase(columnName)) {
				try {
					T value = (T)new PropertyDescriptor(field.getName(), clazz).getReadMethod().invoke(this);
					return value;
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		}
		throw new FieldColumnMappingException(ErrorCode.FIELD_COLUMN_MAPPING_AS_NOT_EXIST_COLUMN, 
				"Column name " + columnName + " not exist for " + clazz.getName());
	}

	/**
	 * 判断实体对象的状态是否为瞬时状态(ID值为空)
	 *
	 * @return
	 * 创建日期：2012-9-24
	 * 修改说明：
	 * @author wangk
	 */
	public boolean isTransient() {
		return identityString() == null;
	}

}
