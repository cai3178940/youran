package com.youran.generate.web.rest;

import com.youran.common.pojo.vo.ReplyVO;
import com.youran.generate.constant.GenerateConst;
import com.youran.generate.pojo.dto.MetaConstDetailAddDTO;
import com.youran.generate.pojo.dto.MetaConstDetailUpdateDTO;
import com.youran.generate.pojo.mapper.MetaConstDetailMapper;
import com.youran.generate.pojo.po.MetaConstDetailPO;
import com.youran.generate.pojo.qo.MetaConstDetailQO;
import com.youran.generate.pojo.vo.MetaConstDetailListVO;
import com.youran.generate.pojo.vo.MetaConstDetailShowVO;
import com.youran.generate.service.MetaConstDetailService;
import com.youran.generate.web.api.MetaConstDetailAPI;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>Title:【常量值】控制器</p>
 * <p>Description:</p>
 * @author: cbb
 * @date: 2017/5/12
 */
@RestController
@RequestMapping(GenerateConst.API_PATH +"/meta_const_detail")
public class MetaConstDetailController implements MetaConstDetailAPI {

    @Autowired
    private MetaConstDetailService metaConstDetailService;

    @Override
    @PostMapping(value = "/save")
    public ReplyVO<MetaConstDetailShowVO> save(@Valid @RequestBody MetaConstDetailAddDTO metaConstDetailAddDTO) {
        MetaConstDetailPO metaConstDetailPO = metaConstDetailService.save(metaConstDetailAddDTO);
        return ReplyVO.success().data(MetaConstDetailMapper.INSTANCE.toShowVO(metaConstDetailPO));
    }

    @Override
    @PutMapping(value = "/update")
    public ReplyVO<MetaConstDetailShowVO> update(@Valid @RequestBody MetaConstDetailUpdateDTO metaConstDetailUpdateDTO) {
        MetaConstDetailPO metaConstDetailPO = metaConstDetailService.update(metaConstDetailUpdateDTO);
        return ReplyVO.success().data(MetaConstDetailMapper.INSTANCE.toShowVO(metaConstDetailPO));
    }

    @Override
    @GetMapping(value = "/list")
    public ReplyVO<List<MetaConstDetailListVO>> list(@Valid MetaConstDetailQO metaConstDetailQO) {
        List<MetaConstDetailListVO> list = metaConstDetailService.list(metaConstDetailQO);
        ReplyVO<List<MetaConstDetailListVO>> result = ReplyVO.success();
        result.setData(list);
        return result;
    }

    @Override
    @GetMapping(value = "/{constDetailId}")
    public ReplyVO<MetaConstDetailShowVO> show(@PathVariable Integer constDetailId) {
        MetaConstDetailShowVO metaConstDetailShowVO = metaConstDetailService.show(constDetailId);
        ReplyVO<MetaConstDetailShowVO> result = ReplyVO.success();
        result.setData(metaConstDetailShowVO);
        return result;
    }

    @Override
    @DeleteMapping(value = "/{constDetailId}")
    public ReplyVO<Integer> delete(@PathVariable Integer constDetailId) {
        int count = metaConstDetailService.delete(constDetailId);
        ReplyVO<Integer> result = ReplyVO.success();
        result.setData(count);
        return result;
    }

    @Override
    @PutMapping(value = "deleteBatch")
    public ReplyVO<Integer> deleteBatch(@RequestBody Integer[] constDetailId) {
        if(ArrayUtils.isEmpty(constDetailId)){
            return ReplyVO.fail("参数为空");
        }
        int count = metaConstDetailService.delete(constDetailId);
        ReplyVO<Integer> result = ReplyVO.success();
        result.setData(count);
        return result;
    }
}
