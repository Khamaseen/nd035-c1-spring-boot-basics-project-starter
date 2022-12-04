package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.models.forms.groupedform.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.models.forms.groupedform.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static com.udacity.jwdnd.course1.cloudstorage.utils.EscapeString.escape;

@Controller
public class CredentialRestController {

    private final CredentialService credentialService;
    private final UserService userService;

    public CredentialRestController(CredentialService credentialService, UserService userService) {
        this.credentialService = credentialService;
        this.userService = userService;
    }

    @PostMapping("/upload-credential")
    public String credentialUpload(@ModelAttribute Credential credential, Authentication authentication, Model redirectAttrs) {
        String username = authentication.getName();
        User currentLoggedInUser = this.userService.getUserByUserName(username);

        if (currentLoggedInUser == null) {
            return "login";
        }

        if (credential.getUrl() == null || credential.getPassword() == null || credential.getUsername() == null) {
            redirectAttrs.addAttribute("errorMessage", "Data was invalid or missing properties");

            return "result";
        }

        if (credential.getCredentialId() != null) {
            this.credentialService.updateCredential(
                    credential.getCredentialId(),
                    credential.getUrl(),
                    credential.getUsername(),
                    credential.getPassword(),
                    currentLoggedInUser.getUserId()
            );
            redirectAttrs.addAttribute("successMessage", "Successfully updated the credentials");

            return "result";
        } else {
            this.credentialService.insertCredential(
                    credential.getUrl(),
                    credential.getUsername(),
                    credential.getPassword(),
                    currentLoggedInUser.getUserId()
            );
        }

        redirectAttrs.addAttribute("successMessage", "Successfully uploaded new credentials");

        return "result";
    }

    @PostMapping("/delete-credential/{credentialId}")
    public String deletecredential(@PathVariable("credentialId") Integer credentialId, Authentication authentication, Model model) {
        String username = authentication.getName();
        User currentLoggedInUser = this.userService.getUserByUserName(username);

        if (currentLoggedInUser == null) {
            return "login";
        }

        CredentialForm credentialToDelete = this.credentialService.getCredential(credentialId);
        if (credentialToDelete == null || credentialToDelete.getUserId() != currentLoggedInUser.getUserId()) {
            model.addAttribute("errorMessage", "Something went wrong while deleting a credential. Please try again.");

            return "result";
        }

        this.credentialService.deleteCredential(credentialId);

        model.addAttribute("successMessage", "Credential successfully deleted.");

        return "result";
    }

}
