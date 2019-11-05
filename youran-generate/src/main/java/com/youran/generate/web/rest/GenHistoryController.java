package com.youran.generate.web.rest;

import com.youran.common.pojo.vo.PageVO;
import com.youran.generate.constant.WebConst;
import com.youran.generate.pojo.qo.GenHistoryQO;
import com.youran.generate.pojo.vo.GenHistoryListVO;
import com.youran.generate.service.GenHistoryService;
import com.youran.generate.web.AbstractController;
import com.youran.generate.web.api.GenHistoryAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


/**
 * 【生成历史】控制器
 * <p> Git提交历史相关的服务接口
 *
 * @author: cbb
 * @date: 2018/03/17
 */
@RestController
@RequestMapping(WebConst.API_PATH + "/gen_history")
public class GenHistoryController extends AbstractController implements GenHistoryAPI {

    @Autowired
    private GenHistoryService genHistoryService;


    @Override
    @GetMapping(value = "/list")
    public ResponseEntity<PageVO<GenHistoryListVO>> list(@Valid GenHistoryQO genHistoryQO) {
        PageVO<GenHistoryListVO> page = genHistoryService.list(genHistoryQO);
        return ResponseEntity.ok(page);
    }


}


