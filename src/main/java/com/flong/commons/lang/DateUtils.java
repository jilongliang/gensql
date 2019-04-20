package com.flong.commons.lang;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.log4j.Logger;


/**
 * 日期工具类, 继承org.apache.commons.lang.time.DateUtils类
 * @author liangjilong
 * @version 2013-3-15
 */
@SuppressWarnings("all")
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
	private static Logger log = Logger.getLogger(DateUtils.class);
	
	private static String[] parsePatterns = { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss" };
	public static final String YYYYMMDD = "yyyy-MM-dd";
	public static final String YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";
	
	private static Pattern p = Pattern.compile(" *(\\d{4})-(\\d{1,2})-(\\d{1,2}) *(\\d{1,2})?(:)?(\\d{1,2})?(:)?(\\d{1,2})?(\\.\\d*)? *");

	private static Pattern p2 = Pattern.compile(" *(\\d{1,2})/(\\d{1,2})/(\\d{4}) *(\\d{1,2})?(:)?(\\d{1,2})?(:)?(\\d{1,2})?(\\.\\d*)? *");

	private static Pattern p3 = Pattern.compile(" *(\\d{4})(\\d{2})(\\d{2}) *(\\d{1,2})?(:)?(\\d{1,2})?(:)?(\\d{1,2})?(\\.\\d*)? *");

	private static Pattern p4 = Pattern.compile(" *(\\d{4})/(\\d{1,2})/(\\d{1,2}) *(\\d{1,2})?(:)?(\\d{1,2})?(:)?(\\d{1,2})?(\\.\\d*)? *");
	/**
	 * 将微信消息中的CreateTime转换成标准格式的时间（yyyy-MM-dd HH:mm:ss）
	 * 
	 * @param createTime
	 *            消息创建时间
	 * @return
	 */
	public static String formatTime(String createTime) {
		// 将微信传入的CreateTime转换成long类型，再乘以1000
		long msgCreateTime = Long.parseLong(createTime) * 1000L;
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(new Date(msgCreateTime));
	}
	/** 
     * 获取前/后n天日期(M月d日) 
     *  
     * @return 
     */  
    public static String getMonthDay(int diff) {  
        DateFormat df = new SimpleDateFormat("M月d日");  
        Calendar c = Calendar.getInstance();  
        c.add(Calendar.DAY_OF_YEAR, diff);  
        return df.format(c.getTime());  
    }  
	/**
	 * 获取时间
	 * @return
	 */
	public static long getCreateTime(){
		return new Date().getTime();
	}
	
	/**
	 * 获取年
	 * @param date
	 * @return
	 */
	public static int getYear(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(1);
	}

	/**
	 * 获取月份
	 * @param date
	 * @return
	 */
	public static int getMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(2) + 1;
	}
	/**
	 * 获取天
	 * @param date
	 * @return
	 */
	public static int getDay(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(5);
	}
	/**
	 * 获取时
	 * @param date
	 * @return
	 */
	public static int getHour(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(11);
	}
	/**
	 * 获取分
	 * @param date
	 * @return
	 */
	public static int getMinute(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(12);
	}
	/**
	 * 获取秒
	 * @param date
	 * @return
	 */
	public static int getSecond(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(13);
	}
	/**
	 * 获取毫秒
	 * @param date
	 * @return
	 */
	public static long getMillis(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.getTimeInMillis();
	}
	/**
	 * 获取添加月份
	 * @param date
	 * @return
	 */
	public static Date addMonth(Date date, int month) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(2, month);
		return cal.getTime();
	}
	/**
	 * 获取时间
	 * @param date
	 * @return
	 */
	public static Date addDate(Date date, int day) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(getMillis(date) + day * 24L * 3600L * 1000L);
		return c.getTime();
	}
	/**
	 * 获取小时
	 * @param date
	 * @return
	 */
	public static Date addHours(Date date, int hours) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(getMillis(date) + hours * 3600L * 1000L);
		return c.getTime();
	}
	/**
	 * 获取分
	 * @param date
	 * @return
	 */
	public static Date addMinutes(Date date, long minutes) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(getMillis(date) + minutes * 60L * 1000L);
		return c.getTime();
	}
	/**
	 * @param date
	 * @return
	 */
	public static Date add(Date date, long minutes) {
		if (date == null) {
			throw new IllegalArgumentException();
		}

		return addMinutes(date, minutes);
	}
	/**
	 * 截取
	 * @param date
	 * @return
	 */
	public static long sub(Date minuend, Date subtrahend) {
		long subResult = 0L;
		if ((minuend == null) || (subtrahend == null)) {
			throw new IllegalArgumentException();
		}
		subResult = minuend.getTime() - subtrahend.getTime();
		subResult /= 1000L;
		return subResult;
	}
	/**
	 * 获取2个时间段
	 * @param date
	 * @return
	 */
	public static long between(Date beginDate, Date endDate) {
		Calendar calBegin = Calendar.getInstance();
		Calendar calEnd = Calendar.getInstance();
		calBegin.setTime(beginDate);
		calEnd.setTime(endDate);
		calBegin.clear(14);
		calEnd.clear(14);
		long millisecs = calBegin.getTime().getTime()
				- calEnd.getTime().getTime();
		long remainder = millisecs % 86400000L;
		return (millisecs - remainder) / 86400000L;
	}
	/**
	 * 获取格式化时间
	 * @param date
	 * @return
	 */
	public static String formaterDate(Date date, String format) {
		return formatDateByFormat(date, format);
	}
	/**
	 * 时间转字符串
	 * @param date
	 * @return
	 */
	public static String dateToString(Date date, String format) {
		return formatDateByFormat(date, format);
	}
	/**
	 * 格式化
	 * @param date
	 * @return
	 */
	public static String formatDateByFormat(Date date, String format) {
		String result = "";
		if (date != null) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat(format);
				result = sdf.format(date);
			} catch (Exception ex) {
				log.info("date:" + date);
			}
		}
		return result;
	}
	/**
	 * 日期精确到日,小时，分钟，秒都是最
	 * 
	 * @param date
	 * @return
	 */
	public static Date maxDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		return cal.getTime();
	}
	/**
	 * 日期精确到日,小时，分钟，秒
	 * 
	 * @param date
	 * @return
	 */
	public static Date minDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	/**
	 * 返回格式如：2009年8月6日? 星期一 12:30:00
	 * 
	 * @param date
	 * @return
	 */
	public static String getCurrentTime() {
		Calendar currentCalendar = Calendar.getInstance();
		StringBuffer currentCalendarStr = new StringBuffer();
		currentCalendarStr.append(currentCalendar.get(Calendar.YEAR)).append("年");
		currentCalendarStr.append(currentCalendar.get(Calendar.MONTH) + 1).append("月");
		currentCalendarStr.append(currentCalendar.get(Calendar.DAY_OF_MONTH) + 1).append("日");
		int week = currentCalendar.get(Calendar.DAY_OF_WEEK);
		switch (week) {
		case 0:
			currentCalendarStr.append("&nbsp;&nbsp;星期天");
			break;
		case 1:
			currentCalendarStr.append("&nbsp;&nbsp;星期一");
			break;
		case 2:
			currentCalendarStr.append("&nbsp;&nbsp;星期二");
			break;
		case 3:
			currentCalendarStr.append("&nbsp;&nbsp;星期三");
			break;
		case 4:
			currentCalendarStr.append("&nbsp;&nbsp;星期四");
			break;
		case 5:
			currentCalendarStr.append("&nbsp;&nbsp;星期五");
			break;
		case 6:
			currentCalendarStr.append("&nbsp;&nbsp;星期六");
			break;
		}
		currentCalendarStr.append("&nbsp;&nbsp;").append(currentCalendar.get(Calendar.HOUR_OF_DAY));
		currentCalendarStr.append(":").append(currentCalendar.get(Calendar.MINUTE));
		currentCalendarStr.append(":").append(currentCalendar.get(Calendar.SECOND));
		return currentCalendarStr.toString();
	}

	/***格式化
	 * @param str
	 * @param format
	 * @return
	 */
	public static Date formatDate(String str, String format) {
		return stringToDate(str, format);
	}

	public static Date stringToDate(String str, String format) {
		if (str != null) {
			DateFormat dateFormat = new SimpleDateFormat(format);
			try {
				return dateFormat.parse(str);
			} catch (ParseException e) {
				return null;
			}
		}
		return null;
	}

	/**
	 * @param checkDate
	 * @return
	 */
	public static boolean isToday(String checkDate) {
		Date date = stringToDate(checkDate, "yyyy-MM-dd");
		formatDate(date, "yyyy-MM-dd");
		return formatDate(new Date(), "yyyy-MM-dd").equals(formatDate(date, "yyyy-MM-dd"));
	}

	/**
	 * @param date
	 * @return
	 */
	public static Timestamp dateToTimestamp(Date date) {
		Timestamp tmResult = null;
		if (date != null)
			tmResult = new Timestamp(date.getTime());
		return tmResult;
	}
	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd）
	 */
	public static String getDate() {
		return getDate("yyyy-MM-dd");
	}
	
	
	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String getDate(String pattern) {
		return DateFormatUtils.format(new Date(), pattern);
	}
	
	/**
	 * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String formatDate(Date date, Object... pattern) {
		String formatDate = null;
		if (pattern != null && pattern.length > 0) {
			formatDate = DateFormatUtils.format(date, pattern[0].toString());
		} else {
			formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
		}
		return formatDate;
	}

	/**
	 * 得到当前时间字符串 格式（HH:mm:ss）
	 */
	public static String getTime() {
		return formatDate(new Date(), "HH:mm:ss");
	}

	/**
	 * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String getDateTime() {
		return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前年份字符串 格式（yyyy）
	 */
	public static String getYear() {
		return formatDate(new Date(), "yyyy");
	}

	/**
	 * 得到当前月份字符串 格式（MM）
	 */
	public static String getMonth() {
		return formatDate(new Date(), "MM");
	}

	/**
	 * 得到当天字符串 格式（dd）
	 */
	public static String getDay() {
		return formatDate(new Date(), "dd");
	}

	/**
	 * 得到当前星期字符串 格式（E）星期几
	 */
	public static String getWeek() {
		return formatDate(new Date(), "E");
	}
	
	/**
	 * 日期型字符串转化为日期 格式（"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss" ）
	 */
	public static Date parseDate(String str) {
		try {
			return parseDate(str, parsePatterns);
		} catch (ParseException e) {
			return null;
		}
	}
	/**
	 * 当前日历，这里用中国时间表示
	 * 
	 * @return 以当地时区表示的系统当前日历
	 */
	public static Calendar getCalendar() {
		return Calendar.getInstance();
	}
	/**
	 * 获取过去的天数
	 * @param date
	 * @return
	 */
	public static long pastDays(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(24*60*60*1000);
	}
	
	/**
	 * s1对比s2 两个日期的大小
	 * @param s1                     日期 字符串
	 * @param s2                     日期 字符串
	 * @param pattern   格式
	 * @return          返回 负数s1小 ，返回0两数相等，返回正整数s1大
	 */
	public static int compare(String s1, String s2, String pattern) {
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		try {
			c1.setTime(df.parse(s1));
			c2.setTime(df.parse(s2));
		} catch (Exception e) {
			System.err.println("日期格式不正确");
		}
		return c1.compareTo(c2); 
	}
	
	/********************新增时间-------------------------******************/
	
	/**
	 * 用来处理时间转换格式的方法
	 * @param formate
	 * @param time
	 * @return
	 */
	private static String getConvertDateFormat(String formate, Date time) {
		SimpleDateFormat dateFormat=new SimpleDateFormat(formate);
		String date=dateFormat.format(time);
		return date;
	}
	/**  
	 * 得到本月的第一天  
	 * @return  
	 */  
	public static String getCurrentMonthFirstDay() { 
	    Calendar calendar = Calendar.getInstance();   
	    calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));   
	    return getConvertDateFormat("yyyy-MM-dd", calendar.getTime());   
	}   
	  
	/**  
	 * 得到本月的最后一天  
	 *   
	 * @return  
	 */  
	public static String getCurrentMonthLastDay() {   
	    Calendar calendar = Calendar.getInstance();   
	    calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));   
	    return getConvertDateFormat("yyyy-MM-dd", calendar.getTime());   
	}   
	/**
	 * 
	 * 获取上个月的第一天
	 * 
	 * @return
	 */
	public static String getBeforeMonthFirstDay() {
		Calendar cal = Calendar.getInstance();
		Date date = new Date();
		cal.setTime(date);
		int year = 0;
		int month = cal.get(Calendar.MONTH); // 上个月月份
		int day = cal.getActualMinimum(Calendar.DAY_OF_MONTH);//起始天数

		if (month == 0) {
			year = cal.get(Calendar.YEAR) - 1;
			month = 12;
		} else {
			year = cal.get(Calendar.YEAR);
		}
		String endDay = year + "-" + month + "-" + day;
		return endDay;
	}

	/**
	 * 获取上个月的最一天
	 * 
	 * @return
	 */
	public static String getBeforeMonthLastDay() {
		//实例一个日历单例对象
		Calendar cal = Calendar.getInstance();
		Date date = new Date();
		cal.setTime(date);
		int year = 0;
		int month = cal.get(Calendar.MONTH); // 上个月月份
		// int day1 = cal.getActualMinimum(Calendar.DAY_OF_MONTH);//起始天数
		int day = cal.getActualMaximum(Calendar.DAY_OF_MONTH); // 结束天数

		if (month == 0) {
			//year = cal.get(Calendar.YEAR) - 1;
			month = 12;
		} else {
			year = cal.get(Calendar.YEAR);
		}
		String endDay = year + "-" + month + "-" + day;
		return endDay;
	}
	
	/**
	 * 
	 * 获取下月的第一天
	 * 
	 * @return
	 */
	public static String getNextMonthFirstDay() {
		Calendar cal = Calendar.getInstance();
		Date date = new Date();
		cal.setTime(date);
		int year = 0;
		int month = cal.get(Calendar.MONDAY)+2; // 下个月月份
		/*
		 * 如果是这样的加一的话代表本月的第一天
		 * int month = cal.get(Calendar.MONDAY)+1; 
		 * int month = cal.get(Calendar.MONTH)+1
		 */
		int day = cal.getActualMinimum(Calendar.DAY_OF_MONTH);//起始天数

		if (month == 0) {
			year = cal.get(Calendar.YEAR) - 1;
			month = 12;
		} else {
			year = cal.get(Calendar.YEAR);
		}
		String Day = year + "-" + month + "-" + day;
		return Day;
	}

	/**
	 * 获取下个月的最一天
	 * 
	 * @return
	 */
	public static String getNextMonthLastDay() {
		//实例一个日历单例对象
		Calendar cal = Calendar.getInstance();
		Date date = new Date();
		cal.setTime(date);
		int year = 0;
		int month = cal.get(Calendar.MONDAY)+2; // 下个月份
		// int day1 = cal.getActualMinimum(Calendar.DAY_OF_MONTH);//起始天数
		int day = cal.getActualMaximum(Calendar.DAY_OF_MONTH); // 结束天数

		if (month == 0) {
			//year = cal.get(Calendar.YEAR) - 1;
			month = 12;
		} else {
			year = cal.get(Calendar.YEAR);
		}
		String endDay = year + "-" + month + "-" + day;
		return endDay;
	}

	/**
	 * 本地时区输出当前日期 GMT时间
	 */
	public static String getLocalDate() {
		Date date = new Date();
		return date.toLocaleString();// date.toGMTString();
	}
	/**
	 * 判断客户端输入的是闰年Leap还是平年Average
	 * @return
	 */
	public static String getLeapOrAverage (int year){
		
		if((year%4==0 && year%100!=0)||year%400==0){
			return year+"闰年";
		}else{
			return year+"平年";
		}
	}
	/**
	 * 
	 * @return
	 */
	public static String getYYYYMMddHHmmss(){
		DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//String currentTime=df.format(new Date());
		Calendar calendar = Calendar.getInstance();
		
		return df.format(calendar.getTime());
		
	}
	
	/**
	 * 传入的日期按照指定格式进行格式化
	 * @param date
	 * @param format（yyyy-MM-DD或yyyy/MM/DD或yyyy-MM-dd HH:mm:ss）
	 * @return String
	 */
	public static String formatDate(Date date, String format) {
		SimpleDateFormat fmt = new SimpleDateFormat(format);
		return fmt.format(date);
	}
	/**
	 * 获取当前时间是本年的第几周
	 * @return
	 */
	public static String getWeeK_OF_Year(){
		Calendar cal = Calendar.getInstance();
		Date date = new Date();
		cal.setTime(date);
		int week=cal.get(Calendar.WEEK_OF_YEAR );
		return  "当日是本年的第"+week+"周";
	}
	/**
	 * 获取当日是本年的的第几天
	 * @return
	 */
	public static String getDAY_OF_YEAR(){
		Calendar cal = Calendar.getInstance();
		Date date = new Date();
		cal.setTime(date);
		int day=cal.get(Calendar.DAY_OF_YEAR );
		return  "当日是本年的第"+day+"天";
	}
	/**
	 * 可以根据设置时分秒去执行方法
	 * @param hour执行的小时
	 * @param Minute执行的分钟
	 * @param Second执行的秒
	 * @return
	 */
	public static Date scheduleTimer(Integer Hour,Integer Minute,Integer Second) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, Hour); // 凌晨1点
		calendar.set(Calendar.MINUTE, Minute);//分
		calendar.set(Calendar.SECOND, Second);//秒
		Date date = calendar.getTime(); // 第一次执行定时任务的时间
		// 如果第一次执行定时任务的时间 小于当前的时间
		// 此时要在 第一次执行定时任务的时间加一天，以便此任务在下个时间点执行。如果不加一天，任务会立即执行。
		if (date.before(new Date())) {
			date = addDay(date, 1);
		}
		return date;
	}
	/***
	  * 增加或减少天数  
	  * @param date
	  * @param num
	  * @return
	  */
    public static Date addDay(Date date, int num) {  
        Calendar startDT = Calendar.getInstance();  
        startDT.setTime(date);  
        startDT.add(Calendar.DAY_OF_MONTH, num);  
        return startDT.getTime();  
    }  
    

	

	/***
	 * 字符串时间转成时间
	 * @param dateString
	 * @return
	 */
	public static Date converToDate(String dateString) {
		if (dateString == null)
			return null;
		Matcher m = p.matcher(dateString);

		String y = null;
		String M = null;
		String d = null;
		String H = null;
		String mm = null;
		String s = null;

		if (m.matches()) {
			y = m.group(1);
			M = m.group(2);
			d = m.group(3);
			H = m.group(4);
			mm = m.group(6);
			s = m.group(8);
			Calendar cal = Calendar.getInstance();
			cal.set(y == null ? 0 : Integer.parseInt(y), M == null ? 0
					: Integer.parseInt(M) - 1,
					d == null ? 0 : Integer.parseInt(d), H == null ? 0
							: Integer.parseInt(H),
					mm == null ? 0 : Integer.parseInt(mm), s == null ? 0
							: Integer.parseInt(s));

			return cal.getTime();
		}

		m = p2.matcher(dateString);
		if (m.matches()) {
			y = m.group(3);
			M = m.group(2);
			d = m.group(1);
			H = m.group(4);
			mm = m.group(6);
			s = m.group(8);
			Calendar cal = Calendar.getInstance();
			cal.set(y == null ? 0 : Integer.parseInt(y), M == null ? 0
					: Integer.parseInt(M) - 1,
					d == null ? 0 : Integer.parseInt(d), H == null ? 0
							: Integer.parseInt(H),
					mm == null ? 0 : Integer.parseInt(mm), s == null ? 0
							: Integer.parseInt(s));

			return cal.getTime();
		}

		m = p3.matcher(dateString);
		if (m.matches()) {
			y = m.group(1);
			M = m.group(2);
			d = m.group(3);
			H = m.group(4);
			mm = m.group(6);
			s = m.group(8);
			Calendar cal = Calendar.getInstance();
			cal.set(y == null ? 0 : Integer.parseInt(y), M == null ? 0
					: Integer.parseInt(M) - 1,
					d == null ? 0 : Integer.parseInt(d), H == null ? 0
							: Integer.parseInt(H),
					mm == null ? 0 : Integer.parseInt(mm), s == null ? 0
							: Integer.parseInt(s));

			return cal.getTime();
		}

		m = p4.matcher(dateString);
		if (m.matches()) {
			y = m.group(1);
			M = m.group(2);
			d = m.group(3);
			H = m.group(4);
			mm = m.group(6);
			s = m.group(8);
			Calendar cal = Calendar.getInstance();
			cal.set(y == null ? 0 : Integer.parseInt(y), M == null ? 0
					: Integer.parseInt(M) - 1,
					d == null ? 0 : Integer.parseInt(d), H == null ? 0
							: Integer.parseInt(H),
					mm == null ? 0 : Integer.parseInt(mm), s == null ? 0
							: Integer.parseInt(s));

			return cal.getTime();
		}

		return null;
	}

	 

	/**
	 * @param args
	 * @throws ParseException
	 */
	public static void main(String[] args) throws Exception {
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
		.format(converToDate(" 2999/12/31    ")));
	//		System.out.println(formatDate(parseDate("2010/3/6")));
	//		System.out.println(getDate("yyyy年MM月dd日 E"));
	//		long time = new Date().getTime()-parseDate("2012-11-19").getTime();
	//		System.out.println(time/(24*60*60*1000));
	}
}
