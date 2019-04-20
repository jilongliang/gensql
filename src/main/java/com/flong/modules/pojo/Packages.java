package com.flong.modules.pojo;

import java.io.Serializable;
/**
 * 
 * @Description	包实体
 * @ClassName	Packages
 * @Date		2017年3月1日 上午9:51:16
 * @Author		liangjilong
 * @Copyright (c) All Right Reserved , 2017.
 */
@SuppressWarnings("all")
public class Packages implements Serializable{
	private String owner; 
	private String object_name; 
	private String subobject_name; 
	private String object_id;
	private String data_object_id; 
	private String object_type; 
	private String created; 
	private String last_ddl_time; 
	private String timestamp; 
	private String status; 
	private String temporary; 
	private String generated; 
	private String secondary; 
	private String namespace; 
	private String edition_name;
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getObject_name() {
		return object_name;
	}
	public void setObject_name(String object_name) {
		this.object_name = object_name;
	}
	public String getSubobject_name() {
		return subobject_name;
	}
	public void setSubobject_name(String subobject_name) {
		this.subobject_name = subobject_name;
	}
	public String getObject_id() {
		return object_id;
	}
	public void setObject_id(String object_id) {
		this.object_id = object_id;
	}
	public String getData_object_id() {
		return data_object_id;
	}
	public void setData_object_id(String data_object_id) {
		this.data_object_id = data_object_id;
	}
	public String getObject_type() {
		return object_type;
	}
	public void setObject_type(String object_type) {
		this.object_type = object_type;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public String getLast_ddl_time() {
		return last_ddl_time;
	}
	public void setLast_ddl_time(String last_ddl_time) {
		this.last_ddl_time = last_ddl_time;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTemporary() {
		return temporary;
	}
	public void setTemporary(String temporary) {
		this.temporary = temporary;
	}
	public String getGenerated() {
		return generated;
	}
	public void setGenerated(String generated) {
		this.generated = generated;
	}
	public String getSecondary() {
		return secondary;
	}
	public void setSecondary(String secondary) {
		this.secondary = secondary;
	}
	public String getNamespace() {
		return namespace;
	}
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}
	public String getEdition_name() {
		return edition_name;
	}
	public void setEdition_name(String edition_name) {
		this.edition_name = edition_name;
	}
}
