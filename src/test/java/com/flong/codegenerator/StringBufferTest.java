package com.flong.codegenerator;
/***
 *@Author:liangjilong
 *@Date:2016年1月4日下午3:11:06
 *@Email:jilongliang@sina.com
 *@Version:1.0
 *@CopyRight(c)liangjilong
 *@Description:
 */
public class StringBufferTest {
	public static void main(String[] args) {
		
		StringBuilder sb = new StringBuilder();
		StringBuffer sb1 =new StringBuffer();
		
		sb.append(" A,B,C,D,");
		sb1.append(" A,B,C,D,");
		
		sb.deleteCharAt(sb.length()-1);
		sb1.deleteCharAt(sb1.length()-1);
		
		System.out.println(sb.toString());
		System.out.println(sb1.toString());
		
	}
}
