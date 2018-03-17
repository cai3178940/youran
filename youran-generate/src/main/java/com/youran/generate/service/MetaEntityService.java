package com.youran.generate.service;

import com.youran.common.optimistic.OptimisticLock;
import com.youran.common.pojo.vo.PageVO;
import com.youran.generate.dao.MetaEntityDAO;
import com.youran.generate.exception.GenerateException;
import com.youran.generate.pojo.dto.MetaEntityAddDTO;
import com.youran.generate.pojo.qo.MetaEntityQO;
import com.youran.generate.pojo.dto.MetaEntityUpdateDTO;
import com.youran.generate.pojo.mapper.MetaEntityMapper;
import com.youran.generate.pojo.po.MetaEntityPO;
import com.youran.generate.pojo.vo.MetaEntityListVO;
import com.youran.generate.pojo.vo.MetaEntityShowVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Title:元数据实体增删改查服务
 * Description:
 * Author: cbb
 * Create Time:2017/5/12 11:17
 */
@Service
public class MetaEntityService {

    @Autowired
    private MetaEntityDAO metaEntityDAO;
    @Autowired
    private MetaProjectService metaProjectService;

    /**
     * 新增实体
     * @param metaEntityDTO
     * @return
     */
    @Transactional
    public MetaEntityPO save(MetaEntityAddDTO metaEntityDTO) {
        MetaEntityPO metaEntity = MetaEntityMapper.INSTANCE.fromAddDTO(metaEntityDTO);
        metaEntityDAO.save(metaEntity);
        metaProjectService.updateProjectVersion(metaEntityDTO.getProjectId());
        return metaEntity;
    }

    /**
     * 修改实体
     * @param metaEntityUpdateDTO
     * @return
     */
    @Transactional
    @OptimisticLock
    public void update(MetaEntityUpdateDTO metaEntityUpdateDTO) {
        Integer entityId = metaEntityUpdateDTO.getEntityId();
        MetaEntityPO metaEntity = metaEntityDAO.findById(entityId);
        if (metaEntity == null) {
            throw new GenerateException("entityId有误");
        }
        MetaEntityMapper.INSTANCE.setPO(metaEntity, metaEntityUpdateDTO);
        metaEntityDAO.update(metaEntity);
        metaProjectService.updateProjectVersion(metaEntity.getProjectId());
    }

    /**
     * 查询分页列表
     * @param metaEntityQO
     * @return
     */
    public PageVO<MetaEntityListVO> list(MetaEntityQO metaEntityQO) {
        PageVO<MetaEntityListVO> page = metaEntityDAO.findByPage(metaEntityQO);
        return page;
    }

    /**
     * 查询实体详情
     * @param entityId
     * @return
     */
    public MetaEntityShowVO show(Integer entityId) {
        MetaEntityPO metaEntity = metaEntityDAO.findById(entityId);
        if (metaEntity == null) {
            throw new GenerateException("未查询到记录");
        }
        MetaEntityShowVO showVO = MetaEntityMapper.INSTANCE.toShowVO(metaEntity);
        return showVO;
    }

    /**
     * 删除实体
     * @param entityId
     * @return
     */
    @Transactional
    public int delete(Integer... entityId) {
        int count = 0;
        Integer projectId = null;
        for (Integer id : entityId) {
            MetaEntityPO entityPO = metaEntityDAO.findById(id);
            if(entityPO==null){
                continue;
            }
            projectId = entityPO.getProjectId();
            count += metaEntityDAO.delete(id);
        }
        if(count>0) {
            metaProjectService.updateProjectVersion(projectId);
        }
        return count;
    }
}
