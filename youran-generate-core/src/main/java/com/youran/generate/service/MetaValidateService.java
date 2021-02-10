package com.youran.generate.service;

import com.youran.common.constant.ErrorCode;
import com.youran.common.exception.BusinessException;
import com.youran.generate.constant.JFieldType;
import com.youran.generate.pojo.po.MetaConstPO;
import com.youran.generate.pojo.po.MetaEntityPO;
import com.youran.generate.pojo.po.MetaFieldPO;
import com.youran.generate.pojo.po.MetaProjectPO;
import com.youran.generate.pojo.vo.MetaEntityInnerValidateVO;
import com.youran.generate.pojo.vo.MetaFieldValidateVO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 元数据校验服务
 *
 * @author: cbb
 * @date: 2019/10/10
 */
@Service
public class MetaValidateService {

    @Autowired
    private MetaQueryAssembleService metaQueryAssembleService;

    /**
     * 校验实体内部数据
     *
     * @param entityId
     * @return
     */
    public MetaEntityInnerValidateVO validateEntityInner(Integer projectId, Integer entityId) {
        // 获取装配完成的项目
        MetaProjectPO project = metaQueryAssembleService.getAssembledProject(projectId, false);
        // 获取装配完成的枚举
        List<MetaConstPO> consts = project.getConsts();
        List<MetaEntityPO> entities = project.getEntities();
        MetaEntityPO entity = entities.stream()
            .filter(e -> e.getEntityId().equals(entityId))
            .findFirst()
            .orElseThrow(() -> new BusinessException(ErrorCode.RECORD_NOT_FIND, "实体不存在"));
        Map<Integer, MetaFieldPO> fields = entity.getFields();
        // 是否需要标记标题候选字段
        boolean mustSetTitle = false;
        // 判断当前实体是否被外键级联
        if(CollectionUtils.isNotEmpty(entity.getForeignEntities())){
            mustSetTitle = true;
        }
        // 判断当前实体是否被多对多级联hold
        else if(!entity.getMtmsForOpp().isEmpty()){
            mustSetTitle = true;
        }
        boolean finalMustSetTitle = mustSetTitle;
        // 校验实体中的所有字段
        List<MetaFieldValidateVO> fieldValidateVOS = fields.values().stream()
            .map(field -> this.doValidateField(field, entity, consts, finalMustSetTitle))
            .collect(Collectors.toList());

        MetaEntityInnerValidateVO vo = new MetaEntityInnerValidateVO();
        vo.setFields(fieldValidateVOS);
        return vo;
    }

    /**
     * 字段校验
     *
     * @param field        被校验的字段
     * @param entity       字段所属实体
     * @param consts       项目内所有枚举
     * @param mustSetTitle 是否必须要标记标题字段
     * @return
     */
    private MetaFieldValidateVO doValidateField(MetaFieldPO field,
                                                MetaEntityPO entity,
                                                List<MetaConstPO> consts,
                                                boolean mustSetTitle) {
        MetaFieldValidateVO vo = new MetaFieldValidateVO(field.getFieldId());
        // 校验枚举是否存在
        String dic = field.getDicType();
        if (StringUtils.isNotBlank(dic)) {
            Optional<MetaConstPO> optional = consts.stream().filter(metaConstPO -> dic.equals(metaConstPO.getConstName()))
                .findAny();
            if (!optional.isPresent()) {
                vo.dicNotExistError(field);
            }
        }
        for (Map.Entry<Integer, MetaFieldPO> entry : entity.getFields().entrySet()) {
            if (field.getFieldId().equals(entry.getKey())) {
                continue;
            }
            MetaFieldPO otherField = entry.getValue();
            // 校验java字段名重复
            if (Objects.equals(field.getJfieldName(), otherField.getJfieldName())) {
                vo.sameJfieldNameError();
            }
            // 校验mysql字段名重复
            if (Objects.equals(field.getFieldName(), otherField.getFieldName())) {
                vo.sameFieldNameError();
            }
        }
        // 标记父节点id候选字段，前提条件：外键 && 关联了当前实体主键
        if (field.getForeignKey()) {
            MetaFieldPO pkField = entity.getPkField();
            MetaFieldPO foreignField = field.getForeignField();
            if (pkField != null && foreignField != null
                && Objects.equals(foreignField.getFieldId(), pkField.getFieldId())) {
                vo.setPidCandidate(1);
            }
        }
        // 标记标题候选字段，前提条件是：非主键 && 非外键 && 非特殊字段 && 是字符串
        if (!field.getPrimaryKey() && !field.getForeignKey()
            && StringUtils.isBlank(field.getSpecialField())
            && JFieldType.STRING.getJavaType().equals(field.getJfieldType())) {
            if (mustSetTitle) {
                // 较高级别
                vo.setTitleCandidate(2);
            } else {
                // 较低级别
                vo.setTitleCandidate(1);
            }
        }
        return vo;
    }

}
