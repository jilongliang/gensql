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
