package com.youran.generate.pojo.vo;

import com.youran.common.pojo.vo.AbstractVO;
import com.youran.common.tree.TreeNode;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.io.FilenameUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 代码文件节点
 *
 * @author cbb
 * @date 2019/8/29
 */
public class FileNodeVO<T> extends AbstractVO implements TreeNode<FileNodeVO> {

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

    /**
     * 其他信息
     */
    @ApiModelProperty(notes = "其他信息", example = "")
    private T info;

    @ApiModelProperty(notes = "子节点")
    private List<FileNodeVO> children;


    public FileNodeVO() {
    }

    public FileNodeVO(Boolean dir, String path, T info) {
        this.dir = dir;
        this.path = path;
        this.name = path.substring(path.lastIndexOf("/") + 1);
        this.type = FilenameUtils.getExtension(this.name);
        if (dir) {
            this.children = new ArrayList<>();
        }
        this.info = info;
    }

    public T getInfo() {
        return info;
    }

    public void setInfo(T info) {
        this.info = info;
    }

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

    @Override
    public Object fetchId() {
        return path;
    }

    @Override
    public Object fetchParentId() {
        return null;
    }

    @Override
    public List<FileNodeVO> fetchChildren() {
        return children;
    }

    @Override
    public void putChildren(List<FileNodeVO> children) {
        this.children = children;
    }

}
