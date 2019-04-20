package com.flong.modules.service;

import java.util.List;

import com.flong.commons.persistence.dao.EntityDao;
import com.flong.modules.pojo.UserColComments;

public interface UserColCommentsService extends  EntityDao<UserColComments>  {
	
	/**
	 * @Description 根据表名进行查询列注释
	 * @Author		liangjilong
	 * @Date		2017年3月28日 下午4:47:15
	 * @param tableName
	 * @return 		参数
	 * @return 		List<UserColComments> 返回类型
	 */
	public List<UserColComments> getUserColCommentsByName(String tableName) ;
}
