package com.youran.generate.pojo.po;

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


}
