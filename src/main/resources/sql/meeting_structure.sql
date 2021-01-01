/*
Navicat MySQL Data Transfer

Source Server         : lab3test
Source Server Version : 80019
Source Host           : localhost:3306
Source Database       : lab3

Target Server Type    : MYSQL
Target Server Version : 80019
File Encoding         : 65001

Date: 2020-04-12 10:15:27
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for meeting
-- ----------------------------
DROP TABLE IF EXISTS `meeting`;
CREATE TABLE `meeting` (
  `meetingId` bigint NOT NULL,
  `adminId` bigint DEFAULT NULL,
  `chairId` bigint DEFAULT NULL,
  `deadline` varchar(255) DEFAULT NULL,
  `fullname` varchar(255) DEFAULT NULL,
  `meetingState` bit(1) NOT NULL,
  `organizationTime` varchar(255) DEFAULT NULL,
  `place` varchar(255) DEFAULT NULL,
  `resultReleaseTime` varchar(255) DEFAULT NULL,
  `shortname` varchar(255) DEFAULT NULL,
  `startSubmissionTime` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`meetingId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
