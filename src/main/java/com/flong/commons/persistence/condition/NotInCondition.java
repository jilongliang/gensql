package com.flong.commons.persistence.condition;
/**
 * not in条件
 * 
 * @created 2013-5-29
 * @author  wangk
 */
public class NotInCondition extends Condition {
	private static final long serialVersionUID = -313075395842837260L;

	/** 列名 */
	private String column;
	/** 子查询或参数集合 */
	private String component;
	/** 参数数组 */
	private Object[] parameters;

	/**
	 * 构造方法
	 *
	 * @created 2013-5-29
	 * @author  wangk
	 */
	public NotInCondition() {
	}

	/**
	 * 构造方法
	 *
	 * @param column      列名
	 * @param component   子查询或参数集合
	 * @param parameters  参数数组
	 * @created 2013-5-29
	 * @author  wangk
	 */
	public NotInCondition(String column, String component, Object[] parameters) {
		this.column = column;
		this.component = component;
		this.parameters = parameters;
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

	public void setParameters(Object[] parameters) {
		this.parameters = parameters;
	}

	/**
	 * @see com.flong.commons.persistence.condition.trse.immortal.domain.condition.Condition#getParameters()
	 * @created 2013-5-29
	 * @author  wangk
	 */
	@Override
	public Object[] getParameters() {
		return parameters;
	}

	/**
	 * @see com.flong.commons.persistence.condition.trse.immortal.domain.condition.Condition#toSqlString()
	 * @created 2013-5-29
	 * @author  wangk
	 */
	@Override
	public String toSqlString() {
		return column + SPACE + NOT_IN + SPACE + LEFT_BRACE + component + RIGHT_BRACE;
	}

}
