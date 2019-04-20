package com.flong.commons.persistence.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 表字段注解
 *
 * 创建日期：2012-9-19
 * @author wangk
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
	/** 字段名，默认为空字符串，表示字段名为所注解的属性的名称的大写形式 */
	public String value() default "";
	/** 字段类型，取值为java.sql.Types中的常量值，默认值0表示未指定类型  */
	public int type() default 0;
	/** 字段长度，默认值0表示未指定长度  */
	public int size() default 0;
	/** 是否有唯一性约束标志，默认false表示没有唯一性约束  */
	public boolean isUnique() default false;
	/** 是否有非空约束标志，默认false表示没有非空约束  */
	public boolean isNotNull() default false;
	/** 是否有默认值标志，默认false表示没有默认值  */
	public boolean hasDefault() default false;

}
