DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
    `id` int(10) AUTO_INCREMENT COMMENT '主键id',
    `userName` varchar(40) NOT NULL COMMENT '用户名',
    `sex` smallint(1) NOT NULL COMMENT '性别',
    `birthday` datetime DEFAULT NULL COMMENT '生日',
    `createDate` datetime NOT NULL COMMENT '创建日期',
    `createBy` varchar(50) NOT NULL COMMENT '创建人',
    `operateDate` datetime NOT NULL COMMENT '操作日期',
    `operateBy` varchar(50) NOT NULL COMMENT '操作人',
    `version` int(12) NOT NULL DEFAULT 1 COMMENT '乐观锁版本号',
    `delSign` smallint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `i_user_username` (`userName`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户';

