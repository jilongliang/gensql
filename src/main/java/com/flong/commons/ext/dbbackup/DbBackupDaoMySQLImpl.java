package com.flong.commons.ext.dbbackup;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

@Repository
public class DbBackupDaoMySQLImpl implements DbBackupDao {
	private static final String NEWLINE = "\r\n";
	private static final SimpleDateFormat DF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private JdbcTemplate jdbc;

	public String getSchema() {
		String sql = "select DATABASE()";
		String schema = (String) this.jdbc.queryForObject(sql, String.class);
		return schema;
	}

	public List<String> getTables(String schema) {
		String sql = "SELECT table_name FROM INFORMATION_SCHEMA.TABLES WHERE table_schema = ?";
		List tables = this.jdbc.queryForList(sql, new Object[] { schema },String.class);

		return tables;
	}

	public String getTableDDL(String schema, String table) {
		String sql = new StringBuilder().append("show create table `")
				.append(schema).append("`.`").append(table).append("`")
				.toString();

		String ddl = new StringBuilder().append("DROP TABLE IF EXISTS `")
				.append(table).append("`;").append("\r\n")
				.append((String) this.jdbc.query(sql, new ResultSetExtractor() {
					public String extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						if (rs.next()) {
							return rs.getString(2);
						}
						return null;
					}
				})).append(";").append("\r\n").toString();

		return ddl;
	}

	public void writeData(String schema, String table, BufferedWriter writer) {
	}

	public void restore(BufferedReader reader) throws DataAccessException,
			IOException {
		String line = null;
		StringBuilder sqlBuilder = new StringBuilder();
		while ((line = reader.readLine()) != null) {
			if ((StringUtils.isBlank(line)) || (line.startsWith("--"))) {
				continue;
			}
			sqlBuilder.append(line);
			if (line.endsWith(";")) {
				sqlBuilder.setLength(sqlBuilder.length() - 1);
				String sql = sqlBuilder.toString();
				this.jdbc.execute(sql);
				sqlBuilder.setLength(0);
			}
		}
	}

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbc = new JdbcTemplate(dataSource);
	}
}