package com.youran.generate.service;

import com.youran.common.optimistic.OptimisticLock;
import com.youran.common.util.AESSecurityUtil;
import com.youran.generate.config.GenerateProperties;
import com.youran.generate.dao.MetaEntityDAO;
import com.youran.generate.dao.MetaProjectDAO;
import com.youran.generate.exception.GenerateException;
import com.youran.generate.pojo.dto.MetaProjectAddDTO;
import com.youran.generate.pojo.dto.MetaProjectUpdateDTO;
import com.youran.generate.pojo.mapper.MetaProjectMapper;
import com.youran.generate.pojo.po.MetaEntityPO;
import com.youran.generate.pojo.po.MetaProjectPO;
import com.youran.generate.pojo.qo.MetaProjectQO;
import com.youran.generate.pojo.vo.MetaProjectListVO;
import com.youran.generate.pojo.vo.MetaProjectShowVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final static Logger LOGGER = LoggerFactory.getLogger(MetaProjectService.class);

    @Autowired
    private MetaEntityDAO metaEntityDAO;
    @Autowired
    private MetaProjectDAO metaProjectDAO;

    @Autowired
    private GenerateProperties generateProperties;

    public String getNormalProjectName(Integer projectId){
        MetaProjectPO projectPO = this.getProject(projectId,true);
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
        String password = metaProject.getPassword();
        if(StringUtils.isNotBlank(password)){
            String encrypt;
            try {
                encrypt = AESSecurityUtil.encrypt(password, generateProperties.getAesKey());
            } catch (Exception e) {
                LOGGER.error("密码加密异常",e);
                throw new GenerateException("密码加密异常");
            }
            metaProject.setPassword(encrypt);
        }
        metaProject.setProjectVersion(1);
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
        MetaProjectPO metaProject = this.getProject(projectId,true);
        MetaProjectMapper.INSTANCE.setPO(metaProject, metaProjectUpdateDTO);
        if(StringUtils.isNotBlank(metaProjectUpdateDTO.getPassword())){
            String encrypt;
            try {
                encrypt = AESSecurityUtil.encrypt(metaProjectUpdateDTO.getPassword(), generateProperties.getAesKey());
            } catch (Exception e) {
                LOGGER.error("密码加密异常",e);
                throw new GenerateException("密码加密异常");
            }
            metaProject.setPassword(encrypt);
        }
        metaProjectDAO.update(metaProject);
        this.updateProjectVersion(metaProject.getProjectId());
    }


    public MetaProjectPO getProject(Integer projectId, boolean force) {
        MetaProjectPO metaProject = metaProjectDAO.findById(projectId);
        if (force && metaProject == null) {
            throw new GenerateException("未查询到项目");
        }

        return metaProject;
    }

    /**
     * 查询分页列表
     * @param metaProjectQO
     * @return
     */
    public List<MetaProjectListVO> list(MetaProjectQO metaProjectQO) {
        return metaProjectDAO.findListByQuery(metaProjectQO);
    }

    /**
     * 查询项目详情
     * @param projectId
     * @return
     */
    public MetaProjectShowVO show(Integer projectId) {
        MetaProjectPO metaProject = this.getProject(projectId,true);
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


    /**
     * 更新项目版本号
     * @param projectId
     */
    public void updateProjectVersion(Integer projectId){
        MetaProjectPO projectPO = this.getProject(projectId,true);
        projectPO.setProjectVersion(projectPO.getProjectVersion()+1);
        metaProjectDAO.update(projectPO);
    }
    /**
     * 通过entityId更新项目版本号
     * @param entityId
     */
    public void updateProjectVersionByEntityId(Integer entityId) {
        MetaEntityPO entityPO = metaEntityDAO.findById(entityId);
        this.updateProjectVersion(entityPO.getProjectId());
    }


}
