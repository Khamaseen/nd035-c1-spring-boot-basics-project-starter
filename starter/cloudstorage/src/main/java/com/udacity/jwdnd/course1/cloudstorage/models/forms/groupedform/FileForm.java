package com.udacity.jwdnd.course1.cloudstorage.models.forms.groupedform;

public class FileForm {
    private Integer fileId;
    private String fileName;
    private String fileType;
    private Long fileSizeInBytes;
    private String fileSizeToDisplay;
    private byte[] fileDataAsBlob;
    private Integer userid;

    public FileForm(
            Integer fileId,
            String fileName,
            String fileType,
            Long fileSizeInBytes,
            String fileSizeToDisplay,
            byte[] fileDataAsBlob,
            Integer userid
    ) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.fileSizeInBytes = fileSizeInBytes;
        this.fileSizeToDisplay = fileSizeToDisplay;
        this.fileDataAsBlob = fileDataAsBlob;
        this.userid = userid;
    }

    public Integer getFileId() {
        return fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public Long getFileSizeInBytes() {
        return fileSizeInBytes;
    }

    public String getFileSizeToDisplay() {
        return fileSizeToDisplay;
    }

    public byte[] getFileDataAsBlob() {
        return fileDataAsBlob;
    }

    public Integer getUserid() {
        return userid;
    }
}
