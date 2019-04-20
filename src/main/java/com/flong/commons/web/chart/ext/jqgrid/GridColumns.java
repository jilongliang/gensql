/**
 * 
 */
package com.flong.commons.web.chart.ext.jqgrid;

import java.util.List;

/**
 * jqGrid列配置
 * 
 * @author LiuLongbiao
 * @create 2012-12-10
 * 
 */
public class GridColumns {

	/*
	 * 列表头名称
	 */
	private List<String> colNames;
	/*
	 * 列模型，为与jqGrid一致，没有使用复数形式
	 */
	private List<ColModel> colModel;

	public List<String> getColNames() {
		return colNames;
	}

	public void setColNames(List<String> colNames) {
		this.colNames = colNames;
	}

	public List<ColModel> getColModel() {
		return colModel;
	}

	public void setColModel(List<ColModel> colModel) {
		this.colModel = colModel;
	}

}
