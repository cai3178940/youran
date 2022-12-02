package com.youran.generate.pojo.dto;

/**
 * git认证对象
 *
 * @author: cbb
 * @date: 2018/3/17
 */
public class GitCredentialDTO {

    public static final int TYPE_USER_PASSWORD = 1;
    public static final int TYPE_PRIVATE_KEY = 2;

    private int type;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 私钥路径
     */
    private String privateKeyPath;

    public GitCredentialDTO() {
    }

    public static GitCredentialDTO buildUserPasswordCredential(String username, String password) {
        GitCredentialDTO dto = new GitCredentialDTO();
        dto.setUsername(username);
        dto.setPassword(password);
        dto.setType(TYPE_USER_PASSWORD);
        return dto;
    }

    public static GitCredentialDTO buildPrivateKeyCredential(String privateKeyPath) {
        GitCredentialDTO dto = new GitCredentialDTO();
        dto.setPrivateKeyPath(privateKeyPath);
        dto.setType(TYPE_PRIVATE_KEY);
        return dto;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPrivateKeyPath() {
        return privateKeyPath;
    }

    public void setPrivateKeyPath(String privateKeyPath) {
        this.privateKeyPath = privateKeyPath;
    }
}
