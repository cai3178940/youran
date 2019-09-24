package com.youran.generate.service;

import com.youran.common.constant.ErrorCode;
import com.youran.common.exception.BusinessException;
import com.youran.common.optimistic.OptimisticLock;
import com.youran.generate.dao.MetaEntityDAO;
import com.youran.generate.dao.MetaManyToManyDAO;
import com.youran.generate.pojo.dto.MetaManyToManyAddDTO;
import com.youran.generate.pojo.dto.MetaManyToManyUpdateDTO;
import com.youran.generate.pojo.mapper.MetaManyToManyMapper;
import com.youran.generate.pojo.po.MetaManyToManyPO;
import com.youran.generate.pojo.qo.MetaManyToManyQO;
import com.youran.generate.pojo.vo.MetaManyToManyListVO;
import com.youran.generate.pojo.vo.MetaManyToManyShowVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * <p>Title:多对多关联增删改查服务</p>
 * <p>Description:</p>
 * @author: cbb
 * @date: 2017/5/12
 */
@Service
public class MetaManyToManyService {

    @Autowired
    private MetaManyToManyDAO metaManyToManyDAO;
    @Autowired
    private MetaEntityDAO metaEntityDAO;
    @Autowired
    private MetaProjectService metaProjectService;

    /**
     * 新增多对多关联
     * @param metaManyToManyDTO
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public MetaManyToManyPO save(MetaManyToManyAddDTO metaManyToManyDTO) {
        Integer projectId = metaManyToManyDTO.getProjectId();
        //校验操作人
        metaProjectService.checkOperatorByProjectId(projectId);
        MetaManyToManyPO metaManyToMany = MetaManyToManyMapper.INSTANCE.fromAddDTO(metaManyToManyDTO);
        //校验数据是否合法
        this.checkManyToMany(metaManyToMany);
        metaManyToManyDAO.save(metaManyToMany);
        metaProjectService.updateProjectVersion(projectId);
        return metaManyToMany;
    }

    /**
     * 修改多对多关联
     * @param metaManyToManyUpdateDTO
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    @OptimisticLock
    public MetaManyToManyPO update(MetaManyToManyUpdateDTO metaManyToManyUpdateDTO) {
        Integer mtmId = metaManyToManyUpdateDTO.getMtmId();
        MetaManyToManyPO metaManyToMany = this.getMetaManyToMany(mtmId,true);
        Integer projectId = metaManyToMany.getProjectId();
        //校验操作人
        metaProjectService.checkOperatorByProjectId(projectId);
        MetaManyToManyMapper.INSTANCE.setPO(metaManyToMany, metaManyToManyUpdateDTO);
        //校验数据是否合法
        this.checkManyToMany(metaManyToMany);
        metaManyToManyDAO.update(metaManyToMany);
        metaProjectService.updateProjectVersion(projectId);
        return metaManyToMany;
    }

    /**
     * 校验多对多是否合法
     * @param mtm
     */
    private void checkManyToMany(MetaManyToManyPO mtm){
        if (!metaEntityDAO.exist(mtm.getEntityId1())) {
            throw new BusinessException(ErrorCode.BAD_PARAMETER,"entityId1参数有误");
        }
        if (!metaEntityDAO.exist(mtm.getEntityId2())) {
            throw new BusinessException(ErrorCode.BAD_PARAMETER,"entityId2参数有误");
        }
        if(Objects.equals(mtm.getEntityId1(),mtm.getEntityId2())){
            throw new BusinessException(ErrorCode.BAD_PARAMETER,"不支持同一个实体跟自己建立多对多关系");
        }
    }

    /**
     * 获取多对多关系对象
     * @param mtmId
     * @param force
     * @return
     */
    public MetaManyToManyPO getMetaManyToMany(Integer mtmId, boolean force){
        MetaManyToManyPO manyToManyPO = metaManyToManyDAO.findById(mtmId);
        if(force && manyToManyPO==null){
            throw new BusinessException(ErrorCode.RECORD_NOT_FIND,"多对多关系未找到");
        }
        return manyToManyPO;
    }

    /**
     * 查询列表
     * @param metaManyToManyQO
     * @return
     */
    public List<MetaManyToManyListVO> list(MetaManyToManyQO metaManyToManyQO) {
        List<MetaManyToManyListVO> list = metaManyToManyDAO.findListByQuery(metaManyToManyQO);
        return list;
    }

    /**
     * 查询多对多关联详情
     * @param mtmId
     * @return
     */
    public MetaManyToManyShowVO show(Integer mtmId) {
        MetaManyToManyPO metaManyToMany = this.getMetaManyToMany(mtmId,true);
        MetaManyToManyShowVO showVO = MetaManyToManyMapper.INSTANCE.toShowVO(metaManyToMany);
        return showVO;
    }

    /**
     * 删除多对多关联
     * @param mtmId
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public int delete(Integer... mtmId) {
        int count = 0;
        Integer projectId = null;
        for (Integer id : mtmId) {
            MetaManyToManyPO manyToMany = this.getMetaManyToMany(id,false);
            if(manyToMany==null){
                continue;
            }
            projectId = manyToMany.getProjectId();
            //校验操作人
            metaProjectService.checkOperatorByProjectId(projectId);
            count += metaManyToManyDAO.delete(id);
        }
        if(count>0) {
            metaProjectService.updateProjectVersion(projectId);
        }
        return count;
    }

    /**
     * 根据项目id查询多对多列表
     * @param projectId
     * @return
     */
    public List<MetaManyToManyPO> findByProjectId(Integer projectId){
        return metaManyToManyDAO.findByProjectId(projectId);
    }

}
