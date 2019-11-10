package com.youran.generate.web.rest;

import com.youran.generate.constant.MetaSpecialField;
import com.youran.generate.constant.WebConst;
import com.youran.generate.pojo.po.MetaEntityPO;
import com.youran.generate.pojo.po.MetaFieldPO;
import com.youran.generate.pojo.po.MetaManyToManyPO;
import com.youran.generate.pojo.vo.EntityDiagramVO;
import com.youran.generate.pojo.vo.ErDiagramVO;
import com.youran.generate.pojo.vo.FieldDiagramVO;
import com.youran.generate.pojo.vo.RelationDiagramVO;
import com.youran.generate.service.MetaEntityService;
import com.youran.generate.service.MetaManyToManyService;
import com.youran.generate.service.MetaQueryAssembleService;
import com.youran.generate.web.AbstractController;
import com.youran.generate.web.api.ErDiagramAPI;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 【ER图】控制器
 *
 * @author cbb
 * @date 2018/7/17
 */
@RestController
@RequestMapping(WebConst.API_PATH + "/er_diagram")
public class ErDiagramController extends AbstractController implements ErDiagramAPI {

    @Autowired
    private MetaQueryAssembleService metaQueryAssembleService;
    @Autowired
    private MetaEntityService metaEntityService;
    @Autowired
    private MetaManyToManyService metaManyToManyService;

    @Override
    @GetMapping(value = "/show")
    public ResponseEntity<ErDiagramVO> show(@RequestParam Integer projectId,
                                            @RequestParam(required = false) List<Integer> entityIds) {

        // 如果没有传入实体id列表，则获取项目下所有实体id
        if (CollectionUtils.isEmpty(entityIds)) {
            entityIds = metaEntityService.findIdsByProject(projectId);
        }
        // 获取组装完的实体列表
        List<MetaEntityPO> metaEntities = entityIds
            .stream()
            .map(metaQueryAssembleService::getAssembledEntity).collect(Collectors.toList());
        // 组装外键实体和外键字段
        metaQueryAssembleService.assembleForeign(metaEntities, false);
        // 查询多对多列表
        List<MetaManyToManyPO> manyToManies = metaManyToManyService.findByProjectId(projectId, false);
        // 组装多对多对象引用
        manyToManies = metaQueryAssembleService.assembleManyToManyWithEntities(metaEntities, manyToManies, false);


        List<EntityDiagramVO> nodeData = new ArrayList<>();
        List<RelationDiagramVO> linkData = new ArrayList<>();
        for (MetaEntityPO metaEntity : metaEntities) {
            EntityDiagramVO entityDiagramVO = new EntityDiagramVO();
            entityDiagramVO.setKey(metaEntity.getTableName());
            for (MetaFieldPO field : metaEntity.getFields().values()) {
                String type = "";
                if (field.getPrimaryKey()) {
                    type = FieldDiagramVO.PRIMARY_KEY;
                } else if (field.getForeignKey()) {
                    type = FieldDiagramVO.FOREIGN_KEY;
                } else if (MetaSpecialField.isVersion(field.getSpecialField())) {
                    type = FieldDiagramVO.VERSION;
                } else if (MetaSpecialField.isDeleted(field.getSpecialField())) {
                    type = FieldDiagramVO.DELETE;
                }
                entityDiagramVO.addField(field.getFieldName(), type, field.getFieldDesc());
            }
            nodeData.add(entityDiagramVO);

            Set<MetaEntityPO> foreignEntities = metaEntity.getForeignEntities();
            if (CollectionUtils.isNotEmpty(foreignEntities)) {
                for (MetaEntityPO foreignEntity : foreignEntities) {
                    RelationDiagramVO realtionVO = new RelationDiagramVO();
                    realtionVO.setFrom(foreignEntity.getTableName());
                    realtionVO.setTo(metaEntity.getTableName());
                    realtionVO.setText(RelationDiagramVO.MANY);
                    // TODO 这里也有可能是一对一
                    realtionVO.setToText(RelationDiagramVO.ONE);
                    linkData.add(realtionVO);
                }
            }
        }

        for (MetaManyToManyPO manyToManyPO : manyToManies) {
            MetaEntityPO refer1 = manyToManyPO.getRefer1();
            MetaEntityPO refer2 = manyToManyPO.getRefer2();
            if (refer1 == null || refer2 == null) {
                continue;
            }
            RelationDiagramVO realtionVO = new RelationDiagramVO();
            realtionVO.setFrom(refer1.getTableName());
            realtionVO.setTo(refer2.getTableName());
            realtionVO.setText(RelationDiagramVO.MANY);
            realtionVO.setToText(RelationDiagramVO.MANY);
            linkData.add(realtionVO);
        }

        ErDiagramVO vo = new ErDiagramVO(nodeData, linkData);

        return ResponseEntity.ok(vo);
    }
}
