package com.cbb.sample.web.rest;

import com.cbb.sample.constant.SampleConst;
import com.cbb.sample.pojo.dto.UserAddDTO;
import com.cbb.sample.pojo.dto.UserQueryDTO;
import com.cbb.sample.pojo.dto.UserUpdateDTO;
import com.cbb.sample.pojo.po.UserPO;
import com.cbb.sample.pojo.vo.UserListVO;
import com.cbb.sample.pojo.vo.UserShowVO;
import com.cbb.sample.service.UserService;
import com.cbb.sample.web.api.UserAPI;
import com.youran.common.pojo.vo.PageVO;
import com.youran.common.pojo.vo.ReplyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Title:
 * Description:
 * Author: cbb
 * Create Time:2017/9/13 14:59
 */
@RestController
@RequestMapping(SampleConst.SAMPLE_ROOT_PATH+"/user")
public class UserController implements UserAPI{

    @Autowired
    private UserService userService;

    @Override
    @PostMapping(value = "/save")
    public ReplyVO<Integer> save(@Valid @RequestBody UserAddDTO userAddDTO) {
        UserPO user = userService.save(userAddDTO);
        ReplyVO<Integer> replyVO = ReplyVO.success();
        replyVO.setData(user.getId());
        return replyVO;
    }

    @Override
    @PutMapping(value = "/update")
    public ReplyVO<Void> update(@Valid @RequestBody UserUpdateDTO userUpdateDTO) {
        userService.update(userUpdateDTO);
        ReplyVO<Void> replyVO = ReplyVO.success();
        return replyVO;
    }


    @Override
    @PostMapping(value = "/list")
    public ReplyVO<PageVO<UserListVO>> list(@Valid @RequestBody UserQueryDTO userQueryDTO) {
        PageVO<UserListVO> page = userService.list(userQueryDTO);
        ReplyVO<PageVO<UserListVO>> replyVO = ReplyVO.success();
        replyVO.setData(page);
        return replyVO;
    }

    @Override
    @GetMapping(value = "/{id}")
    public ReplyVO<UserShowVO> show(@PathVariable Integer id) {
        UserShowVO userShowVO = userService.show(id);
        ReplyVO<UserShowVO> replyVO = ReplyVO.success();
        replyVO.setData(userShowVO);
        return replyVO;
    }

    @Override
    @DeleteMapping(value = "/{id}")
    public ReplyVO<Integer> delete(@PathVariable Integer id) {
        int count = userService.delete(id);
        ReplyVO<Integer> replyVO = ReplyVO.success();
        replyVO.setData(count);
        return replyVO;
    }

}
