package com.youran.generate.help;

import com.youran.generate.pojo.dto.*;
import com.youran.generate.pojo.po.*;
import com.youran.generate.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 封装单元测试使用的元数据基本操作
 *
 * @author: cbb
 * @date: 2017/5/18
 */
@Component
public class GenerateHelper {

    @Autowired
    private MetaProjectService metaProjectService;
    @Autowired
    private MetaEntityService metaEntityService;
    @Autowired
    private MetaFieldService metaFieldService;
    @Autowired
    private MetaIndexService metaIndexService;
    @Autowired
    private MetaConstService metaConstService;
    @Autowired
    private MetaManyToManyService metaManyToManyService;
    @Autowired
    private MetaConstDetailService metaConstDetailService;
    @Autowired
    private MetaCascadeExtService metaCascadeExtService;
    @Autowired
    private MetaMtmCascadeExtService metaMtmCascadeExtService;


    /**
     * 保存项目示例
     *
     * @return
     */
    public MetaProjectPO saveProjectExample() {
        MetaProjectAddDTO addDTO = MetaProjectHelper.getAddDTO();
        return metaProjectService.save(addDTO);
    }

    /**
     * 保存实体示例
     *
     * @return
     */
    public MetaEntityPO saveEntityExample(Integer projectId, int i) {
        MetaEntityAddDTO addDTO = MetaEntityHelper.getAddDTO(projectId, i);
        return metaEntityService.save(addDTO);
    }

    /**
     * 保存字段示例
     *
     * @return
     */
    public MetaFieldPO saveFieldExample(Integer entityId) {
        MetaFieldAddDTO addDTO = MetaFieldHelper.getAddDTO(entityId);
        return metaFieldService.save(addDTO);
    }

    /**
     * 保存字段示例
     *
     * @return
     */
    public MetaFieldPO saveFieldExample(Integer entityId, String fieldName) {
        MetaFieldAddDTO addDTO = MetaFieldHelper.getAddDTO(entityId);
        addDTO.setFieldName(fieldName);
        addDTO.setJfieldName(fieldName);
        return metaFieldService.save(addDTO);
    }

    /**
     * 保存级联扩展示例
     *
     * @return
     */
    public MetaCascadeExtPO saveCascadeExtExample(Integer fieldId, Integer entityId, Integer cascadeFieldId, Integer cascadeEntityId) {
        MetaCascadeExtAddDTO addDTO = MetaCascadeExtHelper.getAddDTO(fieldId, entityId, cascadeFieldId, cascadeEntityId);
        return metaCascadeExtService.save(addDTO);
    }

    /**
     * 保存级联扩展示例
     *
     * @return
     */
    public MetaMtmCascadeExtPO saveMtmCascadeExtExample(Integer mtmId, Integer entityId, Integer cascadeEntityId, Integer cascadeFieldId) {
        MetaMtmCascadeExtAddDTO addDTO = MetaMtmCascadeExtHelper.getAddDTO(mtmId, entityId, cascadeEntityId, cascadeFieldId);
        return metaMtmCascadeExtService.save(addDTO);
    }

    /**
     * 保存多对多关联示例
     *
     * @return
     */
    public MetaManyToManyPO saveManyToManyExample(Integer projectId, Integer entityId1, Integer entityId2) {
        MetaManyToManyAddDTO addDTO = MetaManyToManyHelper.getAddDTO(projectId, entityId1, entityId2);
        return metaManyToManyService.save(addDTO);
    }

    /**
     * 保存索引示例
     *
     * @return
     */
    public MetaIndexPO saveIndexExample(Integer... fieldIds) {
        MetaIndexAddDTO addDTO = MetaIndexHelper.getAddDTO(fieldIds);
        return metaIndexService.save(addDTO);
    }

    /**
     * 保存常量示例
     *
     * @return
     */
    public MetaConstPO saveConstExample(Integer projectId) {
        MetaConstAddDTO addDTO = MetaConstHelper.getAddDTO(projectId);
        return metaConstService.save(addDTO);
    }

    /**
     * 保存常量值示例
     *
     * @return
     */
    public MetaConstDetailPO saveConstDetailExample(Integer constId) {
        MetaConstDetailAddDTO addDTO = MetaConstDetailHelper.getAddDTO(constId);
        return metaConstDetailService.save(addDTO);
    }



}
