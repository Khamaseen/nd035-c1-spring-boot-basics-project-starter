package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.formmappers.NoteFormMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.forms.groupedform.NoteForm;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NoteService {

    private NoteFormMapper noteFormMapper;

    public NoteService(NoteFormMapper noteFormMapper) {
        this.noteFormMapper = noteFormMapper;
    }

    // TODO returns mock, remove this
    public List<NoteForm> getNotes(Integer userId) {
        List<Note> list = new ArrayList<Note>();
        list.add(new Note("title", "description", 123));
        return this.noteFormMapper.mapNotesToNoteForms(list);
    }
}
