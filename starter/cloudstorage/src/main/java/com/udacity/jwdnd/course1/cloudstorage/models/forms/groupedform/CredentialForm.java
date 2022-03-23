package com.udacity.jwdnd.course1.cloudstorage.models.forms.groupedform;

public class CredentialForm {
    private String url;
    private String username;
    private Integer userId;
    private String password;

    public CredentialForm(Integer userId, String url, String username, String password) {
        this.url = url;
        this.userId = userId;
        this.username = username;
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public Integer getUserId() {
        return userId;
    }
}
