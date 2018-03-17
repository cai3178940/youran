package com.youran.generate.service;

import com.youran.generate.exception.GenerateException;
import com.youran.generate.pojo.dto.GitCredentialDTO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

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
     * clone远程仓库+checkout旧分支+创建新分支
     * @param projectName 项目名
     * @param remoteUrl 远程地址
     * @param credential 认证信息
     * @param oldBranchName 旧分支
     * @param newBranchName 新分支
     * @return
     */
    public String cloneRemoteRepository(String projectName, String remoteUrl,GitCredentialDTO credential,
                                        String oldBranchName, String newBranchName){
        try {
            File repoDir = File.createTempFile(projectName, "");
            if(!repoDir.delete()) {
                throw new IOException("Could not delete temporary file " + repoDir);
            }
            LOGGER.info("Cloning from " + remoteUrl + " to " + repoDir);

            CloneCommand cloneCommand = Git.cloneRepository()
                .setURI(remoteUrl)
                .setDirectory(repoDir);
            //如果指定了旧分支，则只clone该分支
            if(StringUtils.isNotBlank(oldBranchName)){
                cloneCommand.setBranchesToClone(Arrays.asList(oldBranchName));
            }
            //认证
            CredentialsProvider credentialsProvider = null;
            if(credential!=null) {
                credentialsProvider = new UsernamePasswordCredentialsProvider(credential.getUsername(), credential.getPassword());
            }
            try (Git git = cloneCommand
                .setCredentialsProvider(credentialsProvider)
                .call()) {
                //列出远程所有分支
                Collection<Ref> refs = git.lsRemote()
                    .setCredentialsProvider(credentialsProvider)
                    .call();
                //如果没有任何分支则进行一次提交
                if(CollectionUtils.isEmpty(refs)){
                    git.commit()
                        .setAll(true)
                        .setMessage("首次提交")
                        .call();
                }
                //创建分支
                git.branchCreate()
                    .setName(newBranchName)
                    .call();
                //checkout分支
                git.checkout().setName(newBranchName).call();
                return git.getRepository().getDirectory().getParent();
            }
        } catch (IOException e) {
            LOGGER.error("clone仓库异常",e);
            throw new GenerateException("clone仓库异常");
        } catch (GitAPIException e) {
            LOGGER.error("clone仓库异常",e);
            throw new GenerateException("clone仓库异常");
        }
    }

    /**
     * 将整个仓库提交+推送远程
     * @param repoDir 仓库目录
     * @return
     */
    public String commitAll(String repoDir,String message,GitCredentialDTO credential){
        CredentialsProvider credentialsProvider = null;
        if(credential!=null) {
            credentialsProvider = new UsernamePasswordCredentialsProvider(credential.getUsername(), credential.getPassword());
        }
        Repository repository = this.openRepository(repoDir);
        if(repository==null){
            return null;
        }
        try (Git git = new Git(repository)) {
            // add所有文件
            git.add().addFilepattern(".").call();
            // 全部提交
            RevCommit commit = git.commit()
                .setAll(true)
                .setMessage(message)
                .call();

            git.push()
                .setCredentialsProvider(credentialsProvider)
                .call();

            return commit.getName();
        }catch (GitAPIException e) {
            LOGGER.error("提交仓库异常",e);
            throw new GenerateException("提交仓库异常");
        }
    }

    /**
     * 打开仓库
     * @param repoDir 仓库目录
     * @return
     */
    private Repository openRepository(String repoDir){
        File _gitDir = new File(repoDir,".git");
        // now open the resulting repository with a FileRepositoryBuilder
        FileRepositoryBuilder builder = new FileRepositoryBuilder();
        Repository repository;
        try {
            repository = builder.setGitDir(_gitDir)
                .readEnvironment() // scan environment GIT_* variables
                .findGitDir() // scan up the file system tree
                .build();
        } catch (IOException e) {
            LOGGER.error("打开仓库异常",e);
            throw new GenerateException("打开仓库异常");
        }
        return repository;
    }






    /*public static void main(String[] args) throws IOException {
        JGitService jGitService = new JGitService();
        GitCredentialDTO credentialDTO = new GitCredentialDTO("caibinbing", "CaiXiaoGe0611@");
        String repository = jGitService.cloneRemoteRepository(
            "cbbTest",
            "http://git.jd.com/cbb/my-test2.git",
            credentialDTO,
            "branch2",
            "branch3");

        System.out.println(repository);
        // create the file
        File myfile = new File(repository, "testfile3");
        myfile.createNewFile();
        try(PrintWriter writer = new PrintWriter(myfile)) {
            writer.append("Hello, world!3");
        }

        jGitService.commitAll(repository,"branch3 commit",credentialDTO);

    }*/
}
