package com.flong.commons.persistence.bean;

import org.apache.commons.lang3.StringUtils;

import com.flong.commons.persistence.dao.impl.BaseDomain;

/**
 * 封装一异常处理modelObject，该modelObject将传递给异常处理jsp页面进行显示 
 * 创建日期：2012-12-19
 * @author niezhegang
 */
public class ExceptionModelObject extends BaseDomain {
	private static final long serialVersionUID = 3533913303260331358L;
	
	/**异常模型对象在ModelAndView中对应的属性名称*/
	public final static String EXCEPTION_MODEL_OBJECT_NAME = "exceptionModel";
	/**异常类名 */
    private String exceptionClassName;
    /**异常堆栈*/
    private String stacktrace;
    /**异常消息*/
    private String message;
    /**异常原因(异常根源)*/
    private String cause;
    
    /**
     * 构造方法
     * 将异常信息转化为modelObject信息
     * @param e
     * 创建日期：2012-12-19
     * 修改说明：
     * @author niezhegang
     */
	public ExceptionModelObject(Exception e) {
		//传入异常对象为空，则返回
		if (e == null)
			return;
		exceptionClassName = e.getClass().getName();
		message = e.getMessage();
		cause = e.toString();
		StringBuilder buildTrace = new StringBuilder();
		appendStackTrace(e,buildTrace);
		//加入换行
		stacktrace = StringUtils.replace(buildTrace.toString(), ")", ")\r\n");
	}
	
	/**
	 * 递归获取深层次异常信息
	 * @param e
	 * @param buildTrace
	 * 创建日期：2012-12-20
	 * 修改说明：
	 * @author niezhegang
	 */
	private void appendStackTrace(Throwable e,StringBuilder buildTrace){
		Throwable causeException = e.getCause();
		if(causeException != null){
			cause = causeException.toString();
			appendStackTrace(causeException,buildTrace);
		}
		else{
			buildTrace.append("Caused by: ");
			StackTraceElement[] stes = e.getStackTrace();
			if (stes != null && stes.length > 0) {
				for (StackTraceElement ste : stes) {
					buildTrace.append(ste.toString());
				}
			}
		}
	}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getExceptionClassName() {
		return exceptionClassName;
	}

	public void setExceptionClassName(String exceptionClassName) {
		this.exceptionClassName = exceptionClassName;
	}

	public String getStacktrace() {
		return stacktrace;
    }

    public void setStacktrace(String stacktrace) {
        this.stacktrace = stacktrace;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }
}