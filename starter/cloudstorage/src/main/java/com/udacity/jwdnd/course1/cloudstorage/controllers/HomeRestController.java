package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.google.gson.JsonSyntaxException;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@RestController
@RequestMapping("/home")
public class HomeRestController {

    private FileService fileService;

    public HomeRestController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/get-file/{fileId}")
    public ResponseEntity downloadFile(@PathVariable("fileId") Integer fileId, Authentication authentication) {
        try {
            File file = this.fileService.getFile(fileId);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
                    .body(file);
        } catch (Error e) {
            return ResponseEntity.internalServerError().body(new Object());
        }
    }

    @PostMapping("/upload-file")
    public void handleFileUpload(@RequestParam("file") MultipartFile file, Authentication authentication) {
        if (file.isEmpty()) {
            return;
        }

        try {
            InputStream fis = file.getInputStream();
            byte[] blob = fis.readAllBytes();
            fis.close();

            //TODO get userID
            Integer userId = 123;
            this.fileService.insertFile(file.getOriginalFilename(), file.getSize(), file.getContentType(), userId, blob);
        } catch (Exception e) {
            //
        }

    }

    @DeleteMapping("/delete-file/{fileId}")
    public void deleteFile(@PathVariable("fileId") Integer fileId, Authentication authentication) {
        try {
            File fileToDelete = this.fileService.getFile(fileId);
            if (fileToDelete != null) {
                // TODO check if user is really authorized (is his/her file) authentication.getCredentials()
                int deleteFiles = this.fileService.deleteFile(fileId);
                System.out.println(deleteFiles);
            }
        } catch (JsonSyntaxException e) {
            //
        }
    }

}
