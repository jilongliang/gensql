package com.flong.commons.persistence;
 /**
  * @author liangjilong
  * @Date:2015-07-16
  */
@SuppressWarnings("all")
public class SqlUtils {
	
	/***
	 * @param buffer
	 * @param column当是HQL的时候这个column就是实体类的属性，当是SQL的时候column就是数据库表的字段.
	 * @param s
	 */
	public static void andEquals(StringBuffer buffer, String column, String s){
		if(s!=null && !s.isEmpty()){
			buffer.append(" and ");
			buffer.append(column+"='");
			buffer.append(s+"'");
		}
	}
	/***
	 * @param buffer
	 * @param column当是HQL的时候这个column就是实体类的属性，当是SQL的时候column就是数据库表的字段.
	 * @param s
	 */
	public static void andLike(StringBuffer buffer, String column, String s){
		if(s!=null && !s.isEmpty()){
			buffer.append(" and ");
			buffer.append(column+" like '%");
			buffer.append(s+"%'");
		}
	}
	/***
	 *  
	 * @param buffer
	 * @param column当是HQL的时候这个column就是实体类的属性，当是SQL的时候column就是数据库表的字段.
	 * @param s
	 */
	public static void orEquals(StringBuffer buffer, String column, String s){
		if(s!=null && !s.isEmpty()){
			buffer.append(" or ");
			buffer.append(column+"='");
			buffer.append(s+"'");
		}
	}
	/***
	 *  
	 * @param buffer
	 * @param column当是HQL的时候这个column就是实体类的属性，当是SQL的时候column就是数据库表的字段.
	 * @param s
	 */
	public static void orLike(StringBuffer buffer, String column, String s){
		if(s!=null && !s.isEmpty()){
			buffer.append(" or ");
			buffer.append(column+" like '%");
			buffer.append(s+"%'");
		}
	}
	
	/**
	 * 大于与等于
	 * @param buffer
	 * @param column 当是HQL的时候这个column就是实体类的属性，当是SQL的时候column就是数据库表的字段.
	 * @param value字符串时间.格式是yyyy-mm--dd
	 */
	public static void andGtEqualsDate(StringBuffer buffer, String column, String value){
		if(value!=null && !value.isEmpty()){
			buffer.append(" and ");
			buffer.append(column+" >= '");
			buffer.append(value);
			buffer.append(" 00:00:00'");
		}
	}
	/**
	 * 小于与等于时间
	 * @param buffer
	 * @param column
	 * @param value字符串时间.格式是yyyy-mm--dd
	 */
	public static void andLtEqualsDate(StringBuffer buffer, String column, String value){
		if(value!=null && !value.isEmpty()){
			buffer.append(" and ");
			buffer.append(column+" <= '");
			buffer.append(value);
			buffer.append(" 23:59:59'");
		}
	}
	/**
	 * @param buffer
	 * @param column当是HQL的时候这个column就是实体类的属性，当是SQL的时候column就是数据库表的字段.
	 * @param values格式必须是如：1,2,3,4这个值可以支持中文，与英文,号
	 */
	public static void andIn(StringBuffer buffer, String column, String values){
		if(values!=null && !values.isEmpty()){
			buffer.append(" and ");
			buffer.append(column+" in ");
			buffer.append("(");
			buffer.append(createInParam(values));
			buffer.append(")");
		}
	}
	

	/**
     * 生成IN语句的参数
     * 
     * @param paramStr 逗号(,)分隔的参数串
     * @return
     */
    public static String createInParam(String paramStr){
    	String[] params = paramStr.replace("，", ",").split(",");
    	StringBuffer formatVal = new StringBuffer();
    	for(int i = 0; i < params.length; i++){
    		String param = params[i];
    		formatVal.append("'").append(param).append("'");
    		if(i != params.length - 1){
    			formatVal.append(",");
    		}
    	}
    	return formatVal.toString();
    }
    
	public static void main(String[] args) {
		StringBuffer  buffer=new StringBuffer();
		andIn(buffer,"sss.aaa","112121");
		System.out.println(buffer.toString());
		System.out.println(createInParam("U0C,U0E"));
		 
	}
}
