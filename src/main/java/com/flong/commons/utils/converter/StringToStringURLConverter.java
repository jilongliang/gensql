package com.flong.commons.utils.converter;

import java.net.URLDecoder;

import org.apache.log4j.Logger;
import org.springframework.core.convert.converter.Converter;

/**
 * 字符串URL解码转换器
 * @description
 * 创建日期：2012-12-13
 * @author wangk
 * @since bear-emsserver-ci
 */
public class StringToStringURLConverter implements Converter<String, String> {
	/** log4j对象 */
	private static final Logger log = Logger.getLogger(StringToStringURLConverter.class);
	
	@Override
	public String convert(String string) {
		if(string == null) {
			return null;
		}
		try {
			if(string.matches("^(.*(%[A-Fa-f\\d]{2})+.*)+$")) {
				string = URLDecoder.decode(string, "UTF-8");
			}
		} catch (Exception e) {
			log.warn(e);
		}
		return string;
	}

}
