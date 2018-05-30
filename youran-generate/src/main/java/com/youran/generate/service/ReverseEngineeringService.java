package com.youran.generate.service;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLCreateTableStatement;
import com.alibaba.druid.sql.parser.ParserException;
import com.youran.common.constant.BoolConst;
import com.youran.common.util.SafeUtil;
import com.youran.generate.exception.GenerateException;
import com.youran.generate.pojo.dto.MetaEntityAddDTO;
import com.youran.generate.pojo.dto.MetaFieldAddDTO;
import com.youran.generate.pojo.dto.ReverseEngineeringDTO;
import com.youran.generate.pojo.po.MetaEntityPO;
import com.youran.generate.pojo.po.MetaFieldPO;
import com.youran.generate.pojo.po.MetaProjectPO;
import com.youran.generate.util.MetadataUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Title: 反向工程Service
 * Description:
 * Author: cbb
 * Create Time: 2018/5/30 17:57
 */
@Service
public class ReverseEngineeringService {

    private final static Logger LOGGER = LoggerFactory.getLogger(ReverseEngineeringService.class);


    @Autowired
    private MetaProjectService metaProjectService;

    @Autowired
    private MetaEntityService metaEntityService;

    @Autowired
    private MetaFieldService metaFieldService;

    @Autowired
    private MetaIndexService metaIndexService;


    /**
     * 解析DDL
     * @param dto
     * @return
     */
    public List<SQLStatement> parse(ReverseEngineeringDTO dto) {

        List<SQLStatement> sqlStatements;
        try {
            sqlStatements = SQLUtils.parseStatements(dto.getDdl(), dto.getDbType());
        } catch (ParserException e) {
            LOGGER.warn("反向工程校验失败：{}",e);
            throw new GenerateException(e.getMessage());
        }
        if(CollectionUtils.isEmpty(sqlStatements)){
            throw new GenerateException("未找到有效DDL语句");
        }
        for (SQLStatement sqlStatement : sqlStatements) {
            if(!(sqlStatement instanceof SQLCreateTableStatement)){
                throw new GenerateException("只支持create table语句，请删除多余的sql");
            }
        }
        return sqlStatements;
    }

    public void execute(ReverseEngineeringDTO dto) {


        MetaProjectPO project = metaProjectService.getProject(dto.getProjectId(),true);



        List<SQLStatement> list = this.parse(dto);

        for (SQLStatement sqlStatement : list) {

            SQLCreateTableStatement createTableStatement = (SQLCreateTableStatement)sqlStatement;

            String tableName = cleanQuote(createTableStatement.getTableSource().getName().getSimpleName());

            String comment = cleanQuote(SafeUtil.getString(createTableStatement.getComment()));

            MetaEntityPO entity = createEntity(project, tableName, comment);


            createTableStatement.forEachColumn(sqlColumnDefinition -> {




            });




        }

    }

    private MetaEntityPO createEntity(MetaProjectPO project, String tableName, String comment) {
        MetaEntityAddDTO metaEntityDTO = new MetaEntityAddDTO();
        metaEntityDTO.setProjectId(project.getProjectId());
        metaEntityDTO.setSchemaName("");
        metaEntityDTO.setClassName(MetadataUtil.underlineToCamelCase(tableName,true));
        metaEntityDTO.setTableName(tableName);
        metaEntityDTO.setTitle(comment);
        metaEntityDTO.setDesc(comment);
        metaEntityDTO.setCommonCall(BoolConst.FALSE);
        metaEntityDTO.setPageSign(BoolConst.TRUE);
        return metaEntityService.save(metaEntityDTO);
    }


    private MetaFieldPO createField(MetaEntityPO entity){

        MetaFieldAddDTO metaFieldDTO =  new MetaFieldAddDTO();

        metaFieldDTO.setEntityId();
        metaFieldDTO.setAutoIncrement();
        metaFieldDTO.setDefaultValue();
        metaFieldDTO.setDicType();
        metaFieldDTO.setEditType();
        metaFieldDTO.setFieldComment();
        metaFieldDTO.setFieldDesc();
        metaFieldDTO.setFieldExample();
        metaFieldDTO.setFieldLength();
        metaFieldDTO.setFieldName();
        metaFieldDTO.setFieldScale();
        metaFieldDTO.setFieldType();
        metaFieldDTO.setInsert();
        metaFieldDTO.setJfieldName();
        metaFieldDTO.setJfieldType();
        metaFieldDTO.setList();
        metaFieldDTO.setListSort();
        metaFieldDTO.setNotNull();
        metaFieldDTO.setOrderNo();
        metaFieldDTO.setPrimaryKey();
        metaFieldDTO.setForeignKey();
        metaFieldDTO.setForeignEntityId();
        metaFieldDTO.setForeignFieldId();
        metaFieldDTO.setQuery();
        metaFieldDTO.setQueryType();
        metaFieldDTO.setShow();
        metaFieldDTO.setUpdate();
        metaFieldDTO.setSpecialField();


        return metaFieldService.save(metaFieldDTO);
    }


    private String cleanQuote(String value){
        if(StringUtils.isBlank(value)){
            return value;
        }
        return value.replaceAll("`","");
    }

    public static void main(String[] args) {
        ReverseEngineeringService service = new ReverseEngineeringService();
        ReverseEngineeringDTO dto = new ReverseEngineeringDTO();
        dto.setDdl("CREATE TABLE `meta_entity` (\n" +
            "  `entityId` int(11) AUTO_INCREMENT COMMENT '实体id',\n" +
            "  `projectId` int(11) NOT NULL COMMENT '所属项目id',\n" +
            "  `schemaName` varchar(20) DEFAULT NULL COMMENT '模式名',\n" +
            "  `className` varchar(50) NOT NULL COMMENT '类名',\n" +
            "  `tableName` varchar(50) NOT NULL COMMENT '表名',\n" +
            "  `title` varchar(25) NOT NULL COMMENT '标题',\n" +
            "  `desc` varchar(250) DEFAULT NULL COMMENT '实体描述',\n" +
            "  `commonCall` smallint(1) NOT NULL COMMENT '是否支持通用服务调用',\n" +
            "  `pageSign` smallint(1) DEFAULT NULL COMMENT '是否支持分页查询',\n" +
            "  `createDate` datetime DEFAULT NULL COMMENT '创建时间',\n" +
            "  `createBy` varchar(32) DEFAULT NULL COMMENT '创建人',\n" +
            "  `operateDate` datetime DEFAULT NULL COMMENT '操作时间',\n" +
            "  `operateBy` varchar(32) DEFAULT NULL COMMENT '操作人',\n" +
            "  `delSign` smallint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',\n" +
            "  `version` int(11) NOT NULL DEFAULT 0 COMMENT '乐观锁版本号',\n" +
            "  PRIMARY KEY (`entityId`),\n" +
            "  KEY `i_meta_entity_0` (`projectId`) USING BTREE,\n" +
            "  UNIQUE KEY `i_meta_entity_1` (`projectId`,`className`) USING BTREE\n" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='meta_entity';");
        dto.setDbType("mysql");

        service.execute(dto);
    }

}
