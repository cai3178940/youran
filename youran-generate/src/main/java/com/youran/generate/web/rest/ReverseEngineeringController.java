package com.youran.generate.web.rest;

import com.youran.common.pojo.vo.ReplyVO;
import com.youran.generate.constant.GenerateConst;
import com.youran.common.exception.BusinessException;
import com.youran.generate.pojo.dto.ReverseEngineeringDTO;
import com.youran.generate.service.MetaProjectService;
import com.youran.generate.service.ReverseEngineeringService;
import com.youran.generate.web.api.ReverseEngineeringAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


/**
 * <p>Title:【反向工程】控制器</p>
 * <p>Description:</p>
 * Project: bbs
 * @author: cbb
 * @date: 2018-03-17
 */
@RestController
@RequestMapping(GenerateConst.API_PATH +"/reverse_engineering")
public class ReverseEngineeringController implements ReverseEngineeringAPI {

    @Autowired
    private ReverseEngineeringService reverseEngineeringService;
    @Autowired
    private MetaProjectService metaProjectService;


    @Override
    @PostMapping(value = "/check")
    public ReplyVO<Void> check(@Valid @RequestBody ReverseEngineeringDTO dto) {
        try {
            reverseEngineeringService.parse(dto);
        } catch (BusinessException e) {
            return ReplyVO.fail(e.getMessage());
        }
        return ReplyVO.success();
    }

    @Override
    @PostMapping(value = "/execute")
    public ReplyVO<Void> execute(@Valid @RequestBody ReverseEngineeringDTO dto) {
        try {
            //校验操作人
            metaProjectService.checkOperatorByProjectId(dto.getProjectId());
            reverseEngineeringService.execute(dto);
        } catch (BusinessException e) {
            return ReplyVO.fail(e.getMessage());
        }
        return ReplyVO.success();
    }
}


