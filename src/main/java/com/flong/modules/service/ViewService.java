package com.flong.modules.service;

import java.util.List;

import com.flong.commons.persistence.bean.SimplePage;
import com.flong.commons.persistence.dao.EntityDao;
import com.flong.modules.pojo.Views;

public interface ViewService extends  EntityDao<Views>  {
	
	/**
	 * @Description 查询视图信息，包含分页
	 * @Author		liangjilong
	 * @Date		2017年3月28日 下午4:50:36
	 * @param page
	 * @param object
	 * @return 		参数
	 * @return 		List<Views> 返回类型
	 */
	public  List<Views> queryViews(SimplePage page, Views object) ;

	/**
	 * @Description 根据视图名导出视图删除SQL脚本
	 * @Author		liangjilong
	 * @Date		2017年3月28日 下午4:50:56
	 * @param view_name
	 * @return 		参数
	 * @return 		String 返回类型
	 */
	public String dropViewSQLScript(String view_name);

	/**
	 * @Description  根据视图名导出视图创建SQL脚本
	 * @Author		liangjilong
	 * @Date		2017年3月28日 下午4:51:41
	 * @param view_name
	 * @return 		参数
	 * @return 		String 返回类型
	 */
	public String createViewSQLScript(String view_name);
}
