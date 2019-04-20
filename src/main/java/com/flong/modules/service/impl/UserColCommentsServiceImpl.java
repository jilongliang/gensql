package com.flong.modules.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flong.commons.persistence.dao.impl.EntityDaoSupport;
import com.flong.modules.dao.UserColCommentsDao;
import com.flong.modules.pojo.UserColComments;
import com.flong.modules.service.UserColCommentsService;

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
public class UserColCommentsServiceImpl extends  EntityDaoSupport<UserColComments> implements UserColCommentsService {

	@Autowired UserColCommentsDao userColCommentsDao;
	@Override
	public List<UserColComments> getUserColCommentsByName(String tableName) {
		return userColCommentsDao.getUserColCommentsByName(tableName);
	}
	 
	 
	

}
