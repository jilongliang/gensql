package com.flong.commons.persistence.exception;

/**
 * 属性列匹配异常
 *
 * 创建日期：2012-12-4
 * @author wangk
 */
public class FieldColumnMappingException extends BaseDataException {
	private static final long serialVersionUID = 1202029476999942277L;

	public FieldColumnMappingException() {
	}

	public FieldColumnMappingException(int errorCode, String message,
			Throwable cause) {
		super(errorCode, message, cause);
	}

	public FieldColumnMappingException(int errorCode, String message) {
		super(errorCode, message);
	}

	public FieldColumnMappingException(int errorCode, Throwable cause) {
		super(errorCode, cause);
	}

	public FieldColumnMappingException(int errorCode) {
		super(errorCode);
	}

	public FieldColumnMappingException(String message, Throwable cause) {
		super(message, cause);
	}

	public FieldColumnMappingException(String message) {
		super(message);
	}

	public FieldColumnMappingException(Throwable cause) {
		super(cause);
	}

}
