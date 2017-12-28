/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.178.49
 Source Server Type    : MySQL
 Source Server Version : 50514
 Source Host           : 192.168.178.49:3358
 Source Schema         : youran

 Target Server Type    : MySQL
 Target Server Version : 50514
 File Encoding         : 65001

 Date: 27/12/2017 09:20:58
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for meta_const
-- ----------------------------
DROP TABLE IF EXISTS `meta_const`;
CREATE TABLE `meta_const`  (
  `constId` int(11) NOT NULL AUTO_INCREMENT COMMENT '常量类id',
  `projectId` int(11) NOT NULL COMMENT '所属项目id',
  `constName` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '常量类名',
  `constRemark` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '常量名称(中文描述)',
  `constType` int(11) NOT NULL COMMENT '常量字段类型【1-整数，2-字符串】',
  `createDate` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `createBy` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `operateDate` datetime NULL DEFAULT NULL COMMENT '操作时间',
  `operateBy` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作人',
  `delSign` smallint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `version` int(11) NOT NULL DEFAULT 0 COMMENT '乐观锁版本号',
  PRIMARY KEY (`constId`) USING BTREE,
  INDEX `i_meta_const_0`(`projectId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'meta_const' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of meta_const
-- ----------------------------
INSERT INTO `meta_const` VALUES (1, 1, 'Sex', '性别', 1, '2017-12-04 18:12:06', 'admin', '2017-12-04 18:22:42', 'admin', 0, 1);
INSERT INTO `meta_const` VALUES (5, 9, 'Sex', '性别', 1, '2017-12-12 13:35:07', 'admin', '2017-12-12 13:35:07', 'admin', 0, 1);
INSERT INTO `meta_const` VALUES (6, 9, 'ResType', '资源类型', 1, '2017-12-12 13:53:09', 'admin', '2017-12-12 13:53:09', 'admin', 0, 1);
INSERT INTO `meta_const` VALUES (7, 9, 'RoleType', '角色类型', 1, '2017-12-14 17:17:09', 'admin', '2017-12-14 17:17:09', 'admin', 0, 1);
INSERT INTO `meta_const` VALUES (8, 9, 'DataResType', '数据资源类型', 1, '2017-12-27 09:08:25', 'admin', '2017-12-27 09:08:25', 'admin', 0, 1);

-- ----------------------------
-- Table structure for meta_const_detail
-- ----------------------------
DROP TABLE IF EXISTS `meta_const_detail`;
CREATE TABLE `meta_const_detail`  (
  `constDetailId` int(11) NOT NULL AUTO_INCREMENT COMMENT '常量值id',
  `constId` int(11) NOT NULL COMMENT '常量类id',
  `detailName` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '常量字段名称',
  `detailValue` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '常量值',
  `detailRemark` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '常量值备注',
  `createDate` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `createBy` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `operateDate` datetime NULL DEFAULT NULL COMMENT '操作时间',
  `operateBy` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作人',
  `delSign` smallint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `version` int(11) NOT NULL DEFAULT 0 COMMENT '乐观锁版本号',
  PRIMARY KEY (`constDetailId`) USING BTREE,
  INDEX `i_meta_const_detail_0`(`constId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'meta_const_detail' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of meta_const_detail
-- ----------------------------
INSERT INTO `meta_const_detail` VALUES (1, 1, 'WOMAN', '2', '女', '2017-12-05 12:17:21', 'admin', '2017-12-05 12:17:21', 'admin', 0, 1);
INSERT INTO `meta_const_detail` VALUES (2, 1, 'MAN', '1', '男', '2017-12-05 12:17:33', 'admin', '2017-12-05 12:17:33', 'admin', 0, 1);
INSERT INTO `meta_const_detail` VALUES (3, 5, 'FEMALE', '2', '女', '2017-12-12 13:35:31', 'admin', '2017-12-12 13:36:25', 'admin', 0, 1);
INSERT INTO `meta_const_detail` VALUES (4, 5, 'MALE', '1', '男', '2017-12-12 13:35:43', 'admin', '2017-12-12 13:36:32', 'admin', 0, 1);
INSERT INTO `meta_const_detail` VALUES (5, 6, 'APP', '1', '应用', '2017-12-12 13:53:32', 'admin', '2017-12-12 13:53:32', 'admin', 0, 1);
INSERT INTO `meta_const_detail` VALUES (6, 6, 'MENU', '2', '菜单', '2017-12-12 13:53:43', 'admin', '2017-12-12 13:53:43', 'admin', 0, 1);
INSERT INTO `meta_const_detail` VALUES (7, 6, 'BUTTON', '3', '按钮', '2017-12-12 13:54:07', 'admin', '2017-12-12 13:54:07', 'admin', 0, 1);
INSERT INTO `meta_const_detail` VALUES (8, 6, 'ACTION', '4', '动作', '2017-12-12 13:54:22', 'admin', '2017-12-12 13:54:22', 'admin', 0, 1);
INSERT INTO `meta_const_detail` VALUES (9, 6, 'OTHER', '9', '其他', '2017-12-12 13:54:45', 'admin', '2017-12-12 13:54:45', 'admin', 0, 1);
INSERT INTO `meta_const_detail` VALUES (10, 6, 'CATEGORY', '5', '商品分类', '2017-12-13 11:30:32', 'admin', '2017-12-13 11:30:32', 'admin', 0, 1);
INSERT INTO `meta_const_detail` VALUES (11, 7, 'SYSTEM', '1', '系统角色', '2017-12-14 17:17:37', 'admin', '2017-12-14 17:17:37', 'admin', 0, 1);
INSERT INTO `meta_const_detail` VALUES (12, 7, 'DATA', '2', '数据角色', '2017-12-14 17:17:50', 'admin', '2017-12-14 17:17:50', 'admin', 0, 1);
INSERT INTO `meta_const_detail` VALUES (13, 8, 'CATEGORY', '1', '商品品类', '2017-12-27 09:09:17', 'admin', '2017-12-27 09:09:17', 'admin', 0, 1);
INSERT INTO `meta_const_detail` VALUES (14, 8, 'BRAND', '2', '品牌', '2017-12-27 09:09:43', 'admin', '2017-12-27 09:09:43', 'admin', 0, 1);
INSERT INTO `meta_const_detail` VALUES (15, 8, 'OTHER', '9', '其他', '2017-12-27 09:09:55', 'admin', '2017-12-27 09:09:55', 'admin', 0, 1);

-- ----------------------------
-- Table structure for meta_entity
-- ----------------------------
DROP TABLE IF EXISTS `meta_entity`;
CREATE TABLE `meta_entity`  (
  `entityId` int(11) NOT NULL AUTO_INCREMENT COMMENT '实体id',
  `projectId` int(11) NOT NULL COMMENT '所属项目id',
  `schemaName` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模式名',
  `className` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类名',
  `tableName` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '表名',
  `title` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标题',
  `desc` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '实体描述',
  `commonCall` smallint(1) NOT NULL COMMENT '是否支持通用服务调用',
  `createDate` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `createBy` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `operateDate` datetime NULL DEFAULT NULL COMMENT '操作时间',
  `operateBy` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作人',
  `delSign` smallint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `version` int(11) NOT NULL DEFAULT 0 COMMENT '乐观锁版本号',
  PRIMARY KEY (`entityId`) USING BTREE,
  UNIQUE INDEX `i_meta_entity_1`(`projectId`, `className`) USING BTREE,
  INDEX `i_meta_entity_0`(`projectId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'meta_entity' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of meta_entity
-- ----------------------------
INSERT INTO `meta_entity` VALUES (1, 1, NULL, 'User', 't_user', '用户', '用户表', 1, '2017-12-04 11:13:12', 'admin', '2017-12-04 11:13:12', 'admin', 0, 1);
INSERT INTO `meta_entity` VALUES (5, 9, NULL, 'Site', 'rbac_site', '站点', '站点', 1, '2017-12-12 09:55:49', 'admin', '2017-12-13 13:37:30', 'admin', 0, 1);
INSERT INTO `meta_entity` VALUES (6, 9, NULL, 'Role', 'rbac_role', '角色', '角色', 1, '2017-12-12 09:56:16', 'admin', '2017-12-12 09:56:16', 'admin', 0, 1);
INSERT INTO `meta_entity` VALUES (7, 9, NULL, 'User', 'rbac_user', '用户', '用户', 1, '2017-12-12 09:56:59', 'admin', '2017-12-12 09:56:59', 'admin', 0, 1);
INSERT INTO `meta_entity` VALUES (8, 9, NULL, 'Organization', 'rbac_org', '组织', '组织', 1, '2017-12-12 09:59:05', 'admin', '2017-12-12 11:30:55', 'admin', 0, 1);
INSERT INTO `meta_entity` VALUES (9, 9, NULL, 'Resource', 'rbac_res', '资源', '资源【应用、菜单、按钮、动作等】', 1, '2017-12-12 10:00:06', 'admin', '2017-12-12 10:00:06', 'admin', 0, 1);
INSERT INTO `meta_entity` VALUES (10, 9, NULL, 'ResourceAttach', 'rbac_res_att', '资源附属', '资源附属【一般是动作】', 1, '2017-12-12 10:01:06', 'admin', '2017-12-12 10:01:06', 'admin', 0, 1);
INSERT INTO `meta_entity` VALUES (11, 9, NULL, 'Dic', 'rbac_dic', '数据字典', '数据字典', 1, '2017-12-21 11:24:01', 'admin', '2017-12-22 09:35:06', 'admin', 0, 1);
INSERT INTO `meta_entity` VALUES (12, 9, NULL, 'DicItem', 'rbac_dic_item', '字典项', '字典项', 1, '2017-12-21 11:25:06', 'admin', '2017-12-22 09:35:14', 'admin', 0, 1);
INSERT INTO `meta_entity` VALUES (13, 9, NULL, 'DataResource', 'rbac_data_res', '数据资源', '数据资源【商品品类、品牌等】', 1, '2017-12-27 09:00:21', 'admin', '2017-12-27 09:01:15', 'admin', 0, 1);

-- ----------------------------
-- Table structure for meta_field
-- ----------------------------
DROP TABLE IF EXISTS `meta_field`;
CREATE TABLE `meta_field`  (
  `fieldId` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `entityId` int(11) NOT NULL COMMENT '所属实体id',
  `jfieldName` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '实体字段名称',
  `fieldName` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '表字段名称',
  `jfieldType` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '实体字段类型',
  `fieldType` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '表字段类型',
  `fieldDesc` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '字段描述（中文名）',
  `fieldExample` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '字段示例',
  `fieldComment` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '字段备注',
  `fieldLength` int(11) NOT NULL COMMENT '表字段长度',
  `fieldScale` int(11) NULL DEFAULT NULL COMMENT '表字段精度',
  `primaryKey` smallint(1) NOT NULL COMMENT '是否主键',
  `autoIncrement` smallint(1) NOT NULL COMMENT '是否自动递增',
  `notNull` smallint(1) NOT NULL COMMENT '是否不能为空',
  `defaultValue` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '默认值',
  `editType` int(11) NULL DEFAULT NULL COMMENT '字段编辑方式：文本框、下拉框等',
  `dicType` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字典类型',
  `insert` smallint(1) NOT NULL COMMENT '是否手动插入字段',
  `update` smallint(1) NOT NULL COMMENT '是否手动更新字段',
  `list` smallint(1) NOT NULL COMMENT '是否列表展示字段',
  `show` smallint(1) NOT NULL COMMENT '是否详情展示字段',
  `query` smallint(1) NOT NULL COMMENT '是否查询字段',
  `queryType` int(4) NULL DEFAULT NULL COMMENT '查询方式',
  `orderNo` int(4) NULL DEFAULT NULL COMMENT '排序号',
  `specialField` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '特殊字段类型',
  `createDate` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `createBy` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `operateDate` datetime NULL DEFAULT NULL COMMENT '操作时间',
  `operateBy` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作人',
  `delSign` smallint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `version` int(11) NOT NULL DEFAULT 0 COMMENT '乐观锁版本号',
  PRIMARY KEY (`fieldId`) USING BTREE,
  INDEX `i_meta_field_0`(`entityId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 143 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'meta_field' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of meta_field
-- ----------------------------
INSERT INTO `meta_field` VALUES (1, 1, 'name', 'name', 'String', 'varchar', '姓名', '张三', '姓名【字符型】', 20, NULL, 0, 0, 1, 'NULL', NULL, NULL, 1, 1, 1, 1, 1, 2, 4, NULL, '2017-12-04 11:14:58', 'admin', '2017-12-05 12:53:23', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (2, 1, 'id', 'id', 'Long', 'bigint', '主键', '1', '主键id', 20, NULL, 1, 1, 1, 'NULL', NULL, NULL, 0, 0, 0, 0, 0, NULL, 1, NULL, '2017-12-04 11:16:02', 'admin', '2017-12-05 12:21:40', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (3, 1, 'age', 'age', 'Short', 'smallint', '年龄', '21', '年龄【整型】', 3, NULL, 0, 0, 0, 'NULL', NULL, NULL, 1, 1, 1, 1, 1, 1, 3, NULL, '2017-12-04 11:16:56', 'admin', '2017-12-04 13:44:45', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (4, 1, 'createTime', 'create_time', 'Date', 'datetime', '创建时间', '2017-12-04 00:00:00', '创建时间【日期型】', 0, NULL, 0, 0, 1, 'NULL', NULL, NULL, 0, 0, 1, 1, 1, 7, 101, 'createDate', '2017-12-04 11:18:48', 'admin', '2017-12-04 13:45:41', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (5, 1, 'createUser', 'create_user', 'String', 'varchar', '创建人员', 'admin', '创建人员【字符型】', 20, NULL, 0, 0, 1, 'NULL', NULL, NULL, 0, 0, 1, 1, 0, NULL, 100, 'createBy', '2017-12-04 11:20:18', 'admin', '2017-12-04 13:45:31', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (6, 1, 'version', 'version', 'Integer', 'int', '乐观锁版本号', '1', '乐观锁版本号【整型】', 11, NULL, 0, 0, 1, 'NULL', NULL, NULL, 0, 0, 0, 0, 0, NULL, 105, 'version', '2017-12-04 11:21:55', 'admin', '2017-12-05 12:48:14', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (7, 1, 'delSign', 'del_sign', 'Integer', 'smallint', '逻辑删除标识', '0', '逻辑删除标识【0-未删除，1-已删除】', 1, NULL, 0, 0, 1, 'NULL', NULL, NULL, 0, 0, 0, 0, 0, NULL, 106, 'delSign', '2017-12-04 11:23:51', 'admin', '2017-12-05 12:48:22', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (8, 1, 'sex', 'sex', 'Integer', 'smallint', '性别', '2', '性别【1-男，2-女】', 1, NULL, 0, 0, 0, 'NULL', NULL, 'Sex', 1, 1, 1, 1, 1, 1, 5, NULL, '2017-12-05 12:20:11', 'admin', '2017-12-05 12:20:11', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (9, 1, 'operateUser', 'operate_user', 'String', 'varchar', '操作用户', 'admin', '操作用户【字符串】', 20, NULL, 0, 0, 1, 'NULL', NULL, NULL, 0, 0, 0, 0, 0, NULL, 102, 'operateBy', '2017-12-05 12:50:06', 'admin', '2017-12-05 12:50:06', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (10, 1, 'operateTime', 'operate_time', 'Date', 'datetime', '操作时间', '2017-12-05 00:00:00', '操作日期【日期型】', 0, NULL, 0, 0, 1, 'NULL', NULL, NULL, 0, 0, 0, 0, 0, NULL, 103, 'operateDate', '2017-12-05 12:51:31', 'admin', '2017-12-05 12:52:06', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (11, 1, 'username', 'username', 'String', 'varchar', '用户名', 'zhangsan', '用户名', 20, NULL, 0, 0, 1, 'NULL', NULL, NULL, 1, 1, 1, 1, 1, 2, 2, NULL, '2017-12-05 12:53:16', 'admin', '2017-12-05 12:53:16', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (17, 5, 'siteId', 'site_id', 'Integer', 'smallint', '主键', '1', '主键ID', 6, NULL, 1, 1, 1, 'NULL', NULL, NULL, 0, 0, 1, 1, 0, NULL, 1, NULL, '2017-12-12 10:12:24', 'admin', '2017-12-21 11:26:00', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (18, 5, 'createDate', 'create_date', 'Date', 'datetime', '创建时间', '2017-12-07 00:00:00', '创建时间【yyyy-MM-dd HH:mm:ss】', 0, NULL, 0, 0, 1, 'NULL', NULL, NULL, 0, 0, 0, 0, 0, NULL, 100, 'createDate', '2017-12-12 10:12:41', 'admin', '2017-12-12 10:12:41', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (19, 5, 'createBy', 'create_by', 'String', 'varchar', '创建人', 'admin', '创建人【最大长度20】', 20, NULL, 0, 0, 1, 'NULL', NULL, NULL, 0, 0, 0, 0, 0, NULL, 101, 'createBy', '2017-12-12 10:12:49', 'admin', '2017-12-12 10:12:49', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (20, 5, 'operateDate', 'operate_date', 'Date', 'datetime', '修改时间', '2017-12-07 00:00:00', '修改时间【yyyy-MM-dd HH:mm:ss】', 0, NULL, 0, 0, 1, 'NULL', NULL, NULL, 0, 0, 1, 1, 0, NULL, 102, 'operateDate', '2017-12-12 10:12:57', 'admin', '2017-12-12 10:12:57', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (21, 5, 'operateBy', 'operate_by', 'String', 'varchar', '修改人', 'admin', '修改人【最大长度20】', 20, NULL, 0, 0, 1, 'NULL', NULL, NULL, 0, 0, 1, 1, 0, NULL, 103, 'operateBy', '2017-12-12 10:13:04', 'admin', '2017-12-12 10:13:04', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (22, 5, 'version', 'version', 'Integer', 'int', '乐观锁版本号', '1', '乐观锁版本号【整型】', 11, NULL, 0, 0, 1, 'NULL', NULL, NULL, 0, 0, 0, 0, 0, NULL, 104, 'version', '2017-12-12 10:13:14', 'admin', '2017-12-12 10:13:14', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (23, 5, 'delSign', 'del_sign', 'Integer', 'tinyint', '逻辑删除标识', '0', '逻辑删除标识【0-未删除，1-已删除】', 1, NULL, 0, 0, 1, 'NULL', NULL, NULL, 0, 0, 0, 0, 0, NULL, 105, 'delSign', '2017-12-12 10:13:21', 'admin', '2017-12-12 10:58:04', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (24, 6, 'roleId', 'role_id', 'Integer', 'int', '主键', '1', '主键ID', 11, NULL, 1, 1, 1, 'NULL', NULL, NULL, 0, 0, 1, 1, 0, NULL, 1, NULL, '2017-12-12 10:13:41', 'admin', '2017-12-21 11:26:12', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (25, 6, 'createDate', 'create_date', 'Date', 'datetime', '创建时间', '2017-12-07 00:00:00', '创建时间【yyyy-MM-dd HH:mm:ss】', 0, NULL, 0, 0, 1, 'NULL', NULL, NULL, 0, 0, 0, 0, 0, NULL, 100, 'createDate', '2017-12-12 10:13:48', 'admin', '2017-12-12 10:13:48', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (26, 6, 'createBy', 'create_by', 'String', 'varchar', '创建人', 'admin', '创建人【最大长度20】', 20, NULL, 0, 0, 1, 'NULL', NULL, NULL, 0, 0, 0, 0, 0, NULL, 101, 'createBy', '2017-12-12 10:13:58', 'admin', '2017-12-12 10:13:58', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (27, 6, 'operateDate', 'operate_date', 'Date', 'datetime', '修改时间', '2017-12-07 00:00:00', '修改时间【yyyy-MM-dd HH:mm:ss】', 0, NULL, 0, 0, 1, 'NULL', NULL, NULL, 0, 0, 1, 1, 0, NULL, 102, 'operateDate', '2017-12-12 10:14:05', 'admin', '2017-12-12 10:14:05', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (28, 6, 'operateBy', 'operate_by', 'String', 'varchar', '修改人', 'admin', '修改人【最大长度20】', 20, NULL, 0, 0, 1, 'NULL', NULL, NULL, 0, 0, 1, 1, 0, NULL, 103, 'operateBy', '2017-12-12 10:14:12', 'admin', '2017-12-12 10:14:12', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (29, 6, 'version', 'version', 'Integer', 'int', '乐观锁版本号', '1', '乐观锁版本号【整型】', 11, NULL, 0, 0, 1, 'NULL', NULL, NULL, 0, 0, 0, 0, 0, NULL, 104, 'version', '2017-12-12 10:15:53', 'admin', '2017-12-12 10:15:53', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (30, 6, 'delSign', 'del_sign', 'Integer', 'tinyint', '逻辑删除标识', '0', '逻辑删除标识【0-未删除，1-已删除】', 1, NULL, 0, 0, 1, 'NULL', NULL, NULL, 0, 0, 0, 0, 0, NULL, 105, 'delSign', '2017-12-12 10:16:03', 'admin', '2017-12-12 10:58:27', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (31, 7, 'userId', 'user_id', 'Long', 'bigint', '主键', '1', '主键ID', 20, NULL, 1, 1, 1, 'NULL', NULL, NULL, 0, 0, 1, 1, 0, NULL, 1, NULL, '2017-12-12 10:16:46', 'admin', '2017-12-21 11:26:20', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (32, 7, 'createDate', 'create_date', 'Date', 'datetime', '创建时间', '2017-12-07 00:00:00', '创建时间【yyyy-MM-dd HH:mm:ss】', 0, NULL, 0, 0, 1, 'NULL', NULL, NULL, 0, 0, 0, 0, 0, NULL, 100, 'createDate', '2017-12-12 10:16:54', 'admin', '2017-12-12 10:16:54', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (33, 7, 'createBy', 'create_by', 'String', 'varchar', '创建人', 'admin', '创建人【最大长度20】', 20, NULL, 0, 0, 1, 'NULL', NULL, NULL, 0, 0, 0, 0, 0, NULL, 101, 'createBy', '2017-12-12 10:17:01', 'admin', '2017-12-12 10:17:01', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (34, 7, 'operateBy', 'operate_by', 'String', 'varchar', '修改人', 'admin', '修改人【最大长度20】', 20, NULL, 0, 0, 1, 'NULL', NULL, NULL, 0, 0, 1, 1, 0, NULL, 103, 'operateBy', '2017-12-12 10:17:07', 'admin', '2017-12-12 10:17:07', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (35, 7, 'operateDate', 'operate_date', 'Date', 'datetime', '修改时间', '2017-12-07 00:00:00', '修改时间【yyyy-MM-dd HH:mm:ss】', 0, NULL, 0, 0, 1, 'NULL', NULL, NULL, 0, 0, 1, 1, 0, NULL, 102, 'operateDate', '2017-12-12 10:17:14', 'admin', '2017-12-12 10:17:14', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (36, 7, 'version', 'version', 'Integer', 'int', '乐观锁版本号', '1', '乐观锁版本号【整型】', 11, NULL, 0, 0, 1, 'NULL', NULL, NULL, 0, 0, 0, 0, 0, NULL, 104, 'version', '2017-12-12 10:17:26', 'admin', '2017-12-12 10:17:26', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (37, 7, 'delSign', 'del_sign', 'Integer', 'tinyint', '逻辑删除标识', '0', '逻辑删除标识【0-未删除，1-已删除】', 1, NULL, 0, 0, 1, 'NULL', NULL, NULL, 0, 0, 0, 0, 0, NULL, 105, 'delSign', '2017-12-12 10:17:33', 'admin', '2017-12-12 10:58:41', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (38, 8, 'orgId', 'org_id', 'Integer', 'int', '主键', '1', '主键ID', 11, NULL, 1, 1, 1, 'NULL', NULL, NULL, 0, 0, 1, 1, 0, NULL, 1, NULL, '2017-12-12 10:18:32', 'admin', '2017-12-21 11:25:48', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (39, 8, 'createBy', 'create_by', 'String', 'varchar', '创建人', 'admin', '创建人【最大长度20】', 20, NULL, 0, 0, 1, 'NULL', NULL, NULL, 0, 0, 0, 0, 0, NULL, 101, 'createBy', '2017-12-12 10:18:40', 'admin', '2017-12-12 10:18:40', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (40, 8, 'createDate', 'create_date', 'Date', 'datetime', '创建时间', '2017-12-07 00:00:00', '创建时间【yyyy-MM-dd HH:mm:ss】', 0, NULL, 0, 0, 1, 'NULL', NULL, NULL, 0, 0, 0, 0, 0, NULL, 100, 'createDate', '2017-12-12 10:18:46', 'admin', '2017-12-12 10:18:46', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (41, 8, 'operateDate', 'operate_date', 'Date', 'datetime', '修改时间', '2017-12-07 00:00:00', '修改时间【yyyy-MM-dd HH:mm:ss】', 0, NULL, 0, 0, 1, 'NULL', NULL, NULL, 0, 0, 1, 1, 0, NULL, 102, 'operateDate', '2017-12-12 10:18:54', 'admin', '2017-12-12 10:18:54', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (42, 8, 'operateBy', 'operate_by', 'String', 'varchar', '修改人', 'admin', '修改人【最大长度20】', 20, NULL, 0, 0, 1, 'NULL', NULL, NULL, 0, 0, 1, 1, 0, NULL, 103, 'operateBy', '2017-12-12 10:19:03', 'admin', '2017-12-12 10:19:03', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (43, 8, 'version', 'version', 'Integer', 'int', '乐观锁版本号', '1', '乐观锁版本号【整型】', 11, NULL, 0, 0, 1, 'NULL', NULL, NULL, 0, 0, 0, 0, 0, NULL, 104, 'version', '2017-12-12 10:19:14', 'admin', '2017-12-12 10:19:14', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (44, 8, 'delSign', 'del_sign', 'Integer', 'tinyint', '逻辑删除标识', '0', '逻辑删除标识【0-未删除，1-已删除】', 1, NULL, 0, 0, 1, 'NULL', NULL, NULL, 0, 0, 0, 0, 0, NULL, 105, 'delSign', '2017-12-12 10:19:23', 'admin', '2017-12-12 10:58:54', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (45, 9, 'resId', 'res_id', 'Integer', 'int', '主键', '1', '主键ID', 11, NULL, 1, 1, 1, 'NULL', NULL, NULL, 0, 0, 1, 1, 0, NULL, 1, NULL, '2017-12-12 10:19:44', 'admin', '2017-12-21 11:26:34', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (46, 9, 'createDate', 'create_date', 'Date', 'datetime', '创建时间', '2017-12-07 00:00:00', '创建时间【yyyy-MM-dd HH:mm:ss】', 0, NULL, 0, 0, 1, 'NULL', NULL, NULL, 0, 0, 0, 0, 0, NULL, 100, 'createDate', '2017-12-12 10:19:52', 'admin', '2017-12-12 10:19:52', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (47, 9, 'createBy', 'create_by', 'String', 'varchar', '创建人', 'admin', '创建人【最大长度20】', 20, NULL, 0, 0, 1, 'NULL', NULL, NULL, 0, 0, 0, 0, 0, NULL, 101, 'createBy', '2017-12-12 10:19:58', 'admin', '2017-12-12 10:19:58', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (48, 9, 'operateDate', 'operate_date', 'Date', 'datetime', '修改时间', '2017-12-07 00:00:00', '修改时间【yyyy-MM-dd HH:mm:ss】', 0, NULL, 0, 0, 1, 'NULL', NULL, NULL, 0, 0, 1, 1, 0, NULL, 102, 'operateDate', '2017-12-12 10:20:05', 'admin', '2017-12-12 10:20:05', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (49, 9, 'operateBy', 'operate_by', 'String', 'varchar', '修改人', 'admin', '修改人【最大长度20】', 20, NULL, 0, 0, 1, 'NULL', NULL, NULL, 0, 0, 1, 1, 0, NULL, 103, 'operateBy', '2017-12-12 10:20:12', 'admin', '2017-12-12 10:20:12', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (50, 9, 'version', 'version', 'Integer', 'int', '乐观锁版本号', '1', '乐观锁版本号【整型】', 11, NULL, 0, 0, 1, 'NULL', NULL, NULL, 0, 0, 0, 0, 0, NULL, 104, 'version', '2017-12-12 10:20:19', 'admin', '2017-12-12 10:20:19', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (51, 9, 'delSign', 'del_sign', 'Integer', 'tinyint', '逻辑删除标识', '0', '逻辑删除标识【0-未删除，1-已删除】', 1, NULL, 0, 0, 1, 'NULL', NULL, NULL, 0, 0, 0, 0, 0, NULL, 105, 'delSign', '2017-12-12 10:20:27', 'admin', '2017-12-12 10:59:07', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (52, 10, 'attId', 'att_id', 'Integer', 'int', '主键', '1', '主键ID', 11, NULL, 1, 1, 1, 'NULL', NULL, NULL, 0, 0, 1, 1, 0, NULL, 1, NULL, '2017-12-12 10:20:44', 'admin', '2017-12-21 11:26:43', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (53, 10, 'createDate', 'create_date', 'Date', 'datetime', '创建时间', '2017-12-07 00:00:00', '创建时间【yyyy-MM-dd HH:mm:ss】', 0, NULL, 0, 0, 1, 'NULL', NULL, NULL, 0, 0, 0, 0, 0, NULL, 100, 'createDate', '2017-12-12 10:20:51', 'admin', '2017-12-12 10:20:51', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (54, 10, 'createBy', 'create_by', 'String', 'varchar', '创建人', 'admin', '创建人【最大长度20】', 20, NULL, 0, 0, 1, 'NULL', NULL, NULL, 0, 0, 0, 0, 0, NULL, 101, 'createBy', '2017-12-12 10:20:57', 'admin', '2017-12-12 10:20:57', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (55, 10, 'operateDate', 'operate_date', 'Date', 'datetime', '修改时间', '2017-12-07 00:00:00', '修改时间【yyyy-MM-dd HH:mm:ss】', 0, NULL, 0, 0, 1, 'NULL', NULL, NULL, 0, 0, 1, 1, 0, NULL, 102, 'operateDate', '2017-12-12 10:21:03', 'admin', '2017-12-12 10:21:03', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (56, 10, 'operateBy', 'operate_by', 'String', 'varchar', '修改人', 'admin', '修改人【最大长度20】', 20, NULL, 0, 0, 1, 'NULL', NULL, NULL, 0, 0, 1, 1, 0, NULL, 103, 'operateBy', '2017-12-12 10:21:11', 'admin', '2017-12-12 10:21:11', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (57, 10, 'version', 'version', 'Integer', 'int', '乐观锁版本号', '1', '乐观锁版本号【整型】', 11, NULL, 0, 0, 1, 'NULL', NULL, NULL, 0, 0, 0, 0, 0, NULL, 104, 'version', '2017-12-12 10:21:17', 'admin', '2017-12-12 10:21:17', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (59, 10, 'delSign', 'del_sign', 'Integer', 'tinyint', '逻辑删除标识', '0', '逻辑删除标识【0-未删除，1-已删除】', 1, NULL, 0, 0, 1, 'NULL', NULL, NULL, 0, 0, 0, 0, 0, NULL, 105, 'delSign', '2017-12-12 10:21:32', 'admin', '2017-12-12 10:59:18', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (60, 8, 'orderNo', 'order_no', 'Integer', 'int', '排序号', '1', '排序号【整型】', 11, NULL, 0, 0, 1, 'NULL', NULL, NULL, 1, 1, 1, 1, 0, NULL, 90, NULL, '2017-12-12 10:29:47', 'admin', '2017-12-12 10:29:47', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (61, 9, 'orderNo', 'order_no', 'Integer', 'int', '排序号', '1', '排序号【整型】', 11, NULL, 0, 0, 1, 'NULL', NULL, NULL, 1, 1, 1, 1, 0, NULL, 90, NULL, '2017-12-12 10:30:08', 'admin', '2017-12-12 10:30:08', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (62, 5, 'name', 'name', 'String', 'varchar', '英文简称', 'cms', '英文简称【最大长度10】', 10, NULL, 0, 0, 1, 'NULL', NULL, NULL, 1, 1, 1, 1, 1, 2, 2, NULL, '2017-12-12 10:33:08', 'admin', '2017-12-12 13:59:53', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (63, 5, 'fullName', 'full_name', 'String', 'varchar', '站点全称', '内容管理系统', '站点全称【最大长度40】', 40, NULL, 0, 0, 1, 'NULL', NULL, NULL, 1, 1, 1, 1, 1, 2, 3, NULL, '2017-12-12 10:34:33', 'admin', '2017-12-12 10:38:39', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (64, 6, 'name', 'name', 'String', 'varchar', '英文简称', 'cms', '英文简称【最大长度10】', 10, NULL, 0, 0, 1, 'NULL', NULL, NULL, 1, 1, 1, 1, 1, 2, 2, NULL, '2017-12-12 10:39:37', 'admin', '2017-12-12 13:59:39', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (65, 6, 'fullName', 'full_name', 'String', 'varchar', '角色名', '系统管理员', '角色名【最大长度40】', 40, NULL, 0, 0, 1, 'NULL', NULL, NULL, 1, 1, 1, 1, 1, 2, 3, NULL, '2017-12-12 10:40:37', 'admin', '2017-12-12 10:40:37', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (66, 6, 'status', 'status', 'Integer', 'tinyint', '角色状态', '1', '角色状态【0-停用，1-启用】', 1, NULL, 0, 0, 1, 'NULL', NULL, 'BoolConst', 1, 1, 1, 1, 1, 1, 10, NULL, '2017-12-12 10:45:00', 'admin', '2017-12-12 10:59:41', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (67, 6, 'siteId', 'site_id', 'Integer', 'smallint', '站点id', '1', '站点id', 6, NULL, 0, 0, 1, 'NULL', NULL, NULL, 1, 1, 1, 1, 1, 1, 50, NULL, '2017-12-12 11:02:03', 'admin', '2017-12-12 11:02:03', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (68, 7, 'siteId', 'site_id', 'Integer', 'smallint', '站点id', '1', '站点id', 6, NULL, 0, 0, 1, 'NULL', NULL, NULL, 1, 1, 1, 1, 1, 1, 50, NULL, '2017-12-12 11:03:26', 'admin', '2017-12-12 11:03:26', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (69, 8, 'siteId', 'site_id', 'Integer', 'smallint', '站点id', '1', '站点id', 6, NULL, 0, 0, 1, 'NULL', NULL, NULL, 1, 1, 1, 1, 1, 1, 50, NULL, '2017-12-12 11:03:36', 'admin', '2017-12-12 11:03:36', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (70, 9, 'siteId', 'site_id', 'Integer', 'smallint', '站点id', '1', '站点id', 6, NULL, 0, 0, 1, 'NULL', NULL, NULL, 1, 1, 1, 1, 1, 1, 50, NULL, '2017-12-12 11:03:51', 'admin', '2017-12-12 11:03:51', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (71, 10, 'siteId', 'site_id', 'Integer', 'smallint', '站点id', '1', '站点id', 6, NULL, 0, 0, 1, 'NULL', NULL, NULL, 1, 1, 1, 1, 1, 1, 50, NULL, '2017-12-12 11:04:03', 'admin', '2017-12-12 11:04:03', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (72, 7, 'status', 'status', 'Integer', 'tinyint', '用户状态', '1', '用户状态【0-停用，1-启用】', 1, NULL, 0, 0, 1, 'NULL', NULL, 'BoolConst', 1, 1, 1, 1, 1, 1, 10, NULL, '2017-12-12 11:22:25', 'admin', '2017-12-12 11:22:25', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (73, 7, 'orgId', 'org_id', 'Integer', 'int', '组织id', '1', '组织id', 11, NULL, 0, 0, 1, 'NULL', NULL, NULL, 1, 1, 1, 1, 1, 1, 9, NULL, '2017-12-12 11:32:50', 'admin', '2017-12-12 13:41:46', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (74, 7, 'erp', 'erp', 'String', 'varchar', '用户erp', 'zhangsan', '用户erp', 32, NULL, 0, 0, 1, 'NULL', NULL, NULL, 1, 0, 1, 1, 1, 1, 2, NULL, '2017-12-12 11:39:03', 'admin', '2017-12-12 11:39:03', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (75, 7, 'jdAccount', 'jd_account', 'String', 'varchar', '京东账号', 'cs32346', '京东账号', 32, NULL, 0, 0, 1, 'NULL', NULL, NULL, 1, 1, 1, 1, 1, 1, 3, NULL, '2017-12-12 11:41:45', 'admin', '2017-12-12 11:41:45', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (76, 7, 'name', 'name', 'String', 'varchar', '姓名', '张三', '姓名', 32, NULL, 0, 0, 1, 'NULL', NULL, NULL, 1, 1, 1, 1, 1, 2, 4, NULL, '2017-12-12 13:31:59', 'admin', '2017-12-12 13:31:59', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (77, 7, 'sex', 'sex', 'Integer', 'tinyint', '性别', '1', '性别【1-男性，2-女性】', 1, NULL, 0, 0, 0, 'NULL', NULL, 'Sex', 1, 1, 1, 1, 1, 1, 6, NULL, '2017-12-12 13:38:21', 'admin', '2017-12-12 13:38:21', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (78, 7, 'mail', 'mail', 'String', 'varchar', '邮箱', 'zhangsan@jd.com', '邮箱', 64, NULL, 0, 0, 0, 'NULL', NULL, NULL, 1, 1, 1, 1, 1, 2, 8, NULL, '2017-12-12 13:39:42', 'admin', '2017-12-12 13:39:42', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (79, 7, 'mobile', 'mobile', 'String', 'varchar', '电话', '13888888888', '电话', 20, NULL, 0, 0, 0, 'NULL', NULL, NULL, 1, 1, 1, 1, 1, 1, 8, NULL, '2017-12-12 13:41:29', 'admin', '2017-12-12 13:41:29', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (80, 8, 'orgNo', 'org_no', 'String', 'varchar', '组织编号', '00001', '组织编号', 20, NULL, 0, 0, 0, 'NULL', NULL, NULL, 1, 1, 1, 1, 1, 2, 2, NULL, '2017-12-12 13:44:25', 'admin', '2017-12-12 13:44:25', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (82, 8, 'name', 'name', 'String', 'varchar', '组织名称', '软件开发部', '组织名称【最大长度32】', 32, NULL, 0, 0, 1, 'NULL', NULL, NULL, 1, 1, 1, 1, 1, 2, 4, NULL, '2017-12-12 13:45:58', 'admin', '2017-12-12 13:45:58', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (83, 8, 'manager', 'manager', 'String', 'varchar', '负责人', 'zhangsan', '负责人【最大长度32】', 32, NULL, 0, 0, 0, 'NULL', NULL, NULL, 1, 1, 1, 1, 1, 1, 5, NULL, '2017-12-12 13:47:59', 'admin', '2017-12-12 13:47:59', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (84, 8, 'pid', 'pid', 'Integer', 'int', '父id', '1', '父id', 11, NULL, 0, 0, 0, 'NULL', NULL, NULL, 1, 1, 1, 1, 1, 1, 8, NULL, '2017-12-12 13:50:01', 'admin', '2017-12-12 13:50:01', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (85, 8, 'level', 'level', 'Integer', 'tinyint', '层级', '1', '层级【只能取1-6】', 1, NULL, 0, 0, 1, 'NULL', NULL, NULL, 1, 1, 1, 1, 1, 1, 9, NULL, '2017-12-12 13:52:04', 'admin', '2017-12-12 13:52:04', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (86, 9, 'resType', 'res_type', 'Integer', 'tinyint', '资源类型', '1', '资源类型【1-应用，2-菜单，3-按钮，4-动作，9-其他】', 2, NULL, 0, 0, 1, 'NULL', NULL, 'ResType', 1, 1, 1, 1, 1, 1, 2, NULL, '2017-12-12 13:57:28', 'admin', '2017-12-15 09:19:17', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (87, 9, 'name', 'name', 'String', 'varchar', '英文简称', 'addButton', '英文简称【最大长度10】', 10, NULL, 0, 0, 0, 'NULL', NULL, NULL, 1, 1, 1, 1, 1, 1, 3, NULL, '2017-12-12 13:59:14', 'admin', '2017-12-12 13:59:14', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (88, 9, 'fullName', 'full_name', 'String', 'varchar', '资源名称', '用户添加按钮', '资源名称【最大长度40】', 40, NULL, 0, 0, 1, 'NULL', NULL, NULL, 1, 1, 1, 1, 1, 2, 4, NULL, '2017-12-12 14:00:55', 'admin', '2017-12-12 14:00:55', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (89, 9, 'icon', 'icon', 'String', 'varchar', '图标', 'icon-add', '图标【最大长度10】', 10, NULL, 0, 0, 0, 'NULL', NULL, NULL, 1, 1, 1, 1, 1, 1, 5, NULL, '2017-12-12 14:02:18', 'admin', '2017-12-12 14:02:18', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (90, 9, 'url', 'url', 'String', 'varchar', '资源路径', '/user/add', '资源路径【最大长度256】', 256, NULL, 0, 0, 0, 'NULL', NULL, NULL, 1, 1, 1, 1, 0, 2, 6, NULL, '2017-12-12 14:04:56', 'admin', '2017-12-12 14:04:56', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (91, 9, 'status', 'status', 'Integer', 'tinyint', '资源状态', '1', '资源状态【0-停用，1-启用】', 1, NULL, 0, 0, 1, 'NULL', NULL, 'BoolConst', 1, 1, 1, 1, 1, 1, 10, NULL, '2017-12-12 14:12:19', 'admin', '2017-12-12 14:12:19', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (92, 9, 'remark', 'remark', 'String', 'varchar', '备注', '这是备注', '备注【最大长度256】', 256, NULL, 0, 0, 0, 'NULL', NULL, NULL, 1, 1, 1, 1, 0, NULL, 20, NULL, '2017-12-12 14:14:03', 'admin', '2017-12-12 14:14:03', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (93, 9, 'pid', 'pid', 'Integer', 'int', '父id', '1', '父id', 11, NULL, 0, 0, 0, 'NULL', NULL, NULL, 1, 1, 1, 1, 1, 1, 8, NULL, '2017-12-12 14:14:47', 'admin', '2017-12-12 14:14:47', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (94, 9, 'level', 'level', 'Integer', 'tinyint', '层级', '1', '层级【只能取1-6】', 1, NULL, 0, 0, 1, 'NULL', NULL, NULL, 1, 1, 1, 1, 1, 1, 9, NULL, '2017-12-12 14:15:05', 'admin', '2017-12-12 14:15:05', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (95, 10, 'name', 'name', 'String', 'varchar', '资源附属名称', 'currentUserInfo', '资源附属名称【最大长度20】', 20, NULL, 0, 0, 0, 'NULL', NULL, NULL, 1, 1, 1, 1, 1, 2, 3, NULL, '2017-12-12 14:17:49', 'admin', '2017-12-12 14:17:49', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (96, 10, 'url', 'url', 'String', 'varchar', '路径', '/user/add', '路径【最大长度256】', 256, NULL, 0, 0, 0, 'NULL', NULL, NULL, 1, 1, 1, 1, 0, 2, 6, NULL, '2017-12-12 14:18:17', 'admin', '2017-12-12 14:18:17', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (97, 10, 'resId', 'res_id', 'Integer', 'int', '资源id', '1', '资源id', 11, NULL, 0, 0, 1, 'NULL', NULL, NULL, 1, 1, 1, 1, 1, 1, 2, NULL, '2017-12-12 14:19:31', 'admin', '2017-12-12 14:19:54', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (98, 6, 'roleType', 'role_type', 'Integer', 'tinyint', '角色类型', '1', '角色状态【1-系统角色，2-数据角色】', 1, NULL, 0, 0, 1, 'NULL', NULL, 'RoleType', 1, 1, 1, 1, 1, 1, 9, NULL, '2017-12-14 17:16:48', 'admin', '2017-12-14 17:18:44', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (99, 11, 'dicId', 'dic_id', 'Integer', 'int', '主键', '1', '主键ID', 11, NULL, 1, 1, 1, 'NULL', NULL, NULL, 0, 0, 1, 1, 0, NULL, 1, NULL, '2017-12-21 11:27:27', 'admin', '2017-12-21 11:27:27', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (100, 12, 'dicItemId', 'dic_item_id', 'Integer', 'int', '主键', '1', '主键ID', 11, NULL, 1, 1, 1, 'NULL', NULL, NULL, 0, 0, 1, 1, 0, NULL, 1, NULL, '2017-12-21 11:28:13', 'admin', '2017-12-21 11:31:40', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (101, 11, 'dicCode', 'dic_code', 'String', 'varchar', '编号', 'C001', '字典编号【最大长度10】', 10, NULL, 0, 0, 1, 'NULL', NULL, NULL, 1, 1, 1, 1, 1, 1, 2, NULL, '2017-12-21 11:30:00', 'admin', '2017-12-21 11:31:16', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (102, 12, 'itemCode', 'item_code', 'String', 'varchar', '编号', 'T001', '字典项编号【最大长度10】', 10, NULL, 0, 0, 1, 'NULL', NULL, NULL, 1, 1, 1, 1, 1, 1, 2, NULL, '2017-12-21 11:32:40', 'admin', '2017-12-21 11:32:40', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (103, 11, 'name', 'name', 'String', 'varchar', '字典名', '用户类型', '字典名【最大长度40】', 40, NULL, 0, 0, 1, 'NULL', NULL, NULL, 1, 1, 1, 1, 1, 2, 3, NULL, '2017-12-21 11:33:51', 'admin', '2017-12-21 11:33:51', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (105, 12, 'label', 'label', 'String', 'varchar', '标签名', '类型1', '标签名【最大长度40】', 40, NULL, 0, 0, 1, 'NULL', NULL, NULL, 1, 1, 1, 1, 1, 2, 4, NULL, '2017-12-21 11:37:05', 'admin', '2017-12-21 11:37:05', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (106, 11, 'remark', 'remark', 'String', 'varchar', '备注', '这是备注', '备注【最大长度256】', 256, NULL, 0, 0, 0, 'NULL', NULL, NULL, 1, 1, 1, 1, 0, NULL, 10, NULL, '2017-12-21 13:42:01', 'admin', '2017-12-21 13:42:01', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (107, 12, 'remark', 'remark', 'String', 'varchar', '备注', '这是备注', '备注【最大长度256】', 256, NULL, 0, 0, 0, 'NULL', NULL, NULL, 1, 1, 1, 1, 0, NULL, 10, NULL, '2017-12-21 13:42:38', 'admin', '2017-12-21 13:42:38', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (108, 12, 'status', 'status', 'Integer', 'tinyint', '状态', '1', '状态【0-不可见，1-可见】', 1, NULL, 0, 0, 1, 'NULL', NULL, 'BoolConst', 1, 1, 1, 1, 1, 1, 9, NULL, '2017-12-21 13:43:53', 'admin', '2017-12-21 16:26:21', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (109, 11, 'createBy', 'create_by', 'String', 'varchar', '创建人', 'admin', '创建人【最大长度20】', 20, NULL, 0, 0, 1, 'NULL', NULL, NULL, 0, 0, 0, 0, 0, NULL, 101, 'createBy', '2017-12-21 13:44:54', 'admin', '2017-12-21 13:44:54', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (110, 11, 'createDate', 'create_date', 'Date', 'datetime', '创建时间', '2017-12-07 00:00:00', '创建时间【yyyy-MM-dd HH:mm:ss】', 0, NULL, 0, 0, 1, 'NULL', NULL, NULL, 0, 0, 0, 0, 0, NULL, 100, 'createDate', '2017-12-21 13:45:02', 'admin', '2017-12-21 13:45:02', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (111, 11, 'operateDate', 'operate_date', 'Date', 'datetime', '修改时间', '2017-12-07 00:00:00', '修改时间【yyyy-MM-dd HH:mm:ss】', 0, NULL, 0, 0, 1, 'NULL', NULL, NULL, 0, 0, 1, 1, 0, NULL, 102, 'operateDate', '2017-12-21 13:45:10', 'admin', '2017-12-21 13:45:10', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (112, 11, 'operateBy', 'operate_by', 'String', 'varchar', '修改人', 'admin', '修改人【最大长度20】', 20, NULL, 0, 0, 1, 'NULL', NULL, NULL, 0, 0, 1, 1, 0, NULL, 103, 'operateBy', '2017-12-21 13:45:19', 'admin', '2017-12-21 13:45:19', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (113, 11, 'version', 'version', 'Integer', 'int', '乐观锁版本号', '1', '乐观锁版本号【整型】', 11, NULL, 0, 0, 1, 'NULL', NULL, NULL, 0, 0, 0, 0, 0, NULL, 104, 'version', '2017-12-21 13:45:29', 'admin', '2017-12-21 13:45:29', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (114, 11, 'delSign', 'del_sign', 'Integer', 'tinyint', '逻辑删除标识', '0', '逻辑删除标识【0-未删除，1-已删除】', 1, NULL, 0, 0, 1, 'NULL', NULL, NULL, 0, 0, 0, 0, 0, NULL, 105, 'delSign', '2017-12-21 13:45:37', 'admin', '2017-12-21 13:45:37', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (115, 12, 'orderNo', 'order_no', 'Integer', 'int', '排序号', '1', '排序号【整型】', 11, NULL, 0, 0, 1, 'NULL', NULL, NULL, 1, 1, 1, 1, 0, NULL, 90, NULL, '2017-12-21 13:45:58', 'admin', '2017-12-21 13:45:58', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (116, 12, 'createDate', 'create_date', 'Date', 'datetime', '创建时间', '2017-12-07 00:00:00', '创建时间【yyyy-MM-dd HH:mm:ss】', 0, NULL, 0, 0, 1, 'NULL', NULL, NULL, 0, 0, 0, 0, 0, NULL, 100, 'createDate', '2017-12-21 13:46:07', 'admin', '2017-12-21 13:46:07', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (117, 12, 'createBy', 'create_by', 'String', 'varchar', '创建人', 'admin', '创建人【最大长度20】', 20, NULL, 0, 0, 1, 'NULL', NULL, NULL, 0, 0, 0, 0, 0, NULL, 101, 'createBy', '2017-12-21 13:46:14', 'admin', '2017-12-21 13:46:14', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (118, 12, 'operateDate', 'operate_date', 'Date', 'datetime', '修改时间', '2017-12-07 00:00:00', '修改时间【yyyy-MM-dd HH:mm:ss】', 0, NULL, 0, 0, 1, 'NULL', NULL, NULL, 0, 0, 1, 1, 0, NULL, 102, 'operateDate', '2017-12-21 13:46:22', 'admin', '2017-12-21 13:46:22', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (119, 12, 'version', 'version', 'Integer', 'int', '乐观锁版本号', '1', '乐观锁版本号【整型】', 11, NULL, 0, 0, 1, 'NULL', NULL, NULL, 0, 0, 0, 0, 0, NULL, 104, 'version', '2017-12-21 13:46:33', 'admin', '2017-12-21 13:46:33', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (120, 12, 'operateBy', 'operate_by', 'String', 'varchar', '修改人', 'admin', '修改人【最大长度20】', 20, NULL, 0, 0, 1, 'NULL', NULL, NULL, 0, 0, 1, 1, 0, NULL, 103, 'operateBy', '2017-12-21 13:46:44', 'admin', '2017-12-21 13:46:44', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (121, 12, 'delSign', 'del_sign', 'Integer', 'tinyint', '逻辑删除标识', '0', '逻辑删除标识【0-未删除，1-已删除】', 1, NULL, 0, 0, 1, 'NULL', NULL, NULL, 0, 0, 0, 0, 0, NULL, 105, 'delSign', '2017-12-21 13:46:53', 'admin', '2017-12-21 13:46:53', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (122, 11, 'siteId', 'site_id', 'Integer', 'smallint', '站点id', '1', '站点id', 6, NULL, 0, 0, 1, 'NULL', NULL, NULL, 1, 1, 1, 1, 1, 1, 50, NULL, '2017-12-21 13:47:53', 'admin', '2017-12-21 13:47:53', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (124, 12, 'siteId', 'site_id', 'Integer', 'smallint', '站点id', '1', '站点id', 6, NULL, 0, 0, 1, 'NULL', NULL, NULL, 1, 1, 1, 1, 1, 1, 50, NULL, '2017-12-21 13:48:25', 'admin', '2017-12-21 13:48:25', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (125, 7, 'userType', 'user_type', 'String', 'varchar', '用户类型', 'SUPER', '用户类型【SUPER-超级管理员，还有其他自定义类型】', 5, NULL, 0, 0, 1, 'NULL', NULL, NULL, 1, 1, 1, 1, 1, 1, 11, NULL, '2017-12-21 16:25:23', 'admin', '2017-12-21 16:25:23', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (126, 12, 'dicId', 'dic_id', 'Integer', 'int', '字典id', '1', '字典id', 11, NULL, 0, 0, 1, 'NULL', NULL, NULL, 1, 1, 1, 1, 1, 1, 3, NULL, '2017-12-21 16:36:14', 'admin', '2017-12-21 16:36:14', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (127, 13, 'dataResId', 'data_res_id', 'Integer', 'int', '主键', '1', '主键ID', 11, NULL, 1, 1, 1, 'NULL', NULL, NULL, 0, 0, 1, 1, 0, NULL, 1, NULL, '2017-12-27 09:03:09', 'admin', '2017-12-27 09:03:09', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (128, 13, 'dataResType', 'data_res_type', 'Integer', 'tinyint', '数据资源类型', '1', '数据资源类型【1-品类，2-品牌，9-其他】', 2, NULL, 0, 0, 1, 'NULL', NULL, 'DataResType', 1, 1, 1, 1, 1, 1, 2, NULL, '2017-12-27 09:04:14', 'admin', '2017-12-27 09:04:14', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (129, 13, 'code', 'code', 'String', 'varchar', '代码', 'P001', '资源代码【最大长度10】', 10, NULL, 0, 0, 0, 'NULL', NULL, NULL, 1, 1, 1, 1, 1, 1, 3, NULL, '2017-12-27 09:05:04', 'admin', '2017-12-27 09:05:04', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (130, 13, 'name', 'name', 'String', 'varchar', '资源名称', '家电类', '资源名称【最大长度40】', 40, NULL, 0, 0, 1, 'NULL', NULL, NULL, 1, 1, 1, 1, 1, 2, 4, NULL, '2017-12-27 09:05:46', 'admin', '2017-12-27 09:05:46', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (131, 13, 'pid', 'pid', 'Integer', 'int', '父id', '1', '父id', 11, NULL, 0, 0, 0, 'NULL', NULL, NULL, 1, 1, 1, 1, 1, 1, 8, NULL, '2017-12-27 09:05:58', 'admin', '2017-12-27 09:05:58', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (132, 13, 'level', 'level', 'Integer', 'tinyint', '层级', '1', '层级【只能取1-6】', 1, NULL, 0, 0, 1, 'NULL', NULL, NULL, 1, 1, 1, 1, 1, 1, 9, NULL, '2017-12-27 09:06:12', 'admin', '2017-12-27 09:06:12', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (133, 13, 'status', 'status', 'Integer', 'tinyint', '资源状态', '1', '资源状态【0-停用，1-启用】', 1, NULL, 0, 0, 1, 'NULL', NULL, 'BoolConst', 1, 1, 1, 1, 1, 1, 10, NULL, '2017-12-27 09:06:25', 'admin', '2017-12-27 09:06:25', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (134, 13, 'remark', 'remark', 'String', 'varchar', '备注', '这是备注', '备注【最大长度256】', 256, NULL, 0, 0, 0, 'NULL', NULL, NULL, 1, 1, 1, 1, 0, NULL, 20, NULL, '2017-12-27 09:06:36', 'admin', '2017-12-27 09:06:36', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (135, 13, 'siteId', 'site_id', 'Integer', 'smallint', '站点id', '1', '站点id', 6, NULL, 0, 0, 1, 'NULL', NULL, NULL, 1, 1, 1, 1, 1, 1, 50, NULL, '2017-12-27 09:06:45', 'admin', '2017-12-27 09:06:45', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (136, 13, 'orderNo', 'order_no', 'Integer', 'int', '排序号', '1', '排序号【整型】', 11, NULL, 0, 0, 1, 'NULL', NULL, NULL, 1, 1, 1, 1, 0, NULL, 90, NULL, '2017-12-27 09:06:56', 'admin', '2017-12-27 09:06:56', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (137, 13, 'createDate', 'create_date', 'Date', 'datetime', '创建时间', '2017-12-07 00:00:00', '创建时间【yyyy-MM-dd HH:mm:ss】', 0, NULL, 0, 0, 1, 'NULL', NULL, NULL, 0, 0, 0, 0, 0, NULL, 100, 'createDate', '2017-12-27 09:07:07', 'admin', '2017-12-27 09:07:07', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (138, 13, 'createBy', 'create_by', 'String', 'varchar', '创建人', 'admin', '创建人【最大长度20】', 20, NULL, 0, 0, 1, 'NULL', NULL, NULL, 0, 0, 0, 0, 0, NULL, 101, 'createBy', '2017-12-27 09:07:13', 'admin', '2017-12-27 09:07:13', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (139, 13, 'operateDate', 'operate_date', 'Date', 'datetime', '修改时间', '2017-12-07 00:00:00', '修改时间【yyyy-MM-dd HH:mm:ss】', 0, NULL, 0, 0, 1, 'NULL', NULL, NULL, 0, 0, 1, 1, 0, NULL, 102, 'operateDate', '2017-12-27 09:07:25', 'admin', '2017-12-27 09:07:25', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (140, 13, 'operateBy', 'operate_by', 'String', 'varchar', '修改人', 'admin', '修改人【最大长度20】', 20, NULL, 0, 0, 1, 'NULL', NULL, NULL, 0, 0, 1, 1, 0, NULL, 103, 'operateBy', '2017-12-27 09:07:35', 'admin', '2017-12-27 09:07:35', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (141, 13, 'version', 'version', 'Integer', 'int', '乐观锁版本号', '1', '乐观锁版本号【整型】', 11, NULL, 0, 0, 1, 'NULL', NULL, NULL, 0, 0, 0, 0, 0, NULL, 104, 'version', '2017-12-27 09:07:42', 'admin', '2017-12-27 09:07:42', 'admin', 0, 1);
INSERT INTO `meta_field` VALUES (142, 13, 'delSign', 'del_sign', 'Integer', 'tinyint', '逻辑删除标识', '0', '逻辑删除标识【0-未删除，1-已删除】', 1, NULL, 0, 0, 1, 'NULL', NULL, NULL, 0, 0, 0, 0, 0, NULL, 105, 'delSign', '2017-12-27 09:07:48', 'admin', '2017-12-27 09:07:48', 'admin', 0, 1);

-- ----------------------------
-- Table structure for meta_index
-- ----------------------------
DROP TABLE IF EXISTS `meta_index`;
CREATE TABLE `meta_index`  (
  `indexId` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `indexName` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '索引名称',
  `entityId` int(11) NOT NULL COMMENT '所属实体id',
  `unique` smallint(1) NOT NULL COMMENT '是否唯一索引',
  `createDate` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `createBy` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `operateDate` datetime NULL DEFAULT NULL COMMENT '操作时间',
  `operateBy` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作人',
  `delSign` smallint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `version` int(11) NOT NULL DEFAULT 0 COMMENT '乐观锁版本号',
  PRIMARY KEY (`indexId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'meta_index' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for meta_index_field
-- ----------------------------
DROP TABLE IF EXISTS `meta_index_field`;
CREATE TABLE `meta_index_field`  (
  `indexId` int(11) NOT NULL COMMENT '索引id',
  `fieldId` int(11) NOT NULL COMMENT '字段id',
  PRIMARY KEY (`indexId`, `fieldId`) USING BTREE,
  INDEX `i_meta_index_field_0`(`indexId`) USING BTREE,
  INDEX `i_meta_index_field_1`(`fieldId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'meta_index_field' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for meta_mtm
-- ----------------------------
DROP TABLE IF EXISTS `meta_mtm`;
CREATE TABLE `meta_mtm`  (
  `mtmId` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `projectId` int(11) NOT NULL COMMENT '所属项目id',
  `tableName` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '关联表名',
  `schemaName` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '模式名',
  `desc` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关联描述',
  `entityId1` int(11) NOT NULL COMMENT '实体A的id',
  `entityId2` int(11) NOT NULL COMMENT '实体B的id',
  `holdRefer1` smallint(1) NOT NULL COMMENT '实体A是否持有B的引用',
  `holdRefer2` smallint(1) NOT NULL COMMENT '实体B是否持有A的引用',
  `createDate` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `createBy` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `operateDate` datetime NULL DEFAULT NULL COMMENT '操作时间',
  `operateBy` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作人',
  `delSign` smallint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `version` int(11) NOT NULL DEFAULT 0 COMMENT '乐观锁版本号',
  PRIMARY KEY (`mtmId`) USING BTREE,
  INDEX `i_meta_mtm_0`(`projectId`) USING BTREE,
  INDEX `i_meta_mtm_1`(`entityId1`) USING BTREE,
  INDEX `i_meta_mtm_2`(`entityId2`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'meta_mtm' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of meta_mtm
-- ----------------------------
INSERT INTO `meta_mtm` VALUES (1, 9, 'rbac_user_role', NULL, '用户角色关联', 7, 6, 1, 0, '2017-12-12 14:21:31', 'admin', '2017-12-12 14:21:31', 'admin', 0, 1);
INSERT INTO `meta_mtm` VALUES (2, 9, 'rbac_role_res', NULL, '角色资源关联', 6, 9, 1, 0, '2017-12-12 14:22:36', 'admin', '2017-12-12 14:22:36', 'admin', 0, 1);
INSERT INTO `meta_mtm` VALUES (3, 9, 'rbac_user_res', NULL, '用户资源关联', 7, 9, 1, 0, '2017-12-21 17:19:37', 'admin', '2017-12-22 09:35:28', 'admin', 0, 1);
INSERT INTO `meta_mtm` VALUES (4, 9, 'rbac_user_d_res', NULL, '用户数据资源关联', 7, 13, 1, 0, '2017-12-27 09:11:02', 'admin', '2017-12-27 09:11:02', 'admin', 0, 1);
INSERT INTO `meta_mtm` VALUES (5, 9, 'rbac_role_d_res', NULL, '角色数据资源关联', 6, 13, 1, 0, '2017-12-27 09:17:13', 'admin', '2017-12-27 09:17:13', 'admin', 0, 1);

-- ----------------------------
-- Table structure for meta_project
-- ----------------------------
DROP TABLE IF EXISTS `meta_project`;
CREATE TABLE `meta_project`  (
  `projectId` int(11) NOT NULL AUTO_INCREMENT COMMENT '项目id',
  `packageName` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主包名',
  `projectName` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '项目名简称',
  `author` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '开发者',
  `createDate` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `createBy` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `operateDate` datetime NULL DEFAULT NULL COMMENT '操作时间',
  `operateBy` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作人',
  `delSign` smallint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `version` int(11) NOT NULL DEFAULT 0 COMMENT '乐观锁版本号',
  PRIMARY KEY (`projectId`) USING BTREE,
  UNIQUE INDEX `i_meta_project_0`(`projectName`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'meta_project' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of meta_project
-- ----------------------------
INSERT INTO `meta_project` VALUES (1, 'com.cbb.bbs', 'bbs', '菜小哥', '2017-12-04 11:12:12', 'admin', '2017-12-04 11:12:12', 'admin', 0, 1);
INSERT INTO `meta_project` VALUES (9, 'com.jd.dps.rbac', 'dps-rbac', 'cbb', '2017-12-12 09:44:54', 'admin', '2017-12-15 09:31:50', 'admin', 0, 1);

SET FOREIGN_KEY_CHECKS = 1;
