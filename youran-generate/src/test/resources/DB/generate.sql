DROP TABLE IF EXISTS `meta_project`;

CREATE TABLE `meta_project` (
  `projectId` int(11) AUTO_INCREMENT COMMENT '项目id',
  `packageName` varchar(100) NOT NULL COMMENT '主包名',
  `projectName` varchar(50) NOT NULL COMMENT '项目名简称',
  `author` varchar(50) DEFAULT NULL COMMENT '开发者',
  `createDate` datetime DEFAULT NULL COMMENT '创建时间',
  `createBy` varchar(32) DEFAULT NULL COMMENT '创建人',
  `operateDate` datetime DEFAULT NULL COMMENT '操作时间',
  `operateBy` varchar(32) DEFAULT NULL COMMENT '操作人',
  `delSign` smallint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `version` int(11) NOT NULL DEFAULT 0 COMMENT '乐观锁版本号',
  PRIMARY KEY (`projectId`),
  UNIQUE KEY `i_meta_project_0` (`projectName`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='meta_project';

DROP TABLE IF EXISTS `meta_entity`;

CREATE TABLE `meta_entity` (
  `entityId` int(11) AUTO_INCREMENT COMMENT '实体id',
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
  `delSign` smallint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `version` int(11) NOT NULL DEFAULT 0 COMMENT '乐观锁版本号',
  PRIMARY KEY (`entityId`),
  KEY `i_meta_entity_0` (`projectId`) USING BTREE,
  UNIQUE KEY `i_meta_entity_1` (`projectId`,`className`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='meta_entity';

DROP TABLE IF EXISTS `meta_field`;

CREATE TABLE `meta_field` (
  `fieldId` int(11) AUTO_INCREMENT COMMENT '主键',
  `entityId` int(11) NOT NULL COMMENT '所属实体id',
  `jfieldName` varchar(20) NOT NULL COMMENT '实体字段名称',
  `fieldName` varchar(20) NOT NULL COMMENT '表字段名称',
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
  `delSign` smallint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `version` int(11) NOT NULL DEFAULT 0 COMMENT '乐观锁版本号',
  PRIMARY KEY (`fieldId`),
  KEY `i_meta_field_0` (`entityId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='meta_field';

DROP TABLE IF EXISTS `meta_index`;

CREATE TABLE `meta_index` (
  `indexId` int(11) AUTO_INCREMENT COMMENT '主键',
  `indexName` varchar(40) NOT NULL COMMENT '索引名称',
  `entityId` int(11) NOT NULL COMMENT '所属实体id',
  `unique` smallint(1) NOT NULL COMMENT '是否唯一索引',
  `createDate` datetime DEFAULT NULL COMMENT '创建时间',
  `createBy` varchar(32) DEFAULT NULL COMMENT '创建人',
  `operateDate` datetime DEFAULT NULL COMMENT '操作时间',
  `operateBy` varchar(32) DEFAULT NULL COMMENT '操作人',
  `delSign` smallint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `version` int(11) NOT NULL DEFAULT 0 COMMENT '乐观锁版本号',
  PRIMARY KEY (`indexId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='meta_index';

DROP TABLE IF EXISTS `meta_index_field`;

CREATE TABLE `meta_index_field` (
  `indexId` int(11) NOT NULL COMMENT '索引id',
  `fieldId` int(11) NOT NULL COMMENT '字段id',
  `orderNo` int(11) NOT NULL COMMENT '排序号',
  PRIMARY KEY (`indexId`,`fieldId`),
  KEY `i_meta_index_field_0` (`indexId`) USING BTREE,
  KEY `i_meta_index_field_1` (`fieldId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='meta_index_field';

DROP TABLE IF EXISTS `meta_const`;

CREATE TABLE `meta_const` (
  `constId` int(11) AUTO_INCREMENT COMMENT '常量类id',
  `projectId` int(11) NOT NULL COMMENT '所属项目id',
  `constName` varchar(20) NOT NULL COMMENT '常量类名',
  `constRemark` varchar(100) DEFAULT NULL COMMENT '常量名称(中文描述)',
  `constType` int(11) NOT NULL COMMENT '常量字段类型【1-整数，2-字符串】',
  `createDate` datetime DEFAULT NULL COMMENT '创建时间',
  `createBy` varchar(32) DEFAULT NULL COMMENT '创建人',
  `operateDate` datetime DEFAULT NULL COMMENT '操作时间',
  `operateBy` varchar(32) DEFAULT NULL COMMENT '操作人',
  `delSign` smallint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `version` int(11) NOT NULL DEFAULT 0 COMMENT '乐观锁版本号',
  PRIMARY KEY (`constId`),
  KEY `i_meta_const_0` (`projectId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='meta_const';

DROP TABLE IF EXISTS `meta_const_detail`;

CREATE TABLE `meta_const_detail` (
  `constDetailId` int(11) AUTO_INCREMENT COMMENT '常量值id',
  `constId` int(11) NOT NULL COMMENT '常量类id',
  `detailName` varchar(20) NOT NULL COMMENT '常量字段名称',
  `detailValue` varchar(20) NOT NULL COMMENT '常量值',
  `detailRemark` varchar(100) DEFAULT NULL COMMENT '常量值备注',
  `createDate` datetime DEFAULT NULL COMMENT '创建时间',
  `createBy` varchar(32) DEFAULT NULL COMMENT '创建人',
  `operateDate` datetime DEFAULT NULL COMMENT '操作时间',
  `operateBy` varchar(32) DEFAULT NULL COMMENT '操作人',
  `delSign` smallint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `version` int(11) NOT NULL DEFAULT 0 COMMENT '乐观锁版本号',
  PRIMARY KEY (`constDetailId`),
  KEY `i_meta_const_detail_0` (`constId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='meta_const_detail';

DROP TABLE IF EXISTS `meta_mtm`;

CREATE TABLE `meta_mtm` (
  `mtmId` int(11) AUTO_INCREMENT COMMENT '主键id',
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
  `delSign` smallint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `version` int(11) NOT NULL DEFAULT 0 COMMENT '乐观锁版本号',
  PRIMARY KEY (`mtmId`),
  KEY `i_meta_mtm_0` (`projectId`) USING BTREE,
  KEY `i_meta_mtm_1` (`entityId1`) USING BTREE,
  KEY `i_meta_mtm_2` (`entityId2`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='meta_mtm';

