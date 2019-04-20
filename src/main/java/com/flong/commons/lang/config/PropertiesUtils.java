package com.flong.commons.lang.config;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

/**
 * 处理properties文件的类
 * @date 2013-4-1
 */
public class PropertiesUtils {

	/**
	 * 读取properties文件中的值
	 * @param key properties 文件中要查找的key值
	 * @param propertiesFileName properties文件的完整名称
	 * @return 查找到的value值
	 * @throws IOException 
	 */
	public static String getPropertiesValue(String key,String propertiesFileName) throws IOException{
		Properties pop = new Properties();
		pop.load(PropertiesUtils.class.getClassLoader().getResourceAsStream(propertiesFileName));
		return pop.getProperty(key);
	}
	
	/**
	 * 将文件中的斜杠换成系统认定的文件分割符
	 * @param path
	 * @return
	 */
	public static String getCorrectPath(String path){
		//将正反斜杠全部换成系统认定的文件分割符
		path = StringUtils.join(path.split("/"),File.separator);
		path = StringUtils.join(path.split("\\\\"),File.separator);
		return path;
	}
}
