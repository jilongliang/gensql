package com.flong.commons.lang.exception.handler;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.flong.commons.lang.exception.DataAccessException;
import com.flong.commons.lang.exception.IExceptionJspErrorPage;
import com.flong.commons.lang.exception.IExceptionLogger;
import com.flong.commons.persistence.bean.ExceptionModelObject;
import com.flong.commons.utils.JsonViewFactory;
import com.flong.commons.utils.ObjectUtil;
import com.flong.commons.web.ResponseResult;

/**
 * 系统异常处理器
 *
 * 创建日期：2012-12-17
 * @author wangk
 */
public class SystemExceptionHandler implements HandlerExceptionResolver, Ordered {
	/** log4j常量 */
	private static final Logger log = Logger.getLogger(SystemExceptionHandler.class);
	
	/** 处理器优先级，数值越小优先级越高 */
    private int order;
    /**jsp页面异常处理视图*/
    private String exceptionView;
  
    @Override
    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getExceptionView() {
		return exceptionView;
	}

	public void setExceptionView(String exceptionView) {
		this.exceptionView = exceptionView;
	}

    /**
     * 异常拦截方法，当控制层的方法出现没有处理的异常时会被当前方法拦截，并记录异常日志，返回带有异常消息的模型视图对象
     * 
     * @see org.springframework.web.servlet.HandlerExceptionResolver#resolveException(
     * 		javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
     *  	java.lang.Object, java.lang.Exception)
     * 创建日期：2012-12-17
     * 修改说明：
     * @author wangk
     */
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response,
    		Object handler, Exception e) {
    	ModelAndView retModelAndView = null;
    	//记录异常日志
    	if(e instanceof IExceptionLogger) {
    		((IExceptionLogger)e).writeTo(log);
    	} else {
    		//按默认的异常日志级别(error)记录
    		log.error(e);
    	}
    	//异常处理视图目前为两种，一种是返回统一的json错误响应格式的（如ajax调用类），还有一种跳转到jsp错误处理页面展示异常信息
    	if(e instanceof IExceptionJspErrorPage){
    		retModelAndView = new ModelAndView(exceptionView);
    		retModelAndView.addObject(ExceptionModelObject.EXCEPTION_MODEL_OBJECT_NAME, new ExceptionModelObject(e));
    	}
    	else{
    		ResponseResult<Object> responseResult = new ResponseResult<Object>(false, getJsonViewErrorMessage(e));
    		retModelAndView = JsonViewFactory.buildJsonView(responseResult);
    	}
    	return retModelAndView;
    }

    /**
     * 获取JSON视图统一格式的错误消息
     *
     * @param e 异常对象
     * @return
     * 创建日期：2013-1-25
     * 修改说明：
     * @author wangk
     */
	public String getJsonViewErrorMessage(Exception e) {
		StringBuilder messageBuilder = new StringBuilder();
		String message = e.getMessage();
		Throwable cause = e.getCause();
		if(message != null && (cause == null || !message.startsWith(cause.getClass().getName()))) {
			messageBuilder.append(message);
		}
		List<String> messageList = getExceptionMessageList(cause);
		if(CollectionUtils.isNotEmpty(messageList)) {
			if(messageBuilder.length() > 0) {
				messageBuilder.append(" ");
			}
			messageBuilder.append("(");
			String separator = " -> ";
			for (String message0 : messageList) {
				messageBuilder.append(message0);
				messageBuilder.append(separator);
			}
			messageBuilder.delete(messageBuilder.length()-separator.length(), messageBuilder.length());
			messageBuilder.append(")");
		}
		if(messageBuilder.length() == 0) {
			messageBuilder.append("请求失败！");
		}
		return messageBuilder.toString();
	}

	/**
	 * 递归获取异常消息集合
	 *
	 * @param e 异常对象
	 * @return
	 * 创建日期：2013-1-25
	 * 修改说明：
	 * @author wangk
	 */
	private List<String> getExceptionMessageList(Throwable e) {
		List<String> messageList = new ArrayList<String>();
		if(e == null) {
			return messageList;
		}
		String message = e.getMessage();
		Throwable cause = e.getCause();
		if(message != null && (cause == null || !message.startsWith(cause.getClass().getName()))) {
			messageList.add(message);
		}
		if(cause != null && !ObjectUtil.isExtends(cause.getClass(), DataAccessException.class)) {
			messageList.addAll(getExceptionMessageList(cause));
		}
		return messageList;
	}

}
