package com.youran.generate.pojo.vo;

import com.youran.generate.pojo.dto.MetaEntityFeatureDTO;

/**
 * 实体详情展示对象
 *
 * @author: cbb
 * @date: 2017/5/12
 */
public class MetaEntityShowVO extends MetaEntityListVO {

    private MetaEntityFeatureDTO feature;

    public MetaEntityFeatureDTO getFeature() {
        return feature;
    }

    public void setFeature(MetaEntityFeatureDTO feature) {
        this.feature = feature;
    }
}
