package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.models.forms.groupedform.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static com.udacity.jwdnd.course1.cloudstorage.utils.EscapeString.escape;

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
    public String credentialUpload(@RequestBody() String data, Authentication authentication, Model redirectAttrs) {
        String username = authentication.getName();
        User currentLoggedInUser = this.userService.getUserByUserName(username);

        if (currentLoggedInUser == null) {
            return "redirect:/login";
        }

        try {
            Gson gson = new Gson();
            CredentialRestController.JSONCredentialData jsonCredentialData = gson.fromJson(escape(data), CredentialRestController.JSONCredentialData.class);
            if (jsonCredentialData.url == null || jsonCredentialData.username == null || jsonCredentialData.password == null) {
                redirectAttrs.addAttribute("errorMessage", "Error: data was invalid or missing properties");

                return "result";
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

                    redirectAttrs.addAttribute("successMessage", "Successfully updated the credential");
                }

            } else {
                this.credentialService.insertCredential(
                        jsonCredentialData.url,
                        jsonCredentialData.username,
                        jsonCredentialData.password,
                        currentLoggedInUser.getUserId()
                );

                redirectAttrs.addAttribute("successMessage", "Success created a new credential");
            }
        } catch (Exception e) {
            System.err.println(e);
            redirectAttrs.addAttribute("errorMessage", "Error: something went wrong, please try again.");

            return "result";
        }

        redirectAttrs.addAttribute("successMessage", "Success uploaded the credentials");

        System.out.println("SHOULD REDIRECT TO RESULT");
        return "result";
    }

    @DeleteMapping("/delete-credential/{credentialId}")
    public String deletecredential(@PathVariable("credentialId") Integer credentialId, Authentication authentication, Model redirectAttrs) {
        String username = authentication.getName();
        User currentLoggedInUser = this.userService.getUserByUserName(username);

        if (currentLoggedInUser == null) {
            return "redirect:/login";
        }

        try {
            CredentialForm credentialToDelete = this.credentialService.getCredential(credentialId);
            if (credentialToDelete != null && credentialToDelete.getUserId() == currentLoggedInUser.getUserId()) {
                this.credentialService.deleteCredential(credentialId);
            }
            redirectAttrs.addAttribute("successMessage", "Successfully deleted the credential.");

        } catch (JsonSyntaxException e) {
            System.err.println(e);
            redirectAttrs.addAttribute("errorMessage", "Error: something went wrong, please try again");

            return "result";
        }

        System.out.println("SHOULD REDIRECT TO RESULT");
        return "result";
    }

    class JSONCredentialData {
        public String url;
        public String username;
        public String password;
        public String credentialId;
    }
}
