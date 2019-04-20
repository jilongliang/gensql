package com.flong.commons.persistence.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.flong.commons.persistence.Entity;

/**
 * 表外键注解
 *
 * 创建日期：2012-9-25
 * @author wangk
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Reference {
	/** 参照实体类型，默认为Entity.class表示所注解的实体自身  */
	public Class<? extends Entity> value() default Entity.class;
	/** 参照属性的字段注解value值，默认为空字符串，表示参照实体类的ID列  */
	public String column() default "";

}
