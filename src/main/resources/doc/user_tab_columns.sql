create or replace view user_tab_columns as
select TABLE_NAME, COLUMN_NAME, DATA_TYPE, DATA_TYPE_MOD, DATA_TYPE_OWNER,
       DATA_LENGTH, DATA_PRECISION, DATA_SCALE, NULLABLE, COLUMN_ID,
       DEFAULT_LENGTH, DATA_DEFAULT, NUM_DISTINCT, LOW_VALUE, HIGH_VALUE,
       DENSITY, NUM_NULLS, NUM_BUCKETS, LAST_ANALYZED, SAMPLE_SIZE,
       CHARACTER_SET_NAME, CHAR_COL_DECL_LENGTH,
       GLOBAL_STATS, USER_STATS, AVG_COL_LEN, CHAR_LENGTH, CHAR_USED,
       V80_FMT_IMAGE, DATA_UPGRADED, HISTOGRAM
  from USER_TAB_COLS
 where HIDDEN_COLUMN = 'NO';
comment on column USER_TAB_COLUMNS.TABLE_NAME is 'Table, view or cluster name';
comment on column USER_TAB_COLUMNS.COLUMN_NAME is 'Column name';
comment on column USER_TAB_COLUMNS.DATA_TYPE is 'Datatype of the column';
comment on column USER_TAB_COLUMNS.DATA_TYPE_MOD is 'Datatype modifier of the column';
comment on column USER_TAB_COLUMNS.DATA_TYPE_OWNER is 'Owner of the datatype of the column';
comment on column USER_TAB_COLUMNS.DATA_LENGTH is 'Length of the column in bytes';
comment on column USER_TAB_COLUMNS.DATA_PRECISION is 'Length: decimal digits (NUMBER) or binary digits (FLOAT)';
comment on column USER_TAB_COLUMNS.DATA_SCALE is 'Digits to right of decimal point in a number';
comment on column USER_TAB_COLUMNS.NULLABLE is 'Does column allow NULL values?';
comment on column USER_TAB_COLUMNS.COLUMN_ID is 'Sequence number of the column as created';
comment on column USER_TAB_COLUMNS.DEFAULT_LENGTH is 'Length of default value for the column';
comment on column USER_TAB_COLUMNS.DATA_DEFAULT is 'Default value for the column';
comment on column USER_TAB_COLUMNS.NUM_DISTINCT is 'The number of distinct values in the column';
comment on column USER_TAB_COLUMNS.LOW_VALUE is 'The low value in the column';
comment on column USER_TAB_COLUMNS.HIGH_VALUE is 'The high value in the column';
comment on column USER_TAB_COLUMNS.DENSITY is 'The density of the column';
comment on column USER_TAB_COLUMNS.NUM_NULLS is 'The number of nulls in the column';
comment on column USER_TAB_COLUMNS.NUM_BUCKETS is 'The number of buckets in histogram for the column';
comment on column USER_TAB_COLUMNS.LAST_ANALYZED is 'The date of the most recent time this column was analyzed';
comment on column USER_TAB_COLUMNS.SAMPLE_SIZE is 'The sample size used in analyzing this column';
comment on column USER_TAB_COLUMNS.CHARACTER_SET_NAME is 'Character set name';
comment on column USER_TAB_COLUMNS.CHAR_COL_DECL_LENGTH is 'Declaration length of character type column';
comment on column USER_TAB_COLUMNS.GLOBAL_STATS is 'Are the statistics calculated without merging underlying partitions?';
comment on column USER_TAB_COLUMNS.USER_STATS is 'Were the statistics entered directly by the user?';
comment on column USER_TAB_COLUMNS.AVG_COL_LEN is 'The average length of the column in bytes';
comment on column USER_TAB_COLUMNS.CHAR_LENGTH is 'The maximum length of the column in characters';
comment on column USER_TAB_COLUMNS.CHAR_USED is 'C is maximum length given in characters, B if in bytes';
comment on column USER_TAB_COLUMNS.V80_FMT_IMAGE is 'Is column data in 8.0 image format?';
comment on column USER_TAB_COLUMNS.DATA_UPGRADED is 'Has column data been upgraded to the latest type version format?';
