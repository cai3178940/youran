package com.youran.generate.service;

import com.youran.common.optimistic.OptimisticLock;
import com.youran.generate.dao.MetaConstDetailDAO;
import com.youran.generate.exception.GenerateException;
import com.youran.generate.pojo.dto.MetaConstDetailAddDTO;
import com.youran.generate.pojo.dto.MetaConstDetailUpdateDTO;
import com.youran.generate.pojo.mapper.MetaConstDetailMapper;
import com.youran.generate.pojo.po.MetaConstDetailPO;
import com.youran.generate.pojo.qo.MetaConstDetailQO;
import com.youran.generate.pojo.vo.MetaConstDetailListVO;
import com.youran.generate.pojo.vo.MetaConstDetailShowVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>Title:常量值增删改查服务</p>
 * <p>Description:</p>
 * @author: cbb
 * @date: 2017/5/12
 */
@Service
public class MetaConstDetailService {

    @Autowired
    private MetaConstDetailDAO metaConstDetailDAO;
    @Autowired
    private MetaProjectService metaProjectService;
    /**
     * 新增常量值
     * @param metaConstDetailDTO
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public MetaConstDetailPO save(MetaConstDetailAddDTO metaConstDetailDTO) {
        Integer constId = metaConstDetailDTO.getConstId();
        //校验操作人
        metaProjectService.checkOperatorByConstId(constId);
        MetaConstDetailPO metaConstDetail = MetaConstDetailMapper.INSTANCE.fromAddDTO(metaConstDetailDTO);
        metaConstDetailDAO.save(metaConstDetail);
        metaProjectService.updateProjectVersionByConstId(constId);
        return metaConstDetail;
    }

    /**
     * 修改常量值
     * @param metaConstDetailUpdateDTO
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    @OptimisticLock
    public MetaConstDetailPO update(MetaConstDetailUpdateDTO metaConstDetailUpdateDTO) {
        MetaConstDetailPO metaConstDetail = this.getMetaConstDetail(metaConstDetailUpdateDTO.getConstDetailId(),true);
        Integer constId = metaConstDetail.getConstId();
        //校验操作人
        metaProjectService.checkOperatorByConstId(constId);
        MetaConstDetailMapper.INSTANCE.setPO(metaConstDetail, metaConstDetailUpdateDTO);
        metaConstDetailDAO.update(metaConstDetail);
        metaProjectService.updateProjectVersionByConstId(constId);
        return metaConstDetail;
    }

    /**
     * 获取枚举值对象
     * @param constDetailId
     * @param force
     * @return
     */
    public MetaConstDetailPO getMetaConstDetail(Integer constDetailId,boolean force){
        MetaConstDetailPO constDetailPO = metaConstDetailDAO.findById(constDetailId);
        if(force && constDetailPO==null){
            throw new GenerateException("枚举值未找到");
        }
        return constDetailPO;
    }

    /**
     * 查询分页列表
     * @param metaConstDetailQO
     * @return
     */
    public List<MetaConstDetailListVO> list(MetaConstDetailQO metaConstDetailQO) {
        return metaConstDetailDAO.findListByQuery(metaConstDetailQO);
    }

    /**
     * 查询常量值详情
     * @param constDetailId
     * @return
     */
    public MetaConstDetailShowVO show(Integer constDetailId) {
        MetaConstDetailPO metaConstDetail = this.getMetaConstDetail(constDetailId,true);
        MetaConstDetailShowVO showVO = MetaConstDetailMapper.INSTANCE.toShowVO(metaConstDetail);
        return showVO;
    }

    /**
     * 删除常量值
     * @param constDetailId
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public int delete(Integer... constDetailId) {
        int count = 0;
        Integer constId = null;
        for (Integer id : constDetailId) {
            MetaConstDetailPO metaConstDetail = this.getMetaConstDetail(id,false);
            if(metaConstDetail==null){
                continue;
            }
            constId = metaConstDetail.getConstId();
            //校验操作人
            metaProjectService.checkOperatorByConstId(constId);
            count += metaConstDetailDAO.delete(id);
        }
        if(count>0) {
            metaProjectService.updateProjectVersionByConstId(constId);
        }
        return count;
    }

    /**
     * 根据常量id查询常量值列表
     * @param constId
     * @return
     */
    public List<MetaConstDetailPO> findByConstId(Integer constId) {
        return metaConstDetailDAO.findByConstId(constId);
    }
}
