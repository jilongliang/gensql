package com.flong.modules;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.flong.commons.lang.config.PropertiesHelper;
import com.flong.commons.persistence.DBHelper;

/**
 * @Description	BaseService
 * @ClassName	BaseService
 * @Date		2017年3月7日 下午2:24:35
 * @Author		liangjilong
 * @Copyright (c) All Right Reserved , 2017.
 */
public class BaseService {
	protected static Connection conn = null;
	protected static Statement stmt = null;
	protected static PreparedStatement psmt = null;
	protected static CallableStatement callableStatement = null;
	protected static ResultSet rs = null;
	protected static final String ENTER = "\n";
	protected static final String TAB = "    ";
	protected static final String COLS_NAME = "NAME";//列名称
	protected static final String COLS_TYPE = "TYPE";//列的类型
	protected static final String COLS_SIZE = "SIZE";//列的长度
	protected static final String COLS_CLASS = "CLASS";//列的类型
	protected static String dialect = PropertiesHelper.getValueByKey("jdbc.dialect");//获取方言
	public static final String ROOT_PACKAGE = PropertiesHelper.getValueByKey("rootPackage");//文件输出的路径
	public static final String AUTHOR = PropertiesHelper.getValueByKey("author");//作者
	protected static String COMMA=",";
	protected static String sql="";//动态sql
	protected static String result = "";
	
	/**
	 * @Description 获取num条数
	 * @Author		liangjilong
	 * @Date		2017年3月1日 下午1:30:20
	 * @param result
	 * @param sql
	 * @return
	 * @throws SQLException 		参数
	 * @return 		int 返回类型
	 */
	protected static int getNumFieldValue(int result, String sql) throws SQLException {
		psmt = conn.prepareStatement(sql);
		rs = psmt.executeQuery();
		while (rs.next()) {
			result = rs.getInt("num");//获取总条数
		}
		DBHelper.release(conn, psmt, rs);
		return result;
	}
}
