package com.youran.generate.pojo.dto;

/**
 * <p>Title: 系统导入导出专用DTO</p>
 * <p>Description: </p>
 * @author cbb
 * @date 2019/10/23
 */
public class SystemDTO {

    /**
     * 系统版本
     */
    private String version;

    public SystemDTO() {
    }

    public SystemDTO(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
