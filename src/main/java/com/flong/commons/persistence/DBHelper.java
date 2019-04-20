package com.flong.commons.persistence;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.flong.commons.lang.config.PropertiesHelper;
import com.flong.commons.lang.config.PropertiesUtils;

/***
 * 
 * @author liangjilong
 * @Description：数据库帮助类..
 */
@SuppressWarnings("all")
public class DBHelper {
	
	private static DBHelper instance = null;
	private static Connection conn = null;
	private static Statement stmt = null;
	private static PreparedStatement preparedStatement = null;
	private static CallableStatement callableStatement = null;
	private static ResultSet rs = null;
	private Map<String,String> columnTypeMap = new HashMap<String, String>();	//存放字段名和数据类型
	private static String Driver = PropertiesHelper.getValueByKey("jdbc.driver");
	private static String Url = PropertiesHelper.getValueByKey("jdbc.url");
	private static String UserName = PropertiesHelper.getValueByKey("jdbc.username");
	private static String PassWord = PropertiesHelper.getValueByKey("jdbc.password");
	
	
	private static final String driverClass = PropertiesHelper.getValueByKey("jdbc.driver");
	private static final String connectionUrl = PropertiesHelper.getValueByKey("jdbc.url");
	private static final String connectionAllUrl = PropertiesHelper.getValueByKey("jdbc.allUrl");
	private static final String username = PropertiesHelper.getValueByKey("jdbc.username");
	private static final String password = PropertiesHelper.getValueByKey("jdbc.password");
	
	
	public static boolean isServerFlag =false;//部署到百度云环境的时候设置为true，默认让不要执行本地的数据库。

	/**建立单例模式
	 * Single
	 * @return
	 */
	public static DBHelper getInstance() {
		if (instance == null) {
			synchronized (DBHelper.class) {
				instance = new DBHelper();
			}
		}
		return instance;
	}

	/**
	 * 返回一个:Connection
	 * @return:Connection
	 */
	public static Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName(Driver);
			conn = DriverManager.getConnection(Url, UserName, PassWord);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	/**
	 * 加载链接数据库.
	 */
	public void loadConnection(){
		try{
			 Class.forName(Driver); 
			 conn = DriverManager.getConnection(Url, UserName, PassWord);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**构造方法---*/
	public  DBHelper(){
		try {
			loadConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * method3: 专门用于发送增删改语句的方法
	 * @param pstmt
	 * @return
	 */
    public static int execOther(PreparedStatement pstmt){
        try {
            //1、使用Statement对象发送SQL语句
            int affectedRows = pstmt.executeUpdate();
            //2、返回结果
            return affectedRows;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }


    /***
     * method4: 专门用于发送查询语句
     * @param pstmt
     * @return
     */
    public static ResultSet execQuery(PreparedStatement pstmt){
        try {
            //1、使用Statement对象发送SQL语句
            rs = pstmt.executeQuery();
            //2、返回结果
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    /***
	 * 更新
	 * @param sql
	 * @param params
	 */
	public static void update(String sql, List<Object> params) {
		System.out.println("sql: " + sql);
		//System.out.println("params: " + params);
		try {
			PreparedStatement psmt = conn.prepareStatement(sql);
			if(params != null) {
				for (int i = 0; i < params.size(); i++) {
					psmt.setObject(i+1, params.get(i));
				}
			}	
			psmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	

	/**
	 * 查询数据
	 * @param sql
	 * @param params
	 * @return
	 */
	public static ResultSet query(String sql, List<Object> params) {
		//System.out.println("sql: " + sql);
		//System.out.println("params: " + params);
		try {
			PreparedStatement psmt = conn.prepareStatement(sql);
			if(params != null) {
				for (int i = 0; i < params.size(); i++) {
					psmt.setObject(i+1, params.get(i));
				}
			}	
			return psmt.executeQuery();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}


	
 
	 /**
     * 获取环境所有数据库名称
     * @return
     */
	public static List<String> getDataNames() {
		conn=getInstance().getConnectionAll();
		//String sql = "SELECT `SCHEMA_NAME` FROM  SCHEMATA ";
		List<String> arr = new ArrayList<String>();
		try {
			DatabaseMetaData dmd=(DatabaseMetaData)conn.getMetaData();
			ResultSet catalogs = dmd.getCatalogs();
			while(catalogs.next()){
				String dbName = catalogs.getString("TABLE_CAT");
				arr.add(dbName);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return arr;
	}
	/**
	 * 获取MySQL所有数据库名称的链接
	 * @return
	 */
    public static Connection getConnectionAll(){
		try {
			Class.forName(driverClass);
			return DriverManager.getConnection(connectionAllUrl, username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}  
		return null;
	}
    
	
	
	/**
	 * 获取数据DatabaseMetaData对象
	 * @return
	 */
	public DatabaseMetaData  getDatabaseMetaData(){
		
		try {
			return getInstance().getConnection().getMetaData();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	 /** 
     * 获得数据库的一些相关信息 
     */  
    public void getDataBaseInformations() {  
        try {             
            System.out.println("数据库已知的用户: "+ getDatabaseMetaData().getUserName());    
            System.out.println("数据库的系统函数的逗号分隔列表: "+ getDatabaseMetaData().getSystemFunctions());    
            System.out.println("数据库的时间和日期函数的逗号分隔列表: "+ getDatabaseMetaData().getTimeDateFunctions());    
            System.out.println("数据库的字符串函数的逗号分隔列表: "+ getDatabaseMetaData().getStringFunctions());    
            System.out.println("数据库供应商用于 'schema' 的首选术语: "+ getDatabaseMetaData().getSchemaTerm());    
            System.out.println("数据库URL: " + getDatabaseMetaData().getURL());    
            System.out.println("是否允许只读:" + getDatabaseMetaData().isReadOnly());    
            System.out.println("数据库的产品名称:" + getDatabaseMetaData().getDatabaseProductName());    
            System.out.println("数据库的版本:" + getDatabaseMetaData().getDatabaseProductVersion());    
            System.out.println("驱动程序的名称:" + getDatabaseMetaData().getDriverName());    
            System.out.println("驱动程序的版本:" + getDatabaseMetaData().getDriverVersion());   
  
            System.out.println();    
            System.out.println("数据库中使用的表类型");    
            ResultSet rs = getDatabaseMetaData().getTableTypes();    
            while (rs.next()) {    
                System.out.println(rs.getString(1));    
            }    
            rs.close();                
            System.out.println();  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
    }  
    
    /** 
     * 获得该用户下面的所有表 
     */  
    public void getAllTableList(String schemaName) {  
        try {  
            // table type. Typical types are "TABLE", "VIEW", "SYSTEM TABLE", "GLOBAL TEMPORARY", "LOCAL TEMPORARY", "ALIAS", "SYNONYM".  
            String[] types = { "TABLE" };  
            ResultSet rs = getDatabaseMetaData().getTables(null, schemaName, "%", types);  
            while (rs.next()) {  
                String tableName = rs.getString("TABLE_NAME");  //表名  
                String tableType = rs.getString("TABLE_TYPE");  //表类型  
                String remarks = rs.getString("REMARKS");       //表备注  
                System.out.println(tableName + "-" + tableType + "-" + remarks);  
            }  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
    }  
    
    /** 
     * 获得该用户下面的所有视图 
     */  
    public void getAllViewList(String schemaName) {  
         try{    
             String[] types = { "VIEW" };                 
             ResultSet rs = getDatabaseMetaData().getTables(null, schemaName, "%", types);  
             while (rs.next()){  
                 String viewName = rs.getString("TABLE_NAME"); //视图名  
                 String viewType = rs.getString("TABLE_TYPE"); //视图类型  
                 String remarks = rs.getString("REMARKS");      //视图备注  
                 System.out.println(viewName + "-" + viewType + "-" + remarks);  
             }  
         } catch (SQLException e) {  
             e.printStackTrace();  
         }  
    }  
      
     /**   
     * 获得数据库中所有方案名称   
     */    
    public void getAllSchemas(){  
        try{  
            ResultSet rs = getDatabaseMetaData().getSchemas();   
            while (rs.next()){     
                String tableSchem = rs.getString("TABLE_SCHEM");     
                System.out.println(tableSchem);     
            }     
        } catch (SQLException e){  
            e.printStackTrace();     
        }     
    }     
  
  
    /** 
     * 获得表或视图中的所有列信息 
     */  
    public void getTableColumns(String schemaName, String tableName) {  
           
        try{     
                  
            ResultSet rs = getDatabaseMetaData().getColumns(null, schemaName, tableName, "%");              
            while (rs.next()){  
                    String tableCat = rs.getString("TABLE_CAT");//表目录（可能为空）                  
                    String tableSchemaName = rs.getString("TABLE_SCHEM");//表的架构（可能为空）     
                    String tableName_ = rs.getString("TABLE_NAME");//表名  
                    String columnName = rs.getString("COLUMN_NAME");//列名  
                    int dataType = rs.getInt("DATA_TYPE"); //对应的java.sql.Types类型     
                    String dataTypeName = rs.getString("TYPE_NAME");//java.sql.Types类型   名称  
                    int columnSize = rs.getInt("COLUMN_SIZE");//列大小  
                    int decimalDigits = rs.getInt("DECIMAL_DIGITS");//小数位数  
                    int numPrecRadix = rs.getInt("NUM_PREC_RADIX");//基数（通常是10或2）  
                    int nullAble = rs.getInt("NULLABLE");//是否允许为null  
                    String remarks = rs.getString("REMARKS");//列描述  
                    String columnDef = rs.getString("COLUMN_DEF");//默认值  
                    int sqlDataType = rs.getInt("SQL_DATA_TYPE");//sql数据类型  
                    int sqlDatetimeSub = rs.getInt("SQL_DATETIME_SUB");   //SQL日期时间分?  
                    int charOctetLength = rs.getInt("CHAR_OCTET_LENGTH");   //char类型的列中的最大字节数  
                    int ordinalPosition = rs.getInt("ORDINAL_POSITION");  //表中列的索引（从1开始）  
                      
                    /** 
                     * ISO规则用来确定某一列的为空性。 
                     * 是---如果该参数可以包括空值; 
                     * 无---如果参数不能包含空值 
                     * 空字符串---如果参数为空性是未知的 
                     */  
                    String isNullAble = rs.getString("IS_NULLABLE");  
                      
                    /** 
                     * 指示此列是否是自动递增 
                     * 是---如果该列是自动递增 
                     * 无---如果不是自动递增列 
                     * 空字串---如果不能确定它是否 
                     * 列是自动递增的参数是未知 
                     */  
                    String isAutoincrement = rs.getString("IS_AUTOINCREMENT");     
                      
                    System.out.println(tableCat + "-" + tableSchemaName + "-" + tableName_ + "-" + columnName + "-"    
                            + dataType + "-" + dataTypeName + "-" + columnSize + "-" + decimalDigits + "-" + numPrecRadix     
                            + "-" + nullAble + "-" + remarks + "-" + columnDef + "-" + sqlDataType + "-" + sqlDatetimeSub     
                            + charOctetLength + "-" + ordinalPosition + "-" + isNullAble + "-" + isAutoincrement + "-");     
                }     
            } catch (SQLException e){  
                e.printStackTrace();     
            }  
    }  
  
  
    /** 
     * 获得一个表的索引信息 
     */  
    public void getIndexInfo(String schemaName, String tableName) {  
        try{  
            ResultSet rs = getDatabaseMetaData().getIndexInfo(null, schemaName, tableName, true, true);  
            while (rs.next()){  
                boolean nonUnique = rs.getBoolean("NON_UNIQUE");//非唯一索引(Can index values be non-unique. false when TYPE is  tableIndexStatistic   )  
                String indexQualifier = rs.getString("INDEX_QUALIFIER");//索引目录（可能为空）  
                String indexName = rs.getString("INDEX_NAME");//索引的名称  
                short type = rs.getShort("TYPE");//索引类型  
                short ordinalPosition = rs.getShort("ORDINAL_POSITION");//在索引列顺序号  
                String columnName = rs.getString("COLUMN_NAME");//列名  
                String ascOrDesc = rs.getString("ASC_OR_DESC");//列排序顺序:升序还是降序  
                int cardinality = rs.getInt("CARDINALITY");   //基数  
                System.out.println(nonUnique + "-" + indexQualifier + "-" + indexName + "-" + type + "-" + ordinalPosition + "-" + columnName + "-" + ascOrDesc + "-" + cardinality);     
            }     
        } catch (SQLException e){  
            e.printStackTrace();     
        }   
    }  
  
  
    /** 
     * 获得一个表的主键信息 
     */  
    public void getAllPrimaryKeys(String schemaName, String tableName) {  
        try{  
            ResultSet rs = getDatabaseMetaData().getPrimaryKeys(null, schemaName, tableName);  
            while (rs.next()){  
                String columnName = rs.getString("COLUMN_NAME");//列名  
                short keySeq = rs.getShort("KEY_SEQ");//序列号(主键内值1表示第一列的主键，值2代表主键内的第二列)  
                String pkName = rs.getString("PK_NAME"); //主键名称    
                System.out.println(columnName + "-" + keySeq + "-" + pkName);     
            }  
        }catch (SQLException e){  
            e.printStackTrace();  
        }  
    }  
  
    
  
    /** 
     * 获得一个表的外键信息 
     */  
    public void getAllExportedKeys(String schemaName, String tableName) {  
          
        try{  
            ResultSet rs = getDatabaseMetaData().getExportedKeys(null, schemaName, tableName);  
            while (rs.next()){  
                String pkTableCat = rs.getString("PKTABLE_CAT");//主键表的目录（可能为空）  
                String pkTableSchem = rs.getString("PKTABLE_SCHEM");//主键表的架构（可能为空）  
                String pkTableName = rs.getString("PKTABLE_NAME");//主键表名   
                String pkColumnName = rs.getString("PKCOLUMN_NAME");//主键列名    
                String fkTableCat = rs.getString("FKTABLE_CAT");//外键的表的目录（可能为空）出口（可能为null）  
                String fkTableSchem = rs.getString("FKTABLE_SCHEM");//外键表的架构（可能为空）出口（可能为空）  
                String fkTableName = rs.getString("FKTABLE_NAME");//外键表名  
                String fkColumnName = rs.getString("FKCOLUMN_NAME"); //外键列名                  
                short keySeq = rs.getShort("KEY_SEQ");//序列号（外键内值1表示第一列的外键，值2代表在第二列的外键）。  
                  
                /** 
                 * hat happens to foreign key when primary is updated:  
                 * importedNoAction - do not allow update of primary key if it has been imported 
                 * importedKeyCascade - change imported key to agree with primary key update  
                 * importedKeySetNull - change imported key to NULL if its primary key has been updated 
                 * importedKeySetDefault - change imported key to default values if its primary key has been updated 
                 * importedKeyRestrict - same as importedKeyNoAction (for ODBC 2.x compatibility)    
                 */  
                short updateRule = rs.getShort("UPDATE_RULE");  
                  
                /** 
                 * What happens to the foreign key when primary is deleted. 
                 * importedKeyNoAction - do not allow delete of primary key if it has been imported 
                 * importedKeyCascade - delete rows that import a deleted key  
                 * importedKeySetNull - change imported key to NULL if its primary key has been deleted  
                 * importedKeyRestrict - same as importedKeyNoAction (for ODBC 2.x compatibility) 
                 * importedKeySetDefault - change imported key to default if its primary key has been deleted    
                 */  
                short delRule = rs.getShort("DELETE_RULE");  
                String fkName = rs.getString("FK_NAME");//外键的名称（可能为空）  
                String pkName = rs.getString("PK_NAME");//主键的名称（可能为空）  
                  
                /** 
                 * can the evaluation of foreign key constraints be deferred until commit 
                 * importedKeyInitiallyDeferred - see SQL92 for definition 
                 * importedKeyInitiallyImmediate - see SQL92 for definition  
                 * importedKeyNotDeferrable - see SQL92 for definition    
                 */  
                short deferRability = rs.getShort("DEFERRABILITY");  
                  
                System.out.println(pkTableCat + "-" + pkTableSchem + "-" + pkTableName + "-" + pkColumnName + "-"    
                        + fkTableCat + "-" + fkTableSchem + "-" + fkTableName + "-" + fkColumnName + "-" + keySeq + "-"    
                        + updateRule + "-" + delRule + "-" + fkName + "-" + pkName + "-" + deferRability);     
            }  
        } catch (SQLException e){  
            e.printStackTrace();     
        }  
    }  
  

	/**
	 * 支持批量处理delete update insert
	 * 
	 * @param sqls
	 * @return
	 */
	public int supportsBatch(Object[] sqls) {
		try {
			conn = DBHelper.getInstance().getConnection();
			conn.setAutoCommit(false);
			DatabaseMetaData dma = conn.getMetaData();
			if (dma.supportsBatchUpdates()) {
				int bufferSize = 0;
				stmt = conn.createStatement();
				for (int i = 0; i < sqls.length; i++) {
					bufferSize++;
					if ((bufferSize + 1) % 100 == 0) {
						stmt.addBatch(sqls[i].toString());
						conn.commit();
						stmt.clearBatch();
					}
				}
				int[] rows = stmt.executeBatch();
				conn.commit();
				return rows.length;
			} else {
				return sqls.length;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ReleaseUtils.releaseAll(null, stmt, conn);
		}
		return 0;
	}

	 
	/**
	 * 执行(增删改)
	 * 
	 * @param sql
	 * @param args
	 * @return boolean
	 */
	public static boolean executeUpdates(String sql, Object[] args) {
		boolean sign = false;
		Connection conn = null;
		PreparedStatement state = null;
		try {
			conn = DBHelper.getInstance().getConnection();
			state = conn.prepareStatement(sql);
			if (args != null && args.length > 0) {
				for (int i = 0; i < args.length; i++) {
					state.setObject(i + 1, args[i]);
				}
			}
			int rows = state.executeUpdate();
			if (rows > 0){
				sign = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ReleaseUtils.releaseAll(null, state, conn);
		}
		return sign;
	}

	public static Integer executeUpdatex(String sql, Object[] args) {
		Connection conn = null;
		PreparedStatement state = null;
		try {
			conn = DBHelper.getInstance().getConnection();
			state = conn.prepareStatement(sql);
			if (args != null && args.length > 0) {
				for (int i = 0; i < args.length; i++) {
					state.setObject(i + 1, args[i]);
				}
			}
			int rows = state.executeUpdate();
			if (rows > 0){
				return rows;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ReleaseUtils.releaseAll(null, state, conn);
		}
		return 0;
	}

	
	
	 /**
     * 用于查询，返回结果集
     *
     * @param sql
     *            sql语句
     * @return 结果集
     * @throws SQLException
     */
    public static List query(String sql) throws SQLException {
        ResultSet rs = null;
        try {
            getPreparedStatement(sql);
            rs = preparedStatement.executeQuery();
            return ResultToListMap(rs);
        } catch (SQLException e) {
            throw new SQLException(e);
        } finally {
        	ReleaseUtils.free(rs);
        }
    }
 
    /**
     * 用于带参数的查询，返回结果集
     *
     * @param sql
     *            sql语句
     * @param paramters
     *            参数集合
     * @return 结果集
     * @throws SQLException
     */
    @SuppressWarnings("rawtypes")
    public static List query(String sql, Object... paramters) throws SQLException {
        ResultSet rs = null;
        try {
            getPreparedStatement(sql);
            for (int i = 0; i < paramters.length; i++) {
                preparedStatement.setObject(i + 1, paramters[i]);
            }
            rs = preparedStatement.executeQuery();
            return ResultToListMap(rs);
        } catch (SQLException e) {
            throw new SQLException(e);
        } finally {
        	ReleaseUtils.free(rs);
        }
    }
 
    /**
     * 返回单个结果的值，如count\min\max等等
     *
     * @param sql
     *            sql语句
     * @return 结果集
     * @throws SQLException
     */
    public static Object getSingle(String sql) throws SQLException {
        Object result = null;
        ResultSet rs = null;
        try {
            getPreparedStatement(sql);
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                result = rs.getObject(1);
            }
            return result;
        } catch (SQLException e) {
            throw new SQLException(e);
        } finally {
        	ReleaseUtils.free(rs);
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
    public static void release() {
        release(null);
    }
    /**
     * 释放资源
     * @param rs
     *            结果集
     */
    public static void release(ResultSet rs) {
    	release(conn, preparedStatement, rs);
    }
    /**
     * 释放连接
     * @param conn
     */
    private static void releaseConnection(Connection conn) {
        try {
            conn.close();
            conn = null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
 
    /**
     * 释放statement
     * @param statement
     */
    private static void releaseStatement(Statement statement) {
        try {
            statement.close();
            statement = null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
 
    /**
     * 释放resultset
     * @param rs
     */
    private static void releaseResultSet(ResultSet rs) {
        try {
            rs.close();
            rs= null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
 
    /**
     * 释放资源,节省空间
     * @param conn
     * @param statement
     * @param rs
     */
    public static void release(Connection conn, Statement statement, ResultSet rs) {
        if (rs != null) {
            releaseResultSet(rs);
        }
        if (statement != null) {
            releaseStatement(statement);
        }
        if (conn != null) {
            releaseConnection(conn);
        }
    }
    /**
     * 返回单个结果值，如count\min\max等
     *
     * @param sql
     *            sql语句
     * @param paramters
     *            参数列表
     * @return 结果
     * @throws SQLException
     */
    public static Object getSingle(String sql, Object... paramters) throws SQLException {
        Object result = null;
        ResultSet rs = null;
        try {
            getPreparedStatement(sql);
            for (int i = 0; i < paramters.length; i++) {
                preparedStatement.setObject(i + 1, paramters[i]);
            }
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                result = rs.getObject(1);
            }
            return result;
        } catch (SQLException e) {
            throw new SQLException(e);
        } finally {
        	ReleaseUtils.free(rs);
        }
    }
 
    /**
     * 用于增删改
     *
     * @param sql
     *            sql语句
     * @return 影响行数
     * @throws SQLException
     */
    public static int update(String sql) throws SQLException {
        try {
            getPreparedStatement(sql);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        } finally {
        	ReleaseUtils.free();
        }
    }
 
    /**
     * 用于增删改（带参数）
     *
     * @param sql
     *            sql语句
     * @param paramters
     *            sql语句
     * @return 影响行数
     * @throws SQLException
     */
    public static int update(String sql, Object... paramters) throws SQLException {
        try {
            getPreparedStatement(sql);
            for (int i = 0; i < paramters.length; i++) {
                preparedStatement.setObject(i + 1, paramters[i]);
            }
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        } finally {
        	ReleaseUtils.free();
        }
    }
 
    /**
     * 插入值后返回主键值
     *
     * @param sql
     *            插入sql语句
     * @return 返回结果
     * @throws Exception
     */
    public static Object insertWithReturnPrimeKey(String sql) throws SQLException {
        ResultSet rs = null;
        Object result = null;
        try {
        	conn = DBHelper.getInstance().getConnection();
            preparedStatement = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.execute();
            rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                result = rs.getObject(1);
            }
            return result;
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }
 
    /**
     * 插入值后返回主键值
     *
     * @param sql
     *            插入sql语句
     * @param paramters
     *            参数列表
     * @return 返回结果
     * @throws SQLException
     */
    public static Object insertWithReturnPrimeKey(String sql, Object... paramters) throws SQLException {
        ResultSet rs = null;
        Object result = null;
        try {
        	conn = DBHelper.getInstance().getConnection();
            preparedStatement = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            for (int i = 0; i < paramters.length; i++) {
                preparedStatement.setObject(i + 1, paramters[i]);
            }
            preparedStatement.execute();
            rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                result = rs.getObject(1);
            }
            return result;
        } catch (SQLException e) {
            throw new SQLException(e);
        }
 
    }
 
    /**
     * 调用存储过程执行查询
     *
     * @param procedureSql
     *            存储过程
     * @return
     * @throws SQLException
     */
    public static List callableQuery(String procedureSql) throws SQLException {
        ResultSet rs = null;
        try {
            getCallableStatement(procedureSql);
            rs = callableStatement.executeQuery();
            return ResultToListMap(rs);
        } catch (SQLException e) {
            throw new SQLException(e);
        } finally {
        	ReleaseUtils.free(rs);
        }
    }
 
    /**
     * 调用存储过程（带参数）,执行查询
     *
     * @param procedureSql
     *            存储过程
     * @param paramters
     *            参数表
     * @return
     * @throws SQLException
     */
    @SuppressWarnings("rawtypes")
    public static List callableQuery(String procedureSql, Object... paramters) throws SQLException {
        ResultSet rs = null;
        try {
            getCallableStatement(procedureSql);
            for (int i = 0; i < paramters.length; i++) {
                callableStatement.setObject(i + 1, paramters[i]);
            }
            rs = callableStatement.executeQuery();
            return ResultToListMap(rs);
        } catch (SQLException e) {
            throw new SQLException(e);
        } finally {
        	ReleaseUtils.free(rs);
        }
    }
 
    /**
     * 调用存储过程，查询单个值
     * @param procedureSql
     * @return
     * @throws SQLException
     */
    public static Object callableGetSingle(String procedureSql) throws SQLException {
        Object result = null;
        ResultSet rs = null;
        try {
            getCallableStatement(procedureSql);
            rs = callableStatement.executeQuery();
            while (rs.next()) {
                result = rs.getObject(1);
            }
            return result;
        } catch (SQLException e) {
            throw new SQLException(e);
        } finally {
        	ReleaseUtils.free(rs);
        }
    }
 
    /**
     * 调用存储过程(带参数)，查询单个值
     * @param procedureSql
     * @param parameters
     * @return
     * @throws SQLException
     */
    public static Object callableGetSingle(String procedureSql, Object... paramters) throws SQLException {
        Object result = null;
        ResultSet rs = null;
        try {
            getCallableStatement(procedureSql);
            for (int i = 0; i < paramters.length; i++) {
                callableStatement.setObject(i + 1, paramters[i]);
            }
            rs = callableStatement.executeQuery();
            while (rs.next()) {
                result = rs.getObject(1);
            }
            return result;
        } catch (SQLException e) {
            throw new SQLException(e);
        } finally {
        	ReleaseUtils.free(rs);
        }
    }
 
    public static Object callableWithParamters(String procedureSql) throws SQLException {
        try {
            getCallableStatement(procedureSql);
            callableStatement.registerOutParameter(0, Types.OTHER);
            callableStatement.execute();
            return callableStatement.getObject(0);
        } catch (SQLException e) {
            throw new SQLException(e);
        } finally {
        	ReleaseUtils.free();
        }
    }
 
    /**
     * 调用存储过程，执行增删改
     * @param procedureSql
     *            存储过程
     * @return 影响行数
     * @throws SQLException
     */
    public static int callableUpdate(String procedureSql) throws SQLException {
        try {
            getCallableStatement(procedureSql);
            return callableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        } finally {
        	ReleaseUtils.free();
        }
    }
 
    /**
     * 调用存储过程（带参数），执行增删改
     * @param procedureSql
     *            存储过程
     * @param parameters
     * @return 影响行数
     * @throws SQLException
     */
    public static int callableUpdate(String procedureSql, Object... parameters) throws SQLException {
        try {
            getCallableStatement(procedureSql);
            for (int i = 0; i < parameters.length; i++) {
                callableStatement.setObject(i + 1, parameters[i]);
            }
            return callableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        } finally {
        	ReleaseUtils.free();
        }
    }
 
    /**
     * 批量更新数据
     * @param sqlList
     *            一组sql
     * @return
     */
    public static int[] batchUpdate(List<String> sqlList) {
        int[] result = new int[] {};
        Statement statenent = null;
        try {
        	conn = DBHelper.getInstance().getConnection();
            conn.setAutoCommit(false);
            statenent = conn.createStatement();
            for (String sql : sqlList) {
                statenent.addBatch(sql);
            }
            result = statenent.executeBatch();
            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                throw new ExceptionInInitializerError(e1);
            }
            throw new ExceptionInInitializerError(e);
        } finally {
        	ReleaseUtils.free(statenent, null);
        }
        return result;
    }
 
    private static List ResultToListMap(ResultSet rs) throws SQLException {
        List list = new ArrayList();
        while (rs.next()) {
            ResultSetMetaData md = rs.getMetaData();
            Map map = new HashMap();
            for (int i = 1; i <= md.getColumnCount(); i++) {
                map.put(md.getColumnLabel(i), rs.getObject(i));
            }
            list.add(map);
        }
        return list;
    }
 
    /**
     * 获取PreparedStatement
     * @param sql
     * @throws SQLException
     */
    private static void getPreparedStatement(String sql) throws SQLException {
    	conn = DBHelper.getInstance().getConnection();
        preparedStatement = conn.prepareStatement(sql);
    }
 
    /**
     * 获取CallableStatement
     * @param procedureSql
     * @throws SQLException
     */
    private static void getCallableStatement(String procedureSql) throws SQLException {
    	boolean isServerFlag=DBHelper.isServerFlag;
    	conn = DBHelper.getInstance().getConnection();
        callableStatement = conn.prepareCall(procedureSql);
    }
  
    
    
    
    /**
	 * 关闭连接
	 * @param conn
	 * @param statement
	 * @param rs
	 */
	public void close(Connection conn,Statement statement,ResultSet rs){
		try{
			if(rs!=null){
				rs.close();
			}if(statement!=null){
				statement.close();
			}if(conn!=null){
				conn.close();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取结果集列数
	 * @param rs
	 * @return
	 */
	public int findMaxCount(ResultSet rs){
		ResultSetMetaData rsmd;
		try {
			rsmd = rs.getMetaData();
			return rsmd.getColumnCount();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return 0;
	}
	
	/**
	 * 返回一维数组，不管结果集是一条还是多条数据，只返回第一条
	 * @param sql
	 * @return String[]
	 */
	public String[] findOneArray(String sql) {
		String[] strs = null;
		try {
			if (conn == null) {
				try {
					loadConnection();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			int columnCount = findMaxCount(rs);
			strs = new String[columnCount];
			if (rs.next()) {
				for (int i = 1; i <= columnCount; i++) {
					strs[i - 1] = rs.getString(i);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, stmt, rs);
		}
		return strs;
	}
	
	/**
	 * 返回二维数组
	 * @param sql
	 * @return String[][]
	 */
	public String[][] findTwoArray(String sql) {
		String[][] strs = null;
		try {
			if (conn == null) {
				try {
					loadConnection();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			// 创建结果集可以移动的statement对象,Concurrency并发resultSetConcurrency
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			// 执行sql
			rs = stmt.executeQuery(sql);
			// 得到列数
			int columnCount = findMaxCount(rs);
			// 将结果集游标移到最后
			rs.last();
			// 获取行数
			int length = rs.getRow();
			// 根据查询结果行数和列数创建二维数组
			strs = new String[length][columnCount];
			// 将结果集游标移到最前面
			rs.beforeFirst();
			// 将结果集封装到数组
			for (int j = 0; j < length; j++) {
				if (rs.next()) {
					for (int i = 1; i <= columnCount; i++) {
						strs[j][i - 1] = rs.getString(i);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
		}
		return strs;
	}
	
	/**
	 * 返回list，list里面装字符数组
	 * @param sql
	 * @return
	 */
	public List<String[]> findList(String sql) {
		List<String[]> list = new ArrayList<String[]>();
		String[] strs = null;
		try {
			if(conn==null){
				try {
					loadConnection();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			stmt = conn.createStatement();
			//执行sql
			rs = stmt.executeQuery(sql);
			//得到列数
			int columnCount = findMaxCount(rs);
			strs = new String[columnCount];
			 //将结果集封装到list
				 while(rs.next()){
					 for(int i=1;i<=columnCount;i++){
						 strs[i-1] = rs.getString(i);
					 }
					 list.add(strs);
					 strs = new String[columnCount];
				 }
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
		}
		return list;
	}
	/**
	 * 返回一条数据，数据格式为map直接通过数据库字段名获取值
	 * @param sql
	 * @return
	 * 格式 {p_name=多琳纳, ppid=24}
	 */
	public Map<String, String> findOneMap(String sql) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			if(conn==null){
				try {
					loadConnection();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			int columnCount = findMaxCount(rs);
			ResultSetMetaData metaData = rs.getMetaData();
			if(rs.next()){
				for(int i=1;i<=columnCount;i++){
					map.put(metaData.getColumnName(i), rs.getString(i));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
		}
		return map;
	}
	
	/**
	 * 返回list，list里面装map数据
	 * @param sql
	 * @return
	 */
	public List<Map<String,String>> findListMap(String sql) {
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		Map<String, String> map = null;
		try {
			if(conn==null){
				try {
					loadConnection();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			int columnCount = findMaxCount(rs);
			ResultSetMetaData metaData = rs.getMetaData();
			while(rs.next()){
				map = new HashMap<String, String>();
				for(int i=1;i<=columnCount;i++){
					map.put(metaData.getColumnName(i), rs.getString(i));
				}
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
		}
		return list;
	}
	
	public <T> List<T> findEntity(Class<T> t,String sql){
		List<T> list = new ArrayList<T>();
		Object obj = null;
		//将实体类set方法名和类型存起来
		 Field[] field = t.getDeclaredFields();
	        for (int i = 0; i < field.length; i++) {
	            // 权限修饰符
	            // 属性类型
	            Class<?> type = field[i].getType();
	            columnTypeMap.put(field[i].getName(), type.getName());
	        }
		try {
			if(conn==null){
				try {
					loadConnection();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			int columnCount = findMaxCount(rs);
			ResultSetMetaData metaData = rs.getMetaData();
			
			String columnName = "";
			String setName = "";
			//	Class<?> type = null;
			while(rs.next()){
				obj = t.newInstance();
				for(int i = 1;i<=columnCount;i++){
					//得到数据库字段名
					columnName = metaData.getColumnName(i);
					//拼接set方法字符串
					setName = "set" + columnName.substring(0, 1).toUpperCase()+columnName.substring(1, columnName.length());
					//		type = field[i-1].getType();
					//将数据库的字段值封装到对象里
					doSetColumn(obj,setName,columnName,rs.getString(i));
				}
				
				list.add((T)obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
		}
		return list;
	}
	/**
	 * 封装,暂时只做了int和String类型的处理，其他的需要再写上
	 * @param obj
	 * @param setName
	 * @param type
	 * @param v
	 */
	private void doSetColumn(Object obj, String setName,String columnName,String v) {
		Method method;
		String tp = columnTypeMap.get(columnName);
		try {
			if("int".equals(tp)){
				method = obj.getClass().getMethod(setName,int.class);
				method.invoke(obj,Integer.valueOf(v));
			}else if("java.lang.Integer".equals(tp)){
				method = obj.getClass().getMethod(setName,Integer.class);
				method.invoke(obj,v);
			}else if("java.lang.String".equals(tp)){
				method = obj.getClass().getMethod(setName,String.class);
				method.invoke(obj,v);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
    public static void main(String[] args) throws Exception {
        /*DBHelperUtils metaData = new DBHelperUtils();  
        metaData.getDataBaseInformations();  
        metaData.getAllTableList(null);  
        metaData.getAllViewList(null);  
        metaData.getAllSchemas();  
        metaData.getTableColumns(null, "test");  
        metaData.getIndexInfo(null, "test");  
        metaData.getAllPrimaryKeys(null, "test");  
        metaData.getAllExportedKeys(null, "test");*/  
        
    	List<String> dataNames = getDataNames();
    	
    	for(String db:dataNames){
    		System.out.println(db);
    	}
        
	}
	
	
}
