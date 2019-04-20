package com.flong.commons.persistence.bean;

public class FileReturn {
	private String oldName;
	private String newName;
	
	public FileReturn(String oldName, String newName) {
		this.oldName = oldName;
		this.newName = newName;
		
	}
	
	public String getOldName() {
		return oldName;
	}
	public void setOldName(String oldName) {
		this.oldName = oldName;
	}
	public String getNewName() {
		return newName;
	}
	public void setNewName(String newName) {
		this.newName = newName;
	}
	
	
}
