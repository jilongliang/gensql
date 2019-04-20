package com.flong.apistore.baidu;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.alibaba.fastjson.JSONObject;

/**
 * @Author:liangjilong
 * @Date:2015年10月30日-下午2:45:07
 * @Email:jilonglinag@sina.com
 * @Version:1.0
 * @Description:http://apistore.baidu.com/
 */
public class Meinv {
	
	public static void main(String[] args) {
		String httpUrl = "http://apis.baidu.com/txapi/mvtp/meinv";
		String jsonResult ="";
		/*for (int i = 0; i < 10; i++) {
			String httpArg = "num="+i;
			 jsonResult += request(httpUrl, httpArg);
			 
			 JSONObject json=JSONObject.parseObject(jsonResult);
			 
		}*/
		
		String httpArg = "num="+2;
		jsonResult = request(httpUrl, httpArg);
		System.out.println(jsonResult);
		 JSONObject json=JSONObject.parseObject(jsonResult);
		 
		 
		 String code = json.get("0").toString();
		
		System.out.println(code);
	}


	
	/**
	 * @param urlAll
	 *            :请求接口
	 * @param httpArg
	 *            :参数
	 * @return 返回结果
	 */
	public static String request(String httpUrl, String httpArg) {
	    BufferedReader reader = null;
	    String result = null;
	    StringBuffer sbf = new StringBuffer();
	    httpUrl = httpUrl + "?" + httpArg;
	
	    try {
	        URL url = new URL(httpUrl);
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setRequestMethod("GET");
	        // 填入apikey到HTTP header
	        connection.setRequestProperty("apikey",  "你的apikey");
	        connection.connect();
	        InputStream is = connection.getInputStream();
	        reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
	        String strRead = null;
	        while ((strRead = reader.readLine()) != null) {
	            sbf.append(strRead);
	            sbf.append("\r\n");
	        }
	        reader.close();
	        result = sbf.toString();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return result;
	}

}
