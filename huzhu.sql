/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80032
 Source Host           : localhost:3306
 Source Schema         : huzhu

 Target Server Type    : MySQL
 Target Server Version : 80032
 File Encoding         : 65001

 Date: 14/05/2023 23:40:40
*/
/*
创建一个database数据库
*/
CREATE database huzhu;
use huzhu;
/*
必须
*/
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for content
-- ----------------------------
DROP TABLE IF EXISTS `content`;
CREATE TABLE `content` (
  `id` int NOT NULL AUTO_INCREMENT,
  `contentid` int DEFAULT NULL,
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `content_time` datetime DEFAULT NULL,
  `content_type` varchar(255) DEFAULT NULL,
  `content_image` varchar(255) DEFAULT NULL,
  `userid` int DEFAULT NULL,
  `content_imagearray` varchar(5000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=115 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of content
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for fraud
-- ----------------------------
DROP TABLE IF EXISTS `fraud`;
CREATE TABLE `fraud` (
  `id` int NOT NULL AUTO_INCREMENT,
  `userid` int DEFAULT NULL,
  `fraud_type` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `fraud_time` datetime DEFAULT NULL,
  `fraud_method` varchar(255) DEFAULT NULL,
  `fraud_name` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `contact` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `amount` decimal(10,2) DEFAULT NULL,
  `imagepath` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `description` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `status` varchar(255) DEFAULT NULL,
  `contentcount` varchar(255) DEFAULT NULL,
  `star1count` varchar(255) DEFAULT NULL,
  `star2count` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `star3count` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `isstar` varchar(255) DEFAULT NULL,
  `imagearray` varchar(5000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of fraud
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for liveorfraud
-- ----------------------------
DROP TABLE IF EXISTS `liveorfraud`;
CREATE TABLE `liveorfraud` (
  `id` int NOT NULL AUTO_INCREMENT,
  `liveorfraud` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `description` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `precaution` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `imagepath` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `userid` int DEFAULT NULL,
  `imagearray` varchar(5000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33326 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of liveorfraud
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for logininfo
-- ----------------------------
DROP TABLE IF EXISTS `logininfo`;
CREATE TABLE `logininfo` (
  `id` int NOT NULL AUTO_INCREMENT,
  `userid` int DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `loginip` varchar(255) DEFAULT NULL,
  `logintime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of logininfo
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `available` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of menu
-- ----------------------------
BEGIN;
INSERT INTO `menu` (`id`, `type`, `title`, `available`) VALUES (1, '00设置', '个人', '1');
INSERT INTO `menu` (`id`, `type`, `title`, `available`) VALUES (2, '01发布管理', '互助', '1');
INSERT INTO `menu` (`id`, `type`, `title`, `available`) VALUES (3, '01发布管理', '日常', '1');
INSERT INTO `menu` (`id`, `type`, `title`, `available`) VALUES (4, '01发布管理', '骗子', '1');
INSERT INTO `menu` (`id`, `type`, `title`, `available`) VALUES (5, '02消息', '系统', '1');
INSERT INTO `menu` (`id`, `type`, `title`, `available`) VALUES (6, '02消息', '私信', '1');
INSERT INTO `menu` (`id`, `type`, `title`, `available`) VALUES (7, '03点评', '评论', '1');
INSERT INTO `menu` (`id`, `type`, `title`, `available`) VALUES (9, '03点评', '点赞', '1');
INSERT INTO `menu` (`id`, `type`, `title`, `available`) VALUES (10, '03点评', '收藏', '1');
COMMIT;

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `contact` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `message` varchar(10000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of message
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for resort
-- ----------------------------
DROP TABLE IF EXISTS `resort`;
CREATE TABLE `resort` (
  `id` int NOT NULL AUTO_INCREMENT,
  `userid` int DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `resort_name` varchar(255) DEFAULT NULL,
  `resort_type` varchar(255) DEFAULT NULL,
  `resort_time` datetime DEFAULT NULL,
  `description` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `address` varchar(255) DEFAULT NULL,
  `contact` varchar(255) DEFAULT NULL,
  `imagepath` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `contentcount` varchar(255) DEFAULT NULL,
  `star1count` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `star2count` varchar(255) DEFAULT NULL,
  `star3count` varchar(255) DEFAULT NULL,
  `isstar` varchar(255) DEFAULT NULL,
  `imagearray` varchar(5000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of resort
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for star
-- ----------------------------
DROP TABLE IF EXISTS `star`;
CREATE TABLE `star` (
  `id` int NOT NULL AUTO_INCREMENT,
  `starid` int DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `isstar` varchar(255) DEFAULT NULL,
  `userid` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=83 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of star
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for type
-- ----------------------------
DROP TABLE IF EXISTS `type`;
CREATE TABLE `type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` varchar(255) DEFAULT NULL,
  `resortorfraud` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `available` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of type
-- ----------------------------
BEGIN;
INSERT INTO `type` (`id`, `type`, `resortorfraud`, `available`) VALUES (1, '租房', 'resort', '1');
INSERT INTO `type` (`id`, `type`, `resortorfraud`, `available`) VALUES (2, '出租', 'resort', '1');
INSERT INTO `type` (`id`, `type`, `resortorfraud`, `available`) VALUES (3, '出二手', 'resort', '1');
INSERT INTO `type` (`id`, `type`, `resortorfraud`, `available`) VALUES (4, '找二手', 'resort', '1');
INSERT INTO `type` (`id`, `type`, `resortorfraud`, `available`) VALUES (5, '找工作', 'resort', '1');
INSERT INTO `type` (`id`, `type`, `resortorfraud`, `available`) VALUES (6, '招聘', 'resort', '1');
INSERT INTO `type` (`id`, `type`, `resortorfraud`, `available`) VALUES (7, '求助', 'resort', '1');
INSERT INTO `type` (`id`, `type`, `resortorfraud`, `available`) VALUES (8, '交友', 'resort', '1');
INSERT INTO `type` (`id`, `type`, `resortorfraud`, `available`) VALUES (9, '旅游', 'live', '1');
INSERT INTO `type` (`id`, `type`, `resortorfraud`, `available`) VALUES (10, '美食', 'live', '1');
INSERT INTO `type` (`id`, `type`, `resortorfraud`, `available`) VALUES (11, '游玩', 'live', '1');
INSERT INTO `type` (`id`, `type`, `resortorfraud`, `available`) VALUES (12, '资源', 'live', '1');
INSERT INTO `type` (`id`, `type`, `resortorfraud`, `available`) VALUES (13, '电信诈骗', 'fraud', '1');
INSERT INTO `type` (`id`, `type`, `resortorfraud`, `available`) VALUES (14, '银行卡诈骗', 'fraud', '1');
INSERT INTO `type` (`id`, `type`, `resortorfraud`, `available`) VALUES (15, '身份诈骗', 'fraud', '1');
INSERT INTO `type` (`id`, `type`, `resortorfraud`, `available`) VALUES (16, '换日币骗局', 'fraud', '1');
INSERT INTO `type` (`id`, `type`, `resortorfraud`, `available`) VALUES (17, '代金券骗局', 'fraud', '1');
INSERT INTO `type` (`id`, `type`, `resortorfraud`, `available`) VALUES (18, '免税骗局', 'fraud', '1');
INSERT INTO `type` (`id`, `type`, `resortorfraud`, `available`) VALUES (19, '兼职骗局', 'fraud', '1');
INSERT INTO `type` (`id`, `type`, `resortorfraud`, `available`) VALUES (20, '短信诈骗', 'fraud', '1');
INSERT INTO `type` (`id`, `type`, `resortorfraud`, `available`) VALUES (21, '机票骗局', 'fraud', '1');
INSERT INTO `type` (`id`, `type`, `resortorfraud`, `available`) VALUES (22, '代购托运骗局', 'fraud', '1');
COMMIT;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `salt` varchar(255) DEFAULT NULL,
  `Verified` varchar(255) DEFAULT NULL,
  `limits` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `imagepath` varchar(255) DEFAULT NULL,
  `contact` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `reg_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
