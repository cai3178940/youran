package com.youran.generate.util;

import com.youran.common.constant.ErrorCode;
import com.youran.common.exception.BusinessException;
import com.youran.generate.constant.JFieldType;
import com.youran.generate.constant.MetaSpecialField;
import com.youran.generate.pojo.po.MetaFieldPO;

import java.util.Objects;

/**
 * 字段校验工具
 *
 * @author cbb
 * @date 2019/6/5
 */
public class MetaFieldCheckUtil {

    /**
     * @param metaField
     */
    public static void checkFieldPO(MetaFieldPO metaField) {
        String specialField = metaField.getSpecialField();
        if (MetaSpecialField.isDeleted(specialField)) {
            if (!Objects.equals(metaField.getJfieldType(), JFieldType.BOOLEAN.getJavaType())) {
                throw new BusinessException(ErrorCode.BAD_PARAMETER, "逻辑删除字段必须是Boolean类型");
            }
        }
        if (MetaSpecialField.isVersion(specialField)) {
            if (!Objects.equals(metaField.getJfieldType(), JFieldType.INTEGER.getJavaType())) {
                throw new BusinessException(ErrorCode.BAD_PARAMETER, "乐观锁版本字段必须是Integer类型");
            }
        }
        if (MetaSpecialField.isCreatedBy(specialField)) {
            if (!Objects.equals(metaField.getJfieldType(), JFieldType.STRING.getJavaType())) {
                throw new BusinessException(ErrorCode.BAD_PARAMETER, "创建人员字段必须是String类型");
            }
        }
        if (MetaSpecialField.isOperatedBy(specialField)) {
            if (!Objects.equals(metaField.getJfieldType(), JFieldType.STRING.getJavaType())) {
                throw new BusinessException(ErrorCode.BAD_PARAMETER, "更新人员字段必须是String类型");
            }
        }
        if (MetaSpecialField.isCreatedTime(specialField)) {
            if (!Objects.equals(metaField.getJfieldType(), JFieldType.DATE.getJavaType())) {
                throw new BusinessException(ErrorCode.BAD_PARAMETER, "创建时间字段必须是Date类型");
            }
        }
        if (MetaSpecialField.isOperatedTime(specialField)) {
            if (!Objects.equals(metaField.getJfieldType(), JFieldType.DATE.getJavaType())) {
                throw new BusinessException(ErrorCode.BAD_PARAMETER, "更新时间字段必须是Date类型");
            }
        }
    }

}
