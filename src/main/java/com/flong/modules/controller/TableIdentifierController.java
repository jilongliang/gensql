package com.flong.modules.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.flong.commons.persistence.bean.SimplePage;
import com.flong.commons.web.BaseController;
import com.flong.modules.pojo.TableIdentifiers;
import com.flong.modules.service.TableIdentifiersService;
/**
 * @author liangjilong
 *
 */
@Controller
@RequestMapping("tableIdentifiers")
public class TableIdentifierController extends BaseController{

	@Autowired TableIdentifiersService tableIdentifiersService;

	/***
	 * 
	 * @param tableIdentifiers
	 * @param page
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="list")
	public String list(TableIdentifiers tableIdentifiers,SimplePage page ,HttpServletRequest request ,HttpServletResponse response){
	 
		List<TableIdentifiers> findList = tableIdentifiersService.findList(page, tableIdentifiers);
		request.setAttribute("tableIdentifiers", tableIdentifiers);
		request.setAttribute("page", page);
		request.setAttribute("list", findList);
		
		return "modules/tableIdentifierList";
	}
	
	 
	

}
