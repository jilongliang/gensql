package com.flong.modules.dao;

import java.util.List;

import com.flong.commons.persistence.dao.EntityDao;
import com.flong.modules.pojo.UserTabColumns;

public interface TableColumnsDao extends EntityDao<UserTabColumns> {
	
	/**
	 * @Description 根据表名进行查询表和列信息
	 * @Author		liangjilong
	 * @Date		2017年3月28日 下午4:42:52
	 * @param tableName
	 * @return 		参数
	 * @return 		List<UserTabColumns> 返回类型
	 */
	public List<UserTabColumns> getUserTabColumnsByName(String tableName) ;
}
