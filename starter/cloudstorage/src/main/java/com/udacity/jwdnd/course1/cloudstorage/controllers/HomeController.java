package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

    private UserService userService;
    private CredentialService credentialService;
    private NoteService noteService;
    private FileService fileService;

    public HomeController(CredentialService credentialService, NoteService noteService, FileService fileService, UserService userService) {
        this.credentialService = credentialService;
        this.noteService = noteService;
        this.fileService = fileService;
        this.userService = userService;
    }

    @GetMapping
    public String getHomePage(Authentication authentication, Model model) {
        String username = authentication.getName();
        User currentLoggedInUser = this.userService.getUserByUserName(username);

        if (currentLoggedInUser == null) {
            return "redirect:login";
        }
        
        Integer userId = currentLoggedInUser.getUserId();
        model.addAttribute("fileForms", this.fileService.getFiles(userId));
        model.addAttribute("noteForms", this.noteService.getNotes(userId));
        model.addAttribute("credentialForms", this.credentialService.getCredentialsForUser(userId));

        return "home";
    }

}

