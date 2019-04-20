package com.flong.modules.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flong.commons.persistence.bean.SimplePage;
import com.flong.commons.persistence.dao.impl.EntityDaoSupport;
import com.flong.modules.dao.ViewDao;
import com.flong.modules.pojo.Sequences;
import com.flong.modules.pojo.Views;
import com.flong.modules.service.ViewService;

/**
 * @Description	TODO
 * @ClassName	TableService
 * @Date		2017年3月1日 上午10:48:44
 * @Author		liangjilong
 * @Copyright (c) All Right Reserved , 2017.
 */
	
@Service
@Transactional
@SuppressWarnings("all")
public class ViewServiceImpl extends  EntityDaoSupport<Views> implements ViewService {
	@Autowired ViewDao viewDao;
	/**
	 * @Description  获取数据库所有表
	 * @Author		liangjilong
	 * @Date		2017年2月23日 下午2:30:56
	 * @throws Exception 		参数
	 * @return 		List<String> 返回类型
	 */
	public  List<Views> queryViews(SimplePage page, Views object) {
		return viewDao.queryViews(page,object);
	}
	@Override
	public String dropViewSQLScript(String view_name) {
		List<Views> list = viewDao.queryViews(view_name);
		if(list!=null && list.size()>0){
			StringBuffer buffer = new StringBuffer(); 
			buffer.append("--"+view_name).append(ENTER);
			buffer.append("declare").append(ENTER);
			buffer.append(TAB+TAB).append("iCnt number := 0;").append(ENTER);
			buffer.append(TAB).append("begin").append(ENTER);
			buffer.append(TAB).append(" SELECT count(*) into iCnt  FROM all_views where upper(view_name)=upper('"+view_name+"');").append(ENTER);
			buffer.append(TAB).append(" if iCnt > 0 then ").append(ENTER);
			buffer.append(TAB).append("   execute immediate 'drop VIEW "+view_name+" ';").append(ENTER);
			buffer.append(TAB).append(" end if;").append(ENTER);
			buffer.append(TAB).append("end;").append(ENTER);
			buffer.append("/").append(ENTER);
			return buffer.toString();
		}
		return null;
	}
	@Override
	public String createViewSQLScript(String view_name) {
		List<Views> list = viewDao.queryViews(view_name);
		if(list!=null && list.size()>0){
			StringBuffer buffer = new StringBuffer(); 
			Views view = list.get(0);
			buffer.append("--"+view_name).append(ENTER);
			buffer.append("declare").append(ENTER);
			buffer.append(TAB+TAB).append("iCnt number := 0;").append(ENTER);
			buffer.append(TAB).append("begin").append(ENTER);
			buffer.append(TAB).append("  SELECT count(*) into iCnt  FROM all_views where upper(view_name)=upper('"+view_name+"');").append(ENTER);
			buffer.append(TAB).append(" if iCnt = 0 then ").append(ENTER);
			buffer.append(TAB).append("  execute immediate ' CREATE OR REPLACE VIEW "+view_name+" as ").append(ENTER);
			buffer.append(TAB).append("   "+view.getText()).append(ENTER);
			buffer.append(TAB).append("   '; ").append(ENTER);
			buffer.append(TAB).append(" end if;").append(ENTER);
			buffer.append(TAB).append("end;").append(ENTER);
			buffer.append("/").append(ENTER);
			return buffer.toString();
		}
		return null;
	}
	
	 

	
 
	
 
	

	

}
