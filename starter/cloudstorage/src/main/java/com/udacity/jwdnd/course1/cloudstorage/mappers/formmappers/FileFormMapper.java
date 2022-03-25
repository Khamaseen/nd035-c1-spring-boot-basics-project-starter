package com.udacity.jwdnd.course1.cloudstorage.mappers.formmappers;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.forms.groupedform.FileForm;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FileFormMapper {
    private final int STEP_OF_1024 = 1024;

    public List<FileForm> mapFilesToFileForms(List<File> files) {
        return files.stream().map(this::mapFileToFileForm).toList();
    }

    public FileForm mapFileToFileForm(File file) {
        return new FileForm(
                file.getFileId(),
                file.getFileName(),
                file.getFileType(),
                file.getFileSizeInBytes(),
                this.mapFileSizeInBytesToFileSizeToDisplay(file.getFileSizeInBytes()),
                file.getFileDataAsBlob(),
                file.getUserId()
        );
    }

    private String mapFileSizeInBytesToFileSizeToDisplay(Long fileSizeInBytes) {
        if (fileSizeInBytes <= STEP_OF_1024) {
            return fileSizeInBytes + " Bytes";
        }

        long fileSizeInKb = fileSizeInBytes / STEP_OF_1024;
        if (fileSizeInKb <= STEP_OF_1024) {
            return fileSizeInKb + " KB";
        }

        long fileSizeInMb = fileSizeInKb / STEP_OF_1024;
        if (fileSizeInMb <= STEP_OF_1024) {
            return String.valueOf(fileSizeInMb);
        }

        return fileSizeInMb / 1024 + " GB";
    }
}
