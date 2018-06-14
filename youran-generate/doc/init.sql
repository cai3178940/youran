
CREATE TABLE `gen_history` (
    `historyId` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `projectId` int(11) NOT NULL COMMENT '所属项目id',
    `remoteUrl` varchar(256) NOT NULL COMMENT 'Git仓库地址',
    `commit` varchar(64) NOT NULL COMMENT 'commit号',
    `branch` varchar(32) NOT NULL COMMENT '分支名称',
    `sysVersion` varchar(20) NOT NULL COMMENT '系统版本号',
    `projectVersion` int(11) NOT NULL COMMENT '项目版本号',
    `createDate` datetime DEFAULT NULL COMMENT '创建时间',
    `createBy` varchar(32) DEFAULT NULL COMMENT '创建人',
    `operateDate` datetime DEFAULT NULL COMMENT '操作时间',
    `operateBy` varchar(32) DEFAULT NULL COMMENT '操作人',
    `delSign` smallint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
    `version` int(11) NOT NULL DEFAULT '0' COMMENT '乐观锁版本号',
    PRIMARY KEY (`historyId`),
    KEY `i_gen_history_0` (`projectId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='gen_history';



CREATE TABLE `meta_cascade_ext` (
    `cascadeExtId` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `fieldId` int(11) NOT NULL COMMENT '所属字段id',
    `entityId` int(11) NOT NULL COMMENT '所属实体id',
    `alias` varchar(255) NOT NULL COMMENT '展示字段别名',
    `list` smallint(1) NOT NULL COMMENT '是否在列表中展示',
    `show` smallint(1) NOT NULL COMMENT '是否在详情中展示',
    `query` smallint(1) NOT NULL COMMENT '是否为查询条件',
    `cascadeEntityId` int(11) NOT NULL COMMENT '级联实体的id',
    `cascadeFieldId` int(11) NOT NULL COMMENT '级联展示字段的id',
    `createDate` datetime DEFAULT NULL COMMENT '创建时间',
    `createBy` varchar(32) DEFAULT NULL COMMENT '创建人',
    `operateDate` datetime DEFAULT NULL COMMENT '操作时间',
    `operateBy` varchar(32) DEFAULT NULL COMMENT '操作人',
    `delSign` smallint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
    `version` int(11) NOT NULL DEFAULT '0' COMMENT '乐观锁版本号',
    PRIMARY KEY (`cascadeExtId`),
    KEY `i_meta_cascade_ext_0` (`fieldId`) USING BTREE,
    KEY `i_meta_cascade_ext_1` (`entityId`) USING BTREE,
    KEY `i_meta_cascade_ext_2` (`cascadeFieldId`) USING BTREE,
    KEY `i_meta_cascade_ext_3` (`cascadeEntityId`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='级联扩展';

INSERT INTO `meta_cascade_ext` VALUES (1,19,1,'deptName',1,1,1,2,18,'2018-06-05 11:22:04','admin','2018-06-05 11:22:04','admin',0,1);


CREATE TABLE `meta_const` (
    `constId` int(11) NOT NULL AUTO_INCREMENT COMMENT '常量类id',
    `projectId` int(11) NOT NULL COMMENT '所属项目id',
    `constName` varchar(20) NOT NULL COMMENT '常量类名',
    `constRemark` varchar(100) DEFAULT NULL COMMENT '常量名称(中文描述)',
    `constType` int(11) NOT NULL COMMENT '常量字段类型【1-整数，2-字符串】',
    `createDate` datetime DEFAULT NULL COMMENT '创建时间',
    `createBy` varchar(32) DEFAULT NULL COMMENT '创建人',
    `operateDate` datetime DEFAULT NULL COMMENT '操作时间',
    `operateBy` varchar(32) DEFAULT NULL COMMENT '操作人',
    `delSign` smallint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
    `version` int(11) NOT NULL DEFAULT '0' COMMENT '乐观锁版本号',
    PRIMARY KEY (`constId`),
    KEY `i_meta_const_0` (`projectId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='meta_const';

CREATE TABLE `meta_const_detail` (
    `constDetailId` int(11) NOT NULL AUTO_INCREMENT COMMENT '常量值id',
    `constId` int(11) NOT NULL COMMENT '常量类id',
    `detailName` varchar(50) NOT NULL COMMENT '常量字段名称',
    `detailValue` varchar(50) NOT NULL COMMENT '常量值',
    `detailRemark` varchar(100) DEFAULT NULL COMMENT '常量值备注',
    `createDate` datetime DEFAULT NULL COMMENT '创建时间',
    `createBy` varchar(32) DEFAULT NULL COMMENT '创建人',
    `operateDate` datetime DEFAULT NULL COMMENT '操作时间',
    `operateBy` varchar(32) DEFAULT NULL COMMENT '操作人',
    `delSign` smallint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
    `version` int(11) NOT NULL DEFAULT '0' COMMENT '乐观锁版本号',
    PRIMARY KEY (`constDetailId`),
    KEY `i_meta_const_detail_0` (`constId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='meta_const_detail';

CREATE TABLE `meta_entity` (
    `entityId` int(11) NOT NULL AUTO_INCREMENT COMMENT '实体id',
    `projectId` int(11) NOT NULL COMMENT '所属项目id',
    `schemaName` varchar(20) DEFAULT NULL COMMENT '模式名',
    `className` varchar(50) NOT NULL COMMENT '类名',
    `tableName` varchar(50) NOT NULL COMMENT '表名',
    `title` varchar(25) NOT NULL COMMENT '标题',
    `desc` varchar(250) DEFAULT NULL COMMENT '实体描述',
    `commonCall` smallint(1) NOT NULL COMMENT '是否支持通用服务调用',
    `pageSign` smallint(1) DEFAULT NULL COMMENT '是否支持分页查询',
    `createDate` datetime DEFAULT NULL COMMENT '创建时间',
    `createBy` varchar(32) DEFAULT NULL COMMENT '创建人',
    `operateDate` datetime DEFAULT NULL COMMENT '操作时间',
    `operateBy` varchar(32) DEFAULT NULL COMMENT '操作人',
    `delSign` smallint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
    `version` int(11) NOT NULL DEFAULT '0' COMMENT '乐观锁版本号',
    PRIMARY KEY (`entityId`),
    UNIQUE KEY `i_meta_entity_1` (`projectId`,`className`) USING BTREE,
    KEY `i_meta_entity_0` (`projectId`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='meta_entity';

INSERT INTO `meta_entity` VALUES (1,1,NULL,'User','t_user','用户','用户',1,1,'2018-06-05 11:15:57','admin','2018-06-05 11:15:57','admin',0,1),(2,1,NULL,'Dept','t_dept','部门','部门',1,1,'2018-06-05 11:18:54','admin','2018-06-05 11:18:54','admin',0,1);

CREATE TABLE `meta_field` (
    `fieldId` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `entityId` int(11) NOT NULL COMMENT '所属实体id',
    `jfieldName` varchar(50) NOT NULL COMMENT '实体字段名称',
    `fieldName` varchar(64) NOT NULL COMMENT '表字段名称',
    `jfieldType` varchar(10) NOT NULL COMMENT '实体字段类型',
    `fieldType` varchar(10) NOT NULL COMMENT '表字段类型',
    `fieldDesc` varchar(40) NOT NULL COMMENT '字段描述（中文名）',
    `fieldExample` varchar(200) NOT NULL COMMENT '字段示例',
    `fieldComment` varchar(200) NOT NULL COMMENT '字段备注',
    `fieldLength` int(11) NOT NULL COMMENT '表字段长度',
    `fieldScale` int(11) DEFAULT NULL COMMENT '表字段精度',
    `primaryKey` smallint(1) NOT NULL COMMENT '是否主键',
    `autoIncrement` smallint(1) NOT NULL COMMENT '是否自动递增',
    `notNull` smallint(1) NOT NULL COMMENT '是否不能为空',
    `defaultValue` varchar(40) NOT NULL COMMENT '默认值',
    `foreignKey` smallint(1) NOT NULL COMMENT '是否外键',
    `foreignEntityId` int(11) DEFAULT NULL COMMENT '外键实体id',
    `foreignFieldId` int(11) DEFAULT NULL COMMENT '外键字段id',
    `editType` int(11) DEFAULT NULL COMMENT '字段编辑方式：文本框、下拉框等',
    `dicType` varchar(100) DEFAULT NULL COMMENT '字典类型',
    `insert` smallint(1) NOT NULL COMMENT '是否手动插入字段',
    `update` smallint(1) NOT NULL COMMENT '是否手动更新字段',
    `list` smallint(1) NOT NULL COMMENT '是否列表展示字段',
    `listSort` smallint(1) NOT NULL COMMENT '是否支持排序',
    `show` smallint(1) NOT NULL COMMENT '是否详情展示字段',
    `query` smallint(1) NOT NULL COMMENT '是否查询字段',
    `queryType` int(4) DEFAULT NULL COMMENT '查询方式',
    `orderNo` int(4) DEFAULT NULL COMMENT '排序号',
    `specialField` varchar(20) DEFAULT NULL COMMENT '特殊字段类型',
    `createDate` datetime DEFAULT NULL COMMENT '创建时间',
    `createBy` varchar(32) DEFAULT NULL COMMENT '创建人',
    `operateDate` datetime DEFAULT NULL COMMENT '操作时间',
    `operateBy` varchar(32) DEFAULT NULL COMMENT '操作人',
    `delSign` smallint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
    `version` int(11) NOT NULL DEFAULT '0' COMMENT '乐观锁版本号',
    PRIMARY KEY (`fieldId`),
    KEY `i_meta_field_0` (`entityId`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 COMMENT='meta_field';

INSERT INTO `meta_field` VALUES (1,1,'operateBy','operate_by','String','varchar','修改人','admin','修改人【最大长度20】',20,0,0,0,1,'NULL',0,NULL,NULL,NULL,NULL,0,0,0,0,0,0,NULL,103,'operateBy','2018-06-05 11:16:18','admin','2018-06-05 11:16:18','admin',0,1),(2,1,'createDate','create_date','Date','datetime','创建时间','2017-12-07 00:00:00','创建时间【yyyy-MM-dd HH:mm:ss】',0,0,0,0,1,'NULL',0,NULL,NULL,NULL,NULL,0,0,0,1,0,0,NULL,100,'createDate','2018-06-05 11:16:18','admin','2018-06-05 11:16:18','admin',0,1),(3,1,'id','id','Long','bigint','主键','1','主键ID',20,0,1,1,1,'NULL',0,NULL,NULL,NULL,NULL,0,0,1,0,1,0,NULL,1,NULL,'2018-06-05 11:16:18','admin','2018-06-05 11:16:18','admin',0,1),(4,1,'createBy','create_by','String','varchar','创建人','admin','创建人【最大长度20】',20,0,0,0,1,'NULL',0,NULL,NULL,NULL,NULL,0,0,0,0,0,0,NULL,101,'createBy','2018-06-05 11:16:18','admin','2018-06-05 11:16:18','admin',0,1),(5,1,'operateDate','operate_date','Date','datetime','修改时间','2017-12-07 00:00:00','修改时间【yyyy-MM-dd HH:mm:ss】',0,0,0,0,1,'NULL',0,NULL,NULL,NULL,NULL,0,0,0,1,0,0,NULL,102,'operateDate','2018-06-05 11:16:18','admin','2018-06-05 11:16:18','admin',0,1),(6,1,'version','version','Integer','int','乐观锁版本号','1','乐观锁版本号【整型】',11,0,0,0,1,'NULL',0,NULL,NULL,NULL,NULL,0,0,0,0,0,0,NULL,104,'version','2018-06-05 11:16:18','admin','2018-06-05 11:16:18','admin',0,1),(7,1,'delSign','del_sign','Integer','tinyint','逻辑删除标识','0','逻辑删除标识【0-未删除，1-已删除】',1,0,0,0,1,'NULL',0,NULL,NULL,NULL,NULL,0,0,0,0,0,0,NULL,105,'delSign','2018-06-05 11:16:18','admin','2018-06-05 11:16:18','admin',0,1),(8,1,'name','name','String','varchar','姓名','张三','姓名',40,NULL,0,0,1,'NULL',0,0,0,NULL,NULL,1,1,1,0,1,1,2,3,NULL,'2018-06-05 11:17:30','admin','2018-06-05 11:17:30','admin',0,1),(9,1,'age','age','Integer','smallint','年龄','21','年龄',4,NULL,0,0,0,'NULL',0,0,0,NULL,NULL,1,1,1,1,1,1,7,4,NULL,'2018-06-05 11:18:21','admin','2018-06-05 11:18:21','admin',0,1),(10,2,'deptId','dept_id','Integer','int','主键','1','主键ID',11,NULL,1,1,1,'NULL',0,0,0,NULL,NULL,0,0,1,0,1,0,NULL,1,NULL,'2018-06-05 11:19:18','admin','2018-06-05 11:24:40','admin',0,1),(11,2,'createBy','create_by','String','varchar','创建人','admin','创建人【最大长度20】',20,0,0,0,1,'NULL',0,NULL,NULL,NULL,NULL,0,0,0,0,0,0,NULL,101,'createBy','2018-06-05 11:19:18','admin','2018-06-05 11:19:18','admin',0,1),(12,2,'createDate','create_date','Date','datetime','创建时间','2017-12-07 00:00:00','创建时间【yyyy-MM-dd HH:mm:ss】',0,0,0,0,1,'NULL',0,NULL,NULL,NULL,NULL,0,0,0,1,0,0,NULL,100,'createDate','2018-06-05 11:19:18','admin','2018-06-05 11:19:18','admin',0,1),(13,2,'operateDate','operate_date','Date','datetime','修改时间','2017-12-07 00:00:00','修改时间【yyyy-MM-dd HH:mm:ss】',0,0,0,0,1,'NULL',0,NULL,NULL,NULL,NULL,0,0,0,1,0,0,NULL,102,'operateDate','2018-06-05 11:19:18','admin','2018-06-05 11:19:18','admin',0,1),(14,2,'operateBy','operate_by','String','varchar','修改人','admin','修改人【最大长度20】',20,0,0,0,1,'NULL',0,NULL,NULL,NULL,NULL,0,0,0,0,0,0,NULL,103,'operateBy','2018-06-05 11:19:18','admin','2018-06-05 11:19:18','admin',0,1),(15,2,'version','version','Integer','int','乐观锁版本号','1','乐观锁版本号【整型】',11,0,0,0,1,'NULL',0,NULL,NULL,NULL,NULL,0,0,0,0,0,0,NULL,104,'version','2018-06-05 11:19:18','admin','2018-06-05 11:19:18','admin',0,1),(16,2,'delSign','del_sign','Integer','tinyint','逻辑删除标识','0','逻辑删除标识【0-未删除，1-已删除】',1,0,0,0,1,'NULL',0,NULL,NULL,NULL,NULL,0,0,0,0,0,0,NULL,105,'delSign','2018-06-05 11:19:18','admin','2018-06-05 11:19:18','admin',0,1),(17,2,'orderNo','order_no','Integer','int','排序号','1','排序号【整型】',11,0,0,0,1,'NULL',0,NULL,NULL,NULL,NULL,1,1,1,1,1,0,NULL,90,NULL,'2018-06-05 11:19:18','admin','2018-06-05 11:19:18','admin',0,1),(18,2,'deptName','dept_name','String','varchar','部门名称','软件开发事业部','部门名称',100,NULL,0,0,1,'NULL',0,0,0,NULL,NULL,1,1,1,0,1,1,2,3,NULL,'2018-06-05 11:20:24','admin','2018-06-05 11:20:24','admin',0,1),(19,1,'deptId','dept_id','Integer','int','部门id','1','部门id',11,NULL,0,0,1,'NULL',1,2,10,NULL,NULL,1,1,1,1,1,1,1,6,NULL,'2018-06-05 11:21:48','admin','2018-06-05 11:21:48','admin',0,1);

CREATE TABLE `meta_index` (
    `indexId` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `indexName` varchar(40) NOT NULL COMMENT '索引名称',
    `entityId` int(11) NOT NULL COMMENT '所属实体id',
    `unique` smallint(1) NOT NULL COMMENT '是否唯一索引',
    `createDate` datetime DEFAULT NULL COMMENT '创建时间',
    `createBy` varchar(32) DEFAULT NULL COMMENT '创建人',
    `operateDate` datetime DEFAULT NULL COMMENT '操作时间',
    `operateBy` varchar(32) DEFAULT NULL COMMENT '操作人',
    `delSign` smallint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
    `version` int(11) NOT NULL DEFAULT '0' COMMENT '乐观锁版本号',
    PRIMARY KEY (`indexId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='meta_index';

CREATE TABLE `meta_index_field` (
    `indexId` int(11) NOT NULL COMMENT '索引id',
    `fieldId` int(11) NOT NULL COMMENT '字段id',
    `orderNo` int(11) NOT NULL COMMENT '排序号',
    PRIMARY KEY (`indexId`,`fieldId`),
    KEY `i_meta_index_field_0` (`indexId`) USING BTREE,
    KEY `i_meta_index_field_1` (`fieldId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='meta_index_field';

CREATE TABLE `meta_mtm` (
    `mtmId` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `projectId` int(11) NOT NULL COMMENT '所属项目id',
    `tableName` varchar(50) NOT NULL COMMENT '关联表名',
    `schemaName` varchar(20) DEFAULT NULL COMMENT '模式名',
    `desc` varchar(500) DEFAULT NULL COMMENT '关联描述',
    `entityId1` int(11) NOT NULL COMMENT '实体A的id',
    `entityId2` int(11) NOT NULL COMMENT '实体B的id',
    `holdRefer1` smallint(1) NOT NULL COMMENT '实体A是否持有B的引用',
    `holdRefer2` smallint(1) NOT NULL COMMENT '实体B是否持有A的引用',
    `createDate` datetime DEFAULT NULL COMMENT '创建时间',
    `createBy` varchar(32) DEFAULT NULL COMMENT '创建人',
    `operateDate` datetime DEFAULT NULL COMMENT '操作时间',
    `operateBy` varchar(32) DEFAULT NULL COMMENT '操作人',
    `delSign` smallint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
    `version` int(11) NOT NULL DEFAULT '0' COMMENT '乐观锁版本号',
    PRIMARY KEY (`mtmId`),
    KEY `i_meta_mtm_0` (`projectId`) USING BTREE,
    KEY `i_meta_mtm_1` (`entityId1`) USING BTREE,
    KEY `i_meta_mtm_2` (`entityId2`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='meta_mtm';


CREATE TABLE `meta_project` (
    `projectId` int(11) NOT NULL AUTO_INCREMENT COMMENT '项目id',
    `packageName` varchar(100) NOT NULL COMMENT '主包名',
    `projectName` varchar(50) NOT NULL COMMENT '项目名简称',
    `groupId` varchar(50) NOT NULL COMMENT 'groupId',
    `author` varchar(50) DEFAULT NULL COMMENT '开发者',
    `remote` smallint(1) NOT NULL COMMENT '启用Git仓库',
    `remoteUrl` varchar(256) DEFAULT NULL COMMENT 'Git仓库地址',
    `username` varchar(32) DEFAULT NULL COMMENT 'Git用户名',
    `password` varchar(256) DEFAULT NULL COMMENT 'Git密码',
    `lastHistoryId` int(11) DEFAULT NULL COMMENT '最后提交历史id',
    `projectVersion` int(11) NOT NULL COMMENT '项目版本号',
    `createDate` datetime DEFAULT NULL COMMENT '创建时间',
    `createBy` varchar(32) DEFAULT NULL COMMENT '创建人',
    `operateDate` datetime DEFAULT NULL COMMENT '操作时间',
    `operateBy` varchar(32) DEFAULT NULL COMMENT '操作人',
    `delSign` smallint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
    `version` int(11) NOT NULL DEFAULT '0' COMMENT '乐观锁版本号',
    PRIMARY KEY (`projectId`),
    UNIQUE KEY `i_meta_project_0` (`projectName`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='meta_project';

INSERT INTO `meta_project` VALUES (1,'com.jd.bbs','bbs','com.jd','菜小哥',0,'','','',NULL,16,'2018-06-04 15:51:20','admin','2018-06-05 11:24:43','admin',0,1);

