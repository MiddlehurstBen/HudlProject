package com.asos.ip;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;


public class WebpageController {

    public WebDriver getDriver() {
        return driver;
    }

    WebDriver driver;
    Map<String, String> loginDetails = new HashMap<>();

    WebpageController(WebDriver driver) {
        this.driver = driver;
    }

    public void scrollToFindElement(String elementName) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.name(elementName)));
    }

    public void clickElement(String elementName) {
        driver.findElement(By.name(elementName)).click();
    }

    public void enterPassword(String password) {
        driver.findElement(By.name("action")).click();

        WebElement textBox = driver.findElement(By.name("password"));

        textBox.sendKeys(password);
    }


    public void enterEmailAddress(String emailAddress) {
        WebElement textBox = driver.findElement(By.name("username"));

        textBox.sendKeys(emailAddress);
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
