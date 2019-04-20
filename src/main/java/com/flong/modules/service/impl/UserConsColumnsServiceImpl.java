package com.flong.modules.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flong.commons.persistence.bean.SimplePage;
import com.flong.commons.persistence.dao.impl.EntityDaoSupport;
import com.flong.modules.dao.UserConsColumnsDao;
import com.flong.modules.pojo.UserConsColumns;
import com.flong.modules.service.UserConsColumnsService;

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
public class UserConsColumnsServiceImpl extends  EntityDaoSupport<UserConsColumns> implements UserConsColumnsService {
	@Autowired UserConsColumnsDao userConsColumnsDao;
	
	@Override
	public List<UserConsColumns> getUserConsColumnList(SimplePage page, UserConsColumns object) {
		return userConsColumnsDao.getUserConsColumnList(page, object);
	}

	@Override
	public UserConsColumns getUserConsColumnsByName(String tableName) {
		return userConsColumnsDao.getUserConsColumnsByName(tableName);
	}
	 

	
 
	
 
	

	

}
