/**
 * 
 */
package com.flong.modules.dao.impl;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.flong.commons.lang.StringUtils;
import com.flong.commons.lang.exception.BizLogicException;
import com.flong.commons.lang.exception.DaoAccessException;
import com.flong.commons.lang.exception.IdOutOfBoundsException;
import com.flong.commons.lang.exception.QuantityNumberException;
import com.flong.commons.lang.exception.TableNotFoundException;
import com.flong.commons.persistence.bean.SimplePage;
import com.flong.commons.persistence.dao.impl.EntityDaoSupport;
import com.flong.modules.dao.TableIdentifiersDAO;
import com.flong.modules.pojo.TableIdentifiers;

/**
 *
 */
@Repository
@SuppressWarnings("all")
public class TableIdentifiersDAOImpl extends EntityDaoSupport<TableIdentifiers> implements TableIdentifiersDAO {
	private boolean locked = false;

	private synchronized boolean lock() {
		if (!locked) {
			locked = true;
			return true;
		}
		return false;
	}

	@Override
	public String getBizIdentifier(Serializable s) throws BizLogicException {

		String name = s.getClass().getName();
		int pos = name.lastIndexOf('.');
		name = name.substring(pos + 1, name.length());
		while (!lock()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		TableIdentifiers id = this.getTableName(name);
		if (id == null) {
			unlock();
			throw new TableNotFoundException();
		}
		int index = id.getIdentifier().intValue();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String today = format.format(new Date());
		if (today.equals(id.getLast_date())) {
			index++;
		} else {
			index = 1;
		}
		id.setLast_date(today);
		id.setIdentifier(index);
		try {
			update(id);
		} catch (DaoAccessException e) {
			e.printStackTrace();
		}
		unlock();
		// String sellerPrefix = getSellerPrefix();
		if (index >= Math.pow(10, id.getId_length())) {
			throw new IdOutOfBoundsException();
		}
		StringBuffer sb = new StringBuffer();
		// sb.append(sellerPrefix);
		// sb.append("-");
		sb.append(id.getPrefix());
		sb.append(today);
		sb.append(formatNumber(index, id.getId_length()));
		String result = sb.toString();
		return result;
	}

	/**
	 * 根据TableName 获取Table
	 * 
	 * @param name
	 * @return
	 */
	private TableIdentifiers getTableName(String name) {
		List<TableIdentifiers> list=new ArrayList<TableIdentifiers>();
		StringBuffer sb=new StringBuffer();
		//String[] params = { name};
		sb.append("select * from table_identifiers t where t.table_name='"+name+"'");
		
		list=iSQLQuery.query(sb.toString(),TableIdentifiers.class,new SimplePage());
		//list = this.query(sb.toString(), params);
		
		if(list.size()>0){
			return list.get(0);
		}
 
		return null;
	}

	@Override
	public String getBizIdentifier(Serializable s, boolean bool)
			throws BizLogicException {

		String name = s.getClass().getName();
		int pos = name.lastIndexOf('.');
		name = name.substring(pos + 1, name.length());
		while (!lock()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		TableIdentifiers id = this.getTableName(name);
		if (id == null) {
			unlock();
			throw new TableNotFoundException();
		}
		int index = id.getIdentifier().intValue();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String today = format.format(new Date());
		if (today.equals(id.getLast_date())) {
			index++;
		} else {
			index = 1;
		}
		id.setLast_date(today);
		id.setIdentifier(index);
		try {
			update(id);
		} catch (DaoAccessException e) {
			e.printStackTrace();
		}
		unlock();
		String sellerPrefix = getSellerPrefix();
		if (index >= Math.pow(10, id.getId_length())) {
			throw new IdOutOfBoundsException();
		}
		StringBuffer sb = new StringBuffer();
		if (bool) {
			// sb.append(sellerPrefix);
			// sb.append("-");
		}
		if (id.getPrefix() != null) {
			sb.append(id.getPrefix());
		}
		sb.append(today);
		sb.append(formatNumber(index, id.getId_length()));
		String result = sb.toString();
		return result;

	}

	private String getSellerPrefix() {
		return "O";
	}

	@Override
	public String getGoodsIdentifier(Serializable s, String classCode)
			throws BizLogicException {
		String name = s.getClass().getName();
		int pos = name.lastIndexOf('.');
		name = name.substring(pos + 1, name.length());
		while (!lock()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		TableIdentifiers id = this.getTableName(name);
		if (id == null) {
			unlock();
			throw new TableNotFoundException();
		}
		int index = id.getIdentifier().intValue();
		id.setIdentifier(++index);
		try {
			update(id);
		} catch (DaoAccessException e) {
			e.printStackTrace();
		}
		unlock();
		// String sellerPrefix = getSellerPrefix();
		if (index >= Math.pow(10, id.getId_length())) {
			throw new IdOutOfBoundsException();
		}
		StringBuffer sb = new StringBuffer();
		// sb.append(sellerPrefix);
		// sb.append("-");
		sb.append(id.getPrefix());
		sb.append(classCode);
		sb.append(formatNumber(index, id.getId_length()));
		String result = sb.toString();
		return result;
	}

	@Override
	public String getNormalIdentifier(Serializable s) throws BizLogicException {
		String name = s.getClass().getName();
		int pos = name.lastIndexOf('.');
		name = name.substring(pos + 1, name.length());
		while (!lock()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		TableIdentifiers id = this.getTableName(name);
		if (id == null) {
			unlock();
			throw new TableNotFoundException();
		}
		int index = id.getIdentifier().intValue();
		id.setIdentifier(++index);
		try {
			update(id);
		} catch (DaoAccessException e) {
			e.printStackTrace();
		}
		unlock();
		String sellerPrefix = getSellerPrefix();
		if (index >= Math.pow(10, id.getId_length())) {
			throw new IdOutOfBoundsException();
		}
		StringBuffer sb = new StringBuffer();
		// sb.append(sellerPrefix);
		// sb.append("-");
		sb.append(id.getPrefix());
		sb.append(formatNumber(index, id.getId_length()));
		String result = sb.toString();
		return result;
	}

	private void unlock() {
		locked = false;
	}

	@Override
	public String getSubIdentifier(String tableName, String column, String value)
			throws BizLogicException {
		int index = 0;
		StringBuffer sb = new StringBuffer();
		sb.append("select count(*) as number from ");
		sb.append(tableName);
		sb.append(" where ");
		sb.append(column);
		sb.append("='");
		sb.append(value);
		sb.append("'");
		String sql = sb.toString();
		try {
			List<Map<String, Object>> list = iSQLQuery.query(sb.toString());
			index = ((Long) list.get(0).get("number")).intValue();
		} catch (Exception e) {
			e.printStackTrace();
			throw new TableNotFoundException();
		}
		index++;
		if (index >= 100) {
			throw new IdOutOfBoundsException();
		}
		return value + "-" + formatNumber(index, 2);
	}

	private String formatNumber(int n, int size) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < size; i++) {
			sb.append("0");
		}
		sb.append(n);
		String numbers = sb.toString();
		int len = numbers.length();
		numbers = numbers.substring(len - size, len);
		return numbers;
	}

	@Override
	public String[] getBizIdentifiers(Serializable s, int n)
			throws BizLogicException {
		if (n <= 0) {
			throw new QuantityNumberException();
		}
		String name = s.getClass().getName();
		int pos = name.lastIndexOf('.');
		name = name.substring(pos + 1, name.length());
		while (!lock()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		TableIdentifiers id = this.getTableName(name);
		if (id == null) {
			unlock();
			throw new TableNotFoundException();
		}
		int index = id.getIdentifier().intValue();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String today = format.format(new Date());
		if (!today.equals(id.getLast_date())) {
			index = 0;
		}
		id.setLast_date(today);
		id.setIdentifier(index + n);
		try {
			update(id);
		} catch (DaoAccessException e) {
			e.printStackTrace();
		}
		unlock();
		// String sellerPrefix = getSellerPrefix();
		if (index + n >= Math.pow(10, id.getId_length())) {
			throw new IdOutOfBoundsException();
		}
		String[] result = new String[n];
		for (int i = 1; i <= n; i++) {
			StringBuffer sb = new StringBuffer();
			// sb.append(sellerPrefix);
			// sb.append("-");
			sb.append(id.getPrefix());
			sb.append(today);
			sb.append(formatNumber(index + i, id.getId_length()));
			result[i - 1] = sb.toString();
		}
		return result;
	}

	@Override
	public String[] getSubIdentifiers(String tableName, String column,
			String value, int n) throws BizLogicException {
		if (n <= 0) {
			throw new QuantityNumberException();
		}
		int index = 0;
		StringBuffer sb = new StringBuffer();
		sb.append("select count(*) as number from ");
		sb.append(tableName);
		sb.append(" where ");
		sb.append(column);
		sb.append("='");
		sb.append(value);
		sb.append("'");
		String sql = sb.toString();
		try {
			List<Map<String, Object>> list = iSQLQuery.query(sql);
			index = ((Long) list.get(0).get("number")).intValue();
		} catch (Exception e) {
			e.printStackTrace();
			throw new TableNotFoundException();
		}
		if (index + n >= 100) {
			throw new IdOutOfBoundsException();
		}
		String[] result = new String[n];
		for (int i = 1; i <= n; i++) {
			result[i - 1] = value + "-" + formatNumber(index + i, 2);
		}
		return result;
	}

	@Override
	public String[] getGoodsIdentifiers(Serializable s, String[] classCode,
			int n) throws BizLogicException {
		if (n <= 0 || n != classCode.length) {
			throw new QuantityNumberException();
		}
		String name = s.getClass().getName();
		int pos = name.lastIndexOf('.');
		name = name.substring(pos + 1, name.length());
		while (!lock()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		TableIdentifiers id = this.getTableName(name);
		if (id == null) {
			unlock();
			throw new TableNotFoundException();
		}
		int index = id.getIdentifier().intValue();
		id.setIdentifier(index + n);
		try {
			update(id);
		} catch (DaoAccessException e) {
			e.printStackTrace();
		}
		unlock();
		// String sellerPrefix = getSellerPrefix();
		if (index >= Math.pow(10, id.getId_length())) {
			throw new IdOutOfBoundsException();
		}
		String[] result = new String[n];
		for (int i = 1; i <= n; i++) {
			StringBuffer sb = new StringBuffer();
			// sb.append(sellerPrefix);
			// sb.append("-");
			sb.append(id.getPrefix());
			sb.append(classCode);
			sb.append(formatNumber(index, id.getId_length()));
			result[i - 1] = sb.toString();
		}
		return result;
	}

	@Override
	public String[] getNormalIdentifiers(Serializable s, int n)
			throws BizLogicException {
		if (n <= 0) {
			throw new QuantityNumberException();
		}
		String name = s.getClass().getName();
		int pos = name.lastIndexOf('.');
		name = name.substring(pos + 1, name.length());
		while (!lock()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		TableIdentifiers id = this.getTableName(name);
		if (id == null) {
			unlock();
			throw new TableNotFoundException();
		}
		int index = id.getIdentifier().intValue();
		id.setIdentifier(index + n);
		try {
			update(id);
		} catch (DaoAccessException e) {
			e.printStackTrace();
		}
		unlock();
		// String sellerPrefix = getSellerPrefix();
		if (index >= Math.pow(10, id.getId_length())) {
			throw new IdOutOfBoundsException();
		}
		String[] result = new String[n];
		for (int i = 1; i <= n; i++) {
			StringBuffer sb = new StringBuffer();
			// sb.append(sellerPrefix);
			// sb.append("-");
			sb.append(id.getPrefix());
			sb.append(formatNumber(index, id.getId_length()));
			result[i - 1] = sb.toString();
		}
		return result;
	}

	@Override
	public String getPackageIdentifier(Serializable s, String goodsCode)
			throws BizLogicException {
		String name = s.getClass().getName();
		int pos = name.lastIndexOf('.');
		name = name.substring(pos + 1, name.length());
		while (!lock()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		TableIdentifiers id = this.getTableName(name);
		if (id == null) {
			unlock();
			throw new TableNotFoundException();
		}
		int index = id.getIdentifier().intValue();
		id.setIdentifier(++index);
		try {
			update(id);
		} catch (DaoAccessException e) {
			e.printStackTrace();
		}
		unlock();
		// String sellerPrefix = getSellerPrefix();
		if (index >= Math.pow(10, id.getId_length())) {
			throw new IdOutOfBoundsException();
		}
		StringBuffer sb = new StringBuffer();
		// sb.append(sellerPrefix);
		// sb.append("-");
		sb.append(id.getPrefix());
		sb.append(goodsCode);
		sb.append(formatNumber(index, 4));
		String result = sb.toString();
		return result;
	}

	@Override
	public List<String> getBizIdentifierBatch(Class<?> clazz, int size)
			throws BizLogicException {
		if(size < 0){
			throw new IdOutOfBoundsException();
		}
		
		String name = clazz.getName();
		int pos = name.lastIndexOf('.');
		name = name.substring(pos + 1, name.length());
		while (!lock()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		TableIdentifiers id = this.getTableName(name);
		if (id == null) {
			unlock();
			throw new TableNotFoundException();
		}
		int cur = id.getIdentifier().intValue();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String today = format.format(new Date());
		int start = today.equals(id.getLast_date())? cur+1:1;
		int end = start + size -1;
		if (end >= Math.pow(10, id.getId_length())) {
			unlock();
			throw new IdOutOfBoundsException();
		}else{
			id.setLast_date(today);
			id.setIdentifier(end);
			try {
				update(id);
			} catch (DaoAccessException e) {
				e.printStackTrace();
			}
			unlock();
		}
		// String sellerPrefix = getSellerPrefix();
		List<String> ids = new ArrayList<String>(size);
		for(int index=start; index<=end; index++){
			StringBuffer sb = new StringBuffer();
			// sb.append(sellerPrefix);
			// sb.append("-");
			sb.append(id.getPrefix());
			sb.append(today);
			sb.append(formatNumber(index, id.getId_length()));
			ids.add(sb.toString());
		}
		return ids;
	}

	public List<TableIdentifiers> findList(SimplePage page, TableIdentifiers object) {
		String sql="select id_length ,table_name,identifier,prefix,last_date  from table_identifiers where 1=1 ";
		if(!StringUtils.isEmpty(object.getTable_name())){
			sql +=" and table_name like '%"+object.getTable_name()+"%'";
		}
		List<TableIdentifiers> query = iSQLQuery.query(sql, TableIdentifiers.class, page);
		return query;
	}

}
