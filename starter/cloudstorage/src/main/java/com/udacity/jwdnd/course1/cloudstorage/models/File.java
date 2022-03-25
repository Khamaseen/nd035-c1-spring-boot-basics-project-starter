package com.udacity.jwdnd.course1.cloudstorage.models;

public class File {
    private Integer fileId;
    private String fileName;
    private String fileType;
    private Long fileSizeInBytes;
    private byte[] fileDataAsBlob;
    private Integer userId;

    public File(Integer fileId, Long fileSizeInBytes, String fileName, String fileType, byte[] fileDataAsBlob, Integer userId) {
        this.fileId = fileId;
        this.fileSizeInBytes = fileSizeInBytes;
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileDataAsBlob = fileDataAsBlob;
        this.userId = userId;
    }

    public File(Long fileSizeInBytes, String fileName, String fileType, byte[] fileDataAsBlob, Integer userId) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileSizeInBytes = fileSizeInBytes;
        this.fileDataAsBlob = fileDataAsBlob;
        this.userId = userId;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getFileSizeInBytes() {
        return fileSizeInBytes;
    }

    public void setFileSizeInBytes(Long fileSizeInBytes) {
        this.fileSizeInBytes = fileSizeInBytes;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public byte[] getFileDataAsBlob() {
        return fileDataAsBlob;
    }

    public void setFileDataAsBlob(byte[] fileDataAsBlob) {
        this.fileDataAsBlob = fileDataAsBlob;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
