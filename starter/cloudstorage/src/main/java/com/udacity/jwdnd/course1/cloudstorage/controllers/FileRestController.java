package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.google.gson.JsonSyntaxException;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@RestController
@RequestMapping("/home/files")
public class FileRestController {

    private FileService fileService;
    private UserService userService;

    public FileRestController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @GetMapping("/get-file/{fileId}")
    public ResponseEntity downloadFile(@PathVariable("fileId") Integer fileId, Authentication authentication) {
        try {
            File file = this.fileService.getFile(fileId);
            return ResponseEntity.ok()
                    .header(
                            HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + file.getFileName() + "\""
                    )
                    .body(file);
        } catch (Error e) {
            System.err.println(e);
            return ResponseEntity.internalServerError().body(new Object());
        }
    }

    @PostMapping("/upload-file")
    public void fileUpload(@RequestParam("file") MultipartFile file, Authentication authentication) {
        String username = authentication.getName();
        User currentLoggedInUser = this.userService.getUserByUserName(username);

        if (currentLoggedInUser == null) {
            return;
        }

        try {
            InputStream fis = file.getInputStream();
            byte[] blob = fis.readAllBytes();
            fis.close();

            this.fileService.insertFile(
                    file.getOriginalFilename(),
                    file.getSize(),
                    file.getContentType(),
                    currentLoggedInUser.getUserId(),
                    blob
            );
        } catch (Exception e) {
            System.err.println(e);
        }

    }

    @DeleteMapping("/delete-file/{fileId}")
    public void deleteFile(@PathVariable("fileId") Integer fileId, Authentication authentication) {
        String username = authentication.getName();
        User currentLoggedInUser = this.userService.getUserByUserName(username);

        if (currentLoggedInUser == null) {
            return;
        }

        try {
            File fileToDelete = this.fileService.getFile(fileId);
            if (fileToDelete != null && fileToDelete.getUserId() == currentLoggedInUser.getUserId()) {
                this.fileService.deleteFile(fileId);
            }
        } catch (JsonSyntaxException e) {
            System.err.println(e);
        }
    }

}
