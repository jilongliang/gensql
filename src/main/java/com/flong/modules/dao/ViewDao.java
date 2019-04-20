package com.flong.modules.dao;

import java.util.List;

import com.flong.commons.persistence.bean.SimplePage;
import com.flong.commons.persistence.dao.EntityDao;
import com.flong.modules.pojo.Views;

public interface ViewDao extends EntityDao<Views> {
	
	/**
	 * @Description 带分页查询信息
	 * @Author		liangjilong
	 * @Date		2017年3月28日 下午3:00:07
	 * @param page
	 * @param object
	 * @return 		参数
	 * @return 		List<Views> 返回类型
	 */
	public List<Views> queryViews(SimplePage page, Views object);

	/**
	 * @Description 根据视图名称查询视图信息
	 * @Author		liangjilong
	 * @Date		2017年3月28日 下午2:59:46
	 * @param view_name
	 * @return 		参数
	 * @return 		List<Views> 返回类型
	 */
	public List<Views> queryViews(String view_name);
}
