package com.youran.generate.pojo.vo;

import com.youran.generate.pojo.dto.MetaProjectFeatureDTO;

/**
 * 项目详情展示对象
 *
 * @author: cbb
 * @date 2017/5/24
 */
public class MetaProjectShowVO extends MetaProjectListVO {

    private MetaProjectFeatureDTO feature;

    public MetaProjectFeatureDTO getFeature() {
        return feature;
    }

    public void setFeature(MetaProjectFeatureDTO feature) {
        this.feature = feature;
    }

}
