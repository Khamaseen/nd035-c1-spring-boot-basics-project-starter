package com.udacity.jwdnd.course1.cloudstorage.models.forms.groupedform;

public class CredentialForm {
    private Integer credentialId;
    private String url;
    private String username;
    private String password;
    private Integer userId;

    public CredentialForm(String url, String username, String password, Integer userId) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.userId = userId;
    }

    public CredentialForm(Integer credentialId, String url, String username, String password, Integer userId) {
        this.credentialId = credentialId;
        this.url = url;
        this.username = username;
        this.password = password;
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

    public Integer getUserId() {
        return userId;
    }
}
