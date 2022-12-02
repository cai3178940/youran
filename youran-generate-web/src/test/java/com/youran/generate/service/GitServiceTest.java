package com.youran.generate.service;

import com.youran.generate.pojo.dto.GitCredentialDTO;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.File;

/**
 * @author: cbb
 * @date: 2022-11-30
 */
public class GitServiceTest {

    GitService gitService = new GitService();

    @Test
    @Disabled
    public void cloneRemoteRepository() throws Exception {
        String remoteUrl = "git@coding.xx.com:rmb/rebirth-cis-platform.git";
        String lastCommit = "a5177c74e5bdf6f070e6e0edf753163d6fd6dd0e";
        File repoDirFile = new File("D:\\youran-test");
        GitCredentialDTO credential = GitCredentialDTO.buildPrivateKeyCredential("D:\\id_rsa");
        gitService.cloneRemoteRepository(repoDirFile,
            remoteUrl, credential,
            "auto", "auto", lastCommit);
    }

}
