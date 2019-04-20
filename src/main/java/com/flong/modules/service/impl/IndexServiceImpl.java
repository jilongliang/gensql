package com.flong.modules.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flong.commons.persistence.bean.SimplePage;
import com.flong.commons.persistence.dao.impl.EntityDaoSupport;
import com.flong.modules.dao.IndexDao;
import com.flong.modules.pojo.UserIndColumns;
import com.flong.modules.service.IndexService;

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
public class IndexServiceImpl extends  EntityDaoSupport<UserIndColumns> implements IndexService {
	@Autowired IndexDao indexDao;
	/**
	 * @Description  获取数据库所有表
	 * @Author		liangjilong
	 * @Date		2017年2月23日 下午2:30:56
	 * @throws Exception 		参数
	 * @return 		List<String> 返回类型
	 */
	public  List<UserIndColumns> queryIndexColumnList(SimplePage page, UserIndColumns object) {
		return indexDao.queryIndexColumnList(page,object);
	}
	
	@Override
	public String createIndexSQLScript(String table_name, String index_name,String columns_name) {
		List<UserIndColumns> list = indexDao.queryIndexColumnList(table_name,index_name);
		
		StringBuffer buffer = new StringBuffer(); 
		if(list!=null && list.size()>0){
			buffer.append("--"+table_name).append(ENTER);
			buffer.append("declare").append(ENTER);
			buffer.append(TAB+TAB).append("iCnt number := 0;").append(ENTER);
			buffer.append(TAB).append("begin").append(ENTER);
			buffer.append(TAB).append(" select count(*) into iCnt from user_ind_columns where lower(index_name)= lower('"+index_name+"') and lower(table_name)=lower('"+table_name+"');").append(ENTER);
			buffer.append(TAB).append(" if iCnt = 0 then ").append(ENTER);
			buffer.append(TAB).append("    execute immediate 'create unique index "+index_name+" on "+table_name+"("+columns_name+") ' ; ").append(ENTER);
			buffer.append(TAB).append(" end if;").append(ENTER);
			buffer.append("end;").append(ENTER);
			buffer.append("/").append(ENTER);
			return buffer.toString();
		}
		return  null;
	}

	@Override
	public String dropIndexSQLScript(String table_name, String index_name) {
		List<UserIndColumns> list = indexDao.queryIndexColumnList(table_name,index_name);
		StringBuffer buffer = new StringBuffer(); 
		if(list!=null && list.size()>0){
			buffer.append("--"+table_name).append(ENTER);
			buffer.append("declare").append(ENTER);
			buffer.append(TAB+TAB).append("iCnt number := 0;").append(ENTER);
			buffer.append(TAB).append("begin").append(ENTER);
			buffer.append(TAB).append(" select count(*) into iCnt from user_ind_columns where lower(index_name)= lower('"+index_name+"') and lower(table_name)=lower('"+table_name+"');").append(ENTER);
			buffer.append(TAB).append(" if iCnt > 0 then ").append(ENTER);
			buffer.append(TAB).append("    execute immediate 'drop index "+index_name+"' ;").append(ENTER);
			buffer.append(TAB).append(" end if;").append(ENTER);
			buffer.append("end;").append(ENTER);
			buffer.append("/").append(ENTER);
			return buffer.toString();
		}
		return  null;
	}

	

}
