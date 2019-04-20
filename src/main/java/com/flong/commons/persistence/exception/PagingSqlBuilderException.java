package com.flong.commons.persistence.exception;

/**
 * 分页SQL语句构建异常
 *
 * 创建日期：2012-12-9
 * @author wangk
 */
public class PagingSqlBuilderException extends BaseDataException {
	/** 序列化版本标识 */
	private static final long serialVersionUID = -5366364228211678601L;

	/**
	 * 构造方法
	 * 创建日期：2012-12-9
	 * 修改说明：
	 * @author wangk
	 */
	public PagingSqlBuilderException() {
	}

	public PagingSqlBuilderException(int errorCode) {
		super(errorCode);
	}
	
	public PagingSqlBuilderException(String message) {
		super(message);
	}

	public PagingSqlBuilderException(Throwable cause) {
		super(cause);
	}

	public PagingSqlBuilderException(int errorCode, String message) {
		super(errorCode, message);
	}

	public PagingSqlBuilderException(int errorCode, Throwable cause) {
		super(errorCode, cause);
	}

	public PagingSqlBuilderException(String message, Throwable cause) {
		super(message, cause);
	}

	public PagingSqlBuilderException(int errorCode, String message,
			Throwable cause) {
		super(errorCode, message, cause);
	}

}
