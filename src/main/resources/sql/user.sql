/*
Navicat MySQL Data Transfer

Source Server         : lab3test
Source Server Version : 80019
Source Host           : localhost:3306
Source Database       : lab3

Target Server Type    : MYSQL
Target Server Version : 80019
File Encoding         : 65001

Date: 2020-04-12 09:44:12
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint NOT NULL,
  `fullname` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `sector` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'Mary Black', '123456', 'Mary', 'France', null, 'Paris');
INSERT INTO `user` VALUES ('2', 'Jack White', '123456', 'Jack', 'France', null, 'Essonne');
INSERT INTO `user` VALUES ('3', 'John Green', '123456', 'John', 'USA', '', 'Montgomery');
INSERT INTO `user` VALUES ('4', 'Lucy King', '123456', 'Lucy', 'USA', null, 'Little Rock');
INSERT INTO `user` VALUES ('5', 'Lily Black', '123456', 'Lily', 'USA', null, 'Washington, D.C.');
INSERT INTO `user` VALUES ('6', 'Coco Brown', '123456', 'Coco', 'UK', null, 'Cheshire');
INSERT INTO `user` VALUES ('7', 'Frank Green', '123456', 'Frank', 'UK', null, 'London');
INSERT INTO `user` VALUES ('8', 'Ben King', '123456', 'Ben', 'UK', null, 'Halton');
