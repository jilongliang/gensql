package com.flong.modules.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flong.commons.persistence.dao.impl.EntityDaoSupport;
import com.flong.modules.dao.TableColumnsDao;
import com.flong.modules.pojo.UserTabColumns;
import com.flong.modules.service.TableColumnsService;

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
public class TableColumnsServiceImpl extends  EntityDaoSupport<UserTabColumns> implements TableColumnsService {
	
	@Autowired TableColumnsDao tableColumnsDao;

	@Override
	public List<UserTabColumns> getUserTabColumns(String tableName) {
		return tableColumnsDao.getUserTabColumnsByName(tableName);
	}
	 
	

}
