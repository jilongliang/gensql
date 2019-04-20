/**
 * 
 */
package com.flong.commons.web.chart.pie;

import static com.flong.commons.web.chart.core.ChartConstant.*;

import com.flong.commons.web.chart.core.ChartElement;

/**
 * 饼图配置项
 * 
 * @author LiuLongbiao
 * @create 2012-11-30
 * 
 */
public class PieConfig extends ChartElement {
	@Override
	public PieConfig attr(String key, Object value) {
		return (PieConfig) super.attr(key, value);
	}

	/**
	 * 设置饼图标题
	 * 
	 * @author LiuLongbiao
	 * @create 2012-11-30
	 * @param title
	 * @return
	 */
	public PieConfig title(String title) {
		return this.attr(KEY_TITLE, title);
	}

	/**
	 * 设置饼图半径，可传整数或百分比
	 * 
	 * @author LiuLongbiao
	 * @create 2012-11-30
	 * @param radius
	 * @return
	 */
	public PieConfig radius(String radius) {
		return this.attr(KEY_RADIUS, radius);
	}
}
