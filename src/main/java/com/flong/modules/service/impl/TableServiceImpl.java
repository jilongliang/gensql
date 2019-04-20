package com.flong.modules.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flong.commons.persistence.bean.SimplePage;
import com.flong.commons.persistence.dao.impl.EntityDaoSupport;
import com.flong.commons.utils.ObjectUtil;
import com.flong.modules.dao.TableColumnsDao;
import com.flong.modules.dao.TableDao;
import com.flong.modules.dao.UserColCommentsDao;
import com.flong.modules.dao.UserConsColumnsDao;
import com.flong.modules.dao.UserTabCommentsDao;
import com.flong.modules.pojo.Sequences;
import com.flong.modules.pojo.UserColComments;
import com.flong.modules.pojo.UserConsColumns;
import com.flong.modules.pojo.UserTabColumns;
import com.flong.modules.pojo.UserTabComments;
import com.flong.modules.pojo.UserTables;
import com.flong.modules.service.TableService;

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
public class TableServiceImpl extends  EntityDaoSupport<UserTables> implements TableService {
	
	@Autowired TableDao tableDao;
	@Autowired TableColumnsDao  tableColumnsDao;
	@Autowired UserColCommentsDao  userColCommentsDao;
	@Autowired UserTabCommentsDao  userTabCommentsDao;
	@Autowired UserConsColumnsDao  userConsColumnsDao;
	
	public  List<UserTables> queryTables(SimplePage page, UserTables object) {
		return tableDao.queryTables(page,object);
	}
	
	public List<UserTables> queryTableByName(String table_name){
		
		return tableDao.queryTableByName(table_name);
	}

	@Override
	public String createTableSQLScript(String tableName) {
		String result = "";

		//1、判断是否存在这个表
		List<UserTables> findList = queryTableByName( tableName);
		//2、组装execute immediate 'create table ...'
		if(findList!=null &&findList.size()>0){
			StringBuffer buffer =  new StringBuffer();
			//3、获取表的字段名称，并且获取约束是否为空(Null or Not null)
			List<UserTabColumns> userTabColumnList = tableColumnsDao.getUserTabColumnsByName(tableName);
			List<UserTabComments> userTabCommentList = userTabCommentsDao.getUserTabCommentsByName(tableName);
			List<UserColComments> userColCommentList = userColCommentsDao.getUserColCommentsByName(tableName);
			UserConsColumns userConsColumns = userConsColumnsDao.getUserConsColumnsByName(tableName);
			//----组装表字段列名
			int size = userTabColumnList.size()-1;//计算最后一个表列名
			buffer.append("--创建"+tableName+"表").append(ENTER);
			buffer.append("declare").append(ENTER);
			buffer.append(TAB).append("iCnt number := 0;").append(ENTER);
			buffer.append("begin ").append(ENTER);
			buffer.append("  select count(*) into iCnt from user_tables where lower(table_name) = lower('"+tableName+"');").append(ENTER); 
			buffer.append("  if iCnt = 0 then ").append(ENTER);  
			buffer.append(TAB).append("execute immediate 'create table "+tableName+"").append(ENTER);
			buffer.append(TAB).append("(").append(ENTER);
			
			for (int i = 0; i < userTabColumnList.size(); i++) {
				UserTabColumns userTabColumns = userTabColumnList.get(i);
				UserColComments userColComments = userColCommentList.get(i);
				
				String column_name1 = userColComments.getColumn_name();//列名
				
				String column_name2 = userTabColumns.getColumn_name();//列名
				String nullable = userTabColumns.getNullable();//是否允许为空
				String data_type = userTabColumns.getData_type();//数据库表的字段类型.
				BigDecimal data_length = userTabColumns.getData_length();
				BigDecimal data_precision = userTabColumns.getData_precision();
				BigDecimal data_scale = userTabColumns.getData_scale();
				
				if(ObjectUtil.isNotEmpty(column_name1) &&  ObjectUtil.isNotEmpty(column_name2) && column_name1.equalsIgnoreCase(column_name2)){
					
					//判断表字段类型是否为NULL或NOT NULL
					if(ObjectUtil.isNotEmpty(nullable) && !"N".equalsIgnoreCase(nullable)){
						if(size==i){
							if("VARCHAR2".equalsIgnoreCase(data_type) || "NVARCHAR2".equalsIgnoreCase(data_type) ||"CHAR".equalsIgnoreCase(data_type)){
									buffer.append(TAB+TAB+column_name1+TAB+data_type+"("+data_length+")" ).append(ENTER);
							}else if("NUMBER".equalsIgnoreCase(data_type)){
								if(data_precision!= null && data_scale!=null){
									buffer.append(TAB+TAB+column_name1+TAB+data_type+"("+data_precision+","+data_scale+") ").append(ENTER);
								}else if(data_precision!= null && data_scale==null){
									buffer.append(TAB+TAB+column_name1+TAB+data_type+"("+data_precision+",0) ").append(ENTER);
								}else{
									buffer.append(TAB+TAB+column_name1+TAB+data_type).append(ENTER);
								}
							}else if(("DATE".equalsIgnoreCase(data_type))||(data_type!=null && data_type.contains("TIMESTAMP")) ){
								buffer.append(TAB+TAB+column_name1+TAB+data_type).append(ENTER);
							}else if("LONG".equalsIgnoreCase(data_type) ||"NCLOB".equalsIgnoreCase(data_type)){
								buffer.append(TAB+TAB+column_name1+TAB+data_type).append(ENTER);
							}
						}else{
							//为空但是有逗号分开
							if("VARCHAR2".equalsIgnoreCase(data_type) || "NVARCHAR2".equalsIgnoreCase(data_type) ||"CHAR".equalsIgnoreCase(data_type)){
									buffer.append(TAB+TAB+column_name1+TAB+data_type+"("+data_length+")" ).append(COMMA).append(ENTER);
							}else if("NUMBER".equalsIgnoreCase(data_type)){
								if(data_precision!= null && data_scale!=null){
									buffer.append(TAB+TAB+column_name1+TAB+data_type+"("+data_precision+","+data_scale+") ").append(COMMA).append(ENTER);
								}else if(data_precision!= null && data_scale==null){
									buffer.append(TAB+TAB+column_name1+TAB+data_type+"("+data_precision+",0) ").append(COMMA).append(ENTER);
								}else{
									buffer.append(TAB+TAB+column_name1+TAB+data_type).append(COMMA).append(ENTER);
								}
							}else if(("DATE".equalsIgnoreCase(data_type))||(data_type!=null && data_type.contains("TIMESTAMP")) ){
								buffer.append(TAB+TAB+column_name1+TAB+data_type).append(COMMA).append(ENTER);
							}else if("LONG".equalsIgnoreCase(data_type) ||"NCLOB".equalsIgnoreCase(data_type)){
								buffer.append(TAB+TAB+column_name1+TAB+data_type).append(COMMA).append(ENTER);
							}
						}
					}else{
						if(size==i){
							if("VARCHAR2".equalsIgnoreCase(data_type) || "NVARCHAR2".equalsIgnoreCase(data_type) ||"CHAR".equalsIgnoreCase(data_type)){
									buffer.append(TAB+TAB+column_name1+TAB+data_type+"("+data_length+")  not null "  ).append(ENTER);
							}else if("NUMBER".equalsIgnoreCase(data_type)){
								if(data_precision!= null && data_scale!=null){
									buffer.append(TAB+TAB+column_name1+TAB+data_type+"("+data_precision+","+data_scale+")  not null").append(ENTER);
								}else if(data_precision!= null && data_scale==null){
									buffer.append(TAB+TAB+column_name1+TAB+data_type+"("+data_precision+",0)  not null ").append(ENTER);
								}else{
									buffer.append(TAB+TAB+column_name1+TAB+data_type+"").append(ENTER);
								}
							}else if(("DATE".equalsIgnoreCase(data_type))||(data_type!=null && data_type.contains("TIMESTAMP")) ){
								buffer.append(TAB+TAB+column_name1+TAB+data_type).append(ENTER);
							}else if("LONG".equalsIgnoreCase(data_type) ||"NCLOB".equalsIgnoreCase(data_type)){
								buffer.append(TAB+TAB+column_name1+TAB+data_type).append(ENTER);
							}
							
						}else{
							if("VARCHAR2".equalsIgnoreCase(data_type) || "NVARCHAR2".equalsIgnoreCase(data_type) ||"CHAR".equalsIgnoreCase(data_type)){
									buffer.append(TAB+TAB+column_name1+TAB+data_type+"("+data_length+")  not null "  ).append(COMMA).append(ENTER);
							}else if("NUMBER".equalsIgnoreCase(data_type)){
								if(data_precision!= null && data_scale!=null){
									buffer.append(TAB+TAB+column_name1+TAB+data_type+"("+data_precision+","+data_scale+")  not null").append(COMMA).append(ENTER);
								}else if(data_precision!= null && data_scale==null){
									buffer.append(TAB+TAB+column_name1+TAB+data_type+"("+data_precision+",0)  not null ").append(COMMA).append(ENTER);
								}else{
									buffer.append(TAB+TAB+column_name1+TAB+data_type+" ").append(COMMA).append(ENTER);
								}
							}else if(("DATE".equalsIgnoreCase(data_type))||(data_type!=null && data_type.contains("TIMESTAMP")) ){
								buffer.append(TAB+TAB+column_name1+TAB+data_type).append(COMMA).append(ENTER);
							}else if("LONG".equalsIgnoreCase(data_type) ||"NCLOB".equalsIgnoreCase(data_type)){
								buffer.append(TAB+TAB+column_name1+TAB+data_type).append(COMMA).append(ENTER);
							}
						}
					} 
				}
			}
			buffer.append(TAB).append(")';").append(ENTER);
			//4、获取表名给予表加备注.（有就加，无默认表描述）
			if(ObjectUtil.isNotEmpty(userTabCommentList) && ObjectUtil.isNotEmpty(userTabCommentList.get(0))){
				String comments = userTabCommentList.get(0).getComments();
		    	comments = (comments==null?" this is "+tableName+" tableName ":comments);
				buffer.append(TAB).append("execute immediate 'comment on table "+tableName +" is ''"+comments+"'''").append(";").append(ENTER);
			}
			//5、添加主键执行SQL脚本
			if(ObjectUtil.isNotEmpty(userConsColumns) && ObjectUtil.isNotEmpty(userConsColumns.getConstraint_name())){
				buffer.append(TAB).append("execute immediate 'alter table "+tableName).append(" add constraint "+userConsColumns.getConstraint_name()+" ");
				buffer.append("primary key ("+userConsColumns.getColumn_name()+")' ;").append(ENTER);
			}
			//6、获取表字段名给予字段加备注（有就加，无默认字段描述）
		    for(UserColComments col: userColCommentList){
		    	String comments = col.getComments();
		    	String column_name = col.getColumn_name();
		    	comments = (comments==null?" this is "+column_name+" columnName ":comments);
		    	buffer.append(TAB).append("execute immediate 'comment on column "+tableName+"."+col.getColumn_name()+" is ''"+comments+"'''").append(";").append(ENTER);
		    }
		    
		    //---end if
		    buffer.append(" end if;").append(ENTER);
		    buffer.append("end;").append(ENTER);
		    buffer.append("/").append(ENTER);
			result = buffer.toString();
		}
		return result;
	
	}

	
	
	@Override
	public String dropTableSQLScript(String table_name) {
		
		List<UserTables> list = queryTableByName(table_name);
		StringBuffer buffer = new StringBuffer(); 
		if(list!=null && list.size()>0){
			UserTables sequences = list.get(0);
			buffer.append("--"+table_name).append(ENTER);
			buffer.append("declare").append(ENTER);
			buffer.append(TAB+TAB).append("iCnt number := 0;").append(ENTER);
			buffer.append(TAB).append("begin").append(ENTER);
			buffer.append(TAB).append(" select count(*) into iCnt from user_tables  where  lower(table_name) = '"+table_name+"'; --根据表进行查询").append(ENTER);
			buffer.append(TAB).append(" if iCnt > 0 then ").append(ENTER);
			buffer.append(TAB).append(" 	execute immediate 'drop table "+table_name+"'; --删除表,表结构和数据一起清空 ").append(ENTER);
			buffer.append(TAB).append(" end if;").append(ENTER);
			buffer.append(TAB).append("end;").append(ENTER);
			buffer.append("/").append(ENTER);
			return buffer.toString();
		}
		
		return null;
	}

	

}
