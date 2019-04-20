package com.flong.codegenerator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class FileUtils {
	/**
	 * 把生成的文件都保存.
	 * @param path
	 * @param data
	 */
	public static void save(String path, String data) {
		try {
			File file = new File(path);
			File dir = new File(path.substring(0, path.lastIndexOf("/")));
			if(!dir.exists()) {
				dir.mkdirs();
			}
			FileWriter out = new FileWriter(file);
			out.write(data);
			out.flush();
			out.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 读文件流
	 * 
	 * @param formPath从哪里读取的文件路径
	 * @return
	 */
	public static String reader(String formPath) {
		String content="";
		FileReader read = null;
		BufferedReader reader = null;
		try {
			read = new FileReader(new File(formPath));
			reader = new BufferedReader(read);
			StringBuffer buffer = new StringBuffer("");
			content = reader.readLine();
			while (content != null) {
				buffer.append(content).append("\n");
				content = reader.readLine();
				
			}
			return content = buffer.toString();// 返回
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null)
					reader.close();
				if (read != null)
					read.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "";// 没值就返回空
	}
	
	/**
	 * 包含的字符就跳出程序.
	 * @param formPath
	 * @param containStr
	 * @return
	 */
	public static String reader1(String formPath,String containStr) {
		String content="";
		FileReader read = null;
		BufferedReader reader = null;
		try {
			read = new FileReader(new File(formPath));
			reader = new BufferedReader(read);
			StringBuffer buffer = new StringBuffer("");
			content = reader.readLine();
			while (content != null) {
				buffer.append(content).append("\n");
				content = reader.readLine();
				if(content.contains(containStr)){
					break;
				}
				
			}
			return content = buffer.toString();// 返回
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null)
					reader.close();
				if (read != null)
					read.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "";// 没值就返回空
	}
}
