package com.youran.generate.service;

import com.youran.common.optimistic.OptimisticLock;
import com.youran.generate.dao.MetaCascadeExtDAO;
import com.youran.generate.dao.MetaFieldDAO;
import com.youran.generate.exception.GenerateException;
import com.youran.generate.pojo.dto.MetaCascadeExtAddDTO;
import com.youran.generate.pojo.dto.MetaCascadeExtUpdateDTO;
import com.youran.generate.pojo.mapper.MetaCascadeExtMapper;
import com.youran.generate.pojo.po.MetaCascadeExtPO;
import com.youran.generate.pojo.po.MetaFieldPO;
import com.youran.generate.pojo.qo.MetaCascadeExtQO;
import com.youran.generate.pojo.vo.MetaCascadeExtListVO;
import com.youran.generate.pojo.vo.MetaCascadeExtShowVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Title:级联扩展增删改查服务
 * Description:
 * Author: cbb
 * Create Time:2017/5/24
 */
@Service
public class MetaCascadeExtService {

    private final static Logger LOGGER = LoggerFactory.getLogger(MetaCascadeExtService.class);

    @Autowired
    private MetaCascadeExtDAO metaCascadeExtDAO;
    @Autowired
    private MetaProjectService metaProjectService;
    @Autowired
    private MetaFieldDAO metaFieldDAO;


    /**
     * 新增级联扩展
     * @param addDTO
     * @return
     */
    @Transactional
    public MetaCascadeExtPO save(MetaCascadeExtAddDTO addDTO) {
        MetaCascadeExtPO metaCascadeExt = MetaCascadeExtMapper.INSTANCE.fromAddDTO(addDTO);
        metaCascadeExtDAO.save(metaCascadeExt);
        metaProjectService.updateProjectVersionByEntityId(metaCascadeExt.getEntityId());
        return metaCascadeExt;
    }

    /**
     * 修改级联扩展
     * @param updateDTO
     * @return
     */
    @Transactional
    @OptimisticLock
    public void update(MetaCascadeExtUpdateDTO updateDTO) {
        MetaCascadeExtPO metaCascadeExt = metaCascadeExtDAO.findById(updateDTO.getCascadeExtId());
        if (metaCascadeExt == null) {
            throw new GenerateException("cascadeExtId有误");
        }
        MetaCascadeExtMapper.INSTANCE.setPO(metaCascadeExt, updateDTO);
        metaCascadeExtDAO.update(metaCascadeExt);
        metaProjectService.updateProjectVersionByEntityId(metaCascadeExt.getEntityId());
    }

    /**
     * 查询分页列表
     * @param metaCascadeExtQO
     * @return
     */
    public List<MetaCascadeExtListVO> list(MetaCascadeExtQO metaCascadeExtQO) {
        return metaCascadeExtDAO.findByQuery(metaCascadeExtQO);
    }

    /**
     * 查询级联扩展
     * @param cascadeExtId
     * @return
     */
    public MetaCascadeExtShowVO show(Integer cascadeExtId) {
        MetaCascadeExtPO metaCascadeExt = metaCascadeExtDAO.findById(cascadeExtId);
        if (metaCascadeExt == null) {
            throw new GenerateException("未查询到记录");
        }
        MetaCascadeExtShowVO showVO = MetaCascadeExtMapper.INSTANCE.toShowVO(metaCascadeExt);
        MetaFieldPO cascadeField = metaFieldDAO.findById(metaCascadeExt.getCascadeFieldId());
        showVO.setCascadeFieldDesc(cascadeField.getFieldDesc());
        showVO.setCascadeJfieldName(cascadeField.getJfieldName());
        return showVO;
    }

    /**
     * 删除级联扩展
     * @param cascadeExtId
     * @return
     */
    @Transactional
    public int delete(Integer... cascadeExtId) {
        int count = 0;
        Integer entityId = null;
        for (Integer id : cascadeExtId) {
            MetaCascadeExtPO cascadeExtPO = metaCascadeExtDAO.findById(id);
            if(cascadeExtPO==null){
                continue;
            }
            entityId = cascadeExtPO.getEntityId();
            count += metaCascadeExtDAO.delete(id);
        }
        if(count>0) {
            metaProjectService.updateProjectVersionByEntityId(entityId);
        }
        return count;
    }


}
