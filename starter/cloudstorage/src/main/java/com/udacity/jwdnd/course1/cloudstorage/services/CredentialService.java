package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mappers.formmappers.CredentialFormMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.forms.groupedform.CredentialForm;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CredentialService {

    private final CredentialFormMapper credentialFormMapper;
    private final CredentialMapper credentialMapper;

    public CredentialService(CredentialFormMapper credentialFormMapper, CredentialMapper credentialMapper) {
        this.credentialFormMapper = credentialFormMapper;
        this.credentialMapper = credentialMapper;
    }

    // TODO returns mock, remove this
    public List<CredentialForm> getCredentialsForUser(Integer userId) {
        List<Credential> list;
        try {
            list = this.credentialMapper.getCredentials(userId);
        } catch (Error e) {
            list = new ArrayList<Credential>();
        }

        return this.credentialFormMapper.mapCredentialsToCredentialForms(list);
    }

    public CredentialForm getCredential(Integer credentialId) {
        Credential credential = this.credentialMapper.getCredential(credentialId);

        if (credential != null) {
            return this.credentialFormMapper.mapCredentialToCredentialForm(credential);
        }

        return null;
    }

    public int updateCredential(Integer credentialId, String url, String username, String password, Integer userId) {
        Credential newCredential = new Credential(
                credentialId,
                url,
                username,
                password,
                userId
        );

        return this.credentialMapper.update(newCredential);
    }

    public int insertCredential(String url, String username, String password, Integer userId) {
        String fakeKey = "key"; // TODO key should be used for encryption & decryption
        Credential newCredential = new Credential(
                url,
                username,
                fakeKey,
                password,
                userId
        );

        return this.credentialMapper.insert(newCredential);
    }

    public int deleteCredential(Integer credentialId) {
        return this.credentialMapper.delete(credentialId);
    }
}
