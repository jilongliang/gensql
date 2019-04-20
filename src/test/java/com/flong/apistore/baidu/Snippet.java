package com.flong.apistore.baidu;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import com.flong.commons.lang.net.UserAgentUtils;

/**
 *@Author:liangjilong
 *@Date:2015年9月9日下午12:45:42
 *@Email:jilonglinag@sina.com
 */
@SuppressWarnings("all")
public class Snippet {
	//private static final String  USER_AGENT="Mozilla/5.0 (Linux; U; Android 2.3.6; zh-cn; GT-S5660 Build/GINGERBREAD) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1 MicroMessenger/4.5.255";
	//private static final String  USER_AGENT="Mozilla/5.0 (Linux; U; Android 2.2; en-us; Nexus One Build/FRF91) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1";
	private static final String  USER_AGENT=UserAgentUtils.getUser_Agent_Android();
	
	
	private static String ENCODING_UTF_8="utf-8";
	static String name="女网友忘关摄像头";
	

	//private static String pathUrl = "http://mmnews.net.cn/h5/lf/lifegame.htm?from=timeline&isappinstalled=0";
	//private static String pathUrl = "http://mmnews.net.cn/fruit/com/1/csg/index.htm?from=groupmessage&isappinstalled=0";
	//private static String pathUrl = "http://wpower.com.cn/?uc=532731&from=singlemessage&isappinstalled=0";
	//private static String pathUrl = "http://www.smhaocai.cn/fenxiangdaandaxue/"+getUrl(name)+".php";
	//private static String pathUrl = "http://s22.cnzz.com/stat.php?id=5871071&web_id=5871071";
	//private static String pathUrl = "http://mmnews.net.cn/moon/qq.com/payact/fi000001.1.htm";
	
	//private static String pathUrl = "http://tejia.gzxmt.cn/";
	//private static String pathUrl = "http://wx.weisft.com/youxi/tcbgy/bgy_sibi/index.aspx";
	//private static String pathUrl = "http://wx.weisft.com/dzzz/biguiyuan/wwfh/index.html";
	//private static String pathUrl = "http://wx.weisft.com/youxi/tcbgy/bgy_sibi/index.aspx?from=singlemessage&isappinstalled=0";
	
	private static String pathUrl = "http://mp.weixin.qq.com/s?__biz=MzAxNTI0ODE2MA==&mid=404260869&idx=1&sn=88b5d5ecf09d805b75f88736b5445c49&3rd=MzA3MDU4NTYzMw==&scene=6#rd";
	
	public static void main(String[] args)throws Exception {
		httpUrlConnection();
	}
	
	public static String getUrl(String name){
		
		String encode = URLEncoder.encode(name);
		return encode;
		
	}
	
	private static void httpUrlConnection() {
		try {
			// 建立连接
			URL url = new URL(pathUrl);
			HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();

			// //设置连接属性
			httpConn.setDoOutput(true);// 使用 URL 连接进行输出
			httpConn.setDoInput(true);// 使用 URL 连接进行输入
			httpConn.setUseCaches(false);// 忽略缓存
			httpConn.setRequestMethod("POST");// 设置URL请求方法
			String requestString = "客服端要以以流方式发送到服务端的数据...";

			// 设置请求属性
			// 获得数据字节数据，请求数据流的编码，必须和下面服务器端处理请求流的编码一致
			byte[] requestStringBytes = requestString.getBytes(ENCODING_UTF_8);
			httpConn.setRequestProperty("Content-length", ""+ requestStringBytes.length);
			httpConn.setRequestProperty("Content-Type","application/octet-stream");
			//httpConn.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
			httpConn.setRequestProperty("Charset", "UTF-8");
			httpConn.setRequestProperty("User-agent",USER_AGENT);

			//String name = URLEncoder.encode("黄武艺", "utf-8");
			//httpConn.setRequestProperty("NAME", name);

			// 建立输出流，并写入数据
			OutputStream outputStream = httpConn.getOutputStream();
			outputStream.write(requestStringBytes);
			outputStream.close();
			// 获得响应状态
			int responseCode = httpConn.getResponseCode();
			System.out.println(responseCode);
			if (HttpURLConnection.HTTP_OK == responseCode) {// 连接成功
				// 当正确响应时处理数据
				StringBuffer sb = new StringBuffer();
				String readLine;
				BufferedReader responseReader;
				// 处理响应流，必须与服务器响应流输出的编码一致
				responseReader = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), ENCODING_UTF_8));
				while ((readLine = responseReader.readLine()) != null) {
					sb.append(readLine).append("\n");
				}
				
				System.out.println(sb.toString());
				responseReader.close();
				//tv.setText(sb.toString());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

}
