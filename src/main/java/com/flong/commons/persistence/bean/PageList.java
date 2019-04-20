package com.flong.commons.persistence.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
/**
 *@Author:liangjilong
 *@Date:2015年10月1日-上午9:03:51
 *@Email:jilongliang@sina.aom
 *@Version:1.0
 *@Desct:........
 */
@SuppressWarnings("all")
public final class PageList<E> implements List<E>, Serializable {
	private List<E> resulstList;
	private int pageSize;//分页条数
	private int pageNum;//页数
	private int total;//总条数
	private int pageCount;//多少页

	public PageList(List<E> list) {
		this.resulstList = new ArrayList();
		if (list != null)
			this.resulstList.addAll(list);
	}

	public PageList(List<E> list, int pageSize, int pageNum, int total) {
		this(list);
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		this.total = total;
	}

	public int size() {
		return this.resulstList.size();
	}

	public boolean isEmpty() {
		if (this.resulstList == null) {
			return true;
		}
		return this.resulstList.isEmpty();
	}

	public boolean contains(Object o) {
		if (this.resulstList == null) {
			return false;
		}
		return this.resulstList.contains(o);
	}

	public Iterator<E> iterator() {
		return this.resulstList.iterator();
	}

	public Object[] toArray() {
		return this.resulstList.toArray();
	}

	public <T> T[] toArray(T[] a) {
		return this.resulstList.toArray(a);
	}

	public boolean add(E e) {
		return this.resulstList.add(e);
	}

	public boolean remove(Object o) {
		return this.resulstList.remove(o);
	}

	public boolean containsAll(Collection<?> c) {
		return this.resulstList.containsAll(c);
	}

	public boolean addAll(Collection<? extends E> c) {
		return this.resulstList.addAll(c);
	}

	public boolean addAll(int index, Collection<? extends E> c) {
		return this.resulstList.addAll(index, c);
	}

	public boolean removeAll(Collection<?> c) {
		return this.resulstList.removeAll(c);
	}

	public boolean retainAll(Collection<?> c) {
		return c.retainAll(c);
	}

	public void clear() {
		this.resulstList.clear();
	}

	public E get(int index) {
		return this.resulstList.get(index);
	}

	public E set(int index, E element) {
		return this.resulstList.set(index, element);
	}

	public void add(int index, E element) {
		this.resulstList.set(index, element);
	}

	public E remove(int index) {
		return this.resulstList.remove(index);
	}

	public int indexOf(Object o) {
		return this.resulstList.indexOf(o);
	}

	public int lastIndexOf(Object o) {
		return this.resulstList.lastIndexOf(o);
	}

	public ListIterator<E> listIterator() {
		return this.resulstList.listIterator();
	}

	public ListIterator<E> listIterator(int index) {
		return this.resulstList.listIterator(index);
	}

	public List<E> subList(int fromIndex, int toIndex) {
		return this.resulstList.subList(fromIndex, toIndex);
	}

	public int getPageNum() {
		return this.pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getTotal() {
		return this.total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getPageSize() {
		return this.pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageCount() {
		return this.pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
}