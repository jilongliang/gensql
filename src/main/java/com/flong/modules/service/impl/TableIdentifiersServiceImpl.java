/**
 * 
 */
package com.flong.modules.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flong.commons.lang.exception.BizLogicException;
import com.flong.commons.persistence.bean.SimplePage;
import com.flong.commons.persistence.dao.impl.EntityDaoSupport;
import com.flong.modules.dao.TableIdentifiersDAO;
import com.flong.modules.pojo.TableIdentifiers;
import com.flong.modules.service.TableIdentifiersService;

/**
 * @author Administrator
 *
 */
@Service
@Transactional
public class TableIdentifiersServiceImpl extends  EntityDaoSupport<TableIdentifiers>  implements TableIdentifiersService {
	
	@Autowired TableIdentifiersDAO tableIdentifiersDAO;

	@Override
	public String nextBizId(Serializable s) throws BizLogicException {		
		return tableIdentifiersDAO.getBizIdentifier(s);
	}
	@Override
	public String nextBizId(Serializable s, boolean bool)
			throws BizLogicException {
		return tableIdentifiersDAO.getBizIdentifier(s,bool);
	}

 

	@Override
	public String nextGoodsId(Serializable s, String classCode) throws BizLogicException {
		return tableIdentifiersDAO.getGoodsIdentifier(s, classCode);
	}

	@Override
	public String nextNormalId(Serializable s) throws BizLogicException {
		return tableIdentifiersDAO.getNormalIdentifier(s);
	}

	@Override
	public String nextSubId(String tableName, String column, String value)
			throws BizLogicException {
		return tableIdentifiersDAO.getSubIdentifier(tableName, column, value);
	}

	@Override
	public String[] nextBizIds(Serializable s, int n) throws BizLogicException {
		return tableIdentifiersDAO.getBizIdentifiers(s, n);
	}

	@Override
	public String[] nextGoodsIds(Serializable s, String[] classCode, int n)
			throws BizLogicException {
		return tableIdentifiersDAO.getGoodsIdentifiers(s, classCode, n);
	}

	@Override
	public String[] nextSubIds(String tableName, String column, String value, int n) throws BizLogicException {
		return tableIdentifiersDAO.getSubIdentifiers(tableName, column, value, n);
	}

	@Override
	public String[] nextNormalIds(Serializable s, int n)
			throws BizLogicException {
		return tableIdentifiersDAO.getNormalIdentifiers(s, n);
	}
	@Override
	public String nextPackageId(Serializable s, String goodsCode)
			throws BizLogicException {
		return tableIdentifiersDAO.getPackageIdentifier(s, goodsCode);
	}
	@Override
	public List<TableIdentifiers> findList(SimplePage page, TableIdentifiers object) {
		return tableIdentifiersDAO.findList(page,object);
	}

	

}
