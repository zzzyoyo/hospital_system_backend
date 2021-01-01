/*
Navicat MySQL Data Transfer

Source Server         : lab3test
Source Server Version : 80019
Source Host           : localhost:3306
Source Database       : lab3

Target Server Type    : MYSQL
Target Server Version : 80019
File Encoding         : 65001

Date: 2020-04-12 09:44:39
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user_meeting_as_chair
-- ----------------------------
DROP TABLE IF EXISTS `user_meeting_as_chair`;
CREATE TABLE `user_meeting_as_chair` (
  `user_id` bigint NOT NULL,
  `meeting_as_chair_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`meeting_as_chair_id`),
  KEY `FKpvab7fv0gdkcecj6jr9c2frox` (`meeting_as_chair_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of user_meeting_as_chair
-- ----------------------------
INSERT INTO `user_meeting_as_chair` VALUES ('1', '13');
INSERT INTO `user_meeting_as_chair` VALUES ('3', '14');
INSERT INTO `user_meeting_as_chair` VALUES ('3', '15');
INSERT INTO `user_meeting_as_chair` VALUES ('4', '16');
INSERT INTO `user_meeting_as_chair` VALUES ('4', '17');
INSERT INTO `user_meeting_as_chair` VALUES ('5', '18');
INSERT INTO `user_meeting_as_chair` VALUES ('5', '21');
INSERT INTO `user_meeting_as_chair` VALUES ('6', '19');
INSERT INTO `user_meeting_as_chair` VALUES ('7', '20');
