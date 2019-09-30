package com.youran.generate.pojo.po;

import com.youran.common.constant.BoolConst;
import com.youran.common.constant.ErrorCode;
import com.youran.common.exception.BusinessException;

import java.util.List;
import java.util.Objects;

/**
 * <p>Title:多对多关联关系</p>
 * <p>Description:</p>
 * @author: cbb
 * @date: 2017/7/4
 */
public class MetaManyToManyPO extends BasePO {

    private Integer mtmId;
    /**
     * 所属项目id
     */
    private Integer projectId;

    /**
     * 关联表名
     */
    private String tableName;

    /**
     * 模式名
     */
    private String schemaName;

    /**
     * 关联描述
     */
    private String desc;
    /**
     * 实体A的id
     */
    private Integer entityId1;
    /**
     * 实体B的id
     */
    private Integer entityId2;

    /**
     * 实体A是否持有B引用
     */
    private Integer holdRefer1;
    /**
     * 实体B是否持有A引用
     */
    private Integer holdRefer2;

    /**
     * 实体A对应多对多关联表的id字段名
     * 2019-08-07新增
     */
    private String entityIdField1;

    /**
     * 实体B对应多对多关联表的id字段名
     * 2019-08-07新增
     */
    private String entityIdField2;

    /**
     * 是否需要自增id字段
     * 2019-08-07新增
     */
    private Boolean needId;
    /**
     * id字段是否bigint
     * 2019-08-07新增
     */
    private Boolean bigId;
    /**
     * 引用实体A
     */
    private MetaEntityPO refer1;
    /**
     * 引用实体B
     */
    private MetaEntityPO refer2;

    /**
     * 实体A持有的级联扩展列表
     */
    private List<MetaMtmCascadeExtPO> cascadeExtList1;

    /**
     * 实体B持有的级联扩展列表
     */
    private List<MetaMtmCascadeExtPO> cascadeExtList2;

    /**
     * 外键字段别名A-sql字段
     */
    private String fkAliasForSql1;
    /**
     * 外键字段别名B-sql字段
     */
    private String fkAliasForSql2;

    /**
     * 外键字段别名A-java字段
     */
    private String fkAliasForJava1;
    /**
     * 外键字段别名B-java字段
     */
    private String fkAliasForJava2;

    /**
     * 根据传入的实体id获取另一方实体【还没用到】
     * @param entityId
     * @return
     */
    @Deprecated
    public MetaEntityPO getOtherRefer(Integer entityId){
        if(Objects.equals(entityId,entityId1)){
            return refer2;
        }else if(Objects.equals(entityId,entityId2)){
            return refer1;
        }
        throw new BusinessException(ErrorCode.INNER_DATA_ERROR,
            "获取多对多另一方实体异常，mtm_id="+mtmId +",entityId="+entityId);
    }

    /**
     * 判断传入的实体id是否持有对方引用
     * @param entityId
     * @return
     */
    public boolean isHold(Integer entityId){
        if(Objects.equals(entityId,entityId1)){
            return BoolConst.isTrue(holdRefer1);
        }else if(Objects.equals(entityId,entityId2)){
            return BoolConst.isTrue(holdRefer2);
        }
        throw new BusinessException(ErrorCode.INNER_DATA_ERROR,
            "获取多对多实体是否持有引用异常，mtm_id="+mtmId +",entityId="+entityId);
    }

    /**
     * 根据传入的实体id获取其对应的外键字段别名
     * @param entityId 实体id
     * @param forSql 是否sql字段
     * @return
     */
    public String getFkAlias(Integer entityId,boolean forSql){
        if(Objects.equals(entityId,entityId1)){
            if(forSql){
                return fkAliasForSql1;
            }else{
                return fkAliasForJava1;
            }
        }else if(Objects.equals(entityId,entityId2)){
            if(forSql){
                return fkAliasForSql2;
            }else{
                return fkAliasForJava2;
            }
        }
        throw new BusinessException(ErrorCode.INNER_DATA_ERROR,
            "获取多对多外键字段别名异常，mtm_id="+mtmId +",entityId="+entityId);
    }


    /**
     * 传入宿主实体id，获取宿主实体持有的级联扩展列表
     * @param entityId 宿主实体id
     * @return
     */
    public List<MetaMtmCascadeExtPO> getCascadeExtList(Integer entityId){
        if(Objects.equals(entityId1, entityId)){
            return cascadeExtList1;
        }else if(Objects.equals(entityId2, entityId)){
            return cascadeExtList2;
        }else{
            throw new BusinessException(ErrorCode.INNER_DATA_ERROR,
                "获取多对多级联扩展数据异常，mtm_id="+mtmId +",entityId="+entityId);
        }
    }


    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public MetaEntityPO getRefer2() {
        return refer2;
    }

    public void setRefer2(MetaEntityPO refer2) {
        this.refer2 = refer2;
    }

    public MetaEntityPO getRefer1() {
        return refer1;
    }

    public void setRefer1(MetaEntityPO refer1) {
        this.refer1 = refer1;
    }

    public Integer getMtmId() {
        return mtmId;
    }

    public void setMtmId(Integer mtmId) {
        this.mtmId = mtmId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getEntityId1() {
        return entityId1;
    }

    public void setEntityId1(Integer entityId1) {
        this.entityId1 = entityId1;
    }

    public Integer getEntityId2() {
        return entityId2;
    }

    public void setEntityId2(Integer entityId2) {
        this.entityId2 = entityId2;
    }

    public Integer getHoldRefer1() {
        return holdRefer1;
    }

    public void setHoldRefer1(Integer holdRefer1) {
        this.holdRefer1 = holdRefer1;
    }

    public Integer getHoldRefer2() {
        return holdRefer2;
    }

    public void setHoldRefer2(Integer holdRefer2) {
        this.holdRefer2 = holdRefer2;
    }

    public String getEntityIdField1() {
        return entityIdField1;
    }

    public void setEntityIdField1(String entityIdField1) {
        this.entityIdField1 = entityIdField1;
    }

    public String getEntityIdField2() {
        return entityIdField2;
    }

    public void setEntityIdField2(String entityIdField2) {
        this.entityIdField2 = entityIdField2;
    }

    public Boolean getNeedId() {
        return needId;
    }

    public void setNeedId(Boolean needId) {
        this.needId = needId;
    }

    public Boolean getBigId() {
        return bigId;
    }

    public void setBigId(Boolean bigId) {
        this.bigId = bigId;
    }

    public List<MetaMtmCascadeExtPO> getCascadeExtList1() {
        return cascadeExtList1;
    }

    public void setCascadeExtList1(List<MetaMtmCascadeExtPO> cascadeExtList1) {
        this.cascadeExtList1 = cascadeExtList1;
    }

    public List<MetaMtmCascadeExtPO> getCascadeExtList2() {
        return cascadeExtList2;
    }

    public void setCascadeExtList2(List<MetaMtmCascadeExtPO> cascadeExtList2) {
        this.cascadeExtList2 = cascadeExtList2;
    }

    public String getFkAliasForSql1() {
        return fkAliasForSql1;
    }

    public void setFkAliasForSql1(String fkAliasForSql1) {
        this.fkAliasForSql1 = fkAliasForSql1;
    }

    public String getFkAliasForSql2() {
        return fkAliasForSql2;
    }

    public void setFkAliasForSql2(String fkAliasForSql2) {
        this.fkAliasForSql2 = fkAliasForSql2;
    }

    public String getFkAliasForJava1() {
        return fkAliasForJava1;
    }

    public void setFkAliasForJava1(String fkAliasForJava1) {
        this.fkAliasForJava1 = fkAliasForJava1;
    }

    public String getFkAliasForJava2() {
        return fkAliasForJava2;
    }

    public void setFkAliasForJava2(String fkAliasForJava2) {
        this.fkAliasForJava2 = fkAliasForJava2;
    }
}
