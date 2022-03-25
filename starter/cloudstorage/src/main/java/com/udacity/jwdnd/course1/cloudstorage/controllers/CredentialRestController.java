package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.udacity.jwdnd.course1.cloudstorage.models.forms.groupedform.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/home/credentials")
public class CredentialRestController {
    
    private final CredentialService credentialService;

    public CredentialRestController(CredentialService credentialService) {
        this.credentialService = credentialService;
    }

    @PostMapping("/upload-credential")
    public void handlecredentialUpload(@RequestBody() String data, Authentication authentication) {
        try {
            Gson gson = new Gson();
            CredentialRestController.JSONCredentialData credentialForm = gson.fromJson(data, CredentialRestController.JSONCredentialData.class);
            if (credentialForm.url == null || credentialForm.username == null || credentialForm.password == null) {
                return;
            }

            //TODO get userID
            Integer userId = 123;
            if (credentialForm.credentialId != null && !credentialForm.credentialId.trim().isEmpty()) {
                this.credentialService.updateCredential(
                        Integer.valueOf(credentialForm.credentialId),
                        credentialForm.url,
                        credentialForm.username,
                        credentialForm.password,
                        userId);
            } else {
                this.credentialService.insertCredential(credentialForm.url,
                        credentialForm.username,
                        credentialForm.password,
                        userId);
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @DeleteMapping("/delete-credential/{credentialId}")
    public void deletecredential(@PathVariable("credentialId") Integer credentialId, Authentication authentication) {
        try {
            CredentialForm credentialToDelete = this.credentialService.getCredential(credentialId);
            if (credentialToDelete != null) {
                // TODO check if user is really authorized (is his/her credential) authentication.getCredentials()
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
