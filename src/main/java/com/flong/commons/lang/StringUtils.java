package com.flong.commons.lang;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * 字符串工具类, 继承org.apache.commons.lang3.StringUtils类
 * @author liangjilong
 * @version 2013-01-15
 */
@SuppressWarnings("all")
public class StringUtils extends org.apache.commons.lang3.StringUtils {

	public static Logger logger = Logger.getLogger(StringUtils.class);
	/**
     * 去除字符串中的空格<br>
     * 注：此方法主要是为了方便如下情况<br>
     * handleNULL(Map.get("str"))，handleNULL(request.getAttribute("str"))等
     * <p>
     * 例：输入null，返回"";输入" "，返回"";输入"sdf ss df"，返回"sdfssdf"，输入" null"，返回""
     * 
     * @param input 待检查的对象
     * @return 如果传入的对象不是字符串或为null，则返回空字符串""
     */
    public static String handleNULL(Object input) {
        if (input != null) {
            if (input instanceof String)
                return handleNULL((String) input, "");
        }
        return "";
    }

    /**
     * 去除字符串中的空格<br>
     * <p>
     * 例：输入null，返回"";输入" "，返回"";输入"sdf ss df"，返回"sdfssdf"，输入" null"，返回""
     * 
     * @param input 待检查的字符串
     * @return 如果传入的对象不是字符串或为null，则返回空字符串""
     */
    public static String handleNULL(String input) {
        return handleNULL(input, "");
    }

    /**
     * 去除字符串中的空格<br>
     * <p>
     * 例：输入null，返回def;输入" "，返回def;输入"sdf ss df"，返回"sdfssdf"，输入" null"，返回def
     * 
     * @param input 待检查的字符串
     * @param def 默认字符串
     * @return 如果传入的对象不是字符串或为null，则返回def知道的字符串
     */
    public static String handleNULL(String input, String def) {
        if (null == input || input.trim().length() <= 0
                || input.trim().toLowerCase().equals("null")) {
            return def;
        } else {
            return input.trim();
        }
    }

     
    /**
     * 将input保留scale位小数并转为字符串
     * 
     * @param input 数字
     * @param scale 小数位数
     * @return 字符串
     */
    public static String handleScale(double input, int scale) {
        BigDecimal bigDecimal = new BigDecimal(input);
        bigDecimal = bigDecimal.setScale(scale, BigDecimal.ROUND_HALF_EVEN);
        return handleNULL(bigDecimal.toString());

    }

    /**
     * 将input保留scale位小数并转为字符串
     * 
     * @param input 数字字符串
     * @param scale 小数位数
     * @return 字符串
     */
    public static String handleScale(String input, int scale) {
        BigDecimal bigDecimal = new BigDecimal(input);
        bigDecimal = bigDecimal.setScale(scale, BigDecimal.ROUND_HALF_EVEN);
        return handleNULL(bigDecimal.toString());

    }

    /**
     * 如果字符串长度小于length，前面就用指定的字符补齐
     * <p>
     * 如：leadingChar("123",6,'0')，返回"000123"
     * 
     * @param input 字符串
     * @param length 长度
     * @return 字符串
     */
    public static String leadingChar(String input, int length, char ch) {
        input = handleNULL(input);
        char nils[] = new char[Math.max(0, length - input.length())];
        Arrays.fill(nils, ch);
        input = String.valueOf(nils) + input;
        return handleNULL(input);
    }

    /**
     * 如果字符串长度小于length，前面就用0补齐
     * <p>
     * 如：leadingChar("123",6)，返回"000123"
     * 
     * @param input 字符串
     * @param length 长度
     */
    public static String leadingZero(String input, int length) {
        return leadingChar(input, length, '0');
    }

    /**
     * 把用分表示的字符串货币转成元(格式："0.00")
     * 
     * @param fen 用分表示的货币金额字符串
     * @return 用元表示的货币金额字符串
     */
    public static String convertFenToYuan(String fen) {
        return convertFenToYuan(Double.parseDouble(fen));
    }

    /**
     * 把用分表示的货币金额转成元(格式："0.00")
     * 
     * @param fen 用分表示的货币金额
     * @return 用元表示的货币金额字符串
     */
    public static String convertFenToYuan(double fen) {
        String result;
        try {
            double yuan = fen / 100;
            result = handleScale(yuan, 2);
        } catch (Exception ex) {
            result = "0.00";
        }
        return result;
    }

    /**
     * 把字符串按一定的分隔符生成数组<br>
     * （注：此方法不会返回null）<br>
     * 同String.split(String)方法比较，优点是对于特殊字符分割时不需要转义，<br>
     * 如"asd\\asd".split("\\\\")，String2Arr("asd\\asd","\\");
     * 
     * @param str 待分割的字符串
     * @param delim 分隔符
     * @return 字符串数组
     */
    public static String[] String2Array(String str, String delim) {
        if (str == null || delim == null) {
            return new String[0];
        }
        StringTokenizer st = new StringTokenizer(str, delim);
        String[] retArr = new String[st.countTokens()];
        int i = 0;
        while (st.hasMoreTokens()) {
            retArr[i] = st.nextToken();
            i++;
        }
        return retArr;
    }
    
    /**
	 * Check if an array of strings contains the string s
	 *
	 * @param	a	the array of strings
	 * @param	s	the string
	 * @return		true if a contains s, false otherwise
	 */
	public static boolean containsValue(String[] a, String s) {
		boolean bRetVal = false;

		for (int i = 0; i < a.length; i++) {
			if (handleNULL(a[i]).equals(handleNULL(s))) {
				bRetVal = true;
				break;
			}
		}

		return bRetVal;
	}
	
	/**
	 * 截取掉ADSL后缀后返回,如gzDSL234234266@163.gd，返回gzDSL234234266
	 *
	 * @param	s	the string
	 * @return		true if a contains s, false otherwise
	 */
	public static String substringADSLSuffix(String s) {
		if(isEmpty(s)){
			return "";
		}
		if(s.indexOf("@")!=-1){
			return s.substring(0,s.indexOf("@"));
		}
		return s;
	}

    /**
     * Within a string, replace a substring with another string.
     * 
     * @param vStr a string
     * @param vOld the old word to be replaced
     * @param vNew the new word to be replaced with
     * @return the new string
     */
    static public String replaceString(String vStr, String vOld, String vNew) {
        if (isEmpty(vStr))
            return "";

        String sRetVal = "";
        int pos = vStr.indexOf(vOld);

        while (pos != -1) {
            sRetVal = sRetVal + vStr.substring(0, pos) + vNew;
            vStr = vStr.substring(pos + vOld.length());

            pos = vStr.indexOf(vOld);
        }

        sRetVal = sRetVal + vStr;

        return sRetVal;
    }

    /**
     * Convert an int to a string.
     * 
     * @param vValue the int to be converted
     * @param len the string length
     * @param vChar a single-char string to be repeatly padded before the int if the int is shorter
     *            than len
     * @return the String representation of the int
     */
    static public String intToStr(int vValue, int len, String vChar) {
        String sTemp = "" + vValue;

        if (!vChar.equals("")) {
            while (sTemp.length() < len) {
                sTemp = vChar + sTemp;
            }
        }

        return sTemp;
    }

    /**
     * Convert an int to a string.
     * 
     * @param vValue the int to be converted
     * @return the String representation of the int
     */
    static public String intToStr(int vValue) {
        return intToStr(vValue, 0, "");
    }

    /**
     * Convert the array a to a string, each element is separated by sDivider.
     * 
     * @param a the array of strings
     * @param sDivider the divider that separates each element
     * @return the resulted string
     */
    static public String arrayToString(String[] a, String sDivider) {
        String sRetVal = "";

        for (int i = 0; i < a.length; i++) {
            if (sRetVal.equals(""))
                sRetVal = a[i];
            else
                sRetVal = sRetVal + sDivider + a[i];
        }

        return sRetVal;
    }

    /**
     * Parse a list in string format
     * 
     * @param vList the list in string
     * @param vDelimiter a one charactor delimiter
     * @return the list in an array
     */
    static public String[] stringToArray(String vList, String vDelimiter) {
        Vector avList = new Vector();

        StringTokenizer st = new StringTokenizer(vList, vDelimiter);

        while (st.hasMoreTokens()) {
            avList.addElement(st.nextToken());
        }

        String[] aList = new String[avList.size()];
        avList.copyInto(aList);

        return aList;
    }

    /**
     * 字符串转int型数字
     * 
     * @param vValue 字符串
     * @return int型数字，如果字符串不合法，则返回0
     * @throws Exception 字符串转换异常
     */
    static public int strToInt(String vValue) throws Exception {
        return strToInt(vValue, 0);
    }

    /**
     * 字符串转int型数字
     * 
     * @param vValue 字符串
     * @param ifNullReturn vValue为空时返回的数字
     * @return int型数字
     * @throws Exception 字符串转换异常
     */
    static public int strToInt(String vValue, int ifNullReturn) throws Exception {
        if (isEmpty(vValue))
            return ifNullReturn;
        else
            return (strToBigDecimal(vValue)).intValue();
    }

    /**
     * 字符串转float型数字
     * 
     * @param vValue 字符串
     * @return float型数字
     * @throws Exception 字符串转换异常
     */
    static public float strToFloat(String vValue) throws Exception {
        return strToBigDecimal(vValue).floatValue();
    }

    /**
     * 字符串转double型数字
     * 
     * @param vValue 字符串
     * @return double型数字
     * @throws Exception 字符串转换异常
     */
    static public double strToDouble(String vValue) throws Exception {
        return strToBigDecimal(vValue).doubleValue();
    }

    /**
     * 字符串转double型数字
     * 
     * @param vValue 字符串
     * @return long型数字
     * @throws Exception 字符串转换异常
     */
    static public long strToLong(String vValue) throws Exception {
        return strToBigDecimal(vValue).longValue();
    }

    /**
     * 将字符串转为BigDecimal
     * 
     * @param vValue 字符串
     * @return BigDecimal对象，如果字符串不合法，返回BigDecimal.valueOf(0)
     * @throws Exception 字符串转换异常
     */
    static public BigDecimal strToBigDecimal(String vValue) throws Exception {
        try {
            return new BigDecimal(vValue);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * Get a random string with letters or digits
     * 
     * @param vStrLength the length of the random string
     * @return the string
     */
    static public String getRandomString(int vStrLength) {
        String s = "";
        for (int i = 1; i <= vStrLength; i++) {
            int nextChar = (int) (Math.random() * 62);
            if (nextChar < 10) // 0-9
                s += nextChar;
            else if (nextChar < 36) // a-z
                s += (char) (nextChar - 10 + 'a');
            else
                s += (char) (nextChar - 36 + 'A');
        }
        return s;
    }

    static public String getRandomNumberString(int vStrLength) {
        String s = "";
        for (int i = 1; i <= vStrLength; i++) {
            int nextChar = (int) (Math.random() * 10);
            if (nextChar < 10) // 0-9
                s += nextChar;
            else
                s += "0";
        }
        return s;
    }

    /**
     * Return text with the specified length
     * 
     * @param s the text
     * @param maxlimit the max limit
     * @return the substring
     */
    static public String truncate(String s, int maxlimit) {
        String sRetVal = "";
        if (s.length() > maxlimit) {
            sRetVal = s.substring(0, maxlimit);
        }

        return sRetVal;
    }

    static public boolean isValidEmail(String vEmailAddress) {
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("^.+\\@(\\[?)[a-zA-Z0-9\\-\\.]+\\.([a-zA-Z]{2,20}|[0-9]{1,3})(\\]?)$");
        java.util.regex.Matcher matcher = pattern.matcher(vEmailAddress);

        return matcher.matches();
    }

    static public boolean isValidMultiEmails(String vEmailAddress) {
        boolean bValidEmail = true;

        String[] aEmail = stringToArray(vEmailAddress, ",");

        for (int i = 0; i < aEmail.length; i++) {
            if (!isEmpty(aEmail[i])) {
                if (!isValidEmail(aEmail[i])) {
                    bValidEmail = false;
                    break;
                }
            }
        }
        return bValidEmail;
    }

    /**
     * 获取限定字节数的字符串。 输入为一个字符串和字节数，输出为按字节截取的字符串。 但是要保证汉字不被截半个，
     * 如“我ABC”4，应该截为“我AB”，输入“我ABC汉DEF”，6，应该输出为“我ABC”而不是“我ABC+汉的半个”
     * 
     * @param str
     * @param len
     * @return
     * @throws Exception
     */
    public static String getLimitBytesString(String str, int len) throws Exception {
        if (str == null || str.length() == 0)
            return str;
        int counterOfDoubleByte = 0;
        byte b[];

        b = str.getBytes("GBK");
        if (b.length <= len)
            return str;
        for (int i = 0; i < len; i++) {
            if (b[i] < 0)
                counterOfDoubleByte++;
        }

        if (counterOfDoubleByte % 2 == 0) {
            return new String(b, 0, len, "GBK");
        } else {
            return new String(b, 0, len - 1, "GBK");
        }

    }

    /**
     * 从Clob对象中读取字符串
     * 
     * @param clob
     * @return 字符串
     * @throws SQLException
     */
    public static String getClob(Clob clob) throws SQLException {
        String str = "";
        if (clob != null && clob.length() != 0) {
            try {
                str = clob.getSubString((long) 0, (int) clob.length());
            } catch (SQLException e) {
            	if (logger.isInfoEnabled()) {
                    logger.info("INFO:the current driver wants to start from 1,not 0,"
                            + e.getMessage());
            	}
                str = clob.getSubString((long) 1, (int) clob.length());
            }
            int i = str.indexOf("<CLOB ");
            if (i >= 0)
                str = str.substring(0, i);
        }
        return str;
    }

    /**
     * 判断字符串是否全是数字组成
     * 
     * @param str String
     * @return boolean
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * check string is vaild integer or not.
     * 
     * @param sValue
     * @return
     */
    public static boolean isValidInteger(String sValue) {
        if (isEmpty(sValue)) {
            return false;
        }
        for (int i = 0; i < sValue.length(); i++) {
            char c = sValue.charAt(i);
            if ((c < '0') || (c > '9'))
                return false;
        }
        return true;
    }

    /**
     * 从url中获取参数的值
     * 
     * @param url url字符串
     * @param key 参数
     * @return 没有找到参数返回""
     */
    public static String getParameterFromURL(String url, String key) {
        if (isEmpty(url) || isEmpty(key))
            return "";
        String[] tmp1 = url.split("\\?");
        if (tmp1.length <= 1)
            return "";
        String[] tmp2 = tmp1[1].split("&");
        int length = tmp2.length;
        String tmp3 = null;
        String[] tmp4 = null;
        for (int i = 0; i < length; i++) {
            tmp3 = tmp2[i];
            if (!isEmpty(tmp3)) {
                tmp4 = tmp3.split("=");
                if (tmp4.length <= 1) {
                    if (tmp4[0].equals(key))
                        return "";
                } else {
                    if (tmp4[0].equals(key))
                        return tmp4[1];
                }
            }
        }
        return "";
    }
    
	/**
	 * 将BigDecimal类型转换为int
	 * @param bg	BigDecimal
	 * @return	int
	 */
	public static int convertBigDecimalToInt(BigDecimal bg) {
		if (bg == null) {
			return 0;
		}
		try {
			return bg.intValue();
		} catch (Exception e) {
			return 0;
		}
	}
	
	/**
	 * 将BigDecimal类型转换为int
	 * @param bg	BigDecimal
	 * @return	int
	 */
	public static String convertBigDecimalToStr(BigDecimal bg) {
		if (bg == null) {
			return null;
		}
		try {
			return String.valueOf(bg.intValue());
		} catch (Exception e) {
			return null;
		}
	}
	/**
	 * 判断是否是空字符串 null和"" 都返回 true
	 * 
	 * @author Robin Chang
	 * @param s
	 * @return
	 */
	public static boolean isEmpty(String s) {
		if (s != null && !s.equals("")) {
			return false;
		}
		return true;
	}
	/**
	 * 判断对象是否为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(Object str) {
		boolean flag = true;
		if (str != null && !str.equals("")) {
			if (str.toString().length() > 0) {
				flag = true;
			}
		} else {
			flag = false;
		}
		return flag;
	}
	public static boolean isNotEmpty(String str)
    {
        return str != null && str.length() > 0;
    }
	/**
	 * 替换掉HTML标签
	 */
	public static String replaceHtml(String html) {
		String regEx = "<.+?>";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(html);
		String s = m.replaceAll("");
		return s;
	}

	/**
	 * 缩略字符串（不区分中英文字符）
	 * @param str 目标字符串
	 * @param length 截取长度
	 * @return
	 */
	public static String abbr(String str, int length) {
		if (str == null) {
			return "";
		}
		try {
			StringBuilder sb = new StringBuilder();
			int currentLength = 0;
			for (char c : str.toCharArray()) {
				currentLength += String.valueOf(c).getBytes("GBK").length;
				if (currentLength <= length - 3) {
					sb.append(c);
				} else {
					sb.append("...");
					break;
				}
			}
			return sb.toString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";

	}

	/**
	 * 向客户端写出内容
	 * @param rep
	 * @param msg
	 * @throws Exception
	 */
	public static void writeResp(HttpServletResponse rep, String msg)
			throws Exception {
		rep.getWriter().write(msg);
		rep.getWriter().flush();
	}
	
	/**
	 * 转义HTML
	 * @param content
	 * @return
	 * @author ss
	 */
	public static String html(String content) {
        if(content==null) return "";       
        String html = content;
        html = StringUtils.replace(html, "&amp;","&");
        html = StringUtils.replace(html, "&nbsp;" ," ");// 替换空格
        html = StringUtils.replace(html, "&apos;","'" );
        html = StringUtils.replace(html, "&quot;", "\"" );
        html = StringUtils.replace(html, "&lt;","<");
        html = StringUtils.replace(html, "&gt;",">");
        return html;
	 }
	/**
	 * 返回文件后缀名
	 * @param fileName
	 * @return
	 */
	public static String getExtension(String fileName){
		if((fileName!=null)&&(fileName.length()>0))
		{
			int dot=fileName.lastIndexOf(".");
			if((dot>-1)&&(dot<(fileName.length()-1))){
				return fileName.substring(dot+1);
			}
		}
		return fileName;
	}
	/**
	 * 获取文件名称
	 * @param fileName
	 * @return
	 */
	public static String getFileName(String fileName){
		if((fileName!=null)&&(fileName.length()>0))
		{	
			fileName=fileName.substring(0, fileName.lastIndexOf("."));
		}
		return fileName;
	}
	
	public static void main(String[] args) {
		//org.apache.commons.lang3.StringUtils类一些方法使用..
		System.out.println("org.apache.commons.lang3.StringUtils");
		System.out.println(uncapitalize("I love you"));//把的一个字母转换成小写
		System.out.println(capitalize("i love you"));//把的一个字母转换成大写
		System.out.println(lowerCase("I LOVE YOU"));//所有字母都转换成小写
		System.out.println(upperCase("i love you"));//所有字母都转换成大写
		
		//rightPad
		System.out.println(StringUtils.rightPad("i love you", 3)+"I");
		
		
		//--------------------join----拼接SQL适合--------------
		String[] strs =new String[]{"i","love","you"};
		String str2=StringUtils.join(strs, ",");//
		System.out.println(str2);
		String sqlStr="";
		for(String st:strs)
		{
			sqlStr="'"+st+str2+"'";
		}
		System.out.println(sqlStr);
		
	}
}
