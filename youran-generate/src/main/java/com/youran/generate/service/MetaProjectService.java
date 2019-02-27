package com.youran.generate.service;

import com.youran.common.context.LoginContext;
import com.youran.common.optimistic.OptimisticLock;
import com.youran.common.util.AESSecurityUtil;
import com.youran.generate.config.GenerateProperties;
import com.youran.generate.dao.MetaProjectDAO;
import com.youran.generate.exception.GenerateException;
import com.youran.generate.pojo.dto.MetaProjectAddDTO;
import com.youran.generate.pojo.dto.MetaProjectUpdateDTO;
import com.youran.generate.pojo.mapper.MetaProjectMapper;
import com.youran.generate.pojo.po.MetaConstPO;
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
 * <p>Title:项目增删改查服务</p>
 * <p>Description:</p>
 * @author: cbb
 * Create Time:2017/5/24
 */
@Service
public class MetaProjectService {

    private final static Logger LOGGER = LoggerFactory.getLogger(MetaProjectService.class);

    @Autowired
    private MetaEntityService metaEntityService;
    @Autowired
    private MetaConstService metaConstService;
    @Autowired
    private MetaProjectDAO metaProjectDAO;

    @Autowired
    private GenerateProperties generateProperties;

    @Autowired
    private LoginContext loginContext;

    public String getNormalProjectName(Integer projectId){
        MetaProjectPO projectPO = this.getProject(projectId,true);
        return projectPO.fetchNormalProjectName();
    }
    /**
     * 新增项目
     * @param metaProjectDTO
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
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
     * @param updateDTO
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    @OptimisticLock
    public MetaProjectPO update(MetaProjectUpdateDTO updateDTO) {
        Integer projectId = updateDTO.getProjectId();
        MetaProjectPO project = this.getProject(projectId,true);
        this.doCheckOperator(project);
        MetaProjectMapper.INSTANCE.setPO(project, updateDTO);
        if(StringUtils.isNotBlank(updateDTO.getPassword())){
            String encrypt;
            try {
                encrypt = AESSecurityUtil.encrypt(updateDTO.getPassword(), generateProperties.getAesKey());
            } catch (Exception e) {
                LOGGER.error("密码加密异常",e);
                throw new GenerateException("密码加密异常");
            }
            project.setPassword(encrypt);
        }
        metaProjectDAO.update(project);
        this.updateProjectVersion(project.getProjectId());
        return project;
    }


    /**
     * 查询项目实体
     * @param projectId
     * @param force
     * @return
     */
    public MetaProjectPO getProject(Integer projectId, boolean force) {
        MetaProjectPO metaProject = metaProjectDAO.findById(projectId);
        if (force && metaProject == null) {
            throw new GenerateException("项目不存在");
        }
        return metaProject;
    }


    /**
     * 更新项目的最终提交历史
     * @param projectId
     * @param historyId
     */
    public void updateLastHistory(Integer projectId,Integer historyId){
        MetaProjectPO project = this.getProject(projectId, true);
        project.setLastHistoryId(historyId);
        metaProjectDAO.update(project);
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
    @Transactional(rollbackFor = RuntimeException.class)
    public int delete(Integer... projectId) {
        int count = 0;
        for (Integer id : projectId) {
            this.checkOperatorByProjectId(id);
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
        MetaEntityPO entityPO = metaEntityService.getEntity(entityId,true);
        this.updateProjectVersion(entityPO.getProjectId());
    }
    /**
     * 通过constId更新项目版本号
     * @param constId
     */
    public void updateProjectVersionByConstId(Integer constId) {
        MetaConstPO constPO = metaConstService.getConst(constId, true);
        this.updateProjectVersion(constPO.getProjectId());
    }

    /**
     * 根据项目id校验操作人
     * 如果当前操作人不是创建人，则抛异常
     * @param projectId
     */
    public void checkOperatorByProjectId(Integer projectId){
        MetaProjectPO projectPO = this.getProject(projectId,true);
        doCheckOperator(projectPO);
    }

    /**
     * 执行操作人校验
     * @param projectPO
     */
    private void doCheckOperator(MetaProjectPO projectPO) {
        String currentUser = loginContext.getCurrentOperatorId();
        if(StringUtils.isBlank(currentUser)){
            throw new GenerateException("获取当前登录用户失败");
        }
        if(!currentUser.equals(projectPO.getCreatedBy())){
            throw new GenerateException("您不是该项目的创建者，无此操作权限");
        }
    }


    /**
     * 根据实体id校验操作人
     * 如果当前操作人不是创建人，则抛异常
     * @param entityId
     */
    public void checkOperatorByEntityId(Integer entityId) {
        MetaEntityPO entityPO = metaEntityService.getEntity(entityId,true);
        this.checkOperatorByProjectId(entityPO.getProjectId());
    }


    /**
     * 根据常量id校验操作人
     * 如果当前操作人不是创建人，则抛异常
     * @param constId
     */
    public void checkOperatorByConstId(Integer constId) {
        MetaConstPO constPO = metaConstService.getConst(constId, true);
        this.checkOperatorByProjectId(constPO.getProjectId());
    }




}
