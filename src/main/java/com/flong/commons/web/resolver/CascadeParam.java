package com.flong.commons.web.resolver;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description
 * 创建日期：2013-2-20
 * @author wangk
 * @since trse-commons
 */
@Documented
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface CascadeParam {
	public String value() default "";
}
