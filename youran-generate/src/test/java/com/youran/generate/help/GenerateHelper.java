package com.youran.generate.help;

import com.youran.common.constant.BoolConst;
import com.youran.generate.constant.*;
import com.youran.generate.data.*;
import com.youran.generate.pojo.dto.*;
import com.youran.generate.pojo.po.*;
import com.youran.generate.service.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Title:封装单元测试使用的元数据基本操作
 * Description:
 * Author: cbb
 * Create Time:2017/5/18 11:33
 */
@Component
public class GenerateHelper{

    @Autowired
    private MetaProjectService metaProjectService;
    @Autowired
    private MetaEntityService metaEntityService;
    @Autowired
    private MetaFieldService metaFieldService;
    @Autowired
    private MetaIndexService metaIndexService;
    @Autowired
    private MetaConstService metaConstService;
    @Autowired
    private MetaManyToManyService metaManyToManyService;
    @Autowired
    private MetaConstDetailService metaConstDetailService;
    @Autowired
    private MetaCascadeExtService metaCascadeExtService;


    /**
     * 保存项目示例
     * @return
     */
    public MetaProjectPO saveProjectExample(){
        MetaProjectAddDTO addDTO = MetaProjectData.getAddDTO();
        return metaProjectService.save(addDTO);
    }
    /**
     * 保存实体示例
     * @return
     */
    public MetaEntityPO saveEntityExample(Integer projectId, int i){
        MetaEntityAddDTO addDTO = MetaEntityData.getAddDTO(projectId,i);
        return metaEntityService.save(addDTO);
    }

    /**
     * 保存字段示例
     * @return
     */
    public MetaFieldPO saveFieldExample(Integer entityId){
        MetaFieldAddDTO addDTO = MetaFieldData.getAddDTO(entityId);
        return metaFieldService.save(addDTO);
    }

    /**
     * 保存级联扩展示例
     * @return
     */
    public MetaCascadeExtPO saveCascadeExtExample(Integer fieldId, Integer entityId,Integer cascadeFieldId, Integer cascadeEntityId){
        MetaCascadeExtAddDTO addDTO = MetaCascadeExtData.getAddDTO(fieldId,entityId,cascadeFieldId,cascadeEntityId);
        return metaCascadeExtService.save(addDTO);
    }

    /**
     * 保存多对多关联示例
     * @return
     */
    public MetaManyToManyPO saveManyToManyExample(Integer projectId, Integer entityId1, Integer entityId2){
        MetaManyToManyAddDTO addDTO = MetaManyToManyData.getAddDTO(projectId,entityId1,entityId2);
        return metaManyToManyService.save(addDTO);
    }

    /**
     * 保存索引示例
     * @return
     */
    public MetaIndexPO saveIndexExample(Integer... fieldIds){
        MetaIndexAddDTO addDTO = MetaIndexData.getAddDTO(fieldIds);
        return metaIndexService.save(addDTO);
    }

    /**
     * 保存常量示例
     * @return
     */
    public MetaConstPO saveConstExample(Integer projectId){
        MetaConstAddDTO addDTO = MetaConstData.getAddDTO(projectId);
        return metaConstService.save(addDTO);
    }
    /**
     * 保存常量值示例
     * @return
     */
    public MetaConstDetailPO saveConstDetailExample(Integer constId){
        MetaConstDetailAddDTO addDTO = MetaConstDetailData.getAddDTO(constId);
        return metaConstDetailService.save(addDTO);
    }






    //保存createdTime字段
    public MetaFieldPO saveCreatedTimeField(MetaEntityPO entity) {
        MetaFieldAddDTO dto = newMetaFieldAddDTO(entity);
        dto.setNotNull(1);
        dto.setFieldName("createdTime");
        dto.setFieldType(MySqlType.DATETIME);
        dto.setFieldComment("创建日期");
        dto.setFieldExample("2017-05-22 00:00:00");
        dto.setFieldLength(0);
        dto.setFieldDesc("创建日期");
        dto.setJfieldName("createdTime");
        dto.setJfieldType(JFieldType.DATE.getJavaType());
        dto.setEditType(EditType.DATE);
        dto.setOrderNo(1001);
        dto.setInsert(0);
        dto.setQuery(0);
        dto.setQueryType(null);
        dto.setList(1);
        dto.setListSort(1);
        dto.setShow(1);
        dto.setUpdate(0);
        dto.setSpecialField(MetaSpecialField.CREATED_TIME);
        MetaFieldPO metaField = metaFieldService.save(dto);
        entity.addField(metaField);
        return metaField;
    }

    public MetaFieldPO saveCreatedByField(MetaEntityPO entity) {
        MetaFieldAddDTO dto = newMetaFieldAddDTO(entity);
        dto.setNotNull(1);
        dto.setFieldName("createdBy");
        dto.setFieldType(MySqlType.VARCHAR);
        dto.setFieldComment("创建人");
        dto.setFieldLength(50);
        dto.setFieldDesc("创建人");
        dto.setFieldExample("admin");
        dto.setJfieldName("createdBy");
        dto.setJfieldType(JFieldType.STRING.getJavaType());
        dto.setEditType(EditType.TEXT);
        dto.setOrderNo(1002);
        dto.setInsert(0);
        dto.setQuery(0);
        dto.setQueryType(null);
        dto.setList(1);
        dto.setListSort(0);
        dto.setShow(1);
        dto.setUpdate(0);
        dto.setSpecialField(MetaSpecialField.CREATED_BY);
        MetaFieldPO metaField = metaFieldService.save(dto);
        entity.addField(metaField);
        return metaField;
    }

    public MetaFieldPO saveOperatedTimeField(MetaEntityPO entity) {
        MetaFieldAddDTO dto = newMetaFieldAddDTO(entity);
        dto.setNotNull(1);
        dto.setFieldName("operatedTime");
        dto.setFieldType(MySqlType.DATETIME);
        dto.setFieldComment("操作日期");
        dto.setFieldLength(0);
        dto.setFieldDesc("操作日期");
        dto.setFieldExample("2017-05-22 00:00:00");
        dto.setJfieldName("operatedTime");
        dto.setJfieldType(JFieldType.DATE.getJavaType());
        dto.setEditType(EditType.DATE);
        dto.setOrderNo(1003);
        dto.setInsert(0);
        dto.setQuery(0);
        dto.setQueryType(null);
        dto.setList(1);
        dto.setListSort(1);
        dto.setShow(1);
        dto.setUpdate(0);
        dto.setSpecialField(MetaSpecialField.OPERATED_TIME);
        MetaFieldPO metaField = metaFieldService.save(dto);
        entity.addField(metaField);
        return metaField;
    }

    public MetaFieldPO saveOperatedByField(MetaEntityPO entity) {
        MetaFieldAddDTO dto = newMetaFieldAddDTO(entity);
        dto.setNotNull(1);
        dto.setFieldName("operatedBy");
        dto.setFieldType(MySqlType.VARCHAR);
        dto.setFieldComment("操作人");
        dto.setFieldLength(50);
        dto.setFieldDesc("操作人");
        dto.setFieldExample("admin");
        dto.setJfieldName("operatedBy");
        dto.setJfieldType(JFieldType.STRING.getJavaType());
        dto.setEditType(EditType.TEXT);
        dto.setOrderNo(1004);
        dto.setInsert(0);
        dto.setQuery(0);
        dto.setQueryType(null);
        dto.setList(1);
        dto.setListSort(0);
        dto.setShow(1);
        dto.setUpdate(0);
        dto.setSpecialField(MetaSpecialField.OPERATED_BY);
        MetaFieldPO metaField = metaFieldService.save(dto);
        entity.addField(metaField);
        return metaField;
    }

    //保存version字段
    public MetaFieldPO saveVersionField(MetaEntityPO entity) {
        MetaFieldAddDTO dto = newMetaFieldAddDTO(entity);
        dto.setDefaultValue("1");
        dto.setNotNull(1);
        dto.setFieldName("version");
        dto.setFieldType(MySqlType.INT);
        dto.setFieldComment("乐观锁版本号");
        dto.setFieldExample("1");
        dto.setFieldLength(12);
        dto.setFieldDesc("乐观锁版本号");
        dto.setJfieldName("version");
        dto.setJfieldType(JFieldType.INTEGER.getJavaType());
        dto.setEditType(null);
        dto.setOrderNo(1005);
        dto.setInsert(0);
        dto.setQuery(0);
        dto.setQueryType(null);
        dto.setList(0);
        dto.setListSort(0);
        dto.setShow(0);
        dto.setUpdate(0);
        dto.setSpecialField(MetaSpecialField.VERSION);
        MetaFieldPO metaField = metaFieldService.save(dto);
        entity.addField(metaField);
        return metaField;
    }

    //保存deleted字段
    public MetaFieldPO saveDeletedField(MetaEntityPO entity) {
        MetaFieldAddDTO dto = newMetaFieldAddDTO(entity);
        dto.setDefaultValue("0");
        dto.setNotNull(1);
        dto.setFieldName("deleted");
        dto.setFieldType(MySqlType.SMALLINT);
        dto.setFieldComment("是否删除");
        dto.setFieldExample("0");
        dto.setFieldLength(1);
        dto.setFieldDesc("是否删除");
        dto.setJfieldName("deleted");
        dto.setJfieldType(JFieldType.INTEGER.getJavaType());
        dto.setEditType(null);
        dto.setOrderNo(1006);
        dto.setInsert(0);
        dto.setQuery(0);
        dto.setQueryType(null);
        dto.setList(0);
        dto.setListSort(0);
        dto.setShow(0);
        dto.setUpdate(0);
        dto.setSpecialField(MetaSpecialField.DELETED);
        MetaFieldPO metaField = metaFieldService.save(dto);
        entity.addField(metaField);
        return metaField;
    }

    //保存主键id字段
    public MetaFieldPO saveIdField(MetaEntityPO entity,String fieldName,String fieldDesc,String jfieldType) {
        MetaFieldAddDTO dto = newMetaFieldAddDTO(entity);
        dto.setAutoIncrement(1);
        dto.setEditType(null);
        dto.setFieldComment(fieldDesc);
        dto.setFieldDesc(fieldDesc);
        dto.setFieldExample("1");
        dto.setFieldName(fieldName);
        dto.setInsert(0);
        dto.setJfieldName(fieldName);
        dto.setJfieldType(jfieldType);
        if(JFieldType.LONG.equals(jfieldType)){
            dto.setFieldType(MySqlType.BIGINT);
            dto.setFieldLength(20);
        }else if(JFieldType.INTEGER.equals(jfieldType)){
            dto.setFieldType(MySqlType.INT);
            dto.setFieldLength(10);
        }else{
            throw new RuntimeException("jfieldType error");
        }
        dto.setList(1);
        dto.setListSort(1);
        dto.setNotNull(1);
        dto.setOrderNo(1);
        dto.setPrimaryKey(1);
        dto.setQuery(0);
        dto.setQueryType(null);
        dto.setShow(1);
        dto.setUpdate(0);
        dto.setSpecialField(null);
        MetaFieldPO metaField = metaFieldService.save(dto);
        entity.addField(metaField);
        return metaField;
    }

    public MetaFieldAddDTO newMetaFieldAddDTO(MetaEntityPO entity){
        MetaFieldAddDTO dto = new MetaFieldAddDTO();
        dto.setEntityId(entity.getEntityId());
        dto.setPrimaryKey(0);//默认非主键
        dto.setForeignKey(0);
        dto.setAutoIncrement(0);//默认非自动递增
        dto.setDefaultValue(GenerateConst.METAFIELD_NULL_VALUE);//默认值为null
        dto.setNotNull(0);//默认可为空
        dto.setEditType(EditType.TEXT);
        dto.setFieldScale(null);
        dto.setDicType(null);
        dto.setInsert(1);
        dto.setQuery(1);
        dto.setQueryType(QueryType.EQ);
        dto.setList(1);
        dto.setListSort(0);
        dto.setShow(1);
        dto.setUpdate(1);
        dto.setSpecialField(null);
        return dto;
    }

    public MetaEntityAddDTO newMetaEntityAddDTO(MetaProjectPO project){
        MetaEntityAddDTO dto = new MetaEntityAddDTO();
        dto.setProjectId(project.getProjectId());
        dto.setSchemaName("");
        dto.setCommonCall(0);
        return dto;
    }

    public MetaIndexAddDTO newMetaIndexAddDTO(MetaEntityPO entity){
        MetaIndexAddDTO dto = new MetaIndexAddDTO();
        dto.setUnique(0);
        dto.setEntityId(entity.getEntityId());
        return dto;
    }

    public MetaIndexPO saveCommonIndex(MetaEntityPO entity,String indexName,MetaFieldPO ...fields){
        MetaIndexAddDTO dto = newMetaIndexAddDTO(entity);
        dto.setIndexName(indexName);
        String ids = Arrays.stream(fields)
                .map(field -> field.getFieldId().toString())
                .reduce((id, id2) -> id+","+id2)
                .get();
        dto.setFieldIds(ids);
        return metaIndexService.save(dto);
    }

    public MetaIndexPO saveUniqueIndex(MetaEntityPO entity,String indexName,MetaFieldPO ...fields){
        MetaIndexAddDTO dto = newMetaIndexAddDTO(entity);
        dto.setIndexName(indexName);
        String ids = Arrays.stream(fields)
                .map(field -> field.getFieldId().toString())
                .reduce((id, id2) -> id+","+id2)
                .get();
        dto.setFieldIds(ids);
        dto.setUnique(1);
        return metaIndexService.save(dto);
    }

    public MetaConstPO saveConst(MetaProjectPO project,String constName,String constRemark,int constType) {
        MetaConstAddDTO dto = new MetaConstAddDTO();
        dto.setProjectId(project.getProjectId());
        dto.setConstName(constName);
        dto.setConstRemark(constRemark);
        dto.setConstType(constType);
        MetaConstPO metaConst = metaConstService.save(dto);
        project.addConst(metaConst);
        return metaConst;
    }
    public MetaConstPO saveConstEnum(MetaProjectPO project,String constName,String constRemark,int constType) {
        MetaConstAddDTO dto = new MetaConstAddDTO();
        dto.setProjectId(project.getProjectId());
        dto.setConstName(constName);
        dto.setConstRemark(constRemark);
        dto.setConstType(constType);
        MetaConstPO metaConst = metaConstService.save(dto);
        project.addConst(metaConst);
        return metaConst;
    }

    public MetaConstDetailPO saveConstDetail(MetaConstPO metaConst,String detailName,String detailValue,String detailRemark) {
        MetaConstDetailAddDTO dto = new MetaConstDetailAddDTO();
        dto.setConstId(metaConst.getConstId());
        dto.setDetailName(detailName);
        dto.setDetailValue(detailValue);
        dto.setDetailRemark(detailRemark);
        MetaConstDetailPO detail = metaConstDetailService.save(dto);
        metaConst.addDetail(detail);
        return detail;
    }

    /**
     * 保存可选长字符字段，如备注字段
     * @param entity
     * @param fieldName
     * @param desc
     * @param length
     * @param orderNo
     * @return
     */
    public MetaFieldPO saveLongStringField(MetaEntityPO entity,String fieldName,String desc,int length,int orderNo) {
        MetaFieldAddDTO dto = newMetaFieldAddDTO(entity);
        dto.setEntityId(entity.getEntityId());
        dto.setFieldComment(desc);
        dto.setFieldDesc(desc);
        dto.setFieldExample("");
        dto.setFieldLength(length);
        dto.setFieldName(fieldName);
        dto.setFieldType(MySqlType.VARCHAR);
        dto.setJfieldName(fieldName);
        dto.setJfieldType(JFieldType.STRING.getJavaType());
        dto.setOrderNo(orderNo);
        dto.setQuery(0);
        dto.setQueryType(null);
        MetaFieldPO metaField = metaFieldService.save(dto);
        entity.addField(metaField);
        return metaField;
    }


    public MetaFieldPO saveFlagField(MetaEntityPO entity, String fieldName,String desc,int orderNo) {
        MetaFieldAddDTO dto = newMetaFieldAddDTO(entity);
        dto.setDicType("com.youran.common.constant.BoolConst");
        dto.setDefaultValue("0");
        dto.setNotNull(1);
        dto.setFieldName(fieldName);
        dto.setFieldType(MySqlType.SMALLINT);
        dto.setFieldComment(desc+" 1是 0否");
        dto.setFieldExample("1");
        dto.setFieldLength(1);
        dto.setFieldDesc(desc);
        dto.setJfieldName(fieldName);
        dto.setJfieldType(JFieldType.INTEGER.getJavaType());
        dto.setEditType(EditType.SELECT);
        dto.setOrderNo(orderNo);
        MetaFieldPO metaField = metaFieldService.save(dto);
        entity.addField(metaField);
        return metaField;
    }

    public MetaFieldPO saveEnumField(MetaEntityPO entity, String fieldName,String desc,MetaConstPO enumConst,int orderNo) {
        String comment = enumConst.getDetailList()
                .stream()
                .map(d ->" " + d.getDetailValue() + d.getDetailRemark())
                .reduce(String::concat)
                .get();
        MetaFieldAddDTO dto = newMetaFieldAddDTO(entity);
        dto.setDicType(enumConst.getConstName());
        dto.setEditType(EditType.SELECT);
        dto.setFieldComment(desc+comment);
        dto.setFieldDesc(desc);
        dto.setFieldExample(enumConst.getDetailList().get(0).getDetailValue());
        dto.setFieldLength(2);
        dto.setFieldName(fieldName);
        dto.setFieldType(MySqlType.SMALLINT);
        dto.setJfieldName(fieldName);
        dto.setJfieldType(JFieldType.INTEGER.getJavaType());
        dto.setNotNull(1);
        dto.setOrderNo(orderNo);
        MetaFieldPO metaField = metaFieldService.save(dto);
        entity.addField(metaField);
        return metaField;
    }

    public MetaFieldPO saveEnumField(MetaEntityPO entity, MetaConstPO enumConst,int orderNo) {
        String constName = enumConst.getConstName();
        String fieldName = StringUtils.uncapitalize(constName);
        return this.saveEnumField(entity,fieldName,enumConst.getConstRemark(),enumConst,orderNo);
    }



    public MetaFieldPO saveForeignKey(MetaEntityPO entity,String fieldName,String desc,boolean bigint,boolean notnull,int orderNo) {
        MetaFieldAddDTO dto = newMetaFieldAddDTO(entity);
        dto.setUpdate(0);
        dto.setFieldComment(desc);
        dto.setFieldDesc(desc);
        dto.setFieldExample("1");
        dto.setFieldName(fieldName);
        dto.setJfieldName(fieldName);
        if(bigint) {
            dto.setFieldLength(20);
            dto.setFieldType(MySqlType.BIGINT);
            dto.setJfieldType(JFieldType.LONG.getJavaType());
        }else{
            dto.setFieldLength(10);
            dto.setFieldType(MySqlType.INT);
            dto.setJfieldType(JFieldType.INTEGER.getJavaType());
        }
        if(notnull){
            dto.setNotNull(1);
        }
        dto.setOrderNo(orderNo);
        MetaFieldPO metaField = metaFieldService.save(dto);
        entity.addField(metaField);
        return metaField;
    }


    public MetaProjectPO saveProject(String groupId,String projectName, String packageName, String author) {
        MetaProjectAddDTO dto = new MetaProjectAddDTO();
        dto.setGroupId(groupId);
        dto.setPackageName(packageName);
        dto.setProjectName(projectName);
        dto.setAuthor(author);
        dto.setRemote(BoolConst.FALSE);
        return metaProjectService.save(dto);
    }


    public void saveCreatedOperatedDeletedVersion(MetaEntityPO entity){
        this.saveCreatedByField(entity);
        this.saveCreatedTimeField(entity);
        this.saveOperatedByField(entity);
        this.saveOperatedTimeField(entity);
        this.saveVersionField(entity);
        this.saveDeletedField(entity);
    }

    public MetaEntityPO saveMetaEntity(MetaProjectPO project,String className,String tableName,String title){
        MetaEntityAddDTO dto = newMetaEntityAddDTO(project);
        dto.setProjectId(project.getProjectId());
        dto.setSchemaName("");
        dto.setClassName(className);
        dto.setTableName(tableName);
        dto.setTitle(title);
        dto.setDesc(title);
        dto.setPageSign(1);
        MetaEntityPO metaEntity = metaEntityService.save(dto);
        project.addEntity(metaEntity);
        return metaEntity;
    }

}
