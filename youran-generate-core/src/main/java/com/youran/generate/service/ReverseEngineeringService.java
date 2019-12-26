package com.youran.generate.service;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.*;
import com.alibaba.druid.sql.dialect.mysql.ast.MySqlKey;
import com.alibaba.druid.sql.dialect.mysql.ast.MySqlPrimaryKey;
import com.alibaba.druid.sql.dialect.mysql.ast.MySqlUnique;
import com.alibaba.druid.sql.parser.ParserException;
import com.youran.common.constant.ErrorCode;
import com.youran.common.exception.BusinessException;
import com.youran.common.util.SafeUtil;
import com.youran.generate.constant.JFieldType;
import com.youran.generate.pojo.dto.MetaEntityAddDTO;
import com.youran.generate.pojo.dto.MetaFieldAddDTO;
import com.youran.generate.pojo.dto.MetaIndexAddDTO;
import com.youran.generate.pojo.dto.ReverseEngineeringDTO;
import com.youran.generate.pojo.po.MetaEntityPO;
import com.youran.generate.pojo.po.MetaFieldPO;
import com.youran.generate.pojo.po.MetaIndexPO;
import com.youran.generate.pojo.po.MetaProjectPO;
import com.youran.generate.util.GuessUtil;
import com.youran.generate.util.SwitchCaseUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 反向工程Service
 *
 * @author: cbb
 * @date: 2018/5/30
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
     * 清理反引号
     *
     * @param value
     * @return
     */
    private static String cleanQuote(String value) {
        if (StringUtils.isBlank(value)) {
            return value;
        }
        boolean surroundedByBackQuotes = value.startsWith("`") && value.endsWith("`");
        boolean surroundedBySingleQuotes = value.startsWith("'") && value.endsWith("'");
        if (surroundedByBackQuotes || surroundedBySingleQuotes) {
            return value.substring(1, value.length() - 1);
        }
        return value;
    }

    /**
     * 解析DDL
     *
     * @param dto
     * @return
     */
    public List<SQLStatement> parse(ReverseEngineeringDTO dto) {
        List<SQLStatement> sqlStatements;
        try {
            sqlStatements = SQLUtils.parseStatements(dto.getDdl(), dto.getDbType());
        } catch (ParserException e) {
            LOGGER.warn("反向工程校验失败：{}", e);
            throw new BusinessException(ErrorCode.BAD_PARAMETER, "DDL解析失败:" + e.getMessage());
        }
        if (CollectionUtils.isEmpty(sqlStatements)) {
            throw new BusinessException(ErrorCode.BAD_PARAMETER, "未找到有效DDL语句");
        }
        for (SQLStatement sqlStatement : sqlStatements) {
            if (!(sqlStatement instanceof SQLCreateTableStatement)) {
                throw new BusinessException(ErrorCode.BAD_PARAMETER, "只支持create table语句，请删除多余的sql");
            }
        }
        return sqlStatements;
    }

    /**
     * 执行反向工程
     *
     * @param dto
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public void execute(ReverseEngineeringDTO dto) {
        MetaProjectPO project = metaProjectService.getAndCheckProject(dto.getProjectId());
        List<SQLStatement> list = this.parse(dto);
        for (SQLStatement sqlStatement : list) {
            this.saveEntityFromSqlStatement(project, (SQLCreateTableStatement) sqlStatement);
        }
    }

    /**
     * 从单个建表语句中创建实体
     *
     * @param project
     * @param sqlStatement
     */
    private void saveEntityFromSqlStatement(MetaProjectPO project, SQLCreateTableStatement sqlStatement) {
        SQLCreateTableStatement createTableStatement = sqlStatement;
        // 解析表名
        String tableName = this.parseTableName(createTableStatement);
        if (StringUtils.isBlank(tableName)) {
            return;
        }
        String comment = cleanQuote(SafeUtil.getString(createTableStatement.getComment()));
        MetaEntityPO entity = createEntity(project, tableName, comment);
        // 获取单独声明的主键
        String pkAlone = this.getPkAlone(createTableStatement);
        List<SQLTableElement> tableElementList = createTableStatement.getTableElementList();
        int orderNo = 0;
        // 缓存遍历到的字段
        Map<String, MetaFieldPO> fieldMap = new HashMap<>(32);
        for (SQLTableElement element : tableElementList) {
            // 创建字段
            if (element instanceof SQLColumnDefinition) {
                orderNo += 10;
                SQLColumnDefinition sqlColumnDefinition = (SQLColumnDefinition) element;
                String fieldName = cleanQuote(sqlColumnDefinition.getNameAsString());
                boolean pk = this.isPkField(sqlColumnDefinition, pkAlone);
                String fieldType = sqlColumnDefinition.getDataType().getName();
                int fieldLength = 0;
                int fieldScale = 0;
                List<SQLExpr> arguments = sqlColumnDefinition.getDataType().getArguments();
                if (CollectionUtils.isNotEmpty(arguments)) {
                    fieldLength = SafeUtil.getInteger(arguments.get(0));
                    if (arguments.size() >= 2) {
                        fieldScale = SafeUtil.getInteger(arguments.get(1));
                    }
                }
                boolean autoIncrement = sqlColumnDefinition.isAutoIncrement();
                boolean notNull = sqlColumnDefinition.containsNotNullConstaint();
                if (pk) {
                    notNull = true;
                }
                String defaultValue = sqlColumnDefinition.getDefaultExpr() == null ? "NULL" : sqlColumnDefinition.getDefaultExpr().toString();
                String desc = sqlColumnDefinition.getComment() == null ? "" : cleanQuote(sqlColumnDefinition.getComment().toString());

                MetaFieldPO field = this.createField(entity, fieldName, fieldType,
                    fieldLength, fieldScale, pk,
                    autoIncrement, notNull, orderNo,
                    defaultValue, desc);
                fieldMap.put(fieldName, field);
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
                this.createIndex(entity, indexName, unique, fields);
            }
        }
    }

    /**
     * 解析表名
     *
     * @param createTableStatement
     * @return
     */
    private String parseTableName(SQLCreateTableStatement createTableStatement) {
        return cleanQuote(createTableStatement.getTableSource().getName().getSimpleName());
    }

    /**
     * 获取单独声明的主键名称
     *
     * @param createTableStatement
     * @return
     */
    private String getPkAlone(SQLCreateTableStatement createTableStatement) {
        SQLPrimaryKey primaryKey = createTableStatement.findPrimaryKey();
        if (primaryKey != null) {
            if (primaryKey.getColumns().size() > 1) {
                throw new BusinessException(ErrorCode.BAD_PARAMETER,
                    "表【" + this.parseTableName(createTableStatement) + "】存在联合主键，反向工程暂不支持联合主键");
            }
            return cleanQuote(primaryKey.getColumns().get(0).getExpr().toString());
        }
        return null;
    }

    /**
     * 判断是否主键字段
     *
     * @param sqlColumnDefinition
     * @param pkAlone
     * @return
     */
    private boolean isPkField(SQLColumnDefinition sqlColumnDefinition, String pkAlone) {
        if (sqlColumnDefinition.isPrimaryKey()) {
            return true;
        }
        if (StringUtils.isNotBlank(pkAlone)) {
            String fieldName = cleanQuote(sqlColumnDefinition.getNameAsString());
            if (pkAlone.equals(fieldName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 创建实体
     *
     * @param project
     * @param tableName
     * @param comment
     * @return
     */
    private MetaEntityPO createEntity(MetaProjectPO project, String tableName, String comment) {
        MetaEntityAddDTO metaEntityDTO = new MetaEntityAddDTO();
        metaEntityDTO.setProjectId(project.getProjectId());
        metaEntityDTO.setSchemaName("");
        metaEntityDTO.setClassName(SwitchCaseUtil.underlineToCamelCase(tableName, true));
        metaEntityDTO.setTableName(tableName);
        metaEntityDTO.setTitle(StringUtils.abbreviate(comment, 25));
        metaEntityDTO.setDesc(StringUtils.abbreviate(comment, 250));
        metaEntityDTO.setPageSign(true);
        return metaEntityService.save(metaEntityDTO);
    }

    /**
     * 创建字段
     *
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
                                    int fieldLength, int fieldScale,
                                    boolean pk, boolean autoIncrement,
                                    boolean notNull, int orderNo,
                                    String defaultValue, String desc) {
        JFieldType jFieldType = GuessUtil.guessJFieldType(fieldName, fieldType, fieldLength);
        String specialField = GuessUtil.guessSpecialField(fieldName, jFieldType);
        // 如果不存在字段描述，则使用字段名替代
        if (StringUtils.isBlank(desc)) {
            desc = fieldName;
        }
        MetaFieldAddDTO metaFieldDTO = new MetaFieldAddDTO();
        metaFieldDTO.setEntityId(entity.getEntityId());
        metaFieldDTO.setAutoIncrement(autoIncrement);
        metaFieldDTO.setDefaultValue(defaultValue);
        metaFieldDTO.setDicType(null);
        metaFieldDTO.setEditType(GuessUtil.guessEditType(jFieldType, fieldType));
        metaFieldDTO.setFieldComment(StringUtils.abbreviate(desc, 200));
        metaFieldDTO.setFieldDesc(StringUtils.abbreviate(desc, 40));
        metaFieldDTO.setFieldExample(GuessUtil.guessFieldExample(fieldName, jFieldType, fieldLength));
        metaFieldDTO.setFieldLength(fieldLength);
        metaFieldDTO.setFieldName(fieldName);
        metaFieldDTO.setFieldScale(fieldScale);
        metaFieldDTO.setFieldType(fieldType);
        metaFieldDTO.setInsert(!autoIncrement);
        metaFieldDTO.setJfieldName(SwitchCaseUtil.underlineToCamelCase(fieldName, false));
        metaFieldDTO.setJfieldType(jFieldType.getJavaType());
        metaFieldDTO.setList(true);
        metaFieldDTO.setListSort(false);
        metaFieldDTO.setOrderNo(orderNo);
        metaFieldDTO.setPrimaryKey(pk);
        metaFieldDTO.setForeignKey(false);
        metaFieldDTO.setNotNull(notNull);
        metaFieldDTO.setForeignEntityId(null);
        metaFieldDTO.setForeignFieldId(null);
        metaFieldDTO.setQuery(!pk);
        metaFieldDTO.setQueryType(GuessUtil.guessQueryType(jFieldType, fieldLength));
        metaFieldDTO.setShow(true);
        metaFieldDTO.setUpdate(!pk);
        metaFieldDTO.setSpecialField(specialField);

        return metaFieldService.save(metaFieldDTO);
    }

    /**
     * 创建索引
     *
     * @param entity
     * @param indexName
     * @param unique
     * @param fields
     * @return
     */
    public MetaIndexPO createIndex(MetaEntityPO entity, String indexName, boolean unique, List<MetaFieldPO> fields) {
        MetaIndexAddDTO metaIndexAddDTO = new MetaIndexAddDTO();
        metaIndexAddDTO.setIndexName(indexName);
        metaIndexAddDTO.setEntityId(entity.getEntityId());
        metaIndexAddDTO.setUnique(unique);
        metaIndexAddDTO.setUniqueCheck(unique);
        String fieldIds = fields.stream()
            .map(field -> field.getFieldId().toString())
            .collect(Collectors.joining(","));
        metaIndexAddDTO.setFieldIds(fieldIds);
        return metaIndexService.save(metaIndexAddDTO);
    }


}
