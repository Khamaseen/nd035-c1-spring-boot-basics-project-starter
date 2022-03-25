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
    private final EncryptionService encryptionService;

    public CredentialService(
            CredentialFormMapper credentialFormMapper,
            CredentialMapper credentialMapper,
            EncryptionService encryptionService
            ) {
        this.credentialFormMapper = credentialFormMapper;
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

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
        byte[] key = this.encryptionService.generateKey();
        Credential newCredential = new Credential(
                credentialId,
                url,
                username,
                key,
                this.encryptionService.encryptValue(password, key),
                userId
        );

        return this.credentialMapper.update(newCredential);
    }

    public int insertCredential(String url, String username, String password, Integer userId) {
        byte[] key = this.encryptionService.generateKey();
        Credential newCredential = new Credential(
                url,
                username,
                key,
                this.encryptionService.encryptValue(password, key),
                userId
        );

        return this.credentialMapper.insert(newCredential);
    }

    public int deleteCredential(Integer credentialId) {
        return this.credentialMapper.delete(credentialId);
    }
}
