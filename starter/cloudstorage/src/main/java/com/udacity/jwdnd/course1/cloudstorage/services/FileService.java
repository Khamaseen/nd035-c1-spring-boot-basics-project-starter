package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.formmappers.FileFormMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.forms.groupedform.FileForm;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {

    private final FileFormMapper fileFormMapper;

    public FileService(FileFormMapper fileFormMapper) {
        this.fileFormMapper = fileFormMapper;
    }

    // TODO returns mock, remove this
    public List<FileForm> getFiles(Integer userId) {
        List<File> list = new ArrayList<File>();
        list.add(new File("title", "description", ".dtt", 123));
        return this.fileFormMapper.mapFilesToFileForms(list);
    }

}
