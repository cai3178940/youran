package com.youran.generate.web.rest;

import com.youran.common.pojo.vo.ReplyVO;
import com.youran.generate.constant.GenerateConst;
import com.youran.generate.exception.GenerateException;
import com.youran.generate.pojo.dto.ReverseEngineeringDTO;
import com.youran.generate.service.ReverseEngineeringService;
import com.youran.generate.web.api.ReverseEngineeringAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


/**
 * Title: 【反向工程】控制器
 * Description:
 * Project: bbs
 * Author: cbb
 * Create Time: 2018-03-17 16:47
 */
@RestController
@RequestMapping(GenerateConst.GENERATE_ROOT_PATH +"/reverse_engineering")
public class ReverseEngineeringController implements ReverseEngineeringAPI {

    @Autowired
    private ReverseEngineeringService reverseEngineeringService;


    @Override
    @PostMapping(value = "/check")
    public ReplyVO<Void> check(@Valid @RequestBody ReverseEngineeringDTO dto) {
        try {
            reverseEngineeringService.parse(dto);
        } catch (GenerateException e) {
            return ReplyVO.fail(e.getMessage());
        }
        return ReplyVO.success();
    }

    @Override
    @PostMapping(value = "/execute")
    public ReplyVO<Void> execute(@Valid @RequestBody ReverseEngineeringDTO dto) {
        try {
            reverseEngineeringService.execute(dto);
        } catch (GenerateException e) {
            return ReplyVO.fail(e.getMessage());
        }
        return ReplyVO.success();
    }
}


