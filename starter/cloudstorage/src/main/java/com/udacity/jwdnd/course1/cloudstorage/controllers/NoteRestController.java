package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.models.forms.groupedform.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/home/notes")
public class NoteRestController {

    private final UserService userService;
    private final NoteService noteService;

    public NoteRestController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @PostMapping("/upload-note")
    public void noteUpload(@RequestBody() String data, Authentication authentication) {
        String username = authentication.getName();
        User currentLoggedInUser = this.userService.getUserByUserName(username);

        if (currentLoggedInUser == null) {
            return;
        }

        try {
            Gson gson = new Gson();
            JSONNoteData jsonNoteData = gson.fromJson(data, JSONNoteData.class);
            if (jsonNoteData.noteTitle == null || jsonNoteData.noteDescription == null) {
                return;
            }

            if (jsonNoteData.noteId != null && !jsonNoteData.noteId.trim().isEmpty()) {
                Integer noteId = Integer.valueOf(jsonNoteData.noteId);
                NoteForm previousVersionNote = this.noteService.getNote(noteId);
                if (previousVersionNote.getUserId() == currentLoggedInUser.getUserId()) {
                    this.noteService.updateNote(noteId, jsonNoteData.noteTitle, jsonNoteData.noteDescription, currentLoggedInUser.getUserId());
                }

            } else {
                this.noteService.insertNote(jsonNoteData.noteTitle, jsonNoteData.noteDescription, currentLoggedInUser.getUserId());
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @DeleteMapping("/delete-note/{noteId}")
    public void deleteNote(@PathVariable("noteId") Integer noteId, Authentication authentication) {
        String username = authentication.getName();
        User currentLoggedInUser = this.userService.getUserByUserName(username);

        if (currentLoggedInUser == null) {
            return;
        }

        try {
            NoteForm noteToDelete = this.noteService.getNote(noteId);
            if (noteToDelete != null && noteToDelete.getUserId() == currentLoggedInUser.getUserId()) {
                this.noteService.deleteNote(noteId);
            }
        } catch (JsonSyntaxException e) {
            System.err.println(e);
        }
    }

    class JSONNoteData {
        public String noteTitle;
        public String noteDescription;
        public String noteId;
    }
}
