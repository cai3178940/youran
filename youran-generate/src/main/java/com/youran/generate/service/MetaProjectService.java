package com.youran.generate.service;

import com.youran.common.constant.ErrorCode;
import com.youran.common.exception.BusinessException;
import com.youran.common.optimistic.OptimisticLock;
import com.youran.common.util.AESSecurityUtil;
import com.youran.common.util.JsonUtil;
import com.youran.generate.config.GenerateProperties;
import com.youran.generate.dao.MetaProjectDAO;
import com.youran.generate.pojo.dto.MetaProjectAddDTO;
import com.youran.generate.pojo.dto.MetaProjectFeatureDTO;
import com.youran.generate.pojo.dto.MetaProjectUpdateDTO;
import com.youran.generate.pojo.mapper.MetaProjectMapper;
import com.youran.generate.pojo.po.MetaConstPO;
import com.youran.generate.pojo.po.MetaEntityPO;
import com.youran.generate.pojo.po.MetaProjectPO;
import com.youran.generate.pojo.qo.CodeTemplateQO;
import com.youran.generate.pojo.qo.MetaProjectQO;
import com.youran.generate.pojo.vo.CodeTemplateListVO;
import com.youran.generate.pojo.vo.MetaProjectListVO;
import com.youran.generate.pojo.vo.MetaProjectShowVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 项目增删改查服务
 *
 * @author: cbb
 * @date 2017/5/24
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
    private CodeTemplateService codeTemplateService;

    /**
     * 获取项目正规名称
     *
     * @param projectId
     * @return
     */
    public String getNormalProjectName(Integer projectId) {
        MetaProjectPO projectPO = this.getProject(projectId, true);
        return projectPO.fetchNormalProjectName();
    }

    /**
     * 新增项目
     *
     * @param metaProjectDTO
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public MetaProjectPO save(MetaProjectAddDTO metaProjectDTO) {
        MetaProjectPO project = MetaProjectMapper.INSTANCE.fromAddDTO(metaProjectDTO);
        String password = project.getPassword();
        if (StringUtils.isNotBlank(password)) {
            String encrypt;
            try {
                encrypt = AESSecurityUtil.encrypt(password, generateProperties.getAesKey());
            } catch (Exception e) {
                LOGGER.error("密码加密异常", e);
                throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR, "密码加密异常");
            }
            project.setPassword(encrypt);
        }
        this.doSave(project);
        return project;
    }

    public void doSave(MetaProjectPO project) {
        project.setProjectVersion(1);
        metaProjectDAO.save(project);
    }

    /**
     * 修改项目
     *
     * @param updateDTO
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    @OptimisticLock
    public MetaProjectPO update(MetaProjectUpdateDTO updateDTO) {
        Integer projectId = updateDTO.getProjectId();
        MetaProjectPO project = this.getProject(projectId, true);
        this.doCheckOperator(project);
        MetaProjectMapper.INSTANCE.setPO(project, updateDTO);
        if (StringUtils.isNotBlank(updateDTO.getPassword())) {
            String encrypt;
            try {
                encrypt = AESSecurityUtil.encrypt(updateDTO.getPassword(), generateProperties.getAesKey());
            } catch (Exception e) {
                LOGGER.error("密码加密异常", e);
                throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR, "密码加密异常");
            }
            project.setPassword(encrypt);
        }
        metaProjectDAO.update(project);
        this.updateProjectVersion(project.getProjectId());
        return project;
    }


    /**
     * 查询项目实体
     *
     * @param projectId
     * @param force
     * @return
     */
    public MetaProjectPO getProject(Integer projectId, boolean force) {
        MetaProjectPO project = metaProjectDAO.findById(projectId);
        if (force && project == null) {
            throw new BusinessException(ErrorCode.RECORD_NOT_FIND, "项目不存在");
        }
        // 兼容旧数据，如果feature字段为空，则设置默认值
        if (StringUtils.isBlank(project.getFeature())) {
            project.setFeature(JsonUtil.toJSONString(new MetaProjectFeatureDTO()));
        }
        return project;
    }


    /**
     * 更新项目的最终提交历史
     *
     * @param projectId
     * @param historyId
     */
    public void updateLastHistory(Integer projectId, Integer historyId) {
        MetaProjectPO project = this.getProject(projectId, true);
        project.setLastHistoryId(historyId);
        metaProjectDAO.update(project);
    }


    /**
     * 查询分页列表
     *
     * @param metaProjectQO
     * @return
     */
    public List<MetaProjectListVO> list(MetaProjectQO metaProjectQO) {
        Map<Integer, CodeTemplateListVO> templateMap = codeTemplateService.list(new CodeTemplateQO())
            .stream().collect(Collectors.toMap(CodeTemplateListVO::getTemplateId, o -> o));
        List<MetaProjectListVO> list = metaProjectDAO.findListByQuery(metaProjectQO);
        list.forEach(vo -> {
            if(vo.getTemplateId()!=null) {
                CodeTemplateListVO template = templateMap.get(vo.getTemplateId());
                if(template!=null){
                    vo.setTemplateCode(template.getCode());
                }
            }
            if(vo.getTemplateId2()!=null) {
                CodeTemplateListVO template = templateMap.get(vo.getTemplateId2());
                if(template!=null){
                    vo.setTemplateCode2(template.getCode());
                }
            }
            if(vo.getTemplateId3()!=null) {
                CodeTemplateListVO template = templateMap.get(vo.getTemplateId3());
                if(template!=null){
                    vo.setTemplateCode3(template.getCode());
                }
            }
        });
        return list;
    }

    /**
     * 查询项目详情
     *
     * @param projectId
     * @return
     */
    public MetaProjectShowVO show(Integer projectId) {
        MetaProjectPO metaProject = this.getProject(projectId, true);
        MetaProjectShowVO showVO = MetaProjectMapper.INSTANCE.toShowVO(metaProject);
        return showVO;
    }

    /**
     * 删除项目
     *
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
     *
     * @param projectId
     */
    public void updateProjectVersion(Integer projectId) {
        MetaProjectPO projectPO = this.getProject(projectId, true);
        projectPO.setProjectVersion(projectPO.getProjectVersion() + 1);
        metaProjectDAO.update(projectPO);
    }

    /**
     * 通过entityId更新项目版本号
     *
     * @param entityId
     */
    public void updateProjectVersionByEntityId(Integer entityId) {
        MetaEntityPO entityPO = metaEntityService.getEntity(entityId, true);
        this.updateProjectVersion(entityPO.getProjectId());
    }

    /**
     * 通过constId更新项目版本号
     *
     * @param constId
     */
    public void updateProjectVersionByConstId(Integer constId) {
        MetaConstPO constPO = metaConstService.getConst(constId, true);
        this.updateProjectVersion(constPO.getProjectId());
    }

    /**
     * 根据项目id校验操作人
     * 如果当前操作人不是创建人，则抛异常
     *
     * @param projectId
     */
    public void checkOperatorByProjectId(Integer projectId) {
        MetaProjectPO projectPO = this.getProject(projectId, true);
        doCheckOperator(projectPO);
    }

    /**
     * 执行操作人校验
     *
     * @param projectPO
     */
    private void doCheckOperator(MetaProjectPO projectPO) {
        /*String currentUser = loginContext.getCurrentOperatorId();
        if(StringUtils.isBlank(currentUser)){
            throw new BusinessException("获取当前登录用户失败");
        }
        if(!currentUser.equals(projectPO.getCreatedBy())){
            throw new BusinessException("您不是该项目的创建者，无此操作权限");
        }*/
    }


    /**
     * 根据实体id校验操作人
     * 如果当前操作人不是创建人，则抛异常
     *
     * @param entityId
     */
    public void checkOperatorByEntityId(Integer entityId) {
        MetaEntityPO entityPO = metaEntityService.getEntity(entityId, true);
        this.checkOperatorByProjectId(entityPO.getProjectId());
    }


    /**
     * 根据常量id校验操作人
     * 如果当前操作人不是创建人，则抛异常
     *
     * @param constId
     */
    public void checkOperatorByConstId(Integer constId) {
        MetaConstPO constPO = metaConstService.getConst(constId, true);
        this.checkOperatorByProjectId(constPO.getProjectId());
    }


}
