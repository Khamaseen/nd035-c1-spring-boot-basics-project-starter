package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class HomePage {

    // Logout
    @FindBy(id = "logoutForm")
    private WebElement logoutButton;

    // Notes
    @FindBy(id = "nav-notes-tab")
    public WebElement notesTab;

    @FindBy(id = "addNewNoteButton")
    public WebElement addNewNoteButton;

    @FindBy(id = "note-title")
    public WebElement modalNoteTitle;

    @FindBy(id = "note-description")
    public WebElement modalNoteDescription;

    @FindBy(id = "modalSubmitButton")
    public WebElement modalSubmitNoteButton;

    // Credentials
    @FindBy(id = "nav-credentials-tab")
    public WebElement credentialsTab;

    @FindBy(id = "addNewCredentialButton")
    public WebElement addNewCredentialButton;

    @FindBy(id = "credential-url")
    public WebElement modalCredentialUrl;

    @FindBy(id = "credential-username")
    public WebElement modalCredentialUsername;

    @FindBy(id = "credential-password")
    public WebElement modalCredentialPassword;

    @FindBy(id = "modalSubmitCredential")
    public WebElement modalSubmitCredentialButton;


    private WebDriver webDriver;

    public HomePage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    // Tests for authorization
    public void logOut() {
        logoutButton.submit();
    }

    public void goToNotesTab() throws InterruptedException {
        notesTab.click();
        Thread.sleep(500);
    }

    public void gotToCredentialsTab() throws InterruptedException {
        credentialsTab.click();
        Thread.sleep(500);
    }

    // Tests for notes
    public void createNote(String title, String description) throws InterruptedException {
        this.goToNotesTab();
        addNewNoteButton.click();
        Thread.sleep(500);
        modalNoteTitle.sendKeys(title);
        Thread.sleep(500);
        modalNoteDescription.sendKeys(description);
        Thread.sleep(500);
        modalSubmitNoteButton.click();
        Thread.sleep(2000);
    }

    public boolean doesNoteExist(String title, String description) throws InterruptedException {
        this.goToNotesTab();

        List<WebElement> rows = this.webDriver.findElements(By.tagName("tr"));
        Thread.sleep(500);
        for (WebElement row : rows) {
            List<WebElement> cellsTh = row.findElements(By.tagName("th"));
            List<WebElement> cellsTd = row.findElements(By.tagName("td"));
            if (cellsTh.get(0).getText().equals(title)) {
                if (cellsTd.get(1).getText().equals(description)) {
                    return true;
                }
            }
        }

        return false;
    }

    public WebElement findEditButtonForNote(String title, String description) throws InterruptedException {
        this.goToNotesTab();

        List<WebElement> rows = this.webDriver.findElements(By.tagName("tr"));
        Thread.sleep(500);
        for (WebElement row : rows) {
            List<WebElement> cellsTh = row.findElements(By.tagName("th"));
            List<WebElement> cellsTd = row.findElements(By.tagName("td"));
            if (cellsTh.get(0).getText().equals(title)) {
                if (cellsTd.get(1).getText().equals(description)) {
                    return row.findElements(By.className("btn-success")).get(0);
                }
            }
        }

        return null;
    }

    public WebElement findDeleteButtonForNote(String title, String description) throws InterruptedException {
        this.goToNotesTab();

        List<WebElement> rows = this.webDriver.findElements(By.tagName("tr"));
        Thread.sleep(500);
        for (WebElement row : rows) {
            List<WebElement> cellsTh = row.findElements(By.tagName("th"));
            List<WebElement> cellsTd = row.findElements(By.tagName("td"));
            if (cellsTh.get(0).getText().equals(title)) {
                if (cellsTd.get(1).getText().equals(description)) {
                    return row.findElements(By.className("btn-danger")).get(0);
                }
            }
        }

        return null;
    }

    public void editNote(String previousTitle, String previousDescription, String newTitle, String newDescription) throws InterruptedException {
        WebElement editButton = this.findEditButtonForNote(previousTitle, previousDescription);
        editButton.click();
        Thread.sleep(500);

        modalNoteTitle.clear();
        modalNoteTitle.sendKeys(newTitle);
        Thread.sleep(500);

        modalNoteDescription.clear();
        modalNoteDescription.sendKeys(newDescription);
        Thread.sleep(500);

        modalSubmitNoteButton.click();
        Thread.sleep(2000);
    }

    public void deleteNote(String title, String description) throws InterruptedException {
        WebElement deleteButtonForNote = this.findDeleteButtonForNote(title, description);
        deleteButtonForNote.click();
        Thread.sleep(500);
    }

    // Tests for credentials
    public void createCredential(String url, String username, String password) throws InterruptedException {
        this.gotToCredentialsTab();

        addNewCredentialButton.click();
        Thread.sleep(500);
        modalCredentialUrl.sendKeys(url);
        Thread.sleep(500);
        modalCredentialUsername.sendKeys(username);
        Thread.sleep(500);
        modalCredentialPassword.sendKeys(password);
        Thread.sleep(500);
        modalSubmitCredentialButton.click();
        Thread.sleep(2000);
    }

    public boolean doesCredentialExistWithEncryptedPassword(String url, String username, String password) throws InterruptedException {
        this.gotToCredentialsTab();

        List<WebElement> rows = this.webDriver.findElements(By.tagName("tr"));
        Thread.sleep(500);
        for (WebElement row : rows) {
            List<WebElement> cellsTh = row.findElements(By.tagName("th"));
            List<WebElement> cellsTd = row.findElements(By.tagName("td"));
            if (cellsTh.get(0).getText().equals(url)) {
                if (cellsTd.get(1).getText().equals(username) && !cellsTd.get(2).getText().equals(password)) {
                    return true;
                }
            }
        }

        return false;
    }

    public WebElement findCredentialRow(String url, String username) throws InterruptedException {
        this.gotToCredentialsTab();

        List<WebElement> rows = this.webDriver.findElements(By.tagName("tr"));
        Thread.sleep(500);
        for (WebElement row : rows) {
            List<WebElement> cellsTh = row.findElements(By.tagName("th"));
            List<WebElement> cellsTd = row.findElements(By.tagName("td"));
            if (cellsTh.get(0).getText().equals(url)) {
                if (cellsTd.get(1).getText().equals(username)) {
                    return row;
                }
            }
        }

        return null;
    }

    public boolean isPasswordCorrect(String url, String username, String password) throws InterruptedException {
        this.gotToCredentialsTab();

        WebElement row = this.findCredentialRow(url, username);
        WebElement btnEdit = row.findElements(By.className("btn-success")).get(0);

        btnEdit.click();

        return this.modalCredentialPassword.getAttribute("value").equals(password);
    }


    public void editCredential(String url, String username, String newUrl, String newUsername, String newPassword) throws InterruptedException {
        this.gotToCredentialsTab();

        WebElement row = this.findCredentialRow(url, username);
        WebElement btnEdit = row.findElements(By.className("btn-success")).get(0);

        btnEdit.click();

        modalCredentialUrl.clear();
        modalCredentialUrl.sendKeys(newUrl);
        Thread.sleep(500);

        modalCredentialUsername.clear();
        modalCredentialUsername.sendKeys(newUsername);
        Thread.sleep(500);

        modalCredentialPassword.clear();
        modalCredentialPassword.sendKeys(newPassword);
        Thread.sleep(500);

        modalSubmitCredentialButton.click();
        Thread.sleep(2000);
    }

    public void deleteCredential(String url, String username) throws InterruptedException {
        this.gotToCredentialsTab();

        WebElement row = this.findCredentialRow(url, username);
        WebElement btnDelete = row.findElements(By.className("btn-danger")).get(0);

        btnDelete.click();
    }
}
