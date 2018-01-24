package com.youran.generate.service;

import com.youran.common.optimistic.OptimisticLock;
import com.youran.generate.dao.MetaEntityDAO;
import com.youran.generate.dao.MetaFieldDAO;
import com.youran.generate.exception.GenerateException;
import com.youran.generate.pojo.dto.MetaFieldAddDTO;
import com.youran.generate.pojo.qo.MetaFieldQO;
import com.youran.generate.pojo.dto.MetaFieldUpdateDTO;
import com.youran.generate.pojo.mapper.MetaFieldMapper;
import com.youran.generate.pojo.po.MetaFieldPO;
import com.youran.generate.pojo.vo.MetaFieldListVO;
import com.youran.generate.pojo.vo.MetaFieldShowVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Title:元数据字段增删改查服务
 * Description:
 * Author: cbb
 * Create Time:2017/5/12 11:17
 */
@Service
public class MetaFieldService {

    @Autowired
    private MetaFieldDAO metaFieldDAO;
    @Autowired
    private MetaEntityDAO metaEntityDAO;

    /**
     * 新增字段
     * @param metaFieldDTO
     * @return
     */
    @Transactional
    public MetaFieldPO save(MetaFieldAddDTO metaFieldDTO) {
        if (!metaEntityDAO.exist(metaFieldDTO.getEntityId())) {
            throw new GenerateException("entityId参数有误");
        }
        MetaFieldPO metaField = MetaFieldMapper.INSTANCE.fromAddDTO(metaFieldDTO);
        metaFieldDAO.save(metaField);
        return metaField;
    }

    /**
     * 修改字段
     * @param metaFieldUpdateDTO
     * @return
     */
    @Transactional
    @OptimisticLock
    public void update(MetaFieldUpdateDTO metaFieldUpdateDTO) {
        if (!metaEntityDAO.exist(metaFieldUpdateDTO.getEntityId())) {
            throw new GenerateException("entityId参数有误");
        }
        Integer fieldId = metaFieldUpdateDTO.getFieldId();
        MetaFieldPO metaField = metaFieldDAO.findById(fieldId);
        if (metaField == null) {
            throw new GenerateException("fieldId有误");
        }
        MetaFieldMapper.INSTANCE.setPO(metaField, metaFieldUpdateDTO);
        metaFieldDAO.update(metaField);
    }

    /**
     * 查询列表
     * @param metaFieldQO
     * @return
     */
    public List<MetaFieldListVO> list(MetaFieldQO metaFieldQO) {
        List<MetaFieldListVO> list = metaFieldDAO.findByQuery(metaFieldQO);
        return list;
    }

    /**
     * 查询字段详情
     * @param fieldId
     * @return
     */
    public MetaFieldShowVO show(Integer fieldId) {
        MetaFieldPO metaField = metaFieldDAO.findById(fieldId);
        if (metaField == null) {
            throw new GenerateException("未查询到记录");
        }
        MetaFieldShowVO showVO = MetaFieldMapper.INSTANCE.toShowVO(metaField);
        return showVO;
    }

    /**
     * 删除字段
     * @param fieldId
     * @return
     */
    @Transactional
    public int delete(Integer... fieldId) {
        int count = 0;
        for (Integer id : fieldId) {
            count += metaFieldDAO.delete(id);
        }
        return count;
    }

}
