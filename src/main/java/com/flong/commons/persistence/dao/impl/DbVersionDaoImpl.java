package com.flong.commons.persistence.dao.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import com.flong.commons.lang.exception.DaoAccessException;
import com.flong.commons.persistence.DbVersion;
import com.flong.commons.persistence.SqlScriptReader;
import com.flong.commons.persistence.dao.DbVersionDao;

/**
 * 数据库版本dao实现
 * 创建日期：2013-1-7
 * @author niezhegang
 */
@Repository
public class DbVersionDaoImpl extends EntityDaoSupport<DbVersion> implements DbVersionDao {
	/**目前支持的可升级数据库类型名称*/
	private String[] dbTypeNames = {"mysql"};
	/***/
	private Logger logger = Logger.getLogger(this.getClass());
	/**查询当前版本的SQL语句*/
	private String queryCurrentVersionSQL = "select id,version from "+DbVersion.TABLENAME;
	
	private String updateToNextVersionSQL = "update " + DbVersion.TABLENAME+" set version = version + 1";
	
	/**
	 * @see com.flong.commons.persistence.dao.DbVersionDao#getCurrentVersion()
	 * 创建日期：2013-1-7
	 * 修改说明：
	 * @author niezhegang
	 */
	@Override
	public DbVersion getCurrentVersion() throws DaoAccessException {
		return jdbcTemplate.queryForObject(queryCurrentVersionSQL, new RowMapper<DbVersion>(){
			@Override
			public DbVersion mapRow(ResultSet arg0, int arg1)
					throws SQLException {
				DbVersion version = new DbVersion();
				version.setId(arg0.getLong(1)); 
				version.setVersionNumber(arg0.getInt(2)); 
				return version;
			}
		});
	}
	
	/**
	 * @see com.flong.commons.persistence.dao.DbVersionDao#updateToNextVersion()
	 * 创建日期：2013-1-7
	 * 修改说明：
	 * @author niezhegang
	 */
	@Override
	public void updateToNextVersion() throws DaoAccessException {
		jdbcTemplate.update(updateToNextVersionSQL);
	}
	
	/**
	 * @see com.flong.commons.persistence.dao.DbVersionDao#isInitVersion()
	 * 创建日期：2013-1-7
	 * 修改说明：
	 * @author niezhegang
	 */
	@Override
	public boolean isInitVersion() throws DaoAccessException {
		Connection conn = null;
		boolean ret = false;
		DataSource dataSource = jdbcTemplate.getDataSource();
		try{
			conn = DataSourceUtils.getConnection(dataSource);
			DatabaseMetaData dbmd = conn.getMetaData();
			ResultSet rs = dbmd.getTables(null,null,"SYS_DBVERSION",null);
			if(!rs.next()){
				ret = true;
			}
			return ret;
		}
		catch(Exception e){
			throw new DaoAccessException("判断是否初始化版本失败！",e);
		}
		finally{
			DataSourceUtils.releaseConnection(conn, dataSource);
		}
	}
	
	/**
	 * @see com.flong.commons.persistence.dao.DbVersionDao#executeOneSQLFile(java.io.File)
	 * 创建日期：2013-1-8
	 * 修改说明：
	 * @author niezhegang
	 */
	@Override
	public void executeOneSQLFile(File updateFile) throws DaoAccessException {
		SqlScriptReader scriptReader = null;
		try{
			BufferedReader bufferedReader = new BufferedReader(new FileReader(updateFile));
			scriptReader = new SqlScriptReader(bufferedReader);
			String sql = scriptReader.readOneSQL();
			while(sql != null){
				if(!StringUtils.isBlank(sql)){
					logger.debug(sql);
					jdbcTemplate.execute(sql);
				}
				sql = scriptReader.readOneSQL();
			}
		}
		catch(Exception exception){
			throw new DaoAccessException(exception);
		}
		finally{
			if(scriptReader != null)
				scriptReader.close();
		}
	}

	/**
	 * @see com.flong.commons.persistence.dao.DbVersionDao#getVersionDatabaseTypeName()
	 * 创建日期：2013-1-8
	 * 修改说明：
	 * @author niezhegang
	 */
	@Override
	public String getVersionDatabaseTypeName() throws DaoAccessException{
		Connection conn = null;
		String ret = null;
		DataSource dataSource = jdbcTemplate.getDataSource();
		try{
			conn = DataSourceUtils.getConnection(dataSource);
			DatabaseMetaData dbmd = conn.getMetaData();
			ret = queryMatchedDbname(dbmd);
			return ret;
		}
		catch(Exception e){
			throw new DaoAccessException("获取！",e);
		}
		finally{
			DataSourceUtils.releaseConnection(conn, dataSource);
		}
	} 
	
	/**
	 * 根据数据库元数据匹配目前支持的数据库名
	 * @param dbmd
	 * @return
	 * 创建日期：2013-1-8
	 * 修改说明：
	 * @author niezhegang
	 */
	private String queryMatchedDbname(DatabaseMetaData dbmd) throws SQLException{
		String ret = null;
		String driverName = dbmd.getDriverName();
		for(int i = 0; i < dbTypeNames.length ; i++){
			if(StringUtils.containsIgnoreCase(driverName, dbTypeNames[i])){
				ret = dbTypeNames[i];
				break;
			}
		}
		if(StringUtils.isBlank(ret))
			throw new RuntimeException("不支持的数据库类型："+driverName);
		return ret;
	}

}
