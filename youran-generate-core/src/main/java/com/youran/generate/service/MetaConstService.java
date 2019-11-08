package com.youran.generate.service;

import com.youran.common.constant.ErrorCode;
import com.youran.common.exception.BusinessException;
import com.youran.common.optimistic.OptimisticLock;
import com.youran.common.pojo.vo.PageVO;
import com.youran.generate.dao.MetaConstDAO;
import com.youran.generate.pojo.dto.MetaConstAddDTO;
import com.youran.generate.pojo.dto.MetaConstUpdateDTO;
import com.youran.generate.pojo.mapper.MetaConstMapper;
import com.youran.generate.pojo.po.MetaConstPO;
import com.youran.generate.pojo.po.MetaProjectPO;
import com.youran.generate.pojo.qo.MetaConstQO;
import com.youran.generate.pojo.vo.MetaConstListVO;
import com.youran.generate.pojo.vo.MetaConstShowVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 常量增删改查服务
 *
 * @author: cbb
 * @date: 2017/5/12
 */
@Service
public class MetaConstService {

    @Autowired
    private MetaConstDAO metaConstDAO;
    @Autowired
    private MetaProjectService metaProjectService;

    /**
     * 新增常量
     *
     * @param metaConstDTO
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public MetaConstPO save(MetaConstAddDTO metaConstDTO) {
        Integer projectId = metaConstDTO.getProjectId();
        MetaProjectPO project = metaProjectService.getAndCheckProject(projectId);
        MetaConstPO metaConst = MetaConstMapper.INSTANCE.fromAddDTO(metaConstDTO);
        this.doSave(metaConst);
        metaProjectService.updateProject(project);
        return metaConst;
    }

    public void doSave(MetaConstPO constPO) {
        metaConstDAO.save(constPO);
    }

    /**
     * 修改常量
     *
     * @param metaConstUpdateDTO
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    @OptimisticLock
    public MetaConstPO update(MetaConstUpdateDTO metaConstUpdateDTO) {
        Integer constId = metaConstUpdateDTO.getConstId();
        MetaConstPO metaConst = this.getConst(constId, true);
        Integer projectId = metaConst.getProjectId();
        MetaProjectPO project = metaProjectService.getAndCheckProject(projectId);
        MetaConstMapper.INSTANCE.setPO(metaConst, metaConstUpdateDTO);
        metaConstDAO.update(metaConst);
        metaProjectService.updateProject(project);
        return metaConst;
    }

    /**
     * 获取常量对象
     *
     * @param constId
     * @param force
     * @return
     */
    public MetaConstPO getConst(Integer constId, boolean force) {
        MetaConstPO metaConstPO = metaConstDAO.findById(constId);
        if (force && metaConstPO == null) {
            throw new BusinessException(ErrorCode.RECORD_NOT_FIND, "常量不存在");
        }
        return metaConstPO;
    }

    /**
     * 查询分页列表
     *
     * @param metaConstQO
     * @return
     */
    public PageVO<MetaConstListVO> list(MetaConstQO metaConstQO) {
        PageVO<MetaConstListVO> page = metaConstDAO.findByPage(metaConstQO);
        return page;
    }

    /**
     * 查询常量详情
     *
     * @param constId
     * @return
     */
    public MetaConstShowVO show(Integer constId) {
        MetaConstPO metaConst = this.getConst(constId, true);
        MetaConstShowVO showVO = MetaConstMapper.INSTANCE.toShowVO(metaConst);
        return showVO;
    }

    /**
     * 删除常量
     *
     * @param constId
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public int delete(Integer... constId) {
        int count = 0;
        for (Integer id : constId) {
            MetaConstPO metaConst = this.getConst(id, false);
            if (metaConst == null) {
                continue;
            }
            MetaProjectPO project = metaProjectService.getAndCheckProject(metaConst.getProjectId());
            count += metaConstDAO.delete(id);
            metaProjectService.updateProject(project);
        }
        return count;
    }

    /**
     * 根据项目id查询常量id列表
     *
     * @param projectId
     * @return
     */
    public List<Integer> findIdsByProject(Integer projectId) {
        return metaConstDAO.findIdsByProject(projectId);
    }


}
