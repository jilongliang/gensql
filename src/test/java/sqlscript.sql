
declare
    iCnt number := 0; 
begin 
    select count(*) into iCnt from user_tables  where  lower(table_name) = lower('T_CLASS'); 
    if iCnt = 0 then -- 如果查询不到这个表就创建这个表
    execute immediate  'create table T_CLASS 
    (
        CLASS_ID    NUMBER(11) NOT NULL ,
        CLASS_NAME    VARCHAR2(50) NOT NULL,
        CLASS_NO    VARCHAR2(30) NOT NULL,
        CLASS_NUM    NUMBER(10) NOT NULL
    )'; 
    execute immediate 'comment on table T_CLASS is ''班级表''';
    execute immediate 'alter table T_CLASS add constraint PK_CLASS_ID primary key (CLASS_ID)' ;
    execute immediate 'comment on column T_CLASS.CLASS_NAME is ''班级名称''';
    execute immediate 'comment on column T_CLASS.CLASS_NO is ''班级编号''';
    execute immediate 'comment on column T_CLASS.CLASS_NUM is ''班级人数''';
    end if; 
end; 
/
commit

declare
    iCnt number := 0; 
begin 
    select count(*) into iCnt from user_tables  where  lower(table_name) = lower('T_STUDENT'); 
    if iCnt = 0 then -- 如果查询不到这个表就创建这个表
    execute immediate  'create table T_STUDENT 
    (
        USER_ID    NUMBER(11) NOT NULL ,
        ID_CARD_CODE    VARCHAR2(50) NOT NULL,
        USER_NAME    VARCHAR2(30) NOT NULL,
        PASS_WORD    VARCHAR2(30) NOT NULL,
        AGE    NUMBER(10)  NOT NULL,
        ADDRESS    VARCHAR2(255) ,
        CREATE_TIME    DATE
    )'; 
    execute immediate 'comment on table T_STUDENT is ''学生用户表''';
    execute immediate 'alter table T_STUDENT add constraint PK_USER_ID primary key (USER_ID)' ;
    execute immediate 'comment on column T_STUDENT.ID_CARD_CODE is ''身份证编号''';
    execute immediate 'comment on column T_STUDENT.USER_NAME is ''用户名''';
    execute immediate 'comment on column T_STUDENT.PASS_WORD is ''用户密码''';
    execute immediate 'comment on column T_STUDENT.AGE is ''年龄''';
    execute immediate 'comment on column T_STUDENT.ADDRESS is ''地址''';
    execute immediate 'comment on column T_STUDENT.CREATE_TIME is ''创建时间''';
    end if; 
end; 
/
commit


--删除T_STUDENT的唯一索引
declare
  iCnt number := 0;
begin
  select count(*) into iCnt from user_ind_columns where lower(index_name) = lower('stu_idx_card_code') and lower(table_name)=lower('T_STUDENT');
  if iCnt > 0 then  
     execute immediate 'drop index stu_idx_card_code '; 
  end if;
end;
/

--对学生表的身份证ID加唯一索引ID_CARD_CODE
declare
  iCnt number := 0;
begin
  select count(*) into iCnt from user_ind_columns  where  lower(index_name) = lower('stu_idx_card_code') and lower(table_name)=lower('T_STUDENT');
  if iCnt = 0 then  
     execute immediate 'create unique index stu_idx_card_code on T_STUDENT(id_card_code)'; 
  end if;
end;
/
----------------------------------------------------------------------------------

--删除学生表序列Id		
declare
  iCount integer;
begin
  select count(*) into iCount from user_sequences where upper(sequence_name)=upper('SQ_T_STUDENT');
  if iCount > 0 then
    execute immediate  'drop sequence SQ_T_STUDENT ';
  end if;
end;
/	
--创建学生表序列Id		
declare
  iCount integer;
begin
  select count(*) into iCount from user_sequences where upper(sequence_name)=upper('SQ_T_STUDENT');
  if iCount = 0 then
    execute immediate
      'create sequence SQ_T_STUDENT
        minvalue 1
        maxvalue 999999999999999999999999999
        start with 1
        increment by 1
        nocache';
  end if;
end;
/	

--传统创建视图脚本
 CREATE OR REPLACE VIEW V_STUDENT AS
    SELECT
       user_id,
       user_name ,  
       pass_word ,  
       age,       
       create_time, 
       id_card_code
     FROM T_STUDENT
WITH READ ONLY;   --只读视图
--声明式删除视图
declare
  iCnt number := 0;
begin 
  SELECT count(*) into iCnt  FROM all_views where upper(view_name)=upper('V_STUDENT');
  if iCnt > 0 then
    execute immediate 'drop VIEW V_STUDENT ';
  end if;
end;
/
 --声明式创建视图脚本,WITH READ ONLY表示只读视图
declare
  iCnt number := 0;
begin 
  SELECT count(*) into iCnt  FROM all_views where upper(view_name)=upper('V_STUDENT');
  if iCnt = 0 then
    execute immediate 'CREATE OR REPLACE VIEW V_STUDENT AS
        SELECT
          user_id,
          user_name ,  
          pass_word ,  
          age,       
          create_time, 
          ID_CARD_CODE
        FROM T_STUDENT
        WITH READ ONLY
      ';
  end if;
end;
/