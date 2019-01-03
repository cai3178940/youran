package com.youran.generate.service;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.*;
import com.alibaba.druid.sql.dialect.mysql.ast.MySqlKey;
import com.alibaba.druid.sql.dialect.mysql.ast.MySqlPrimaryKey;
import com.alibaba.druid.sql.dialect.mysql.ast.MySqlUnique;
import com.alibaba.druid.sql.parser.ParserException;
import com.youran.common.constant.BoolConst;
import com.youran.common.util.SafeUtil;
import com.youran.generate.constant.JFieldType;
import com.youran.generate.constant.MySqlType;
import com.youran.generate.constant.QueryType;
import com.youran.generate.exception.GenerateException;
import com.youran.generate.pojo.dto.MetaEntityAddDTO;
import com.youran.generate.pojo.dto.MetaFieldAddDTO;
import com.youran.generate.pojo.dto.MetaIndexAddDTO;
import com.youran.generate.pojo.dto.ReverseEngineeringDTO;
import com.youran.generate.pojo.po.MetaEntityPO;
import com.youran.generate.pojo.po.MetaFieldPO;
import com.youran.generate.pojo.po.MetaIndexPO;
import com.youran.generate.pojo.po.MetaProjectPO;
import com.youran.generate.util.MetadataUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    /**
     * 执行反向工程
     * @param dto
     */
    public void execute(ReverseEngineeringDTO dto) {
        MetaProjectPO project = metaProjectService.getProject(dto.getProjectId(),true);
        //MetaProjectPO project = null;

        List<SQLStatement> list = this.parse(dto);

        for (SQLStatement sqlStatement : list) {

            SQLCreateTableStatement createTableStatement = (SQLCreateTableStatement)sqlStatement;

            String tableName = cleanQuote(createTableStatement.getTableSource().getName().getSimpleName());

            String comment = cleanQuote(SafeUtil.getString(createTableStatement.getComment()));

            MetaEntityPO entity = createEntity(project, tableName, comment);
            //MetaEntityPO entity = null;

            SQLPrimaryKey primaryKey = createTableStatement.findPrimaryKey();
            if(primaryKey==null){
                throw new GenerateException("表【"+tableName+"】不存在主键");
            }
            if(primaryKey.getColumns().size()>1){
                throw new GenerateException("表【"+tableName+"】存在联合主键，反向工程暂不支持联合主键");
            }
            String pkFieldName = cleanQuote(primaryKey.getColumns().get(0).getExpr().toString());

            List<SQLTableElement> tableElementList = createTableStatement.getTableElementList();

            int orderNo = 0;
            // 缓存遍历到的字段
            Map<String,MetaFieldPO> fieldMap = new HashMap<>();
            for (SQLTableElement element : tableElementList) {
                // 创建字段
                if (element instanceof SQLColumnDefinition) {
                    orderNo+=10;
                    SQLColumnDefinition sqlColumnDefinition = (SQLColumnDefinition) element;
                    String fieldName = cleanQuote(sqlColumnDefinition.getNameAsString());
                    boolean pk = fieldName.equals(pkFieldName);
                    String fieldType = sqlColumnDefinition.getDataType().getName();
                    int fieldLength = 0;
                    int fieldScale = 0;
                    List<SQLExpr> arguments = sqlColumnDefinition.getDataType().getArguments();
                    if(CollectionUtils.isNotEmpty(arguments)){
                        fieldLength = SafeUtil.getInteger(arguments.get(0));
                        if(arguments.size()>=2){
                            fieldScale = SafeUtil.getInteger(arguments.get(1));
                        }
                    }
                    boolean autoIncrement = sqlColumnDefinition.isAutoIncrement();
                    boolean notNull = sqlColumnDefinition.containsNotNullConstaint();
                    String defaultValue = sqlColumnDefinition.getDefaultExpr()==null?"NULL":sqlColumnDefinition.getDefaultExpr().toString();
                    String desc = sqlColumnDefinition.getComment()==null?"":cleanQuote(sqlColumnDefinition.getComment().toString());

                    MetaFieldPO field = this.createField(entity, fieldName, fieldType,
                        fieldLength, fieldScale, pk,
                        autoIncrement, notNull, orderNo,
                        defaultValue, desc);

                    fieldMap.put(fieldName,field);
                    continue;
                }
                if (element instanceof MySqlPrimaryKey) {
                    continue;
                }
                // 创建索引
                if (element instanceof MySqlKey) {
                    boolean unique = (element instanceof MySqlUnique);
                    MySqlKey sqlKey = (MySqlKey) element;
                    String indexName = cleanQuote(sqlKey.getName().toString());

                    List<MetaFieldPO> fields = new ArrayList<>();
                    for (SQLSelectOrderByItem item : sqlKey.getColumns()) {
                        String columnName = cleanQuote(item.getExpr().toString());
                        fields.add(fieldMap.get(columnName));
                    }

                    this.createIndex(entity,indexName,unique,fields);

                }
            }



        }

    }

    /**
     * 创建实体
     * @param project
     * @param tableName
     * @param comment
     * @return
     */
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

    /**
     * 创建字段
     * @param entity
     * @param fieldName
     * @param fieldType
     * @param fieldLength
     * @param fieldScale
     * @param pk
     * @param autoIncrement
     * @param notNull
     * @param orderNo
     * @param defaultValue
     * @param desc
     * @return
     */
    private MetaFieldPO createField(MetaEntityPO entity,
                                    String fieldName, String fieldType,
                                    int fieldLength,int fieldScale,
                                    boolean pk,boolean autoIncrement,
                                    boolean notNull,int orderNo,
                                    String defaultValue,String desc){

        JFieldType jFieldType = MySqlType.mapperJFieldType(fieldType);

        MetaFieldAddDTO metaFieldDTO =  new MetaFieldAddDTO();

        metaFieldDTO.setEntityId(entity.getEntityId());
        metaFieldDTO.setAutoIncrement(autoIncrement?BoolConst.TRUE:BoolConst.FALSE);
        metaFieldDTO.setDefaultValue(defaultValue);
        metaFieldDTO.setDicType(null);
        metaFieldDTO.setEditType(null);
        metaFieldDTO.setFieldComment(desc);
        metaFieldDTO.setFieldDesc(desc);
        metaFieldDTO.setFieldExample("");
        metaFieldDTO.setFieldLength(fieldLength);
        metaFieldDTO.setFieldName(fieldName);
        metaFieldDTO.setFieldScale(fieldScale);
        metaFieldDTO.setFieldType(fieldType);
        metaFieldDTO.setInsert(autoIncrement?BoolConst.FALSE:BoolConst.TRUE);
        metaFieldDTO.setJfieldName(MetadataUtil.underlineToCamelCase(fieldName,false));
        metaFieldDTO.setJfieldType(jFieldType.getJavaType());
        metaFieldDTO.setList(BoolConst.TRUE);
        metaFieldDTO.setListSort(BoolConst.TRUE);
        metaFieldDTO.setNotNull(notNull?BoolConst.TRUE:BoolConst.FALSE);
        metaFieldDTO.setOrderNo(orderNo);
        metaFieldDTO.setPrimaryKey(pk?BoolConst.TRUE:BoolConst.FALSE);
        metaFieldDTO.setForeignKey(BoolConst.FALSE);
        metaFieldDTO.setForeignEntityId(null);
        metaFieldDTO.setForeignFieldId(null);
        metaFieldDTO.setQuery(pk?BoolConst.FALSE:BoolConst.TRUE);
        metaFieldDTO.setQueryType(QueryType.guessQueryType(jFieldType,fieldLength));
        metaFieldDTO.setShow(BoolConst.TRUE);
        metaFieldDTO.setUpdate(pk?BoolConst.FALSE:BoolConst.TRUE);
        metaFieldDTO.setSpecialField(null);

        return metaFieldService.save(metaFieldDTO);
    }


    /**
     * 创建索引
     * @param entity
     * @param indexName
     * @param unique
     * @param fields
     * @return
     */
    public MetaIndexPO createIndex(MetaEntityPO entity, String indexName, boolean unique, List<MetaFieldPO> fields){
        MetaIndexAddDTO metaIndexAddDTO = new MetaIndexAddDTO();
        metaIndexAddDTO.setIndexName(indexName);
        metaIndexAddDTO.setEntityId(entity.getEntityId());
        metaIndexAddDTO.setUnique(unique?BoolConst.TRUE:BoolConst.FALSE);
        metaIndexAddDTO.setUniqueCheck(unique?BoolConst.TRUE:BoolConst.FALSE);
        String fieldIds = fields.stream()
            .map(field -> field.getFieldId().toString())
            .collect(Collectors.joining(","));
        metaIndexAddDTO.setFieldIds(fieldIds);
        return metaIndexService.save(metaIndexAddDTO);
    }

    /**
     * 清理反引号
     * @param value
     * @return
     */
    private static String cleanQuote(String value){
        if(StringUtils.isBlank(value)){
            return value;
        }
        if((value.startsWith("`")&&value.endsWith("`"))
            ||
            (value.startsWith("'")&&value.endsWith("'"))){
            return value.substring(1,value.length()-1);
        }
        return value;
    }


}
