package com.youran.generate.web.rest;

import com.youran.common.pojo.vo.ReplyVO;
import com.youran.common.util.JsonUtil;
import com.youran.generate.help.GenerateHelper;
import com.youran.generate.pojo.dto.ReverseEngineeringDTO;
import com.youran.generate.pojo.po.MetaProjectPO;
import com.youran.generate.web.AbstractWebTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
/**
 * Title:
 * Description:
 * Author: cbb
 * Create Time: 2018/5/31 13:37
 */
public class ReverseEngineeringControllerTest extends AbstractWebTest {

    @Autowired
    private GenerateHelper generateHelper;

    private MetaProjectPO metaProject;

    private String ddl;

    @Before
    public void init(){
        this.metaProject = generateHelper.saveProjectExample();
        this.ddl = "CREATE TABLE `meta_entity` (\n" +
            "  `entityId` int(11) AUTO_INCREMENT COMMENT '实体id',\n" +
            "  `projectId` int(11) NOT NULL COMMENT '所属项目id',\n" +
            "  `schemaName` varchar(20) DEFAULT NULL COMMENT '模式名',\n" +
            "  `className` varchar(50) NOT NULL COMMENT '类名',\n" +
            "  `tableName` varchar(50) NOT NULL COMMENT '表名',\n" +
            "  `title` varchar(25) NOT NULL COMMENT '标题',\n" +
            "  `desc` varchar(250) DEFAULT NULL COMMENT '实体描述',\n" +
            "  `commonCall` smallint(1) NOT NULL COMMENT '是否支持通用服务调用',\n" +
            "  `pageSign` smallint(1) DEFAULT NULL COMMENT '是否支持分页查询',\n" +
            "  `createdTime` datetime DEFAULT NULL COMMENT '创建时间',\n" +
            "  `createdBy` varchar(32) DEFAULT NULL COMMENT '创建人',\n" +
            "  `operatedTime` datetime DEFAULT NULL COMMENT '操作时间',\n" +
            "  `operatedBy` varchar(32) DEFAULT NULL COMMENT '操作人',\n" +
            "  `deleted` smallint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',\n" +
            "  `version` int(11) NOT NULL DEFAULT 0 COMMENT '乐观锁版本号',\n" +
            "  PRIMARY KEY (`entityId`),\n" +
            "  KEY `i_meta_entity_0` (`projectId`) USING BTREE,\n" +
            "  UNIQUE KEY `i_meta_entity_1` (`projectId`,`className`) USING BTREE\n" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='meta_entity';";
    }

    @Test
    public void execute() throws Exception {

        ReverseEngineeringDTO dto = new ReverseEngineeringDTO();
        dto.setProjectId(metaProject.getProjectId());
        dto.setDdl(ddl);
        dto.setDbType("mysql");

        restMockMvc.perform(post(getRootPath()+"/reverse_engineering/check")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(JsonUtil.toJSONString(dto)))
            .andExpect(jsonPath("$.code").value(is(ReplyVO.SUCCESS_CODE)));

        restMockMvc.perform(post(getRootPath()+"/reverse_engineering/execute")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(JsonUtil.toJSONString(dto)))
            .andExpect(jsonPath("$.code").value(is(ReplyVO.SUCCESS_CODE)));
    }


}
