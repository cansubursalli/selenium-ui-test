package com.trendyol.bootcamp;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class SmokeTest {

    /*
    * Browser acilir
    * trendyol.com a gidilir
    - popup ekrani kapatilir
    * Giris yapilir.
    * searchbar'a telefon yazılır
    * enter'a basilir
    * sayfanın ust kısmında telefon yazdıgı kontrol edilir
    * */

    WebDriver driver;
    private ExpectedConditions ExceptionConditions;

    @BeforeMethod
    public void startUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\chromedriver.exe");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.manage().window().maximize();

        driver.get("http://www.trendyol.com/");

        driver.findElement(By.className("fancybox-close")).click();
    }

    @Test
    public void shouldLogin() {
        Actions action = new Actions(driver);
        WebElement we = driver.findElement(By.className("link-text"));
        action.moveToElement(we).moveToElement(driver.findElement(By.className("link-text"))).click().build().perform();
        driver.findElement(By.id("login-email")).sendKeys("******");
        driver.findElement(By.id("login-password-input")).sendKeys("******");
        driver.findElement(By.className("submit")).click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExceptionConditions.visibilityOfElementLocated(By.className("component-list")));
    }

    @Test
    public void shouldSearch() {
        driver.findElement(By.className("search-box")).sendKeys("telefon");
        driver.findElement(By.className("search-box")).sendKeys(Keys.ENTER);

        String resultText = driver.findElement(By.cssSelector(".dscrptn > h1")).getText();
        Assert.assertEquals(resultText, "telefon");
    }

    @Test
    public void shouldDisplayRecommendationsOnSearchBox() {
        driver.findElement(By.className("search-box")).sendKeys("telefon");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        boolean searchRecommendations = driver.findElement(By.className("suggestion-title")).isDisplayed();
        Assert.assertEquals(searchRecommendations, true);
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}