package com.youran.generate.web.rest;

import com.youran.common.constant.ErrorCode;
import com.youran.common.exception.BusinessException;
import com.youran.generate.constant.WebConst;
import com.youran.generate.pojo.dto.MetaEntityAddDTO;
import com.youran.generate.pojo.dto.MetaEntityUpdateDTO;
import com.youran.generate.pojo.mapper.MetaEntityMapper;
import com.youran.generate.pojo.po.MetaEntityPO;
import com.youran.generate.pojo.qo.MetaEntityQO;
import com.youran.generate.pojo.vo.MetaEntityListPairVO;
import com.youran.generate.pojo.vo.MetaEntityListVO;
import com.youran.generate.pojo.vo.MetaEntityShowVO;
import com.youran.generate.pojo.vo.MetaMtmEntityListVO;
import com.youran.generate.service.MetaEntityService;
import com.youran.generate.web.AbstractController;
import com.youran.generate.web.api.MetaEntityAPI;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Map;

/**
 * 【实体】控制器
 * <p> 实体增删改查
 *
 * @author: cbb
 * @date: 2017/5/12
 */
@RestController
@RequestMapping(WebConst.API_PATH + "/meta_entity")
public class MetaEntityController extends AbstractController implements MetaEntityAPI {

    @Autowired
    private MetaEntityService metaEntityService;

    @Override
    @PostMapping(value = "/save")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<MetaEntityShowVO> save(@Valid @RequestBody MetaEntityAddDTO metaEntityAddDTO) throws Exception {
        MetaEntityPO metaEntityPO = metaEntityService.save(metaEntityAddDTO);
        return ResponseEntity.created(new URI(apiPath + "/meta_entity/" + metaEntityPO.getEntityId()))
            .body(MetaEntityMapper.INSTANCE.toShowVO(metaEntityPO));
    }

    @Override
    @PutMapping(value = "/update")
    public ResponseEntity<MetaEntityShowVO> update(@Valid @RequestBody MetaEntityUpdateDTO metaEntityUpdateDTO) {
        MetaEntityPO metaEntityPO = metaEntityService.update(metaEntityUpdateDTO);
        return ResponseEntity.ok(MetaEntityMapper.INSTANCE.toShowVO(metaEntityPO));
    }

    @Override
    @PutMapping(value = "/{entityId}/feature")
    public ResponseEntity<MetaEntityShowVO> updateFeature(@PathVariable Integer entityId,
                                              @Valid @RequestBody Map<String,Object> attributes) {
        MetaEntityPO metaEntityPO = metaEntityService.updateFeatureAttr(entityId, attributes);
        return ResponseEntity.ok(MetaEntityMapper.INSTANCE.toShowVO(metaEntityPO));
    }

    @Override
    @GetMapping(value = "/list")
    public ResponseEntity<List<MetaEntityListVO>> list(@Valid MetaEntityQO metaEntityQO) {
        List<MetaEntityListVO> list = metaEntityService.list(metaEntityQO);
        return ResponseEntity.ok(list);
    }

    @Override
    @GetMapping(value = "/{entityId}")
    public ResponseEntity<MetaEntityShowVO> show(@PathVariable Integer entityId) {
        MetaEntityShowVO metaEntityShowVO = metaEntityService.show(entityId);
        return ResponseEntity.ok(metaEntityShowVO);
    }

    @Override
    @DeleteMapping(value = "/{entityId}")
    public ResponseEntity<Integer> delete(@PathVariable Integer entityId) {
        int count = metaEntityService.delete(entityId);
        return ResponseEntity.ok(count);
    }

    @Override
    @PutMapping(value = "/delete_batch")
    public ResponseEntity<Integer> deleteBatch(@RequestBody Integer[] entityId) {
        if (ArrayUtils.isEmpty(entityId)) {
            throw new BusinessException(ErrorCode.PARAM_IS_NULL);
        }
        int count = metaEntityService.delete(entityId);
        return ResponseEntity.ok(count);
    }

    @Override
    @GetMapping(value = "/{entityId}/mtm_entity_list_pair")
    public ResponseEntity<MetaEntityListPairVO> mtmEntityListPair(@PathVariable Integer entityId) {
        List<MetaMtmEntityListVO> holds = metaEntityService.mtmEntityList(entityId, true);
        List<MetaMtmEntityListVO> unholds = metaEntityService.mtmEntityList(entityId, false);
        return ResponseEntity.ok(new MetaEntityListPairVO(holds, unholds));
    }

    @Override
    @GetMapping(value = "/{entityId}/default_fk_field_name_for_sql")
    public ResponseEntity<String> getDefaultFkFieldNameForSql(@PathVariable Integer entityId) {
        String fkFieldName = metaEntityService.getDefaultFkFieldName(entityId, true);
        return ResponseEntity.ok(fkFieldName);
    }

    @Override
    @GetMapping(value = "/labels")
    public ResponseEntity<List<String>> findLabels() {
        List<String> labels = metaEntityService.findLabels();
        return ResponseEntity.ok(labels);
    }
}
