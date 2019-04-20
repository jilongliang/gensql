package com.flong.commons.utils.converter;

import org.apache.log4j.Logger;
import org.springframework.core.convert.converter.Converter;
import org.springframework.web.multipart.MultipartFile;

/**
 * 将字符串转换空上传文件对象的转换器
 *
 * 创建日期：2012-12-26
 * @author wangk
 */
public class StringToMultipartFileConverter implements Converter<String, MultipartFile> {
	/** log4j对象 */
	private static final Logger log = Logger.getLogger(StringToMultipartFileConverter.class);	
	
	@Override
	public MultipartFile convert(String string) {
		log.warn(string);
		return null;
	}	

}
