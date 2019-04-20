package com.flong.commons.lang.exception;

import org.apache.log4j.Logger;

/**
 * 异常日志记录器接口 
 *
 * 创建日期：2012-12-18
 * @author wangk
 */
public interface IExceptionLogger {
	
	/**
	 * 将异常信息写入到日志记录器中
	 *
	 * @param logger
	 * 创建日期：2012-12-18
	 * 修改说明：
	 * @author wangk
	 */
	void writeTo(Logger logger);
}
