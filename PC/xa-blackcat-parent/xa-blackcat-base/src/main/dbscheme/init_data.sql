CREATE DATABASE  IF NOT EXISTS `mainframe` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `mainframe`;

LOCK TABLES `tb_ms_cms_resource` WRITE;
/*!40000 ALTER TABLE `tb_ms_cms_resource` DISABLE KEYS */;
INSERT INTO `tb_ms_cms_resource` (resource_id,order_num,parent_id,resource_name,resource_url,show_type,STATUS,icon) VALUES (1,100,NULL,'首页','/index.html','0',1,NULL),(2,100,NULL,'后台管理','无路径','2',1,'user_mang'),(3,100,2,'用户管理','/admin/adminList.html','2',1,NULL),(4,100,2,'角色管理','/admin/role.html','2',1,NULL),(5,100,2,'资源管理','/admin/resources.html','2',1,NULL),(6,100,5,'新增资源','../xaCmsResource/*','1',1,NULL),(7,100,5,'编辑资源','../xaCmsResource/update*','1',1,NULL),(8,100,5,'删除资源','../xaCmsResource/resource*','1',1,NULL),(9,100,5,'查询资源','../xaCmsResource/showResource','1',1,NULL),(10,100,2,'版本管理','/version/version.html','2',1,NULL);
/*!40000 ALTER TABLE `tb_ms_cms_resource` ENABLE KEYS */;
UNLOCK TABLES;

LOCK TABLES `tb_ms_cms_role` WRITE;
/*!40000 ALTER TABLE `tb_ms_cms_role` DISABLE KEYS */;
INSERT INTO `tb_ms_cms_role`(role_id,role_desc,role_name,STATUS) VALUES (1,'系统后台所有功能都可以操作的权限','超级管理员',1),(2,'普通管理员，能管理一般权限。1','管理员',1);
/*!40000 ALTER TABLE `tb_ms_cms_role` ENABLE KEYS */;
UNLOCK TABLES;

LOCK TABLES `tb_ms_cms_role_resource` WRITE;
/*!40000 ALTER TABLE `tb_ms_cms_role_resource` DISABLE KEYS */;
INSERT INTO `tb_ms_cms_role_resource` (role_resource_id,resource_id,role_id,VERSION) VALUES (1,3,1,0),(2,4,1,0),(3,6,1,0),(4,7,1,0),(5,8,1,0),(6,9,1,0),(7,5,1,0),(8,2,1,0),(9,1,1,0);
/*!40000 ALTER TABLE `tb_ms_cms_role_resource` ENABLE KEYS */;
UNLOCK TABLES;

LOCK TABLES `tb_ms_cms_user` WRITE;
/*!40000 ALTER TABLE `tb_ms_cms_user` DISABLE KEYS */;
INSERT INTO `tb_ms_cms_user` (user_id,remark,email,mobile,`password`,nick_name,user_type,user_name,role_id,status) VALUES (1,'超级管理员用户','superAdmin@163.com','13800000000','e10adc3949ba59abbe56e057f20f883e','超级管理员',1,'superadmin',1,1);
/*!40000 ALTER TABLE `tb_ms_cms_user` ENABLE KEYS */;
UNLOCK TABLES;