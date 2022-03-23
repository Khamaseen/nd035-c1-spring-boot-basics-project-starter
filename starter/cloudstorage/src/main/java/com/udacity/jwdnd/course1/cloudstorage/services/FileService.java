package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mappers.formmappers.FileFormMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.forms.groupedform.FileForm;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {

    private final FileFormMapper fileFormMapper;
    private final FileMapper fileMapper;

    public FileService(FileFormMapper fileFormMapper, FileMapper fileMapper) {
        this.fileFormMapper = fileFormMapper;
        this.fileMapper = fileMapper;
    }

    // TODO progress is stopped here
    public List<FileForm> getFiles(Integer userId) {
        List<File> list = this.fileMapper.getFilesFromUser();
        System.out.println("get files: " + list);
        if (list == null) {
            list = new ArrayList<File>();
        }

        return this.fileFormMapper.mapFilesToFileForms(list);
    }

    public int insertFile(byte[] blob) {
        File file = new File(
                "fileName",
                "contentType",
                "5 MB",
                blob,
                123
        );

        return this.fileMapper.insert(file);
    }

}
