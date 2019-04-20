package com.flong.modules.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.flong.codegenerator.FileUtils;
import com.flong.commons.persistence.bean.SimplePage;
import com.flong.commons.web.BaseController;
import com.flong.modules.pojo.UserIndColumns;
import com.flong.modules.service.IndexService;

/***
 *@Author:liangjilong
 *@Date:2015年12月22日下午12:17:05
 *@Email:jilongliang@sina.com
 *@Version:1.0
 *@CopyRight(c)liangjilong
 *@Description:
 */

@Controller
@RequestMapping("index")
public class IndexController  extends BaseController{
	@Autowired IndexService indexService;
	
	/***
	 *跳转到jsp页面..
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="list")
	public ModelAndView list(UserIndColumns userIndColumns,SimplePage page ,HttpServletRequest request ,HttpServletResponse response){
		List<UserIndColumns> findList = indexService.queryIndexColumnList(page, userIndColumns);
		request.setAttribute("userIndColumns", userIndColumns);
		request.setAttribute("page", page);
		request.setAttribute("list", findList);		
		
		return new ModelAndView("modules/indexList");
	}

	
	/**
	 * @Description 根据表名进行查询表信息
	 * @Author		liangjilong
	 * @Date		2017年3月27日 上午8:23:14
	 * @param table_name
	 * @param request
	 * @return 		参数
	 * @return 		String 返回类型
	 */
	@RequestMapping(value="createIndexSQLScript/{table_name}/{index_name}/{columns_name}")
	public String createIndexSQLScript(@PathVariable String table_name,@PathVariable String index_name,@PathVariable String columns_name,HttpServletRequest request ){
		String resultSQLStr = indexService.createIndexSQLScript(table_name,index_name,columns_name);
		request.setAttribute("resultSQLStr", resultSQLStr);
		return "modules/sqlScriptInfo";
	}
	
	/**
	 * @Description 根据表名进行查询表信息
	 * @Author		liangjilong
	 * @Date		2017年3月27日 上午8:23:14
	 * @param table_name
	 * @param request
	 * @return 		参数
	 * @return 		String 返回类型
	 */
	@RequestMapping(value="dropIndexSQLScript/{table_name}/{index_name}")
	public String dropIndexQLScript(@PathVariable String table_name,@PathVariable String index_name,HttpServletRequest request ){
		String resultSQLStr = indexService.dropIndexSQLScript(table_name,index_name);
		request.setAttribute("resultSQLStr", resultSQLStr);
		return "modules/sqlScriptInfo";
	}
	
	

	@RequestMapping(value="exportDropSQL/{table_name}/{index_name}")
	public void exportDropSQL(@PathVariable String table_name,@PathVariable String index_name,HttpServletResponse response){
		String resultSQLStr = indexService.dropIndexSQLScript(table_name,index_name);
		//这里为了快速方便使用指定路径实现，并以.sql后缀文件导出到指定硬盘。可以根据自己的情况进行修改，比如：你可以使用SpringMVC下载文件方式实现，
		String path = "C://output-sql/"+"exportCreateSequenceSQL-"+formatDate+".sql";
		FileUtils.save(path, resultSQLStr);//保存文件
		JSONObject jsonObject = new JSONObject();//使用fastjson技术实现ajax异步处理
		jsonObject.put("path", path);
		jsonObject.put("result", "1");
		this.writeJson(jsonObject, response);
		
	}
	
	@RequestMapping(value="exportCreateSQL/{table_name}/{index_name}/{columns_name}")
	public void exportCreateSQL(@PathVariable String table_name,@PathVariable String index_name,@PathVariable String columns_name,HttpServletResponse response ){
		String resultSQLStr = indexService.createIndexSQLScript(table_name,index_name,columns_name);
		//这里为了快速方便使用指定路径实现，并以.sql后缀文件导出到指定硬盘。可以根据自己的情况进行修改，比如：你可以使用SpringMVC下载文件方式实现，
		String path = "C://output-sql/"+"exportDropSequenceSQL-"+formatDate+".sql";
		FileUtils.save(path, resultSQLStr);//保存文件
		JSONObject jsonObject = new JSONObject();//使用fastjson技术实现ajax异步处理
		jsonObject.put("path", path);
		jsonObject.put("result", "1");
		this.writeJson(jsonObject, response);
	}

}
