package com.youran.generate.pojo.vo;

import com.youran.common.pojo.vo.AbstractVO;

import java.util.List;

/**
 * <p>Title: 实体关系图VO</p>
 * <p>Description: </p>
 *
 * @author cbb
 * @date 2018/7/17
 */
public class ErDiagramVO extends AbstractVO {

    private List<EntityDiagramVO> nodeData;

    private List<RelationDiagramVO> linkData;


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
}
