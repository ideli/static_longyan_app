--2014.06.24  本地search_index表
CREATE TABLE `xiwa_sns_employee_search_index` (
  `id` INTEGER(11) NOT NULL AUTO_INCREMENT,
  `belongedId` INTEGER(11) DEFAULT NULL,
  `createDate` DATETIME NOT NULL,
  `updateDate` DATETIME DEFAULT NULL,
  `employeeId` INTEGER(11) DEFAULT NULL,
  `xingMing` VARCHAR(256)  DEFAULT NULL,
  `departmentId` INTEGER(11) DEFAULT NULL,
  `searchIndex` TEXT,
  `indexOrder` INTEGER(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
