-- MySQL dump 10.13  Distrib 5.7.10, for osx10.9 (x86_64)
--
-- Host: 192.168.2.201    Database: bszk
-- ------------------------------------------------------
-- Server version	5.5.28

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `tb_xa_expertcategory`
--

DROP TABLE IF EXISTS `tb_xa_expertcategory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_xa_expertcategory` (
  `id` varchar(32) NOT NULL COMMENT 'id',
  `create_time` varchar(255) DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(255) DEFAULT NULL COMMENT '创建用户',
  `modify_description` longtext COMMENT '修改描述',
  `modify_time` varchar(255) DEFAULT NULL COMMENT '修改时间',
  `modify_user` varchar(255) DEFAULT NULL COMMENT '修改用户',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态 1-有效 3-逻辑删除',
  `version` int(11) DEFAULT NULL COMMENT 'hibernate控制的版本号',
  `comment` varchar(200) DEFAULT NULL COMMENT '名称',
  `name` varchar(50) NOT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='专家类别表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_xa_expertcategory`
--

LOCK TABLES `tb_xa_expertcategory` WRITE;
/*!40000 ALTER TABLE `tb_xa_expertcategory` DISABLE KEYS */;
INSERT INTO `tb_xa_expertcategory` VALUES ('000000005a365bf1015a368df6080000','2017-02-13 16:19:17','1','初始创建',NULL,'1',1,0,NULL,'金融专家'),('402883c65a07ab66015a07ad06fc0000','2017-02-04 13:51:04','1','初始创建',NULL,'1',1,0,'政府官员','政府官员'),('402883c65a07ab66015a07ad514e0001','2017-02-04 13:51:23','1','初始创建',NULL,'1',1,0,'经济金融','经济金融'),('402883c65a07ab66015a07ad78cc0002','2017-02-04 13:51:33','1','初始创建',NULL,'1',1,0,'商业管理','商业管理'),('402883c65a07ab66015a07ad9ddb0003','2017-02-04 13:51:43','1','初始创建',NULL,'1',1,0,'地产政策','地产政策'),('402883c65a07ab66015a07adbfae0004','2017-02-04 13:51:51','1','初始创建',NULL,'1',1,0,'互联科技','互联科技'),('402883c65a07ab66015a07ade4bb0005','2017-02-04 13:52:01','1','初始创建',NULL,'1',1,0,'文教创新','文教创新'),('402883c65a07ab66015a07ae06f30006','2017-02-04 13:52:09','1','初始创建',NULL,'1',1,0,'养生健康','养生健康'),('402883c65a07ab66015a07ae26750007','2017-02-04 13:52:18','1','初始创建',NULL,'1',1,0,'农业生态','农业生态'),('402883c65a07ab66015a07ae46640008','2017-02-04 13:52:26','1','初始创建',NULL,'1',1,0,'品质生活','品质生活'),('402883c65a07ab66015a07ae70a60009','2017-02-04 13:52:37','1','初始创建',NULL,'1',1,0,'易经风水','易经风水'),('402883c65a07ab66015a07ae94fc000a','2017-02-04 13:52:46','1','初始创建',NULL,'1',1,0,'主持人','主持人'),('402883c65a07ab66015a07aeb6c2000b','2017-02-04 13:52:54','1','初始创建',NULL,'1',1,0,'娱乐明星 ','娱乐明星 ');
/*!40000 ALTER TABLE `tb_xa_expertcategory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_ms_cms_user`
--

DROP TABLE IF EXISTS `tb_ms_cms_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_ms_cms_user` (
  `email` varchar(50) DEFAULT NULL COMMENT '电子邮件',
  `last_login_date` varchar(50) DEFAULT NULL COMMENT '最近登录时间',
  `mobile` varchar(50) DEFAULT NULL COMMENT '手机号',
  `nick_name` varchar(50) DEFAULT NULL COMMENT '昵称',
  `password` varchar(2000) DEFAULT NULL,
  `regist_date` varchar(50) DEFAULT NULL COMMENT '注册日期',
  `remark` longtext COMMENT '备注',
  `role_id` bigint(20) DEFAULT NULL COMMENT '所属角色id',
  `status` int(11) NOT NULL COMMENT '状态',
  `user_name` varchar(50) DEFAULT NULL COMMENT '用户名',
  `user_type` int(11) DEFAULT NULL COMMENT '用户类型',
  `user_id` varchar(32) NOT NULL COMMENT '用户id',
  KEY `FK_enpahekbdxy9rgjliq9utxtlo` (`role_id`),
  CONSTRAINT `FK_enpahekbdxy9rgjliq9utxtlo` FOREIGN KEY (`role_id`) REFERENCES `tb_ms_cms_role` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_ms_cms_user`
--

LOCK TABLES `tb_ms_cms_user` WRITE;
/*!40000 ALTER TABLE `tb_ms_cms_user` DISABLE KEYS */;
INSERT INTO `tb_ms_cms_user` VALUES ('superAdmin@163.com','2017-03-24 10:41:08','13800000000','超级管理员','d0de670062f7abca90790266f763fa8a2ab7215e0c3a3cdeeaf057ee7ac5f4ae1a56279e49398c8ec37ba8f7f87c5219',NULL,'超级管理员用户',1,1,'superadmin',1,'1');
/*!40000 ALTER TABLE `tb_ms_cms_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_ms_cms_role`
--

DROP TABLE IF EXISTS `tb_ms_cms_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_ms_cms_role` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `create_time` varchar(255) DEFAULT NULL COMMENT '创建日期',
  `role_desc` varchar(255) DEFAULT NULL COMMENT '角色描述',
  `role_name` varchar(40) NOT NULL COMMENT '角色名称',
  `status` int(11) DEFAULT NULL COMMENT '状态',
  `update_time` varchar(255) DEFAULT NULL COMMENT '更新时间',
  `user_id` bigint(20) DEFAULT NULL COMMENT '创建用户id',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='角色表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_ms_cms_role`
--

LOCK TABLES `tb_ms_cms_role` WRITE;
/*!40000 ALTER TABLE `tb_ms_cms_role` DISABLE KEYS */;
INSERT INTO `tb_ms_cms_role` VALUES (1,NULL,'系统后台所有功能都可以操作的权限','超级管理员',1,'2017-02-04 13:44:38',NULL),(2,NULL,'普通管理员，能管理一般权限。1','管理员',1,NULL,NULL),(3,'2017-02-07 11:10:52','总监','总监',1,NULL,1),(4,'2017-02-07 11:11:07','员工','员工',1,NULL,1);
/*!40000 ALTER TABLE `tb_ms_cms_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-03-24 11:05:45
