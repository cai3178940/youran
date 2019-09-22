package com.youran.generate.service;

import com.youran.common.constant.ErrorCode;
import com.youran.common.exception.BusinessException;
import com.youran.common.optimistic.OptimisticLock;
import com.youran.generate.dao.MetaMtmCascadeExtDAO;
import com.youran.generate.pojo.dto.MetaMtmCascadeExtAddDTO;
import com.youran.generate.pojo.dto.MetaMtmCascadeExtUpdateDTO;
import com.youran.generate.pojo.mapper.MetaMtmCascadeExtMapper;
import com.youran.generate.pojo.po.MetaMtmCascadeExtPO;
import com.youran.generate.pojo.qo.MetaMtmCascadeExtQO;
import com.youran.generate.pojo.vo.MetaMtmCascadeExtListVO;
import com.youran.generate.pojo.vo.MetaMtmCascadeExtShowVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>Title: 【多对多级联扩展】删改查服务</p>
 * <p>Description: </p>
 * @author cbb
 * @date 2019/09/21
 */
@Service
public class MetaMtmCascadeExtService {



    @Autowired
    private MetaMtmCascadeExtDAO metaMtmCascadeExtDAO;


    /**
     * 新增【多对多级联扩展】
     * @param metaMtmCascadeExtDTO
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public MetaMtmCascadeExtPO save(MetaMtmCascadeExtAddDTO metaMtmCascadeExtDTO) {
        MetaMtmCascadeExtPO metaMtmCascadeExt = MetaMtmCascadeExtMapper.INSTANCE.fromAddDTO(metaMtmCascadeExtDTO);
        metaMtmCascadeExtDAO.save(metaMtmCascadeExt);
        return metaMtmCascadeExt;
    }

    /**
     * 修改【多对多级联扩展】
     * @param metaMtmCascadeExtUpdateDTO
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    @OptimisticLock
    public MetaMtmCascadeExtPO update(MetaMtmCascadeExtUpdateDTO metaMtmCascadeExtUpdateDTO) {
        Integer cascadeMtmExtId = metaMtmCascadeExtUpdateDTO.getCascadeMtmExtId();
        MetaMtmCascadeExtPO metaMtmCascadeExt = this.getMetaMtmCascadeExt(cascadeMtmExtId, true);
        MetaMtmCascadeExtMapper.INSTANCE.setUpdateDTO(metaMtmCascadeExt,metaMtmCascadeExtUpdateDTO);
        metaMtmCascadeExtDAO.update(metaMtmCascadeExt);
        return metaMtmCascadeExt;
    }
    /**
     * 查询列表
     * @param metaMtmCascadeExtQO
     * @return
     */
    public List<MetaMtmCascadeExtListVO> list(MetaMtmCascadeExtQO metaMtmCascadeExtQO) {
        List<MetaMtmCascadeExtListVO> list = metaMtmCascadeExtDAO.findListByQuery(metaMtmCascadeExtQO);
        return list;
    }

    /**
     * 根据主键获取【多对多级联扩展】
     * @param cascadeMtmExtId 主键
     * @param force 是否强制获取
     * @return
     */
    public MetaMtmCascadeExtPO getMetaMtmCascadeExt(Integer cascadeMtmExtId, boolean force){
        MetaMtmCascadeExtPO metaMtmCascadeExt = metaMtmCascadeExtDAO.findById(cascadeMtmExtId);
        if (force && metaMtmCascadeExt == null) {
            throw new BusinessException(ErrorCode.RECORD_NOT_FIND);
        }
        return metaMtmCascadeExt;
    }


    /**
     * 查询【多对多级联扩展】详情
     * @param cascadeMtmExtId
     * @return
     */
    public MetaMtmCascadeExtShowVO show(Integer cascadeMtmExtId) {
        MetaMtmCascadeExtPO metaMtmCascadeExt = this.getMetaMtmCascadeExt(cascadeMtmExtId, true);
        MetaMtmCascadeExtShowVO showVO = MetaMtmCascadeExtMapper.INSTANCE.toShowVO(metaMtmCascadeExt);
        return showVO;
    }

    /**
     * 删除【多对多级联扩展】
     * @param cascadeMtmExtIds
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public int delete(Integer... cascadeMtmExtIds) {
        int count = 0;
        for (Integer cascadeMtmExtId : cascadeMtmExtIds) {
            count += metaMtmCascadeExtDAO.delete(cascadeMtmExtId);
        }
        return count;
    }


}


