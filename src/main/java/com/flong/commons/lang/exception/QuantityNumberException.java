package com.flong.commons.lang.exception;
@SuppressWarnings("all")
public class QuantityNumberException extends BizLogicException {

	public QuantityNumberException(String string) {
		super(string);
	}

	public QuantityNumberException(){
		this("批量生成编码的数量错误");
	}
}
