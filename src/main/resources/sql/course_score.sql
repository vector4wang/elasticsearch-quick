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

 Date: 12/01/2020 23:10:01
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for course_score
-- ----------------------------
DROP TABLE IF EXISTS `course_score`;
CREATE TABLE `course_score` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `stu_id` int(10) DEFAULT NULL,
  `class_name` varchar(10) DEFAULT NULL,
  `score` double(5,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of course_score
-- ----------------------------
BEGIN;
INSERT INTO `course_score` VALUES (1, 1, '语文', 57.04);
INSERT INTO `course_score` VALUES (2, 1, '数学', 88.99);
INSERT INTO `course_score` VALUES (3, 1, '外语', 73.85);
INSERT INTO `course_score` VALUES (4, 1, '物理', 2.29);
INSERT INTO `course_score` VALUES (5, 1, '化学', 89.90);
INSERT INTO `course_score` VALUES (6, 1, '生物', 42.63);
INSERT INTO `course_score` VALUES (8, 2, '语文', 43.46);
INSERT INTO `course_score` VALUES (9, 2, '数学', 89.41);
INSERT INTO `course_score` VALUES (10, 2, '外语', 16.64);
INSERT INTO `course_score` VALUES (11, 2, '物理', 14.99);
INSERT INTO `course_score` VALUES (12, 2, '化学', 25.04);
INSERT INTO `course_score` VALUES (13, 2, '生物', 80.21);
INSERT INTO `course_score` VALUES (14, 3, '语文', 25.93);
INSERT INTO `course_score` VALUES (15, 3, '数学', 89.03);
INSERT INTO `course_score` VALUES (16, 3, '外语', 67.36);
INSERT INTO `course_score` VALUES (17, 3, '物理', 69.73);
INSERT INTO `course_score` VALUES (18, 3, '化学', 46.54);
INSERT INTO `course_score` VALUES (19, 3, '生物', 23.52);
INSERT INTO `course_score` VALUES (20, 4, '语文', 77.97);
INSERT INTO `course_score` VALUES (21, 4, '数学', 19.31);
INSERT INTO `course_score` VALUES (22, 4, '外语', 62.65);
INSERT INTO `course_score` VALUES (23, 4, '物理', 55.31);
INSERT INTO `course_score` VALUES (24, 4, '化学', 88.58);
INSERT INTO `course_score` VALUES (25, 4, '生物', 76.98);
INSERT INTO `course_score` VALUES (26, 5, '语文', 19.15);
INSERT INTO `course_score` VALUES (27, 5, '数学', 64.81);
INSERT INTO `course_score` VALUES (28, 5, '外语', 66.62);
INSERT INTO `course_score` VALUES (29, 5, '物理', 38.67);
INSERT INTO `course_score` VALUES (30, 5, '化学', 93.48);
INSERT INTO `course_score` VALUES (31, 5, '生物', 51.40);
INSERT INTO `course_score` VALUES (32, 6, '语文', 76.55);
INSERT INTO `course_score` VALUES (33, 6, '数学', 28.56);
INSERT INTO `course_score` VALUES (34, 6, '外语', 13.16);
INSERT INTO `course_score` VALUES (35, 6, '物理', 80.09);
INSERT INTO `course_score` VALUES (36, 6, '化学', 61.00);
INSERT INTO `course_score` VALUES (37, 6, '生物', 64.71);
INSERT INTO `course_score` VALUES (38, 7, '语文', 40.57);
INSERT INTO `course_score` VALUES (39, 7, '数学', 8.72);
INSERT INTO `course_score` VALUES (40, 7, '外语', 21.88);
INSERT INTO `course_score` VALUES (41, 7, '物理', 83.25);
INSERT INTO `course_score` VALUES (42, 7, '化学', 50.61);
INSERT INTO `course_score` VALUES (43, 7, '生物', 3.28);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
