package com.flong.modules.dao;

import java.util.List;

import com.flong.commons.persistence.dao.EntityDao;
import com.flong.modules.pojo.UserTabComments;

public interface UserTabCommentsDao extends EntityDao<UserTabComments> {
	
	/**
	 * @Description 根据表名进行查询表注释
	 * @Author		liangjilong
	 * @Date		2017年3月28日 下午4:41:54
	 * @param tableName
	 * @return 		参数
	 * @return 		List<UserTabComments> 返回类型
	 */
	public List<UserTabComments> getUserTabCommentsByName(String tableName) ;
}
