/**
 * 
 */
package com.flong.commons.web.chart.linebar;

/**
 * 因子类型
 * 
 * @author LiuLongbiao
 * @create 2012-11-29
 * 
 */
public enum FactorType {

	/**
	 * 柱状因子
	 */
	COLUMN("column"),
	/**
	 * 线型因子
	 */
	LINE("line");

	public final String code;

	private FactorType(String code) {
		this.code = code;
	}

}
