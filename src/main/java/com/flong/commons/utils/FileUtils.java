package com.flong.commons.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
/**
 * 
 * @date 2014-2-14 下午1:24:30 
 * @author wangk
 * @Description:
 * @project TJY
 */
public class FileUtils {
	
	private static String content = "", line = System.getProperty("line.separator");// 换行相当于\n
	/**
	 * 
	 * @Description TODO
	 * @Author		liangjilong
	 * @Date		2017年3月1日 下午2:27:00
	 * @param path
	 * @param data 		参数
	 * @return 		void 返回类型
	 */
	public static void save(String path, String data) {
		try {
			File file = new File(path);
			File dir = new File(path.substring(0, path.lastIndexOf("/")));
			if(!dir.exists()) {
				dir.mkdirs();
			}
			FileWriter out = new FileWriter(file);
			out.write(data);
			out.flush();
			out.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 写文件 BufferedWriter BufferedReader
	 * 
	 * @param formPath
	 * @param toPath
	 */
	public static boolean writer(String formPath, String toPath) {
		Reader reader = null;
		Writer writer = null;
		boolean flag = true;
		BufferedWriter buffWriter = null;
		BufferedReader buffReader = null;
		try {
			reader = new FileReader(new File(formPath));
			writer = new FileWriter(new File(toPath));// writer不能关闭

			buffWriter = new BufferedWriter(writer);// 这个写完可以关闭
			buffReader = new BufferedReader(reader);

			content = buffReader.readLine();
			while (content != null) {
				buffWriter.write(line + content);
				content = buffReader.readLine();
				buffWriter.flush();// 只要用到缓冲区就flush//其实关闭了缓冲区,就是关闭缓冲区中流的对象.
									// //写一次flush是为了防止停电就挂了~
			}
			reader.close();
			buffReader.close();
			buffWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return flag;
	}
	/**
	 * 读文件流
	 * 
	 * @param formPath从哪里读取的文件路径
	 * @return
	 */
	public static String reader(String formPath) {
		FileReader read = null;
		BufferedReader reader = null;
		try {
			read = new FileReader(new File(formPath));
			reader = new BufferedReader(read);
			StringBuffer buffer = new StringBuffer("");
			content = reader.readLine();
			while (content != null) {
				buffer.append(content).append(line);
				content = reader.readLine();
			}
			return content = buffer.toString();// 返回
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null)
					reader.close();
				if (read != null)
					read.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "";// 没值就返回空
	}
	
	/**
	 * @Description 根据文件路径并带编码进行读取文件流内容.
	 * @Author		liangjilong
	 * @Date		2017年3月3日 下午3:58:58
	 * @param filePath
	 * @param charsetName
	 * @return 		参数
	 * @return 		StringBuffer 返回类型
	 */
	public static StringBuffer reader(String filePath, String charsetName) {
		StringBuffer sb = new StringBuffer();
	    FileInputStream fis = null;
	    InputStreamReader isr = null;
	    BufferedReader reader = null;
	    try {
	    	if(ObjectUtil.isEmpty(charsetName)){
	    		charsetName = "UTF-8";
	    	}
	    	fis = new FileInputStream(filePath);
	    	isr = new InputStreamReader(fis, charsetName);
	    	reader = new BufferedReader(isr);
	    	String line = null;
	    	while ((line = reader.readLine()) != null){
	    		sb.append(line).append("\r\n");
	    	}
	    } catch (IOException e) {
	    	e.printStackTrace();
	    } finally {
	    	try {
	    		if(fis!=null){fis.close();fis=null;}
	    		if(isr!=null){isr.close();isr=null;}
	    		if(reader!=null){reader.close();reader=null;}
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	    return sb;
	}
	
	
	  /** 
     * 读取文件里面的所有文件 
     *  
     * @param filePath 
     */  
    public static void fileList(String filePath) {  
        File srcFile = new File(filePath);  
        boolean flag = srcFile.exists();  
        /** 
         * @flag判断文件是否存在 
         * @isDirectory测试此抽象路径名表示的文件是否是一个目录 
         * @canWrite测试应用程序是否可以读取此抽象路径名表示的文件 
         */  
        if (!flag || !srcFile.isDirectory() || !srcFile.canRead()) {  
            try {  
                srcFile.createNewFile();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        } else {  
            File[] file = srcFile.listFiles();  
            for (int i = 0; i < file.length; i++) {  
            	String absolutePath = file[i].getAbsolutePath();
            	//System.out.println(absolutePath);
            	String string = "<script src=\"${pluginsPath}jquery/syntaxhighlighter/scripts/"+absolutePath.split("\\\\")[8]+"\"" +" type=\"text/javascript\"></script>";
            	
            	//String string = "<link type=\"text/css\" rel=\"stylesheet\"  href=\""+absolutePath.split("\\\\")[8]+"\"/>" ;
                System.out.println(string);  
            }  
        }  
    }  
    
    public static void main(String[] args) {
    	//String filePath = "C:\\Users\\liangjilong\\Desktop\\JS高亮代码demo\\syntaxhighlighter_3.0.83\\syntaxhighlighter_3.0.83\\scripts";
    	String filePath = "C:\\Users\\liangjilong\\Desktop\\JS高亮代码demo\\syntaxhighlighter_3.0.83\\syntaxhighlighter_3.0.83\\scripts";
    	fileList(filePath);
	}
}
