package com.flong.commons.persistence.condition;

/**
 * is null条件
 * 
 * @created 2013-5-29
 * @author  wangk
 */
public class IsNullCondition extends Condition {
	private static final long serialVersionUID = 5041004211231977803L;

	/** 列名 */
	private String column;

	/**
	 * 构造方法
	 *
	 * @created 2013-5-29
	 * @author  wangk
	 */
	public IsNullCondition() {
	}

	/**
	 * 构造方法
	 *
	 * @param column  列名
	 * @created 2013-5-29
	 * @author  wangk
	 */
	public IsNullCondition(String column) {
		this.column = column;
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	/**
	 * @see com.flong.commons.persistence.condition.trse.immortal.domain.condition.Condition#getParameters()
	 * @created 2013-5-29
	 * @author  wangk
	 */
	@Override
	public Object[] getParameters() {
		return new Object[0];
	}

	/**
	 * @see com.flong.commons.persistence.condition.trse.immortal.domain.condition.Condition#toSqlString()
	 * @created 2013-5-29
	 * @author  wangk
	 */
	@Override
	public String toSqlString() {
		return column + SPACE + IS_NULL;
	}

}
