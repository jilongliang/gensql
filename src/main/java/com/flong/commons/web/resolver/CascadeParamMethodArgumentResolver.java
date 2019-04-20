package com.flong.commons.web.resolver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.AbstractNamedValueMethodArgumentResolver;

import com.flong.commons.lang.annotation.CascadeParam;
import com.flong.commons.utils.ObjectUtil;
import com.flong.commons.utils.converter.StringToStringURLConverter;

/**
 * 级联参数解析器
 *
 * 创建日期：2013-2-20
 * @author wangk
 */
public class CascadeParamMethodArgumentResolver extends AbstractNamedValueMethodArgumentResolver {
	/** 级联参数分隔符 */
	private static final String cascadeParamSeparator = ".";
	
	/**
	 * 构造方法
	 * 创建日期：2013-2-20
	 * 修改说明：
	 * @author wangk
	 */
	public CascadeParamMethodArgumentResolver() {
		super(null);
	}

	/**
	 * 构造方法
	 * @param beanFactory
	 * 创建日期：2013-2-20
	 * 修改说明：
	 * @author wangk
	 */
	public CascadeParamMethodArgumentResolver(ConfigurableBeanFactory beanFactory) {
		super(beanFactory);
	}

	/**
	 * @see org.springframework.web.method.support.HandlerMethodArgumentResolver#supportsParameter(
	 * 		org.springframework.core.MethodParameter)
	 * 创建日期：2013-2-20
	 * 修改说明：
	 * @author wangk
	 */
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(CascadeParam.class);
	}

	/**
	 * @see org.springframework.web.method.annotation.AbstractNamedValueMethodArgumentResolver#createNamedValueInfo(
	 *      org.springframework.core.MethodParameter)
	 * 创建日期：2013-2-20
	 * 修改说明：
	 * @author wangk
	 */
	@Override
	protected NamedValueInfo createNamedValueInfo(MethodParameter parameter) {
		return new RequestParamNamedValueInfo();
	}

	/**
	 * @see org.springframework.web.method.annotation.AbstractNamedValueMethodArgumentResolver#resolveName(
	 *      java.lang.String, org.springframework.core.MethodParameter,
	 *      org.springframework.web.context.request.NativeWebRequest)
	 * 创建日期：2013-2-20
	 * 修改说明：
	 * @author wangk
	 */
	@Override
	protected Object resolveName(String name, MethodParameter parameter,
			NativeWebRequest nativeWebRequest) throws Exception {
		String paramName = parameter.getParameterAnnotation(CascadeParam.class).value();
		if("".equals(paramName)) {
			paramName = name;
		}
		StringToStringURLConverter stringToStringURLConverter = new StringToStringURLConverter();
		Map<String, Object> requestDataMap = new HashMap<String, Object>();
		for (String param : nativeWebRequest.getParameterMap().keySet()) {
			param = stringToStringURLConverter.convert(param);
			String[] cascadeParam = null;
			if(param.matches("^\\w+(\\[\\w+\\])+$")) {
				String[] paramComponents = param.split("\\[");
				cascadeParam = new String[paramComponents.length];
				if(!paramName.equals(paramComponents[0])) {
					continue;
				} else {
					cascadeParam[0] = paramComponents[0];
				}
				for (int i = 1; i < cascadeParam.length; i++) {
					cascadeParam[i] = paramComponents[i].substring(0, paramComponents[i].length()-1);
				}
			} else {
				cascadeParam = param.split("\\" + cascadeParamSeparator);
				if(cascadeParam.length < 2 || !paramName.equals(cascadeParam[0])) {
					continue;
				}
			}
			Object value = null;
			String[] values = nativeWebRequest.getParameterValues(param);
			if(values.length == 1) {
				value = stringToStringURLConverter.convert(values[0]);
			} else {
				List<String> valueList = new ArrayList<String>();
				for (String item : values) {
					valueList.add(stringToStringURLConverter.convert(item));
				}
				value = valueList;
			}
			Map<String, Object> cascadeParamMap = requestDataMap;
			for (int i = 1; i < cascadeParam.length-1; i++) {
				@SuppressWarnings("unchecked")
				Map<String, Object> indexMap = (Map<String, Object>)cascadeParamMap.get(cascadeParam[i]);
				if(indexMap == null) {
					indexMap = new HashMap<String, Object>();
					cascadeParamMap.put(cascadeParam[i], indexMap);
				}
				cascadeParamMap = indexMap;
			}
			cascadeParamMap.put(cascadeParam[cascadeParam.length-1], value);
		}
		if(requestDataMap.isEmpty()) {
			return null;
		}
		return ObjectUtil.parse(requestDataMap, parameter.getParameterType());
	}
	
	/**
	 * @see org.springframework.web.method.annotation.AbstractNamedValueMethodArgumentResolver#handleMissingValue(
	 *      java.lang.String, org.springframework.core.MethodParameter)
	 * 创建日期：2013-2-20
	 * 修改说明：
	 * @author wangk
	 */
	@Override
	protected void handleMissingValue(String name, MethodParameter parameter)
			throws ServletException {
		throw new MissingServletRequestParameterException(name, parameter.getParameterType().getName());
	}

	/**
	 * 请求参数信息类
	 *
	 * 创建日期：2013-2-20
	 * @author wangk
	 */
	private static class RequestParamNamedValueInfo extends NamedValueInfo {
        private RequestParamNamedValueInfo() {
            super("", false, "");
        }
    }

}
