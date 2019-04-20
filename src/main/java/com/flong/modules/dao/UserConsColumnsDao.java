package com.flong.modules.dao;

import java.util.List;

import com.flong.commons.persistence.bean.SimplePage;
import com.flong.commons.persistence.dao.EntityDao;
import com.flong.modules.pojo.UserConsColumns;

public interface UserConsColumnsDao extends EntityDao<UserConsColumns> {
 
	
	public  List<UserConsColumns> getUserConsColumnList(SimplePage page,UserConsColumns object);
	
	public  UserConsColumns getUserConsColumnsByName(String tableName);
}
