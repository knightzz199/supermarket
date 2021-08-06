/*
 Navicat Premium Data Transfer

 Source Server         : aliyun
 Source Server Type    : MySQL
 Source Server Version : 80023
 Source Host           : www.xtnb.xyz:3306
 Source Schema         : supermarket

 Target Server Type    : MySQL
 Target Server Version : 80023
 File Encoding         : 65001

 Date: 07/08/2021 00:04:40
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for address
-- ----------------------------
DROP TABLE IF EXISTS `address`;
CREATE TABLE `address`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '地址id',
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收货人姓名',
  `phone` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收货人联系电话',
  `addr` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址名称',
  `user_id` int(0) NULL DEFAULT NULL COMMENT '用户id ',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_userId`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of address
-- ----------------------------
INSERT INTO `address` VALUES (1, '徐厅', '13647606251', '重庆璧山', 3);

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_czech_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_czech_ci NULL DEFAULT NULL COMMENT '密码',
  `sex` int(0) NULL DEFAULT NULL COMMENT '店家性别',
  `pic_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_czech_ci NULL DEFAULT NULL COMMENT '店家头像',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_adminId`(`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_czech_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES (1, '李四', '123456', 0, 'https://gitee.com/kxt666/knightzz/raw/master/image/%60%7BVFN8SM)FB((F%60Q)1FU$L1.png');

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `goods_description` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品的描述信息',
  `pic_url` varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品图片1',
  `stock` int(0) NULL DEFAULT NULL COMMENT '库存数',
  `state` int(0) NULL DEFAULT NULL COMMENT '商品状态，0表示下架了，1表示可以购买,  2表示售罄了',
  `sale_num` int(0) NULL DEFAULT NULL COMMENT '该款商品的销售数量',
  `goods_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '商品单价',
  `admin_id` int(0) NULL DEFAULT NULL COMMENT '商家id',
  `type_id` int(0) NULL DEFAULT NULL COMMENT '类型id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_adminId`(`admin_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES (15, '一件很棒的衣服，鸿星尔克！！', 'https://gitee.com/kxt666/knightzz/raw/master/image/7bb9a9397aee46f7b1acb5e8a54441f0.jpg', 30, 1, 15, 80.00, 1, 12);
INSERT INTO `goods` VALUES (16, '一双小鞋子，希望大家喜欢', 'https://gitee.com/kxt666/knightzz/raw/master/image/1a0cbb72060b4f6a96abec9b5074cf4f.jpg', 50, 1, 0, 300.00, 1, 13);

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `num` int(0) NULL DEFAULT NULL COMMENT '购买数量',
  `price` decimal(10, 0) NULL DEFAULT NULL COMMENT '订单总价',
  `express_fee` decimal(7, 0) NULL DEFAULT NULL COMMENT '快递费',
  `goods_id` int(0) NULL DEFAULT NULL COMMENT '商品id',
  `user_id` int(0) NULL DEFAULT NULL COMMENT '用户id',
  `address_id` int(0) NULL DEFAULT NULL COMMENT '收获地址',
  `state` int(0) NULL DEFAULT NULL COMMENT '订单状态，0未付款，1已付款，2表示未发货，3表示已发货，4表示已完成',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `total_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '总价格',
  `admin_id` int(0) NULL DEFAULT NULL COMMENT '商家id',
  `order_num` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '订单号',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_createTime`(`create_time`) USING BTREE,
  INDEX `idx_userId`(`user_id`) USING BTREE,
  INDEX `idx_goodsId`(`goods_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order
-- ----------------------------
INSERT INTO `order` VALUES (2, 5, 1500, 10, 16, 3, 1, 0, '2021-08-05 15:57:30', 1510.00, 1, 'aa34556e-6429-4d52-995a-f709dac4558b');
INSERT INTO `order` VALUES (3, 5, 400, 10, 15, 3, 1, 0, '2021-08-05 16:02:00', 410.00, 1, '2a3b4cc0-1fbb-43bc-8d23-53fdb90011c6');
INSERT INTO `order` VALUES (4, 15, 1200, 10, 15, 3, 1, 0, '2021-08-06 01:10:24', 1210.00, 1, '1d9327b5-ffc2-43c2-a1f6-99fd4983d0aa');

-- ----------------------------
-- Table structure for type
-- ----------------------------
DROP TABLE IF EXISTS `type`;
CREATE TABLE `type`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '父类型主键',
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品类型的中文描述',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_typeId`(`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of type
-- ----------------------------
INSERT INTO `type` VALUES (12, '衣服');
INSERT INTO `type` VALUES (13, '鞋子');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `username` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户密码',
  `sex` int(0) NULL DEFAULT NULL COMMENT '用户性别，0表示男，1表示女',
  `pic_url` varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户头像的url地址',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_userId`(`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (3, 'kxt', '123', 0, 'https://gitee.com/kxt666/knightzz/raw/master/image/1f561cb6472044a4a67e064e0ac41263.jpg');
INSERT INTO `user` VALUES (4, '张三', '123456', 1, 'https://gitee.com/kxt666/knightzz/raw/master/image/%60%7BVFN8SM)FB((F%60Q)1FU$L1.png');

-- ----------------------------
-- Table structure for user_collection
-- ----------------------------
DROP TABLE IF EXISTS `user_collection`;
CREATE TABLE `user_collection`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` char(9) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户id',
  `goods_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_collection
-- ----------------------------
INSERT INTO `user_collection` VALUES (1, '3', '15');
INSERT INTO `user_collection` VALUES (2, '3', '16');

SET FOREIGN_KEY_CHECKS = 1;
