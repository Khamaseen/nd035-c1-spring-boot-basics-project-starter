package com.udacity.jwdnd.course1.cloudstorage.mappers.formmappers;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.forms.groupedform.NoteForm;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NoteFormMapper {

    public List<NoteForm> mapNotesToNoteForms(List<Note> notes) {
        return notes.stream().map(this::mapNoteToNoteForm).toList();
    }

    public NoteForm mapNoteToNoteForm(Note note) {
        return new NoteForm(
                note.getTitle(),
                note.getDescription(),
                note.getUserId()
        );
    }
}
