package com.youran.template02;

import org.apache.commons.io.filefilter.HiddenFileFilter;
import org.apache.commons.lang3.ArrayUtils;
import org.eclipse.jgit.api.Git;
import org.junit.Test;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.function.Consumer;

/**
 * 初始化代码模板
 *
 * @author cbb
 * @date 2019/11/20
 */
public class InitTemplateFile {

    public static final String REMOTE_URL = "https://github.com/PanJiaChen/vue-admin-template.git";
    public static final String BRANCH_TO_CLONE = "master";

    @Test
    public void initCode() throws Exception {
        //File repoDir = this.checkOutVueAdminTemplate();
        File repoDir = new File("C:/Users");
        this.onEachRepoFile(repoDir,this::copyRepoFile);
    }

    /**
     * 从github下载vue-admin-template原始代码
     */
    private File checkOutVueAdminTemplate() throws Exception {
        // 创建临时文件，并删除该文件，通过该方式防止文件夹已经被占用
        File repoDir = File.createTempFile("vue-admin-template", "");
        repoDir.delete();

        System.out.println("下载github原始代码到该目录:" + repoDir.getPath());
        Git.cloneRepository()
            .setURI(REMOTE_URL)
            .setCloneAllBranches(false)
            .setBranchesToClone(Arrays.asList("master"))
            .setDirectory(repoDir)
            .call();
        return repoDir;
    }

    /**
     * 遍历每个原始文件
     *
     * @param dir
     */
    private void onEachRepoFile(File dir, Consumer<File> consumer) {
        File[] files = dir.listFiles(getFileFilter());
        if (ArrayUtils.isEmpty(files)) {
            return;
        }
        for (File file : files) {
            if (file.isHidden()) {
                continue;
            }
            if (file.isDirectory()) {
                this.onEachRepoFile(file, consumer);
            } else {
                consumer.accept(file);
            }
        }
    }

    /**
     * 获取文件过滤器
     *
     * @return
     */
    public static FileFilter getFileFilter() {
        return HiddenFileFilter.VISIBLE;
    }

    /**
     * 将原始代码拷贝成freemarker模板文件
     *
     * @param repoFile
     */
    private void copyRepoFile(File repoFile) {
        System.out.println(repoFile.getPath());
    }


}
