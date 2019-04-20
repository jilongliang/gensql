package com.flong.commons.web.domain;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

import com.flong.commons.persistence.dao.impl.BaseDomain;

/**
 * 上传文件请求数据模型类
 *
 * 创建日期：2012-12-27
 * @author wangk
 */
public class FileRequestData extends BaseDomain {
	private static final long serialVersionUID = 8664205995902241737L;
	
	/** 业务相关的请求数据 */
	private RequestData requestData;
	/** 上传的文件 */
	private MultipartFile file;

	/**
	 * 获得业务相关的请求数据
	 *
	 * @return
	 * 创建日期：2012-12-27
	 * 修改说明：
	 * @author wangk
	 */
	public RequestData getRequestData() {
		return requestData;
	}
	
	public void setRequestData(RequestData requestData) {
		this.requestData = requestData;
	}
	
	public MultipartFile getFile() {
		return file;
	}
	
	public void setFile(MultipartFile file) {
		this.file = file;
	}

	/**
	 * 获得文件名称
	 *
	 * @return
	 * 创建日期：2012-12-27
	 * 修改说明：
	 * @author wangk
	 */
	public String getName() {
		return file==null?null:file.getOriginalFilename();
	}

	/**
	 * 获得文件类型
	 *
	 * @return
	 * 创建日期：2012-12-27
	 * 修改说明：
	 * @author wangk
	 */
	public String getContentType() {
		return file==null?null:file.getContentType();
	}
	
	/**
	 * 获得文件大小
	 *
	 * @return
	 * 创建日期：2012-12-27
	 * 修改说明：
	 * @author wangk
	 */
	public long getSize() {
		return file==null?null:file.getSize();
	}
	
	/**
	 * 获得文件的字节数组
	 *
	 * @return
	 * @throws IOException
	 * 创建日期：2012-12-27
	 * 修改说明：
	 * @author wangk
	 */
	public byte[] getBytes() throws IOException {
		return file==null?null:file.getBytes();
	}
	
	/**
	 * 获得文件的输入流
	 *
	 * @return
	 * @throws IOException
	 * 创建日期：2012-12-27
	 * 修改说明：
	 * @author wangk
	 */
	public InputStream getInputStream() throws IOException {
		return file==null?null:file.getInputStream();
	}

	/**
	 * 将上传的文件转移到file指定的文件中
	 *
	 * @param file
	 * @throws IOException
	 * 创建日期：2012-12-27
	 * 修改说明：
	 * @author wangk
	 */
	public void transferTo(File file) throws IOException {
		if(this.file != null) {
			this.file.transferTo(file);
		}
	}

	/**
	 * 将上传的文件转移到path指定的文件中
	 *
	 * @param path
	 * @throws IOException
	 * 创建日期：2012-12-27
	 * 修改说明：
	 * @author wangk
	 */
	public void transferTo(String path) throws IOException {
		transferTo(new File(path));
	}

}
