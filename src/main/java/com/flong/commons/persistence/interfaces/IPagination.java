package com.flong.commons.persistence.interfaces;

import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import com.flong.commons.persistence.bean.SimplePage;

/**
 *@Author:liangjilong
 *@Date:2015年10月1日-上午9:03:51
 *@Email:jilongliang@sina.aom
 *@Version:1.0
 *@Desct:........
 */
@SuppressWarnings("all")
public abstract interface IPagination extends ResultSetExtractor {
	
	public abstract RowMapper getRowMapper();

	public abstract void setRowMapper(RowMapper paramRowMapper);

	public abstract void setPageConfig(SimplePage paramPageConfig);

	public abstract SimplePage getPageConfig();

	public abstract String fixSQl(String paramString);

	public abstract String fixCountSQL(String paramString);

	public abstract List list();
}