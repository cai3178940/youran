package com.youran.generate.service;

import com.youran.common.constant.BoolConst;
import com.youran.generate.constant.MetaSpecialField;
import com.youran.generate.dao.*;
import com.youran.generate.exception.GenerateException;
import com.youran.generate.pojo.po.*;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Title:
 * Description:
 * Author: cbb
 * Create Time:2017/5/14 10:45
 */
@Service
public class MetadataQueryService {

    @Autowired
    private MetaEntityDAO metaEntityDAO;
    @Autowired
    private MetaFieldDAO metaFieldDAO;
    @Autowired
    private MetaIndexDAO metaIndexDAO;
    @Autowired
    private MetaConstDAO metaConstDAO;
    @Autowired
    private MetaConstDetailDAO metaConstDetailDAO;
    @Autowired
    private MetaIndexFieldDAO metaIndexFieldDAO;


    /**
     * 获取元数据常量及所有关联对象
     * @param constId 常量id
     * @return
     */
    public MetaConstPO getConstWithAll(Integer constId) {
        MetaConstPO metaConst = metaConstDAO.findById(constId);
        if (metaConst == null) {
            throw new GenerateException("未查询到元数据常量，constId=" + constId);
        }
        List<MetaConstDetailPO> detailList = metaConstDetailDAO.findByConstId(constId);
        if (CollectionUtils.isEmpty(detailList)) {
            throw new GenerateException("元数据常量无对应常量值，constId=" + constId);
        }
        metaConst.setDetailList(detailList);
        return metaConst;
    }

    /**
     * 获取元数据实体及所有关联对象
     * @param entityId 实体id
     * @return
     */
    public MetaEntityPO getEntityWithAll(Integer entityId) {
        MetaEntityPO metaEntity = metaEntityDAO.findById(entityId);
        if (metaEntity == null) {
            throw new GenerateException("未查询到元数据实体，entityId=" + entityId);
        }
        return this.fillEntity(metaEntity);
    }

    /**
     * 填充元数据实体所有关联对象
     * @param metaEntity 实体
     * @return
     */
    public MetaEntityPO fillEntity(MetaEntityPO metaEntity) {
        Integer entityId = metaEntity.getEntityId();
        List<MetaFieldPO> fieldList = metaFieldDAO.findByEntityId(entityId);
        if (CollectionUtils.isEmpty(fieldList)) {
            throw new GenerateException("元数据实体无对应字段，entityId=" + entityId);
        }
        //将list转化为map,并填充实体内部字段
        Map<Integer, MetaFieldPO> fieldMap = new HashMap<>(fieldList.size());
        List<MetaFieldPO> queryFields = new ArrayList<>();
        List<MetaFieldPO> insertFields = new ArrayList<>();
        List<MetaFieldPO> updateFields = new ArrayList<>();
        List<MetaFieldPO> listFields = new ArrayList<>();
        List<MetaFieldPO> showFields = new ArrayList<>();
        for (MetaFieldPO metaField : fieldList) {
            String specialField = metaField.getSpecialField();
            if (BoolConst.TRUE == metaField.getPrimaryKey()) {
                metaEntity.setPkField(metaField);
            } else if (Objects.equals(specialField, MetaSpecialField.DEL_SIGN)) {
                metaEntity.setDelField(metaField);
            } else if (Objects.equals(specialField, MetaSpecialField.CREATE_BY)) {
                metaEntity.setCreateByField(metaField);
            } else if (Objects.equals(specialField, MetaSpecialField.CREATE_DATE)) {
                metaEntity.setCreateDateField(metaField);
            } else if (Objects.equals(specialField, MetaSpecialField.OPERATE_BY)) {
                metaEntity.setOperateByField(metaField);
            } else if (Objects.equals(specialField, MetaSpecialField.OPERATE_DATE)) {
                metaEntity.setOperateDateField(metaField);
            } else if (Objects.equals(specialField, MetaSpecialField.VERSION)) {
                metaEntity.setVersionField(metaField);
            }
            if (BoolConst.TRUE == metaField.getQuery()) {
                queryFields.add(metaField);
            }
            if (BoolConst.TRUE == metaField.getInsert()) {
                insertFields.add(metaField);
            }
            if (BoolConst.TRUE == metaField.getUpdate()) {
                updateFields.add(metaField);
            }
            if (BoolConst.TRUE == metaField.getList()) {
                listFields.add(metaField);
            }
            if (BoolConst.TRUE == metaField.getShow()) {
                showFields.add(metaField);
            }

            fieldMap.put(metaField.getFieldId(), metaField);
        }
        metaEntity.setFields(fieldList);
        metaEntity.setQueryFields(queryFields);
        metaEntity.setInsertFields(insertFields);
        metaEntity.setUpdateFields(updateFields);
        metaEntity.setListFields(listFields);
        metaEntity.setShowFields(showFields);
        List<MetaIndexPO> metaIndices = metaIndexDAO.findByEntityId(entityId);
        //索引中填充字段对象
        if (CollectionUtils.isNotEmpty(metaIndices)) {
            for (MetaIndexPO metaIndex : metaIndices) {
                List<Integer> fieldIds = metaIndexFieldDAO.findIdsByIndexId(metaIndex.getIndexId());
                if (CollectionUtils.isEmpty(fieldIds)) {
                    throw new GenerateException("索引有误，缺少索引字段，entityId=" + entityId + ",indexId=" + metaIndex.getIndexId());
                }
                fieldIds.stream().forEach(fieldId -> {
                    MetaFieldPO field = fieldMap.get(fieldId);
                    if (field == null) {
                        throw new GenerateException("索引字段非本实体字段，indexId=" +
                                metaIndex.getIndexId() + ",fieldId=" + fieldId);
                    }
                    metaIndex.addMetaField(field);
                });
            }
        }
        metaEntity.setIndices(metaIndices);
        return metaEntity;
    }

}
