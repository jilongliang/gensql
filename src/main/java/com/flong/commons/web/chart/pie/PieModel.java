/**
 * 
 */
package com.flong.commons.web.chart.pie;

import static com.flong.commons.web.chart.core.ChartConstant.*;

import com.flong.commons.web.chart.core.ChartElement;

/**
 * 饼图数据项
 * 
 * @author LiuLongbiao
 * @create 2012-11-29
 * 
 */
public class PieModel extends ChartElement {

	@Override
	public PieModel attr(String key, Object value) {
		return (PieModel) super.attr(key, value);
	}

	/**
	 * 设置因子名称
	 * 
	 * @author LiuLongbiao
	 * @create 2012-11-29
	 * @param name
	 * @return
	 */
	public PieModel name(Object name) {
		return this.attr(KEY_NAME, name);
	}

	/**
	 * 设置因子值
	 * 
	 * @author LiuLongbiao
	 * @create 2012-11-29
	 * @param value
	 * @return
	 */
	public PieModel value(Object value) {
		return this.attr(KEY_VALUE, value);
	}

	/**
	 * 设置因子颜色
	 * 
	 * @author LiuLongbiao
	 * @create 2012-11-29
	 * @param color
	 * @return
	 */
	public PieModel color(String color) {
		return this.attr(KEY_COLOR, color);
	}

	/**
	 * 设置是否显示图例
	 * 
	 * @author LiuLongbiao
	 * @create 2012-11-29
	 * @param visible
	 * @return
	 */
	public PieModel visible(boolean visible) {
		return this.attr(KEY_VISIBLE, visible);
	}

	/**
	 * 设置是否显示为拉伸效果
	 * 
	 * @author LiuLongbiao
	 * @create 2012-11-29
	 * @param pulled
	 * @return
	 */
	public PieModel pulled(boolean pulled) {
		return this.attr(KEY_PULLED, pulled);
	}
}
