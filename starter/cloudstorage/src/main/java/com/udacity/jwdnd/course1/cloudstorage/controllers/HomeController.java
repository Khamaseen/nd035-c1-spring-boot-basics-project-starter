package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @PostMapping()
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                           RedirectAttributes redirectAttributes) {
        System.out.println("multipart file");



        String message = "";
        byte[] pic;
        try {
            InputStream fis = file.getInputStream();
            pic = new byte[fis.available()];
            fis.read(pic);
            fis.close();

            System.out.println("saved file : " +this.fileService.insertFile(pic));

            message = "Uploaded the file successfully: " + file.getOriginalFilename();
        } catch (Exception e) {
            System.out.println(e);
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
        }

        redirectAttributes.addFlashAttribute("message", message);

        return "redirect:/home";
    }

}

