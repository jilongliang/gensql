package com.flong.modules.service;

import java.util.List;

import com.flong.commons.persistence.dao.EntityDao;
import com.flong.modules.pojo.UserTabComments;

public interface UserTabCommentsService extends  EntityDao<UserTabComments>  {
	
	/**
	 * @Description 根据表名查询表注释信息
	 * @Author		liangjilong
	 * @Date		2017年3月28日 下午4:50:12
	 * @param tableName
	 * @return 		参数
	 * @return 		List<UserTabComments> 返回类型
	 */
	public List<UserTabComments> getUserTabCommentsByName(String tableName) ;
}
