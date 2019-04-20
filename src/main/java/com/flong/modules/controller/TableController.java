package com.flong.modules.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.flong.codegenerator.FileUtils;
import com.flong.commons.persistence.bean.SimplePage;
import com.flong.commons.web.BaseController;
import com.flong.modules.pojo.UserTables;
import com.flong.modules.service.TableService;
/**
 * @author liangjilong
 *
 */
@Controller
@RequestMapping("table")
public class TableController extends BaseController{

	@Autowired TableService tableService;
 
	
	/***
	 * 查询所有Oracle内置数据库表或者视图信息
	 * @param tableIdentifiers
	 * @param page
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="list")
	public String list(UserTables userTables,SimplePage page ,HttpServletRequest request ){
		List<UserTables> findList = tableService.queryTables(page, userTables);
		request.setAttribute("userTables", userTables);
		request.setAttribute("page", page);
		request.setAttribute("list", findList);
		return "modules/tableList";
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
	@RequestMapping(value="createTableSQLScript/{table_name}")
	public String createTableSQLScript(@PathVariable  String table_name,HttpServletRequest request ){
		String resultSQLStr = tableService.createTableSQLScript(table_name);
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
	@RequestMapping(value="dropTableSQLScript/{table_name}")
	public String dropTableSQLScript(@PathVariable  String table_name,HttpServletRequest request ){
		String resultSQLStr = tableService.dropTableSQLScript(table_name);
		request.setAttribute("resultSQLStr", resultSQLStr);
		return "modules/sqlScriptInfo";
	}
	 
	/**
	 * 
	 * @Description 导出SQL 
	 *    本来html的textarea标签可以使用js实现拷贝的，为了模仿iteye或者csdn博客开发页面拷贝功能的，由于找了好几个插件都没试出来，
	 *    而且他们都是使用swf插件实现，在脚本高亮在显示关键字就研究了SyntaxHighlighter导致textarea使用js拷贝功能失效就放弃按钮拷
	 *    贝功能。为了更加方便通过文件流处理，指定电脑硬盘路径和名称，并以.sql后缀文件直接输出，这样更方便帮助我们直接省了Ctrl+C
	 *    和Ctrl+V内容， //再新建文件整理脚本工作动作。cnblogs博客是双击代码，按 Ctrl+C复制代码（此时是jQuery高亮插件自带功能提示）
	 *    参考博客：http://www.cnblogs.com/tylerdonet/p/4533782.html
	 * @Author liangjilong
	 * @Date 2017年3月27日 上午8:21:15
	 * @param table_name
	 * @param request
	 *            参数
	 * @return void 返回类型
	 */
	@RequestMapping(value="exportCreateSQL/{table_name}")
	public void exportCreateSQL(@PathVariable  String table_name,HttpServletResponse response ){
		String resultSQLStr = tableService.createTableSQLScript(table_name);
		//这里为了快速方便使用指定路径实现，并以.sql后缀文件导出到指定硬盘。可以根据自己的情况进行修改，比如：你可以使用SpringMVC下载文件方式实现，
		String path = "C://output-sql/"+"TableSQL-"+formatDate+".sql";
		FileUtils.save(path, resultSQLStr);//保存文件
		JSONObject jsonObject = new JSONObject();//使用fastjson技术实现ajax异步处理
		jsonObject.put("path", path);
		jsonObject.put("result", "1");
		this.writeJson(jsonObject, response);
		
	}

	
	/**
	 * 
	 * @Description 导出SQL 
	 *    本来html的textarea标签可以使用js实现拷贝的，为了模仿iteye或者csdn博客开发页面拷贝功能的，由于找了好几个插件都没试出来，
	 *    而且他们都是使用swf插件实现，在脚本高亮在显示关键字就研究了SyntaxHighlighter导致textarea使用js拷贝功能失效就放弃按钮拷
	 *    贝功能。为了更加方便通过文件流处理，指定电脑硬盘路径和名称，并以.sql后缀文件直接输出，这样更方便帮助我们直接省了Ctrl+C
	 *    和Ctrl+V内容， //再新建文件整理脚本工作动作。cnblogs博客是双击代码，按 Ctrl+C复制代码（此时是jQuery高亮插件自带功能提示）
	 *    参考博客：http://www.cnblogs.com/tylerdonet/p/4533782.html
	 * @Author liangjilong
	 * @Date 2017年3月27日 上午8:21:15
	 * @param table_name
	 * @param request
	 *            参数
	 * @return void 返回类型
	 */
	@RequestMapping(value="exportDropSQL/{table_name}")
	public void exportDropSQL(@PathVariable  String table_name,HttpServletResponse response ){
		String resultSQLStr = tableService.dropTableSQLScript(table_name);
		//这里为了快速方便使用指定路径实现，并以.sql后缀文件导出到指定硬盘。可以根据自己的情况进行修改，比如：你可以使用SpringMVC下载文件方式实现，
		String path = "C://output-sql/"+"TableSQL-"+formatDate+".sql";
		FileUtils.save(path, resultSQLStr);//保存文件
		JSONObject jsonObject = new JSONObject();//使用fastjson技术实现ajax异步处理
		jsonObject.put("path", path);
		jsonObject.put("result", "1");
		this.writeJson(jsonObject, response);
		
	}
	
}
