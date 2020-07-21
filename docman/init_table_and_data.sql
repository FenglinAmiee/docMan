/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.28-log : Database - docman
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`docman` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `docman`;

/*Table structure for table `hibernate_sequence` */

DROP TABLE IF EXISTS `hibernate_sequence`;

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `hibernate_sequence` */

insert  into `hibernate_sequence`(`next_val`) values (1),(1);

/*Table structure for table `t_document` */

DROP TABLE IF EXISTS `t_document`;

CREATE TABLE `t_document` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(255) NOT NULL COMMENT '名称',
  `level` int(11) NOT NULL DEFAULT '0' COMMENT '等级',
  `publisher` varchar(255) NOT NULL COMMENT '发布者',
  `affect_date` varchar(20) NOT NULL COMMENT '生效时间',
  `expire_date` varchar(20) DEFAULT NULL COMMENT '失效时间',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '当前状态',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `keyword` varchar(255) NOT NULL COMMENT '关键字',
  `path` varchar(255) NOT NULL COMMENT '保存路径',
  `real_name` varchar(255) NOT NULL COMMENT '源文件名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

/*Data for the table `t_document` */

/*Table structure for table `t_user` */

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `name` varchar(50) DEFAULT NULL COMMENT '姓名',
  `level` int(11) DEFAULT NULL COMMENT '等级',
  `contact` varchar(20) DEFAULT NULL COMMENT '联系方式',
  `department` varchar(50) DEFAULT NULL COMMENT '部门',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `t_user` */

insert  into `t_user`(`id`,`username`,`password`,`name`,`level`,`contact`,`department`,`remark`) values (0,'admin','$2a$10$7SnUHqQmTEqmJURxzelwEehU9d3i6p18.EdSLtalNqBPshXSV7Kue','admin',1,'','','123456');
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
