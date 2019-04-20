package com.flong.commons.persistence.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 关联关系注解
 * 	    注解一对多关联关系，依赖于Reference注解使用
 * 	    所注解的属性的类型必须出现在Reference注解里	
 *
 * 创建日期：2012-12-3
 * @author wangk
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Association {

}
