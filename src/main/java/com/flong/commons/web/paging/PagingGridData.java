package com.flong.commons.web.paging;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.flong.commons.persistence.bean.DataStore;
import com.flong.commons.persistence.dao.impl.BaseDomain;

/**
 * 分页表格请求返回数据域
 * 
 * 创建日期：2012-7-30
 * @author 谢铭
 */
public class PagingGridData<T> extends BaseDomain {
	private static final long serialVersionUID = 4254854126239985934L;

	public static final int SORT_ORDER_DEFAULT = 0;
	public static final int SORT_ORDER_STRING = 1;
	public static final int SORT_ORDER_NUMBER = 2;
	
	/** 当前页 */
	private int page;
	/** 总页数 */
	private int total;
	/** 总记录数 */
	private int records;
	/** 表格数据列表 */
	private List<T> rows = new ArrayList<T>();

	/**
	 * 无参数构造方法
	 * 创建日期：2012-7-30
	 * 修改说明：
	 * @author 谢铭
	 */
	public PagingGridData() {
	}

	/**
	 * 构造方法，所有的记录一页显示(即不分页)
	 * @param rows 所有的记录
	 * 创建日期：2013-1-18
	 * 修改说明：
	 * @author wangk
	 */
	public PagingGridData(List<T> rows) {
		this(1, 1, rows==null?0:rows.size(), rows);
	}

	/**
	 * 构造方法
	 * @param page
	 * @param total
	 * @param records
	 * @param rows
	 * 创建日期：2012-7-30
	 * 修改说明：
	 * @author 谢铭
	 */
	public PagingGridData(int page, int total, int records, List<T> rows) {
		this.page = page;
		this.total = total;
		this.records = records;
		this.rows = rows;
	}

	/**
	 * 构造方法
	 * @param pagingRequestData 分页表格请求数据对象
	 * @param records           总记录数
	 * 创建日期：2012-8-15
	 * 修改说明：
	 * @author wangk
	 */
	public PagingGridData(PagingRequestData pagingRequestData, int records) {
		this.page = pagingRequestData.getPage();
		int rows = pagingRequestData.getRows();
		if(rows > 0) {
			this.total = records%rows==0?records/rows:records/rows+1;
		}
		this.records = records;
	}

	/**
	 * 构造方法
	 * @param pagingRequestData jqGrid请求数据对象
	 * @param records  总记录数
	 * @param rows     响应数据
	 * 创建日期：2012-8-15
	 * 修改说明：
	 * @author wangk
	 */
	public PagingGridData(PagingRequestData pagingRequestData, int records, List<T> rows) {
		this(pagingRequestData, records);
		this.rows = rows;
	}
	
	/**
	 * 用于一次性查出所有数据的情况(数据量较小时使用)
	 * 构造方法
	 * @param pagingRequestData jqGrid请求数据对象
	 * @param records  总记录数
	 * @param rows     响应数据
	 * 创建日期：2012-8-15
	 * 修改说明：
	 * @author wangk
	 */
	public PagingGridData(PagingRequestData pagingRequestData, List<T> rows) {
		this(pagingRequestData, rows.size());
		this.rows = rows;
	}

	/**
	 * 构造方法
	 * @param pagingRequestData 分页表格请求数据对象
	 * @param dataStore         响应的分页数据
	 * 创建日期：2012-8-15
	 * 修改说明：
	 * @author wangk
	 */
	public PagingGridData(PagingRequestData pagingRequestData, DataStore<T> dataStore) {
		this(pagingRequestData, dataStore.getRecords(), dataStore.getDatas());
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getRecords() {
		return records;
	}

	public void setRecords(int records) {
		this.records = records;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	/**
	 * 添加记录集合
	 *
	 * @param rows 记录集合
	 * 创建日期：2013-1-18
	 * 修改说明：
	 * @author wangk
	 */
	public void addRows(Collection<T> rows) {
		getClass();
		this.rows.addAll(rows);
	}

	/**
	 * 排序方法
	 *
	 * @param <E>     记录类型参数
	 * @param rows    记录集合
	 * @param orders  排序对象
	 * 创建日期：2013-1-18
	 * 修改说明：
	 * @author wangk
	 */
	public static <E> void sort(List<E> rows, String orders, int type) {
		if(orders==null || StringUtils.isBlank(orders)) {
			return;
		}
		String[] sidxSord = orders.trim().split("\\s+");
		sort(rows, sidxSord[0], sidxSord.length==1?null:sidxSord[1], type);
	}

	/**
	 * 排序方法，记录类型为List
	 *
	 * @param <E>   记录类型参数，必须是List
	 * @param rows  记录集合
	 * @param sidx  排序列在记录中的下标序号
	 * @param sord  排序方式，若指定为'desc'(不区分大小写)则按降序排列，否则按升序排列
	 * 创建日期：2013-1-18
	 * 修改说明：
	 * @author wangk
	 */
	public static <E> void sort(List<E> rows, int sidx, String sord, int type) {
		sort(rows, String.valueOf(sidx), sord, type);
	}

	/**
	 * 排序方法，记录类型为Map或POJO类
	 *
	 * @param <E>    记录类型参数，必须是Map或POJO类
	 * @param rows   记录集合
	 * @param sidx   排序列名，即Map的key值或POJO类的属性名
	 * @param sord   排序方式，若指定为'desc'(不区分大小写)则按降序排列，否则按升序排列
	 * 创建日期：2013-1-18
	 * 修改说明：
	 * @author wangk
	 */
	public static <E> void sort(List<E> rows, String sidx, String sord, int type) {
		if(CollectionUtils.isEmpty(rows)) {
			return;
		}
		List<Row<E>> _rows = new ArrayList<Row<E>>();
		for (E row : rows) {
			_rows.add(new Row<E>(sidx, type, row));
		}
		Collections.sort(_rows);
		if("desc".equalsIgnoreCase(sord)) {
			Collections.reverse(_rows);
		}
		rows.clear();
		for (Row<E> _row : _rows) {
			rows.add(_row.getRow());
		}
	}

	/**
	 * 行对象，用于排序
	 *
	 * 创建日期：2013-1-18
	 * @author wangk
	 */
	private static class Row<T> implements Comparable<Row<T>> {
		 private String sidx;
		 private int type;
		 private T row;

		public Row(String sidx, int type, T row) {
			this.sidx = sidx;
			this.type = type;
			this.row = row;
		}

		public T getRow() {
			return row;
		}

		/**
		 * @see java.lang.Comparable#compareTo(java.lang.Object)
		 * 创建日期：2013-1-18
		 * 修改说明：
		 * @author wangk
		 */
		@Override
		@SuppressWarnings("unchecked")
		public int compareTo(Row<T> other) {
			if(row instanceof List) {
				int _sidx = Integer.valueOf(sidx);
				List<Object> list = (List<Object>) row;
				Object v = list.get(_sidx);
				Object ov = ((List<Object>)other.getRow()).get(_sidx);
				return compare(v, ov);
			}
			if(row instanceof Map) {
				Map<String, Object> map = (Map<String, Object>) row;
				Object v = map.get(sidx);
				Object ov = ((Map<String, Object>)other.getRow()).get(sidx);
				return compare(v, ov);
			}
			try {
				Method readMethod = new PropertyDescriptor(sidx, row.getClass()).getReadMethod();
				Object v = readMethod.invoke(row);
				Object ov = readMethod.invoke(other.getRow());
				return compare(v, ov);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		/**
		 * 根据type指定的比较方式比较两个对象的值
		 *
		 * @param v   对象1
		 * @param ov  对象2
		 * @return
		 * 创建日期：2013-1-18
		 * 修改说明：
		 * @author wangk
		 */
		@SuppressWarnings({ "rawtypes", "unchecked" })
		private int compare(Object v, Object ov) {
			if(v == null && ov == null) {
				return 0;
			}
			if(v == null) {
				return 1;
			}
			if(ov == null) {
				return -1;
			}
			if(type == SORT_ORDER_STRING) {
				return v.toString().compareTo(ov.toString());
			}
			if(type == SORT_ORDER_NUMBER) {
				return new BigDecimal(v.toString()).compareTo(new BigDecimal(ov.toString()));
			}
			return ((Comparable)v).compareTo(ov);
		}
	}

}
