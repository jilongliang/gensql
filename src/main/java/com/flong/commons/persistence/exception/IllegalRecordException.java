package com.flong.commons.persistence.exception;

/**
 * 非法记录异常，表示待新增或更新的记录不符合约束条件
 *
 * 创建日期：2013-1-22
 * @author wangk
 */
public class IllegalRecordException extends BaseDataException {
	private static final long serialVersionUID = -2772359510913722415L;

	/**
	 * 构造方法
	 * 创建日期：2013-1-22
	 * 修改说明：
	 * @author wangk
	 */
	public IllegalRecordException() {
	}

	public IllegalRecordException(int errorCode, String message, Throwable cause) {
		super(errorCode, message, cause);
	}

	public IllegalRecordException(int errorCode, String message) {
		super(errorCode, message);
	}

	public IllegalRecordException(int errorCode, Throwable cause) {
		super(errorCode, cause);
	}

	public IllegalRecordException(int errorCode) {
		super(errorCode);
	}

	public IllegalRecordException(String message, Throwable cause) {
		super(message, cause);
	}

	public IllegalRecordException(String message) {
		super(message);
	}

	public IllegalRecordException(Throwable cause) {
		super(cause);
	}

}
