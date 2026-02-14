package com.nopcommerce.tests;

import com.nopcommerce.tests.base.BaseTest;
import com.nopcommerce.framework.pages.frontend.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * HomePageTest

 * Validates basic functionality of the frontend homepage.

 * This test ensures:
 * - Driver lifecycle works
 * - Page navigation works
 * - WaitUtils works
 * - Page Object Model is functioning correctly
 */
public class HomePageTest extends BaseTest {

    @Test(enabled = true)
    public void verifyHomePageTitle() {

        HomePage homePage = new HomePage();

        // Navigate to homepage
        homePage.navigateToHomePage();

        // Validate page title
        String actualTitle = homePage.getPageTitleText();
        Assert.assertEquals(actualTitle, "nopCommerce demo store. Home page title");
    }

    @Test(enabled = true)
    public void verifyHomePageTitleSecond() {

        HomePage homePage = new HomePage();

        homePage.navigateToHomePage();

        String actualTitle = homePage.getPageTitleText();

        Assert.assertEquals(actualTitle, "nopCommerce demo store. Home page title");
    }

}
