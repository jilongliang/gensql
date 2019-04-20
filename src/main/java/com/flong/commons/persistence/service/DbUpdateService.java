package com.flong.commons.persistence.service;

import org.springframework.core.Ordered;

import com.flong.commons.lang.exception.ServiceAccessException;

/**
 * 定义一数据库升级服务接口
 * 创建日期：2013-1-6
 * @author niezhegang
 */
public interface DbUpdateService extends Ordered{
	/**
	 * 检查数据库是否需要升级，如果需要升级则启动数据库升级程序
	 * @throws ServiceAccessException
	 * 创建日期：2013-1-6
	 * 修改说明：
	 * @author niezhegang
	 */
	public void checkDbUpdate() throws ServiceAccessException;
}
