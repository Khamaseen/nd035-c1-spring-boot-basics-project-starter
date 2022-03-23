package com.udacity.jwdnd.course1.cloudstorage.mappers.formmappers;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.forms.groupedform.FileForm;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FileFormMapper {

    public List<FileForm> mapFilesToFileForms(List<File> files) {
        return files.stream().map(this::mapFileToFileForm).toList();
    }

    public FileForm mapFileToFileForm(File file) {
        return new FileForm(
                file.getFilename(),
                file.getFilesize(),
                file.getContenttype(),
                file.getUserid()
        );
    }
}
