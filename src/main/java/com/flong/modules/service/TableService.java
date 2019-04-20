package com.flong.modules.service;

import java.util.List;

import com.flong.commons.persistence.bean.SimplePage;
import com.flong.commons.persistence.dao.EntityDao;
import com.flong.modules.pojo.UserTables;

public interface TableService extends  EntityDao<UserTables>  {
		
	/**
	 * @Description 查询所有表信息包含分页
	 * @Author		liangjilong
	 * @Date		2017年3月28日 下午4:43:34
	 * @param page
	 * @param object
	 * @return 		参数
	 * @return 		List<UserTables> 返回类型
	 */
	public  List<UserTables> queryTables(SimplePage page, UserTables object) ;
	
	/**
	 * @Description 根据表名查询表信息
	 * @Author		liangjilong
	 * @Date		2017年3月28日 下午4:43:57
	 * @param tableName
	 * @return 		参数
	 * @return 		List<UserTables> 返回类型
	 */
	public List<UserTables> queryTableByName(String tableName);
	
	/**
	 * @Description 根据表名进行生成数据库存在表的创建表的SQL脚本，如果是页面传多个表的时候可以遍历此方法
	 * @Author		liangjilong
	 * @Date		2017年3月28日 下午4:44:15
	 * @param tableName
	 * @return 		参数
	 * @return 		String 返回类型
	 */
	public String createTableSQLScript(String tableName);

	/**
	 * @Description 根据表名进行生成数据库存在表的删除表的的SQL脚本，如果是页面传多个表的时候可以遍历此方法
	 * @Author		liangjilong
	 * @Date		2017年3月28日 下午4:55:02
	 * @param table_name
	 * @return 		参数
	 * @return 		String 返回类型
	 */
	public String dropTableSQLScript(String table_name);
	
}
