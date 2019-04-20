package com.flong.commons.persistence.bean;

import com.flong.commons.persistence.dao.impl.BaseDomain;

/**
 * 树型节点，用于递归深度判断
 *
 * 创建日期：2012-10-26
 * @author wangk
 */
public class TreeNode<T> extends BaseDomain {
	private static final long serialVersionUID = -7173070903585772021L;
	
	/** 节点数据域 */
	public T data;
	/** 父节点引用域 */
	public TreeNode<T> parent;
	/** 当前节点深度 */
	public int depth;

	/**
	 * 构造方法
	 * @param data
	 * 创建日期：2012-10-26
	 * 修改说明：
	 * @author wangk
	 */
	public TreeNode(T data) {
		this.data = data;
	}
	
	/**
	 * 构造方法
	 * @param data
	 * @param parent
	 * @param depth
	 * 创建日期：2012-10-26
	 * 修改说明：
	 * @author wangk
	 */
	public TreeNode(T data, TreeNode<T> parent, int depth) {
		this.data = data;
		this.parent = parent;
		this.depth = depth;
	}

	/**
	 * 判断从当前节点至根节点是否形成闭环
	 *
	 * @return
	 * 创建日期：2012-10-26
	 * 修改说明：
	 * @author wangk
	 */
	public boolean isClosedLoop() {
		TreeNode<T> treeNode = parent;
		while (treeNode != null) {
			if(equals(treeNode)) {
				return true;
			}
			treeNode = treeNode.parent;
		}
		return false;
	}
	
	@Override
	public boolean equals(Object other) {
		if(this == other) {
			return true;
		}
		if(other instanceof TreeNode) {
			@SuppressWarnings("unchecked")
			TreeNode<T> _other = (TreeNode<T>)other;
			if(data == _other.data) {
				return true;
			}
			if(data == null) {
				return false;
			}
			return data.equals(_other.data);
		}
		return false;
	}

}
