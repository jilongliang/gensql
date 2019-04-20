package com.flong.commons.lang.exception;

@SuppressWarnings("all")
public class IdOutOfBoundsException extends BizLogicException {

	public IdOutOfBoundsException(String string) {
		super(string);
	}
	
	public IdOutOfBoundsException(){
		this("已达到ID最大值限制");
	}

}
