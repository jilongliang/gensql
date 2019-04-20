package com.flong.commons.persistence;
import java.io.BufferedReader;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 定义一sql脚本读取器
 * 创建日期：2013-1-7
 * @author niezhegang
 */
public class SqlScriptReader {
	/**实际读取器*/
	private BufferedReader innerReader = null;
	/**SQL语句分隔符*/
	private static final String SqlSeperator = ";";
	/**系统行分隔符*/
	private static final String SystemLineSeperator = getSystemLineSeperator();
	/**单行注释符*/
	private static final String SingleLineCommentSeperator = "--";
	/**多行注释符起始符*/
	private static final String MultiLineCommentSeperator_Begin = "/*";
	/**多行注释符结束符*/
	private static final String MultiLineCommentSeperator_End = "*/";
	/**已读取字符缓冲*/
	private StringBuilder sb = new StringBuilder();
	/**
	 * 构造方法
	 * @param reader
	 * 创建日期：2013-1-7
	 * 修改说明：
	 * @author niezhegang
	 */
	public SqlScriptReader(BufferedReader reader){
		innerReader = reader;
	}
	
	/**
	 * 关闭流
	 * @see java.io.Reader#close()
	 * 创建日期：2013-1-7
	 * 修改说明：
	 * @author niezhegang
	 */
	public void close() {
		if(innerReader != null){
			IOUtils.closeQuietly(innerReader);
			innerReader = null;
		}
	}
	
	/**
	 * 读取一行数据 
	 * 过滤掉空行和注释行
	 * @return
	 * 创建日期：2013-1-7
	 * 修改说明：
	 * @author niezhegang
	 */
	private String readLine() throws IOException{
		String ret = null;
		do {
			String lineData = innerReader.readLine();
			//读至文件内容结束
			if (lineData == null)
				break;
			lineData = StringUtils.trimToEmpty(lineData);
			//如果为空行或单行注释
			if (StringUtils.isBlank(lineData)
					|| StringUtils.startsWith(lineData,
							SingleLineCommentSeperator)){
				continue;
			}
			//如果为多行注释
			if(StringUtils.startsWith(lineData,
					MultiLineCommentSeperator_Begin)){
				//处理多行注释
				if(!StringUtils.endsWith(lineData, MultiLineCommentSeperator_End)){
					do{
						 lineData = innerReader.readLine();
						 if(lineData == null)
							 break;
						 lineData = StringUtils.trimToEmpty(lineData);
					}while(!StringUtils.endsWith(lineData, MultiLineCommentSeperator_End));
				}	
			}
			else{
				if(!endWithSeperator(lineData)){
					//前后添加空格
					ret = " " + lineData+SystemLineSeperator;
				}
				else
					ret = " " + lineData;
				break;
			}
		} while (true);
		return ret;
	}

	/**
	 * 读取一条sql语句
	 * @return
	 * @throws IOException
	 * 创建日期：2013-1-7
	 * 修改说明：
	 * @author niezhegang
	 */
	public String readOneSQL() throws IOException {
		String ret = null;
		do{
			//找到
			if(endWithSeperator(sb.toString())){
				ret = getSQLFrom(sb);
				break;
			}
			String lineData = readLine();
			//文件末尾
			if(lineData == null){
				if(sb.length() > 0){
					ret = sb.toString();
					sb.setLength(0);
				}
				break;
			}
			else{
				sb.append(lineData);
			}
		}while(true);
		return ret;
	}
	
	/**
	 * 判断是否以;结束
	 * @param sql
	 * @return
	 * 创建日期：2013-1-7
	 * 修改说明：
	 * @author niezhegang
	 */
	private boolean endWithSeperator(String sql){
		return StringUtils.endsWith(sql.toString(), SqlSeperator);
	}
	
	/**
	 * 从缓冲变量中获取语句内容
	 * @param sb
	 * @return
	 * 创建日期：2013-1-7
	 * 修改说明：
	 * @author niezhegang
	 */
	private String getSQLFrom(StringBuilder sb){
		String ret = sb.substring(0,sb.length() - 1) + SystemLineSeperator;
		sb.delete(0, sb.length() + SqlSeperator.length());
		return ret;
	}
	
	/**
	 * 获取系统行分隔符
	 * @return
	 * 创建日期：2013-1-8
	 * 修改说明：
	 * @author niezhegang
	 */
	public static String getSystemLineSeperator() {
		String sysLineSeperator = System.getProperty("line.separator");
		if (sysLineSeperator == null)
			sysLineSeperator = "\\r\\n";
		return sysLineSeperator;
	}

}
