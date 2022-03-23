package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.formmappers.CredentialFormMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.forms.groupedform.CredentialForm;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CredentialService {

    private final CredentialFormMapper credentialFormMapper;

    public CredentialService(CredentialFormMapper credentialFormMapper) {
        this.credentialFormMapper = credentialFormMapper;
    }

    // TODO returns mock, remove this
    public List<CredentialForm> getCredentialsForUser(Integer userId) {
        List<Credential> list = new ArrayList<Credential>();
        list.add(new Credential(123, "url:here", "username:dennis", "xaioadfajdfa"));
        return this.credentialFormMapper.mapCredentialsToCredentialForms(list);
    }
}
