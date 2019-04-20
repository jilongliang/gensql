/**
 * 
 */
package com.flong.commons.web.chart.ext;

import static com.flong.commons.web.chart.core.ChartConstant.KEY_TITLE;

import com.flong.commons.web.chart.core.ChartElement;

/**
 * 概况信息
 * 
 * @author LiuLongbiao
 * @create 2012-12-7
 * 
 */
public class Overview extends ChartElement {

	@Override
	public Overview attr(String key, Object value) {
		return (Overview) super.attr(key, value);
	}

	/**
	 * 设置概况标题
	 * 
	 * @author LiuLongbiao
	 * @create 2012-12-7
	 * @param title
	 * @return
	 */
	public Overview title(String title) {
		return this.attr(KEY_TITLE, title);
	}
}
