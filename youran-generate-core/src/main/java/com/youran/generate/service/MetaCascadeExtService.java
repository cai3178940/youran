package com.youran.generate.service;

import com.youran.common.constant.ErrorCode;
import com.youran.common.exception.BusinessException;
import com.youran.common.optimistic.OptimisticLock;
import com.youran.generate.dao.MetaCascadeExtDAO;
import com.youran.generate.dao.MetaFieldDAO;
import com.youran.generate.pojo.dto.MetaCascadeExtAddDTO;
import com.youran.generate.pojo.dto.MetaCascadeExtUpdateDTO;
import com.youran.generate.pojo.mapper.MetaCascadeExtMapper;
import com.youran.generate.pojo.po.MetaCascadeExtPO;
import com.youran.generate.pojo.po.MetaFieldPO;
import com.youran.generate.pojo.qo.MetaCascadeExtQO;
import com.youran.generate.pojo.vo.MetaCascadeExtListVO;
import com.youran.generate.pojo.vo.MetaCascadeExtShowVO;
import com.youran.generate.util.JfieldNameCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 级联扩展增删改查服务
 *
 * @author: cbb
 * @date: 2017/5/24
 */
@Service
public class MetaCascadeExtService {

    @Autowired
    private MetaCascadeExtDAO metaCascadeExtDAO;
    @Autowired
    private MetaProjectService metaProjectService;
    @Autowired
    private MetaFieldService metaFieldService;
    @Autowired
    private MetaFieldDAO metaFieldDAO;


    /**
     * 新增级联扩展
     *
     * @param addDTO
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public MetaCascadeExtPO save(MetaCascadeExtAddDTO addDTO) {
        // 校验别名
        JfieldNameCheckUtil.check(addDTO.getAlias());
        Integer entityId = addDTO.getEntityId();
        // 校验操作人
        metaProjectService.checkOperatorByEntityId(entityId);
        MetaCascadeExtPO cascadeExt = MetaCascadeExtMapper.INSTANCE.fromAddDTO(addDTO);
        this.doSave(cascadeExt);
        metaProjectService.updateProjectVersionByEntityId(entityId);
        return cascadeExt;
    }

    public void doSave(MetaCascadeExtPO cascadeExtPO) {
        // 校验级联扩展
        this.checkCascadeExtPO(cascadeExtPO);
        metaCascadeExtDAO.save(cascadeExtPO);
    }

    /**
     * 修改级联扩展
     *
     * @param updateDTO
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    @OptimisticLock
    public MetaCascadeExtPO update(MetaCascadeExtUpdateDTO updateDTO) {
        // 校验别名
        JfieldNameCheckUtil.check(updateDTO.getAlias());
        MetaCascadeExtPO metaCascadeExt = this.getMetaCascadeExt(updateDTO.getCascadeExtId(), true);
        Integer entityId = metaCascadeExt.getEntityId();
        // 校验操作人
        metaProjectService.checkOperatorByEntityId(entityId);
        MetaCascadeExtMapper.INSTANCE.setPO(metaCascadeExt, updateDTO);
        // 校验级联扩展
        this.checkCascadeExtPO(metaCascadeExt);
        metaCascadeExtDAO.update(metaCascadeExt);
        metaProjectService.updateProjectVersionByEntityId(entityId);
        return metaCascadeExt;
    }

    /**
     * 校验级联扩展
     *
     * @param po
     */
    private void checkCascadeExtPO(MetaCascadeExtPO po) {
        List<String> jFieldNames = metaFieldDAO.findJFieldNames(po.getEntityId());
        if (jFieldNames.contains(po.getAlias())) {
            throw new BusinessException(ErrorCode.BAD_PARAMETER, "当前实体中存在字段名：" + po.getAlias());
        }
        // 校验重复添加
        boolean exists = metaCascadeExtDAO.cascadeFieldIdExists(po.getFieldId(), po.getCascadeEntityId(),
            po.getCascadeFieldId(), po.getCascadeExtId());
        if (exists) {
            throw new BusinessException(ErrorCode.BAD_PARAMETER, "字段重复");
        }
        // TODO 校验当前实体下其他级联扩展字段
    }


    /**
     * 获取级联扩展对象
     *
     * @param cascadeExtId
     * @param force
     * @return
     */
    public MetaCascadeExtPO getMetaCascadeExt(Integer cascadeExtId, boolean force) {
        MetaCascadeExtPO cascadeExtPO = metaCascadeExtDAO.findById(cascadeExtId);
        if (force && cascadeExtPO == null) {
            throw new BusinessException(ErrorCode.RECORD_NOT_FIND, "级联扩展未找到");
        }
        return cascadeExtPO;
    }

    /**
     * 查询分页列表
     *
     * @param metaCascadeExtQO
     * @return
     */
    public List<MetaCascadeExtListVO> list(MetaCascadeExtQO metaCascadeExtQO) {
        return metaCascadeExtDAO.findListByQuery(metaCascadeExtQO);
    }

    /**
     * 查询级联扩展
     *
     * @param cascadeExtId
     * @return
     */
    public MetaCascadeExtShowVO show(Integer cascadeExtId) {
        MetaCascadeExtPO metaCascadeExt = this.getMetaCascadeExt(cascadeExtId, true);
        MetaCascadeExtShowVO showVO = MetaCascadeExtMapper.INSTANCE.toShowVO(metaCascadeExt);
        MetaFieldPO cascadeField = metaFieldService.getField(metaCascadeExt.getCascadeFieldId(), true);
        showVO.setCascadeFieldDesc(cascadeField.getFieldDesc());
        showVO.setCascadeJfieldName(cascadeField.getJfieldName());
        return showVO;
    }

    /**
     * 删除级联扩展
     *
     * @param cascadeExtId
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public int delete(Integer... cascadeExtId) {
        int count = 0;
        Integer entityId = null;
        for (Integer id : cascadeExtId) {
            MetaCascadeExtPO cascadeExtPO = this.getMetaCascadeExt(id, false);
            if (cascadeExtPO == null) {
                continue;
            }
            entityId = cascadeExtPO.getEntityId();
            //校验操作人
            metaProjectService.checkOperatorByEntityId(entityId);
            count += metaCascadeExtDAO.delete(id);
        }
        if (count > 0) {
            metaProjectService.updateProjectVersionByEntityId(entityId);
        }
        return count;
    }


    /**
     * 根据字段id查询级联扩展列表
     *
     * @param fieldId
     * @return
     */
    public List<MetaCascadeExtPO> findByFieldId(Integer fieldId) {
        return metaCascadeExtDAO.findByFieldId(fieldId);
    }

    /**
     * 根据级联字段id查询级联扩展列表
     *
     * @param cascadeFieldId
     * @return
     */
    public List<Integer> findPkByCascadeFieldId(Integer cascadeFieldId) {
        return metaCascadeExtDAO.findPkByCascadeFieldId(cascadeFieldId);
    }


}
