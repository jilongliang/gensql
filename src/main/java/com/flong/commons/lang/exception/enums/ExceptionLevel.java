package com.flong.commons.lang.exception.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 异常日志级别枚举类
 *
 * 创建日期：2012-12-18
 * @author wangk
 */
public enum ExceptionLevel {
	/**
	 * 枚举值
	 */
	/** 警告级别，日志等级设为3，1和2为debug,info的级别，和log4j日志级别对应  */
	WARN(3, "warn", "警告"),
	/** 错误级别，默认的异常日志级别  */
	ERROR(4, "error", "错误"),
	/** 严重错误级别 */
	FATAL(5, "fatal", "严重错误");

	/** 日志等级 */
	private int level;
	/** 常量编码 */
	private String code;
	/** 常量描述 */
	private String desc;

	private ExceptionLevel(int level, String code, String desc) {
		this.level = level;
		this.code = code;
		this.desc = desc;
	}
	
	public int getLevel() {
		return level;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
	
	/**
	 * 根据code获取异常日志级别，不存在则返回默认的级别error
	 *
	 * @param code
	 * @return
	 * 创建日期：2012-11-13
	 * 修改说明：
	 * @author wangk
	 */
	public static ExceptionLevel getByCode(String code){
		for(ExceptionLevel exceptionLevel : ExceptionLevel.values()){
			if(StringUtils.equals(exceptionLevel.getCode(), code)) {
				return exceptionLevel;
			}
		}
		return ExceptionLevel.ERROR;
	}

}
