package com.youran.common.tree;

import java.util.List;

/**
 * 树节点接口
 *
 * @author cbb
 * @date 2017/5/8
 */
public interface TreeNode<T extends TreeNode> {

    /**
     * 获取当前节点id
     *
     * @return
     */
    Object fetchId();

    /**
     * 获取父节点id
     *
     * @return
     */
    Object fetchParentId();

    /**
     * 获取子节点列表
     *
     * @return
     */
    List<T> fetchChildren();

    /**
     * 设置子节点
     *
     * @param children
     */
    void putChildren(List<T> children);

}
