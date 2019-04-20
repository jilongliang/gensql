package com.flong.commons.lang.exception;

import com.flong.commons.lang.exception.enums.ExceptionLevel;

/**
 * DAO访问异常类（受检查异常），系统DAO层异常超类
 *
 * 创建日期：2012-12-18
 * @author wangk
 */
public class DaoAccessException extends DataAccessException {
	/** 序列化版本标识 */
	private static final long serialVersionUID = -7329416253080169335L;

	/**
	 * 构造方法
	 * 创建日期：2012-12-18
	 * 修改说明：
	 * @author wangk
	 */
	public DaoAccessException() {
	}

	public DaoAccessException(ExceptionLevel level, String message,
			Throwable cause) {
		super(level, message, cause);
	}

	public DaoAccessException(ExceptionLevel level, String message) {
		super(level, message);
	}

	public DaoAccessException(ExceptionLevel level, Throwable cause) {
		super(level, cause);
	}

	public DaoAccessException(ExceptionLevel level) {
		super(level);
	}

	public DaoAccessException(String message, Throwable cause) {
		super(message, cause);
	}

	public DaoAccessException(String message) {
		super(message);
	}

	public DaoAccessException(Throwable cause) {
		super(cause);
	}
	
}
