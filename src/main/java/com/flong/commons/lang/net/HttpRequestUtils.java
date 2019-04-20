package com.flong.commons.lang.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *@Author:jilongliang
 *@Email:jilongliang@sina.com
 *@Date:2015-2-23
 *@CopyRight:liangjilong
 *@Description:
 */
@SuppressWarnings("all")
public class HttpRequestUtils {
	private static String ENCODING_UTF_8="utf-8";
	
	/**
	 * 发送http请求
	 * POST和GET请求都可以
	 * @param requestUrl 请求地址
	 * @param method传入的执行的方式 是GET或POST方式
	 * @return String
	 */
	public static String HttpURLConnRequest(String requestUrl,String method) {
		StringBuffer buffer = new StringBuffer();
		try {
			URL url = new URL(requestUrl);
			HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();
			httpUrlConn.setDoInput(true);
			httpUrlConn.setRequestMethod(method);
			httpUrlConn.setUseCaches(false);  
			httpUrlConn.setInstanceFollowRedirects(true); //重定向
			httpUrlConn.connect();
			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}
	  
	/**
	 * 获取文件流
	 * @param buffer
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	private static String getStreamReader(StringBuffer buffer, InputStream inputStream)throws IOException {
		String content;
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		String str = null;
		while ((str = bufferedReader.readLine()) != null) {
			buffer.append(str);
			buffer.append("\n");
		}
		content=buffer.toString();
		// 释放资源
		bufferedReader.close();
		inputStreamReader.close();
		inputStream.close();
		inputStream = null;
		return content;
	}
	
	
	/***
	 * @param requestParams这个参数必须是有三个0是requestUrl 1是requestString 2是代表requestMethod
	 * 3是contentType，4是userAgent
	 * @return
	 */
	private static String httpUrlConnection(String ...requestParams) {
		String result="";
		String requestUrl=requestParams[0],  requestString=requestParams[1], requestMethod=requestParams[2];
		String contentType=requestParams[3], userAgent=requestParams[4];
		try {
			// 建立连接
			URL url = new URL(requestUrl);
			HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();

			// //设置连接属性
			httpConn.setDoOutput(true);// 使用 URL 连接进行输出
			httpConn.setDoInput(true);// 使用 URL 连接进行输入
			httpConn.setUseCaches(false);// 忽略缓存
			httpConn.setRequestMethod(requestMethod);// 设置URL请求方法

			// 设置请求属性
			// 获得数据字节数据，请求数据流的编码，必须和下面服务器端处理请求流的编码一致
			byte[] requestStringBytes = requestString.getBytes(ENCODING_UTF_8);
			httpConn=setRequestProperty(httpConn, requestStringBytes, contentType, userAgent);
			
			//String name = URLEncoder.encode("黄武艺", "utf-8");
			//httpConn.setRequestProperty("NAME", name);

			// 建立输出流，并写入数据
			OutputStream outputStream = httpConn.getOutputStream();
			outputStream.write(requestStringBytes);
			outputStream.close();
			// 获得响应状态
			int responseCode = httpConn.getResponseCode();

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
				
				result=sb.toString();
				responseReader.close();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}
	
	/***
	 * @param httpConn
	 * @param requestStringBytes
	 * @param contentType参考web-mime-mapping.xml配置（application/octet-stream,application/json......）
	 * @param userAgent代理可以参考代理userAgent.jar
	 * @return
	 */
	private static HttpURLConnection setRequestProperty(HttpURLConnection httpConn, byte[] requestStringBytes,String contentType,String userAgent) {
		
		httpConn.setRequestProperty("Content-length", ""+ requestStringBytes.length);
		httpConn.setRequestProperty("Content-Type",contentType);
		httpConn.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
		httpConn.setRequestProperty("Charset", ENCODING_UTF_8);
		httpConn.setRequestProperty("User-agent",userAgent);
		return httpConn;
	}
}
