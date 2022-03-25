package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignUpPage {

    @FindBy(id = "inputFirstName")
    public WebElement inputFirstName;

    @FindBy(id = "inputLastName")
    public WebElement inputLastName;

    @FindBy(id = "inputUsername")
    public WebElement inputUsername;

    @FindBy(id = "inputPassword")
    public WebElement inputPassword;

    @FindBy(id = "submitLogin")
    public WebElement submitLogin;

    @FindBy(id = "hrefLogin")
    public WebElement hrefLogin;

    @FindBy(id = "messageSignUpSuccess")
    public WebElement messageSignUpSuccess;

    @FindBy(id = "messageSignUpError")
    public WebElement messageSignUpError;

    public SignUpPage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    public void SignUp(String firstName, String lastName, String userName, String password) {
        inputFirstName.sendKeys(firstName);
        inputLastName.sendKeys(lastName);
        inputUsername.sendKeys(userName);
        inputPassword.sendKeys(password);

        submitLogin.click();
    }
}
