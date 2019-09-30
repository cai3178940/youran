package com.youran.generate.service;

import com.youran.common.constant.BoolConst;
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
import com.youran.generate.pojo.qo.MetaMtmCascadeExtQO;
import com.youran.generate.pojo.vo.MetaMtmCascadeExtListVO;
import com.youran.generate.pojo.vo.MetaMtmCascadeExtShowVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>Title: 【多对多级联扩展】删改查服务</p>
 * <p>Description: </p>
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
     * @param metaMtmCascadeExtDTO
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public MetaMtmCascadeExtPO save(MetaMtmCascadeExtAddDTO metaMtmCascadeExtDTO) {
        Integer entityId = metaMtmCascadeExtDTO.getEntityId();
        // 校验操作人
        metaProjectService.checkOperatorByEntityId(entityId);
        MetaMtmCascadeExtPO metaMtmCascadeExt = MetaMtmCascadeExtMapper.INSTANCE.fromAddDTO(metaMtmCascadeExtDTO);
        // 校验级联扩展
        this.checkCascadeExtPO(metaMtmCascadeExt);
        metaMtmCascadeExtDAO.save(metaMtmCascadeExt);
        metaProjectService.updateProjectVersionByEntityId(entityId);
        return metaMtmCascadeExt;
    }

    /**
     * 修改【多对多级联扩展】
     * @param metaMtmCascadeExtUpdateDTO
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    @OptimisticLock
    public MetaMtmCascadeExtPO update(MetaMtmCascadeExtUpdateDTO metaMtmCascadeExtUpdateDTO) {
        Integer mtmCascadeExtId = metaMtmCascadeExtUpdateDTO.getMtmCascadeExtId();
        MetaMtmCascadeExtPO metaMtmCascadeExt = this.getMetaMtmCascadeExt(mtmCascadeExtId, true);
        Integer entityId = metaMtmCascadeExt.getEntityId();
        // 校验操作人
        metaProjectService.checkOperatorByEntityId(entityId);
        MetaMtmCascadeExtMapper.INSTANCE.setUpdateDTO(metaMtmCascadeExt,metaMtmCascadeExtUpdateDTO);
        // 校验级联扩展
        this.checkCascadeExtPO(metaMtmCascadeExt);
        metaMtmCascadeExtDAO.update(metaMtmCascadeExt);
        metaProjectService.updateProjectVersionByEntityId(entityId);
        return metaMtmCascadeExt;
    }

    /**
     * 查询列表
     * @param metaMtmCascadeExtQO
     * @return
     */
    public List<MetaMtmCascadeExtListVO> list(MetaMtmCascadeExtQO metaMtmCascadeExtQO) {
        List<MetaMtmCascadeExtListVO> list = metaMtmCascadeExtDAO.findListByQuery(metaMtmCascadeExtQO);
        return list;
    }

    /**
     * 根据主键获取【多对多级联扩展】
     * @param mtmCascadeExtId 主键
     * @param force 是否强制获取
     * @return
     */
    public MetaMtmCascadeExtPO getMetaMtmCascadeExt(Integer mtmCascadeExtId, boolean force){
        MetaMtmCascadeExtPO metaMtmCascadeExt = metaMtmCascadeExtDAO.findById(mtmCascadeExtId);
        if (force && metaMtmCascadeExt == null) {
            throw new BusinessException(ErrorCode.RECORD_NOT_FIND);
        }
        return metaMtmCascadeExt;
    }


    /**
     * 查询【多对多级联扩展】详情
     * @param mtmCascadeExtId
     * @return
     */
    public MetaMtmCascadeExtShowVO show(Integer mtmCascadeExtId) {
        MetaMtmCascadeExtPO metaMtmCascadeExt = this.getMetaMtmCascadeExt(mtmCascadeExtId, true);
        MetaMtmCascadeExtShowVO showVO = MetaMtmCascadeExtMapper.INSTANCE.toShowVO(metaMtmCascadeExt);
        MetaFieldPO cascadeField = metaFieldService.getField(metaMtmCascadeExt.getCascadeFieldId(),true);
        showVO.setCascadeFieldDesc(cascadeField.getFieldDesc());
        showVO.setCascadeJfieldName(cascadeField.getJfieldName());
        return showVO;
    }

    /**
     * 删除【多对多级联扩展】
     * @param mtmCascadeExtIds
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public int delete(Integer... mtmCascadeExtIds) {
        int count = 0;
        Integer entityId = null;
        for (Integer mtmCascadeExtId : mtmCascadeExtIds) {
            MetaMtmCascadeExtPO po = this.getMetaMtmCascadeExt(mtmCascadeExtId,false);
            if(po==null){
                continue;
            }
            entityId = po.getEntityId();
            //校验操作人
            metaProjectService.checkOperatorByEntityId(entityId);
            count += metaMtmCascadeExtDAO.delete(mtmCascadeExtId);
        }
        if(count>0) {
            metaProjectService.updateProjectVersionByEntityId(entityId);
        }
        return count;
    }


    /**
     * 校验级联扩展
     * @param po
     */
    private void checkCascadeExtPO(MetaMtmCascadeExtPO po){
        List<String> jFieldNames = metaFieldDAO.findJFieldNamesForQuery(po.getEntityId());
        if(BoolConst.isTrue(po.getQuery()) && jFieldNames.contains(po.getAlias())){
            throw new BusinessException(ErrorCode.BAD_PARAMETER,"查询字段别名有冲突："+po.getAlias());
        }
        // 校验重复添加
        boolean exists = metaMtmCascadeExtDAO.cascadeFieldIdExists(po.getMtmId(),po.getEntityId(),
            po.getCascadeFieldId(), po.getMtmCascadeExtId());
        if(exists){
            throw new BusinessException(ErrorCode.BAD_PARAMETER,"字段重复");
        }
        // TODO 校验当前实体下其他多对多级联扩展字段
    }


    /**
     * 根据字段id查询级联扩展列表
     * @param mtmId
     * @param entityId
     * @return
     */
    public List<MetaMtmCascadeExtPO> findByMtmIdAndEntityId(Integer mtmId, Integer entityId) {
        return metaMtmCascadeExtDAO.findByMtmIdAndEntityId(mtmId,entityId);
    }

    /**
     * 根据级联字段id查询主键列表
     * @param cascadeFieldId
     * @return
     */
    public List<Integer> findPkByCascadeFieldId(Integer cascadeFieldId) {
        return metaMtmCascadeExtDAO.findPkByCascadeFieldId(cascadeFieldId);
    }


}


