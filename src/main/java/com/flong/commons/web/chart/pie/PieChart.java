/**
 * 
 */
package com.flong.commons.web.chart.pie;

import java.util.List;

/**
 * 饼图总数据
 * 
 * @author LiuLongbiao
 * @create 2012-11-30
 * 
 */
public class PieChart {
	/*
	 * 模型数据(包含名称、数值及因子配置等)
	 */
	private List<PieModel> models;
	/*
	 * 配置(包含标题、半径等)
	 */
	private PieConfig config;

	public List<PieModel> getModels() {
		return models;
	}

	public void setModels(List<PieModel> models) {
		this.models = models;
	}

	public PieConfig getConfig() {
		return config;
	}

	public void setConfig(PieConfig config) {
		this.config = config;
	}

}
