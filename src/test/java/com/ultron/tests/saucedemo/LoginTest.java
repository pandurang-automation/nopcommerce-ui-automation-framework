package com.ultron.tests.saucedemo;

import com.ultron.framework.applications.saucedemo.pages.InventoryPage;
import com.ultron.framework.applications.saucedemo.pages.LoginPage;
import com.ultron.framework.data.JsonDataReader;
import com.ultron.tests.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @DataProvider(name = "validLoginData")
    public Object[][] validLoginData() {
        return JsonDataReader.getLoginData("validLogin");
    }

    @Test(dataProvider = "validLoginData")
    public void verifyValidLogin(String username, String password) {

        LoginPage loginPage = new LoginPage();
        loginPage.navigateToLoginPage();
        loginPage.login(username, password);

        InventoryPage inventoryPage = new InventoryPage();

        Assert.assertTrue(inventoryPage.isInventoryVisible(), "Login failed — Inventory page not visible");
    }

    @DataProvider(name = "invalidLoginData")
    public Object[][] invalidLoginData() {
        return JsonDataReader.getLoginData("invalidLogin");
    }

    @Test(dataProvider = "invalidLoginData")
    public void verifyInvalidLogin(String username, String password) {

        LoginPage loginPage = new LoginPage();
        loginPage.navigateToLoginPage();
        loginPage.login(username, password);

        Assert.assertTrue(loginPage.getErrorMessage().contains("Epic sadface"), "Expected error message not displayed");
    }

}
