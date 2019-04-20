/**
 * 
 */
package com.flong.commons.web.chart.linebar;

import static com.flong.commons.web.chart.core.ChartConstant.*;

import com.flong.commons.web.chart.core.ChartElement;

/**
 * 数据警戒线
 * 
 * @author LiuLongbiao
 * @create 2012-11-29
 * 
 */
public class DataLine extends ChartElement {

	@Override
	public DataLine attr(String key, Object value) {
		return (DataLine) super.attr(key, value);
	}

	/**
	 * 设置警戒线名称
	 * 
	 * @author LiuLongbiao
	 * @create 2012-11-29
	 * @param value
	 * @return
	 */
	public DataLine name(Object value) {
		return this.attr(KEY_NAME, value);
	}

	/**
	 * 设置警戒线值
	 * 
	 * @author LiuLongbiao
	 * @create 2012-11-29
	 * @param value
	 * @return
	 */
	public DataLine value(Object value) {
		return this.attr(KEY_VALUE, value);
	}

	/**
	 * 设置警戒线颜色,不指定默认为红色
	 * 
	 * @author LiuLongbiao
	 * @create 2012-11-29
	 * @param value
	 * @return
	 */
	public DataLine lineColor(String value) {
		return this.attr(KEY_LINE_COLOR, value);
	}
}
