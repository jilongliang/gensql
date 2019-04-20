package com.flong.commons.persistence.condition;

/**
 * 简单条件，由列名、操作符与参数值组成
 * 
 * @created 2013-5-28
 * @author wangk
 */
public class SimpleCondition extends Condition {
	private static final long serialVersionUID = -2874189715688238636L;

	/** 列名 */
	private String column;
	/** 操作符 */
	private String operator;
	/** 参数值 */
	private Object parameter;

	/**
	 * 构造方法
	 *
	 * @created 2013-5-28
	 * @author  wangk
	 */
	public SimpleCondition() {
	}

	/**
	 * 构造方法
	 *
	 * @param column    列名
	 * @param operator  操作符
	 * @param parameter 参数值
	 * @created 2013-5-28
	 * @author  wangk
	 */
	public SimpleCondition(String column, String operator, Object parameter) {
		this.column = column;
		this.operator = operator;
		this.parameter = parameter;
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Object getParameter() {
		return parameter;
	}

	public void setParameter(Object parameter) {
		this.parameter = parameter;
	}

	/**
	 * @see com.flong.commons.persistence.condition.trse.immortal.domain.condition.Condition#getParameters()
	 * @created 2013-5-28
	 * @author  wangk
	 */
	@Override
	public Object[] getParameters() {
		return new Object[]{parameter};
	}

	/**
	 * @see com.flong.commons.persistence.condition.trse.immortal.domain.condition.Condition#toSqlString()
	 * @created 2013-5-28
	 * @author  wangk
	 */
	@Override
	public String toSqlString() {
		return column + SPACE + operator + SPACE + PARAMETER_PLACEHOLDER;
	}

}
