package com.youran.generate.pojo.vo;

import java.util.List;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * @author cbb
 * @date 2019/9/21
 */
public class MetaEntityListPairVO {

    private List<MetaEntityListVO> holds;
    private List<MetaEntityListVO> unholds;

    public MetaEntityListPairVO() {
    }

    public MetaEntityListPairVO(List<MetaEntityListVO> holds, List<MetaEntityListVO> unholds) {
        this.holds = holds;
        this.unholds = unholds;
    }

    public List<MetaEntityListVO> getHolds() {
        return holds;
    }

    public void setHolds(List<MetaEntityListVO> holds) {
        this.holds = holds;
    }

    public List<MetaEntityListVO> getUnholds() {
        return unholds;
    }

    public void setUnholds(List<MetaEntityListVO> unholds) {
        this.unholds = unholds;
    }
}
