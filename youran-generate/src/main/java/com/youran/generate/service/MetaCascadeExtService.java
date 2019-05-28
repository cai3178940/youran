package com.youran.generate.service;

import com.youran.common.optimistic.OptimisticLock;
import com.youran.generate.dao.MetaCascadeExtDAO;
import com.youran.common.exception.BusinessException;
import com.youran.generate.pojo.dto.MetaCascadeExtAddDTO;
import com.youran.generate.pojo.dto.MetaCascadeExtUpdateDTO;
import com.youran.generate.pojo.mapper.MetaCascadeExtMapper;
import com.youran.generate.pojo.po.MetaCascadeExtPO;
import com.youran.generate.pojo.po.MetaFieldPO;
import com.youran.generate.pojo.qo.MetaCascadeExtQO;
import com.youran.generate.pojo.vo.MetaCascadeExtListVO;
import com.youran.generate.pojo.vo.MetaCascadeExtShowVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>Title:级联扩展增删改查服务</p>
 * <p>Description:</p>
 * @author: cbb
 * Create Time:2017/5/24
 */
@Service
public class MetaCascadeExtService {

    @Autowired
    private MetaCascadeExtDAO metaCascadeExtDAO;
    @Autowired
    private MetaProjectService metaProjectService;
    @Autowired
    private MetaFieldService metaFieldService;


    /**
     * 新增级联扩展
     * @param addDTO
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public MetaCascadeExtPO save(MetaCascadeExtAddDTO addDTO) {
        Integer entityId = addDTO.getEntityId();
        //校验操作人
        metaProjectService.checkOperatorByEntityId(entityId);
        MetaCascadeExtPO metaCascadeExt = MetaCascadeExtMapper.INSTANCE.fromAddDTO(addDTO);
        metaCascadeExtDAO.save(metaCascadeExt);
        metaProjectService.updateProjectVersionByEntityId(entityId);
        return metaCascadeExt;
    }

    /**
     * 修改级联扩展
     * @param updateDTO
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    @OptimisticLock
    public MetaCascadeExtPO update(MetaCascadeExtUpdateDTO updateDTO) {
        MetaCascadeExtPO metaCascadeExt = this.getMetaCascadeExt(updateDTO.getCascadeExtId(),true);
        Integer entityId = metaCascadeExt.getEntityId();
        //校验操作人
        metaProjectService.checkOperatorByEntityId(entityId);
        MetaCascadeExtMapper.INSTANCE.setPO(metaCascadeExt, updateDTO);
        metaCascadeExtDAO.update(metaCascadeExt);
        metaProjectService.updateProjectVersionByEntityId(entityId);
        return metaCascadeExt;
    }

    /**
     * 获取级联扩展对象
     * @param cascadeExtId
     * @param force
     * @return
     */
    public MetaCascadeExtPO getMetaCascadeExt(Integer cascadeExtId,boolean force){
        MetaCascadeExtPO cascadeExtPO = metaCascadeExtDAO.findById(cascadeExtId);
        if(force && cascadeExtPO == null){
            throw new BusinessException("级联扩展未找到");
        }
        return cascadeExtPO;
    }

    /**
     * 查询分页列表
     * @param metaCascadeExtQO
     * @return
     */
    public List<MetaCascadeExtListVO> list(MetaCascadeExtQO metaCascadeExtQO) {
        return metaCascadeExtDAO.findListByQuery(metaCascadeExtQO);
    }

    /**
     * 查询级联扩展
     * @param cascadeExtId
     * @return
     */
    public MetaCascadeExtShowVO show(Integer cascadeExtId) {
        MetaCascadeExtPO metaCascadeExt = this.getMetaCascadeExt(cascadeExtId,true);
        MetaCascadeExtShowVO showVO = MetaCascadeExtMapper.INSTANCE.toShowVO(metaCascadeExt);
        MetaFieldPO cascadeField = metaFieldService.getField(metaCascadeExt.getCascadeFieldId(),true);
        showVO.setCascadeFieldDesc(cascadeField.getFieldDesc());
        showVO.setCascadeJfieldName(cascadeField.getJfieldName());
        return showVO;
    }

    /**
     * 删除级联扩展
     * @param cascadeExtId
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public int delete(Integer... cascadeExtId) {
        int count = 0;
        Integer entityId = null;
        for (Integer id : cascadeExtId) {
            MetaCascadeExtPO cascadeExtPO = this.getMetaCascadeExt(id,false);
            if(cascadeExtPO==null){
                continue;
            }
            entityId = cascadeExtPO.getEntityId();
            //校验操作人
            metaProjectService.checkOperatorByEntityId(entityId);
            count += metaCascadeExtDAO.delete(id);
        }
        if(count>0) {
            metaProjectService.updateProjectVersionByEntityId(entityId);
        }
        return count;
    }


    /**
     * 根据字段id查询级联扩展列表
     * @param fieldId
     * @return
     */
    public List<MetaCascadeExtPO> findByFieldId(Integer fieldId) {
        return metaCascadeExtDAO.findByFieldId(fieldId);
    }
}
