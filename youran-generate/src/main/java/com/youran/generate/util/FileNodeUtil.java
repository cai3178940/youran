package com.youran.generate.util;

import com.google.common.collect.Lists;
import com.youran.common.util.TreeUtil;
import com.youran.generate.pojo.vo.FileNodeVO;
import com.youran.generate.pojo.vo.TemplateFileListVO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.comparator.CompositeFileComparator;
import org.apache.commons.io.comparator.DirectoryFileComparator;
import org.apache.commons.io.comparator.NameFileComparator;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.OrFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileFilter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 文件节点工具类
 *
 * @author cbb
 * @date 2019/10/30
 */
public class FileNodeUtil {

    public static FileNodeComparator fileNodeComparator = new FileNodeComparator();

    /**
     * 获取文件过滤器
     *
     * @param suffixes
     * @return
     */
    public static FileFilter getFileFilter(String[] suffixes) {
        IOFileFilter suffixFileFilter = new SuffixFileFilter(suffixes);
        return new OrFileFilter(Lists.newArrayList(suffixFileFilter, DirectoryFileFilter.INSTANCE));
    }

    /**
     * 递归获取某个目录下的文件节点树
     *
     * @param dirFile
     * @param basePath
     * @param suffixes
     * @return
     */
    public static List<FileNodeVO> recurFileNodeTree(File dirFile, File basePath, String[] suffixes) {
        File[] files = dirFile.listFiles(getFileFilter(suffixes));
        if (ArrayUtils.isEmpty(files)) {
            return Collections.emptyList();
        }
        return Arrays.stream(files)
            .sorted(new CompositeFileComparator(DirectoryFileComparator.DIRECTORY_COMPARATOR, NameFileComparator.NAME_COMPARATOR))
            .map(file -> {
                FileNodeVO nodeVO = fileToNodeVO(file, basePath);
                if (file.isDirectory()) {
                    List<FileNodeVO> children = recurFileNodeTree(file, basePath, suffixes);
                    nodeVO.setChildren(children);
                }
                return nodeVO;
            })
            .collect(Collectors.toList());
    }


    /**
     * 文件转节点VO
     *
     * @param file
     * @param basePath
     * @return
     */
    public static FileNodeVO fileToNodeVO(File file, File basePath) {
        String path = file.getPath().substring(basePath.getPath().length())
            .replaceAll("\\\\", "/");
        return new FileNodeVO(file.isDirectory(), path, null);
    }


    /**
     * 将模板文件结果集转换成树
     *
     * @param list
     * @return
     */
    public static List<FileNodeVO> templateFileListToNodeTree(List<TemplateFileListVO> list) {
        List<FileNodeVO> tree = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(vo -> setFileNodeTreeByTemplateFile(tree, vo));
        }
        return tree;
    }

    /**
     * 对整棵树进行排序
     *
     * @param tree
     */
    public static void treeSort(List<FileNodeVO> tree) {
        tree.sort(fileNodeComparator);
        TreeUtil.bfsSearch(tree, t -> {
            if (!t.getDir()) {
                return false;
            }
            List<FileNodeVO> children = t.getChildren();
            children.sort(fileNodeComparator);
            return false;
        });
    }


    private static void setFileNodeTreeByTemplateFile(List<FileNodeVO> tree, TemplateFileListVO vo) {
        String fileDir = vo.getFileDir();
        String fileName = vo.getFileName();
        StringBuilder sb = new StringBuilder();
        List<FileNodeVO> currentChildren = tree;
        List<String> list = Arrays.stream(fileDir.split("/"))
            .filter(StringUtils::isNotBlank)
            .collect(Collectors.toList());
        for (String dir : list) {
            sb.append("/").append(dir);
            String path = sb.toString();
            FileNodeVO node = findByPath(currentChildren, path);
            if (node == null) {
                node = new FileNodeVO(true, path, null);
                currentChildren.add(node);
            }
            currentChildren = node.getChildren();
        }

        sb.append("/").append(fileName);
        String path = sb.toString();
        FileNodeVO node = findByPath(currentChildren, path);
        if (node == null) {
            node = new FileNodeVO(false, path, vo);
            currentChildren.add(node);
        }
    }


    private static FileNodeVO findByPath(List<FileNodeVO> list, String path) {
        return list.stream().filter(vo -> Objects.equals(vo.getPath(), path))
            .findFirst()
            .orElse(null);
    }


    /**
     * 文件节点比较器
     */
    public static class FileNodeComparator implements Comparator<FileNodeVO> {

        private int getDirType(FileNodeVO vo) {
            if (vo.getDir()) {
                return 1;
            } else {
                return 2;
            }
        }

        @Override
        public int compare(FileNodeVO o1, FileNodeVO o2) {
            int dirType1 = this.getDirType(o1);
            int dirType2 = this.getDirType(o2);
            if (dirType1 == dirType2) {
                return StringUtils.compare(o1.getPath(), o2.getPath());
            } else {
                return dirType1 - dirType2;
            }
        }

    }

}
