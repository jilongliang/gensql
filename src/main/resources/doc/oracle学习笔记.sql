
-- 首先,以超级管理员的身份登录oracle  
     sqlplus sys/123456 as sysdba  
   
 --然后,解除对scott用户的锁  
     alter user scott account unlock;  
 --那么这个用户名就能使用了。  
 --(默认全局数据库名orcl)  
   
 1、select ename, sal * 12 from emp; --计算年薪  
 2、select 2*3 from dual;  --计算一个比较纯的数据用dual表 
 3、select sysdate from dual;  --查看当前的系统时间  
 4、select ename, sal*12 anuual_sal from emp; --给搜索字段更改名称（双引号 keepFormat 别名有特殊字符,要加双引号）。  
 5、--任何含有空值的数学表达式,最后的计算结果都是空值。  
 6、select ename||sal from emp;  --(将sal的查询结果转化为字符串,与ename连接到一起,相当于Java中的字符串连接)  
 7、select ename||'afasjkj' from emp;   --字符串的连接  
 8、select distinct deptno from emp;   --消除deptno字段重复的值  
 9、select distinct deptno , job from emp; --将与这两个字段都重复的值去掉  
 10、select * from emp where deptno=10;   --(条件过滤查询)               
 11、select * from emp where empno > 10;  --大于 过滤判断  
 12、select * from emp where empno <> 10  --不等于  过滤判断  
 13、select * from emp where ename > 'cba';  --字符串比较,实际上比较的是每个字符的AscII值,与在Java中字符串的比较是一样的  
 14、select ename, sal from emp where sal between 800 and 1500;  --(between and过滤,包含800 1500)  
 15、select ename, sal, comm from emp where comm is null;  --(选择comm字段为null的数据)  
 16、select ename, sal, comm from emp where comm is not null;  --(选择comm字段不为null的数据)  
 17、select ename, sal, comm from emp where sal in (800, 1500,2000);  --(in 表范围)  
 18、select ename, sal, hiredate from emp where hiredate > '02-2月-1981'; --(只能按照规定的格式写)  
 19、select ename, sal from emp where deptno =10 or sal >1000;  
 20、select ename, sal from emp where deptno =10 and sal >1000;  
 21、select ename, sal, comm from emp where sal not in (800, 1500,2000);  --(可以对in指定的条件进行取反)  
 22、select ename from emp where ename like '%ALL%';   --(模糊查询)  
 23、select ename from emp where ename like '_A%';    --(取第二个字母是A的所有字段)  
 24、select ename from emp where ename like '%/%%';   --(用转义字符/查询字段中本身就带%字段的)  
 25、select ename from emp where ename like '%$%%' escape '$';   --(用转义字符/查询字段中本身就带%字段的)  
 26、select * from dept order by deptno desc; (使用order by  desc字段 对数据进行降序排列 默认为升序asc);  
 27、select * from dept where deptno <>10 order by deptno asc;   --(我们可以将过滤以后的数据再进行排序)    
 28、select ename, sal, deptno from emp order by deptno asc, ename desc;   --(按照多个字段排序 首先按照deptno升序排列,当detpno相同时,内部再按照ename的降序排列)  
 29、select lower(ename) from emp;  --(函数lower() 将ename搜索出来后全部转化为小写)；  
 30、select ename from emp where lower(ename) like '_a%';  --(首先将所搜索字段转化为小写,然后判断第二个字母是不是a)  
 31、select substr(ename, 2, 3) from emp;    --(使用函数substr() 将搜素出来的ename字段从第二个字母开始截,一共截3个字符)  
 32、select chr(65) from dual;  --(函数chr() 将数字转化为AscII中相对应的字符)   
 33、select ascii('A') from dual;  --(函数ascii()与32中的chr()函数是相反的 将相应的字符转化为相应的Ascii编码）                                                                                                                                                                                                                                                                                                                                             )  
 34、select round(23.232) from dual;  --(函数round() 进行四舍五入操作)  
 35、select round(23.232, 2) from dual;  --(四舍五入后保留的小数位数 0 个位 -1 十位)  
 36、select to_char(sal, '$99,999.9999')from emp;  --(加$符号加入千位分隔符,保留四位小数,没有的补零)  
 37、select to_char(sal, 'L99,999.9999')from emp;  --(L 将货币转化为本地币种此处将显示￥人民币)  
 38、select to_char(sal, 'L00,000.0000')from emp;  --(补零位数不一样,可到数据库执行查看)  
 39、select to_char(hiredate, 'yyyy-MM-DD HH:MI:SS') from emp;  --(改变日期默认的显示格式)  
 40、select to_char(sysdate, 'yyyy-MM-DD HH:MI:SS') from dual;  --(用12小时制显示当前的系统时间)  
 41、select to_char(sysdate, 'yyyy-MM-DD HH24:MI:SS') from dual;  --(用24小时制显示当前的系统时间)  
 42、select ename, hiredate from emp where hiredate > to_date('1981-2-20 12:24:45','YYYY-MM-DD HH24:MI:SS');   --(函数to-date 查询公司在所给时间以后入职的人员)  
 43、select sal from emp where sal > to_number('$1,250.00', '$9,999.99');   --(函数to_number()求出这种薪水里带有特殊符号的)  
 44、select ename, sal*12 +  nvl(comm,0) from emp;   --(函数nvl() 求出员工的"年薪 + 提成(或奖金)问题")  
 45、select max(sal) from emp;  -- (函数max() 求出emp表中sal字段的最大值)  
 46、select min(sal) from emp;  -- (函数max() 求出emp表中sal字段的最小值)  
 47、select avg(sal) from emp;  --(avg()求平均薪水);  
 48、select to_char(avg(sal), '999999.99') from emp;   --(将求出来的平均薪水只保留2位小数)  
 49、select round(avg(sal), 2) from emp;  --(将平均薪水四舍五入到小数点后2位)  
 50、select sum(sal) from emp;  --(求出每个月要支付的总薪水)  
   
 ------------------------/组函数(共5个)：将多个条件组合到一起最后只产生一个数据------min() max() avg() sum() count()----------------------------/  
 51、select count(*) from emp;  --求出表中一共有多少条记录  
 52、select count(*) from emp where deptno=10;  --再要求一共有多少条记录的时候,还可以在后面跟上限定条件  
 53、select count(distinct deptno) from emp;   --统计部门编号前提是去掉重复的值  
 ------------------------聚组函数group by() --------------------------------------  
 54、select deptno, avg(sal) from emp group by deptno;  --按照deptno分组,查看每个部门的平均工资  
 55、select max(sal) from emp group by deptno, job; --分组的时候,还可以按照多个字段进行分组,两个字段不相同的为一组  
 56、select ename from emp where sal = (select max(sal) from emp); --求出  
 57、select deptno, max(sal) from emp group by deptno; --搜素这个部门中薪水最高的的值  
 --------------------------------------------------having函数对于group by函数的过滤 不能用where--------------------------------------  
 58、select deptno, avg(sal) from emp group by deptno having avg(sal) >2000; （order by ）--求出每个部门的平均值,并且要 > 2000  
 59、select avg(sal) from emp where sal >1200 group by deptno having avg(sal) >1500 order by avg(sal) desc;--求出sal>1200的平均值按照deptno分组,平均值要>1500最后按照sal的倒序排列  
 60、select ename,sal from emp where sal > (select avg(sal) from emp);  --求那些人的薪水是在平均薪水之上的。  
 61、select ename, sal from emp join (select max(sal) max_sal ,deptno from emp group by deptno) t on (emp.sal = t.max_sal and emp.deptno=t.deptno);  --查询每个部门中工资最高的那个人  
 ------------------------------/等值连接--------------------------------------  
 62、select e1.ename, e2.ename from emp e1, emp e2 where e1.mgr = e2.empno;  --自连接,把一张表当成两张表来用  
 63、select ename, dname from emp, dept;  --92年语法 两张表的连接 笛卡尔积。  
 64、select ename, dname from emp cross join dept; --99年语法 两张表的连接用cross join  
 65、select ename, dname from emp, dept where emp.deptno = dept.deptno; -- 92年语法 表连接 + 条件连接  
 66、select ename, dname from emp join dept on(emp.deptno = dept.deptno); -- 新语法  
 67、select ename,dname from emp join dept using(deptno); --与66题的写法是一样的,但是不推荐使用using ： 假设条件太多  
 --------------------------------------/非等值连接------------------------------------------/  
 68、select ename,grade from emp e join salgrade s on(e.sal between s.losal and s.hisal); --两张表的连接 此种写法比用where更清晰  
 69、select ename, dname, grade from emp e  
     join dept d on(e.deptno = d.deptno)  
     join salgrade s on (e.sal between s.losal and s.hisal)  
     where ename not like '_A%';  --三张表的连接  
 70、select e1.ename, e2.ename from emp e1 join emp e2 on(e1.mgr = e2.empno); --自连接第二种写法,同62  
 71、select e1.ename, e2.ename from emp e1 left join emp e2 on(e1.mgr = e2.empno); --左外连接 把左边没有满足条件的数据也取出来  
 72、select ename, dname from emp e right join dept d on(e.deptno = d.deptno); --右外连接  
 73、select deptno, avg_sal, grade from (select deptno, avg(sal) avg_sal from emp group by deptno) t join salgrade s  on    (t.avg_sal between s.losal and s.hisal);--求每个部门平均薪水的等级  
 74、select ename from emp where empno in (select mgr from emp); -- 在表中搜索那些人是经理  
 75、select sal from emp where sal not in(select distinct e1.sal from emp e1 join emp e2 on(e1.sal < e2.sal)); -- 面试题 不用组函数max()求薪水的最大值  
 76、select deptno, max_sal from  
     (select avg(sal) max_sal,deptno from emp group by deptno)  
         where max_sal =  
         (select max(max_sal) from  
          (select avg(sal) max_sal,deptno from emp group by deptno)  
     );--求平均薪水最高的部门名称和编号。  
 77、select t1.deptno, grade, avg_sal from  
       (select deptno, grade, avg_sal from  
     (select deptno, avg(sal) avg_sal from emp group by deptno) t  
         join salgrade s on(t.avg_sal between s.losal and s.hisal)  
       ) t1  
     join dept on (t1.deptno = dept.deptno)  
     where t1.grade =   
       (  
         select min(grade) from  
           (select deptno, grade, avg_sal from  
     (select deptno, avg(sal) avg_sal from emp group by deptno) t  
     join salgrade s on(t.avg_sal between s.losal and s.hisal)  
      )  
    )--求平均薪水等级最低的部门的名称 哈哈 确实比较麻烦  
 78、create view v$_dept_avg_sal_info as  
     select deptno, grade, avg_sal from  
        (select deptno, avg(sal) avg_sal from emp group by deptno) t  
     join salgrade s on(t.avg_sal between s.losal and s.hisal);  
     --视图的创建,一般以v$开头,但不是固定的  
   
   
 79、select t1.deptno, grade, avg_sal from v$_dept_avg_sal_info t1  
     join dept on (t1.deptno = dept.deptno)  
     where t1.grade =   
       (  
         select min(grade) from  
          v$_dept_avg_sal_info t1  
      )  
    )--求平均薪水等级最低的部门的名称 用视图,能简单一些,相当于Java中方法的封装  
   
 80、---创建视图出现权限不足时候的解决办法：  
     conn sys/admin as sysdba;  
         --显示：连接成功 Connected  
     grant create table, create view to scott;  
        -- 显示: 授权成功 Grant succeeded  
 81、-------求比普通员工最高薪水还要高的经理人的名称 -------  
     select ename, sal from emp where empno in  
        (select distinct mgr from emp where mgr is not null)  
     and sal >  
     (  
        select max(sal) from emp where empno not in  
          (select distinct mgr from emp where mgr is not null)  
     )  
 82、---面试题：比较效率  
        select * from emp where deptno = 10 and ename like '%A%';--好,将过滤力度大的放在前面  
        select * from emp where ename like '%A%' and deptno = 10;  
 83、-----表的备份  
        create table dept2 as select * from dept;  
 84、-----插入数据  
         insert into dept2 values(50,'game','beijing');  
       ----只对某个字段插入数据  
         insert into dept2(deptno,dname) values(60,'game2');  
 85、-----将一个表中的数据完全插入另一个表中（表结构必须一样）  
     insert into dept2 select * from dept;  
 86、-----求前五名员工的编号和名称（使用虚字段rownum 只能使用 < 或 = 要使用 > 必须使用子查询）  
     select empno,ename from emp where rownum <= 5;  
 86、----求10名雇员以后的雇员名称--------  
     select ename from (select rownum r,ename from emp) where r > 10;  
 87、----求薪水最高的前5个人的薪水和名字---------  
     select ename, sal from (select ename, sal from emp order by sal desc) where rownum <=5;    
 88、----求按薪水倒序排列后的第6名到第10名的员工的名字和薪水--------  
     select ename, sal from  
            (select ename, sal, rownum r from  
               (select ename, sal from emp order by sal desc)  
            )  
         where r>=6 and r<=10  
 89、----------------创建新用户---------------  
     1、backup scott--备份  
         exp--导出  
     2、create user  
         create user guohailong identified(认证) by guohailong  default tablespace users quota(配额) 10M on users  
         grant create session(给它登录到服务器的权限),create table, create view to guohailong  
     3、import data  
         imp  
 90、-----------事务回退语句--------  
     rollback;  
       
 91、-----------事务确认语句--------  
     commit;--此时再执行rollback无效  
   
 92、--当正常断开连接的时候例如exit,事务自动提交。  当非正常断开连接,例如直接关闭dos窗口或关机,事务自动提交  
 93、/*有3个表S,C,SC   
     S（SNO,SNAME）代表（学号,姓名）   
     C（CNO,CNAME,CTEACHER）代表（课号,课名,教师）   
     SC（SNO,CNO,SCGRADE）代表（学号,课号成绩）   
     问题：   
     1,找出没选过“黎明”老师的所有学生姓名。   
     2,列出2门以上（含2门）不及格学生姓名及平均成绩。   
     3,即学过1号课程有学过2号课所有学生的姓名。  
     */答案：  
     1、  
         select sname from s join sc on(s.sno = sc.sno) join c on (sc.cno = c.cno) where cteacher <> '黎明';  
     2、  
         select sname where sno in (select sno from sc where scgrade < 60 group by sno having count(*) >=2);  
     3、  
         select sname from s where sno in (select sno, from sc where cno=1 and cno in  
                             (select distinct sno from sc where cno = 2);  
                          )  
   
 94、--------------创建表--------------  
        create table stu  
     (  
     id number(6),  
     name varchar2(20) constraint stu_name_mm not null,  
     sex number(1),  
     age number(3),  
     sdate date,  
     grade number(2) default 1,  
     class number(4),  
     email varchar2(50) unique  
     );  
 95、--------------给name字段加入 非空 约束,并给约束一个名字,若不取,系统默认取一个-------------  
        create table stu  
     (  
     id number(6),  
     name varchar2(20) constraint stu_name_mm not null,  
     sex number(1),  
     age number(3),  
     sdate date,  
     grade number(2) default 1,  
     class number(4),  
     email varchar2(50)  
     );  
 96、--------------给nameemail字段加入 唯一 约束 两个 null值 不为重复-------------  
        create table stu  
     (  
     id number(6),  
     name varchar2(20) constraint stu_name_mm not null,  
     sex number(1),  
     age number(3),  
     sdate date,  
     grade number(2) default 1,  
     class number(4),  
     email varchar2(50) unique  
     );  
 97、--------------两个字段的组合不能重复 约束：表级约束-------------  
        create table stu  
     (  
     id number(6),  
     name varchar2(20) constraint stu_name_mm not null,  
     sex number(1),  
     age number(3),  
     sdate date,  
     grade number(2) default 1,  
     class number(4),  
     email varchar2(50),  
     constraint stu_name_email_uni unique(email, name)  
     );  
 98、--------------主键约束-------------  
        create table stu  
     (  
     id number(6),  
     name varchar2(20) constraint stu_name_mm not null,  
     sex number(1),  
     age number(3),  
     sdate date,  
     grade number(2) default 1,  
     class number(4),  
     email varchar2(50),  
     constraint stu_id_pk primary key (id),  
     constraint stu_name_email_uni unique(email, name)  
     );  
   99、--------------外键约束   被参考字段必须是主键 -------------  
        create table stu  
     (  
     id number(6),  
     name varchar2(20) constraint stu_name_mm not null,  
     sex number(1),  
     age number(3),  
     sdate date,  
     grade number(2) default 1,  
     class number(4) references class(id),  
     email varchar2(50),  
     constraint stu_class_fk foreign key (class) references class(id),  
     constraint stu_id_pk primary key (id),  
     constraint stu_name_email_uni unique(email, name)  
     );  
       
     create table class   
     (  
     id number(4) primary key,  
     name varchar2(20) not null  
     );  
 100、---------------修改表结构,添加字段------------------  
     alter table stu add(addr varchar2(29));  
 101、---------------删除字段--------------------------  
     alter table stu drop (addr);  
 102、---------------修改表字段的长度------------------  
     alter table  stu modify (addr varchar2(50));--更改后的长度必须要能容纳原先的数据  
 103、----------------删除约束条件----------------  
     alter table stu drop constraint  约束名  
 104、-----------修改表结构添加约束条件---------------  
     alter table  stu add constraint stu_class_fk foreign key (class) references class (id);  
 105、---------------数据字典表----------------  
      desc dictionary;  
      --数据字典表共有两个字段 table_name comments  
      --table_name主要存放数据字典表的名字  
      --comments主要是对这张数据字典表的描述  
        
 105、---------------查看当前用户下面所有的表、视图、约束-----数据字典表user_tables---  
     select table_name from user_tables;  
     select view_name from user_views;  
     select constraint_name from user-constraints;  
 106、-------------索引------------------  
     create index idx_stu_email on stu (email);-- 在stu这张表的email字段上建立一个索引：idx_stu_email  
 107、---------- 删除索引 ------------------  
     drop index index_stu_email;  
 108、---------查看所有的索引----------------  
     select index_name from user_indexes;  
 109、---------创建视图-------------------  
     create view v$stu as selesct id,name,age from stu;  
      视图的作用: 简化查询 保护我们的一些私有数据,通过视图也可以用来更新数据,但是我们一般不这么用 缺点：要对视图进行维护  
   
 110、-----------创建序列------------  
     create sequence seq;--创建序列  
     select seq.nextval from dual;-- 查看seq序列的下一个值  
     drop sequence seq;--删除序列  
 111、------------数据库的三范式--------------  
     （1）、要有主键,列不可分  
     （2）、不能存在部分依赖：当有多个字段联合起来作为主键的时候,不是主键的字段不能部分依赖于主键中的某个字段  
     （3）、不能存在传递依赖   
 ==============================================PL/SQL==========================  
 112、-------------------在客户端输出helloworld-------------------------------  
     set serveroutput on;--默认是off,设成on是让Oracle可以在客户端输出数据  
 113、begin  
     dbms_output.put_line('helloworld');  
     end;  
     /  
 114、----------------pl/sql变量的赋值与输出----  
     declare  
         v_name varchar2(20);--声明变量v_name变量的声明以v_开头  
     begin  
         v_name := 'myname';  
         dbms_output.put_line(v_name);  
     end;  
     /  
 115、-----------pl/sql对于异常的处理(除数为0)-------------  
     declare  
         v_num number := 0;  
     begin  
         v_num := 2/v_num;  
         dbms_output.put_line(v_num);  
     exception  
         when others then  
         dbms_output.put_line('error');  
     end;  
     /  
 116、----------变量的声明----------  
     binary_integer:整数,主要用来计数而不是用来表示字段类型   比number效率高  
     number:数字类型  
     char:定长字符串  
     varchar2：变长字符串  
     date：日期  
     long：字符串,最长2GB  
     boolean：布尔类型,可以取值true,false,null--最好给一初值  
 117、----------变量的声明,使用 '%type'属性  
     declare  
         v_empno number(4);  
         v_empno2 emp.empno%type;  
         v_empno3 v_empno2%type;  
     begin  
         dbms_output.put_line('Test');  
     end;  
     /  
     --使用%type属性,可以使变量的声明根据表字段的类型自动变换,省去了维护的麻烦,而且%type属性,可以用于变量身上  
 118、---------------Table变量类型(table表示的是一个数组)-------------------  
     declare  
         type type_table_emp_empno is table of emp.empno%type index by binary_integer;  
             v_empnos type_table type_table_empno;  
     begin  
         v_empnos(0) := 7345;  
         v_empnos(-1) :=9999;  
         dbms_output.put_line(v_empnos(-1));  
     end;  
 119、-----------------Record变量类型  
     declare  
         type type_record_dept is record  
         (  
             deptno dept.deptno%type,  
             dname dept.dname%type,  
             loc dept.loc%type  
         );  
     begin  
         v_temp.deptno:=50;  
         v_temp.dname:='aaaa';  
         v_temp.loc:='bj';  
         dbms_output.put_line(v temp.deptno || ' ' || v temp.dname);  
     end;  
 120、-----------使用 %rowtype声明record变量  
     declare  
         v_temp dept%rowtype;  
     begin  
         v_temp.deptno:=50;  
         v_temp.dname:='aaaa';  
         v_temp.loc:='bj';  
     dbms_output.put_line(v temp.deptno || '' || v temp.dname)             
     end;  
       
 121、--------------sql%count 统计上一条sql语句更新的记录条数   
 122、--------------sql语句的运用  
     declare  
         v_ename emp.ename%type;  
         v_sal emp.sal%type;  
     begin  
         select ename,sal into v_ename,v_sal from emp where empno = 7369;  
         dbms_output.put_line(v_ename || '' || v_sal);  
     end;  
   
 123、  -------- pl/sql语句的应用  
     declare  
         v_emp emp%rowtype;  
     begin  
         select * into v_emp from emp where empno=7369;  
         dbms_output_line(v_emp.ename);  
     end;  
 124、-------------pl/sql语句的应用   
     declare  
         v_deptno dept.deptno%type := 50;  
         v_dname dept.dname%type :='aaa';  
         v_loc dept.loc%type := 'bj';  
     begin  
         insert into dept2 values(v_deptno,v_dname,v_loc);  
     commit;  
     end;  
 125、-----------------ddl语言,数据定义语言  
     begin  
         execute immediate 'create table T (nnn varchar(30) default ''a'')';  
     end;  
 126、------------------if else的运用  
      declare  
         v_sal emp.sal%type;  
      begin  
         select sal into v_sal from emp where empno = 7369;  
     if(v_sal < 2000) then  
         dbms_output.put_line('low');  
     elsif(v_sal > 2000) then  
         dbms_output.put_line('middle');  
     else   
         dbms_output.put_line('height');  
         end if;  
       end;  
 127、-------------------循环 =====do while  
     declare  
         i binary_integer := 1;  
     begin  
         loop  
                 dbms_output.put_line(i);  
                 i := i + 1;  
             exit when (i>=11);  
         end loop;  
     end;  
 128、---------------------while   
     declare  
         j binary_integer := 1;  
     begin  
         while j < 11 loop  
             dbms_output.put_line(j);  
         j:=j+1;  
         end loop;  
     end;  
 129、---------------------for  
     begin  
         for k in 1..10 loop  
             dbms_output.put_line(k);  
         end loop;  
         for k in reverse 1..10 loop  
             dbms_output.put_line(k);  
         end loop;  
     end;  
 130、-----------------------异常(1)  
     declare  
         v_temp number(4);  
     begin  
         select empno into v_temp from emp where empno = 10;  
     exception  
         when too_many_rows then  
             dbms_output.put_line('太多记录了');  
         when others then  
             dbms_output.put_line('error');    
     end;  
 131、-----------------------异常(2)  
     declare  
         v_temp number(4);  
     begin  
         select empno into v_temp from emp where empno = 2222;  
     exception  
         when no_data_found then  
             dbms_output.put_line('太多记录了');  
     end;  
 132、----------------------创建序列  
     create sequence seq_errorlog_id start with 1 increment by 1;  
 133、-----------------------错误处理(用表记录：将系统日志存到数据库便于以后查看)  
   
   
    -- 创建日志表:  
     create table errorlog  
     (  
     id number primary key,  
     errcode number,  
     errmsg varchar2(1024),  
     errdate date  
     );  
   
   
       
     declare  
         v_deptno dept.deptno%type := 10;  
         v_errcode  number;  
         v_errmsg varchar2(1024);  
     begin  
         delete from dept where deptno = v_deptno;  
        commit;  
     exception  
         when others then  
             rollback;  
                 v_errcode := SQLCODE;  
                 v_errmsg := SQLERRM;  
         insert into errorlog values (seq_errorlog_id.nextval, v_errcode,v_errmsg, sysdate);  
                 commit;  
     end;  
 133---------------------PL/SQL中的重点cursor(游标)和指针的概念差不多  
     declare  
         cursor c is  
             select * from emp; --此处的语句不会立刻执行,而是当下面的open c的时候,才会真正执行  
         v_emp c%rowtype;  
     begin  
         open c;  
             fetch c into v_emp;  
         dbms_output.put_line(v_emp.ename); --这样会只输出一条数据 134将使用循环的方法输出每一条记录  
       close c;  
     end;  
 134----------------------使用do while  循环遍历游标中的每一个数据  
     declare  
         cursor c is  
             select * from emp;  
         v_emp c%rowtype;  
     begin  
         open c;   
         loop  
             fetch c into v_emp;  
             (1) exit when (c%notfound);  --notfound是oracle中的关键字,作用是判断是否还有下一条数据  
             (2) dbms_output.put_line(v_emp.ename);  --(1)(2)的顺序不能颠倒,最后一条数据,不会出错,会把最后一条数据,再次的打印一遍  
        end loop;  
        close c;  
     end;  
 135------------------------while循环,遍历游标  
     declare  
         cursor c is  
             select * from emp;  
         v_emp emp%rowtype;  
     begin  
         open c;  
         fetch c into v_emp;  
         while(c%found) loop  
            dbms_output.put_line(v_emp.ename);  
            fetch c into v_emp;  
        end loop;  
        close c;  
     end;  
 136--------------------------for 循环,遍历游标  
     declare  
         cursor c is  
            select * from emp;  
     begin  
         for v_emp in c loop  
             dbms_output.put_line(v_emp.ename);  
         end loop;  
     end;  
   
 137---------------------------带参数的游标  
     declare  
         cursor c(v_deptno emp.deptno%type, v_job emp.job%type)  
         is  
            select ename, sal from emp where deptno=v_deptno and job=v_job;  
         --v_temp c%rowtype;此处不用声明变量类型  
     begin  
         for v_temp in c(30, 'click') loop  
             dbms_output.put_line(v_temp.ename);  
         end loop;  
     end;  
 138-----------------------------可更新的游标  
     declare  
         cursor c  --有点小错误  
         is  
            select * from emp2 for update;  
         -v_temp c%rowtype;  
     begin  
        for v_temp in c loop  
         if(v_temp.sal < 2000) then  
             update emp2 set sal = sal * 2 where current of c;  
           else if (v_temp.sal =5000) then  
         delete from emp2 where current of c;  
            end if;  
          end loop;  
          commit;  
     end;  
 139-----------------------------------procedure存储过程(带有名字的程序块)  
     create or replace procedure p  
         is--这两句除了替代declare,下面的语句全部都一样    
         cursor c is  
             select * from emp2 for update;  
     begin  
          for v_emp in c loop  
         if(v_emp.deptno = 10) then  
             update emp2 set sal = sal +10 where current of c;  
         else if(v_emp.deptno =20) then  
             update emp2 set sal =  sal + 20 where current of c;  
         else  
             update emp2 set sal = sal + 50 where current of c;  
         end if;  
         end loop;  
       commit;  
      end;  
       
     --执行存储过程的两种方法：  
     （1）exec p;(p是存储过程的名称)  
     （2）  
         begin  
             p;  
         end;  
         /  
 140-------------------------------带参数的存储过程  
     create or replace procedure p  
         (v_a in number, v_b number, v_ret out number, v_temp in out number)  
     is  
       
     begin  
         if(v_a > v_b) then  
             v_ret := v_a;  
         else  
             v_ret := v_b;  
         end if;  
         v_temp := v_temp + 1;  
     end;  
 141----------------------调用140  
     declare  
         v_a  number := 3;  
         v_b  number := 4;  
         v_ret number;  
         v_temp number := 5;  
   
     begin  
         p(v_a, v_b, v_ret, v_temp);  
         dbms_output.put_line(v_ret);  
         dbms_output.put_line(v_temp);  
     end;  
   
 142------------------删除存储过程  
     drop procedure p;  
 143------------------------创建函数计算个人所得税    
     create or replace function sal_tax  
         (v_sal  number)   
         return number  
     is  
     begin  
         if(v_sal < 2000) then  
             return 0.10;  
         elsif(v_sal <2750) then  
             return 0.15;  
         else  
             return 0.20;  
         end if;  
     end;  
 ----144-------------------------创建触发器（trigger）  触发器不能单独的存在,必须依附在某一张表上  
   
     --创建触发器的依附表  
       
     create table emp2_log  
     (  
     ename varchar2(30) ,  
     eaction varchar2(20),  
     etime date  
     );    
   
     create or replace trigger trig  
         after insert or delete or update on emp2 ---for each row 加上此句,每更新一行,触发一次,不加入则值触发一次  
     begin  
         if inserting then  
             insert into emp2_log values(USER, 'insert', sysdate);  
         elsif updating then  
             insert into emp2_log values(USER, 'update', sysdate);  
         elsif deleting then  
             insert into emp2_log values(USER, 'delete', sysdate);  
         end if;  
     end;  
 145-------------------------------通过触发器更新数据  
     create or replace trigger trig  
         after update on dept  
         for each row  
     begin  
         update emp set deptno =:NEW.deptno where deptno =: OLD.deptno;  
     end;  
       
   
     ------只编译不显示的解决办法 set serveroutput on;  
 145-------------------------------通过创建存储过程完成递归  
     create or replace procedure p(v_pid article.pid%type,v_level binary_integer) is  
         cursor c is select * from article where pid = v_pid;  
         v_preStr varchar2(1024) := '';  
     begin  
       for i in 0..v_leave loop  
         v_preStr := v_preStr || '****';  
       end loop;  
   
       for v_article in c loop  
         dbms_output.put_line(v_article.cont);  
         if(v_article.isleaf = 0) then  
             p(v_article.id);  
         end if;  
         end loop;  
       
     end;  
 146-------------------------------查看当前用户下有哪些表---  
     --首先,用这个用户登录然后使用语句：  
     select * from tab;  
       
 147-----------------------------用Oracle进行分页！--------------  
     --因为Oracle中的隐含字段rownum不支持'>'所以：  
     select * from (  
         select rownum rn, t.* from (  
             select * from t_user where user_id <> 'root'  
         ) t where rownum <6  
     ) where rn >3  
 148------------------------Oracle下面的清屏命令----------------  
     clear screen; 或者 cle scr;  
   
 149-----------将创建好的guohailong的这个用户的密码改为abc--------------  
     alter user guohailong identified by abc  
     --当密码使用的是数字的时候可能会不行  
     
     
     
     
     
     
     --使用在10 Oracle以上的正则表达式在dual表查询
  with test1 as(
        select '欧' name from dual union all
        select '阳' from dual union all
        select '锋' from dual  )
        select distinct regexp_replace(name,'[0-9]','') from test1
   
 ------------------------------------------
 with tab as (
       select '洪'  name from dual union all
       select '七' name from dual union all 
       select '公' name from dual) 
       select translate(name,'\\0123456789','\\') from tab; 

 --
 CREATE OR REPLACE PROCEDURE 
     calc(i_birth VARCHAR2) IS 
     s VARCHAR2(8);  
     o VARCHAR2(8);  
     PROCEDURE cc(num VARCHAR2, s OUT VARCHAR2) IS 
     BEGIN 
     FOR i 
        IN REVERSE 2 .. length(num) LOOP
      s := s || substr(substr(num, i, 1) + substr(num, i - 1, 1), -1);
       END LOOP; 
        SELECT REVERSE(s) INTO s FROM dual; 
         END; 
          BEGIN o := i_birth; 
          LOOP 
           cc(o, s); 
           o := s; 
          dbms_output.put_line(s); 
         EXIT WHEN length(o) < 2; 
         END LOOP; 
      END; 
    set serveroutput on; 
      
  exec calc('19890313');  
  
  
  
  
  ----算命pl/sql
  with t as
  (select '19800728' x from dual)
	  select 
	    case 
		     when mod (i, 2) = 0 then '命好'
		     when  i = 9 		 then '命运悲惨' 
		        else '一般'
		     end result
	    from (select mod(sum((to_number(substr(x, level, 1)) +to_number(substr(x, -level, 1))) *                      
	         greatest(((level - 1) * 2 - 1) * 7, 1)),10) i from t connect by level <= 4);
       
       
 
--构造一个表,和emp表的部分字段相同,但是顺序不同
SQL> create table t_emp as
  2  select ename,empno,deptno,sal
  3  from emp
  4  where 1=0
  5  /
 
Table created
 --添加数据
SQL> insert into t_emp(ename,empno,deptno,sal)
  2  select ename,empno,deptno,sal
  3  from emp
  4  where sal >= 2500 
  5  /
  
  
select * from tb_product where createdate>=to_date('2011-6-13','yyyy-MM-dd') and createdate<=to_date('2011-6-16','yyyy-MM-dd');  

sysdate --获取当前系统的时间
to_date('','yyyy-mm-dd') --时间转换

select * from tb_product where to_char(createdate,'yyyy-MM-dd')>='2011-6-13' and to_char(createdate,'yyyy-MM-dd')<='2011-6-16';  


select * from tb_product where trunc(createdate)>=? and trunc(createdate)<=?--用trunc函数就可以了 




/*
 * @Description:
 *  	declare 代表是声明变量,输入参数/输出参数的SQL关键字
 *	    begin 表示开始执行
 *      execute immediate  立刻执行
 *      end if 结束if语句
 *      end 结束开始语句
 *  	/ 斜杠代表结束SQL语句
 *  	into代表是赋值给某个变量/是插入SQL语句的关键字
 *  	then然后的意思
 *		comment on  table与comment on  column 对表和列进行添加备注说明
 *		primary key 主键字
 *		http://www.jb51.net/article/45539.htm
 *
 */
 
 
--添加字段的语法：alter table tablename add (column datatype [default value][null/not null],….);
--修改字段的语法：alter table tablename modify (column datatype [default value][null/not null],….);
--删除字段的语法：alter table tablename drop (column);


---==================================（删除/清空）表脚本=====================================

declare
  iCnt number := 0;
begin
  select count(*) into iCnt from user_tables  where  lower(table_name) = 't_user_info'; --根据表进行查询
  if iCnt = 1 or iCnt>0  then -- 如果查询到（删除/清空）表
	execute immediate 'drop 	table t_user_info'; --删除表,表结构和数据一起清空
	--execute immediate 'truncate table t_user_info'; --清除表,表结构和数据一起清除
	--execute immediate 'delete 	from  t_user_info'; --删除表数据,可以根据条件删除
  end if;
end;
/

 
/*
  ---****************************************查询索引信息表	****************************************
	select table_name,constraint_name,constraint_type from user_constraints where table_name='大写的表名' ;  --用户约束
	select table_name,constraint_name,constraint_type from dba_constraints where table_name='大写的表名' ;	 --dba约束
	select * from USER_INDEXES where table_name = '大写的表名';   --用户索引upper(table_name)=upper('表名');
	select * from ALL_INDEXes where table_name = '大写的表名';    --所有索引
	
	---****************************************查询索引信息表	****************************************
*/
---==================================创建表脚本=====================================
declare
  iCnt number := 0;
begin
  select count(*) into iCnt from user_tables  where  lower(table_name) = 't_user_info'; --根据表进行查询
  if iCnt = 0 then -- 如果查询不到这个表就创建这个表
  execute immediate  'create table t_user_info 
	 (
		user_id 			      number not null,
		user_name         	varchar2(32) not null,
		pass_word         	varchar2(32) not null,
		age         		    number(10) not null,
		create_time      	  date,  
		id_card_code  	    varchar2(38) not null
	 )';     
	execute immediate 'alter table t_user_info add constraint pk_user_id primary key (user_id)'; --主键
	execute immediate 'create unique index idx_id_card_code on t_user_info (id_card_code)'; --对身份证号创建唯一索引
	execute immediate 'comment on table t_user_info  is ''用户表'''; 					 --用户表描述
	execute immediate 'comment on column t_user_info.user_id  is ''主键(我方主键)'''; -- 主键描述
	execute immediate 'comment on column t_user_info.user_name  is ''用户名'''; 		 -- 用户名描述
	execute immediate 'comment on column t_user_info.pass_word  is ''用户密码'''; 		 -- 用户密码描述
	execute immediate 'comment on column t_user_info.age  is ''年龄'''; 				 -- 年龄描述
	execute immediate 'comment on column t_user_info.create_time  is ''创建时间'''; 	 -- 创建时间描述
	execute immediate 'comment on column t_user_info.id_card_code  is ''身份证号'''; 	 -- 身份证号描述 
  end if;
end;
/
 
 
---==================================删除表序列号=====================================

declare
  iCount integer; --声明一个整型的变量
begin
  select count(*) into iCount from user_sequences where upper(sequence_name) = upper('SEQ_T_USER_INFO'); --upper表示转换为大写进行查询
  if(iCount = 1 or iCount > 0 )  then --查到等于1或者是大于0就把这个序列号执行删除SQL语句
    execute immediate 'drop sequence SEQ_T_USER_INFO';
  end if;
end;
/

---==================================创建序列号脚本,每一个序列号对一个表=====================================
declare
  iCnt integer;
begin
  select count(*) into iCnt from user_sequences where upper(sequence_name)=upper('SEQ_T_USER_INFO');
  if iCnt = 0 then
	/*minvalue最小值为1
	 *maxvalue最大值为999999999999999999999999999
	 *start从1开始
	 *increment每插入一条递增1
	 *nocache无缓存
	 */
    execute immediate 'create sequence SEQ_T_USER_INFO
        minvalue 1
        maxvalue 999999999999999999999999999
        start with 1
        increment by 1
        nocache';
  end if;
end;
/


---==================================插入数据脚本=====================================

declare
  iCnt number := 0;
  SEQ_USER_ID number :=0;--序列id
begin
  select count(*) into iCnt from user_tables  where  lower(table_name) = 't_user_info'; --根据表进行查询
  select SEQ_T_USER_INFO.nextval into SEQ_USER_ID from dual;--获取主键序列
  if iCnt = 1 then -- 如果查询不到这个表就创建这个表 
     insert into t_user_info (user_id, user_name, pass_word,age,create_time,id_card_code)
     values (SEQ_USER_ID, '张三丰', '123456',100,sysdate,'X'||SEQ_USER_ID);
     --或者直接序列.nextval values (SEQ_T_USER_INFO.nextval, '张三丰', '123456',100,sysdate,'X'||SEQ_USER_ID);
   commit;--提交
  end if;
end;
/

---==================================修改表脚本（递增字段脚本）=====================================
/*
  alter table table_name
  add
    (
		column1_name column1_datatype column1_constraint,   ---列名,列名类型,列约束
		column2_name column2_datatype column2_constraint, 
		............................
    );
  
   如：
   alter table  table_name 
   add 
   (
      address           char(1) NOT NULL,
      sex            	  number,
	  ............................
   );
 */
declare
  iCnt number := 0;
begin
     --对表添加字段,不对表select count(*)检测,原因:在升级脚本时，SQL脚本一般有主副脚本顺序规则执行,且最后脚本结尾加一个commit关键字,
     --在主副脚本只仅有一个commit,故在升级sql脚本步骤先必须遵循升级前先主后副脚本(数据初始化,修改表结构,添加表结构等SQL脚本)执行
     select count(*) into iCnt from user_tab_columns  where  lower(table_name) = 't_user_info' and lower(column_name) = 'update_time'; 
     --查询不到这个update_time字段,然后往下面执行			  
     if iCnt = 0 then 
        execute immediate 'alter table  t_user_info add  update_time  date  '; --对t_user_info表添加字段
        execute immediate 'comment on column t_user_info.update_time  is ''更新时间'''; -- 更新时间描述    
     end if; 
end;
/
 
 
 
 /* =================================存储过程 =================================*/
declare
  iCnt  integer; --声明变量
begin
--根据存储过程的名称进行查询是否存在存储过程.
  SELECT count(*) into iCnt FROM USER_SOURCE WHERE TYPE = upper('procedure') and name in(upper('pro_user_info'));
    if(iCnt = 1 or iCnt > 0 )  then
        execute immediate 'drop procedure  pro_user_info';       
    end if ;  
end;
/

---创建存储过程
 create or replace procedure pro_user_info(
	 vc_in_userName in varchar2,       --用户名,输入参数
	 out_result     out sys_refcursor  -- 结果标识
 ) is          
vc_sql   varchar2(10000) := ''; --动态sql
begin 
  vc_sql := 'select user_id,user_name,age,create_time from t_user_info ';
  --判断不为空的时候
 if(vc_in_userName <> '') then
	vc_sql :=  'where user_name=:vc_in_userName';           
	execute immediate  vc_sql using vc_in_userName;--执行
	 --open  out_result  for  --打开游标                               
	   -- return; --返回游标值.
 end if;
 exception when others then rollback;
	dbms_output.put_line('事务回滚');
end ;




 
 
 
 

 