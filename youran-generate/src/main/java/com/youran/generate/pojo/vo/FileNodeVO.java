package com.youran.generate.pojo.vo;

import com.youran.common.pojo.vo.AbstractVO;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * <p>Title: 代码文件节点</p>
 * <p>Description: </p>
 * @author cbb
 * @date 2019/8/29
 */
public class FileNodeVO extends AbstractVO {

    /**
     * 文件名
     */
    @ApiModelProperty(notes = "文件名", example = "xxx.txt")
    private String name;

    /**
     * 是否目录
     */
    @ApiModelProperty(notes = "是否目录", example = "false")
    private Boolean dir;

    /**
     * 路径
     */
    @ApiModelProperty(notes = "路径", example = "/aaa/xxx.txt")
    private String path;

    /**
     * 文件类型(后缀)
     */
    @ApiModelProperty(notes = "文件类型(后缀)", example = "txt")
    private String type;

    @ApiModelProperty(notes = "子节点")
    private List<FileNodeVO> children;

    public List<FileNodeVO> getChildren() {
        return children;
    }

    public void setChildren(List<FileNodeVO> children) {
        this.children = children;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getDir() {
        return dir;
    }

    public void setDir(Boolean dir) {
        this.dir = dir;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
