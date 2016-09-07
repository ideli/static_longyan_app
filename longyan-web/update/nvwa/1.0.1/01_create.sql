use nvwa_base;
CREATE TABLE `xiwa_nvwa_producer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `loginName` varchar(64) DEFAULT NULL,
  `password` varchar(64) DEFAULT NULL,
  `belongedId` int(11) DEFAULT NULL,
  `objectId` int(11) DEFAULT NULL,
  `target` varchar(64) DEFAULT NULL,
  `securityQuestion` VARCHAR(256) DEFAULT NULL,
  `securityAnswer`   VARCHAR(256) DEFAULT NULL,
  `activeCode` varchar(256) DEFAULT NULL,
  `active` tinyint(4) DEFAULT NULL,
  `roles` int(11) DEFAULT NULL,
  `sessionToken` varchar(256) DEFAULT NULL,
  `loginType` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `xiwa_nvwa_app` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(256) DEFAULT NULL,
  `description` Text DEFAULT NULL,
  `indexPageAlias` varchar(256) DEFAULT NULL,
  `alias` varchar(256) DEFAULT NULL,
  `createDate` DATETIME DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `xiwa_nvwa_app_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(256) DEFAULT NULL,
  `description` Text DEFAULT NULL,
  `appAlias` varchar(256) DEFAULT NULL,
  `pageIds` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `xiwa_nvwa_page_authority` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pageAlias` varchar(256) DEFAULT NULL,
  `authType` varchar(256) DEFAULT NULL,
  `authId` INT(11) DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;