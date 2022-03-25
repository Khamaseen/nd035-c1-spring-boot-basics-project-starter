package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.forms.groupedform.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

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
    public String getChatPage(Authentication authentication, Model model) {
        // TODO get user Id --> by getting user? from user name?
        model.addAttribute("fileForms", this.fileService.getFiles(123));
        model.addAttribute("noteForms", this.noteService.getNotes(123));
        model.addAttribute("credentialForms", this.credentialService.getCredentialsForUser(123));

        return "home";
    }

}

