package com.youran.generate.service;

import com.youran.common.optimistic.OptimisticLock;
import com.youran.common.util.ConvertUtil;
import com.youran.generate.dao.MetaFieldDAO;
import com.youran.generate.dao.MetaIndexDAO;
import com.youran.generate.dao.MetaIndexFieldDAO;
import com.youran.generate.exception.GenerateException;
import com.youran.generate.pojo.dto.MetaIndexAddDTO;
import com.youran.generate.pojo.dto.MetaIndexUpdateDTO;
import com.youran.generate.pojo.mapper.MetaIndexMapper;
import com.youran.generate.pojo.po.MetaIndexPO;
import com.youran.generate.pojo.qo.MetaIndexQO;
import com.youran.generate.pojo.vo.MetaFieldListVO;
import com.youran.generate.pojo.vo.MetaIndexListVO;
import com.youran.generate.pojo.vo.MetaIndexShowVO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Title:索引增删改查服务
 * Description:
 * Author: cbb
 * Create Time:2017/5/12 11:17
 */
@Service
public class MetaIndexService {

    @Autowired
    private MetaFieldDAO metaFieldDAO;
    @Autowired
    private MetaIndexDAO metaIndexDAO;
    @Autowired
    private MetaIndexFieldDAO metaIndexFieldDAO;
    @Autowired
    private MetaProjectService metaProjectService;
    /**
     * 新增索引
     * @param metaIndexAddDTO
     * @return
     */
    @Transactional
    public MetaIndexPO save(MetaIndexAddDTO metaIndexAddDTO) {
        //校验字段id是否是本实体下存在的字段
        String fieldIds = metaIndexAddDTO.getFieldIds();
        List<Integer> fieldIdList =  ConvertUtil.convertIntegerList(fieldIds);
        int fieldCount = metaFieldDAO.findCount(metaIndexAddDTO.getEntityId(), fieldIdList);
        if (fieldCount != fieldIdList.size()) {
            throw new GenerateException("索引字段异常");
        }
        //映射属性
        MetaIndexPO metaIndex = MetaIndexMapper.INSTANCE.fromAddDTO(metaIndexAddDTO);
        //保存索引对象
        metaIndexDAO.save(metaIndex);
        //保存关联关系
        int count = metaIndexFieldDAO.saveBatch(metaIndex.getIndexId(), fieldIdList);
        if (count == 0 || fieldIdList.size() != count) {
            throw new GenerateException("索引保存异常");
        }
        metaProjectService.updateProjectVersionByEntityId(metaIndexAddDTO.getEntityId());
        return metaIndex;
    }

    /**
     * 修改索引
     * @param metaIndexUpdateDTO
     * @return
     */
    @Transactional
    @OptimisticLock
    public MetaIndexPO update(MetaIndexUpdateDTO metaIndexUpdateDTO) {
        Integer indexId = metaIndexUpdateDTO.getIndexId();
        MetaIndexPO metaIndex = this.getIndex(indexId,true);
        //校验新字段id是否是本实体下存在的字段
        String fieldIds = metaIndexUpdateDTO.getFieldIds();
        List<Integer> fieldIdList = ConvertUtil.convertIntegerList(fieldIds);
        int fieldCount = metaFieldDAO.findCount(metaIndexUpdateDTO.getEntityId(), fieldIdList);
        if (fieldCount != fieldIdList.size()) {
            throw new GenerateException("索引字段异常");
        }
        //映射属性
        MetaIndexMapper.INSTANCE.setPO(metaIndex, metaIndexUpdateDTO);
        //修改索引对象
        metaIndexDAO.update(metaIndex);
        //先清除旧关联关系
        metaIndexFieldDAO.delete(metaIndex.getIndexId());
        //保存新的关联关系
        int count = metaIndexFieldDAO.saveBatch(metaIndex.getIndexId(), fieldIdList);
        if (count == 0 || fieldIdList.size() != count) {
            throw new GenerateException("索引更新异常");
        }

        metaProjectService.updateProjectVersionByEntityId(metaIndexUpdateDTO.getEntityId());
        return metaIndex;
    }

    /**
     * 获取索引对象
     * @param indexId
     * @param force
     * @return
     */
    public MetaIndexPO getIndex(Integer indexId,boolean force){
        MetaIndexPO indexPO = metaIndexDAO.findById(indexId);
        if(force && indexPO == null){
            throw new GenerateException("索引未找到");
        }
        return indexPO;
    }

    /**
     * 查询列表
     * @param metaIndexQO
     * @return
     */
    public List<MetaIndexListVO> list(MetaIndexQO metaIndexQO) {
        List<MetaIndexListVO> list = metaIndexDAO.findListByQuery(metaIndexQO);
        return list;
    }

    /**
     * 查询索引详情
     * @param indexId
     * @return
     */
    public MetaIndexShowVO show(Integer indexId) {
        MetaIndexPO metaIndex = this.getIndex(indexId,true);
        MetaIndexShowVO showVO = MetaIndexMapper.INSTANCE.toShowVO(metaIndex);
        List<MetaFieldListVO> fields = metaIndexFieldDAO.findByIndexId(showVO.getIndexId());
        showVO.setFields(fields);
        return showVO;
    }

    /**
     * 删除索引
     * @param indexId
     * @return
     */
    @Transactional
    public int delete(Integer... indexId) {
        int count = 0;
        Integer entityId = null;
        for (Integer id : indexId) {
            MetaIndexPO metaIndex = this.getIndex(id,false);
            if(metaIndex==null){
                continue;
            }
            entityId = metaIndex.getEntityId();
            metaIndexFieldDAO.delete(id);
            count += metaIndexDAO.delete(id);
        }
        if(count>0) {
            metaProjectService.updateProjectVersionByEntityId(entityId);
        }
        return count;
    }

    /**
     * 移除索引字段
     * @param indexId
     * @param fieldIds
     * @return
     */
    @Transactional
    public int removeField(Integer indexId, List<Integer> fieldIds) {
        MetaIndexPO metaIndex = this.getIndex(indexId,false);
        if(metaIndex==null){
            return 0;
        }
        int count = metaIndexFieldDAO.remove(indexId,fieldIds);
        if(count==0){
            return 0;
        }
        List<MetaFieldListVO> fields = metaIndexFieldDAO.findByIndexId(indexId);
        // 如果所有字段都已经清空，则删除整个索引
        if(CollectionUtils.isEmpty(fields)){
            metaIndexDAO.delete(indexId);
        }
        if(count>0) {
            metaProjectService.updateProjectVersionByEntityId(metaIndex.getEntityId());
        }
        return count;
    }

    /**
     * 根据实体id查询索引对象列表
     * @param entityId
     * @return
     */
    public List<MetaIndexPO> findByEntityId(Integer entityId) {
        return metaIndexDAO.findByEntityId(entityId);
    }

    /**
     * 根据索引id查询字段id列表
     * @param indexId
     * @return
     */
    public List<Integer> findFieldIdsByIndexId(Integer indexId) {
        return metaIndexFieldDAO.findIdsByIndexId(indexId);
    }
}
