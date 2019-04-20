package com.flong.modules.pojo;
import com.flong.commons.persistence.Entity;
import com.flong.commons.persistence.annotation.Column;
import com.flong.commons.persistence.annotation.Id;
import com.flong.commons.persistence.annotation.Relation;
import com.flong.commons.utils.Table;


/**
 * @Created：2015-12-25
 * @Author liangjilong
 * @Version:1.0
 * @Description:TableIdentifiers
 * @Email:jilongliang@sina.com
*/
@Relation(TableIdentifiers.TABLE)
@SuppressWarnings("all")
public class TableIdentifiers extends Entity {

    /** 表名常量 */
    public static final String TABLE = Table.TABLE_IDENTIFIERS;

    /**
     * 列名常量
     */
    public static final String COL_TABLE_NAME = "TABLE_NAME";//table_name
    public static final String COL_ID_LENGTH = "ID_LENGTH";//id_length
    public static final String COL_IDENTIFIER = "IDENTIFIER";//identifier
    public static final String COL_PREFIX = "PREFIX";//prefix
    public static final String COL_LAST_DATE = "LAST_DATE";//last_date

    /**
     * 列属性
     */
    @Id    
    @Column(COL_TABLE_NAME)
    private String table_name;
    
    @Column(COL_ID_LENGTH)
    private Integer id_length;
    
    @Column(COL_IDENTIFIER)
    private Integer identifier;
    
    @Column(COL_PREFIX)
    private String prefix;
    
    @Column(COL_LAST_DATE)
    private String last_date;

    public String getTable_name() {
        return table_name;
    }
    public void setTable_name(String table_name) {
        this.table_name = table_name;
    }
    public Integer getId_length() {
        return id_length;
    }
    public void setId_length(Integer id_length) {
        this.id_length = id_length;
    }
    public Integer getIdentifier() {
        return identifier;
    }
    public void setIdentifier(Integer identifier) {
        this.identifier = identifier;
    }
    public String getPrefix() {
        return prefix;
    }
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
    public String getLast_date() {
        return last_date;
    }
    public void setLast_date(String last_date) {
        this.last_date = last_date;
    }
}
