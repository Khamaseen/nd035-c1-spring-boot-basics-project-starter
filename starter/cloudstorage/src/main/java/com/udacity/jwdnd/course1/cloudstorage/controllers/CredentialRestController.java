package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.models.forms.groupedform.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/home/credentials")
public class CredentialRestController {
    
    private final CredentialService credentialService;
    private final UserService userService;

    public CredentialRestController(CredentialService credentialService, UserService userService) {
        this.credentialService = credentialService;
        this.userService = userService;
    }

    @PostMapping("/upload-credential")
    public void credentialUpload(@RequestBody() String data, Authentication authentication) {
        String username = authentication.getName();
        User currentLoggedInUser = this.userService.getUserByUserName(username);

        if (currentLoggedInUser == null) {
            return;
        }

        try {
            Gson gson = new Gson();
            CredentialRestController.JSONCredentialData jsonCredentialData = gson.fromJson(data, CredentialRestController.JSONCredentialData.class);
            if (jsonCredentialData.url == null || jsonCredentialData.username == null || jsonCredentialData.password == null) {
                return;
            }

            if (jsonCredentialData.credentialId != null && !jsonCredentialData.credentialId.trim().isEmpty()) {
                Integer credentialId = Integer.valueOf(jsonCredentialData.credentialId);
                CredentialForm previousCredentialForm = this.credentialService.getCredential(credentialId);
                if (previousCredentialForm.getUserId() == currentLoggedInUser.getUserId()) {
                    this.credentialService.updateCredential(
                            Integer.valueOf(jsonCredentialData.credentialId),
                            jsonCredentialData.url,
                            jsonCredentialData.username,
                            jsonCredentialData.password,
                            currentLoggedInUser.getUserId()
                    );
                }

            } else {
                this.credentialService.insertCredential(
                        jsonCredentialData.url,
                        jsonCredentialData.username,
                        jsonCredentialData.password,
                        currentLoggedInUser.getUserId()
                );
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @DeleteMapping("/delete-credential/{credentialId}")
    public void deletecredential(@PathVariable("credentialId") Integer credentialId, Authentication authentication) {
        String username = authentication.getName();
        User currentLoggedInUser = this.userService.getUserByUserName(username);

        if (currentLoggedInUser == null) {
            return;
        }

        try {
            CredentialForm credentialToDelete = this.credentialService.getCredential(credentialId);
            if (credentialToDelete != null && credentialToDelete.getUserId() == currentLoggedInUser.getUserId()) {
                this.credentialService.deleteCredential(credentialId);
            }
        } catch (JsonSyntaxException e) {
            System.err.println(e);
        }
    }

    class JSONCredentialData {
        public String url;
        public String username;
        public String password;
        public String credentialId;
    }
}
