DROP TABLE IF EXISTS `meta_project`;

CREATE TABLE `meta_project` (
  `project_id` int(11) AUTO_INCREMENT COMMENT '项目id',
  `package_name` varchar(100) NOT NULL COMMENT '主包名',
  `project_name` varchar(50) NOT NULL COMMENT '项目标识',
  `project_desc` varchar(100) NOT NULL COMMENT '项目名称',
  `group_id` varchar(50) NOT NULL COMMENT 'groupId',
  `author` varchar(50) DEFAULT NULL COMMENT '开发者',
  `template_id` int(11) DEFAULT NULL COMMENT '模板id',
  `template_id_2` int(11) DEFAULT NULL COMMENT '模板id2',
  `template_id_3` int(11) DEFAULT NULL COMMENT '模板id3',
  `remote` tinyint(1) NOT NULL COMMENT '启用Git仓库',
  `remote_url` varchar(256) DEFAULT NULL COMMENT 'Git仓库地址',
  `remote_url_2` varchar(256) DEFAULT NULL COMMENT 'Git仓库地址2',
  `remote_url_3` varchar(256) DEFAULT NULL COMMENT 'Git仓库地址3',
  `username` varchar(32) DEFAULT NULL COMMENT 'Git用户名',
  `password` varchar(256) DEFAULT NULL COMMENT 'Git密码',
  `last_history_id` int(11) DEFAULT NULL COMMENT '最后提交历史id',
  `last_history_id_2` int(11) DEFAULT NULL COMMENT '最后提交历史id2',
  `last_history_id_3` int(11) DEFAULT NULL COMMENT '最后提交历史id3',
  `project_version` int(11) NOT NULL COMMENT '项目版本号',
  `feature` varchar(400) DEFAULT NULL COMMENT '特性json',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `created_by` varchar(32) DEFAULT NULL COMMENT '创建人',
  `operated_time` datetime DEFAULT NULL COMMENT '操作时间',
  `operated_by` varchar(32) DEFAULT NULL COMMENT '操作人',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `version` int(11) NOT NULL DEFAULT 0 COMMENT '乐观锁版本号',
  PRIMARY KEY (`project_id`),
  KEY `i_meta_project_0` (`project_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='meta_project';

DROP TABLE IF EXISTS `meta_entity`;

CREATE TABLE `meta_entity` (
  `entity_id` int(11) AUTO_INCREMENT COMMENT '实体id',
  `project_id` int(11) NOT NULL COMMENT '所属项目id',
  `schema_name` varchar(20) DEFAULT NULL COMMENT '模式名',
  `class_name` varchar(50) NOT NULL COMMENT '类名',
  `table_name` varchar(50) NOT NULL COMMENT '表名',
  `title` varchar(25) NOT NULL COMMENT '标题',
  `desc` varchar(250) DEFAULT NULL COMMENT '实体描述',
  `page_sign` tinyint(1) DEFAULT NULL COMMENT '是否支持分页查询',
  `feature` varchar(400) DEFAULT NULL COMMENT '特性json',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `created_by` varchar(32) DEFAULT NULL COMMENT '创建人',
  `operated_time` datetime DEFAULT NULL COMMENT '操作时间',
  `operated_by` varchar(32) DEFAULT NULL COMMENT '操作人',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `version` int(11) NOT NULL DEFAULT 0 COMMENT '乐观锁版本号',
  PRIMARY KEY (`entity_id`),
  KEY `i_meta_entity_0` (`project_id`) USING BTREE,
  KEY `i_meta_entity_1`(`project_id`,`class_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='meta_entity';

DROP TABLE IF EXISTS `meta_field`;

CREATE TABLE `meta_field` (
  `field_id` int(11) AUTO_INCREMENT COMMENT '主键',
  `entity_id` int(11) NOT NULL COMMENT '所属实体id',
  `jfield_name` varchar(50) NOT NULL COMMENT '实体字段名称',
  `field_name` varchar(64) NOT NULL COMMENT '表字段名称',
  `jfield_type` varchar(10) NOT NULL COMMENT '实体字段类型',
  `field_type` varchar(10) NOT NULL COMMENT '表字段类型',
  `field_desc` varchar(40) NOT NULL COMMENT '字段描述（中文名）',
  `field_example` varchar(200) NOT NULL COMMENT '字段示例',
  `field_comment` varchar(200) NOT NULL COMMENT '字段备注',
  `field_length` int(11) NOT NULL COMMENT '表字段长度',
  `field_scale` int(11) DEFAULT NULL COMMENT '表字段精度',
  `primary_key` tinyint(1) NOT NULL COMMENT '是否主键',
  `auto_increment` tinyint(1) NOT NULL COMMENT '是否自动递增',
  `not_null` tinyint(1) NOT NULL COMMENT '是否不能为空',
  `default_value` varchar(40) NOT NULL COMMENT '默认值',
  `foreign_key` tinyint(1) NOT NULL COMMENT '是否外键',
  `foreign_entity_id` int(11) DEFAULT NULL COMMENT '外键实体id',
  `foreign_field_id` int(11) DEFAULT NULL COMMENT '外键字段id',
  `edit_type` int(11) DEFAULT NULL COMMENT '字段编辑方式：文本框、下拉框等',
  `dic_type` varchar(100) DEFAULT NULL COMMENT '字典类型',
  `insert` tinyint(1) NOT NULL COMMENT '是否手动插入字段',
  `update` tinyint(1) NOT NULL COMMENT '是否手动更新字段',
  `list` tinyint(1) NOT NULL COMMENT '是否列表展示字段',
  `list_sort` tinyint(1) NOT NULL COMMENT '是否支持排序',
  `show` tinyint(1) NOT NULL COMMENT '是否详情展示字段',
  `query` tinyint(1) NOT NULL COMMENT '是否查询字段',
  `query_type` smallint(4) DEFAULT NULL COMMENT '查询方式',
  `order_no` smallint(4) DEFAULT NULL COMMENT '排序号',
  `special_field` varchar(20) DEFAULT NULL COMMENT '特殊字段类型',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `created_by` varchar(32) DEFAULT NULL COMMENT '创建人',
  `operated_time` datetime DEFAULT NULL COMMENT '操作时间',
  `operated_by` varchar(32) DEFAULT NULL COMMENT '操作人',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `version` int(11) NOT NULL DEFAULT 0 COMMENT '乐观锁版本号',
  PRIMARY KEY (`field_id`),
  KEY `i_meta_field_0` (`entity_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='meta_field';

DROP TABLE IF EXISTS `meta_index`;

CREATE TABLE `meta_index` (
  `index_id` int(11) AUTO_INCREMENT COMMENT '主键',
  `index_name` varchar(40) NOT NULL COMMENT '索引名称',
  `entity_id` int(11) NOT NULL COMMENT '所属实体id',
  `unique` tinyint(1) NOT NULL COMMENT '是否唯一索引',
  `unique_check` tinyint(1) NOT NULL COMMENT '唯一性校验',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `created_by` varchar(32) DEFAULT NULL COMMENT '创建人',
  `operated_time` datetime DEFAULT NULL COMMENT '操作时间',
  `operated_by` varchar(32) DEFAULT NULL COMMENT '操作人',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `version` int(11) NOT NULL DEFAULT 0 COMMENT '乐观锁版本号',
  PRIMARY KEY (`index_id`),
  KEY `i_meta_index_0` (`entity_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='meta_index';

DROP TABLE IF EXISTS `meta_index_field`;

CREATE TABLE `meta_index_field` (
  `index_id` int(11) NOT NULL COMMENT '索引id',
  `field_id` int(11) NOT NULL COMMENT '字段id',
  `order_no` int(11) NOT NULL COMMENT '排序号',
  PRIMARY KEY (`index_id`,`field_id`),
  KEY `i_meta_index_field_0` (`index_id`) USING BTREE,
  KEY `i_meta_index_field_1` (`field_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='meta_index_field';

DROP TABLE IF EXISTS `meta_const`;

CREATE TABLE `meta_const` (
  `const_id` int(11) AUTO_INCREMENT COMMENT '常量类id',
  `project_id` int(11) NOT NULL COMMENT '所属项目id',
  `const_name` varchar(20) NOT NULL COMMENT '常量类名',
  `const_remark` varchar(100) DEFAULT NULL COMMENT '常量名称(中文描述)',
  `const_type` int(11) NOT NULL COMMENT '常量字段类型【1-整数，2-字符串】',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `created_by` varchar(32) DEFAULT NULL COMMENT '创建人',
  `operated_time` datetime DEFAULT NULL COMMENT '操作时间',
  `operated_by` varchar(32) DEFAULT NULL COMMENT '操作人',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `version` int(11) NOT NULL DEFAULT 0 COMMENT '乐观锁版本号',
  PRIMARY KEY (`const_id`),
  KEY `i_meta_const_0` (`project_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='meta_const';

DROP TABLE IF EXISTS `meta_const_detail`;

CREATE TABLE `meta_const_detail` (
  `const_detail_id` int(11) AUTO_INCREMENT COMMENT '常量值id',
  `const_id` int(11) NOT NULL COMMENT '常量类id',
  `detail_name` varchar(50) NOT NULL COMMENT '常量字段名称',
  `detail_value` varchar(50) NOT NULL COMMENT '常量值',
  `detail_remark` varchar(100) DEFAULT NULL COMMENT '常量值备注',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `created_by` varchar(32) DEFAULT NULL COMMENT '创建人',
  `operated_time` datetime DEFAULT NULL COMMENT '操作时间',
  `operated_by` varchar(32) DEFAULT NULL COMMENT '操作人',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `version` int(11) NOT NULL DEFAULT 0 COMMENT '乐观锁版本号',
  PRIMARY KEY (`const_detail_id`),
  KEY `i_meta_const_detail_0` (`const_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='meta_const_detail';

DROP TABLE IF EXISTS `meta_mtm`;

CREATE TABLE `meta_mtm` (
  `mtm_id` int(11) AUTO_INCREMENT COMMENT '主键id',
  `project_id` int(11) NOT NULL COMMENT '所属项目id',
  `table_name` varchar(50) NOT NULL COMMENT '关联表名',
  `schema_name` varchar(20) DEFAULT NULL COMMENT '模式名',
  `desc` varchar(500) DEFAULT NULL COMMENT '关联描述',
  `entity_id1` int(11) NOT NULL COMMENT '实体A的id',
  `entity_id2` int(11) NOT NULL COMMENT '实体B的id',
  `hold_refer1` tinyint(1) NOT NULL COMMENT '实体A是否持有B的引用',
  `hold_refer2` tinyint(1) NOT NULL COMMENT '实体B是否持有A的引用',
  `entity_id_field1` varchar(64) DEFAULT '' NULL COMMENT '实体A对应多对多关联表的id字段名',
  `entity_id_field2` varchar(64) DEFAULT '' NOT NULL COMMENT '实体B对应多对多关联表的id字段名',
  `need_id` tinyint(1) DEFAULT 0 NOT NULL COMMENT '是否需要自增id字段',
  `big_id` tinyint(1) DEFAULT 0 NOT NULL COMMENT 'id字段是否bigint',
  `feature` varchar(400) DEFAULT NULL COMMENT '特性json',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `created_by` varchar(32) DEFAULT NULL COMMENT '创建人',
  `operated_time` datetime DEFAULT NULL COMMENT '操作时间',
  `operated_by` varchar(32) DEFAULT NULL COMMENT '操作人',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `version` int(11) NOT NULL DEFAULT 0 COMMENT '乐观锁版本号',
  PRIMARY KEY (`mtm_id`),
  KEY `i_meta_mtm_0` (`project_id`) USING BTREE,
  KEY `i_meta_mtm_1` (`entity_id1`) USING BTREE,
  KEY `i_meta_mtm_2` (`entity_id2`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='meta_mtm';

DROP TABLE IF EXISTS `meta_cascade_ext`;

CREATE TABLE `meta_cascade_ext` (
    `cascade_ext_id` int(11) AUTO_INCREMENT COMMENT '主键id',
    `field_id` int(11) NOT NULL COMMENT '所属字段id',
    `entity_id` int(11) NOT NULL COMMENT '所属实体id',
    `alias` varchar(255) NOT NULL COMMENT '展示字段别名',
    `list` tinyint(1) NOT NULL COMMENT '是否在列表中展示',
    `show` tinyint(1) NOT NULL COMMENT '是否在详情中展示',
    `query` tinyint(1) NOT NULL COMMENT '是否为查询条件',
    `cascade_entity_id` int(11) NOT NULL COMMENT '级联实体的id',
    `cascade_field_id` int(11) NOT NULL COMMENT '级联展示字段的id',
    `created_time` datetime DEFAULT NULL COMMENT '创建时间',
    `created_by` varchar(32) DEFAULT NULL COMMENT '创建人',
    `operated_time` datetime DEFAULT NULL COMMENT '操作时间',
    `operated_by` varchar(32) DEFAULT NULL COMMENT '操作人',
    `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
    `version` int(11) NOT NULL DEFAULT '0' COMMENT '乐观锁版本号',
    PRIMARY KEY (`cascade_ext_id`),
    KEY `i_meta_cascade_ext_0` (`field_id`) USING BTREE,
    KEY `i_meta_cascade_ext_1` (`entity_id`) USING BTREE,
    KEY `i_meta_cascade_ext_2` (`cascade_field_id`) USING BTREE,
    KEY `i_meta_cascade_ext_3` (`cascade_entity_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='级联扩展';


DROP TABLE IF EXISTS `meta_mtm_cascade_ext`;

CREATE TABLE `meta_mtm_cascade_ext` (
    `mtm_cascade_ext_id` int(11) AUTO_INCREMENT COMMENT '主键ID',
    `mtm_id` int(11) NOT NULL COMMENT '多对多id',
    `entity_id` int(11) NOT NULL COMMENT '实体id',
    `cascade_entity_id` int(11) NOT NULL COMMENT '级联所属实体id',
    `cascade_field_id` int(11) NOT NULL COMMENT '级联所属字段id',
    `alias` varchar(255) NOT NULL COMMENT '级联查询字段别名',
    `list` tinyint(1) NOT NULL COMMENT '是否在列表中展示',
    `show` tinyint(1) NOT NULL COMMENT '是否在详情中展示',
    `query` tinyint(1) NOT NULL COMMENT '是否为查询条件',
    `created_time` datetime NOT NULL COMMENT '创建时间【yyyy-MM-dd HH:mm:ss】',
    `created_by` varchar(20) NOT NULL COMMENT '创建人【最大长度20】',
    `operated_time` datetime NOT NULL COMMENT '修改时间【yyyy-MM-dd HH:mm:ss】',
    `operated_by` varchar(20) NOT NULL COMMENT '修改人【最大长度20】',
    `version` int(11) NOT NULL COMMENT '乐观锁版本号【整型】',
    `deleted` tinyint(1) NOT NULL COMMENT '逻辑删除标识【0-未删除，1-已删除】',
    PRIMARY KEY (`mtm_cascade_ext_id`),
    KEY `IDX_CAS_MTM_EXT_1` (`mtm_id`,`entity_id`,`cascade_entity_id`) USING BTREE,
    KEY `IDX_CAS_MTM_EXT_2` (`cascade_field_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='多对多级联扩展';

DROP TABLE IF EXISTS `gen_history`;

CREATE TABLE `gen_history` (
    `history_id` int(11) AUTO_INCREMENT COMMENT '主键id',
    `project_id` int(11) NOT NULL COMMENT '所属项目id',
    `remote_url` varchar(256) NOT NULL COMMENT 'Git仓库地址',
    `commit` varchar(64) NOT NULL COMMENT 'commit号',
    `branch` varchar(32) NOT NULL COMMENT '分支名称',
    `sys_version` varchar(20) NOT NULL COMMENT '系统版本号',
    `project_version` int(11) NOT NULL COMMENT '项目版本号',
    `created_time` datetime DEFAULT NULL COMMENT '创建时间',
    `created_by` varchar(32) DEFAULT NULL COMMENT '创建人',
    `operated_time` datetime DEFAULT NULL COMMENT '操作时间',
    `operated_by` varchar(32) DEFAULT NULL COMMENT '操作人',
    `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
    `version` int(11) NOT NULL DEFAULT 0 COMMENT '乐观锁版本号',
    PRIMARY KEY (`history_id`),
    KEY `i_gen_history_0` (`project_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='gen_history';

DROP TABLE IF EXISTS `code_template`;

CREATE TABLE `code_template` (
    `template_id` int(11) AUTO_INCREMENT COMMENT '主键ID',
    `code` varchar(32) NOT NULL COMMENT '模板编号',
    `name` varchar(32) NOT NULL COMMENT '模板名称',
    `template_type` tinyint(4) NOT NULL COMMENT '模板类型【1后端，2前端】',
    `template_version` varchar(10) NOT NULL COMMENT '模板版本号',
    `sys_low_version` varchar(10) NOT NULL COMMENT '兼容最低系统版本号',
    `sys_default` tinyint(4) NOT NULL COMMENT '是否系统默认模板',
    `remark` varchar(256) DEFAULT NULL COMMENT '备注【最大长度256】',
    `inner_version` int(11) NOT NULL COMMENT '内部版本号，每次模板有变动都自动加一',
    `created_time` datetime NOT NULL COMMENT '创建时间【yyyy-MM-dd HH:mm:ss】',
    `created_by` varchar(20) NOT NULL COMMENT '创建人【最大长度20】',
    `operated_time` datetime NOT NULL COMMENT '修改时间【yyyy-MM-dd HH:mm:ss】',
    `operated_by` varchar(20) NOT NULL COMMENT '修改人【最大长度20】',
    `version` int(11) NOT NULL COMMENT '乐观锁版本号【整型】',
    `deleted` tinyint(1) NOT NULL COMMENT '逻辑删除标识【0-未删除，1-已删除】',
    PRIMARY KEY (`template_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='代码模板';

DROP TABLE IF EXISTS `template_file`;

CREATE TABLE `template_file` (
    `file_id` int(11) AUTO_INCREMENT COMMENT '主键ID',
    `file_name` varchar(100) NOT NULL COMMENT '文件名',
    `file_dir` varchar(300) NOT NULL COMMENT '文件目录',
    `template_id` int(11) NOT NULL COMMENT '模板id',
    `context_type` tinyint(4) NOT NULL COMMENT '上下文类型【1全局、2实体、3常量】',
    `abstracted` tinyint(4) NOT NULL COMMENT '是否抽象文件',
    `content` text DEFAULT NULL COMMENT '内容',
    `created_time` datetime NOT NULL COMMENT '创建时间【yyyy-MM-dd HH:mm:ss】',
    `created_by` varchar(20) NOT NULL COMMENT '创建人【最大长度20】',
    `operated_time` datetime NOT NULL COMMENT '修改时间【yyyy-MM-dd HH:mm:ss】',
    `operated_by` varchar(20) NOT NULL COMMENT '修改人【最大长度20】',
    `version` int(11) NOT NULL COMMENT '乐观锁版本号【整型】',
    `deleted` tinyint(1) NOT NULL COMMENT '逻辑删除标识【0-未删除，1-已删除】',
    PRIMARY KEY (`file_id`),
    KEY `IDX_TEMPLATE_FILE_1` (`template_id`,`file_dir`,`file_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='模板文件';

