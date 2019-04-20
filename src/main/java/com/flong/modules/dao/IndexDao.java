package com.flong.modules.dao;

import java.util.List;

import com.flong.commons.persistence.bean.SimplePage;
import com.flong.commons.persistence.dao.EntityDao;
import com.flong.modules.pojo.UserIndColumns;

public interface IndexDao extends EntityDao<UserIndColumns> {
	
	/**
	 * @Description 查询所有所有信息
	 * @Author		liangjilong
	 * @Date		2017年3月28日 下午5:58:10
	 * @param page
	 * @param object
	 * @return 		参数
	 * @return 		List<UserIndColumns> 返回类型
	 */
	public List<UserIndColumns> queryIndexColumnList(SimplePage page, UserIndColumns object);
	/**
	 * @Description 更加表名和索引名进行查询信息
	 * @Author		liangjilong
	 * @Date		2017年3月28日 下午5:58:16
	 * @param table_name
	 * @param index_name
	 * @return 		参数
	 * @return 		List<UserIndColumns> 返回类型
	 */
	public List<UserIndColumns> queryIndexColumnList(String table_name, String index_name);
}
