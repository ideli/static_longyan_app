SET SQL_SAFE_UPDATES=0;
UPDATE `xiwa_nvwa_container` SET `publicObject`='1';
UPDATE `xiwa_nvwa_page` SET `publicObject`='1';
UPDATE `xiwa_nvwa_component` SET `publicObject`='1';
/*update 2014-12-17*/
update `xiwa_nvwa_client_event` set `arguments`='{}' ;
SET SQL_SAFE_UPDATES=1;