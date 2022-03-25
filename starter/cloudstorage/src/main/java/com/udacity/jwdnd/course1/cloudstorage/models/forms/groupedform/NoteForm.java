package com.udacity.jwdnd.course1.cloudstorage.models.forms.groupedform;

public class NoteForm {
    private Integer noteId;
    private String title;
    private String description;
    private Integer userId;

    public NoteForm(Integer noteId, String title, String description, Integer userId) {
        this.noteId = noteId;
        this.title = title;
        this.description = description;
        this.userId = userId;
    }

    public Integer getNoteId() {
        return noteId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Integer getUserId() {
        return userId;
    }
}
