package com.udacity.jwdnd.course1.cloudstorage.models;

public class LoginForm {
    private boolean providedInvalidCredentials;
    private boolean redirectedAfterLogout;

    public boolean isProvidedInvalidCredentials() {
        return providedInvalidCredentials;
    }

    public void setProvidedInvalidCredentials(boolean providedInvalidCredentials) {
        this.providedInvalidCredentials = providedInvalidCredentials;
    }

    public boolean isRedirectedAfterLogout() {
        return redirectedAfterLogout;
    }

    public void setRedirectedAfterLogout(boolean redirectedAfterLogout) {
        this.redirectedAfterLogout = redirectedAfterLogout;
    }
}
