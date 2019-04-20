package com.flong.commons.persistence.dao;

import java.io.File;

import com.flong.commons.lang.exception.DaoAccessException;
import com.flong.commons.persistence.DbVersion;

public interface DbVersionDao {
	
	/**
	 * 获取当前数据库版本 
	 * @return
	 * @throws DaoAccessException
	 * 创建日期：2013-1-7
	 * 修改说明：
	 * @author niezhegang
	 */
	public DbVersion getCurrentVersion() throws DaoAccessException;
	
	/**
	 * 版本升级成功后需要更新版本标志到下一版本
	 * @throws DaoAccessException
	 * 创建日期：2013-1-7
	 * 修改说明：
	 * @author niezhegang
	 */
	public void updateToNextVersion() throws DaoAccessException;
	
	/**
	 * 因为数据库版本信息存放在SYS_DBVERSION表中
	 * 如果在初始版本，SYS_DBVERSION表本身在数据库中
	 * 也不存在，这时候需要判断，如果是初始版本，则需要先建立SYS_DBVERSION表
	 * @return
	 * @throws DaoAccessException
	 * 创建日期：2013-1-7
	 * 修改说明：
	 * @author niezhegang
	 */
	public boolean isInitVersion() throws DaoAccessException;
	
	/**
	 * 获取版本升级数据库类型名
	 * @return
	 * @throws DaoAccessException
	 * 创建日期：2013-1-8
	 * 修改说明：
	 * @author niezhegang
	 */
	public String getVersionDatabaseTypeName() throws DaoAccessException;
	
	/**
	 * 执行一个sql文件的升级
	 * @param updateFile
	 * @throws DaoAccessException
	 * 创建日期：2013-1-8
	 * 修改说明：
	 * @author niezhegang
	 */
	public void executeOneSQLFile(File updateFile) throws DaoAccessException;

}
