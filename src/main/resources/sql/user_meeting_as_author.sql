/*
Navicat MySQL Data Transfer

Source Server         : lab3test
Source Server Version : 80019
Source Host           : localhost:3306
Source Database       : lab3

Target Server Type    : MYSQL
Target Server Version : 80019
File Encoding         : 65001

Date: 2020-04-12 09:44:25
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user_meeting_as_author
-- ----------------------------
DROP TABLE IF EXISTS `user_meeting_as_author`;
CREATE TABLE `user_meeting_as_author` (
  `user_id` bigint NOT NULL,
  `meeting_as_author_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`meeting_as_author_id`),
  KEY `FKdnprqiynbi98b2a5jj9u66spl` (`meeting_as_author_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of user_meeting_as_author
-- ----------------------------
INSERT INTO `user_meeting_as_author` VALUES ('1', '19');
INSERT INTO `user_meeting_as_author` VALUES ('2', '13');
INSERT INTO `user_meeting_as_author` VALUES ('2', '14');
INSERT INTO `user_meeting_as_author` VALUES ('2', '16');
INSERT INTO `user_meeting_as_author` VALUES ('2', '17');
INSERT INTO `user_meeting_as_author` VALUES ('3', '13');
INSERT INTO `user_meeting_as_author` VALUES ('3', '14');
INSERT INTO `user_meeting_as_author` VALUES ('3', '19');
INSERT INTO `user_meeting_as_author` VALUES ('3', '21');
INSERT INTO `user_meeting_as_author` VALUES ('4', '13');
INSERT INTO `user_meeting_as_author` VALUES ('4', '19');
INSERT INTO `user_meeting_as_author` VALUES ('4', '20');
INSERT INTO `user_meeting_as_author` VALUES ('5', '13');
INSERT INTO `user_meeting_as_author` VALUES ('5', '15');
INSERT INTO `user_meeting_as_author` VALUES ('5', '16');
INSERT INTO `user_meeting_as_author` VALUES ('6', '15');
INSERT INTO `user_meeting_as_author` VALUES ('6', '16');
INSERT INTO `user_meeting_as_author` VALUES ('6', '17');
INSERT INTO `user_meeting_as_author` VALUES ('6', '19');
INSERT INTO `user_meeting_as_author` VALUES ('7', '13');
INSERT INTO `user_meeting_as_author` VALUES ('7', '17');
INSERT INTO `user_meeting_as_author` VALUES ('7', '21');
INSERT INTO `user_meeting_as_author` VALUES ('8', '18');
INSERT INTO `user_meeting_as_author` VALUES ('8', '19');
INSERT INTO `user_meeting_as_author` VALUES ('8', '21');
