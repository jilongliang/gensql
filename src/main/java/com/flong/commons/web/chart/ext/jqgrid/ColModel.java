/**
 * 
 */
package com.flong.commons.web.chart.ext.jqgrid;

import static com.flong.commons.web.chart.core.ChartConstant.*;

import com.flong.commons.web.chart.core.ChartElement;

/**
 * 列模型
 * 
 * @author LiuLongbiao
 * @create 2012-12-10
 * 
 */
public class ColModel extends ChartElement {

	public ColModel() {
		super();
		// 设置默认值
		this.align(Align.CENTER).width(90).sortable(false);
	}

	public ColModel(String prop) {
		this();
		this.prop(prop);
	}

	@Override
	public ColModel attr(String key, Object value) {
		return (ColModel) super.attr(key, value);
	}

	/**
	 * 设置列字段名及索引
	 * 
	 * @author LiuLongbiao
	 * @create 2012-12-10
	 * @param prop
	 * @return
	 */
	public ColModel prop(String prop) {
		return this.name(prop).index(prop);
	}

	/**
	 * 设置列字段名
	 * 
	 * @author LiuLongbiao
	 * @create 2012-12-10
	 * @param name
	 * @return
	 */
	public ColModel name(String name) {
		return this.attr(KEY_NAME, name);
	}

	/**
	 * 设置列字段索引
	 * 
	 * @author LiuLongbiao
	 * @create 2012-12-10
	 * @param index
	 * @return
	 */
	public ColModel index(String index) {
		return this.attr(KEY_INDEX, index);
	}

	/**
	 * 设置列字段索引
	 * 
	 * @author LiuLongbiao
	 * @create 2012-12-10
	 * @param index
	 * @return
	 */
	public ColModel align(Align align) {
		return this.attr(KEY_INDEX, align.code);
	}

	/**
	 * 设置宽度像素值
	 * 
	 * @author LiuLongbiao
	 * @create 2012-12-10
	 * @param px
	 * @return
	 */
	public ColModel width(int px) {
		return this.attr(KEY_WIDTH, px);
	}

	/**
	 * 设置列是否可排序
	 * 
	 * @author LiuLongbiao
	 * @create 2012-12-10
	 * @param sortable
	 * @return
	 */
	public ColModel sortable(boolean sortable) {
		return this.attr(KEY_SORTABLE, sortable);
	}

	/**
	 * 设置列是否隐藏
	 * 
	 * @author LiuLongbiao
	 * @create 2012-12-10
	 * @param hidden
	 * @return
	 */
	public ColModel hidden(boolean hidden) {
		return this.attr(KEY_HIDDEN, hidden);
	}
}
