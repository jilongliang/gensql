package com.flong.commons.utils;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

/**
 * Condition组装工具类
 *
 * 创建日期：2013-1-15
 * @author wangk
 */
public class ConditionUtil {
	/** LIKE子句匹配任意字符的字符串常量 */
	public static final Character LIKE_ARBITRARY_CHARACTER = '%';
	/** LIKE子句通配字符集合 */
	private static final Set<Character> likeWildcardCharacterSet = new HashSet<Character>();

	/**
	 * 初始化LIKE子句通配字符集合
	 */
	static {
		likeWildcardCharacterSet.add(LIKE_ARBITRARY_CHARACTER);
		likeWildcardCharacterSet.add('_');
		likeWildcardCharacterSet.add('^');
		likeWildcardCharacterSet.add('[');
	}

	/**
	 * 构建文本含有component指定内容的LIKE条件值，如："%中%"
	 *
	 * @param component
	 * @return
	 * 创建日期：2013-1-15
	 * 修改说明：
	 * @author wangk
	 */
	public static String buildLikeValue(String component) {
		return LIKE_ARBITRARY_CHARACTER + handleLikeComponent(component) + LIKE_ARBITRARY_CHARACTER;
	}
	
	/**
	 * 构建文本匹配以component指定内容开头的LIKE条件值，如："中%"
	 *
	 * @param component
	 * @return
	 * 创建日期：2013-1-15
	 * 修改说明：
	 * @author wangk
	 */
	public static String buildLikeValueMatchStart(String component) {
		return handleLikeComponent(component) + LIKE_ARBITRARY_CHARACTER;
	}

	/**
	 * 构建文本匹配以component指定内容结尾的LIKE条件值，如："%中"
	 *
	 * @param component
	 * @return
	 * 创建日期：2013-1-15
	 * 修改说明：
	 * @author wangk
	 */
	public static String buildLikeValueMatchEnd(String component) {
		return LIKE_ARBITRARY_CHARACTER + handleLikeComponent(component);
	}

	/**
	 * 处理LIKE条件值，将通配符转义
	 *
	 * @param component
	 * @return
	 * 创建日期：2013-1-15
	 * 修改说明：
	 * @author wangk
	 */
	private static String handleLikeComponent(String component) {
		if(StringUtils.isEmpty(component)) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < component.length(); i++) {
			Character character = component.charAt(i);
			if(likeWildcardCharacterSet.contains(character)) {
				sb.append("[");
				sb.append(character);
				sb.append("]");
			} else {
				sb.append(character);
			}
		}
		return sb.toString();
	}

}
