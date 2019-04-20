package com.flong.commons.persistence.condition;

import com.flong.commons.persistence.dao.impl.BaseDomain;

/**
 * SQL条件抽象类
 * 
 * @created 2013-5-28
 * @author  wangk
 */
public abstract class Condition extends BaseDomain {
	private static final long serialVersionUID = 4558142209318243533L;

	/** 空格符 */
	public static final String SPACE = " ";
	/** 参数占位符 */
	public static final String PARAMETER_PLACEHOLDER = "?";
	/** 左括号 */
	public static final String LEFT_BRACE = "(";
	/** 右括号 */
	public static final String RIGHT_BRACE = ")";

	/**
	 * 操作符
	 */
	public static final String EQ = "=";
	public static final String LT = "<";
	public static final String GT = ">";
	public static final String NE = "<>";
	public static final String LE = "<=";
	public static final String GE = ">=";
	public static final String LIKE = "LIKE";
	public static final String IS_NULL = "IS NULL";
	public static final String IS_NOT_NULL = "IS NOT NULL";
	public static final String BETWEEN = "BETWEEN";
	public static final String AND = "AND";
	public static final String OR = "OR";
	public static final String IN = "IN";
	public static final String NOT_IN = "NOT IN";

	/** 恒为真条件 */
	public static final Condition ALWAYS_TRUE_CONDITION = new AlwaysTrueCondition();
	/** 恒为假条件 */
	public static final Condition ALWAYS_FALSE_CONDITION = new AlwaysFalseCondition();

	/**
	 * 获得条件参数数组
	 * 
	 * @return
	 * @created 2013-5-28
	 * @author  wangk
	 */
	public abstract Object[] getParameters();

	/**
	 * 将条件对象转化成SQL语句
	 * 
	 * @return
	 * @created 2013-5-28
	 * @author  wangk
	 */
	public abstract String toSqlString();

	/**
	 * 添加子条件，默认抛出运行时异常，只有and和or条件对象需要重写该方法
	 * 
	 * @param component
	 * @return
	 * @created 2013-5-28
	 * @author  wangk
	 */
	public Condition add(Condition component) {
		throw new RuntimeException("Add component not supported!");
	}

	/**
	 * 构建等于条件
	 * 
	 * @param column     列名
	 * @param parameter  参数
	 * @return
	 * @created 2013-5-29
	 * @author  wangk
	 */
	public static Condition eq(String column, Object parameter) {
		return new SimpleCondition(column, EQ, parameter);
	}

	/**
	 * 构建小于条件
	 * 
	 * @param column     列名
	 * @param parameter  参数
	 * @return
	 * @created 2013-5-29
	 * @author  wangk
	 */
	public static Condition lt(String column, Object parameter) {
		return new SimpleCondition(column, LT, parameter);
	}

	/**
	 * 构建大于条件
	 * 
	 * @param column     列名
	 * @param parameter  参数
	 * @return
	 * @created 2013-5-29
	 * @author  wangk
	 */
	public static Condition gt(String column, Object parameter) {
		return new SimpleCondition(column, GT, parameter);
	}

	/**
	 * 构建不等于条件
	 * 
	 * @param column     列名
	 * @param parameter  参数
	 * @return
	 * @created 2013-5-29
	 * @author  wangk
	 */
	public static Condition ne(String column, Object parameter) {
		return new SimpleCondition(column, NE, parameter);
	}

	/**
	 * 构建小于等于条件
	 * 
	 * @param column     列名
	 * @param parameter  参数
	 * @return
	 * @created 2013-5-29
	 * @author  wangk
	 */
	public static Condition le(String column, Object parameter) {
		return new SimpleCondition(column, LE, parameter);
	}

	/**
	 * 构建大于等于条件
	 * 
	 * @param column     列名
	 * @param parameter  参数
	 * @return
	 * @created 2013-5-29
	 * @author  wangk
	 */
	public static Condition ge(String column, Object parameter) {
		return new SimpleCondition(column, GE, parameter);
	}

	/**
	 * 构建like条件
	 * 
	 * @param column     列名
	 * @param parameter  参数
	 * @return
	 * @created 2013-5-29
	 * @author  wangk
	 */
	public static Condition like(String column, String parameter) {
		return new SimpleCondition(column, LIKE, parameter);
	}

	/**
	 * 构建is null条件
	 * 
	 * @param column   列名
	 * @return
	 * @created 2013-5-29
	 * @author  wangk
	 */
	public static Condition isNull(String column) {
		return new IsNullCondition(column);
	}

	/**
	 * 构建is not null条件
	 * 
	 * @param column   列名
	 * @return
	 * @created 2013-5-29
	 * @author  wangk
	 */
	public static Condition isNotNull(String column) {
		return new IsNotNullCondition(column);
	}
	
	/**
	 * 构建between ... and条件
	 * 
	 * @param column      列名
	 * @param lowerLimit  下限
	 * @param upperLimit  上限
	 * @return
	 * @created 2013-5-29
	 * @author  wangk
	 */
	public static Condition betweenAnd(String column, Object lowerLimit,
			Object upperLimit) {
		return new BetweenAndCondition(column, lowerLimit, upperLimit);
	}

	/**
	 * 构建in条件
	 * 
	 * @param column      列名
	 * @param component   子查询或参数集合
	 * @param parameters  参数数组
	 * @return
	 * @created 2013-5-29
	 * @author  wangk
	 */
	public static Condition in(String column, String component,
			Object... parameters) {
		return new InCondition(column, component, parameters);
	}

	/**
	 * 构建not in条件
	 * 
	 * @param column      列名
	 * @param component   子查询或参数集合
	 * @param parameters  参数数组
	 * @return
	 * @created 2013-5-29
	 * @author  wangk
	 */
	public static Condition notIn(String column, String component,
			Object... parameters) {
		return new NotInCondition(column, component, parameters);
	}

	/**
	 * 构建and条件
	 * 
	 * @param components   子条件数组
	 * @return
	 * @created 2013-5-29
	 * @author  wangk
	 */
	public static Condition and(Condition... components) {
		return new AndCondition(components);
	}

	/**
	 * 构建or条件
	 * 
	 * @param components   子条件数组
	 * @return
	 * @created 2013-5-29
	 * @author  wangk
	 */
	public static Condition or(Condition... components) {
		return new OrCondition(components);
	}

}
