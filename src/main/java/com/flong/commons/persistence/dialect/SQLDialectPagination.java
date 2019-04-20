package com.flong.commons.persistence.dialect;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.flong.commons.lang.StringUtils;
import com.flong.commons.lang.config.PropertiesHelper;
import com.flong.commons.persistence.DefaultPagination;
import com.flong.commons.persistence.bean.SimplePage;

/**
 *@Author:liangjilong
 *@Date:2015年10月1日-上午9:03:51
 *@Email:jilongliang@sina.aom
 *@Version:1.0
 *@Desct:........
 */
@SuppressWarnings("all")
public class SQLDialectPagination extends DefaultPagination {
	private final Logger log = Logger.getLogger(SQLDialectPagination.class);
	/**
	 * 固定sql进行处理
	 */
	public String fixSQl(String sql) {
		this.isNotSkipResultRows = true;
		SimplePage pageConfig = this.simplePage;
		int showCount = 10;//每页显示多少条
		int page = 1;
		if (pageConfig != null) {
			showCount = pageConfig.getShowCount();//显示多少条记录数
			page = pageConfig.getPageNum();//分页数
		}
		if (showCount < 1)
			showCount = 10;
		if (page < 1)
			page = 1;
		int begin = (page - 1) * showCount;//开始条数
		int end = page * showCount;//结束条数
		
		//1、通过指定key进行配置数据库方言,此方法必须要配置方言key.
		String dialect = pro.getValueByKey("jdbc.dialect");
		//2、通过解析jdbc.url或jdbc.driver的key进行识别是什么数据库进行判断方言
		//String dialectDB = pro.getValueByKey("jdbc.driver");//获取方言
		//if(StringUtils.isNotEmpty(dialect) && dialectDB.toLowerCase().contains("oracle")){
		//}
		//-------------------------获取方言切换数据库进行处理做查询总数进行分页-------------------------
		if (StringUtils.isNotEmpty(dialect) && "oracle".equalsIgnoreCase(dialect)) {
			dialects = new OracleDialect();
			sql = dialects.getLimitString(sql, begin, showCount);
		}else if (StringUtils.isNotEmpty(dialect) && "mysql".equalsIgnoreCase(dialect)) {
			dialects = new MySQLDialect();
			sql = dialects.getLimitString(sql, begin, showCount);
		}else if (StringUtils.isNotEmpty(dialect) && "Postgre".equalsIgnoreCase(dialect)) {
			dialects = new PostgreSQLDialect();
			sql = dialects.getLimitString(sql, begin, showCount);
		}else if (StringUtils.isNotEmpty(dialect) && "SQLServer2005".equalsIgnoreCase(dialect)) {
			dialects = new SQLServer2005Dialect();
			sql = dialects.getLimitString(sql, begin, showCount);
		}else if (StringUtils.isNotEmpty(dialect) && "HSQL".equalsIgnoreCase(dialect)) {
			dialects = new HSQLDialect();
			sql = dialects.getLimitString(sql, begin, showCount);
		}else if (StringUtils.isNotEmpty(dialect) && "SQLServer".equalsIgnoreCase(dialect)) {
			dialects = new SQLServerDialect();//SQLServer2000
			sql = dialects.getLimitString(sql, begin, showCount);
		}else if (StringUtils.isNotEmpty(dialect) && "H2".equalsIgnoreCase(dialect)) {
			dialects = new H2Dialect();
			sql = dialects.getLimitString(sql, begin, showCount);
		}else if (StringUtils.isNotEmpty(dialect) && "Sybase".equalsIgnoreCase(dialect)) {
			dialects = new SybaseDialect();
			sql = dialects.getLimitString(sql, begin, showCount);
		}else if (StringUtils.isNotEmpty(dialect) && "DB2".equalsIgnoreCase(dialect)) {
			dialects = new DB2Dialect();
			sql = dialects.getLimitString(sql, begin, showCount);
		}else if (StringUtils.isNotEmpty(dialect) && "Derby".equalsIgnoreCase(dialect)) {
			dialects = new DerbyDialect();
			sql = dialects.getLimitString(sql, begin, showCount);
		}
		
		log.info(sql);
		return sql;
	}
}