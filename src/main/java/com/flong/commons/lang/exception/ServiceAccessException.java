package com.flong.commons.lang.exception;

import com.flong.commons.lang.exception.enums.ExceptionLevel;

/**
 * Service访问异常类（受检查异常），系统Service层异常超类
 *
 * 创建日期：2012-12-18
 * @author wangk
 */
public class ServiceAccessException extends BaseException {
	/** 序列化版本标识 */
	private static final long serialVersionUID = 8236804044254264101L;

	/**
	 * 构造方法
	 * 创建日期：2012-12-18
	 * 修改说明：
	 * @author wangk
	 */
	public ServiceAccessException() {
	}

	public ServiceAccessException(ExceptionLevel level, String message,
			Throwable cause) {
		super(level, message, cause);
	}

	public ServiceAccessException(ExceptionLevel level, String message) {
		super(level, message);
	}

	public ServiceAccessException(ExceptionLevel level, Throwable cause) {
		super(level, cause);
	}

	public ServiceAccessException(ExceptionLevel level) {
		super(level);
	}

	public ServiceAccessException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceAccessException(String message) {
		super(message);
	}

	public ServiceAccessException(Throwable cause) {
		super(cause);
	}
	
}
