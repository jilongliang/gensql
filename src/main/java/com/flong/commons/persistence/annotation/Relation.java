package com.flong.commons.persistence.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 表名注解
 *
 * 创建日期：2012-9-19
 * @author wangk
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Relation {
	/** 表名  */
	public String value();

}
