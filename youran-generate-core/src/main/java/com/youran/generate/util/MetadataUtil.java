package com.youran.generate.util;

import com.google.common.base.Joiner;
import com.youran.common.constant.ErrorCode;
import com.youran.common.exception.BusinessException;
import com.youran.generate.constant.JFieldType;
import com.youran.generate.constant.WordBlacklist;
import com.youran.generate.constant.MetaSpecialField;
import com.youran.generate.pojo.po.MetaEntityPO;
import com.youran.generate.pojo.po.MetaFieldPO;
import com.youran.generate.pojo.po.MetaManyToManyPO;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * 元数据工具类
 *
 * @author: cbb
 * @date: 2017/5/21
 */
public class MetadataUtil {

    /**
     * 获取多对多外键别名
     *
     * @param mtm
     * @param refer
     * @param forSql
     * @return
     */
    public static String getMtmFkAlias(MetaManyToManyPO mtm, MetaEntityPO refer, boolean forSql) {
        String fkAlias;
        if (refer.equals(mtm.getRefer1())) {
            fkAlias = mtm.getEntityIdField1();
        } else if (refer.equals(mtm.getRefer2())) {
            fkAlias = mtm.getEntityIdField2();
        } else {
            throw new IllegalStateException("特殊异常");
        }
        if (forSql) {
            return fkAlias;
        } else {
            return SwitchCaseUtil.underlineToCamelCase(fkAlias, false);
        }
    }

    /**
     * 构建默认多对多外键别名
     *
     * @param className
     * @param forSql
     * @return
     */
    public static String buildDefaultMtmFkAlias(String className, boolean forSql) {
        String alias = SwitchCaseUtil.lowerFirstWord(className) + "Id";
        if (forSql) {
            String[] split = StringUtils.splitByCharacterTypeCamelCase(alias);
            String join = Joiner.on("_").join(split);
            alias = join.toLowerCase();
        }
        return alias;
    }


    /**
     * java字段名校验
     *
     * @param value
     */
    public static void jfieldNameCheck(String value) {
        if (WordBlacklist.isJavaKeyword(value)) {
            throw new BusinessException(ErrorCode.BAD_REQUEST, "字段名“" + value + "”是java关键字");
        }
    }

    /**
     * 校验字段PO
     *
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
            if (!Objects.equals(metaField.getJfieldType(), JFieldType.DATE.getJavaType())
                && !Objects.equals(metaField.getJfieldType(), JFieldType.LOCALDATETIME.getJavaType())) {
                throw new BusinessException(ErrorCode.BAD_PARAMETER, "创建时间字段必须是LocalDateTime或则Date类型");
            }
        }
        if (MetaSpecialField.isOperatedTime(specialField)) {
            if (!Objects.equals(metaField.getJfieldType(), JFieldType.DATE.getJavaType())
                && !Objects.equals(metaField.getJfieldType(), JFieldType.LOCALDATETIME.getJavaType())) {
                throw new BusinessException(ErrorCode.BAD_PARAMETER, "更新时间字段必须是LocalDateTime或则Date类型");
            }
        }
    }
}
