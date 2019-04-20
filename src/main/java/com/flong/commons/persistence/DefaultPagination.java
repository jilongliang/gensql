package com.flong.commons.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;

import com.flong.commons.lang.config.PropertiesHelper;
import com.flong.commons.persistence.bean.SimplePage;
import com.flong.commons.persistence.dialect.Dialect;
import com.flong.commons.persistence.interfaces.IPagination;

/***
 * @Author:liangjilong
 * @Date:2015年9月13日-下午7:04:19
 * @Email:jilongliang@sina.aom
 */
@SuppressWarnings("all")
public class DefaultPagination implements IPagination {
	protected Dialect dialects = null;
	protected RowMapper rowMapper;
	protected SimplePage simplePage;
	protected List list = new ArrayList();
	protected boolean isNotSkipResultRows = false;
	protected PropertiesHelper pro = new PropertiesHelper();
	public Object extractData(ResultSet rs) throws SQLException,
			DataAccessException {
		if (this.simplePage == null) {
			this.simplePage = new SimplePage();
			this.simplePage.setPageNum(1);
			this.simplePage.setShowCount(10);
		}
		int pageSize = this.simplePage.getShowCount();
		int p = this.simplePage.getPageNum();
		long begin = (p - 1) * pageSize;
		long end = p * pageSize;
		if (this.isNotSkipResultRows) {
			begin = 0L;
			end = pageSize;
		}
		long row = 0L;
		while (rs.next()) {
			long crow = row;
			row += 1L;
			if (crow >= begin) {
				if ((crow < end ? 1 : 0) == 0)
					break;
				Object o = this.rowMapper.mapRow(rs, (int) row);
				this.list.add(o);
			}
		}
		return null;
	}

	public SimplePage getPageConfig() {
		return this.simplePage;
	}

	public void setPageConfig(SimplePage simplePage) {
		this.simplePage = simplePage;
	}

	public List list() {
		List rst = this.list;
		this.list = new ArrayList();
		return rst;
	}

	public RowMapper getRowMapper() {
		return this.rowMapper;
	}

	public void setRowMapper(RowMapper rowmapper) {
		this.rowMapper = rowmapper;
	}

	public String fixSQl(String sql) {
		return sql;
	}

	public String fixCountSQL(String sql) {
		return "select count(1) from (" + sql + ") A ";
	}
}