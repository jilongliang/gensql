package com.flong.commons.persistence;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *@Author:liangjilong
 *@Date:2014-12-7
 *@Email:jilongliang@sina.com
 */
public class ReleaseUtils {
	private static Connection conn = null;
	private static PreparedStatement preparedStatement = null;
	private static ResultSet rs = null;
	/***
	 * 释放资源...
	 * 
	 * @param rs
	 * @param st
	 * @param conn
	 */
	public static void releaseAll(ResultSet rs, Statement st, Connection conn) {
		try {
			if (rs != null)
				rs.close();
			if (st != null)
				st.close();
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 释放所有连接
	 */
	public static void releaseAll() {
		if (rs != null) {
			try {
				rs=null;
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (preparedStatement != null) {
			try {
				preparedStatement=null;
				preparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				conn=null;
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	/**
     * 释放资源
     */
    public static void free() {
        free(null);
    }
    /**
     * 释放资源
     * @param rs
     *            结果集
     */
    public static void free(ResultSet rs) {
    	free(conn, preparedStatement, rs);
    }
    /**
     * 释放连接
     * @param conn
     */
    private static void freeConnection(Connection conn) {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
 
    /**
     * 释放statement
     * @param statement
     */
    private static void freeStatement(Statement statement) {
        try {
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
 
    /**
     * 释放resultset
     * @param rs
     */
    private static void freeResultSet(ResultSet rs) {
        try {
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
 
    /**
     * 释放资源
     * @param conn
     * @param statement
     * @param rs
     */
    public static void free(Connection conn, Statement statement, ResultSet rs) {
        if (rs != null) {
            freeResultSet(rs);
        }
        if (statement != null) {
            freeStatement(statement);
        }
        if (conn != null) {
            freeConnection(conn);
        }
    }

    /**
     * 释放资源
     * @param statement
     * @param rs
     */
    public static void free(Statement statement, ResultSet rs) {
    	free(conn, statement, rs);
    }

}
