/**
 * 
 */
package com.flong.commons.web.chart.linebar;

import static com.flong.commons.web.chart.core.ChartConstant.*;

import java.util.List;

import com.flong.commons.web.chart.core.ChartElement;

/**
 * 柱状图、线图配置项
 * 
 * @author LiuLongbiao
 * @create 2012-11-29
 * 
 */
public class LineBarConfig extends ChartElement {

	/**
	 * 初始化配置项
	 */
	public LineBarConfig() {
		super();
		// 设置默认值
		this.xLabel("").yLabel("").attr("markerSize", "11")
				.attr("depth3D", "10").attr("columnSpacing", 0);
	}

	/**
	 * 根据因子列表和单位标题初始化配置项
	 * 
	 * @param factors
	 * @param unit
	 */
	public LineBarConfig(List<LineBarFactor> factors, String unit) {
		this();
		this.factors(factors).yLabel(unit);
	}

	@Override
	public LineBarConfig attr(String key, Object value) {
		return (LineBarConfig) super.attr(key, value);
	}

	/**
	 * 设置因子列表
	 * 
	 * @author LiuLongbiao
	 * @create 2012-11-29
	 * @param factors
	 * @return
	 */
	public LineBarConfig factors(List<LineBarFactor> factors) {
		return this.attr(KEY_FACTORS, factors);
	}

	/**
	 * 设置警戒线列表
	 * 
	 * @author LiuLongbiao
	 * @create 2012-11-29
	 * @param dataLines
	 * @return
	 */
	public LineBarConfig dataLines(List<DataLine> dataLines) {
		return this.attr(KEY_DATA_LINES, dataLines);
	}

	/**
	 * 设置额外数据
	 * 
	 * @author LiuLongbiao
	 * @create 2012-11-29
	 * @param extraDatas
	 * @return
	 */
	public LineBarConfig extraDatas(List<ChartElement> extraDatas) {
		return this.attr(KEY_DATA_LINES, extraDatas);
	}

	/**
	 * 设置主标题
	 * 
	 * @author LiuLongbiao
	 * @create 2012-11-29
	 * @param title
	 * @return
	 */
	public LineBarConfig title(String title) {
		return this.attr(KEY_MAIN_TITLE, title);
	}

	/**
	 * 设置X轴标签
	 * 
	 * @author LiuLongbiao
	 * @create 2012-11-29
	 * @param label
	 * @return
	 */
	public LineBarConfig xLabel(String label) {
		return this.attr(KEY_X_LABEL, label);
	}

	/**
	 * 设置(左侧)Y轴标签
	 * 
	 * @author LiuLongbiao
	 * @create 2012-11-29
	 * @param label
	 * @return
	 */
	public LineBarConfig yLabel(String label) {
		return this.attr(KEY_Y_LABEL, label);
	}

	/**
	 * 设置左侧Y轴标签
	 * 
	 * @author LiuLongbiao
	 * @create 2012-11-29
	 * @param label
	 * @return
	 */
	public LineBarConfig leftYLabel(String label) {
		return this.attr(KEY_Y_LABEL, label);
	}

	/**
	 * 设置右侧Y轴标签
	 * 
	 * @author LiuLongbiao
	 * @create 2012-11-29
	 * @param label
	 * @return
	 */
	public LineBarConfig rightYLabel(String label) {
		return this.attr(KEY_RIGHT_Y_LABEL, label);
	}
}
