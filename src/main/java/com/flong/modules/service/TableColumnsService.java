package com.flong.modules.service;

import java.util.List;

import com.flong.commons.persistence.dao.EntityDao;
import com.flong.modules.pojo.UserTabColumns;

public interface TableColumnsService extends  EntityDao<UserTabColumns>  {
	
	public List<UserTabColumns> getUserTabColumns(String tableName) ;
	
}
