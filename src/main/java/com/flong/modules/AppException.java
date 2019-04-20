package com.flong.modules;
/**
 * @Description	AppException
 * @ClassName	AppException
 * @Date		2017年2月23日 下午2:21:04
 * @Copyright (c) All Right Reserved , 2017.
 */
public class AppException extends Exception {

	private static final long serialVersionUID = 1L;	
	/**异常码*/
	public int code;
	/**异常信息*/
	public String msg;
	/**异常级别*/
	public int level = 0;		// 默认应用级别异常
	

	public AppException() {
		
	}
 
	public AppException(int code,String msg){
		super();
		this.msg = msg;
		this.code=code;
	}
	/**
	 */
	public AppException(int code,String msg,Throwable e){
		super(msg, e);
		this.msg = msg;
		this.code=code;
	}
	/**
	 */
	public AppException(int code,Throwable e){
		super(e);
		this.code=code;
	}

	/**
	 * @return 异常码 */
	public int getCode() {
		return code;
	}
	public String getMsg(){
		return msg;
	}
}