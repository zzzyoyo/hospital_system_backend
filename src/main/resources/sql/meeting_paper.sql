/*
Navicat MySQL Data Transfer

Source Server         : lab3test
Source Server Version : 80019
Source Host           : localhost:3306
Source Database       : lab3

Target Server Type    : MYSQL
Target Server Version : 80019
File Encoding         : 65001

Date: 2020-04-13 19:49:49
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for meeting_paper
-- ----------------------------
DROP TABLE IF EXISTS `meeting_paper`;
CREATE TABLE `meeting_paper` (
  `meeting_id` bigint NOT NULL,
  `paper_id` bigint NOT NULL,
  PRIMARY KEY (`meeting_id`,`paper_id`),
  KEY `FKonuhpwy0k37rpbyo3td0yn05a` (`paper_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of meeting_paper
-- ----------------------------
INSERT INTO `meeting_paper` VALUES ('13', '29');
INSERT INTO `meeting_paper` VALUES ('15', '32');
INSERT INTO `meeting_paper` VALUES ('19', '31');
INSERT INTO `meeting_paper` VALUES ('19', '33');
INSERT INTO `meeting_paper` VALUES ('19', '34');
INSERT INTO `meeting_paper` VALUES ('21', '30');
