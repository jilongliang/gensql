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