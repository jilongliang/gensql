--第一种方法: 查询dba_tab_columns

select COLUMN_NAME,DATA_TYPE,DATA_LENGTH from dba_tab_columns where table_name =upper('表名') order by COLUMN_NAME

--这种方法需要有DBA权限

--第二种方法: 查询 user_tab_cols
select  COLUMN_NAME,DATA_TYPE,DATA_LENGTH   from  user_tab_cols   where table_name=upper('表名')

order by COLUMN_NAME
--这种方法只能查找当前用户下的表

--第三种方法: 查询ALL_TAB_COLUMNS
select distinct COLUMN_NAME,DATA_TYPE,DATA_LENGTH 
from ALL_TAB_COLUMNS
WHERE TABLE_NAME= upper('表名')
--这种方法可以查询所有用户下的表

---------------------------补充-------------------------------------------------------------

--增加cw_srcbpb表字段
alter table cw_srcbpb  add (SRCBPB_RJBPBL varchar2(100) );
--修改cw_srcbpb表字段
alter table cw_srcbpb  modify (SRCBPB_RJBPBL number(30,3) );

--Oracle查看所有表和字段


--获取表：

select table_name from user_tables; --当前用户的表       
select table_name from all_tables; --所有用户的表   
select table_name from dba_tables; --包括系统表
select table_name from dba_tables where owner='LBSP'; --获取用户***所拥有的表这里的用户名要记得是用大写的。
-- 获取表字段：其实这里是根据用户的权限来获取字段的属性（表名要大写）
select * from user_tab_columns where Table_Name='用户表';--获取用户表的所有字段还有字段的属性。
select * from all_tab_columns where Table_Name='用户表';--获取用户表的所有字段还有字段的属性。所属用户是***
select * from dba_tab_columns where Table_Name='用户表';--获取用户表的所有字段还有字段的属性。所属用户是***

--获取表注释：
select * from user_tab_comments
--user_tab_comments：table_name,table_type,comments
--相应的还有dba_tab_comments，all_tab_comments，这两个比user_tab_comments多了ower列。

--获取字段注释：
select * from user_col_comments
--user_col_comments：table_name,column_name,comments
--相应的还有dba_col_comments，all_col_comments，这两个比user_col_comments多了ower列。
--查询出用户所有表的索引
select   *   from   user_indexes
--查询用户表的索引(非聚集索引): 
select   *   from   user_indexes where   uniqueness='NONUNIQUE'
--查询用户表的主键(聚集索引): 
select   *   from   user_indexes where   uniqueness='UNIQUE' 
--查询表的索引
select t.*,i.index_type from user_ind_columns t,user_indexes i where t.index_name = i.index_name and
t.table_name='NODE'
--查询表的主键
select cu.* from user_cons_columns cu, user_constraints au where cu.constraint_name = au.constraint_name and
au.constraint_type = 'P' AND cu.table_name = 'NODE'
--查找表的唯一性约束（包括名称，构成列）： 
select column_name from user_cons_columns cu, user_constraints au where cu.constraint_name=au.constraint_name and
cu.table_name='NODE'
--查找表的外键
select * from user_constraints c where c.constraint_type = 'R' and c.table_name='STAFFPOSITION';
--查询外键约束的列名： 
select * from user_cons_columns cl where cl.constraint_name = '外键名称';
--查询引用表的键的列名：
select * from user_cons_columns cl where cl.constraint_name = '外键引用表的键名';











 
