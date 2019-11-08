package com.youran.generate.service;

import com.youran.common.constant.ErrorCode;
import com.youran.common.exception.BusinessException;
import com.youran.common.optimistic.OptimisticLock;
import com.youran.common.pojo.vo.PageVO;
import com.youran.generate.dao.UserSettingDAO;
import com.youran.generate.pojo.dto.UserSettingAddDTO;
import com.youran.generate.pojo.dto.UserSettingUpdateDTO;
import com.youran.generate.pojo.mapper.UserSettingMapper;
import com.youran.generate.pojo.po.UserSettingPO;
import com.youran.generate.pojo.qo.UserSettingQO;
import com.youran.generate.pojo.vo.UserSettingListVO;
import com.youran.generate.pojo.vo.UserSettingShowVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 【用户配置】删改查服务
 *
 * @author cbb
 * @date 2019/11/08
 */
@Service
public class UserSettingService {

    @Autowired
    private UserSettingDAO userSettingDAO;

    /**
     * 校验数据唯一性
     * @param userSetting
     * @param isUpdate 是否更新校验
     */
    private void checkUnique(UserSettingPO userSetting, boolean isUpdate){
        if(userSettingDAO.notUnique(userSetting.getUsername(), isUpdate?userSetting.getId():null)){
            throw new BusinessException(ErrorCode.DUPLICATE_KEY);
        }
    }


    /**
     * 新增【用户配置】
     * @param userSettingDTO
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public UserSettingPO save(UserSettingAddDTO userSettingDTO) {
        UserSettingPO userSetting = UserSettingMapper.INSTANCE.fromAddDTO(userSettingDTO);
        // 唯一性校验
        this.checkUnique(userSetting,false);
        userSettingDAO.save(userSetting);
        return userSetting;
    }

    /**
     * 修改【用户配置】
     * @param userSettingUpdateDTO
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    @OptimisticLock
    public UserSettingPO update(UserSettingUpdateDTO userSettingUpdateDTO) {
        Integer id = userSettingUpdateDTO.getId();
        UserSettingPO userSetting = this.getUserSetting(id, true);
        UserSettingMapper.INSTANCE.setUpdateDTO(userSetting,userSettingUpdateDTO);
        // 唯一性校验
        this.checkUnique(userSetting,true);
        userSettingDAO.update(userSetting);
        return userSetting;
    }
    /**
     * 查询分页列表
     * @param userSettingQO
     * @return
     */
    public PageVO<UserSettingListVO> list(UserSettingQO userSettingQO) {
        PageVO<UserSettingListVO> page = userSettingDAO.findByPage(userSettingQO);
        return page;
    }

    /**
     * 根据主键获取【用户配置】
     * @param id 主键
     * @param force 是否强制获取
     * @return
     */
    public UserSettingPO getUserSetting(Integer id, boolean force){
        UserSettingPO userSetting = userSettingDAO.findById(id);
        if (force && userSetting == null) {
            throw new BusinessException(ErrorCode.RECORD_NOT_FIND);
        }
        return userSetting;
    }

    /**
     * 根据主键获取【用户配置】
     * @param username 用户名
     * @param force 是否强制获取
     * @return
     */
    public UserSettingPO getUserSettingByUsername(String username, boolean force){
        UserSettingPO userSetting = userSettingDAO.findByUsername(username);
        if (force && userSetting == null) {
            throw new BusinessException(ErrorCode.RECORD_NOT_FIND);
        }
        return userSetting;
    }


    /**
     * 查询【用户配置】详情
     * @param id
     * @return
     */
    public UserSettingShowVO show(Integer id) {
        UserSettingPO userSetting = this.getUserSetting(id, true);
        UserSettingShowVO showVO = UserSettingMapper.INSTANCE.toShowVO(userSetting);
        return showVO;
    }

    /**
     * 删除【用户配置】
     * @param ids
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public int delete(Integer... ids) {
        int count = 0;
        for (Integer id : ids) {
            count += userSettingDAO.delete(id);
        }
        return count;
    }


}


