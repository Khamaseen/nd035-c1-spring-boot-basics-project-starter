package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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

    @FindBy(id = "modalSubmitButton")
    public WebElement modalSubmitButton;

    @FindBy(id = "note-title")
    public WebElement modalNoteTitle;

    @FindBy(id = "note-description")
    public WebElement modalNoteDescription;

    // Credentials TODO
    @FindBy(id = "notesTab")
    public WebElement credentialsTab;
    @FindBy(id = "credentialsList")
    public WebElement credentialsList;
    @FindBy(id = "addNote")
    public WebElement addCredential;
    @FindBy(id = "editNote") // Get from credentialsList
    public WebElement editCredential;
    @FindBy(id = "deleteNote") // Get from credentialsList
    public WebElement deleteCredendial;
    @FindBy(id = "modalEdit")
    public WebElement modalEditCredential;
    @FindBy(id = "modalCancel")
    public WebElement modalCancelCredential;
    @FindBy(id = "modalCredentialUrl")
    public WebElement modalCredentialUrl;
    @FindBy(id = "modalCredentialUsername")
    public WebElement modalCredentialUsername;
    @FindBy(id = "modalCredentialPassword")
    public WebElement modalCredentialPassword;

    private WebDriver webDriver;
    public HomePage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    // Tests for authorization
    public void logOut() {
        logoutButton.submit();
    }

    // Tests for notes
    public void createNote(String title, String description) throws InterruptedException {
        notesTab.click();
        Thread.sleep(500);
        addNewNoteButton.click();
        Thread.sleep(500);
        modalNoteTitle.sendKeys(title);
        Thread.sleep(500);
        modalNoteDescription.sendKeys(description);

        JavascriptExecutor jse = (JavascriptExecutor) this.webDriver;
        jse.executeScript("document.getElementById('modalSubmitButton').focus();");

        Thread.sleep(6500);
        modalSubmitButton.click();
        Thread.sleep(6500);
    }

    public boolean findNote(String title, String description) throws InterruptedException {
        Thread.sleep(500);
        notesTab.click();
        Thread.sleep(500);

        List<WebElement> rows = this.webDriver.findElements(By.tagName("tr"));
        Thread.sleep(500);
        for (WebElement row : rows)
        {
            List<WebElement> cellsTh = row.findElements(By.tagName("th"));
            List<WebElement> cellsTd = row.findElements(By.tagName("td"));
            if (cellsTh.get(0).getText().equals(title))
            {
                if (cellsTd.get(1).getText().equals(description)) {
                    return true;
                }
            }
        }

        return false;
    }

    public void editNote(String previousTitle, String previousDescription, String newTitle, String newDescription) {
        //
    }

    public void deleteNote(String title, String description) {
        //
    }

    // Tests for credentials
    public void createCredential(String url, String username, String password) {
        //
    }

    public boolean findCredential(String url, String username) {
        return true; //
    }

    public void editCredential(String url, String username, String newUrl, String newUsername, String newPassword) {
        //
    }

    public void deleteCredential(String url, String username) {
        //
    }
}
