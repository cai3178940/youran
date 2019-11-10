package com.youran.generate.service;

import com.youran.generate.constant.JFieldType;
import com.youran.generate.pojo.po.MetaConstPO;
import com.youran.generate.pojo.po.MetaEntityPO;
import com.youran.generate.pojo.po.MetaFieldPO;
import com.youran.generate.pojo.vo.MetaEntityInnerValidateVO;
import com.youran.generate.pojo.vo.MetaFieldValidateVO;
import com.youran.generate.util.GuessUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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


    public MetaEntityInnerValidateVO validateEntityInner(Integer entityId) {
        // 获取装配完成的实体
        MetaEntityPO entity = metaQueryAssembleService.getAssembledEntity(entityId);
        // 获取装配完成的枚举
        List<MetaConstPO> consts = metaQueryAssembleService.getAllAssembledConsts(entity.getProjectId(), false);
        // 校验实体中的所有字段
        List<MetaFieldValidateVO> fieldValidateVOS = entity.getFields().values().stream()
            .map(field -> this.doValidateField(field, consts))
            .collect(Collectors.toList());

        MetaEntityInnerValidateVO vo = new MetaEntityInnerValidateVO();
        vo.setFields(fieldValidateVOS);
        return vo;
    }

    private MetaFieldValidateVO doValidateField(MetaFieldPO field,
                                                List<MetaConstPO> consts) {
        MetaFieldValidateVO vo = new MetaFieldValidateVO();
        vo.setFieldId(field.getFieldId());
        // 校验枚举是否存在
        String dic = field.getDicType();
        if (StringUtils.isNotBlank(dic)) {
            Optional<MetaConstPO> optional = consts.stream().filter(metaConstPO -> dic.equals(metaConstPO.getConstName()))
                .findAny();
            if (!optional.isPresent()) {
                vo.dicNotExist(dic);
                Integer constType = GuessUtil.guessConstType(JFieldType.find(field.getJfieldType()));
                vo.setSuggestConstType(constType);
                vo.setSuggestConstRemark(field.getFieldDesc());
            }
        }
        return vo;
    }

}
