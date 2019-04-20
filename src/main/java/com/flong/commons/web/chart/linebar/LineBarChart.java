/**
 * 
 */
package com.flong.commons.web.chart.linebar;

import java.util.List;

/**
 * 线图、柱状图总数据
 * 
 * @author LiuLongbiao
 * @create 2012-11-29
 * 
 */
public class LineBarChart {

	/*
	 * 模型数据
	 */
	private List<LineBarModel> models;
	/*
	 * 配置(包含因子、标题等)
	 */
	private LineBarConfig config;

	public LineBarChart() {
		super();
	}

	public LineBarChart(List<LineBarModel> models, LineBarConfig config) {
		super();
		this.models = models;
		this.config = config;
	}

	public List<LineBarModel> getModels() {
		return models;
	}

	public void setModels(List<LineBarModel> models) {
		this.models = models;
	}

	public LineBarConfig getConfig() {
		return config;
	}

	public void setConfig(LineBarConfig config) {
		this.config = config;
	}

}
