/*
Navicat MySQL Data Transfer

Source Server         : lab3test
Source Server Version : 80019
Source Host           : localhost:3306
Source Database       : lab3

Target Server Type    : MYSQL
Target Server Version : 80019
File Encoding         : 65001

Date: 2020-04-12 09:44:49
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user_meeting_aspcmember
-- ----------------------------
DROP TABLE IF EXISTS `user_meeting_aspcmember`;
CREATE TABLE `user_meeting_aspcmember` (
  `user_id` bigint NOT NULL,
  `meeting_aspcmember_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`meeting_aspcmember_id`),
  KEY `FKhbu3psbhsg00jkneah29beyyc` (`meeting_aspcmember_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of user_meeting_aspcmember
-- ----------------------------
INSERT INTO `user_meeting_aspcmember` VALUES ('1', '14');
INSERT INTO `user_meeting_aspcmember` VALUES ('1', '17');
INSERT INTO `user_meeting_aspcmember` VALUES ('1', '21');
INSERT INTO `user_meeting_aspcmember` VALUES ('2', '15');
INSERT INTO `user_meeting_aspcmember` VALUES ('2', '16');
INSERT INTO `user_meeting_aspcmember` VALUES ('2', '18');
INSERT INTO `user_meeting_aspcmember` VALUES ('3', '16');
INSERT INTO `user_meeting_aspcmember` VALUES ('3', '17');
INSERT INTO `user_meeting_aspcmember` VALUES ('4', '13');
INSERT INTO `user_meeting_aspcmember` VALUES ('4', '14');
INSERT INTO `user_meeting_aspcmember` VALUES ('4', '21');
INSERT INTO `user_meeting_aspcmember` VALUES ('5', '15');
INSERT INTO `user_meeting_aspcmember` VALUES ('5', '19');
INSERT INTO `user_meeting_aspcmember` VALUES ('5', '20');
INSERT INTO `user_meeting_aspcmember` VALUES ('6', '15');
INSERT INTO `user_meeting_aspcmember` VALUES ('6', '17');
INSERT INTO `user_meeting_aspcmember` VALUES ('7', '13');
INSERT INTO `user_meeting_aspcmember` VALUES ('7', '19');
INSERT INTO `user_meeting_aspcmember` VALUES ('8', '18');
INSERT INTO `user_meeting_aspcmember` VALUES ('8', '20');
