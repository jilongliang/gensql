package com.flong.commons.utils;

import org.springframework.web.servlet.ModelAndView;

import com.flong.commons.web.ResponseResult;

/**
 * JSON视图工厂类，用于构建控制层响应的JSON视图对象
 *
 * 创建日期：2012-12-17
 * @author wangk
 */
public class JsonViewFactory {
	/** 视图名称，使用系统配置的统一名称：jsonView */
	public static final String VIEW_NAME = "jsonView";
	/** 模型数据的Key值，使用系统配置的统一Key值：responseResult */
	public static final String MODEL_KEY = "responseResult";
	
	/**
	 * 构建JSON视图对象，用于请求成功但不需要响应数据和响应消息的情况
	 *
	 * @return
	 * 创建日期：2012-12-17
	 * 修改说明：
	 * @author wangk
	 */
	public static ModelAndView buildJsonView() {
		return buildJsonView(new ModelAndView());
	}
	
	/**
	 * 构建JSON视图对象，用于请求成功且需要响应数据的情况
	 *
	 * @param <T>  响应数据类型参数
	 * @param data 响应数据
	 * @return
	 * 创建日期：2012-12-17
	 * 修改说明：
	 * @author wangk
	 */
	public static <T> ModelAndView buildJsonView(T data) {
    	return buildJsonView(new ModelAndView(), data);
	}
	
	/**
	 * 构建JSON视图对象，需要指定完整的响应结果对象
	 *
	 * @param <T>            响应数据类型参数
	 * @param responseResult 响应结果
	 * @return
	 * 创建日期：2012-12-17
	 * 修改说明：
	 * @author wangk
	 */
	public static <T> ModelAndView buildJsonView(ResponseResult<T> responseResult) {
		return buildJsonView(new ModelAndView(), responseResult);
	}
	
	/**
	 * 在指定的modelAndView对象上构建JSON视图对象，用于设置视图名和添加响应结果，下同 
	 *
	 * @param modelAndView 模型视图对象
	 * @return
	 * 创建日期：2012-12-17
	 * 修改说明：
	 * @author wangk
	 */
	public static ModelAndView buildJsonView(ModelAndView modelAndView) {
		return buildJsonView(modelAndView, new ResponseResult<Object>());
	}
	
	/**
	 * 构建JSON视图对象，用法说明见buildJsonView(data)
	 *
	 * @param <T>
	 * @param modelAndView 模型视图对象
	 * @param data         响应数据
	 * @return
	 * 创建日期：2012-12-17
	 * 修改说明：
	 * @author wangk
	 */
	public static <T> ModelAndView buildJsonView(ModelAndView modelAndView, T data) {
    	return buildJsonView(modelAndView, new ResponseResult<T>(data));
	}
	
	/**
	 * 构建JSON视图对象，用法说明见buildJsonView(responseResult)
	 *
	 * @param <T>
	 * @param modelAndView   模型视图对象
	 * @param responseResult 响应结果
	 * @return
	 * 创建日期：2012-12-17
	 * 修改说明：
	 * @author wangk
	 */
	public static <T> ModelAndView buildJsonView(ModelAndView modelAndView, ResponseResult<T> responseResult) {
		modelAndView.setViewName(VIEW_NAME);
    	modelAndView.addObject(MODEL_KEY, responseResult);
    	return modelAndView;
	}

	/**
	 * 构建成功消息JSON视图(没有data值)
	 * <p>
	 * 失败消息通常由抛出异常得到
	 * 
	 * @author LiuLongbiao
	 * @create 2012-12-21
	 * @param message
	 * @return
	 */
	public static ModelAndView buildSuccessMessageJsonView(String message) {
		return buildJsonView(new ResponseResult<Object>(true, message));
	}

	/**
	 * 构建原始JSON视图，data的数据即返回的JSON
	 * 
	 * @param data
	 * @return
	 */
	public static <T> ModelAndView buildRawJsonView(T data) {
		ModelAndView mav = new ModelAndView(VIEW_NAME);
		mav.addObject(MODEL_KEY, data);
		return mav;
	}

}
