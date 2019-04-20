package com.flong.modules.service;

import java.util.List;

import com.flong.commons.persistence.bean.SimplePage;
import com.flong.commons.persistence.dao.EntityDao;
import com.flong.modules.pojo.Sequences;

public interface SequenceService extends  EntityDao<Sequences>  {
	/**
	 * @Description 查询序列信息，包含分页
	 * @Author		liangjilong
	 * @Date		2017年3月28日 下午4:45:43
	 * @param page
	 * @param object
	 * @return 		参数
	 * @return 		List<Sequences> 返回类型
	 */
	public  List<Sequences> querySequences(SimplePage page, Sequences object) ;

	/**
	 * @Description 生成序列创建Sql脚本
	 * @Author		liangjilong
	 * @Date		2017年3月28日 下午4:46:09
	 * @param sequence_name
	 * @return 		参数
	 * @return 		String 返回类型
	 */
	public String createSequenceSQLScript(String sequence_name);

	/**
	 * @Description 生成序列删除Sql脚本
	 * @Author		liangjilong
	 * @Date		2017年3月28日 下午4:46:27
	 * @param sequence_name
	 * @return 		参数
	 * @return 		String 返回类型
	 */
	public String dropSequenceSQLScript(String sequence_name);
}
