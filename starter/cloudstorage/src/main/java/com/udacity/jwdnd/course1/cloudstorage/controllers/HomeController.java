package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.FileInputStream;
import java.io.InputStream;

@Controller
@RequestMapping("/home")
public class HomeController {

    private CredentialService credentialService;
    private NoteService noteService;
    private FileService fileService;

    public HomeController(CredentialService credentialService, NoteService noteService, FileService fileService) {
        this.credentialService = credentialService;
        this.noteService = noteService;
        this.fileService = fileService;
    }

    @GetMapping
    public String getChatPage(Model model) {
        model.addAttribute("fileForms", this.fileService.getFiles(123));
        model.addAttribute("noteForms", this.noteService.getNotes(123));
        model.addAttribute("credentialForms", this.credentialService.getCredentialsForUser(123));

        return "home";
    }

//    @PostMapping()
//    public String handleFileUpload(@RequestParam("file") MultipartFile file) {
//        byte[] blob;
//        try {
//            InputStream fis = file.getInputStream();
//            blob = new byte[fis.available()];
//            fis.read(blob);
//            fis.close();
//
//            //TODO get userID
//            Integer userId = 123;
//            this.fileService.insertFile(file.getOriginalFilename(), file.getContentType(), file.getSize(), userId, blob);
//        } catch (Exception e) {
//            //
//        }
//
//        return "redirect:/home";
//    }

//    @PostMapping("/delete-file")
//    public String deleteFile(@RequestBody() String data, Authentication authentication) {
//        try {
//            Gson gson = new Gson();
//            JSONWithId json = gson.fromJson(data, JSONWithId.class);
//            // TODO check if user is really authorized (is his/her file) authentication.getCredentials()
//            int deleteFiles = this.fileService.deleteFile(json.id);
//            System.out.println(deleteFiles);
//        } catch (JsonSyntaxException e) {
//            //
//        }
//
//        return "redirect:/home";
//    }
//
//    private class JSONWithId {
//        public int id;
//    }

//    @DeleteMapping("/{fileId}")
//    public void deleteFile(@PathVariable Integer fileId, Authentication authentication) {
//        try {
//            // TODO check if user is really authorized (is his/her file) authentication.getCredentials()
//            int deleteFiles = this.fileService.deleteFile(fileId);
//            System.out.println(deleteFiles);
//        } catch (JsonSyntaxException e) {
//            //
//        }
//
////        return "redirect:/home";
//    }

}

