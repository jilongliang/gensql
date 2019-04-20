package com.flong.commons.lang.exception;

import com.flong.commons.lang.exception.enums.ExceptionLevel;

/**
 * 通过jsp错误页面展示的异常信息
 * 如果控制器方法中需要将错误 通过统一的jsp错误页面展示，则需将异常包装成该类型并向上抛出
 * 创建日期：2012-12-20
 * @author niezhegang
 */
public class JspErrorPageException extends ApplicationAccessException implements IExceptionJspErrorPage{

	private static final long serialVersionUID = 4256876503018260341L;
	
	public JspErrorPageException() {
	}

	public JspErrorPageException(ExceptionLevel level, String message,
			Throwable cause) {
		super(level, message, cause);
	}

	public JspErrorPageException(ExceptionLevel level, String message) {
		super(level, message);
	}

	public JspErrorPageException(ExceptionLevel level, Throwable cause) {
		super(level, cause);
	}

	public JspErrorPageException(ExceptionLevel level) {
		super(level);
	}

	public JspErrorPageException(String message, Throwable cause) {
		super(message, cause);
	}

	public JspErrorPageException(String message) {
		super(message);
	}

	public JspErrorPageException(Throwable cause) {
		super(cause);
	}

}
