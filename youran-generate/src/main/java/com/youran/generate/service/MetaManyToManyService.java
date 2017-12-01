package com.youran.generate.service;

import com.youran.common.optimistic.OptimisticLock;
import com.youran.generate.dao.MetaEntityDAO;
import com.youran.generate.dao.MetaManyToManyDAO;
import com.youran.generate.exception.GenerateException;
import com.youran.generate.pojo.dto.MetaManyToManyAddDTO;
import com.youran.generate.pojo.dto.MetaManyToManyQueryDTO;
import com.youran.generate.pojo.dto.MetaManyToManyUpdateDTO;
import com.youran.generate.pojo.mapper.MetaManyToManyMapper;
import com.youran.generate.pojo.po.MetaManyToManyPO;
import com.youran.generate.pojo.vo.MetaManyToManyListVO;
import com.youran.generate.pojo.vo.MetaManyToManyShowVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Title:多对多关联增删改查服务
 * Description:
 * Author: cbb
 * Create Time:2017/5/12 11:17
 */
@Service
public class MetaManyToManyService {

    @Autowired
    private MetaManyToManyDAO metaManyToManyDAO;
    @Autowired
    private MetaEntityDAO metaEntityDAO;

    /**
     * 新增多对多关联
     * @param metaManyToManyDTO
     * @return
     */
    @Transactional
    public MetaManyToManyPO save(MetaManyToManyAddDTO metaManyToManyDTO) {
        if (!metaEntityDAO.exist(metaManyToManyDTO.getEntityId1())) {
            throw new GenerateException("entityId1参数有误");
        }
        if (!metaEntityDAO.exist(metaManyToManyDTO.getEntityId2())) {
            throw new GenerateException("entityId2参数有误");
        }
        MetaManyToManyPO metaManyToMany = MetaManyToManyMapper.INSTANCE.fromAddDTO(metaManyToManyDTO);
        metaManyToManyDAO.save(metaManyToMany);
        return metaManyToMany;
    }

    /**
     * 修改多对多关联
     * @param metaManyToManyUpdateDTO
     * @return
     */
    @Transactional
    @OptimisticLock
    public void update(MetaManyToManyUpdateDTO metaManyToManyUpdateDTO) {
        if (!metaEntityDAO.exist(metaManyToManyUpdateDTO.getEntityId1())) {
            throw new GenerateException("entityId1参数有误");
        }
        if (!metaEntityDAO.exist(metaManyToManyUpdateDTO.getEntityId2())) {
            throw new GenerateException("entityId2参数有误");
        }
        Integer mtmId = metaManyToManyUpdateDTO.getMtmId();
        MetaManyToManyPO metaManyToMany = metaManyToManyDAO.findById(mtmId);
        if (metaManyToMany == null) {
            throw new GenerateException("mtmId有误");
        }
        MetaManyToManyMapper.INSTANCE.setPO(metaManyToMany, metaManyToManyUpdateDTO);
        metaManyToManyDAO.update(metaManyToMany);
    }

    /**
     * 查询列表
     * @param metaManyToManyQueryDTO
     * @return
     */
    public List<MetaManyToManyListVO> list(MetaManyToManyQueryDTO metaManyToManyQueryDTO) {
        List<MetaManyToManyListVO> list = metaManyToManyDAO.findByQuery(metaManyToManyQueryDTO);
        return list;
    }

    /**
     * 查询多对多关联详情
     * @param mtmId
     * @return
     */
    public MetaManyToManyShowVO show(Integer mtmId) {
        MetaManyToManyPO metaManyToMany = metaManyToManyDAO.findById(mtmId);
        if (metaManyToMany == null) {
            throw new GenerateException("未查询到记录");
        }
        MetaManyToManyShowVO showVO = MetaManyToManyMapper.INSTANCE.toShowVO(metaManyToMany);
        return showVO;
    }

    /**
     * 删除多对多关联
     * @param mtmId
     * @return
     */
    @Transactional
    public int delete(Integer... mtmId) {
        int count = 0;
        for (Integer id : mtmId) {
            count += metaManyToManyDAO.delete(id);
        }
        return count;
    }

}
