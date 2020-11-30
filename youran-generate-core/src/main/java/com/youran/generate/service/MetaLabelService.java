package com.youran.generate.service;

import com.google.common.collect.Lists;
import com.youran.generate.pojo.dto.MetaLabelDTO;
import com.youran.generate.pojo.dto.MetaLabelPackDTO;
import com.youran.generate.pojo.po.CodeTemplatePO;
import com.youran.generate.pojo.po.MetaProjectPO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 标签元数据服务
 *
 * @author: cbb
 * @date: 2020-09-13
 */
@Service
public class MetaLabelService {

    @Autowired
    private MetaProjectService metaProjectService;
    @Autowired
    private CodeTemplateService codeTemplateService;

    /**
     * 根据项目id获取标签元数据列表
     *
     * @param projectId
     * @param labelType
     * @return
     */
    public List<MetaLabelDTO> getMetaLabelByProjectId(Integer projectId, String labelType) {
        MetaProjectPO project = metaProjectService.getProject(projectId, true, true);
        return this.getMetaLabelByTemplateId(Arrays.asList(
            project.getTemplateId(),
            project.getTemplateId2(),
            project.getTemplateId3()
        ), labelType);
    }

    /**
     * 根据模板id获取标签元数据列表
     *
     * @param templateIds
     * @param labelType
     * @return
     */
    public List<MetaLabelDTO> getMetaLabelByTemplateId(List<Integer> templateIds, String labelType) {
        List<MetaLabelPackDTO> metaLabelPacks = this.findMetaLabelPacks(templateIds);
        if (CollectionUtils.isEmpty(metaLabelPacks)) {
            return Collections.emptyList();
        }
        Map<String, MetaLabelDTO> map = new TreeMap<>();
        for (MetaLabelPackDTO metaLabelPack : metaLabelPacks) {
            List<MetaLabelDTO> metaLabelDTOS = metaLabelPack.fetchByType(labelType);
            if (CollectionUtils.isNotEmpty(metaLabelDTOS)) {
                for (MetaLabelDTO metaLabelDTO : metaLabelDTOS) {
                    map.put(metaLabelDTO.getKey(), metaLabelDTO);
                }
            }
        }
        return Lists.newArrayList(map.values());
    }

    /**
     * 获取模板下的标签元数据包
     *
     * @param templateIds
     * @return
     */
    private List<MetaLabelPackDTO> findMetaLabelPacks(List<Integer> templateIds) {
        return templateIds.stream()
            .filter(Objects::nonNull)
            .map(this::getMetaLabelPackByTemplate)
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }

    /**
     * 从模板中获取标签元数据包
     *
     * @param templateId
     * @return
     */
    private MetaLabelPackDTO getMetaLabelPackByTemplate(Integer templateId) {
        CodeTemplatePO codeTemplate = codeTemplateService.getCodeTemplate(templateId, false);
        if (codeTemplate != null) {
            MetaLabelPackDTO metaLabelPack = codeTemplate.fetchMetaLabelPack();
            if (metaLabelPack != null) {
                return metaLabelPack;
            }
        }
        return null;
    }

}
