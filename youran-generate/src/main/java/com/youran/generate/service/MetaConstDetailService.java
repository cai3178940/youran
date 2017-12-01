package com.youran.generate.service;

import com.youran.common.optimistic.OptimisticLock;
import com.youran.common.pojo.vo.PageVO;
import com.youran.generate.dao.MetaConstDetailDAO;
import com.youran.generate.exception.GenerateException;
import com.youran.generate.pojo.dto.MetaConstDetailAddDTO;
import com.youran.generate.pojo.dto.MetaConstDetailQueryDTO;
import com.youran.generate.pojo.dto.MetaConstDetailUpdateDTO;
import com.youran.generate.pojo.mapper.MetaConstDetailMapper;
import com.youran.generate.pojo.po.MetaConstDetailPO;
import com.youran.generate.pojo.vo.MetaConstDetailListVO;
import com.youran.generate.pojo.vo.MetaConstDetailShowVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Title:元数据常量值增删改查服务
 * Description:
 * Author: cbb
 * Create Time:2017/5/12 11:17
 */
@Service
public class MetaConstDetailService {

    @Autowired
    private MetaConstDetailDAO metaConstDetailDAO;

    /**
     * 新增常量值
     * @param metaConstDetailDTO
     * @return
     */
    @Transactional
    public MetaConstDetailPO save(MetaConstDetailAddDTO metaConstDetailDTO) {
        MetaConstDetailPO metaConstDetail = MetaConstDetailMapper.INSTANCE.fromAddDTO(metaConstDetailDTO);
        metaConstDetailDAO.save(metaConstDetail);
        return metaConstDetail;
    }

    /**
     * 修改常量值
     * @param metaConstDetailUpdateDTO
     * @return
     */
    @Transactional
    @OptimisticLock
    public void update(MetaConstDetailUpdateDTO metaConstDetailUpdateDTO) {
        Integer constDetailId = metaConstDetailUpdateDTO.getConstDetailId();
        MetaConstDetailPO metaConstDetail = metaConstDetailDAO.findById(constDetailId);
        if (metaConstDetail == null) {
            throw new GenerateException("constDetailId有误");
        }
        MetaConstDetailMapper.INSTANCE.setPO(metaConstDetail, metaConstDetailUpdateDTO);
        metaConstDetailDAO.update(metaConstDetail);
    }

    /**
     * 查询分页列表
     * @param metaConstDetailQueryDTO
     * @return
     */
    public PageVO<MetaConstDetailListVO> list(MetaConstDetailQueryDTO metaConstDetailQueryDTO) {
        PageVO<MetaConstDetailListVO> page = metaConstDetailDAO.findByPage(metaConstDetailQueryDTO);
        return page;
    }

    /**
     * 查询常量值详情
     * @param constDetailId
     * @return
     */
    public MetaConstDetailShowVO show(Integer constDetailId) {
        MetaConstDetailPO metaConstDetail = metaConstDetailDAO.findById(constDetailId);
        if (metaConstDetail == null) {
            throw new GenerateException("未查询到记录");
        }
        MetaConstDetailShowVO showVO = MetaConstDetailMapper.INSTANCE.toShowVO(metaConstDetail);
        return showVO;
    }

    /**
     * 删除常量值
     * @param constDetailId
     * @return
     */
    @Transactional
    public int delete(Integer... constDetailId) {
        int count = 0;
        for (Integer id : constDetailId) {
            count += metaConstDetailDAO.delete(id);
        }
        return count;
    }
}
