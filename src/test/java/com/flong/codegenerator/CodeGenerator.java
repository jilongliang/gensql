package com.flong.codegenerator;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.flong.commons.lang.config.PropertiesHelper;
/***
 *@Author:liangjilong
 *@Date:2015年12月5日下午12:25:12
 *@Email:jilongliang@sina.com
 *@Version:1.0
 *@CopyRight(c)jilongliang
 *@Desction:★★★★★★★★★★★★★★★代码生成器实现思路★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
 *
 *   ★★在快速开发的过程中，为了节省时间和成本很多人都会开发自己的代码生成器，而且成为程序员开发过程中必不可少的神器.
 *   ★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
 *	  第一种：先有数据库表，然后通过jdbc链接数据库再读取表的字段等属性出来生成Entity,Dao,Service,Controller，JSP等代码
 *   这种必须是有数据库和表的思想,通过程序去读取数据库表的属性等信息,然后组织代码通过文件流生成文件.
 *   
 *   ★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
 *   第二种：已经设计好数据库表文档，把所有表的字段属性配置到EXCEL或者CSV格式的文件通过JXL/POI/技术去读取文件的字段实现
 *   或者通过MyEclipse自带的Hibernate配置数据库，逆向生成相应逻辑的代码.Entity,Dao,Service,Controller，JSP,在过程中会
 *   借助Freemaker,Velocity去实现.三层和jsp，然后通过一下ORM(hibernate,batis,myibatis等)技术逆向生成数据库表.这种是无
 *   数据库表的思想. 在生成java的代码一般不建议建ORM映射主从表关系,通过SQL去建立关系,为啥？因为在一些大型的公司如：银行，
 *   阿里巴巴，电信等公司，很多项目开发过程中在数据库表很少建立表关系的因为在些业务复杂的情况下通过SQL和程序控制的解决方
 *   案比ORM映射关系方案占优势.比如优化性能/维护/灵活性更加好等.
 *   ★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
 *   
 *   此程序以MySQL为主，未实现其他数据库.此程序可以再优化的，为了有些初学者,就不作太多的处理和优化.一些高手会编程更好的生
 *   成器，此程序只提供参考和学习,如有什么问题，可以多指出.共同学习和进步.谢谢！~
 *   ★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
 */
@SuppressWarnings("all")
public class CodeGenerator {
	
	 
	/*************************变量****Begin************************************/
	private static final String myEmail="jilongliang@sina.com";//Email
	private static final String Version="1.0";//版本
	private static final String Description=" ";//描述
	
	private static final String ENTER = "\n";//换行
	private static final String TAB = "    ";//tab空格.
	private static final String NAME = "NAME";
	private static final String TABLE_CAT = "TABLE_CAT";//表 catalog
	private static final String TABLE_SCHEM = "TABLE_SCHEM";//表 schema
	private static final String TABLE_NAME = "TABLE_NAME";//表名
	private static final String TABLE_TYPE = "TABLE_TYPE";//表类型
	private static final String REMARKS = "REMARKS";//表注释
	private static final String TYPE = "TYPE";//表的类型
	private static final String SIZE = "SIZE";//大小
	private static final String CLASS = "CLASS";//类别
	
	/*************************变量****End************************************/
	
	private static final String NOW_DATE = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	/***************获取数据库的配置连接************/
	private static final String DB_NAME = PropertiesHelper.getValueByKey("jdbc.url").substring(
										  PropertiesHelper.getValueByKey("jdbc.url").lastIndexOf("/")+1,
										  PropertiesHelper.getValueByKey("jdbc.url").indexOf("?") == -1?
										  PropertiesHelper.getValueByKey("jdbc.url").length():
									      PropertiesHelper.getValueByKey("jdbc.url").indexOf("?"));
	//从配置获取工程的报名路径
	public static final String ROOT_PACKAGE = PropertiesHelper.getValueByKey("rootPackage");
	//获取作者.
	public static final String AUTHOR = PropertiesHelper.getValueByKey("author");
	//忽略表的后缀.
	public static final List<String> IGNORE_TABLE_PREFIX = new ArrayList<String>();

	/*******定义代码块*******/
	static {
		String ignoreTablePrefix = PropertiesHelper.getValueByKey("ignoreTablePrefix");
		if(ignoreTablePrefix.length() > 0) {
			String[] ignoreTablePrefixs = ignoreTablePrefix.split("\\s*\\,\\s*");
			for (String elem : ignoreTablePrefixs) {
				IGNORE_TABLE_PREFIX.add(elem);
			}
		}
	}

	/***
	 * 生成实体类的代码
	 * @param table
	 * @throws Exception
	 */
	public void createEntityClass(String table) throws Exception {
		String tableConstantName = getTableConstantName(table);
		
		String className = getClassName(tableConstantName);
		StringBuilder buffer = new StringBuilder();
		buffer.append("package " + ROOT_PACKAGE + ".entity;").append(ENTER);
		buffer.append("import java.util.Date;").append(ENTER);
		buffer.append("import com.flong.commons.persistence.Entity;").append(ENTER);
		buffer.append("import com.flong.commons.persistence.annotation.Column;").append(ENTER);
		buffer.append("import com.flong.commons.persistence.annotation.Id;").append(ENTER);
		buffer.append("import com.flong.commons.persistence.annotation.Relation;").append(ENTER);
		buffer.append("import com.flong.commons.persistence.annotation.Table;").append(ENTER);
		buffer.append(ENTER);
		buffer.append(ENTER);
		buffer.append("/**\n * @Created：" + NOW_DATE + "\n * @Author " + AUTHOR + "\n");
		buffer.append(" * @Version:").append(Version).append(ENTER);
		buffer.append(" * @Description:").append(className).append(ENTER);
		buffer.append(" * @Email:").append(myEmail).append("\n*/");
		buffer.append(ENTER);
		buffer.append("@Relation(" + className + ".TABLE)");
		buffer.append(ENTER);
		buffer.append("@SuppressWarnings(\"all\")");
		buffer.append(ENTER);
		buffer.append("public class " + className + " extends Entity {");
		buffer.append(ENTER);
		buffer.append(ENTER);
		buffer.append(TAB);
		buffer.append("/** 表名常量 */");
		buffer.append(ENTER);
		buffer.append(TAB);
		buffer.append("public static final String TABLE = Table." + tableConstantName + ";");
		buffer.append(ENTER);
		buffer.append(ENTER);
		buffer.append(TAB);
		buffer.append("/**");
		buffer.append(ENTER);
		buffer.append(TAB);
		buffer.append(" * 列名常量");
		buffer.append(ENTER);
		buffer.append(TAB);
		buffer.append(" */");
		buffer.append(ENTER);
		for (Map<String, Object> col : getCols(table)) {
			String colName = col.get(NAME).toString().toUpperCase();
			buffer.append(TAB);//生成字段变量
			buffer.append("public static final String COL_" + colName + " = \"" + colName + "\";//"+col.get(REMARKS));
			buffer.append(ENTER);
		}
		buffer.append(ENTER);
		buffer.append(TAB);
		buffer.append("/**");
		buffer.append(ENTER);
		buffer.append(TAB);
		buffer.append(" * 列属性");
		buffer.append(ENTER);
		buffer.append(TAB);
		buffer.append(" */");
		
		String tablePrimaryKeys = getTablePrimaryKeys(table);//如果是主键
		
		//if(col.get(NAME).toString().equalsIgnoreCase("ID")) {
		if(tablePrimaryKeys!=null){	
			buffer.append(ENTER+TAB);
			
			//如果主键不为空的时候就给一个@Id注解.
			//如果是hibernate的可以给其他的注解，如@GeneratedValue(strategy = GenerationType.IDENTITY)   @SequenceGenerator等
			//并要在包的下面头部导入
			//import javax.persistence.Column;
			//import javax.persistence.Entity;
			//import javax.persistence.GeneratedValue;
			//import javax.persistence.GenerationType;
			//import javax.persistence.Id;
			//import javax.persistence.Table;
			buffer.append("@Id");
			//这里不赋值给,因为下面这个for循环有一个.
			//sb.append("@Column(COL_" + tablePrimaryKeys + ")");
		}
		for (Map<String, Object> col : getCols(table)) {
			buffer.append(TAB);
			buffer.append(ENTER);
			buffer.append(TAB);
			buffer.append("@Column(COL_" + col.get(NAME).toString().toUpperCase() + ")");
			buffer.append(ENTER);
			buffer.append(TAB);
			buffer.append("private ");
			//这行代码的意思就是说，如果找到数据库表的字段是为ID的时候，或后缀有_ID的就认为是主键,并且忽略大小写就给一个Long
			//在实际过程中应该判断是它的字段是不是为了PrimaryKey才设为Long才适合.
			//if(col.get(NAME).toString().equalsIgnoreCase("ID") || col.get(NAME).toString().toUpperCase().endsWith("_ID")) {
			if(Class.forName(col.get(CLASS).toString()).isAssignableFrom(Date.class) || Class.forName(col.get(CLASS).toString()) == Timestamp.class) {
				buffer.append("Date");
			}else if(Class.forName(col.get(CLASS).toString()).isAssignableFrom(Double.class) || Class.forName(col.get(CLASS).toString()) == double.class) {
				buffer.append("Double");
			} else if(getClassName(col.get(NAME).toString()).equals(Class.forName(col.get(CLASS).toString()).getSimpleName())) {
				buffer.append(col.get(CLASS));
			} else {
				buffer.append(Class.forName(col.get(CLASS).toString()).getSimpleName());
			}
			//sb.append(" " + getFieldName(col.get(NAME).toString()) + ";");
			buffer.append(" " + col.get(NAME).toString() + ";");
			buffer.append(ENTER);
		}
		buffer.append(ENTER);
		for (Map<String, Object> col : getCols(table)){
			buffer.append(TAB);
			buffer.append("public ");
			if(Class.forName(col.get(CLASS).toString()).isAssignableFrom(Date.class) || Class.forName(col.get(CLASS).toString()) == Timestamp.class) {
				buffer.append("Date");
			} else if(getClassName(col.get(NAME).toString()).equals(Class.forName(col.get(CLASS).toString()).getSimpleName())) {
				buffer.append(col.get(CLASS));
			} else {
				buffer.append(Class.forName(col.get(CLASS).toString()).getSimpleName());
			}
			buffer.append(" ").append("get").append(col.get(NAME).toString().replaceFirst("\\b(\\w)|\\s(\\w)", col.get(NAME).toString().substring(0,1).toUpperCase()));
			buffer.append("() {");
			buffer.append(ENTER);
			buffer.append(TAB);
			buffer.append(TAB);
			buffer.append("return ").append(col.get(NAME).toString()).append(";");
			buffer.append(ENTER);
			buffer.append(TAB);
			buffer.append("}");
			buffer.append(ENTER);
			buffer.append(TAB);
			buffer.append("public void ").append("set").append(col.get(NAME).toString().replaceFirst("\\b(\\w)|\\s(\\w)", col.get(NAME).toString().substring(0,1).toUpperCase()));
			buffer.append("(");
			if(Class.forName(col.get(CLASS).toString()).isAssignableFrom(Date.class) || Class.forName(col.get(CLASS).toString()) == Timestamp.class) {
				buffer.append("Date");
			} else if(getClassName(col.get(NAME).toString()).equals(Class.forName(col.get(CLASS).toString()).getSimpleName())) {
				buffer.append(col.get(CLASS));
			} else {
				buffer.append(Class.forName(col.get(CLASS).toString()).getSimpleName());
			}
			buffer.append(" ").append(col.get(NAME).toString());
			buffer.append(") {");
			buffer.append(ENTER);
			buffer.append(TAB);
			buffer.append(TAB);
			buffer.append("this.").append(col.get(NAME).toString()).append(" = ").append(col.get(NAME).toString()).append(";");
			buffer.append(ENTER);
			buffer.append(TAB);
			buffer.append("}");
			buffer.append(ENTER);
		}
		buffer.append("}");
		buffer.append(ENTER);
		FileUtils.save("output-code/" + ROOT_PACKAGE.replaceAll("\\.", "/") + "/entity/" + className + ".java", buffer.toString());
	}
	
	/***
	 * 生成dao接口interface类的代码
	 * @param table
	 * @throws Exception
	 */
	public void createDaoClass(String table) throws Exception {
		String className = getClassName(getTableConstantName(table));
		
		String objectName = StringUtils.uncapitalize(className);
		
		StringBuilder buffer = new StringBuilder();
		buffer.append("package " + ROOT_PACKAGE + ".dao;").append(ENTER);
		buffer.append("import java.io.Serializable;").append(ENTER);
		buffer.append("import java.util.List;").append(ENTER);
		buffer.append("import com.flong.commons.persistence.bean.SimplePage;").append(ENTER);
		buffer.append("import com.flong.commons.persistence.dao.EntityDao;").append(ENTER);
		buffer.append("import com.flong.modules.pojo."+className+";").append(ENTER);
		
		buffer.append(ENTER);
		buffer.append(ENTER);
		buffer.append("/**\n * @Created：" + NOW_DATE + "\n * @Author " + AUTHOR + "\n");
		buffer.append(" * @Version:").append(Version).append(ENTER);
		buffer.append(" * @Description:").append(className).append(ENTER);
		buffer.append(" * @Email:").append(myEmail).append("\n*/");
		buffer.append(ENTER);
		
		buffer.append("public interface " + className + "Dao extends EntityDao<" + className + "> {").append(ENTER);
		
		
		buffer.append("/**查询*/").append(ENTER);
		buffer.append(" public List<"+className+"> list(SimplePage simplePage,"+className+" "+objectName+");").append(ENTER);
		
		buffer.append("/**保存数据*/").append(ENTER);
		buffer.append(" public void saveData("+className+" "+objectName+");").append(ENTER);
		
		buffer.append("/**更新数据*/").append(ENTER);
		
		buffer.append(" public void updateData("+className+" "+objectName+");").append(ENTER);
		
		buffer.append("/**删除数据*/").append(ENTER);
		
		buffer.append(" public void deleteData(Long pk);").append(ENTER);
		
		buffer.append(ENTER);
		buffer.append(ENTER);
		buffer.append("}");
		buffer.append(ENTER);
		FileUtils.save("output-code/" + ROOT_PACKAGE.replaceAll("\\.", "/") + "/dao/" + className + "Dao.java", buffer.toString());
	}
	
	/***
	 * 生成dao的实现类的代码
	 * @param table
	 * @throws Exception
	 */
	public void createDaoImplClass(String table) throws Exception {
		String className = getClassName(getTableConstantName(table));
		String objectName =  StringUtils.uncapitalize(className);
		String tableName = StringUtils.lowerCase(getTableConstantName(table));//获取表名
		
		StringBuilder buffer = new StringBuilder();
		buffer.append("package " + ROOT_PACKAGE + ".dao.impl;").append(ENTER);
		buffer.append("import java.io.Serializable;").append(ENTER);
		buffer.append("import org.apache.commons.lang3.StringUtils;").append(ENTER);
		buffer.append("import org.springframework.stereotype.Repository;").append(ENTER);
		buffer.append("import com.flong.commons.persistence.bean.SimplePage;").append(ENTER);
		buffer.append("import com.flong.commons.persistence.dao.impl.EntityDaoSupport;").append(ENTER);
		buffer.append("import com.flong.modules.dao."+className+"Dao;").append(ENTER);
		buffer.append("import com.flong.modules.pojo."+className+";").append(ENTER);
 
		
		buffer.append(ENTER);
		buffer.append(ENTER);
		buffer.append("/**\n * @Created：" + NOW_DATE + "\n * @Author " + AUTHOR + "\n");
		buffer.append(" * @Version:").append(Version).append(ENTER);
		buffer.append(" * @Description:").append(className).append(ENTER);
		buffer.append(" * @Email:").append(myEmail).append("\n*/");
		buffer.append(ENTER);
		buffer.append("@Repository");//这个是spring的注解
		buffer.append(ENTER);
		buffer.append("public class " + className + "DaoImpl extends EntityDaoSupport<" + className + "> implements " + className + "Dao {");
		
		
		buffer.append("/**查询*/").append(ENTER);
		buffer.append(" public List<"+className+"> list(SimplePage simplePage,"+className+" "+objectName+"){").append(ENTER);
		
		buffer.append(ENTER);
		
		String mergeField=getSQLMergeField(table);
		
		buffer.append("    String sql = ").append("\" select "+mergeField+" from ").append(tableName).append(" where 1=1 \"; ").append(ENTER);//这个TABLE是实体类的变量
		//daoQuery这个是底层封装的一个接口,自个可以更加自己需求封装.
		buffer.append("    List<"+className+"> list= iSQLQuery.query(sql,"+className+".class,simplePage);").append(ENTER);
		buffer.append(" return list;").append(ENTER);
		
		buffer.append("}").append(ENTER);//查询的结束{
		
		
		buffer.append("/**保存数据*/").append(ENTER);
		buffer.append(" public void saveData("+className+" "+objectName+"){").append(ENTER);
		buffer.append("   try {").append(ENTER);
		buffer.append("	     saveOrUpdate("+objectName+");").append(ENTER);
		buffer.append("   } catch (DaoAccessException e) {").append(ENTER);
		buffer.append("      e.printStackTrace();").append(ENTER);
		buffer.append("  }").append(ENTER);
		
		buffer.append("}");
		
		
		buffer.append("/**更新数据*/").append(ENTER);
		
		buffer.append(" public void updateData("+className+" "+objectName+"){").append(ENTER);
		
		buffer.append("   try {").append(ENTER);
		buffer.append("	     saveOrUpdate("+objectName+");").append(ENTER);
		buffer.append("   } catch (DaoAccessException e) {").append(ENTER);
		buffer.append("      e.printStackTrace();").append(ENTER);
		buffer.append("  }").append(ENTER);
		
		buffer.append("}");
		
		
		buffer.append("/**删除数据*/").append(ENTER);
		buffer.append(" public void deleteData(Long pk){").append(ENTER);
		
		buffer.append("   try {").append(ENTER);
		buffer.append("	     delete(pk);").append(ENTER);
		buffer.append("   } catch (DaoAccessException e) {").append(ENTER);
		buffer.append("      e.printStackTrace();").append(ENTER);
		buffer.append("  }").append(ENTER);
		
		
		buffer.append("}");
		
		buffer.append(ENTER);
		buffer.append(ENTER);
		buffer.append("}");
		buffer.append(ENTER);
		FileUtils.save("output-code/" + ROOT_PACKAGE.replaceAll("\\.", "/") + "/dao/impl/" + className + "DaoImpl.java", buffer.toString());
	}

	
	/***
	 * 创建Service的接口
	 * createServiceClass
	 * @param table
	 */
	public void createServiceClass(String table) {

		String className = getClassName(getTableConstantName(table));
		String objectName = StringUtils.uncapitalize(className);
		
		StringBuilder buffer = new StringBuilder();
		
		buffer.append("package " + ROOT_PACKAGE + ".service;");

		buffer.append("import java.io.Serializable;").append(ENTER);
		buffer.append("import java.util.List;").append(ENTER);
		buffer.append("import com.flong.commons.persistence.bean.SimplePage;").append(ENTER);
		buffer.append("import com.flong.commons.persistence.dao.EntityDao;").append(ENTER);
		buffer.append("import com.flong.modules.pojo."+className+";").append(ENTER);
		
		
		buffer.append(ENTER);
		buffer.append(ENTER);
		buffer.append("/**\n * @Created：" + NOW_DATE + "\n * @Author " + AUTHOR + "\n");
		buffer.append(" * @Version:").append(Version).append(ENTER);
		buffer.append(" * @Description:").append(className).append(ENTER);
		buffer.append(" * @Email:").append(myEmail).append("\n*/");
		buffer.append(ENTER);
		buffer.append("public interface " + className + "Service {");
		
		buffer.append("/**查询*/").append(ENTER);
		buffer.append(" public List<"+className+"> list(SimplePage simplePage,"+className+" "+objectName+");").append(ENTER);
		
		buffer.append("/**保存数据*/").append(ENTER);
		buffer.append(" public void saveData("+className+" "+objectName+");").append(ENTER);
		
		buffer.append("/**更新数据*/").append(ENTER);
		
		buffer.append(" public void updateData("+className+" "+objectName+");").append(ENTER);
		
		buffer.append("/**删除数据*/").append(ENTER);
		
		buffer.append(" public void deleteData(Long pk);").append(ENTER);
		
		buffer.append(ENTER);
		buffer.append(ENTER);
		buffer.append("}");
		buffer.append(ENTER);
		FileUtils.save("output-code/" + ROOT_PACKAGE.replaceAll("\\.", "/") + "/service/" + className + "Service.java", buffer.toString());
	
	}

	/***
	 * 创建Service层的实现类
	 * 这里跟Dao的实现的都继承了EntityDaoSupport，主要是为了体现三层service分成的体验保留.
	 * createServiceImplClass
	 * @param table
	 */
	public void createServiceImplClass(String table) {

		String className = getClassName(getTableConstantName(table));
	
		String objectName = StringUtils.uncapitalize(className);
		
		StringBuilder buffer = new StringBuilder();
		
		buffer.append("package " + ROOT_PACKAGE + ".service.impl;");
		buffer.append("import java.io.Serializable;").append(ENTER);
		buffer.append("import java.util.List;").append(ENTER);
		buffer.append("import org.springframework.beans.factory.annotation.Autowired;").append(ENTER);
		buffer.append("import org.springframework.stereotype.Service;").append(ENTER);
		buffer.append("import com.flong.commons.persistence.bean.SimplePage;").append(ENTER);
		buffer.append("import com.flong.commons.persistence.dao.impl.EntityDaoSupport;").append(ENTER);
		buffer.append("import com.flong.modules.dao."+className+"Dao;").append(ENTER);
		buffer.append("import com.flong.modules.pojo."+className+";").append(ENTER);
		buffer.append("import com.flong.modules.service."+className+"Service;").append(ENTER);
		
		 
		buffer.append(ENTER);
		buffer.append(ENTER);
		buffer.append("/**\n * @Created：" + NOW_DATE + "\n * @Author " + AUTHOR + "\n");
		buffer.append(" * @Version:").append(Version).append(ENTER);
		buffer.append(" * @Description:").append(className).append(ENTER);
		buffer.append(" * @Email:").append(myEmail).append("\n*/");
		buffer.append(ENTER);
		buffer.append("@Service");
		buffer.append(ENTER);
		buffer.append("public class " + className + "ServiceImpl extends EntityDaoSupport<"+className+">  implements " + className + "Service {").append(ENTER);
		buffer.append("@Autowired "+className+"Dao "+objectName+"Dao;");
		
		buffer.append("/**查询*/").append(ENTER);
		buffer.append(" public List<"+className+"> list(SimplePage simplePage,"+className+" "+objectName+"){").append(ENTER);
		buffer.append(" 	return "+objectName+"Dao.list(simplePage,"+objectName+");").append(ENTER);
		buffer.append("}").append(ENTER);//查询的结束{
		
		
		
		buffer.append("/**保存数据*/").append(ENTER);
		buffer.append(" public void saveData("+className+" "+objectName+"){").append(ENTER);
			
		buffer.append(    objectName+"Dao.saveData("+objectName+");").append(ENTER);
		buffer.append("}");
		
		buffer.append("/**更新数据*/").append(ENTER);
		
		buffer.append(" public void updateData("+className+" "+objectName+"){").append(ENTER);
		buffer.append(    objectName+"Dao.updateData("+objectName+");").append(ENTER);
		
		buffer.append("}");
		buffer.append("/**删除数据*/").append(ENTER);
		
		buffer.append(" public void deleteData(Long pk){").append(ENTER);
		buffer.append(    objectName+"Dao.deleteData(pk);").append(ENTER);
		buffer.append("}");
		
		
		buffer.append(ENTER);
		buffer.append(ENTER);
		buffer.append("}");
		buffer.append(ENTER);
		FileUtils.save("output-code/" + ROOT_PACKAGE.replaceAll("\\.", "/") + "/service/impl/" + className + "ServiceImpl.java", buffer.toString());
	
	}
	
	
	/***
	 * 创建控制层类Controller
	 * @param table
	 */
	public void createControllerClass(String table){
		//类名
		String className = getClassName(getTableConstantName(table));
		//通过 org.apache.commons.lang3.StringUtils的uncapitalize方法把类名第一个字母转换成小写
		String objectName = StringUtils.uncapitalize(className);
		
		//通过 org.apache.commons.lang3.StringUtils的lowerCase方法把类名整个单词转化成小写然后为springmvc的路径返回jsp请求.
		String BASE_PATH="modules/"+StringUtils.lowerCase(className)+"/";//modules+模块名
		
		StringBuilder buffer = new StringBuilder();
		/*******处理这个导入需要的类*********/
		buffer.append("import java.util.List;").append(ENTER);
		buffer.append("import javax.servlet.http.HttpServletRequest;").append(ENTER);
		buffer.append("import javax.servlet.http.HttpServletResponse;").append(ENTER);
		buffer.append("import org.springframework.beans.factory.annotation.Autowired;").append(ENTER);
		buffer.append("import org.springframework.stereotype.Controller;").append(ENTER);
		buffer.append("import org.springframework.web.bind.annotation.RequestMapping;").append(ENTER);
		buffer.append("import com.flong.commons.persistence.bean.SimplePage;").append(ENTER);
		buffer.append("import com.flong.commons.web.BaseController;").append(ENTER);
		buffer.append("import com.flong.modules.pojo."+className+";").append(ENTER);
		buffer.append("import com.flong.modules.service."+className+"Service;").append(ENTER);
		 
		buffer.append(ENTER);
		buffer.append(ENTER);
		buffer.append("/**\n * @Created：" + NOW_DATE + "\n * @Author " + AUTHOR + "\n");
		buffer.append(" * @Version:").append(Version).append(ENTER);
		buffer.append(" * @Description:").append(className).append(ENTER);
		buffer.append(" * @Email:").append(myEmail).append("\n*/");
		buffer.append(ENTER);
		buffer.append(ENTER);
		buffer.append("@Controller").append(ENTER);
		buffer.append("@RequestMapping(\""+StringUtils.lowerCase(className)+"\")");
		buffer.append(ENTER);
		buffer.append("public class " + className + "Controller extends BaseController {");
		buffer.append(ENTER);
		buffer.append(ENTER);
		buffer.append(" @Autowired "+className+"Service " +className+"Service;");//注入Service层的接口Name
		buffer.append(ENTER);
		
		//创建一个默认的查询..
		buffer.append(ENTER);
		buffer.append("   @RequestMapping(value=\"list\")").append(ENTER);
		buffer.append("   public String list("+className+" "+objectName+",SimplePage simplePage ,HttpServletRequest request ,HttpServletResponse response){");
		buffer.append(ENTER);
		buffer.append("         List<"+className+"> list = "+className+"Service.list(simplePage, "+objectName+");");
		buffer.append(ENTER);
		buffer.append("	     request.setAttribute(\""+objectName+"\", "+objectName+");");
		buffer.append(ENTER);
		buffer.append("		 request.setAttribute(\"page\", simplePage);");
		buffer.append(ENTER);
		buffer.append("		 request.setAttribute(\"list\", list);");
		buffer.append(ENTER);
		buffer.append("		 return \""+BASE_PATH+"list\";");
		buffer.append(ENTER);
		buffer.append("   }");
		
		buffer.append(ENTER);
		buffer.append("}");
		buffer.append(ENTER);
		FileUtils.save("output-code/" + ROOT_PACKAGE.replaceAll("\\.", "/") + "/controller/" + className + "Controller.java", buffer.toString());
	
		
	}
	
	
	/***
	 * 创建JSP页面.
	 * 以bootstrap3.x为主.
	 * @param table
	 */
	public void createJspView(String table)throws Exception{

		String tableConstantName = getTableConstantName(table);
		
		String className = getClassName(tableConstantName);//获取类名
		//通过 org.apache.commons.lang3.StringUtils的uncapitalize方法把类名第一个字母转换成小写
		String objectName = StringUtils.uncapitalize(className);
		
		StringBuilder buffer = new StringBuilder();
		 
		buffer.append(" <%@ page language=\"java\" contentType=\"text/html; charset=UTF-8\"  pageEncoding=\"UTF-8\"%>").append(ENTER);
		//这个就标注一下，这个taglib.jsp文件是JSTL的EL表达式，Spring 标签，自定义标签，等的文件。
		buffer.append("  <%@ include file=\"/WEB-INF/views/include/taglib.jsp\"%>").append(ENTER);
		buffer.append(" <!DOCTYPE htm>").append(ENTER);
		buffer.append(" <html>").append(ENTER);
		buffer.append(" <head>").append(ENTER);
		//添加一个插件公共的文件，这个我就不一一备注
		buffer.append("  <%@ include file=\"/WEB-INF/views/include/meta.jsp\"%>").append(ENTER);
		buffer.append("  <%@ include file=\"/WEB-INF/views/include/include.jsp\"%>").append(ENTER);
		buffer.append(" <title></title>").append(ENTER);
		/**=======================添加style===Begin====================**/
		buffer.append(" <style>").append(ENTER);
		buffer.append(" 	.breadcrumb{").append(ENTER);
		buffer.append(" 		background-color: #fff;").append(ENTER);
		buffer.append(" 	}").append(ENTER);
		buffer.append(" 	.form-search{").append(ENTER);
		buffer.append(" 		background-color: #fff;").append(ENTER);
		buffer.append(" }").append(ENTER);
		buffer.append(" .form-search1{").append(ENTER);
		buffer.append(" 	    padding: 8px 15px;").append(ENTER);
		buffer.append(" 		background-color: #f5f5f5;").append(ENTER);
		buffer.append(" 	}").append(ENTER);
		buffer.append(" </style>").append(ENTER);
		buffer.append(" </head>").append(ENTER);
		/**=======================添加style===End====================**/
		
		buffer.append("<body>").append(ENTER);
		buffer.append("<ul class=\"nav nav-tabs\">").append(ENTER);
		buffer.append( "<li class=\"active\"><a href=\"${basePath}"+StringUtils.lowerCase(className)+"/list\">"+className+"列表</a></li>").append(ENTER);
		buffer.append("</ul>").append(ENTER);
		buffer.append( " <form:form id=\"searchForm\" modelAttribute=\""+objectName+"\" action=\"${basePath}"+StringUtils.lowerCase(className)+"/list\" method=\"post\" class=\"breadcrumb form-search form-inline\">").append(ENTER);
		buffer.append("  <div style=\"margin-bottom: 20px;\" class=\"form-search1\">").append(ENTER);
		
		//这里可以判断数据库的字段的类型做变量弄处理条件查询.
		 
		for (Map<String, Object> col : getCols(table)) {
			 
			//判断如果是数据库表的字段是DateTime类型的就设值My97DatePicker插件上,方便大家使用.
			if(Class.forName(col.get(CLASS).toString()).isAssignableFrom(Date.class) || Class.forName(col.get(CLASS).toString()) == Timestamp.class) {
				buffer.append("<input id=\""+col.get(NAME).toString()+"\" name=\""+col.get(NAME).toString()+"\" type=\"text\" readonly=\"readonly\" maxlength=\"20\" class=\"Wdate\"").append(ENTER);
				//在这里用了$是为了查询的时候保留值.
				buffer.append(" value=\"<fmt:formatDate value=\"${"+className+"."+col.get(NAME).toString()+"}\" pattern=\"yyyy-MM-dd HH:mm:ss\"/>\"").append(ENTER);
				buffer.append(" onclick=\"WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});\"/>").append(ENTER);
			} else if(getClassName(col.get(NAME).toString()).equals(Class.forName(col.get(CLASS).toString()).getSimpleName())) {
				//form:input是spring架构的input标签path必须要等于实体类要有的属性.否则会报错.placeholder是html5有的占位符的属性,
				//htmlEscape也是spring的有属性.在这个jar下面,因为我这个工程是用maven搭建的，所有拷贝的时候,拷贝的时候它带上路径.方便大家伙找jar,而且我在这的spring是用3.x
				//C:\Users\liangjilong\.m2\repository\org\springframework\org.springframework.web.servlet\3.1.1.RELEASE\org.springframework.web.servlet-3.1.1.RELEASE.jar
				//org.springframework.web.servlet-3.1.1.RELEASE.jar这个文件下面有一个spring-from.tld文件，可以找到path,htmlEscape等属性.
				buffer.append("   <label>"+col.get(NAME).toString()+" ：</label><form:input path=\""+col.get(NAME).toString()+"\" htmlEscape=\"false\" maxlength=\"50\" class=\"input-medium form-control\" placeholder=\""+col.get(NAME).toString()+"\"/>").append(ENTER);
			}else{
				buffer.append("   <label>"+col.get(NAME).toString()+" ：</label><form:input path=\""+col.get(NAME).toString()+"\" htmlEscape=\"false\" maxlength=\"50\" class=\"input-medium form-control\" placeholder=\""+col.get(NAME).toString()+"\"/>").append(ENTER);
			}
			buffer.append(ENTER);
		}
		//btn btn-info这个样式用过bootstrap的人都知道这个是.
		buffer.append("    &nbsp;<input id=\"btnSubmit\" class=\"btn btn-info\" type=\"submit\" value=\"查询\"/>").append(ENTER);
		buffer.append("  </div>").append(ENTER);
		
		buffer.append("<table id=\"contentTable\" class=\"table table-striped table-bordered table-hover\">").append(ENTER);
		buffer.append("<thead>").append(ENTER);//thead标签End
		buffer.append("<tr>").append(ENTER);//tr标签End
		/*******遍历列表的th的列*****/
		for (Map<String, Object> col : getTableRemarks(table)) {
			 for (String k : col.keySet()){  
		        String colName = col.get(k).toString();
				buffer.append("<th>").append(colName).append("</th>");
				buffer.append(ENTER);
		      }  
		}
		buffer.append("<th>操作</th> ");
		buffer.append(ENTER);
		
		buffer.append("</tr>").append(ENTER);
		buffer.append("</thead>").append(ENTER);
		
		buffer.append("<tbody>").append(ENTER);
		
		/*******遍历列表的td的列*****/
		buffer.append("   <c:forEach items=\"${list}\" var=\""+objectName+"\" varStatus=\"row\">").append(ENTER);
		buffer.append("		<tr>").append(ENTER);
		buffer.append("		<td>${row.index+1 }</td>").append(ENTER);
		
		for (Map<String, Object> col : getCols(table)) {
			buffer.append("         <td>");
			if(Class.forName(col.get(CLASS).toString()).isAssignableFrom(Date.class) || Class.forName(col.get(CLASS).toString()) == Timestamp.class) {
				//如果是Date类型就转换用EL表达式格式化fmt:formatDate
				buffer.append("<fmt:formatDate value=\"${"+objectName+"."+col.get(NAME).toString()+"}\"  type=\"date\" dateStyle=\"long\"/>");
			} else if(getClassName(col.get(NAME).toString()).equals(Class.forName(col.get(CLASS).toString()).getSimpleName())) {
				buffer.append(" ${"+objectName+"."+col.get(NAME).toString()+"}" );
			}else{
				buffer.append(" ${"+objectName+"."+col.get(NAME).toString()+"}" );
			}
			buffer.append("</td>");
			buffer.append(ENTER);
		}
		
		buffer.append("    </tr>").append(ENTER);
		buffer.append("   </c:forEach>").append(ENTER);
		buffer.append("</tbody>").append(ENTER);//tbody标签结束.
		
		buffer.append("</table>").append(ENTER);
		//这个是pagination.jsp是分页文件.
		buffer.append("<%@ include file=\"/WEB-INF/views/include/pagination.jsp\"%>").append(ENTER);
		buffer.append("</form:form>").append(ENTER);//form:form标签结束.
		buffer.append("</body>").append(ENTER);//body标签结束.
		buffer.append("</html>").append(ENTER);//html标签结束.
		buffer.append(ENTER);
		buffer.append(ENTER);
		FileUtils.save("output-code/" + ROOT_PACKAGE.replaceAll("\\.", "/") + "/jsp/" + className + ".jsp", buffer.toString());
		
	}
	
	
	/***
	 * 创建表的类定义常量
	 * @param tables
	 */
	public void createTableClass(List<String> tables) {
		StringBuilder buffer = new StringBuilder();
		buffer.append("package " + ROOT_PACKAGE + ".domain;");
		buffer.append(ENTER);
		buffer.append(ENTER);
		buffer.append("/**\n * @Created：" + NOW_DATE + "\n * @Author " + AUTHOR + "\n");
		buffer.append(" * @Version:").append(Version).append(ENTER);
		buffer.append(" * @Email:").append(myEmail).append("\n*/");
		buffer.append(ENTER);
		buffer.append("public interface Table {");
		buffer.append(ENTER);
		for (String table : tables) {
			buffer.append(TAB);
			buffer.append("String " + getTableConstantName(table) + " = \"" + table.toUpperCase() + "\";");
			buffer.append(ENTER);
		}
		buffer.append(ENTER);
		buffer.append("}");
		buffer.append(ENTER);
		FileUtils.save("output-code/" + ROOT_PACKAGE.replaceAll("\\.", "/") + "/domain/Table.java", buffer.toString());
	}

	/***
	 * 获取数据库表名
	 * @return
	 * @throws Exception
	 */
	public List<String> getTables() throws Exception {
		List<Object> params = new ArrayList<Object>();
		//System.out.println("==========="+DB_NAME);
		//params.add(DB_NAME);
		String dbname=DB_NAME;
		params.add(dbname);
		
		ResultSet rs = DBHelperUtils.query("select table_name from information_schema.tables where table_schema = ? order by table_name", params);
		List<String> tables = new ArrayList<String>();
		while (rs.next()) {
			tables.add(rs.getString(1));		
		}
		return tables;
	}
	
	/***
	 * 列名 类型 => 说明
	 * TABLE_CAT String => 表 catalog
	 * TABLE_SCHEM String => 表 schema
	 * TABLE_NAME String => 表名
	 * TABLE_TYPE String => 表类型
	 * REMARKS String => 表注释
	 * 获取表的列
	 * @param table
	 * @return
	 * @throws Exception
	 */
	private List<Map<String, Object>> getCols(String table) throws Exception {
		 List<Map<String, Object>> cols = new ArrayList<Map<String,Object>>();
		 ResultSetMetaData md = DBHelperUtils.query("select * from " + table + " where 1 = 2", null).getMetaData();
		 
		 for (int i = 1; i <= md.getColumnCount(); i++) {
			 Map<String, Object> col = new HashMap<String, Object>();
			 cols.add(col);
			 col.put(NAME, md.getColumnName(i));
			 col.put(CLASS, md.getColumnClassName(i));
			 col.put(SIZE, md.getColumnDisplaySize(i));
			 col.put(REMARKS, md.getColumnName(i));
		/*	System.out.println("1"+ md.getCatalogName(i));
			System.out.println("2"+ md.getColumnClassName(i));
			System.out.println("3"+ md.getColumnDisplaySize(i));
			System.out.println("4"+ md.getColumnType(i));
			System.out.println("5"+ md.getSchemaName(i));
			System.out.println("6"+ md.getPrecision(i));
			System.out.println("7"+ md.getScale(i));*/
			 
			 String _type = null;
			 String type = md.getColumnTypeName(i);
			 if(type.equals("INT")) {
				 _type = "INTEGER";
			 } else if(type.equals("DATETIME")) {
				 _type = "TIMESTAMP";
			 } else {
				 _type = type;
			 }
			 col.put(TYPE, _type);
		}
		return cols;
	}
 
	
	/**
	 * 获取所有表
	 * @param conn
	 * @throws SQLException
	 */
	public static List<Map<String, Object>> getAllTable() throws SQLException {
		/**
		 * 定义一个Lis
		 */
		List<Map<String, Object>> cols = new ArrayList<Map<String,Object>>();
		DatabaseMetaData metaData = DBHelperUtils.getInstance().getDatabaseMetaData();

		//这个是获取所有表.
		ResultSet rs = metaData.getTables(null, "%", "%", new String[] {"TABLE"});

		while (rs.next()) {
			String tableName = rs.getString("TABLE_NAME");////这个是获取表名

			if(tableName!=null){
				Map<String, Object> col = new HashMap<String, Object>();
				// rs =getConnection.getMetaData().getColumns(null, getXMLConfig.getSchema(),tableName.toUpperCase(), "%");
				//其他数据库不需要这个方法的，直接传null，这个是oracle和db2这么用
				
				ResultSet rs1 = metaData.getColumns(null, "%", tableName, "%");
                
                while(rs1.next()){
                	String COLUMN_NAME = rs1.getString("COLUMN_NAME");
                	String REMARKS = rs1.getString("REMARKS");
                 	//先判断备注是否为空,不为空就取表的字段的注释说明，否则的话就去字段列名
	            	if(REMARKS==null||REMARKS==""){
	               		col.put(COLUMN_NAME, COLUMN_NAME);
	               	}else{
	               		col.put(REMARKS, REMARKS);
	               	}
                	cols.add(col);
                }
			}
		}
		return cols;
	}
	
	/***
	 * 获取列的备注
	 * @param table
	 * @return
	 * @throws SQLException
	 */
	public static List<Map<String, Object>> getTableRemarks(String table) throws SQLException {
		
		List<Map<String, Object>> cols = new ArrayList<Map<String,Object>>();
		 
		Connection conn=DBHelperUtils.getInstance().getConnection();
		DatabaseMetaData metaData = conn.getMetaData();

		ResultSet rs = metaData.getTables(null, "%", "%", new String[] {"TABLE"});

		while (rs.next()) {
			
			String tableName = rs.getString("TABLE_NAME");
			//传进来的表名和查询出来的表名作对比，并且是忽略大小写
			if(tableName!=null){
				if(table.equalsIgnoreCase(tableName)){
					Map<String, Object> col = new HashMap<String, Object>();
					//Map<String, Object> col = new HashTable<String, Object>();
					ResultSet rs1 = metaData.getColumns(null, "%", tableName, "%");
	                while(rs1.next()){
		               	String COLUMN_NAME = rs1.getString("COLUMN_NAME");
		               	String REMARKS = rs1.getString("REMARKS");
		               	
		               	//先判断备注是否为空,不为空就取表的字段的注释说明，否则的话就去字段列名
		               
		            	if(REMARKS==null||REMARKS==""){
		               		col.put(COLUMN_NAME, REMARKS);
		               	}else{
		               		col.put(REMARKS, COLUMN_NAME);
		               	}
		            	//去掉重复的数据
		               	col = removeRepeatData();
		               	cols.add(col);
	               }
	               break;
				}
			}
		}
		return cols;
	}
	 
	/**
	 * 获取表的主键.
	 * @param tableName
	 */
	public static String getTablePrimaryKeys(String tableName)throws Exception{
		DatabaseMetaData metaData = DBHelperUtils.getInstance().getDatabaseMetaData();
		ResultSet pkRSet = metaData.getPrimaryKeys(null, null, tableName); 
		
		String primaryKey = "";
		if(pkRSet.next() ) { 
			//把这个列的名称获取出来
			  primaryKey = pkRSet.getString("PK_NAME");//PK_NAME/COLUMN_NAME
			  primaryKey=(primaryKey==null?"":primaryKey); 
			  
			  System.out.println(primaryKey);
		} 
		return primaryKey;
	}
	
	/**
	 * 获取表的主键和外键包括外键表的名
	 * @param tableName
	 */
	public static String[] getTablePrimaryKeyForeignKey(String tableName)throws Exception{
		DatabaseMetaData metaData = DBHelperUtils.getInstance().getDatabaseMetaData();
		ResultSet fkSet = metaData.getPrimaryKeys(null, null, tableName); 
		String pkColumnName="",fkColumnName="",pkTablenName="";
		String [] paramsKey= new String[3];
		
		while(fkSet.next()){
			 pkColumnName = fkSet.getString("PK_NAME");//主键在网查到的有可能是PKCOLUMN_NAME
			 fkColumnName = fkSet.getString("FK_NAME");//外键网查到的有可能是PKCOLUMN_NAME
			 pkTablenName = fkSet.getString("PKTABLE_NAME");//主键表名
			 //System.out.println(pkColumnName+fkColumnName+pkTablenName);
			 pkColumnName=(pkColumnName==null?"":pkColumnName);
			 fkColumnName=(fkColumnName==null?"":fkColumnName);
			 pkTablenName=(pkTablenName==null?"":pkTablenName);
			 paramsKey[0]=fkColumnName;
			 paramsKey[1]=fkColumnName;
			 paramsKey[2]=pkTablenName;
		}  
		 
		return paramsKey;
	}

	/***
	 * 去掉重复的数据
	 * @return
	 */
	private static Map<String, Object> removeRepeatData() {
		Map<String, Object> col = new HashMap<String, Object>();
		Set<String> keySet = col.keySet();
		for (String str : keySet) {
			col.put(str, str);
		}
		return col;
	}
	
	
	/***
	 * 获取表的常量名，一般是在数据库建表的时候，写的注释..
	 * @param table
	 * @return
	 */
	private String getTableConstantName(String table) {
		String tableConstantName = table.toUpperCase();
		for (String item : IGNORE_TABLE_PREFIX) {
			tableConstantName = tableConstantName.replaceAll("^" + item.toUpperCase(), "");
		}
		return tableConstantName;
	}

	/***
	 * 获取类的名
	 * @param name
	 * @return
	 */
	private String getClassName(String name) {
		String[] names = name.split("_");
		StringBuilder sb = new StringBuilder();
		for (String n : names) {
			if(n.length() == 0) {
				sb.append("_");
			} else {
				sb.append(n.substring(0, 1).toUpperCase());
				if(n.length() > 1) {
					sb.append(n.substring(1).toLowerCase());
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 获取字段名
	 * @param name
	 * @return
	 */
	private String getFieldName(String name) {
		String _name = getClassName(name);
		return _name.substring(0, 1).toLowerCase() + _name.substring(1);
	}


	/**
	 * 转换成泛型Map
	 * @param limit
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public static List<Map> toListMap(int limit, ResultSet rs)throws SQLException {
		ResultSetMetaData rsmd = rs.getMetaData();
		int count = 0;
		List list = new ArrayList();
		while (rs.next()) {
			Map row = new HashMap();
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				row.put(rsmd.getColumnName(i), rs.getObject(i));
			}
			list.add(row);
			count++;
			if (count >= limit) {
				break;
			}
		}
		return list;
	 }
	
	/***
	 * 获取查询list
	 * @param conn
	 * @param sql
	 * @param limit
	 * @return
	 * @throws SQLException
	 */
	public static List<Map> queryForList(Connection conn, String sql, int limit)throws SQLException {
		PreparedStatement ps = conn.prepareStatement(sql.trim());
		ps.setMaxRows(limit);
		ps.setFetchDirection(1000);
		ResultSet rs = ps.executeQuery();
		return toListMap(limit, rs);
	}
	/***
	 * 获取查询list
	 * @param conn
	 * @param sql
	 * @param limit
	 * @return
	 * @throws SQLException
	 */
	public static List<Map> queryForList(String sql, int limit) throws SQLException {
		Connection conn = DBHelperUtils.getInstance().getConnection();
		return queryForList(conn, sql, limit);
	}
	
	
	/**
	 * 根据表取获取列的字段,并且合并成sql
	 * @throws Exception 
	 */
	public  String  getSQLMergeField(String tableName) throws Exception{
		String mergeField = "";
		//--遍历获取列,并拼接字符串,SQL的查询列,查询不建议用*去查询表的所有列.
		for (Map<String, Object> col : getCols(tableName)){
			//
			if(col.get(NAME).toString()!=null){
				mergeField +=col.get(NAME).toString()+",";//合并字段并用,隔开字段名
			}
		}
		//去掉最后一个,号然后拼接成一个完成的select查询字段
		//sb.deleteCharAt(sb.length()-1);
		if(mergeField!=null){
			mergeField = mergeField.substring(0, mergeField.length()-1);
		}
		
		return mergeField;
		
	}
	
	/***
	 * 获取表的所有字段，并给列起一个骆驼名称的字段.
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	public  String  getSQLMergeFieldAliases(String tableName) throws Exception{ 
		return null;
	}
	
	
	/***
	 * 生成所有Entity,Dao,Service,Controller,JSP 代码
	 * @throws Exception
	 */
	public void createAllCodeGenerator()throws Exception{
		List<String> tables = getTables();
		for (String table : tables) {
			createEntityClass(table);//this is method create Entity
			createDaoClass(table);//this is method create  Dao Interface
			createDaoImplClass(table);//this is method create Dao implement
			createServiceClass(table);//this is method create Service Interface
			createServiceImplClass(table);//this is method create Service implement
			createControllerClass(table);//this is method create Controller
			createJspView(table);//this is method JspView
		}
		 createTableClass(tables);
		
	}
	
	
	public static void main(String[] args)throws Exception {
		String sql="select * from SYS_MENU ";
		//List<Map> queryForList = queryForList(sql, 1000);
		/*for(Map m:queryForList){
			System.out.println("======"+m);
		}*/
		
		String tableName = "SYS_MENU";//表名
	  /*	List<Map<String, Object>> tableRemarks = getTableRemarks(tableName);
		int i=0;
		for (Map<String, Object> col : getTableRemarks(tableName)) {
			Set<String> keySet = col.keySet();
			for (Object str : keySet) {
				//System.out.println(str);
			}
		}
		*/
		
		//getTablePrimaryKeys("test");
		
		//new CodeGenerator().createJspView("sup_email");
		new CodeGenerator().createDaoImplClass("sup_email");
		//new CodeGenerator().getTablePrimaryKeyForeignKey("test");
		String sqlMergeField = new CodeGenerator().getSQLMergeField("sup_email");
		//System.out.println(sqlMergeField1);
		//System.out.println(sqlMergeField);
		String  colStr = "create_date_time";//createDateTime
		//create_date  createDate
		if(colStr.contains("_")){
			String columnStr="";
			String firstCol="";
			String otherCol="";
			String mrageCol="";
			
			String[] splits = colStr.split("_");
			for (int i = 0; i < splits.length; i++) {
				//System.out.println(splits[i]);
				if(i==0){
					 firstCol = splits[i];//第一个字符串的第一个字母不转换成大写，为了java的规范骆驼命名.
				}else{
					otherCol=splits[i];
					mrageCol += StringUtils.capitalize(otherCol);
				}
			}
			System.out.println(firstCol+mrageCol);
		}
		
		//createAllCodeGenerator();
		
	}

}
