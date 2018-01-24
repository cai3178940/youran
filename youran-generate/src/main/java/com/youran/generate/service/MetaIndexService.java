package com.youran.generate.service;

import com.google.common.base.Joiner;
import com.youran.common.optimistic.OptimisticLock;
import com.youran.common.util.ConvertUtil;
import com.youran.generate.dao.MetaEntityDAO;
import com.youran.generate.dao.MetaFieldDAO;
import com.youran.generate.dao.MetaIndexDAO;
import com.youran.generate.dao.MetaIndexFieldDAO;
import com.youran.generate.exception.GenerateException;
import com.youran.generate.pojo.dto.MetaIndexAddDTO;
import com.youran.generate.pojo.qo.MetaIndexQO;
import com.youran.generate.pojo.dto.MetaIndexUpdateDTO;
import com.youran.generate.pojo.mapper.MetaIndexMapper;
import com.youran.generate.pojo.po.MetaIndexPO;
import com.youran.generate.pojo.vo.MetaIndexListVO;
import com.youran.generate.pojo.vo.MetaIndexShowVO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Title:元数据索引增删改查服务
 * Description:
 * Author: cbb
 * Create Time:2017/5/12 11:17
 */
@Service
public class MetaIndexService {

    @Autowired
    private MetaEntityDAO metaEntityDAO;
    @Autowired
    private MetaFieldDAO metaFieldDAO;
    @Autowired
    private MetaIndexDAO metaIndexDAO;
    @Autowired
    private MetaIndexFieldDAO metaIndexFieldDAO;

    /**
     * 新增索引
     * @param metaIndexAddDTO
     * @return
     */
    @Transactional
    public MetaIndexPO save(MetaIndexAddDTO metaIndexAddDTO) {
        if (!metaEntityDAO.exist(metaIndexAddDTO.getEntityId())) {
            throw new GenerateException("entityId参数有误");
        }
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
        return metaIndex;
    }

    /**
     * 修改索引
     * @param metaIndexUpdateDTO
     * @return
     */
    @Transactional
    @OptimisticLock
    public void update(MetaIndexUpdateDTO metaIndexUpdateDTO) {
        if (!metaEntityDAO.exist(metaIndexUpdateDTO.getEntityId())) {
            throw new GenerateException("entityId参数有误");
        }
        Integer indexId = metaIndexUpdateDTO.getIndexId();
        MetaIndexPO metaIndex = metaIndexDAO.findById(indexId);
        if (metaIndex == null) {
            throw new GenerateException("indexId有误");
        }
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
    }

    /**
     * 查询列表
     * @param metaIndexQO
     * @return
     */
    public List<MetaIndexListVO> list(MetaIndexQO metaIndexQO) {
        List<MetaIndexListVO> list = metaIndexDAO.findByQuery(metaIndexQO);
        if (CollectionUtils.isNotEmpty(list)) {
            for (MetaIndexListVO metaIndexListVO : list) {
                List<Integer> fieldIds = metaIndexFieldDAO.findByIndexId(metaIndexListVO.getIndexId());
                metaIndexListVO.setFieldIds(Joiner.on(",").join(fieldIds));
            }
        }
        return list;
    }

    /**
     * 查询索引详情
     * @param indexId
     * @return
     */
    public MetaIndexShowVO show(Integer indexId) {
        MetaIndexPO metaIndex = metaIndexDAO.findById(indexId);
        if (metaIndex == null) {
            throw new GenerateException("未查询到记录");
        }
        MetaIndexShowVO showVO = MetaIndexMapper.INSTANCE.toShowVO(metaIndex);
        List<Integer> fieldIds = metaIndexFieldDAO.findByIndexId(showVO.getIndexId());
        showVO.setFieldIds(Joiner.on(",").join(fieldIds));
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
        for (Integer id : indexId) {
            metaIndexFieldDAO.delete(id);
            count += metaIndexDAO.delete(id);
        }
        return count;
    }

}
