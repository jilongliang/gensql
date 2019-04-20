package com.flong.commons.web.paging;

import org.apache.commons.lang3.StringUtils;

import com.flong.commons.persistence.bean.PagingParameter;
import com.flong.commons.persistence.dao.impl.BaseDomain;
import com.flong.commons.web.domain.RequestData;

/**
 * 封装JqGrid插件请求的数据
 *
 * 创建日期：2012-8-15
 * @author wangk
 */
public class PagingRequestData extends BaseDomain {
	private static final long serialVersionUID = 4545969837991981187L;

	/** 业务相关的请求数据 */
	private RequestData requestData;
	/** 当前页数 */
	private int page;
	/** 每页显示的行数 */
	private int rows;
	/** 排序列 */
	private String sidx;
	/** 排序方式 */
	private String sord;

	/**
	 * 获得业务相关的请求数据
	 *
	 * @return
	 * 创建日期：2012-8-15
	 * 修改说明：
	 * @author wangk
	 */
	public RequestData getRequestData() {
		return requestData;
	}
	
	public void setRequestData(RequestData requestData) {
		this.requestData = requestData;
	}

	public int getPage() {
		return page;
	}
	
	public void setPage(int page) {
		this.page = page;
	}
	
	public int getRows() {
		return rows;
	}
	
	public void setRows(int rows) {
		this.rows = rows;
	}
	
	public String getSidx() {
		return sidx;
	}
	
	public void setSidx(String sidx) {
		this.sidx = sidx;
	}
	
	public String getSord() {
		return sord;
	}
	
	public void setSord(String sord) {
		this.sord = sord;
	}

	/**
	 * 获得分页参数对象
	 *
	 * @return
	 * 创建日期：2012-8-15
	 * 修改说明：
	 * @author wangk
	 */
	public PagingParameter getPaging() {
		if(page < 1 || rows < 1) {
			return null;
		}
		return new PagingParameter((page-1)*rows, rows);
	}

	/**
	 * 获得排序对象
	 *
	 * @return
	 * 创建日期：2012-8-15
	 * 修改说明：
	 * @author wangk
	 */
	public String getString() {
		if(StringUtils.isBlank(sidx)){
			return null;
		}
		String orderString = sidx;
		if(StringUtils.isNotBlank(sord)){
			orderString += " " + sord;
		}
		return orderString;
	}

}
