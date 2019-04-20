-----------------------MYSQL---------------------


SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for table_identifiers
-- ----------------------------
DROP TABLE IF EXISTS `table_identifiers`;
CREATE TABLE `table_identifiers` (
  `table_name` varchar(64) NOT NULL,
  `id_length` int(11) DEFAULT NULL,
  `identifier` int(11) DEFAULT NULL,
  `prefix` varchar(10) DEFAULT NULL,
  `last_date` varchar(8) DEFAULT NULL,
  PRIMARY KEY (`table_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of table_identifiers
-- ----------------------------
INSERT INTO `table_identifiers` VALUES ('UserInfo', '4', '1', 'UI', '20150616');