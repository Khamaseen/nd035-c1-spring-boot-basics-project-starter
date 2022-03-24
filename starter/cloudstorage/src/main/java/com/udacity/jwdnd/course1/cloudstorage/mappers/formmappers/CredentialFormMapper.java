package com.udacity.jwdnd.course1.cloudstorage.mappers.formmappers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.forms.groupedform.CredentialForm;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CredentialFormMapper {

    public List<CredentialForm> mapCredentialsToCredentialForms(List<Credential> credentials) {
        return credentials.stream().map(this::mapCredentialToCredentialForm).toList();
    }

    public CredentialForm mapCredentialToCredentialForm(Credential credential) {
        return new CredentialForm(
                credential.getCredentialId(),
                credential.getUrl(),
                credential.getUsername(),
                credential.getPassword(),
                credential.getUserId()
                );
    }
}
