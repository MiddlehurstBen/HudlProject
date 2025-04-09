package com.asos.ip;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.Duration;

public class WebpageController {

    public WebDriver getDriver() {
        return driver;
    }

    WebDriver driver;

    WebpageController(WebDriver driver) {
        this.driver = driver;
    }

    public void waitForPageToLoadForXSeconds(int seconds) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(seconds));
    }


    public void scrollToFindElement(By locator) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(locator));
    }

    public void clickElement(By locator) {
        driver.findElement(locator).click();
    }

    public void enterPassword(String password) {

        WebElement textBox = driver.findElement(By.name("password"));

        textBox.sendKeys(password);
    }


    public void enterEmailAddressAndContinue(String emailAddress) {
        WebElement textBox = driver.findElement(By.name("username"));

        textBox.sendKeys(emailAddress);

        driver.findElement(By.name("action")).click();
    }

    public void navigateToHudlLoginPage() {

        navigateToHudlWebsite();

        driver.findElement(By.linkText("Log in")).click();

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));

        driver.findElement(By.className("subnav__item")).click();

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));

        driver.manage().window().maximize();
    }

    private void navigateToHudlWebsite() {

        driver.get("https://www.hudl.com");

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(3000));
    }

    public void closeBrowser() {
        driver.quit();
    }
}
