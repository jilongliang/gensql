package com.flong.commons.utils.converter;

import org.springframework.core.convert.converter.Converter;

import com.flong.commons.web.domain.RequestData;

/**
 * @description 将字符串转换RequestData对象的转换器
 * @author wangk
 * @since bear-emsserver-ci 
 * @create 2012-7-4 下午08:03:05
 */
public class StringToRequestDataConverter implements Converter<String, RequestData> {

	@Override
	public RequestData convert(String requestData) {
		return new RequestData(new StringToStringURLConverter().convert(requestData));
	}

}
