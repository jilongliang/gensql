package com.flong.commons.persistence.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.flong.commons.lang.exception.BaseRuntimeException;
import com.flong.commons.lang.exception.DaoAccessException;
import com.flong.commons.lang.exception.ServiceAccessException;
import com.flong.commons.persistence.DbVersion;
import com.flong.commons.persistence.dao.DbVersionDao;
import com.flong.commons.persistence.service.DbUpdateService;

/**
 * 数据库升级服务实现类
 * 创建日期：2013-1-6
 * @author niezhegang
 */
public class DbUpdateServiceImpl implements DbUpdateService {
	/**数据库版本数据访问对象*/
	private DbVersionDao dbVersionDao;
	/**日志记录器*/
	private Logger logger = Logger.getLogger(this.getClass());
	/**系统需要运行在那个数据库版本上*/
	private int systemRunningForDbVersion = 1;
	/**升级文件前缀*/
	private String prefixForUpdateFile = "aems";
	/**数据库初始化版本号*/
	private static final int DbInitVersion = -1;
	/**事务模板类*/
	private TransactionTemplate transactionTemplate;
	/**实现order接口*/
	private int order = 0;
	/**
	 * @see com.flong.commons.persistence.service.DbUpdateService#checkDbUpdate()
	 * 创建日期：2013-1-6
	 * 修改说明：
	 * @author niezhegang
	 */
	@Override
	public void checkDbUpdate() throws ServiceAccessException {
		try{
			boolean needUpdateFlag = false;
			DbVersion currentDbVersion = null;
			//如果数据库未初始化
			if(dbVersionDao.isInitVersion()){
				needUpdateFlag = true;
				currentDbVersion = new DbVersion();
				currentDbVersion.setVersionNumber(DbInitVersion);
			}
			else{
				currentDbVersion = dbVersionDao.getCurrentVersion();
				//判断是否需要升级
				if(currentDbVersion.getVersionNumber() < systemRunningForDbVersion){
					needUpdateFlag = true;
				}
			}
			//需求升级则启动升级
			if(needUpdateFlag){
				logger.info("系统运行需要数据库版本["+systemRunningForDbVersion+"]，当前数据库版本为["+currentDbVersion.getVersionNumber()+"]！");
				startDbUpdate(currentDbVersion);
			}
		}
		catch(Exception e){
			logger.error(e);
			throw new ServiceAccessException("检查数据库版本升级失败！",e);
		}
	}
	
	/**
	 * 开始数据库升级
	 * @param currentDbVersion
	 * 创建日期：2013-1-8
	 * 修改说明：
	 * @author niezhegang
	 */
	private void startDbUpdate(DbVersion currentDbVersion) throws Exception{
		//获取升级版本的数据库类型名称
		String dbVersionDbTypeName = dbVersionDao.getVersionDatabaseTypeName();
		int currentVersion = currentDbVersion.getVersionNumber();
		File updateFile = null;
		logger.info("开始数据库升级......");
		//依次升级
		while(currentVersion < systemRunningForDbVersion){
			updateFile = getNextVersionFile(dbVersionDbTypeName,currentVersion);
			if(updateFile != null && updateFile.exists()){
				executeOneSQLFile(updateFile);
				logger.info("当前数据库版本成功升级到["+(currentVersion+1)+"]");
			}
			currentVersion += 1;
		}
		logger.info("数据库升级成功......");
	}
	
	/**
	 * 执行一个sql文件的升级
	 * @param updateFile
	 * 创建日期：2013-1-8
	 * 修改说明：
	 * @author niezhegang
	 */
	private void executeOneSQLFile(final File updateFile) throws DaoAccessException{
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				try{
					dbVersionDao.executeOneSQLFile(updateFile);
					dbVersionDao.updateToNextVersion();
				}
				catch(Exception e){
					throw new BaseRuntimeException("数据库升级出错！",e);
				}
			}
		});
	}
	/**
	 * 获取下一升级版本的文件
	 * @param dbVersionDbName
	 * @param currentVersion
	 * @return
	 * 创建日期：2013-1-8
	 * 修改说明：
	 * @author niezhegang
	 */
	private File getNextVersionFile(String dbVersionDbTypeName,int currentVersion) throws IOException{
		int nextVersion = currentVersion + 1;
		String fileName = prefixForUpdateFile + "_"+dbVersionDbTypeName+"_"+nextVersion+".sql";
		Resource resource = new ClassPathResource("update/"+fileName);
		File retFile = null;
		try{
			retFile = resource.getFile();
		}
		catch(IOException e){
			if(e instanceof FileNotFoundException){
				retFile = null;
				logger.info("升级版本数据库脚本文件["+fileName+"]未找到，将跳过该版本的升级！");
			}
			else{
				throw e;
			}
		}
		return retFile;
	}

	@Override
	public int getOrder() {
		return order;
	}

	public DbVersionDao getDbVersionDao() {
		return dbVersionDao;
	}

	public void setDbVersionDao(DbVersionDao dbVersionDao) {
		this.dbVersionDao = dbVersionDao;
	}

	public int getSystemRunningForDbVersion() {
		return systemRunningForDbVersion;
	}

	public void setSystemRunningForDbVersion(int systemRunningForDbVersion) {
		this.systemRunningForDbVersion = systemRunningForDbVersion;
	}

	public TransactionTemplate getTransactionTemplate() {
		return transactionTemplate;
	}

	public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public String getPrefixForUpdateFile() {
		return prefixForUpdateFile;
	}

	public void setPrefixForUpdateFile(String prefixForUpdateFile) {
		this.prefixForUpdateFile = prefixForUpdateFile;
	}
}
