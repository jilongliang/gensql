package com.flong.modules.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flong.commons.persistence.bean.SimplePage;
import com.flong.commons.persistence.dao.impl.EntityDaoSupport;
import com.flong.modules.dao.SequenceDao;
import com.flong.modules.pojo.Sequences;
import com.flong.modules.service.SequenceService;

/**
 * @Description	TODO
 * @ClassName	TableService
 * @Date		2017年3月1日 上午10:48:44
 * @Author		liangjilong
 * @Copyright (c) All Right Reserved , 2017.
 */
	
@Service
@Transactional
@SuppressWarnings("all")
public class SequenceServiceImpl extends  EntityDaoSupport<Sequences> implements SequenceService {
	@Autowired SequenceDao sequenceDao;
	/**
	 * @Description  获取数据库所有表
	 * @Author		liangjilong
	 * @Date		2017年2月23日 下午2:30:56
	 * @throws Exception 		参数
	 * @return 		List<String> 返回类型
	 */
	public  List<Sequences> querySequences(SimplePage page, Sequences object) {
		return sequenceDao.querySequences(page,object);
	}
	@Override
	public String createSequenceSQLScript(String sequence_name) {
		List<Sequences> list = sequenceDao.querySequences(sequence_name);
		StringBuffer buffer = new StringBuffer(); 
		if(list!=null && list.size()>0){
			Sequences sequences = list.get(0);
			buffer.append("--"+sequence_name).append(ENTER);
			buffer.append("declare").append(ENTER);
			buffer.append(TAB+TAB).append("iCnt number := 0;").append(ENTER);
			buffer.append(TAB).append("begin").append(ENTER);
			buffer.append(TAB).append(" select count(*) into iCnt from user_sequences where upper(sequence_name)=upper('"+sequence_name+"');").append(ENTER);
			buffer.append(TAB).append(" if iCnt = 0 then ").append(ENTER);
			buffer.append(TAB).append("   execute immediate 'create sequence "+sequence_name).append(ENTER);
			buffer.append(TAB).append("      minvalue "+sequences.getMin_value()).append(ENTER);
			buffer.append(TAB).append("      maxvalue "+sequences.getMax_value()).append(ENTER);
			buffer.append(TAB).append("      start with 1 ").append(ENTER);
			buffer.append(TAB).append("      increment by 1 ").append(ENTER);
			buffer.append(TAB).append("      nocache '; ").append(ENTER);
			buffer.append(TAB).append(" end if;").append(ENTER);
			buffer.append(TAB).append("end;").append(ENTER);
			buffer.append("/").append(ENTER);
			return buffer.toString();
		}
		return null;
	}
	@Override
	public String dropSequenceSQLScript(String sequence_name) {
		List<Sequences> list = sequenceDao.querySequences(sequence_name);
		StringBuffer buffer = new StringBuffer(); 
		if(list!=null && list.size()>0){
			Sequences sequences = list.get(0);
			buffer.append("--"+sequence_name).append(ENTER);
			buffer.append("declare").append(ENTER);
			buffer.append(TAB+TAB).append("iCnt number := 0;").append(ENTER);
			buffer.append(TAB).append("begin").append(ENTER);
			buffer.append(TAB).append(" select count(*) into iCnt from user_sequences where upper(sequence_name)=upper('"+sequence_name+"');").append(ENTER);
			buffer.append(TAB).append(" if iCnt > 0 then ").append(ENTER);
			buffer.append(TAB).append("   execute immediate  'drop sequence "+sequence_name+"' ;").append(ENTER);
			buffer.append(TAB).append(" end if;").append(ENTER);
			buffer.append(TAB).append("end;").append(ENTER);
			buffer.append("/").append(ENTER);
			return buffer.toString();
		}
		return null;
	}
	
	

}
