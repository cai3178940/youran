package com.youran.generate.service;

import com.youran.common.optimistic.OptimisticLock;
import com.youran.common.service.AbstractService;
import com.youran.generate.dao.MetaEntityDAO;
import com.youran.generate.dao.MetaManyToManyDAO;
import com.youran.generate.exception.GenerateException;
import com.youran.generate.pojo.dto.MetaManyToManyAddDTO;
import com.youran.generate.pojo.dto.MetaManyToManyUpdateDTO;
import com.youran.generate.pojo.mapper.MetaManyToManyMapper;
import com.youran.generate.pojo.po.MetaManyToManyPO;
import com.youran.generate.pojo.qo.MetaManyToManyQO;
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
public class MetaManyToManyService extends AbstractService {

    @Autowired
    private MetaManyToManyDAO metaManyToManyDAO;
    @Autowired
    private MetaEntityDAO metaEntityDAO;
    @Autowired
    private MetaProjectService metaProjectService;

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
        metaManyToMany.preInsert(loginContext.getCurrentOperatorId());
        metaManyToManyDAO.save(metaManyToMany);
        metaProjectService.updateProjectVersion(metaManyToMany.getProjectId());
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
        metaManyToMany.preUpdate(loginContext.getCurrentOperatorId());
        metaManyToManyDAO.update(metaManyToMany);
        metaProjectService.updateProjectVersion(metaManyToMany.getProjectId());
    }

    /**
     * 查询列表
     * @param metaManyToManyQO
     * @return
     */
    public List<MetaManyToManyListVO> list(MetaManyToManyQO metaManyToManyQO) {
        List<MetaManyToManyListVO> list = metaManyToManyDAO.findListByQuery(metaManyToManyQO);
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
        Integer projectId = null;
        for (Integer id : mtmId) {
            MetaManyToManyPO manyToMany = metaManyToManyDAO.findById(id);
            if(manyToMany==null){
                continue;
            }
            projectId = manyToMany.getProjectId();
            count += metaManyToManyDAO.delete(id);
        }
        if(count>0) {
            metaProjectService.updateProjectVersion(projectId);
        }
        return count;
    }

}
