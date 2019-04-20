package com.flong.commons.persistence.exception;

/**
 * 错误代码常量接口
 *
 * 创建日期：2012-11-9
 * @author wangk
 */
public interface ErrorCode {
	/** 获取单一实体时有重复记录 */
	int DUPLICATE_RECORDE_AS_GET_ENTITY = 100101;
	/** 保存记录时数据库已经存在此记录 */
	int DUPLICATE_RECORDE_AS_SAVE_ENTITY = 100102;
	
	/** ID注解没有找到 */
	int ANNOTATION_NOT_FOUND_AS_ID = 100201;
	/** 关联注解没有找到 */
	int ANNOTATION_NOT_FOUND_AS_ASSOCIATION = 100202;
	/** 关系注解没有找到 */
	int ANNOTATION_NOT_FOUND_AS_RELATION = 100203;
	/** 列注解没有找到 */
	int ANNOTATION_NOT_FOUND_AS_COLUMN = 100204;
	/** 引用注解没有找到 */
	int ANNOTATION_NOT_FOUND_AS_REFERENCE = 100205;
	
	/** 指定列在实体中不存在对应的属性 */
	int FIELD_COLUMN_MAPPING_AS_NOT_EXIST_COLUMN = 100301;
	/** ID属性类型错误 */
	int FIELD_COLUMN_MAPPING_AS_ID_TYPE_ERROR = 100302;
	/** ID属性不唯一 */
	int FIELD_COLUMN_MAPPING_AS_ID_NOT_UNIQUE = 100303;
	/** 实体非自身循环关联 */
	int FIELD_COLUMN_MAPPING_AS_ASSOCIATION_CLOSED_LOOP = 100304;
	/** 实体有重复关联属性 */
	int FIELD_COLUMN_MAPPING_AS_DUPLICATE_ASSOCIATION = 100305;
	/** 实体关联类型错误 */
	int FIELD_COLUMN_MAPPING_AS_ASSOCIATION_TYPE_ERROR = 100306;
	/** 实体有重复引用属性 */
	int FIELD_COLUMN_MAPPING_AS_DUPLICATE_REFERENCE = 100307;
	/** 引用属性类型错误 */
	int FIELD_COLUMN_MAPPING_AS_REFERENCE_TYPE_ERROR = 100308;
	/** 列属性类型错误 */
	int FIELD_COLUMN_MAPPING_AS_FIELD_TYPE_ERROR = 100309;

	/** 创建分页SQL语句时未知数据库类型 */
	int PAGING_SQL_BUILDER_AS_UNKNOWN_DB_TYPE = 100401;

	/** 有非空约束的列其值为空 */
	int ILLEGAL_RECORD_AS_NULL_VALUE_ERROR = 100501;
	/** 更新瞬时状态的实体对象 */
	int ILLEGAL_RECORD_AS_UPDATE_TRANSIENT = 100502;

}
