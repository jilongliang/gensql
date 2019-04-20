/**
 * 
 */
package com.flong.commons.web.chart.linebar;

import com.flong.commons.web.chart.core.ChartConstant;
import com.flong.commons.web.chart.core.ChartElement;

/**
 * 柱状图、线图数据项
 * 
 * @author LiuLongbiao
 * @create 2012-11-29
 * 
 */
public class LineBarModel extends ChartElement {

	/**
	 * 设置数据项标签，该标签标示该数据的名称
	 * <p>
	 * 对应Flash中time属性
	 * </p>
	 * 
	 * @author LiuLongbiao
	 * @create 2012-11-29
	 * @param value
	 * @return
	 */
	public LineBarModel label(Object value) {
		return this.attr(ChartConstant.KEY_TIME, value);
	}

	@Override
	public LineBarModel attr(String key, Object value) {
		return (LineBarModel) super.attr(key, value);
	}
}
