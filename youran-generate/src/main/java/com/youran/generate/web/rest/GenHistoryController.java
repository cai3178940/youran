package com.youran.generate.web.rest;

import com.youran.common.pojo.vo.PageVO;
import com.youran.common.pojo.vo.ReplyVO;
import com.youran.generate.pojo.qo.GenHistoryQO;
import com.youran.generate.pojo.vo.GenHistoryListVO;
import com.youran.generate.service.GenHistoryService;
import com.youran.generate.web.api.GenHistoryAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


/**
 * Title: 【生成历史】控制器
 * Description:
 * Project: bbs
 * Author: cbb
 * Create Time: 2018-03-17 16:47
 */
@RestController
@RequestMapping("/gen_history")
public class GenHistoryController implements GenHistoryAPI {

    @Autowired
    private GenHistoryService genHistoryService;


    @Override
    @GetMapping(value = "/list")
    public ReplyVO<PageVO<GenHistoryListVO>> list(@Valid GenHistoryQO genHistoryQO) {
        PageVO<GenHistoryListVO> page = genHistoryService.list(genHistoryQO);
        return ReplyVO.success().data(page);
    }



}


