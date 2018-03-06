package com.youran.generate.service;

import com.youran.common.optimistic.OptimisticLock;
import com.youran.generate.dao.MetaProjectDAO;
import com.youran.generate.exception.GenerateException;
import com.youran.generate.pojo.dto.MetaProjectAddDTO;
import com.youran.generate.pojo.qo.MetaProjectQO;
import com.youran.generate.pojo.dto.MetaProjectUpdateDTO;
import com.youran.generate.pojo.mapper.MetaProjectMapper;
import com.youran.generate.pojo.po.MetaProjectPO;
import com.youran.generate.pojo.vo.MetaProjectListVO;
import com.youran.generate.pojo.vo.MetaProjectShowVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Title:元数据项目增删改查服务
 * Description:
 * Author: cbb
 * Create Time:2017/5/24
 */
@Service
public class MetaProjectService {

    @Autowired
    private MetaProjectDAO metaProjectDAO;

    public String getNormalProjectName(Integer projectId){
        MetaProjectPO projectPO = metaProjectDAO.findById(projectId);
        return projectPO.fetchNormalProjectName();
    }
    /**
     * 新增项目
     * @param metaProjectDTO
     * @return
     */
    @Transactional
    public MetaProjectPO save(MetaProjectAddDTO metaProjectDTO) {
        MetaProjectPO metaProject = MetaProjectMapper.INSTANCE.fromAddDTO(metaProjectDTO);
        metaProjectDAO.save(metaProject);
        return metaProject;
    }

    /**
     * 修改项目
     * @param metaProjectUpdateDTO
     * @return
     */
    @Transactional
    @OptimisticLock
    public void update(MetaProjectUpdateDTO metaProjectUpdateDTO) {
        Integer projectId = metaProjectUpdateDTO.getProjectId();
        MetaProjectPO metaProject = metaProjectDAO.findById(projectId);
        if (metaProject == null) {
            throw new GenerateException("projectId有误");
        }
        MetaProjectMapper.INSTANCE.setPO(metaProject, metaProjectUpdateDTO);
        metaProjectDAO.update(metaProject);
    }

    /**
     * 查询分页列表
     * @param metaProjectQO
     * @return
     */
    public List<MetaProjectListVO> list(MetaProjectQO metaProjectQO) {
        return metaProjectDAO.findByQuery(metaProjectQO);
    }

    /**
     * 查询项目详情
     * @param projectId
     * @return
     */
    public MetaProjectShowVO show(Integer projectId) {
        MetaProjectPO metaProject = metaProjectDAO.findById(projectId);
        if (metaProject == null) {
            throw new GenerateException("未查询到记录");
        }
        MetaProjectShowVO showVO = MetaProjectMapper.INSTANCE.toShowVO(metaProject);
        return showVO;
    }

    /**
     * 删除项目
     * @param projectId
     * @return
     */
    @Transactional
    public int delete(Integer... projectId) {
        int count = 0;
        for (Integer id : projectId) {
            count += metaProjectDAO.delete(id);
        }
        return count;
    }
}
