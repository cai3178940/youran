package com.youran.generate.service;

import com.youran.common.optimistic.OptimisticLock;
import com.youran.common.service.AbstractService;
import com.youran.generate.dao.MetaConstDAO;
import com.youran.generate.dao.MetaConstDetailDAO;
import com.youran.generate.exception.GenerateException;
import com.youran.generate.pojo.dto.MetaConstDetailAddDTO;
import com.youran.generate.pojo.dto.MetaConstDetailUpdateDTO;
import com.youran.generate.pojo.mapper.MetaConstDetailMapper;
import com.youran.generate.pojo.po.MetaConstDetailPO;
import com.youran.generate.pojo.po.MetaConstPO;
import com.youran.generate.pojo.qo.MetaConstDetailQO;
import com.youran.generate.pojo.vo.MetaConstDetailListVO;
import com.youran.generate.pojo.vo.MetaConstDetailShowVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Title:元数据常量值增删改查服务
 * Description:
 * Author: cbb
 * Create Time:2017/5/12 11:17
 */
@Service
public class MetaConstDetailService extends AbstractService {

    @Autowired
    private MetaConstDetailDAO metaConstDetailDAO;
    @Autowired
    private MetaConstDAO metaConstDAO;
    @Autowired
    private MetaProjectService metaProjectService;
    /**
     * 新增常量值
     * @param metaConstDetailDTO
     * @return
     */
    @Transactional
    public MetaConstDetailPO save(MetaConstDetailAddDTO metaConstDetailDTO) {
        MetaConstPO metaConst = metaConstDAO.findById(metaConstDetailDTO.getConstId());
        if (metaConst==null) {
            throw new GenerateException("constId参数有误");
        }
        MetaConstDetailPO metaConstDetail = MetaConstDetailMapper.INSTANCE.fromAddDTO(metaConstDetailDTO);
        metaConstDetail.preInsert(loginContext.getCurrentOperatorId());
        metaConstDetailDAO.save(metaConstDetail);
        metaProjectService.updateProjectVersion(metaConst.getProjectId());
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
        MetaConstDetailPO metaConstDetail = metaConstDetailDAO.findById(metaConstDetailUpdateDTO.getConstDetailId());
        if (metaConstDetail == null) {
            throw new GenerateException("constDetailId有误");
        }
        MetaConstDetailMapper.INSTANCE.setPO(metaConstDetail, metaConstDetailUpdateDTO);
        metaConstDetail.preUpdate(loginContext.getCurrentOperatorId());
        metaConstDetailDAO.update(metaConstDetail);
        MetaConstPO metaConst = metaConstDAO.findById(metaConstDetail.getConstId());
        metaProjectService.updateProjectVersion(metaConst.getProjectId());
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
        Integer constId = null;
        for (Integer id : constDetailId) {
            MetaConstDetailPO metaConstDetail = metaConstDetailDAO.findById(id);
            if(metaConstDetail==null){
                continue;
            }
            constId = metaConstDetail.getConstId();
            count += metaConstDetailDAO.delete(id);
        }
        if(count>0) {
            MetaConstPO metaConst = metaConstDAO.findById(constId);
            metaProjectService.updateProjectVersion(metaConst.getProjectId());
        }
        return count;
    }
}
