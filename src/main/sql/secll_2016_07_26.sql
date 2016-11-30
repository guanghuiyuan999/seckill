/*
SQLyog Ultimate v12.08 (64 bit)
MySQL - 5.6.26-log : Database - seckill
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`seckill` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `seckill`;

/*Table structure for table `seckill` */

DROP TABLE IF EXISTS `seckill`;

CREATE TABLE `seckill` (
  `seckill_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品库存id',
  `name` varchar(120) NOT NULL COMMENT '商品名称',
  `number` int(11) NOT NULL COMMENT '库存数量',
  `start_time` timestamp NOT NULL DEFAULT '2016-05-07 13:28:00',
  `end_time` timestamp NOT NULL DEFAULT '2016-05-07 13:28:00',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`seckill_id`),
  KEY `idx_start_time` (`start_time`),
  KEY `idx_end_time` (`end_time`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=21121213 DEFAULT CHARSET=utf8 COMMENT='秒杀库存表';

/*Data for the table `seckill` */

insert  into `seckill`(`seckill_id`,`name`,`number`,`start_time`,`end_time`,`create_time`) values (1000,'1000元秒杀iphone6',98,'2015-11-01 00:00:00','2016-07-03 00:00:00','2016-06-23 17:56:34'),(1001,'500元秒杀ipad2',200,'2015-11-01 00:00:00','2016-07-27 00:00:00','2016-06-23 17:56:34'),(1002,'300元秒杀小米4',300,'2015-11-01 00:00:00','2016-07-27 00:00:00','2016-06-23 17:56:34'),(1003,'200元秒杀红米note',400,'2015-11-01 00:00:00','2016-07-28 00:00:00','2016-06-23 17:56:34');

/*Table structure for table `success_killed` */

DROP TABLE IF EXISTS `success_killed`;

CREATE TABLE `success_killed` (
  `seckill_id` bigint(20) NOT NULL COMMENT '秒杀商品id',
  `user_phone` bigint(20) NOT NULL COMMENT '用户手机号',
  `state` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态标示:-1:无效 0:成功 1:已付款',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`seckill_id`,`user_phone`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='秒杀明细表';

/*Data for the table `success_killed` */

insert  into `success_killed`(`seckill_id`,`user_phone`,`state`,`create_time`) values (1000,18500366587,0,'2016-07-01 16:27:16');

/* Procedure structure for procedure `execute_seckill` */

/*!50003 DROP PROCEDURE IF EXISTS  `execute_seckill` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`o2o-dev`@`%` PROCEDURE `execute_seckill`(in v_seckill_id bigint,in v_phone bigint,
 in v_kill_time timestamp,out r_result int)
BEGIN
 DECLARE insert_count int DEFAULT 0;
 START TRANSACTION;
 insert ignore into success_killed
 (seckill_id,user_phone,create_time)
 values (v_seckill_id,v_phone,v_kill_time);
 select ROW_COUNT() into insert_count;
 IF(insert_count =0)THEN
 ROLLBACK ;
 set r_result =-1;
 ELSEIF(insert_count<0)THEN
 ROLLBACK ;
 SET r_result=-2;
 ELSE
  update seckill
  set number = number-1
  where seckill_id = v_seckill_id
  and end_time > v_kill_time
  and start_time < v_kill_time
  and number >0;
  select ROW_COUNT() into insert_count;
  IF(insert_count = 0)THEN
  ROLLBACK ;
  set r_result =0;
 ELSEIF (insert_count < 0) THEN
   ROLLBACK ;
  set r_result =-2;
  ELSE
   COMMIT;
   set r_result =1;
   END IF;
 END IF;
END */$$
DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
