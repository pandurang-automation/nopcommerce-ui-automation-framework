package com.ultron.framework.applications.saucedemo.pages;

import com.ultron.framework.base.BasePage;
import com.ultron.framework.config.ConfigReader;
import com.ultron.framework.utils.LocatorUtil;
import org.openqa.selenium.By;

public class LoginPage extends BasePage {

    private static final String PAGE = "LoginPage";

    private final By username = LocatorUtil.getLocator(PAGE, "username");
    private final By password = LocatorUtil.getLocator(PAGE, "password");
    private final By loginButton = LocatorUtil.getLocator(PAGE, "loginButton");
    private final By errorMessage = LocatorUtil.getLocator(PAGE, "errorMessage");

    public void navigateToLoginPage() {
        getDriver().get(ConfigReader.getInstance().getBaseUrl());
    }

    public void login(String user, String pass) {
        type(username, user);
        type(password, pass);
        click(loginButton);
    }

    public String getErrorMessage() {
        return getText(errorMessage);
    }
    public boolean isLoginButtonVisible() {
        return isDisplayed(loginButton);
    }

}
