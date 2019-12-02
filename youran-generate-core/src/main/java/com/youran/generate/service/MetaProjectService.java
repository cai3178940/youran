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
        MetaProjectPO projectPO = this.getProject(projectId, true, false);
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
        MetaProjectPO project = this.getAndCheckProject(projectId);
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
        this.updateProject(project);
        return project;
    }


    /**
     * 查询项目,同时校验用户权限
     *
     * @param projectId
     * @return
     */
    public MetaProjectPO getAndCheckProject(Integer projectId) {
        return this.getProject(projectId, true, true);
    }

    /**
     * 查询项目实体
     *
     * @param projectId
     * @param force
     * @param checkOperator 是否校验操作人
     * @return
     */
    public MetaProjectPO getProject(Integer projectId, boolean force, boolean checkOperator) {
        MetaProjectPO project = metaProjectDAO.findById(projectId);
        if (force && project == null) {
            throw new BusinessException(ErrorCode.RECORD_NOT_FIND, "项目不存在");
        }
        if (checkOperator) {
            this.checkOperatorByProject(project);
        }
        // 兼容旧数据，如果feature字段为空，则设置默认值
        if (StringUtils.isBlank(project.getFeature())) {
            project.setFeature(JsonUtil.toJSONString(new MetaProjectFeatureDTO()));
        }
        return project;
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
            if (vo.getTemplateId() != null) {
                CodeTemplateListVO template = templateMap.get(vo.getTemplateId());
                if (template == null) {
                    vo.setTemplateId(null);
                }
            }
            if (vo.getTemplateId2() != null) {
                CodeTemplateListVO template = templateMap.get(vo.getTemplateId2());
                if (template == null) {
                    vo.setTemplateId2(null);
                }
            }
            if (vo.getTemplateId3() != null) {
                CodeTemplateListVO template = templateMap.get(vo.getTemplateId3());
                if (template == null) {
                    vo.setTemplateId3(null);
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
        MetaProjectPO metaProject = this.getAndCheckProject(projectId);
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
            this.getAndCheckProject(id);
            count += metaProjectDAO.delete(id);
        }
        return count;
    }


    /**
     * 更新项目信息（增加内部版本号）
     *
     * @param projectPO
     */
    public void updateProject(MetaProjectPO projectPO) {
        projectPO.setProjectVersion(projectPO.getProjectVersion() + 1);
        metaProjectDAO.update(projectPO);
    }

    /**
     * 通过实体Id获取项目
     *
     * @param entityId      实体id
     * @param checkOperator 是否校验用户权限
     * @return 项目
     */
    public MetaProjectPO getProjectByEntityId(Integer entityId, boolean checkOperator) {
        MetaEntityPO entityPO = metaEntityService.getEntity(entityId, true);
        MetaProjectPO projectPO = this.getProject(entityPO.getProjectId(), true, checkOperator);
        return projectPO;
    }

    /**
     * 通过常量Id获取项目
     *
     * @param constId       常量id
     * @param checkOperator 是否校验用户权限
     * @return 项目
     */
    public MetaProjectPO getProjectByConstId(Integer constId, boolean checkOperator) {
        MetaConstPO constPO = metaConstService.getConst(constId, true);
        MetaProjectPO projectPO = this.getProject(constPO.getProjectId(), true, checkOperator);
        return projectPO;
    }


    /**
     * 根据项目校验操作人
     *
     * @param projectPO
     */
    public void checkOperatorByProject(MetaProjectPO projectPO) {
        /*String currentUser = loginContext.getCurrentOperatorId();
        if(StringUtils.isBlank(currentUser)){
            throw new BusinessException("获取当前登录用户失败");
        }
        if(!currentUser.equals(projectPO.getCreatedBy())){
            throw new BusinessException("您不是该项目的创建者，无此操作权限");
        }*/
    }


}
