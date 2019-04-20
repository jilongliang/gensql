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
import com.flong.modules.pojo.Sequences;
import com.flong.modules.service.SequenceService;
/**
 * @author liangjilong
 *
 */
@Controller
@RequestMapping("sequence")
public class SequenceController extends BaseController{

	@Autowired SequenceService sequenceService;

	/***
	 * 
	 * @param tableIdentifiers
	 * @param page
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="list")
	public String list(Sequences sequences,SimplePage page ,HttpServletRequest request ,HttpServletResponse response){
		List<Sequences> findList = sequenceService.querySequences(page, sequences);
		request.setAttribute("sequence", sequences);
		request.setAttribute("page", page);
		request.setAttribute("list", findList);
		return "modules/sequenceList";
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
	@RequestMapping(value="dropSequenceSQLScript/{sequence_name}")
	public String dropSequenceSQLScript(@PathVariable String sequence_name,HttpServletRequest request ){
		String resultSQLStr = sequenceService.dropSequenceSQLScript(sequence_name);
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
	@RequestMapping(value="createSequenceSQLScript/{sequence_name}")
	public String createIndexSQLScript(@PathVariable String sequence_name,HttpServletRequest request ){
		String resultSQLStr = sequenceService.createSequenceSQLScript(sequence_name);
		request.setAttribute("resultSQLStr", resultSQLStr);
		return "modules/sqlScriptInfo";
	}

	@RequestMapping(value="exportCreateSQL/{sequence_name}")
	public void exportCreateSQL(@PathVariable  String sequence_name,HttpServletResponse response ){
		String resultSQLStr = sequenceService.createSequenceSQLScript(sequence_name);
		//这里为了快速方便使用指定路径实现，并以.sql后缀文件导出到指定硬盘。可以根据自己的情况进行修改，比如：你可以使用SpringMVC下载文件方式实现，
		String path = "C://output-sql/"+"exportCreateSequenceSQL-"+formatDate+".sql";
		FileUtils.save(path, resultSQLStr);//保存文件
		JSONObject jsonObject = new JSONObject();//使用fastjson技术实现ajax异步处理
		jsonObject.put("path", path);
		jsonObject.put("result", "1");
		this.writeJson(jsonObject, response);
		
	}
	
	@RequestMapping(value="exportDropSQL/{sequence_name}")
	public void exportDropSQL(@PathVariable  String sequence_name,HttpServletResponse response ){
		String resultSQLStr = sequenceService.dropSequenceSQLScript(sequence_name);
		//这里为了快速方便使用指定路径实现，并以.sql后缀文件导出到指定硬盘。可以根据自己的情况进行修改，比如：你可以使用SpringMVC下载文件方式实现，
		String path = "C://output-sql/"+"exportDropSequenceSQL-"+formatDate+".sql";
		FileUtils.save(path, resultSQLStr);//保存文件
		JSONObject jsonObject = new JSONObject();//使用fastjson技术实现ajax异步处理
		jsonObject.put("path", path);
		jsonObject.put("result", "1");
		this.writeJson(jsonObject, response);
	}

}
