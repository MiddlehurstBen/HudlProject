package com.asos.ip;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

import java.util.Map;
import java.util.stream.Stream;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

class HudlWebsiteLoginTests {

    Map<String, String> loginDetails = ConfigLoader.getLoginDetailsAsMap();
    WebpageController webpageController;

    static Stream<WebDriver> webDriverStream() {
        return Stream.of(
                new ChromeDriver(),
                new EdgeDriver()
                );
    }

    @Test
    @DisplayName("Testing Logging into Website Happy Path")
    void testingLoggingIntoWebsiteHappyPath() {
        webpageController = new WebpageController(new ChromeDriver());

        webpageController.navigateToHudlLoginPage();

        webpageController.enterEmailAddressAndContinue(loginDetails.get("email"));
        webpageController.enterPassword(loginDetails.get("password"));
        webpageController.scrollToFindElement(By.name("action"));
        webpageController.clickElement(By.name("action"));

        webpageController.waitForPageToLoadForXSeconds(5);

        assertEquals("Home - Hudl", webpageController.getDriver().getTitle());
    }

    @ParameterizedTest
    @MethodSource("webDriverStream")
    @DisplayName("Testing Logging into Website Happy Path With Different Browsers")
    void testingLoggingIntoWebsiteHappyPathDifferentBrowsers(WebDriver webDriver) {

        webpageController = new WebpageController(webDriver);

        webpageController.navigateToHudlLoginPage();

        webpageController.enterEmailAddressAndContinue(loginDetails.get("email"));
        webpageController.enterPassword(loginDetails.get("password"));
        webpageController.scrollToFindElement(By.name("action"));
        webpageController.clickElement(By.name("action"));

        webpageController.waitForPageToLoadForXSeconds(1);

        assertEquals("Home - Hudl", webpageController.getDriver().getTitle());
    }

    @Test
    @DisplayName("Testing entering incorrect password")
    void testingEnteringIncorrectPassword() {

        webpageController = new WebpageController(new ChromeDriver());

        webpageController.navigateToHudlLoginPage();

        webpageController.enterEmailAddressAndContinue(loginDetails.get("email"));
        webpageController.enterPassword("incorrectPassword");
        webpageController.scrollToFindElement(By.name("action"));
        webpageController.clickElement(By.name("action"));

        assertTrue(webpageController.getDriver().findElement(By.id("error-element-password")).isDisplayed());
    }

    @Test
    @DisplayName("Testing Incorrect Password Limit")
    void testingIncorrectPasswordLimit() throws InterruptedException {

        webpageController = new WebpageController(new ChromeDriver());

        webpageController.navigateToHudlLoginPage();

        webpageController.enterEmailAddressAndContinue(loginDetails.get("email"));


        while (webpageController.getDriver().getTitle().equals("Log In")) {
            try {
                webpageController.enterPassword("incorrectPassword");
                webpageController.scrollToFindElement(By.name("action"));
                webpageController.clickElement(By.name("action"));

                webpageController.waitForPageToLoadForXSeconds(5);

                webpageController.getDriver().findElement(By.name("action")).isDisplayed();
            } catch (NoSuchElementException | StaleElementReferenceException ignored) {
            }
        }

        assertTrue(webpageController.getDriver().findElement(By.className("error-header")).isDisplayed());
    }

    @Test
    @DisplayName("Testing entering incorrect email address")
    void testingEnteringIncorrectEmailAddress() {

        webpageController = new WebpageController(new ChromeDriver());

        webpageController.navigateToHudlLoginPage();

        webpageController.enterEmailAddressAndContinue("IncorrectEmailAddress@error.com");
        webpageController.enterPassword(loginDetails.get("password"));
        webpageController.scrollToFindElement(By.name("action"));
        webpageController.clickElement(By.name("action"));

        assertTrue(webpageController.getDriver().findElement(By.id("error-element-password")).isDisplayed());
    }

    @Test
    @DisplayName("Testing trying to log in with an invalid email address format")
    void testingTryingToLogInWithAnInvalidEmailAddressFormat() {

        webpageController = new WebpageController(new ChromeDriver());

        webpageController.navigateToHudlLoginPage();
        webpageController.enterEmailAddressAndContinue("InvalidEmailAddressFormat");

        assertTrue(webpageController.getDriver().findElement(By.id("error-element-username")).isDisplayed());
    }

    @Test
    @DisplayName("Testing trying to log in with an invalid email address attempt limit")
    void testingTryingToLogInWithAnInvalidEmailAddressAttemptLimit() {

        webpageController = new WebpageController(new ChromeDriver());

        webpageController.navigateToHudlLoginPage();
        webpageController.enterEmailAddressAndContinue("InvalidEmailAddressFormat");

        webpageController.waitForPageToLoadForXSeconds(2);

        while (webpageController.getDriver().getTitle().equals("Log In")) {
            try {
                webpageController.clickElement(By.name("action"));

                webpageController.getDriver().findElement(By.name("action")).isDisplayed();

            } catch (NoSuchElementException | StaleElementReferenceException ignored) {
            }
        }

        assertTrue(webpageController.getDriver().findElement(By.className("error-header")).isDisplayed());
    }

    @Test
    @DisplayName("Testing edit email address button")
    void testingEditEmailAddressButton() {

        webpageController = new WebpageController(new ChromeDriver());

        webpageController.navigateToHudlLoginPage();
        webpageController.enterEmailAddressAndContinue(loginDetails.get("email"));
        webpageController.clickElement(By.linkText("Edit"));

        WebElement emailAddressTextBox = webpageController.getDriver().findElement(By.name("username"));

        assertTrue(emailAddressTextBox.isDisplayed());
    }

    @Test
    @DisplayName("Testing show password toggle")
    void testingShowPasswordToggle() {
        String testPassword = "TestPassword";

        webpageController = new WebpageController(new ChromeDriver());

        webpageController.navigateToHudlLoginPage();
        webpageController.enterEmailAddressAndContinue(loginDetails.get("email"));
        webpageController.enterPassword(testPassword);

        webpageController.getDriver().findElement(By.className("ulp-button-icon")).click();

        WebElement passwordTextBox = webpageController.getDriver().findElement(By.name("password"));

        assertEquals(testPassword, passwordTextBox.getAttribute("value"));
    }

    @Test
    @DisplayName("Testing forgot password")
    void testingForgotPassword() {

        webpageController = new WebpageController(new ChromeDriver());

        webpageController.navigateToHudlLoginPage();
        webpageController.enterEmailAddressAndContinue(loginDetails.get("email"));
        webpageController.scrollToFindElement(By.linkText("Forgot Password"));
        webpageController.clickElement(By.linkText("Forgot Password"));

        assertEquals("Reset Password", webpageController.getDriver().getTitle());
    }

    @Test
    @DisplayName("Testing create account")
    void testingCreateAccount() {

        webpageController = new WebpageController(new ChromeDriver());

        webpageController.navigateToHudlLoginPage();
        webpageController.scrollToFindElement(By.linkText("Create Account"));
        webpageController.clickElement(By.linkText("Create Account"));

        assertEquals("Create Account", webpageController.getDriver().getTitle());
    }

    @AfterEach
    void tearDown() {
        webpageController.getDriver().manage().deleteAllCookies();
        webpageController.closeBrowser();
    }
}
