package com.flong.modules.service;

import java.util.List;

import com.flong.commons.persistence.bean.SimplePage;
import com.flong.commons.persistence.dao.EntityDao;
import com.flong.modules.pojo.UserConsColumns;

public interface UserConsColumnsService extends  EntityDao<UserConsColumns>  {
	/**
	 * @Description 查询所有表主外约束信息，包含分页信息
	 * @Author		liangjilong
	 * @Date		2017年3月28日 下午4:49:06
	 * @param page
	 * @param object
	 * @return 		参数
	 * @return 		List<UserConsColumns> 返回类型
	 */
	public  List<UserConsColumns> getUserConsColumnList(SimplePage page,UserConsColumns object);
	/**
	 * @Description 根据表名查询主外约束信息。
	 * @Author		liangjilong
	 * @Date		2017年3月28日 下午4:49:29
	 * @param tableName
	 * @return 		参数
	 * @return 		UserConsColumns 返回类型
	 */
	public  UserConsColumns getUserConsColumnsByName(String tableName);
}
