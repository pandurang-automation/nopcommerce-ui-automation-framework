package com.ultron.tests.saucedemo;

import com.ultron.framework.applications.saucedemo.pages.InventoryPage;
import com.ultron.framework.applications.saucedemo.pages.LoginPage;
import com.ultron.framework.config.ConfigReader;
import com.ultron.tests.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LogoutTest extends BaseTest {

    @Test
    public void verifyLogout() {

        LoginPage loginPage = new LoginPage();
        loginPage.navigateToLoginPage();
        loginPage.login(
                ConfigReader.getInstance().getUsername(),
                ConfigReader.getInstance().getPassword()
        );

        InventoryPage inventoryPage = new InventoryPage();
        inventoryPage.logout();

        Assert.assertTrue(
                loginPage.isLoginButtonVisible(),
                "Logout failed — Login page not visible"
        );
    }
}
