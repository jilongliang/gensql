package com.flong.commons.persistence.condition;

/**
 * 恒为真条件
 * 
 * @created 2013-5-29
 * @author  wangk
 */
public class AlwaysTrueCondition extends Condition {
	private static final long serialVersionUID = -6774936720410854164L;

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
		return "1 = 1";
	}

}
