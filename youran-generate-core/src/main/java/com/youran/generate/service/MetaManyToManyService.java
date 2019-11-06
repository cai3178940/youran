package com.youran.generate.service;

import com.youran.common.constant.BoolConst;
import com.youran.common.constant.ErrorCode;
import com.youran.common.exception.BusinessException;
import com.youran.common.optimistic.OptimisticLock;
import com.youran.common.util.JsonUtil;
import com.youran.generate.dao.MetaEntityDAO;
import com.youran.generate.dao.MetaManyToManyDAO;
import com.youran.generate.pojo.dto.MetaManyToManyAddDTO;
import com.youran.generate.pojo.dto.MetaManyToManyUpdateDTO;
import com.youran.generate.pojo.dto.MetaMtmEntityFeatureDTO;
import com.youran.generate.pojo.dto.MetaMtmFeatureDTO;
import com.youran.generate.pojo.mapper.FeatureMapper;
import com.youran.generate.pojo.mapper.MetaManyToManyMapper;
import com.youran.generate.pojo.po.MetaManyToManyPO;
import com.youran.generate.pojo.qo.MetaManyToManyQO;
import com.youran.generate.pojo.vo.MetaManyToManyListVO;
import com.youran.generate.pojo.vo.MetaManyToManyShowVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * 多对多关联增删改查服务
 *
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
     *
     * @param addDTO
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public MetaManyToManyPO save(MetaManyToManyAddDTO addDTO) {
        Integer projectId = addDTO.getProjectId();
        //校验操作人
        metaProjectService.checkOperatorByProjectId(projectId);
        MetaManyToManyPO metaManyToMany = MetaManyToManyMapper.INSTANCE.fromAddDTO(addDTO);
        this.doSave(metaManyToMany);
        metaProjectService.updateProjectVersion(projectId);
        return metaManyToMany;
    }

    public void doSave(MetaManyToManyPO mtmPO) {
        //校验数据是否合法
        this.checkManyToMany(mtmPO);
        metaManyToManyDAO.save(mtmPO);
    }

    /**
     * 修改多对多关联
     *
     * @param updateDTO
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    @OptimisticLock
    public MetaManyToManyPO update(MetaManyToManyUpdateDTO updateDTO) {
        Integer mtmId = updateDTO.getMtmId();
        MetaManyToManyPO metaManyToMany = this.getMetaManyToMany(mtmId, true);
        Integer projectId = metaManyToMany.getProjectId();
        //校验操作人
        metaProjectService.checkOperatorByProjectId(projectId);
        MetaManyToManyMapper.INSTANCE.setPO(metaManyToMany, updateDTO);
        //校验数据是否合法
        this.checkManyToMany(metaManyToMany);
        metaManyToManyDAO.update(metaManyToMany);
        metaProjectService.updateProjectVersion(projectId);
        return metaManyToMany;
    }

    /**
     * 校验多对多是否合法
     *
     * @param mtm
     */
    private void checkManyToMany(MetaManyToManyPO mtm) {
        if (BoolConst.isFalse(mtm.getHoldRefer1())
            && BoolConst.isFalse(mtm.getHoldRefer2())) {
            throw new BusinessException(ErrorCode.BAD_PARAMETER, "至少要有一个实体持有对方引用");
        }
        if (!metaEntityDAO.exist(mtm.getEntityId1())) {
            throw new BusinessException(ErrorCode.BAD_PARAMETER, "entityId1参数有误");
        }
        if (!metaEntityDAO.exist(mtm.getEntityId2())) {
            throw new BusinessException(ErrorCode.BAD_PARAMETER, "entityId2参数有误");
        }
        if (Objects.equals(mtm.getEntityId1(), mtm.getEntityId2())) {
            throw new BusinessException(ErrorCode.BAD_PARAMETER, "不支持同一个实体跟自己建立多对多关系");
        }
        boolean exists = metaManyToManyDAO.findManyToManyExists(mtm.getEntityId1(), mtm.getEntityId2(),
            mtm.getMtmId());
        if (exists) {
            throw new BusinessException(ErrorCode.BAD_PARAMETER, "两实体已经存在多对多关系");
        }
        if (BoolConst.isTrue(mtm.getHoldRefer1())) {
            MetaMtmEntityFeatureDTO f1 = mtm.getF1();
            if (BoolConst.isFalse(f1.getAddRemove())
                && BoolConst.isFalse(f1.getSet())
                && BoolConst.isFalse(f1.getWithinEntity())) {
                throw new BusinessException(ErrorCode.BAD_PARAMETER, "至少勾选一项\"实体1功能\"");
            }
        }
        if (BoolConst.isTrue(mtm.getHoldRefer2())) {
            MetaMtmEntityFeatureDTO f2 = mtm.getF2();
            if (BoolConst.isFalse(f2.getAddRemove())
                && BoolConst.isFalse(f2.getSet())
                && BoolConst.isFalse(f2.getWithinEntity())) {
                throw new BusinessException(ErrorCode.BAD_PARAMETER, "至少勾选一项\"实体2功能\"");
            }
        }
    }

    /**
     * 获取多对多关系对象
     *
     * @param mtmId
     * @param force
     * @return
     */
    public MetaManyToManyPO getMetaManyToMany(Integer mtmId, boolean force) {
        MetaManyToManyPO mtm = metaManyToManyDAO.findById(mtmId);
        if (force && mtm == null) {
            throw new BusinessException(ErrorCode.RECORD_NOT_FIND, "多对多关系未找到");
        }
        this.parseMtmFeature(mtm);
        return mtm;
    }

    /**
     * 解析特性对象
     *
     * @param mtm
     */
    public void parseMtmFeature(MetaManyToManyPO mtm) {
        // 兼容旧数据，如果feature字段为空，则设置默认值
        if (StringUtils.isBlank(mtm.getFeature())) {
            mtm.setFeature(JsonUtil.toJSONString(new MetaMtmFeatureDTO()));
        }
        // 设置feature对象
        MetaMtmFeatureDTO feature = FeatureMapper.asMtmFeatureDTO(mtm.getFeature());
        mtm.setF1(feature.getF1());
        mtm.setF2(feature.getF2());
    }

    /**
     * 查询列表
     *
     * @param metaManyToManyQO
     * @return
     */
    public List<MetaManyToManyListVO> list(MetaManyToManyQO metaManyToManyQO) {
        List<MetaManyToManyListVO> list = metaManyToManyDAO.findListByQuery(metaManyToManyQO);
        return list;
    }

    /**
     * 查询多对多关联详情
     *
     * @param mtmId
     * @return
     */
    public MetaManyToManyShowVO show(Integer mtmId) {
        MetaManyToManyPO metaManyToMany = this.getMetaManyToMany(mtmId, true);
        MetaManyToManyShowVO showVO = MetaManyToManyMapper.INSTANCE.toShowVO(metaManyToMany);
        return showVO;
    }

    /**
     * 删除多对多关联
     *
     * @param mtmId
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public int delete(Integer... mtmId) {
        int count = 0;
        Integer projectId = null;
        for (Integer id : mtmId) {
            MetaManyToManyPO manyToMany = this.getMetaManyToMany(id, false);
            if (manyToMany == null) {
                continue;
            }
            projectId = manyToMany.getProjectId();
            //校验操作人
            metaProjectService.checkOperatorByProjectId(projectId);
            count += metaManyToManyDAO.delete(id);
        }
        if (count > 0) {
            metaProjectService.updateProjectVersion(projectId);
        }
        return count;
    }

    /**
     * 根据项目id查询多对多列表
     *
     * @param projectId
     * @param parseFeature
     * @return
     */
    public List<MetaManyToManyPO> findByProjectId(Integer projectId, boolean parseFeature) {
        List<MetaManyToManyPO> list = metaManyToManyDAO.findByProjectId(projectId);
        if (parseFeature) {
            list.stream().forEach(this::parseMtmFeature);
        }
        return list;
    }


}
