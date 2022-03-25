package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    @FindBy(id = "inputUsername")
    public WebElement inputUsername;

    @FindBy(id = "inputPassword")
    public WebElement inputPassword;

    @FindBy(id = "submitLogin")
    public WebElement submitLogin;

    @FindBy(id = "hrefSignUp")
    public WebElement hrefSignUp;

    @FindBy(id = "messageLogout")
    public WebElement messageLogout;

    @FindBy(id = "messageError")
    public WebElement messageError;

    public LoginPage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    public void login(String name, String password) {
        inputUsername.sendKeys(name);
        inputPassword.sendKeys(password);
        submitLogin.click();
    }
}
