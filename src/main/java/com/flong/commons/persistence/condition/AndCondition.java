package com.flong.commons.persistence.condition;

import java.util.ArrayList;
import java.util.List;

/**
 * and连接条件
 * 
 * @created 2013-5-29
 * @author  wangk
 */
public class AndCondition extends Condition {
	private static final long serialVersionUID = -1175065710839607420L;

	/** 子条件集合 */
	private List<Condition> components = new ArrayList<Condition>();

	/**
	 * 构造方法
	 *
	 * @created 2013-5-29
	 * @author  wangk
	 */
	public AndCondition() {
	}

	/**
	 * 构造方法
	 *
	 * @param component    子条件数组
	 * @created 2013-5-29
	 * @author  wangk
	 */
	public AndCondition(Condition[] components) {
		for (Condition component : components) {
			add(component);
		}
	}

	public List<Condition> getComponents() {
		return components;
	}

	public void setComponents(List<Condition> components) {
		this.components = components;
	}

	/**
	 * @see com.flong.commons.persistence.condition.trse.immortal.domain.condition.Condition#getParameters()
	 * @created 2013-5-29
	 * @author  wangk
	 */
	@Override
	public Object[] getParameters() {
		if(components.isEmpty()) {
			return ALWAYS_TRUE_CONDITION.getParameters();
		}
		if(components.size()  == 1) {
			return components.get(0).getParameters();
		}
		List<Object> parameters = new ArrayList<Object>();
		for (Condition component : components) {
			for (Object parameter : component.getParameters()) {
				parameters.add(parameter);
			}
		}
		return parameters.toArray();
	}

	/**
	 * @see com.flong.commons.persistence.condition.trse.immortal.domain.condition.Condition#toSqlString()
	 * @created 2013-5-29
	 * @author  wangk
	 */
	@Override
	public String toSqlString() {
		if(components.isEmpty()) {
			return ALWAYS_TRUE_CONDITION.toSqlString();
		}
		if(components.size()  == 1) {
			return components.get(0).toSqlString();
		}
		StringBuilder sb = new StringBuilder(LEFT_BRACE + components.get(0).toSqlString() + RIGHT_BRACE);
		for (int i = 1; i < components.size(); i++) {
			sb.append(SPACE + AND + SPACE + LEFT_BRACE + components.get(i).toSqlString() + RIGHT_BRACE);
		}
		return sb.toString();
	}

	/**
	 * @see com.flong.commons.persistence.condition.trse.immortal.domain.condition.Condition#add(com.flong.commons.persistence.condition.trse.immortal.domain.condition.Condition)
	 * @created 2013-5-29
	 * @author  wangk
	 */
	@Override
	public Condition add(Condition component) {
		components.add(component);
		return this;
	}

}
