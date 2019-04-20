package com.flong.modules.dao;

import java.util.List;

import com.flong.commons.persistence.bean.SimplePage;
import com.flong.commons.persistence.dao.EntityDao;
import com.flong.modules.pojo.UserTables;

public interface TableDao extends EntityDao<UserTables> {
	
	/**
	 * @Description  根据表名查询表所有信息，包含分页
	 * @Author		liangjilong
	 * @Date		2017年3月28日 下午4:53:01
	 * @param tableName
	 * @return 		参数
	 * @return 		List<UserTables> 返回类型
	 */
	public List<UserTables> queryTables(SimplePage page, UserTables object);
	
	/**
	 * @Description  根据表名查询表信息
	 * @Author		liangjilong
	 * @Date		2017年3月28日 下午4:53:01
	 * @param tableName
	 * @return 		参数
	 * @return 		List<UserTables> 返回类型
	 */
	public List<UserTables> queryTableByName(String tableName);
	
	
	/**
	 * @Description 根据表名创建表SQL脚本
	 * @Author		liangjilong
	 * @Date		2017年3月28日 下午4:52:29
	 * @param tableName
	 * @return 		参数
	 * @return 		String 返回类型
	 */
	public String createTableSQLScript(String tableName);
	
}
