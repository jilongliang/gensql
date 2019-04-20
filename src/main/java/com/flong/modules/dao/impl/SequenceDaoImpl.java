package com.flong.modules.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.flong.commons.lang.StringUtils;
import com.flong.commons.persistence.bean.SimplePage;
import com.flong.commons.persistence.dao.impl.EntityDaoSupport;
import com.flong.modules.dao.SequenceDao;
import com.flong.modules.pojo.Sequences;

@Repository
@SuppressWarnings("all")
public class SequenceDaoImpl extends EntityDaoSupport<Sequences> implements SequenceDao {

	private static String SELECT_SEQUENCE_ORACLE_SQL = "select sequence_name,min_value,max_value,increment_by,cycle_flag,order_flag,cache_size,last_number from user_sequences ";

	
	public List<Sequences> querySequences(SimplePage page, Sequences object) {
		String sql = SELECT_SEQUENCE_ORACLE_SQL+" where  1=1 ";//
		if(!StringUtils.isEmpty(object.getSequence_name())){
			sql +=" and sequence_name like '%"+object.getSequence_name()+"%'";
		}
		List<Sequences> query = iSQLQuery.query(sql, Sequences.class, page);
		return query;
	}
	
	
	public List<Sequences> querySequences(String sequence_name) {
		
		String sql = SELECT_SEQUENCE_ORACLE_SQL+" where upper(sequence_name) = upper('"+sequence_name+"') ";
		SimplePage simplePage =new SimplePage();
		simplePage.setRowCount(Integer.MAX_VALUE);
		List<Sequences> query = iSQLQuery.query(sql, Sequences.class, simplePage);
		return query;
	}
	
	 
	
}
