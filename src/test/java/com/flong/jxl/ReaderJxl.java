package com.flong.jxl;

import java.io.File;

import com.flong.commons.lang.config.PropertiesHelper;
import com.flong.commons.utils.FileUtils;
import com.flong.commons.utils.ObjectUtil;

import jxl.Sheet;
import jxl.Workbook;

/**
 * @author liangjilong
 */
public class ReaderJxl {

	protected static final String ENTER = "\n";//换行常量
	protected static final String TAB = "    ";//空格置位常量
	protected static String COMMA=",";//SQL逗号常量
	public static final String ROOT_PACKAGE = PropertiesHelper.getValueByKey("rootPackage");//文件输出的路径
	//内方法
	public static void main(String[] args) {
		String path = ReaderJxl.class.getClassLoader().getResource("table_all.xls").getPath();
		String text = readerExcel(path);
		//指定路径进行输出
		FileUtils.save("output-doc/" + ROOT_PACKAGE.replaceAll("\\.", "/") + "/sqlcript/database.sql",text);
	}

	/**
	 * @Description通过指定路径读取规定的excel模板进行生成Oracle声明式脚本.
	 *        目前是只对VARCHAR2，NVARCHAR2,CHAR,DATE,TIMESTAMP,LONG,NCLOB类型进行处理.
	 *        由于最近在工作中，以上类型用比较多,故对以进行处理。可以根据自己需求进行更改程序。
	 *        数据库类型字段可以在PLSQLDEV、Navicat Premium等工具进行打开数据库表查看进行研发
	 *        推荐学习博客：http://www.cnblogs.com/kerrycode/p/3265120.html
	 * @Author		liangjilong
	 * @Date		2017年3月24日 下午5:43:16
	 * @param path  文件路径
	 * @return 		void 返回类型
	 */
	private static String readerExcel(String path) {
		String result = "";
		try {
			Workbook book = Workbook.getWorkbook(new File(path));//根据指定模板文件进行读完文件
			String [] sheetNames = book.getSheetNames();//获取当前Excel所有sheet的名称
			Sheet [] sheets = book.getSheets();//读取一个excel里面有多少个sheet
			for (int i = 0; i < sheets.length; i++) {
			 
				String sheetName = sheetNames[i];
				StringBuilder buffer = new StringBuilder();  
				buffer.append("declare").append(ENTER);//declare关键字
				buffer.append(TAB+"iCnt number := 0; ").append(ENTER);//查询是否存在此表的记录数变量
				buffer.append("begin ").append(ENTER);//begin关键字
				buffer.append(TAB+"select count(*) into iCnt from user_tables  where  lower(table_name) = lower('"+sheetName+"'); ").append(ENTER);
				buffer.append(TAB+"if iCnt = 0 then -- 如果查询不到这个表就创建这个表").append(ENTER);
				buffer.append(TAB+"execute immediate  'create table "+sheetName+" ").append(ENTER);//组装创建表SQL脚本
				buffer.append(TAB+"(").append(ENTER);
				
				Sheet sheet = sheets[i];//获取每一个Sheet
				int rows = sheet.getRows();//读取每个sheet有多少行
				//j是每个sheet的行数
				for (int j = 0; j < rows; j++) { 
					//字段名称,字段类型,字段长度,是否为空
					String fieldName = "",fieldType="",fieldLength="" ,isNull = "";
					if(j==2){
						//sheet.getCell(1, j)获取每个sheet的第二行的第一个列的值
						 fieldName = sheet.getCell(1, j).getContents();
						 fieldType = sheet.getCell(2, j).getContents();
						 fieldLength = sheet.getCell(3, j).getContents();
						 isNull = sheet.getCell(4, j).getContents();
						 if(ObjectUtil.isNotEmpty(isNull) && "N".equalsIgnoreCase(isNull.trim())){
							 if("VARCHAR2".equalsIgnoreCase(fieldType) || "NVARCHAR2".equalsIgnoreCase(fieldType) ||"CHAR".equalsIgnoreCase(fieldType)){
									buffer.append(TAB+TAB+fieldName+TAB+fieldType+"("+fieldLength+") NOT NULL " ).append(COMMA).append(ENTER);
								}else if("NUMBER".equalsIgnoreCase(fieldType)){
									buffer.append(TAB+TAB+fieldName+TAB+fieldType+"("+fieldLength+") NOT NULL " ).append(COMMA).append(ENTER);
								}else if(("DATE".equalsIgnoreCase(fieldType))||(fieldType!=null && fieldType.contains("TIMESTAMP")) ){
									buffer.append(TAB+TAB+fieldName+TAB+fieldType).append(COMMA).append(ENTER);
								}else if("LONG".equalsIgnoreCase(fieldType) ||"NCLOB".equalsIgnoreCase(fieldType)){
									buffer.append(TAB+TAB+fieldName+TAB+fieldType).append(COMMA).append(ENTER);
								}
						 }else{
							 if("VARCHAR2".equalsIgnoreCase(fieldType) || "NVARCHAR2".equalsIgnoreCase(fieldType) ||"CHAR".equalsIgnoreCase(fieldType)){
									buffer.append(TAB+TAB+fieldName+TAB+fieldType+"("+fieldLength+") " ).append(COMMA).append(ENTER);
								}else if("NUMBER".equalsIgnoreCase(fieldType)){
									buffer.append(TAB+TAB+fieldName+TAB+fieldType+"("+fieldLength+")" ).append(COMMA).append(ENTER);
								}else if(("DATE".equalsIgnoreCase(fieldType))||(fieldType!=null && fieldType.contains("TIMESTAMP")) ){
									buffer.append(TAB+TAB+fieldName+TAB+fieldType).append(COMMA).append(ENTER);
								}else if("LONG".equalsIgnoreCase(fieldType) ||"NCLOB".equalsIgnoreCase(fieldType)){
									buffer.append(TAB+TAB+fieldName+TAB+fieldType).append(COMMA).append(ENTER);
								}
						 }
					}else if(j>2){
						 fieldName = sheet.getCell(1, j).getContents();
						 fieldType = sheet.getCell(2, j).getContents();
						 fieldLength = sheet.getCell(3, j).getContents();
						 isNull = sheet.getCell(4, j).getContents();
						 //判断类型是否非必填字段
						 if(ObjectUtil.isNotEmpty(isNull) && "N".equalsIgnoreCase(isNull.trim())){
							//计算是否是最后一个行,是不添加,符号
							 if(rows-1==j){
								 if("VARCHAR2".equalsIgnoreCase(fieldType) || "NVARCHAR2".equalsIgnoreCase(fieldType) ||"CHAR".equalsIgnoreCase(fieldType)){
									buffer.append(TAB+TAB+fieldName+TAB+fieldType+"("+fieldLength+") NOT NULL" ).append(ENTER);
								 }else if("NUMBER".equalsIgnoreCase(fieldType)){
									buffer.append(TAB+TAB+fieldName+TAB+fieldType+"("+fieldLength+") NOT NULL" ).append(ENTER);
								 }else if(("DATE".equalsIgnoreCase(fieldType))||(fieldType!=null && fieldType.contains("TIMESTAMP")) ){
									buffer.append(TAB+TAB+fieldName+TAB+fieldType).append(ENTER);
								 }else if("LONG".equalsIgnoreCase(fieldType) ||"NCLOB".equalsIgnoreCase(fieldType)){
									buffer.append(TAB+TAB+fieldName+TAB+fieldType).append(ENTER);
								 }
							 }else{
								 if("VARCHAR2".equalsIgnoreCase(fieldType) || "NVARCHAR2".equalsIgnoreCase(fieldType) ||"CHAR".equalsIgnoreCase(fieldType)){
									buffer.append(TAB+TAB+fieldName+TAB+fieldType+"("+fieldLength+") NOT NULL" ).append(COMMA).append(ENTER);
								 }else if("NUMBER".equalsIgnoreCase(fieldType)){
									buffer.append(TAB+TAB+fieldName+TAB+fieldType+"("+fieldLength+")  NOT NULL" ).append(COMMA).append(ENTER);
								 }else if(("DATE".equalsIgnoreCase(fieldType))||(fieldType!=null && fieldType.contains("TIMESTAMP")) ){
									buffer.append(TAB+TAB+fieldName+TAB+fieldType).append(COMMA).append(ENTER);
								 }else if("LONG".equalsIgnoreCase(fieldType) ||"NCLOB".equalsIgnoreCase(fieldType)){
									buffer.append(TAB+TAB+fieldName+TAB+fieldType).append(COMMA).append(ENTER);
								 }
							 }
						 }else{
							 //计算是否是最后一个行,是不添加,符号
							 if(rows-1==j){
								 if("VARCHAR2".equalsIgnoreCase(fieldType) || "NVARCHAR2".equalsIgnoreCase(fieldType) ||"CHAR".equalsIgnoreCase(fieldType)){
									buffer.append(TAB+TAB+fieldName+TAB+fieldType+"("+fieldLength+") " ).append(ENTER);
								 }else if("NUMBER".equalsIgnoreCase(fieldType)){
									buffer.append(TAB+TAB+fieldName+TAB+fieldType+"("+fieldLength+") " ).append(ENTER);
								 }else if(("DATE".equalsIgnoreCase(fieldType))||(fieldType!=null && fieldType.contains("TIMESTAMP")) ){
									buffer.append(TAB+TAB+fieldName+TAB+fieldType).append(ENTER);
								 }else if("LONG".equalsIgnoreCase(fieldType) ||"NCLOB".equalsIgnoreCase(fieldType)){
									buffer.append(TAB+TAB+fieldName+TAB+fieldType).append(ENTER);
								 }
							 }else{
								 if("VARCHAR2".equalsIgnoreCase(fieldType) || "NVARCHAR2".equalsIgnoreCase(fieldType) ||"CHAR".equalsIgnoreCase(fieldType)){
									buffer.append(TAB+TAB+fieldName+TAB+fieldType+"("+fieldLength+") " ).append(COMMA).append(ENTER);
								 }else if("NUMBER".equalsIgnoreCase(fieldType)){
									buffer.append(TAB+TAB+fieldName+TAB+fieldType+"("+fieldLength+") " ).append(COMMA).append(ENTER);
								 }else if(("DATE".equalsIgnoreCase(fieldType))||(fieldType!=null && fieldType.contains("TIMESTAMP")) ){
									buffer.append(TAB+TAB+fieldName+TAB+fieldType).append(COMMA).append(ENTER);
								 }else if("LONG".equalsIgnoreCase(fieldType) ||"NCLOB".equalsIgnoreCase(fieldType)){
									buffer.append(TAB+TAB+fieldName+TAB+fieldType).append(COMMA).append(ENTER);
								 }
							 }
						 }
					}
				}//读取行列
				
				buffer.append(TAB+")'; ").append(ENTER);//创建表脚本结束
				addTableAndColumnComent(buffer, sheet, rows);//对表与字段进行添加备注描述
				buffer.append(TAB+"end if; ").append(ENTER);//if脚本结束
				buffer.append("end; ").append(ENTER);//sql脚本结束
				buffer.append("/").append(ENTER);//sql脚本结束
				result+=buffer.toString();
			}
			book.close();//关闭文件流
			return result;
		} catch (Exception e) {
		   e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * @Description 添加表名与列的注释
	 * @Author		liangjilong
	 * @Date		2017年3月24日 下午5:39:23
	 * @param buffer 追加sql对象
	 * @param sheet 指定excel的sheet
	 * @param rows 	excel的每一个sheet单元有多少行
	 * @return 		void 返回类型
	 */
	private static void addTableAndColumnComent(StringBuilder buffer, Sheet sheet, int rows) {
		//----------------------添加表与字段备注信息说明----------------------
		for (int j = 0; j < rows; j++) { 
			String tableName = "" ,tableNameDesc = "";//表名,表描述
			String isPrimaryKey="",fieldName = "",fieldDescription="";//是否为主键,字段名称,字段描述
			if(j==0){
				tableName = sheet.getCell(1, j).getContents();
				tableNameDesc = sheet.getCell(3, j).getContents();//获取每个sheet的第一行的第三个列的值
				tableNameDesc = (tableNameDesc==null?" this is "+tableName+" tableName ":tableNameDesc);
				buffer.append(TAB).append("execute immediate 'comment on table "+tableName +" is ''"+tableNameDesc+"'''").append(";").append(ENTER);
			}else if (j==2){
				 tableName = sheet.getCell(1, 0).getContents();//重新获取表名给约束
				 isPrimaryKey = sheet.getCell(0, j).getContents();
				 fieldName = sheet.getCell(1, j).getContents();
				 if(ObjectUtil.isNotEmpty(isPrimaryKey)){
					buffer.append(TAB).append("execute immediate 'alter table "+tableName).append(" add constraint PK_"+fieldName+" ");
					buffer.append("primary key ("+fieldName+")' ;").append(ENTER);
				}
			}else if(j>2){ 
				 tableName = sheet.getCell(1, 0).getContents();//重新从第一行进行获取表名称
				 fieldDescription = sheet.getCell(6, j).getContents();
				 fieldName = sheet.getCell(1, j).getContents();
				 if(fieldName!=null&& !"".equals(fieldName)){
					 buffer.append(TAB).append("execute immediate 'comment on column "+tableName+"."+fieldName+" is ''"+fieldDescription+"'''").append(";").append(ENTER);
				 }
			}
		}
	}
}
