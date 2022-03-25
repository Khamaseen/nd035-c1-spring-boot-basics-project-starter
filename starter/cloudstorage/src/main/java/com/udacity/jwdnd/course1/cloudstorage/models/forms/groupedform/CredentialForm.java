package com.udacity.jwdnd.course1.cloudstorage.models.forms.groupedform;

public class CredentialForm {
    private Integer credentialId;
    private String url;
    private String username;
    private String password;
    private String decryptedPassword;
    private Integer userId;

    public CredentialForm(Integer credentialId, String url, String username, String password, String decryptedPassword, Integer userId) {
        this.credentialId = credentialId;
        this.url = url;
        this.username = username;
        this.password = password;
        this.decryptedPassword = decryptedPassword;
        this.userId = userId;
    }

    public Integer getCredentialId() {
        return credentialId;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDecryptedPassword() {
        return decryptedPassword;
    }

    public Integer getUserId() {
        return userId;
    }
}
