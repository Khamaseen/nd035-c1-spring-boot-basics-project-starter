package com.udacity.jwdnd.course1.cloudstorage.models.forms.groupedform;

public class FileForm {
    private String fileName;
    private String uri;
    private String extension;
    private Integer userId;

    public FileForm(String fileName, String uri, String extension, Integer userId) {
        this.fileName = fileName;
        this.uri = uri;
        this.extension = extension;
        this.userId = userId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public Integer getUserId() {
        return userId;
    }

}
