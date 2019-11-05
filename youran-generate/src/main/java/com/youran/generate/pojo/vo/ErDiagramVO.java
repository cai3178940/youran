package com.youran.generate.pojo.vo;

import com.youran.common.pojo.vo.AbstractVO;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * 实体关系图VO
 *
 * @author cbb
 * @date 2018/7/17
 */
public class ErDiagramVO extends AbstractVO {

    private List<EntityDiagramVO> nodeData;

    private List<RelationDiagramVO> linkData;

    public ErDiagramVO() {
    }

    public ErDiagramVO(List<EntityDiagramVO> nodeData, List<RelationDiagramVO> linkData) {
        this.nodeData = nodeData;
        this.linkData = linkData;
    }

    public List<EntityDiagramVO> getNodeData() {
        return nodeData;
    }

    public void setNodeData(List<EntityDiagramVO> nodeData) {
        this.nodeData = nodeData;
    }

    public List<RelationDiagramVO> getLinkData() {
        return linkData;
    }

    public void setLinkData(List<RelationDiagramVO> linkData) {
        this.linkData = linkData;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("nodeData", nodeData)
            .append("linkData", linkData)
            .toString();
    }
}
