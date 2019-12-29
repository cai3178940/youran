package com.youran.generate.service;

import com.youran.common.constant.ErrorCode;
import com.youran.common.exception.BusinessException;
import com.youran.common.optimistic.OptimisticLock;
import com.youran.generate.dao.MetaFieldDAO;
import com.youran.generate.dao.MetaMtmCascadeExtDAO;
import com.youran.generate.pojo.dto.MetaMtmCascadeExtAddDTO;
import com.youran.generate.pojo.dto.MetaMtmCascadeExtUpdateDTO;
import com.youran.generate.pojo.mapper.MetaMtmCascadeExtMapper;
import com.youran.generate.pojo.po.MetaFieldPO;
import com.youran.generate.pojo.po.MetaMtmCascadeExtPO;
import com.youran.generate.pojo.po.MetaProjectPO;
import com.youran.generate.pojo.qo.MetaMtmCascadeExtQO;
import com.youran.generate.pojo.vo.MetaMtmCascadeExtListVO;
import com.youran.generate.pojo.vo.MetaMtmCascadeExtShowVO;
import com.youran.generate.util.MetadataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 【多对多级联扩展】删改查服务
 *
 * @author cbb
 * @date 2019/09/21
 */
@Service
public class MetaMtmCascadeExtService {

    @Autowired
    private MetaMtmCascadeExtDAO metaMtmCascadeExtDAO;
    @Autowired
    private MetaProjectService metaProjectService;
    @Autowired
    private MetaFieldService metaFieldService;
    @Autowired
    private MetaFieldDAO metaFieldDAO;

    /**
     * 新增【多对多级联扩展】
     *
     * @param addDTO
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public MetaMtmCascadeExtPO save(MetaMtmCascadeExtAddDTO addDTO) {
        // 校验别名
        MetadataUtil.jfieldNameCheck(addDTO.getAlias());
        // 获取项目，并校验操作人
        MetaProjectPO project = metaProjectService.getProjectByEntityId(addDTO.getEntityId(),
            true);
        MetaMtmCascadeExtPO metaMtmCascadeExt = MetaMtmCascadeExtMapper.INSTANCE.fromAddDTO(addDTO);
        metaMtmCascadeExt.setProjectId(project.getProjectId());
        this.doSave(metaMtmCascadeExt);
        metaProjectService.updateProject(project);
        return metaMtmCascadeExt;
    }

    public void doSave(MetaMtmCascadeExtPO mtmCascadeExtPO) {
        // 校验级联扩展
        this.checkCascadeExtPO(mtmCascadeExtPO);
        metaMtmCascadeExtDAO.save(mtmCascadeExtPO);
    }

    /**
     * 修改【多对多级联扩展】
     *
     * @param updateDTO
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    @OptimisticLock
    public MetaMtmCascadeExtPO update(MetaMtmCascadeExtUpdateDTO updateDTO) {
        // 校验别名
        MetadataUtil.jfieldNameCheck(updateDTO.getAlias());
        Integer mtmCascadeExtId = updateDTO.getMtmCascadeExtId();
        MetaMtmCascadeExtPO metaMtmCascadeExt = this.getMetaMtmCascadeExt(mtmCascadeExtId, true);
        // 获取项目，并校验操作人
        MetaProjectPO project = metaProjectService.getAndCheckProject(metaMtmCascadeExt.getProjectId());
        MetaMtmCascadeExtMapper.INSTANCE.setUpdateDTO(metaMtmCascadeExt, updateDTO);
        // 校验级联扩展
        this.checkCascadeExtPO(metaMtmCascadeExt);
        metaMtmCascadeExtDAO.update(metaMtmCascadeExt);
        metaProjectService.updateProject(project);
        return metaMtmCascadeExt;
    }

    /**
     * 查询列表
     *
     * @param metaMtmCascadeExtQO
     * @return
     */
    public List<MetaMtmCascadeExtListVO> list(MetaMtmCascadeExtQO metaMtmCascadeExtQO) {
        List<MetaMtmCascadeExtListVO> list = metaMtmCascadeExtDAO.findListByQuery(metaMtmCascadeExtQO);
        return list;
    }

    /**
     * 根据主键获取【多对多级联扩展】
     *
     * @param mtmCascadeExtId 主键
     * @param force           是否强制获取
     * @return
     */
    public MetaMtmCascadeExtPO getMetaMtmCascadeExt(Integer mtmCascadeExtId, boolean force) {
        MetaMtmCascadeExtPO metaMtmCascadeExt = metaMtmCascadeExtDAO.findById(mtmCascadeExtId);
        if (force && metaMtmCascadeExt == null) {
            throw new BusinessException(ErrorCode.RECORD_NOT_FIND);
        }
        return metaMtmCascadeExt;
    }


    /**
     * 查询【多对多级联扩展】详情
     *
     * @param mtmCascadeExtId
     * @return
     */
    public MetaMtmCascadeExtShowVO show(Integer mtmCascadeExtId) {
        MetaMtmCascadeExtPO metaMtmCascadeExt = this.getMetaMtmCascadeExt(mtmCascadeExtId, true);
        MetaMtmCascadeExtShowVO showVO = MetaMtmCascadeExtMapper.INSTANCE.toShowVO(metaMtmCascadeExt);
        MetaFieldPO cascadeField = metaFieldService.getField(metaMtmCascadeExt.getCascadeFieldId(), true);
        showVO.setCascadeFieldDesc(cascadeField.getFieldDesc());
        showVO.setCascadeJfieldName(cascadeField.getJfieldName());
        return showVO;
    }

    /**
     * 删除【多对多级联扩展】
     *
     * @param mtmCascadeExtIds
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public int delete(Integer... mtmCascadeExtIds) {
        int count = 0;
        for (Integer mtmCascadeExtId : mtmCascadeExtIds) {
            MetaMtmCascadeExtPO po = this.getMetaMtmCascadeExt(mtmCascadeExtId, false);
            if (po == null) {
                continue;
            }
            // 获取项目，并校验操作人
            MetaProjectPO project = metaProjectService.getAndCheckProject(po.getProjectId());
            count += metaMtmCascadeExtDAO.delete(mtmCascadeExtId);
            metaProjectService.updateProject(project);
        }
        return count;
    }


    /**
     * 校验级联扩展
     *
     * @param po
     */
    private void checkCascadeExtPO(MetaMtmCascadeExtPO po) {
        List<String> jFieldNames = metaFieldDAO.findJFieldNamesForQuery(po.getEntityId());
        if (po.getQuery() && jFieldNames.contains(po.getAlias())) {
            throw new BusinessException(ErrorCode.BAD_PARAMETER, "查询字段别名有冲突：" + po.getAlias());
        }
        // 校验重复添加
        boolean exists = metaMtmCascadeExtDAO.cascadeFieldIdExists(po.getMtmId(), po.getEntityId(),
            po.getCascadeFieldId(), po.getMtmCascadeExtId());
        if (exists) {
            throw new BusinessException(ErrorCode.BAD_PARAMETER, "字段重复");
        }
    }


    /**
     * 根据字段id查询级联扩展列表
     *
     * @param mtmId
     * @param entityId
     * @return
     */
    public List<MetaMtmCascadeExtPO> findByMtmIdAndEntityId(Integer mtmId, Integer entityId) {
        return metaMtmCascadeExtDAO.findByMtmIdAndEntityId(mtmId, entityId);
    }

    /**
     * 根据级联字段id查询主键列表
     *
     * @param cascadeFieldId
     * @return
     */
    public List<Integer> findPkByCascadeFieldId(Integer cascadeFieldId) {
        return metaMtmCascadeExtDAO.findPkByCascadeFieldId(cascadeFieldId);
    }


}


