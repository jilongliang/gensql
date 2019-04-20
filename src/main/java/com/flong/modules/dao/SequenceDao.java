package com.flong.modules.dao;

import java.util.List;

import com.flong.commons.persistence.bean.SimplePage;
import com.flong.commons.persistence.dao.EntityDao;
import com.flong.modules.pojo.Sequences;

public interface SequenceDao extends EntityDao<Sequences> {
	
	public List<Sequences> querySequences(SimplePage page, Sequences object);

	public List<Sequences> querySequences(String sequence_name);
}
