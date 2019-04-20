package com.flong.commons.persistence;

import com.flong.commons.persistence.annotation.Column;
import com.flong.commons.persistence.annotation.Id;
import com.flong.commons.persistence.annotation.Relation;

/**
 * 数据库版本pojo对象
 * 创建日期：2013-1-6
 * @author niezhegang
 */
@Relation("SYS_DBVERSION")
public class DbVersion extends Entity{
	private static final long serialVersionUID = -7994526227425075013L;
	/**ID*/
	@Id
	@Column(COL_ID)
	private Long id;
	/**版本号*/
	@Column(COL_VERSIONNUMBER)
	private Integer versionNumber = 0;
	public static final String TABLENAME = "SYS_DBVERSION";
	/**对应ID列名常量*/
	private static final String COL_ID = "ID";
	/**对应版本号列名常量*/
	private static final String COL_VERSIONNUMBER = "VERSIONNUMBER";
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getVersionNumber() {
		return versionNumber;
	}
	public void setVersionNumber(Integer versionNumber) {
		this.versionNumber = versionNumber;
	}
	
}
