package com.youran.generate.pojo.template;

import com.youran.generate.pojo.po.MetaEntityPO;
import com.youran.generate.pojo.po.MetaFieldPO;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.LinkedList;
import java.util.List;

/**
 * <p>Title: 外键关联树节点</p>
 * <p>Description: </p>
 *
 * @author cbb
 * @date 2018/8/7
 */
public class ForeignEntityTreeNode {

    private Integer entityId;

    private MetaEntityPO metaEntity;

    private List<MetaFieldPO> foreignFieldList;

    private List<MetaEntityPO> foreignEntityList;

    private List<ForeignEntityTreeNode> children;

    private ForeignEntityTreeNode parent;

    public ForeignEntityTreeNode(MetaEntityPO metaEntity, ForeignEntityTreeNode parent) {
        this.metaEntity = metaEntity;
        this.entityId = metaEntity.getEntityId();
        this.parent = parent;
    }


    public void addForeign(MetaFieldPO field) {
        if (this.foreignFieldList == null) {
            this.foreignFieldList = new LinkedList<>();
            this.foreignEntityList = new LinkedList<>();
        }
        this.foreignFieldList.add(field);
        this.foreignEntityList.add(field.getForeignEntity());
    }


    public void addChild(ForeignEntityTreeNode child) {
        if (this.children == null) {
            this.children = new LinkedList<>();
        }
        this.children.add(child);
    }


    public Integer getEntityId() {
        return entityId;
    }

    public void setEntityId(Integer entityId) {
        this.entityId = entityId;
    }

    public MetaEntityPO getMetaEntity() {
        return metaEntity;
    }

    public void setMetaEntity(MetaEntityPO metaEntity) {
        this.metaEntity = metaEntity;
    }

    public List<MetaFieldPO> getForeignFieldList() {
        return foreignFieldList;
    }

    public void setForeignFieldList(List<MetaFieldPO> foreignFieldList) {
        this.foreignFieldList = foreignFieldList;
    }

    public List<MetaEntityPO> getForeignEntityList() {
        return foreignEntityList;
    }

    public void setForeignEntityList(List<MetaEntityPO> foreignEntityList) {
        this.foreignEntityList = foreignEntityList;
    }

    public List<ForeignEntityTreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<ForeignEntityTreeNode> children) {
        this.children = children;
    }

    public ForeignEntityTreeNode getParent() {
        return parent;
    }

    public void setParent(ForeignEntityTreeNode parent) {
        this.parent = parent;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ForeignEntityTreeNode that = (ForeignEntityTreeNode) o;
        return new EqualsBuilder()
            .append(entityId, that.entityId)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(entityId)
            .toHashCode();
    }


}
