package com.cbb.sample.service;

import com.cbb.sample.dao.UserDAO;
import com.cbb.sample.exception.SampleException;
import com.cbb.sample.pojo.dto.UserAddDTO;
import com.cbb.sample.pojo.dto.UserQueryDTO;
import com.cbb.sample.pojo.dto.UserUpdateDTO;
import com.cbb.sample.pojo.mapper.UserMapper;
import com.cbb.sample.pojo.po.UserPO;
import com.cbb.sample.pojo.vo.UserListVO;
import com.cbb.sample.pojo.vo.UserShowVO;
import com.youran.common.optimistic.OptimisticLock;
import com.youran.common.pojo.vo.PageVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Title:
 * Description:
 * Author: cbb
 * Create Time:2017/9/13 15:00
 */
@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    /**
     * 新增【用户】
     * @param userDTO
     * @return
     */
    @Transactional
    public UserPO save(UserAddDTO userDTO) {
        UserPO userPO = UserMapper.INSTANCE.fromAddDTO(userDTO);
        userDAO.save(userPO);
        return userPO;
    }

    /**
     * 修改【用户】
     * @param userUpdateDTO
     * @return
     */
    @Transactional
    @OptimisticLock
    public void update(UserUpdateDTO userUpdateDTO) {
        Integer id = userUpdateDTO.getId();
        UserPO userPO = userDAO.findById(id);
        if(userPO==null){
            throw new SampleException("id有误");
        }
        UserMapper.INSTANCE.setPO(userPO,userUpdateDTO);
        userDAO.update(userPO);
    }

    /**
     * 查询分页列表
     * @param queryDTO
     * @return
     */
    public PageVO<UserListVO> list(UserQueryDTO queryDTO) {
        //处理模糊查询字段
        if(StringUtils.isNotBlank(queryDTO.getUserName())){
            queryDTO.setUserName("%"+queryDTO.getUserName()+"%");
        }
        PageVO<UserListVO> pageVO = userDAO.findByPage(queryDTO);
        return pageVO;
    }

    /**
     * 查询【用户】详情
     * @param id
     * @return
     */
    public UserShowVO show(Integer id) {
        UserPO userPO = userDAO.findById(id);
        if(userPO==null){
            throw new SampleException("未查询到记录");
        }
        UserShowVO showVO = UserMapper.INSTANCE.toShowVO(userPO);
        return showVO;
    }

    /**
     * 删除【用户】
     * @param id
     * @return
     */
    @Transactional
    public int delete(Integer id) {
        return userDAO.delete(id);
    }
}
