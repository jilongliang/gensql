package com.flong.commons.persistence.bean;

import java.io.Serializable;
/**
 *@Author:liangjilong
 *@Date:2015年10月1日-上午9:03:51
 *@Email:jilongliang@sina.aom
 *@Version:1.0
 *@Desct:分页的JavaBean
 */
@SuppressWarnings("all")
public class SimplePage implements Serializable {
	private int rowCount;//行的总数
	private int showCount = 10;//默认显示10条数据

	private int pageNum = 1;//第一页
	private int pageCount;//页的总数
	private int currRowNo;//当前行的编号
	private long startindex;//开始的索引
	private long endindex;//结束的索引
	private int pagecode = 10;//分页条数
	private int nextPage;//下一页
	private int previousPage;//上一页
	/**
	 * 此时这个值不是分页传进来的必须要传进去,不调用这个值，即使数据库有100条数据它也只会显示10条数据出来
	 * 为了统一默认给8位数，如果有需要更大的可以自己设置如下两个
	 * simplePage.setShowCount(1000*1000);//或者可以给一个Integer.MAX_VALUE;
	 */
	public static Integer isNoPageRowCount=Integer.MAX_VALUE;
	/**
	 * 此时这个值不是分页传进来的必须要传进去,不调用这个值，即使数据库有100条数据它也只会显示10条数据出来
	 * 为了统一默认给8位数，如果有需要更大的可以自己设置如下两个
	 * simplePage.setRowCount(1000*1000);
	 * simplePage.setShowCount(1000*1000);
	 */
	public static Integer isNoPageShowCount=Integer.MAX_VALUE;
	
	public SimplePage() {}
   
	/***构造方法
	 * @param rowCount
	 * @param showCount
	 * @param pageNum
	 * @param pageCount
	 * @param currRowNo
	 */
	public SimplePage(int rowCount, int showCount, int pageNum, int pageCount, int currRowNo) {
		this.rowCount = rowCount;
		this.showCount = showCount;
		this.pageNum = pageNum;
		this.pageCount = pageCount;
		this.currRowNo = currRowNo;
	}

	public int getRowCount() {
		return this.rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;

		getPageIndex(this.pagecode, this.pageNum, this.pageCount);
	}

	public int getNextPage() {
		int total = getPageCount();
		this.nextPage = (getPageNum() < total ? getPageNum() + 1 : total);
		return this.nextPage;
	}

	public int getPreviousPage() {
		this.previousPage = (getPageNum() < 2 ? 1 : getPageNum() - 1);
		return this.previousPage;
	}

	public int getShowCount() {
		return this.showCount;
	}

	public void setShowCount(int showCount) {
		this.showCount = showCount;
	}

	public int getPageNum() {
		return this.pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageCount() {
		this.pageCount = (this.rowCount % this.showCount == 0 ? this.rowCount
				/ this.showCount : this.rowCount / this.showCount + 1);
		getPageIndex(this.pagecode, this.pageNum, this.pageCount);
		return this.pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getCurrRowNo() {
		this.currRowNo = ((this.pageNum - 1) * this.showCount);
		return this.currRowNo;
	}

	public void setCurrRowNo(int currRowNo) {
		this.currRowNo = currRowNo;
	}

	public long getStartindex() {
		return this.startindex;
	}

	public void setStartindex(long startindex) {
		this.startindex = startindex;
	}

	public long getEndindex() {
		return this.endindex;
	}

	public void setEndindex(long endindex) {
		this.endindex = endindex;
	}

	public void getPageIndex(long viewpagecount, int currentPage, long totalpage) {
		setStartindex(1L);
		setEndindex(totalpage);
	}
}