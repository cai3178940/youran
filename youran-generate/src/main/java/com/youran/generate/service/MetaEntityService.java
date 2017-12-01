package com.youran.generate.service;

import com.youran.common.optimistic.OptimisticLock;
import com.youran.common.pojo.vo.PageVO;
import com.youran.generate.dao.MetaEntityDAO;
import com.youran.generate.exception.GenerateException;
import com.youran.generate.pojo.dto.MetaEntityAddDTO;
import com.youran.generate.pojo.dto.MetaEntityQueryDTO;
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

    /**
     * 新增实体
     * @param metaEntityDTO
     * @return
     */
    @Transactional
    public MetaEntityPO save(MetaEntityAddDTO metaEntityDTO) {
        MetaEntityPO metaEntity = MetaEntityMapper.INSTANCE.fromAddDTO(metaEntityDTO);
        metaEntityDAO.save(metaEntity);
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
    }

    /**
     * 查询分页列表
     * @param metaEntityQueryDTO
     * @return
     */
    public PageVO<MetaEntityListVO> list(MetaEntityQueryDTO metaEntityQueryDTO) {
        PageVO<MetaEntityListVO> page = metaEntityDAO.findByPage(metaEntityQueryDTO);
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
        for (Integer id : entityId) {
            count += metaEntityDAO.delete(id);
        }
        return count;
    }
}
