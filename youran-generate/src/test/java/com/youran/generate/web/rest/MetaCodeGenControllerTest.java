package com.youran.generate.web.rest;

import com.youran.generate.constant.*;
import com.youran.generate.help.GenerateHelper;
import com.youran.generate.pojo.dto.*;
import com.youran.generate.pojo.po.*;
import com.youran.generate.service.*;
import com.youran.generate.web.AbstractWebTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author: cbb
 * @date: 2017/5/14
 */
public class MetaCodeGenControllerTest extends AbstractWebTest {

    @Autowired
    protected MetaEntityService metaEntityService;
    @Autowired
    protected MetaFieldService metaFieldService;
    @Autowired
    protected MetaIndexService metaIndexService;
    @Autowired
    protected MetaConstService metaConstService;
    @Autowired
    protected MetaConstDetailService metaConstDetailService;
    @Autowired
    private GenerateHelper generateHelper;
    @Autowired
    private MetaProjectService metaProjectService;
    private MetaProjectPO bbsProject;
    private MetaEntityPO userEntity;
    private MetaFieldPO idField;
    private MetaFieldPO userNameField;
    private MetaFieldPO sexField;
    private MetaFieldPO birthdayField;
    private MetaFieldPO deletedField;
    private MetaFieldPO createdTimeField;
    private MetaFieldPO createdByField;
    private MetaFieldPO operatedTimeField;
    private MetaFieldPO operatedByField;
    private MetaFieldPO versionField;
    private MetaIndexPO usernameIndex;
    private MetaConstPO sexEnum;
    private MetaConstDetailPO manConstDetail;
    private MetaConstDetailPO womanConstDetail;

    /**
     * 构建元数据
     */
    @Before
    public void buildMetadata() {
        this.bbsProject = generateHelper.saveProject("com.cbb", "bbs", "com.cbb.bbs", "cbb");
        this.userEntity = this.saveUserEntity(this.bbsProject);
        this.idField = this.saveIdField(this.userEntity);
        this.userNameField = this.saveUserNameField(this.userEntity);
        this.sexField = this.saveSexField(this.userEntity);
        this.birthdayField = this.saveBirthdayField(this.userEntity);
        this.deletedField = generateHelper.saveDeletedField(this.userEntity);
        this.createdTimeField = generateHelper.saveCreatedTimeField(this.userEntity);
        this.createdByField = generateHelper.saveCreatedByField(this.userEntity);
        this.operatedTimeField = generateHelper.saveOperatedTimeField(this.userEntity);
        this.operatedByField = generateHelper.saveOperatedByField(this.userEntity);
        this.versionField = generateHelper.saveVersionField(this.userEntity);
        this.usernameIndex = this.saveUsernameIndex(this.userEntity, this.userNameField);
        this.sexEnum = this.saveSexEnum(this.bbsProject);
        this.manConstDetail = this.saveManConstDetail(this.sexEnum);
        this.womanConstDetail = this.saveWomanConstDetail(this.sexEnum);
    }

    //保存用户实体
    private MetaEntityPO saveUserEntity(MetaProjectPO project) {
        MetaEntityAddDTO dto = new MetaEntityAddDTO();
        dto.setProjectId(project.getProjectId());
        dto.setSchemaName("test");
        dto.setClassName("User");
        dto.setTableName("t_user");
        dto.setTitle("用户");
        dto.setDesc("用户");
        dto.setPageSign(1);
        return metaEntityService.save(dto);
    }

    //保存id字段
    private MetaFieldPO saveIdField(MetaEntityPO userEntity) {
        MetaFieldAddDTO dto = new MetaFieldAddDTO();
        dto.setEntityId(userEntity.getEntityId());
        dto.setAutoIncrement(1);
        dto.setDefaultValue(GenerateConst.METAFIELD_NULL_VALUE);
        dto.setDicType(null);
        dto.setEditType(null);
        dto.setFieldComment("主键id");
        dto.setFieldDesc("编号");
        dto.setFieldExample("1");
        dto.setFieldLength(10);
        dto.setFieldName("id");
        dto.setFieldScale(null);
        dto.setFieldType(MySqlType.INT);
        dto.setInsert(0);
        dto.setJfieldName("id");
        dto.setJfieldType(JFieldType.INTEGER.getJavaType());
        dto.setList(1);
        dto.setListSort(1);
        dto.setNotNull(1);
        dto.setOrderNo(1);
        dto.setPrimaryKey(1);
        dto.setForeignKey(0);
        dto.setQuery(0);
        dto.setQueryType(null);
        dto.setShow(1);
        dto.setUpdate(0);
        dto.setSpecialField(null);
        return metaFieldService.save(dto);
    }

    //保存username字段
    private MetaFieldPO saveUserNameField(MetaEntityPO userEntity) {
        MetaFieldAddDTO dto = new MetaFieldAddDTO();
        dto.setEntityId(userEntity.getEntityId());
        dto.setAutoIncrement(0);
        dto.setDefaultValue(GenerateConst.METAFIELD_NULL_VALUE);
        dto.setDicType(null);
        dto.setEditType(EditType.TEXT);
        dto.setFieldComment("用户名");
        dto.setFieldDesc("用户名");
        dto.setFieldExample("Lucy");
        dto.setFieldLength(40);
        dto.setFieldName("userName");
        dto.setFieldScale(null);
        dto.setFieldType(MySqlType.VARCHAR);
        dto.setInsert(1);
        dto.setJfieldName("userName");
        dto.setJfieldType(JFieldType.STRING.getJavaType());
        dto.setList(1);
        dto.setListSort(0);
        dto.setNotNull(1);
        dto.setOrderNo(2);
        dto.setPrimaryKey(0);
        dto.setForeignKey(0);
        dto.setQuery(1);
        dto.setQueryType(QueryType.LIKE);
        dto.setShow(1);
        dto.setUpdate(1);
        dto.setSpecialField(null);
        return metaFieldService.save(dto);
    }

    //保存sex字段
    private MetaFieldPO saveSexField(MetaEntityPO userEntity) {
        MetaFieldAddDTO dto = new MetaFieldAddDTO();
        dto.setEntityId(userEntity.getEntityId());
        dto.setAutoIncrement(0);
        dto.setDefaultValue(GenerateConst.METAFIELD_NULL_VALUE);
        dto.setDicType("SexEnum");
        dto.setEditType(EditType.SELECT);
        dto.setFieldComment("性别");
        dto.setFieldDesc("性别");
        dto.setFieldExample("1");
        dto.setFieldLength(1);
        dto.setFieldName("sex");
        dto.setFieldScale(null);
        dto.setFieldType(MySqlType.SMALLINT);
        dto.setInsert(1);
        dto.setJfieldName("sex");
        dto.setJfieldType(JFieldType.INTEGER.getJavaType());
        dto.setList(1);
        dto.setListSort(1);
        dto.setNotNull(1);
        dto.setOrderNo(3);
        dto.setPrimaryKey(0);
        dto.setForeignKey(0);
        dto.setQuery(1);
        dto.setQueryType(QueryType.EQ);
        dto.setShow(1);
        dto.setUpdate(1);
        dto.setSpecialField(null);
        return metaFieldService.save(dto);
    }


    public MetaFieldPO saveBirthdayField(MetaEntityPO userEntity) {
        MetaFieldAddDTO dto = new MetaFieldAddDTO();
        dto.setEntityId(userEntity.getEntityId());
        dto.setAutoIncrement(0);
        dto.setDefaultValue(GenerateConst.METAFIELD_NULL_VALUE);
        dto.setDicType(null);
        dto.setEditType(EditType.DATE);
        dto.setFieldComment("生日");
        dto.setFieldDesc("生日");
        dto.setFieldExample("1987-08-27");
        dto.setFieldLength(10);
        dto.setFieldName("birthday");
        dto.setFieldScale(null);
        dto.setFieldType(MySqlType.DATETIME);
        dto.setInsert(1);
        dto.setJfieldName("birthday");
        dto.setJfieldType(JFieldType.DATE.getJavaType());
        dto.setList(1);
        dto.setListSort(1);
        dto.setNotNull(0);
        dto.setOrderNo(4);
        dto.setPrimaryKey(0);
        dto.setForeignKey(0);
        dto.setQuery(1);
        dto.setQueryType(QueryType.BETWEEN);
        dto.setShow(1);
        dto.setUpdate(1);
        dto.setSpecialField(null);
        return metaFieldService.save(dto);
    }


    //保存用户名唯一索引
    private MetaIndexPO saveUsernameIndex(MetaEntityPO userEntity, MetaFieldPO userNameField) {
        MetaIndexAddDTO dto = new MetaIndexAddDTO();
        dto.setIndexName("i_user_username");
        dto.setUnique(1);
        dto.setUniqueCheck(1);
        dto.setFieldIds(userNameField.getFieldId().toString());
        dto.setEntityId(userEntity.getEntityId());
        return metaIndexService.save(dto);
    }

    //保存性别常量
    private MetaConstPO saveSexEnum(MetaProjectPO project) {
        MetaConstAddDTO dto = new MetaConstAddDTO();
        dto.setProjectId(project.getProjectId());
        dto.setConstName("SexEnum");
        dto.setConstRemark("性别");
        dto.setConstType(MetaConstType.INTEGER);
        return metaConstService.save(dto);
    }

    //保存男性常量值
    private MetaConstDetailPO saveManConstDetail(MetaConstPO metaConst) {
        MetaConstDetailAddDTO dto = new MetaConstDetailAddDTO();
        dto.setConstId(metaConst.getConstId());
        dto.setDetailName("MAN");
        dto.setDetailValue("1");
        dto.setDetailRemark("男");
        return metaConstDetailService.save(dto);
    }

    //保存女性常量值
    private MetaConstDetailPO saveWomanConstDetail(MetaConstPO metaConst) {
        MetaConstDetailAddDTO dto = new MetaConstDetailAddDTO();
        dto.setConstId(metaConst.getConstId());
        dto.setDetailName("WOMAN");
        dto.setDetailValue("2");
        dto.setDetailRemark("女");
        return metaConstDetailService.save(dto);
    }


    @Test
    public void genCode() throws Exception {
        restMockMvc.perform(get(getApiPath() + "/code_gen/genCode")
            .param("projectId", bbsProject.getProjectId() + ""))
            .andExpect(status().isOk());
    }

}
