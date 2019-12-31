package com.youran.generate.service;

import com.youran.common.constant.ErrorCode;
import com.youran.common.exception.BusinessException;
import com.youran.generate.pojo.dto.GitCredentialDTO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
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
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * @author: cbb
 * @date: 3/16/2018
 */
@Service
public class GitService {

    private final static Logger LOGGER = LoggerFactory.getLogger(GitService.class);

    /**
     * clone远程仓库+checkout旧分支+创建新分支
     *
     * @param projectName   项目名
     * @param remoteUrl     远程地址
     * @param credential    认证信息
     * @param oldBranchName 旧分支
     * @param newBranchName 新分支
     * @param lastCommit    上次commit
     * @return
     */
    public Repository cloneRemoteRepository(String projectName, String remoteUrl, GitCredentialDTO credential,
                                            String oldBranchName, String newBranchName, String lastCommit) {

        try {
            // 创建临时文件，并删除该文件，通过该方式防止文件夹已经被占用
            File repoDir = File.createTempFile(projectName, "");
            if (!repoDir.delete()) {
                throw new IOException("Could not delete temporary file " + repoDir);
            }
            LOGGER.info("从远程仓库clone,仓库地址=" + remoteUrl + " , 目录=" + repoDir +
                "老分支=" + oldBranchName + "新分支=" + newBranchName);

            CloneCommand cloneCommand = Git.cloneRepository()
                .setURI(remoteUrl)
                .setCloneAllBranches(true)
                .setDirectory(repoDir);
            // 如果指定了旧分支，则只clone该分支
            /*if(StringUtils.isNotBlank(oldBranchName)){
                cloneCommand.setBranchesToClone(Arrays.asList(oldBranchName));
            }*/
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
                } else {
                    // 创建本地老分支
                    git.branchCreate()
                        .setForce(true)
                        .setName(oldBranchName)
                        .setStartPoint("origin/" + oldBranchName).call();
                    // check到老分支
                    git.checkout().setName(oldBranchName).call();
                    // 校验上一次commit是否匹配
                    this.checkLastCommit(git, lastCommit, oldBranchName);
                }
                if (StringUtils.isNotBlank(newBranchName)) {
                    // 创建分支
                    git.branchCreate()
                        .setName(newBranchName)
                        .call();
                    // checkout分支
                    git.checkout().setName(newBranchName).call();
                }
                return git.getRepository();
            }
        } catch (IOException e) {
            LOGGER.error("clone仓库异常", e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR, "clone仓库异常");
        } catch (GitAPIException e) {
            LOGGER.error("clone仓库异常", e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR, "clone仓库异常");
        }
    }

    /**
     * 校验上一次commit是否匹配
     *
     * @param git
     * @param lastCommit
     * @param branchName
     * @throws GitAPIException
     */
    private void checkLastCommit(Git git, String lastCommit, String branchName) throws GitAPIException {
        // 如果不存在lastCommit，则直接返回
        if (StringUtils.isBlank(lastCommit)) {
            return;
        }
        Iterator<RevCommit> iterator = git.log().call().iterator();
        if (iterator.hasNext()) {
            RevCommit next = iterator.next();
            if (!Objects.equals(next.getName(), lastCommit)) {
                throw new BusinessException(ErrorCode.INNER_DATA_ERROR,
                    "分支【" + branchName + "】被污染了，估计是您在该分支上手动提交了代码，请手动执行reset。友情提示：请不要对auto分支做任何修改");
            }
        }
    }

    /**
     * 将整个仓库提交+推送远程
     *
     * @param repository 本地仓库
     * @return
     */
    public String commitAll(Repository repository, String message, GitCredentialDTO credential) {
        Assert.notNull(repository, "本地仓库为空");
        CredentialsProvider credentialsProvider = null;
        if (credential != null) {
            credentialsProvider = new UsernamePasswordCredentialsProvider(credential.getUsername(), credential.getPassword());
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
        } catch (GitAPIException e) {
            LOGGER.error("提交仓库异常", e);
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR, "提交仓库异常");
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
     * 获取仓库的代码差异
     *
     * @param repository
     * @return
     */
    public String getDiffText(Repository repository) {
        try (Git git = new Git(repository)) {
            ObjectId head = repository.resolve("HEAD^{tree}");
            ObjectId old = repository.resolve("HEAD~1^{tree}");
            ObjectReader reader = repository.newObjectReader();
            CanonicalTreeParser oldTreeIter = new CanonicalTreeParser();
            oldTreeIter.reset(reader, old);
            CanonicalTreeParser newTreeIter = new CanonicalTreeParser();
            newTreeIter.reset(reader, head);
            List<DiffEntry> diffs = git.diff()
                .setNewTree(newTreeIter)
                .setOldTree(oldTreeIter)
                .call();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            DiffFormatter df = new DiffFormatter(out);
            df.setRepository(repository);
            df.setDiffComparator(RawTextComparator.DEFAULT);
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
