package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.google.gson.JsonSyntaxException;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.models.forms.groupedform.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class NoteRestController {

    private final UserService userService;
    private final NoteService noteService;

    public NoteRestController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @PostMapping("/upload-note")
    public String noteUpload(@ModelAttribute Note note, Authentication authentication, Model model) {
        String username = authentication.getName();
        User currentLoggedInUser = this.userService.getUserByUserName(username);

        if (currentLoggedInUser == null) {
            return "/login";
        }

        if (note.getNoteTitle() == null || note.getNoteDescription() == null) {
            model.addAttribute("errorMessage", "Data was invalid or missing properties");

            return "result";
        }

        if (note.getNoteId() != null) {
            this.noteService.updateNote(note.getNoteId(), note.getNoteTitle(), note.getNoteDescription(), currentLoggedInUser.getUserId());
        } else {
            if (this.noteService.getNoteByTitle(note.getNoteTitle()) != null) {
                model.addAttribute("errorMessage", "Note title already exists.");

                return "result";
            }
            this.noteService.insertNote(note.getNoteTitle(), note.getNoteDescription(), currentLoggedInUser.getUserId());
        }

        model.addAttribute("successMessage", "Note successfully saved.");

        return "result";
    }

    @PostMapping("/delete-note/{noteId}")
    public String deleteNote(@PathVariable("noteId") Integer noteId, Authentication authentication, Model model) {
        String username = authentication.getName();
        User currentLoggedInUser = this.userService.getUserByUserName(username);

        if (currentLoggedInUser == null) {
            return "/login";
        }

        NoteForm noteToDelete = this.noteService.getNote(noteId);
        if (noteToDelete == null || noteToDelete.getUserId() != currentLoggedInUser.getUserId()) {
            model.addAttribute("errorMessage", "Something went wrong while deleting a note. Please try again.");

            return "result";
        }

        this.noteService.deleteNote(noteId);

        model.addAttribute("successMessage", "Note successfully deleted.");

        return "result";

    }

}
