/**
 * 
 */
package com.flong.commons.web.chart.linebar;

/**
 * 因子堆叠类型
 * <p>
 * 多因子时，数据的堆叠
 * </p>
 * 
 * @author LiuLongbiao
 * @create 2012-11-29
 * 
 */
public enum StackType {
	/**
	 * 上下堆叠
	 */
	REGULAR("regular"),
	/**
	 * 左右堆叠
	 */
	NONE("none");

	public final String code;

	private StackType(String code) {
		this.code = code;
	}
}
