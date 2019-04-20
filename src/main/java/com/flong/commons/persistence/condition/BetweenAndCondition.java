package com.flong.commons.persistence.condition;

/**
 * between ... and条件
 * 
 * @created 2013-5-29
 * @author  wangk
 */
public class BetweenAndCondition extends Condition {
	private static final long serialVersionUID = -8402100982159870290L;

	/** 列名 */
	private String column;
	/** 下限 */
	private Object lowerLimit;
	/** 上限 */
	private Object upperLimit;

	/**
	 * 构造方法
	 *
	 * @created 2013-5-29
	 * @author  wangk
	 */
	public BetweenAndCondition() {
	}

	/**
	 * 构造方法
	 *
	 * @param column      列名
	 * @param lowerLimit  下限
	 * @param upperLimit  上限
	 * @created 2013-5-29
	 * @author  wangk
	 */
	public BetweenAndCondition(String column, Object lowerLimit,
			Object upperLimit) {
		this.column = column;
		this.lowerLimit = lowerLimit;
		this.upperLimit = upperLimit;
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public Object getLowerLimit() {
		return lowerLimit;
	}

	public void setLowerLimit(Object lowerLimit) {
		this.lowerLimit = lowerLimit;
	}

	public Object getUpperLimit() {
		return upperLimit;
	}

	public void setUpperLimit(Object upperLimit) {
		this.upperLimit = upperLimit;
	}

	/**
	 * @see com.flong.commons.persistence.condition.trse.immortal.domain.condition.Condition#getParameters()
	 * @created 2013-5-29
	 * @author  wangk
	 */
	@Override
	public Object[] getParameters() {
		return new Object[]{lowerLimit, upperLimit};
	}

	/**
	 * @see com.flong.commons.persistence.condition.trse.immortal.domain.condition.Condition#toSqlString()
	 * @created 2013-5-29
	 * @author  wangk
	 */
	@Override
	public String toSqlString() {
		return column + SPACE + BETWEEN + SPACE + PARAMETER_PLACEHOLDER + 
				SPACE + AND + SPACE + PARAMETER_PLACEHOLDER;
	}

}
