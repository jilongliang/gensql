package com.flong.commons.web.resolver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.AbstractNamedValueMethodArgumentResolver;

import com.flong.commons.utils.JsonUtil;
import com.flong.commons.utils.converter.StringToStringURLConverter;
import com.flong.commons.web.domain.RequestData;
import com.flong.commons.web.paging.PagingRequestData;

/**
 * 请求数据RequestData参数解析器
 *
 * 创建日期：2012-12-26
 * @author wangk
 */
public class RequestDataMethodArgumentResolver extends AbstractNamedValueMethodArgumentResolver {	
	/** 参数名称  */
	private String parameterName;
	
	/**
	 * 构造方法
	 * 创建日期：2012-12-27
	 * 修改说明：
	 * @author wangk
	 */
	public RequestDataMethodArgumentResolver() {
		super(null);
	}

	/**
	 * 构造方法
	 * @param beanFactory
	 * 创建日期：2012-12-27
	 * 修改说明：
	 * @author wangk
	 */
	public RequestDataMethodArgumentResolver(ConfigurableBeanFactory beanFactory) {
		super(beanFactory);
	}
	
	public String getParameterName() {
		return parameterName;
	}

	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		Class<?> parameterType = parameter.getParameterType();
		return parameterType == RequestData.class || parameterType == PagingRequestData.class;
	}

	@Override
	protected NamedValueInfo createNamedValueInfo(MethodParameter parameter) {
		return new RequestParamNamedValueInfo();
	}

	@Override
	protected Object resolveName(String name, MethodParameter parameter,
			NativeWebRequest nativeWebRequest) throws Exception {
		Set<String> names = nativeWebRequest.getParameterMap().keySet();
		StringToStringURLConverter stringToStringURLConverter = new StringToStringURLConverter();
		Class<?> parameterType = parameter.getParameterType();
		if(parameterType == RequestData.class && names.contains(parameterName)) {
			return new RequestData(stringToStringURLConverter.convert(
					nativeWebRequest.getParameter(parameterName)));
		}
		Map<String, String> requestDataMap = new HashMap<String, String>();
		for (String param : names) {
			String[] values = nativeWebRequest.getParameterValues(param);
			//处理转义字符
			param = stringToStringURLConverter.convert(param).replaceAll("^\\\\", "");
			if(values==null || values.length==0) {
				requestDataMap.put(param, null);
			} else if(values.length == 1) {
				requestDataMap.put(param, stringToStringURLConverter.convert(values[0]));
			} else {
				List<String> valueList = new ArrayList<String>();
				for (String value : values) {
					valueList.add(stringToStringURLConverter.convert(value));
				}
				requestDataMap.put(param, JsonUtil.obj2json(valueList));
			}
		}
		RequestData requestData = new RequestData(JsonUtil.obj2json(requestDataMap));
		if(parameterType == RequestData.class) {
			return requestData;
		}
		//创建PagingRequestData对象
		requestDataMap.remove(parameterName);
		PagingRequestData pagingRequestData = JsonUtil.convert(requestDataMap, PagingRequestData.class);
		if(names.contains(parameterName)) {
			pagingRequestData.setRequestData(requestData.getComponent(parameterName));
		} else {
			pagingRequestData.setRequestData(requestData);
		}
		return pagingRequestData;
	}

	@Override
	protected void handleMissingValue(String name, MethodParameter parameter)
			throws ServletException {
		throw new MissingServletRequestParameterException(name, parameter.getParameterType().getName());
	}

	private static class RequestParamNamedValueInfo extends NamedValueInfo {
        private RequestParamNamedValueInfo() {
            super("", false, "requestData");
        }
    }

}
