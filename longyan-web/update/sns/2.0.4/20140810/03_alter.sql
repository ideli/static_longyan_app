--4表增加domain字段
alter table `xiwa_crm_app_news` add column domain varchar(64);
alter table `xiwa_sns_knowledge` add column domain varchar(64);
alter table `xiwa_sns_collaboration` add column domain varchar(64);
alter table `xiwa_sns_group` add column domain varchar(64);

ALTER TABLE `xiwa_sns_app_version` ADD COLUMN `upgrade` INT(2) NULL DEFAULT '0' AFTER `title`;