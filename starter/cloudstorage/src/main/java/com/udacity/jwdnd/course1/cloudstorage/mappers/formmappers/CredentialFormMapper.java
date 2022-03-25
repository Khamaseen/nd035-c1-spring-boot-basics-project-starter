package com.udacity.jwdnd.course1.cloudstorage.mappers.formmappers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.forms.groupedform.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CredentialFormMapper {

    private EncryptionService encryptionService;

    public CredentialFormMapper(EncryptionService encryptionService) {
        this.encryptionService = encryptionService;
    }

    public List<CredentialForm> mapCredentialsToCredentialForms(List<Credential> credentials) {
        return credentials.stream().map(this::mapCredentialToCredentialForm).toList();
    }

    public CredentialForm mapCredentialToCredentialForm(Credential credential) {
        return new CredentialForm(
                credential.getCredentialId(),
                credential.getUrl(),
                credential.getUsername(),
                credential.getPassword(),
                this.getDecryptedPassword(credential.getPassword(), credential.getKey()),
                credential.getUserId()
                );
    }

    private String getDecryptedPassword(String password, byte[] key) {
        return this.encryptionService.decryptValue(password, key);
    }

}
