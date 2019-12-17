package com.youran.generate.service;

import com.youran.generate.constant.JFieldType;
import com.youran.generate.pojo.po.MetaConstPO;
import com.youran.generate.pojo.po.MetaEntityPO;
import com.youran.generate.pojo.po.MetaFieldPO;
import com.youran.generate.pojo.vo.MetaEntityInnerValidateVO;
import com.youran.generate.pojo.vo.MetaFieldValidateVO;
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
    public MetaEntityInnerValidateVO validateEntityInner(Integer entityId) {
        // 获取装配完成的实体
        MetaEntityPO entity = metaQueryAssembleService.getAssembledEntity(entityId, false);
        // 获取装配完成的枚举
        List<MetaConstPO> consts = metaQueryAssembleService.getAllAssembledConsts(entity.getProjectId(),
            false, false);
        Map<Integer, MetaFieldPO> fields = entity.getFields();
        MetaFieldPO titleField = entity.getTitleField();
        // 是否需要标记标题候选字段
        boolean titleCandidate = false;
        if (titleField == null) {
            // 判断当前实体是否被外键级联

            // 判断当前实体是否被多对多级联hold
        }


        // 校验实体中的所有字段
        List<MetaFieldValidateVO> fieldValidateVOS = fields.values().stream()
            .map(field -> this.doValidateField(field, fields, consts, titleCandidate))
            .collect(Collectors.toList());

        MetaEntityInnerValidateVO vo = new MetaEntityInnerValidateVO();
        vo.setFields(fieldValidateVOS);
        return vo;
    }

    /**
     * 字段校验
     *
     * @param field          被校验的字段
     * @param fields         实体中所有字段
     * @param consts         项目内所有枚举
     * @param titleCandidate 是否需要标记候选标题字段
     * @return
     */
    private MetaFieldValidateVO doValidateField(MetaFieldPO field,
                                                Map<Integer, MetaFieldPO> fields,
                                                List<MetaConstPO> consts,
                                                boolean titleCandidate) {
        MetaFieldValidateVO vo = new MetaFieldValidateVO(field);
        // 校验枚举是否存在
        String dic = field.getDicType();
        if (StringUtils.isNotBlank(dic)) {
            Optional<MetaConstPO> optional = consts.stream().filter(metaConstPO -> dic.equals(metaConstPO.getConstName()))
                .findAny();
            if (!optional.isPresent()) {
                vo.dicNotExistError();
            }
        }
        for (Map.Entry<Integer, MetaFieldPO> entry : fields.entrySet()) {
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
        // 标记标题候选字段
        if(titleCandidate){
            // 前提条件是：非主键 && 非外键 && 非特殊字段 && 是字符串
            if(!field.getPrimaryKey() && !field.getForeignKey()
                && StringUtils.isBlank(field.getSpecialField())
                && JFieldType.STRING.getJavaType().equals(field.getJfieldType())){
                vo.setTitleCandidate(true);
            }
        }
        return vo;
    }

}
