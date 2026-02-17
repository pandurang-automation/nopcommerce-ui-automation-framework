package com.ultron.framework.applications.saucedemo.pages;

import com.ultron.framework.base.BasePage;
import com.ultron.framework.utils.LocatorUtil;
import org.openqa.selenium.By;

public class InventoryPage extends BasePage {

    private static final String PAGE = "InventoryPage";

    private final By inventoryContainer =
            LocatorUtil.getLocator(PAGE, "inventoryContainer");

    public boolean isInventoryVisible() {
        return isDisplayed(inventoryContainer);
    }

    private final By menuButton =
            LocatorUtil.getLocator(PAGE, "menuButton");

    private final By logoutLink =
            LocatorUtil.getLocator(PAGE, "logoutLink");

    public void logout() {
        click(menuButton);
        click(logoutLink);
    }

}
