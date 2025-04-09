package com.asos.ip;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class App 
{
    public static void main( String[] args ) {

        WebDriver driver = new ChromeDriver();

        navigateToHudlLoginPage(driver);

        Map<String, String> loginDetails = getLoginDetails();

        loginDetails = getLoginDetails();

        enterEmailAddress(driver, loginDetails.get("email"));

        enterPassword(driver, loginDetails.get("password"));

        scrollToFindElement(driver, "action");

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));

        driver.findElement(By.name("action")).click();
    }

    private static void scrollToFindElement(WebDriver driver, String elementName) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.name(elementName)));
    }

    private static void enterPassword(WebDriver driver, String password) {
        driver.findElement(By.name("action")).click();

        WebElement textBox = driver.findElement(By.name("password"));

        textBox.sendKeys(password);
    }

    public static Map<String, String> getLoginDetails() {
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

    private static void enterEmailAddress(WebDriver driver, String emailAddress) {
        WebElement textBox = driver.findElement(By.name("username"));

        textBox.sendKeys("middlehurstben@googlemail.com");
    }

    private static void navigateToHudlLoginPage(WebDriver driver) {

        navigateToHudlWebsite(driver);

        driver.findElement(By.linkText("Log in")).click();

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));

        driver.findElement(By.className("subnav__item")).click();

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));

        driver.manage().window().maximize();
    }

    public static void navigateToHudlWebsite(WebDriver driver) {

        driver.get("https://www.hudl.com");

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(3000));
    }

}
