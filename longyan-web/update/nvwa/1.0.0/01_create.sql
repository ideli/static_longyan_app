-- MySQL dump 10.13  Distrib 5.5.38, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: nvwa_base
-- ------------------------------------------------------
-- Server version	5.5.38-0ubuntu0.12.04.1
USE nvwa_base;
/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE = @@TIME_ZONE */;
/*!40103 SET TIME_ZONE = '+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES = @@SQL_NOTES, SQL_NOTES = 0 */;

--
-- Table structure for table `xiwa_nvwa_api_config`
--

DROP TABLE IF EXISTS `xiwa_nvwa_api_config`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `xiwa_nvwa_api_config` (
  `id`          INT(11) NOT NULL AUTO_INCREMENT
  COMMENT '自增长ID',
  `name`        VARCHAR(64)      DEFAULT NULL
  COMMENT 'API名称',
  `description` VARCHAR(256)     DEFAULT NULL
  COMMENT 'API描述',
  `appName`     VARCHAR(64)      DEFAULT NULL
  COMMENT 'app名称',
  `appType`     VARCHAR(64)      DEFAULT NULL
  COMMENT 'app类型',
  `alias`       VARCHAR(64)      DEFAULT NULL
  COMMENT '别名',
  `token`       VARCHAR(256)     DEFAULT NULL
  COMMENT '认证令牌',
  `createDate`  DATETIME         DEFAULT NULL
  COMMENT '创建时间',
  `updateDate`  DATETIME         DEFAULT NULL
  COMMENT '更新时间',
  PRIMARY KEY (`id`)
)
  ENGINE =InnoDB
  DEFAULT CHARSET =utf8
  COMMENT ='NVWA API配置';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `xiwa_nvwa_api_resources`
--

DROP TABLE IF EXISTS `xiwa_nvwa_api_resources`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `xiwa_nvwa_api_resources` (
  `id`             INT(11) NOT NULL AUTO_INCREMENT
  COMMENT '自增长ID',
  `apiAlias`       VARCHAR(64)      DEFAULT NULL
  COMMENT 'api别名',
  `containerAlias` VARCHAR(64)      DEFAULT NULL
  COMMENT 'container别名',
  PRIMARY KEY (`id`)
)
  ENGINE =InnoDB
  DEFAULT CHARSET =utf8
  COMMENT ='NVWA API资源';
/*!40101 SET character_set_client = @saved_cs_client */;


DROP TABLE IF EXISTS `xiwa_nvwa_client_event`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `xiwa_nvwa_client_event` (
  `id`              INT(11) NOT NULL AUTO_INCREMENT
  COMMENT '自增长ID',
  `targetId`        INT(11)          DEFAULT NULL
  COMMENT '目标id',
  `target`          VARCHAR(16)      DEFAULT NULL
  COMMENT '目标类型',
  `eventName`       VARCHAR(255)     DEFAULT NULL
  COMMENT '事件名称',
  `alias`           VARCHAR(64)      DEFAULT NULL
  COMMENT '别名',
  `dependence`      VARCHAR(64)      DEFAULT NULL
  COMMENT '依赖类型',
  `dependenceAlias` VARCHAR(64)      DEFAULT NULL
  COMMENT '依赖对象的别名',
  PRIMARY KEY (`id`)
)
  ENGINE =InnoDB
  DEFAULT CHARSET =utf8
  COMMENT ='NVWA客户端事件';
/*!40101 SET character_set_client = @saved_cs_client */;

DROP TABLE IF EXISTS `xiwa_nvwa_client_reserved_event`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `xiwa_nvwa_client_reserved_event` (
  `id`     INT(11) NOT NULL AUTO_INCREMENT
  COMMENT '自增长ID',
  `name`   VARCHAR(64)      DEFAULT NULL
  COMMENT '名称',
  `alias`  VARCHAR(128)     DEFAULT NULL
  COMMENT '别名',
  `target` VARCHAR(32)      DEFAULT NULL
  COMMENT '目标类型',
  PRIMARY KEY (`id`)
)
  ENGINE =InnoDB
  DEFAULT CHARSET =utf8
  COMMENT ='NVWA 客户端保留事件';
/*!40101 SET character_set_client = @saved_cs_client */;

DROP TABLE IF EXISTS `xiwa_nvwa_component`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `xiwa_nvwa_component` (
  `id`    INT(11) NOT NULL AUTO_INCREMENT
  COMMENT '自增长ID',
  `name`  VARCHAR(255)     DEFAULT NULL
  COMMENT '名称',
  `alias` VARCHAR(255)     DEFAULT NULL
  COMMENT '别名',
  `type`  VARCHAR(32)      DEFAULT NULL
  COMMENT '类型',
  `icon`  VARCHAR(32)      DEFAULT NULL
  COMMENT '图标',
  PRIMARY KEY (`id`)
)
  ENGINE =InnoDB
  DEFAULT CHARSET =utf8
  COMMENT ='NVWA 组件';
/*!40101 SET character_set_client = @saved_cs_client */;

DROP TABLE IF EXISTS `xiwa_nvwa_condition`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `xiwa_nvwa_condition` (
  `id`                      INT(11) NOT NULL AUTO_INCREMENT,
  `containerId`             INT(11)          DEFAULT NULL
  COMMENT '自增长ID',
  `conditionOI`             VARCHAR(64)      DEFAULT NULL
  COMMENT '所属OI',
  `conditionGroup`          VARCHAR(64)      DEFAULT NULL
  COMMENT '所属group',
  `conditionGroupType`      VARCHAR(8)       DEFAULT NULL
  COMMENT '所属group类型',
  `conditionFieldName`      VARCHAR(64)      DEFAULT NULL
  COMMENT '字段名称',
  `conditionFieldValueType` VARCHAR(64)      DEFAULT NULL
  COMMENT '字段类型',
  `conditionFieldValue`     VARCHAR(64)      DEFAULT NULL
  COMMENT '字段的值',
  `conditionType`           VARCHAR(8)       DEFAULT NULL
  COMMENT '条件类型',
  `serialNumber`            VARCHAR(64)      DEFAULT NULL
  COMMENT '序列号',
  `connectorPath`           VARCHAR(512)     DEFAULT NULL
  COMMENT '连接器路径',
  PRIMARY KEY (`id`)
)
  ENGINE =InnoDB
  DEFAULT CHARSET =utf8
  COMMENT ='NVWA 检索条件';
/*!40101 SET character_set_client = @saved_cs_client */;

DROP TABLE IF EXISTS `xiwa_nvwa_connector`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `xiwa_nvwa_connector` (
  `id`          INT(11) NOT NULL AUTO_INCREMENT
  COMMENT '自增长ID',
  `name`        VARCHAR(128)     DEFAULT NULL
  COMMENT '名称',
  `description` VARCHAR(255)     DEFAULT NULL
  COMMENT '描述',
  `fromOI`      VARCHAR(128)     DEFAULT NULL
  COMMENT '源OI',
  `fromField`   VARCHAR(128)     DEFAULT NULL
  COMMENT '源字段',
  `toOI`        VARCHAR(128)     DEFAULT NULL
  COMMENT '目标OI',
  `toField`     VARCHAR(128)     DEFAULT NULL
  COMMENT '目标字段',
  `relation`    VARCHAR(16)      DEFAULT NULL
  COMMENT '关系类型',
  `alias`       VARCHAR(128)     DEFAULT NULL
  COMMENT '别名',
  PRIMARY KEY (`id`)
)
  ENGINE =InnoDB
  DEFAULT CHARSET =utf8
  COMMENT ='NVWA 连接器';
/*!40101 SET character_set_client = @saved_cs_client */;

DROP TABLE IF EXISTS `xiwa_nvwa_container`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `xiwa_nvwa_container` (
  `id`    INT(11) NOT NULL AUTO_INCREMENT
  COMMENT '自增长ID',
  `name`  VARCHAR(64)      DEFAULT NULL
  COMMENT '名称',
  `alias` VARCHAR(64)      DEFAULT NULL
  COMMENT '别名',
  `oi`    VARCHAR(64)      DEFAULT NULL
  COMMENT 'OI',
  `type`  VARCHAR(16)      DEFAULT NULL
  COMMENT '类型',
  PRIMARY KEY (`id`)
)
  ENGINE =InnoDB
  DEFAULT CHARSET =utf8
  COMMENT ='NVWA 容器';
/*!40101 SET character_set_client = @saved_cs_client */;

DROP TABLE IF EXISTS `xiwa_nvwa_container_client_attribute`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `xiwa_nvwa_container_client_attribute` (
  `id`              INT(11) NOT NULL AUTO_INCREMENT
  COMMENT '自增长ID',
  `containerId`     INT(11)          DEFAULT NULL
  COMMENT 'container的id',
  `width`           VARCHAR(64)      DEFAULT NULL
  COMMENT '宽度',
  `height`          VARCHAR(64)      DEFAULT NULL
  COMMENT '高度',
  `backgroundImage` VARCHAR(1024)    DEFAULT NULL
  COMMENT '背景图',
  `title`           VARCHAR(255)     DEFAULT NULL
  COMMENT '标题',
  `style`           VARCHAR(255)     DEFAULT NULL
  COMMENT '样式',
  PRIMARY KEY (`id`)
)
  ENGINE =InnoDB
  DEFAULT CHARSET =utf8
  COMMENT ='NVWA 容器客户端属性';
/*!40101 SET character_set_client = @saved_cs_client */;


DROP TABLE IF EXISTS `xiwa_nvwa_container_layout`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `xiwa_nvwa_container_layout` (
  `id`             INT(11) NOT NULL AUTO_INCREMENT
  COMMENT '自增长ID',
  `pageId`         INT(11)          DEFAULT NULL
  COMMENT 'page的ID',
  `containerId`    INT(11)          DEFAULT NULL
  COMMENT 'container的ID',
  `coordinateType` VARCHAR(64)      DEFAULT NULL
  COMMENT '布局类型',
  `x`              VARCHAR(45)      DEFAULT '0'
  COMMENT 'x坐标',
  `y`              VARCHAR(45)      DEFAULT '0'
  COMMENT 'y坐标',
  `z`              VARCHAR(45)      DEFAULT '0'
  COMMENT 'z坐标',
  PRIMARY KEY (`id`)
)
  ENGINE =InnoDB
  DEFAULT CHARSET =utf8
  COMMENT ='NVWA 容器布局';
/*!40101 SET character_set_client = @saved_cs_client */;

DROP TABLE IF EXISTS `xiwa_nvwa_container_server_attribute`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `xiwa_nvwa_container_server_attribute` (
  `id`                 INT(11) NOT NULL AUTO_INCREMENT
  COMMENT '自增长ID',
  `containerId`        INT(11)          DEFAULT NULL
  COMMENT 'container的id',
  `pageSize`           INT(11)          DEFAULT NULL
  COMMENT '分页尺寸',
  `gridType`           VARCHAR(16)      DEFAULT NULL
  COMMENT 'grid类型',
  `orderBy`            VARCHAR(128)     DEFAULT NULL
  COMMENT '排序字段',
  `groupBy`            VARCHAR(128)     DEFAULT NULL
  COMMENT '分组字段',
  `conditionMode`      VARCHAR(32)      DEFAULT NULL
  COMMENT '条件类型',
  `customConditionSQL` VARCHAR(512)     DEFAULT NULL
  COMMENT '自定义sql',
  PRIMARY KEY (`id`)
)
  ENGINE =InnoDB
  DEFAULT CHARSET =utf8
  COMMENT ='NVWA 容器服务器属性';
/*!40101 SET character_set_client = @saved_cs_client */;

DROP TABLE IF EXISTS `xiwa_nvwa_custom_script`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `xiwa_nvwa_custom_script` (
  `id`          INT(11) NOT NULL
  COMMENT '自增长ID',
  `name`        VARCHAR(64)  DEFAULT NULL
  COMMENT '名称',
  `description` VARCHAR(256) DEFAULT NULL
  COMMENT '描述',
  `alias`       VARCHAR(64)  DEFAULT NULL
  COMMENT '别名',
  `code`        TEXT COMMENT '代码',
  `type`        VARCHAR(32)  DEFAULT NULL
  COMMENT '类型',
  PRIMARY KEY (`id`)
)
  ENGINE =InnoDB
  DEFAULT CHARSET =utf8
  COMMENT ='NVWA 自定义脚本';
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `xiwa_nvwa_editor`
--

DROP TABLE IF EXISTS `xiwa_nvwa_editor`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `xiwa_nvwa_editor` (
  `id`    INT(11) NOT NULL AUTO_INCREMENT
  COMMENT '自增长ID',
  `name`  VARCHAR(45)      DEFAULT NULL
  COMMENT '名称',
  `alias` VARCHAR(45)      DEFAULT NULL
  COMMENT '别名',
  PRIMARY KEY (`id`)
)
  ENGINE =InnoDB
  DEFAULT CHARSET =utf8
  COMMENT ='NVWA 编辑器';
/*!40101 SET character_set_client = @saved_cs_client */;

DROP TABLE IF EXISTS `xiwa_nvwa_element`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `xiwa_nvwa_element` (
  `id`             INT(11) NOT NULL AUTO_INCREMENT
  COMMENT '自增长ID',
  `containerId`    INT(11)          DEFAULT NULL
  COMMENT 'container的id',
  `name`           VARCHAR(45)      DEFAULT NULL
  COMMENT '名称',
  `componentAlias` VARCHAR(45)      DEFAULT NULL
  COMMENT '组件的别名',
  `serialNumber`   VARCHAR(128)     DEFAULT NULL
  COMMENT '序列号',
  `icon`           VARCHAR(32)      DEFAULT NULL
  COMMENT '图标',
  PRIMARY KEY (`id`)
)
  ENGINE =InnoDB
  DEFAULT CHARSET =utf8
  COMMENT ='NVWA 元素';
/*!40101 SET character_set_client = @saved_cs_client */;

DROP TABLE IF EXISTS `xiwa_nvwa_element_client_attribute`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `xiwa_nvwa_element_client_attribute` (
  `id`               INT(11) NOT NULL AUTO_INCREMENT
  COMMENT '自增长ID',
  `elementId`        INT(11)          DEFAULT NULL
  COMMENT '元素的id',
  `columnWidth`      INT(11)          DEFAULT NULL
  COMMENT '元素的宽度',
  `text`             VARCHAR(256)     DEFAULT NULL
  COMMENT '文本',
  `glyphicon`        VARCHAR(64)      DEFAULT NULL
  COMMENT '图标样式',
  `size`             VARCHAR(32)      DEFAULT NULL
  COMMENT '大小',
  `feedback`         VARCHAR(64)      DEFAULT NULL
  COMMENT 'feedback属性',
  `defaultValue`     VARCHAR(64)      DEFAULT NULL
  COMMENT '默认值',
  `reverse`          TINYINT(4)       DEFAULT NULL
  COMMENT '支持反转',
  `title`            VARCHAR(255)     DEFAULT NULL
  COMMENT '标题',
  `helpLabel`        VARCHAR(255)     DEFAULT NULL
  COMMENT '帮助文档',
  `readonly`         TINYINT(4)       DEFAULT NULL
  COMMENT '只读属性',
  `disabled`         TINYINT(4)       DEFAULT NULL
  COMMENT '禁用属性',
  `src`              VARCHAR(255)     DEFAULT NULL
  COMMENT '图片的源',
  `alt`              VARCHAR(255)     DEFAULT NULL
  COMMENT '图片alt属性',
  `width`            INT(11)          DEFAULT NULL
  COMMENT '宽度',
  `height`           INT(11)          DEFAULT NULL
  COMMENT '高度',
  `color`            VARCHAR(255)     DEFAULT NULL
  COMMENT '颜色',
  `bgColor`          VARCHAR(255)     DEFAULT NULL
  COMMENT '背景颜色',
  `fontSize`         VARCHAR(255)     DEFAULT NULL
  COMMENT '字体的大小',
  `font`             VARCHAR(255)     DEFAULT NULL
  COMMENT '字体',
  `fontStyle`        VARCHAR(255)     DEFAULT NULL
  COMMENT '字体样式',
  `fontWeight`       VARCHAR(255)     DEFAULT NULL
  COMMENT '字体粗细',
  `link`             VARCHAR(255)     DEFAULT NULL
  COMMENT '链接',
  `prefix`           VARCHAR(255)     DEFAULT NULL
  COMMENT '前缀',
  `dataOffTitle`     VARCHAR(255)     DEFAULT NULL
  COMMENT 'off标签的标题',
  `dataOnTitle`      VARCHAR(255)     DEFAULT NULL
  COMMENT 'on标签的标题',
  `dataOffIconClass` VARCHAR(255)     DEFAULT NULL
  COMMENT 'off标签的图标',
  `dataOnIconClass`  VARCHAR(255)     DEFAULT NULL
  COMMENT 'on标签的图标',
  `dataOfflabel`     VARCHAR(255)     DEFAULT NULL
  COMMENT 'off标签的label',
  `dataOnlabel`      VARCHAR(255)     DEFAULT NULL
  COMMENT 'on标签的label',
  `dataOffColor`     VARCHAR(255)     DEFAULT NULL
  COMMENT 'off标签的颜色',
  `dataOnColor`      VARCHAR(255)     DEFAULT NULL
  COMMENT 'on标签的颜色',
  `selectData`       TEXT COMMENT 'select标签的数据',
  PRIMARY KEY (`id`)
)
  ENGINE =InnoDB
  DEFAULT CHARSET =utf8
  COMMENT ='NVWA 元素客户端属性';
/*!40101 SET character_set_client = @saved_cs_client */;

DROP TABLE IF EXISTS `xiwa_nvwa_element_layout`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `xiwa_nvwa_element_layout` (
  `id`             INT(11) NOT NULL AUTO_INCREMENT
  COMMENT '自增长ID',
  `containerAlias` VARCHAR(256)     DEFAULT NULL
  COMMENT 'container别名',
  `layouts`        TEXT COMMENT '布局',
  PRIMARY KEY (`id`)
)
  ENGINE =InnoDB
  DEFAULT CHARSET =utf8
  COMMENT ='NVWA 元素布局';
/*!40101 SET character_set_client = @saved_cs_client */;

DROP TABLE IF EXISTS `xiwa_nvwa_element_server_attribute`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `xiwa_nvwa_element_server_attribute` (
  `id`            INT(11) NOT NULL AUTO_INCREMENT
  COMMENT '自增长ID',
  `elementId`     INT(11)          DEFAULT NULL
  COMMENT '元素的id',
  `fieldName`     VARCHAR(255)     DEFAULT NULL
  COMMENT '字段名称',
  `fieldValue`    VARCHAR(255)     DEFAULT NULL
  COMMENT '字段的值',
  `connectorPath` VARCHAR(512)     DEFAULT NULL
  COMMENT '连接器路径',
  PRIMARY KEY (`id`)
)
  ENGINE =InnoDB
  DEFAULT CHARSET =utf8
  COMMENT ='NVWA 元素服务器属性';
/*!40101 SET character_set_client = @saved_cs_client */;

DROP TABLE IF EXISTS `xiwa_nvwa_field`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `xiwa_nvwa_field` (
  `id`            INT(11) NOT NULL   AUTO_INCREMENT
  COMMENT '自增长ID',
  `name`          VARCHAR(128)       DEFAULT NULL
  COMMENT '名称',
  `description`   VARCHAR(255)       DEFAULT NULL
  COMMENT '描述',
  `fieldName`     VARCHAR(128)       DEFAULT NULL
  COMMENT '字段名称',
  `defaultValue`  VARCHAR(255)       DEFAULT NULL
  COMMENT '默认值',
  `notNull`       TINYINT(4)         DEFAULT NULL
  COMMENT '是否非空',
  `oiIdentified`  VARCHAR(128)
                  CHARACTER SET utf8 DEFAULT NULL
  COMMENT 'OI',
  `dataTypeField` VARCHAR(128)
                  CHARACTER SET utf8 DEFAULT NULL
  COMMENT '字段类型',
  `length`        INT(11)            DEFAULT NULL
  COMMENT '长度',
  PRIMARY KEY (`id`)
)
  ENGINE =InnoDB
  DEFAULT CHARSET =utf8
  COMMENT ='NVWA 字段';
/*!40101 SET character_set_client = @saved_cs_client */;

DROP TABLE IF EXISTS `xiwa_nvwa_icon`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `xiwa_nvwa_icon` (
  `id`    INT(11) NOT NULL AUTO_INCREMENT
  COMMENT '自增长ID',
  `name`  VARCHAR(32)      DEFAULT NULL
  COMMENT '名称',
  `alias` VARCHAR(32)      DEFAULT NULL
  COMMENT '别名',
  PRIMARY KEY (`id`)
)
  ENGINE =InnoDB
  DEFAULT CHARSET =utf8
  COMMENT ='NVWA 图标';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `xiwa_nvwa_log`
--

DROP TABLE IF EXISTS `xiwa_nvwa_log`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `xiwa_nvwa_log` (
  `id`             INT(11) NOT NULL AUTO_INCREMENT
  COMMENT '自增长ID',
  `logId`          INT(11)          DEFAULT NULL
  COMMENT '目标的id',
  `logIdentified`  VARCHAR(128)     DEFAULT NULL
  COMMENT '目标类型',
  `createDate`     DATETIME         DEFAULT NULL
  COMMENT '创建时间',
  `createXingMing` VARCHAR(128)     DEFAULT NULL
  COMMENT '创建人',
  `content`        VARCHAR(255)     DEFAULT NULL
  COMMENT '内容',
  `type`           VARCHAR(32)      DEFAULT NULL
  COMMENT '日志类型',
  `fieldName`      VARCHAR(128)     DEFAULT NULL
  COMMENT '字段名称',
  `orignValue`     VARCHAR(255)     DEFAULT NULL
  COMMENT '字段原始值',
  `newValue`       VARCHAR(255)     DEFAULT NULL
  COMMENT '字段新值',
  PRIMARY KEY (`id`)
)
  ENGINE =InnoDB
  DEFAULT CHARSET =utf8
  COMMENT ='NVWA 日志';
/*!40101 SET character_set_client = @saved_cs_client */;

DROP TABLE IF EXISTS `xiwa_nvwa_oi`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `xiwa_nvwa_oi` (
  `id`          INT(11) NOT NULL   AUTO_INCREMENT
  COMMENT '自增长ID',
  `name`        VARCHAR(128)       DEFAULT NULL
  COMMENT '名称',
  `description` VARCHAR(255)
                CHARACTER SET utf8 DEFAULT NULL
  COMMENT '描述',
  `identified`  VARCHAR(128)       DEFAULT NULL
  COMMENT '别名',
  `tableName`   VARCHAR(128)       DEFAULT NULL
  COMMENT '数据库表名',
  `status`      VARCHAR(32)        DEFAULT NULL
  COMMENT '状态',
  `createDate`  DATETIME           DEFAULT NULL
  COMMENT '创建时间',
  PRIMARY KEY (`id`)
)
  ENGINE =InnoDB
  DEFAULT CHARSET =utf8
  COMMENT ='NVWA OI';
/*!40101 SET character_set_client = @saved_cs_client */;

DROP TABLE IF EXISTS `xiwa_nvwa_page`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `xiwa_nvwa_page` (
  `id`         INT(11) NOT NULL AUTO_INCREMENT
  COMMENT '自增长ID',
  `name`       VARCHAR(64)      DEFAULT NULL
  COMMENT '名称',
  `alias`      VARCHAR(64)      DEFAULT NULL
  COMMENT '别名',
  `type`       VARCHAR(16)      DEFAULT NULL
  COMMENT '类型',
  `layoutType` VARCHAR(64)      DEFAULT NULL
  COMMENT '布局类型',
  PRIMARY KEY (`id`)
)
  ENGINE =InnoDB
  DEFAULT CHARSET =utf8
  COMMENT ='NVWA 页面';
/*!40101 SET character_set_client = @saved_cs_client */;

DROP TABLE IF EXISTS `xiwa_nvwa_page_client_attribute`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `xiwa_nvwa_page_client_attribute` (
  `id`     INT(11) NOT NULL AUTO_INCREMENT
  COMMENT '自增长ID',
  `pageId` INT(11) NOT NULL
  COMMENT 'page的ID',
  PRIMARY KEY (`id`)
)
  ENGINE =InnoDB
  DEFAULT CHARSET =utf8
  COMMENT ='NVWA 页面客户端属性';
/*!40101 SET character_set_client = @saved_cs_client */;

DROP TABLE IF EXISTS `xiwa_nvwa_page_server_attribute`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `xiwa_nvwa_page_server_attribute` (
  `id`     INT(11) NOT NULL AUTO_INCREMENT
  COMMENT '自增长ID',
  `pageId` INT(11) NOT NULL
  COMMENT 'page的ID',
  PRIMARY KEY (`id`)
)
  ENGINE =InnoDB
  DEFAULT CHARSET =utf8
  COMMENT ='NVWA 页面服务器属性';
/*!40101 SET character_set_client = @saved_cs_client */;

DROP TABLE IF EXISTS `xiwa_nvwa_reserved_field`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `xiwa_nvwa_reserved_field` (
  `id`          INT(11) NOT NULL AUTO_INCREMENT
  COMMENT '自增长ID',
  `name`        VARCHAR(64)      DEFAULT NULL
  COMMENT '名称',
  `fieldName`   VARCHAR(255)     DEFAULT NULL
  COMMENT '字段名称',
  `description` VARCHAR(255)     DEFAULT NULL
  COMMENT '描述',
  `className`   VARCHAR(255)     DEFAULT NULL
  COMMENT '类名',
  `attributes`  VARCHAR(512)     DEFAULT NULL
  COMMENT '属性',
  PRIMARY KEY (`id`)
)
  ENGINE =InnoDB
  DEFAULT CHARSET =utf8
  COMMENT ='NVWA 保留字段';
/*!40101 SET character_set_client = @saved_cs_client */;

DROP TABLE IF EXISTS `xiwa_nvwa_server_event`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `xiwa_nvwa_server_event` (
  `id`        INT(11) NOT NULL AUTO_INCREMENT
  COMMENT '自增长ID',
  `targetId`  INT(11)          DEFAULT NULL
  COMMENT '目标ID',
  `target`    VARCHAR(16)      DEFAULT NULL
  COMMENT '目标类型',
  `eventName` VARCHAR(255)     DEFAULT NULL
  COMMENT '事件名称',
  `alias`     VARCHAR(64)      DEFAULT NULL
  COMMENT '别名',
  PRIMARY KEY (`id`)
)
  ENGINE =InnoDB
  DEFAULT CHARSET =utf8
  COMMENT ='NVWA 服务器事件';
/*!40101 SET character_set_client = @saved_cs_client */;

DROP TABLE IF EXISTS `xiwa_nvwa_server_reserved_event`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `xiwa_nvwa_server_reserved_event` (
  `id`     INT(11) NOT NULL AUTO_INCREMENT
  COMMENT '自增长ID',
  `name`   VARCHAR(64)      DEFAULT NULL
  COMMENT '名称',
  `alias`  VARCHAR(128)     DEFAULT NULL
  COMMENT '别名',
  `target` VARCHAR(32)      DEFAULT NULL
  COMMENT '目标类型',
  PRIMARY KEY (`id`)
)
  ENGINE =InnoDB
  DEFAULT CHARSET =utf8
  COMMENT ='NVWA 服务器保留事件';

/*!40101 SET character_set_client = @saved_cs_client */;

/*!40103 SET TIME_ZONE = @OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE = @OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES = @OLD_SQL_NOTES */;

-- Dump completed on 2014-12-01 10:06:54