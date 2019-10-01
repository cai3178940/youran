package com.youran.generate.pojo.dto;

import com.youran.common.pojo.dto.AbstractDTO;

/**
 * <p>Title: 多对多特性</p>
 * <p>Description: </p>
 * @author cbb
 * @date 2019/9/30
 */
public class MetaMtmFeatureDTO extends AbstractDTO {

    private MetaMtmEntityFeatureDTO f1 = new MetaMtmEntityFeatureDTO();
    private MetaMtmEntityFeatureDTO f2 = new MetaMtmEntityFeatureDTO();

    public MetaMtmEntityFeatureDTO getF1() {
        return f1;
    }

    public void setF1(MetaMtmEntityFeatureDTO f1) {
        this.f1 = f1;
    }

    public MetaMtmEntityFeatureDTO getF2() {
        return f2;
    }

    public void setF2(MetaMtmEntityFeatureDTO f2) {
        this.f2 = f2;
    }
}
