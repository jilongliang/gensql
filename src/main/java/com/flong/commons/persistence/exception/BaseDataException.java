package com.flong.commons.persistence.exception;

import java.util.HashMap;
import java.util.Map;

import com.flong.commons.lang.exception.BaseRuntimeException;

/**
 * 自定义运行时异常基类
 *
 * 创建日期：2012-11-9
 * @author wangk
 */
public class BaseDataException extends BaseRuntimeException {
	/** 序列化版本标识 */
	private static final long serialVersionUID = -440118681330340734L;
	
	/** 错误代码和错误信息映射集合 */
	public static final Map<Integer, String> CODE_MESSAGE_MAPPING = new HashMap<Integer, String>();
	
	/** 错误代码，同一种异常可以有不同的错误代码，分别表示更具体的业务含义 */
	private int errorCode;

	/**
	 * 无参构造方法
	 * 创建日期：2012-11-9
	 * 修改说明：
	 * @author wangk
	 */
	public BaseDataException() {
	}

	/**
	 * 构造方法，初始化errorCode和message，当没有指定message时message初始化成ErrorMessage，下同
	 * @param errorCode  错误代码
	 * 创建日期：2012-11-9
	 * 修改说明：
	 * @author wangk
	 */
	public BaseDataException(int errorCode) {
		super(CODE_MESSAGE_MAPPING.get(errorCode));
		this.errorCode = errorCode;
	}
	
	/**
	 * 构造方法
	 * @param message   异常信息
	 * 创建日期：2012-11-9
	 * 修改说明：
	 * @author wangk
	 */
	public BaseDataException(String message) {
		super(message);
	}

	/**
	 * 构造方法
	 * @param cause 上层异常对象
	 * 创建日期：2012-11-9
	 * 修改说明：
	 * @author wangk
	 */
	public BaseDataException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * 构造方法
	 * @param errorCode
	 * @param message
	 * 创建日期：2012-11-9
	 * 修改说明：
	 * @author wangk
	 */
	public BaseDataException(int errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	/**
	 * 
	 * 构造方法
	 * @param errorCode
	 * @param cause
	 * 创建日期：2012-11-9
	 * 修改说明：
	 * @author wangk
	 */
	public BaseDataException(int errorCode, Throwable cause) {
		super(CODE_MESSAGE_MAPPING.get(errorCode), cause);
		this.errorCode = errorCode;
	}
	
	/**
	 * 
	 * 构造方法
	 * @param message
	 * @param cause
	 * 创建日期：2012-11-9
	 * 修改说明：
	 * @author wangk
	 */
	public BaseDataException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * 
	 * 构造方法
	 * @param errorCode
	 * @param message
	 * @param cause
	 * 创建日期：2012-11-9
	 * 修改说明：
	 * @author wangk
	 */
	public BaseDataException(int errorCode, String message, Throwable cause) {
		super(message, cause);
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * 获得错误信息
	 *
	 * @return
	 * 创建日期：2012-11-9
	 * 修改说明：
	 * @author wangk
	 */
	public String getErrorMessage() {   
	    return CODE_MESSAGE_MAPPING.get(errorCode);
	}

	/**
	 * 初始化错误代码和错误信息映射集合，由spring静态注入
	 *
	 * @param codeMessageMapping
	 * 创建日期：2012-11-9
	 * 修改说明：
	 * @author wangk
	 */
	public static void initCodeMessageMapping(Map<String, String> codeMessageMapping) {
		if(codeMessageMapping == null) {
			return;
		}
		for (String errorCode : codeMessageMapping.keySet()) {
			CODE_MESSAGE_MAPPING.put(Integer.valueOf(errorCode), codeMessageMapping.get(errorCode));
		}
	}

}
