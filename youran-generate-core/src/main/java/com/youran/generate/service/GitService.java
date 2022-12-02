package com.youran.generate.service;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.youran.common.constant.ErrorCode;
import com.youran.common.context.LoginContext;
import com.youran.common.exception.BusinessException;
import com.youran.generate.pojo.dto.GitCredentialDTO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.TransportConfigCallback;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.RawTextComparator;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.*;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.util.FS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * Git操作业务类
 *
 * @author cbb
 * @date 3/16/2018
 */
@Service
public class GitService {

    private final static Logger LOGGER = LoggerFactory.getLogger(GitService.class);
    @Autowired
    private LoginContext loginContext;
    /**
     * 获取从远程克隆下来的git仓库
     *
     * @param repoDir       仓库地址
     * @param remoteUrl     远程地址
     * @param credential    认证信息
     * @param oldBranchName 旧分支，3.1.0之后都是auto分支
     * @param newBranchName 新分支，3.1.0之后都是auto分支
     * @param lastCommit    上次commit
     * @return
     */
    public Repository getClonedRemoteRepository(String repoDir, String remoteUrl, GitCredentialDTO credential,
                                                String oldBranchName, String newBranchName, String lastCommit) {
        File repoDirFile = new File(repoDir);

        File repoGitFile = new File(repoDirFile, ".git");
        // 如果之前已经clone过了，直接返回
        if (repoGitFile.exists()) {
            return this.openRepository(repoDir);
        }
        File parentFile = repoDirFile.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        return this.cloneRemoteRepository(repoDirFile, remoteUrl, credential,
            oldBranchName, newBranchName, lastCommit);
    }

    /**
     * clone远程仓库+checkout旧分支+创建新分支
     *
     * @param repoDirFile   仓库地址
     * @param remoteUrl     远程地址
     * @param credential    认证信息
     * @param oldBranchName 旧分支，3.1.0之后都是auto分支
     * @param newBranchName 新分支，3.1.0之后都是auto分支
     * @param lastCommit    上次commit
     * @return
     */
    public Repository cloneRemoteRepository(File repoDirFile, String remoteUrl, GitCredentialDTO credential,
                                            String oldBranchName, String newBranchName, String lastCommit) {
        try {
            LOGGER.info("从远程仓库clone,远程仓库地址=" + remoteUrl +
                " , 目录=" + repoDirFile.getPath() +
                " , 老分支=" + oldBranchName + " , 新分支=" + newBranchName);

            CloneCommand cloneCommand = Git.cloneRepository()
                .setURI(remoteUrl)
                .setCloneAllBranches(true)
                .setDirectory(repoDirFile);
            if (credential.getType() == GitCredentialDTO.TYPE_PRIVATE_KEY) {
                cloneCommand.setTransportConfigCallback(this.buildTransportConfigCallback(credential));
            }
            // 认证
            CredentialsProvider credentialsProvider = null;
            if (credential != null) {
                credentialsProvider = new UsernamePasswordCredentialsProvider(credential.getUsername(), credential.getPassword());
            }
            try (Git git = cloneCommand
                .setCredentialsProvider(credentialsProvider)
                .call()) {
                // 列出远程所有分支
                Collection<Ref> refs = git.lsRemote()
                    .setCredentialsProvider(credentialsProvider)
                    .call();
                // 如果没有任何分支,或不存在旧分支则进行一次提交
                if (CollectionUtils.isEmpty(refs) || StringUtils.isBlank(oldBranchName)) {
                    git.commit()
                        .setAll(true)
                        .setMessage("首次提交")
                        .call();
                    // 以后都在auto分支上提交代码
                    this.checkoutNewBranch(git, newBranchName);
                } else {
                    // 创建本地老分支
                    git.branchCreate()
                        .setForce(true)
                        .setName(oldBranchName)
                        .setStartPoint("origin/" + oldBranchName).call();
                    // check到老分支
                    git.checkout().setName(oldBranchName).call();
                    // 校验上一次commit是否匹配
                    this.checkLastCommit(git, lastCommit);
                    // 兼容3.1.0之前的版本，需要checkout新分支
                    if (!Objects.equals(oldBranchName, newBranchName)) {
                        this.checkoutNewBranch(git, newBranchName);
                    }
                }
                return git.getRepository();
            }
        } catch (GitAPIException e) {
            LOGGER.error("clone仓库异常", e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR, "clone仓库异常:" + e.getMessage());
        }
    }

    private TransportConfigCallback buildTransportConfigCallback(GitCredentialDTO credential) {
        SshSessionFactory sshSessionFactory = new JschConfigSessionFactory() {
            @Override
            protected void configure(OpenSshConfig.Host host, Session session) {
                session.setConfig("StrictHostKeyChecking", "no");
            }

            @Override
            protected JSch createDefaultJSch(FS fs) throws JSchException {
                JSch defaultJSch = super.createDefaultJSch(fs);
                defaultJSch.addIdentity(credential.getPrivateKeyPath());
                return defaultJSch;
            }
        };
        return transport -> {
            SshTransport sshTransport = (SshTransport) transport;
            sshTransport.setSshSessionFactory(sshSessionFactory);
        };
    }

    /**
     * checkout新分支
     *
     * @param git
     * @param newBranchName 新分支名称，3.1.0之后都是auto分支
     * @throws GitAPIException
     */
    private void checkoutNewBranch(Git git, String newBranchName) throws GitAPIException {
        // 创建分支
        git.branchCreate()
            .setName(newBranchName)
            .call();
        // checkout分支
        git.checkout().setName(newBranchName).call();
    }

    /**
     * 校验上一次commit是否匹配
     *
     * @param git
     * @param lastCommit
     * @throws GitAPIException
     */
    private void checkLastCommit(Git git, String lastCommit) throws GitAPIException {
        // 如果不存在lastCommit，则直接返回
        if (StringUtils.isBlank(lastCommit)) {
            return;
        }
        Iterator<RevCommit> iterator = git.log().call().iterator();
        if (iterator.hasNext()) {
            RevCommit next = iterator.next();
            if (!Objects.equals(next.getName(), lastCommit)) {
                throw new BusinessException(ErrorCode.INNER_DATA_ERROR,
                    "auto分支被污染了，估计是您在该分支上手动提交了代码，请手动执行reset。友情提示：请不要对auto分支做任何修改");
            }
        }
    }


    /**
     * 提交到本地仓库
     *
     * @param repository 本地仓库
     * @return
     */
    public String commitAll(Repository repository, String message) {
        Assert.notNull(repository, "本地仓库为空");
        try (Git git = new Git(repository)) {
            // add所有文件
            git.add().addFilepattern(".").call();
            String user = loginContext.getCurrentUser();
            String email = user + "@youran.com";
            // 全部提交
            RevCommit commit = git.commit()
                .setAll(true)
                .setCommitter(user, email)
                .setAuthor(user, email)
                .setMessage(message)
                .call();
            return commit.getName();
        } catch (GitAPIException e) {
            LOGGER.error("提交仓库异常", e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR, "提交仓库异常");
        }
    }


    /**
     * 提交到暂存区
     *
     * @param repository 本地仓库
     * @return
     */
    public String createStash(Repository repository) {
        Assert.notNull(repository, "本地仓库为空");
        try (Git git = new Git(repository)) {
            // add所有文件
            git.add().addFilepattern(".").call();
            // 全部提交
            RevCommit commit = git.stashCreate().call();
            if (commit == null) {
                return null;
            }
            return commit.getName();
        } catch (GitAPIException e) {
            LOGGER.error("提交到暂存区异常", e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR, "提交到暂存区异常");
        }
    }

    /**
     * 推送远程仓库
     *
     * @param repository 本地仓库
     * @return
     */
    public void push(Repository repository, GitCredentialDTO credential) {
        Assert.notNull(repository, "本地仓库为空");
        CredentialsProvider credentialsProvider = null;
        if (credential != null) {
            credentialsProvider = new UsernamePasswordCredentialsProvider(credential.getUsername(), credential.getPassword());
        }
        try (Git git = new Git(repository)) {
            Iterable<PushResult> pushResults = git.push()
                .setCredentialsProvider(credentialsProvider)
                .call();
            pushResults.forEach(pushResult -> {
                if (StringUtils.isNotBlank(pushResult.getMessages())){
                    // 不一定是错误信息，但是大概率是；error级别醒目些。
                    LOGGER.error("远程仓库信息反馈:"+pushResult.getMessages());
                }
            });
        } catch (GitAPIException e) {
            LOGGER.error("推送远程仓库异常", e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR, "推送远程仓库异常");
        }
    }

    /**
     * 打开仓库
     *
     * @param repoDir 仓库目录
     * @return
     */
    private Repository openRepository(String repoDir) {
        File gitDir = new File(repoDir, ".git");
        // now open the resulting repository with a FileRepositoryBuilder
        FileRepositoryBuilder builder = new FileRepositoryBuilder();
        Repository repository;
        try {
            repository = builder.setGitDir(gitDir)
                .readEnvironment() // scan environment GIT_* variables
                .findGitDir() // scan up the file system tree
                .build();
        } catch (IOException e) {
            LOGGER.error("打开仓库异常", e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR, "打开仓库异常");
        }
        return repository;
    }

    /**
     * 获取暂存区和HEAD的差异
     *
     * @param repository
     * @return
     */
    public String getStashDiff(Repository repository, String stash) {
        try (Git git = new Git(repository)) {
            ObjectId newTreeId = repository.resolve(stash + "^{tree}");
            ObjectId oldTreeId = repository.resolve("HEAD^{tree}");
            ObjectReader reader = repository.newObjectReader();
            CanonicalTreeParser oldTreeIter = new CanonicalTreeParser();
            oldTreeIter.reset(reader, oldTreeId);
            CanonicalTreeParser newTreeIter = new CanonicalTreeParser();
            newTreeIter.reset(reader, newTreeId);
            List<DiffEntry> diffs = git.diff()
                .setNewTree(newTreeIter)
                .setOldTree(oldTreeIter)
                .call();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            DiffFormatter df = new DiffFormatter(out);
            df.setRepository(repository);
            df.setDiffComparator(RawTextComparator.WS_IGNORE_ALL);
            df.setDetectRenames(true);
            df.format(diffs);
            return out.toString("UTF-8");
        } catch (GitAPIException e) {
            LOGGER.error("显示代码差异异常", e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR, "显示代码差异异常");
        } catch (IOException e) {
            LOGGER.error("显示代码差异异常", e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR, "显示代码差异异常");
        }
    }

}
