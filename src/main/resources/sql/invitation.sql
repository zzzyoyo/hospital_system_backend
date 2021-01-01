/*
Navicat MySQL Data Transfer

Source Server         : lab3test
Source Server Version : 80019
Source Host           : localhost:3306
Source Database       : lab3

Target Server Type    : MYSQL
Target Server Version : 80019
File Encoding         : 65001

Date: 2020-04-12 09:43:27
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for invitation
-- ----------------------------
DROP TABLE IF EXISTS `invitation`;
CREATE TABLE `invitation` (
  `invitationId` bigint NOT NULL,
  `PCmemberUsername` varchar(255) DEFAULT NULL,
  `chairUsername` varchar(255) DEFAULT NULL,
  `meetingShortname` varchar(255) DEFAULT NULL,
  `state` int NOT NULL,
  PRIMARY KEY (`invitationId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of invitation
-- ----------------------------
INSERT INTO `invitation` VALUES ('22', 'John', 'Mary', 'AAAI', '0');
INSERT INTO `invitation` VALUES ('23', 'Coco', 'Mary', 'AAAI', '0');
INSERT INTO `invitation` VALUES ('24', 'Jack', 'Mary', 'AAAI', '1');
