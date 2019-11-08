package com.youran.generate.web.rest;

import com.youran.generate.constant.WebConst;
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
 * 【反向工程】控制器
 * <p> 通过SQL生成实体及字段
 *
 * @author: cbb
 * @date: 2018/03/17
 */
@RestController
@RequestMapping(WebConst.API_PATH + "/reverse_engineering")
public class ReverseEngineeringController implements ReverseEngineeringAPI {

    @Autowired
    private ReverseEngineeringService reverseEngineeringService;


    @Override
    @PostMapping(value = "/check")
    public void check(@Valid @RequestBody ReverseEngineeringDTO dto) {
        reverseEngineeringService.parse(dto);
    }

    @Override
    @PostMapping(value = "/execute")
    public void execute(@Valid @RequestBody ReverseEngineeringDTO dto) {
        reverseEngineeringService.execute(dto);
    }
}


