/**
 * 
 */
package com.flong.commons.web.chart.linebar;

/**
 * 因子关联竖轴位置
 * 
 * @author LiuLongbiao
 * @create 2012-11-29
 * 
 */
public enum FactorAxis {
	/**
	 * 柱状因子
	 */
	LEFT("v1"),
	/**
	 * 线型因子
	 */
	RIGHT("v2");

	public final String code;

	private FactorAxis(String code) {
		this.code = code;
	}
}
