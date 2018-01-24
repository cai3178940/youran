package com.youran.generate.service;

import com.youran.common.optimistic.OptimisticLock;
import com.youran.common.pojo.vo.PageVO;
import com.youran.generate.dao.MetaConstDAO;
import com.youran.generate.exception.GenerateException;
import com.youran.generate.pojo.dto.MetaConstAddDTO;
import com.youran.generate.pojo.qo.MetaConstQO;
import com.youran.generate.pojo.dto.MetaConstUpdateDTO;
import com.youran.generate.pojo.mapper.MetaConstMapper;
import com.youran.generate.pojo.po.MetaConstPO;
import com.youran.generate.pojo.vo.MetaConstListVO;
import com.youran.generate.pojo.vo.MetaConstShowVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Title:元数据常量增删改查服务
 * Description:
 * Author: cbb
 * Create Time:2017/5/12 11:17
 */
@Service
public class MetaConstService {

    @Autowired
    private MetaConstDAO metaConstDAO;

    /**
     * 新增常量
     * @param metaConstDTO
     * @return
     */
    @Transactional
    public MetaConstPO save(MetaConstAddDTO metaConstDTO) {
        MetaConstPO metaConst = MetaConstMapper.INSTANCE.fromAddDTO(metaConstDTO);
        metaConstDAO.save(metaConst);
        return metaConst;
    }

    /**
     * 修改常量
     * @param metaConstUpdateDTO
     * @return
     */
    @Transactional
    @OptimisticLock
    public void update(MetaConstUpdateDTO metaConstUpdateDTO) {
        Integer constId = metaConstUpdateDTO.getConstId();
        MetaConstPO metaConst = metaConstDAO.findById(constId);
        if (metaConst == null) {
            throw new GenerateException("constId有误");
        }
        MetaConstMapper.INSTANCE.setPO(metaConst, metaConstUpdateDTO);
        metaConstDAO.update(metaConst);
    }

    /**
     * 查询分页列表
     * @param metaConstQO
     * @return
     */
    public PageVO<MetaConstListVO> list(MetaConstQO metaConstQO) {
        PageVO<MetaConstListVO> page = metaConstDAO.findByPage(metaConstQO);
        return page;
    }

    /**
     * 查询常量详情
     * @param constId
     * @return
     */
    public MetaConstShowVO show(Integer constId) {
        MetaConstPO metaConst = metaConstDAO.findById(constId);
        if (metaConst == null) {
            throw new GenerateException("未查询到记录");
        }
        MetaConstShowVO showVO = MetaConstMapper.INSTANCE.toShowVO(metaConst);
        return showVO;
    }

    /**
     * 删除常量
     * @param constId
     * @return
     */
    @Transactional
    public int delete(Integer... constId) {
        int count = 0;
        for (Integer id : constId) {
            count += metaConstDAO.delete(id);
        }
        return count;
    }
}
