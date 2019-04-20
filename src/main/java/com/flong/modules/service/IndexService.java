package com.flong.modules.service;

import java.util.List;

import com.flong.commons.persistence.bean.SimplePage;
import com.flong.commons.persistence.dao.EntityDao;
import com.flong.modules.pojo.UserIndColumns;

public interface IndexService extends  EntityDao<UserIndColumns>  {
	
	/**
	 * @Description TODO
	 * @Author		liangjilong
	 * @Date		2017年3月28日 下午5:59:50
	 * @param page
	 * @param object
	 * @return 		参数
	 * @return 		List<UserIndColumns> 返回类型
	 */
	public List<UserIndColumns> queryIndexColumnList(SimplePage page, UserIndColumns object) ;

	public String createIndexSQLScript(String table_name, String index_name,String columns_name);

	public String dropIndexSQLScript(String table_name, String index_name);
}
