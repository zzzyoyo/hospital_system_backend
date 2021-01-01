/*
Navicat MySQL Data Transfer

Source Server         : lab3test
Source Server Version : 80019
Source Host           : localhost:3306
Source Database       : lab3

Target Server Type    : MYSQL
Target Server Version : 80019
File Encoding         : 65001

Date: 2020-04-12 09:43:58
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for meetinginline
-- ----------------------------
DROP TABLE IF EXISTS `meetinginline`;
CREATE TABLE `meetinginline` (
  `id` bigint NOT NULL,
  `deadlineInline` varchar(255) DEFAULT NULL,
  `fullnameInline` varchar(255) DEFAULT NULL,
  `organizationTimeInline` varchar(255) DEFAULT NULL,
  `placeInline` varchar(255) DEFAULT NULL,
  `resultReleaseTimeInline` varchar(255) DEFAULT NULL,
  `shortnameInline` varchar(255) DEFAULT NULL,
  `startSubmissionTime` varchar(255) DEFAULT NULL,
  `state` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of meetinginline
-- ----------------------------
