package com.youran.generate.service;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

/**
 * Title:
 * Description:
 * Author: cbb
 * Create Time:3/16/2018 21:43
 */
@Service
public class JGitService {

    private final static Logger LOGGER = LoggerFactory.getLogger(JGitService.class);

    /**
     * 创建仓库+创建分支+checkout分支
     * @return
     */
    public String createNewRepository(String projectName,String branchName){
        try {
            File localPath = File.createTempFile(projectName, "");
            if(!localPath.delete()) {
                throw new IOException("Could not delete temporary file " + localPath);
            }

            // create the directory
            try (Git git = Git.init().setDirectory(localPath).call()) {
                System.out.println("Having repository: " + git.getRepository().getDirectory());
                //创建分支
                git.branchCreate()
                    .setName(branchName)
                    .call();
                //checkout分支
                git.checkout().setName(branchName).call();
                return git.getRepository().getDirectory().getParent();
            }
        } catch (IOException e) {
            LOGGER.error("创建仓库异常",e);
        } catch (GitAPIException e) {
            LOGGER.error("创建仓库异常",e);
        }
        return null;
    }

    /**
     * 将项目代码拷贝到仓库中
     * @param repoDir 仓库目录
     * @param genDir
     */
    public void copyGenFiles(String repoDir,String genDir){

    }

    /**
     * 打开仓库
     * @param repoDir 仓库目录
     * @return
     */
    public Repository openRepository(String repoDir){
        File _gitDir = new File(repoDir,".git");
        // now open the resulting repository with a FileRepositoryBuilder
        FileRepositoryBuilder builder = new FileRepositoryBuilder();
        Repository repository = null;
        try {
            repository = builder.setGitDir(_gitDir)
                .readEnvironment() // scan environment GIT_* variables
                .findGitDir() // scan up the file system tree
                .build();
        } catch (IOException e) {
            LOGGER.error("打开仓库异常",e);
        }
        return repository;
    }
    /**
     * 将整个仓库提交+推送远程
     * @param repoDir 仓库目录
     * @return
     */
    public String commitAll(String repoDir,String remoteURL){
        Repository repository = this.openRepository(repoDir);
        if(repository==null){
            return null;
        }
        try (Git git = new Git(repository)) {
            // Stage all changed files, omitting new files, and commit with one command
            RevCommit commit = git.commit()
                .setAll(true)
                .setMessage("Commit changes to all files")
                .call();

            git.push()
                .setRemote(remoteURL)
                .call();

            return commit.getName();
        }catch (GitAPIException e) {
            LOGGER.error("提交仓库异常",e);
        }
        return null;
    }

    /**
     * 推送到远程分支
     * @param repoDir
     * @return
     */
    public boolean push(String repoDir){
        return true;
    }


    public static void main(String[] args) {
        JGitService jGitService = new JGitService();
        String repository = jGitService.createNewRepository("cbbTest","xxx");
        System.out.println(repository);
    }

}
