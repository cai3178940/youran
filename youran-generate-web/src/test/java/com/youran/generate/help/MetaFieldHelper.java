package com.youran.generate.help;

import com.youran.common.util.SafeUtil;
import com.youran.generate.pojo.dto.MetaFieldAddDTO;
import com.youran.generate.pojo.dto.MetaFieldUpdateDTO;
import com.youran.generate.pojo.po.MetaFieldPO;

import static com.youran.generate.pojo.example.MetaFieldExample.*;

/**
 * 测试数据
 *
 * @author: cbb
 * @date: 2017/5/12
 */
public class MetaFieldHelper {

    /**
     * 生成add测试数据
     *
     * @return
     */
    public static MetaFieldAddDTO getAddDTO(Integer entityId) {
        MetaFieldAddDTO dto = new MetaFieldAddDTO();
        dto.setEntityId(entityId);
        dto.setAutoIncrement(SafeUtil.getInteger(E_AUTOINCREMENT));
        dto.setDefaultValue(E_DEFAULTVALUE);
        dto.setDicType(E_DICTYPE);
        dto.setEditType(SafeUtil.getInteger(E_EDITTYPE));
        dto.setFieldComment(E_FIELDCOMMENT);
        dto.setFieldDesc(E_FIELDDESC);
        dto.setFieldExample(E_FIELDEXAMPLE);
        dto.setFieldLength(SafeUtil.getInteger(E_FIELDLENGTH));
        dto.setFieldName(E_FIELDNAME);
        dto.setFieldScale(SafeUtil.getInteger(E_FIELDSCALE));
        dto.setFieldType(E_FIELDTYPE);
        dto.setInsert(SafeUtil.getInteger(E_INSERT));
        dto.setJfieldName(E_JFIELDNAME);
        dto.setJfieldType(E_JFIELDTYPE);
        dto.setList(SafeUtil.getInteger(E_LIST));
        dto.setListSort(SafeUtil.getInteger(E_LIST_SORT));
        dto.setNotNull(SafeUtil.getInteger(E_NOTNULL));
        dto.setOrderNo(SafeUtil.getInteger(E_ORDERNO));
        dto.setPrimaryKey(SafeUtil.getInteger(E_PRIMARYKEY));
        dto.setForeignKey(SafeUtil.getInteger(E_FOREIGNKEY));
        dto.setQuery(SafeUtil.getInteger(E_QUERY));
        dto.setQueryType(SafeUtil.getInteger(E_QUERYTYPE));
        dto.setShow(SafeUtil.getInteger(E_SHOW));
        dto.setUpdate(SafeUtil.getInteger(E_UPDATE));
        dto.setSpecialField(E_SPECIALFIELD);
        return dto;
    }


    /**
     * 生成update测试数据
     *
     * @return
     */
    public static MetaFieldUpdateDTO getUpdateDTO(MetaFieldPO metaField) {
        MetaFieldUpdateDTO metaFieldUpdateDTO = new MetaFieldUpdateDTO();
        metaFieldUpdateDTO.setFieldId(metaField.getFieldId());
        metaFieldUpdateDTO.setAutoIncrement(metaField.getAutoIncrement());
        metaFieldUpdateDTO.setDefaultValue(metaField.getDefaultValue());
        metaFieldUpdateDTO.setDicType(metaField.getDicType());
        metaFieldUpdateDTO.setEditType(metaField.getEditType());
        metaFieldUpdateDTO.setEntityId(metaField.getEntityId());
        metaFieldUpdateDTO.setFieldComment(metaField.getFieldComment());
        metaFieldUpdateDTO.setFieldDesc(metaField.getFieldDesc());
        metaFieldUpdateDTO.setFieldExample(metaField.getFieldExample());
        metaFieldUpdateDTO.setFieldLength(metaField.getFieldLength());
        metaFieldUpdateDTO.setFieldName(metaField.getFieldName());
        metaFieldUpdateDTO.setFieldScale(metaField.getFieldScale());
        metaFieldUpdateDTO.setFieldType(metaField.getFieldType());
        metaFieldUpdateDTO.setInsert(metaField.getInsert());
        metaFieldUpdateDTO.setJfieldName(metaField.getJfieldName());
        metaFieldUpdateDTO.setJfieldType(metaField.getJfieldType());
        metaFieldUpdateDTO.setList(metaField.getList());
        metaFieldUpdateDTO.setListSort(metaField.getListSort());
        metaFieldUpdateDTO.setNotNull(metaField.getNotNull());
        metaFieldUpdateDTO.setOrderNo(metaField.getOrderNo());
        metaFieldUpdateDTO.setPrimaryKey(metaField.getPrimaryKey());
        metaFieldUpdateDTO.setQuery(metaField.getQuery());
        metaFieldUpdateDTO.setQueryType(metaField.getQueryType());
        metaFieldUpdateDTO.setShow(metaField.getShow());
        metaFieldUpdateDTO.setUpdate(metaField.getUpdate());
        metaFieldUpdateDTO.setSpecialField(metaField.getSpecialField());
        metaFieldUpdateDTO.setForeignKey(metaField.getForeignKey());
        metaFieldUpdateDTO.setForeignEntityId(metaField.getForeignEntityId());
        metaFieldUpdateDTO.setForeignFieldId(metaField.getForeignFieldId());
        return metaFieldUpdateDTO;
    }

}
