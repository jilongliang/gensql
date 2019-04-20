package com.flong.commons.persistence.exception;

/**
 * 重复记录异常类
 * 当指定有唯一性约束的列的条件查询出多条记录时抛出此异常
 * 表示数据库表里有重复的脏数据
 *
 * 创建日期：2012-11-9
 * @author wangk
 */
public class DuplicateRecordException extends BaseDataException {
	/** 序列化版本标识 */
	private static final long serialVersionUID = 8970000748142076745L;

	/**
	 * 构造方法
	 * 创建日期：2012-11-9
	 * 修改说明：
	 * @author wangk
	 */
	public DuplicateRecordException() {
	}

	public DuplicateRecordException(int errorCode) {
		super(errorCode);
	}
	
	public DuplicateRecordException(String message) {
		super(message);
	}

	public DuplicateRecordException(Throwable cause) {
		super(cause);
	}

	public DuplicateRecordException(int errorCode, String message) {
		super(errorCode, message);
	}

	public DuplicateRecordException(int errorCode, Throwable cause) {
		super(errorCode, cause);
	}

	public DuplicateRecordException(String message, Throwable cause) {
		super(message, cause);
	}

	public DuplicateRecordException(int errorCode, String message,
			Throwable cause) {
		super(errorCode, message, cause);
	}

}
