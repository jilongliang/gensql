package com.flong.commons.lang.exception;
@SuppressWarnings("all")
public class TableNotFoundException extends BizLogicException {

	public TableNotFoundException(String string) {
		super(string);
	}

	public TableNotFoundException(){
		this("找不到相应的实体表名称");
	}
}
