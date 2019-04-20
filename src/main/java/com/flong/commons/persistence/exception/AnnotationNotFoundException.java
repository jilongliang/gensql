package com.flong.commons.persistence.exception;

/**
 * 注解没有找到异常类
 *
 * 创建日期：2012-12-4
 * @author wangk
 */
public class AnnotationNotFoundException extends BaseDataException {
	private static final long serialVersionUID = -8446638857882430845L;

	public AnnotationNotFoundException() {
	}

	public AnnotationNotFoundException(int errorCode, String message,
			Throwable cause) {
		super(errorCode, message, cause);
	}

	public AnnotationNotFoundException(int errorCode, String message) {
		super(errorCode, message);
	}

	public AnnotationNotFoundException(int errorCode, Throwable cause) {
		super(errorCode, cause);
	}

	public AnnotationNotFoundException(int errorCode) {
		super(errorCode);
	}

	public AnnotationNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public AnnotationNotFoundException(String message) {
		super(message);
	}

	public AnnotationNotFoundException(Throwable cause) {
		super(cause);
	}

}
