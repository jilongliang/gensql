package com.flong.commons.utils;

import java.net.InetAddress;
import java.util.Date;
import java.util.UUID;
/**
 * @description:系列工具---随机数.....
 */
public class GeneratorUtils {
	private static final int IP;
	private static short counter = (short) 0;

	private static final int JVM = (int) (System.currentTimeMillis() >>> 8);
	static {
		int ipadd;
		try {
			ipadd = toInt(InetAddress.getLocalHost().getAddress());
		} catch (Exception e) {
			ipadd = 0;
		}
		IP = ipadd;
	}

	

	/**
	 * 根据设一个大于0的数字或必须(<=43)即1~43
	 * 去生成一个UUID
	 * @return
	 */
	public static String generateUUID(Integer number) {
		String rtnVal ="";
		if(number<=43){
			rtnVal=Long.toHexString(System.currentTimeMillis());
			rtnVal = rtnVal + UUID.randomUUID();
			rtnVal = rtnVal.replaceAll("-", "");
			rtnVal=rtnVal.substring(0, number);
		}else{
			rtnVal="This is "+number+" Number Too Big";
		}
		return rtnVal;
	}

	/**
	 * 获取一个UUID
	 * @return
	 */
	public static String getUUID() {
		String s = java.util.UUID.randomUUID().toString();
		return s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18)
				+ s.substring(19, 23) + s.substring(24);
	}
	/**
	 * 产生一个32位的UUID
	 * @return
	 */

	public static String generate() {
		StringBuilder buff=new StringBuilder();
		buff.append(format(getIP()));
		buff.append(format(getJVM()));
		buff.append(format(getHiTime()));
		buff.append(format(getLoTime()));
		buff.append(format(getCount()));
		return buff.toString();
	}

	
	/**
	 * 格式
	 * @param intval
	 * @return
	 */
	private final static String format(int intval) {
		String formatted = Integer.toHexString(intval);
		StringBuilder buf = new StringBuilder("00000000");
		buf.replace(8 - formatted.length(), 8, formatted);
		return buf.toString();
	}

	private final static String format(short shortval) {
		String formatted = Integer.toHexString(shortval);
		StringBuilder buf = new StringBuilder("0000");
		buf.replace(4 - formatted.length(), 4, formatted);
		return buf.toString();
	}

	private final static int getJVM() {
		return JVM;
	}

	private final static short getCount() {
		synchronized (GeneratorUtils.class) {
			if (counter < 0)
				counter = 0;
			return counter++;
		}
	}

	/**
	 * Unique in a local network
	 */
	private final static int getIP() {
		return IP;
	}

	/**
	 * Unique down to millisecond
	 */
	private final static short getHiTime() {
		return (short) (System.currentTimeMillis() >>> 32);
	}

	private final static int getLoTime() {
		return (int) System.currentTimeMillis();
	}

	private final static int toInt(byte[] bytes) {
		int result = 0;
		for (int i = 0; i < 4; i++) {
			result = (result << 8) - Byte.MIN_VALUE + (int) bytes[i];
		}
		return result;
	}

	/**
	 * 生成主键32位）
	 * 
	 * @return
	 */
	public static String generateID() {
		String rtnVal = Long.toHexString(System.currentTimeMillis());
		rtnVal += UUID.randomUUID();
		rtnVal = rtnVal.replaceAll("-", "");
		return rtnVal.substring(0, 32);
	}
	
	/**
	 * 生成long类型的ID
	 * 尽量不要使用
	 * @return
	 */
	public static synchronized long generateLongID(){
		long val=System.currentTimeMillis();
		try {
			Thread.currentThread().sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return val;
	}

	/**
	 * 根据指定时间生成主键，该方法只能用来对比主键生成时间，切忌不能用来生成主键插入数据库
	 * 
	 * 
	 * 
	 * @param date
	 *            时间
	 * @return
	 */
	public static String generateID(Date date) {
		String rtnVal = Long.toHexString(date.getTime());
		rtnVal += UUID.randomUUID();
		rtnVal = rtnVal.replaceAll("-", "");
		return rtnVal.substring(0, 32);
	}

	/**
	 * 查看主键生成时间
	 * 
	 * @param id
	 */
	private static void printIDTime(String id) {
		String timeInfo = id.substring(0, 11);
		System.out.println(new Date(Long.parseLong(timeInfo, 16)));
	}

	/**
	 * 根据ID获取该ID创建的时间
	 * 
	 * @author eric
	 * @param id
	 * @return
	 */
	public static Date getIDCreateTime(String id) {
		String timeInfo = id.substring(0, 11);
		return new Date(Long.parseLong(timeInfo, 16));
	}

	
	/**
	 * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
	 */
	public static String uuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	 
	
	
	public static void main(String[] args) {

		System.out.println(generateUUID(36));
		// System.out.println(generateID());
		printIDTime("11fca36d08f032abb30c15642aa89af2");
		printIDTime("11fca36cfa57a4d6a3eea204146b9db7");
		System.out.println(getIDCreateTime("11fca36cfa57a4d6a3eea204146b9db7"));
	}

}