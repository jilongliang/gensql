package com.flong.commons.persistence.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ID字段注解
 * 	  注解在实体类中的属性上，在一个实体类中必须出现且只能出现一次
 *
 * 创建日期：2012-9-25
 * @author wangk
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Id {

}
