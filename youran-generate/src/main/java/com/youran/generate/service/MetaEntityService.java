package com.youran.generate.service;

import com.youran.common.constant.ErrorCode;
import com.youran.common.exception.BusinessException;
import com.youran.common.optimistic.OptimisticLock;
import com.youran.generate.dao.MetaEntityDAO;
import com.youran.generate.dao.MetaManyToManyDAO;
import com.youran.generate.pojo.dto.MetaEntityAddDTO;
import com.youran.generate.pojo.dto.MetaEntityUpdateDTO;
import com.youran.generate.pojo.mapper.MetaEntityMapper;
import com.youran.generate.pojo.po.MetaEntityPO;
import com.youran.generate.pojo.qo.MetaEntityQO;
import com.youran.generate.pojo.vo.MetaEntityListVO;
import com.youran.generate.pojo.vo.MetaEntityShowVO;
import com.youran.generate.pojo.vo.MetaMtmEntityListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>Title:实体增删改查服务</p>
 * <p>Description:</p>
 * @author: cbb
 * @date: 2017/5/12
 */
@Service
public class MetaEntityService {

    @Autowired
    private MetaEntityDAO metaEntityDAO;
    @Autowired
    private MetaProjectService metaProjectService;
    @Autowired
    private MetaManyToManyDAO metaManyToManyDAO;



    /**
     * 校验数据唯一性
     * @param entity
     * @param isUpdate 是否更新校验
     */
    private void checkUnique(MetaEntityPO entity, boolean isUpdate){
        if(metaEntityDAO.classNameNotUnique(entity.getProjectId(),entity.getClassName(), isUpdate?entity.getEntityId():null)){
            throw new BusinessException(ErrorCode.BAD_PARAMETER,"类名重复");
        }
        if(metaEntityDAO.tableNameNotUnique(entity.getProjectId(),entity.getTableName(), isUpdate?entity.getEntityId():null)){
            throw new BusinessException(ErrorCode.BAD_PARAMETER,"表名重复");
        }
    }
    /**
     * 新增实体
     * @param metaEntityDTO
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public MetaEntityPO save(MetaEntityAddDTO metaEntityDTO) {
        Integer projectId = metaEntityDTO.getProjectId();
        //校验操作人
        metaProjectService.checkOperatorByProjectId(projectId);
        MetaEntityPO metaEntity = MetaEntityMapper.INSTANCE.fromAddDTO(metaEntityDTO);
        //唯一性校验
        this.checkUnique(metaEntity,false);
        metaEntityDAO.save(metaEntity);
        metaProjectService.updateProjectVersion(projectId);
        return metaEntity;
    }

    /**
     * 修改实体
     * @param metaEntityUpdateDTO
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    @OptimisticLock
    public MetaEntityPO update(MetaEntityUpdateDTO metaEntityUpdateDTO) {
        MetaEntityPO metaEntity = this.getEntity(metaEntityUpdateDTO.getEntityId(),true);
        Integer projectId = metaEntity.getProjectId();
        //校验操作人
        metaProjectService.checkOperatorByProjectId(projectId);
        MetaEntityMapper.INSTANCE.setPO(metaEntity, metaEntityUpdateDTO);
        //唯一性校验
        this.checkUnique(metaEntity,true);
        metaEntityDAO.update(metaEntity);
        metaProjectService.updateProjectVersion(projectId);
        return metaEntity;
    }


    /**
     * 查询实体
     * @param entityId
     * @param force
     * @return
     */
    public MetaEntityPO getEntity(Integer entityId,boolean force){
        MetaEntityPO metaEntity = metaEntityDAO.findById(entityId);
        if (force && metaEntity == null) {
            throw new BusinessException(ErrorCode.RECORD_NOT_FIND,"实体不存在");
        }
        return metaEntity;
    }

    /**
     * 查询项目下的实体id列表
     * @param projectId
     * @return
     */
    public List<Integer> findIdsByProject(Integer projectId){
        List<Integer> ids = metaEntityDAO.findIdsByProject(projectId);
        return ids;
    }

    /**
     * 查询列表
     * @param metaEntityQO
     * @return
     */
    public List<MetaEntityListVO> list(MetaEntityQO metaEntityQO) {
        List<MetaEntityListVO> page = metaEntityDAO.findListByQuery(metaEntityQO);
        return page;
    }

    /**
     * 查询实体详情
     * @param entityId
     * @return
     */
    public MetaEntityShowVO show(Integer entityId) {
        MetaEntityPO metaEntity = this.getEntity(entityId,true);
        MetaEntityShowVO showVO = MetaEntityMapper.INSTANCE.toShowVO(metaEntity);
        return showVO;
    }

    /**
     * 删除实体
     * @param entityId
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public int delete(Integer... entityId) {
        int count = 0;
        Integer projectId = null;
        for (Integer id : entityId) {
            MetaEntityPO entityPO = this.getEntity(id,false);
            if(entityPO==null){
                continue;
            }
            this.checkDeleteByMtm(id);
            projectId = entityPO.getProjectId();
            //校验操作人
            metaProjectService.checkOperatorByProjectId(projectId);
            count += metaEntityDAO.delete(id);
        }
        if(count>0) {
            metaProjectService.updateProjectVersion(projectId);
        }
        return count;
    }

    /**
     * 校验是否存在【多对多】关联
     * @param entityId
     */
    private void checkDeleteByMtm(Integer entityId) {
        int count = metaManyToManyDAO.getCountByEntityId(entityId);
        if(count>0){
            throw new BusinessException(ErrorCode.INNER_DATA_ERROR,"请先删除多对多关联");
        }
    }


    /**
     * 查询某实体下的多对多关联实体列表
     * @param entityId 实体id
     * @param hold 是否持有引用
     * @return
     */
    public List<MetaMtmEntityListVO> mtmEntityList(Integer entityId, boolean hold) {
        return metaEntityDAO.findMtmEntityList(entityId,hold);
    }
}
