package com.nopcommerce.framework.pages.frontend;

import com.nopcommerce.framework.config.ConfigReader;
import com.nopcommerce.framework.pages.BasePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * HomePage
 *
 * Represents the public landing page of nopCommerce.
 *
 * Responsibilities:
 * - Navigate to frontend URL
 * - Validate page-level elements
 * - Provide homepage-specific interactions
 *
 * Extends BasePage to inherit:
 * - Driver access
 * - Click, type, getText methods
 * - Centralized wait handling
 */
public class HomePage extends BasePage {

    private static final Logger logger = LogManager.getLogger(HomePage.class);

    /**
     * Navigates to frontend URL from configuration.
     */
    public void navigateToHomePage() {
        logger.info("Navigating to frontend homepage");
        String url = ConfigReader.getInstance().getFrontendUrl();
        getDriver().get(url);
    }

    /**
     * Returns the visible page title text.
     */
    public String getPageTitleText() {
        return getDriver().getTitle();
    }
}
