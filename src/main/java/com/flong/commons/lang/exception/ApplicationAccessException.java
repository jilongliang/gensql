package com.flong.commons.lang.exception;

import com.flong.commons.lang.exception.enums.ExceptionLevel;

/**
 * 应用访问异常类（受检查异常），系统控制层异常超类
 *
 * 创建日期：2012-12-18
 * @author wangk
 */
public class ApplicationAccessException extends BaseException {
	/** 序列化版本标识 */
	private static final long serialVersionUID = 8953047096893618652L;

	/**
	 * 构造方法
	 * 创建日期：2012-12-18
	 * 修改说明：
	 * @author wangk
	 */
	public ApplicationAccessException() {
	}

	public ApplicationAccessException(ExceptionLevel level, String message,
			Throwable cause) {
		super(level, message, cause);
	}

	public ApplicationAccessException(ExceptionLevel level, String message) {
		super(level, message);
	}

	public ApplicationAccessException(ExceptionLevel level, Throwable cause) {
		super(level, cause);
	}

	public ApplicationAccessException(ExceptionLevel level) {
		super(level);
	}

	public ApplicationAccessException(String message, Throwable cause) {
		super(message, cause);
	}

	public ApplicationAccessException(String message) {
		super(message);
	}

	public ApplicationAccessException(Throwable cause) {
		super(cause);
	}
	
}
