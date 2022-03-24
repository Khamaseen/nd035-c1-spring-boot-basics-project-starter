package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mappers.formmappers.NoteFormMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.forms.groupedform.NoteForm;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NoteService {

    private final NoteFormMapper noteFormMapper;
    private final NoteMapper noteMapper;

    public NoteService(NoteFormMapper noteFormMapper, NoteMapper noteMapper) {
        this.noteFormMapper = noteFormMapper;
        this.noteMapper = noteMapper;
    }

    public List<NoteForm> getNotes(Integer userId) {
        List<Note> list;
        try {
            list = this.noteMapper.getNotes();
        } catch (Error e) {
            list = new ArrayList<Note>();
        }
        return this.noteFormMapper.mapNotesToNoteForms(list);
    }

    public NoteForm getNote(Integer noteId) {
        Note note = this.noteMapper.getNote(noteId);

        if (note != null) {
            return this.noteFormMapper.mapNoteToNoteForm(note);
        }

        return null;
    }

    public int updateNote(Integer noteId, String title, String description, Integer userId) {
        Note newNote = new Note(
                noteId,
                title,
                description,
                userId
        );

        return this.noteMapper.update(newNote);
    }

    public int insertNote(String title, String description, Integer userId) {
        Note newNote = new Note(
                title,
                description,
                userId
        );

        return this.noteMapper.insert(newNote);
    }

    public int deleteNote(Integer noteId) {
        return this.noteMapper.delete(noteId);
    }
}
