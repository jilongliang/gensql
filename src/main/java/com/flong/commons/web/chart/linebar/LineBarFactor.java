/**
 * 
 */
package com.flong.commons.web.chart.linebar;

import static com.flong.commons.web.chart.core.ChartConstant.*;

import java.util.List;

import com.flong.commons.web.chart.core.ChartElement;

/**
 * 柱状图、线图因子项
 * 
 * @author LiuLongbiao
 * @create 2012-11-29
 * 
 */
public class LineBarFactor extends ChartElement {

	/*
	 * 元数据，用于标识因子项，随后根据它来做不同的操作(该因子不序列化成JSON)
	 */
	private Object metadata;

	public LineBarFactor() {
		super();
		// 设置默认值
		this.type(FactorType.COLUMN).axis(FactorAxis.LEFT);
	}

	public Object getMetadata() {
		return metadata;
	}

	public void setMetadata(Object metadata) {
		this.metadata = metadata;
	}


	@Override
	public LineBarFactor attr(String key, Object value) {
		return (LineBarFactor) super.attr(key, value);
	}

	/**
	 * 设置因子名称
	 * 
	 * @author LiuLongbiao
	 * @create 2012-11-29
	 * @param value
	 * @return
	 */
	public LineBarFactor name(Object value) {
		return this.attr(KEY_NAME, value);
	}

	/**
	 * 设置因子类型
	 * 
	 * @author LiuLongbiao
	 * @create 2012-11-29
	 * @param type
	 * @return
	 */
	public LineBarFactor type(FactorType type) {
		return this.attr(KEY_TYPE, type.code);
	}

	/**
	 * 设置因子竖轴位置
	 * 
	 * @author LiuLongbiao
	 * @create 2012-11-29
	 * @param axis
	 * @return
	 */
	public LineBarFactor axis(FactorAxis axis) {
		return this.attr(KEY_AXIS, axis.code);
	}

	/**
	 * 设置因子颜色
	 * 
	 * @author LiuLongbiao
	 * @create 2012-11-29
	 * @param value
	 * @return
	 */
	public LineBarFactor color(String value) {
		return this.attr(KEY_COLOR, value);
	}

	/**
	 * 设置因子可见性
	 * 
	 * @author LiuLongbiao
	 * @create 2012-11-29
	 * @param value
	 * @return
	 */
	public LineBarFactor visible(boolean value) {
		return this.attr(KEY_VISIBLE, value);
	}

	/**
	 * 设置因子初始化时是否隐藏
	 * <p>
	 * 当visible为true时选择hidden：true时 该因子在初始化时隐藏
	 * </p>
	 * 
	 * @author LiuLongbiao
	 * @create 2012-11-29
	 * @param value
	 * @return
	 */
	public LineBarFactor hidden(boolean value) {
		return this.attr(KEY_HIDDEN, value);
	}

	/**
	 * 设置因子堆叠类型
	 * 
	 * @author LiuLongbiao
	 * @create 2012-11-29
	 * @param type
	 * @return
	 */
	public LineBarFactor stackType(StackType type) {
		return this.attr(KEY_STACK_TYPE, type.code);
	}

	/**
	 * 设置因子是否可堆叠，false时为普通效果
	 * 
	 * @author LiuLongbiao
	 * @create 2012-11-29
	 * @param value
	 * @return
	 */
	public LineBarFactor stackable(boolean value) {
		return this.attr(KEY_STACKABLE, value);
	}

	/**
	 * 设置因子透明度
	 * 
	 * @author LiuLongbiao
	 * @create 2012-11-29
	 * @param alphas
	 *            0到1之间的值的列表,[0]为透明，[1]为不透明
	 * @return
	 */
	public LineBarFactor fillAlphas(List<Double> alphas) {
		return this.attr(KEY_COLOR, alphas);
	}
}
