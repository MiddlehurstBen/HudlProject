package com.asos.ip;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class WebpageController {

    WebDriver driver;

    WebpageController(WebDriver driver) {
        this.driver = driver;
    }

    private void scrollToFindElement(String elementName) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.name(elementName)));
    }

    private void enterPassword(String password) {
        driver.findElement(By.name("action")).click();

        WebElement textBox = driver.findElement(By.name("password"));

        textBox.sendKeys(password);
    }

    public Map<String, String> getLoginDetails() {
        Map<String, String> loginDetails = new HashMap<>();

        Properties properties = new Properties();
        try (BufferedReader reader = new BufferedReader(new java.io.FileReader("loginDetails.config"))) {

            properties.load(reader);

            loginDetails.put("email", properties.getProperty("login"));
            loginDetails.put("password", properties.getProperty("password"));

        } catch (IOException e) {
            e.printStackTrace();
        }

        return loginDetails;
    }

    private void enterEmailAddress(String emailAddress) {
        WebElement textBox = driver.findElement(By.name("username"));

        textBox.sendKeys(emailAddress);
    }

    private void navigateToHudlLoginPage() {

        navigateToHudlWebsite();

        driver.findElement(By.linkText("Log in")).click();

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));

        driver.findElement(By.className("subnav__item")).click();

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));

        driver.manage().window().maximize();
    }

    public void navigateToHudlWebsite() {

        driver.get("https://www.hudl.com");

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(3000));
    }
}
