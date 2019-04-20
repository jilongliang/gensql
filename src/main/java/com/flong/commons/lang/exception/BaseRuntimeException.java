package com.flong.commons.lang.exception;

import org.apache.log4j.Logger;

import com.flong.commons.lang.exception.enums.ExceptionLevel;

/**
 * 运行时异常基类
 *
 * 创建日期：2012-12-18
 * @author wangk
 */
public class BaseRuntimeException extends RuntimeException implements IExceptionLogger, IExceptionLevel {
	/** 序列化版本标识 */
	private static final long serialVersionUID = -4195975136323130509L;
	
	/** 异常日志级别，默认为error级别 */
	private ExceptionLevel level = ExceptionLevel.ERROR;

	/**
	 * 无参构造方法
	 * 创建日期：2012-12-18
	 * 修改说明：
	 * @author wangk
	 */
	public BaseRuntimeException() {
	}

	/**
	 * 构造方法，初始化level
	 * @param level  异常日志级别
	 * 创建日期：2012-12-18
	 * 修改说明：
	 * @author wangk
	 */
	public BaseRuntimeException(ExceptionLevel level) {
		this.level = level;
	}
	
	/**
	 * 构造方法
	 * @param message   异常信息
	 * 创建日期：2012-12-18
	 * 修改说明：
	 * @author wangk
	 */
	public BaseRuntimeException(String message) {
		super(message);
	}

	/**
	 * 构造方法
	 * @param cause 上层异常对象
	 * 创建日期：2012-12-18
	 * 修改说明：
	 * @author wangk
	 */
	public BaseRuntimeException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * 构造方法
	 * @param level
	 * @param message
	 * 创建日期：2012-12-18
	 * 修改说明：
	 * @author wangk
	 */
	public BaseRuntimeException(ExceptionLevel level, String message) {
		super(message);
		this.level = level;
	}

	/**
	 * 构造方法
	 * @param level
	 * @param cause
	 * 创建日期：2012-12-18
	 * 修改说明：
	 * @author wangk
	 */
	public BaseRuntimeException(ExceptionLevel level, Throwable cause) {
		super(cause);
		this.level = level;
	}
	
	/**
	 * 构造方法
	 * @param message
	 * @param cause
	 * 创建日期：2012-12-18
	 * 修改说明：
	 * @author wangk
	 */
	public BaseRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * 构造方法
	 * @param level
	 * @param message
	 * @param cause
	 * 创建日期：2012-12-18
	 * 修改说明：
	 * @author wangk
	 */
	public BaseRuntimeException(ExceptionLevel level, String message, Throwable cause) {
		super(message, cause);
		this.level = level;
	}
	
	@Override
	public ExceptionLevel getLevel() {
		return level;
	}

	public void setLevel(ExceptionLevel level) {
		this.level = level;
	}

	/**
	 * @see com.flong.commons.exception.IExceptionLogger#writeTo(org.apache.log4j.Logger)
	 * 创建日期：2012-12-18
	 * 修改说明：
	 * @author wangk
	 */
	@Override
	public void writeTo(Logger logger) {
		switch (level) {
		case WARN:
			logger.warn(getMessage(), getCause());
			break;
		case FATAL:
			logger.fatal(getMessage(), getCause());
			break;
		default:
			//默认为error级别
			logger.error(getMessage(), getCause());
		}
	}

}
