/**
 * 
 */
package com.flong.commons.web.chart.ext;

import com.flong.commons.web.chart.core.ChartElement;

/**
 * 万家企业企业能耗情况
 * 
 * @author LiuLongbiao
 * @create 2012-12-10
 * 
 */
public class MyriadCompanyConsume extends ChartElement {
	@Override
	public MyriadCompanyConsume attr(String key, Object value) {
		return (MyriadCompanyConsume) super.attr(key, value);
	}

	/**
	 * 设置企业ID
	 * 
	 * @author LiuLongbiao
	 * @create 2012-12-10
	 * @param companyId
	 * @return
	 */
	public MyriadCompanyConsume id(Long companyId) {
		return this.attr("id", companyId);
	}

	/**
	 * 设置企业名称
	 * 
	 * @author LiuLongbiao
	 * @create 2012-12-10
	 * @param companyId
	 * @return
	 */
	public MyriadCompanyConsume name(String companyName) {
		return this.attr("companyName", companyName);
	}

	/**
	 * 设置企业所属行业
	 * 
	 * @author LiuLongbiao
	 * @create 2012-12-10
	 * @param trade
	 * @return
	 */
	public MyriadCompanyConsume trade(Object trade) {
		return this.attr("trade", trade);
	}

	/**
	 * 设置企业所属区县
	 * 
	 * @author LiuLongbiao
	 * @create 2012-12-10
	 * @param area
	 * @return
	 */
	public MyriadCompanyConsume area(Object area) {
		return this.attr("area", area);
	}

	/**
	 * 设置企业所属行业类型
	 * 
	 * @author LiuLongbiao
	 * @create 2012-12-10
	 * @param type
	 * @return
	 */
	public MyriadCompanyConsume type(Object type) {
		return this.attr("type", type);
	}
}
