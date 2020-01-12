/*
 Navicat Premium Data Transfer

 Source Server         : localhost-docker
 Source Server Type    : MySQL
 Source Server Version : 50723
 Source Host           : localhost:3308
 Source Schema         : test

 Target Server Type    : MySQL
 Target Server Version : 50723
 File Encoding         : 65001

 Date: 12/01/2020 23:10:15
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `age` int(2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of student
-- ----------------------------
BEGIN;
INSERT INTO `student` VALUES (1, '小一', 10);
INSERT INTO `student` VALUES (2, '王二', 13);
INSERT INTO `student` VALUES (3, '张三', 14);
INSERT INTO `student` VALUES (4, '李四', 14);
INSERT INTO `student` VALUES (5, '麻子', 15);
INSERT INTO `student` VALUES (6, '狗蛋', 11);
INSERT INTO `student` VALUES (7, '黑皮', 11);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
