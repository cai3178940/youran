package com.youran.common.util;


import com.youran.common.tree.TreeNode;
import org.apache.commons.collections4.CollectionUtils;

import java.io.Serializable;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * 树节点工具类
 *
 * @author cbb
 * @date 2017/5/8 11:30
 */
public class TreeUtil {

    /**
     * 将List重组为tree
     */
    public static <T extends TreeNode> List<T> constructListToTree(Collection<T> taskList, Object parentId) {
        //key:父节点ID value:子节点集合
        Map<Object, List<T>> taskMap = new HashMap<>(64);
        //将List重组到Map中
        taskList.forEach(node -> {
            List<T> tempTaskList = taskMap.get(node.fetchParentId());
            if (tempTaskList == null) {
                tempTaskList = new ArrayList<>();
                taskMap.put(node.fetchParentId(), tempTaskList);
            }
            tempTaskList.add(node);
        });
        //顶级节点集合
        List<T> parentNodeList = taskMap.get(parentId);
        recurTaskList(parentNodeList, taskMap);
        return parentNodeList == null ? new ArrayList<>() : parentNodeList;
    }

    /**
     * 将重组好的Map进行树形结构处理
     *
     * @param taskList  父节点集合(不一定是顶级节点 因为会递归调用)
     * @param sourceMap 组装好的Map集合
     */
    private static <T extends TreeNode> void recurTaskList(Collection<T> taskList, Map<Object, List<T>> sourceMap) {
        if (CollectionUtils.isEmpty(taskList)) {
            return;
        }
        taskList.forEach(node -> {
            node.putChildren(sourceMap.get(node.fetchId()));
            recurTaskList(node.fetchChildren(), sourceMap);
        });
    }

    /**
     * 根据父节点获取所有下级节点集合
     *
     * @param t        父节点对象
     * @param justLeaf 是否只采集叶子节点
     * @param <T>      树节点泛型
     * @return 子节点集合
     */
    public static <T extends TreeNode> Set<T> getChildSet(T t, boolean justLeaf) {
        List<T> children = t.fetchChildren();
        if (CollectionUtils.isEmpty(children)) {
            return null;
        }
        Set<T> childSet = new HashSet<>();
        for (T child : children) {
            TreeUtil.recurTreeNode(child, (t1, isLeaf) -> {
                //仅采集叶子的情况判断
                if (justLeaf && !isLeaf) {
                    return;
                }
                childSet.add(t1);
            });
        }
        return childSet;
    }

    /**
     * 递归父节点下所有节点，并执行处理
     *
     * @param t        父节点
     * @param consumer 消费函数，接收2个参数，第一个参数是“节点对象”，第二个参数是“是否叶子节点标记”
     * @param <T>      树节点泛型
     */
    public static <T extends TreeNode> void recurTreeNode(T t, BiConsumer<T, Boolean> consumer) {
        if (t == null) {
            return;
        }
        List<T> children = t.fetchChildren();
        boolean empty = CollectionUtils.isEmpty(children);
        consumer.accept(t, empty);
        if (!empty) {
            for (T child : children) {
                TreeUtil.recurTreeNode(child, consumer);
            }
        }
    }


    /**
     * 递归获取所有子节点
     *
     * @param parents
     * @param limit
     * @param findChildren
     * @param <T>
     * @return
     */
    public static <T extends Serializable> List<T> findChildrenLoop(List<T> parents, int limit,
                                                                    Function<List<T>, List<T>> findChildren) {
        //没有提供父节点，直接返回空列表
        if (CollectionUtils.isEmpty(parents)) {
            return new ArrayList<>();
        }
        //递归深度耗尽，直接返回空列表
        if (limit <= 0) {
            return new ArrayList<>();
        }
        List<T> all = new ArrayList<>();
        List<T> children = findChildren.apply(parents);
        all.addAll(children);
        if (CollectionUtils.isNotEmpty(children)) {
            List<T> childrenLoop = findChildrenLoop(children, --limit, findChildren);
            if (CollectionUtils.isNotEmpty(childrenLoop)) {
                all.addAll(childrenLoop);
            }
        }
        return all;
    }


    /**
     * 深度优先搜索树
     *
     * @param tree
     * @param predicate 节点处理函数，如果返回false则停止搜索
     * @param <T>       停止节点
     */
    public static <T extends TreeNode> T dfsSearch(List<T> tree, Predicate<T> predicate) {
        Stack<T> nodeStack = new Stack<>();
        nodeStack.addAll(tree);
        while (!nodeStack.isEmpty()) {
            T node = nodeStack.pop();
            if (predicate.test(node)) {
                return node;
            }
            List<T> children = node.fetchChildren();
            if (CollectionUtils.isNotEmpty(children)) {
                for (T child : children) {
                    nodeStack.push(child);
                }
            }
        }
        return null;
    }

    /**
     * 广度优先搜索树，找到第一个就返回
     *
     * @param tree
     * @param predicate
     * @param <T>
     * @return
     */
    public static <T extends TreeNode> T bfsSearch(List<T> tree, Predicate<T> predicate) {
        Deque<T> nodeDeque = new ArrayDeque<>();
        nodeDeque.addAll(tree);
        while (!nodeDeque.isEmpty()) {
            T node = nodeDeque.pollFirst();
            if (predicate.test(node)) {
                return node;
            }
            List<T> children = node.fetchChildren();
            if (CollectionUtils.isNotEmpty(children)) {
                for (T child : children) {
                    nodeDeque.add(child);
                }
            }
        }
        return null;
    }


}
