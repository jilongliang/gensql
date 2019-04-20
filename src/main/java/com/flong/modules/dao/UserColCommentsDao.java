package com.flong.modules.dao;

import java.util.List;

import com.flong.commons.persistence.dao.EntityDao;
import com.flong.modules.pojo.UserColComments;

public interface UserColCommentsDao extends EntityDao<UserColComments> {
	/**
	 * @Description 根据表名进行查询列名注释
	 * @Author		liangjilong
	 * @Date		2017年3月28日 下午4:42:19
	 * @param tableName
	 * @return 		参数
	 * @return 		List<UserColComments> 返回类型
	 */
	public List<UserColComments> getUserColCommentsByName(String tableName) ;
}
