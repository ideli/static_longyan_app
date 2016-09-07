/*2014-12-10 update*/
ALTER TABLE `xiwa_nvwa_container` ADD COLUMN `publicObject` TINYINT NULL  ;
ALTER TABLE `xiwa_nvwa_container` ADD COLUMN `producer` VARCHAR(64) NULL ;
ALTER TABLE `xiwa_nvwa_container` ADD COLUMN `createDate` DATETIME NULL  ;
ALTER TABLE `xiwa_nvwa_page` ADD COLUMN `publicObject` TINYINT NULL  ;
ALTER TABLE `xiwa_nvwa_page` ADD COLUMN `producer` VARCHAR(64) NULL;
ALTER TABLE `xiwa_nvwa_page` ADD COLUMN `createDate` DATETIME NULL  ;
ALTER TABLE `xiwa_nvwa_component` ADD COLUMN `publicObject` TINYINT NULL  ;
ALTER TABLE `xiwa_nvwa_component` ADD COLUMN `producer` VARCHAR(64) NULL;
ALTER TABLE `xiwa_nvwa_component` ADD COLUMN `createDate` DATETIME NULL  ;
/*2014-12-11 update*/
ALTER TABLE `xiwa_nvwa_page` ADD COLUMN `layouts` VARCHAR(512) NULL ;
/*2014-12-12 update*/
ALTER TABLE `xiwa_nvwa_element` ADD COLUMN `pageId` INT NULL;

/*2014-12-17 update*/
ALTER TABLE `xiwa_nvwa_field` ADD COLUMN `serialNumber` VARCHAR(128) NULL  ;
ALTER TABLE `xiwa_nvwa_element` ADD COLUMN `fieldSerialNumber` VARCHAR(128) NULL  ;
ALTER TABLE `xiwa_nvwa_client_event` ADD COLUMN `arguments` VARCHAR(1024) NULL  ;

