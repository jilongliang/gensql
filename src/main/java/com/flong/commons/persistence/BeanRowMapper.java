package com.flong.commons.persistence;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.RowMapper;

import com.flong.commons.lang.reflect.ObjectBeans;
/**
 *@Author:liangjilong
 *@Date:2015年10月1日-上午9:03:51
 *@Email:jilongliang@sina.aom
 *@Version:1.0
 *@Desct:........
 */
@SuppressWarnings("all")
public class BeanRowMapper<T> implements RowMapper<T> {
	ColumnMapRowMapper mapper = new ColumnMapRowMapper();
	private Class<?> clazz;

	public BeanRowMapper(Class<?> type) {
		this.clazz = type;
	}

	/***
	 * 
	 */
	public T mapRow(ResultSet rs, int rowNum) throws SQLException {
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnCount = rsmd.getColumnCount();
		Map mapOfColValues = new HashMap();
		for (int i = 1; i <= columnCount; i++) {
			String key = lookupColumnName(rsmd, i);
			key = key.toUpperCase();
			Object obj = getResultSetValue(rs, i);
			//System.out.println(key+"="+obj);//打印key=values值是否能获取到
			mapOfColValues.put(key, obj);
		}
		try {
			return (T) ObjectBeans.getBean(mapOfColValues, this.clazz);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/***
	 * 获取结果的Set的值Value
	 * @param rs
	 * @param index
	 * @return
	 * @throws SQLException
	 */
	public static Object getResultSetValue(ResultSet rs, int index)
			throws SQLException {
		Object obj = rs.getObject(index);
		String className = null;
		if (obj != null) {
			className = obj.getClass().getName();
		}
		if ((obj instanceof Blob)) {
			obj = rs.getBytes(index);
		}else if ((obj instanceof Clob)) {
			obj = rs.getString(index);
		}else if ((obj instanceof BigDecimal)) {
			obj = rs.getBigDecimal(index);
		}else if ((obj instanceof BigDecimal)) {
			obj = rs.getBigDecimal(index);
		} else if ((className != null)
				&& (("oracle.sql.TIMESTAMP".equals(className)) || ("oracle.sql.TIMESTAMPTZ"
						.equals(className)))) {
			obj = rs.getTimestamp(index);
		} else if ((className != null)
				&& (className.startsWith("oracle.sql.DATE"))) {
			String metaDataClassName = rs.getMetaData().getColumnClassName(
					index);
			if (("java.sql.Timestamp".equals(metaDataClassName))
					|| ("oracle.sql.TIMESTAMP".equals(metaDataClassName))) {
				obj = rs.getTimestamp(index);
			} else {
				obj = rs.getDate(index);
			}
		} else if ((obj != null)
				&& ((obj instanceof Date))
				&& ("java.sql.Timestamp".equals(rs.getMetaData()
						.getColumnClassName(index)))) {
			obj = rs.getTimestamp(index);
		}

		return obj;
	}

	/***
	 * 列的字段名.
	 * @param resultSetMetaData
	 * @param columnIndex
	 * @return
	 * @throws SQLException
	 */
	public static String lookupColumnName(ResultSetMetaData resultSetMetaData,
			int columnIndex) throws SQLException {
		String name = resultSetMetaData.getColumnLabel(columnIndex);
		if ((name == null) || (name.length() < 1)) {
			name = resultSetMetaData.getColumnName(columnIndex);
		}
		return name;
	}
}