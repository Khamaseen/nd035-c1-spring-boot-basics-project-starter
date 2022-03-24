package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import com.udacity.jwdnd.course1.cloudstorage.models.forms.groupedform.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/home/notes")
public class NoteRestController {

    private NoteService noteService;

    public NoteRestController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/get-note/{noteId}")
    public ResponseEntity downloadnote(@PathVariable("noteId") Integer noteId, Authentication authentication) {
        try {
            NoteForm note = this.noteService.getNote(noteId);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; noteTitle=\"" + note.getTitle() + "\"")
                    .body(note);
        } catch (Error e) {
            System.err.println(e);
            return ResponseEntity.internalServerError().body(new Object());
        }
    }

    @PostMapping("/upload-note")
    public void handlenoteUpload(@RequestBody() String data, Authentication authentication) {

        try {
            Gson gson = new Gson();
            JSONNoteData noteForm = gson.fromJson(data, JSONNoteData.class);
            if (noteForm.noteTitle == null || noteForm.noteDescription == null) {
                return;
            }

            //TODO get userID
            Integer userId = 123;
            if (noteForm.noteId != null && !noteForm.noteId.trim().isEmpty()) {
                this.noteService.updateNote(Integer.valueOf(noteForm.noteId), noteForm.noteTitle, noteForm.noteDescription, userId);
            } else {
                this.noteService.insertNote(noteForm.noteTitle, noteForm.noteDescription, userId);
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @DeleteMapping("/delete-note/{noteId}")
    public void deletenote(@PathVariable("noteId") Integer noteId, Authentication authentication) {
        try {
            NoteForm noteToDelete = this.noteService.getNote(noteId);
            if (noteToDelete != null) {
                // TODO check if user is really authorized (is his/her note) authentication.getCredentials()
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
