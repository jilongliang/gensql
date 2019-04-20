package com.flong.commons.lang.exception;

import com.flong.commons.lang.exception.enums.ExceptionLevel;

/**
 * 数据访问异常类（受检查异常），数据服务器接口异常超类
 *
 * 创建日期：2012-12-18
 * @author wangk
 */
public class DataAccessException extends BaseException {
	/** 序列化版本标识 */
	private static final long serialVersionUID = -7329416253080169335L;

	/**
	 * 构造方法
	 * 创建日期：2012-12-18
	 * 修改说明：
	 * @author wangk
	 */
	public DataAccessException() {
	}

	public DataAccessException(ExceptionLevel level, String message,
			Throwable cause) {
		super(level, message, cause);
	}

	public DataAccessException(ExceptionLevel level, String message) {
		super(level, message);
	}

	public DataAccessException(ExceptionLevel level, Throwable cause) {
		super(level, cause);
	}

	public DataAccessException(ExceptionLevel level) {
		super(level);
	}

	public DataAccessException(String message, Throwable cause) {
		super(message, cause);
	}

	public DataAccessException(String message) {
		super(message);
	}

	public DataAccessException(Throwable cause) {
		super(cause);
	}
	
}
