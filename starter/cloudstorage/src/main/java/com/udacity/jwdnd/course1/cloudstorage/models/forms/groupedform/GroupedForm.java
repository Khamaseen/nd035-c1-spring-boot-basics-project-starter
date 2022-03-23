package com.udacity.jwdnd.course1.cloudstorage.models.forms.groupedform;

import java.util.ArrayList;
import java.util.List;

public class GroupedForm {
    private List<FileForm> fileForms;
    private List<NoteForm> noteForms;
    private List<CredentialForm> credentialForms;

    public GroupedForm() {
        this.fileForms = new ArrayList<FileForm>();
        this.noteForms = new ArrayList<NoteForm>();
        this.credentialForms = new ArrayList<CredentialForm>();
    }

    public GroupedForm(List<FileForm> fileForms, List<NoteForm> noteForms, List<CredentialForm> credentialForms) {
        this.fileForms = fileForms;
        this.noteForms = noteForms;
        this.credentialForms = credentialForms;
    }

    public List<FileForm> getFileForms() {
        return fileForms;
    }

    public void setFileForms(List<FileForm> fileForms) {
        this.fileForms = fileForms;
    }

    public List<NoteForm> getNoteForms() {
        return noteForms;
    }

    public void setNoteForms(List<NoteForm> noteForms) {
        this.noteForms = noteForms;
    }

    public List<CredentialForm> getCredentialForms() {
        return credentialForms;
    }

    public void setCredentialForms(List<CredentialForm> credentialForms) {
        this.credentialForms = credentialForms;
    }
}
