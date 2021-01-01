/*
Navicat MySQL Data Transfer

Source Server         : lab3test
Source Server Version : 80019
Source Host           : localhost:3306
Source Database       : lab3

Target Server Type    : MYSQL
Target Server Version : 80019
File Encoding         : 65001

Date: 2020-04-12 09:43:46
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
  `organizationTime` varchar(255) DEFAULT NULL,
  `place` varchar(255) DEFAULT NULL,
  `resultReleaseTime` varchar(255) DEFAULT NULL,
  `shortname` varchar(255) DEFAULT NULL,
  `startSubmissionTime` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`meetingId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of meeting
-- ----------------------------
INSERT INTO `meeting` VALUES ('13', '9', '1', '2020/5/4', 'National Conference of the American Association for Artificial Intelligence', '2020/4/11', 'NewYork', '2020/5/20', 'AAAI', '2019/10/1');
INSERT INTO `meeting` VALUES ('14', '9', '3', '2020/6/30', 'The World Economic Forum', '2020/1/9', 'Geneva', 'WEF', '2020/4/3', '2020/1/25');
INSERT INTO `meeting` VALUES ('15', '10', '3', '2020/5/4', 'International Conference on Autonomous Agents and Multiagent Systems', '2020/4/11', 'NewYork', '2020/5/20', 'AAAMAS', '2019/10/1');
INSERT INTO `meeting` VALUES ('16', '10', '4', '2020/5/30', 'Association of Computational Linguistics', '2020/2/9', 'London', 'ACL', '2020/6/10', '2020/1/25');
INSERT INTO `meeting` VALUES ('17', '12', '4', '2020/10/8', 'Advances in Cryptology', '2020/10/11', 'NewYork', '2020/9/8', 'CRYPTO', '2019/3/20');
INSERT INTO `meeting` VALUES ('18', '11', '5', '2020/6/30', 'International Conference on Dependable Systems', '2020/7/9', 'Paris', '2020/7/4', 'DSN', '2020/1/25');
INSERT INTO `meeting` VALUES ('19', '10', '6', '2020/5/30', 'IEEE Conference on Decision and Control', '2020/2/9', 'London', 'CDC', '2020/6/10', '2020/1/25');
INSERT INTO `meeting` VALUES ('20', '9', '7', '2020/10/8', 'Extending Database', '2020/10/11', 'NewYork', '2020/9/8', 'EDBT', '2019/6/13');
INSERT INTO `meeting` VALUES ('21', '11', '5', '2020/6/17', 'International Joint Conference on Artificial Intelligence', '2020/7/8', 'Paris', '2020/7/4', 'IJCAJ', '2020/2/5');
