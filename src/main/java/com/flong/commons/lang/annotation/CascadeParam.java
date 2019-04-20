package com.flong.commons.lang.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 级联参数注解，用于Spring MVC方法入参
 *    级联参数格式：a.b.c (表单参数) 或 a[b][c] (ajax请求对象参数)
 *
 * 创建日期：2013-2-20
 * @author wangk
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface CascadeParam {
	/** 参数名，默认为空字符串，表示请求方法入参名 */
	public String value() default "";

}
