package com.youran.generate.web.api;

import com.youran.common.pojo.vo.PageVO;
import com.youran.generate.pojo.qo.GenHistoryQO;
import com.youran.generate.pojo.vo.GenHistoryListVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;


/**
 * 【生成历史】API
 * <p>swagger接口文档
 *
 * @author: cbb
 * @date: 2018/03/17
 */
@Api(tags = "GenHistory")
public interface GenHistoryAPI {

    /**
     * 分页查询【生成历史】
     *
     * @param genHistoryQO
     * @return
     */
    @ApiOperation(value = "分页查询【生成历史】")
    ResponseEntity<PageVO<GenHistoryListVO>> list(GenHistoryQO genHistoryQO);

}

