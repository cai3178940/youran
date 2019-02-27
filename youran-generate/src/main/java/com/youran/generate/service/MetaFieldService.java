package com.youran.generate.service;

import com.youran.common.optimistic.OptimisticLock;
import com.youran.generate.dao.MetaFieldDAO;
import com.youran.generate.exception.GenerateException;
import com.youran.generate.pojo.dto.MetaFieldAddDTO;
import com.youran.generate.pojo.dto.MetaFieldUpdateDTO;
import com.youran.generate.pojo.mapper.MetaFieldMapper;
import com.youran.generate.pojo.po.MetaFieldPO;
import com.youran.generate.pojo.qo.MetaFieldQO;
import com.youran.generate.pojo.vo.MetaFieldListVO;
import com.youran.generate.pojo.vo.MetaFieldShowVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>Title:字段增删改查服务</p>
 * <p>Description:</p>
 * @author: cbb
 * @date: 2017/5/12
 */
@Service
public class MetaFieldService {

    @Autowired
    private MetaFieldDAO metaFieldDAO;
    @Autowired
    private MetaProjectService metaProjectService;
    /**
     * 新增字段
     * @param metaFieldDTO
     * @return
     */
    @Transactional
    public MetaFieldPO save(MetaFieldAddDTO metaFieldDTO) {
        Integer entityId = metaFieldDTO.getEntityId();
        //校验操作人
        metaProjectService.checkOperatorByEntityId(entityId);
        MetaFieldPO metaField = MetaFieldMapper.INSTANCE.fromAddDTO(metaFieldDTO);
        metaFieldDAO.save(metaField);
        metaProjectService.updateProjectVersionByEntityId(entityId);
        return metaField;
    }

    /**
     * 修改字段
     * @param metaFieldUpdateDTO
     * @return
     */
    @Transactional
    @OptimisticLock
    public MetaFieldPO update(MetaFieldUpdateDTO metaFieldUpdateDTO) {
        Integer fieldId = metaFieldUpdateDTO.getFieldId();
        MetaFieldPO metaField = this.getField(fieldId,true);
        Integer entityId = metaField.getEntityId();
        //校验操作人
        metaProjectService.checkOperatorByEntityId(entityId);
        MetaFieldMapper.INSTANCE.setPO(metaField, metaFieldUpdateDTO);
        metaFieldDAO.update(metaField);
        metaProjectService.updateProjectVersionByEntityId(entityId);
        return metaField;
    }

    /**
     * 获取字段对象
     * @param fieldId
     * @param force
     * @return
     */
    public MetaFieldPO getField(Integer fieldId, boolean force){
        MetaFieldPO fieldPO = metaFieldDAO.findById(fieldId);
        if(force && fieldPO == null){
            throw new GenerateException("字段未找到");
        }
        return fieldPO;
    }

    /**
     * 查询列表
     * @param metaFieldQO
     * @return
     */
    public List<MetaFieldListVO> list(MetaFieldQO metaFieldQO) {
        List<MetaFieldListVO> list = metaFieldDAO.findListByQuery(metaFieldQO);
        return list;
    }

    /**
     * 查询字段详情
     * @param fieldId
     * @return
     */
    public MetaFieldShowVO show(Integer fieldId) {
        MetaFieldPO metaField = this.getField(fieldId,true);
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
        Integer entityId = null;
        for (Integer id : fieldId) {
            MetaFieldPO metaField = this.getField(id,false);
            if(metaField==null){
                continue;
            }
            entityId = metaField.getEntityId();
            //校验操作人
            metaProjectService.checkOperatorByEntityId(entityId);
            count += metaFieldDAO.delete(id);
        }
        if(count>0) {
            metaProjectService.updateProjectVersionByEntityId(entityId);
        }
        return count;
    }

    /**
     * 根据实体id查询字段列表
     * @param entityId
     * @return
     */
    public List<MetaFieldPO> findByEntityId(Integer entityId) {
        return metaFieldDAO.findByEntityId(entityId);

    }
}
