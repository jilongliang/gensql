package com.flong.commons.web;

import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.flong.commons.lang.DateUtils;

/**
 * 控制器支持类
 * @author liangjilong
 * @version 2013-3-23
 */
@SuppressWarnings("all")
public abstract class BaseController {
	protected String BASE_PATH = "modules/";
	////redirect和RedirectView一样
	public static final String Redirect="redirect:";
	protected static final String ENTER = "\n";
	protected static final String TAB = "    ";
	protected static String COMMA=",";
	protected String formatDate = DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");//给定一个时间编号的文件名称
	
	/**
	 * 验证Bean实例对象
	 */
	/**
	 * 添加Model消息
	 * @param message
	 */
	protected void addMessage(Model model, String... messages) {
		StringBuilder sb = new StringBuilder();
		for (String message : messages){
			sb.append(message).append(messages.length>1?"<br/>":"");
		}
		model.addAttribute("message", sb.toString());
	}
	
	/**
	 * 添加Flash消息
	 * @param message
	 */
	protected void addMessage(RedirectAttributes redirectAttributes, String... messages) {
		StringBuilder sb = new StringBuilder();
		for (String message : messages){
			sb.append(message).append(messages.length>1?"<br/>":"");
		}
		redirectAttributes.addFlashAttribute("message", sb.toString());
	}
	
	/**
	 * 初始化数据绑定
	 * 1. 将所有传递进来的String进行HTML编码，防止XSS攻击
	 * 2. 将字段中Date类型转换为String类型
	 */
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		// String类型转换，将所有传递进来的String进行HTML编码，防止XSS攻击
		binder.registerCustomEditor(String.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				setValue(text == null ? null : StringEscapeUtils.escapeHtml4(text.trim()));
			}
			@Override
			public String getAsText() {
				Object value = getValue();
				return value != null ? value.toString() : "";
			}
		});
		// Date 类型转换
		binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				setValue(DateUtils.parseDate(text));
			}
		});
	}
	 
	/************
	 * 打印JSON
	 * @param o
	 * @param response
	 */
	public void writeJson(Object o, HttpServletResponse response) {
		writeJson(JSON.toJSONString(o), response);
	}
	/************
	 * 打印JSON
	 * @param o
	 * @param response
	 */
	public void writeJson(String o, HttpServletResponse response) {
		try {
			response.getWriter().write(o);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/************
	 * 打印JSON
	 * @param o
	 * @param response
	 */
	  public static void writeJSON(Object json, HttpServletResponse response)
	  {
		try {
			response.setContentType("text/json;charset=utf-8");
			byte[] jsonBytes = json.toString().getBytes("utf-8");

			response.setContentLength(jsonBytes.length);
			response.getOutputStream().write(jsonBytes);
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (Exception e) {
		} finally {
			try {
				response.getOutputStream().flush();
				response.getOutputStream().close();
			} catch (IOException e) {
			}
		}
	}
	
}
