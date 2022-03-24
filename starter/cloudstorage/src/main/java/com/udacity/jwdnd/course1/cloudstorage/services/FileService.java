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

    public File getFile(Integer fileId) {
        return this.fileMapper.getFile(fileId);
    }

    public List<FileForm> getFiles(Integer userId) {
        List<File> list = this.fileMapper.getFilesFromUser();
        if (list == null) {
            list = new ArrayList<File>();
        }

        return this.fileFormMapper.mapFilesToFileForms(list);
    }

    public int insertFile(String filename, Long filesize, String fileType, Integer userid, byte[] blob) {
        File file = new File(
                filesize,
                filename,
                fileType,
                blob,
                userid
        );

        return this.fileMapper.insert(file);
    }

    public int deleteFile(Integer fileId) {
        return this.fileMapper.delete(fileId);
    }

}
