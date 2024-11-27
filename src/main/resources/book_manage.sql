/*
 Navicat Premium Data Transfer

 Source Server         : 3306
 Source Server Type    : MySQL
 Source Server Version : 80012
 Source Host           : localhost:3306
 Source Schema         : book_manage

 Target Server Type    : MySQL
 Target Server Version : 80012
 File Encoding         : 65001

 Date: 27/11/2024 18:53:38
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `nickname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES (1, 'admin', '图书管理员', 'Abc123.');

-- ----------------------------
-- Table structure for book
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book`  (
  `bid` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `desc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `price` decimal(10, 2) NULL DEFAULT NULL,
  `author` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `image_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`bid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of book
-- ----------------------------
INSERT INTO `book` VALUES (1, 'Logic', 'an intro to logic', 35.20, NULL, NULL);
INSERT INTO `book` VALUES (3, 'The Book', 'nothing', 3.99, NULL, NULL);
INSERT INTO `book` VALUES (4, 'Luck', 'To gain Luck', 49.90, NULL, NULL);
INSERT INTO `book` VALUES (5, '活着', '余华著', 35.00, '余华', 'static/picture/books/ToLive.jpg');
INSERT INTO `book` VALUES (6, '你好', '不太好', 5.00, NULL, NULL);
INSERT INTO `book` VALUES (7, 'a', NULL, 0.00, NULL, NULL);
INSERT INTO `book` VALUES (8, 'a', NULL, 0.00, NULL, NULL);
INSERT INTO `book` VALUES (9, 'a', NULL, 0.00, NULL, NULL);
INSERT INTO `book` VALUES (10, 'a', NULL, 0.00, NULL, NULL);
INSERT INTO `book` VALUES (11, '苍穹f', NULL, 0.00, NULL, NULL);
INSERT INTO `book` VALUES (12, 'test', NULL, 0.00, NULL, NULL);
INSERT INTO `book` VALUES (13, NULL, NULL, 0.00, NULL, NULL);
INSERT INTO `book` VALUES (14, '活着f', '', 1.00, NULL, NULL);
INSERT INTO `book` VALUES (15, '1', '2', 35.00, NULL, NULL);
INSERT INTO `book` VALUES (16, '1', '111', 1111.00, NULL, NULL);
INSERT INTO `book` VALUES (17, '1', '2', 123.00, NULL, NULL);
INSERT INTO `book` VALUES (18, '1', '3', 35.00, NULL, NULL);
INSERT INTO `book` VALUES (19, '罪与罚', '好书', 59.90, NULL, '/static/picture/books/ToLive51871.jpg');
INSERT INTO `book` VALUES (20, '发育最', '', 1.00, NULL, 'static/picture/books/ToLive82563.jpg');
INSERT INTO `book` VALUES (21, '1', 'm', 1.00, NULL, '../webapps/book/static/picture/books/ToLive56783.jpg');
INSERT INTO `book` VALUES (22, '1', '', 1.00, NULL, '../webapps/book/static/picture/books/ToLive75565.jpg');
INSERT INTO `book` VALUES (23, '罪与罚', '', 1.00, NULL, 'static/picture/books/SinAndPanish94467.jpg');
INSERT INTO `book` VALUES (24, 'zyf', 'mmm', 1.00, NULL, 'static/picture/books/SinAndPanish28067.jpg');
INSERT INTO `book` VALUES (25, 'threebody', 'good', 12.00, NULL, 'static/picture/books/ThreeBody44906.jpg');
INSERT INTO `book` VALUES (26, 'tb1f', '1', 59.00, NULL, 'static/picture/books/ThreeBody130899.jpg');
INSERT INTO `book` VALUES (27, 'History', '2', 23.00, NULL, 'static/picture/books/History55386.jpg');
INSERT INTO `book` VALUES (28, 'newbook', 'new', 1.00, NULL, 'static/picture/books/Experiences36198.jpg');

-- ----------------------------
-- Table structure for borrow
-- ----------------------------
DROP TABLE IF EXISTS `borrow`;
CREATE TABLE `borrow`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sid` int(11) NULL DEFAULT NULL,
  `bid` int(11) NULL DEFAULT NULL,
  `borrow_time` date NULL DEFAULT NULL,
  `return_time` date NULL DEFAULT NULL,
  `renew_status` tinyint(1) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unique_sid_bid`(`sid` ASC, `bid` ASC) USING BTREE,
  UNIQUE INDEX `f_bid`(`bid` ASC) USING BTREE,
  CONSTRAINT `f_bid` FOREIGN KEY (`bid`) REFERENCES `book` (`bid`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `f_sid` FOREIGN KEY (`sid`) REFERENCES `student` (`sid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of borrow
-- ----------------------------
INSERT INTO `borrow` VALUES (3, 1, 1, '2024-11-26', '2024-12-26', NULL);
INSERT INTO `borrow` VALUES (5, 1, 3, '2024-11-26', '2024-12-26', NULL);

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
  `sid` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `sex` enum('男','女') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `grade` int(11) NOT NULL,
  PRIMARY KEY (`sid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES (1, 'Ming', '男', 2019);
INSERT INTO `student` VALUES (2, 'Ming', '男', 2019);
INSERT INTO `student` VALUES (3, 'Ming', '男', 2019);
INSERT INTO `student` VALUES (4, 'Ming', '男', 2019);
INSERT INTO `student` VALUES (5, 'Luo', '男', 2019);
INSERT INTO `student` VALUES (6, 'Hero', '女', 2020);
INSERT INTO `student` VALUES (7, 'Hero', '男', 2021);
INSERT INTO `student` VALUES (8, 'Nio', '女', 1024);
INSERT INTO `student` VALUES (9, 'no', '女', 2023);
INSERT INTO `student` VALUES (10, '罗', '女', 2024);

-- ----------------------------
-- Triggers structure for table book
-- ----------------------------
DROP TRIGGER IF EXISTS `del_book`;
delimiter ;;
CREATE TRIGGER `del_book` BEFORE DELETE ON `book` FOR EACH ROW DELETE FROM borrow WHERE bid = old.bid
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table student
-- ----------------------------
DROP TRIGGER IF EXISTS `del`;
delimiter ;;
CREATE TRIGGER `del` BEFORE DELETE ON `student` FOR EACH ROW DELETE FROM borrow WHERE sid = old.sid
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
