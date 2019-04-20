CREATE TABLE table_identifiers (
  table_name varchar2(64) NOT NULL,
  id_length number(11) DEFAULT NULL,
  identifier number(11) DEFAULT NULL,
  prefix varchar2(10) DEFAULT NULL,
  last_date varchar2(8) DEFAULT NULL,
  PRIMARY KEY (table_name)
) ;

INSERT INTO table_identifiers VALUES ('UserInfo', '4', '1', 'UI', '20150616');