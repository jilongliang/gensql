package com.flong.modules.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.flong.codegenerator.FileUtils;
import com.flong.commons.lang.DateUtils;
import com.flong.commons.persistence.bean.SimplePage;
import com.flong.commons.web.BaseController;
import com.flong.modules.pojo.Views;
import com.flong.modules.service.ViewService;
/**
 * @author liangjilong
 *
 */
@Controller
@RequestMapping("view")
public class ViewController extends BaseController{
	@Autowired ViewService viewService;

	/***
	 * 
	 * @param tableIdentifiers
	 * @param page
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="list")
	public String list(Views views,SimplePage page ,HttpServletRequest request ,HttpServletResponse response){
		List<Views> findList = viewService.queryViews(page, views);
		request.setAttribute("views", views);
		request.setAttribute("page", page);
		request.setAttribute("list", findList);
		return "modules/viewList";
	}

	
	
	/**
	 * @Description 
	 * @Author		liangjilong
	 * @Date		2017年3月27日 上午8:23:14
	 * @param table_name
	 * @param request
	 * @return 		参数
	 * @return 		String 返回类型
	 */
	@RequestMapping(value="dropViewSQLScript/{view_name}")
	public String dropViewSQLScript(@PathVariable String view_name,HttpServletRequest request ){
		String resultSQLStr = viewService.dropViewSQLScript(view_name);
		request.setAttribute("resultSQLStr", resultSQLStr);
		return "modules/sqlScriptInfo";
	}
	
	/**
	 * @Description 
	 * @Author		liangjilong
	 * @Date		2017年3月27日 上午8:23:14
	 * @param table_name
	 * @param request
	 * @return 		参数
	 * @return 		String 返回类型
	 */
	@RequestMapping(value="createViewSQLScript/{view_name}")
	public String createViewSQLScript(@PathVariable String view_name,HttpServletRequest request ){
		String resultSQLStr = viewService.createViewSQLScript(view_name);
		request.setAttribute("resultSQLStr", resultSQLStr);
		return "modules/sqlScriptInfo";
	}
	
	
	@RequestMapping(value="exportCreateSQL/{view_name}")
	public void exportCreateSQL(@PathVariable  String view_name,HttpServletResponse response ){
		String resultSQLStr = viewService.createViewSQLScript(view_name);
		
		//这里为了快速方便使用指定路径实现，并以.sql后缀文件导出到指定硬盘。可以根据自己的情况进行修改，比如：你可以使用SpringMVC下载文件方式实现，
		String path = "C://output-sql/"+"exportCreateSQL-"+formatDate+".sql";
		FileUtils.save(path, resultSQLStr);//保存文件
		JSONObject jsonObject = new JSONObject();//使用fastjson技术实现ajax异步处理
		jsonObject.put("path", path);
		jsonObject.put("result", "1");
		this.writeJson(jsonObject, response);
		
	}
	
	@RequestMapping(value="exportDropSQL/{view_name}")
	public void exportDropSQL(@PathVariable  String view_name,HttpServletResponse response ){
		String resultSQLStr = viewService.dropViewSQLScript(view_name);
		String formatDate = DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");//给定一个时间编号的文件名称
		//这里为了快速方便使用指定路径实现，并以.sql后缀文件导出到指定硬盘。可以根据自己的情况进行修改，比如：你可以使用SpringMVC下载文件方式实现，
		String path = "C://output-sql/"+"exportDropSQL-"+formatDate+".sql";
		FileUtils.save(path, resultSQLStr);//保存文件
		JSONObject jsonObject = new JSONObject();//使用fastjson技术实现ajax异步处理
		jsonObject.put("path", path);
		jsonObject.put("result", "1");
		this.writeJson(jsonObject, response);
	}
}
