package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.forms.UserForm;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static com.udacity.jwdnd.course1.cloudstorage.utils.EscapeString.escape;

@Controller()
@RequestMapping("/signup")
public class SignUpController {

    private final UserService userService;

    public SignUpController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String signupView(@ModelAttribute UserForm userForm, Model model) {
        return "signup";
    }

    @PostMapping()
    public String signupUser(@ModelAttribute UserForm userForm, Model model, RedirectAttributes redirectAttrs) {
        String signupError = null;

        userForm.setFirstName(escape(userForm.getFirstName()));
        userForm.setLastName(escape(userForm.getLastName()));
        userForm.setUsername(escape(userForm.getUsername()));
        userForm.setPassword(escape(userForm.getPassword()));

        if (!userService.isUsernameAvailable(userForm.getUsername())) {
            signupError = "The username already exists.";
        }

        if (signupError == null) {
            int rowsAdded = userService.createUser(userForm);
            if (rowsAdded < 0) {
                signupError = "There was an error signing you up. Please try again.";
            }
        }

        if (signupError == null) {
            redirectAttrs.addFlashAttribute("signupSuccess", true);
        } else {
            model.addAttribute("signupError", signupError);

            return "signup";
        }

        return "redirect:/login";
    }
}
