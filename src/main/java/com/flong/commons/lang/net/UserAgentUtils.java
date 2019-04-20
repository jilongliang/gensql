package com.flong.commons.lang.net;


/**
 *@Date:2015-1-8
 *@Author:liangjilong
 *@Email:jilongliang@sina.com
 *@Version:1.0
 *@Description：代理工具类
 */
public class UserAgentUtils {
	/**
	 * 在Android下，返回QQ代理
	 */
	public static String getUser_Agent_Android(){
		StringBuffer buffer=new StringBuffer();
		buffer.append("Mozilla/5.0 (Linux; U; Android 2.3.6; zh-cn; GT-S5660 Build/GINGERBREAD) ");
		buffer.append(" AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1 MicroMessenger/4.5.255");
		return buffer.toString();
	}
	/**
	 * 在iPhone下，返回
	 */
	public static String getUser_Agent_iPhone(){
		StringBuffer buffer=new StringBuffer();
		buffer.append("Mozilla/5.0 (iPhone; CPU iPhone OS 5_1 like Mac OS X) ");
		buffer.append(" AppleWebKit/534.46 (KHTML, like Gecko) Mobile/9B176 MicroMessenger/4.3.2 ");
		return buffer.toString();
	}
	

	/**
	 * 在USER_AGENT_Mozilla4下，返回
	 * MSIE 7.0  微软的IE7
	 */
	public static String getUser_Agent_Mozilla4(){
		StringBuffer buffer=new StringBuffer();
		buffer.append("Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; GTB5; .NET CLR 1.1.4322; ");
		buffer.append(" .NET CLR 2.0.50727; Alexa Toolbar; MAXTHON 2.0) ");
		return buffer.toString();
	}
	/**
	 * 在USER_AGENT_Mozilla5下，返回
	 * 
	 */
	public static String getUser_Agent_Mozilla5(){
		StringBuffer buffer=new StringBuffer();
		buffer.append("Mozilla/5.0 (X11; U; Linux i686; zh-CN; rv:1.9.1.2) ");
		buffer.append(" Gecko/20090803 Fedora/3.5.2-2.fc11 Firefox/3.5.2 ");
		return buffer.toString();
	}
	/**
	 * getUser_Agent_Mozilla5_IE9
	 * Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0
	 * @return
	 */
	public static String getUser_Agent_Mozilla5_IE9(){
		StringBuffer buffer=new StringBuffer();
		buffer.append("Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0 ");
		return buffer.toString();
	}
	
	
}
