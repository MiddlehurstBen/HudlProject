package com.asos.ip;

import junit.framework.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Map;

public class HudlWebsiteLoginShould {

    Map<String, String> loginDetails = ConfigLoader.getLoginDetailsAsMap();

    @Test
    @DisplayName("Testing Logging into Website Happy Path")
    void testingLoggingIntoWebsiteHappyPath() {

        WebpageController webpageController = new WebpageController(new ChromeDriver());

        webpageController.navigateToHudlLoginPage();

        webpageController.enterEmailAddress(loginDetails.get("email"));
        webpageController.enterPassword(loginDetails.get("password"));
        webpageController.scrollToFindElement("action");
        webpageController.clickElement("action");

        Assert.assertEquals(webpageController.getDriver().getCurrentUrl(), "https://www.hudl.com/home");
    }
}
